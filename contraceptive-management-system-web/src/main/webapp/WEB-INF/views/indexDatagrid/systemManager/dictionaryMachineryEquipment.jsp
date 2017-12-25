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
<title>字典管理</title>
<link href="js/jquery-easyui-1.3.5/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet"
	type="text/css" />
<script src="js/jquery-easyui-1.3.5/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="js/commonJS1.js" type="text/javascript"></script>
<style type="text/css">
.diva:hover {
	background-color: #ddd;
	font-size: 18px;
	border: dotted 1px black;
}
</style>
<script type="text/javascript">

	$(function() {
		$('#diaLayout').layout();
		$('#diaLayout').layout('collapse', 'west');
	});
	//添加其他字典选项卡
	function addAnther(title, url) {
		title = "<img src='images/menu.png' style='border:0px'/>"+title;
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tabs').tabs('add', {
				title : title,
				content : content,
				closable : true,
				fit : true
			});
		}
	}
	//添加市区选项卡
	function addArea(title, url) {
		title = "<img src='images/menu.png' style='border:0px'/>"+title;
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tabs').tabs('add', {
				title : title,
				content : content,
				closable : true,
				fit : true
			});
		}
	}
	//添加民族选项卡
	function addTabs(title, url) {
		title = "<img src='images/menu.png' style='border:0px'/>"+title;
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tabs').tabs('add', {
				title : title,
				content : content,
				closable : true,
				fit : true
			});
		}
	}
	//添加乡镇选项卡
	function addTowns(title, url) {
		title = "<img src='images/menu.png' style='border:0px'/>"+title;
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tabs').tabs('add', {
				title : title,
				content : content,
				closable : true,
				fit : true
			});
		}
	}
</script>

</head>
<body>
	<div id="dicLayout" class="easyui-layout"
		style="width: 600px; height: 400px;">
		<div region="north" title="North Title" split="true"
			style="height: 100px;"></div>
		<div region="south" title="South Title" split="true"
			style="height: 100px;"></div>
		<div region="east" iconCls="icon-reload" title="East" split="true"
			style="width: 100px;"></div>
		<div region="west" split="true" title="West" style="width: 100px;"></div>
		<div region="center" title="center title"
			style="padding: 5px; background: #eee;"></div>
	</div>
</body>
<body class="easyui-layout">
	<div region="west" split="true" title="导航菜单" style="width: 260px;">
		<div id="accordion" data-options="fit:true" class="easyui-accordion">
			<div title="基本字典" data-options="striped:true,iconCls:'icon-sys'"
				style="overflow: auto; padding: 10px;">
				<div class="diva" style="margin-top: 10px; margin-left: 20px;"
					onclick="addTabs('民族字典', 'nationDictionaryMachineryEquipment')" data-options="iconCls:'icon-ok'">
					<a style="text-decoration: none; color: black;"
						href="javascript:void(0);"><img src='images/menu.png' style='border:0px;'/>民族字典</a>
				</div>
				<div style="margin-top: 10px; margin-left: 20px;" class="diva"
					onclick="addArea('区县字典','areaDictionaryMachineryEquipment')">
					<a style="text-decoration: none; color: black;"
						href="javascript:void(0);"><img src='images/menu.png' style='border:0px'/>区县字典</a>
				</div>
				<div class="diva" style="margin-top: 10px; margin-left: 20px;"
					onclick="addArea('乡镇字典','townsDictionaryMachineryEquipment')">
					<a style="text-decoration: none; color: black;"
						href="javascript:void(0);"><img src='images/menu.png' style='border:0px'/>乡镇字典</a>
				</div>
				<div class="diva" style="margin-top: 10px; margin-left: 20px;"
					onclick="addArea('村级字典','communityDictionaryMachineryEquipment')">
					<a style="text-decoration: none; color: black;"
						href="javascript:void(0);"><img src='images/menu.png' style='border:0px'/>村级字典</a>
				</div>
			</div>
		</div>
	</div>
	<div id="welocme" region="center" title="" style="padding: 0px;">
		<div data-options="fit:true" id="tabs" class="easyui-tabs"
			style="width: 500px; height: 250px;">
			<div title="欢迎使用"
				style="padding: 20px; text-align: center; margin: auto;">
				<img src="images/welcome.png" alt="正在加载" />
			</div>
		</div>
	</div>
</body>
</html>