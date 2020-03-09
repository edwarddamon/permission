package com.lhamster.service;

import com.lhamster.domain.Employee;
import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.QueryVo;
import com.lhamster.domain.Role;

import java.util.List;

public interface EmployeeService {
    /*查询所有员工*/
    public EmployeeRes getEmployeeList(QueryVo vo);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);//更新员工信息

    void updateState(Long id);//修改成离职

    List<Long> getRoleById(Long eId);

    Employee getEmpoyeeByUsername(String username);

    List<String> getRolesById(Long eId);//根据员工id获取角色的的编号

    List<String> getPermissionsById(Long eId);//根据员工id获取权限资源

    List<Employee> getAllEmployee();//获取所有员工信息
}
