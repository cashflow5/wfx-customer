package com.yougou.wfx.customer.common.cookie;

import com.yougou.wfx.customer.common.constant.CookieConstant;
import com.yougou.wfx.customer.common.crypto.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.Charset;

/**
 * 用于cookie的加密和解密
 *
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/31
 */
public final class CookieSecurity {

    private static final Logger logger = LoggerFactory.getLogger(CookieSecurity.class);

    private static Cipher DECRYPT_COOKIE_VALUE = CryptoUtils.AES(CookieConstant.COOKIE_SHOOPING_CART_AES_KEY, Cipher.DECRYPT_MODE);
    private static Cipher ENCRYPT_COOKIE_VALUE = CryptoUtils.AES(CookieConstant.COOKIE_SHOOPING_CART_AES_KEY, Cipher.ENCRYPT_MODE);


    /**
     * 加密方法
     *
     * @param str 待加密字符串
     * @return
     */
    public static String encrypt(String str) {
        Assert.hasText(str, "待加密内容不能为空！");
        String encrypt = "";
        try {
            byte[] result = ENCRYPT_COOKIE_VALUE.doFinal(str.getBytes(Charset.forName("utf-8")));
            encrypt = CryptoUtils.bytes2Hex(result);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("Cookie加密错误", e);
        }
        return encrypt;
    }

    /**
     * 解密方法
     *
     * @param str 待解密字符串
     * @return
     */
    public static String decrypt(String str) {
        Assert.hasText(str, "待解密内容不能为空！");
        String decrypt = "";
        try {
            byte[] data = CryptoUtils.hex2Bytes(str);
            decrypt = new String(DECRYPT_COOKIE_VALUE.doFinal(data), Charset.forName("utf-8"));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("Cookie解密错误", e);
        }
        return decrypt;
    }
}
