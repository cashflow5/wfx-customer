package com.yougou.wfx.customer.ufans;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerDetail;
import com.yougou.wfx.appserver.vo.seller.SubSellerInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerSearcher;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.service.bapp.IBSellerService;

/**
 * 我的分销商
 * Created by lizhw on 2016/4/8.
 */
@Controller
@RequestMapping("ufans/mySeller")
public class MySellerController{
	
	/**
	 * 分销商信息接口
	 */
	@Autowired
	private IBSellerService sellerService;

    /**
     * 获取特定分销商ID下的分销商列表
     *
     * @return
     */
    @RequestMapping("getSubList")
    @LoginValidate
    public String getSubList(SubSellerSearcher searcher,ModelMap modelMap, HttpServletRequest request) {
        searcher.setUserInfo( getUserInfo() );
        //下级分销商名称、注册时间、佣金？
        //这个佣金是指他赚的佣金还是他作为下级分销商贡献给当前分销商的佣金
        PageSearchResult<SubSellerSearcher, SubSellerInfo> result = sellerService.getSubSellerList(searcher,null);
		modelMap.addAttribute("result", result);
		modelMap.addAttribute("searcher", searcher);
        return "ufans/my-seller";
    }
    /**
     * 获取特定分销商ID下的分销商列表
     *
     * @return
     */
    @RequestMapping("getSubListAjax")
    @LoginValidate
    public String getSubListAjax(SubSellerSearcher searcher,ModelMap modelMap, HttpServletRequest request) {
    	getSubList(searcher,modelMap,request);
        return "ufans/my-seller-ajax";
    }
    
    /**
     * 获取特定分销商ID下的三级分销商列表
     *
     * @return
     */
    @RequestMapping("getLevel3SubList")
    @LoginValidate
    public String getLevel3SubList(SubSellerSearcher searcher,String sellerId,ModelMap modelMap, HttpServletRequest request) {
        searcher.setUserInfo(  getUserInfo() );
        //下级分销商名称、注册时间、佣金？
        //这个佣金是指他赚的佣金还是他作为下级分销商贡献给当前分销商的佣金
        PageSearchResult<SubSellerSearcher, SubSellerInfo> result = sellerService.getSubSellerList(searcher,sellerId);
		modelMap.addAttribute("result", result);
		modelMap.addAttribute("sellerId", sellerId);
		modelMap.addAttribute("searcher", searcher);
        return "ufans/level3-seller";
    }
    /**
     * 获取特定分销商ID下的三级分销商列表
     *
     * @return
     */
    @RequestMapping("getLevel3SubListAjax")
    @LoginValidate
    public String getLevel3SubListAjax(SubSellerSearcher searcher,String sellerId,ModelMap modelMap, HttpServletRequest request) {
    	getLevel3SubList(searcher,sellerId,modelMap,request);
        return "ufans/level3-seller-ajax";
    }
    

    /**
     * 获取下级分销商详情
     *
     * @param subSellerId
     * @return
     */
    @RequestMapping("getSubDetail")
    @LoginValidate
    public String getSubSellerDetail(String subSellerId,int level,ModelMap modelMap, HttpServletRequest request) {

        //分销商详情信息（店铺名称、分销商账号、真实姓名、性别、生日、注册时间、所属地区、详细地址）
        SubSellerDetail subSellerDetail = sellerService.getSubSellerDetail(getUserInfo(),subSellerId);
        modelMap.addAttribute("detail", subSellerDetail);
        modelMap.addAttribute("subSellerId", subSellerId);
        modelMap.addAttribute("level", level);

        return "ufans/fenxiaoshang-detail";
    }
    
    private UserInfo getUserInfo(){
    	SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
    	UserInfo userInfo = new UserInfo();
    	userInfo.setId( loginUserDetails.getUserId() );
    	userInfo.setSellerId( loginUserDetails.getSellerId() );
    	return userInfo;
    }
}
