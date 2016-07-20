package com.yougou.wfx.customer.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ImageFtpTypeEnum;
import com.yougou.wfx.system.api.IFileUploadApi;
import com.yougou.wfx.system.api.IWFXSystemApi;

/**
 * <p>Title: CommonController</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月27日
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	@Autowired
	private IFileUploadApi fileUploadApi;
	@Autowired
	protected IWFXSystemApi systemApi;
	
	@ResponseBody
	@RequestMapping(value = "uploadfile", method = RequestMethod.POST)
	public Result<Map<String, String>> uploadFile(@RequestParam MultipartFile file, @RequestParam String fileName, @RequestParam(defaultValue="1") Integer fileType, HttpServletRequest request) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		WFXResult<String> result = null;
		try {
			if(fileType == 1){
				result = fileUploadApi.frontImageUpload(fileName, ImageFtpTypeEnum.SELLER_IDENTITY_IMG, file.getInputStream());
			}else if(fileType == 2){
				result = fileUploadApi.frontImageUpload(fileName, ImageFtpTypeEnum.SELLER_AUTHORIZE_IMG, file.getInputStream());
			}
			String baseUrl = systemApi.obtainImgBaseUrl();
			resultMap.put("imgPath", result.getResult());
			resultMap.put("baseUrl", baseUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Result.create(false, "文件上传失败！", null);
		}
		return Result.create(true, "文件上传成功！", resultMap);
	}
	
}
