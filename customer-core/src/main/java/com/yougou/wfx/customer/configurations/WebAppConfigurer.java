package com.yougou.wfx.customer.configurations;

import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.interceptor.EnvironmentHandlerInterceptor;
import com.yougou.wfx.customer.interceptor.LoginHandlerInterceptor;
import com.yougou.wfx.customer.interceptor.WXHandlerInterceptor;
import com.yougou.wfx.customer.interceptor.WXUfansHandlerInteceptor;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.customer.service.weixin.IWXService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 配置登录校验拦截器
 *
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/23
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Autowired
	private WeiXinProperties weiXinProperties;
	@Autowired
	private IWXService wXService;
	@Autowired
	private WFXProperties wfxProperties;
	@Autowired
	private ISystemService systemService;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new EnvironmentHandlerInterceptor())
				.addPathPatterns("/*.sc", "/*/*.sc", "/*/*/*.sc", "/*/*/*/*.sc", "/*/*/*/*/*.sc");
    	registry.addInterceptor(new WXHandlerInterceptor(requestMappingHandlerAdapter))
				.addPathPatterns("/*.sc", "/discover/home.sc", "/*/item/*.sc", "/*/discover/view/*.sc");
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/*.sc", "/*/*.sc", "/*/*/*.sc", "/*/*/*/*.sc", "/*/*/*/*/*.sc");
        registry.addInterceptor(new WXUfansHandlerInteceptor(weiXinProperties, wXService, wfxProperties, systemService)).addPathPatterns("/*.sc", "/*/*.sc", "/*/*/*.sc", "/*/*/*/*.sc", "/*/*/*/*/*.sc");
    }
}
