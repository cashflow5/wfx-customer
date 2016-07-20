package com.yougou.wfx.customer.configurations.dubbo;

import com.alibaba.dubbo.config.ProtocolConfig;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/29
 */
public class DubboDestruction {
    public DubboDestruction() {
    }

    public void destroy() throws Exception {
        ProtocolConfig.destroyAll();
    }
}
