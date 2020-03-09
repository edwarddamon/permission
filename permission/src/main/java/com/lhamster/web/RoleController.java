package com.lhamster.web;

import com.lhamster.domain.*;
import com.lhamster.service.EmployeeService;
import com.lhamster.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {
    /*注入service层*/
    @Autowired
    private RoleService roleService;

    @RequestMapping("/role")
    public String role(){
        return "role";
    }

    /**
     * 获取所有角色
     * */
    @RequestMapping("/roleList")
    @ResponseBody
    public EmployeeRes roleList(QueryVo vo){
        EmployeeRes res = roleService.roleList(vo);
        return res;
    }

    /**
     * 保存角色和对应的权限
     * */
    @RequestMapping("/saveRole")
    @ResponseBody
    public String saveRole(Role role){
        try {
            roleService.saveRole(role);
            return "success";
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**获取权限*/
    @RequestMapping("/getPermissions")
    @ResponseBody
    public List<Permission> getPermissions(Long roleId){
        return roleService.getPermissions(roleId);
    }

    /**更新角色和权限*/
    @RequestMapping("/updateRole")
    @ResponseBody
    public String updateRole(Role role){
        try {
            roleService.updateRole(role);
            return "success";
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    /**删除角色和权限*/
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long roleId){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.deleteRole(roleId);
            ajaxRes.setMessage("删除成功！");
            ajaxRes.setResult(true);
            return ajaxRes;
        }catch (Exception e){
            ajaxRes.setMessage("删除失败！");
            ajaxRes.setResult(false);
            return ajaxRes;
        }
    }

    /**获取角色*/
    @RequestMapping("/getRoleList")
    @ResponseBody
    public List<Role> getRoleList(){
        return roleService.getRoleList();
    }
}
