/**
 * create by zhongzelong
 */
  wx.config({
           appId: wxConfig.appid, // 必填，公众号的唯一标识
           timestamp: wxConfig.timestamp, // 必填，生成签名的时间戳
           nonceStr: wxConfig.nonceStr, // 必填，生成签名的随机串
           signature: wxConfig.signature,// 必填，签名，见附录1
           jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareQZone','hideOptionMenu','showOptionMenu']
         }); 
   wx.ready(function (){
              wx.hideOptionMenu();
           });