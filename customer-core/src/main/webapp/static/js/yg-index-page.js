var YG_Index = (function($, M) {
	function Init() {
		LazyImage();
		Init_PullRefresh();
	}



	function LazyImage() {
		M('.yg-floor').imageLazyload({
			placeholder: '/static/images/thum.png'
		});
	}

	 // 下拉刷新页面
    function Init_PullRefresh() {
        requestPage = YG_Utils.Url.getParams()['page'];
        if (YG_Utils.Strings.IsBlank(requestPage)) {
            requestPage = 1;
        }
        var shopId=$('#currentShopId').val();
        $('#mui-content-index').dropload({
                                       scrollArea: window,
                                       autoLoad:false,
                                       loadDownFn: function (me) {
                                           var params = YG_Utils.Url.getParams();
                                           params['page'] = requestPage + 1;
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath +'/'+shopId+'/index_commodity_ajax.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (data.trim()==''||requestPage==5) {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                          } else {
                                                              $('#listData').append(data);
                                                              LazyImage();
                                                              requestPage = requestPage + 1;
                                                              me.resetload();
                                                          }
                                                      },
                                                      error: function (xhr, errorType, error) {
                                                          M.toast('刷新订单失败,错误:' + error);
                                                          me.noData(true);
                                                          me.resetload();
                                                      }
                                                  });
                                       }
                                   });
    }

	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));