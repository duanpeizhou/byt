<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>发放机设备管理</title>
		<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
	    <link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.min.js" type="text/javascript"></script>
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	    <script type="text/javascript">
	    $.extend($.fn.combobox.defaults, {panelHeight:true});
	     function usedFormatter(value,row,index){
         	if(value=="1"){
         		return "<a href='javascript:void(0);' class='easyui-splitbutton' style='color: green' ><img src='images/ok.png'>操作</a>";
         	}else if(value=="0"){
         		return "<a href='javascript:updateUsed();' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/no.png'>操作</a>";
         	}
         	
         }
	     
	     function updateUsed(id){
	    	 	var row = $('#dg').datagrid('getSelected');
	                $.ajax({
	                    type: "post",
	                    url: "updateUsed",
	                    data: {id:row.id },
	                    success: function (result) {
	                    	if(result){
        						$("#dg").datagrid('reload');
        					}
	                    }
	            });
	           
	    	 
	     }
	     
	     function turnOnPowerStateFormatter(value,row,index){
	    	 if(value=="0"){
	    		 return "<font color='red'>常闭</font>";
	    	 }else if(value="1"){
	    		 return  "<font color='green'>常开</font>";
	    	 }else if(value="2"){
	    		 return "<font color='blue'>时间段开</font>";
	    	 }
	    	 
	     }
	     
	     
	     function turnOnLightStateFormatter(value,row,index){
	    	 if(value=="0"){
	    		 return "<font color='red'>常闭</font>";
	    	 }else if(value="1"){
	    		 return  "<font color='green'>常开</font>";
	    	 }else if(value="2"){
	    		 return "<font color='blue'>时间段开</font>";
	    	 }
	    	 
	     }
	     
	     
	     function turnOnVedioStateFormatter(value,row,index){
	    	 if(value=="0"){
	    		 return "<font color='red'>常闭</font>";
	    	 }else if(value="1"){
	    		 return  "<font color='green'>常开</font>";
	    	 }else if(value="2"){
	    		 return "<font color='blue'>时间段开</font>";
	    	 }
	    	 
	     }

        //自动开机时间
        function OnChk_Turn(i) {
           var chk_M_On = document.getElementById("BootState1");
           var chk_M_Off = document.getElementById("BootState3");
           var turnonhour = document.getElementById("BootHour1");
           var turnonminute = document.getElementById("BootMinute1");
           var turnoffhour = document.getElementById("BootHour2");
           var turnoffminute = document.getElementById("BootMinute2");
           if (i == 0) {
           chk_M_On.checked = true;
           chk_M_Off.checked = false;
           turnonhour.disabled = true;
           turnonminute.disabled = true;
           turnoffhour.disabled = true;
           turnoffminute.disabled = true;
           } else {
           chk_M_On.checked = false;
           chk_M_Off.checked = true;
           turnonhour.disabled = false;
           turnonminute.disabled = false;
           turnoffhour.disabled = false;
           turnoffminute.disabled = false;
           }
        }
       //自动开灯时间
       function OnChk_TurnLight(i) {
           var on = document.getElementById("OpenLightState1");
           var off = document.getElementById("OpenLightState2");
           var setr = document.getElementById("OpenLightState3");
           var turnonhour = document.getElementById("OpenLightHour1");
           var turnonminute = document.getElementById("OpenLightMinute1");
           var turnoffhour = document.getElementById("OpenLightHour2");
           var turnoffminute = document.getElementById("OpenLightMinute2");
           switch (i) {
           case 0:
           on.checked = true;
           off.checked = false;
           setr.checked = false;
           turnonhour.disabled = true;
           turnonminute.disabled = true;
           turnoffhour.disabled = true;
           turnoffminute.disabled = true;
           break;
           case 1:
           on.checked = false;
           off.checked = true;
           setr.checked = false;
           turnonhour.disabled = true;
           turnonminute.disabled = true;
           turnoffhour.disabled = true;
           turnoffminute.disabled = true;
           break;
           case 2:
           on.checked = false;
           off.checked = false;
           setr.checked = true;
           turnonhour.disabled = false;
           turnonminute.disabled = false;
           turnoffhour.disabled = false;
           turnoffminute.disabled = false;
           break;
           default:
           break;
           }
        }
        //自动视频时间
        function OnChk_TurnVideo(i) {
           var on = document.getElementById("VideoState1");
           var off = document.getElementById("VideoState2");
           var setr = document.getElementById("VideoState3");
           var turnonhour = document.getElementById("VideoHour1");
           var turnonminute = document.getElementById("VideoMinute1");
           var turnoffhour = document.getElementById("VideoHour2");
           var turnoffminute = document.getElementById("VideoMinute2");
           switch (i) {
           case 0:
           on.checked = true;
           off.checked = false;
           setr.checked = false;
           turnonhour.disabled = true;
           turnonminute.disabled = true;
           turnoffhour.disabled = true;
           turnoffminute.disabled = true;
           break;
           case 1:
           on.checked = false;
           off.checked = true;
           setr.checked = false;
           turnonhour.disabled = true;
           turnonminute.disabled = true;
           turnoffhour.disabled = true;
           turnoffminute.disabled = true;
           break;
           case 2:
           on.checked = false;
           off.checked = false;
           setr.checked = true;
           turnonhour.disabled = false;
           turnonminute.disabled = false;
           turnoffhour.disabled = false;
           turnoffminute.disabled = false;
           break;
           default:
           break;
           }
        } 
	     
        
 		 var url="" ;     
  
        function addStrategy(){
        	url="strategyAdd";
        	 $('#adminPassword').val("");
        	 $('#ip').val("");
        	 $('#port').val("");
        	 $('#phone').val("");
        	 $('#name').val("");
        	 $('#MaxAge').val("60");
        	 $('#MinAge').val("18");
        	 
        	 $('#MaxTemperature').val("40");
        	 $('#MinTemperature').val("-30");
     	   $('#w').window('open').dialog('setTitle','添加策略');
     		if($('#hiddenId')){
     			$('#hiddenId').remove();
     		}
     	  
     	
        }
             
        
        function updateStrategy(){
        	url="strategyUpdate";
        	var row = $('#dg').datagrid('getSelected');
        	if(row)
        	{	
        		$('#w').window('open').dialog('setTitle','修改策略');
	     		$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#ff');
        		$('#ff').form("load",row);
        		
        	}else{
        		$.messager.alert({
        			title:'提示',
        			msg:'请选择一条记录!'
        		});
        	}
        }  
        
       

        function save(){
        	$('#ff').form('submit',{
        		url:url,
        		onSubmit:function(){
        			return $(this).form('validate');
        		},
        		success:function(result){
        			if(!result){
        				 $.messager.show({
        					 title: '错误',
        					 msg: "操作失败"
        					 });
        			}else{
        				 
        				 $('#w').window('close'); // close the dialog
        				 $('#dg').datagrid('reload'); // reload the user data
        			}
        		}
        	});
        }
        
     
       
        
        //删除 success
        function deleteStrategy()
        {
        	var row = $('#dg').datagrid('getSelected');
        	if(row.defaultStrategy==true){
        		$.messager.alert('提示','策略正在执行禁止删除！');
        		return ;
        	}
        	if (row)
        	{
        		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
        		    if (r){    
        		        $.post('deleteStrategy',{id:row.id},function(result){
        		        	if(result && result.success == true){
        						$("#dg").datagrid('reload');
        						$.messager.show({
        							title:'提示',
        							msg:'删除成功!'
        						});
        					}else{
        						$.messager.show({
        							title:'提示',
        							msg:'删除失败!'
        						});
        					}   
        		        });    
        		    }    
        		});
        	}
        	else
        	{
        		$.messager.alert('提示','请您选择一条数据!');
        	}
        }

   
         </script> 
