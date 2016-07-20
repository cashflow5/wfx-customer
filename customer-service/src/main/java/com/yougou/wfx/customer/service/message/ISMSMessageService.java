package com.yougou.wfx.customer.service.message;

import com.yougou.wfx.customer.model.common.Result;

/**
 * 短信验证码Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/7 上午9:39
 * @since 1.0 Created by lipangeng on 16/4/7 上午9:39. Email:lipg@outlook.com.
 */
public interface ISMSMessageService {
    /**
     * 发送验证短信,一分钟内只允许发送一条
     *
     * @param phone
     *         要发送给信息的手机号
     * @param message
     *         要发送的信息
     *
     * @since 1.0 Created by lipangeng on 16/4/7 上午9:41. Email:lipg@outlook.com.
     */
    Result sendSMSCode(String phone, String message);

    /**
     * 获取短信数字验证码
     *
     * @param phone
     *         手机号
     * @param length
     *         长度
     * @param codeLive
     *         短信验证码存活时间
     * @param interval
     *         短信验证码重复获取时间
     *
     * @since 1.0 Created by lipangeng on 16/4/7 上午9:43. Email:lipg@outlook.com.
     */
    Result<String> getRandomSMSCode(String phone, int length, int codeLive, int interval);


    /**
     * 获取短信数字验证码
     *
     * @param phone
     *         手机号
     *
     * @since 1.0 Created by lipangeng on 16/4/7 上午9:43. Email:lipg@outlook.com.
     */
    String getSMSCode(String phone);
}
