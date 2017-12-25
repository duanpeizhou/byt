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
		<title>缺货设备监控</title>
		<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js" type="text/javascript"></script>
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	     <script src="js/commonJS1.js" type="text/javascript"></script>
	      <script type="text/javascript">
	      $.extend($.fn.combobox.defaults, {panelHeight:true});
	      	function formatterDeivceNo(value,row,index){
	      		if(row.machineryEquipment.deviceNo){
	      			return row.machineryEquipment.deviceNo;
	      		}
	      	}
	      	function formatterType(value,row,index){
	      		if(row.machineryEquipment.terminalType){
	      			return row.machineryEquipment.terminalType;
	      		}
	      	}
	      	function formatterDistributionPoints(value,row,index){
	      		if(row.machineryEquipment.distributionPoints){
	      			return row.machineryEquipment.distributionPoints;
	      		}
	      	}
	      	function formatterName(value,row,index){
	      		if(row.contraceptive.name){
	      			return row.contraceptive.name;
	      		}
	      	}
            /*
        		定时刷新数据
       		 */
        	function refreshDatagrid(){
	   			$('#dg').datagrid({
	   				reload:"stockoutAislesSearchArea"
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
            		url: 'stockoutAislesSearchArea',
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
				$("<iframe src='stockoutAislesSearchArea/export?cityId="+'<spring:message code="City.no"/>'+"&countryId="+countryId+"&townshipStreetId="+townshipStreetId+"&communityId="+communityId+"&distributionPoints="+distributionPoints+"' width='0px' height='0px'></iframe>").appendTo(document.body);
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
			    选择发放点：
			   <jsp:include page="../base/areaSearch.jsp" />
			   <a href="#" class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>
			   <a  class="easyui-linkbutton" onclick="downloadData();" data-options="iconCls:'icon-download'">导出</a>
			   </form>
		</div>   
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;" data-options="onLoadSuccess:selectOneLine,striped:true,rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[5,10,15],pagination:true,url:'stockoutAislesSearchArea',singleSelect:true,pageSize:10">
				<thead frozen="true">
					<tr>
					<th field="machineryEquipment.deviceNo" data-options="formatter:formatterDeivceNo,sortable:true,align: 'center',fixed:true,width:90">终端编号</th>
					</tr>
				</thead>
			
			<thead>
				<tr>
					<th field="machineryEquipment.terminalType" data-options="formatter:formatterType,sortable:true,align: 'center',fixed:true,width:90">机器类型</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name" data-options="fixed:true,formatter:privenceArea,align: 'center',fixed:true,width:90">省</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.name" data-options="fixed:true,formatter:cityArea,align: 'center',fixed:true,width:90">市</th>
					<th field="machineryEquipment.area.parentArea.parentArea.name" data-options="fixed:true,formatter:countryArea,align: 'center',fixed:true,width:90">区（县）</th>
					<th field="machineryEquipment.area.parentArea.name" data-options="fixed:true,formatter:townStreetArea,align: 'center',fixed:true,width:90">街道（乡镇）</th>
					<th field="machineryEquipment.area.name" data-options="fixed:true,formatter:communityArea,align: 'center',fixed:true,width:120">社区</th>
					<th field="machineryEquipment.distributionPoints" data-options="formatter:formatterDistributionPoints,fixed:true,width:240">发放点</th>
					<th field="contraceptive.name" data-options="align: 'center',fixed:true,width:100,formatter:formatterName" >缺货名称</th>
					<th field="stockoutDate" data-options="sortable:true,align: 'center',fixed:true,width:160">缺货时间</th>
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>