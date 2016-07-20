package com.yougou.wfx.customer.service.usercenter;

import com.yougou.wfx.customer.model.usercenter.moditypassword.ModityPasswordSessionDetails;

/**
 * 修改密码的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/5 下午3:22
 * @since 1.0 Created by lipangeng on 16/4/5 下午3:22. Email:lipg@outlook.com.
 */
public interface IModityPasswordService {

    /**
     * 在session中缓存注册信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    void setModityPasswordDetails(ModityPasswordSessionDetails details);

    /**
     * 获取session中缓存的修改密码信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    ModityPasswordSessionDetails getModityPasswordDetails();

    /**
     * 删除session中的修改密码信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:12. Email:lipg@outlook.com.
     */
    void removeModityPasswordDetails();

    /**
     * 检测session中的修改密码信息是否为空
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午2:58. Email:lipg@outlook.com.
     */
    boolean hasModityPasswordDetails();
}
