var YG_Guide = (function ($, M) {
    function Init() {
        BtnCreate();
    }


    function BtnCreate() {
        $('#btnCreate').on('tap', function () {
            $.ajax({
                type: 'post',
                url: rootPath + '/shop/check.sc',
                dataType: 'json',
                beforeSend:function(){
                    YG_Utils.Layer.show();
                },
                complete:function(){
                    YG_Utils.Layer.closeAll();
                },
                success: function (data, status, xhr) {
                    if (data.success && data.data) {
                        location.href = rootPath + "/" + $('#shopId').val() + '/create.sc';
                    } else {
                        YG_Utils.Notification.toast(data.message);
                        setTimeout(function () {
                            location.href = rootPath + "/" + $('#shopId').val() + ".sc?flag=1";
                        }, 3000);
                    }
                },
                error: function (xhr, errorType, error) {
                    YG_Utils.Notification.toast(data.message);
                }
            });
        });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));