package com.yougou.wfx.customer.service.message.impl;

import com.yougou.wfx.customer.common.constant.RedisConstant;
import com.yougou.wfx.customer.common.math.MathRandomUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.message.ISMSMessageService;
import com.yougou.wfx.messenger.api.front.IMessengerFrontApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 短信消息的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/7 上午9:48
 * @since 1.0 Created by lipangeng on 16/4/7 上午9:48. Email:lipg@outlook.com.
 */
@Service
public class SMSMessageService implements ISMSMessageService {
    private static final Logger logger = LoggerFactory.getLogger(SMSMessageService.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMessengerFrontApi messengerFrontApi;

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
    @Override
    public Result sendSMSCode(String phone, String message) {
        messengerFrontApi.sendSms(new String[]{phone}, message);
        return Result.create();
    }

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
    @Override
    public Result<String> getRandomSMSCode(String phone, int length, int codeLive, int interval) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // 获取短信验证码,如果不存在则创建,存在则检查是否
        String smsCode = valueOperations.get(RedisConstant.WFX_SMS_CODE_PREFIX + phone);
        if (smsCode == null) {
            smsCode = MathRandomUtils.getFixLengthString(length);
        } else if (redisTemplate.getExpire(RedisConstant.WFX_SMS_CODE_PREFIX + phone, TimeUnit.SECONDS) > (codeLive - interval)) {
            logger.warn("用户:{}获取短信验证码间隔小于60秒,怀疑接口异常调用.", phone);
            return Result.create(false, 300, "温馨提示：" + interval + "秒内不能频繁获取验证码!", smsCode);
        }
        valueOperations.set(RedisConstant.WFX_SMS_CODE_PREFIX + phone, smsCode, codeLive, TimeUnit.SECONDS);
        return Result.create(true, "", smsCode);
    }

    /**
     * 获取短信数字验证码
     *
     * @param phone
     *         手机号
     *
     * @since 1.0 Created by lipangeng on 16/4/7 上午9:43. Email:lipg@outlook.com.
     */
    @Override
    public String getSMSCode(String phone) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(RedisConstant.WFX_SMS_CODE_PREFIX + phone);
    }


}
