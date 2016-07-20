var YG_list = (function($, M) {
    function Init(){
        scrollAndClick();
        filterAsideSlider();
        Init_PullRefresh();
        LazyImage();
    }
    var scrollYes=true;
    var imgLazyloadApi = null;
    var requestPage;
    var numberIndex=0;
    var formSubmit=true;
    function scrollAndClick(){
        // if($('#yg-pro-detail-filter').length>0){
        //  $('.yg-product-list-new .yg-pro-detail-content').css('top','118px');
        // }
        //清除按钮筛选项
        $('.yg-pro-detail-filter-clear').on('tap',function(){
            $(this).parent().remove();
            clearValue();
            $('.yg-product-list-new .yg-pro-detail-content').removeAttr('style');
            $('#yg-list-filter').submit();
            
        })
        $('.jumpLink').on('tap', function() {
            var hrefLink=$(this).attr('href');
            console.log(1)
            window.location.href=hrefLink;
        });
        //筛选提交
        $('#yg-img-sb-tab').on('tap',function(){
            $(this).toggleClass('yg-img-sb-tab-big-img');
            $('#yg-pro-detail-view').toggleClass('yg-pro-detail-view-two');
            var cs = $('.commStyle').val();
            if(cs == 'click'){
            	$('.commStyle').val('');
            }else{
            	$('.commStyle').val('click');
            }
        })
        if(commStyle == 'click'){
        	$(this).toggleClass('yg-img-sb-tab-big-img');
            $('#yg-pro-detail-view').toggleClass('yg-pro-detail-view-two');
        }
        //分类选择
        $('.yg-filter-aside-ul li').on('tap',function(index){
            numberIndex=$(this).index();
            var _this=$(this);
            var showCat = _this.find('a').attr('data-name');
            $('#showCat').text(showCat);
            var getDateId=_this.find('a').attr('date-id');
            var getInputValue=$.trim($(this).find('.currentInput').val());
            var secondGetdate=$('.yg-filter-aside-ul-detail-wrap .yg-filter-aside-ul-detail').eq(numberIndex);
            var shopId=$("#shopId_").val();
            var catId = $('#catId').val();
            var brandNo = $('#brandNo').val();
            if(secondGetdate.find('li').length===0){
                $.ajax({
                    type: 'get',
                    url: rootPath+'/'+shopId+'/property.sc',
                    data: {"type":getDateId,"catId":catId,"brandNo":brandNo},
                    beforeSend: function () {
                        YG_Utils.Layer.show();
                    },
                    complete: function () {
                        YG_Utils.Layer.closeAll();
                    },
                    success: function (data, status, xhr) {
                        secondGetdate.append(data);
                        if(getInputValue!=''){
                            var arrayValue=getInputValue.split(',');
                            secondGetdate.find('li').each(function() {
                                var getSecondValue=$(this).attr('date-id');
                                for(var i=0;i<arrayValue.length;i++){
                                    if(getSecondValue==arrayValue[i]){
                                        $(this).addClass('yg-filter-aside-detail-active');
                                    }
                                }
                            });
                        }
                        if(_this.find('.currentInput').val()==''){
                            secondGetdate.find('li').eq(0).addClass('yg-filter-aside-detail-active');
                        }else{
                            secondGetdate.find('li').eq(0).removeClass('yg-filter-aside-detail-active');
                        }
                        _this.addClass('yg-filter-aside-li-select').siblings().removeClass('yg-filter-aside-li-select');
                        $('.yg-filter-aside-ul-detail-wrap').find('.yg-filter-aside-ul-detail').hide().eq(numberIndex).show();
                        filterDetailDisblock();
                        fenClick();
                    },
                    error: function (xhr, errorType, error) {
                        M.toast('刷新商品失败,错误:' + error);
                    }
                });
            }else{
                if(getInputValue!=''){
                    var arrayValue=getInputValue.split(',');
                    secondGetdate.find('li').each(function() {
                        var getSecondValue=$(this).attr('date-id');
                        for(var i=0;i<arrayValue.length;i++){
                            if(getSecondValue==arrayValue[i]){
                                $(this).addClass('yg-filter-aside-detail-active');
                            }
                        }
                    });
                }
                if(_this.find('.currentInput').val()==''){
                    secondGetdate.find('li').eq(0).addClass('yg-filter-aside-detail-active');
                }else{
                    secondGetdate.find('li').eq(0).removeClass('yg-filter-aside-detail-active');
                }
                _this.addClass('yg-filter-aside-li-select').siblings().removeClass('yg-filter-aside-li-select');
                $('.yg-filter-aside-ul-detail-wrap').find('.yg-filter-aside-ul-detail').hide().eq(numberIndex).show();
                filterDetailDisblock();
            }
            M('#offCanvasSideScroll').scroll().scrollTo(0,0,0);
        })
        //分类后退
        $('#yg-aside-filter-back').on('tap',function(){
            if($('.yg-filter-aside-ul').find('.yg-filter-aside-li-select .currentInput').val()==''){
                $('.yg-filter-aside-ul li').eq(numberIndex).removeClass('yg-filter-aside-li-select');
            }
            filterDisblock();
        })
        //分类下选择
        fenClick();
        //清空筛选
        $('#mui-btn-outlined').on('tap',function(){
            clearValue();
        })
        var getDateName=[];
        var getDateValue=[];
        //分类提交
        var getThisId='';
        var getThisName='';
        //var getThisInputName='';
        $('.yg-filter-aside-ul li').each(function(index) {
            var _getThisId=$(this).find('.currentInput').val();
            var _getThisName=$(this).find('.currentInputName').val();
            var _getThisInputName=$(this).find('.currentInput').attr('name');
            
            //解决后退时value值不正确的问题
            var idTrue = $(this).find('.currentInput').attr('data-true');
            var nameTrue = $(this).find('.currentInputName').attr('data-true');
            if(idTrue != _getThisId){
            	$(this).find('.currentInput').val(idTrue);
            	$(this).find('.currentInputName').val(nameTrue);
            	_getThisId = idTrue;
            	_getThisName = nameTrue;
            }
            
            if(_getThisId!='' && _getThisName!=''){
                $(this).find('em').html(_getThisName);
                $(this).addClass('yg-filter-aside-li-shover');
                getThisId=getThisId=='' ? _getThisId : getThisId+','+_getThisId;
                getThisName=getThisName=='' ? _getThisName : getThisName+','+_getThisName;
                //getThisInputName=getThisInputName=='' ? _getThisInputName : getThisInputName+','+_getThisInputName;
            }
            
        });
        if(getThisId!='' && getThisName!=''){
            getThisId=getThisId.split(',');
            getThisName=getThisName.split(',');
            //getThisInputName=getThisInputName.split(',');
            var inlineHtml=[];
            for(var j=0;j<getThisId.length;j++){
                inlineHtml.push('<a class="f12" data-name="'+getThisName[j]+'" data-id="'+getThisId[j]+'" href="javascript:;">'+getThisName[j]+'<em>X</em></a>');
            }
            $('#yg-pro-detail-filter').find('.mui-scroll').html(inlineHtml.join(''));
            $('#yg-pro-detail-filter').show();
            $('.yg-product-list-new .yg-pro-detail-content').css('top','118px');
            inlineBlock();
        }
        $('#mui-btn-comfig-classify').on('tap',function(){
            getDate=[];
            getDateValue=[];
            var thisWrap=$('.yg-filter-aside-ul-detail').eq(numberIndex);
            var thisUl=$('.yg-filter-aside-ul');
            if(thisWrap.find('.yg-filter-aside-detail-active').length>0){
                thisWrap.find('li').each(function(){
                    if($(this).hasClass('yg-filter-aside-detail-active')){
                        getDate.push($(this).attr('date-name'));
                        getDateValue.push($(this).attr('date-id'))
                    }
                })
                thisUl.find('.yg-filter-aside-li-select').find('em').html(getDate.join(','));
                thisUl.find('.yg-filter-aside-li-select').find('.currentInput').val(getDateValue.join(','));
                thisUl.find('.yg-filter-aside-li-select').find('.currentInputName').val(getDate.join(','));
                if(getDate.length==1 && getDate[0]=='全部'){
                    thisUl.find('li').eq(numberIndex).removeClass('yg-filter-aside-li-select');
                    thisUl.find('li').eq(numberIndex).removeClass('yg-filter-aside-li-shover');
                }else{
                    thisUl.find('.yg-filter-aside-li-select').addClass('yg-filter-aside-li-shover');
                }
                //console.log(getDateValue);                
            }
            filterDisblock();
            
        })
        //筛选提交
        $('#mui-btn-comfig-form').on('tap',function(){
            if(formSubmit){
                $("#yg-list-filter").append(filterLabel());
                //console.log($("#yg-list-filter").serialize());
                YG_Utils.Layer.show();
                $('#yg-list-filter').submit();
                formSubmit=false;
            }
            $("#offCanvasSideScroll .mui-scroll").css({transform: 'translate3d(0px, 0px, 0px) translateZ(0px); transition-duration: 0ms; transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1)'})
            //window.location.href=window.location.href;
        })
        //新品人气销量筛选按钮
        $('.yg-pro-detail-nav').on('tap','a',function(){
            var getAction=$('#yg-list-filter').attr('action');
            var ygProNav=$.trim($(this).html());
            var form;
            if(formSubmit){
                if(ygProNav.indexOf('销量')>=0){
                    $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price.png');
                    form = $('<input type="hidden" name="saleQuantityOrder" value="desc" />');
                }else if(ygProNav.indexOf('新品')>=0){
                    $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price.png');
                    form = $('<input type="hidden" name="updateOrder" value="desc" />');
                }else if(ygProNav==='人气'){
                    $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price.png');
                    form = $('<input type="hidden" name="popularity" value="desc" />');
                }else if(ygProNav.indexOf('价格')>=0){
                    var getImgSrc=$('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src');
                    if(getImgSrc.indexOf('down')>=0 || getImgSrc.indexOf('up')>=0){
                        if(getImgSrc.indexOf('down')>=0){
                            $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price-up.png');
                            form = $('<input type="hidden" name="priceOrder" value="asc" />');
                        }else{
                            $(this).find('img').attr('src',rootPath+'/static/images/prodetail/yg-price-down.png');
                            form = $('<input type="hidden" name="priceOrder" value="desc" />');
                        }
                    }else{
                        $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price-up.png');
                        form = $('<input type="hidden" name="priceOrder" value="asc" />');
                    }
                }
                if(ygProNav.indexOf('筛选')<0){
                    $(this).addClass('mui-activeNew').siblings().removeClass('mui-activeNew');
                    $('.yg-filter-aside-ul .yg-filter-aside-li-shover').each(function(){
                        var getTrueData=$(this).find('.currentInput').attr('data-true');
                        var getTrueDataName=$(this).find('.currentInputName').attr('data-true');
                        $(this).find('.currentInput').val(getTrueData);
                        $(this).find('.currentInputName').val(getTrueDataName);
                    })
                    // $("#yg-list-filter-true").append(form);
                    // $('#yg-list-filter-true').submit();
                    $('#yg-list-filter').append(filterLabel());
                    //console.log($('#yg-list-filter').serialize());
                    $('#yg-list-filter').submit();
                    formSubmit=false;
                }
            }
            
        })
        $('.yg-shop-card-fixed').on('tap',function(){
            resetLink($(this));
        })
        listClick();
        inlineBlock();
    }
    function clearValue(){
        $('.yg-filter-aside-ul li').each(function(index){
            $(this).removeClass('yg-filter-aside-li-select').removeClass('yg-filter-aside-li-shover').find('em').html('全部');
            $(this).find('.currentInput').val('');
            $(this).find('.currentInputName').val('');
            $('.yg-filter-aside-ul-detail').eq(index).find('li').each(function(index){
                if(index===0){
                    $(this).addClass('yg-filter-aside-detail-active').siblings().removeClass('yg-filter-aside-detail-active');
                    return false;
                }
                
            });
        })
    }
    function filterLabel(){
        var form;
        $('.yg-pro-detail-nav a').each(function(index){
            if($(this).hasClass('mui-activeNew')){
                var ygProNav=$.trim($(this).html());
                if(ygProNav.indexOf('销量')>=0){
                    $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price.png');
                    form = $('<input type="hidden" name="saleQuantityOrder" value="desc" />');
                }else if(ygProNav.indexOf('新品')>=0){
                    $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price.png');
                    form = $('<input type="hidden" name="updateOrder" value="desc" />');
                }else if(ygProNav==='人气'){
                    $('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src',rootPath+'/static/images/prodetail/yg-price.png');
                    form = $('<input type="hidden" name="popularity" value="desc" />');
                }else if(ygProNav.indexOf('价格')>=0){
                    var getImgSrc=$('.yg-pro-detail-nav .yg-pro-detail-price').find('img').attr('src');
                    if(getImgSrc.indexOf('down')>=0 || getImgSrc.indexOf('up')>=0){
                        if(getImgSrc.indexOf('down')>=0){
                            form = $('<input type="hidden" name="priceOrder" value="desc" />');
                        }else{
                            form = $('<input type="hidden" name="priceOrder" value="asc" />');
                        }
                    }
                }
            }
        });
        return form; 
    }
    function inlineBlock(){
        //删除筛选项
        $('.yg-pro-detail-filter em').off('tap');
        $('.yg-pro-detail-filter em').on('tap',function(){
            if(formSubmit){
                var parentWrap=$(this).parent();
                parentWrap.remove();
                var delId=parentWrap.attr('data-id');
                var delName=parentWrap.attr('data-name');
                $('.yg-filter-aside-ul .yg-filter-aside-li-shover').each(function() {
                    var getTrueData=$(this).find('.currentInput').attr('data-true');
                    var getTrueDataName=$(this).find('.currentInputName').attr('data-true');
                    if(getTrueData.indexOf(','+delId)>=0){
                        getTrueData=getTrueData.replace(','+delId,'');
                        getTrueDataName=getTrueDataName.replace(','+delName,'');
                    }else if(getTrueData.indexOf(delId+',')>=0){
                        getTrueData=getTrueData.replace(delId+',','');
                        getTrueDataName=getTrueDataName.replace(delName+',','');
                    }else if(getTrueData.indexOf(delId)>=0){
                        getTrueData=getTrueData.replace(delId,'');
                        getTrueDataName=getTrueDataName.replace(delName,'');
                    }
                    $(this).find('.currentInput').val(getTrueData);
                    $(this).find('.currentInputName').val(getTrueDataName);
                });
                if($('.yg-pro-detail-filter-w a').length<=0){
                    $('.yg-pro-detail-filter').remove();
                    $('.yg-product-list-new .yg-pro-detail-content').removeAttr('style');
                }
                $('#yg-list-filter').append(filterLabel());
                //console.log($('#yg-list-filter').serialize());
                $('#yg-list-filter').submit();
                formSubmit=false;
            }
        })
    }
    function listClick(){
        $('#yg-pro-detail-view li a').off('off');
        $('#yg-pro-detail-view li a').on('tap',function(){
            resetLink($(this));
        })
    }
    function fenClick(){
        $('.yg-filter-aside-ul-detail li').off('tap');
        $('.yg-filter-aside-ul-detail li').on('tap',function(){
            var snumberIndex=$(this).index();
            var parentW=$(this).parent();
            if(snumberIndex===0){
                parentW.find('li').removeClass('yg-filter-aside-detail-active');
                parentW.find('li').eq(0).addClass('yg-filter-aside-detail-active');
            }else{
                parentW.find('li').eq(0).removeClass('yg-filter-aside-detail-active');
                $(this).toggleClass('yg-filter-aside-detail-active');
                if(parentW.find('.yg-filter-aside-detail-active').length==0){
                    parentW.find('li').eq(0).addClass('yg-filter-aside-detail-active');
                }
            }
        })
    }
    function filterDisblock(){
        $('.yg-filter-aside-ul').show();
        $('.yg-aside-title-one').show();
        $('.yg-filter-aside-ul-detail-wrap').hide();
        $('.yg-aside-title-two').hide();
        M('#offCanvasSideScroll').scroll().scrollTo(0,0,0);
    }
    function filterDetailDisblock(){
        $('.yg-filter-aside-ul').hide();
        $('.yg-aside-title-one').hide();
        $('.yg-filter-aside-ul-detail-wrap').show();
        $('.yg-aside-title-two').show();
    }
    function resetLink(resetLinkA){
        var getLink=resetLinkA.attr('href');
        window.location.href=getLink;
    }
    function filterAsideSlider(){
        M.init({
            swipeBack: false,
        });
        //侧滑容器父节点
        var offCanvasWrapper = M('#offCanvasWrapper');
         //主界面容器
        var offCanvasInner = offCanvasWrapper[0].querySelector('.mui-inner-wrap');
         //菜单容器
        var offCanvasSide = document.getElementById("offCanvasSide");
         //移动效果是否为整体移动
        var moveTogether = false;
         //侧滑容器的class列表，增加.mui-slide-in即可实现菜单移动、主界面不动的效果；
        var classList = offCanvasWrapper[0].classList;
         //变换侧滑动画移动效果；
        offCanvasSide.classList.remove('mui-transitioning');
        offCanvasSide.setAttribute('style', 'width:292px;');
        classList.add('mui-slide-in');
        offCanvasWrapper.offCanvas().refresh();
        document.getElementById('offCanvasShow').addEventListener('tap', function() {
            offCanvasWrapper.offCanvas('show');
        });
        //offCanvasWrapper.offCanvas('show');
        document.getElementById('offCanvasHide').addEventListener('tap', function() {
            offCanvasWrapper.offCanvas('close');
        });
         //主界面和侧滑菜单界面均支持区域滚动；
        M('#offCanvasSideScroll').scroll();
        M('#offCanvasContentScroll').scroll();
        M('#yg-pro-detail-filter-w').scroll({
            scrollX: true,
            scrollY: false,
            indicators: false
        });
        //实现ios平台的侧滑关闭页面；
        if (M.os.plus && M.os.ios) {
            offCanvasWrapper[0].addEventListener('shown', function(e) { //菜单显示完成事件
                plus.webview.currentWebview().setStyle({
                    'popGesture': 'none'
                });
            });
            offCanvasWrapper[0].addEventListener('hidden', function(e) { //菜单关闭完成事件
                plus.webview.currentWebview().setStyle({
                    'popGesture': 'close'
                });
            });
        }
    }
    // 下拉刷新页面
    function Init_PullRefresh() {
        var requestPage = 1;
        var shopId=$("#shopId_").val();
        var loadingCount=true;
        window.addEventListener('scroll',function(){
            var scrollVv=$(window).scrollTop()+$(window).height();
            var offsetTop=$('#morePageDivId').length>0 ? $('#morePageDivId').offset().top : 10000;
            if(scrollVv>offsetTop){
                if(scrollYes){
                    $('#yg-pro-detail-view li').off('tap');
                    if(loadingCount){
                        if($('#yg-list-filter-true').find('.pageCount').length==0){
                            $('#yg-list-filter-true').append(filterLabel());
                            $('#yg-list-filter-true').append('<input type="hidden" class="pageCount" value="2" name="page" >');
                        }
                        loadingCount=false;
                    }else{
                        var getPageCount=parseInt($('#yg-list-filter-true').find('.pageCount').val())+1
                        $('#yg-list-filter-true').find('.pageCount').val(getPageCount);
                    }
                    var params = $('#yg-list-filter-true').serialize();
                    scrollYes=false;
                    $('.yg-pro-detail-list').find('.mui-visibility').removeClass('loading-hide');
                    $.ajax({
                        type: 'get',
                        url: rootPath +'/'+shopId+'/list/ajax.sc',
                        data: params,
                        success: function (data, status, xhr) {
                            if (data == '') {
                                $('.mui-pull-bottom-pocket').find('.mui-pull-loading').removeClass('mui-spinner');
                                $('.mui-pull-bottom-pocket').find('.mui-pull-caption-refresh').html('没有数据了');
                            }else{
                                $('#yg-pro-detail-view').append(data);
                                requestPage = requestPage + 1;
                                LazyImage();
                                listClick();
                                scrollYes=true;
                            }
                            
                        },
                        error: function (xhr, errorType, error) {
                            M.toast('刷新商品失败,错误:' + error);
                        }
                    });
                }
            }
        },false);
    }
    function LazyImage() {
        imgLazyloadApi = M('#yg-pro-detail-view').imageLazyload({
            placeholder: '/static/images/thum.png'
        });
    }
    $(function(){
        Init();
    });
    return {};
}(Zepto, mui));