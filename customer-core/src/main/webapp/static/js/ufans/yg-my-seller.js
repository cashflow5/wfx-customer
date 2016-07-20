var YG_List = (function($, M) {
	var imgLazyloadApi = null;
	var requestPage;
	
	function Init() {
		Init_PullRefresh();
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
        $('.mui-content').dropload({
                                       scrollArea: window,
                                       autoLoad:false,
                                       loadDownFn: function (me) {
                                           var params = YG_Utils.Url.getParams();
                                           params['page'] = requestPage + 1;
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath +'/ufans/mySeller/getSubListAjax.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (!data.trim()) {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                          } else {
                                                              $('#listData').append(data);
                                                              requestPage = requestPage + 1;
                                                              me.resetload();
                                                          }
                                                      },
                                                      error: function (xhr, errorType, error) {
                                                          M.toast('刷新我的经销商列表错误:' + error);
                                                          me.noData(true);
                                                          me.resetload();
                                                      }
                                                  });
                                       }
                                   });
        $(".dropload-refresh").hide(); 
    }

    $(function() {
		Init();
	});
	return {};
}(Zepto, mui));