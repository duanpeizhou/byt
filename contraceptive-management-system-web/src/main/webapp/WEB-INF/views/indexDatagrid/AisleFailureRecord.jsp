<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
	<head>
		<base href="${basePath}"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>货道故障记录</title>
		<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	      <script type="text/javascript">
	      $.extend($.fn.combobox.defaults, {panelHeight:true});
            function deviceNoFormatter(value,row,index){
            	if(row.machineryEquipment.deviceNo){
            		if(row.machineryEquipment.deviceNo){
            			return row.machineryEquipment.deviceNo;
            		}
            	}
            }
            
            function distributionPointsFormatter(value,row,index){
            	if(row.machineryEquipment.distributionPoints){
            		if(row.machineryEquipment.distributionPoints){
            			return row.machineryEquipment.distributionPoints;
            		}
            	}
            }
            function deviceTypeFormatter(value,row,index){
            	if(row.machineryEquipment.terminalType){
            		if(row.machineryEquipment.terminalType){
            			return row.machineryEquipment.terminalType;
            		}
            	}
            }
           
            function aisleIndexFormatter(value,row,index){
            	if(row.aisle.index_){
            		if(row.aisle.index_){
            			return row.aisle.index_;
            		}
            	}
            }
           
	 function submitForm(){
            	params = {};
            	params.cityId = <spring:message code="City.no"/>;
            	params.distributionPoints = $("#distributionPoints").val().length==0?-1:$("#distributionPoints").val();
        		params.startTime = $("#startTime").datetimebox("getValue").length==0?-1:$("#startTime").datetimebox("getValue");
        		params.endTime = $("#endTime").datetimebox("getValue").length==0?-1:$("#endTime").datetimebox("getValue");
            	params.countryId = $("#country").combobox('getValue') != "全部"&&$("#country").combobox('getValue').length>0?$("#country").combobox('getValue'):-1;
            	params.townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"&&$("#townshipStreet").combobox('getValue')>0?$("#townshipStreet").combobox('getValue'):-1;
            	params.communityId = $("#community").combobox('getValue') != "全部"
					&& $("#community").combobox('getValue') > 0 ? $(
					"#community").combobox('getValue') : -1;
            	$('#dg').datagrid({
            		url: 'getAisleFaultRecordSearchArea',
            		queryParams:params
            	});
            }
            function filterDataFun(data){
            	if(data)
            		data.unshift({id:-1,name:"全部"});
            	else
            		data = new Array();
            	return data;
            }
         
            </script> 
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout">
	
		<div data-options="region:'north',title:'条件查询',split:true" style="height:80px;text-align:left;padding:10px;" align="center">
			 <form id="form" method="post">  
			    选择发放点：<!-- <input id="city" class="easyui-combobox" value="全部" name="id"  data-options="panelHeight:'auto',valueField:'id',textField:'name'"/> -->
			  <jsp:include page="../base/areaSearch.jsp" />
			    货道故障时间自：<input id="startTime" class="easyui-datetimebox" name="birthday" data-options=""  style="width:90px"/>
			    到：<input id="endTime" class="easyui-datetimebox" name="birthday" data-options=""  style="width:90px"/>
			   <a href="#" class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>
			   </form>
		</div>   
   
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="striped:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,50],onLoadSuccess:selectOneLine,pagination:true,url:'getAisleFaultRecordSearchArea',singleSelect:true,pageSize:10">
			<thead frozen="true">
				<tr>
					<th field=machineryEquipment.deviceNo data-options="formatter:deviceNoFormatter,sortable:true,fixed:true,width:80" align="center">终端编号</th>
				</tr>
			</thead>				
			<thead>
				<tr>
					
					<th field=machineryEquipment.terminalType data-options="formatter:deviceTypeFormatter,fixed:true,width:80" align="center">终端类型</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name" data-options="formatter:privenceArea,fixed:true,width:80" align="center">省</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.name" data-options="formatter:cityArea,fixed:true,width:80" align="center">市</th>
					<th field="machineryEquipment.area.parentArea.parentArea.name" data-options="formatter:countryArea,fixed:true,width:80" valign="center">区（县）</th>
					<th field="machineryEquipment.area.parentArea.name" data-options="formatter:townStreetArea,fixed:true,width:100" align="center">街道（乡镇）</th>
					<th field="machineryEquipment.area.name" data-options="formatter:communityArea,fixed:true,width:120" align="center">社区</th>
					<th field="machineryEquipment.distributionPoints" data-options="formatter:distributionPointsFormatter,fixed:true,width:240">发放点</th>
					<th field="aisle.index" data-options="formatter:aisleIndexFormatter,fixed:true,width:80" align="center">缺道号</th>
					<th field="failureDate" align="center" data-options="sortable:true,fixed:true,width:160">故障时间</th>
					<th field="recoveryDate" align="center" data-options="sortable:true,fixed:true,width:160">恢复时间</th>
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>