package com.lhamster.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhamster.domain.Systemlog;
import com.lhamster.mapper.SystemlogMapper;
import com.lhamster.util.SystemLogUtil;
import com.lhamster.util.UsernameUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class SystemlogAop {
    @Autowired
    private SystemlogMapper systemlogMapper;

    public void writeSystemLog(JoinPoint joinPoint) {
        Systemlog systemlog = new Systemlog();
        systemlog.setOptime(new Date());/*设置日期*/
        /*获取到request对象*/
        HttpServletRequest request = SystemLogUtil.getRequest();
        if (request != null) {
            systemlog.setIp(request.getRemoteAddr());/*设置ip*/
        }
        /*获取执行的方法的全类名*/
        String name = joinPoint.getTarget().getClass().getName();
        /*获取方法名*/
        String signature = joinPoint.getSignature().getName();
        String function = name + ":" + signature;
        systemlog.setFunct(function);
        /*获取参数*/
        String params = null;
        try {
            params = new ObjectMapper().writeValueAsString(joinPoint.getArgs());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        systemlog.setParams(params);

        /*获取用户名*/
        String userName = UsernameUtil.getUserName();
        systemlog.setUsername(userName);
        System.out.println(systemlog);
        /*存入数据库*/
        systemlogMapper.insert(systemlog);
    }
}
