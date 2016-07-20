package com.yougou.wfx.customer.service.weixin.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.yougou.pay.vo.PayReqVo;
import com.yougou.pay.vo.PayResVo;
import com.yougou.tools.common.utils.StringUtil;
import com.yougou.wfx.customer.common.constant.RedisConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.HttpClientUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.common.utils.RandomUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.weixin.base.WXAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXServerIpResponse;
import com.yougou.wfx.customer.model.weixin.base.WXTicketResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.model.weixin.config.WXConfigVo;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.model.weixin.order.WXCreateOrderReqVo;
import com.yougou.wfx.customer.model.weixin.order.WXCreateOrderResVo;
import com.yougou.wfx.customer.model.weixin.order.WXJSPayVo;
import com.yougou.wfx.customer.model.weixin.user.response.UserSearchResponse;
import com.yougou.wfx.customer.service.pay.IPayService;
import com.yougou.wfx.customer.service.redis.IRedisLockService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.customer.service.weixin.WeiXinBaseClient;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ImageFtpTypeEnum;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.system.api.IFileUploadApi;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * 微信的service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/18 上午10:17
 * @since 1.0 Created by lipangeng on 16/4/18 上午10:17. Email:lipg@outlook.com.
 */
@Service
public class WXService implements IWXService {
    private static final Logger logger = LoggerFactory.getLogger(WXService.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private WeiXinBaseClient weiXinBaseClient;
    @Autowired
    private IRedisLockService redisLockService;
    @Autowired
    private IFileUploadApi fileUploadApi;
    @Autowired
    private IOrderFrontApi orderFrontApi;
    @Autowired
    private IPayService payService;


    /**
     * 刷新微信的AccessToken接口
     *
     * @param before
     *         AccessToken提前刷新时间,单位是毫秒
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:14. Email:lipg@outlook.com.
     */
    @Override
    public Result flushAccessToken(Long before, Boolean force, String appId, String secret) {
        before = before == null ? 1000 * 60 * 5 : before;
        force = force == null ? false : force;
        Result result = null;
        boolean theLock = redisLockService.acquireLock(RedisConstant.WFX_REDIS_ACCESS_TOKEN_LOCK_KEY, 10 * 1000);
        if (theLock) {
            Long expire = redisTemplate.getExpire(RedisConstant.WFX_REDIS_WX_TOKEN_KEY, TimeUnit.MILLISECONDS);
            if (force || (expire != null && expire < before)) {
                WXAccessTokenResponse accessToken = weiXinBaseClient.getAccessToken(appId, secret);
                if (accessToken == null || Strings.isNullOrEmpty(accessToken.getAccessToken())) {
                    if (accessToken == null) {
                        result = Result.create(false, 500, "无法访问微信端口,获取AccessToken");
                    } else {
                        result = Result.create(false, accessToken.getErrcode(), accessToken.getErrmsg());
                    }
                } else {
                    redisTemplate.opsForValue()
                            .set(RedisConstant.WFX_REDIS_WX_TOKEN_KEY,
                                 accessToken.getAccessToken(),
                                 accessToken.getExpiresIn(),
                                 TimeUnit.SECONDS);
                    result = Result.create();
                }
            }else{
            	//根据接口判断accessToken是否可用
            	String accessToken =  redisTemplate.opsForValue().get(RedisConstant.WFX_REDIS_WX_TOKEN_KEY);
            	WXServerIpResponse response = weiXinBaseClient.getWxServerIpList(accessToken);
            	if(response!=null && (response.getErrCode()==40001||response.getErrCode()==40014||response.getErrCode()==42001)){
            		flushAccessToken(null, true, appId, secret);
            		result = Result.create(true, 300, "accessToken已经不可用，强制刷新！");
            	}
            }
            // 执行成功之后解除锁
            redisLockService.releaseLock(RedisConstant.WFX_REDIS_ACCESS_TOKEN_LOCK_KEY);
        }
        if (result != null) {
            return result;
        } else {
            return Result.create(true, 300, "成功执行,但AccessToken未刷新.");
        }
    }

    /**
     * 刷新js的sdk的ticket
     *
     * @param before
     *         flushJSTicket提前刷新时间,单位是毫秒
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:15. Email:lipg@outlook.com.
     */
    @Override
    public Result flushJSTicket(Long before, Boolean force) {
        before = before == null ? 1000 * 60 * 5 : before;
        force = force == null ? false : force;
        Result result = null;
        boolean theLock = redisLockService.acquireLock(RedisConstant.WFX_REDIS_TICKET_LOCK_KEY, 10 * 1000);
        if (theLock) {
            Long expire = redisTemplate.getExpire(RedisConstant.WFX_REDIS_WX_TICKET_KEY, TimeUnit.MILLISECONDS);
            if (force || (expire != null && expire < before)) {
                WXTicketResponse ticket = weiXinBaseClient.getTicket(getAccessToken());
                if (ticket == null || Strings.isNullOrEmpty(ticket.getTicket())) {
                    if (ticket == null) {
                        result = Result.create(false, 500, "无法访问微信端口,获取JSTicket");
                    } else {
                        result = Result.create(false, ticket.getErrCode(), ticket.getErrMsg());
                    }
                } else {
                    redisTemplate.opsForValue()
                            .set(RedisConstant.WFX_REDIS_WX_TICKET_KEY, ticket.getTicket(), ticket.getExpiresIn(), TimeUnit.SECONDS);
                    result = Result.create();
                }
            }
            // 执行完成之后释放锁
            redisLockService.releaseLock(RedisConstant.WFX_REDIS_TICKET_LOCK_KEY);
        }
        if (result != null) {
            return result;
        } else {
            return Result.create(true, 300, "执行成功,但未更新JSTicket");
        }
    }

    public String getAccessToken() {
        return redisTemplate.opsForValue().get(RedisConstant.WFX_REDIS_WX_TOKEN_KEY);
    }
    
    @Override
    public String getAccessToken(String appId, String secret) {
    	String rs = "";
    	String accessToken =  redisTemplate.opsForValue().get(RedisConstant.WFX_REDIS_WX_TOKEN_KEY);
    	WXServerIpResponse response = weiXinBaseClient.getWxServerIpList(accessToken);
    	if(response!=null && response.getErrCode()==0){
    		rs = accessToken;
    	}else{
    		this.flushAccessToken(null, true, appId, secret);
    		rs = redisTemplate.opsForValue().get(RedisConstant.WFX_REDIS_WX_TOKEN_KEY);
    	}
        return rs;
    }

    public String getJSTicket() {
        return redisTemplate.opsForValue().get(RedisConstant.WFX_REDIS_WX_TICKET_KEY);
    }

    /**
     * 获取当前微信配置
     *
     * @param appId
     *         微信配置的appId
     *
     * @return
     */
    @Override
    public WXConfigVo getWXConfig(String appId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        WXConfigVo configVo = new WXConfigVo();
        configVo.setAppId(appId);
        configVo.setNonceStr(RandomUtils.getSignature());
        long timestamp = System.currentTimeMillis() / 1000;
        configVo.setTimestamp(timestamp);
        StringBuilder sb = new StringBuilder();
        sb.append("jsapi_ticket=")
                .append(getJSTicket())
                .append("&noncestr=")
                .append(configVo.getNonceStr())
                .append("&timestamp=")
                .append(configVo.getTimestamp())
                .append("&url=");
        if(StringUtils.isEmpty(request.getQueryString())){
        	sb.append(request.getRequestURL());
        }else{
        	sb.append(request.getRequestURL()+"?"+request.getQueryString());
        }
                
        String signature = Hashing.sha1().hashString(sb.toString(), Charsets.UTF_8).toString();
        configVo.setSignature(signature);
        return configVo;
    }
    
   
    @Override
    public Result<WXUserInfoResponse> getUserInfo(String openId, String accessToken) {
    	WXUserInfoResponse responese = weiXinBaseClient.getUserInfo(openId, accessToken);
    	if(StringUtils.isEmpty(responese.getSex())){
    		responese.setSex("0");
    	}
    	if(responese.hasError()){
    		return Result.create(false, "拉取用户信息失败");
    	}else{
    		return Result.create(true, "成功拉取用户信息", responese);
    	}
    }
    

    @Override
    public Result<WXUserInfoResponse> getUserInfoInSubscribe(String openId, String accessToken) {
    	if(StringUtils.isEmpty(accessToken)){
			accessToken = getAccessToken();
		}
    	WXUserInfoResponse responese = weiXinBaseClient.getUserInfoInSubscribe(accessToken, openId);
    	if(StringUtils.isEmpty(responese.getSex())){
    		responese.setSex("0");
    	}
    	if(responese.hasError()){
    		return Result.create(false, "拉取用户信息失败 ,错误代号：" + responese.getErrCode() + ", 错误信息：" + responese.getErrMsg());
    	}else{
    		return Result.create(true, "成功拉取用户信息", responese);
    	}
    }
    
    /**
     * 通过code换取网页授权access_token
     *
     */
    @Override
    public Result<WXAccessTokenResponse> getAccessTokenByCode(String appId, String secret, String code) {
    	WXAccessTokenResponse responese = weiXinBaseClient.getPageAccessToken(appId, secret, code);
    	if(responese.hasError()){
    		return Result.create(false, "通过code换取网页授权access_token失败");
    	}else{
    		return Result.create(true, "成功通过code换取网页授权access_token", responese);
    	}
        
    }

    /**
     * 刷新access_token（如果需要）
     *
     */
    @Override
    public Result<WXAccessTokenResponse> getRefreshToken(String appId, String refreshToken) {
    	WXAccessTokenResponse responese = weiXinBaseClient.refreshToken(appId, refreshToken);
    	if(responese.hasError()){
    		return Result.create(false, "刷新access_token");
    	}else{
    		return Result.create(true, "刷新access_token", responese);
    	}
    }

    /**
     * 获取用户的openId信息
     *
     * @param appId
     *         微信的appId
     * @param secret
     *         温馨的secret
     * @param code
     *         微信返回的认证code
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:40. Email:lipg@outlook.com.
     */
    @Override
    public Result<String> getUserOpenId(String appId, String secret, String code) {
        WXUserAccessTokenResponse userAccessToken = weiXinBaseClient.getUserAccessToken(appId, secret, code);
        if (userAccessToken == null || userAccessToken.getErrcode() != null) {
            logger.error("获取用户授权信息失败.appid:{}|secret:{}|code:{}", appId, secret, code);
            if (userAccessToken == null) {
                return Result.create(false, 500, "获取用户授权信息接口调用失败");
            } else {
                return Result.create(false, 500, userAccessToken.getErrcode() + ":" + userAccessToken.getErrmsg());
            }
        }
        return Result.create(true, "", userAccessToken.getOpenid());
    }
    
    

    /**
     * 根据serverId下载微信图片
     *
     * @param serverId
     *
     * @return
     */
    @Override
    public Result<Boolean> uploadShopPicture(String url, String serverId) {
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        if (user == null) {
            return Result.create().setHasError(Boolean.TRUE).setMessage("下载图片异常，用户未登录!");
        }
        String requestUrl = String.format(url, getAccessToken(), serverId);
        logger.info("微信图片下载地址:{}", requestUrl);
        byte[] imageBytes = HttpClientUtils.getInstance().downImage(requestUrl).getContent();
        if (ObjectUtils.isEmpty(imageBytes)) {
            return Result.create().setHasError(Boolean.TRUE).setMessage("下载图片异常，返回为空!");
        }
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        Result<Boolean> result = null;
        try {
            WFXResult<String> wfxResult = fileUploadApi.frontImageUpload(serverId, ImageFtpTypeEnum.SHOP_LOGO_IMG, inputStream);
            if (wfxResult == null) {
                result = Result.create().setHasError(Boolean.TRUE).setMessage("图片下载接口异常");
            }
            if (wfxResult.getResultCode() == 0) {
                // TODO: 2016/4/19 保存图片路径
                result = Result.create(Boolean.TRUE, 200, "ok", Boolean.TRUE);
            } else {
                result = Result.create().setHasError(Boolean.TRUE).setMessage(wfxResult.getResultMsg()).setCode(wfxResult.getResultCode());
            }
        } catch (Exception e) {
            logger.info("微信图片下载异常,地址:{},错误信息:{}", requestUrl, e.getMessage());
            result = Result.create().setHasError(Boolean.TRUE).setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 创建订单
     *
     * @param createOrderReq
     *
     * @since 1.0 Created by lipangeng on 16/4/19 上午10:27. Email:lipg@outlook.com.
     */
    @Override
    public Result<WXCreateOrderResVo> createOrder(WXCreateOrderReqVo createOrderReq) {
        if (createOrderReq == null) {
            return Result.create(false, 500, "无法创建订单,参数无效.");
        }
        WXCreateOrderResVo createOrderRes = new WXCreateOrderResVo();
        PayReqVo payReqVo = createOrderReq.toPayReqDto();
        Result<PayResVo> createResult = payService.createOrder(payReqVo);
        // 判断创建订单是否有误
        if (createResult.hasError()) {
            return Result.create(false, createResult.getCode(), createResult.getMessage());
        }
        // 构建微信支付信息
        WXJSPayVo wxjsPay = JacksonUtils.Convert(createResult.getData().getActionForm(), WXJSPayVo.class);
        if (wxjsPay != null) {
            createOrderRes.setWxjsPay(wxjsPay);
        } else {
            return Result.create(false, 500, "无法获取微信的支付信息");
        }
        return Result.create(true, "创建微信订单成功", createOrderRes);
    }

    /**
     * 根据serverId下载微信图片
     *
     * @param imgId
     *
     * @return
     */
    @Override
    public Result<String> downloadHeadImg(String url, String imgId) {
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        if (user == null) {
            return Result.create(false, "更新用户头像异常，用户未登录!");
        }
        String requestUrl = String.format(url, getAccessToken(), imgId);
        logger.info("微信图片下载地址:{}", requestUrl);
        HttpClientUtils.NetFile image = HttpClientUtils.getInstance().downImage(requestUrl);
        byte[] imageBytes = image.getContent();
        if (ObjectUtils.isEmpty(imageBytes)) {
            return Result.create(false, "更新用户头像异常，下载图片为空!");
        }
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        Result<String> result = null;
        try {
            WFXResult<String> wfxResult =
                    fileUploadApi.frontImageUpload(Strings.isNullOrEmpty(image.getFileName()) ? imgId : image.getFileName(),
                                                   ImageFtpTypeEnum.MEMBER_LOGO_IMG,
                                                   inputStream);
            if (wfxResult == null) {
                return Result.create(false, "图片下载接口异常");
            }
            if (wfxResult.getResultCode() == 0) {
                result = Result.create(Boolean.TRUE, 200, "ok", wfxResult.getResult());
            } else {
                result = Result.create(false, wfxResult.getResultCode(), wfxResult.getResultMsg());
            }
        } catch (Exception e) {
            logger.info(String.format("用户头像更新异常,头像的微信地址:%s,错误信息:%s", requestUrl, e.getMessage()), e);
            result = Result.create(false, "无法保存头像数据");
        }
        return result;
    }

    /**
     * 微信获取用户基本新的的url
     *
     * @param callBackUrl
     *         获取code后的回调地址
     *
     * @since 1.0 Created by lipangeng on 16/4/22 上午11:40. Email:lipg@outlook.com.
     */
    @Override
    public Result<String> getAuthorizeBaseUrl(String wxAuthorizeUrl, String appId, String callBackUrl) {
        if (Strings.isNullOrEmpty(wxAuthorizeUrl) || Strings.isNullOrEmpty(appId) || Strings.isNullOrEmpty(callBackUrl)) {
            logger.error("参数错误,无法获取微信授权回调地址");
            return Result.create(false, "参数错误,无法获取微信授权回调地址.");
        }
        try {
            callBackUrl = URLEncoder.encode(callBackUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码微信授权地址错误", e);
        }
        return Result.create(true, "成功获取", String.format(wxAuthorizeUrl, appId, callBackUrl, "state"));
    }

    @Override
	public Result<MessageResponse> sendTextMessage(String accessToken, TextMessageRequest request) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(accessToken)){
			accessToken = getAccessToken();
		}
		if(!StringUtils.isEmpty(request.getToUser())){
			WXUserInfoResponse rs = weiXinBaseClient.getUserInfoInSubscribe(accessToken, request.getToUser());
			if(rs!=null && rs.getSubscribe().equals("1")){
				MessageResponse response = weiXinBaseClient.sendTextMessage(accessToken, request);
				if(response.getErrCode()==0){
					return Result.create(true, "消息发送成功！", response);
				}else{
					return Result.create(false, "消息发送失败！", response);
				}
			}else{
				return Result.create(false, "用户没有关注微信公共号，无法发送消息！", null);
			}
		}else{
			return Result.create(false, "消息接收者openId为null", null);
		}
		
	}

	@Override
	public Result<UserSearchResponse> searchUser(String accessToken,
			String nextOpenId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(accessToken)){
			accessToken = getAccessToken();
		}
		if(StringUtils.isEmpty(accessToken)){
			logger.error("获取调用接口凭证失败！");
			return Result.create(false, "获取调用接口凭证失败！", null);
		}
		System.out.println(accessToken);
		UserSearchResponse response = weiXinBaseClient.searchUser(accessToken, nextOpenId);
		if(response.getErrCode()==0){
			return Result.create(true, "获取用户列表成功！", response);
		}else{
			return Result.create(false, "获取用户列表失败！", response);
		}
	}
}
