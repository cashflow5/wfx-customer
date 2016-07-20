package com.yougou.wfx.customer.model.shoopingcart;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/5
 */
public class SelectItemVo implements Serializable {
    private static final long serialVersionUID = 3064994397617685129L;

    /**
     * id字符串,隔开
     */
    private String ids;

    /**
     * 勾选状态，0未勾选，1 勾选
     */
    private boolean checked;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
