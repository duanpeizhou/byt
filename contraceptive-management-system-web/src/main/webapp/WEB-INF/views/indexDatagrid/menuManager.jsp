<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单管理</title>
<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet"
	type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-easyui-1.3.4/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="js/commonJS1.js" type="text/javascript"></script>
<script src="js/menuManager.js" type="text/javascript"></script>
<script type="text/javascript">
	 $.extend($.fn.combobox.defaults, {panelHeight:true});
</script>
 <style type="text/css">
			#fm{
			margin:0;
			padding:10px 30px;
			}
			.ftitle{
			font-size:14px;
			font-weight:bold;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
			}
			.fitem{
			margin-bottom:10px;
			}
			.fitem label{
			display:inline-block;
			width:80px;
			}
		</style>
</head>
<body style="margin: 0px; padding: 0px;" class="easyui-layout">
	<div id="westId" data-options="region:'west'" title="导航树" style="width: 280px">
		<!-- <ul id="menuTree">
			
		</ul> -->
	</div>
	<div data-options="region:'center',title:'数据列表'">
		<table id="dg" class="easyui-datagrid"
			style="width: 570px; height: 250px;"
			data-options="onLoadSuccess:selectOneLine,striped:true,toolbar:'#toolbar',fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,50,100],pagination:true,url:'menuManagerDatagrid',singleSelect:true,pageSize:25">
			<thead>
				<tr>
					<th field=id align="center" data-options="fixed:true,width:80">ID</th>
					<th field="name" align="left"  data-options="fixed:true,width:120">菜单名称</th>
					<th field="url"  data-options="fixed:true,width:200,align:'left'">URL路径</th>
					<th field="level" align="center"  data-options="fixed:true,width:80">菜单级别</th>
					<th field="description" align="center"  data-options="fixed:true,width:200">菜单说明</th>
					<th field="order" align="center"  data-options="fixed:true,width:80">排序</th>
					<th field="uri" align="center" data-options="formatter:number,fixed:true,width:80">子菜单个数</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar" style="padding: 5px; height: auto">
			<div style="margin-bottom: 5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMenu();">添加</a> 
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateMenu();">修改</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMenu();">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="selectMenu()">查看</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchMenu()">查找</a>
			</div>
		</div>
	</div>
	 <div id="dlg" class="easyui-dialog" data-options="closed:true,buttons:'#buttons'" style="width:300px;height:400px;padding:10px">
	 	<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>URL：</label>
					<input name="url" class="easyui-validatebox" required="true" />
				</div>
				<div class="fitem">
					<label>所在级别：</label>
					<select name="parentMenu.id" style="width: 155px;">
						<option value="1">动态监控</option>
						<option value="2">综合查询</option>
						<option value="3">发放管理</option>
						<option value="4">统计分析</option>
						<option value="5">系统管理</option>
					</select>
				</div>
				<div class="fitem">
					<label>菜单名称：</label>
					<input name="name" class="easyui-validatebox" />
				</div>
				<div class="fitem">
					<label>菜单排序：</label>
					<input name="order" class="easyui-validatebox" />
				</div>
				<div class="fitem">
					<label>菜单说明：</label>
				<textarea id="description"
				class="easyui-validatebox validatebox-text" rows="3"
				validtype="length[0,300]" cols="25" name="description" ></textarea>
				</div>
			</form>
	</div>
	<div id="buttons">
		 <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" onclick="javascript:saveMenu();">保存</a>
		<a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<div id="dialog1" class="easyui-dialog" style="width: 288px;height: 150px;"
		closed="true" buttons="#dialogButtons2">
			<form id="form" method="post">
				<div class="fitem" style="padding-top: 20px;padding-left: 10px">
					<label>菜单名称：</label>
					<input id="menuname" name="name" class="easyui-validatebox" />
				</div>
			</form>
		<div id="dialogButtons2" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSearch()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog1').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>