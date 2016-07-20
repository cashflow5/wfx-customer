package com.yougou.wfx.customer.base;

import com.yougou.wfx.customer.annotations.WXLoginValidate;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.enums.OperatingEnvironmentEnum;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.seller.SellerinfoVo;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.system.ISystemService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/22
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ISystemService systemService;
    @Autowired
    private ISellerService sellerService;
    /**
     * 主页
     *
     * @since 1.0 Created by lipangeng on 16/5/9 下午2:12. Email:lipg@outlook.com.
     */
    @RequestMapping("/index")
    @WXLoginValidate
    public String index(@RequestParam(name = "shopId", required = false) String shopId) {
    	if(StringUtils.isEmpty(shopId)){
			return "redirect:index.sc?shopId=" + SessionUtils.getCurrentShopIdOrDefault();
		}
        return "redirect:/" + shopId + ".sc?flag=1";
    }

    @RequestMapping("/agent")
    public String agent(HttpServletRequest request) {
    	String agnet = request.getHeader("User-Agent").toLowerCase();
    	if(agnet.contains("micromessenger")){
    		 return "base/download";
    	}else{
    		if (agnet.contains("ipad;") || agnet.contains("iphone;") || agnet.contains("safari;")) {
                return "redirect:" + WfxConstant.WFX_APP_IOS_URL;
            } else {
                return "redirect:" + WfxConstant.WFX_APP_ANDROID_URL;
            }
    	}
        
    }
    
    /**
     * 判断用户是否为分销商
     *
     * @since 1.0 Created by lipangeng on 16/5/9 下午2:12. Email:lipg@outlook.com.
     */
    @RequestMapping("/checkIsSeller")
    @ResponseBody
    public Result checkIsSeller(String memberId) {
    	SellerinfoVo v = sellerService.getSellInfoByMemberId(memberId);
    	if(v!=null){
    		return Result.create(true, "用户是分销商");
    	}else{
    		return Result.create(false, "用户不是分销商");
    	}
    }
    
    /**
     * 判断用户的类型
     *
     * @since 1.0 Created by lipangeng on 16/5/9 下午2:12. Email:lipg@outlook.com.
     */
    @RequestMapping("/getMemberTypeByMemberId")
    @ResponseBody
    public String getMemberTypeByMemberId(String memberId) {
    	if(StringUtils.isEmpty(memberId)){
    		return "";
    	}
    	return sellerService.getMemberTypeByMemberId(memberId).getKey();
    }
    
    /**
     * 修改Tip显示状态
     *
     * @since 1.0 Created by lipangeng on 16/5/9 下午2:12. Email:lipg@outlook.com.
     */
    @RequestMapping("/modityTipStatus")
    @ResponseBody
    public void modityTipStatus(int status) {
    	SessionUtils.getRequest().getSession().setAttribute(SessionConstant.TIP_STATUS, status);
    }

    /**
     * 生成地址信息的json数据
     *
     * @since 1.0 Created by lipangeng on 16/5/18 下午5:25. Email:lipg@outlook.com.
     */
    //@RequestMapping("/areaJson")
    //@ResponseBody
    //public String areaJson() {
    //    return systemService.areaJson();
    //}

    /**
     * 测试客户端ip地址获取
     *
     * @since 1.0 Created by lipangeng on 16/5/27 下午3:30. Email:lipg@outlook.com.
     */
    //@RequestMapping("/remoteIPJson")
    //@ResponseBody
    //public String remoteIPJson() {
    //    return SessionUtils.getRemoteIP();
    //}
}
