package com.yougou.wfx.customer.service.bapp.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.finance.BankCardInfo;
import com.yougou.wfx.appserver.vo.finance.CashApply;
import com.yougou.wfx.appserver.vo.finance.CashApplyListResult;
import com.yougou.wfx.appserver.vo.finance.CashApplySearcher;
import com.yougou.wfx.appserver.vo.finance.CashDetail;
import com.yougou.wfx.appserver.vo.finance.CashDetailList;
import com.yougou.wfx.appserver.vo.finance.CashSetting;
import com.yougou.wfx.appserver.vo.finance.Commission;
import com.yougou.wfx.appserver.vo.finance.CommissionDetail;
import com.yougou.wfx.appserver.vo.finance.CommissionIdentity;
import com.yougou.wfx.appserver.vo.finance.CommissionList;
import com.yougou.wfx.appserver.vo.finance.CommissionSearcher;
import com.yougou.wfx.appserver.vo.finance.CommissionSummary;
import com.yougou.wfx.appserver.vo.finance.Incoming;
import com.yougou.wfx.appserver.vo.finance.IncomingSearcher;
import com.yougou.wfx.appserver.vo.finance.ProfitSummary;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.order.OrderProduct;
import com.yougou.wfx.customer.service.bapp.IFinanceService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.dto.base.UserContext;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.BankCompanyEnum;
import com.yougou.wfx.finance.dto.input.FinSellerAccountWithdrawInputDto;
import com.yougou.wfx.finance.dto.input.FinSellerAccountWithdrawPageInputDto;
import com.yougou.wfx.finance.dto.input.FinSellerInfoDetailPageInputDto;
import com.yougou.wfx.finance.dto.output.CommissionCollectOutputDto;
import com.yougou.wfx.finance.dto.output.CommissionDetailOutputDto;
import com.yougou.wfx.finance.dto.output.FinSellerAccountWithdrawOutputDto;
import com.yougou.wfx.finance.dto.output.FinSellerAccountWithdrawVo;
import com.yougou.wfx.finance.dto.output.FinSellerInfoDetailOutputDto;
import com.yougou.wfx.finance.dto.output.FinSellerInfoOutputDto;
import com.yougou.wfx.finance.dto.output.MessageOutputDto;
import com.yougou.wfx.order.dto.output.CommissionOrderOutputDto;
import com.yougou.wfx.order.dto.output.OrderDetailCommissionOutPutDto;
import com.yougou.wfx.order.dto.output.OrderOutputDto;
import com.yougou.wfx.seller.dto.input.SellerAuthorizeInputDto;
import com.yougou.wfx.seller.dto.input.SellerBankInputDto;
import com.yougou.wfx.seller.dto.output.SellerAuthorizeOutputDto;
import com.yougou.wfx.seller.dto.output.SellerBankOutputDto;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * Created by lizhw on 2016/4/12.
 */
@Service
public class FinanceServiceImpl extends BaseServiceImpl implements IFinanceService {

	@Override
	public ProfitSummary getProfitSummary(UserInfo userInfo) {
		// 可用余额、提现中金额、已提现金额
		ProfitSummary result = new ProfitSummary();
		String sellerId = getSellerId(userInfo);
		if (isBlank(sellerId)) {
			return result;
		}
		FinSellerInfoOutputDto seller = finSellerInfoFrontApi.getById(sellerId);
		if (seller != null) {
			// 预估金额
			Double preCommission = orderFrontApi.getAllPreCommission(sellerId);
			//结算金额
			Double amount = finSellerInfoFrontApi.getTotalCommissionAmount(sellerId);

			// 可提现金额
			result.setBalance(toDouble(seller.getAccountBalance()));
			// 佣金收益=预估金额+结算金额
			result.setCommission(addDouble(toDouble(preCommission), toDouble(amount)));
			// 提现中金额
			result.setOnCash(toDouble(seller.getCashingTotalAmount()));
			// 已提现金额
			result.setHasCash(toDouble(seller.getCashedTotalAmount()));
		}

		return result;
	}

	@Override
	public CommissionSummary getCommissionSummary(UserInfo userInfo) {
		CommissionSummary result = new CommissionSummary();
		String sellerId = getSellerId(userInfo);
		if (isBlank(sellerId)) {
			return result;
		}
		// ok:call api
		CommissionCollectOutputDto sInfo = commissionDetailFrontApi.getBySellerId(sellerId);
		if (sInfo != null) {
			// 预估金额
			Double preCommission = orderFrontApi.getAllPreCommission(sellerId);
			// 未结算
			result.setNotSettled(toDouble(sInfo.getUnSettlementAmount())+preCommission);
			// 已经结算
			result.setHasSettled(toDouble(sInfo.getSettlementAmount()));
		}

		return result;
	}

