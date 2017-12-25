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
		<title>状态变化监控</title>
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
	      function formatterDeviNo(value,row,index){
          	if(row.machineryEquipment.deviceNo){
          		return "<font color='red'>"+row.machineryEquipment.deviceNo+"</font>";
          	}
          }
          function formatterDistributionPoints(value,row,index){
				 if(row.machineryEquipment.distributionPoints){
					return row.machineryEquipment.distributionPoints;
				} 
          }
            
            /*
        		定时刷新数据
       		 */
        	function refreshDatagrid(){
	   			$('#dg').datagrid({
	   				reload:"stateChangerRecordSearchArea"
	   			});
     		}
            
            function submitForm(){
            	params = {};
            	params.cityId = <spring:message code="City.no"/>;
            	params.distributionPoints = $("#distributionPoints").val().length==0?-1:$("#distributionPoints").val();
            	params.countryId = $("#country").combobox('getValue') != "全部"&&$("#country").combobox('getValue').length>0?$("#country").combobox('getValue'):-1;
            	params.townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"&&$("#townshipStreet").combobox('getValue')>0?$("#townshipStreet").combobox('getValue'):-1;
            	params.communityId = $("#community").combobox('getValue') != "全部"
					&& $("#community").combobox('getValue') > 0 ? $(
					"#community").combobox('getValue') : -1;
            	$('#dg').datagrid({
            		url: 'stateChangerRecordSearchArea',
            		queryParams:params
            	});
            }
            </script> 
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout">
		<div data-options="region:'north',title:'条件查询',split:true" style="height:80px;text-align:left;padding:10px;" align="center">
			 <form id="form" method="post">  
			    选择发放点：<!-- <input id="city" class="easyui-combobox" value="全部" name="id"  data-options="panelHeight:'auto',valueField:'id',textField:'name',loadFilter:filterDataFun"/> -->
			   <jsp:include page="../../base/areaSearch.jsp" />
			  	<a href="#" class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>
			   </form>
		</div>   
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,40,50],pagination:true,url:'stateChangerRecordSearchArea',singleSelect:true,pageSize:25">
				<thead frozen="true">			
				<tr>
					<th field="machineryEquipment.deviceNo" data-options="align:'center',fixed:true,width:80,formatter:formatterDeviNo,sortable:true">终端编号</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name" data-options="width:80,align:'center',fixed:true,formatter:privenceArea">省</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.name" data-options="width:80,align:'center',fixed:true,formatter:cityArea">市</th>
					<th field="machineryEquipment.area.parentArea.parentArea.name" data-options="width:80,fixed:true,align:'center',formatter:countryArea">区（县）</th>
					<th field="machineryEquipment.area.parentArea.name" data-options="width:100,fixed:true,align:'center',formatter:townStreetArea">街道（乡镇）</th>
					<th field="machineryEquipment.area.name" data-options="width:120,fixed:true,align:'center',formatter:communityArea">社区</th>
					<th field="machineryEquipment.distributionPoints" data-options="fixed:true, width:240,formatter:formatterDistributionPoints">发放点</th>
					<th field=hanpenDate data-options="align:'center',fixed:true,width:160,sortable:true">改变时间</th>
					<th field="state" data-options="align:'center',formatter:stateFormatter">分类</th>
					<th field="detail" data-options="align:'center'">详细信息</th>
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>