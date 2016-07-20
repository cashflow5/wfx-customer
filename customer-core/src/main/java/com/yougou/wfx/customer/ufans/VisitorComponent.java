package com.yougou.wfx.customer.ufans;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.enums.OperatingEnvironmentEnum;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.visitor.VisitorVo;
import com.yougou.wfx.customer.service.bapp.IBShopService;
import com.yougou.wfx.customer.service.visitor.IVisitorService;
import com.yougou.wfx.enums.VisitorSourceType;
import com.yougou.wfx.enums.VisitorVisitType;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

@Component
public class VisitorComponent {
	
	@Autowired
	private IVisitorService visitorService;
	@Autowired
	private IBShopService bShopService;
	private static Logger logger = LoggerFactory.getLogger(VisitorComponent.class);

	/**
	 * 保存记录访客信息
	 * 
	 * @param shopId
	 * @param commodityNo
	 * @param request
	 */
	public void saveVisitorInfo(String shopId, String commodityNo, HttpServletRequest request) {		
		try{
			SessionUserDetails loginUser = SessionUtils.getLoginUserDetails();
			// 记录访客信息
			// 从session 中获取用户信息			
			if (null != loginUser && StringUtils.isNotEmpty(shopId )) {
		    	if(  StringUtils.isNotEmpty( loginUser.getUserId() ) ){
		    		if( StringUtils.isNotEmpty( loginUser.getShopId() )){
		    			if( shopId.equals( loginUser.getShopId() ) ){
			    			return ;
			    		}
		    		}else{
			    		ShopOutputDto shopOutputDto = bShopService.getShopByMemberId( loginUser.getUserId() );
			    		if( shopOutputDto!=null && shopId.equals(shopOutputDto.getId()) ){
			    			return ;
			    		}
		    		}
		    	}
				
				VisitorVo visitorVo = new VisitorVo();
				visitorVo.setCommodityNo(commodityNo);
				visitorVo.setShopId(shopId);
				visitorVo.setVisitorId(loginUser.getUserId());
				visitorVo.setVisitorIp(SessionUtils.getRemoteIP());
				if(StringUtils.isNotBlank(commodityNo)){
					visitorVo.setVisitType(VisitorVisitType.COMMODITY_VISIT.getType());// 访问单品页
				}else{
					visitorVo.setVisitType(VisitorVisitType.SHOP_VISIT.getType());// 访问店铺首页
				}
				visitorVo.setVisitTime(new Date());
				HttpSession httpSession = request.getSession();
				// 获取运行环境
				Object runEnvironment = httpSession.getAttribute(SessionConstant.RUNNING_ENVIRONMENT);
				if (runEnvironment.toString().equals(OperatingEnvironmentEnum.WX_PLATFORM.getKey())) {
					visitorVo.setSourceType(VisitorSourceType.WEIXIN.getType());// 运行环境为微信平台访客来源为weixin
				} else {
					visitorVo.setSourceType(VisitorSourceType.OTHER.getType());// 其他访客来源
				}
	
				setVisitorRecord(visitorVo);
			}
		}catch(Exception e){
			logger.error("记录访客记录异常：", e);
		}
	}

	/**
	 * 异步保存访客信息
	 * 
	 * @param visitorVo
	 */
	public void setVisitorRecord(final VisitorVo visitorVo) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				visitorService.insertVistor(visitorVo);
			}
		}).start();
	}
}
