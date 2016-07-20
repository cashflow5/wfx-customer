package com.yougou.wfx.customer.configurations.freemarker;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/23 上午10:17
 * @since 1.0 Created by lipangeng on 16/3/23 上午10:17. Email:lipg@outlook.com.
 */
@ConfigurationProperties("com.yougou.freemarker")
public class YGFreeMarkerProperties {
    private Map<String, Object> variables = Maps.newHashMap();

    /**
     * 设置一些属性的默认值
     *
     * @since 1.0 Created by lipangeng on 16/4/8 下午3:22. Email:lipg@outlook.com.
     */
    public YGFreeMarkerProperties() {
        variables.put("context", "");
        variables.put("myOrderListCommoditySize", "3");
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
