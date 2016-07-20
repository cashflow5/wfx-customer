package com.yougou.wfx.customer.ufans;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.order.OrderDetailInfo;
import com.yougou.wfx.appserver.vo.order.OrderIdentity;
import com.yougou.wfx.appserver.vo.order.OrderInfo;
import com.yougou.wfx.appserver.vo.order.OrderSearcher;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.service.bapp.IBShopService;
import com.yougou.wfx.customer.service.bapp.IOrderService;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * 订单管理
 * Created by lizhw on 2016/4/8.
 */
@Controller
@RequestMapping("ufans/order")
public class OrderManageController{
	
	/**
	 * 订单接口
	 */
	@Autowired
	private IOrderService orderService;
	/**
	 * 店铺接口
	 */
	@Autowired
	private IBShopService bShopService;
    
	/**
     * 获取订单信息
     * 订单状态、当前页、订单条数、店铺ID
     * @param OrderSearcher：page、pageSize、level、status
     * @return
     */
    @RequestMapping("list")
    @LoginValidate
    public String getOrderInfo(OrderSearcher searcher,ModelMap modelMap, HttpServletRequest request) {
        searcher.setUserInfo(getUserInfo());
        //订单基本信息（分销商名称、付款状态、订单金额、佣金、订单ID）
        PageSearchResult<OrderSearcher, OrderInfo> result = orderService.getShopOrderInfoList(searcher);
        modelMap.addAttribute("pageSearchResult", result);
        modelMap.addAttribute("searcher", searcher);
        return "ufans/ufans-order-new";
    }
	/**
     * 获取订单信息
     * 订单状态、当前页、订单条数、店铺ID
     * @param OrderSearcher：page、pageSize、level、status
     * @return
     */
    @RequestMapping("ajax")
    public String ajax(OrderSearcher searcher,ModelMap modelMap, HttpServletRequest request) {
    	getOrderInfo(searcher,modelMap,request);
        return "ufans/ufans-order-ajax";
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @RequestMapping("detail")
    @LoginValidate
    public String getDetail( OrderIdentity identity,ModelMap modelMap, HttpServletRequest request ) {
        identity.setUserInfo(getUserInfo());
        OrderDetailInfo od = orderService.getOrderDetail(identity);
        modelMap.addAttribute("identity", identity);
        modelMap.addAttribute("order", od);
        return  "ufans/ufans-order-detail";
    }
    
    private UserInfo getUserInfo(){
    	SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
    	ShopOutputDto shopOutputDto = bShopService.getShopByMemberId( loginUserDetails.getUserId() );
    	if( shopOutputDto!=null ){
	    	UserInfo userInfo = new UserInfo();
	    	userInfo.setId(loginUserDetails.getUserId());
	    	userInfo.setLoginName(loginUserDetails.getPhoneNumber());
	    	userInfo.setShopId(shopOutputDto.getId());
	    	userInfo.setShopCode( shopOutputDto.getShopCode());
	    	userInfo.setSellerId(shopOutputDto.getSellerId());
	    	return userInfo;
    	}else{
    		return null;
    	}
    }
}
