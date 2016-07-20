package com.yougou.wfx.customer.service.usercenter;

import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;

/**
 * 登陆服务
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/28 下午5:51
 * @since 1.0 Created by lipangeng on 16/3/28 下午5:51. Email:lipg@outlook.com.
 */
public interface ILoginService {
    /**
     * 登陆接口,用来获取登陆信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午5:53. Email:lipg@outlook.com.
     */
    UserLoginAccountVo doLogin(UserLoginVo loginVo);

    /**
     * 更新session中的用户信息
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午3:51. Email:lipg@outlook.com.
     */
    Result<SessionUserDetails> updateSessionUserDetails(UserLoginAccountVo loginAccountVo);

    /**
     * 更新记住我信息
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午3:54. Email:lipg@outlook.com.
     */
    Result updateRememberMe(SessionUserDetails userDetails);
    
    /**
     * 微信授权登录
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午5:53. Email:lipg@outlook.com.
     */
    UserLoginAccountVo doWXLogin(WXUserInfoResponse userInfo);
    
    /**
     * 微信注册
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午5:53. Email:lipg@outlook.com.
     */
    WFXResult<MemberAccountOutputDto> saveWxUser(WXUserInfoResponse userInfo, String parentSellerId);
    
    /**
     * 微信绑定手机号
     *
     * @zheng.qq
     */
    Result phoneSellerBind(String unionId, String phoneNum, String smsCode, String passWord);
    
    /**
     * 手机号登录绑定微信号
     *
     * @zheng.qq
     */
    Result wxSellerBind(String unionId, String nickName, int sex, String headImgUrl, String memberId);

    /**
	 * 保存微信用户并放入Session中
	 * @param userInfo
	 * @return
	 */
	void doWithWXUserInfo(WXUserInfoResponse userInfo, String appId, String secret);
	
}
