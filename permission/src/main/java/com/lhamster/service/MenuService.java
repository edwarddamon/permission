package com.lhamster.service;

import com.lhamster.domain.AjaxRes;
import com.lhamster.domain.EmployeeRes;
import com.lhamster.domain.Menu;
import com.lhamster.domain.QueryVo;

import java.util.List;

public interface MenuService {
    EmployeeRes getMenuList(QueryVo vo);

    /*获取所有的menu,不分页*/
    List<Menu> getParentMenu();

    /*新增菜单*/
    void saveMenu(Menu menu);

    /*保存编辑后的菜单*/
    AjaxRes editMenu(Menu menu);

    /*删除菜单*/
    AjaxRes deleteMenu(Long id);

    /*树形结构*/
    List<Menu> getTreeData();
}
