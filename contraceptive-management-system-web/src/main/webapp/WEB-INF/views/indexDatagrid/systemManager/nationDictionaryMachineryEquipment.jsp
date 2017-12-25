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
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>避孕药具自助发放机综合管理平台</title>
<link href="js/jquery-easyui-1.3.5/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet"
	type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-easyui-1.3.5/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="js/commonJS1.js" type="text/javascript"></script>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',title:'数据列表'">

		<table class="easyui-datagrid" style="width: 400px; height: 250px"
			data-options="striped:true,toolbar:'#tb',striped:true,loadMsg:'加载中，请稍后...',rownumbers:true,pagination:true,fit:true,url:'datagridNation',singleSelect:true,pagination:true,pageList:[10,25,40,80],pageSize:80">
			<thead>
				<tr>
					<th data-options="field:'code',width:100,align:'center'">编码</th>
					<th data-options="field:'name',width:100,align:'left'">民族</th>
				</tr>
			</thead>
		</table>

	</div>
</body>
</html>