package com.lhamster.service.impl;

import com.lhamster.domain.Department;
import com.lhamster.mapper.DepartmentMapper;
import com.lhamster.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.selectAll();
    }
}
