<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户管理</title>
<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet"
	type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-easyui-1.3.4/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<!-- <script src="js/commonJS1.js" type="text/javascript"></script> -->
<script type="text/javascript">
	//得到所有部门
	$(function() {
		$('#tDepartment').tree({
			url : 'getAllDepartment',
			formatter : function(node) {
				return node.name;
			},
			onSelect:function(node){
			params = {};
				params.id=node.id;
				$('#dg').datagrid({
         		url: 'getManagerByDep',
         		queryParams:params
				});
		}
		});
	});

	var url = "";
	function addManager() {
		url = "managerAdd";
		 $('#username2').val("");
		$('#name2').val("");
		$('#password2').val("");
		$('#note').val(""); 
		//$('#ff').form('clear');
		$('#w').window('open').dialog('setTitle', '添加用户');
		if ($('#hiddenId')) {
			$('#hiddenId').remove();
		}
		$('#country').combobox('enable');
		$('#townshipStreet').combobox('enable');
		addArea();
		$('#townshipStreet').combobox({
     		data: [{id:-1,name:'全  部'}]
     	});
	}


	function updateManager() {
		url = "managerUpdate";
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#w').window('open').dialog('setTitle', '修改用户');
			if ($('#hiddenId')) {
				$('#hiddenId').remove();
			}
			$("<input id='hiddenId' type='hidden'  name='id'/>").appendTo('#ff');
			$('#ff').form("load", row);
			
			if(row.superManager){
				addArea();
				$('#townshipStreet').combobox({
    	     		data: [{id:-1,name:'全  部'}]
    	     	});
				$('#country').combobox({
    	     		data: [{id:-1,name:'全  部'}]
    	     	});
				$('#country').combobox('disable');
				$('#townshipStreet').combobox('disable');
			}else{
				if(row.townshipStreet==null){
					addArea();
					$("#country").combobox('setValue',row.county.id);
					addTown(row.county.id);
					$("#townshipStreet").combobox('setValue',-1);
				}else{
					addArea();
					$("#country").combobox('setValue',row.county.id);
					addTown(row.county.id);
					$("#townshipStreet").combobox('setValue',row.townshipStreet.id);
				}
				$('#country').combobox('enable');
				$('#townshipStreet').combobox('enable');
				
			}
			
		} else {
			$.messager.alert('提示','请选择一条记录!');
		}
	}
	function showManager() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#w1').window('open').dialog('setTitle', '查看用户');
			$('#ff1').form("load", row);
			$('#ff1 input').attr("disabled","disabled");
			
		} else {
			$.messager.alert('提示','请选择一条记录!');
		}
	}
	function addTown(countryId){
		$.ajax({
            type: "post",
            url: "getAreaByParentAreaId",
            data: {parentId:countryId},
            success: function (result) {
           	 result.unshift({id: -1, name: '全部'});
    			$('#townshipStreet').combobox({
    	     		data: result,
    	     	});
            },
            async: false
    	});   
	}
	function save() {
		$('#ff').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (result) {
					$('#w').window('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				} else {
					$.messager.show({
						title : '提示',
						msg : "操作成功"
					});

				}
			}
		});
	}

	//删除 success
	function deleteManager() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
				if (r) {
					$.post('deleteManager',{id : row.id}, function(result) {
						result = jQuery.parseJSON(result);
						if (result && result.success == true) {
							$("#dg").datagrid('reload');
							$.messager.show({
								title : '提示',
								msg : '删除成功!'
							});
						} else {
							$.messager.show({
								title : '提示',
								msg : '删除失败!'
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请您选择一条数据!');
		}
	}
	
	
	
	function searchManager() {
		$('#search').window('open').dialog('setTitle', '查找用户');
	}

	function search1() {
		
             var username=$('#username1').val();
             $('#dg').datagrid({
            		url: 'searchManager',
            		queryParams:{
            			username:username	
            		}
            	});
             $('#search').window('close');
	}

	function fromatterEnable(value, row, index) {
		if (value==true) {
			return "<a href='javascript:updateEnable("+row.id+");' class='easyui-splitbutton' style='color: green' ><img src='images/ok.png'></a>";
		} else if(value==false){
			return "<a href='javascript:updateEnable("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/no.png'></a>";
		}
	}
	
	function updateEnable(id){
		 $.ajax({
              type: "post",
              url: "updateEnable",
              data: {id:id },
              success: function (result) {
              	if(result){
					$("#dg").datagrid('reload');
					}
              },
             
              
      	});       
	}
	
	function fromatterRole(value, row, index) {
		return "<a href='javascript:loadRole("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-search''>设置角色</a>";
	}
	
	function loadRole(id){
		$('#ww2').window('open').dialog('setTitle', '角色设置');
		$('#dgRole').datagrid({
    		url: 'roleList'
    	});
	}
	
	
	
	function fromatterstate(value, row, index){
		var row = $('#dg').datagrid('getSelected');
		if (value==true) {
			return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: green' ><img src='images/ok.png'></a>";
		} else if(value==false){
			return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/no.png'></a>";
		}
	}
	function updateState(id){
		 $.ajax({
             type: "post",
             url: "updateState",
             data: {id:id,roleId:$('#id').val()},
             success: function (result) {
             	if(result  == true){
					$("#dg1").datagrid('reload');
					}
             }
     	});       
	}
	/* 
	地点下拉选
	*/
	function addArea(){
		 $.ajax({
             type: "post",
             url: "countryMenuOptions",
             data: {id:<spring:message code="City.no"/>},
             success: function (result) {
            	 result.unshift({id: -1, name: '全部'});
     			$('#country').combobox({
     	     		data: result,
     	     		onSelect:function(rec){
     	     		if(rec.id!=-1){
     	     			$.post('townshipStreetMenu',{id: rec.id},function(data){
     						data.unshift({id: -1, name: '全部'});
     						$('#townshipStreet').combobox({
     		         			data: data
     		         		});
     	     			});
     	         		
     	     		}else {
     	     			$('#townshipStreet').combobox('loadData',new Array());
     	     			$('#townshipStreet').combobox('clear');
     	     			$('#townshipStreet').combobox({
     	         			data: {id: -1, name: '全部'}
     	         		});
     	     		}
     	     	}});
             },
             async: false
     	});   
	}
	
	function areaLimit(){
		$('#country').combobox('disable');
		$('#townshipStreet').combobox('disable');
	}
	function areaLimit2(){
		$('#country').combobox('enable');
		$('#townshipStreet').combobox('enable');
	}
	$.extend($.fn.validatebox.defaults.rules, {
		number : {
			validator : function(value) {
				return "全部"!=value;
			},
			message : '请选择地点或选择超级管理员'
		}
	});
	
</script>
</head>

<body class="easyui-layout">
	<div data-options="region:'west',title:'导航树'" style="width: 200px;">
		<ul id="tDepartment"></ul>
	</div>

	<div data-options="region:'center',title:'数据列表'">
		<table id="dg" rownumbers="true" class="easyui-datagrid"
			style="width: 570px;"
			data-options="striped:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,20,4,50],pagination:true,url:'managerList',singleSelect:true,pageSize:20,toolbar:'#tb'">
			<thead>
				<tr>  
					<th field="id" data-options="formatter:fromatterRole,fixed:true,width:80,align:'center'">操作</th>
					<th field="enable" data-options="formatter:fromatterEnable,fixed:true,width:80,align:'center'">状态</th>
					<%--<th field="username" align="center" data-options="fixed:true,width:150,align:'left'">用户名</th>--%>
					<th field="name" align="center"  data-options="fixed:true,width:150,align:'left'">真实姓名</th>
					<th field="ip"  data-options="fixed:true,width:120,align:'center'">最后登录Ip</th>
					<th field="lastLoginTime"  data-options="fixed:true,width:160,align:'center'">最后登录时间</th>
					<th field="LastModifyPasswordTime"  data-options="fixed:true,width:160,align:'center'">上次修改密码时间</th>
					<th field="loginTimes"  data-options="fixed:true,width:80,align:'center'">登录次数</th>

				</tr>
			</thead>
		</table> 
		<div id="tb" style="padding: 5px; height: auto">
			<div style="margin-bottom: 5px">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="addManager();">添加</a> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true" onclick="updateManager();">修改</a>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="deleteManager();">删除</a>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" onclick="showManager();">查看</a>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" onclick="searchManager();">查找</a>
			</div>
		</div>
	</div>

	<!-- 添加开始 -->
	<div id="w" class="easyui-window" title="数据维护窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 400px; height: 370px; padding: 10px;">
		<form id="ff" method="post">
			<table width="100%" border="0" align="left" cellspacing="0"
				cellpadding="0" style="padding: 5px;">

				<tr>
					<td align="left">用户名：</td>
					<td><input name="username" id="username2" class="easyui-validatebox"
						required="true" /></td>
				</tr>
				<tr>
					<td align="left">管理员密码：</td>
					<td><input name="password" id="password2" class="easyui-validatebox"
						required="true" /></td>
				</tr>

				<tr>
					<td align="left">真实姓名：</td>
					<td><input name="name" id="name2" class="easyui-validatebox"
						required="true" /></td>
				</tr>
				<tr>
					<td align="left">是否超级管理员：</td>
					<td>
						是：<input type="radio" value="true" name="superManager" onclick="areaLimit();"/>
						否：<input type="radio" value="false" name="superManager" onclick="areaLimit2();" checked="checked"/>
						</td>
				</tr>
				<tr>
					<td align="left">所在区县：</td>
					<td><input id="country" class="easyui-combobox" value="-1" name="county.id"
						data-options="valueField:'id',textField:'name',validType:'number'" /></td>
				</tr>
				<tr>
					<td align="left">所在街道：</td>
					<td><input id="townshipStreet" class="easyui-combobox" name="townshipStreet.id"
						value="-1" 
						data-options="valueField:'id',textField:'name'" /></td>
				</tr>
				<tr align="left">
					<td align="left">锁定状态：</td>
					<td>是：<input type="radio" value="false" name="enable" />
						否：<input type="radio" value="true" name="enable" checked="checked"/>
					</td>
				</tr>
				<tr>
					<td align="left">备注：</td>
					<td><textarea id="note"
							class="easyui-validatebox validatebox-text" rows="3"
							validtype="length[0,300]" cols="25" name="note"></textarea></td>
				</tr>
			</table>
		</form>
		<div
			style="text-align: center; height: 30px; line-height: 30px; width: 400px; display: block;"
			border="false" region="south"
			class="layout-body panel-body panel-body-noheader panel-body-noborder"
			title="">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">确定</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#w').dialog('close')">关闭</a>
		</div>
	</div>
	<!-- 添加结束 -->
	<!-- 显示开始 -->
	<div id="w1" class="easyui-window" title="数据维护窗口"
		data-options="modal:true,closed:true,iconCls:'icon-search'"
		style="width: 400px; height: 370px; padding: 10px;">
		<form id="ff1" method="post">
			<table width="100%" border="0" align="left" cellspacing="0"
				cellpadding="0" style="padding: 5px;">

				<tr>
					<td align="left">用户名：</td>
					<td><input name="username" id="username2" /></td>
				</tr>
				<tr>
					<td align="left">管理员密码：</td>
					<td><input name="password" id="password2"  /></td>
				</tr>

				<tr>
					<td align="left">真实姓名：</td>
					<td><input name="name" id="name2"/></td>
				</tr>
				<tr>
					<td align="left">是否超级管理员：</td>
					<td>
						<input type="text"  name="superManager" />
						</td>
				</tr>
				<tr>
					<td align="left">所在区县：</td>
					<td><input id="country" name="county.name" /></td>
				</tr>
				<tr>
					<td align="left">所在街道：</td>
					<td><input id="townshipStreet"  name="townshipStreet.name"/></td>
				</tr>
				<tr align="left">
					<td align="left">锁定状态：</td>
					<td><input type="text" name="enable" />
					</td>
				</tr>
				<tr>
					<td align="left">备注：</td>
					<td><textarea id="note" rows="3" cols="25" name="note"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 添加结束 -->
	

	<!-- 查询开始 -->
	<div id="search" class="easyui-window" title="数据维护窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 400px; height: 150px; padding: 10px;">

		<form id="ss" method="post">
			<table width="100%" border="0" align="left" cellspacing="0"
				cellpadding="0" style="padding: 5px;">

				<tr>
					<td align="left">用户名：</td>
					<td><input id="username1" name="username" /></td>
				</tr>

			</table>
		</form>
		<div
			style="text-align: center; height: 30px; line-height: 30px; width: 400px; display: block;"
			border="false" region="south"
			class="layout-body panel-body panel-body-noheader panel-body-noborder"
			title="">
			<a 
			    href="javascript:void(0);" class="easyui-linkbutton"
				data-options="iconCls:'icon-save'" onclick="search1()">确定</a> 
			<a
				href="javascript:void(0);" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'"
				onclick="javascript:$('#search').dialog('close')">关闭</a>
		</div>
	</div>
	<!-- 查询结束 -->
	<!-- 更新脚色 -->
	<div id="ww2" class="easyui-window"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 450px; height: 400px; padding: 0px;">
		<table id="dgRole" class="easyui-datagrid" style="width:400px;height:365px"
		data-options="fit:true,loadMsg:'加载中，请稍后...',pageList:[10,20,40,50],pagination:true,fitColumns:true,singleSelect:true,pageSize:20,url:'',singleSelect:true">
			<thead>
				<tr>
					<th field="id" align="center" width="45px">角色ID</th>
					<th field="name" align="center" width="90px">角色名</th>
					<th field="xxx" align="center" width="45px" data-options="formatter:stateFormatter">设置角色</th>
				</tr>
			</thead>
		</table>

	</div>                                      
	<!-- 更新脚色  -->
	<script type="text/javascript">
		function stateFormatter(value,row,index){
			var r=$('#dg').datagrid('getSelected');
			var roles=new Array();
			$.ajax({
				url:'getManagerById',
				type:'POST',
				data:'id='+r.id,
				dataType:'json',
				success:function(root){
					roles=root.role;
				},
				async: false
			});
				if(roles.length!=0){
					for(var i=0;i<roles.length;i++){
						if(roles[i].id==row.id){
							return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/ok.png'></a>";
						}
					}
						return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/no.png'></a>";
				}else{
					return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/no.png'></a>";
				}
		}
		function updateState(roleId){
			var row=$('#dg').datagrid('getSelected');
			$.post('updateManagerRole',{managerId:row.id,roleId:roleId},function(resulte){
				if(resulte){
					$("#dgRole").datagrid('reload'); 
					
				}else{
					alert("修改失败");
				}
			});
		}
	</script>

</body>
</html>