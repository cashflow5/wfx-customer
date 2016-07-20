package com.yougou.wfx.customer.service.bapp;


import java.util.List;

import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.discover.DiscoverArticle;
import com.yougou.wfx.appserver.vo.discover.DiscoverArticleDetail;
import com.yougou.wfx.appserver.vo.discover.DiscoverChannel;
import com.yougou.wfx.appserver.vo.discover.DiscoverHome;
import com.yougou.wfx.appserver.vo.discover.DiscoverSearcher;
import com.yougou.wfx.customer.model.common.Page;

/**
 * 
 * @author li.lq
 * @Date 2016年6月3日
 */
public interface DiscoverService extends IBaseService {

	DiscoverHome getHomeData(DiscoverSearcher discoverSearcher);

	PageSearchResult<DiscoverSearcher, DiscoverArticle> getDiscoverArticleList(DiscoverSearcher discoverSearcher);

	DiscoverArticleDetail getDiscoverArticleById(String articleId);
	
	Page<DiscoverArticle> getDiscoverArticlePageList(String channelId, Page page);

	List<DiscoverChannel> getShowDiscoverChannelList();

}