	@Override
	public CommissionList getCommissionList(CommissionSearcher searcher) {
		CommissionList result = new CommissionList();
		result.setSearcher(searcher);
		String sellerId = getSellerId(searcher.getUserInfo());
		if (isBlank(sellerId)) {
			result.setSummary(new CommissionSummary());
			return result;
		}

		UserContext uc = getUserContext(searcher, "commissionDetailFrontApi.findPage");
//		CommissionDetailPageInputDto inputDto = new CommissionDetailPageInputDto();
//		inputDto.setUserContext(uc);
//		inputDto.setSellerId(sellerId);
		//分销商id：sellerId，结算状态:status 0未结算，1已结算，2异常挂起
		Map<String,String> params = new HashMap<String,String>();
		params.put("sellerId", sellerId);
		params.put("status", "0");
		// ok:状态:未结算,已结算.0:未结算,1:已结算,2:异常挂起,3:已关闭
		// inputDto.setStatus(searcher.getStatus());

		PageModel<CommissionDetailOutputDto> page = new PageModel<CommissionDetailOutputDto>();
		page.setLimit(searcher.getPageSize());
		page.setPage(searcher.getPage());
		// 0:未结算,1:已结算
//		inputDto.setStatus("0");
		
		PageModel<CommissionOrderOutputDto> commissionOrdersPage = orderFrontApi.findCommissionOrderPage(sellerId, page);
	    if(commissionOrdersPage != null){
	    	List<CommissionOrderOutputDto> commissionOrders = commissionOrdersPage.getItems();
	    	for (CommissionOrderOutputDto item : commissionOrders) {
				Commission commission = new Commission();
				commission.setId(item.getOrderId());
				commission.setAmount(toDouble(item.getCommission()));
				commission.setOrderNo(item.getWfxOrderNo());
				commission.setTime(item.getCreateTime());
				commission.setLevel(item.getLevel());
				result.getItems1().add(commission);
			}
	    }
	    if(commissionOrdersPage != null && commissionOrdersPage.getItems() != null){
	    	page.setPage(searcher.getPage()-commissionOrdersPage.getItems().size());
	    }
	    if(page.getPage() > 0){
			PageModel<CommissionDetailOutputDto> comms1 = commissionDetailFrontApi.findPageH5(params, page);
			if (null == comms1) {
	
			} else {
				result.setTotal1(comms1.getTotalCount());
				List<CommissionDetailOutputDto> items1 = comms1.getItems();
				if (items1 != null && items1.size() > 0) {
					for (CommissionDetailOutputDto item : items1) {
						OrderOutputDto order = orderFrontApi.getOrderByWFXOrderNo(item.getWfxOrderNo());
						Commission commission = new Commission();
						commission.setId(order.getId());
						commission.setAmount(toDouble(item.getCommissionAmount()));
						commission.setOrderNo(item.getWfxOrderNo());
						commission.setTime(item.getOrderTime());
						commission.setLevel(item.getCommissionLevel());
						result.getItems1().add(commission);
					}
				}
			}
	    }
		 

//		inputDto.setStatus("1");
		params.put("status", "1");
		page.setPage(searcher.getPage());
		PageModel<CommissionDetailOutputDto> comms2 = commissionDetailFrontApi.findPageH5(params, page);
		if (comms2 != null) {
			result.setTotal2(comms2.getTotalCount());
			List<CommissionDetailOutputDto> items2 = comms2.getItems();
			if (items2 != null && items2.size() > 0) {
				for (CommissionDetailOutputDto item : items2) {
					OrderOutputDto order = orderFrontApi.getOrderByWFXOrderNo(item.getWfxOrderNo());
					Commission commission = new Commission();
					commission.setId(order.getId());
					commission.setAmount(toDouble(item.getCommissionAmount()));
					commission.setOrderNo(item.getWfxOrderNo());
					commission.setTime(item.getOrderTime());
					commission.setLevel(item.getCommissionLevel());
					result.getItems2().add(commission);
				}
			}
		}

		result.setSummary(getCommissionSummary(searcher.getUserInfo()));
		return result;
	}

