/**
 *
 * 优购个人中心-我的地址簿 创建新地址
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/31 下午5:09
 * @since 1.0 Created by lipangeng on 16/3/31 下午5:09. Email:lipg@outlook.com.
 */
YG_MyAddress_Create = function ($, M) {
    function Init() {
        Init_ShowErrorMsg();
        Init_DefaultAddressSwitch();
        Init_CityPicker();
        Init_SubmitAddressForm();
    }

    // 初始化错误提示
    function Init_ShowErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
    }

    // 初始化提交按钮
    function Init_SubmitAddressForm() {
        $('#addressForm').on('tap', '#submitAddressForm', function (e) {
            var _addressForm = $('#addressForm');
            if (YG_Utils.Strings.IsBlank((_addressForm.find('#name').val()))) {
                M.alert('收货人名称不能为空', '温馨提示');
                return;
            }
            if (YG_Utils.Strings.IsBlank(_addressForm.find('#phone').val())) {
                M.alert('收货人手机号不能为空', '温馨提示');
                return;
            }
            if (!YG_Utils.Valid.Phone(_addressForm.find('#phone').val()).status) {
                M.alert('收货人手机号码格式不正确', '温馨提示');
                return;
            }
            if (YG_Utils.Strings.IsBlank(_addressForm.find('#city').val()) ||
                YG_Utils.Strings.IsBlank(_addressForm.find('#province').val()) ||
                YG_Utils.Strings.IsBlank(_addressForm.find('#district').val())) {
                M.alert('收货人地区不能为空', '温馨提示');
                return;
            }
            if (YG_Utils.Strings.IsBlank(_addressForm.find('#address').val())) {
                M.alert('收货人详情地址不能为空', '温馨提示');
                return;
            }
            _addressForm.submit();
        });
    }

    // 初始化默认地址的switch
    function Init_DefaultAddressSwitch() {
        $('#addressForm').on('toggle', '#defaultAddressSwitch', function (e) {
            if (event.detail.isActive) {
                $('#defaultAddress').prop('checked', true);
            } else {
                $('#defaultAddress').prop('checked', false);
            }
        });
    }

    // 初始化城市选择器
    function Init_CityPicker() {
        //级联示例
        var cityPicker3 = new M.PopPicker({
            layer: 3
        });
        cityPicker3.setData(YGCityData);
        $('#addressForm').on('tap', '#showCityPickerThree,#showCityPickerThree2', function (e) {
            $(this).focus();
            $(this).blur();
            // todo 2016年04月01日 计算字符长度,隐藏
            cityPicker3.show(function (items) {
                $('#showCityPickerThree').val(
                    (items[0] || {}).text +
                    " " +
                    (items[1] || {}).text +
                    " " +
                    (items[2] || {}).text);
                $('#province').val((items[0] || {}).text);
                $('#city').val((items[1] || {}).text);
                $('#district').val((items[2] || {}).text);
                //返回 false 可以阻止选择框的关闭
                //return false;
            });
        });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui);