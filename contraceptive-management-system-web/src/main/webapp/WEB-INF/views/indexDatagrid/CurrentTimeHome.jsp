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
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="FusionCharts/FusionCharts.js" type="text/javascript"></script>
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	  
	     <script type="text/javascript">
	     $.extend($.fn.combobox.defaults, {panelHeight:true});
	     /*
	      * 状态的显示
	      */
	     function stateFormatter(value,row,index){
	     	if(value=="OpenDoor"){
	     		return "<font color='red'>开门</font>";
	     	}else
	     	if(value=="CloseDoor"){
	     		return "<font color='green'>关门</font>";
	     	}else
	     	if(value=="Online"){
	     		return "<font color='green'>在线</font>";
	     	}else
	     	if(value=="OffineLine"){
	     		return "<font color='red'>离线</font>";
	     	}else
	     	if(value=="Overtemperature"){
	     		return "<font color='red'>超温</font>";
	     	}else
	     	if(value=="TemperatureRecovery"){
	     		return "<font color='red'>温度恢复</font>";
	     	}else
	     	if(value=="OutStock"){
	     		return "<font color='red'>缺货</font>";
	     	}else
	     	if(value=="Replenishment"){
	     		return "<font color='green'>补货</font>";
	     	}else
	     	if(value=="AisleFailure"){
	     		return "<font color='red'>货道故障</font>";
	     	}else
	     	if(value=="AisleFailureRecovery"){
	     		return "<font color='green'>货道故障恢复</font>";
	     	}else
	     	if(value=="CardReaderFailure"){
	     		return "<font color='red'>读卡器故障</font>";
	     	}else
	     	if(value=="CardReaderFailureRecovery"){
	     		return "<font color='green'>读卡器故障恢复</font>";
	     	}
	     }
	     
            function formatterDeviNo(value,row,index){
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
            function formatterAddress(value,row,index){
           		if(row.machineryEquipment){ 
              		var result = row.machineryEquipment.area.parentArea.parentArea.parentArea.name
              					 +row.machineryEquipment.area.parentArea.parentArea.name
              					 +row.machineryEquipment.area.parentArea.name
              					 +row.machineryEquipment.area.name
              					 +row.machineryEquipment.distributionPoints;
              		return result;
              	 } 
	         }
            $(function(){
            	/*
            	Pie3D.swf
            	Column3D
            	*/
	            	/* $.post('chartData',function(data){
	            		//data=600+";"+350+";"+(600-350)+";"+(600-100)+";"+100+";"+800+";"+900;
	            		data="15;5;10;15;0;0;3";
	            		var arr=data.split(";");
	            		columnChart(arr);
	            		stockPieChart(arr);
	            		pieChart(arr);
	            	}); 
 */
           			$.ajax({
    		  				type:'post',
    		  				url: "chartData",
    		  				success:function(data){
    		  					data=data.substring(1, data.length-1);
    		  					var arr=data.split(";");
    			            	columnChart(arr);
    			            	stockPieChart(arr);
    			            	pieChart(arr);
    		  				},
    		  				async: false
	            	});
            });
            function columnChart(arr){
            	var myChart = new FusionCharts( "FusionCharts/Column3D.swf", "myChartId", "450", "200", "0" );
       			myChart.setJSONData({ 
		           "chart": 
		           { 
		                   "xAxisName" : "种类", 
		                   "yAxisName" : "数量",
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
		           "data" : 
		           [ 
						{ "label" : "设备总量", "value" : arr[0] },
						{ "label" : "在线设备", "value" : arr[1] }, 
						{ "label" : "离线设备", "value" : arr[2] },
						{ "label" : "有货设备", "value" : arr[3] },
						{ "label" : "无货设备", "value" : arr[4] },
						{ "label" : "今日领用量", "value" : arr[5] }, 
						{ "label" : "本月领用量", "value" : arr[6] }
		           ]
  			 });
   		    myChart.render("chartContainer");
            }
            function pieChart(arr){
            	var myChart = new FusionCharts( "FusionCharts/Pie3D.swf", "pieChartId", "360", "200", "0" );
       			myChart.setJSONData({ 
		           "chart": 
		           { 
		                   "xAxisName" : "种类", 
		                   "yAxisName" : "数量",
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
		           "data" : 
		           [ 
		                   { "label" : "在线设备", "value" : arr[1] }, 
		                   { "label" : "离线设备", "value" : arr[2] }
		           ]
  			 });
   		    myChart.render("pieChartContainer");
            }
            function stockPieChart(arr){
            	var myChart = new FusionCharts( "FusionCharts/Pie3D.swf", "piemyChartId1", "360", "200", "0" );
       			myChart.setJSONData({ 
		           "chart": 
		           { 
		                   "xAxisName" : "种类", 
		                   "yAxisName" : "数量",
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
		           "data" : 
		           [ 
		                   { "label" : "有货设备", "value" : arr[3] },
		                   { "label" : "无货设备", "value" : arr[4] } 
		           ]
  			 });
   		    myChart.render("pieChartContainer1");
            }
            </script>
	</head>
	<body style="margin: 0px;padding: 0px;" class="easyui-layout">
	
		<!-- <div data-options="region:'south',split:true,title:'数量统计'" style="height: 250px;">
			<span id="chartContainer" style="margin: 1%;"></span>
			<span id="pieChartContainer" style="margin: 1%;"></span>
			<span id="pieChartContainer1" style="margin: 1%;"></span>
		</div> -->
		<div region="south" split="false" title="监控信息饼形图数据" style="height: 230px; padding: 0px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="550px" align="center">
                    <div id="chartContainer" align="center" style="margin-top: 2px;">
                    </div>
                </td>
                <td width="300px" align="center">
                    <div id="pieChartContainer" align="center" style="margin-top: 2px;">
                    </div>
                </td>
                <td width="300px" align="center">
                    <div id="pieChartContainer1" align="center" style="margin-top: 2px;">
                    </div>
                </td>
            </tr>
        </table>
    </div> 
		 <div data-options="region:'center'" >
				 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,rownumbers:true,fit:true,loadMsg:'',pageList:[10,25,40,50],pagination:true,url:'machineryEquipmentStateChangeRecords',singleSelect:true,pageSize:25">
				<thead frozen="true">
					<tr>
						<th field="state"  data-options="formatter:stateFormatter,align: 'center',fixed:true,width:90,frozen:true">状态</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th field="hanpenDate" data-options="sortable:true,align: 'center',fixed:true,width:160">日期</th>
						 <th field="machineryEquipment.deviceNo" data-options="formatter:formatterDeviNo,sortable:true,align: 'center',fixed:true,width:90">机器编号</th>
						<th field="machineryEquipment.terminalType" data-options="formatter:formatterType,align: 'center',fixed:true,width:90">机器类型</th>
						<!-- <th field="machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name" data-options="formatter:privenceArea,align: 'center',fixed:true,width:70">省</th>
						 <th field="machineryEquipment.area.parentArea.parentArea.parentArea.name" data-options="fixed:true,formatter:cityArea,align: 'center',fixed:true,width:70">市</th>
						 <th field="machineryEquipment.area.parentArea.parentArea.name" data-options="fixed:true,formatter:countryArea,align: 'center',fixed:true,width:70">区（县）</th>
						 <th field="machineryEquipment.area.parentArea.name" data-options="fixed:true,formatter:townStreetArea,align: 'center',fixed:true,width:90">街道（乡镇）</th>
						 <th field="machineryEquipment.area.name" data-options="fixed:true,formatter:communityArea,align: 'center',fixed:true,width:120">社区</th> -->
						<th field="machineryEquipment.distributionPoints" data-options="fixed:true,sortable:true,formatter:formatterAddress,width:450">机器地址</th>
						<th field="detail" data-options="fixed:true,width:180,align: 'center'">描述</th>
					</tr>
				</thead>
				</table>
		</div>
	
	</body>
</html>