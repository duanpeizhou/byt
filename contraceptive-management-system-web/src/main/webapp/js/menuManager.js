function number(value,row,index){
	return row.childMenu.length;
}

$(function(){
	$.post('menuTree',function(data){
		var root=data.childMenu;
		var tree=$('<ul id="tree" class="easyui-tree"></ul>').appendTo('#westId');;
		var treeli=$('<li><span><a onclick="specifiedData(-1);">菜单管理</a></span></li>').appendTo(tree);
		var treeul=$('<ul></ul>').appendTo(treeli);
		for(var i=0;i<root.length;i++){
			var li=$('<li><span><a onclick="specifiedData('+root[i].id+');">'+root[i].name+'</a></span></li>').appendTo(treeul);
			var ul=$('<ul></ul>').appendTo(li);
			var childTree=root[i].childMenu;
			for(var j=0;j<childTree.length;j++){
				var ulli=$('<li></li>').appendTo(ul);
				$('<a onclick="specifiedData('+childTree[j].id+');"></a>').html(childTree[j].name).appendTo(ulli).click(function(){
					/* alert(childTree[j].id); */
				});
			}
		}
		$.parser.parse('#westId');
	});
});
	function specifiedData(s){
		$('#dg').datagrid({
    		url: 'menuManagerDatagrid',
    		queryParams:{
    			id:s
    		}
    	});
	}
	
	var url="";
 	var msg="";
 	var msgSuccess="";
	function addMenu(){
		$('#dlg').dialog('open').dialog('setTitle','添加新菜单');
		$('#fm').form('clear');
 		if($('#hiddenId')){
 			$('#hiddenId').remove();
 		}
		url="addMenu";
		msg="添加失败";
 		msgSuccess="添加成功";
	}
	function updateMenu(){
		var row=$('#dg').datagrid('getSelected');
 		if(row){
     		$('#dlg').dialog('open').dialog('setTitle','修改药具');
     		$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#fm');
     		$('#fm').form('load',row);
     		url="udpateMenu";
     		msg="修改失败";
     		msgSuccess="修改成功";
 		}else {
 			$.messager.alert('提示','请您选择一条数据!');
 		}
 		$("#buttons").show();
	}
	function saveMenu(){
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
					 $('#dlg').dialog('close'); // close the dialog
					 $('#dg').datagrid('reload'); // reload the user data
					 $.messager.show({
							title:'提示',
							msg:msgSuccess
						});
				}
			}
		});
	}
	function deleteMenu(){
		var row = $('#dg').datagrid('getSelected');
		if (row)
		{
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){    
			        $.post('deleteMenu',{id:row.id},function(result){
			        	if(result){
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
	function selectMenu()
	{
		var row = $("#dg").datagrid('getSelected');
		if(row)
		{
			$('#dlg').dialog('open').dialog('setTitle','查看菜单');
			$('#fm').form('load',row);
			$("#buttons").hide();
		}
		else
		{
			$.messager.alert('提示','请您选择一条数据!');
		}
	}
	function searchMenu()
	{
		$('#dialog1').dialog('open').dialog('setTitle','查询窗口');
	}
	function saveSearch()
	{
		var menuName = $("#menuname").val();
		$("#dg").datagrid({
			url:"searchmenubyname",
			queryParams:{
				name:menuName
			}
		});
		$('#dialog1').dialog('close');
	}