package com.yougou.wfx.customer.service.pay.impl;

import com.google.common.base.Strings;
import com.yougou.pay.api.IPayApi;
import com.yougou.pay.util.PayClientUtil;
import com.yougou.pay.vo.PayReqStateEnum;
import com.yougou.pay.vo.PayReqVo;
import com.yougou.pay.vo.PayResVo;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.pay.IPayService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.order.dto.output.AfterPayedCallBackDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/17 下午5:37
 * @since 1.0 Created by lipangeng on 16/5/17 下午5:37. Email:lipg@outlook.com.
 */
@Service
public class PayService implements IPayService {
	private static final Logger logger = LoggerFactory.getLogger(PayService.class);
	@Autowired
	private IOrderFrontApi orderFrontApi;
	@Autowired
	private IPayApi payApi;

	/**
	 * 支付回调信息
	 *
	 * @since 1.0 Created by lipangeng on 16/4/22 下午3:55.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result<AfterPayedCallBackDto> callbackAfterPayed() {
		Result<AfterPayedCallBackDto> callbackResult = null;
		Map<String, String> callBackMap = null;
		WFXResult<AfterPayedCallBackDto> result = null;
		try {
			callBackMap = PayClientUtil.filter(SessionUtils.getRequest());
			logger.info(LogUtils.requestInfo("支付回调信息:{}"), JacksonUtils.Convert(callBackMap));
			result = orderFrontApi.callbackAfterPayed(callBackMap, 1);
			logger.info(LogUtils.requestInfo("支付回调信息处理结果:{}"), JacksonUtils.Convert(result));
			if (result != null && result.getResult() != null) {
				String bankNo = result.getResult().getBankNo();
				if (!Strings.isNullOrEmpty(bankNo)) {
					if (result.getResultCode() == ResultCodeEnum.SUCCESS.getKey()) {
						PayClientUtil.outPrint(SessionUtils.getResponse(), bankNo, true);
						callbackResult = Result.create();
						callbackResult.setData(result.getResult());
					} else {
						PayClientUtil.outPrint(SessionUtils.getResponse(), bankNo, false);
						logger.error(LogUtils.requestInfo("处理支付信息失败,code:{},message:{}", callBackMap, result), result.getResultCode(), result.getResultMsg());
						callbackResult = Result.create(false, String.format("处理支付信息失败,code:%s,message:%s", result.getResultCode(), result.getResultMsg()));
					}
				} else {
					logger.error(LogUtils.requestInfo("BankNo为空", callBackMap, result));
					callbackResult = Result.create(false, "BankNo异常");
				}
			} else {
				logger.error(LogUtils.requestInfo("处理支付通知API返回NULL内容", callBackMap, result));
				callbackResult = Result.create(false, "处理支付通知API返回NULL内容");
			}
		} catch (IOException e) {
			logger.error(LogUtils.requestInfo("处理支付通知信息发生错误.异常信息:{}", callBackMap, result), e.getMessage(), e);
			callbackResult = Result.create(false, "处理支付通知信息发生错误,信息:" + e.getMessage());
		}
		return callbackResult;
	}

	/**
	 * 支付的同步通知
	 *
	 * @since 1.0 Created by lipangeng on 16/5/18 下午5:09.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result<AfterPayedCallBackDto> syncNotify() {
		Result<AfterPayedCallBackDto> callbackResult = null;
		Map<String, String> callBackMap = null;
		WFXResult<AfterPayedCallBackDto> result = null;
		try {
			callBackMap = PayClientUtil.filter(SessionUtils.getRequest());
			logger.info(LogUtils.requestInfo("支付同步回调信息:{}"), JacksonUtils.Convert(callBackMap));
			result = orderFrontApi.callbackAfterPayed(callBackMap, 0);
			logger.info(LogUtils.requestInfo("支付同步回调信息处理结果:{}"), JacksonUtils.Convert(result));
			if (result != null && result.getResult() != null) {
				if (result.getResultCode() == ResultCodeEnum.SUCCESS.getKey()) {
					callbackResult = Result.create();

				} else {
					logger.error(LogUtils.requestInfo("处理支付同步信息失败,code:{},message:{}", callBackMap, result), result.getResultCode(), result.getResultMsg());
					callbackResult = Result.create(false, String.format("处理支付同步信息失败,code:%s,message:%s", result.getResultCode(), result.getResultMsg()));
				}
				// 注入订单id
				callbackResult.setData(result.getResult());
			} else {
				logger.error(LogUtils.requestInfo("处理支付通知API返回NULL内容", callBackMap, result));
				callbackResult = Result.create(false, "处理支付同步通知API返回NULL内容");
			}
		} catch (IOException e) {
			logger.error(LogUtils.requestInfo("处理支付同步通知信息发生错误.异常信息:{}", callBackMap, result), e.getMessage(), e);
			callbackResult = Result.create(false, "处理支付同步通知信息发生错误,信息:" + e.getMessage());
		}
		return callbackResult;
	}

	/**
	 * 创建支付订单,获取支付信息
	 *
	 * @param req
	 *            支付请求信息
	 *
	 * @since 1.0 Created by lipangeng on 16/5/17 下午6:00.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result<PayResVo> createOrder(PayReqVo req) {
		logger.info(LogUtils.requestInfo("创建订单请求信息:{}"), JacksonUtils.Convert(req));
		if (req == null) {
			return Result.create(false, "创建支付请求信息为空");
		}
		Result<PayResVo> createResult = null;
		PayResVo payRes = null;
		try {
			payRes = payApi.getSendPayData(req);
			logger.info(LogUtils.requestInfo("创建订单获取支付信息结果:{}"), JacksonUtils.Convert(payRes));
			if (payRes != null) {
				if (payRes.getPayReqState() == PayReqStateEnum.STATE_SUCCESS) {
					createResult = Result.create(true, "创建支付订单成功", payRes);
				} else if (payRes.getPayReqState() == PayReqStateEnum.STATE_EXIST) {
					logger.error(LogUtils.requestInfo("支付已成功,请勿重复支付.", req, payRes));
					createResult = Result.create(false, 300, "支付已成功,请勿重复支付.");
				} else {
					logger.error(LogUtils.requestInfo("创建支付订单失败.", req, payRes));
					createResult = Result.create(false, 500, payRes.getPayReqStateMsg());
				}
			} else {
				logger.error(LogUtils.requestInfo("无法创建订单,API反馈数据为NULL", req, payRes));
				createResult = Result.create(false, "无法创建订单,API反馈数据为NULL");
			}
		} catch (Exception e) {
			logger.error(LogUtils.requestInfo("无法创建订单,错误:{}", req, payRes), e.getMessage(), e);
			return Result.create(false, 500, "无法创建订单,错误:" + e.getMessage());
		}
		return createResult;
	}
}
