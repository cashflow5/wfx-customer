package com.yougou.wfx.customer.service.usercenter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yougou.wfx.customer.service.message.ISMSMessageService;
import com.yougou.wfx.customer.service.usercenter.IRegisterService;

/**
 * 登陆控制器的Service层.
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午4:50
 * @since 1.0 Created by lipangeng on 16/3/25 下午4:50. Email:lipg@outlook.com.
 */
@Service
public class RegisterService implements IRegisterService {
	private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
	@Autowired
	private ISMSMessageService smsMessageService;
}
