/**
 * create by guoran
 */

var YG_Orders = (function($, M) {
	 var  requestPage = YG_Utils.Url.getParams()['page'];
     if (YG_Utils.Strings.IsBlank(requestPage)) {
         requestPage = 1;
     }
     
	function Init() {
		ConfirmReceipt();
		DropDownMenu();
		ConfirmTip();
		LogisticsPageLocation(); //物流跟踪页面跳转
		SelectList();
		Init_PullRefresh();
	}

	function ConfirmReceipt() {
		M('.yg-nav').on('tap', '#btnBuyNow', function() {
			location.href = "pay-order.shtml";
		});
	}

	function ConfirmTip() {
		M('.confirm-receipt-wrap').on('tap', '.btn-confirm', function(even) {
			event.preventDefault();
			M.confirm('您已确认收到货了吗？<br/>操作确认收货后货款将转账到商家。', '', function(ele) {
				if (ele.index == 1) {
					console.log('ok');
				}
			})
		})
	}  

	function LogisticsPageLocation() {
		M('.confirm-receipt-wrap').on('tap', '.logistics-btn', function() {
			location.href = 'logistics-track.shtml';
		})
	}

	function DropDownMenu() {
		var data = [{
			"id": "1",
			"static": "待付款",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_03.jpg"
		}, {
			"id": "2",
			"static": "待付款",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "3",
			"static": "待发货",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_03.jpg"
		}, {
			"id": "4",
			"static": "待发货",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "5",
			"static": "部分发货",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "6",
			"static": "部分发货",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "7",
			"static": "已发货",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "8",
			"static": "已发货",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "9",
			"static": "交易成功",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "10",
			"static": "交易成功",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "11",
			"static": "交易关闭",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}, {
			"id": "12",
			"static": "交易关闭",
			"trAmount": '128.00',
			"src": "static/images/imglist/goods-img-m_06.jpg"
		}]
		M('.mui-table-view').on('tap', '.mui-tab-item', function() {
			var $this = $(this),
				arr = [],
				divHtml,
				staticText = $this.text(),
				divStart = '<section class="yg-floor yg-shopping-cart-floor"><a href="index.shtml" class="shopping-cart-title border-top"><div class="mui-row"><div class="mui-col-sm-7 mui-col-xs-7"><i class="iconfont Red"></i> 优购XX分销总店 <i class="iconfont Gray"></i></div><div class="mui-col-sm-5 mui-col-xs-5 tright">',
				divSpan,
				divSpanEnd = '</span></div></div></a>',
				divList = '<div class="shopping-cart-list"><div class="shopping-cart-list-item yg-layout-column-left-80"><a class="yg-layout-left" href="order-detail.shtml"><img src="',
				divListCont = '" alt=""></a><div class="yg-layout-main"><div class="yg-layout-container mui-row"><div class="mui-col-sm-8 mui-col-xs-8"><a class="goods-title" href="order-detail.shtml">Belle/百丽2016春季红色时尚舒适甜美优雅漆牛皮革女单鞋... 黑色 37</a>',
				divListBtn,
				divListEnd = '</div><div class="mui-col-sm-4 mui-col-xs-4 tright"><p>&yen;189.00</p><p class="Gray">x11</p></div></div></div></div></div>',
				divEnd;
			$(data).each(function(index, ele) {
				var dataStatic = ele.static,
					dataId = ele.id,
					datahref = ele.src;
				if (dataStatic == staticText) {
					dataStatic == "待付款" ? divSpan = '<span class="Red">' : divSpan = '<span>';
					if (dataStatic == "待付款") {
						divEnd = '<div class="shopping-cart-title border-top"><div class="mui-row"><div class="mui-col-sm-5 mui-col-xs-5 lh-33">实付款：&yen;1188.00</div><div class="mui-col-sm-7 mui-col-xs-7 tright"><a class="mui-btn mui-btn-danger" href="order-detail.shtml">去支付</a></div></div></div></section>';
						divHtml = divList + datahref + divListCont + divListEnd;
					} else if (dataStatic == "待发货") {
						divListBtn = '<div class="pt10"><a class="mui-btn" href="apply-refund.shtml">退款/退货</a></div>';
						divEnd = '<div class="shopping-cart-title border-top"><div class="mui-row"><div class="mui-col-sm-5 mui-col-xs-5 lh-33">实付款：&yen;488.00</div><div class="mui-col-sm-7 mui-col-xs-7 tright"></div></div></div>'
						divHtml = divList + datahref + divListCont + divListBtn + divListEnd;
					} else if (dataStatic == "部分发货") {
						divListBtn = '<div class="pt10"><a class="mui-btn" href="return-goods.shtml">退款/退货</a></div>';
						divEnd = '<div class="shopping-cart-title border-top confirm-receipt-wrap"><div class="mui-row"><div class="mui-col-sm-5 mui-col-xs-5 lh-33">实付款：&yen;1188.00</div><div class="mui-col-sm-7 mui-col-xs-7 tright"><a class="mui-btn logistics-btn" href="javascript:void(0);">物流跟踪</a> <a class="mui-btn mui-btn-danger btn-confirm" href="javascript:;">确认收货</a></div></div></div>',
							divHtml = divList + datahref + divListCont + divListBtn + divListEnd;
					} else if (dataStatic == "已发货") {
						divListBtn = '<div class="pt10"><a class="mui-btn" href="return-goods.shtml">退款/退货</a></div>';
						divEnd = '<div class="shopping-cart-title border-top confirm-receipt-wrap"><div class="mui-row"><div class="mui-col-sm-5 mui-col-xs-5 lh-33">实付款：&yen;1188.00</div><div class="mui-col-sm-7 mui-col-xs-7 tright"><a class="mui-btn logistics-btn" href="javscript:void(0);">物流跟踪</a> <a class="mui-btn mui-btn-danger btn-confirm" href="javascript:;">确认收货</a></div></div></div>',
							divHtml = divList + datahref + divListCont + divListBtn + divListEnd;
					} else if (dataStatic == "交易成功") {
						divListBtn = '<div class="pt10"><a class="mui-btn" href="order-detail-delivered.shtml">退款/退货</a></div>';
						divEnd = '<div class="shopping-cart-title border-top"><div class="mui-row"><div class="mui-col-sm-5 mui-col-xs-5 lh-33">实付款：&yen;488.00</div><div class="mui-col-sm-7 mui-col-xs-7 tright"></div></div></div>',
							divHtml = divList + datahref + divListCont + divListBtn + divListEnd;
					} else if (dataStatic == "交易关闭") {
						divEnd = '<div class="shopping-cart-title border-top"><div class="mui-row"><div class="mui-col-sm-5 mui-col-xs-5 lh-33">实付款：&yen;488.00</div><div class="mui-col-sm-7 mui-col-xs-7 tright"></div></div></div>',
							divHtml = divList + datahref + divListCont + divListEnd;
					}
					arr.push(divHtml);
					divHtml = divStart + divSpan + dataStatic + divSpanEnd + arr.join("") + divEnd;
				}
				$("#myListData").html(divHtml);
				ConfirmTip(); //确认收货提示框
				LogisticsPageLocation(); //物流页面跳转
			})
			mui('.mui-popover').popover('toggle'); //关闭弹出层
		})
		mui('.mui-scroll-wrapper').scroll(); //模态框可以往上滚动
	}

	function GetOrders(data) {
		$.ajax({
			url: rootPath + '/ufans/order/ajax.sc',
			type: 'get',
			data: data,
			dataType: 'string',
			timeout:10000,
			error: function(e){
				//
			//	$('#myListData').append(e);
			//	alert(e);  
			},
			success: function(data) {
				 if (data == '') {
					  $('#myListDatahtml').html("<div class='yg-pro-detail-nomore pt10 pb10' style='text-align:center;'><img src='http://"+document.domain+"/static/images/prodetail/nothing.png' style='width:30px'><br>没有相关信息</div>");  
                 } else {
                     $('#myListDatahtml').html(data);  
                 }
				
			}
		})  
	}  
	function SelectList() {
		M('.yg-header').on('tap', '.mui-title,li', function(e) {
			var $this = $(this);
			switch (this.tagName.toLowerCase()) {
				case 'a':
					console.log('a');
					break;
				case 'li':
					$this.parent().find('.active').removeClass('active').find('.iconfont').appendTo($this);
					$this.addClass('active');
					$('span', '#orderSelect').html($('span', this).text()+"<i class='iconfont f14'>&#xe60c;</i>");
					$('#orderLevel').val($this.data('value'));
					$('#status').val($('.order-type-list a.active').data('value'));
//					$('.order-type-list a:first').addClass('active').siblings().removeClass('active');
					GetOrders({level: $('#orderLevel').val(), status: $('.order-type-list a.active').data('value'),page:1,pageSize:10});	
					requestPage = 0;
					Init_PullRefresh();
					break;
				default:
					break;
			}
			$('.my-order-collapse').toggle();
		});
		M('.order-type-list').on('tap', 'a', function() {
			var $this = $(this),
				value = $this.data('value');
				$this.addClass('active').siblings().removeClass('active');
				$('#status').val($this.data('value'));
				GetOrders({level: $('#orderLevel').val(), status: $this.data('value'),page:1,pageSize:10});
				requestPage = 0;
				Init_PullRefresh();
		})
	}
	 // 下拉刷新页面
    function Init_PullRefresh() {
        $('.mui-content').dropload({
                  scrollArea: window,
	               loadDownFn: function (me) {
	            	   var params={};
	                   params['page'] = requestPage + 1;
	                   params['pageSize'] =10;
	                   params['level'] =$('#orderLevel').val();
	                   params['status'] =$('#status').val();
	                   $.ajax({
	                              type: 'get',
	                              url: rootPath + '/ufans/order/ajax.sc',
	                              data: params,
	                              success: function (data, status, xhr) {
	                                  if (data == '') {
	                                	  if( params['page']==1){
	                                		 $('#myListDatahtml').html(" <div class='yg-pro-detail-nomore pt10 pb10' style='text-align:center;'><img src='http://"+document.domain+"/static/images/prodetail/nothing.png' style='width:30px'><br>没有相关信息</div>");   
	                                	  }
	                                     me.noData(true);
	                                     me.resetload();
	                                     me.lock();                                             				  
	                                   $('.dropload-down').remove();
	                                  } else {
	                                	  if( params['page']==1){
	                                		  $('#myListDatahtml').html(data);   
	                                	  }
	                                	  else
	                                	  {$('#myListDatahtml').append(data);}
	                                      requestPage = requestPage + 1;
	                                     me.resetload(); 
	                                   //  me.unlock(); 
	                                  }
	                              },
	                              error: function (xhr, errorType, error) {
	                                  M.toast('刷新订单失败,错误:' + error);
	                                  me.noData(true);
	                                  me.resetload();
	                                 // me.unlock(); 
	                              }
	                          });
	                  // me.unlock();
	               }
              });
        $(".dropload-refresh").hide();  
    }

	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));