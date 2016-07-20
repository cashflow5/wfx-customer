package com.yougou.wfx.customer.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/21 上午8:44
 * @since 1.0 Created by lipangeng on 16/4/21 上午8:44. Email:lipg@outlook.com.
 */
public class JacksonXmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static XmlMapper mapper;

    private JacksonXmlUtils() {
    }

    static {
        mapper = new XmlMapper();
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
    public static <T> T Convert(String xml, Class<T> classz) {
        if (xml == null) {
            return null;
        }
        try {
            return mapper.readValue(xml, classz);
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
    public static <T> T Convert(String xml, JavaType javaType) {
        if (xml == null) {
            return null;
        }
        try {
            return mapper.readValue(xml, javaType);
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
    public static JavaType createJavaType(Type type) {
        return mapper.getTypeFactory().constructType(type);
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
