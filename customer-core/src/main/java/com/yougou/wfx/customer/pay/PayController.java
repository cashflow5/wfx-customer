package com.yougou.wfx.customer.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.pay.IPayService;
import com.yougou.wfx.customer.service.usercenter.IUserActionLogService;
import com.yougou.wfx.enums.OrderLogTypeEnum;
import com.yougou.wfx.order.dto.output.AfterPayedCallBackDto;

/**
 * Created by zhang.sj on 2016/3/22.
 */

@Controller
@RequestMapping("pay")
public class PayController {
	private static final Logger logger = LoggerFactory.getLogger(PayController.class);
	@Autowired
	private IPayService payService;
	@Autowired
	private IUserActionLogService userActionLogService;

	/**
	 * 其他支付方式
	 *
	 * @since 1.0 Created by lipangeng on 16/5/9 下午3:38. Email:lipg@outlook.com.
	 */
	@RequestMapping("otherPay")
	@ResponseBody
	public String otherPay() {
		return "no suppout";
	}

	/**
	 * 支付通知回调
	 *
	 * @since 1.0 Created by lipangeng on 16/5/17 下午5:35.
	 *        Email:lipg@outlook.com.
	 */
	@RequestMapping(value = "notify")
	public void notifys() {
		payService.callbackAfterPayed();
	}

	/**
	 * 支付的同步通知,获取订单id,并跳转到订单详情页面
	 *
	 * @since 1.0 Created by lipangeng on 16/5/19 下午2:24.
	 *        Email:lipg@outlook.com.
	 */
	@RequestMapping("syncNotify")
	public String syncNotify(RedirectAttributes redirectAttributes, Model model) {
		Result<AfterPayedCallBackDto> syncNotify = payService.syncNotify();
		if (syncNotify.hasError()) {
			String message = syncNotify.getMessage();
			logger.error("处理同步通知失败,错误:{}", message);
			redirectAttributes.addAttribute("errorMsg", "处理支付宝回调通知失败,参考信息:" + message);
			return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
		}
		AfterPayedCallBackDto dto = syncNotify.getData();
		return "redirect:/order/" + dto.getOrderId() + "/payDetail.sc";
	}
}
