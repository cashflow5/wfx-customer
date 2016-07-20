/**
 * create by sqq 2016.6.29
 */
var YG_AccountInformation = (function($, M) {
	function Init(){
		userHeadReplace();
		elementClick();
		keyDownFuntion('#userName');
		keyDownFuntion('#identityCard');
		keyDownFuntion('#cardName');
		keyDownFuntion('#cardNumber');
		keyDownFuntion('#openBranch');
		//allInputChecked();
	}
	function elementClick(){
		if($('#identityCardImgZ').length>0){
			$('#identityCardImgZ').get(0).onchange = function (evt) {
				changeValue('#identityCardImgZ',evt)
			}
		}
		if($('#identityCardImgF').length>0){
			$('#identityCardImgF').get(0).onchange = function (evt) {
				changeValue('#identityCardImgF',evt)
			}
		}
		if($('.userCard .deleteimg').length>0){
			$(".deleteimg").on('tap',function(){
				var changV=$(this).parent().parent().attr('id');
            	if(changV=='cardImg0'){
            		$(this).parent().html('+<span class="f14">正面</span>');
            	}else{
            		$(this).parent().html('+<span class="f14">反面</span>');
            	}
			})
		}
		
	}
	function allInputChecked(){
		var checked=true;
		$('.yg-account-setup .account-input').each(function(index) {
			if($(this).hasClass('account-input-img')){
				var getValue=$.trim($(this).attr('value'));
			}else{
				var getValue=$.trim($(this).val());
			}
			if(getValue==''){
				checked=false;
				return false;
			}
		});
		if(checked){
			$('.acconunt-submit-no').addClass('hide');
			$('.acconunt-submit-yes').removeClass('hide');
		}else{
			$('.acconunt-submit-no').removeClass('hide');
			$('.acconunt-submit-yes').addClass('hide');
		}
	}
	function changeValue(changeV,evetnV){
		var _this=$(changeV);
		var filepath=$(changeV).val();		
		var extStart=filepath.lastIndexOf(".");
		var ext=filepath.substring(extStart,filepath.length).toUpperCase();
		if(ext!=".BMP" && ext!=".PNG" && ext!=".JPG" && ext!=".JPEG"){			
            M.toast('图片限于bmp,png,jpeg,jpg格式');
			return false;
		}	
		var file_size = $(changeV).get(0).files[0].size;
        var size = file_size / 1024;
        if (size > 10*1024) {            
            M.toast('上传的图片大小不能超过10M');
            return false;
        } 
    	var files = evetnV.target.files;
	    for(var i = 0, f; f = files[i]; i++){
	        if(!f.type.match('image.*')) continue;	        
	        var reader = new FileReader();
	        YG_Utils.Layer.show();
	        reader.onload = (function(theFile){
	        	return function(e){
	        		var img = document.createElement('img');
	                img.title = theFile.name;
	                img.src = e.target.result;  
	                _this.parent().find('.imgWrap').html(img);
	                _this.parent().find('.imgWrap').append('<a href="javascript:void(0);" class="deleteimg"></a>');
	                _this.parent().parent().find('.errorInfor').html('');
	                _this.parent().parent().find('.errorInfor').hide();
	                allInputChecked();
	                $(".deleteimg").on('tap',function(){
	                	if(changeV=='cardImg0'){
	                		$(this).parent().html('+<span class="f14">正面</span>');
	                	}else{
	                		$(this).parent().html('+<span class="f14">反面</span>');
	                	}
						
					})
					YG_Utils.Layer.closeAll();
        		}
        	})(f);
	        $(".layermbox").remove();
	        allInputChecked();
        	//reader.readAsDataURL();
        } 
	}
	function getInputValue(getValue){
		var getInputV=$.trim($(getValue).val());
		return getInputV;
	}
	function userHeadReplace(){
		M('.user-head-box').on('tap','.user-head-click',function(){
			$('#userHeadPhoto').trigger('click');
		});
		$('#account-form-submit').on('tap',function(){
			var allNumber=/^[0-9]*$/;
			var userName=getInputValue('#userName');
			var identityCard=getInputValue('#identityCard');
			var cardName=getInputValue('#cardName');
			var openBank=getInputValue('#openBank');
			var openBranch=getInputValue('#openBranch');
			var cardNumber=getInputValue('#cardNumber');
			var cardNumberTrue=$.trim($('#cardNumber').attr('data-true'));
			var identityCardTrue=$.trim($('#identityCard').attr('data-true'));
			var province=getInputValue('#province');
			var city=getInputValue('#city');
			if($('#marlboro').length==0){
				var identityCardImgZ=getInputValue('#identityCardImgZ');
				var identityCardImgF=getInputValue('#identityCardImgF');
				var identityCardImgZTrue=$.trim($('#identityCardImgZ').attr('data-true'));
				var identityCardImgFTrue=$.trim($('#identityCardImgF').attr('data-true'));
			}
			var submitNo=false;
			allNumber.lastIndex=0;
			if(userName==''){
				errorInfor('#userName','请输入您的真实姓名');
				submitNo=true;
			}else{
				if(userName.length>20){
					errorInfor('#userName','真实姓名不能超过20个字符');
					submitNo=true;
				}
			}
			if(identityCard!=identityCardTrue){
				if(identityCard=='' || identityCard.length!=18){
					errorInfor('#identityCard','请输入您的身份证号');
					submitNo=true;
				}else{
			        if(checkId.isCardNo(identityCard) === false || checkId.checkProvince(identityCard) === false || checkId.checkBirthday(identityCard) === false || checkId.checkParity(identityCard) === false){
			            //alert('请输入有效的身份信息');
			            errorInfor('#identityCard','请输入正确有效的身份证');
			            submitNo=true;
			        }
				}
			}
			if(cardName=='' || cardName!=userName){
				errorInfor('#cardName','持卡人需与身份证为同一人');
				submitNo=true;
			}
			if(openBank==''){
				errorInfor('#openBank','请选择开户银行');
				submitNo=true;
			}
			if(openBranch==''){
				errorInfor('#openBranch','请输入您的开户支行');
				submitNo=true;
			}
			if(cardNumber!=cardNumberTrue){
				if(cardNumber==''){
					errorInfor('#cardNumber','请输入您的银行卡卡号');
					submitNo=true;
				}else{
					if(!allNumber.test(cardNumber) || cardNumber.length>19){
						errorInfor('#cardNumber','请输入正确的银行卡卡号');
						submitNo=true;
					}
				}
			}
			if(province=='' || city==''){
				errorInfor('#province','请选择省份');
				submitNo=true;
			}
			if(province=='' || city==''){
				errorInfor('#province','请选择省份');
				submitNo=true;
			}
			if($('#marlboro').length==0){
				if(identityCardImgZTrue=='' && identityCardImgFTrue==''){
					if(identityCardImgZ=='' || identityCardImgF==''){
						errorInfor('#identityCardImgZ','请上传您的身份证');
						submitNo=true;
					}
				}
			}
			if(!submitNo){
				if($('#marlboro').length==0){
					if(identityCardImgZTrue!='' && identityCardImgFTrue!='' && identityCardImgZ == '' && identityCardImgF == ''){
						$('#account-form').append('<input type="hidden" name="uploadImgNoChange" value="12" />');
					}else if(identityCardImgZTrue!='' && identityCardImgZ == ''){
						$('#account-form').append('<input type="hidden" name="uploadImgNoChange" value="2" />');
					}else if(identityCardImgFTrue!='' && identityCardImgF == ''){
						$('#account-form').append('<input type="hidden" name="uploadImgNoChange" value="1" />');
					}
				}else{
					$('#account-form').append('<input type="hidden" name="uploadImgNoChange" value="12" />');
				}
				if(cardNumber==cardNumberTrue){
					$('#account-form').append('<input type="hidden" name="bankCardNoChange" value="1" />');
				}
				if(identityCard==identityCardTrue){
					$('#account-form').append('<input type="hidden" name="idCodeNoChange" value="1" />');
				}
				YG_Utils.Layer.show();
				$('#account-form').submit();
			}else{
				$('.yg-account-setup .mui-collapse').addClass('mui-active');
			}
		})
	}
	function errorInfor(errorName,errorText){
		var wrap=$(errorName).parent().find('.errorInfor');
		if(errorName=='#province' || errorName=='#identityCardImgZ'){
			wrap=$(errorName).parent().parent().find('.errorInfor');
		}
		wrap.show();
		wrap.html(errorText);
	}
	function keyDownFuntion(wrap){	
		 $(wrap).focus(function(){
		 	var thisVal=$.trim($(wrap).val());
			if(thisVal!=''){
		 		$(wrap).parent().find('.errorInfor').hide();
				$(wrap).parent().find('.errorInfor').html();
		 	}
		 	allInputChecked();
		});
		 $(wrap).blur(function(){
				var thisVal=$.trim($(wrap).val());
				if(thisVal!=''){
					$(wrap).parent().find('.errorInfor').hide();
					$(wrap).parent().find('.errorInfor').html();
				}
				allInputChecked();
			});
	}
	$(function() {
		Init();
	});
	return {
		allInputChecked:allInputChecked
	};
}(Zepto, mui));

