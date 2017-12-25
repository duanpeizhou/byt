(function(){
	$.extend($.fn.combobox.defaults, {panelHeight:210});
	$.extend($.fn.datagrid.defaults, {
		nowrap: false,
		rownumbers: true
	});
})();

/*
 * 得到社区的数据
 */
function communityArea(value, row, index) {
	if (row.machineryEquipment.area.name) {
		return row.machineryEquipment.area.name;
	}else {
		return "";
	}
}
/*
 * 得到街道的数据
 */
function townStreetArea(value, row, index) 
{
	if(!row.machineryEquipment.area.parentArea)
	{
		return "";
	}
	if (row.machineryEquipment.area.parentArea.name) {
		return row.machineryEquipment.area.parentArea.name;
	}
}
/*
 * 得到区的数据
 */
function countryArea(value, row, index) {
	if(!row.machineryEquipment.area.parentArea.parentArea)
	{
		return "";
	}
	if (row.machineryEquipment.area.parentArea.parentArea.name) {
		return row.machineryEquipment.area.parentArea.parentArea.name;
	}
}
/*
 * 得到市的数据
 */
function cityArea(value, row, index) {
	if(!row.machineryEquipment.area.parentArea.parentArea.parentArea)
	{
		return "";
	}
	if (row.machineryEquipment.area.parentArea.parentArea.parentArea.name) {
		
		return row.machineryEquipment.area.parentArea.parentArea.parentArea.name;
	}
}
/*
 * 得到省的数据
 */
function privenceArea(value, row, index) {
	if(!row.machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name)
	{
		return "";
	}
	if (row.machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name) {
		return row.machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name;
	}
}

/*
 * 得到社区的数据
 */
function communityArea2(value, row, index) {
	if (row.area.name) {
		return row.area.name;
	}else{
		return "";
	}
}
/*
 * 得到街道的数据
 */
function townStreetArea2(value, row, index) {
	if (row.area.parentArea.name) {
		return row.area.parentArea.name;
	}else{
		return "";
	}
}
/*
 * 得到区的数据
 */
function countryArea2(value, row, index) {
	if (row.area.parentArea.parentArea.name) {
		return row.area.parentArea.parentArea.name;
	}else{
		return "";
	}
}
/*
 * 得到市的数据
 */
function cityArea2(value, row, index) {
	if (row.area.parentArea.parentArea.parentArea.name) {
		return row.area.parentArea.parentArea.parentArea.name;
	}else{
		return "";
	}
}
/*
 * 得到省的数据
 */
function privenceArea2(value, row, index) {
	if (row.area.parentArea.parentArea.parentArea.parentArea.name) {
		return row.area.parentArea.parentArea.parentArea.parentArea.name;
	}else{
		return "";
	}
}

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

/*
加载地点列表
*/

function selectOneLine(data){
	 $('#dg').datagrid("selectRow",0);
}
$("#distributionPoints").css("height","17px");


