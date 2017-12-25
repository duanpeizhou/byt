<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>避孕药具自助发放机综合管理平台</title>
<link href="js/jquery-easyui-1.3.5/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.5/themes/icon.css" rel="stylesheet"
	type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-easyui-1.3.5/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="js/commonJS1.js" type="text/javascript"></script>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
/**
 * 取消
 */
function cancelButton(){
	$("#addDepartment").dialog('close');
	$('#addDepartment_edit_form input[name ="name"]').val("");
}
	$(function(){
			$('#tDepartment').tree({
				url:'getAllDepartment',
				formatter:function(node){
					return node.name;
				},
				onSelect:function(node){
					params = {};
 					params.id=node.id;
 					$('#taTb').datagrid({
 	            		url: 'getAllDepartmentChild',
 	            		queryParams:params
 					});
			}
 		});
	});
	function number(value,row,index){
		return row.childDepartments.length;
	}
	/**
	 * 添加部门显示
	 */
	 	var msg="";
		var msgSuccess="";
		var url="";
	function addDepartmentShow(){
		var nodes = $('#tDepartment').tree('getSelected');
		if(nodes){
			$('#addDepartment').dialog();
			$("#addDepartment_edit_form").form("reset");
			$("#addAreParId").val(nodes.id);
			msg="添加失败";
     		msgSuccess="添加成功";
     		url="addDepartment";
		}else{
			$.messager.alert('提示','请选择对应部门!');
		}
	}
	/**
	 * 添加区县街道
	 */
	function addDepartment(){
		var nodes = $('#tDepartment').tree('getSelected');
		$("#addAreParId").val(nodes.id);
		$('#addDepartment_edit_form').form('submit',{
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
					$('#addDepartment').dialog('close'); // close the dialog
					 $('#taTb').datagrid('reload'); // reload the user data
					 $('#addDeName').val("");
					 $.messager.show({
							title:'提示',
							msg:msgSuccess
					});
				}
			}
		});
	}
	/**
	 * 删除区县街道
	 */
	function deleDepartmentShow(){
		var selectRow=$('#taTb').datagrid('getSelected');
		if(selectRow){
			$.messager.confirm('确认','您确定要删除？',function(data){
				if(data){
					$.post('deleteDepartment',{id:selectRow.id},function(){
							$('#taTb').datagrid('reload');
							$.messager.show({
								title:'提示',
								msg:'删除成功'
							});
					});
				}
			});
		}else{
			$.messager.alert('提示','请您选择一条数据!');
		}
	}
	//编辑
	function updateDepartmentShow(){
		var rowTable=$("#taTb").datagrid('getSelected');
		if(rowTable){
			$('#addDepartment').dialog();
			$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#addDepartment_edit_form');
			$("#addDepartment_edit_form").form('load',rowTable);
			url="updateDepartment";
     		msg="修改失败";
     		msgSuccess="修改成功";
		}else{
			$.messager.alert('提示','请选择一条数据进行修改');
		}
	}
	//查看
	function findShow(){
		var rowTable=$("#taTb").datagrid('getSelected');
		if(rowTable){
			$('#showDepartment').dialog();
			$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#addDepartment_show_form');
			$("#addDepartment_show_form").form('load',rowTable);
			$("#addDepartment_show_form input").attr("disabled","disabled");
		}else{
			$.messager.alert('提示','请选择一条数据进行查看');
		}
	}
	//通过name查找数据
	function findByName(){
		$('#findByName').dialog({
			buttons:[{
				text:'提交',
				handler:function(){
					var name=$('#departmentName').val();
					/* if(data!=null){
						$('#taTb').datagrid({
							url: 'findByName'
						});
						$('#findByName').dialog('close');
					}else{
						$.messager.alert('提示','没有数据');
					} */
					$('#taTb').datagrid({
	            		url: 'findByName',
	            		queryParams:{
	            			name:name
	            		}
	            	});
					$('#findByName').dialog('close');
					$('#departmentName').val("");
				}
			},{
				text:'关闭',
				handler:function(){
					$("#findByName").dialog('close');
				}
			}]
		});
	}
