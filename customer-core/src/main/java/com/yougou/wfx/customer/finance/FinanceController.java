package com.yougou.wfx.customer.finance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.finance.BankCardInfo;
import com.yougou.wfx.appserver.vo.finance.CashApply;
import com.yougou.wfx.appserver.vo.finance.CashApplyListResult;
import com.yougou.wfx.appserver.vo.finance.CashApplySearcher;
import com.yougou.wfx.appserver.vo.finance.CashDetail;
import com.yougou.wfx.appserver.vo.finance.CommissionSearcher;
import com.yougou.wfx.appserver.vo.finance.Incoming;
import com.yougou.wfx.appserver.vo.finance.IncomingSearcher;
import com.yougou.wfx.appserver.vo.finance.ProfitSummary;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.ImageUtils;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.finance.SellerAccountInfoVo;
import com.yougou.wfx.customer.service.bapp.IFinanceService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ImageFtpTypeEnum;
import com.yougou.wfx.enums.SellerAuthorizeStateEnum;
import com.yougou.wfx.seller.api.front.ISellerInfoFrontApi;
import com.yougou.wfx.system.api.IFileUploadApi;
import com.yougou.wfx.system.api.IWFXSystemApi;

@Controller
@RequestMapping(value = "finance")
public class FinanceController {

