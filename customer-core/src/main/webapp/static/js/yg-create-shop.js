/**
 * create by guoran
 */
var YG_CreateShop = (function ($, M) {
    function Init() {
        //SelectPhoto();
    }

    function SelectPhoto() {
        M('.edit-box').on('tap', '.edit-block', function (e) {
            $('#userPhoto').trigger('click');
        })
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));