package com.lhamster.mapper;

import com.lhamster.domain.Menu;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    Long selectParentId(Long pId);

    void deleteMenu(Long id);

    /*打破关系*/
    void breakRelation(Long id);

    List<Menu> getTreeData();
}