package com.yougou.wfx.customer.service.bapp.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerDetail;
import com.yougou.wfx.appserver.vo.seller.SubSellerInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerSearcher;
import com.yougou.wfx.customer.service.bapp.IBSellerService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * Created by lizhw on 2016/4/11.
 */
@Service
public class BSellerServiceImpl extends BaseServiceImpl implements IBSellerService {

	/**
	 * @param SubSellerSearcher： 登陆用户经销商身份
	 * @param parentSellerId ：查该经销商的下级
	 */
    @Override
    public PageSearchResult<SubSellerSearcher, SubSellerInfo> getSubSellerList(SubSellerSearcher searcher,String parentSellerId) {
        PageSearchResult<SubSellerSearcher, SubSellerInfo> result =
                new PageSearchResult<SubSellerSearcher, SubSellerInfo>();
        result.setSearcher(searcher);

        String sellerId = getSellerId(searcher.getUserInfo());
        if (isBlank(sellerId)) {
            result.setTotal(0);
            result.setLevel3total(0);
            return result;
        }
        if (isBlank(parentSellerId)) {// 查自己的下级
        	parentSellerId = sellerId ;
        }
        PageModel<Object> page = new PageModel<Object>();
        page.setPage(searcher.getPage());
        page.setLimit(searcher.getPageSize());

        PageModel<SellerInfoOutputDto> subSellers = sellerInfoFrontApi.getSubSellerListSimple(parentSellerId, page);
        

        if (null != subSellers) {
        	// 2级经销商数目
            result.setTotal(subSellers.getTotalCount());
            // 3级经销商数目
            int level3Tatal = sellerInfoFrontApi.getLevelThreeSellerNum(sellerId);
            result.setLevel3total(level3Tatal);
            
            List<SellerInfoOutputDto> items = subSellers.getItems();
            if (items != null && items.size() > 0) {
                for (SellerInfoOutputDto item : items) {
                    SubSellerInfo subSeller = new SubSellerInfo();
                    //id
                    subSeller.setId(item.getId());
                    //分销商名称
                    subSeller.setName(item.getSellerName());
                    //佣金 (给上级带来的佣金 给上上级带来的佣金 )
                    Double commissionTotalAmountForParent = finSellerInfoFrontApi.getCommissionTotalAmountById(sellerId,item.getId());
                    subSeller.setCommissionTotalAmountForParent(commissionTotalAmountForParent);
                    
                    //注册时间 （审核通过时间）
                    subSeller.setRegTime(item.getPassDate());
                    //下级分销商数量
                    subSeller.setSubSellerCount(toInt(item.getSubSellerCount()));
                    //LOGO图片
                    ShopOutputDto shop = shopFrontApi.getShopBySeller(item.getId());
                    subSeller.setLogoUrl(shop.getLogoUrl());
                    subSeller.setShopCode(shop.getShopCode());
                    result.getItems().add(subSeller);
                }
            }
        }

        return result;
    }


    @Override
    public SubSellerDetail getSubSellerDetail(UserInfo userInfo, String subSellerId) {
        SubSellerDetail d = new SubSellerDetail();
        if (isBlank(subSellerId)) {
            return d;
        }
        SellerInfoOutputDto seller = sellerInfoFrontApi.getSellerInfoById(subSellerId);
       
        d.setId(seller.getId());
        //分销商账号  加工展示
        String loginName = seller.getLoginName();
        if(StringUtils.isEmpty( loginName ) ){
        	loginName = seller.getPlatformUsername();
        }else{
        	loginName = loginName.substring(0,3)+"*****"+loginName.substring(8);
        }
        d.setAccount(loginName);
        d.setBirthDayDate(seller.getBirthday());
        d.setRealName(seller.getSellerName());
        d.setRegTimeDate(seller.getRegisterDate());
        //会员性别,1=男，2=女
        d.setSex("1".equals(seller.getMemberSex()) ? "男" : ("2".equals(seller.getMemberSex()) ? "女" : ""));
        d.setShopName(seller.getShopName());
        d.setSubSellerCount(toInt(seller.getSubSellerCount()));
        d.setShopCode(seller.getShopCode());
        return d;
    }

    public int getSellerStatus(String memberId) {
        WFXResult<Integer> wfxResult = sellerInfoFrontApi.getSellerStatusByMemberId(memberId);
        if (wfxResult == null || wfxResult.getResult() == null) {
            return 0;
        }
        return wfxResult.getResult();
    }
}