	@Override
	public CommissionDetail getCommissionDetail(CommissionIdentity commissionIdentity) {
		CommissionDetail result = new CommissionDetail();
		if (isBlank(commissionIdentity.getId())) {
			return result;
		}
		// ok:call api
		OrderDetailCommissionOutPutDto detail = commissionDetailFrontApi.getCommissionDetailByNo(commissionIdentity.getId());
		if (detail == null) {
			return result;
		}

		result.setShopLevel(detail.getShopLevel());
		result.setOrderNo(detail.getWfxOrderDetailNo());
		result.setTime(detail.getCreateOrderTime());
		result.setAmount(toDouble(detail.getCommission()));
		result.setShopName(detail.getShopName());
		result.setShopCode(detail.getShopName());

		OrderProduct oi = new OrderProduct();
		// ok:商品图片
		oi.setUrl(detail.getPicSmall());
		// ok:商品名称
		oi.setDescribe(detail.getProdName());
		oi.setCommission(toDouble(detail.getCommission()));
		oi.setPrice(toDouble(detail.getWfxPrice()));
		oi.setNum(toInt(detail.getNum()));
		result.getProducts().add(oi);

		return result;
	}

	@Override
	public PageSearchResult<IncomingSearcher, Incoming> getIncomingList(IncomingSearcher searcher) {
		PageSearchResult<IncomingSearcher, Incoming> result = new PageSearchResult<IncomingSearcher, Incoming>();
		result.setSearcher(searcher);
		String sellerId = getSellerId(searcher.getUserInfo());
		if (isBlank(sellerId)) {
			result.setTotal(0);
			return result;
		}
		String loginName = searcher.getUserInfo().getLoginName();
		FinSellerInfoDetailPageInputDto inputDto = new FinSellerInfoDetailPageInputDto();
		inputDto.setSellerId(sellerId);
		UserContext uc = getUserContext(searcher, "finSellerInfoDetailFrontApi.querySellerDetails");
		inputDto.setUserContext(uc);
		inputDto.setTransactionFlag(searcher.getTransactionFlag());
		inputDto.setBillState("1");
		PageModel<Object> page = new PageModel<Object>();
		page.setLimit(searcher.getPageSize());
		page.setPage(searcher.getPage());

		PageModel<FinSellerInfoDetailOutputDto> incoms = finSellerInfoDetailFrontApi.querySellerDetails(inputDto, page);
		if (incoms == null) {
			result.setTotal(0);
			return result;
		}
		result.setTotal(incoms.getTotalCount());
		List<FinSellerInfoDetailOutputDto> items = incoms.getItems();
		if (items != null && items.size() > 0) {
			for (FinSellerInfoDetailOutputDto item : items) {
				Incoming incoming = new Incoming();
				incoming.setId(item.getId());
				incoming.setBillNo(item.getTransactionNumber());
				incoming.setOrderNo(item.getTransactionOrderNum());

				// 交易标识 1：收入，2-支出
				incoming.setTransactionFlag(item.getTransactionFlag());
				// 支出
				incoming.setExpendAmount(item.getExpendAmount());
				// 收入
				incoming.setIncomeAmount(item.getIncomeAmount());

				// 收支类型 (1.佣金收益,2.提现)
				incoming.setIncomingType(item.getTransactionType());

				// 状态(1,交易成功2,处理中,3,交易关闭,4,交易失败)
				incoming.setState(item.getBillState());

				// 支付方式 支付方式编码，详见 BankCompanyEnum
				String paymentStyle = item.getPaymentStyle();
				incoming.setPayWay(paymentStyle);
				incoming.setPayWayName(getDescByKey(paymentStyle));
				// 分销商帐号（登录名）
				incoming.setSellerAccount(loginName);

				// 备注
				incoming.setRemark(item.getRemark());

				incoming.setTime(item.getTransactionTime());
				result.getItems().add(incoming);
			}
		}

		return result;
	}
	
