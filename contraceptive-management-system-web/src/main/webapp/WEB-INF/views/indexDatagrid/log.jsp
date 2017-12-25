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
<title>实时监控主页</title>
<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet"
	type="text/css" />
<script src="js/jquery-easyui-1.3.4/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<script src="js/commonJS1.js" type="text/javascript"></script>
<script type="text/javascript">
$.extend($.fn.combobox.defaults, {panelHeight:true});
	$(function(){
		$("#tree").tree({
			onSelect:function(node){
				$('#dg').datagrid({
            		url: 'logdatagrid',
            		queryParams:{
            			logType:node.id
            		}
            	});
			}
		});
	});
	function deleteLog(){
		var row = $('#dg').datagrid('getSelected');
		if (row)
		{
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){    
			        $.post('deleteLog',{id:row.id},function(result){
			        	if(result){
							$("#dg").datagrid('reload');
							$.messager.show({
								title:'提示',
								msg:'删除成功!'
							});
						}else{
							$.messager.show({
								title:'提示',
								msg:'删除失败!'
							});
						}   
			        });    
			    }    
			});
		}
		else
		{
			$.messager.alert('提示','请您选择一条数据!');
		}
	}
	function selectLog()
	{
		var row = $("#dg").datagrid('getSelected');
		if(row)
		{
			$('#log-dialog').dialog('open').dialog('setTitle','查看日志');
			$('#fm').form('load',row);
		}
		else
		{
			$.messager.alert('提示','请您选择一条数据!');
		}
	}
	function searchLog()
	{
		$('#dialog1').dialog('open').dialog('setTitle','查询窗口');
	}
	function saveSearch()
	{
		var logtitle = $("#title").val();
		$("#dg").datagrid({
			url:"searchlogbytitle",
			queryParams:{
				title:logtitle
			}
		});
		$('#dialog1').dialog('close');
	}
</script>
</head>
<body style="margin: 0px; padding: 0px;" class="easyui-layout">
	<div data-options="region:'west'" title="导航树" style="width: 280px">
		<ul id="tree" class="easyui-tree">
			<li>
				<span>数据库日志</span>
				<ul>
					<li id="LoginSuccess" >登录成功</li>
					<li id="ExceptionOperation">异常操作</li>
					<li id="LoginFailure">登录失败</li>
					<li id="LogoutOperation">退出操作</li>
					<li id="UnauthorizedOperation">超权操作</li>
					<li id="OtherOperation">其他操作</li>
				</ul>
			</li>
		</ul>
	</div>
	<div data-options="region:'center',title:'数据列表'">
		<table id="dg" class="easyui-datagrid"
			style="width: 570px; height: 250px;"
			data-options="striped:true,checkbox:true,toolbar:'#toolbar',fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,50,100],pagination:true,url:'logdatagrid',singleSelect:true,pageSize:25">
			<thead>
				<tr>
					<th field=id align="center" data-options="fixed:true,width:80">ID</th>
					<th field="title" align="left" data-options="fixed:true,width:80">标题</th>
					<th field="titleDescription" align="left" data-options="fixed:true,width:230">标题描述</th>
					<th field="uri" align="left" data-options="fixed:true,width:200">访问地址</th>
					<th field="operationDate" align="center" data-options="fixed:true,width:160">操作时间</th>
					<th field="ip" align="center" data-options="fixed:true,width:120">IP地址</th>
					<th field="username" align="center" data-options="fixed:true,width:80">操作人</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteLog();">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="selectLog()">查看</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchLog()">查找</a>
	</div>
	<div id="log-dialog" class="easyui-dialog" data-options="closed:true" style="width:300px;height:450px;padding:10px">
	 	<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>标题：</label><br />
				<input name="title" class="easyui-validatebox"/>
			</div><br />
			<div class="fitem">
				<label>访问地址：</label><br />
				<input name="uri" class="easyui-validatebox"/>
			</div><br />
			<div class="fitem">
				<label>操作时间：</label><br />
				<input name="operationDate" class="easyui-validatebox"/>
			</div><br />
			<div class="fitem">
				<label>IP地址：</label><br />
				<input name="ip" class="easyui-validatebox"/>
			</div><br />
			<div class="fitem">
				<label>操作人：</label><br />
				<input name="username" class="easyui-validatebox"/>
			</div><br />
			<div class="fitem">
				<label style="text-align: right;">标题描述：</label><br />
				<textarea id="titleDescription" class="easyui-validatebox validatebox-text" rows="3" validtype="length[0,300]" cols="25" name="titleDescription" ></textarea>
			</div>
		</form>
	</div>
	<!-- 通过日志标题查找 -->
	<div id="dialog1" class="easyui-dialog" style="width: 288px;height: 150px;"
		closed="true" buttons="#dialogButtons2">
			<form id="form" method="post">
				<div class="fitem" style="padding-top: 20px;padding-left: 10px">
					<label>日志标题：</label>
					<input id="title" name="title" class="easyui-validatebox" />
				</div>
			</form>
		<div id="dialogButtons2" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSearch()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog1').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>