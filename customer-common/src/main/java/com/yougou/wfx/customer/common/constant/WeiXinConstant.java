package com.yougou.wfx.customer.common.constant;

/**
 * <p>Title: WeiXinConstant</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年7月1日
 */
public class WeiXinConstant {
	/** 
	 * 新增粉丝（锁粉） 
	 * B通过A的链接微信授权登录成功（B必须为首次登录）
	 * */
    public static final String NEW_FANS_MESSAGE = "您刚刚收获了粉丝：{0}（微信昵称）";
    /** 
	 * 新增二级分销商
	 * B成为A的粉丝后，有支付订单产生，此时B成为A的二级分销商
	 * */
    public static final String NEW_TWO_LEVEL_AGENT_MESSAGE = "您新增了二级分销商：{0}（微信昵称）";
    /** 
	 * 新增三级分销商
	 * C成为B的粉丝后，有支付订单产生，此时C成为A的三级分销商
	 * */
    public static final String NEW_THREE_LEVEL_AGENT_MESSAGE = "您的二级分销商{0}（微信昵称）为您带来三级分销商{1}（微信昵称）";
    /** 
	 * 粉丝下单 
	 * 粉丝下单，成功支付
	 * */
    public static final String NEW_FANS_ORDER_MESSAGE = "您的粉丝{0}（微信昵称）在{1}成功购物，订单号：{2}，商品金额：{3}元，您的预估收益：{4}元";
    /** 
	 * 自己下单
	 * 自己进行下单，成功支付
	 * */
    public static final String NEW_MY_ORDER_MESSAGE = "您在{0}成功购物，订单号：{1}，商品金额：{2}元，您的预估收益：{3}元";
    /** 
	 * 二级分销或二级分销的粉丝成功下单
	 * 下线二级分销或二级分销的粉丝进行下单，成功支付
	 * */
    public static final String NEW_TWO_LEVEL_AGENT_AND_FANS_ORDER_MESSAGE = "您的二级分销商{0}（微信昵称）在{1}为您带来订单，订单号：{2}，商品金额：{3}元，您的预估收益：{4}元";
    /** 
	 * 三级分销或三级分销的粉丝成功下单
	 * 下线三级分销或三级分销的粉丝进行下单，成功支付
	 * */
    public static final String NEW_THREE_LEVEL_AGENT_AND_FANS_ORDER_MESSAGE = "您的三级分销商{0}（微信昵称）在{1}为您带来订单，订单号：{2}，商品金额：{3}元，您的预估收益：{4}元";
    /** 
	 * 成为优粉
	 * 有支付订单产生
	 * */
    public static final String TO_BE_FANS = "您已成为优粉，赶紧分享起来吧！佣金赚不停！";
    /** 
	 * 提现申请单审核成功
	 * 优粉申请提现，运营审核通过
	 * */
    public static final String APPLY_CASH_SUCCESS = "您的提现申请{0},已成功提现{1}元，将于X日内到账您的指定账户，请您留意！";
}