	@Override
	public Incoming getIncomeDetail(String id){
		FinSellerInfoDetailOutputDto item = finSellerInfoDetailFrontApi.getById(id);
		Incoming incoming = new Incoming();
		incoming.setId(item.getId());
		incoming.setBillNo(item.getTransactionNumber());
		incoming.setOrderNo(item.getTransactionOrderNum());

		// 交易标识 1：收入，2-支出
		incoming.setTransactionFlag(item.getTransactionFlag());
		// 支出
		incoming.setExpendAmount(item.getExpendAmount());
		// 收入
		incoming.setIncomeAmount(item.getIncomeAmount());

		// 收支类型 (1.佣金收益,2.提现)
		incoming.setIncomingType(item.getTransactionType());

		// 状态(1,交易成功2,处理中,3,交易关闭,4,交易失败)
		incoming.setState(item.getBillState());

		// 支付方式 支付方式编码，详见 BankCompanyEnum
		String paymentStyle = item.getPaymentStyle();
		incoming.setPayWay(paymentStyle);
		incoming.setPayWayName(getDescByKey(paymentStyle));
		// 备注
		incoming.setRemark(item.getRemark());

		incoming.setTime(item.getTransactionTime());
		return incoming;
	}
	
	

	private String getDescByKey(String keyValue) {
		if (null != keyValue) {
			for (BankCompanyEnum st : BankCompanyEnum.values()) {
				String key = st.getKey() + "";
				if (key.equals(keyValue)) {
					return st.getDesc();
				}
			}
		}
		return null;
	}

	@Override
	public BankCardInfo getBankCard(String sellerId) {
		BankCardInfo card = new BankCardInfo();
		if (isBlank(sellerId)) {
			return card;
		}
		SellerBankOutputDto bank = sellerInfoFrontApi.getSellerBank(sellerId);
		if (bank == null) {
			return card;
		}
		FinSellerInfoOutputDto seller = finSellerInfoFrontApi.getById(sellerId);

		if (seller != null) {
			double balance = toDouble(seller.getAccountBalance());
			// 账户余额
			card.setBalance(balance);
		}

		card.setAccountId(bank.getId());
		card.setBankNo(bank.getBankNo());

		card.setCardNo(getBankCardCodeHidden(bank.getBankAccount()));

		card.setBank(bank.getBankName());
		card.setBankProvince(bank.getBankProvince());
		card.setBankCity(bank.getBankCity());
		card.setBankBranch(bank.getBankSubName());
		String trueName = bank.getTrueName() + "";
		int len = trueName.length();
		String name = "";
		StringBuilder sb = new StringBuilder();
		if (len == 1) {
			name = trueName;
		} else if (len == 2) {
			name = trueName.substring(0, 1) + "*";
		} else if (len == 3) {
			name = trueName.substring(0, 2) + "*";
		} else {
			for (int i = 0; i < len; i++) {
				if (i < len - 1) {
					sb.append(trueName.substring(i, i + 1));
				} else {
					sb.append("*");
				}
			}
			name = sb.toString();

		}

		card.setRealName(trueName);
		String code = bank.getIdCardNo();
		if (code.length() < 6) {
			code = "******";
		} else if (code.length() < 10) {
			code = code.substring(0, 6) + "****";
		} else if (code.length() < 14) {
			code = code.substring(0, 6) + "****" + code.substring(10, code.length());
		} else {
			// 11011019010101xxxx
			code = code.substring(0, 6) + "********" + code.substring(14, code.length());
		}

		card.setIdCode(code);

		SellerAuthorizeOutputDto author = bank.getSellerAuthorizeOutputDto();
		if (author != null) {
			card.setIdPicUrl(author.getIdCardPic());
			card.setIdPicUrlBack(author.getIdCardPicBack());
			card.setProxyPicUrl(author.getAuthorizePic());
			card.setStatus(author.getStatus() + "");
		}

		return card;
	}

	private String getBankCardCodeHidden(String bankCardCode) {

		String bankAccount = bankCardCode + "";
		StringBuilder sb = new StringBuilder();
		int len = bankAccount.length();
		for (int i = 0; i < len; i++) {
			if (i < 5 || i > len - 5) {
				sb.append(bankAccount.substring(i, i + 1));
			} else {
				sb.append("*");
			}
		}
		return sb.toString();
	}

	public Map<Integer, String> getAllBank() {
		Map<Integer, String> allBankList = sellerInfoFrontApi.getAllBankList();
		return allBankList;
	}

