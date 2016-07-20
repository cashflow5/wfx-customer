package com.yougou.wfx.customer.usercenter;

import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.spring.BindingUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.myaddress.AccountAddressVo;
import com.yougou.wfx.customer.model.usercenter.myaddress.form.AddAccountAddressFormVo;
import com.yougou.wfx.customer.model.usercenter.myaddress.form.EditAccountAddressFormVo;
import com.yougou.wfx.customer.service.usercenter.IAccountAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * 我的地址簿的Controller
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/31 上午9:44
 * @since 1.0 Created by lipangeng on 16/3/31 上午9:44. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("usercenter/myaddress")
public class MyAddressController {
    private static final Logger logger = LoggerFactory.getLogger(MyAddressController.class);

    @Autowired
    private IAccountAddressService accountAddressService;

    /**
     * 显示我的地址簿列表
     *
     * @since 1.0 Created by lipangeng on 16/3/31 上午9:46. Email:lipg@outlook.com.
     */
    @RequestMapping
    @LoginValidate
    public String list(Model model, @RequestParam(value = "pay", defaultValue = "false") Boolean isPay) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 是否是从创建订单页面跳转过来的
        if (! loginUserDetails.isAddressPayRedirect() && isPay) {
            loginUserDetails.setAddressPayRedirect(isPay);
            SessionUtils.setLoginUserDetails(loginUserDetails);
        }
        // 获取全部收货地址
        List<AccountAddressVo> addresses = accountAddressService.getAllAddress(loginUserDetails.getUserId());
        model.addAttribute("addresses", addresses);
        model.addAttribute("isPay", loginUserDetails.isAddressPayRedirect());
        if (CollectionUtils.isEmpty(addresses)) {
            return "usercenter/myaddress/create";
        }
        return "usercenter/myaddress/myaddress";
    }

    /**
     * 创建收货地址
     *
     * @since 1.0 Created by lipangeng on 16/3/31 下午5:04. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @LoginValidate
    public String create(Model model, @ModelAttribute("address") AddAccountAddressFormVo myAddress) {
        model.addAttribute("isPay", SessionUtils.getLoginUserDetails().isAddressPayRedirect());
        return "usercenter/myaddress/create";
    }

    /**
     * 创建新的收货地址
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午3:17. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @LoginValidate
    public String doCreate(@Valid AddAccountAddressFormVo myAddress, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            redirectAttributes.addFlashAttribute("address", myAddress);
            return "redirect:" + WfxConstant.WFX_USERCENTER_CREATE_MY_ADDRESS_URL;
        }
        // 添加新的收货地址
        Result<AccountAddressVo> result =
                accountAddressService.addAddress(loginUserDetails.getUserId(), AccountAddressVo.valueOf(myAddress));
        // 如果保存失败则跳回编辑页面
        if (result.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", result.getMessage());
            // 如果错误代码是501的话,代表虽然发生错误,但是不致命.
            if (result.getCode() != 501) {
                redirectAttributes.addFlashAttribute("address", myAddress);
                return "redirect:" + WfxConstant.WFX_USERCENTER_CREATE_MY_ADDRESS_URL;
            }
        }
        if (loginUserDetails.isAddressPayRedirect()) {
            return "redirect:/confirm_order.sc?addressId=" + result.getData().getId();
        }
        return "redirect:" + WfxConstant.WFX_USERCENTER_MY_ADDRESS_URL;
    }

    /**
     * 编辑收货地址
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午5:02. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    @LoginValidate
    public String edit(Model model, @PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        model.addAttribute("isPay", loginUserDetails.isAddressPayRedirect());
        // 如果不是因为保存异常跳转过来的页面则注入相关收货地址信息
        if (! model.containsAttribute("address")) {
            AccountAddressVo address = accountAddressService.getAddress(loginUserDetails.getUserId(), id);
            if (address == null) {
                redirectAttributes.addFlashAttribute("errorMsg", "收货地址不存在.无法编辑.");
                return "redirect:" + WfxConstant.WFX_USERCENTER_MY_ADDRESS_URL;
            }
            model.addAttribute("address", address);
        }
        return "usercenter/myaddress/edit";
    }

    /**
     * 编辑收货地址
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午5:02. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @LoginValidate
    public String doEdit(@Valid EditAccountAddressFormVo accountAddress,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            redirectAttributes.addFlashAttribute("address", accountAddress);
        }
        Result<AccountAddressVo> result =
                accountAddressService.updateAddress(loginUserDetails.getUserId(), AccountAddressVo.valueOf(accountAddress));
        if (result.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", result.getMessage());
            redirectAttributes.addFlashAttribute("address", accountAddress);
            return "redirect:/usercenter/myaddress/" + accountAddress.getId() + "/edit.sc";
        }
        if (loginUserDetails.isAddressPayRedirect()) {
            return "redirect:/confirm_order.sc?addressId=" + result.getData().getId();
        }
        return "redirect:" + WfxConstant.WFX_USERCENTER_MY_ADDRESS_URL;
    }


    /**
     * 删除用户收货地址
     *
     * @since 1.0 Created by lipangeng on 16/3/31 下午3:58. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @LoginValidate
    public Result delAddress(@PathVariable("id") String id) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        return accountAddressService.delAddress(loginUserDetails.getUserId(), id);
    }


    /**
     * 设置用户默认收货地址
     *
     * @since 1.0 Created by lipangeng on 16/3/31 下午5:03. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "defaultAddress", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @LoginValidate
    public Result changeDefaultAddress(@RequestParam(value = "addressId") String addressId) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        return accountAddressService.setDefaultAddress(loginUserDetails.getUserId(), addressId);
    }

    /**
     * 设置用户默认收货地址
     *
     * @since 1.0 Created by lipangeng on 16/3/31 下午5:03. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "cancelDefaultAddress", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @LoginValidate
    public Result cancelDefaultAddress(@RequestParam(value = "addressId") String addressId) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        AccountAddressVo accountAddress = new AccountAddressVo();
        accountAddress.setId(addressId);
        accountAddress.setDefaultAddress(false);
        return accountAddressService.updateAddress(loginUserDetails.getUserId(), accountAddress);
    }
}
