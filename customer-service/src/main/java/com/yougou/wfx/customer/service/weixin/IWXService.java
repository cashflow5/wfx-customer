package com.yougou.wfx.customer.service.weixin;

import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.weixin.base.WXAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.model.weixin.config.WXConfigVo;
import com.yougou.wfx.customer.model.weixin.custom.request.MessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.model.weixin.order.WXCreateOrderReqVo;
import com.yougou.wfx.customer.model.weixin.order.WXCreateOrderResVo;
import com.yougou.wfx.customer.model.weixin.user.response.UserSearchResponse;

/**
 * 微信的Service接口
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/18 上午10:13
 * @since 1.0 Created by lipangeng on 16/4/18 上午10:13. Email:lipg@outlook.com.
 */
public interface IWXService {

    /**
     * 刷新微信的AccessToken接口
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:14. Email:lipg@outlook.com.
     */
    Result flushAccessToken(Long before, Boolean force, String appId, String secret);


    /**
     * 刷新js的sdk的ticket
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:15. Email:lipg@outlook.com.
     */
    Result flushJSTicket(Long before, Boolean force);

    /**
     * 获取微信的AccessToken
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:50. Email:lipg@outlook.com.
     */
    String getAccessToken();
    
    /**
     * 获取微信的AccessToken
     * @param appId
     * @param secret
     * @return
     */
    String getAccessToken(String appId, String secret);

    /**
     * 获取微信JS的Ticket
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:51. Email:lipg@outlook.com.
     */
    String getJSTicket();

    /**
     * 获取当前微信配置
     *
     * @param appId
     *
     * @return
     */
    WXConfigVo getWXConfig(String appId);

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
    Result<String> getUserOpenId(String appId, String secret, String code);

    /**
     * 根据serverId下载微信图片
     *
     * @param url
     *         多媒体下载url
     * @param serverId
     *
     * @return
     */
    Result<Boolean> uploadShopPicture(String url, String serverId);

    /**
     * 创建订单
     *
     * @since 1.0 Created by lipangeng on 16/4/19 上午10:27. Email:lipg@outlook.com.
     */
    Result<WXCreateOrderResVo> createOrder(WXCreateOrderReqVo createOrderReq);


    /**
     * 从微信获取并上传用户头像
     *
     * @since 1.0 Created by lipangeng on 16/4/22 上午10:58. Email:lipg@outlook.com.
     */
    Result<String> downloadHeadImg(String url, String imgId);

    /**
     * 微信获取用户基本新的的url
     *
     * @param callBackUrl
     *         获取code后的回调地址
     *
     * @since 1.0 Created by lipangeng on 16/4/22 上午11:40. Email:lipg@outlook.com.
     */
    Result<String> getAuthorizeBaseUrl(String wxAuthorizeUrl, String appId, String callBackUrl);


	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	Result<WXUserInfoResponse> getUserInfo(String openId, String accessToken);
	
	/**
	 * 获取用户基本信息(需要该用户（即openid）关注了公众号后，才能调用成功的。)
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	Result<WXUserInfoResponse> getUserInfoInSubscribe(String openId, String accessToken);


	/**
	 * 通过code换取网页授权access_token
	 * @param appId
	 * @param secret
	 * @param code
	 * @return
	 */
	Result<WXAccessTokenResponse> getAccessTokenByCode(String appId,
			String secret, String code);


	/**
	 * 刷新access_token（如果需要）
	 * @param appId
	 * @param refreshToken
	 * @return
	 */
	Result<WXAccessTokenResponse> getRefreshToken(String appId,
			String refreshToken);
	
	
	/**
	 * 客服接口-发消息(文本消息)
	 * @param accessToken  调用接口凭证
	 * @param request
	 * @return
	 */
	Result<MessageResponse> sendTextMessage(String accessToken, TextMessageRequest request);
	
	/**
	 * 用户管理-获取用户列表
	 * @param accessToken  调用接口凭证
	 * @param nextOpenId  第一个拉取的OPENID，不填默认从头开始拉取
	 * @return
	 */
	Result<UserSearchResponse> searchUser(String accessToken, String nextOpenId);

}