	@Override
	public BankCardInfo bindBankCard(BankCardInfo bankCardInfo) {

		String sellerId = getSellerId(bankCardInfo.getUserInfo());
		if (isBlank(sellerId)) {
			return bankCardInfo;
		}

		SellerBankInputDto inputDto = new SellerBankInputDto();
		SellerAuthorizeInputDto author = new SellerAuthorizeInputDto();

		inputDto.setSellerId(sellerId);

		// 身份证
		inputDto.setIdCardNo(bankCardInfo.getIdCode());
		// 真实姓名
		inputDto.setTrueName(bankCardInfo.getRealName());
		// 银行卡号
		inputDto.setBankAccount(bankCardInfo.getCardNo());
		// 银行编号
		inputDto.setBankNo(bankCardInfo.getBankNo());
		// 开户行
		inputDto.setBankName(bankCardInfo.getBank());

		// 支行省份
		inputDto.setBankProvince(bankCardInfo.getBankProvince());
		// 支行城市
		inputDto.setBankCity(bankCardInfo.getBankCity());
		// 支行名称
		inputDto.setBankSubName(bankCardInfo.getBankBranch());

		UserContext uc = getUserContext(bankCardInfo, "sellerInfoFrontApi.insertSellerBank");

		author.setUserContext(uc);
		author.setSellerId(sellerId);

		// ok:绑定银行卡资质
		// 委托授权书扫描件
		author.setAuthorizePic(bankCardInfo.getProxyPicUrl());
		// 身份证扫描件
		author.setIdCardPic(bankCardInfo.getIdPicUrl());
		author.setIdCardPicBack(bankCardInfo.getIdPicUrlBack());

		author.setCreateTime(new Date());
		author.setSellerLoginName(bankCardInfo.getUserInfo().getLoginName());

		WFXResult<String> wfxResult = sellerInfoFrontApi.insertSellerBank(inputDto, author);
		if (wfxResult != null) {
			bankCardInfo.setOperResultMsg(wfxResult.getResultMsg());
			bankCardInfo.setAccountId(wfxResult.getResult());
		}
		if (wfxResult != null && (isBlank(wfxResult.getResultMsg()) || !isBlank(wfxResult.getResult()))) {
			bankCardInfo.setOperResult(true);
		}
		bankCardInfo.setOperResultMsg(wfxResult.getResultMsg());
		return bankCardInfo;
	}

	@Override
	public BankCardInfo changeBankCard(BankCardInfo bankCardInfo) {
		String sellerId = getSellerId(bankCardInfo.getUserInfo());
		if (isBlank(sellerId)) {
			return bankCardInfo;
		}
		SellerBankOutputDto bank = sellerInfoFrontApi.getSellerBank(sellerId);

		SellerBankInputDto inputDto = new SellerBankInputDto();
		SellerAuthorizeInputDto author = new SellerAuthorizeInputDto();
		
		//分销商ID
		inputDto.setSellerId(sellerId);
		// 身份证
		inputDto.setIdCardNo(bankCardInfo.getIdCode());
		// 真实姓名
		inputDto.setTrueName(bankCardInfo.getRealName());
		// 银行卡号
		inputDto.setBankAccount(bankCardInfo.getCardNo());
		// 银行编号
		inputDto.setBankNo(bankCardInfo.getBankNo());
		// 开户行
		inputDto.setBankName(bankCardInfo.getBank());
		// 支行省份
		inputDto.setBankProvince(bankCardInfo.getBankProvince());
		// 支行城市
		inputDto.setBankCity(bankCardInfo.getBankCity());
		// 支行名称
		inputDto.setBankSubName(bankCardInfo.getBankBranch());

		UserContext uc = getUserContext(bankCardInfo, "sellerInfoFrontApi.updateSellerBank");
		author.setUserContext(uc);
		author.setSellerId(sellerId);
		
		// ok:绑定银行卡资质
		// 身份证扫正反面
		author.setIdCardPic(bankCardInfo.getIdPicUrl());
		author.setIdCardPicBack(bankCardInfo.getIdPicUrlBack());
		
		if (bank != null) {
			// 开户行
			if (isBlank(inputDto.getBankName()))
				inputDto.setBankName(bank.getBankName());
			// 身份证
			if (isBlank(inputDto.getIdCardNo()))
				inputDto.setIdCardNo(bank.getIdCardNo());
			// 真实姓名
			if (isBlank(inputDto.getTrueName()))
				inputDto.setTrueName(bank.getTrueName());
			// 银行卡号
			if (isBlank(inputDto.getBankAccount()))
				inputDto.setBankAccount(bank.getBankAccount());
			// 银行编号
			if (isBlank(inputDto.getBankNo()))
				inputDto.setBankNo(bank.getBankNo());
			// 支行省份
			if (isBlank(inputDto.getBankProvince()))
				inputDto.setBankProvince(bank.getBankProvince());
			// 支行城市
			if (isBlank(inputDto.getBankCity()))
				inputDto.setBankCity(bank.getBankCity());
			// 支行名称
			if (isBlank(inputDto.getBankSubName()))
				inputDto.setBankSubName(bank.getBankSubName());
			SellerAuthorizeOutputDto authorOld = bank.getSellerAuthorizeOutputDto();
			if (authorOld != null) {
				// 身份证扫描件
				if (isBlank(author.getIdCardPic()))
					author.setIdCardPic(authorOld.getIdCardPic());
				// 身份证扫描件
				if (isBlank(author.getIdCardPicBack()))
					author.setIdCardPicBack(authorOld.getIdCardPicBack());
			}
		}

		author.setCreateTime(new Date());
		author.setSellerLoginName(bankCardInfo.getUserInfo().getLoginName());

		WFXResult<Boolean> wfxResult = sellerInfoFrontApi.updateSellerBank(inputDto, author);

		if (wfxResult != null) {
			bankCardInfo.setOperResultMsg(wfxResult.getResultMsg());
		}
		if (wfxResult == null || !wfxResult.getResult()) {
			bankCardInfo.setAccountId("");
		} else {
			bankCardInfo.setOperResult(true);
		}
		return bankCardInfo;

	}

