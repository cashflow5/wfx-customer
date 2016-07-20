package com.yougou.wfx.customer.ufans;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yougou.tools.common.utils.DateUtil;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.enums.OperatingEnvironmentEnum;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.visitor.VisitorVo;
import com.yougou.wfx.customer.service.bapp.IBShopService;
import com.yougou.wfx.customer.service.visitor.IVisitorService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * 访客
 * @author zhang.f1
 *
 */
@Controller
@RequestMapping("ufans/visitor")
public class VisitorController {
	
	@Autowired
	private IVisitorService visitorService;
	
	@Autowired
	private IBShopService bShopService;
	
	/**
	 * 访客记录
	 * @param modelMap
	 * @param request
	 * @return
	 */
    @RequestMapping("visitorRecords")
    @LoginValidate
    public String visitorRecords(ModelMap modelMap, HttpServletRequest request) {
    	String shopId = getLoginMemberShopId();

    	//String shopId = SessionUtils.getCurrentShopId();
    	Map<String,Object> map = visitorService.query7DaysVisitorCount(shopId);
    	if(map != null && map.size() >0){
    		String[] categories = (String[]) map.get("categories");
    		List<Map<String,Object>> data = (List<Map<String, Object>>) map.get("data");
    		modelMap.put("categories", JSONArray.toJSON(categories).toString());
    		modelMap.put("data", JSONArray.toJSON(data).toString());
    	}
    	Map<String,Integer> visitorCountMap = visitorService.getVisitorResourceCount(shopId,null);
    	modelMap.put("visitorCountMap", visitorCountMap);
        return "ufans/ufans-visitorRecords";
    }

    /**
     * 获取当前登陆用户店铺ID
     * @param shopId
     * @return
     */
	private String getLoginMemberShopId() {
		String shopId = null;
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
    	if( loginUserDetails!=null && StringUtils.isNotEmpty( loginUserDetails.getUserId() ) ){
    		String memberId = loginUserDetails.getUserId() ;
    		ShopOutputDto shopOutputDto = bShopService.getShopByMemberId( memberId );
    		if( shopOutputDto!=null ){
    			shopId = shopOutputDto.getId();
    		}
    	}
		return shopId;
	}
	
	 private String getRedirectURL(HttpServletRequest request) {
	        StringBuilder urlBuilder = new StringBuilder();
	        urlBuilder.append(request.getContextPath());
	        urlBuilder.append(request.getServletPath());
	       /* if (StringUtils.isNotBlank(request.getQueryString())) {
	            urlBuilder.append("?").append(request.getQueryString().trim());
	        }*/
	        return urlBuilder.toString();
	 }
    
    
    /**
	 * 访客详情列表
	 * @param modelMap
	 * @param request
	 * @return
	 */
    @RequestMapping("visitorRecordList")
    @LoginValidate
    public String visitorRecordList(ModelMap modelMap, Page page, HttpServletRequest request) {
    	//page.setLimit(3);
    	//String shopId = SessionUtils.getCurrentShopId();
    	//page.setLimit(10);
    	String shopId = getLoginMemberShopId();
    	String date = request.getParameter("date");
    	Date dt = null;
    	if(StringUtils.isNotBlank(date)){
    		dt = DateUtil.parseDate(date, "yyyy-MM-dd");
    	}
    	Page<VisitorVo> visitorList = visitorService.getVisitorPageList(shopId,dt, page);
    	modelMap.put("result", visitorList);
    	modelMap.put("date", date);
        return "ufans/ufans-visitorRecordList";//页面尚未开发
    }
    
    int count = 0;
    /**
  	 * 访客详情列表
  	 * @param modelMap
  	 * @param request
  	 * @return
     * @throws UnsupportedEncodingException 
  	 */
      @RequestMapping("visitorRecordListAjax")
      public String visitorRecordListAjax(ModelMap modelMap, Page page, HttpServletRequest request) throws UnsupportedEncodingException {
	    	SessionUserDetails loginUser = SessionUtils.getLoginUserDetails();
	    	if(null == loginUser){	    		
	            modelMap.put("loginOut", "loginOut");
	            return "ufans/ufans-visitorRecordListAjax";
	    	}
	    	
	      	visitorRecordList(modelMap, page, request);
	        return "ufans/ufans-visitorRecordListAjax";
      }
      
      
     /**
	 * 异步获取访客来源数量统计
	 * @param modelMap
	 * @param request
	 * @return
	 */
    @RequestMapping("getVisitorSourceCountAjax")
    @ResponseBody
    @LoginValidate
    public String getVisitorSourceCountAjax(ModelMap modelMap,  HttpServletRequest request) {
    	//String shopId = SessionUtils.getCurrentShopId();
    	String shopId = getLoginMemberShopId();
    	String date = request.getParameter("date");
    	Date dt = null;
    	if(StringUtils.isNotBlank(date)){
    		dt = DateUtil.parseDate(date, "yyyy-MM-dd"); 
    	}
    	Map<String,Integer> visitorCountMap = visitorService.getVisitorResourceCount(shopId,dt);
    	String result = JSONObject.toJSON(visitorCountMap).toString();
        return result;
    }
}
