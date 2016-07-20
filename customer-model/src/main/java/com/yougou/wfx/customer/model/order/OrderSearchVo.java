package com.yougou.wfx.customer.model.order;

import com.yougou.wfx.order.dto.input.OrderSearchDto;

/**
 * 订单搜索使用的vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/8 上午9:38
 * @since 1.0 Created by lipangeng on 16/4/8 上午9:38. Email:lipg@outlook.com.
 */
public class OrderSearchVo {
    /**
     * 订单状态
     */
    private String status;
    /**
     * 微分销订单编号
     */
    private String wfxOrderNo;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 商品编号
     */
    private String prodId;
    /**
     * 收货人手机号码
     */
    private String receiverMobile;
    /**
	 * 合并查 部分发货和已发货两个状态订单 (1：合并查询)
	 */
	private Integer mergeSearchFlag;
	
	/**
	 * 选项卡类型
	 */
	private int tabType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWfxOrderNo() {
        return wfxOrderNo;
    }

    public void setWfxOrderNo(String wfxOrderNo) {
        this.wfxOrderNo = wfxOrderNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public Integer getMergeSearchFlag() {
		return mergeSearchFlag;
	}

	public void setMergeSearchFlag(Integer mergeSearchFlag) {
		this.mergeSearchFlag = mergeSearchFlag;
	}

	/**
     * 从本地搜索vo转为OrderSearchDto
     *
     * @since 1.0 Created by lipangeng on 16/5/5 下午3:32. Email:lipg@outlook.com.
     */
    public OrderSearchDto toOrderSearchDto() {
        OrderSearchDto search = new OrderSearchDto();
        search.setProdId(this.getProdId());
        search.setProdName(this.getProdName());
        search.setReceiverMobile(this.getReceiverMobile());
        search.setStatus(this.getStatus());
        search.setWfxOrderNo(this.getWfxOrderNo());
        search.setMergeSearchFlag(this.getMergeSearchFlag());
        return search;
        
    }

	public int getTabType() {
		return tabType;
	}

	public void setTabType(int tabType) {
		this.tabType = tabType;
	}
    
}
