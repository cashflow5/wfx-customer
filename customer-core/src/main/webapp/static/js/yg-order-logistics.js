/**
 *
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/9 下午4:03
 * @since 1.0 Created by lipangeng on 16/5/9 下午4:03. Email:lipg@outlook.com.
 */
var YG_Order_Logistics = (function ($, M) {
    function Init() {
        Init_ShowCommity();
    }

    function Init_ShowCommity() {
        $('.shopping-cart-list').on('tap', '.yg-layout-left,.goods-title', function () {
            var _this = $(this);
            var _parent = _this.parents('.shopping-cart-list-item');
            var _shopid = _parent.data('shopid');
            var _commodityId = _parent.data('commodityid');
            if (YG_Utils.Strings.IsBlank(_shopid) || YG_Utils.Strings.IsBlank(_commodityId)) {
                YG_Utils.Notification.error('无法查看商品信息,参数不足');
                return false;
            }
            location.href = rootPath + '/' + _shopid + '/item/' + _commodityId + '.sc';
        });
    }
    
    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));