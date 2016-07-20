package com.yougou.wfx.customer.common.constant;

import java.util.Random;

/**
 * Created by zhang.sj on 2016/3/22.
 */
public class WfxConstant {


    public static final String WFX_INDEX_URL = "/index.sc";
    /**
     * 用户登录url
     */
    public static final String WFX_LOGIN_URL = "/login.sc";

    /**
     * 注册页面地址
     */
    public static final String WFX_REGISTER_URL = "/register.sc";
    /**
     * 手机绑定页面地址
     */
    public static final String WFX_PHONE_BIND_URL = "/usercenter/bind-phone.sc";

    /**
     * 注册页面验证手机地址
     */
    public static final String WFX_REGISTER_PHONE_URL = "/register/phone.sc";

    /**
     * 注册页面设置账户密码地址
     */
    public static final String WFX_REGISTER_PASSWORD_URL = "/register/password.sc";

    /**
     * 注册成功显示页面
     */
    public static final String WFX_REGISTER_SECCESS_URL = "/register/success.sc";

    /**
     * 找回密码
     */
    public static final String WFX_RESET_PASSWORD_URL = "/reset_password.sc";

    /**
     * 找回密码-验证手机号码
     */
    public static final String WFX_RESET_PASSWORD_PHONE_URL = "/reset_password/phone.sc";

    /**
     * 找回密码-设置密码页面
     */
    public static final String WFX_RESET_PASSWORD_PASSWORD_URL = "/reset_password/password.sc";

    /**
     * 找回密码-设置密码成功显示页面
     */
    public static final String WFX_RESET_PASSWORD_SECCESS_URL = "/reset_password/success.sc";

    /**
     * 退出地址
     */
    public static final String WFX_LOGOUT_URL = "/logout.sc";

    public static final String WFX_USERCENTER_URL = "/usercenter.sc";
    /**
     * 用户登录sessionid
     */
    public static final String WFX_LOGIN_CUSTOMER = "wfx_login_customer";
    /**
     * 跳转页面的url参数前缀,获取登录前来源页面
     */
    public static final String WFX_REDIRECT_TO_LOGIN_URL_PREFIX = "redirect:" + WFX_LOGIN_URL + "?redirectURL=";

    /**
     * 用户中心-我的地址簿
     */
    public static final String WFX_USERCENTER_MY_ADDRESS_URL = "/usercenter/myaddress.sc";

    /**
     * 用户中心-添加新地址
     */
    public static final String WFX_USERCENTER_CREATE_MY_ADDRESS_URL = "/usercenter/myaddress/create.sc";

    /**
     * 用户中心-用户信息
     */
    public static final String WFX_USERCENTER_USER_INFO_URL = "/usercenter/myaddress/userinfo.sc";

    /**
     * 用户中心-修改密码
     */
    public static final String WFX_USERCENTER_MODITY_PASSWORD_URL = "/usercenter/moditypassword.sc";

    /**
     * 用户中心-修改密码-验证短信验证码
     */
    public static final String WFX_USERCENTER_MODITY_PASSWORD_VALID_PHONE_URL = "/usercenter/moditypassword/validPhone.sc";

    /**
     * 用户中心-修改密码-修改密码
     */
    public static final String WFX_USERCENTER_MODITY_PASSWORD_SET_PASSWORD_URL = "/usercenter/moditypassword/modityPassword.sc";

    /**
     * 用户中心-修改密码-修改密码
     */
    public static final String WFX_USERCENTER_MODITY_PASSWORD_SUCCESS_URL = "/usercenter/moditypassword/success.sc";

    /**
     * 我的订单列表页面
     */
    public static final String WFX_ORDER_MY_ORDERS_URL = "/order/myorder.sc";

    public static final String WFX_ORDER_MY_REFUNDS_URL = "/order/myrefunds.sc";

    /**
     * 返回成功标识
     */
    public static final String SUCCESS = "success";
    /**
     * 店铺首页热卖商品数量
     */
    public static final int COMMODITY_HOT_COUNT = 20;
    /**
     * 店铺全部热卖商品
     */
    public static final int COMMODITY_HOT_COUNT_ALL = 0;

    /**
     * 商品列表图片
     */
    public static final String WFX_COMMODITY_PICTURE_TYPE_LIST = "mb";

    /**
     * 商品详情角度图
     */
    public static final String WFX_COMMODITY_PICTURE_TYPE_ITEM = "m";
    
    /**
    * 商品S图
    */
   public static final String WFX_COMMODITY_SMALL_PICTURE = "s";
    /**
     * 购物车每个sku最多购买数量
     */
    public static final int WFX_SHOPPINGCART_SKU_MAX_COUNT_DEFAULT = 3;
    /**
     * 后台限制购物车每个sku最多购买数量
     */
    public static final String WFX_ORDER_SINGLE_COMM_MAX_NUM_KEY = "wfx.order.single.comm.max.num";
    /**
     * 购物车最多sku种类数，即最多能保存多少条购物车数据
     */
    public static final int WFX_SHOPPINGCART_SKUs_COUNT_DEFAULT = 99;
    /**
     * 购物车最多sku种类数，即最多能保存多少条购物车数据
     */
    public static final String WFX_ORDER_SHOPPINGCART_MAX_NUM_KEY = "wfx.order.shoppingcart.max.num";
    /**
     * 微信配置中Signature长度
     */
    public static final int WFX_WX_CONFIG_SIGNATURE = 16;
    /**
     * 每天可以创建的订单总数
     */
    public static final int WFX_ORDER_USER_ONE_DAY_MAX = 99;
    /**
     * 每天创建的订单总数的Key
     */
    public static final String WFX_ORDER_USER_ONE_DAY_MAX_KEY = "wfx.order.oneday.max.order.num";
    /**
     * 店招图片
     */
    public static final String WFX_SHOP_SIGN_URL_DEFALUT = "shop_sign";
    /**
     * logo
     */
    public static final String WFX_SHOP_LOGO_URL_DEFALUT = "shop_logo";
    /**
     * 错误信息提示
     */
    public static final String WFX_ERROR_MESSAGE = "3秒后自动跳转";
    /**
     * 错误信息Key
     */
    public static final String WFX_ERROR_MESSAGE_KEY = "errorMsg";

    /** 等待多少天自动确认收货 */
    public static final String WFX_ORDER_AUTO_CONFIRM_ORDER_DAYS = "wfx.order.auto.confirm.order.days";

    /**
     * 应用宝地址
     */
    public static final String WFX_APP_ANDROID_URL = "http://app.qq.com/#id=detail&appid=1105352465";
    /**
     * appstore地址
     */
    public static final String WFX_APP_IOS_URL = "https://itunes.apple.com/cn/app/you-gou-wei-ling-shou/id1115038811?l=cn&mt=8";
    
    /**
     * 微信授权地址
     */
    public static final String WFX_WX_GRANT_URL = "/login-wx.sc";
    
    public final static String SESSION_COOKIE_NAME = "yg_sid";
    public final static String SESSION_USER_KEY = "_u";
    
    /**优粉文章后台配置项**/
    public final static String UFANS_ARTICLE_URL_KEY = "wfx.h5.ad.article.url";
    /**
     * 获取图片cdn域名
     *
     * @return
     */
    public static String getPicServicePath() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            return "http://i1.ygimg.cn/pics";
        } else {
            return "http://i2.ygimg.cn/pics";
        }
    }
}
