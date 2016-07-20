package com.yougou.wfx.customer.service.bapp.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.discover.DiscoverArticle;
import com.yougou.wfx.appserver.vo.discover.DiscoverArticleDetail;
import com.yougou.wfx.appserver.vo.discover.DiscoverCarouselFigure;
import com.yougou.wfx.appserver.vo.discover.DiscoverChannel;
import com.yougou.wfx.appserver.vo.discover.DiscoverHome;
import com.yougou.wfx.appserver.vo.discover.DiscoverSearcher;
import com.yougou.wfx.commodity.dto.output.CommodityStyleOutputDto;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.service.bapp.DiscoverService;
import com.yougou.wfx.discover.api.front.IDiscoverArticleFrontApi;
import com.yougou.wfx.discover.api.front.IDiscoverCarouselFigureFrontApi;
import com.yougou.wfx.discover.api.front.IDiscoverChannelFrontApi;
import com.yougou.wfx.discover.dto.input.DiscoverArticlePageInputDto;
import com.yougou.wfx.discover.dto.output.DiscoverArticleOutputDto;
import com.yougou.wfx.discover.dto.output.DiscoverCarouselFigureOutputDto;
import com.yougou.wfx.discover.dto.output.DiscoverChannelOutputDto;
import com.yougou.wfx.dto.base.PageModel;

@Service
public class DiscoverServiceImpl extends BaseServiceImpl implements DiscoverService {
	/** 发现文章 */
	@Autowired
	protected IDiscoverArticleFrontApi discoverArticleFrontApi;
	/** 轮播图 */
	@Autowired
	protected IDiscoverCarouselFigureFrontApi discoverCarouselFigureFrontApi;
	/** 频道 */
	@Autowired
	protected IDiscoverChannelFrontApi discoverChannelFrontApi;

	@Override
	public DiscoverHome getHomeData(DiscoverSearcher discoverSearcher) {
		DiscoverHome discoverHome = new DiscoverHome();
		// 轮播图
		discoverHome.setDiscoverCarouselFigureList(getDiscoverCarouselFigureList());
		// 频道
		discoverHome.setDiscoverChannelList(getDiscoverChannelList());
		// 文章列表
		discoverSearcher.setChannelId("0");
		discoverHome.setPageDiscoverArticleResult(getDiscoverArticleList(discoverSearcher));
		return discoverHome;
	}

	@Override
	public PageSearchResult<DiscoverSearcher, DiscoverArticle> getDiscoverArticleList(DiscoverSearcher discoverSearcher) {
		PageSearchResult<DiscoverSearcher, DiscoverArticle> result = new PageSearchResult<DiscoverSearcher, DiscoverArticle>();
		PageModel<DiscoverArticleOutputDto> pageModel = new PageModel<DiscoverArticleOutputDto>();
		pageModel.setPage(discoverSearcher.getPage());
		pageModel.setLimit(discoverSearcher.getPageSize());
		DiscoverArticlePageInputDto dto = new DiscoverArticlePageInputDto();
		dto.setChannelId(discoverSearcher.getChannelId());
		PageModel<DiscoverArticleOutputDto> dtos = discoverArticleFrontApi.findPage(dto, pageModel);
		if (dtos != null) {
			result.setTotal(dtos.getTotalCount());
			List<DiscoverArticleOutputDto> items = dtos.getItems();
			if (null != items && items.size() > 0) {
				for (DiscoverArticleOutputDto item : items) {
					result.getItems().add(transform_DiscoverArticle(item));
				}
			}
		}
		return result;
	}

	@Override
	public DiscoverArticleDetail getDiscoverArticleById(String articleId) {
		return transform_DiscoverArticleDetail(discoverArticleFrontApi.getById(articleId));
	}

	@SuppressWarnings("unused")
	private List<DiscoverArticle> getDiscoverArticleTopNList(int i) {
		DiscoverArticlePageInputDto pageInputDto = new DiscoverArticlePageInputDto();
		pageInputDto.setChannelId("0");
		PageModel<DiscoverArticleOutputDto> dtoPageModel = discoverArticleFrontApi.findPage(pageInputDto, new PageModel<DiscoverArticleOutputDto>(0, i));
		return transform_DiscoverArticle(dtoPageModel.getItems());
	}

	private List<DiscoverChannel> getDiscoverChannelList() {
		List<DiscoverChannelOutputDto> dtos = discoverChannelFrontApi.getAllShowChannels();
		return transform_DiscoverChannel(dtos);
	}

	public List<DiscoverCarouselFigure> getDiscoverCarouselFigureList() {
		List<DiscoverCarouselFigureOutputDto> dtos = discoverCarouselFigureFrontApi.getAllShow();
		return transform_DiscoverCarousel(dtos);
	}

	// -----------------------------------类型转换----------------------------

	private List<DiscoverCarouselFigure> transform_DiscoverCarousel(List<DiscoverCarouselFigureOutputDto> dtos) {
		if (CollectionUtils.isEmpty(dtos))
			return new ArrayList<DiscoverCarouselFigure>();
		List<DiscoverCarouselFigure> voList = new ArrayList<DiscoverCarouselFigure>();
		for (DiscoverCarouselFigureOutputDto dto : dtos) {
			voList.add(transform_DiscoverCarousel(dto));
		}
		return voList;
	}

	private DiscoverCarouselFigure transform_DiscoverCarousel(DiscoverCarouselFigureOutputDto dto) {
		DiscoverCarouselFigure vo = new DiscoverCarouselFigure();
		if (dto != null) {
			vo.setName(dto.getName());
			vo.setPicture(dto.getPicture());
			vo.setDiscoverArticle(transform_DiscoverArticle(dto.getDiscoverArticle()));
		}
		return vo;
	}

