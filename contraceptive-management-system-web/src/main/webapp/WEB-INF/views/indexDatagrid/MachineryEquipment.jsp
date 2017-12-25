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
		<title>实时监控主页</title>
		<link href="js/jquery-easyui-1.3.5/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.5/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	     <script type="text/javascript">
	     $.extend($.fn.combobox.defaults, {panelHeight:true});
	     window.location.reload(true); 
            function formatterOfflineDate(value,row,index){
            	if(row.machineryEquipment.deviceNo){
            		return "<font color='red'>"+row.machineryEquipment.deviceNo+"</font>";
            	}
            }
            function formatterType(value,row,index){
				if(row.machineryEquipment.terminalType){
					return row.machineryEquipment.terminalType;
				}
            }
            function formatterLoaction(value,row,index){
            	if(row.machineryEquipment.loaction){
            		if(row.machineryEquipment.loaction){
            			return row.machineryEquipment.loaction;
            		}
            	}
            }
            </script>
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout">
	
		<div data-options="region:'north',title:'条件查询',split:true" style="height:100px;"></div>   
   		<div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
   		<!--  <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
    	<div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   -->
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,fit:true,loadMsg:'加载中，请稍后...',pageList:[5,10,15],pagination:true,url:'offlineME',singleSelect:true,pageSize:10">
			<thead>
				<tr>
					<th field="deviceNo">终端编号</th>
					<th field="loaction">省</th>
					<th field="loaction">市</th>
					<th field="loaction">区（县）</th>
					<th field="loaction">街道（乡镇）</th>
					<th field="loaction">社区</th>
					<th field="distributionPoints">发放点</th>
					<th field="MachineryEquipmentStateInfo.OfflineDate" data-options="formatter:formatterOfflineDate">离线时间</th>
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>