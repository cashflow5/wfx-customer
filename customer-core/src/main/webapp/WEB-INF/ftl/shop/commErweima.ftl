<div class="yg-detail-erweima-box">
	<span id="close" class="tright w100p mui-icon mui-icon-closeempty"></span>
	<section class="yg-floor-erewima mui-clearfix">
		<img id ="shareimg" src="${context}/ufans/generateCommQrCode/${commodityNo!''}.sc" style="width:100%;"/>
	</section>
	<section id="yg-browser" class="yg-form-floor pd0">
		<div class="info-notes pt20 pb10 mb10 mui-clearfix">
			<!--<div class="mui-pull-left" onclick="jiathis_mh5.sendTo('cqq');return false;">
				<span class="iconfont f36 Blue">&#xe685;</span>
				<p>QQ</p>  
			</div>-->
			<div class="mui-pull-left" onclick="shareQzone()" style="width:33%">
				<span class="iconfont f36 Yellow">&#xe68f;</span>
				<p>QQ空间</p>    
			</div>
			<div class="mui-pull-left" onclick="shareSina()" style="width:33%">
				<span class="iconfont f36 Pink">&#xe65a;</span>
				<p>微博</p>
			</div>
			<!--<div class="mui-pull-left" onclick="">
				<span class="iconfont f36 Blue2">&#xe691;</span>
				<p>复制链接</p>
			</div>-->
			<div class="mui-pull-left" onclick="alert('请长按图片，进行下载');" style="width:33%">
				<span class="iconfont f36 Yellow">&#xe69b;</span>
				<p>下载</p>
			</div>
		</div>
	</section>
</div>
<style>
.yg-slide-in .layermcont{max-height:385px;}
</style>