	private List<DiscoverArticle> transform_DiscoverArticle(List<DiscoverArticleOutputDto> dtos) {
		if (CollectionUtils.isEmpty(dtos))
			return new ArrayList<DiscoverArticle>();
		List<DiscoverArticle> voList = new ArrayList<DiscoverArticle>();
		for (DiscoverArticleOutputDto dto : dtos) {
			voList.add(transform_DiscoverArticle(dto));
		}
		return voList;
	}

	private DiscoverArticle transform_DiscoverArticle(DiscoverArticleOutputDto dto) {
		DiscoverArticle vo = new DiscoverArticle();
		if (dto != null) {
			vo.setChannelId(dto.getChannelId());
			vo.setId(dto.getId());
			vo.setPicCover(dto.getPicCover());
			vo.setTitle(dto.getTitle());
			vo.setShowTime(dto.getStringUpdateTime());
			vo.setClassify(dto.getClassify());
		}
		return vo;
	}

	private DiscoverArticleDetail transform_DiscoverArticleDetail(DiscoverArticleOutputDto dto) {
		DiscoverArticleDetail vo = new DiscoverArticleDetail();
		if (dto != null) {
			vo.setAuthorAccount(dto.getAuthorAccount());
			vo.setAuthorType(dto.getAuthorType());
			vo.setChannelId(dto.getChannelId());
			vo.setContent(dto.getContent());
			vo.setId(dto.getId());
			vo.setPicCover(dto.getPicCover());
			vo.setTitle(dto.getTitle());
			vo.setShowTime(dto.getStringUpdateTime());
			vo.setClassify(dto.getClassify());
		}
		return vo;
	}

	private List<DiscoverChannel> transform_DiscoverChannel(List<DiscoverChannelOutputDto> dtos) {
		if (CollectionUtils.isEmpty(dtos))
			return new ArrayList<DiscoverChannel>();
		List<DiscoverChannel> voList = new ArrayList<DiscoverChannel>();
		for (DiscoverChannelOutputDto dto : dtos) {
			voList.add(transform_DiscoverChannel(dto));
		}
		return voList;
	}

	private DiscoverChannel transform_DiscoverChannel(DiscoverChannelOutputDto dto) {
		DiscoverChannel vo = new DiscoverChannel();
		if (dto != null) {
			vo.setId(dto.getId());
			vo.setChannelCode(dto.getChannelCode());
			vo.setChannelName(dto.getChannelName());
		}
		return vo;
	}

	@Override
	public Page<DiscoverArticle> getDiscoverArticlePageList(String channelId, Page page) {
		Page<DiscoverArticle> voPage = new Page<>();
		voPage.setItems(Lists.<DiscoverArticle> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		DiscoverArticlePageInputDto pageInputDto = new DiscoverArticlePageInputDto();
		pageInputDto.setChannelId(channelId);
		// 请求订单
		PageModel<DiscoverArticleOutputDto> dtos = discoverArticleFrontApi.findPage(pageInputDto, pageModel);
		// 填充订单数据
		if (dtos != null && dtos.getItems() != null) {
			List<DiscoverArticleOutputDto> dtoList = dtos.getItems();
			List<DiscoverArticle> voList = transform_DiscoverArticle(dtoList);
			for (DiscoverArticle vo : voList) {
				voPage.getItems().add(vo);
			}
		}
		return voPage;
	}

	@Override
	public List<DiscoverChannel> getShowDiscoverChannelList() {
		return getDiscoverChannelList();
	}

	/*
	 * private List<DiscoverLog>
	 * transform_DiscoverLog(List<DiscoverLogOutputDto> dtos) { if
	 * (CollectionUtils.isEmpty(dtos)) return null; List<DiscoverLog> voList =
	 * new ArrayList<DiscoverLog>(); for (DiscoverLogOutputDto dto : dtos) {
	 * voList.add(transform_DiscoverLog(dto)); } return voList; }
	 * 
	 * private DiscoverLog transform_DiscoverLog(DiscoverLogOutputDto dto) {
	 * DiscoverLog vo = new DiscoverLog();
	 * vo.setBusinessType(dto.getBusinessType()); vo.setId(dto.getId());
	 * vo.setOperateAccount(dto.getOperateAccount());
	 * vo.setOperateContent(dto.getOperateContent());
	 * vo.setOperateDate(dto.getOperateDate());
	 * vo.setOperateType(dto.getOperateType());
	 * vo.setOperateUser(dto.getOperateUser());
	 * vo.setOperatorIp(dto.getOperatorIp()); vo.setRemark(dto.getRemark());
	 * return vo; }
	 */
	/*
	 * public static void main(String[] args) { pri("DiscoverArticleOutputDto",
	 * "DiscoverArticle"); pri("DiscoverChannelOutputDto", "DiscoverChannel");
	 * pri("DiscoverLogOutputDto", "DiscoverLog");
	 * 
	 * }
	 * 
	 * public static void pri(String dto, String vo) { String transFromList =
	 * "private List<" + vo + "> transform(List<" + dto +
	 * "> dtos) {if (CollectionUtils.isEmpty(dtos))return null;List<" + vo +
	 * "> voList = new ArrayList<" + vo + ">();for (" + dto +
	 * " dto : dtos) {voList.add(transform(dto));}return voList;}"; String
	 * transform = "	private " + vo + " transform" + dto + "dto) {" + vo +
	 * " vo = new " + vo + "();return vo;}"; System.out.println(transFromList);
	 * System.out.println(transform); }
	 */

}
