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
		<title>药具领用监控</title>
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
          function categoryFormatter(value,row,index){
            	if(row.contraceptive.name){
            		if(row.contraceptive.name){
            			return row.contraceptive.name;
            		}
            	}
            }
           function sexFormatter(value,row,index){
        	   if(value=="0"){
        		   return "女";
        	   }else if(value=="1"){
        		   return "男";
        	   }else {
        		   return "男";
        	   }
           }
		   /*        
		   		定时刷新数据
		  */
		  function refreshDatagrid(){
		  			$('#dg').datagrid({
		  				reload:"getMedicineSearchArea"
		  			});
			}
            function area(value,row,index){
            	if(row.machineryEquipment.area.parentArea.parentArea.name){
            		return row.machineryEquipment.area.parentArea.parentArea.name;
            	}
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
		            		url: 'getMedicineSearchArea',
		            		queryParams:params
		            	});
		     }
         	function state(value,row,index){
         		if(value){
         			return "<font color='green'>在线</font>";
         		}else{
         			return "<font color='red'>脱机</font>";
         		}
         	}
        	function changeIdNo(value,row,index) {	
        		if(row.idNumber){
        			var idNo = row.idNumber;
        			idNo = idNo.substr(0,6)+"******"+idNo.substr(12);
        			return idNo;
        		}
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
		 <table id="dg" class="easyui-datagrid" style="width: 600px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,50],pagination:true,url:'getMedicineSearchArea',singleSelect:true,pageSize:10">
			<thead frozen="true">				
				<tr>
					<th field="addDate" data-options="sortable:true,align: 'center',fixed:true,width:160">领取时间 </th>
					<th field="currentConnectionState" data-options="formatter:state,align: 'center',fixed:true,width:80">领用状态</th>
					<th field="idNumber" data-options="align: 'center',fixed:true,width:160,formatter:changeIdNo">身份证号 </th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th field="name"  data-options="align:'center',fixed:true,width:80" >姓名 </th>
					<th field="sex" data-options="align:'center',fixed:true,width:60">性别 </th>
					<th field="age" data-options="align:'center',fixed:true,width:60" >年龄</th>
					<th field="contraceptive.name" data-options="formatter:categoryFormatter,align:'center',fixed:true,width:90">领用药具  </th>
					<th field="amount"  data-options="align:'center',fixed:true,width:60">数量</th>
					<th field="machineryEquipment.deviceNo" data-options="formatter:deviceNoFormatter,sortable:true,align: 'center',fixed:true,width:80">终端编号</th>
					<th field="machineryEquipment.area.parentArea.parentArea.name" data-options="formatter:area,align: 'center',fixed:true,width:100">领取地 </th>
					<th field="householdRegistration" data-options="fixed:true,width:200">户籍地 </th>
					<th field="machineryEquipment.distributionPoints" data-options="formatter:distributionPointsFormatter,fixed:true,width:240">发放点</th>
					<th field="turnoverSituation"  data-options="align:'center',fixed:true,width:80" >人员流动情况</th>
					
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>