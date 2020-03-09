package com.lhamster.service.impl;

import com.lhamster.domain.Permission;
import com.lhamster.mapper.PermissionMapper;
import com.lhamster.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> permissionList() {
        return permissionMapper.selectAll();
    }
}
