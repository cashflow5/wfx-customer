package com.yougou.wfx.customer;

import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.configurations.freemarker.YGFreeMarkerProperties;
import com.yougou.wfx.customer.configurations.imagecode.ImageCodeProperties;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication()
@EnableConfigurationProperties({YGFreeMarkerProperties.class, ImageCodeProperties.class, WFXProperties.class, WeiXinProperties.class})
@EnableScheduling
@ImportResource({"classpath:applicationContext-dubbo.xml"})
public class CustomerApplication  implements EmbeddedServletContainerCustomizer{
    private static final Logger logger = LoggerFactory.getLogger(CustomerApplication.class);
    @Autowired
    private WFXProperties wfxProperties;

    /**
     * 软件版本
     */
    private static String APP_VERSION = "0.0.0";

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
        System.out.println("项目启动完成,当前版本:" + getAppVersion());
    }

    /**
     * 初始化项目
     *
     * @since 1.0 Created by lipangeng on 16/5/9 下午5:31. Email:lipg@outlook.com.
     */
    @PostConstruct
    public void init() {
        // 在session工具中注入项目配置
        SessionUtils.setWfxProperties(wfxProperties);
    }
    

    @Override  
    public void customize(ConfigurableEmbeddedServletContainer container) {  
 	        container.setPort(80);  
    }  

    public static String getAppVersion() {
        return APP_VERSION;
    }

    //@Value("${wfx.verison}")
    public void setWfxVersion(String wfxVerison) {
        CustomerApplication.APP_VERSION = wfxVerison;
    }

	
}
