package com.yougou.wfx.customer.usercenter;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.spring.BindingUtils;
import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.usercenter.resetpassword.*;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.usercenter.IAccountService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;
import com.yougou.wfx.customer.service.usercenter.IResetPasswordService;
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

import javax.validation.Valid;

/**
 * 找回密码控制器
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午6:27
 * @since 1.0 Created by lipangeng on 16/3/27 下午6:27. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("reset_password")
public class ResetPasswordController {
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    @Autowired
    private IResetPasswordService resetPasswordService;
    @Autowired
    private ILoginService loginService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private WFXProperties wfxProperties;
    @Autowired
    private IShoppingCartService shoppingCartService;

    /**
     * 显示找回密码页面
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午9:09. Email:lipg@outlook.com.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String retrieve() {
        return "usercenter/resetpassword/reset-password";
    }

    /**
     * 验证手机号和验证码
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午9:10. Email:lipg@outlook.com.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String phoneVaild(@Valid ResetPasswordPhoneVo phoneVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 基础验证手机号和图片验证码
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + BindingUtils.errors(bindingResult));
            redirectAttributes.addFlashAttribute("phoneNumber", phoneVo.getPhoneNumber());
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        // 检查用户是否已经存在,如不存在,则不允许找回密码
        if (! accountService.hasPhoneNumber(phoneVo.getPhoneNumber())) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:该用户尚未注册~!");
            redirectAttributes.addFlashAttribute("phoneNumber", phoneVo.getPhoneNumber());
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        // 验证图片验证码
        String imageCode = SessionUtils.getImageCode();
        if (Strings.isNullOrEmpty(imageCode) || ! phoneVo.getImageCode().toLowerCase().equals(imageCode.toLowerCase())) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:输入的验证码有误");
            redirectAttributes.addFlashAttribute("phoneNumber", phoneVo.getPhoneNumber());
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        // 图片验证码,验证成功更新session信息
        resetPasswordService.updateSessionDetails(phoneVo);
        return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PHONE_URL;
    }

    /**
     * 成功验证手机号码及验证码后显示的手机验证码页面
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午9:26. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "phone", method = RequestMethod.GET)
    public String phone(Model model) {
        // 防止恶意注册,如果未经过第一步,将用户名和验证码写入,则跳转会找回页面
        ResetPasswordSessionDetails resetPasswordDetails = resetPasswordService.getResetPasswordDetails();
        if (! resetPasswordService.hasRegisterDetails() || ! resetPasswordDetails.isImageCodeValid()) {
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        // 发送效验码到用户手机
        if (! model.containsAttribute("errorMsg")) {
            Result sendSMSCode = accountService.sendSMSCode(resetPasswordDetails.getLoginName());
            if (sendSMSCode.hasError()) {
                model.addAttribute("errorMsg", sendSMSCode.getMessage());
            }
        }
        model.addAttribute("phoneNumber", resetPasswordDetails.getLoginName());
        return "usercenter/resetpassword/phone";
    }

    /**
     * 验证短信验证码
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午9:59. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "phone", method = RequestMethod.POST)
    public String smsCodeValid(@Valid ResetPasswordSMSVo smsVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 防止恶意注册,如果未经过第一步,将用户名和验证码写入,则跳转会找回页面
        ResetPasswordSessionDetails resetPasswordDetails = resetPasswordService.getResetPasswordDetails();
        if (! resetPasswordService.hasRegisterDetails() || ! resetPasswordDetails.isImageCodeValid()) {
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + BindingUtils.errors(bindingResult));
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PHONE_URL;
        }
        Result checkSMSCode = accountService.checkSMSCode(resetPasswordDetails.getLoginName(), smsVo.getSmsCode());
        if (checkSMSCode.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:验证码验证失败.");
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PHONE_URL;
        }
        resetPasswordService.updateSessionDetails(smsVo);
        return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PASSWORD_URL;
    }

    /**
     * 显示设置密码
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午5:37. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "password", method = RequestMethod.GET)
    public String smsCodeValided() {
        // 防止恶意注册,如果未经过第一步,将用户名和验证码写入,则跳转会找回页面
        ResetPasswordSessionDetails resetPasswordDetails = resetPasswordService.getResetPasswordDetails();
        if (! resetPasswordService.hasRegisterDetails() || ! resetPasswordDetails.isImageCodeValid() ||
            ! resetPasswordDetails.isSmsCodeValid()) {
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        return "usercenter/resetpassword/password";
    }

    /**
     * 设置密码
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午7:11. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    public String passwordValid(@Valid ResetPasswordPasswordVo passwordVo,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        // 防止恶意注册,如果未经过第一步,将用户名和验证码写入,则跳转会找回页面.检测图片验证码和短信验证码是否通过,防止跳过验证,恶意注册
        ResetPasswordSessionDetails resetPasswordDetails = resetPasswordService.getResetPasswordDetails();
        if (! resetPasswordService.hasRegisterDetails() || ! resetPasswordDetails.isImageCodeValid() ||
            ! resetPasswordDetails.isSmsCodeValid()) {
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_URL;
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + BindingUtils.errors(bindingResult));
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PASSWORD_URL;
        }
        if (! passwordVo.getPassWord().equals(passwordVo.getPassWord2())) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:两次输入的密码不一致");
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PASSWORD_URL;
        }
        // 重置密码
        Result result = accountService.resetPassword(resetPasswordDetails.getLoginName(),
                                                     passwordVo.getPassWord(),
                                                     resetPasswordDetails.getSmsCode());
        if (result.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + result.getMessage());
            return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_PASSWORD_URL;
        }
        // 执行登陆
        UserLoginVo loginVo = new UserLoginVo();
        loginVo.setLoginName(resetPasswordDetails.getLoginName());
        loginVo.setLoginPassword(passwordVo.getPassWord());
        UserLoginAccountVo userLoginAccount = loginService.doLogin(loginVo);
        if (! userLoginAccount.isLogined()) {
            return "redirect:" + WfxConstant.WFX_LOGIN_URL;
        }
        // 更新session中的登陆信息
        loginService.updateSessionUserDetails(userLoginAccount);
        // 合并购物车
        shoppingCartService.mergeShoppingCartData();
        resetPasswordService.removeResetPasswordDetails();
        return "redirect:" + WfxConstant.WFX_RESET_PASSWORD_SECCESS_URL;
    }


    /**
     * 重置密码
     *
     * @since 1.0 Created by lipangeng on 16/3/25 上午1:08. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "success")
    public String success() {
        return "usercenter/resetpassword/success";
    }

    /**
     * 获取验证短信
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午7:12. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "getSmsCode", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Result sendSmsCode() {
        // 防止恶意注册,如果未经过第一步,将用户名和验证码写入,则跳转会找回页面
        ResetPasswordSessionDetails resetPasswordDetails = resetPasswordService.getResetPasswordDetails();
        if (! resetPasswordService.hasRegisterDetails()) {
            Result.create(false, 302, "无法获取用户信息,即将返回重置密码页面");
        }
        return accountService.sendSMSCode(resetPasswordDetails.getLoginName());
    }

}
