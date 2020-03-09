package com.lhamster.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhamster.domain.AjaxRes;
import com.lhamster.domain.Employee;
import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.QueryVo;
import com.lhamster.service.EmployeeService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class EmployeeController {
    /**
     * 注入service层
     */
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/employee")
    @RequiresPermissions("employee:index")
    public String employee() {
        return "employee";
    }

    /**
     * 查找所有员工
     */
    @RequestMapping("/employeeList")
    @ResponseBody
    public EmployeeRes employeeList(QueryVo vo) {
        EmployeeRes employeeRes = employeeService.getEmployeeList(vo);
        return employeeRes;
    }

    @RequestMapping("/saveEmployee")
    @ResponseBody
    @RequiresPermissions("employee:add")
    public String saveEmployee(Employee employee) {
        employee.setState(true);
        try {
            employeeService.saveEmployee(employee);
            return "success";
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 更新员工
     */
    @RequestMapping("/editEmployee")
    @ResponseBody
    @RequiresPermissions("employee:edit")
    public String editEmployee(Employee employee) {
        try {
            employeeService.updateEmployee(employee);
            return "success";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 修改成离职状态
     */
    @RequestMapping("/employeeState")
    @ResponseBody
    @RequiresPermissions("employee:delete")
    public String employeeState(Long id) {
        try {
            employeeService.updateState(id);
            return "success";
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 回显编辑数据
     */
    @RequestMapping("/getRoleById")
    @ResponseBody
    public List<Long> getRoleById(Long eId) {
        List<Long> ids = employeeService.getRoleById(eId);
        return ids;
    }

    /**
     * 处理没有权限的异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public void handleShiroException(HandlerMethod method, HttpServletResponse response) throws Exception {
        //获取指定的注解
        ResponseBody annotation = method.getMethodAnnotation(ResponseBody.class);
        //判断是否是ajax请求
        if (annotation != null) {
            //是，将信息传递到页面，页面进行处理
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setResult(false);
            ajaxRes.setMessage("权限不足！");

            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(ajaxRes));
        } else {
            //否，直接跳转到显示权限不足的页面
            response.sendRedirect("ExceptionHandle.jsp");
        }
    }

    /**
     * excel导出数据
     */
    @RequestMapping("/downLoadExcel")
    @ResponseBody
    public void downLoadExcel(HttpServletResponse response) {
        try {
            /*从数据库获取数据*/
            List<Employee> employees = employeeService.getAllEmployee();
            /*创建excel表*/
            HSSFWorkbook wb = new HSSFWorkbook();
            /*创建第一页*/
            HSSFSheet sheet = wb.createSheet();
            /*创建第一行，并输入内容*/
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("编号");
            row.createCell(1).setCellValue("用户名");
            row.createCell(2).setCellValue("日期");
            row.createCell(3).setCellValue("手机号");
            row.createCell(4).setCellValue("邮箱");
            row.createCell(5).setCellValue("所属部门");
            /*将数据库中的内容一一存入excel表中*/
            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);
                /*创建一行*/
                HSSFRow sheetRow = sheet.createRow(i + 1);
                sheetRow.createCell(0).setCellValue(employee.getEId());
                sheetRow.createCell(1).setCellValue(employee.getUsername());
                if (employee.getInputtime() == null) {
                    sheetRow.createCell(2).setCellValue("");
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(employee.getInputtime());
                    sheetRow.createCell(2).setCellValue(time);
                }
                sheetRow.createCell(3).setCellValue(employee.getTel());
                sheetRow.createCell(4).setCellValue(employee.getEmail());
                if (employee.getDepartment() != null) {
                    sheetRow.createCell(5).setCellValue(employee.getDepartment().getName());
                } else {
                    sheetRow.createCell(5).setCellValue("");
                }
            }
            /*将数据写到前端  设置下载的文件名和响应头*/
            String fileName = new String("员工数据.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("content-Disposition", "attachment;filename=" + fileName);
            wb.write(response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载模板
     */
    @RequestMapping("/downloadTmp")
    @ResponseBody
    public void downloadTmp(HttpServletRequest request, HttpServletResponse response) {
        /*获取文件全路径*/
        String realPath = request.getSession().getServletContext().getRealPath("static/EmployeeTmp.xls");
        FileInputStream inputStream = null;
        try {
            /*获取文件的输入流*/
            inputStream = new FileInputStream(realPath);
            /*将数据写到前端  设置下载的文件名和响应头*/
            String fileName = new String("EmployeeTemplate.xls");
            response.setHeader("content-Disposition", "attachment;filename=" + fileName);
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传excel
     */
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public AjaxRes uploadExcel(MultipartFile excel) {
        AjaxRes ajaxRes = null;
        try {
            ajaxRes = new AjaxRes();
            ajaxRes.setResult(true);
            ajaxRes.setMessage("上传成功！");

            HSSFWorkbook workbook = new HSSFWorkbook(excel.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);//拿到第0页
            /*获取最后一行的角标*/
            int lastRowNum = sheet.getLastRowNum();
            /*获取除了首行的所有内容*/
            Row employeeRow = null;
            for (int i = 1; i <= lastRowNum; i++) {
                employeeRow = sheet.getRow(i);
                System.out.println(getCellValue(employeeRow.getCell(0)));
                System.out.println(getCellValue(employeeRow.getCell(1)));
                System.out.println(getCellValue(employeeRow.getCell(2)));
                System.out.println(getCellValue(employeeRow.getCell(3)));
                System.out.println(getCellValue(employeeRow.getCell(4)));
            }
        } catch (Exception e) {
            ajaxRes.setResult(false);
            ajaxRes.setMessage("上传失败！");
            e.printStackTrace();
        }
        return ajaxRes;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
        }
        return cell;
    }
}