</script>
</head>
<body class="easyui-layout">
	 <div data-options="region:'west',title:'导航树'" style="width: 200px;">
	 	<ul id="tDepartment"></ul>     
	 </div>
	 <div data-options="region:'center',title:'数据列表',split:true">
	 	<table id="taTb" class="easyui-datagrid"   
        	data-options="striped:true,toolbar:'#tb', striped:true, loadMsg:'加载中，请稍后...',rownumbers:true,pagination:true,url:'getAllDepartmentChild',fit:true,fixed:true,singleSelect:true,pagination:true,pageList:[10,25,40,80],pageSize:80">
        	<thead>
				<tr>
					<th data-options="field:'id',width:100,fixed:true,width:80,align:'center'">ID</th>
					<th data-options="field:'name',width:100,fixed:true,width:80,align:'left'">名称</th>
					<th data-options="field:'level',width:100,fixed:true,width:80,align:'center'">层级</th>
					<th data-options="field:'childDepartments',width:100,formatter:number,fixed:true,width:80,align:'center'">子集个数</th>
				</tr>
			</thead>
        </table>
	 </div>
	 <div id="tb" style="padding: 5px; height: auto;">
			<div style="margin-bottom: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDepartmentShow();">新增</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleDepartmentShow();">删除</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateDepartmentShow();">编辑</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="findShow()">查看</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="findByName()">查找</a>
			</div>
	</div>
	<div id="findByName" style="width:300px;height:200px;"data-options="iconCls:'icon-save',resizable:true,modal:true" title="查找">
		<form id="findByName_edit_form" method="post" name="edit_form" data-options="onLoadSuccess:markDeviec">
			<table id="dgTable" align="center" cellpadding="3">
				<tr>
					<td>名称</td>
					<td><input id="departmentName" type="text" name="name" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="addDepartment" style="width:300px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true" buttons="#dialogButtons" title="数据维护窗口">   
    	   <form id="addDepartment_edit_form" method="post" name="edit_form" data-options="onLoadSuccess:markDeviec">
    	   		 <table id="dgTable" align="center" cellpadding="3">
    	    	<tr>
    	    		<td>名称：</td>
    	    		<td><input id="addDeName" class="easyui-validatebox validatebox-text" type="text" required="true" 
maxlength="50" name="name"/></td>
    	    	</tr>
    	    	<tr>
    	    		<td></td>
    	    		<td><input id="addAreParId" type="hidden"  name="pranetDepartment.id" /></td>
    	    	</tr>
    	    	<tr>
    	    		<td>Level:</td>
    	    		<td><input id="addDeLevel" type="text" name="level" value="2" readonly="readonly"/></td>
    	    	</tr>
    	    	<tr>
    	    		<td>描述:</td>
    	    		<td><input id="description" type="text" name="description" value=""/></td>
    	    	</tr>
    	    </table>
    	   </form>
    	  <div id="dialogButtons" style="display: none;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="addDepartment()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelButton()">取消</a>
		</div>
	</div>
	<div id="showDepartment" style="width:300px;height:200px;"   
        data-options="iconCls:'icon-search',resizable:true,modal:true" title="数据查看窗口">   
    	   <form id="addDepartment_show_form" method="post" name="show_form" data-options="onLoadSuccess:markDeviec">
    	   		 <table id="dgTable" align="center" cellpadding="3">
    	    	<tr>
    	    		<td>名称：</td>
    	    		<td><input id="deName" type="text"  name="name"/></td>
    	    	</tr>
    	    	<tr>
    	    		<td></td>
    	    		<td><input id="areParId" type="hidden" name="pranetDepartment.id" /></td>
    	    	</tr>
    	    	<tr>
    	    		<td>Level:</td>
    	    		<td><input id="deLevel" type="text" name="level" value="2" readonly="readonly"/></td>
    	    	</tr>
    	    	<tr>
    	    		<td>描述:</td>
    	    		<td><input id="description" type="text" name="description"  value=""/></td>
    	    	</tr>
    	    </table>
    	   </form>
	</div>
</body>
</html>