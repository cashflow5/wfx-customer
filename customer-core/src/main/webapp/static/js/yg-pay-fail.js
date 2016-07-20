/**
 * 常用工具类
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/6 下午4:42
 * @since 1.0 Created by lipangeng on 16/4/6 下午4:42. Email:lipg@outlook.com.
 */
var YG_OrderPayFail = (function ($, M) {
    /** 初始化 */
    function Init() {
        Init_OrderDetail();
    }

    function Init_OrderDetail() {
        $('.mui-content').on('tap', '#orderDetail', function () {
            location.href = rootPath + '/order/' + orderId + '.sc';
        })
    }

    $(function () {
        Init();
    });
    return {};
}(Zepto, mui));