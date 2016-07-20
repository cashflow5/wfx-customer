/**
 * create by zhang.sj
 */
var YG_Error = (function ($, M) {
    function Init() {
        Load();
    }

    function Load() {
        var url = $('.tip-msg').data('url');
        if ($.trim(url) != '') {
            setTimeout(function () {
                location.href = rootPath + '/' + url + '.sc';
            }, 3000);
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));