package com.lhamster.mapper;

import com.lhamster.domain.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long perId);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long perId);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);
}