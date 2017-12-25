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
	     <style type="text/css">
			#fm{
			margin:0;
			padding:10px 30px;
			}
			.ftitle{
			font-size:14px;
			font-weight:bold;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
			}
			.fitem{
			margin-bottom:5px;
			}
			.fitem label{
			display:inline-block;
			width:80px;
			}
		</style>
	    <script src="js/jquery-easyui-1.3.5/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <script src="FusionCharts/FusionCharts.js" type="text/javascript"></script>
	    <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	     <script type="text/javascript">
	     $.extend($.fn.combobox.defaults, {panelHeight:true});
	     	function regionalFormatter(value,row,index){
	     		if(value=='0'){
	     			return "无限制";
	     		}else {
	     			return "限制";
	     		}
	     	}
	     	var url="";
	     	var msg="";
	     	var msgSuccess="";
	     	function newContraceptive(){
	     		$('#dialog').dialog('open').dialog('setTitle','添加新药具');
	     		$('#fm').form('clear');
	     		if($('#hiddenId')){
	     			$('#hiddenId').remove();
	     		}
	     		msg="添加失败";
	     		msgSuccess="添加成功";
	     		url="addContraceptive";
	     	}
	     	function editContraceptive(){
	     		var row=$('#dg').datagrid('getSelected');
	     		if(row){
		     		$('#dialog').dialog('open').dialog('setTitle','修改药具');
		     		$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#fm');
		     		$('#fm').form('load',row);
		     		url="updateContraceptive";
		     		msg="修改失败";
		     		msgSuccess="修改成功";
	     		}else {
	     			$.messager.alert('提示','请您选择一条数据!');
	     		}
	     	}
	     	function saveContraceptive(){
	     		$('#fm').form('submit',{
	    			url:url,
	    			onSubmit:function(){
	    				return $(this).form('validate');
	    			},
	    			success:function(result){
	    				if(!result){
	    					 $.messager.show({
	    						 title: '错误',
	    						 msg: msg
	    						 });
	    				}else{
	    					 $('#dialog').dialog('close'); // close the dialog
	    					 $('#dg').datagrid('reload'); // reload the user data
	    					 $.messager.show({
  								title:'提示',
  								msg:msgSuccess
  							});
	    				}
	    			}
	    		});
	     	}
	     	function deleteContraceptive(){
	     		var row = $('#dg').datagrid('getSelected');
	     		if (row)
	     		{
	     			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	     			    if (r){    
	     			        $.post('deleteContraceptive',{id:row.id},function(result){
	     			        	if(result && result == true){
	     							$("#dg").datagrid('reload');
	     							$.messager.show({
	     								title:'提示',
	     								msg:'删除成功!'
	     							});
	     						}else{
	     							$.messager.alert('提示','药具正在使用不能删除!');
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
		<!-- <div data-options="region:'south',split:true,title:'数量统计'" style="height: 350px;">
			<span id="chartContainer" style="margin: 5%;"></span>
			<span id="pieChartContainer" style="margin: 5%;"></span>
			<span id="pieChartContainer1" style="margin: 5%;"></span>
		</div> -->
		 <div data-options="region:'center'" >
				 <table id="dg" class="easyui-datagrid" style="width: 570px;height: 250px;"
				data-options="onLoadSuccess:selectOneLine,striped:true,pagination:true,toolbar:'#dgToolbar',rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,25,40,50],pagination:true,url:'getAllContraceptive',singleSelect:true,pageSize:25">
				<thead>
					<tr>
						<th field="id" data-options="align:'center',fixed:true,width:80">种类编号</th>
						 <th field="name" data-options="align:'center',fixed:true,width:80">药具名称</th>
						 <th field="category" data-options="align:'center',fixed:true,width:80">所属类别</th>
						 <th field="type" data-options="align:'center',fixed:true,width:80">规格型号</th>
						 <th field="allowingNumber" data-options="align:'center',fixed:true,width:80">允许领取数量</th>
						 <th field="measuringUnit" data-options="align:'center',fixed:true,width:80">计量单位</th>
						 <th field="minAge" data-options="fixed:true,align:'center',width:80">最小使用年龄</th>
						 <th field="maxAge" data-options="fixed:true,align:'center',width:80">最大使用年龄</th>
						<th field="regionalLimit" data-options="fixed:true,align:'center',formatter:regionalFormatter,width:80">地域限制</th> 
						<th field="sexLimit" data-options="align:'center',fixed:true,width:60">性别</th>
						<th field="description" data-options="align:'center',fixed:true,width:160">说明</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="dgToolbar">
			 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newContraceptive()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editContraceptive()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteContraceptive()">删除</a>
		</div>
		<div id="dialog" class="easyui-dialog" style="width: 400px;height: 500px;padding: 10px 20px" 
		closed="true" buttons="#dialogButtons">
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>药具名称：</label>
					<input name="name" class="easyui-validatebox" required="true" />
				</div>
				<div class="fitem">
					<label>所属类别：</label>
					<input name="category" class="easyui-validatebox" />
				</div>
				<div class="fitem">
					<label>规格型号：</label>
					<input name="type" class="easyui-validatebox" />
				</div>
				<div class="fitem">
					<label>允许领取的数量：</label>
					<input name="allowingNumber" class="easyui-validatebox" required="true" />
				</div>
				<div class="fitem">
					<label>计量单位：</label>
					<input name="measuringUnit" class="easyui-validatebox" />
				</div>
				<div class="fitem">
					<label>最小使用年龄：</label>
					<input name="minAge" class="easyui-validatebox" required="true"/>
				</div>
				<div class="fitem">
					<label>最大使用年龄：</label>
					<input name="maxAge" class="easyui-validatebox" required="true"/>
				</div>
				<div class="fitem">
					<label>地域限制：</label>
					无：<input id='radio1' name="regionalLimit" value="false" type="radio"  />
					有：<input id='radio2' name="regionalLimit" value="true" type="radio" />
				</div>
				<div class="fitem">
					<label>性别限制：</label>
					<select name="sexLimit">
						<option value="不限">不限</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select>
				</div>
				<div class="fitem">
					<label>说明：</label>
					<textarea id="Remark"
										class="easyui-validatebox validatebox-text" rows="3"
										validtype="length[0,300]" cols="25" name="description"></textarea>
				</div>
			</form>
		<div id="dialogButtons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveContraceptive()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog').dialog('close')">取消</a>
		</div>
	</div>
	</body>
</html>