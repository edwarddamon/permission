package com.lhamster.web;

import com.lhamster.domain.*;
import com.lhamster.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

@Controller
public class MenuController {
    //注入service层
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu")
    public String menu() {
        return "menu";
    }

    /**
     * 获取菜单
     *
     * @return
     */
    @RequestMapping("/menuList")
    @ResponseBody
    public EmployeeRes menuList(QueryVo vo) {
        EmployeeRes res = menuService.getMenuList(vo);
        return res;
    }

    /**
     * 获取父菜单
     */
    @RequestMapping("/parentList")
    @ResponseBody
    public List<Menu> parentList() {
        return menuService.getParentMenu();
    }

    /**
     * 添加菜单
     */
    @RequestMapping("/addMenu")
    @ResponseBody
    public AjaxRes addMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            menuService.saveMenu(menu);
            ajaxRes.setResult(true);
            ajaxRes.setMessage("添加成功！");
        } catch (Exception e) {
            System.out.println(e);
            ajaxRes.setResult(false);
            ajaxRes.setMessage("添加失败！");
        }
        return ajaxRes;
    }

    /**
     * 保存编辑后菜单
     */
    @RequestMapping("/editMenu")
    @ResponseBody
    public AjaxRes editMenu(Menu menu) {
        return menuService.editMenu(menu);
    }

    /**
     * 删除菜单
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public AjaxRes deleteMenu(Long id) {
        return menuService.deleteMenu(id);
    }

    /**
     * 树形菜单
     */
    @RequestMapping("/getTreeData")
    @ResponseBody
    public List<Menu> getTreeData() {
        List<Menu> treeData = menuService.getTreeData();
        /*获取当前主体*/
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        /*如果不是管理员，需要过虑菜单*/
        if (!employee.getAdmin()) {
            checkPermission(treeData);
        }
        return treeData;
    }

    /**
     * 过虑菜单
     */
    public static void checkPermission(List<Menu> menus) {
        Subject subject = SecurityUtils.getSubject();
        Iterator<Menu> iterator = menus.iterator();
        /*迭代出每一个菜单，进行过虑*/
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            /*判断该对象是否有权限对象，如果有则进行过虑*/
            if (menu.getPermission() != null) {
                /*判断该员工是否有该项权限*/
                if (!subject.isPermitted(menu.getPermission().getPerResource())) {
                    /*没有该项权限的话，就删除*/
                    iterator.remove();
                    continue;
                }
            }
            /*递归过虑子菜单*/
            if(menu.getChildren().size()>0){
                checkPermission(menu.getChildren());
            }
        }
    }
}
