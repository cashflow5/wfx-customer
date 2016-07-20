/**
 * create by lijunfang
 */
var YG_AccountInformation = (function ($, M) {
    function Init() {
        Init_Birthday();
        Init_UserSex();
       // Init_WeiXinConfig();
        Init_ReloadUserHeadUrl();
    }

    //
    function Init_WeiXinConfig() {
        // 如果不是在微信打开,则点击上传按钮时提示
        if (!YG_Utils.WeiXin.checkBrower()) {
            $('.user-head-box').on('tap', '.user-head-click', function () {
                M.alert('如需上传头像,请在微信中打开此页面', '温馨提示', function () {
                    return false;
                });
            });
            return;
        }
        wx.config({
                      // debug: true,
                      appId: wxConfig.appid, // 必填，公众号的唯一标识
                      timestamp: wxConfig.timestamp, // 必填，生成签名的时间戳
                      nonceStr: wxConfig.nonceStr, // 必填，生成签名的随机串
                      signature: wxConfig.signature,// 必填，签名，见附录1
                      jsApiList: ['chooseImage', 'uploadImage']
                  });
        wx.checkJsApi({
                          jsApiList: [
                              'checkJsApi',
                              'chooseImage',
                              'uploadImage'
                          ],
                          success: function (res) {
                              alert(res);
                          },
                          fail: function (res) {
                              YG_Utils.Notification.error("无法进行头像上传(请确保在微信中打开该页面)");
                          }
                      });
        wx.ready(function () {
            Init_UserHeadReplace();
        });
        wx.error(function (res) {
            YG_Utils.Notification.error('微信权限获取失败,错误信息:' + JSON.stringify(res));
        });
    }

    // 注册设定生日按钮事件
    function Init_Birthday() {
        var result = $('.picker-data')[0];
        var btns = $('.picker-demo');
        btns.each(function (i, btn) {
            btn.addEventListener('tap', function () {
                var _btn = this;
                var optionsJson = this.getAttribute('data-options') || '{}';
                var options = JSON.parse(optionsJson);
                // 配置最大可选日期
                options.endYear = new Date().getFullYear();
                var id = this.getAttribute('id');
                var picker = new M.DtPicker(options);
                picker.show(function (rs) {
                    if (!CheckBirthday(rs.text)) {
                        picker.dispose();
                        M.alert('生日不能大于当前日期', '温馨提示', function () {
                            $(_btn).trigger('tap');
                            return false;
                        });
                        return false;
                    }
                    // 更新页面配置数据
                    var _options = JSON.parse(optionsJson);
                    _options.value = rs.text;
                    _btn.setAttribute('data-options', JSON.stringify(_options));
                    updateBirthday(rs.text, result);
                    picker.dispose();
                });
            }, false);
        });
    }

    // 检查日期是否符合条件
    /**
     * @return {boolean}
     */
    function CheckBirthday(birthday) {
        if (birthday == undefined) {
            return false;
        }
        var regEx = new RegExp("\\-", "gi");
        var _birthdayDate = new Date(birthday.replace(regEx, "/"));
        var _nowDate = new Date();
        if (_birthdayDate > _nowDate) {
            return false;
        }
        return true;
    }

    // 更新生日,如果更新成功,则将birthday写入result
    function updateBirthday(birthday, result) {
        $.ajax({
                   type: 'post',
                   url: rootPath + '/usercenter/userinfo.sc',
                   data: {birthday: birthday, updateType: 'birthday'},
                   dataType: 'json',
                   error: function (xhr, errorType, error) {
                       M.alert('无法更新出生日期信息,错误:' + error, '错误提示');
                   },
                   success: function (data, status, xhr) {
                       if (data.success) {
                           M.toast('更新出生日期信息成功');
                           result.innerText = birthday;
                       } else {
                           M.alert('更新出生日期信息失败,错误:' + data.message, '错误提示');
                       }
                   }
               })
    }

    // 修改用户头像按钮,触发上传头像操作
    function Init_UserHeadReplace() {
        // 实际上传头像
        function downLoad(imgId) {
            $.ajax({
                       type: 'post',
                       url: rootPath + '/image/headimg/upload.sc',
                       dataType: 'json',
                       data: {imgId: imgId},
                       success: function (data, status, xhr) {
                           if (data.success) {
                               M.toast("头像设置成功");
                               if (data.data != undefined) {
                                   $('.user-head-portrait img')[0].src = data.data;
                                   $('#userHeadUrlInput').val(data.data);
                                   if (window.sessionStorage) {
                                       window.sessionStorage.setItem('userHeadUrl', data.data);
                                   }
                               } else {
                                   YG_Utils.Notification.error('上传头像成功,但未能刷新头像。请刷新页面查看最新结果!');
                               }
                           } else {
                               YG_Utils.Notification.error(data.message);
                           }
                       },
                       error: function (xhr, errorType, error) {
                           YG_Utils.Notification.error('头像上传异常!');
                       }
                   });
        }

        // 上传头像触发
        $('.user-head-box').on('tap', '.user-head-click', function () {
            wx.chooseImage({
                               count: 1,
                               success: function (res) {
                                   var localId = res.localIds[0];
                                   wx.uploadImage({
                                                      localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
                                                      isShowProgressTips: 1, // 默认为1，显示进度提示
                                                      success: function (res) {
                                                          downLoad(res.serverId);
                                                      },
                                                      fail: function (res) {
                                                          YG_Utils.Notification.error("上传头像失败:" + JSON.stringify(res));
                                                      }

                                                  });
                               },
                               fail: function (res) {
                                   YG_Utils.Notification.error("选择头像失败:" + JSON.stringify(res));
                               }
                           });
        });
    }


    // 修改用户性别触发
    function Init_UserSex() {
        var _sexSelect = $('.sex-selected');
        $('.yg-account-information').on('tap', '.sex-right-select', function (e) {
            var sexPicker = new M.PopPicker();
            sexPicker.setData([{value: '1', text: '男'}, {value: '2', text: '女'}]);
            if (YG_Utils.Strings.NotBlank(_sexSelect.val())) {
                sexPicker.pickers[0].setSelectedValue(_sexSelect.val());
            }
            sexPicker.show(function (item) {
                sexPicker.dispose();
                if (item != undefined && item[0] != undefined && item[0].value != undefined) {
                    _sexSelect.val(item[0].value);
                    updateSex();
                } else {
                    YG_Utils.Notification.error('数据异常');
                }
            });
        });
        $('.yg-account-information').on('mousedown', '.sex-selected', function (e) {
            this.blur();
            $('.sex-right-select').trigger('tap');
            return false;
        });
    }

    // 更新用户性别
    function updateSex() {
        var _sexSelect = $('.sex-selected');
        if (_sexSelect.val() == 0) {
            YG_Utils.Notification.error('请选择性别');
            return false;
        }
        YG_Utils.Layer.show();
        $.ajax({
                   type: 'post',
                   url: rootPath + '/usercenter/userinfo.sc',
                   data: {sex: _sexSelect.val(), updateType: 'sex'},
                   dataType: 'json',
                   error: function (xhr, errorType, error) {
                       YG_Utils.Layer.closeAll();
                       M.alert('无法更新性别信息,错误:' + error, '错误提示');
                       changeSex(_sexSelect);
                   },
                   success: function (data, status, xhr) {
                       YG_Utils.Layer.closeAll();
                       if (data.success) {
                           M.toast('更新性别信息成功');
                       } else {
                           M.alert('更新性别信息失败,错误:' + data.message, '错误提示');
                           changeSex(_sexSelect);
                       }
                   }
               });
    }

    // 强刷头像地址
    function Init_ReloadUserHeadUrl() {
        var _headUrl = $('#userHeadUrlInput').val();
        if (YG_Utils.Strings.NotBlank(_headUrl)) {
            $('.user-head-portrait img')[0].src = _headUrl;
        }
    }

    // 改变性别
    function changeSex(sexSelect) {
        var _sex = sexSelect.val();
        if (_sex == 0) {
            sexSelect.val(1);
        } else if (_sex == 1) {
            sexSelect.val(0);
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));