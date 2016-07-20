<#-- @ftlvariable name="wxConfig" type="com.yougou.wfx.customer.model.weixin.config.WXConfigVo" -->
<#-- @ftlvariable name="userInfo" type="com.yougou.wfx.customer.model.usercenter.account.UserInfoVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_账户信息</title>
<#include "../layouts/style.ftl">
    <link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css?v=${static_version}" />
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">账户信息</h1>
    </header>
    <div class="mui-content yg-account-information">
        <section>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <input type="file" accept="image/**" id="userHeadPhoto" style="display:none;" />
                    <a href="javascript:;" class="user-head-box">
                        <div class="mui-row">
                            <div class="mui-col-sm-6 mui-col-xs-6 pt10"><span class="Gray">头像</span></div>
                            <div class="mui-col-sm-6 mui-col-xs-6 tright user-head-click">
										<span class="user-head-portrait mr20">
											<img src="${(userAccount.userHeadUrl)!context+"static/images/admin-user-photo.png"}" />
                                            <input id="userHeadUrlInput"
                                                   name="userHeadUrlInput"
                                                   hidden
                                                   disabled />
										</span>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate">
                        <div class="mui-row">
                            <div class="mui-col-sm-6 mui-col-xs-6"><span class="Gray">用户名</span></div>
                            <div class="mui-col-sm-6 mui-col-xs-6 tright">
                                <span class="pr20">${(userInfo.userName)!''}</span>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right sex-right-select">
                        <div class="mui-row">
                            <div class="mui-col-sm-6 mui-col-xs-6"><span class="Gray">性别</span></div>
                            <div class="mui-col-sm-6 mui-col-xs-6 tright">
                                <span class="mui-content-padded mr20 ">
                                    <select class="mui-btn sex-selected">
                                        <option value="0" <#if ((userInfo.sex)!0)==0>selected</#if> disabled></option>
                                        <option value="1" <#if ((userInfo.sex)!0)==1>selected</#if>>男</option>
                                        <option value="2" <#if ((userInfo.sex)!0)==2>selected</#if>>女</option>
                                    </select>
                                </span>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="javascript:void(0);"
                       class="picker-demo mui-navigate-right"
                       data-options='{"type":"date","beginYear":1900,"value":"<#if (userInfo.birthday)??>${(userInfo.birthday)?string('yyyy-MM-dd')}<#else >1995-01-01</#if>"}'>
                        <div class="mui-row">
                            <div class="mui-col-sm-6 mui-col-xs-6"><span class="Gray">出生日期</span></div>
                            <div class="mui-col-sm-6 mui-col-xs-6 tright">
                                <span class="picker-data pr20"><#if (userInfo.birthday)??>${(userInfo.birthday)?string('yyyy-MM-dd')}</#if></span>
                            </div>
                        </div>
                    </a>
                </li>
            </ul>
        </section>
        <section class="mt20">
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right" href="${context}/usercenter/myaddress.sc">
                        <div class="mui-row">
                            <div class="mui-col-sm-6 mui-col-xs-6"><span class="Gray">收货地址管理</span></div>
                            <div class="mui-col-sm-6 mui-col-xs-6 tright">
                                <span class="pr20"></span>
                            </div>
                        </div>
                    </a>
                </li>
                <#if running_environment == '2'>
                <li class="mui-table-view-cell">
                    <a href="${context}/usercenter/moditypassword.sc" class="mui-navigate-right">
                        <div class="mui-row">
                            <div class="mui-col-sm-6 mui-col-xs-6"><span class="Gray">账户安全</span></div>
                            <div class="mui-col-sm-6 mui-col-xs-6 tright">
                                <span class="pr20">可修改密码</span>
                            </div>
                        </div>
                    </a>
                </li>
                </#if>
            </ul>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.picker.min.js?v=${static_version}" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    var wxConfig = {};
    wxConfig.appid = '${(wxConfig.appId)!''}';
    wxConfig.timestamp = '${(wxConfig.timestamp)!''}';
    wxConfig.nonceStr = '${(wxConfig.nonceStr)!''}';
    wxConfig.signature = '${(wxConfig.signature)!''}';
</script>
<script src="${context}/static/js/yg-account-information.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>