package com.crazy.dashboard.interceptor;

import com.crazy.dashboard.model.system.ModuleTree;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 命令信息装配拦截器：
 *  用户请求的控制器对应的命令信息，包括所属菜单、模块、命令等，用于视图显示，
 * 根据用户请求的命令地址（url path）从moduleTree匹配相应的命令信息，绑定到
 * ModelAndView 上，这样在视图中可通过JSTL标签直接引用。
 *  1、可设定不装配命令信息的扩展名
 *  2、可指定noTreeParam参数，标识不需要装配整棵树
 *  3、管理员装配完整树，其他用户根据权限装配自己有权使用的树
 * Created by wanghongwei on 9/2/15.
 */
public class CommandAssembleInterceptor extends HandlerInterceptorAdapter {
    private final static Logger log =
            LoggerFactory.getLogger(CommandAssembleInterceptor.class);

    private boolean caseSensitive;

    @Autowired
    private ModuleTree moduleTree;

    private Set<String> excludeSuffix;

    private String noTreeParam;

    public CommandAssembleInterceptor() {
        caseSensitive = true;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }
        String requestPath = getRequestPath(request);
        String suffix = getSuffix(requestPath);
        if (excludeSuffix.contains(suffix) || modelAndView == null) {
            return;
        }
        if ((StringUtils.isBlank(noTreeParam)
                || request.getParameter(noTreeParam) == null) && moduleTree != null) {
            modelAndView.addObject("moduleTree", moduleTree);
        }
    }

    /**
     * 从请求对象中获取请求的路径
     * @param request HttpServletRequest
     * @return String 请求的地址
     */
    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }

        if (!caseSensitive) {
            url = url.toLowerCase();
        }
        return url;
    }

    /**
     * 获取请求地址的后缀字符串，如果无后缀返回空字符串。
     * @param requestPath String 请求的路径
     * @return String 后最字符串ma
     */
    private String getSuffix(String requestPath) {
        int lastSlashIndex = requestPath.lastIndexOf("/");
        String fileName = lastSlashIndex != -1 ? requestPath.substring(lastSlashIndex + 1) : requestPath;
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex + 1) : "";
    }

    public void setNoTreeParam(String noTreeParam) {
        this.noTreeParam = noTreeParam;
    }

    public void setExcludeSuffix(Set<String> excludeSuffix) {
        this.excludeSuffix = excludeSuffix;
    }
}
