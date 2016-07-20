package com.yougou.wfx.customer.service.bapp;


import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.LoginVo;
import com.yougou.wfx.appserver.vo.login.MemberBindPhoneVo;
import com.yougou.wfx.appserver.vo.login.MemberForWXVo;
import com.yougou.wfx.appserver.vo.login.ModifyPasswordVo;
import com.yougou.wfx.appserver.vo.login.NewPasswordVo;
import com.yougou.wfx.appserver.vo.login.Sms;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.login.ValidCode;
import com.yougou.wfx.appserver.vo.login.ValidResult;
import com.yougou.wfx.appserver.vo.login.WXParam;
import com.yougou.wfx.appserver.vo.seller.SubSellerInfo;
import com.yougou.wfx.appserver.vo.seller.SubSellerSearcher;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.visitor.VisitorVo;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;

/**
 * Created by lizhw on 2016/4/11.
 */
public interface IMemberAccountService  extends IBaseService{
    UserInfo login(LoginVo loginVo);

    BooleanResult sendSms(Sms sms);
    BooleanResult changePassword(NewPasswordVo passwd);

    ValidResult checkValidCode(ValidCode validCode);

    BooleanResult modifyPassword(ModifyPasswordVo mp);

	Object wxSellerBind(MemberForWXVo memberForWXVo);

	Object memberBindPhone(MemberBindPhoneVo memberBindPhoneVo);

	BooleanResult sendSmsNew(Sms sms);

	WXParam getWXParam();
	/** 获取当前经销商的粉丝列表 */
	Page<MemberAccountOutputDto> queryFansList(String sellerId,Page page);
	int queryFansListCount(String sellerId);
}
