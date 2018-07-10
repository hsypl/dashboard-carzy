package com.crazy.code.security.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Request请求认证失败处理，仿照 HttpStatusEntryPoint 类，同时返回 json格式的body，
 * 适合独立验证权限的接口使用
 * Created by huangshuoying on 9/17/15.
 */
public final class HttpStatusWithBodyEntryPoint implements AuthenticationEntryPoint {

    private final HttpStatus httpStatus;

    private final String redirectURI;

    public HttpStatusWithBodyEntryPoint(HttpStatus httpStatus, String redirectURI) {
        Assert.notNull(httpStatus, "httpStatus cannot be null");
        this.httpStatus = httpStatus;
        this.redirectURI = redirectURI;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.sendRedirect(redirectURI);
    }
}
