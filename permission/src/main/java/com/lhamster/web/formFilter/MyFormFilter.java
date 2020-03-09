package com.lhamster.web.formFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhamster.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFormFilter extends FormAuthenticationFilter {
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setResult(true);
        ajaxRes.setMessage("登陆成功！");
        response.getWriter().print(new ObjectMapper().writeValueAsString(ajaxRes));
        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setResult(false);
        try {
            String exceptionName = e.getClass().getName();
            if(exceptionName.equals(UnknownAccountException.class.getName())){
                ajaxRes.setMessage("账号不存在！");
            }else if(exceptionName.equals(IncorrectCredentialsException.class.getName())){
                ajaxRes.setMessage("密码错误！");
            }else{
                System.out.println(e);
                ajaxRes.setMessage("未知错误！");
            }
            response.getWriter().print(new ObjectMapper().writeValueAsString(ajaxRes));
        }catch (Exception e1){
            e1.printStackTrace();
        }
        return false;
    }
}
