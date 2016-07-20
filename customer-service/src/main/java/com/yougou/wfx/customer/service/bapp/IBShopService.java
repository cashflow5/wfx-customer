package com.yougou.wfx.customer.service.bapp;



import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.shop.ShopInfo;
import com.yougou.wfx.appserver.vo.shop.ShopInfoDetail;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * Created by lizhw on 2016/4/11.
 */
public interface IBShopService  extends IBaseService{
    com.yougou.wfx.appserver.vo.home.ShopInfo getShop(UserInfo userInfo, boolean b);

    ShopInfoDetail getShopDetailInfo(UserInfo userInfo);

    BooleanResult setShopInfo(ShopInfo shopInfo);

	ShopInfoDetail getShopDetailInfoByLoginName(String authorAccount);

	ShopOutputDto getShopByMemberId(String memberId);

	com.yougou.wfx.appserver.vo.home.ShopInfo getShop(ShopOutputDto shop,boolean b);

	WFXResult<String> generateQrCode( String wxCodeUrl,ShopOutputDto shopOutputDto);
}
