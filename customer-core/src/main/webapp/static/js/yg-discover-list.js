var YG_List = (function($, M) {
	var imgLazyloadApi = null;
	var requestPage;
	
	function Init() {
		Init_PullRefresh();
		$(".swiper-slide").each(function(){
			 var channelid=$("#channel").val();
			 if($(this).attr("rel")==channelid){$(this).addClass("slide-active");}
			
		})
		$('.mui-tab-item').on('tap', function () {
            location.href = $(this).attr('href');
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
        var channel="0";
      //var channel=$("#channel").val();暂时屏蔽频道，做首页的下拉加载
        $('.mui-content').dropload({
                                       scrollArea: window,
                                       autoLoad:false,
                                       loadDownFn: function (me) {
                                           var params = YG_Utils.Url.getParams();
                                           params['page'] = requestPage + 1;
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath +'/discover/list_ajax/'+channel+'.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (data.trim()=='') {
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