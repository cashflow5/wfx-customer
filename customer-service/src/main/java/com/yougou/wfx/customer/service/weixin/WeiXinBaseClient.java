package com.yougou.wfx.customer.service.weixin;

import com.yougou.wfx.customer.model.weixin.base.WXAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXServerIpResponse;
import com.yougou.wfx.customer.model.weixin.base.WXTicketResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.model.weixin.custom.request.MessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.model.weixin.user.response.UserSearchResponse;

import feign.Param;
import feign.RequestLine;

/**
 * 微信的基础客户端
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/15 上午11:22
 * @since 1.0 Created by lipangeng on 16/4/15 上午11:22. Email:lipg@outlook.com.
 */
public interface WeiXinBaseClient {

    /**
     * 获取微信基础的授权用的AccessToken
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:37. Email:lipg@outlook.com.
     */
    @RequestLine("GET /cgi-bin/token?grant_type=client_credential&appid={appId}&secret={secret}")
    WXAccessTokenResponse getAccessToken(@Param("appId") String appId, @Param("secret") String secret);

    /**
     * 获取jssdk用的ticket
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:37. Email:lipg@outlook.com.
     */
    @RequestLine("GET /cgi-bin/ticket/getticket?access_token={accesstoken}&type=jsapi")
    WXTicketResponse getTicket(@Param("accesstoken") String accesstoken);

    /**
     * 获取用户的AccessToken
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:37. Email:lipg@outlook.com.
     */
    @RequestLine("GET sns/oauth2/access_token?appid={appId}&secret={secret}&code={code}&grant_type=authorization_code")
    WXUserAccessTokenResponse getUserAccessToken(@Param("appId") String appId, @Param("secret") String secret, @Param("code") String code);
    
    /**
     * 获取微信的用户基本信息的授权用的AccessToken
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:37. Email:lipg@outlook.com.
     */
    @RequestLine("GET sns/userinfo?access_token={accessToken}&openid={openId}&lang=zh_CN")
    WXUserInfoResponse getUserInfo(@Param("openId") String openId, @Param("accessToken") String accessToken);
    
    /**
     * 通过code换取网页授权access_token
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:37. Email:lipg@outlook.com.
     */
    @RequestLine("GET sns/oauth2/access_token?appid={appId}&secret={secret}&code={code}&grant_type=authorization_code")
    WXAccessTokenResponse getPageAccessToken(@Param("appId") String appId, @Param("secret") String secret, @Param("code") String code);
    
	/**
     * 刷新access_token
     *
     * @since 1.0 Created by lipangeng on 16/4/18 下午5:37. Email:lipg@outlook.com.
     */
    @RequestLine("GET sns/oauth2/refresh_token?appid={appId}&grant_type=refresh_token&refresh_token={accessToken}")
    WXAccessTokenResponse refreshToken(@Param("appId") String appId, @Param("accessToken") String accessToken);
    
    /**
     * 客服接口-发文本消息
     * @param accessToken
     * @param msg
     * @return
     */
    @RequestLine("POST cgi-bin/message/custom/send?access_token={accessToken}")
    MessageResponse sendTextMessage(@Param("accessToken") String accessToken, TextMessageRequest msg);
    
    /**
     * 获取公共号的用户列表
     * @param accessToken
     * @param msg
     * @return
     */
    @RequestLine("GET cgi-bin/user/get?access_token={accessToken}&next_openid={nextOpenId}")
    UserSearchResponse searchUser(@Param("accessToken") String accessToken, @Param("nextOpenId")String nextOpenId);
    
    /**
     * 获取用户基本信息(需要该用户（即openid）关注了公众号后，才能调用成功的。)
     * @param accessToken
     * @param openId
     * @return
     */
    @RequestLine("GET cgi-bin/user/info?access_token={accessToken}&openid={openId}&lang=zh_CN")
    WXUserInfoResponse getUserInfoInSubscribe(@Param("accessToken") String accessToken, @Param("openId")String openId);
    
    /**
     * 获取微信服务器IP地址
     * @param accessToken
     * @return
     */
    @RequestLine("GET cgi-bin/getcallbackip?access_token={accessToken}")
    WXServerIpResponse getWxServerIpList(@Param("accessToken") String accessToken);
}
