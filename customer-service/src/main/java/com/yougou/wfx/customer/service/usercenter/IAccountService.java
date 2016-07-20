package com.yougou.wfx.customer.service.usercenter;

import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.account.UserAccountVo;
import com.yougou.wfx.customer.model.usercenter.account.UserInfoVo;

import java.util.Date;

/**
 * 用户service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/30 下午4:43
 * @since 1.0 Created by lipangeng on 16/3/30 下午4:43. Email:lipg@outlook.com.
 */
public interface IAccountService {
    /**
     * 检查手机号是否已经存在
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午2:58. Email:lipg@outlook.com.
     */
    boolean hasPhoneNumber(String phoneNumber);

    /**
     * 重置用户密码
     *
     * @param loginName
     *         用户名
     * @param loginPassword
     *         密码
     * @param validCode
     *         短信验证码
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午4:18. Email:lipg@outlook.com.
     */
    Result resetPassword(String loginName, String loginPassword, String validCode);

    /**
     * 创建用户信息
     *
     * @param loginName
     *         用户名
     * @param loginPassword
     *         密码
     * @param SessionUtils.getCurrentShopId() 
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午4:02. Email:lipg@outlook.com.
     */
    Result createAccount(String loginName, String loginPassword, String validCode, String currentShopId);


    /**
     * 获取用户账户信息
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午5:49. Email:lipg@outlook.com.
     */
    UserAccountVo getUserAccount(String userId);

    /**
     * 获取用户的profile信息
     *
     * @since 1.0 Created by lipangeng on 16/4/4 下午1:58. Email:lipg@outlook.com.
     */
    UserInfoVo getUserInfo(String userId);

    /**
     * 更新用户信息
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午6:22. Email:lipg@outlook.com.
     */
    Result updateUserBirthday(String userId, Date birthday);

    /**
     * 更新用户性别信息
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午6:23. Email:lipg@outlook.com.
     */
    Result updateUserSex(String userId, Integer sex);

    /**
     * 更新用户头像信息
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午6:24. Email:lipg@outlook.com.
     */
    Result updateUserHeadImage(String userId, String url);


    /**
     * 给用户发送短信验证码
     *
     * @since 1.0 Created by lipangeng on 16/4/25 上午9:50. Email:lipg@outlook.com.
     */
    Result sendSMSCode(String phoneNum);

    /**
     * 验证短信验证码是否正确
     *
     * @since 1.0 Created by lipangeng on 16/4/25 上午10:43. Email:lipg@outlook.com.
     */
    Result checkSMSCode(String phoneNum, String vaildCode);



    String getRegisterCheckMobile(String userId);

}
