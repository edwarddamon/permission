package com.lhamster.mapper;

import com.lhamster.domain.Department;
import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long eId);

    int insert(Department record);

    Department selectByPrimaryKey(Long eId);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
}