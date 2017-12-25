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
    <title>综合查询-缺货补货记录</title>
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
            idFiled: 'StockID',
            actionUrl: 'OutOfStockCommon.ashx'
        };

        $(function () {
            pageInit();
            loadGrid();
            three();
        });


        function three() {
            $("#SelectCounty").width("110");
            $("#SelectTownshipStreet").width("110");
            $("#SelectCommunity").width("110");
            $("#SelectCounty > option").remove();
            //省份查询方法
            $.ajax({
                url: "../CommonUi.ashx?method=Three_queryprovince",
                type: 'POST',
                dataType: "json",
                success: function (ret) {
                    $("#SelectCounty").find("option").remove().end().append("<option value='0'>全部</option>").val(0); ;
                    for (i = 0; i < ret.length; ++i) {
                        $("#SelectCounty").append("<option value='" + ret[i].id + "'>" + ret[i].name + "</option>");
                    }
                    $("#SelectCounty").val(0);
                },
                error: function (x, e) {
                    alert(x.responseText);
                }
            });
            //城市查询方法
            $("#SelectCounty").change(function () {
                $("#SelectTownshipStreet > option").remove();
                $("#SelectCommunity > option").remove();
                if ($(this).val() == 0) {
                    $("#SelectTownshipStreet").find("option").remove().end().append("<option value='0'>全部</option>").val(0);
                    $("#SelectCommunity").find("option").remove().end().append("<option value='0'>全部</option>").val(0);
                } else {
                    $.ajax({
                        url: "../CommonUi.ashx?method=Three_city",
                        type: 'POST',
                        dataType: "json",
                        data: { "ID": $(this).val() },
                        success: function (ret) {
                            $("#SelectTownshipStreet").find("option").remove().end().append("<option value='0'>全部</option>").val(0);
                            $("#SelectCommunity").find("option").remove().end().append("<option value='0'>全部</option>").val(0);
                            for (i = 0; i < ret.length; ++i) {
                                $("#SelectTownshipStreet").append("<option value='" + ret[i].id + "'>" + ret[i].name + "</option>");
                            }
                            $("#SelectTownshipStreet").val(0);
                        },
                        error: function (x, e) {
                            alert("获取下级分类失败");
                        }
                    });
                }
            });
            //区县查询方法
            $("#SelectTownshipStreet").change(function () {
                $("#SelectCommunity > option").remove();
                if ($(this).val() == 0) {
                    $("#SelectCommunity").find("option").remove().end().append("<option value='0'>全部</option>").val(0);
                } else {
                    $.ajax({
                        url: "../CommonUi.ashx?method=Three_area",
                        type: 'POST',
                        dataType: "json",
                        data: { "ID": $(this).val() },
                        success: function (ret) {
                            $("#SelectCommunity").find("option").remove().end().append("<option value='0'>全部</option>").val(0); ;
                            for (i = 0; i < ret.length; ++i) {
                                $("#SelectCommunity").append("<option value='" + ret[i].id + "'>" + ret[i].name + "</option>");
                            }
                            $("#SelectCommunity").val(0);
                        },
                        error: function (x, e) {
                            alert("获取下级分类失败");
                        }
                    });
                }
            });
        }

        function pageInit() {
            me.edit_window = $('#edit_window');
            me.edit_form = me.edit_window.find('#edit_form');
            me.search_window = $('#search_window');
            me.search_form = me.search_window.find('#search_form');
            me.datagrid1 = $('#datagrid1');
        }

        //加载列表
        function loadGrid() {
            me.datagrid1.datagrid({
                sortName: me.idFiled,
                idField: me.idFiled,
                url: me.actionUrl + '?Method=OutOfStockReplenishmentRecord_List',
                frozenColumns: [[
                  { field: 'TerminalID', title: '终端编号', width: 80, sortable: true, align: 'center' }
                  ]],
                columns: [[
                  { field: 'ShengName', title: '省', width: 80, sortable: true, align: 'center' },
                  { field: 'ShiName', title: '市', width: 80, sortable: true, align: 'center' },
                  { field: 'QxName', title: '区（县）', width: 100, sortable: true, align: 'center' },
                  { field: 'XiangName', title: '街道（乡镇）', width: 120, sortable: true, align: 'center' },
                  { field: 'CunName', title: '社区', width: 150, sortable: true, align: 'center' },
                  { field: 'DistributionPoints', title: '发放点', width: 250, sortable: true, align: 'left' },
                  { field: 'ContraceptivesName', title: '缺货名称', width: 100, sortable: true, align: 'center' },
                  { field: 'ShortageTime', title: '缺货时间', width: 140, sortable: true, align: 'center' },
                  { field: 'RestockTime', title: '补货时间', width: 140, sortable: true, align: 'center' }
                ]],
                onBeforeLoad: function (param) {
                    me.search_form.find('input').each(function (index) {
                        param[this.name] = $(this).val();
                    });
                }
            });
        }

        function Search() {
            //获取查询参数，并附值，附值成功后翻页将自动使用此参数值，如果控件项值改变需重新调用此方法才能生效
            var queryParameter = $('#datagrid1').datagrid("options").queryParams;
            queryParameter.SelectCounty = $("#SelectCounty").val();
            queryParameter.SelectTownshipStreet = $("#SelectTownshipStreet").val();
            queryParameter.SelectCommunity = $("#SelectCommunity").val();
            queryParameter.SelectDistributionPoints = $("#SelectDistributionPoints").val();
            queryParameter.tbSearchVD1 = $("#tbSearchVD1").val();
            queryParameter.tbSearchVD2 = $("#tbSearchVD2").val();
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
    <div region="north" title="查询条件" split="false" style="height: 70px;">
        <div style="margin-top: 8px; margin-left: 5px;">
            选择发放点：
            <select id="SelectCounty">
                <option value="0">全部</option>
            </select>
            <select id="SelectTownshipStreet">
                <option value="0">全部</option>
            </select>
            <select id="SelectCommunity">
                <option value="0">全部</option>
            </select>
            <input type="text" name="SelectDistributionPoints" id="SelectDistributionPoints"
                class="editbox easyui-validatebox" maxlength="100" style="width: 150px" />
            &nbsp; &nbsp; 缺货时间：
            <input name="tbSearchVD1" type="text" id="tbSearchVD1" style="width: 85px;" class="easyui-datetimebox" />至
            <input name="tbSearchVD2" type="text" id="tbSearchVD2" style="width: 85px;" class="easyui-datetimebox" />&nbsp;
            <a id="btnSearch" class="easyui-linkbutton" icon="icon-search" href="javascript:Search()">
                查询</a>
        </div>
    </div>
    <div title="数据列表" region="center" border="false">
        <table id="datagrid1">
        </table>
    </div>
    <form id="form1" runat="server">
    </form>
</body>
</html>
