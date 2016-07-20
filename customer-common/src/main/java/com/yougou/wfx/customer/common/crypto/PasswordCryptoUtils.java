package com.yougou.wfx.customer.common.crypto;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.Charset;

/**
 * 密码工具
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/25 下午3:06
 * @since 1.0 Created by lipangeng on 16/4/25 下午3:06. Email:lipg@outlook.com.
 */
public class PasswordCryptoUtils {
    private static final Logger logger = LoggerFactory.getLogger(PasswordCryptoUtils.class);
    private static Cipher DECRYPT_PASSWORD_VALUE = CryptoUtils.AES(SessionConstant.PASSWORD_AES_KEY, Cipher.DECRYPT_MODE);
    private static Cipher ENCRYPT_PASSWORD_VALUE = CryptoUtils.AES(SessionConstant.PASSWORD_AES_KEY, Cipher.ENCRYPT_MODE);

    /**
     * 加密密码
     *
     * @since 1.0 Created by lipangeng on 16/4/25 下午3:09. Email:lipg@outlook.com.
     */
    public static String encryptPassword(String password) {
        if (Strings.isNullOrEmpty(password)) {
            return "";
        }
        try {
            byte[] result = ENCRYPT_PASSWORD_VALUE.doFinal(password.getBytes(Charset.forName("utf-8")));
            return CryptoUtils.bytes2Hex(result);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("加密密码失败", e);
            return "";
        }
    }

    /**
     * 解密密码
     *
     * @since 1.0 Created by lipangeng on 16/4/25 下午3:09. Email:lipg@outlook.com.
     */
    public static String decryptPassword(String passwordEncode) {
        if (Strings.isNullOrEmpty(passwordEncode)) {
            return "";
        }
        try {
            byte[] bytes = CryptoUtils.hex2Bytes(passwordEncode);
            if (bytes == null) {
                return "";
            }
            return new String(DECRYPT_PASSWORD_VALUE.doFinal(bytes), Charset.forName("utf-8"));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("解密密码失败", e);
            return "";
        }
    }
}
