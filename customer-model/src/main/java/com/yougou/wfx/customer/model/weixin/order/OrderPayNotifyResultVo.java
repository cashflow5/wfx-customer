package com.yougou.wfx.customer.model.weixin.order;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/20 下午6:32
 * @since 1.0 Created by lipangeng on 16/4/20 下午6:32. Email:lipg@outlook.com.
 */
@XmlRootElement(name = "xml")
public class OrderPayNotifyResultVo {
    @XmlElement(name = "return_code")
    private String returnCode;
    @XmlElement(name = "return_msg")
    private String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
