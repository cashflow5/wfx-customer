package com.yougou.wfx.customer.service.bapp;


import java.io.InputStream;

import com.yougou.wfx.appserver.vo.upload.UploadResult;

/**
 * Created by lizhw on 2016/4/26.
 */
public interface IFileUploadService extends IBaseService {
    public UploadResult uploadProxyAuthorizPic(InputStream inputStream, String fileName) throws
            Exception;

    public UploadResult uploadIdPic(InputStream inputStream, String fileName) throws
            Exception;
}
