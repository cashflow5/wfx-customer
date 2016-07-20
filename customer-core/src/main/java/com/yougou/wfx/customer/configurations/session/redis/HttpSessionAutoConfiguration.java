package com.yougou.wfx.customer.configurations.session.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 启用HttpSession的Redis配置
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/23 下午3:10
 * @since 1.0 Created by lipangeng on 16/3/23 下午3:10. Email:lipg@outlook.com.
 */
@EnableRedisHttpSession(redisNamespace = "wfx")
@Configuration
public class HttpSessionAutoConfiguration {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("WFXSSIONID"); // <1>
        serializer.setCookiePath("/"); // <2>
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // <3>
//        serializer.setDomainNamePattern("^\\w+\\.(\\w+\\.[a-z]+)$"); // <3>
        return serializer;
    }
}