(function(M) {  
	//					二级联动
	var cityPicker = new M.PopPicker({
		layer: 2
	});
	cityPicker.setData(cityData);
	var showCityPickerButton = document.getElementById('showCityPicker');
	var showCityPickerButtonSecond = document.getElementById('showCityPickerSecond');
	//					var cityResult = doc.getElementById('cityResult');
	showCityPickerButton.addEventListener('tap', function(event) {
		cityPicker.show(function(items) {
			showCityPickerButton.innerText = items[0].text;
			showCityPickerButtonSecond.innerText = items[1].text;
			$('#showCityPicker').parent().find('.account-input').val(items[0].text);
			$('#showCityPickerSecond').parent().find('.account-input').val(items[1].text);
			$('#showCityPicker').parent().parent().find('.errorInfor').hide();
			$('#showCityPicker').parent().parent().find('.errorInfor').html();
			YG_AccountInformation.allInputChecked();
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	showCityPickerButtonSecond.addEventListener('tap', function(event) {
		cityPicker.show(function(items) {
			showCityPickerButton.innerText = items[0].text;
			showCityPickerButtonSecond.innerText = items[1].text;
			$('#showCityPicker').parent().find('.account-input').val(items[0].text);
			$('#showCityPickerSecond').parent().find('.account-input').val(items[1].text);
			$('#showCityPicker').parent().parent().find('.errorInfor').hide();
			$('#showCityPicker').parent().parent().find('.errorInfor').html();
			YG_AccountInformation.allInputChecked();
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
})(mui);
var checkId={
    vcity:{
        11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
        21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
        33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
        42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
        51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
        63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门"
    },
    isCardNo:function(card){
        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
        if(reg.test(card) === false){
            return false;
        }
        return true;
    },
    checkProvince:function(card){
        var province = card.substr(0,2);
        if(this.vcity[province] == undefined){
            return false;
        }
        return true;
    },
    checkBirthday:function(card){
        var len = card.length;
        //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
        if(len == '15'){
            var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
            var arr_data = card.match(re_fifteen);
            var year = arr_data[2];
            var month = arr_data[3];
            var day = arr_data[4];
            var birthday = new Date('19'+year+'/'+month+'/'+day);
            return this.verifyBirthday('19'+year,month,day,birthday);
        }
        //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
        if(len == '18'){
            var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
            var arr_data = card.match(re_eighteen);
            var year = arr_data[2];
            var month = arr_data[3];
            var day = arr_data[4];
            var birthday = new Date(year+'/'+month+'/'+day);
            return this.verifyBirthday(year,month,day,birthday);
        }
        return false;
    },
    verifyBirthday:function(year,month,day,birthday){
        var now = new Date();
        var now_year = now.getFullYear();
        //年月日是否合理
        var now_year=now.getFullYear();
        var now_month=now.getMonth() + 1;
        var now_date=now.getDate();
        if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day){
            //判断年月日的范围（从1900到至今，年月日不能超过当天的日期）
            var time = now_year - year;  
            if(year<=now_year && year>=1900)  
            {  
                if(year==now_year){
                    if(month<now_month){
                        return true;
                    }else{
                        if(now_month==month){
                            if(day<=now_date){
                                return true;
                            }
                            return false;
                        }
                    }
                }
                return true;  
            }  
            return false;
        }
        return false;
    },
    checkParity:function(card){
      //15位转18位
      card = this.changeFivteenToEighteen(card);
      var len = card.length;
      if(len == '18')
      {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
        var cardTemp = 0, i, valnum; 
        for(i = 0; i < 17; i ++) 
        { 
          cardTemp += card.substr(i, 1) * arrInt[i]; 
        } 
        valnum = arrCh[cardTemp % 11]; 
        if (valnum == card.substr(17, 1)) 
        {
          return true;
        }
        return false;
      }
      return false;
    },
    changeFivteenToEighteen:function(card)
    {
      if(card.length == '15')
      {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
        var cardTemp = 0, i;   
        card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
        for(i = 0; i < 17; i ++) 
        { 
          cardTemp += card.substr(i, 1) * arrInt[i]; 
        } 
        card += arrCh[cardTemp % 11]; 
        return card;
      }
      return card;
    }
}  