/**
 * create by guoran
 */
var YG_Orders = (function ($, M) {
    var requestPage;
    var leve=$("#level").val();
    var status=$("#status").val();
    if(status==undefined){status=null}
    function Init() {
        DropDownMenu();
        Init_PullRefresh();
        Init_ErrorMsg();
        titleShow();
      //  switchStatus();
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
                                       loadDownFn: function (me) {
                                           var params = YG_Utils.Url.getParams();
                                           params['page'] = requestPage + 1;
                                           params['pageSize'] =10;
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath + '/ufans/order/ajax.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (data == '') {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                          } else {
                                                              $('#myListData').append(data);
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

    function DropDownMenu() {
        $('#myListData').on('tap', 'a', function () {
            var $this = $(this),sta='';
            var staticText = $this.text();
            if (staticText == "待付款") {
            	sta="2";
                //location.href = rootPath + '/ufans/order/list.sc?status=WAIT_PAY';
            } else if (staticText == "待发货") {
            	sta="3";
               // location.href = rootPath + '/ufans/order/list.sc?status=WAIT_PAY';
//            } else if (staticText == "部分发货") {
//            	sta="PART_DELIVERED";
            } else if (staticText == "待收货") {
            	sta="5";
                // location.href = rootPath + '/ufans/order/list.sc?status=DELIVERED';
             }else if (staticText == "交易成功") {
            	sta="6";
               // location.href = rootPath + '/ufans/order/list.sc?status=TRADE_SUCCESS';
            }
            locationload(leve,sta)
//            else if (staticText == "交易关闭") {
//                location.href = rootPath + '/ufans/order/list.sc?status=TRADE_CLOSED';
//            }
           // mui('.mui-popover').popover('toggle');//关闭弹出层
        });
       // mui('.mui-scroll-wrapper').scroll();//模态框可以往上滚动
        $('.mui-table-view').on('tap', 'li', function(){
		   // $('.my-order-h').hide();
		    var lev=$(this).attr("lev");
		    locationload(lev,status)
	    })  
    }
   function locationload(leve,statu){
	   location.href = rootPath + '/ufans/order/list.sc?level='+leve+'&status='+statu; 
   }
    function titleShow(){
    	$(".mui-title").click(function(){
    		var show=$(".mui-collapse-content").css("display");
    		if(show=="block"){
    			$(".mui-collapse-content").hide().animate({height:"0px",opacity:"0",callback:function(){$(".mui-collapse-content").hide();}});
    		}
    		else{
    			$(".mui-collapse-content").show().animate({height:"375px",opacity:"1",callback:function(){$(".mui-collapse-content").show();}});}
    	})
    	
    	
    }
    $(function () {
    	 $('.my-order-h').hide();
	  
        Init();
    });

    return {
        pullFresh: function () {
            pullfresh();
        }
    };
}(Zepto, mui));