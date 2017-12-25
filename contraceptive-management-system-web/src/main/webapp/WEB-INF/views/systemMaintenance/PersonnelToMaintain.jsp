<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server" >
	<base href="${basePath}"/>
    <title>系统维护-人员维护</title>
    <link href="../../_Libs/jquery.easyui1.2.2/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
    <link href="../../_Libs/jquery.easyui1.2.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="../../_Libs/jquery.easyui1.2.2/jquery1.4.4.min.js" type="text/javascript"></script>
    <script src="../../_Libs/jquery.easyui1.2.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="../../_Libs/jquery.easyui1.2.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="../../_Libs/jquery.easyui1.2.2/Web/common.js" type="text/javascript"></script>
    <link href="../_Themes/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    $.extend($.fn.combobox.defaults, {panelHeight:true});
        var me = {
            datagrid1: null,
            edit_form: null,
            edit_window: null,
            search_form: null,
            search_window: null,
            tree1: null,
            idFiled: 'UserID',
            actionUrl: 'Common.ashx'
        };

        $(function () {
            pageInit();
//            loadTree();
            loadGrid();
//            document.getElementById("aa").style.display = "block";
//            document.getElementById("bb").style.display = "block";
        });

        function pageInit() {
            me.edit_window = $('#edit_window');
            me.edit_form = me.edit_window.find('#edit_form');
            me.search_window = $('#search_window');
            me.search_form = me.search_window.find('#search_form');
            me.tree1 = $('#tree1');
            me.datagrid1 = $('#datagrid1');

            $('#btn_edit_ok').linkbutton().click(function () { SaveData(); });
            $('#btn_search_ok').linkbutton().click(function () { me.datagrid1.datagrid({ pageNumber: 1 }); });
            $('#btn_edit_cancel').linkbutton().click(function () { me.edit_window.window('close'); });
            $('#btn_search_cancel').linkbutton().click(function () { me.search_window.window('close'); });
        }

        //加载树
        function loadTree() {
            me.tree1.tree({
                url: me.actionUrl + '?Method=Tree',
                onClick: function (node) {
                    if (node.id == 0) {
                        me.edit_form.find('#ParentID').val(node.id);
                        me.edit_form.find('#Level').val(1);
                        me.edit_form.find('#LevelPath').val(node.id + '.');
                        me.edit_form.find('#State').attr('checked', true);
                    } else {
                        me.edit_form.find('#ParentID').val(node.id);
                        me.edit_form.find('#Level').val(parseInt(node.attributes['Level']) + 1);
                        me.edit_form.find('#LevelPath').val(node.attributes['LevelPath'] + node.id + '.');
                        me.edit_form.find('#State').attr('checked', true);
                    }
                    me.datagrid1.datagrid('reload');

                },
                onLoadSuccess: function (node, param) {
                    var selectedNode = $(this).tree('find', me.edit_form.find('#ParentID').val());
                    if (selectedNode) $(this).tree('select', selectedNode.target);
                }
            });
        }

        //加载列表
        function loadGrid() {
            me.datagrid1.datagrid({
                sortName: 'FSort',
                idField: me.idFiled,
                url: me.actionUrl + '?Method=PersonnelToMaintain_List',
                frozenColumns: [[
	                { field: 'UserName', title: '登录名', width: 120, sortable: true, align: 'center' }
				]],
                columns: [[
                  { field: 'Node', title: '所在节点', width: 120, sortable: true, align: 'center' },
                  { field: 'UserType', title: '类型', width: 100, sortable: true, align: 'center' },
                  { field: 'TrueName', title: '姓名', width: 100, sortable: true, align: 'center' },
                  { field: 'IsReceiveSms', title: '短信', width: 60, sortable: true, align: 'center', formatter: function (value, rec, index) {
                      var e = "";
                      if (value == 1) {
                          e = "<input id='CheckBox1' type='checkbox' name='CheckBox1' checked='checked' />";
                      } else {
                          e = "<input id='CheckBox2' type='checkbox' name='CheckBox2' />";
                      }
                      return e;
                  }
                  },
                  { field: 'CellPhone', title: '手机', width: 120, sortable: true, align: 'center' },
                  { field: 'Explain', title: '说明', width: 220, sortable: true, align: 'center' }
                ]],
                toolbar: ['-'
                , { text: '新增', iconCls: 'icon-add', handler: function () { AddOrUpdate('add'); } }, '-'
                , { text: '修改', iconCls: 'icon-edit', handler: function () { AddOrUpdate('update'); } }, '-'
                , { text: '删除', iconCls: 'icon-remove', handler: function () { var ids = []; var rows = me.datagrid1.datagrid('getSelections'); if (rows.length == 0) { showError('请选择一条记录进行操作!'); } else { for (var i = 0; i < rows.length; i++) { ids.push(rows[i][me.idFiled]); } Delete(ids.join(',')); } } }, '-'
                ],
                onBeforeLoad: function (param) {
                    param.ParentID = me.edit_form.find('#ParentID').val();
                    me.search_form.find('input').each(function (index) { param[this.name] = $(this).val(); });
                }
            });
        }

        function AddOrUpdate(action) {

            switch (action) {
                case 'add':
                    $('#UserID').val('0');
                    me.edit_form.find('#State').attr('checked', true);
                    me.edit_form.find('#Name,#Code,#Url').val('');

                    me.edit_window.find('div[region="south"]').css('display', 'block');
                    me.edit_window.window('open');
                    break;
                case 'updateState':
                    $.ajax({
                        url: me.actionUrl + '?Method=UpdateState',
                        data: { UserID: arguments[1], State: arguments[2] },
                        success: function (returnData) {
                            if (returnData) {
                                if (returnData.isOk == 1) {
                                    me.datagrid1.datagrid('reload');
                                } else {
                                    showError(returnData.message);
                                }
                            }
                        }
                    });
                    break;
                case 'update':
                    var selectedRows = me.datagrid1.datagrid('getSelections');
                    if (selectedRows.length > 0) {
                        $.ajax({
                            url: me.actionUrl + '?Method=AddOrUpdate&UserID=' + selectedRows[0][me.idFiled],
                            success: function (data) {
                                me.edit_form.form('load', data);
                            }
                        });
                        me.edit_window.find('div[region="south"]').css('display', 'block');
                        me.edit_window.window('open');
                    } else {
                        showError('请选择一条记录进行操作!');
                        return;
                    }
                    break;
                case 'view':
                    var selectedRows = me.datagrid1.datagrid('getSelections');
                    if (selectedRows.length > 0) {
                        $.ajax({
                            url: me.actionUrl + '?Method=AddOrUpdate&UserID=' + selectedRows[0][me.idFiled],
                            success: function (data) {
                                me.edit_form.form('load', data);
                            }
                        });
                        me.edit_window.find('div[region="south"]').css('display', 'none');
                        me.edit_window.window('open');
                    } else {
                        showError('请选择一条记录进行操作!');
                        return;
                    }
                    break;
            }
        }

        function SaveData() {
            if (me.edit_form.form('validate')) {
                $.ajax({
                    url: me.actionUrl + '?Method=Save',
                    data: me.edit_form.serialize(),
                    success: function (returnData) {
                        if (returnData) {
                            if (returnData.isOk == 1) {
                                showInfo(returnData.message);
                                me.datagrid1.datagrid('reload');
                                me.tree1.tree('reload');
                            } else {
                                showError(returnData.message);
                            }
                        }
                    }
                });
            }
        }

        function Delete(ids) {
            $.messager.confirm('提示信息', '确认要删除选择项？【' + ids + '】', function (isClickedOk) {
                if (isClickedOk) {
                    $.ajax({
                        url: me.actionUrl,
                        data: { Method: 'Delete', ids: ids },
                        success: function (returnData) {
                            if (returnData) {
                                if (returnData.isOk == 1) {
                                    showInfo(returnData.message);
                                    me.datagrid1.datagrid('reload');
                                    me.tree1.tree('reload');
                                } else {
                                    showError(returnData.message);
                                }
                            }
                        }
                    });
                }
            })
        }

        //=================================设置功能开始
        function SetMenuFun(UserID) {
            $('#menu_window_datagrid').datagrid({
                sortName: "DictID",
                idField: "MenuFunID",
                pagination: false,
                url: '../SysMenu.ashx?Method=ListByMenu&state=1',
                queryParams: { UserID: UserID },
                columns: [[
                  { field: 'DictID', title: '功能ID', width: 60, sortable: true, align: 'center' },
                  { field: 'Name', title: '功能名', width: 100, sortable: true, align: 'center' },
                  { field: 'Code', title: '功能编码', width: 100, sortable: true, align: 'center' },
                  { field: 'MenuFunID', title: '绑定功能', width: 60, sortable: true, align: 'center', formatter: function (value, rowData, rowIndex) {
                      var strReturn = '';
                      var MenuFunID = rowData['MenuFunID'] == '' ? 0 : rowData['MenuFunID'];
                      var FunID = rowData['DictID'];
                      if (MenuFunID) {
                          strReturn = '<a href="javascript:void(0)" class="l-btn-text icon-ok" title="点击取消绑定功能"  onClick="UpdateState(' + MenuFunID + ',' + FunID + ',' + UserID + ');" style="padding-left:20px;" >取消</a>';
                      } else {
                          strReturn = '<a href="javascript:void(0)" class="l-btn-text icon-useless"  title="点击绑定功能" onClick="UpdateState(' + MenuFunID + ',' + FunID + ',' + UserID + ');" style="padding-left:20px;" >绑定</a>';
                      }
                      return strReturn;
                  }
                  }
                ]]
            });
            $('#menu_window').window({ title: "绑定菜单功能" }).window('open');
        }
        //提交操作
        function UpdateState(MenuFunID, FunID, UserID) {
            $.ajax({
                url: '../ashx/SysMenuFun.ashx?Method=Save',
                data: { MenuFunID: MenuFunID, UserID: UserID, FunID: FunID },
                success: function (returnData) {
                    if (returnData) {
                        if (returnData.isOk == 1) {
                            $('#menu_window_datagrid').datagrid('reload');
                        } else {
                            showError(returnData.message);
                        }
                    }
                }
            });
        }
        function Search() {
            //获取查询参数，并附值，附值成功后翻页将自动使用此参数值，如果控件项值改变需重新调用此方法才能生效
            var queryParameter = $('#datagrid1').datagrid("options").queryParams;
            queryParameter.adds = $("#adds").val();
            $('#datagrid1').datagrid("reload");
        }
    </script>
