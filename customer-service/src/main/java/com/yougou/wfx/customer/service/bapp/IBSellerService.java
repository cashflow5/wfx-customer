package com.yougou.wfx.customer.service.bapp;


import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerDetail;
import com.yougou.wfx.appserver.vo.seller.SubSellerInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerSearcher;

/**
 * Created by lizhw on 2016/4/11.
 */
public interface IBSellerService extends IBaseService {
	/**
	 * @param SubSellerSearcher： 登陆用户经销商身份
	 * @param parentSellerId ：查该经销商的下级
	 */
    PageSearchResult<SubSellerSearcher, SubSellerInfo> getSubSellerList(SubSellerSearcher searcher,String parentSellerId) ;

    SubSellerDetail getSubSellerDetail(UserInfo userInfo, String subSellerId);
     int getSellerStatus(String memberId);
}
