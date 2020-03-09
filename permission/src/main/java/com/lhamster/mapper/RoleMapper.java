package com.lhamster.mapper;

import com.lhamster.domain.Permission;
import com.lhamster.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    Role selectByPrimaryKey(Long roleId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    void insertRoleAndPermissionRel(@Param("roleId") Long roleId, @Param("perId") Long perId);

    List<Permission> getPermissions(Long roleId);

    void deleteRel(Long roleId);
}