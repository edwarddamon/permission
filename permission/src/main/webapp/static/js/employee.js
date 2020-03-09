$(function () {
    $("#dg").datagrid({
        url: "employeeList",
        striped: true,//斑马线
        singleSelect: true,/*只能选择一行*/
        onClickRow:function (rowIndex, rowData) {
            if(!rowData.state){
                $("#delete").linkbutton("disable");
            }else {
                $("#delete").linkbutton("enable");
            }
        },
        columns: [[
            {field: 'username', title: '姓名', width: 100, align: 'center'},
            {field: 'inputtime', title: '入职日期', width: 100, align: 'center'},
            {field: 'tel', title: '电话', width: 100, align: 'center'},
            {field: 'email', title: '邮箱', width: 100, align: 'center'},
            {
                field: 'department', title: '部门', width: 100, align: 'center', formatter: function (value, row, index) {
                    if (value) {
                        return value.name;
                    }
                }
            },
            {
                field: 'state', title: '状态', width: 100, align: 'center', formatter: function (value, row, index) {
                    if (row.state) {
                        return "在职";
                    } else {
                        return "<font style='color: darkorange;'>离职</font>"
                    }
                }
            },
            {
                field: 'admin', title: '管理员', width: 100, align: 'center', formatter: function (value, row, index) {
                    if (row.admin) {
                        return "是";
                    } else {
                        return "<p style='color: red;'>否</p>"
                    }
                }
            },
        ]],
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        toolbar: '#tb',
    })
    /*对话框*/
    $("#dialog").dialog({
        width: 300,
        height: 400,
        closed: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var eurl;
                var eid = $("[name='eid']").val();
                if (eid) {
                    /*eId*/
                    $("#eId").val($("#eid").val());
                    eurl = "editEmployee";
                } else {
                    eurl = "saveEmployee";
                }
                $("#formEm").form("submit", {
                    url: eurl,/*提交成员*/
                    onSubmit: function (param) {
                        /*获取选中的数据*/
                        var values = $("#role").combobox("getValues");
                        for(var i=0;i<values.length;i++){
                            param["roles["+i+"].roleId"]=values[i];
                        }
                    },
                    success: function (data) {//提交表单后返回的信息
                        if (data == null) {
                            $.messager.alert("温馨提示", "添加/更新失败！");
                        } else {
                            $.messager.alert("温馨提示", "添加/更新成功！", 'info', function () {
                                /*隐藏对话框*/
                                $("#dialog").dialog("close");
                                /*重新加载页面*/
                                $("#dg").datagrid("reload");
                            });
                        }
                    }
                });
            }
        }, {
            text: '关闭',
            handler: function () {
                /*隐藏对话框*/
                $("#dialog").dialog("close");
            }
        }]
    });

    /*点击添加，弹出对话框*/
    $("#add").click(function () {
        $("#pwd").show();
        $("#formEm").form("clear");
        $("#dialog").dialog("open");
        $("#dialog").dialog("setTitle", "添加员工");
        $("[name='password']").validatebox({required: true});
    });

    /*部门*/
    $("#department").combobox({
        url: 'departmentList',
        width: 150,
        panelHeight: 'auto',
        valueField: 'eid',
        textField: 'name',
        editable: false,
        /*数据加载完毕后回调*/
        onLoadSuccess: function () {
            $("#department").each(function (i) {
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if (targetInput) {
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    /*是否为管理员*/
    $("#admin").combobox({
        width: 150,
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        editable: false,
        data: [{
            label: '是',
            value: "true"
        }, {
            label: '否',
            value: "false"
        }],
        /*数据加载完毕后回调*/
        onLoadSuccess: function () {
            $("#admin").each(function (i) {
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if (targetInput) {
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    /*选择角色*/
    $("#role").combobox({
        url: 'getRoleList',
        width: 150,
        panelHeight: 'auto',
        valueField: 'roleId',
        textField: 'roleName',
        editable: false,
        multiple:true,
        data: [{
            label: '是',
            value: "true"
        }, {
            label: '否',
            value: "false"
        }],
        /*数据加载完毕后回调*/
        onLoadSuccess: function () {
            $("#role").each(function (i) {
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if (targetInput) {
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });

    /*编辑*/
    $("#edit").click(function () {
        /*获取选中行*/
        var row = $("#dg").datagrid("getSelected");
        if (!row) {
            $.messager.alert("提示", "请选中要编辑的员工!");
            return;
        }
        /*显示对话框*/
        $("#dialog").dialog("open");
        /*清空表单*/
        $("#formEm").form("clear");
        /*回显角色*/
        $.get("getRoleById?eId="+row.eid,function (data) {
            $("#role").combobox("setValues",data);
        })
        /*隐藏密码*/
        $("#pwd").hide();
        /*部门回显*/
        if (row.department) {
            /*回显给表单，前面的参数是表单的id，后面的是json数据的内容*/
            row["department.eId"] = row.department.eid;
        }
        row["admin"] = row.admin + "";
        /*密码不必填*/
        $("[name='password']").validatebox({required: false});
        /*回显表单*/
        $("#formEm").form("load", row);
        $("#dialog").dialog("setTitle", "编辑员工");
    });

    /*离职按钮*/
    $("#delete").click(function () {
        /*获取选中行*/
        var row = $("#dg").datagrid("getSelected");
        if (!row) {
            $.messager.alert("提示", "请选择要离职的员工!");
            return;
        }
        /*进行二次判断*/
        $.messager.confirm("确认","您确定要将该员工的状态设置为离职吗？",function (res) {
            if(res){
                /*修改状态*/
                $.get("employeeState?id="+row.eid,function (data) {
                    if(data!=null){
                        $.messager.alert("提示","修改成功！",'info',function () {
                            $("#dg").datagrid("reload");
                        });
                    }else {
                        $.messager.alert("提示","修改失败！");
                    }
                });
            }
        });
    });

    /*搜索*/
    $("#searchbtn").click(function () {
        var keyword = $("[name='keyword']").val();
        $("#dg").datagrid("load",{keyword:keyword});
    });

    /*刷新*/
    $("#reload").click(function () {
        /*清空搜索框内容*/
        $("[name='keyword']").val('');
        /*重新加载数据*/
        $("#dg").datagrid("load",{keyword:null});
    });

    /*excel导出*/
    $("#excelOut").click(function () {
        window.open("downLoadExcel");
    });

    /*上传界面js*/
    $("#excelUpload").dialog({
        width:260,
        height:180,
        title:"导入Excel",
        buttons:[{
            text:'保存',
            handler:function(){
                $("#uploadExcelForm").form("submit",{
                    url:"uploadExcel",
                    success:function (data) {
                        data = $.parseJSON(data);
                        if(data.result){
                            $.messager.alert("温馨提示",data.message,'info',function () {
                                /*关闭对话框*/
                                $("#excelUpload").dialog("close");
                                /*重新加载数据表格*/
                                $("#dg").datagrid("reload");
                            });
                        }else {
                            $.messager.alert("温馨提示",data.message);
                        }
                    }
                })
            }
        },{
            text:'关闭',
            handler:function(){
                $("#excelUpload").dialog("close");
            }
        }],
        closed:true
    })

    /*点击弹出上传框*/
    $("#excelIn").click(function () {
        $("#excelUpload").dialog("open");
    });

    /*下载模板*/
    $("#downloadTml").click(function () {
        window.open("downloadTmp");
    });
});