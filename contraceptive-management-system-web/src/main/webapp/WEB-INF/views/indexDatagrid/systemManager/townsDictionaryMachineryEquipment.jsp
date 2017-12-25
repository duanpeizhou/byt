<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server" >
	<base href="${basePath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>避孕药具自助发放机综合管理平台</title>
    <link href="js/jquery-easyui-1.3.5/themes/default/easyui.css" rel="stylesheet"
        type="text/css" />
    <link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="js/jquery-easyui-1.3.5/jquery.min.js" type="text/javascript"></script>
    <script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
   	<script src="js/commonJS1.js" type="text/javascript"></script>
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <link href="css/index.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    /**
	 * 取消
	 */
	function cancelButton(){
		$("#addTownshipStreet").dialog('close');
    	$('#addTownshipStreet_edit_form input[name ="name"]').val("");
	}
    
    function parentAreaId(value,row,index){
		if(row.parentArea.id){
			return row.parentArea.id;
		}
	}
    /**
	 * 区县树加载
	 */
    	$(function(){
    		$('#tt').tree({
 				url:'countryMenu',
 				onSelect:function(node){
 					params = {};
 					params.pid=node.id;
 					$('#taTownship').datagrid({
 	            		url: 'getAllTownshipStreet',
 	            		queryParams:params
 	            	});
 				}
 			});
    	});
    	
    	/**
    	 * 添加区县街道
    	 */
    	 	var msg="";
    		var msgSuccess="";
    		var url="";
    	function addTownshipStreetShow(){
    		var nodes = $('#tt').tree('getSelected');
    		if(nodes){
    			$('#addTownshipStreet').dialog();
    			$("#addTownshipStreet_edit_form").form("reset");
    			$("#areParId").val(nodes.id);
    			msg="添加失败";
         		msgSuccess="添加成功";
         		url="addTownshipStreet";
    		}else{
    			$.messager.alert('提示','请选择新增乡镇所属区县菜单!');
    		}
    	}
    	
    	/**
    	 * 删除区县街道
    	 */
    	function deleTownshipStreetShow(){
    		var selectRow=$('#taTownship').datagrid('getSelected');
    		if(selectRow){
    			$.messager.confirm('确认','您确定要删除？',function(data){
    				if(data){
    					$.post('deleteTownshipStreet',{id:selectRow.id},function(result){
    						if(result){
    							$('#taTownship').datagrid('reload');
    							$.messager.show({
    								title:'提示',
    								msg:'删除成功'
    							});
    						}
    					});
    				}
    			});
    		}else{
    			$.messager.alert('提示','请您选择一条数据!');
    		}
    	}
    	
    	/**
    	 * 添加区县街道
    	 */
    	function addTownshipStreet(){
    		var nodes = $('#tt').tree('getSelected');
    		$("#areParId").val(nodes.id);
    		$('#addTownshipStreet_edit_form').form('submit',{
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
    					 $('#taTownship').datagrid('reload'); // reload the user data
    					 $('#areaName').val("");
    					 $.messager.show({
    							title:'提示',
    							msg:msgSuccess
    					});
    					$('#addTownshipStreet').dialog('close'); // close the dialog
    				}
    			}
    			
    		});
    	}
    	//编辑
    	function updateTownshipStreetShow(){
    		var rowTable=$("#taTownship").datagrid('getSelected');
    		if(rowTable){
    			$('#addTownshipStreet').dialog();
    			$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#addTownshipStreet_edit_form');
    			$("#addTownshipStreet_edit_form").form('load',rowTable);
    			$("#areParId").val(rowTable.parentArea.id);
    			url="upTownshipStreet";
         		msg="修改失败";
         		msgSuccess="修改成功";
    		}else{
    			$.messager.alert('提示','请选择一条数据进行修改');
    		}
    	}
    </script>
</head>

<body class="easyui-layout">
	 <div data-options="region:'west',title:'导航树'" style="width: 200px;">
	 	<ul id="tt"></ul>     
	 </div>
	 <div data-options="region:'center',title:'数据列表',split:true">
		<table id="taTownship" class="easyui-datagrid"   
        data-options="striped:true,toolbar:'#tb',striped:true, loadMsg:'加载中，请稍后...',rownumbers:true,pagination:true,url:'getAllTownshipStreet',fit:true,fixed:true,singleSelect:true,pagination:true,pageList:[10,25,40,80],pageSize:80">   
			<thead>
				<tr>
					<th data-options="field:'id',width:100,align:'center'">ID</th>
					<th data-options="field:'name',width:200,align:'left'">名称</th>
					<th data-options="field:'parentArea.id',width:100,align:'center',formatter:parentAreaId">父类ID</th>
					<th data-options="field:'order',width:100,align:'center'">排序</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="tb" style="padding: 5px; height: auto;">
			<div style="margin-bottom: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addTownshipStreetShow();">添加</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleTownshipStreetShow();">删除</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateTownshipStreetShow();">编辑</a>
			</div>
	</div>
	<div id="addTownshipStreet" style="width:300px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true" buttons="#dialogButtons" title="数据维护窗口">   
    	   <form id="addTownshipStreet_edit_form" method="post" name="edit_form" data-options="onLoadSuccess:markDeviec">
    	   		 <table id="dgTable" align="center" cellpadding="3">
    	    	<tr>
    	    		<td>名称：</td>
    	    		<td><input id="areaName" class="easyui-validatebox validatebox-text" type="text" required="true" 
maxlength="50" name="name"/></td>
    	    	</tr>
    	    	<tr>
    	    		<td>父级ID:</td>
    	    		<td><input id="areParId" type="text" name="parentArea.id" /></td>
    	    	</tr>
    	    	<tr style="display: none">
    	    		<td>Level:</td>
    	    		<td><input id="areaCity" type="text" name="level" value="TownshipStreet" /></td>
    	    	</tr>
    	    	<tr>
    	    		<td>排序:</td>
    	    		<td><input id="areOrder" type="text" name="order" value="0"/></td>
    	    	</tr>
    	    	<tr>
    	    		<td>状态:</td>
    	    		<td><input id="areStatus" checked="checked" type="checkbox" name="status" /></td>
    	    	</tr>
    	    </table>
    	   </form>
    	  <div id="dialogButtons" style="display: none;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="addTownshipStreet()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelButton()">取消</a>
		</div>
	</div>
</body>
</html>