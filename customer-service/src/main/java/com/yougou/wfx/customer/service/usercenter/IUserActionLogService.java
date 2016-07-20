package com.yougou.wfx.customer.service.usercenter;

/**
 * 用户操作日志Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/6 上午11:13
 * @since 1.0 Created by lipangeng on 16/5/6 上午11:13. Email:lipg@outlook.com.
 */
public interface IUserActionLogService {

    /**
     * 创建用户日志
     *
     * @since 1.0 Created by lipangeng on 16/5/6 上午11:46. Email:lipg@outlook.com.
     */
    void addUserActionLog(String optType, String remark);

    /**
     * 创建用户操作日志
     *
     * @since 1.0 Created by lipangeng on 16/5/6 上午11:51. Email:lipg@outlook.com.
     */
    void addUserActionLog(String userId, String userName, String optType, String remark);
}
