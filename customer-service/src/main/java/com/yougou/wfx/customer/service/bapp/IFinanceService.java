package com.yougou.wfx.customer.service.bapp;


import java.util.Map;

import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.finance.BankCardInfo;
import com.yougou.wfx.appserver.vo.finance.CashApply;
import com.yougou.wfx.appserver.vo.finance.CashApplyListResult;
import com.yougou.wfx.appserver.vo.finance.CashApplySearcher;
import com.yougou.wfx.appserver.vo.finance.CashDetail;
import com.yougou.wfx.appserver.vo.finance.CashSetting;
import com.yougou.wfx.appserver.vo.finance.CommissionDetail;
import com.yougou.wfx.appserver.vo.finance.CommissionIdentity;
import com.yougou.wfx.appserver.vo.finance.CommissionList;
import com.yougou.wfx.appserver.vo.finance.CommissionSearcher;
import com.yougou.wfx.appserver.vo.finance.CommissionSummary;
import com.yougou.wfx.appserver.vo.finance.Incoming;
import com.yougou.wfx.appserver.vo.finance.IncomingSearcher;
import com.yougou.wfx.appserver.vo.finance.ProfitSummary;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.finance.dto.input.FinSellerAccountWithdrawInputDto;

/**
 * Created by lizhw on 2016/4/12.
 */
public interface IFinanceService extends IBaseService {
    ProfitSummary getProfitSummary(UserInfo userInfo);

    CommissionSummary getCommissionSummary(UserInfo userInfo);

    CommissionList getCommissionList(CommissionSearcher searcher);

    CommissionDetail getCommissionDetail(CommissionIdentity commissionIdentity);

    PageSearchResult<IncomingSearcher, Incoming> getIncomingList(IncomingSearcher searcher);

    Map<Integer, String> getAllBank();

    BankCardInfo getBankCard(String sellerId);

    BankCardInfo bindBankCard(BankCardInfo bankCardInfo);

    BankCardInfo changeBankCard(BankCardInfo bankCardInfo);

    BooleanResult applyCash(CashApply apply);

    CashSetting getCashSetting();

    CashApplyListResult applyCashList(CashApplySearcher searcher);


    BooleanResult getCanApplyCash(UserInfo userInfo);

	Incoming getIncomeDetail(String id);

	CashDetail getCashDetail(String id);

	BooleanResult applyCashValidation(String sellerId);
}
