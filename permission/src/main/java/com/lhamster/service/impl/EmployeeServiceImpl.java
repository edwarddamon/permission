package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.Employee;
import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.QueryVo;
import com.lhamster.domain.Role;
import com.lhamster.mapper.EmployeeMapper;
import com.lhamster.service.EmployeeService;
import com.lhamster.util.UsernameUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    /**注入mapper*/
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeRes getEmployeeList(QueryVo vo) {
        /*分页*/
        Page<Employee> pages = PageHelper.startPage(vo.getPage(), vo.getRows());/*当前页和每页的数据个数*/
        /*查询所有员工*/
        List<Employee> employees = employeeMapper.selectAll(vo);
        /*封装成满足返回格式的对象*/
        EmployeeRes employeeRes = new EmployeeRes();
        employeeRes.setTotal(pages.getTotal());
        employeeRes.setRows(employees);
        return employeeRes;
    }

    @Override
    public void saveEmployee(Employee employee) {

        /*进行md5加密*/
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getUsername(), 2);
        employee.setPassword(md5Hash.toString());

        /*保存employee*/
        employeeMapper.insert(employee);
        /*建立employee和role之间的关系*/
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmploy_Role_rel(employee.getEId(),role.getRoleId());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        /*更新employee*/
        employeeMapper.updateByPrimaryKey(employee);
        /*删除原先的关系*/
        employeeMapper.deleteRel(employee.getEId());
        /*建立新的关系*/
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmploy_Role_rel(employee.getEId(),role.getRoleId());
        }
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }

    @Override
    public List<Long> getRoleById(Long eId) {
        List<Long> ids = employeeMapper.getRoleById(eId);
        return ids;
    }

    @Override
    public Employee getEmpoyeeByUsername(String username) {
        return employeeMapper.getEmployeeByUsername(username);
    }

    @Override
    public List<String> getRolesById(Long eId) {
        return employeeMapper.getRolesById(eId);
    }

    @Override
    public List<String> getPermissionsById(Long eId) {
        return employeeMapper.getPermissionsById(eId);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeMapper.getAllEmployee();
    }
}
