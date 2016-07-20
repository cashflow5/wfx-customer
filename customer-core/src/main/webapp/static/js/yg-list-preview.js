var YG_List = (function($, M) {
	var imgLazyloadApi = null;
	var requestPage;
	
	function Init() {
		ShopCart();
		Init_PullRefresh();
		LazyImage();
	}


	function LazyImage() {
		imgLazyloadApi = M('.yg-piclist').imageLazyload({
			placeholder: '/static/images/thum.png'
		});
	}
	function ShopCart() {
		$.ajax({
			type: 'post',
			url: rootPath + '/shoopingcart/count.sc',
			dataType: 'json',
			data: {},
			success: function (data, status, xhr) {
				$('#shopcartTotal').text(data);
			},
			error: function (xhr, errorType, error) {
				YG_Utils.Notification.toast("获取购物车数据超时!");
			}
		});
	}
	function Init_ErrorMsg() {
	      YG_Utils.Notification.error(errorMsg);
	}

	 // 下拉刷新页面
    function Init_PullRefresh() {
        requestPage = YG_Utils.Url.getParams()['page'];
        if (YG_Utils.Strings.IsBlank(requestPage)) {
            requestPage = 1;
        }
        var shopId=$("#shopId_").val();
        $('.mui-content').dropload({
                                       scrollArea: window,
                                       loadDownFn: function (me) {
                                           var params = YG_Utils.Url.getParams();
                                           params['page'] = requestPage + 1;
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath +'/preview/shop/list/ajax/'+shopId+'.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (data == '') {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                          } else {
                                                              $('#listData').append(data);
                                                              requestPage = requestPage + 1;
                                                              me.resetload();
                                                              LazyImage();
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