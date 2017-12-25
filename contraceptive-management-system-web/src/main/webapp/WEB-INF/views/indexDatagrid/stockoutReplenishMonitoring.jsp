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
		<title>缺货实时监控</title>
		<link href="js/jquery-easyui-1.3.5/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.5/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	  
	     <script type="text/javascript">
	     $.extend($.fn.combobox.defaults, {panelHeight:true});
	     /*
	      * 状态的显示
	      */
	     function stateFormatter(value,row,index){
	    	 return "<font color='red'>缺货</font>";
	     }
           function formatterAddress(value,row,index){
           	/* if(row.machineryEquipment){ */
           		var result = row.area.parentArea.parentArea.parentArea.name
           					 +row.area.parentArea.parentArea.name
           					 +row.area.parentArea.name
           					 +row.area.name
           					 +row.distributionPoints;
           		return result;
           	/* } */
           }
           function aisle1(value,row,index){
           	if(row.aisles[0]!=undefined){
           		if(row.aisles[0].num <= 1){
           			return "<a style='color:red'>"+row.aisles[0].num+"</a>";
           		}else{
	           		return row.aisles[0].num;
           		}
           	}else{
           		return 0;
           	}
           }
           function aisle2(value,row,index){
           	if(row.aisles[1]!=undefined){
           		if(row.aisles[1].num <= 1){
           			return "<a style='color:red'>"+row.aisles[1].num+"</a>";
           		}else{
	           		return row.aisles[1].num;
           		}
           	}else{
           		return 0;
           	}
           }
           function aisle3(value,row,index){
           	if(row.aisles[2]!=undefined){
           		if(row.aisles[2].num <= 1){
           			return "<a style='color:red'>"+row.aisles[2].num+"</a>";
           		}else{
	           		return row.aisles[2].num;
           		}
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
           function formatterStockoutDate(value,row,index){
        	   var date;
        	   for(var i in row.aisles){
        		   if(i == 4 && row.aislesNum<4){
        			   
        		   }else  if(row.aisles[i].num<=5 && row.aisles[i].stockoutDate){
        			   if(date == null){
        				   date = row.aisles[i].stockoutDate;
        			   }else{
        				   var d1 = parseDate(date);
        				   var d2 = parseDate(row.aisles[i].stockoutDate);
        				   if(d1.getTime()>d2.getTime()){
        					   date = row.aisles[i].stockoutDate;
        				   }
        			   }
        		   }
        	   }
        	   if(date){
	        	   return date;
        	   }else{
        		   return "";
        	   }
           }
           
           function parseDate(str){  
        	   if(typeof str == 'string'){  
        	     var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);  
        	     if(results && results.length>3)  
        	       return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]));   
        	     results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);  
        	     if(results && results.length>6)  
        	       return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]));   
        	     results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);  
        	     if(results && results.length>7)  
        	       return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]),parseInt(results[7]));   
        	   }  
        	   return null;  
        	 } 
            </script>
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout" > 
		 <div data-options="region:'center'" style="border: 0px;">
				 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,rownumbers:true,fit:true,fitColumns:true,loadMsg:'',pageList:[10,30,40,50,100],pagination:true,url:'allstockout',singleSelect:true,pageSize:30">
				<thead>
					<tr>
						<th field="state_" data-options="formatter:stateFormatter,align: 'center',fixed:true,width:90">状态</th>
						<th field="deviceNo" data-options="sortable:true,align: 'center',fixed:true,width:90">机器编号</th>
						<th field="address" data-options="fixed:true,sortable:true,width:450,formatter:formatterAddress">机器地址</th>
						<th field="1" data-options="align:'center',formatter:aisle1">货道一剩余量（个）</th>
						<th field="2" data-options="align:'center',formatter:aisle2">货道二剩余量（个）</th>
						<th field="3" data-options="align:'center',formatter:aisle3">货道三剩余量（个）</th>
						<th field="4" data-options="align:'center',formatter:aisle4">货道四剩余量（个）</th>
						<th field="stockoutDate" data-options="fixed:true,align:'center',width:150,formatter:formatterStockoutDate">缺货时间</th>
					</tr>
				</thead>
				</table>
		</div>
	
	</body>
</html>