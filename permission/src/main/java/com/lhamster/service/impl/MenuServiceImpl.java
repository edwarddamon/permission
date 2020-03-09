package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.AjaxRes;
import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.Menu;
import com.lhamster.domain.QueryVo;
import com.lhamster.mapper.MenuMapper;
import com.lhamster.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    /*注入mapper*/
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public EmployeeRes getMenuList(QueryVo vo) {
        /*分页查询*/
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Menu> menus = menuMapper.selectAll();

        /*封装成res*/
        EmployeeRes employeeRes = new EmployeeRes();
        employeeRes.setTotal(page.getTotal());
        employeeRes.setRows(menus);
        return employeeRes;
    }

    @Override
    public List<Menu> getParentMenu() {
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public AjaxRes editMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        /*判断选择的父菜单不是自己的子菜单*/
        Long pId = menu.getParent().getId();//当前的父菜单id
        Long oldId = menuMapper.selectParentId(pId);//根据父菜单id，查出其父菜单的id进行对比
        if(menu.getId().equals(oldId)){
            ajaxRes.setResult(false);
            ajaxRes.setMessage("不能选择自己的子菜单作为父菜单!");
            return ajaxRes;
        }
        try {
            menuMapper.updateByPrimaryKey(menu);
            ajaxRes.setResult(true);
            ajaxRes.setMessage("编辑成功！");
        }catch (Exception e){
            ajaxRes.setResult(false);
            ajaxRes.setMessage("编辑失败！");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes deleteMenu(Long id) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            /*打破关系，删除当前菜单和其子菜单的联系*/
            menuMapper.breakRelation(id);
            /*删除当前菜单*/
            menuMapper.deleteMenu(id);
            ajaxRes.setResult(true);
            ajaxRes.setMessage("删除成功！");
        }catch (Exception e){
            ajaxRes.setResult(false);
            ajaxRes.setMessage("删除失败");
        }
        return ajaxRes;
    }

    @Override
    public List<Menu> getTreeData() {
        return menuMapper.getTreeData();
    }
}
