package com.yougou.wfx.customer.common.constant;

/**
 * Session相关的常量
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/24 上午10:09
 * @since 1.0 Created by lipangeng on 16/3/24 上午10:09. Email:lipg@outlook.com.
 */
public class SessionConstant {
    /** Http头中来源常量 */
    public static final String HEADER_REFERER = "referer";

    public static final String HEADER_USER_AGENT = "user-agent";

    /** 登录地址的来源信息,供登陆后跳转原网页使用 */
    public static final String LOGIN_REFERER = "login_refer";

    /** 用来存储记住我信息的cookie的key */
    public static final String COOKIE_REMEMBER_ME = "YG_REMEMBER_ME";

    /** 记住我功能,对信息加密的附加salt */
    public static final String COOKIE_REMEMBER_ME_SALT = "|cookie_rms|";

    /** cookie的AES加密的KEY */
    public static final byte[] COOKIE_REMEMBER_ME_AES_KEY =
            new byte[]{- 26, 80, 59, 83, - 73, 66, - 16, - 96, 120, 74, 1, - 6, 50, 53, - 77, - 41};

    /** 密码的AES加密的KEY */
    public static final byte[] PASSWORD_AES_KEY = new byte[]{- 26, 80, 40, 83, - 73, 66, - 15, - 96, 12, 74, 1, - 6, 50, 53, - 71, - 41};

    /** 图片验证码 */
    public static final String IMAGE_CODE = "image_code";

    /** 注册时记录用户的手机号 */
    public static final String REGISTER_DETAILS = "register_details";

    /** 注册时记录用户的手机号 */
    public static final String RESET_PASSWORD_DETAILS = "reset_password_details";

    /** 修改密码时缓存的信息 */
    public static final String MODITY_PASSWORD_DETAILS = "modity_password_details";

    /** 当前店铺的id */
    public static final String CURRENT_SHOP_ID = "current_shop_id";

    /** 购买的店铺的id */
    public static final String BUYING_SHOP_ID = "buying_shop_id";
    /** 运行环境 */
    public static final String RUNNING_ENVIRONMENT = "running_environment";
    /** 微信用户信息 */
    public static final String WX_USER_INFO = "wx_user_info";
    /** 当前菜单 */
    public static final String CURRENT_MENU_ID = "current_menu_id";
    /** 运行环境 */
    public static final String WEB_ROOT = "context";
    /** IOS APP版本号 */
    public static final String IOS_APP_VERSION = "ios_app_version";
    /** ANDROID APP版本号 */
    public static final String ANDROID_APP_VERSION = "android_app_version";
    /** TipStatus */
    public static final String TIP_STATUS = "tip_status";
    /** ufan_article_url */
    public static final String UFAN_ARTICLE_URL = "ufan_article_url";

}
