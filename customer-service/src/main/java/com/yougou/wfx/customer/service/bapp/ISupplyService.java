package com.yougou.wfx.customer.service.bapp;


import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.supply.Style;
import com.yougou.wfx.appserver.vo.supply.SupplyMarketInfo;

/**
 * Created by lizhw on 2016/4/12.
 */
public interface ISupplyService extends IBaseService {

    BooleanResult setProxyProduct(Style style);

    SupplyMarketInfo getSupplyMarketInfo();

	BooleanResult setAllProxyProducts(UserInfo userInfo);

}
