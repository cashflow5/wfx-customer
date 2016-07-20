/**
 * create by zheng.qq
 */
var YG_Common_Foot = (function ($, M) {
    function Init() {
    	checkIsShowTip();
    	$(".viewport").delegate('#closeBefans','tap',function(){closeTip(this) });
    	if(global.memberId!=''){
//    		checkUserIsDistributor();
    		
        	if(running_environment == '1'){
        		checkUserIsSubscribe();
        	}
        	$(".viewport").delegate('#btnFocus','tap',function(){FocusWx()});
        	
    	}
    }  
    /**
     * 判断用户是否关注微信公共号
     */
    function checkUserIsSubscribe() {
    	$.ajax({
	      	type:'get',
	      	url: rootPath + "/weixin/checkUserIsSubscribe.sc",
	  	  	data: {'memberId': global.memberId},
	      	dataType:'json',
	      	async:false,
	      	success:function(data){
	      		if(!data){
	      			//显示微信公共号引导控件。
                     var showHtml='<div class="yg-trip-message-box focus-wx fixed"><div class="message-item yg-trip-message-box-btns"><img class="logo" src="'+rootPath+'/static/images/logo.png"><span class="ml20">成为优粉，玩转分享时尚生活</span> </div><div class="message-item"><a id="btnFocus" class="btn-go">关注公众号</a></div></div>';
                   $('.viewport').prepend(showHtml);
	      		}
	      	}
	   	})
    } 
    function FocusWx() {
			layer.open({
				type: 1,
				style: 'width:190px;',
				content: '<div class="tcenter pd10"><img class="tvcenter" src="'+rootPath+'/static/images/qr-code.jpg" style="width:170;height:173px;"/><div class="pt10"> 长按识别图中二维码，关注优购微零售</div></div>'
			});
	}
    /**
     * 判断用户是不是分销商
     */
    function checkUserIsDistributor() {
    	$.ajax({
          	type:'get',
          	url: rootPath + "/checkIsSeller.sc",
      	  	data:{'memberId': global.memberId},
          	dataType:'json',
          	async:false,
          	success:function(data){
          		if(data.success==false){
          			//显示成为优粉引导控件。
          				var showHtml='<div class="yg-trip-message-box trip-normal fixed" style="border-top: 1px dashed #56575a"><div class="message-item yg-trip-message-box-btns">';
          				showHtml+='<span class="ml20">购买一单，即可代理整个商城>></span></div><div class="message-item"><a href="javascript:;" class="btn-close" id="closeBefans"></a></div></div>';
          			$('.viewport').prepend(showHtml);
          		}
          	}
       	})
       
    }  
    
    /**
     * 是否显示Tip提示
     */
    function checkIsShowTip() {
    	if(global.tip_status == '1'){
    			var showHtml='<div class="yg-trip-message-box trip-normal fixed" style="border-top: 1px dashed #56575a"><div class="message-item yg-trip-message-box-btns">';
    			showHtml+='<span class="ml20"><a href='+global.ufans_url+' style="color:#fff">购买一单，即可代理整个商城>></a></span></div><div class="message-item"><a href="javascript:;" class="btn-close" id="closeBefans"></a></div></div>';
 			$('.viewport').prepend(showHtml);
    	}
    }  
    
    /**
     * 关闭Tip提示
     */
    function closeTip(tip) {
    	$(tip).parents('.yg-trip-message-box').remove();
    	$.ajax({
          	type:'get',
          	url: rootPath + "/modityTipStatus.sc",
      	  	data:{'status': 0},
          	dataType:'json',
          	success:function(data){
          		
          	}
       	})
    } 

	$(function() {
		Init();
	});

	return {
	};
}(Zepto, mui));