	@Override
	public BooleanResult applyCash(CashApply apply) {
		BooleanResult result = new BooleanResult();
		UserContext uc = getUserContext(apply, "createApplyBill");
		String sellerId = apply.getUserInfo().getSellerId();
		if (isBlank(sellerId)) {
			result.setError("分销商信息为空！");
			return result;
		}

		SellerBankOutputDto bank = sellerInfoFrontApi.getSellerBank(sellerId);

		if (bank == null) {
			result.setError("绑定银行卡信息为空！");
			return result;
		}
		FinSellerAccountWithdrawInputDto inputDto = new FinSellerAccountWithdrawInputDto();
		ShopOutputDto shop = shopFrontApi.getShopBySeller(sellerId);
		FinSellerInfoOutputDto seller = finSellerInfoFrontApi.getById(sellerId);
		if (seller == null || shop == null) {
			result.setError("店铺信息为空！");
			return result;
		}
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 提现申请单号（*）?
		inputDto.setWithdrawApplyNo(uuid);
		// 申请时间（*）
		inputDto.setApplyTime(new Date());
		// 店铺名称（*）
		inputDto.setShopName(shop.getName());
		// 分销商账号（*）
		inputDto.setSellerAccount(apply.getUserInfo().getLoginName());
		// 分销商ID（*）
		inputDto.setSellerId(sellerId);
		inputDto.setUserContext(uc);
		if (seller != null) {
			// 余额账户余额（*）
			inputDto.setAccountBalance(toDouble(seller.getAccountBalance()));
		}

		// 实收费用（*）?
		inputDto.setActualReceivedAmount(apply.getAmount());
		// 服务费（*）?
		inputDto.setServiceAmount(0d);
		// 提现金额（*）
		inputDto.setWithdrawAmount(apply.getAmount());
		// 开户名（*）
		inputDto.setAccountName(bank.getTrueName());
		// 开户行（*）
		inputDto.setAccountBankNo(bank.getBankNo());
		String bankNo = bank.getBankNo();
		if (isBlank(bankNo)) {
			result.setError("银行编码错误，请重新绑定！");
			return result;
		}
		Map<Integer, String> allBankList = sellerInfoFrontApi.getAllBankList();
		try {
			Integer integer = new Integer(bankNo);
			String s = allBankList.get(integer);
			if (!isBlank(s)) {
				// 开户行名称
				inputDto.setAccountBankName(bank.getBankName());
			} else {
				result.setError("银行编码错误，请重新绑定！");
				return result;
			}
		} catch (Exception ex) {

		}

		// 开户行名称+分行名称
		inputDto.setAccountBankAllName(bank.getBankName() + bank.getBankSubName());
		// 开户账号（*）
		inputDto.setAccountNo(bank.getBankAccount());
		// 申请人（*）?
		inputDto.setApplyer(bank.getTrueName());

		MessageOutputDto applyBill = finSellerAccountWithdrawFrontApi.createApplyBill(inputDto);
		/**
		 * 200：单次提现金额不能大于 500 元 300：月累计提现金额不能超过30000元 400：账户余额不足（提现金额大于账户余额）
		 * code:100 表示当前操作成功. code:500 表示当前操作失败.
		 */
		if (applyBill == null) {
			result.setError("系统错误！");
		} else if ("100".equals(applyBill.getCode())) {
			result.setMsg("操作成功.！");
			result.setResult(true);
		} else if ("200".equals(applyBill.getCode())) {
			result.setError("单次提现金额不能大于 500 元！");
		} else if ("300".equals(applyBill.getCode())) {
			result.setError("月累计提现金额不能超过30000元！");
		} else if ("400".equals(applyBill.getCode())) {
			result.setError("账户余额不足！");
		} else if ("500".equals(applyBill.getCode())) {
			result.setError("操作失败.！");
		} else {
			result.setError("操作失败.！");
		}
		return result;

	}

