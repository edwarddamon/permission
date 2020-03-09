package com.lhamster.web;

import com.lhamster.domain.Permission;
import com.lhamster.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    /**
     * 获取所有权限
     * @return list
     * */
    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList(){
        return permissionService.permissionList();
    }
}
