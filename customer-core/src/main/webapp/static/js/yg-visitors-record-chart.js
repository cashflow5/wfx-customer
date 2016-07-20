/**
 * create by guoran
 */
var YG_Visitors_Record_Chart = (function($, M) {
	function Init() {
		bindChart(data,categories);
		$(".highcharts-legend").css('display','none');
		$("#loadVisitorList").click(function(){
			loadVisitorList();
		});
	}
	
	/**
	 * 异步获取指定日期
	 */
	function getVisitorResourceCountAjax(date){
		$.ajax({
            type: 'post',
            url: '/ufans/visitor/getVisitorSourceCountAjax.sc',
            data: {'date':date},
            dataType:'json',
            success: function (data, status, xhr) {
                if(data){
                	$("#weixinCountSpan").text(data.weixinCount);
                	$("#otherCountSpan").text(data.otherCount);
                }
            },
            error: function (xhr, errorType, error) {
                M.toast('刷新访客来源,错误:' + error);
            }
        });
	}
	
	function loadVisitorList(){
		var date = $("#date").val();
		$("#visitorForm").submit();		
	}

	/**
	 * 今日交易统计页面js
	 */
	function bindChart(data,categories) {
		
		$('#tody_data').highcharts({
			chart:{
				height:180,
				marginLeft:10,
				marginRight:10
			},
			title: {
				text: '',
				x: -20 //center
			},
			subtitle: {
				text: '',
				x: -20
			},
			xAxis: {
				gridLineWidth: 1,
				gridLineColor: '#f0f0f0',
				lineWidth: 1,
				categories:categories, //['0', '1', '2', '3', '4', '5', '6'],
				tickInterval: 1,
				minorTickInterval: '',
				lineColor: '#d2e7fc',
				tickWidth: 0,
				tickPosition: 'inside'
			},
			yAxis: {
				labels:{
                     enabled: false
                 },
				title: {
					text: '' //y轴副标题
				},
				tickInterval: 10, //设置刻度隔几个值显示
				gridLineWidth: 0,
				gridLineColor: '#f0f0f0',
				lineColor: '#f0f0f0',
				lineWidth: 0,
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}],
				min: 0,
				startOnTick: true
			},
			tooltip: {
				valueSuffix: '人'
			},
			legend: {
				layout: 'horizontal',
				align: 'center',
				verticalAlign: 'bottom',
				borderWidth: 0
			},
			credits: {
				enabled: false,
				text: "m.yougou.net",
				href: ""
			},
			onClick: function(data, e){
				var date = data.date;
				$("#date").val(date);
				getVisitorResourceCountAjax(date);
				//alert(data.date);
				/*debugger
					console.log('haha')*/
			},
			series: [{
				name: ' ',
				data:data
				/*data: [{
					name: '6月1日',
					y: 81
				}, {
					name: '6月2日',
					y: 48
				}, {
					name: '6月3日',
					y: 355
				}, {
					name: '6月4日',
					y: 523
				}, {
					name: '6月5日',
					y: 635
				}, {
					name: '6月6日',
					y: 1112
				}, {
					name: '6月7日',
					y: 1181
				}, ]*/
			}]
});
	}

	$(function() {
		Init();
	});

	return {};
}(jQuery, mui));