package com.yougou.wfx.customer.configurations.weixin;

import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.weixin.IWXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/15 下午4:53
 * @since 1.0 Created by lipangeng on 16/4/15 下午4:53. Email:lipg@outlook.com.
 */
@Component
public class WeiXinAutoConfigure {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinAutoConfigure.class);
    @Autowired
    private IWXService wxService;
    @Autowired
    private WeiXinProperties weiXinProperties;

    /**
     * 刷新微信的AccessToken,每5分钟检测一次.
     *
     * @since 1.0 Created by lipangeng on 16/4/15 下午4:58. Email:lipg@outlook.com.
     */
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void flushWeiXinToken() {
        Result flushAccessToken = wxService.flushAccessToken(weiXinProperties.getBeforeRefreshTime(),
                                                             false,
                                                             weiXinProperties.getAppId(),
                                                             weiXinProperties.getAppSecret());
        if (flushAccessToken.isSuccess()) {
            logger.info("刷新AccessToken成功,信息:" + JacksonUtils.Convert(flushAccessToken));
            Result flushJSTicket = wxService.flushJSTicket(weiXinProperties.getBeforeRefreshTime(), false);
            if (flushJSTicket.hasError()) {
                logger.error("刷新JSTocket失败,错误信息:" + JacksonUtils.Convert(flushJSTicket));
            } else {
                logger.info("刷新JSTocket成功,信息:" + JacksonUtils.Convert(flushJSTicket));
            }
        } else {
            logger.error("刷新AccessToken失败.错误信息:" + JacksonUtils.Convert(flushAccessToken));
        }
    }
}
