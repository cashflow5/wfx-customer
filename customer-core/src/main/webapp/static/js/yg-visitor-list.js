var YG_List = (function($, M) {
	var imgLazyloadApi = null;
	var requestPage;
	
	function Init() {
		 checkPullRefresh()	
	}
	function Init_ErrorMsg() {
	      YG_Utils.Notification.error(errorMsg);
	}
	
	//判断是否需要初始化下拉
	function checkPullRefresh(){
		 var pageCount = $("#pageCount").val();
		 requestPage = YG_Utils.Url.getParams()['page'];
         if (YG_Utils.Strings.IsBlank(requestPage)) {
            requestPage = 1;
         }
		 if(pageCount > 1 && pageCount >= requestPage){
				Init_PullRefresh();
		 }		
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
                                                      url: rootPath +'/ufans/visitor/visitorRecordListAjax.sc?date='+$("#date").val(),
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (!data.trim()) {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                          }else if((data.trim()=='loginOut')){
                                                        	  M.toast('登陆超时，请重新登录!');
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                        	  //window.location.href='/login.sc';
                                                          } else {
                                                              $('#listData').append(data);
                                                              requestPage = requestPage + 1;
                                                              me.resetload();
                                                          }
                                                      },
                                                      error: function (xhr, errorType, error) {
                                                          M.toast('刷新访客详情,错误:' + error);
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