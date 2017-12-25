<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
	<head>
		<base href="${basePath}"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>角色管理</title>
		<link href="js/jquery-easyui-1.3.5/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
        <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet" type="text/css" />
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
			margin-bottom:5px;
			}
			.fitem label{
			display:inline-block;
			width:80px;
			}
		</style>
	    <script src="js/jquery-easyui-1.3.5/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <script src="FusionCharts/FusionCharts.js" type="text/javascript"></script>
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	    <script src="js/formater.js" type="text/javascript"></script>
	    <script src="js/roleManager.js" type="text/javascript"></script>
	    <script type="text/javascript">
	    $.extend($.fn.combobox.defaults, {panelHeight:true});
	    </script>
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout">
		 <div data-options="region:'center'" >
		<!--  <audio id="media" src="1.mp3" ></audio> -->
			<table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,pagination:true,toolbar:'#dgToolbar',rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,40,50],pagination:true,url:'roleList',singleSelect:true,pageSize:25">
				<thead>
					<tr>
						 <th field="xxxx" data-options="align:'center',formatter:formatterOperate,fixed:true,width:200">操作</th>
						 <th field="state" data-options="align:'center',formatter:fromatterState,fixed:true,width:80">状态</th>
						 <th field="id" data-options="align:'center',fixed:true,width:80">ID</th>
						 <th field="name" data-options="align:'left',fixed:true,width:80">角色名</th>
						 <th field="description" data-options="align:'left',fixed:true,width:200">描述</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="dgToolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRole()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRole()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="selectRole()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchRole()">查找</a>
		</div>
		<!-- 添加 -->
		<div id="dialog" class="easyui-dialog" style="width: 400px;height: 400px;padding: 10px 20px" 
		closed="true" buttons="#dialogButtons1">
			<form id="fm" method="post" >
				<input type="hidden" name="id" value=""/>
				<div class="fitem">
					<label>角色名：</label>
					<input name="name" class="easyui-validatebox" required="true" />
				</div>
				<div class="fitem">
					<label>状态：</label>
					<input type="hidden" name="state"/>
					<input type="checkbox" name="state_temp"/>
				</div>
				<div class="fitem">
					<label>描述：</label>
					<textarea id="note"	class="easyui-validatebox validatebox-text" rows="3" validtype="length[0,300]" cols="25" name="description"></textarea>
				</div>
			</form>
		<div id="dialogButtons1" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog').dialog('close')">取消</a>
		</div>
	</div>
	<!-- 通过角色名查找 -->
	<div id="dialog1" class="easyui-dialog" style="width: 288px;height: 150px;"
		closed="true" buttons="#dialogButtons2">
			<form id="form" method="post"  style="margin-top: 20px;margin-left: 20px">
				<label>角色名：</label>
				<input name="name" id="rolename" class="easyui-validatebox" required="true" />
			</form>
		<div id="dialogButtons2" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSearch()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog1').dialog('close')">取消</a>
		</div>
	</div>
	<!-- 角色下的用户 -->
	<div id="dialog2" class="easyui-dialog" style="width: 488px;height: 365px;" closed="true">
		<table id="dg1" class="easyui-datagrid" data-options="striped:true,rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,40,50],pagination:true,url:'',singleSelect:true,pageSize:25">
			<thead>
				<tr>
					 <th field="id" data-options="align:'center'">用户ID</th>
					 <th field="username" data-options="align:'center'">用户名</th>
					 <th field="name" data-options="align:'center'">登录名</th>
					 <th field="department.name" data-options="align:'center',formatter:formatterName">部门</th>
				</tr>
			</thead>
		</table>	
	</div>
	<!-- 所有菜单 -->
	<div id="dialog3" class="easyui-dialog" style="width: 488px;height: 365px;" closed="true" buttons="#dialogButtons3">
		 <ul id="tt"></ul>
		<div id="dialogButtons3" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePermission()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('.easyui-dialog').dialog('close')">取消</a>
		</div>
	</div>
	</body>
</html>