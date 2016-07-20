package com.yougou.wfx.customer.model.order;

import com.google.common.collect.Lists;
import com.yougou.wfx.order.dto.output.ConsignInfosOutPutDto;
import com.yougou.wfx.order.dto.output.StyleOutPutDto;
import com.yougou.wfx.order.dto.output.WfxLogisticsData;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 物流信息Vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/11 下午3:33
 * @since 1.0 Created by lipangeng on 16/4/11 下午3:33. Email:lipg@outlook.com.
 */
public class LogisticsVo {
    /** 数据集合 */
    private List<LogisticsLog> Logs = Lists.newArrayList();
    /** 商品列表 */
    private List<Style> styles = Lists.newArrayList();
    /** 物流单是否完结(冗余,无效) */
    private boolean isWhether = false;

    /** 订单编号(冗余,无效) */
    private String orderNo;

    /** 物流公司名称 */
    private String logisticsName;

    /** 物流公司code */
    private String logisticsCode;

    /** 物流单号 */
    private String logisticsOrderNo;

    /** 发货时间 */
    private Date shipTime;

    /** 物流公司官网(冗余,无效) */
    private String logisticsWebsite;

    /** 物流公司官方联系号码(冗余,无效) */
    private String logisticsTel;

    public List<LogisticsLog> getLogs() {
        return Logs;
    }

    public void setLogs(List<LogisticsLog> logs) {
        Logs = logs;
    }

    public boolean isWhether() {
        return isWhether;
    }

    public void setWhether(boolean whether) {
        isWhether = whether;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsOrderNo() {
        return logisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        this.logisticsOrderNo = logisticsOrderNo;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getLogisticsWebsite() {
        return logisticsWebsite;
    }

    public void setLogisticsWebsite(String logisticsWebsite) {
        this.logisticsWebsite = logisticsWebsite;
    }

    public String getLogisticsTel() {
        return logisticsTel;
    }

    public void setLogisticsTel(String logisticsTel) {
        this.logisticsTel = logisticsTel;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    /**
     * 物流日志信息
     *
     * @since 1.0 Created by lipangeng on 16/4/11 下午3:35. Email:lipg@outlook.com.
     */
    public static class LogisticsLog {
        /** 时间 */
        private String time;

        /** 处理情况 */
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        /**
         * dto转换为本地Vo
         *
         * @since 1.0 Created by lipangeng on 16/5/3 下午5:19. Email:lipg@outlook.com.
         */
        public static LogisticsLog valueOf(WfxLogisticsData dto) {
            if (dto == null) {
                return null;
            }
            LogisticsLog log = new LogisticsLog();
            log.setTime(dto.getTime());
            log.setContext(dto.getContext());
            return log;
        }
    }

    /**
     * 商品
     *
     * @since 1.0 Created by lipangeng on 16/5/3 下午5:06. Email:lipg@outlook.com.
     */
    public static class Style {
        /** 商品图片 */
        private String picUrl;
        /** 商品名称 */
        private String prodName;
        /** 商品规格 */
        private String prodSpec;
        /** 数量 */
        private Integer num;
        /** 商品id */
        private String commodityId;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public String getProdSpec() {
            return prodSpec;
        }

        public void setProdSpec(String prodSpec) {
            this.prodSpec = prodSpec;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        /**
         * dto转换为本地Vo
         *
         * @since 1.0 Created by lipangeng on 16/5/3 下午5:20. Email:lipg@outlook.com.
         */
        public static Style valueOf(StyleOutPutDto dto) {
            if (dto == null) {
                return null;
            }
            Style style = new Style();
            style.setPicUrl(dto.getPicUrl());
            style.setProdName(dto.getProd_name());
            style.setNum(dto.getNum());
            style.setProdSpec(dto.getProdSpec());
            style.setCommodityId(dto.getCommId());
            return style;
        }
    }

    /**
     * dto转换为本地vo
     *
     * @since 1.0 Created by lipangeng on 16/5/3 下午4:29. Email:lipg@outlook.com.
     */
    public static LogisticsVo valueOf(ConsignInfosOutPutDto dto) {
        if (dto == null) {
            return null;
        }
        LogisticsVo logistics = new LogisticsVo();
        logistics.setLogisticsName(dto.getExpressName());
        logistics.setLogisticsCode(dto.getExpressCode());
        logistics.setLogisticsOrderNo(dto.getExpressNo());
        logistics.setShipTime(dto.getConsignTime());
        if (dto.getTraceData() != null) {
            for (WfxLogisticsData wfxLogisticsData : dto.getTraceData()) {
                LogisticsLog logisticsLog = LogisticsLog.valueOf(wfxLogisticsData);
                if (logisticsLog != null &&
                    StringUtils.hasText(logisticsLog.getContext()) && StringUtils.hasText(logisticsLog.getTime())) {
                    logistics.getLogs().add(logisticsLog);
                }
            }
        }
        if (dto.getStyleList() != null) {
            for (StyleOutPutDto styleOutPutDto : dto.getStyleList()) {
                logistics.getStyles().add(Style.valueOf(styleOutPutDto));
            }
        }
        return logistics;
    }
}
