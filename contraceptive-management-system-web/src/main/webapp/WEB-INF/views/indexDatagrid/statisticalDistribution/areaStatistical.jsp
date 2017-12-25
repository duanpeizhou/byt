<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>区县统计列表</title>
		<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css"
		rel="stylesheet" type="text/css" />
		<link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet"
				type="text/css" />
		<script src="js/jquery-easyui-1.3.4/jquery.min.js"
				type="text/javascript"></script>
		<script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js"
				type="text/javascript"></script>
				<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"
				type="text/javascript"></script>
		<script src="FusionCharts/FusionCharts.js" type="text/javascript"></script>
		<script src="js/commonJS1.js" type="text/javascript"></script>
		<script type="text/javascript">
		function praseFusionCharts(arr){
			var result = new Array();
			for(var i in arr){
				var r = $.extend(arr[i],{
					label:arr[i].area.name,
					value:arr[i].total
				});
				result.push(r);
			}
			return result;
		}
		 $.extend($.fn.combobox.defaults, {panelHeight:true});
			function areaFormatter(value,row,index){
				if(row.area.name){
					return row.area.name;
				}
				
			}
			$(function(){
				$.post('areaStatisticalColumn3D',function(json){
					var root=json.rows; 
					var charData = praseFusionCharts(root);
            		columnChart(charData);
            		pieChart(charData);
            	}); 
			});
			
			
			
			function columnChart(arr){
            	var myChart = new FusionCharts( "FusionCharts/Column3D.swf", "myChartId", "550", "300", "0" );
       			myChart.setJSONData({ 
		           "chart": 
		           { 
		                   "xAxisName" : "地区", 
		                   "yAxisName" : "领用的数量",
		                   'palett':'2',
		                   'showFCMenuItem':'0',
		                   'imageSave':'0',
		                   'baseFontSize':'12',
		                   'bgColor':'999999,FFFFFF',
		                   'borderColor':'1D8BD1' ,
		                   'orderThickness':'1',
		                   'borderAlpha':'50',
		                   'bgAlpha':'30',
		                   'showBorder':'1',
		                   'animation':'1',
		                   'subcaption':'',
		                   'formatNumberScale':'0',
		                   'numberPrefix':'',
		                   'showValues':'1',
		                   'showPercentInToolTip':'1'
		           },
		           "styles": {
		        	    "definition": [
		        	      {
		        	    	  'type':'font',
		        	    	  'color':'555555',
		        	    	  'name':'CaptionFont',
		        	    	  'size':'15'
		        	      },
		        	      {
		        	    	  'type':'font',
		        	    	  'name':'SubCaptionFont',
		        	    	  'bold':'0'
		        	      }
		        	     ],
		        	     "application": [
		        	                     {
											'toObject':'caption',
											'styles':'CaptionFont'
		        	                     },
		        	                     {
												'toObject':'SubCaption',
												'styles':'SubCaptionFont'
			        	                     }
		        	      ]
		           },
		           "data" : arr
  			 });
   		    myChart.render("chartContainer");
            }
			function pieChart(arr){
            	var myChart = new FusionCharts( "FusionCharts/Pie3D.swf", "pieChartId", "550", "300", "0" );
            	
            	myChart.setJSONData({ 
 		           "chart": 
 		           { 
 		                   "xAxisName" : "年龄段", 
 		                   "yAxisName" : "领用的数量",
 		                 	'palett':'2',
		                   'showFCMenuItem':'0',
		                   'imageSave':'0',
		                   'baseFontSize':'12',
		                   'bgColor':'999999,FFFFFF',
		                   'borderColor':'1D8BD1' ,
		                   'orderThickness':'1',
		                   'borderAlpha':'50',
		                   'bgAlpha':'30',
		                   'showBorder':'1',
		                   'animation':'1',
		                   'subcaption':'',
		                   'formatNumberScale':'0',
		                   'numberPrefix':'',
		                   'showValues':'1',
		                   'showPercentInToolTip':'1' 		                   
 		           },
 		           "styles": {
 		        	    "definition": [
 		        	       {
		        	    	  'type':'font',
		        	    	  'color':'555555',
		        	    	  'name':'CaptionFont',
		        	    	  'size':'15'
		        	      },
		        	      {
		        	    	  'type':'font',
		        	    	  'name':'SubCaptionFont',
		        	    	  'bold':'0'
		        	      }
 		        	     ],
 		        	     "application": [
 		        	                    {
											'toObject':'caption',
											'styles':'CaptionFont'
		        	                     },
		        	                     {
												'toObject':'SubCaption',
												'styles':'SubCaptionFont'
			        	                     }
 		        	      ]
 		           },
 		           "data" : arr
   			 });
   		    myChart.render("pieChartContainer");
            }
			
			
			function submitForm(){
				params = {};
				params.cityId =<spring:message code="City.no"/>;
				params.countryId = $("#country").combobox('getValue') != "全部"
						&& $("#country").combobox('getValue').length > 0 ? $("#country")
						.combobox('getValue')
						: -1;
				params.townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"
						&& $("#townshipStreet").combobox('getValue') > 0 ? $(
						"#townshipStreet").combobox('getValue') : -1;
						
				params.communityId = $("#community").combobox('getValue') != "全部"
							&& $("#community").combobox('getValue') > 0 ? $(
							"#community").combobox('getValue') : -1;
				params.startTime=$("#startTime").datetimebox('getValue');
				params.endTime=$("#endTime").datetimebox('getValue');
				$('#dg').datagrid({
					url : 'areaStatisticalList',
					queryParams : params
				});
			}
			
			
		</script>
	</head>
	<body class="easyui-layout">
		 	<div data-options="region:'north',title:'条件查询'" style="height:100px;text-align:left;padding:20px;" align="center">
			 <form id="form" method="post">  
			    选择发放点：<!-- <input id="city" class="easyui-combobox" value="全部" name="id"  data-options="panelHeight:'auto',valueField:'id',textField:'name',loadFilter:filterDataFun"/> -->
			    <jsp:include page="../../base/statisticsAnalysisareaSearch.jsp" />
			     &nbsp; &nbsp; 时间自：
			    <input id="startTime" type="text" name="startTime" class="easyui-datetimebox" style="width:90px"></input>
			   &nbsp; 到：<input id="endTime" type="text" name="endTime" class="easyui-datetimebox" style="width:90px"></input>
			   <a class="easyui-linkbutton" onclick="submitForm();" data-options="iconCls:'icon-search'" >查询</a>
			   </form>
		</div>   
		    <div data-options="region:'east',title:'数据统计',split:true" style="width:600px;padding: 0px;">
					<div id="chartContainer" style="margin: 2%;"></div>
					<div id="pieChartContainer" style="margin: 2%;"></div>
		    </div>   
		    <div data-options="region:'center',title:'数据列表'">
		    	 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,40,50],pagination:true,url:'areaStatisticalList',singleSelect:true,pageSize:25">
				<thead frozen="true">
					<tr>
						<th field="area.name" data-options="formatter:areaFormatter,fixed:true,width:150" align="center">区县</th>
						</tr>
				</thead>
				<thead>
				<tr>
						<th field="total" data-options="fixed:true,width:80,align:'center'" >领用数量</th>
						<th field="manTotal"  data-options="fixed:true,width:80,align:'center'" >男</th>
						<th field="womanTotal"  data-options="fixed:true,width:80,align:'center'" >女</th>
						<th field="countyOfCity"  data-options="fixed:true,width:80,align:'center'" >本市本县</th>
						<th field="countyOutCity"  data-options="fixed:true,width:80,align:'center'" >本市外县</th>
						<th field="provinceOutCity" data-options="fixed:true,width:80,align:'center'" >本省外市</th>
						<th field="otherProvinces"  data-options="fixed:true,width:80,align:'center'" >外省市</th>
					</tr>
				</thead>
			</table>
		    </div>
	</body>
</html>