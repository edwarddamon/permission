<%--
  Created by IntelliJ IDEA.
  User: Edward
  Date: 2020/1/17
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色页面</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/role.js"></script>

    <style>
        #dialog #myform .panel-title{
            color: black;
        }
    </style>
</head>
<body>
<table id="emp_dg"></table>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="remove">删除</a>
</div>
<table id="dg"></table>
<div id="dialog">
    <form id="myform">
        <table align="center" style="border-spacing: 20px 30px">
            <input type="hidden" name="id">
            <tr align="center">
                <input type="hidden" name="roleId">
                <td>角色编号<input type="text" name="roleNum" class="easyui-validatebox"></td>
                <td>角色名称<input type="text" name="roleName" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>
                    <div id="role_data1"></div>
                </td>
                <td>
                    <div id="role_data2"></div>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
