/**
 * create by lijunfang
 */
var YG_RefundAfterSale = (function ($, M) {
    var requestPage;

    function Init() {
        DropDownMenu();
        Init_ErrorMsg();
        if($(".yg-shopping-cart-floor").length>9)
          {
             Init_PullRefresh();
          }
        setStatue();
        M('.yg-header').on('tap','.mui-pull-left',function(){
        	window.location.href=$(this).attr("href");
        }); 
        M('.yg-header').on('tap','.mui-title',function(){
        	mui('#middlePopover').popover('toggle');//关闭弹出层
        });
    }
  function setStatue(){
	  var text="退款/售后",num=0;
	  var status=YG_Utils.Url.getParams()['status'];
	  if (YG_Utils.Strings.IsBlank(status)) {
		  status = "退款/售后";
		  num=0
      }
	  else if(status=="APPLYING"){
		  status = "退款申请中";
		  num=1;
	  }else if(status=="SUCCESS_REFUND"){
		  status = "退款成功";
		  num=2
	  }else if(status=="PENDING_DELIVERD"){
		  status = "待商家确认收货";
		  num=3
	  }else if(status=="REJECT_REFUND"){
		  status = "卖家拒绝退款";
		  num=4
	  }else if(status=="CLOSE_REFUND"){
		  status = "退款关闭";
		  num=5
	  }
	  $(".mui-table-view-cell").eq(num).addClass("active");
	  $('span', '#orderSelect').html(status+"<i class='iconfont f14'>&#xe60c;</i>");
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
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath + '/order/myrefunds/ajax.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (data == '') {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                          } else {
                                                              $('#myListData').append(data);
                                                              requestPage = requestPage + 1;
                                                              me.resetload();
                                                          }
                                                      },
                                                      error: function (xhr, errorType, error) {
                                                          M.toast('刷新订单失败,错误:' + error);
                                                          me.resetload();
                                                      }
                                                  });
                                       }
                                   });
        $(".dropload-refresh").hide();  
    }


    // 显示错误提示信息
    function Init_ErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
    }

    function DropDownMenu() {
        $('.mui-table-view').on('tap', '.mui-table-view-cell', function () {
            var $this = $(this).find('a');
            var staticText = $this.text();
            if (staticText == '全部') {
                location.href = rootPath + '/order/myrefunds.sc';
            }
            if (staticText == '退款申请中') {
                location.href = rootPath + '/order/myrefunds.sc?status=APPLYING';
            }
            else if (staticText == '退款成功') {
                location.href = rootPath + '/order/myrefunds.sc?status=SUCCESS_REFUND';
            }
            else if (staticText == '待商家确认收货') {
                location.href = rootPath + '/order/myrefunds.sc?status=PENDING_DELIVERD';
            }
            else if (staticText == '卖家拒绝退款') {
                location.href = rootPath + '/order/myrefunds.sc?status=REJECT_REFUND';
            }
            else if (staticText == '退款关闭') {
                location.href = rootPath + '/order/myrefunds.sc?status=CLOSE_REFUND';
            }
            mui('#middlePopover').popover('toggle');//关闭弹出层
        });
        mui('.mui-scroll-wrapper').scroll();//模态框可以往上滚动
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));