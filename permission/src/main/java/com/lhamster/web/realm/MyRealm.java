package com.lhamster.web.realm;

import com.lhamster.domain.Employee;
import com.lhamster.service.EmployeeService;
import com.lhamster.util.UsernameUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeService employeeService;

    @Override/*授权*/
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("来到了授权---");
        /*获取用户信息*/
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        System.out.println(employee);
        //声明角色集合
        List<String> roles = new ArrayList<>();
        /*声明权限集合*/
        List<String> permissions = new ArrayList<>();
        if(employee.getAdmin()){
            //添加所有权限
            permissions.add("*:*");
        }else{
            //获取用户的角色并将其放入角色集合
            roles = employeeService.getRolesById(employee.getEId());
            /*获取角色对应的权限放入集合中*/
            permissions = employeeService.getPermissionsById(employee.getEId());
        }

        /*把角色和权限添加到 授权信息中*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    @Override/*认证*/
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /*获取表单提交的用户名*/
        String username = (String) authenticationToken.getPrincipal();
        /*查询数据库是否有该用户*/
        Employee employee = employeeService.getEmpoyeeByUsername(username);
        /*没有就返回空*/
        if (null == employee) {
            return null;
        }
        System.out.println("来到了认证---");
        /*将用户名存入threadlocal中*/
        UsernameUtil.setUserName(username);
        /*有的话进行认证
         * 参数:主体身份，正确的凭据，盐，realm的名字
         * */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee,
                employee.getPassword(),
                ByteSource.Util.bytes(employee.getUsername()),
                this.getName());
        return info;
    }
}
