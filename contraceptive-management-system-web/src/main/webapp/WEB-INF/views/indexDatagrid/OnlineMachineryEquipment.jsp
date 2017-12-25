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
		<title>在线设备监控</title>
		<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" />
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	      <script type="text/javascript">
	      $.extend($.fn.combobox.defaults, {panelHeight:true});
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
            function onlineDateFormatter(value,row,index){
            	if(row.machineryEquipmentState.onlineDate){
            		return row.machineryEquipmentState.onlineDate;
            	}
            }
            function signalStrengthFormatter(value,row,index){
            	if(!row.machineryEquipmentState.signalStrength){
            		return "<font color='red'>无信号</font>";
            	}else
            	if(row.machineryEquipmentState.signalStrength){
            		var val=row.machineryEquipmentState.signalStrength;
            		return "<img title='" + val + "级' src='images/signal" + val + ".png' style='cursor:pointer' />";
            	}
            }
            function deviceTemperatureFormatter(value,row,index){
            	if(!row.machineryEquipmentState.deviceTemperature){
            		return 0;
            	}else
            	if(row.machineryEquipmentState.deviceTemperature){
            		return row.machineryEquipmentState.deviceTemperature;
            	}
            }
            /*
        	定时刷新数据
        */
        	function refreshDatagrid(){
	   			$('#dg').datagrid({
	   				reload:"onlineSearchArea"
	   			});
	     		
     		}
            function submitForm(){
            	
            	params = {};
            	params.cityId = <spring:message code="City.no"/>;
            	params.countryId = $("#country").combobox('getValue') != "全部"&&$("#country").combobox('getValue').length>0?$("#country").combobox('getValue'):-1;
            	params.townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"&&$("#townshipStreet").combobox('getValue')>0?$("#townshipStreet").combobox('getValue'):-1;
            	params.distributionPoints = $("#distributionPoints").val().length==0?-1:$("#distributionPoints").val();
            	params.communityId = $("#community").combobox('getValue') != "全部"
					&& $("#community").combobox('getValue') > 0 ? $(
					"#community").combobox('getValue') : -1;
            	$('#dg').datagrid({
            		url: 'onlineSearchArea',
            		queryParams:params
            	});
            }
			function downloadData(){
            	countryId = $("#country").combobox('getValue') != "全部"&&$("#country").combobox('getValue').length>0?$("#country").combobox('getValue'):-1;
            	townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"&&$("#townshipStreet").combobox('getValue')>0?$("#townshipStreet").combobox('getValue'):-1;
            	distributionPoints = $("#distributionPoints").val().length==0?-1:$("#distributionPoints").val();
            	communityId = $("#community").combobox('getValue') != "全部"
					&& $("#community").combobox('getValue') > 0 ? $(
					"#community").combobox('getValue') : -1;
				$("<iframe src='getOnlinExcel/export?cityId="+<spring:message code="City.no"/>+"&countryId="+countryId+"&townshipStreetId="+townshipStreetId+"&communityId="+communityId+"&distributionPoints="+distributionPoints+"' width='0px' height='0px'></iframe>").appendTo(document.body);
            }
            function aisle1(value,row,index){
            	if(row.aisles[0]!=undefined){
            		return row.aisles[0].num;
            	}else{
            		return 0;
            	}
            }
            function aisle2(value,row,index){
            	if(row.aisles[1]!=undefined){
            		return row.aisles[1].num;
            	}else{
            		return 0;
            	}
            }
            function aisle3(value,row,index){
            	if(row.aisles[2]!=undefined){
            		return row.aisles[2].num;
            	}else{
            		return 0;
            	}
            }
            function aisle4(value,row,index){
            	if(row.aislesNum<4){
            		return "";
            	}
            	if(row.aisles[3]!=undefined){
            		return row.aisles[3].num;
            	}else{
            		return " ";
            	}
            }
            $.extend($.fn.combobox.defaults, {panelHeight:210});
            </script> 
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout">
	
		<div data-options="region:'north',title:'条件查询',split:true" style="height:80px;text-align:left;padding:10px;" align="center">
			 <form id="form" method="post">  
			    选择发放点：<!-- <input id="city"  class="easyui-combobox" name="id"  data-options="panelHeight:'auto',valueField:'id',textField:'name',loadFilter:filterDataFun"/> -->
			   <jsp:include page="../base/areaSearch.jsp" />
			   <a  class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>&nbsp;&nbsp;&nbsp;
			    <a  class="easyui-linkbutton" onclick="downloadData();" data-options="iconCls:'icon-download'" >导出</a>
			   </form>
		</div>   
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,40,50,100],pagination:true,url:'onlineSearchArea',singleSelect:true,pageSize:25">
			<thead frozen="true">
				<tr>
					<th field="deviceNo" data-options="align:'center',fixed:true,width:80,sortable:true">终端编号</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th field="area.parentArea.parentArea.parentArea.parentArea.name" data-options="width:80,align:'center',fixed:true,formatter:privenceArea2">省</th>
					<th field="area.parentArea.parentArea.parentArea.name" data-options="width:80,align:'center',fixed:true,formatter:cityArea2">市</th>
					<th field="area.parentArea.parentArea.name" data-options="width:80,fixed:true,align:'center',formatter:countryArea2">区（县）</th>
					<th field="area.parentArea.name" data-options="width:100,fixed:true,align:'center',formatter:townStreetArea2">街道（乡镇）</th>
					<th field="area.name" data-options="width:120,align:'center',fixed:true,formatter:communityArea2">社区</th>
					<th field="distributionPoints" data-options="fitColumns:true,width:240">发放点</th>
					<th field="machineryEquipmentState.onlineDate" data-options="align:'center',formatter:onlineDateFormatter,sortable:true,fixed:true,width:160">上线时间</th>
					<th field="machineryEquipmentState.signalStrength" data-options="align:'center',formatter:signalStrengthFormatter">信号强度</th>
					<th field="machineryEquipmentState.deviceTemperature" data-options="align:'center',formatter:deviceTemperatureFormatter,sortable:true">设备温度（℃）</th>
					<th field="1" data-options="align:'center',formatter:aisle1">货道一剩余量（个）</th>
					<th field="2" data-options="align:'center',formatter:aisle2">货道二剩余量（个）</th>
					<th field="3" data-options="align:'center',formatter:aisle3">货道三剩余量（个）</th>
					<th field="4" data-options="align:'center',formatter:aisle4">货道四剩余量（个）</th>
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>