package com.yougou.wfx.customer.base;

import com.google.common.base.Objects;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/22
 */
@Controller
public class ErrorHandlerContorller implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerContorller.class);
    @Autowired
    private ServerProperties serverProperties;
    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 通用的异常处理机制
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午2:50. Email:lipg@outlook.com.
     */
    @RequestMapping("/error")
    public Object errorHandler(HttpServletRequest request, ModelMap modelMap) {
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
        Throwable ex = errorAttributes.getError(servletRequestAttributes);
        if (ex != null) {
            logger.error("服务器内部异常", ex);
        } else {
            logger.error("服务器内部异常,参考信息:" + JacksonUtils.Convert(errorAttributes.getErrorAttributes(servletRequestAttributes, true)));
        }
        String method = request.getMethod();
        String contentType = request.getHeader("Accept");
        contentType = contentType == null ? null : contentType.toLowerCase();
        if (HttpMethod.resolve(method) == HttpMethod.POST &&
                (Objects.equal(contentType, "application/json") || Objects.equal(contentType, "application/javascript"))) {
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            modelAndView.addObject("success", false);
            modelAndView.addObject("code", 500);
            modelAndView.addObject("message", "服务器内部错误");
            return modelAndView;
        } else {
            String msg = "数据异常";
            if (!ObjectUtils.isEmpty(request.getAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY)))
                msg = request.getAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY).toString();
            modelMap.addAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY, String.format("%s，%s", msg, WfxConstant.WFX_ERROR_MESSAGE));
            if (!ObjectUtils.isEmpty(request.getAttribute("url")))
                modelMap.addAttribute("url", request.getAttribute("url"));
            else
                modelMap.addAttribute("url", WfxConstant.WFX_INDEX_URL);
            return "base/error";
        }
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return serverProperties.getError().getPath();
    }
}
