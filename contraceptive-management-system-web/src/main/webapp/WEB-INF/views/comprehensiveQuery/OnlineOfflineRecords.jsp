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
            		url: 'onlineOfflineRecordSearchArea',
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
			    选择发放点：<input id="city" style="width: 30px;" style="width: 75px;" class="easyui-combobox" value="全部" name="id"  data-options="panelHeight:'auto',valueField:'id',textField:'name'"/>
			    <input id="country" class="easyui-combobox" value="全部" name="id" data-options="valueField:'id',textField:'name'"/>
			    <input id="townshipStreet" class="easyui-combobox" value="全部" name="id" data-options="valueField:'id',textField:'name'"/>
			   <a href="#" class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>
			   </form>
		</div>   
   
   		<div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,50],pagination:true,url:'onlineOfflineRecords',singleSelect:true,pageSize:10">
			<thead>
				<tr>
					<th field=machineryEquipment.deviceNo data-options="formatter:deviceNoFormatter">终端编号</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name" data-options="formatter:privenceArea">省</th>
					<th field="machineryEquipment.area.parentArea.parentArea.parentArea.name" data-options="formatter:cityArea">市</th>
					<th field="machineryEquipment.area.parentArea.parentArea.name" data-options="formatter:countryArea">区（县）</th>
					<th field="machineryEquipment.area.parentArea.name" data-options="formatter:townStreetArea">街道（乡镇）</th>
					<th field="machineryEquipment.area.name" data-options="formatter:communityArea">社区</th>
					<th field="machineryEquipment.distributionPoints" data-options="formatter:distributionPointsFormatter">发放点</th>
					<th field="onlineDate">上线时间</th>
					<th field="offlineDate">下线时间</th>
				</tr>
			</thead>
		</table>
	</div>
	</body>
</html>