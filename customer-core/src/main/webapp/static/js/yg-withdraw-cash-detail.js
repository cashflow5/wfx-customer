var YG_My_Income = (function($, M) {
	var imgLazyloadApi = null;
	var requestPage;
	
	function Init() {
		initPage();
		Init_swithPage();
		Init_PullRefresh();
	}

	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}

	function initPage() {
		var tab = getQueryString("tab");
		$(".mui-bar-tab>a").eq(tab).addClass("mui-active");
	}
	
	function Init_swithPage(){
		M('.mui-bar-tab').on('tap','.link-jump',function(){
			var $this = $(this);
			var id = $this.attr("id");
			switchPage(id);
		});
	}
	
	function switchPage(tap){
	    var array = ["cashing","cashed","cashErr"];
	    for(var i in array){
	    	if(array[i] == tap){
	    		$("ul[name='"+array[i]+"']").show();
	    	}else{
	    		$("ul[name='"+array[i]+"']").hide();
	    	}
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
                          url: rootPath +'/finance/applyCashListAjax.sc',
                          data: params,
                          success: function (data, status, xhr) {
                              if (!data.trim()) {
                                  me.noData(true);
                                  me.resetload();
                                  me.lock();
                                  $('.dropload-down').remove();
                                  M.toast('没有更多数据了');
                              } else {
                                  $('#listData').append(data);
                                  requestPage = requestPage + 1;
                                  switchPage($(".mui-active").attr("id"));
                                  me.resetload();
                              }
                          },
                          error: function (xhr, errorType, error) {
                              M.toast('刷新提现列表,错误:' + error);
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