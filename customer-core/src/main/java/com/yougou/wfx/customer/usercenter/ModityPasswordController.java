package com.yougou.wfx.customer.usercenter;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.spring.BindingUtils;
import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.moditypassword.form.DoModityPasswordFormVo;
import com.yougou.wfx.customer.model.usercenter.moditypassword.ModityPasswordSessionDetails;
import com.yougou.wfx.customer.service.usercenter.impl.AccountService;
import com.yougou.wfx.customer.service.usercenter.impl.ModityPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 修改密码页面
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/5 下午1:57
 * @since 1.0 Created by lipangeng on 16/4/5 下午1:57. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("usercenter/moditypassword")
public class ModityPasswordController {
    private static final Logger logger = LoggerFactory.getLogger(ModityPasswordController.class);
    @Autowired
    private ModityPasswordService modityPasswordService;
    @Autowired
    private AccountService accountService;

    /**
     * 修改密码的页面,显示用户手机号
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午2:14. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @LoginValidate
    public String showPhone(Model model) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        String phone = accountService.getRegisterCheckMobile(loginUserDetails.getUserId());
        if (! Strings.isNullOrEmpty(phone)) {
            model.addAttribute("phoneNumber", phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length()));
            // 更新session中的手机号信息
            ModityPasswordSessionDetails details = new ModityPasswordSessionDetails();
            details.setLoginName(phone);
            modityPasswordService.setModityPasswordDetails(details);
        }
        return "usercenter/moditypassword/modity-password";
    }

    /**
     * 验证短信验证码
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午2:54. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "validPhone", method = RequestMethod.GET)
    @LoginValidate
    public String validPhone(Model model) {
        ModityPasswordSessionDetails modityPasswordDetails = modityPasswordService.getModityPasswordDetails();
        if (! modityPasswordService.hasModityPasswordDetails()) {
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_URL;
        }
        // 发送短信
        if (! model.containsAttribute("errorMsg")) {
            Result sendSMSCode = accountService.sendSMSCode(modityPasswordDetails.getLoginName());
            if (sendSMSCode.hasError()) {
                model.addAttribute("errorMsg", sendSMSCode.getMessage());
            }
        }
        String phone = modityPasswordDetails.getLoginName();
        if (! Strings.isNullOrEmpty(phone)) {
            model.addAttribute("phoneNumber", phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length()));
        }
        return "usercenter/moditypassword/valid-phone";
    }

    /**
     * 验证短信验证码是否正确
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午2:56. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "validPhone", method = RequestMethod.POST)
    @LoginValidate
    public String doValidPhone(String smsCode, RedirectAttributes redirectAttributes) {
        ModityPasswordSessionDetails modityPasswordDetails = modityPasswordService.getModityPasswordDetails();
        if (! modityPasswordService.hasModityPasswordDetails()) {
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_URL;
        }
        if (Strings.isNullOrEmpty(smsCode)) {
            redirectAttributes.addFlashAttribute("errorMsg", "温馨提示:验证码不能为空");
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_VALID_PHONE_URL;
        }
        Result checkSMSCode = accountService.checkSMSCode(modityPasswordDetails.getLoginName(), smsCode);
        if (checkSMSCode.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", checkSMSCode.getMessage());
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_VALID_PHONE_URL;
        }
        modityPasswordDetails.setSmsCode(smsCode);
        modityPasswordDetails.setSmsCodeValid(true);
        modityPasswordService.setModityPasswordDetails(modityPasswordDetails);
        return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SET_PASSWORD_URL;
    }

    /**
     * 设置密码
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午2:54. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "modityPassword", method = RequestMethod.GET)
    @LoginValidate
    public String modityPassword() {
        if (! modityPasswordService.hasModityPasswordDetails() || ! modityPasswordService.getModityPasswordDetails().isSmsCodeValid()) {
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_URL;
        }
        return "usercenter/moditypassword/new-password";
    }

    /**
     * 修改密码
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午2:58. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "modityPassword", method = RequestMethod.POST)
    @LoginValidate
    public String doModityPassword(DoModityPasswordFormVo modityPassword,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        ModityPasswordSessionDetails modityPasswordDetails = modityPasswordService.getModityPasswordDetails();
        if (! modityPasswordService.hasModityPasswordDetails() || ! modityPasswordService.getModityPasswordDetails().isSmsCodeValid()) {
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_URL;
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SET_PASSWORD_URL;
        }
        if (! modityPassword.getPassword().equals(modityPassword.getPassword2())) {
            redirectAttributes.addFlashAttribute("errorMsg", "两次输入的密码不一致");
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SET_PASSWORD_URL;
        }
        Result resetPasswordResult = accountService.resetPassword(modityPasswordDetails.getLoginName(),
                                                                  modityPassword.getPassword(),
                                                                  modityPasswordDetails.getSmsCode());
        if (resetPasswordResult.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", resetPasswordResult.getMessage());
            return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SET_PASSWORD_URL;
        }
        // 更改完密码后的清理操作
        modityPasswordService.removeModityPasswordDetails();
        return "redirect:" + WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SUCCESS_URL;
    }

    /**
     * 发送短信验证码
     *
     * @since 1.0 Created by lipangeng on 16/4/7 上午11:32. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "sendSMSCode", method = RequestMethod.POST, produces = "application/json")
    @LoginValidate
    @ResponseBody
    public Result doSendSMSCode() {
        ModityPasswordSessionDetails modityPasswordDetails = modityPasswordService.getModityPasswordDetails();
        if (! modityPasswordService.hasModityPasswordDetails()) {
            return Result.create(false, 302, "无法找到用户相关信息");
        }
        return accountService.sendSMSCode(modityPasswordDetails.getLoginName());
    }

    /**
     * 显示修改密码成功页面
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午2:49. Email:lipg@outlook.com.
     */
    @RequestMapping("success")
    @LoginValidate
    public String showSuccess() {
        SessionUtils.logout();
        return "usercenter/moditypassword/modity-password-success";
    }
}
