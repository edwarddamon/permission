package com.lhamster.util;

import javax.servlet.http.HttpServletRequest;

public class SystemLogUtil {
    public static ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return local.get();
    }

    public static void setRequest(HttpServletRequest request) {
        local.set(request);
    }
}
