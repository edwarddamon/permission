package com.lhamster.mapper;

import com.lhamster.domain.Employee;
import com.lhamster.domain.QueryVo;
import com.lhamster.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long eId);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long eId);

    List<Employee> selectAll(QueryVo vo);

    int updateByPrimaryKey(Employee record);

    void updateState(Long id);

    void insertEmploy_Role_rel(@Param("eId") Long eId, @Param("roleId") Long roleId);

    List<Long> getRoleById(Long eId);

    void deleteRel(Long eId);

    Employee getEmployeeByUsername(String username);

    /*根据员工id获取角色的的编号*/
    List<String> getRolesById(Long eId);

    /*根据员工id获取权限资源*/
    List<String> getPermissionsById(Long eId);

    List<Employee> getAllEmployee();
}