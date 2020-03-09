$(function () {

    $("#menu_datagrid").datagrid({
        url: "menuList",
        columns: [[
            {field: 'text', title: '名称', width: 1, align: 'center'},
            {field: 'url', title: '跳转地址', width: 1, align: 'center'},
            {
                field: 'parent', title: '父标签', width: 1, align: 'center', formatter: function (value, row, index) {
                    return value ? value.text : '';
                }
            }
        ]],
        fit: true,
        rownumbers: true,
        singleSelect: true,
        striped: true,
        pagination: true,
        fitColumns: true,
        toolbar: '#menu_toolbar'
    });

    /* 初始化新增/编辑对话框*/
    $("#menu_dialog").dialog({
        width: 300,
        height: 300,
        closed: true,
        buttons: '#menu_dialog_bt'
    });

    $("#add").click(function () {
        /*清空表单*/
        $("#menu_form").form("clear");
        $("#menu_dialog").dialog("open");
    });

    /*父菜单选择框*/
    $("#parentID").combobox({
        url: 'parentList',
        width: 150,
        panelHeight: 'auto',
        valueField: 'id',
        textField: 'text',
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

    /*编辑*/
    $("#edit").click(function () {
        /*获取选中行*/
        var row = $("#menu_datagrid").datagrid("getSelected");
        if (!row) {
            $.messager.alert("提示", "请选中要编辑的行!");
            return;
        }
        /*清空表单*/
        $("#menu_form").form("clear");
        /*父菜单回显*/
        if (row.parent) {
            row["parent.id"] = row.parent.id;
        }
        /*回显表单*/
        $("#menu_form").form("load", row);
        $("#menu_dialog").dialog("open");
        $("#menu_dialog").dialog("setTitle", "编辑菜单");
    });

    /*删除*/
    $("#del").click(function () {
        /*获取选中行*/
        var row = $("#menu_datagrid").datagrid("getSelected");
        if (!row) {
            $.messager.alert("提示", "请选中要删除的行!");
            return;
        }
        $.messager.confirm("提示", "您确定要删除该菜单吗？", function (res) {
            if (res) {
                //删除选中行
                $.get("deleteMenu?id="+row.id,function (data) {
                    if(data.result){
                        $.messager.alert("温馨提示",data.message,'info',function () {
                            $("#menu_datagrid").datagrid("reload");
                        })
                    }else {
                        $.messager.alert("温馨提示",data.message);
                    }
                })
            }
        })

        /*重新加载下拉列表*/
        $("#parentID").combobox("reload");
    });

    /*刷新*/
    $("#reload").click(function () {
        $("#menu_datagrid").datagrid("reload");
    });

    /*保存*/
    $("#save").click(function () {
        /*判断是添加还是编辑*/
        var id = $("[name='id']").val();
        var url;
        if (id) {
            url = "editMenu";
        } else {
            url = "addMenu";
        }
        ;
        /*判断选择的父菜单不是自己本身*/
        var row = $("#menu_datagrid").datagrid("getSelected");
        var pId = $("[name='parent.id']").val();
        if (pId == id && pId !='') {
            $.messager.alert("温馨提示", "不能选择自己为父菜单！");
            return;
        }
        /*提交表单*/
        $("#menu_form").form("submit", {
            url: url,/*提交成员*/
            success: function (data) {//提交表单后返回的信息
                data = $.parseJSON(data);
                if (!data.result) {
                    $.messager.alert("温馨提示", data.message);
                } else {
                    $.messager.alert("温馨提示", data.message, 'info', function () {
                        /*隐藏对话框*/
                        $("#menu_dialog").dialog("close");
                        $("#parentID").combobox("reload");
                        /*重新加载页面*/
                        $("#menu_datagrid").datagrid("reload");
                    });
                }
            }
        });
    });

    /*取消*/
    $("#cancel").click(function () {
        /*隐藏对话框*/
        $("#menu_dialog").dialog("close");
    });
});