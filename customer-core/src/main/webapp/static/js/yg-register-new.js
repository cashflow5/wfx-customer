
/**
 * create by zhongzelong
 */
var register={
  init:function(){
	 $("#setPwdNext").click(function(){register.register();});
	 $("#smsCodeBtn").click(function(){
		 register.getSmsCode() ;
	 });
	  $('#reloadImg').click( function () {
		  register.reloadImg();
      });
  }	,	
 checkPhoneEmpty:function(){
	 var phone = $("#loginName").val(),imgCode=$("#imgCode").val(), flag=true;
	 var isPhone = YG_Utils.Valid.Phone(phone).status;
      if (phone == "") {
          $("#errorMsg").html("错误提示：手机号码不能为空!");
          flag = false;
          return  flag;
      }
      else if (!isPhone) {
          $("#errorMsg").html("错误提示：手机号格式不正确!");
          flag = false;
          return  flag;
      } else{$("#errorMsg").html("");}
      if (imgCode == "") {
          $("#errorMsgPic").html("错误提示：图片验证码不能为空!");
          flag = false;
          return  flag;
      } else{
    	  $("#errorMsgPic").html("");
          return  flag;
      }
   },
   reloadImg:function(){
       document.getElementById("myimg").src = rootPath + "/getImageCode.sc?" + new Date().getTime();
   },
   checkPhone:function(){
	   $("#errorMsg").html("");
       $.ajax({url:rootPath + '/register/checkPhone.sc', 
           data: {phone:$("#loginName").val().trim()},
           dataType: 'json',//服务器返回json格式数据
           type: 'post', //HTTP请求类型
           success: function (data) {
               if (!data.success) {
                       $("#errorMsg").html(data.message);
                       return false;
               } else {
            	//  register.setSmsCode();
            	   register.checkImgCode();
               }
           },
           error: function (xhr, type, errorThrown) {
               $("#errorMsg").html('错误提示：系统异常!');
               return false;
           }
       });  
   },
   checkImgCode:function(){
	   $("#errorMsg").html("");
       $.ajax({url:rootPath + '/register/checkImageCode.sc', 
           data: {imageCode:$("#imgCode").val()},
           dataType: 'json',//服务器返回json格式数据
           type: 'post', //HTTP请求类型
           success: function (data) {
               if (!data.success) {
                       $("#errorMsgPic").html(data.message);
                       return false;
               } else {
            	  register.setSmsCode();
               }
           },
           error: function (xhr, type, errorThrown) {
               $("#errorMsgPic").html('错误提示：系统异常!');
               return false;
           }
       });
	   
   },
   getSmsCode:function(){
	   if(register.checkPhoneEmpty()==true){
		   register.checkPhone()
	   }   
   },
   setSecond:function(object){
	   var second = 60, $this = $(object);
       $this.attr('disabled', 'disabled');
       var codeSetInterval = setInterval(function () {
           if (second > 0) {
               second--;
               $this.val(second + '秒后重新获取');
           } else {
               $this.val('获取验证码');
               $this.removeAttr('disabled');
               clearInterval(codeSetInterval);
           }
       },1000);  
   },
   setSmsCode:function(){
	   $("#errorMsg0").html("");
       $.ajax({url:rootPath + '/register/sendSmsCode.sc',
           data: {phone:$("#loginName").val().trim()},
           dataType: 'json',//服务器返回json格式数据
           type: 'post', //HTTP请求类型
           success: function (data) {
               //服务器返回响应
               if (!data.success) {
                       $("#errorMsg0").html(data.message);
               } else {
                   YG_Utils.Notification.toast('验证码已发送');
                   register.setSecond("#smsCodeBtn");
               }
           },
           error: function (xhr, type, errorThrown) {
               //异常处理；
               $("#errorMsg0").html('错误提示：系统异常!');
           }
       });
   },
   checkSmsCode:function(){
	   $.ajax({url:rootPath + '/register/checkSmsCode.sc', 
           data: {phone:$("#loginName").val(),smsCode:$('#smsCode').val()},
           dataType: 'json',//服务器返回json格式数据
           type: 'post', //HTTP请求类型
           success: function (data) {
              // 服务器返回响应
               if (!data.success) {
                      $("#errorMsg0").html(data.message);
               } else {
                  // YG_Utils.Notification.toast('验证码已发送');
            	   $("#registerForm").submit();
               }
           },
           error: function (xhr, type, errorThrown) {
               //异常处理；
               $("#errorMsg0").html('错误提示：系统异常!');
           }
       }); 
	   
   },
   register:function() {
	   $(".error-msg").html('');
      // $("#errorMsg1").html("");
      // $("#errorMsg2").html("");
       var loginName = $("#loginName").val();
       var password = $("#password").val();
       var password2 = $("#password2").val();
       var smsCode = $("#smsCode").val();
       register.checkPhoneEmpty();
       if(smsCode==''){
    	   $("#errorMsg0").html("错误提示：验证码不能为空");
           return false;
       }
       if (password == "") {
           $("#errorMsg1").html("错误提示：密码不能为空");
           return false;
       }
       if (password2 == "") {
           $("#errorMsg2").html("错误提示：密码不能为空");
           return false;
       }
       if (password != password2) {
           $("#errorMsg2").html("错误提示：密码不一致");
           return false;
       }
       if (!YG_Utils.Valid.Password(password).status) {
           $("#errorMsg1").html("错误提示：请设置6-18位，数字+字母的密码");
           return false;
       }
       //$("#registerForm").submit();
       register.checkSmsCode();
   }
};
$(function(){
	register.init();
})