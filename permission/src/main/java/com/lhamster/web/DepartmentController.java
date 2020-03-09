package com.lhamster.web;

import com.lhamster.domain.Department;
import com.lhamster.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/departmentList")
    @ResponseBody
    public List<Department> departmentList(){
        List<Department> departments = departmentService.getAllDepartment();
        return departments;
    }
}
