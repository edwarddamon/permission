package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.Permission;
import com.lhamster.domain.QueryVo;
import com.lhamster.domain.Role;
import com.lhamster.mapper.RoleMapper;
import com.lhamster.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    /*注入mapper*/
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public EmployeeRes roleList(QueryVo vo) {
        /*分页*/
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        /*查询*/
        List<Role> roles = roleMapper.selectAll();
        EmployeeRes res = new EmployeeRes();
        res.setTotal(page.getTotal());
        res.setRows(roles);
        return res;
    }

    @Override
    public void saveRole(Role role) {
        /*保存角色*/
        roleMapper.insert(role);
        /*维护角色和权限间的关系*/
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRoleId(),permission.getPerId());
        }
    }

    @Override
    public List<Permission> getPermissions(Long roleId) {
        return roleMapper.getPermissions(roleId);
    }

    @Override
    public void updateRole(Role role) {
        /*打破关系*/
        roleMapper.deleteRel(role.getRoleId());
        /*更新role*/
        roleMapper.updateByPrimaryKey(role);
        /*建立新的关系*/
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRoleId(),permission.getPerId());
        }
    }

    @Override
    public void deleteRole(Long roleId) {
        /*解除权限关系*/
        roleMapper.deleteRel(roleId);
        /*删除角色*/
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.selectAll();
    }
}