	private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);

	@Autowired
	private IFinanceService financeService;
	@Autowired
	private ISellerInfoFrontApi sellerInfoFrontApi;
	@Autowired
	private IFileUploadApi fileUploadApi;
	@Autowired
	protected IWFXSystemApi systemApi;

	/**
	 * 我的收益
	 */
	@RequestMapping("myIncome")
	@LoginValidate
	public String myIncome(Model model) {
		model.addAttribute("summary", financeService.getProfitSummary(getUserInfo()));
		return "finance/my-income";
	}

	/**
	 * 佣金详情
	 */
	@RequestMapping("commissionDetail")
	@LoginValidate
	public String commissionDetail(Model model, Page page) {
		CommissionSearcher searcher = new CommissionSearcher();
		searcher.setUserInfo(getUserInfo());
		searcher.setPage(page.getPage());
		// searcher.setPageSize(page.getLimit());
		// 订单号、时间、金额、总条数
		// 0:未结算,1:已结算,2:异常挂起,3:已关闭
		model.addAttribute("commissionList", financeService.getCommissionList(searcher));
		return "finance/commission_detail";
	}

	/**
	 * 佣金详情
	 */
	@RequestMapping("commissionDetailAjax")
	@LoginValidate
	public String commissionDetailAjax(Model model, Page page) {
		commissionDetail(model, page);
		return "finance/commission_detail_ajax";
	}

	/**
	 * 获取收支明细列表 分销商ID、当前页、分页条数
	 *
	 * @return
	 */
	@RequestMapping("getIncomingList")
	@LoginValidate
	public String getIncomingList(Model model, IncomingSearcher searcher) {
		searcher.setUserInfo(getUserInfo());
		// 总条数、收支明细ID、状态、时间、金额
		PageSearchResult<IncomingSearcher, Incoming> result = financeService.getIncomingList(searcher);
		model.addAttribute("list", result.getItems());
		return "finance/income-expense-detail";
	}

	/**
	 * 获取收支明细列表 分销商ID、当前页、分页条数
	 *
	 * @return
	 */
	@RequestMapping("getIncomingListAjax")
	@LoginValidate
	public String getIncomingListAjax(Model model, IncomingSearcher searcher) {
		getIncomingList(model, searcher);
		return "finance/income-expense-detail-ajax";
	}

	/**
	 * 进入账户设置
	 *
	 * @return
	 */
	@RequestMapping("accountSetUp")
	public String accountSetUp(Model model) {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();

		// 审核状态
		Integer authorizeStatus = sellerInfoFrontApi.getSellerAuthorizeStatus(loginUserDetails.getSellerId());
		model.addAttribute("authorizeStatus", authorizeStatus);// 审核状态
		String authorizeStatusDesc = "";
		if (authorizeStatus == null || authorizeStatus.intValue() == 0) {
			authorizeStatusDesc = "待上传";
		} else {
			authorizeStatusDesc = SellerAuthorizeStateEnum.getDescByKey(authorizeStatus);
		}
		model.addAttribute("authorizeStatusDesc", authorizeStatusDesc);// 审核状态

		// 基本信息
		BankCardInfo result = financeService.getBankCard(loginUserDetails.getSellerId());
		model.addAttribute("bank", result);

		// 银行列表
		Map<Integer, String> bankMap = financeService.getAllBank();
		Map<String, String> newBankMap = new HashMap<String, String>();
		for (Map.Entry<Integer, String> entry : bankMap.entrySet()) {
			newBankMap.put(entry.getKey().toString(), entry.getValue());
		}
		model.addAttribute("bankMap", newBankMap);

		return "finance/account-set-up-begin";
	}

	/**
	 * 查看账户设置示例图片
	 * 
	 * @param model
	 * @param type
	 * @return
	 */
	public String showAccountSetUpImg(Model model, String type) {
		model.addAttribute("imgType", type);
		return "finance/account-set-imgshow";
	}

	/**
	 * 修改账户设置
	 *
	 * @return
	 */
	@RequestMapping(value = "modityAccountSetUp", method = RequestMethod.POST)
	public String modityAccountSetUp(HttpServletRequest request, HttpServletResponse response, Model model, SellerAccountInfoVo sellerAccountInfoVo) {

		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();

		BankCardInfo bankCardInfo = new BankCardInfo();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(loginUserDetails.getUserId());
		userInfo.setSellerId(loginUserDetails.getSellerId());
		userInfo.setLoginName(loginUserDetails.getUserName());
		bankCardInfo.setUserInfo(userInfo);// 用户信息
		bankCardInfo.setRealName(sellerAccountInfoVo.getRealName());// 真实姓名
		bankCardInfo.setBankNo(sellerAccountInfoVo.getBankNo());// 银行编码
		bankCardInfo.setBank(sellerAccountInfoVo.getBank());// 银行名称
		bankCardInfo.setBankBranch(sellerAccountInfoVo.getBankBranch());// 开户支行
		bankCardInfo.setBankProvince(sellerAccountInfoVo.getBankProvince());// 开户省
		bankCardInfo.setBankCity(sellerAccountInfoVo.getBankCity());// 开户市

		// 是否需要更新
		int bankCardNoChange = sellerAccountInfoVo.getBankCardNoChange();
		int idCodeNoChange = sellerAccountInfoVo.getIdCodeNoChange();
		int uploadImgNoChange = sellerAccountInfoVo.getUploadImgNoChange();
		if (idCodeNoChange != 1) {
			bankCardInfo.setIdCode(sellerAccountInfoVo.getIdCode());// 身份证号
		}
		if (bankCardNoChange != 1) {
			bankCardInfo.setCardNo(sellerAccountInfoVo.getCardNo());// 银行卡号
		}

		response.setContentType("text/html;charset=UTF-8");
		try {
			String imgstrZ = sellerAccountInfoVo.getIdPicUrl();
			String imgstrF = sellerAccountInfoVo.getIdPicUrlBack();
			if (!StringUtils.isEmpty(imgstrZ) || !StringUtils.isEmpty(imgstrF)) {
				InputStream fontInputStream = null;
				InputStream backInputStream = null;
				if(!StringUtils.isEmpty(imgstrZ)){
					String[] arr = imgstrZ.split(",");
					if(arr.length == 2){
						imgstrZ = arr[1];
						fontInputStream = ImageUtils.base64Decoder(imgstrZ);
					}
				}
				if(!StringUtils.isEmpty(imgstrF)){
					String[] arr = imgstrF.split(",");
					if(arr.length == 2){
						imgstrF = arr[1];
						backInputStream = ImageUtils.base64Decoder(imgstrF);
					}
				}
				if(backInputStream == null || fontInputStream == null){
					uploadImgNoChange = 12;
				}

				String frontFilename = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";

				String backFilename = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
				if (uploadImgNoChange != 1 && uploadImgNoChange != 12) {
//					logger.error("======================压缩正面图片开始==========================");
//					fontInputStream = ImageUtils.resize(fontInputStream, 1000, 1300);
//					logger.error("======================压缩正面图片结束==========================");
					logger.error("======================上传正面图片开始==========================");
					WFXResult<String> fileFrontResult = fileUploadApi.frontImageUpload(frontFilename, ImageFtpTypeEnum.SELLER_IDENTITY_IMG, fontInputStream);
					logger.error("======================上传正面图片结束==========================");
					sellerAccountInfoVo.setIdPicUrl(fileFrontResult.getResultCode() == 0 ? fileFrontResult.getResult() : "");
				}
				if (uploadImgNoChange != 2 && uploadImgNoChange != 12) {
//					logger.error("======================压缩反面图片开始==========================");
//					backInputStream = ImageUtils.resize(backInputStream, 1000, 1300);
//					logger.error("======================压缩反面图片开始==========================");
					logger.error("======================上传反面图片开始==========================");
					WFXResult<String> fileBackResult = fileUploadApi.frontImageUpload(backFilename, ImageFtpTypeEnum.SELLER_IDENTITY_IMG, backInputStream);
					logger.error("======================上传反面图片结束==========================");
					sellerAccountInfoVo.setIdPicUrlBack(fileBackResult.getResultCode() == 0 ? fileBackResult.getResult() : "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		bankCardInfo.setIdPicUrl(sellerAccountInfoVo.getIdPicUrl());// 身份证正正面
		bankCardInfo.setIdPicUrlBack(sellerAccountInfoVo.getIdPicUrlBack());// 身份证正反面

		// 更新
		Integer authorizeStatus = sellerInfoFrontApi.getSellerAuthorizeStatus(loginUserDetails.getSellerId());
		if (authorizeStatus == null || authorizeStatus.intValue() == 0) {
			financeService.bindBankCard(bankCardInfo);
		} else {
			financeService.changeBankCard(bankCardInfo);
		}

		return accountSetUp(model);
	}

	/**
	 * 查看账户设置相关图片
	 * 
	 * @param type
	 * @return
	 */
	public String showAccountImg(String type, Model model) {
		// model.
		return "finance/account-img-show";
	}

	/**
	 * 获取收支明细列表
	 *
	 * @return
	 */
	@RequestMapping("getIncomingDetail")
	@LoginValidate
	public String getIncomingDetail(Model model, Incoming incoming) {
		incoming = financeService.getIncomeDetail(incoming.getId());
		model.addAttribute("income", incoming);
		if ("1".equals(incoming.getIncomingType())) {
			return "finance/income-detail";
		} else {
			return "finance/expense-detail";
		}
	}

	/**
	 * 提现明细列表
	 *
	 * @param searcher
	 * @return
	 */
	@RequestMapping(value = "applyCashList")
	@LoginValidate
	public String applyCashList(Model model, CashApplySearcher searcher, String tab) {
		searcher.setUserInfo(getUserInfo());
		CashApplyListResult result = financeService.applyCashList(searcher);
		model.addAttribute("cash", result);
		model.addAttribute("tab", tab);
		return "finance/withdraw-cash-list";
	}

	/**
	 * 提现明细列表
	 *
	 * @param searcher
	 * @return
	 */
	@RequestMapping(value = "applyCashListAjax")
	@LoginValidate
	public String applyCashListAjax(Model model, CashApplySearcher searcher, String tab) {
		applyCashList(model, searcher, tab);
		return "finance/withdraw-cash-list-ajax";
	}

	/**
	 * 提现明细列表
	 *
	 * @param searcher
	 * @return
	 */
	@RequestMapping(value = "applyCashDetail")
	@LoginValidate
	public String applyCashDetail(Model model, String id) {

		CashDetail cashDetail = financeService.getCashDetail(id);
		cashDetail.setAccountNo(StringUtils.substring(cashDetail.getAccountNo(), -4));
		model.addAttribute("cashDetail", cashDetail);
		return "finance/withdraw-cash-detail";
	}

	@RequestMapping(value = "applyCash")
	@LoginValidate
	public String applyCash(Model model) {
		// 获取是否可以提现 财务接口
		UserInfo userInfo = getUserInfo();
		// 审核状态
		Integer authorizeStatus = sellerInfoFrontApi.getSellerAuthorizeStatus(userInfo.getSellerId());
		if (authorizeStatus == null || authorizeStatus == 0) {
			return accountSetUp(model);
		} else {
			BankCardInfo bankInfo = financeService.getBankCard(userInfo.getSellerId());
			ProfitSummary profit = financeService.getProfitSummary(userInfo);
			model.addAttribute("bankInfo", bankInfo);
			model.addAttribute("profit", profit);
			model.addAttribute("cashSetting", financeService.getCashSetting());
			return "finance/apply-cash";
		}
	}

	@RequestMapping(value = "applyCashNow")
	@ResponseBody
	@LoginValidate
	public String applyCashNow(Model model, CashApply apply) {
		apply.setUserInfo(getUserInfo());
		// true or false?
		BooleanResult result = financeService.applyCash(apply);
		return result.getError();
	}

	@RequestMapping(value = "applyCashValidation")
	@ResponseBody
	@LoginValidate
	public String applyCashValidation(Model model) {
		UserInfo userInfo = getUserInfo();
		Integer authorizeStatus = sellerInfoFrontApi.getSellerAuthorizeStatus(userInfo.getSellerId());
		if (authorizeStatus == null || authorizeStatus == 0) {
			return "setbank";
		} else {
			BooleanResult result = financeService.getCanApplyCash(userInfo);
			if(result.isResult()){
				result = financeService.applyCashValidation(userInfo.getSellerId());
			}
			return result.getError();
		}
	}

	private UserInfo getUserInfo() {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		UserInfo userInfo = new UserInfo();
		userInfo.setSellerId(loginUserDetails.getSellerId());
		userInfo.setId(loginUserDetails.getUserId());
		userInfo.setLoginName(loginUserDetails.getUserName());
		return userInfo;
	}

}
