var YG_List_Category = (function ($, M) {
    var imgLazyloadApi = null;

    function Init() {
        Menu();
        LazyImage();
        LinkJump();
        Init_PullRefresh();
    }

    function Menu() {
        M('.yg-header').on('tap', '#btnMenu', function (e) {
            $('.yg-menu').toggle();
        });
    }

    function LazyImage() {
        imgLazyloadApi = M('.yg-piclist').imageLazyload({
            placeholder: '/static/images/thum.png'
        });
    }
    function LinkJump(){
        $('#indexMenu').on('tap', '.mui-tab-item', function(e) {
            location.href = $(this).attr('href');
        });
    }
    // 下拉刷新页面
    function Init_PullRefresh() {
        requestPage = YG_Utils.Url.getParams()['page'];
        if (YG_Utils.Strings.IsBlank(requestPage)) {
            requestPage = 1;
        }
        var params = YG_Utils.Url.getParams();
        params['page'] = requestPage + 1;
        params['categoryId'] = $('#categoryId').val();
        $('.mui-content').dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                $.ajax({
                    type: 'post',
                    url: rootPath + '/' + $('#shopId').val() + '/category-search/refresh.sc',
                    data: params,
                    success: function (data, status, xhr) {
                        if (data == '') {
                            me.noData(true);
                        } else {
                            $('.yg-floor').append(data);
                            LazyImage();
                            requestPage = requestPage + 1;
                            me.resetload();
                        }
                    },
                    error: function (xhr, errorType, error) {
                        M.toast('刷新商品列表失败,错误:' + error);
                        me.resetload();
                    }
                });
            }
        });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));