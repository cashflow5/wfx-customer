package com.yougou.wfx.customer.service.bapp.impl;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.upload.UploadResult;
import com.yougou.wfx.customer.service.bapp.IFileUploadService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ImageFtpTypeEnum;

/**
 * Created by lizhw on 2016/4/26.
 */
@Service
public class FileUploadServiceImpl extends BaseServiceImpl implements IFileUploadService {


    protected void setResult(UploadResult result, WFXResult<String> wfxResult) {
        if (wfxResult == null) {
            return;
        }
        if (isBlank(wfxResult.getResult())) {
            result.setError(wfxResult.getResultMsg());
            return;
        }

        result.setResult(true);
        result.setMsg("上传成功");

        result.setUrl(getImgBaseUrl() + wfxResult.getResult());
    }

    public UploadResult uploadProxyAuthorizPic(InputStream inputStream, String fileName) throws
            Exception {
        UploadResult result = new UploadResult();
        WFXResult<String> stringWFXResult = fileUploadApi.frontImageUpload(fileName, ImageFtpTypeEnum
                .SELLER_AUTHORIZE_IMG, inputStream);
        setResult(result, stringWFXResult);
        result.setFileName(fileName);
        return result;
    }

    public UploadResult uploadIdPic(InputStream inputStream, String fileName) throws
            Exception {
        UploadResult result = new UploadResult();
        WFXResult<String> stringWFXResult = fileUploadApi.frontImageUpload(fileName, ImageFtpTypeEnum
                .SELLER_IDENTITY_IMG, inputStream);
        setResult(result, stringWFXResult);
        result.setFileName(fileName);
        return result;
    }


}
