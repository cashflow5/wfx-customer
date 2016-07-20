/**
 * create by lijunfang
 */
var YG_Admin = (function ($, M) {
    function Init() {
        LinkJump();
        Init_ReplaceUrl();
        Init_ReloadUserHeadUrl();
    }

    function LinkJump() {
        M('.yg-bar').on('tap', '.link-jump', function () {
            var $this = $(this),
                childText = $this.find('.mui-tab-label').html();
            switch (childText) {
                case "全部订单":
                    location.href = rootPath + '/order/myorder.sc';
                    break;
                case "待付款":
                    location.href = rootPath + '/order/myorder.sc?status=WAIT_PAY';
                    break;
                case "已发货":
                    location.href = rootPath + '/order/myorder.sc?status=DELIVERED';
                    break;
                case "退款/售后":
                    location.href = rootPath + '/order/myrefunds.sc';
                    break;
                default:
                    location.href = "#";
                    break;
            }
        });
        $('.user-photo').on('tap', '#userHeadImg', function (e) {
            location.href = rootPath + '/usercenter/userinfo.sc';
        })
    }

    // 重写地址栏url
    function Init_ReplaceUrl() {
        if (YG_Utils.Strings.IsBlank(shopId)) {
            return;
        }
        var _params = YG_Utils.Url.getParams();
        if (_params.shopId != undefined && YG_Utils.Strings.NotBlank(_params.shopId)) {
            return;
        }
        var _hostname = location.hostname;
        var _pathname = location.pathname;
        var _protocol = location.protocol;
        var _port = location.port;
        var _url = _protocol + '//' + _hostname;
        if (_port != '80' && YG_Utils.Strings.NotBlank(_port)) {
            _url = _url + ':' + _port;
        }
        _url = _url + _pathname;
        _params.shopId = shopId;
        _params.versions = new Date().getTime();
        _url = _url + '?shopId=' + shopId;
        for (var key in _params) {
            if (key != 'shopId') {
                _url = _url + '&' + key + '=' + _params[key];
            }
        }
        history.pushState({}, document.title, _url);
    }

    // 强刷头像地址
    function Init_ReloadUserHeadUrl() {
        if (window.sessionStorage) {
            var _headUrlStorage = window.sessionStorage.getItem('userHeadUrl');
            if (YG_Utils.Strings.NotBlank(_headUrlStorage)) {
                $('#userHeadUrlInput').val(_headUrlStorage);
                window.sessionStorage.removeItem('userHeadUrl');
            }
        }
        var _headUrl = $('#userHeadUrlInput').val();
        if (YG_Utils.Strings.NotBlank(_headUrl)) {
            $('#userHeadImg')[0].src = _headUrl;
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));