	@Override
	public CashSetting getCashSetting() {
		CashSetting result = new CashSetting();
		String lowerstAmount = systemApi.getSystemConfigValue("wfx.finance.applycash.lowerst");
		String highestAmount = systemApi.getSystemConfigValue("wfx.finance.applycash.highest");
		String monthTotalAmount = systemApi.getSystemConfigValue("wfx.finance.applycash.month");
		double lowerstAmountInt = 50;
		double highestAmountInt = 500;
		double monthTotalAmountInt = 30000;
		try{
			if(StringUtils.isNoneBlank(lowerstAmount,highestAmount,monthTotalAmount)){
				lowerstAmountInt = Double.valueOf(lowerstAmount);
				highestAmountInt = Double.valueOf(highestAmount);
				monthTotalAmountInt= Double.valueOf(monthTotalAmount);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		result.setLowerstAmount(lowerstAmountInt);
		result.setHighestAmount(highestAmountInt);
		result.setMonthTotalAmount(monthTotalAmountInt);
		return result;
	}

	@Override
	public CashApplyListResult applyCashList(CashApplySearcher searcher) {
		CashApplyListResult result = new CashApplyListResult();
		CashDetailList cashing = applyCashList(searcher, 1);
		CashDetailList cashed = applyCashList(searcher, 2);
		CashDetailList cashErr = applyCashList(searcher, 3);
		result.setSearcher(searcher);
		result.setCashing(cashing);
		result.setCashed(cashed);
		result.setCashErr(cashErr);

		return result;

	}

	private CashDetailList applyCashList(CashApplySearcher searcher, int status) {
		CashDetailList result = new CashDetailList();
		result.setSearcher(searcher);
		String sellerId = searcher.getUserInfo().getSellerId();
		if (isBlank(sellerId)) {
			return result;
		}
		String loginName = searcher.getUserInfo().getLoginName();

		FinSellerAccountWithdrawPageInputDto pageInputDto = new FinSellerAccountWithdrawPageInputDto();
		pageInputDto.setSellerId(sellerId);
		// 提现列表接口 statusFlag:(1:提现中，2:已提现，3:提现失败)
		pageInputDto.setStatusFlag(status);
		PageModel<Object> page = new PageModel<Object>();
		page.setPage(searcher.getPage());
		page.setLimit(searcher.getPageSize());
		FinSellerAccountWithdrawVo list = finSellerAccountWithdrawFrontApi.queryPageList(pageInputDto, page);
		if (list == null) {
			return result;
		}
		// 状态总金额
		result.setAmount(toDouble(list.getAmount()));
		PageModel<FinSellerAccountWithdrawOutputDto> pageModel = list.getPageModel();
		if (pageModel != null) {
			result.setTotal(pageModel.getTotalCount());
			List<FinSellerAccountWithdrawOutputDto> items = pageModel.getItems();
			if (items != null && items.size() > 0) {
				for (FinSellerAccountWithdrawOutputDto item : items) {
					CashDetail d = new CashDetail();
					d.setId(item.getId());
					d.setWithdrawApplyNo(item.getWithdrawApplyNo());

					d.setApplyTime(item.getApplyTime());

					d.setAccountBalance(item.getAccountBalance());

					d.setActualReceivedAmount(item.getActualReceivedAmount());

					// 服务费
					d.setServiceAmount(item.getServiceAmount());

					// 提现金额
					d.setWithdrawAmount(item.getWithdrawAmount());

					// 支付方式编码，详见 BankCompanyEnum
					// d.setPaymentStyle(item.getPaymentStyle());
					// String paymentStyle = item.getPaymentStyle();
					// d.setPaymentStyleName(getDescByKey(paymentStyle));

					// 开户名
					d.setAccountName(item.getAccountName());
					// 开户行
					d.setAccountBankNo(item.getAccountBankNo());
					// 开户行简称（例：招商银行）
					d.setAccountBankName(item.getAccountBankName());

					// 开户行全称（例：某某银行支行）
					d.setAccountBankAllName(item.getAccountBankAllName());

					// 开户账号
					d.setAccountNo(getBankCardCodeHidden(item.getAccountNo()));

					// 付款时间，即提现时间
					d.setModifyTime(item.getModifyTime());

					// 对应的交易明细ID
					d.setSellerDetailId(item.getSellerDetailId());
					// 备注
					d.setRemark(item.getRemark());
					// 提现状态：1-待审核,2-已审核,3-已提现,4-审核不通过
					// 说明：提现中（待审核和已审核），已提现（已提现），提现失败（审核不通过）
					d.setBillStatus(item.getBillStatus());
					d.setSellerAccount(loginName);
					result.getItems().add(d);
				}
			}
		}
		return result;
	}

	public BooleanResult getCanApplyCash(UserInfo userInfo) {
		BooleanResult result = new BooleanResult();
		String sellerId = userInfo.getSellerId();
		if (isBlank(sellerId)) {
			return result;
		}
		WFXResult<Boolean> pass = sellerInfoFrontApi.isSellerAuthorizePass(sellerId);
		if (pass != null && pass.getResult()) {
			result.setResult(true);

		} else if (pass != null) {
			result.setError(pass.getResultMsg());
			result.setResult(false);
		}
		return result;
	}
	
	@Override
	public CashDetail getCashDetail(String id){
		FinSellerAccountWithdrawOutputDto withdrawOutputDto = finSellerAccountWithdrawFrontApi.queryDetailById(id);
		CashDetail d = new CashDetail();
		d.setId(withdrawOutputDto.getId());
		d.setWithdrawApplyNo(withdrawOutputDto.getWithdrawApplyNo());

		d.setApplyTime(withdrawOutputDto.getApplyTime());

		d.setAccountBalance(withdrawOutputDto.getAccountBalance());

		d.setActualReceivedAmount(withdrawOutputDto.getActualReceivedAmount());

		// 服务费
		d.setServiceAmount(withdrawOutputDto.getServiceAmount());

		// 提现金额
		d.setWithdrawAmount(withdrawOutputDto.getWithdrawAmount());

		// 支付方式编码，详见 BankCompanyEnum
		// d.setPaymentStyle(item.getPaymentStyle());
		// String paymentStyle = item.getPaymentStyle();
		// d.setPaymentStyleName(getDescByKey(paymentStyle));

		// 开户名
		d.setAccountName(withdrawOutputDto.getAccountName());
		// 开户行
		d.setAccountBankNo(withdrawOutputDto.getAccountBankNo());
		// 开户行简称（例：招商银行）
		d.setAccountBankName(withdrawOutputDto.getAccountBankName());

		// 开户行全称（例：某某银行支行）
		d.setAccountBankAllName(withdrawOutputDto.getAccountBankAllName());

		// 开户账号
		d.setAccountNo(getBankCardCodeHidden(withdrawOutputDto.getAccountNo()));

		// 付款时间，即提现时间
		d.setModifyTime(withdrawOutputDto.getModifyTime());

		// 对应的交易明细ID
		d.setSellerDetailId(withdrawOutputDto.getSellerDetailId());
		// 备注
		d.setRemark(withdrawOutputDto.getRemark());
		// 提现状态：1-待审核,2-已审核,3-已提现,4-审核不通过
		// 说明：提现中（待审核和已审核），已提现（已提现），提现失败（审核不通过）
		d.setBillStatus(withdrawOutputDto.getBillStatus());
		return d;
	}

	@Override
	public BooleanResult applyCashValidation(String sellerId) {
		MessageOutputDto outputDto = finSellerAccountWithdrawFrontApi.applyBillValidation(sellerId);
		BooleanResult result = new BooleanResult();
		if("100".equals(outputDto.getCode())){
			result.setResult(true);
		}else if("600".equals(outputDto.getCode())){
			result.setResult(false);
			result.setError("提现失败，超出提现次数(一周只能提现一次)");
		}else{
			result.setResult(false);
			result.setError("提现失败，分销商信息不存在");
		}
		return result;
	}
	

}
