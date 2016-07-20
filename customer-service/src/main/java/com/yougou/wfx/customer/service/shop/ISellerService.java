package com.yougou.wfx.customer.service.shop;

import com.yougou.wfx.customer.common.enums.UserTypeEnum;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.seller.SellerinfoVo;
import com.yougou.wfx.customer.model.shop.ShopCatVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;

import java.util.List;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/29
 */
public interface ISellerService {
    /**
     * 根据分销商id获取店铺销售分类信息
     *
     * @param id
     * @return
     */
    List<ShopCatVo> getShopCatByShopId(String id);

    /**
     * 创建店铺接口
     *
     * @param shopVo
     * @param parentShopId
     * @return
     */
    Result<String> createShop(ShopVo shopVo, String parentShopId);

    /**
     * 获取分销商信息
     * @param sellerId
     * @return
     */
    SellerinfoVo getSellInfoById(String sellerId);

	/**
	 * 根据会员ID获取分销商信息
	 * @param memberId
	 * @return
	 */
	SellerinfoVo getSellInfoByMemberId(String memberId);
	
	/**
	 * 根据会员ID获取分销商信息
	 * @param memberId
	 * @return
	 */
	UserTypeEnum getMemberTypeByMemberId(String memberId);
	
}
