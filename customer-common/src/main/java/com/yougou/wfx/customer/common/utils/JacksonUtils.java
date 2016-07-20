package com.yougou.wfx.customer.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Jackson的工具
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/6 下午2:51
 * @since 1.0 Created by lipangeng on 16/4/6 下午2:51. Email:lipg@outlook.com.
 */
public class JacksonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static ObjectMapper mapper;

    private JacksonUtils() {
    }

    static {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        //mapper.setSerializationInclusion(Include.NON_EMPTY);
        //mapper.setSerializationInclusion(Include.NON_DEFAULT);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    /**
     * <p>转换方法，转换为字符串，并返回</p>
     *
     * @since 1.0 Created by 盼庚 on 2014/9/25 14:14. Email:lipg@outlook.com.
     */
    public static String Convert(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("转换对象异常", e);
        }
        return null;
    }

    /**
     * <p>转换json为对象</p>
     *
     * @since 1.0 Created by 盼庚 on 2014/11/27 14:39. Email:lipg@outlook.com.
     */
    public static <T> T Convert(String json, Class<T> classz) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, classz);
        } catch (IOException e) {
            logger.error("转换对象异常", e);
        }
        return null;
    }

    /**
     * <p>转换json为复杂对象javaType类型</p>
     *
     * @since 1.0 Created by 盼庚 on 2014/11/27 14:39. Email:lipg@outlook.com.
     */
    public static <T> T Convert(String json, JavaType javaType) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("转换对象异常", e);
        }
        return null;
    }

    /**
     * <p>构造javaType类型</p>
     *
     * @since 1.0 Created by 盼庚 on 2014/11/27 14:39. Email:lipg@outlook.com.
     */
    public static JavaType createJavaType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * <p>构造javaType类型</p>
     *
     * @since 1.0 Created by 盼庚 on 2014/11/27 14:39. Email:lipg@outlook.com.
     */
    public static JavaType createJavaType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * <p>类型构造器工厂</p>
     *
     * @since 1.0 Created by lipangeng on 15/9/24 上午9:35. Email:lipg@outlook.com.
     */
    public static TypeFactory getTypeFactory() {
        return mapper.getTypeFactory();
    }
}
