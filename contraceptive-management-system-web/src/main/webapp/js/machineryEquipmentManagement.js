function formatterConnectionState(value,row,index){
	if(row.machineryEquipmentState==null)
		return "";
	if(row.machineryEquipmentState.connectionState){
		return "在线";
	}
	else
		return "离线";
}
/**
 * 添加设备
 */
var url="";
function addMachineryEquipment(){
	$("#machineryEquipment_edit_dialog").dialog('open');
	$('#machineryEquipment_edit_form').form('clear');
	clearMark(addMap);
	if($('#hiddenId')){
			$('#hiddenId').remove();
	}
	url="machineryEquipmentAdd";
	addArea();
}


function deleteMachineryEquipment()
{
	var row = $('#machineryEquipment_datagrid').datagrid('getSelected');
	if (row)
	{
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.post('deletemachineryequipment',{id:row.id},function(result){
		        	if(result){
						$("#machineryEquipment_datagrid").datagrid('reload');
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
/**
 * 修改设备信息
 */
function updateMachineryEquipment()
{	
	url="updateMachineryEquipment";
	var row = $('#machineryEquipment_datagrid').datagrid('getSelected');
	if(row)
	{
		$("#machineryEquipment_edit_dialog_buttons").css("display","block");
		addmark(row,addMap);
		$("#machineryEquipment_edit_dialog").dialog('open');
		$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#machineryEquipment_edit_form');
		$('#machineryEquipment_edit_form').form("load",row);
		addArea();
		$("#countryAdd").combobox('loadData',[row.area.parentArea.parentArea]);
		$("#countryAdd").combobox('loadData',[row.area.parentArea.parentArea]);
		$("#townshipStreetAdd").combobox('loadData',[row.area.parentArea]);
		$("#communityAdd").combobox('loadData',[row.area]);
		$("#countryAdd").combobox('setValue',row.area.parentArea.parentArea.id);
		$("#countryAdd").combobox('setValue',row.area.parentArea.parentArea.id);
		$("#townshipStreetAdd").combobox('setValue',row.area.parentArea.id);
		$("#communityAdd").combobox('setValue',row.area.id);
		$("#contraceptive1").combobox('setValue',row.aisles[0].contraceptive.id);
		$("#contraceptive2").combobox('setValue',row.aisles[1].contraceptive.id);
		$("#contraceptive3").combobox('setValue',row.aisles[2].contraceptive.id);
		if(row.aislesNum==3){
			$('#contraceptive4').combobox('disable');
		}else{
			$('#contraceptive4').combobox('enable');
			$("#contraceptive4").combobox('setValue',row.aisles[3].contraceptive.id);
		}
	}else{
		$.messager.alert('提示','请选择一条记录！');
	}
}
/**
 * 查看设备信息
 */
function showMachineryEquipment()
{	
	url="updateMachineryEquipment";
	var row = $('#machineryEquipment_datagrid').datagrid('getSelected');
	if(row)
	{
		$("#machineryEquipment_edit_dialog_buttons").css("display","none");
		$("#biaozhu").css("display","none");
		addmark(row,addMap);
		$("#machineryEquipment_edit_dialog").dialog('open');
		$("<input id='hiddenId' type='hidden' name='id'/>").appendTo('#machineryEquipment_edit_form');
		$('#machineryEquipment_edit_form').form("load",row);
		addArea();
		$("#countryAdd").combobox('loadData',[row.area.parentArea.parentArea]);
		$("#countryAdd").combobox('loadData',[row.area.parentArea.parentArea]);
		$("#townshipStreetAdd").combobox('loadData',[row.area.parentArea]);
		$("#communityAdd").combobox('loadData',[row.area]);
		$("#countryAdd").combobox('setValue',row.area.parentArea.parentArea.id);
		$("#countryAdd").combobox('setValue',row.area.parentArea.parentArea.id);
		$("#townshipStreetAdd").combobox('setValue',row.area.parentArea.id);
		$("#communityAdd").combobox('setValue',row.area.id);
//		console.info(row); 
//		console.info(row.aisles[0].contraceptive.name+"  asdfa");
		$("#contraceptive1").combobox('setValue',row.aisles[0].contraceptive.id);
		$("#contraceptive2").combobox('setValue',row.aisles[1].contraceptive.id);
		$("#contraceptive3").combobox('setValue',row.aisles[2].contraceptive.id);
		if(row.aislesNum==3){
			$('#contraceptive4').combobox('disable');
		}else{
			$('#contraceptive4').combobox('enable');
			$("#contraceptive4").combobox('setValue',row.aisles[3].contraceptive.id);
		}
	}else{
		$.messager.alert('提示','请选择一条记录！');
	}
}
/**
 * 保存对话框中的数据
 */

function save(){
	$('#machineryEquipment_edit_form').form('submit',{
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
				 $('#machineryEquipment_edit_dialog').dialog('close'); // close the dialog
				 $('#machineryEquipment_datagrid').datagrid('reload'); // reload the user data
			}
		}
	});
}