var url="";
var msg="";
var msgSuccess="";
function newRole(){
	$('#dialog').dialog('open').dialog('setTitle','添加新角色');
	$('#fm').form('clear');
	$("input[name='id']").val(0);
	msg="添加失败";
	msgSuccess="添加成功";
	url="addrole";
}
function editRole(){
	var row=$('#dg').datagrid('getSelected');
	if(row){
 		$('#dialog').dialog('open').dialog('setTitle','修改角色');
 		$('#fm').form('load',row);
 		$("input[name='id']").val(row.id);
 		if(row.state){
 			$("input[name='state_temp']").prop("checked",true);
 		}else{
 			$("input[name='state_temp']").prop("checked",false);
 		}
 		url="updaterole";
 		msg="修改失败";
 		msgSuccess="修改成功";
	}else {
		$.messager.alert('提示','请您选择一条数据!');
	}
	$("#dialogButtons1").show();
}
function saveRole(){
	$('#fm').form('submit',{
		url:url,
		onSubmit:function(param){
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
function deleteRole(){
	var row = $('#dg').datagrid('getSelected');
	if (row)
	{
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.post('deleterole',{id:row.id},function(result){
		        	if(result){
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
function selectRole()
{
	var row = $('#dg').datagrid('getSelected');
	if(row)
	{
		$('#dialog').dialog('open').dialog('setTitle','查看角色');
		$('#fm').form('load',row);
		if(row.state){
 			$("input[name='state_temp']").prop("checked",true);
 		}else{
 			$("input[name='state_temp']").prop("checked",false);
 		}
		$("#dialogButtons1").hide();
	}
}
function searchRole()
{
	$('#dialog1').dialog('open').dialog('setTitle','查询窗口');
}
function saveSearch()
{
	var rolename=$("#rolename").val();
     $('#dg').datagrid({
    		url: 'searchbyname',
    		queryParams:{
    			name:rolename	
    		}
    	});
     $('.easyui-dialog').dialog('close');
}
function selectUserOfRole(id)
{
	$('#dialog2').dialog('open').dialog('setTitle','角色下的用户');
	$('#dg1').datagrid({
		url: 'managerlistofrole',
		queryParams:{
			id:id
		}
	});
}
function setPermission()
{
	$('#dialog3').dialog('open').dialog('setTitle','设置权限');
	$.post("loadmenus",function(d){
		var data = convertMenu2Tree(d);
		$("#tt").tree({
			data:[data],
			checkbox:true
		});
		$.post("loadpermission",{id:$('#dg').datagrid('getSelected').id},function(data){
			if(data){
				for(i in data){
					var node = $('#tt').tree('find',data[i].id);
					$('#tt').tree('check',node.target);  
				}
			}
		});
	});
}

function formatterOperate(value,row,index){
	return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='selectUserOfRole("+row.id+")'>查看用户</a>"+"  "+"<a href='javascript:void(0)' style='text-decoration:none' onclick='setPermission()'>设置权限</a>";
}
 function fromatterState(value, row, index){
	if (value) {
		return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: green' ><img src='images/ok.png'></a>";
	} else {
		return "<a href='javascript:updateState("+row.id+");' class='easyui-splitbutton' style='color: red' data-options='iconCls:'icon-cancel''><img src='images/no.png'></a>";
	}
}
function updateState(id){
    $.ajax({
         type: "post",
         url: "updaterolestate",
         data: {id:id},
         success: function (result) {
         	if(result){
				$("#dg").datagrid('reload');
			}
         }
 	});       
} 
function formatterName(value,row,index){
	if(row.department){
		if(row.department.name)
		{
			return row.department.name;
		}
	}else{
		return "";
	}
}

function savePermission(){
	var checked = $("#tt").tree("getChecked");
	var row = $('#dg').datagrid('getSelected');
	var data = new Array();
	for(var i=0; i<checked.length;i++){
		data.push(checked[i].id);
	}
	$.ajax({
		data:{id:row.id,menus:data},
		dataType:'json',
		type:'post',
		url:'savepermission',
		success:function(result){
			if(result.success){
				 $.messager.show({
					 title: '成功',
					 msg: '设置成功'
					 });
				 
				 $('.easyui-dialog').dialog('close');
			}else{
				 $.messager.show({
					 title: '错误',
					 msg: '设置失败'
					 });
			}
		}
	});
}

$(function(){
	$("input[name='state_temp']").click(function(){
		if($("input[name='state']").val()=="true"){
			$("input[name='state']").val("false");
		}else{
			$("input[name='state']").val("true");
		}
	});
});
