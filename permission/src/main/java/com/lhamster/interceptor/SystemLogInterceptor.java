package com.lhamster.interceptor;

import com.lhamster.util.SystemLogUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemLogInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("来到了拦截器---");
        SystemLogUtil.setRequest(request);
        return true;
    }
}
