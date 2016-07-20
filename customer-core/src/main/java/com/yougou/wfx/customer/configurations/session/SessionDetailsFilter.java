package com.yougou.wfx.customer.configurations.session;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/23 下午3:04
 * @since 1.0 Created by lipangeng on 16/3/23 下午3:04. Email:lipg@outlook.com.
 */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE + 101)
public class SessionDetailsFilter extends OncePerRequestFilter {
    static final String SESSION_DETAILS = "SESSION_DETAILS";
    static final String UNKNOWN = "Unknown";

    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        updateSessionDetails(request);
        chain.doFilter(request, response);
    }

    /**
     * 获取用户的真实路径ip
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午5:54. Email:lipg@outlook.com.
     */
    private String getRemoteAddress(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null) {
            remoteAddr = request.getRemoteAddr();
        } else if (remoteAddr.contains(",")) {
            remoteAddr = remoteAddr.split(",")[0];
        }
        return remoteAddr;
    }

    /**
     * 更新session中存储的访问信息
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午5:56. Email:lipg@outlook.com.
     */
    private void updateSessionDetails(HttpServletRequest request) {
        System.out.println(request.getCookies()[0].getValue());
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
        }
        System.out.println(session.getId() + request.getRequestURL());
    }

}
