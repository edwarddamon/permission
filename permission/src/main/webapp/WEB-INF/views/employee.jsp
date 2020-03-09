<%--
  Created by IntelliJ IDEA.
  User: Edward
  Date: 2020/1/17
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<head>
    <title>员工主页</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/employee.js"></script>
</head>
<body>
<div id="tb">
    <shiro:hasPermission name="employee:add">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:edit">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:delete">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="delete">离职</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="reload">刷新</a>
    <input type="text" name="keyword" style="width: 200px; height: 30px;padding-left: 5px;">
    <a class="easyui-linkbutton" iconCls="icon-search" id="searchbtn">查询</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="excelOut">导出</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="excelIn">导入</a>
</div>
<div id="excelUpload">
    <form method="post" enctype="multipart/form-data" id="uploadExcelForm">
        <tabel>
            <tr>
                <td><input type="file" name="excel" style="width: 180px; margin-top: 20px; margin-left: 5px;"></td>
                <td><a href="javascript:void(0);" id="downloadTml">下载模板</a></td>
            </tr>
        </tabel>
    </form>
</div>
<table id="dg"></table>
<%--对话框--%>
<div id="dialog">
    <form id="formEm" method="post">
        <table align="center" style="border-spacing: 0px 10px">
            <%--隐藏属性--%>
            <input id="eId" type="hidden" name="eId">
            <input id="eid" type="hidden" name="eid">
            <tr>
                <td>用户名:</td>
                <td><input type="text" name="username" class="easyui-validatebox" data-options="required:true"></td>
            </tr>
            <tr id="pwd">
                <td>密码:</td>
                <td><input type="text" name="password" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>手机:</td>
                <td><input type="text" name="tel" class="easyui-validatebox" data-options="required:true"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="email" class="easyui-validatebox"
                           data-options="required:true,validType:'email'"></td>
            </tr>
            <tr>
                <td>入职日期:</td>
                <td><input type="text" class="easyui-datebox" name="inputtime"/></td>
            </tr>
            <tr>
                <td>部门:</td>
                <td><input id="department" placeholder="请选择部门..." name="department.eId"/></td>
            </tr>
            <tr>
                <td>是否管理员:</td>
                <td><input id="admin" placeholder="是否为管理员" name="admin"/></td>
            </tr>
            <tr>
                <td>选择角色:</td>
                <td><input id="role" placeholder="请选择角色..." name="role.roleId"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
