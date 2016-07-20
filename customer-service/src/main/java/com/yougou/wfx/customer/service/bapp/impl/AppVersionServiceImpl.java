package com.yougou.wfx.customer.service.bapp.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.version.AppVersion;
import com.yougou.wfx.basicset.api.front.IVersionManageFrontApi;
import com.yougou.wfx.basicset.dto.output.AppVersionOutputDto;
import com.yougou.wfx.customer.service.bapp.AppVersionService;

/**
 * Created by LLQ on 2016/5/19.
 */
@Service
public class AppVersionServiceImpl extends BaseServiceImpl implements AppVersionService {

	/**
	 * App版本管理接口
	 */
	@Autowired
	protected IVersionManageFrontApi versionManageFrontApi;

	@Override
	public AppVersion getAndroidNewestVersion() {
		AppVersionOutputDto dto = versionManageFrontApi.getAndroidNewestVersion();
		AppVersion appVersion = new AppVersion();
		if (dto != null) {
			appVersion.setVersionName(dto.getVersionName());
			appVersion.setVersionCode(dto.getVersionCode());
			appVersion.setVersionUrl(dto.getVersionUrl());
			appVersion.setForceUpdate(dto.getForceUpdate());
			appVersion.setVersionContent(dto.getVersionContent());
		}
		return appVersion;
	}
}