</head>

<body style="margin: 0px;padding: 0px;" class="easyui-layout">
	
	   <div data-options="region:'center',title:'数据列表'" >   
		 <table id="dg" rownumbers="true"  class="easyui-datagrid" style="width: 570px;" 
		 data-options="striped:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,20,4,50],pagination:true,url:'getStrategyList',singleSelect:true,pageSize:20,toolbar:'#tb'">
			<thead>
				<tr>
			
					
					<th field="defaultStrategy" data-options="formatter:usedFormatter,fixed:true,width:80" align="center">默认策略 </th>
					<th field="name" data-options="fixed:true,width:80,align:'center'">策略名称</th>
					<th field="turnOnPowerState" data-options="formatter:turnOnPowerStateFormatter,fixed:true,width:80" align="center">开机状态</th>
					<th field="turnOnLightState" data-options="formatter:turnOnLightStateFormatter,fixed:true,width:80" align="center">开灯状态</th>
					<th field="turnOnVedioState" data-options="formatter:turnOnVedioStateFormatter,fixed:true,width:80" align="center">视频状态</th>
					<th field="alarmMaxTemperature" data-options="fixed:true,width:80,align:'center'">温度:上限 </th>
					<th field="alarmMinTemperature" data-options="fixed:true,width:80,align:'center'">下限 </th>
					<th field="maxAge" data-options="fixed:true,width:80,align:'center'">年龄:上限 </th>
					<th field="minAge" data-options="fixed:true,width:80,align:'center'">下限 </th>
					<th field="alarmOutStockAmount" data-options="fixed:true,width:80,align:'center'">缺货报警数量 </th>
					<th field="giveOutDay" data-options="fixed:true,width:80,align:'center'">领用限制(天)</th>
				</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addStrategy();">添加</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateStrategy();">修改</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteStrategy();">删除</a>
		
			</div>
		</div>
	</div>
	
	
	<!-- 添加开始 -->
	 <div id="w" class="easyui-window" title="数据维护窗口" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:670px;height:440px;padding:10px;">
	 
	 <form id="ff" method="post">
	 		
			<div class="panel" style="display: block; width: 645px;"><div class="panel-header" style="width: 628px;">
				<div class="panel-title">设备参数设置 </div><div class="panel-tool"></div>
			</div>
			<div collapsible="false" style="width: 638px; margin-bottom: 10px; height: auto;" title="" class="easyui-panel panel-body">
                    <table width="100%" border="0" align="left" cellspacing="0" cellpadding="0" style="padding: 5px;">
                        <tbody><tr>
                            <td align="right"> 策略名称 ：</td>
                            <td>
                            <input class="easyui-validatebox textbox" type="text" name="name" id="name" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                            <td align="right">
                                自动开机时间 ：
                            </td>
                            <td width="*" height="25" align="left" colspan="3">
                                <input type="radio" onclick="OnChk_Turn(1);" value="0" name="turnOnLightState" id="BootState1">常开
                                <input type="radio" onclick="OnChk_Turn(3);" value="2" name="turnOnLightState" id="BootState3">设置
                                <select style="width: 40px;" id="BootHour1" name="turnOnBeginLightHour" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>&nbsp;时&nbsp;
                                <select style="width: 40px;" id="BootMinute1" name="turnOnBeginLightMinute" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option><option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option><option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option><option value="46">46</option><option value="47">47</option><option value="48">48</option><option value="49">49</option><option value="50">50</option><option value="51">51</option><option value="52">52</option><option value="53">53</option><option value="54">54</option><option value="55">55</option><option value="56">56</option><option value="57">57</option><option value="58">58</option><option value="59">59</option></select>&nbsp;分&nbsp;至&nbsp;
                                <select style="width: 40px;" id="BootHour2" name="turnOnEndLightHour" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>&nbsp;时&nbsp;
                                <select style="width: 40px;" id="BootMinute2" name="turnOnEndLightMinute" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option><option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option><option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option><option value="46">46</option><option value="47">47</option><option value="48">48</option><option value="49">49</option><option value="50">50</option><option value="51">51</option><option value="52">52</option><option value="53">53</option><option value="54">54</option><option value="55">55</option><option value="56">56</option><option value="57">57</option><option value="58">58</option><option value="59">59</option></select>&nbsp;分
                            </td>
                        </tr>
                        <tr>
                            <td height="25" align="right">
                                自动开灯时间 ：
                            </td>
                            <td width="*" height="25" align="left" colspan="3">
                                <input type="radio" onclick="OnChk_TurnLight(1);" value="0" name="turnOnPowerState" id="OpenLightState1">常开
                                <input type="radio" onclick="OnChk_TurnLight(2);" value="1" name="turnOnPowerState" id="OpenLightState2">常闭
                                <input type="radio" onclick="OnChk_TurnLight(3);" value="2" name="turnOnPowerState" id="OpenLightState3">设置
                                <select style="width: 40px;" id="OpenLightHour1" name="turnOnBeginPowerHour" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>&nbsp;时&nbsp;
                                <select style="width: 40px;" id="OpenLightMinute1" name="turnOnBeginPowerMinute" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option><option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option><option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option><option value="46">46</option><option value="47">47</option><option value="48">48</option><option value="49">49</option><option value="50">50</option><option value="51">51</option><option value="52">52</option><option value="53">53</option><option value="54">54</option><option value="55">55</option><option value="56">56</option><option value="57">57</option><option value="58">58</option><option value="59">59</option></select>&nbsp;分&nbsp;至&nbsp;
                                <select style="width: 40px;" id="OpenLightHour2" name="turnOnEndPowerHour" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>&nbsp;时&nbsp;
                                <select style="width: 40px;" id="OpenLightMinute2" name="turnOnEndPowerMinute" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option><option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option><option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option><option value="46">46</option><option value="47">47</option><option value="48">48</option><option value="49">49</option><option value="50">50</option><option value="51">51</option><option value="52">52</option><option value="53">53</option><option value="54">54</option><option value="55">55</option><option value="56">56</option><option value="57">57</option><option value="58">58</option><option value="59">59</option></select>&nbsp;分
                            </td>
                        </tr>
                        <tr>
                            <td height="25" align="right">
                                自动视频时间 ：
                            </td>
                            <td width="*" height="25" align="left" colspan="3">
                                <input type="radio" onclick="OnChk_TurnVideo(1);" value="0" name="turnOnVedioState" id="VideoState1">常开
                                <input type="radio" onclick="OnChk_TurnVideo(2);" value="1" name="turnOnVedioState" id="VideoState2">常闭
                                <input type="radio" onclick="OnChk_TurnVideo(3);" value="2" name="turnOnVedioState" id="VideoState3">设置
                                <select style="width: 40px;" id="VideoHour1" name="turnOnBeginVedioHour" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>&nbsp;时&nbsp;
                                <select style="width: 40px;" id="VideoMinute1" name="turnOnBeginVedioMinute" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option><option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option><option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option><option value="46">46</option><option value="47">47</option><option value="48">48</option><option value="49">49</option><option value="50">50</option><option value="51">51</option><option value="52">52</option><option value="53">53</option><option value="54">54</option><option value="55">55</option><option value="56">56</option><option value="57">57</option><option value="58">58</option><option value="59">59</option></select>&nbsp;分&nbsp;至&nbsp;
                                <select style="width: 40px;" id="VideoHour2" name="turnOnEndVedioHour" disabled="">
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>&nbsp;时&nbsp;
                                <select style="width: 40px;" id="VideoMinute2" name="turnOnEndVedioMinute" disabled="">
                                
                                <option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option><option value="32">32</option><option value="33">33</option><option value="34">34</option><option value="35">35</option><option value="36">36</option><option value="37">37</option><option value="38">38</option><option value="39">39</option><option value="40">40</option><option value="41">41</option><option value="42">42</option><option value="43">43</option><option value="44">44</option><option value="45">45</option><option value="46">46</option><option value="47">47</option><option value="48">48</option><option value="49">49</option><option value="50">50</option><option value="51">51</option><option value="52">52</option><option value="53">53</option><option value="54">54</option><option value="55">55</option><option value="56">56</option><option value="57">57</option><option value="58">58</option><option value="59">59</option></select>&nbsp;分
                            </td>
                        </tr>
                        <tr>
                            <td width="20%" height="25" align="right">
                                报警温度 ：
                            </td>
                            <td width="*" height="25" align="left" colspan="3">
                                上限 ：
                                <input type="text" required="true" class="easyui-validatebox validatebox-text" style="width: 40px;" id="MaxTemperature" value="40" name="alarmMaxTemperature">&nbsp;℃&nbsp; &nbsp; &nbsp; 下限
                                ：
                                <input type="text" required="true" class="easyui-validatebox validatebox-text" style="width: 40px;" id="MinTemperature" value="-30" name="alarmMinTemperature">&nbsp;℃
                            </td>
                        </tr>
                        <tr>
                            <td width="20%" height="25" align="right">
                                储存年龄 ：
                            </td>
                            <td width="*" height="25" align="left" colspan="3">
                                上限 ：
                                <input type="text" required="true" class="easyui-validatebox validatebox-text" style="width: 40px;" id="MaxAge" value="60" name="maxAge">&nbsp;岁&nbsp; &nbsp; &nbsp; 下限 ：
                                <input type="text" required="true" class="easyui-validatebox validatebox-text" style="width: 40px;" id="MinAge" value="18" name="minAge">&nbsp;岁
                            </td>
                        </tr>
                        <tr>
                            <td width="20%" height="25" align="right">
                                【管理员】密码 ：
                            </td>
                            <td width="*" height="25" align="left">
                                <input type="text" class="easyui-validatebox validatebox-text" style="width: 160px;" id="adminPassword" maxlength="16" name="adminPassword">
                            </td>
                            <td width="20%" height="25" align="right">
                                电话号码 ：
                            </td>
                            <td width="*" height="25" align="left">
                                <input type="text" class="easyui-validatebox validatebox-text" style="width: 160px;" id="phone" maxlength="30" name="phone">
                            </td>
                        </tr>
                        <tr>
                            <td width="20%" height="25" align="right">
                                【服务器】 IP ：
                            </td>
                            <td width="*" height="25" align="left">
                                <input type="text" class="easyui-validatebox validatebox-text" style="width: 160px;" id="ip" maxlength="30" name="ip">
                            </td>
                            <td width="20%" height="25" align="right">
                                端口 ：
                            </td>
                            <td width="*" height="25" align="left">
                                <input type="text" class="easyui-validatebox validatebox-text" style="width: 160px;" id="port" maxlength="30" name="port">
                            </td>
                        </tr>
                    </tbody></table>
                </div></div>
                
                <div class="panel" style="display: block; width: 645px;"><div class="panel-header" style="width: 628px;"><div class="panel-title">总体发放策略</div><div class="panel-tool"></div></div><div collapsible="false" style="width: 638px; margin-bottom: 10px; height: auto;" title="" class="easyui-panel panel-body">
                    <table width="100%" border="0" align="left" cellspacing="0" cellpadding="0" style="padding: 5px;">
                        <tbody><tr>
                            <td width="20%" height="25" align="right">
                                总体发放策略 ：
                            </td>
                            <td width="*" height="25" align="left" colspan="3">
                                <input type="checkbox" value="1" checked="checked" id="IsEnables" name="used">启用
                                <input type="text" required="true" class="easyui-validatebox validatebox-text" style="width: 30px;" id="DayNum" value="20" name="giveOutDay">
                                	天内最多可领取&nbsp;<input type="text" required="true" class="easyui-validatebox validatebox-text" style="width: 30px;" id="Species" value="1" name="giveOutAmount">
                                	种药具 &nbsp; 缺货报警数量 ：<input type="text" required="true" maxlength="2" class="easyui-validatebox validatebox-text" style="width: 30px;" id="AlarmNo" value="5" name="alarmOutStockAmount">
                                   &nbsp; 默认策略:<input type="checkbox" value="1" checked="checked" id="IsLocals" name="defaultStrategy">
                            </td>
                        </tr>
                    </tbody></table>
                </div></div>
                </form>
		<div style="text-align: center; height: 30px; line-height: 30px; width: 645px; display: block;" border="false" region="south" class="layout-body panel-body panel-body-noheader panel-body-noborder" title="">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#w').dialog('close')">关闭</a>
		</div>
	</div>
	<!-- 添加结束 -->

	
	

</body>

</html>