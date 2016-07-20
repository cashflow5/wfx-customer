package com.yougou.wfx.customer.service.usercenter.impl;

import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.service.usercenter.IUserActionLogService;
import com.yougou.wfx.member.api.front.IMemberActionLogFrontApi;
import com.yougou.wfx.member.dto.input.MemberActionLogInputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户操作日志Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/6 上午11:14
 * @since 1.0 Created by lipangeng on 16/5/6 上午11:14. Email:lipg@outlook.com.
 */
@Service
public class UserActionLogService implements IUserActionLogService {
    private static final Logger logger = LoggerFactory.getLogger(UserActionLogService.class);
    @Autowired
    private IMemberActionLogFrontApi memberActionLogFrontApi;

    /**
     * 插入新的用户日志
     *
     * @since 1.0 Created by lipangeng on 16/5/6 上午11:26. Email:lipg@outlook.com.
     */
    @Override
    public void addUserActionLog(String optType, String remark) {
        addUserActionLog(null, null, optType, remark);
    }

    /**
     * 创建用户操作日志
     *
     * @param userId
     * @param userName
     * @param optType
     * @param remark
     *
     * @since 1.0 Created by lipangeng on 16/5/6 上午11:51. Email:lipg@outlook.com.
     */
    @Override
    public void addUserActionLog(String userId, String userName, String optType, String remark) {
        try {
            MemberActionLogInputDto memberActionLogInputDto = new MemberActionLogInputDto();
            memberActionLogInputDto.setLoginaccountId(userId);
            memberActionLogInputDto.setLoginName(userName);
            memberActionLogInputDto.setOptType(optType);
            memberActionLogInputDto.setRemark(remark);
            // 注入基础信息
            memberActionLogInputDto.setOptIp(SessionUtils.getRemoteIP());
            memberActionLogInputDto.setOptTime(new Date());
            memberActionLogInputDto.setOptSite(0);
            SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
            if (loginUserDetails != null) {
                if (memberActionLogInputDto.getLoginaccountId() == null) {
                    memberActionLogInputDto.setLoginaccountId(loginUserDetails.getUserId());
                }
                if (memberActionLogInputDto.getLoginName() == null) {
                    memberActionLogInputDto.setLoginName(loginUserDetails.getUserName());
                }
                memberActionLogInputDto.setOptShopId(SessionUtils.getCurrentShopId());
            }
            memberActionLogFrontApi.insert(memberActionLogInputDto);
        } catch (Exception e) {
            logger.error("插入用户操作日志发生异常.内容:", e);
        }
    }
}
