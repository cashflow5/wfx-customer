package com.yougou.wfx.customer.ufans;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.wfx.appserver.vo.home.ShopInfo;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.enums.MenuEnum;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.MatrixToImageWriter;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.bapp.IBShopService;
import com.yougou.wfx.customer.service.bapp.IMemberAccountService;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shop.impl.ShopServiceImpl;
import com.yougou.wfx.customer.service.visitor.IVisitorService;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;
/**
 * 首页
 * Created by lizhw on 2016/4/8.
 */
@Controller
@RequestMapping("ufans")
public class HomeController  {
	/**
	 * 店铺接口
	 */
	@Autowired
	private IBShopService bShopService;
	@Autowired
	private IWXService wXService;
	@Autowired
	private  WeiXinProperties weiXinProperties;
	@Autowired
	private ICommodityService commodityService;
	@Autowired
	private IVisitorService visitorService;
	@Autowired
	private IMemberAccountService memberAccountService;
	@Autowired
	private ISellerService sellerService;
	@Autowired
	private IShopService shopService;
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
    /**
     * 获取店铺信息
     *
     * @return
     */
    @RequestMapping("/home")
    @LoginValidate
    public String getShopInfo(ModelMap modelMap, HttpServletRequest request) {
    	//设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_FANS.getKey());
    	SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
    	if( loginUserDetails!=null && StringUtils.isNotEmpty( loginUserDetails.getUserId() ) ){
    		//获取当日店铺所有访客数量
    		int totalVisitCount = visitorService.getTotalVisitCount(SessionUtils.getCurrentShopIdOrDefault());
    		modelMap.put("totalVisitCount", totalVisitCount);
    		//店铺名称、店铺LOGO、招聘信息,今日收益,总收益（累计收益）,今日访客 ,7日订单,可体现金额（单位：分）
    		ShopOutputDto shopOutputDto = bShopService.getShopByMemberId( loginUserDetails.getUserId() );
    		if( shopOutputDto!=null ){
    			ShopInfo shop = bShopService.getShop( shopOutputDto, false);
    			shop.setPhoneNumber( shopOutputDto.getLoginName() );
                modelMap.addAttribute("shop", shop);
                modelMap.addAttribute("memberType", sellerService.getMemberTypeByMemberId(loginUserDetails.getUserId()).getKey());
                return "ufans/ufans-home";
    		}
    	}
    	String shopId = SessionUtils.getCurrentShopIdOrDefault();
		modelMap.put("shopId", shopId);
		return "ufans/what-is-ufans";
	}
    
    
    @RequestMapping("/detailShare")
    public String detailShare(ModelMap modelMap, HttpServletRequest request) {
    	return "ufans/detail-share";
	}
    
    @RequestMapping("/qrCodeOfShop")
    @LoginValidate
    public String qrCodeOfShop(ModelMap modelMap, HttpServletRequest request,String wxCodeUrl) {
    	SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
    	if( loginUserDetails!=null && StringUtils.isNotEmpty( loginUserDetails.getUserId() ) ){
    		modelMap.addAttribute("memberId" ,loginUserDetails.getUserId() );
            modelMap.addAttribute("wxConfig", wXService.getWXConfig(weiXinProperties.getAppId()));
    		ShopOutputDto shopOutputDto = bShopService.getShopByMemberId( loginUserDetails.getUserId() );
			if( shopOutputDto!=null  ){
				String qrCodeUrl =   shopOutputDto.getQrCodeUrl() ;
				if(StringUtils.isEmpty(qrCodeUrl)){
	    			try {
	    				qrCodeUrl = findQrCodeUrl(modelMap,wxCodeUrl,shopOutputDto);
	    				shopOutputDto.setQrCodeUrl(qrCodeUrl);
					} catch (Exception e) {
						logger.error("系统自动生成优粉二维码错误：",e);
					}
				}
    			modelMap.addAttribute("shop", shopOutputDto);
    		}
    	}
    	return "ufans/shop-erweima";
	}
    
    private String findQrCodeUrl(ModelMap modelMap,String wxCodeUrl, ShopOutputDto shopOutputDto ) throws Exception {
    	
		if (StringUtils.isNotBlank(wxCodeUrl)) {
			String logoUrl = shopOutputDto.getLogoUrl();
			if( StringUtils.isBlank(logoUrl) ){
				logoUrl = "http://w1.ygimg.cn/SHOP_LOGO/default_logo.png";
			}
			String shopCode = shopOutputDto.getShopCode();
			if(!shopCode.equals("优购微零售总店")){
				shopOutputDto.setShopCode("No."+shopCode);
			}
			// 生成图片 上传 保存DB
			WFXResult<String> baseResult = bShopService.generateQrCode(wxCodeUrl, shopOutputDto);
			
			if( baseResult!=null && ( ResultCodeEnum.SUCCESS.getKey()==baseResult.getResultCode() ) ){
				return baseResult.getResult();
			}
		}
		return "";
	}
    
