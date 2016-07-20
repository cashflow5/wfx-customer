package com.yougou.wfx.customer.common.crypto;

import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/24 下午5:32
 * @since 1.0 Created by lipangeng on 16/3/24 下午5:32. Email:lipg@outlook.com.
 */
public class CryptoUtils {
    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    /**
     * 获取AES加密的工具
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午10:33. Email:lipg@outlook.com.
     */
    public static Cipher AES(byte[] keys, int model) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(model, new SecretKeySpec(keys, "AES"));
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            logger.error("AES加密工具初始化失败", e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 二进制转十六进制字符串
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午5:33. Email:lipg@outlook.com.
     */
    public static String bytes2Hex(byte bytes[]) {
        StringBuilder sb = new StringBuilder();
        String temp;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            temp = Integer.toHexString(0xFF & b);
            // 每个字节8为，转为16进制标志，2个16进制位
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 十六进制转二进制字符串
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午5:34. Email:lipg@outlook.com.
     */
    public static byte[] hex2Bytes(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 进行MD5加密
     *
     * @since 1.0 Created by lipangeng on 16/5/6 下午4:42. Email:lipg@outlook.com.
     */
    public static String passwordMd5(String password) {
        try {
            return Hashing.md5().hashString(password, Charset.forName("utf-8")).toString().toUpperCase();
        } catch (Exception e) {
            logger.error("进行MD5加密发生异常.", e);
        }
        return null;
    }
}
