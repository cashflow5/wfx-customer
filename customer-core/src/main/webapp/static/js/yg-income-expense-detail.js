var YG_My_Income = (function($, M) {
	var imgLazyloadApi = null;
	var requestPage;
	var typeG = '0';
	
	function Init() {
		LinkJump();
		Init_PullRefresh();
	}
	
	function Init_ErrorMsg() {
	      YG_Utils.Notification.error(errorMsg);
	}

	function LinkJump() {
		M('.mui-table-view').on('tap','.mui-tab-item',function(){
			var $this = $(this),arr = [],divHtml,
			staticText = $this.text();
			type = $this.attr('type');
			typeG = type;
			initPage(type);
			$("#showSelectV").text(staticText);
			mui('.mui-popover').popover('toggle');//关闭弹出层
		})
	}
	
	function initPage(type){
		if(type=='0'){
			$("div[name='all']").show();
			$("div[name='income']").hide();
			$("div[name='expense']").hide();
		}else if(type=='1'){
			$("div[name='all']").hide();
			$("div[name='income']").show();
			$("div[name='expense']").hide();
		}else{
			$("div[name='all']").hide();
			$("div[name='income']").hide();
			$("div[name='expense']").show();
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
                                                      url: rootPath +'/finance/getIncomingListAjax.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (!data.trim()) {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                              M.toast('没有更多数据了');
                                                          } else {
                                                        	  $('.dropload-down').remove();
                                                              $('#listData').append(data);
                                                              requestPage = requestPage + 1;
                                                              initPage(typeG);
                                                              me.resetload();
                                                          }
                                                      },
                                                      error: function (xhr, errorType, error) {
                                                          M.toast('刷新收支列表,错误:' + error);
                                                          me.noData(true);
                                                          me.resetload();
                                                      }
                                                  });
                                       }
                                   });
        $(".dropload-refresh").hide();  
        $(".dropload-down").css("background","#fff");
    }

	
	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));