    /**
	 * 生成店铺微信二维码
	 * @param request
	 * @param response
	 * @param wxCodeUrl 链接地址（每个商城店铺）
	 * @param shopCode 店铺编码，logoUrl 图片服务器全路径（每个店铺的logo）
	 * @throws Exception
	 */
	@RequestMapping("/generateWxQrCode")
	public void generateWxQrCode(HttpServletRequest request, HttpServletResponse response, String wxCodeUrl,ShopOutputDto shop) throws Exception {
		if (StringUtils.isNotBlank(wxCodeUrl) && shop !=null && StringUtils.isNotBlank(shop.getShopCode()) ) {
			if( StringUtils.isBlank(shop.getLogoUrl()) ){
				shop.setLogoUrl("http://w1.ygimg.cn/SHOP_LOGO/default_logo.png");
			}
			String shopCode = shop.getShopCode();
			if(!shopCode.equals("优购微零售总店")){
				shop.setShopCode("No."+shopCode);
			}
			// 1.设置响应头控制浏览器浏览器以图片的方式打开
			response.setContentType("image/png");// 等同于response.setHeader("Content-Type","image/jpeg");
			// 2.设置响应头控制浏览器不缓存图片数据
			response.setDateHeader("expries", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			OutputStream outStream = response.getOutputStream();
			MatrixToImageWriter.drawWholeToStream(shop,wxCodeUrl, 500, 810, outStream);
		}
	}
	
	/**
	 * 生成商品二维码图片
	 * @param request
	 * @param response
	 * @param wxCodeUrl
	 * @param logoUrl
	 * @throws Exception
	 */
	@RequestMapping("/generateCommQrCode/{commNo}")
	public void generateCommQrCode(@PathVariable String commNo,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getServerName();
		String shopId = SessionUtils.getCurrentShopIdOrDefault();
		String urlPath = path + "/" + shopId + "/item/" + commNo + ".sc";
		if(!urlPath.startsWith("http")){
			urlPath = "http://" + urlPath;
		}
		logger.info("urlPath:====" + urlPath);
		CommodityStyleVo commVo = commodityService.getCommodityByNo(commNo, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		ShopVo shop = shopService.getAndCheckShopById(shopId, request);
		String shopNo = "";
		if(null != shop){
			String shopCode = shop.getShopCode();
			if(null != shopCode && "优购微零售总店".equals(shopCode)){
				shopNo = shopCode;
			}else{
				shopNo = "No." + shopCode;
			}
		}
		Assert.notNull(commVo, String.format("获取商品%s详情异常", commNo));
		if (StringUtils.isNotBlank(urlPath)) {
			// 1.设置响应头控制浏览器浏览器以图片的方式打开
			response.setContentType("image/png");// 等同于response.setHeader("Content-Type","image/jpeg");
			// 2.设置响应头控制浏览器不缓存图片数据
			response.setDateHeader("expries", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			OutputStream outStream = response.getOutputStream();
			MatrixToImageWriter.drawCommQrCode(commVo,urlPath,shopNo,350,250,outStream);
		}
	}
	
	/**
	 * 生成普通微信二维码
	 * @param request
	 * @param response
	 * @param wxCodeUrl 链接地址
	 * @param logoUrl 图片服务器全路径（每个店铺的logo）
	 * @throws Exception
	 */
	@RequestMapping("/generateQrCode")
	public void generateQrCode(HttpServletRequest request, HttpServletResponse response, String wxCodeUrl,String logoUrl) throws Exception {
		if (StringUtils.isNotBlank(wxCodeUrl)) {
			if( StringUtils.isBlank(logoUrl) ){
				logoUrl = "http://w1.ygimg.cn/SHOP_LOGO/default_logo.png";
			}
			// 1.设置响应头控制浏览器浏览器以图片的方式打开
			response.setContentType("image/png");// 等同于response.setHeader("Content-Type","image/jpeg");
			// 2.设置响应头控制浏览器不缓存图片数据
			response.setDateHeader("expries", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			OutputStream outStream = response.getOutputStream();
			MatrixToImageWriter.drawQrCodeToStream(wxCodeUrl, 230, 230, logoUrl, outStream);
		}
	}
	
    @RequestMapping("/fansList")
    @LoginValidate
    public String fansList(ModelMap modelMap, Page page,  HttpServletRequest request) {
    	SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
    	if( loginUserDetails!=null && StringUtils.isNotEmpty( loginUserDetails.getSellerId() ) ){
    		String sellerId = loginUserDetails.getSellerId() ;
    		modelMap.addAttribute("sellerId" , sellerId );
    		int count = memberAccountService.queryFansListCount(sellerId);
    		modelMap.addAttribute("count", count);
    		if( count>0 ){
    			Page<MemberAccountOutputDto> result = memberAccountService.queryFansList(sellerId, page);
    			modelMap.addAttribute("result", result);
    		}
    	}
    	return "ufans/fans-list";
	}
    
    @RequestMapping("/fansListAjax")
    @LoginValidate
    public String fansListAjax(ModelMap modelMap, Page page,  HttpServletRequest request) {
    	fansList(modelMap, page, request);
    	return "ufans/fans-list-ajax";
	}

}
