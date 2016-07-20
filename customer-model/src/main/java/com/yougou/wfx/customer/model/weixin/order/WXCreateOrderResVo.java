package com.yougou.wfx.customer.model.weixin.order;

/**
 * 微信创建订单的返回值
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/22 下午3:06
 * @since 1.0 Created by lipangeng on 16/4/22 下午3:06. Email:lipg@outlook.com.
 */
public class WXCreateOrderResVo {
    private WXJSPayVo wxjsPay;

    public WXJSPayVo getWxjsPay() {
        return wxjsPay;
    }

    public void setWxjsPay(WXJSPayVo wxjsPay) {
        this.wxjsPay = wxjsPay;
    }
}
