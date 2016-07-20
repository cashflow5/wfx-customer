package com.yougou.wfx.customer.service.visitor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.yougou.wfx.appserver.vo.discover.DiscoverArticle;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.visitor.VisitorVo;
import com.yougou.wfx.customer.service.visitor.IVisitorService;
import com.yougou.wfx.discover.dto.input.DiscoverArticlePageInputDto;
import com.yougou.wfx.discover.dto.output.DiscoverArticleOutputDto;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.visitor.api.front.IVisitorFrontApi;
import com.yougou.wfx.visitor.dto.input.VisitorInputDto;
import com.yougou.wfx.visitor.dto.output.VisitorCountOutputDto;
import com.yougou.wfx.visitor.dto.output.VisitorOutputDto;

@Service
public class VisitorServiceImpl implements IVisitorService {
	
	@Autowired
	private IVisitorFrontApi visitorFrontApi;

	@Override
	public Map<String, Object> query7DaysVisitorCount(String shopId) {
		// TODO Auto-generated method stub
		return visitorFrontApi.query7DaysVisitorCount(shopId);
	}

	@Override
	public Map<String, Integer> getVisitorResourceCount(String shopId,Date date) {
		// TODO Auto-generated method stub
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		VisitorCountOutputDto visitorCount= visitorFrontApi.queryVisitorSourceCount(shopId,date);
		//int weixinCount = visitorCount.getWeixinVisitorCount(); 
		resultMap.put("weixinCount", visitorCount.getWeixinVisitorCount());
		resultMap.put("otherCount", visitorCount.getOtherVisitorCount());
		return resultMap;
	}

	@Override
	public Page<VisitorVo> getVisitorPageList(String shopId,Date date, Page page) {
		// TODO Auto-generated method stub
		Page<VisitorVo> voPage = new Page<>();
		voPage.setItems(Lists.<VisitorVo> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		
		PageModel<VisitorOutputDto> visitorOutDtos = visitorFrontApi.queryCurDayVisitorList(shopId,date, pageModel);	
	
		// 填充订单数据
		if (visitorOutDtos != null && visitorOutDtos.getItems() != null) {
			
			List<VisitorOutputDto> dtoList = visitorOutDtos.getItems();
			List<VisitorVo> voList = transform_VisitorVo(dtoList);
			for (VisitorVo vo : voList) {
				voPage.getItems().add(vo);
			}
			int pageCount = visitorOutDtos.getPageCount();
			voPage.setPageCount(visitorOutDtos.getPageCount());
		}
		
		return voPage;

	}
	
	private List<VisitorVo> transform_VisitorVo(List<VisitorOutputDto> dtos) {
		if (CollectionUtils.isEmpty(dtos))
			return new ArrayList<VisitorVo>();
		List<VisitorVo> voList = new ArrayList<VisitorVo>();
		for (VisitorOutputDto dto : dtos) {
			voList.add(transform_VisitorVo(dto));
		}
		return voList;
	}

	private VisitorVo transform_VisitorVo(VisitorOutputDto dto) {
		VisitorVo vo = new VisitorVo();
		if (dto != null) {			
			vo.setId(dto.getId());
			vo.setHeadShowImg(dto.getHeadShowImg());
			vo.setVisitorName(dto.getVisitorName());
		}
		return vo;
	}

	@Override
	public void insertVistor(VisitorVo visitorVo) {
		// TODO Auto-generated method stub
		if(visitorVo!=null){
			VisitorInputDto visitorDto = new  VisitorInputDto();
			visitorDto.setCommodityNo(visitorVo.getCommodityNo());
			visitorDto.setShopId(visitorVo.getShopId());
			visitorDto.setSourceType(visitorVo.getSourceType());
			visitorDto.setVisitorId(visitorVo.getVisitorId());
			visitorDto.setVisitorIp(visitorVo.getVisitorIp());
			visitorDto.setVisitTime(visitorVo.getVisitTime());
			visitorDto.setVisitType(visitorVo.getVisitType());
			visitorFrontApi.insertVisitor(visitorDto);
		}
	}

	@Override
	public int getTotalVisitCount(String shopId) {
		// TODO Auto-generated method stub
		return visitorFrontApi.queryShopCurDayVisitCount(shopId);
	}

}
