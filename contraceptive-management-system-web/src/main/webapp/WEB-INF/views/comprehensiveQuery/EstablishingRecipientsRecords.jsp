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
		<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
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
            	if(row.contraceptive.category){
            		if(row.contraceptive.category){
            			return row.contraceptive.category;
            		}
            	}
            }
           
            $(function(){
            	$('#city').combobox({
            		value:"全部",
            		url:'cityMenu',
            		onSelect:function(rec){
            		$('#country').combobox({
            			 url:'countryMenu?id='+rec.id,
						 value:"全部"
            			
            		});
            	}});
            	
            	$('#country').combobox({
            		onSelect:function(rec){
            		$('#townshipStreet').combobox({
            			 url:'townshipStreetMenu?id='+rec.id,
						 value:"全部"
            			
            		});
            	}});
            	
            	
            });
            
            
	 function submitForm(){
            	params = {};
            	params.cityId = $("#city").combobox('getValue') != "全部" && $("#city").combobox('getValue').length>0?$("#city").combobox('getValue'):-1;
            	params.countryId = $("#country").combobox('getValue') != "全部"&&$("#country").combobox('getValue').length>0?$("#country").combobox('getValue'):-1;
            	params.townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"&&$("#townshipStreet").combobox('getValue')>0?$("#townshipStreet").combobox('getValue'):-1;

            	$('#dg').datagrid({
            		url: 'getMedicineRecordSearchArea',
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
			    选择发放点：<input id="city" class="easyui-combobox" value="全部" name="id" style="width: 75px;" data-options="panelHeight:'auto',valueField:'id',textField:'name'"/>
			    <input id="country" class="easyui-combobox" value="全部" name="id" data-options="valueField:'id',textField:'name'"/>
			    <input id="townshipStreet" class="easyui-combobox" value="全部" name="id" data-options="valueField:'id',textField:'name'"/>
			   <a href="#" class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>
			   </form>
		</div>   
   
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,50],pagination:true,url:'getMedicineRecordsDatagrid',singleSelect:true,pageSize:10">
			<thead>
				<tr>
					<th field=id>ID</th>
		 		
					<th field="getMedicineDate">领取时间 </th>
					
					<th field="addDate">添加时间 </th>
					<th field="currentConnectionState">领用状态</th>
					<th field="idNumber" data-options="formatter:changeIdNo">身份证号 </th>
					<th field="name">姓名 </th>
					<th field="sex">性别 </th>
					<th field="age">年龄</th>
			
					<th field="contraceptive.category" data-options="formatter:categoryFormatter">领用药具  </th>
					<th field="amount">数量</th>
					<th field=machineryEquipment.deviceNo data-options="formatter:deviceNoFormatter">终端编号</th>
					<th field="address">领取地 </th>
					<th field="householdRegistration">户籍地 </th>
				
			
					<th field="machineryEquipment.distributionPoints" data-options="formatter:distributionPointsFormatter">发放点</th>
					<th field="onlineDate">人员流动情况</th>
					
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>