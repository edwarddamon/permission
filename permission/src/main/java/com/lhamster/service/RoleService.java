package com.lhamster.service;

import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.Permission;
import com.lhamster.domain.QueryVo;
import com.lhamster.domain.Role;

import java.util.List;

public interface RoleService {
    /*获取所有角色*/
    public EmployeeRes roleList(QueryVo vo);

    void saveRole(Role role);

    List<Permission> getPermissions(Long roleId);

    void updateRole(Role role);

    void deleteRole(Long roleId);

    List<Role> getRoleList();/*无条件获取所有role*/
}
