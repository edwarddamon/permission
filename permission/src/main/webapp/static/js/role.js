$(function () {
    /*大datagrid*/
    $("#dg").datagrid({
        url: "roleList",
        striped: true,//斑马线
        singleSelect: true,/*只能选择一行*/
        columns: [[
            {field: 'roleNum', title: '角色编号', width: 100, align: 'center'},
            {field: 'roleName', title: '角色名称', width: 100, align: 'center'},
        ]],
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        toolbar: '#toolbar'
    })
    /*对话框*/
    $("#dialog").dialog({
        width: 450,
        height: 500,
        fitColumns: true,
        closed: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var url;
                var rID = $("[name='roleId']").val();
                if (rID) {
                    /*编辑*/
                    url = "updateRole";
                } else {
                    /*保存*/
                    url = "saveRole";
                }
                $("#myform").form("submit", {
                    url: url,
                    /*权限*/
                    onSubmit: function (param) {
                        var rows = $("#role_data2").datagrid("getRows");
                        for (var i = 0; i < rows.length; i++) {
                            /*取出每一行数据*/
                            var row = rows[i];
                            param["permissions[" + i + "].perId"] = row.perId;
                        }
                    },
                    success: function (data) {
                        if (null != data) {
                            $.messager.alert("提示", "保存/更新成功！", 'info', function () {
                                $("#dialog").dialog("close");
                                $("#dg").datagrid("reload");
                            });
                        } else {
                            $.messager.alert("提示", "保存/更新失败!");
                        }
                    }
                });
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#dialog").dialog("close");
            }
        }]
    });
    /*所有权限对话框*/
    $("#role_data1").datagrid({
        url: "permissionList",
        title: "所有权限(点击添加)",
        width: 180,
        height: 300,
        singleSelect: true,/*只能选择一行*/
        columns: [[
            {field: 'perName', title: '权限名称', width: 178, align: 'center'}
        ]],
        onClickRow: function (rowIndex, rowData) {
            /*判断该权限是否在已有权限中*/
            var rows = $("#role_data2").datagrid("getRows");
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                if (row.perId == rowData.perId) {
                    /*获取对应的索引*/
                    var index = $("#role_data2").datagrid("getRowIndex", row);
                    /*选中该行数据*/
                    $("#role_data2").datagrid("selectRow", index);
                    return;
                }
            }
            /*如果该权限不在已有权限中，添加到已有权限中*/
            $("#role_data2").datagrid("appendRow", rowData);
        }
    });
    /*已选中权限对话框*/
    $("#role_data2").datagrid({
        title: "已选中权限(点击删除)",
        width: 180,
        height: 300,
        singleSelect: true,/*只能选择一行*/
        columns: [[
            {field: 'perName', title: '权限名称', width: 178, align: 'center'}
        ]],
        onClickRow: function (rowIndex, rowData) {
            /*删除选中行*/
            $("#role_data2").datagrid("deleteRow", rowIndex);
        }
    });
    /*点击添加，弹出对话框*/
    $("#add").click(function () {
        $("#dialog").dialog("setTitle", "添加权限");
        /*清空数据*/
        $("#myform").form("clear");
        $("#role_data2").datagrid("loadData", []);
        $("#dialog").dialog("open");
    });
    /*编辑*/
    $("#edit").click(function () {
        /*获取选中行*/
        var row = $("#dg").datagrid("getSelected");
        if (!row) {
            $.messager.alert("提示", "请选中要编辑的角色!");
            return;
        }
        $("#myform").form("clear");/*清空表单*/

        /*查询该角色拥有的权限*/
        var option = $("#role_data2").datagrid("options");
        option.url = "getPermissions?roleId=" + row.roleId;
        /*重新加载数据*/
        $("#role_data2").datagrid("load");

        /*回显表单*/
        $("#myform").form("load", row);
        $("#dialog").dialog("open");
        $("#dialog").dialog("setTitle", "编辑权限");
    });
    /*删除*/
    $("#remove").click(function () {
        /*判断是否选中一行*/
        var row = $("#dg").datagrid("getSelected");
        if (!row) {
            $.messager.alert("提示", "请选中要删除的角色!");
            return;
        } else {
            $.messager.confirm("提示", "您确定要删除记录吗？", function (res) {
                if (res) {
                    $.get("deleteRole?roleId=" + row.roleId, function (data) {
                        if (data.result) {
                            $.messager.alert("提示", data.message, 'info', function () {
                                $("#dg").datagrid("reload");
                            });
                        }
                    })
                }
            })

        }
    });
});