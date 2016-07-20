<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="format-detection" content="telephone=no,address=no,email=no">
<meta name="wap-font-scale" content="no">
<#--设置根路径-->
<script type="text/javascript">
    // 根路径
    
    var rootPath = '${context!''}';
    var running_environment= '${running_environment!''}';
    var ios_app_version= '${ios_app_version!''}';
    var android_app_version= '${android_app_version!''}';
    var isOldVersion= '${isOldVersion!'0'}';
    var global = {};
        global.memberId= '${(wfx_login_customer.userId)!''}'; 
        global.tip_status = '${(tip_status)!1}'; 
        global.ufans_url = '${ufan_article_url!''}';
   
    var wxConfig = {};
        wxConfig.appid ='${(wxConfig.appId)!''}';
        wxConfig.timestamp ='${(wxConfig.timestamp)!''}';
        wxConfig.nonceStr ='${(wxConfig.nonceStr)!''}';
        wxConfig.signature ='${(wxConfig.signature)!''}'; 

</script>