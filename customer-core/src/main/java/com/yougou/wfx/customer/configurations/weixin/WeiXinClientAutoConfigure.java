package com.yougou.wfx.customer.configurations.weixin;

import com.yougou.wfx.customer.service.weixin.WeiXinBaseClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信客户端的自动配置
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/15 上午11:41
 * @since 1.0 Created by lipangeng on 16/4/15 上午11:41. Email:lipg@outlook.com.
 */
@Configuration
public class WeiXinClientAutoConfigure {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinClientAutoConfigure.class);

    @Bean
    public WeiXinBaseClient weiXinBaseClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(WeiXinBaseClient.class, "https://api.weixin.qq.com/");
    }
}
