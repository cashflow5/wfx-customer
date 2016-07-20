package com.yougou.wfx.customer.common.utils;

import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.dto.base.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/16 上午10:02
 * @since 1.0 Created by lipangeng on 16/5/16 上午10:02. Email:lipg@outlook.com.
 */
public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    private LogUtils() {
    }

    /**
     * 生成日志文本
     *
     * @since 1.0 Created by lipangeng on 16/5/16 上午10:03. Email:lipg@outlook.com.
     */
    public static String requestInfo() {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        UserContext userContext = SessionUtils.getUserContext();
        StringBuilder builder = new StringBuilder();
        builder.append(" - RequestInfo:");
        if (loginUserDetails != null) {
            builder.append(loginUserDetails.toLogString()).append(",");
        }
        return builder.append(userContext.toString()).toString();
    }

    /**
     * 生成日志文本
     *
     * @since 1.0 Created by lipangeng on 16/5/16 上午10:03. Email:lipg@outlook.com.
     */
    public static String requestInfo(String logMsg) {
        return logMsg + requestInfo();
    }

    /**
     * 生成日志文本
     *
     * @since 1.0 Created by lipangeng on 16/5/16 上午10:03. Email:lipg@outlook.com.
     */
    public static String requestInfo(String logMsg, Object req, Object res) {
        return logMsg + String.format("参考信息:[Req:%s,Res:%s]", JacksonUtils.Convert(req), JacksonUtils.Convert(res)) + requestInfo();
    }
}