</head>
<body class="easyui-layout">
    <noscript>
        <div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px;
            width: 100%; text-align: center;">
            <img src="../images/noscript.gif" alt='抱歉，请开启脚本支持！' />
        </div>
    </noscript>
    <div region="west" hide="true" split="true" title="导航树" style="width: 180px;">
        <ul id="tree1">
        </ul>
    </div>
    <div title="数据列表" region="center" border="false">
        <div region="north" title="查询条件" split="false" style="height: 30px;">
            <div style="margin-top: 8px; margin-left: 5px;">
                用户名：
                <input type="text" name="adds" id="adds" class="editbox easyui-validatebox" maxlength="30"
                    style="width: 150px" />
                <a id="btnSearch" class="easyui-linkbutton" icon="icon-search" href="javascript:Search()">
                    查询</a>
            </div>
        </div>
        <table id="datagrid1">
        </table>
    </div>
    <div id="edit_window" class="easyui-window" closed="true" title="数据维护窗口" style="width: 500px;
        height: 400px; padding: 5px;">
        <div class="easyui-layout" fit="true" style="border: 0px; display: none" id="aa">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <form id="edit_form" name="edit_form" method="post">
                <div title="隐藏参数">
                    <input type="hidden" id="UserID" name="UserID" value="0" />
                </div>
                <table cellpadding="3" align="center">
                    <tr>
                        <td>
                            URL：
                        </td>
                        <td>
                            <input id="Url" name="Url" value="" class="easyui-validatebox" required="true" maxlength="200"
                                style="width: 250px;" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            编码：
                        </td>
                        <td>
                            <input id="Code" name="Code" type="text" class="easyui-validatebox" required="true"
                                maxlength="30" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            名称：
                        </td>
                        <td>
                            <input id="Name" name="Name" type="text" class="easyui-validatebox" required="true"
                                maxlength="30" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            状态：
                        </td>
                        <td align="left">
                            <input type="checkbox" id="State" name="State" value="1" checked="checked" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            父ID：
                        </td>
                        <td>
                            <input id="ParentID" name="ParentID" class="easyui-numberbox" min="0" style="width: 50px;"
                                reuired="true" maxlength="20" value="0" />
                            排序：
                            <input id="FSort" name="FSort" class="easyui-numberbox" min="0" style="width: 50px;"
                                reuired="true" maxlength="20" value="0" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            层级：
                        </td>
                        <td>
                            <input id="Level" name="Level" class="easyui-numberbox" min="1" reuired="true" style="width: 50px;"
                                maxlength="10" value="1" />
                            <input id="LevelPath" name="LevelPath" class="easyui-validatebox" reuired="true"
                                maxlength="50" value="0." />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            备注：
                        </td>
                        <td>
                            <textarea id="Remark" name="Remark" cols="40" class="easyui-validatebox" validtype="length[0,200]"
                                rows="3"></textarea>
                        </td>
                    </tr>
                </table>
                </form>
            </div>
            <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
                <a id="btn_edit_ok" icon="icon-save" href="javascript:void(0)">确定</a> <a id="btn_edit_cancel"
                    icon="icon-cancel" href="javascript:void(0)">关闭</a>
            </div>
        </div>
    </div>
    <div id="search_window" class="easyui-window" closed="true" title="查询窗口" style="width: 350px;
        height: 200px; padding: 5px;">
        <div class="easyui-layout" fit="true" style="border: 0px; display: none" id="bb">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <form id="search_form" method="post">
                <table>
                    <tr>
                        <td>
                            名称：
                        </td>
                        <td>
                            <input name="Name" id="Name" style="width: 150px;" />
                        </td>
                    </tr>
                </table>
                </form>
            </div>
            <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
                <a href="javascript:void(0)" id="btn_search_ok" icon="icon-ok">确定</a> <a href="javascript:void(0)"
                    id="btn_search_cancel" icon="icon-cancel">关闭</a>
            </div>
        </div>
    </div>
    <div id="menu_window" class="easyui-window" closed="true" title="设置功能" style="width: 400px;
        height: 400px;">
        <table id="menu_window_datagrid">
        </table>
    </div>
    <form id="form1" runat="server">
    <div>
    </div>
    </form>
</body>
</html>
