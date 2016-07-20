package com.yougou.wfx.customer.service.visitor;

import java.util.Date;
import java.util.Map;

import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.visitor.VisitorVo;

public interface IVisitorService {
	
	/**
	 * 查询最近7天店铺每天的访客数量
	 * @param shopId
	 * @return
	 */
	Map<String,Object> query7DaysVisitorCount(String shopId);
	
	/**
	 * 获取当天各个来源访客数量
	 * @param shopId
	 * @return
	 */
	Map<String,Integer> getVisitorResourceCount(String shopId,Date date);
	
	/**
	 * 分页获取访客列表
	 * @param shopId
	 * @return
	 */
	Page<VisitorVo> getVisitorPageList(String shopId,Date date,Page page);
	
	/**
	 * 新增访客记录
	 * @param visitorVo
	 */
	void insertVistor(VisitorVo visitorVo);
	
	/**
	 * 获取当天店铺所有访客数量
	 * @param shopId
	 * @return
	 */
	int getTotalVisitCount(String shopId);
	
}
