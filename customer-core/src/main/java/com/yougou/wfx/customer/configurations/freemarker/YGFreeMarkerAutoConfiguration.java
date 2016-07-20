package com.yougou.wfx.customer.configurations.freemarker;

import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.Servlet;

import java.util.Map;

/**
 * freemarker的扩展配置文件
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/23 上午10:22
 * @since 1.0 Created by lipangeng on 16/3/23 上午10:22. Email:lipg@outlook.com.
 */
@Configuration
@ConditionalOnClass(Servlet.class)
@ConditionalOnWebApplication
public class YGFreeMarkerAutoConfiguration extends org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration
        .FreeMarkerWebConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(YGFreeMarkerAutoConfiguration.class);
    @Autowired
    private YGFreeMarkerProperties ygFreeMarkerProperties;
    @Autowired
    private WFXProperties wfxProperties;
    @Autowired
    private WeiXinProperties weiXinProperties;

    @Bean
    @Override
    @ConditionalOnMissingBean(FreeMarkerConfig.class)
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = super.freeMarkerConfigurer();
        applyYGProperites(configurer);
        logger.info("配置FreeMarker完成");
        return configurer;
    }

    private void applyYGProperites(FreeMarkerConfigurer configurer) {
        Map<String, Object> variables = ygFreeMarkerProperties.getVariables();
        variables.put("myOrderListCommoditySize", wfxProperties.getMyOrderListShowCommoditySize());
        variables.put("appId", weiXinProperties.getAppId());
        variables.put("appSecret", weiXinProperties.getAppSecret());
        variables.put("key", weiXinProperties.getKey());
       
        configurer.setFreemarkerVariables(variables);
    }

}
