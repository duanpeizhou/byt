<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
        <link href="css/default.css" rel="stylesheet" type="text/css" />
	    <link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet" type="text/css" />
	    <script src="js/jquery-easyui-1.3.4/jquery.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js" type="text/javascript"></script>
	    <script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	    <script src="js/commonJS1.js" type="text/javascript"></script>
	     <script src="js/baidumap.js" type="text/javascript"></script>
	 	 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=39d01498d77eb3144433767a17e3f796"></script>
	    <script src="js/machineryEquipmentManagement.js" type="text/javascript"></script>
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
        	var map;
        	var addMap;
	    
            $(function(){
	           map = newMap("map",'<spring:message code="City.name"/>');
    	       addMap = newMap("addmap",'<spring:message code="City.name"/>');
             	/* $('#country').combobox({'data':[{id:-1,name:'全部'}]});
         		$('#townshipStreet').combobox({'data':[{id:-1,name:'全部'}]}); */
             	/* $('#city').combobox({
             		value:"全部",
             		url:'cityMenu',
             		onSelect:function(rec){
             		if(rec.id!=-1){
 	            		$('#country').combobox({
 	            			 url:'countryMenuOptions?id='+rec.id,
 							 value:"全部"
 	            			
 	            		});
 	            		$('#community').combobox('loadData',new Array());
 	            		$('#community').combobox('clear');
             		}else {
             			$('#country').combobox('loadData',new Array());
             			$('#townshipStreet').combobox('loadData',new Array());
             			$('#country').combobox('clear');
             			$('#townshipStreet').combobox('clear');
             			('#community').combobox('loadData',new Array());
             			$('#community').combobox('clear');
             		}
             	
             		
             		
             		}});
             	
             	$('#country').combobox({
             	
             		
             		onSelect:function(rec){
             			if(rec.id!=-1){
 		            		$('#townshipStreet').combobox({
 		            			 url:'townshipStreetMenu?id='+rec.id,
 								 value:"全部"
 		            			
 		            		});
             			}else {
             				$('#townshipStreet').combobox('loadData',new Array());
             				$('#townshipStreet').combobox('clear');
             				('#community').combobox('loadData',new Array());
                 			$('#community').combobox('clear');
             			}
             	}});
             	 */
             });
            
            /*
            	添加设备地点下拉选框
            */
            function addArea(){
            	$('#countryAdd').combobox({
            		value:"请选择",
            		url:'countryMenuOptions?id=<spring:message code="City.no"/>',
            		onSelect:function(rec){
            			if(rec.id!=-1){
	            			$('#townshipStreetAdd').combobox({
	            				value:"请选择",
	            				url:'townshipStreetMenu?id='+rec.id
	            			});
	            			$('#community').combobox('loadData',new Array());
	                 		$('#community').combobox('clear');
            			}else{
            				$('#townshipStreetAdd').combobox('loadData',new Array());
                 			$('#communityAdd').combobox('loadData',new Array());
                 			$('#townshipStreetAdd').combobox('clear');
                 			$('#communityAdd').combobox('clear');
            			}
            		}
            	});
            	
            	$('#townshipStreetAdd').combobox({
             		onSelect:function(rec){
             			if(rec.id!=-1){
 		            		$('#communityAdd').combobox({
 		            			 url:'communityMenu?id='+rec.id,
 								 value:"全部"
 		            			
 		            		});
             			}else {
             				$('#communityAdd').combobox('loadData',new Array());
             				$('#communityAdd').combobox('clear');
             			}
             	}});
            }
            function disableCtraceptive4(){
            	$('#aislesId').combobox({
            		onSelect:function(rec){
            			if(rec.id==3){
            				$('#contraceptive4').combobox('disable');
            			}else {
            				alert(3);
            			}
            		}
            	});
            }
        	function submitForm(){
            	params = {};
//             	params.cityId = $("#city").combobox('getValue') != "全部" && $("#city").combobox('getValue').length>0?$("#city").combobox('getValue'):-1;
            	params.countryId = $("#country").combobox('getValue') != "全部"&&$("#country").combobox('getValue').length>0?$("#country").combobox('getValue'):-1;
            	params.townshipStreetId = $("#townshipStreet").combobox('getValue') != "全部"&&$("#townshipStreet").combobox('getValue')>0?$("#townshipStreet").combobox('getValue'):-1;
            	params.communityId = $("#community").combobox('getValue') != "全部"&&$("#community").combobox('getValue')>0?$("#community").combobox('getValue'):-1;
            	params.distributionPoints = $("#distributionPoints").val();
            	$('#machineryEquipment_datagrid').datagrid({
            		url: 'specifiedMachineryequipment',
            		queryParams:params
            	});
            }
            
            function filterDataFun(data){
            	if(data)
            		data.unshift({id:-1,name:"请选择"});
            	else
            		data = new Array();
            	return data;
            }
            function filterDataFun1(data){
            	if(data)
            		data.unshift({id:-1,name:"全部"});
            	else
            		data = new Array();
            	return data;
            }
            function clickbtnsetpoint(){
            	positionEquipment(function(p){
	       			$("#Longitude").val(p.lng);
	       			$("#Dimension").val(p.lat);
            	},addmap);
            }
            
            function locationDis(){
            	var local = new BMap.LocalSearch(addMap, {
            		  renderOptions:{map: addMap, autoViewport:true}
            		});
            	var key = $("#DistributionPoints").val();
            	local.searchNearby(key, '<spring:message code="City.name"/>');
            }

            
            function onLoadSuccessFun(data){
            	clearMark(map);
            	addmark(data.rows,map);
            	$("#machineryEquipment_datagrid").datagrid("selectRow",0);
            }
            function markDeviec(data){
            	clearMark(addMap);
            	addmark(data,addMap);
            }
            
            
            function showOperater(value,row,index){
            	if(row.machineryEquipmentState && row.machineryEquipmentState.connectionState == true){
            		return "<img src='images/todolist-on-la.gif' onclick=\"showExactInfo("
            				+row.id+","+row.deviceNo+","+row.latitude+","+row.longitude+",'"+row.distributionPoints+"');\"></img>";
            	}else{
            		return "<img src='images/todolist-off-la.gif'  onmouseover=\"this.style.cursor='hand'\"  onclick=\"showExactInfo("
    				+row.id+","+row.deviceNo+","+row.latitude+","+row.longitude+",'"+row.distributionPoints+"');\"   ></img>";
            	}
            }
            function showState(value,row,index){
            	if(row.machineryEquipmentState && row.machineryEquipmentState.connectionState == true){
            		return "<span style='color:green;'>在线</span>";
            	}else{
            		return "<span style='color:red;'>离线</span>";
            	}
            }
            
            function showExactInfo(id,deviceNo,latitude,longitude,distributionpoints){
            	var result = "设备编号："+deviceNo+"<br/>纬度："+latitude+"<br/>经度："+longitude+"<br/>发放点："+distributionpoints;
            	//alert(result);
            	//在这里要找到marker,==首先找到map,全局的，然后根据经纬度，来找到该marker,然后调用marker的openInfoWindow方法
            	var point = new BMap.Point(longitude,latitude);
            	var marker = new BMap.Marker(point);
            	
//             	map.addOverlay(marker);
            	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                var opts = { 
                		 enableSendToPhone: false,
                	     width : 250,     // 信息窗口宽度    
                	     height: 105,     // 信息窗口高度    
                	     title : "设备信息"  // 信息窗口标题   
                	    };    
               var infoWindow = new BMap.InfoWindow(result, opts);  // 创建信息窗口对象    
               map.openInfoWindow(infoWindow, point);      // 打开信息窗口    
//             	marker.openInfoWindow(infoWindow,point);
            }
            
            $.extend($.fn.validatebox.defaults.rules, {
        		number : {
        			validator : function(value) {
        				var reg=/^[0-9]*$/;
        				return reg.test(value);
        			},
        			message : '请输入数字'
        		}
        	});
            function selectMachineryEquipmentOneLine(data){
           		 $('#machineryEquipment_datagrid').datagrid("selectRow",0);
           }

         </script> 
</head>

<body style="margin: 0px;padding: 0px;" class="easyui-layout">
	
		<div data-options="region:'north',title:'条件查询',split:true" style="height:80px;text-align:left;padding:10px;" align="center">
			    选择发放点：
<!-- 			    <input id="city" class="easyui-combobox" value="-1"  data-options="editable: false,panelHeight:'auto',valueField:'id',textField:'name',loadFilter:filterDataFun1"/> -->
				 <jsp:include page="../base/areaSearch.jsp" />	
			   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="submitForm();">查询</a>
		</div>   
   		 <div data-options="region:'east',split:true"  title="百度地图" id="map" >
	    </div>     

   	  <div data-options="region:'west',title:'数据列表'" style="width: 670px;" >   
		 <table id="machineryEquipment_datagrid" class="easyui-datagrid" style="width: 560px;" data-options="striped:true,onLoadSuccess:onLoadSuccessFun,rownumbers:true,fit:true,loadMsg:'加载中，请稍后...',pageList:[10,20,4,50],pagination:true,url:'allmachineryequipment',singleSelect:true,pageSize:20,toolbar:'#tb'">
			<thead frozen="true">
				<tr>
					<th field="deviceNo" data-options="fixed:true,width:60,align:'center',sortable:true">设备编号</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th field="opeater" data-options="formatter:showOperater,fixed:true,width:60,align:'center'">定位</th>
					<th field="state" data-options="formatter:showState,fixed:true,width:60,align:'center'">设备状态</th>
					<th field="area.parentArea.parentArea.name" data-options="formatter:countryArea2,fixed:true,width:60,align:'center'">区（县）</th>
					<th field="area.parentArea.name" data-options="formatter:townStreetArea2,fixed:true,width:90,align:'center'">街道（乡镇）</th>
					<th field="area.name" data-options="formatter:communityArea2,fixed:true,width:100,align:'center'">社区</th>

					<th field="distributionPoints" data-options="fixed:true,width:200" >发放点</th>
				</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMachineryEquipment();">添加</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateMachineryEquipment();">修改</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMachineryEquipment();">删除</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="showMachineryEquipment();">查看</a>
			</div>
		</div>
	</div>
	<div id="machineryEquipment_edit_dialog" class="easyui-dialog" fit="true" data-options="closed:true,title:'数据维护窗口',maximizable:true">
		<div id="cc" class="easyui-layout" data-options="fit:true" >   
		    <div id="machineryEquipment_edit_dialog_buttons" data-options="region:'south'" style="height: 50px;padding: 5px;">
		    	<div style="margin-left: 50%;">
		    		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save();">确定</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#machineryEquipment_edit_dialog').dialog('close')">关闭</a>
				</div>
		    </div>   
		    <div data-options="region:'west',split:true" style="width: 400px;">
		   		<form id="machineryEquipment_edit_form" method="post" name="edit_form" data-options="onLoadSuccess:markDeviec">
					<table align="center" cellpadding="3">
						<tbody>
							<tr id="biaozhu">
								<td>标注：</td>
								<td><input id="btnsetpoint" type="button" value="点击标注"
									onclick="clickbtnsetpoint();"></td>
							</tr>
							<tr>
								<td>坐标：</td>
								<td>X： <input id="Longitude" type="text"
									style="width: 103px" name="longitude"> <br> Y： <input
									id="Dimension" type="text" style="width: 103px"
									name="latitude"></td>
							</tr>
							<tr>
								<td>所在区县：</td>
								<td><input id="countryAdd" class="easyui-combobox" value="-1"  data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun"/>
			    
							</td>
							</tr>
							<tr>
								<td>所在街道：</td>
								<td><input id="townshipStreetAdd" class="easyui-combobox" value="-1"  data-options="panelHeight:250,valueField:'id',textField:'name',loadFilter:filterDataFun"/>
							</tr>
							<tr>
								<td>所在社区：</td>
								<td><input id="communityAdd" class="easyui-combobox" value="-1" name="area.id" data-options="panelHeight:200,valueField:'id',textField:'name',loadFilter:filterDataFun"/>
								</td>
							</tr>
							<tr>
								<td>发放点：</td>
								<td><input id="DistributionPoints" style="width: 250px;"
									class="easyui-validatebox validatebox-text" type="text"
							
									maxlength="240" required="true" 
									name="distributionPoints"></td>
							</tr>
							<tr>
								<td>定位：</td>
								<td><input type="button" value="定位"
									onclick="locationDis();"></td>
							</tr>
							<tr>
								<td>设备编号：</td>
								<td><input id="TheAlias"
									class="easyui-validatebox validatebox-text" type="text" data-options="validType:'number'"
									maxlength="50" name="deviceNo"></td>
							</tr>
							<tr>
								<td>上传编号：</td>
								<td><input id="sendNum"
									class="easyui-validatebox validatebox-text" type="text" 
									maxlength="7" name="sendNum"></td>
							</tr>
							<tr>
								<td>设备别名：</td>
								<td><input id="TheAlias"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="50" name="alias"></td>
							</tr>
							<tr>
								<td>设备类型：</td>
								<td><input id="TerminalType"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="30" required="true" name="terminalType"></td>
							</tr>
							<tr>
								<td>内置手机号码：</td>
								<td><input id="BuiltinPhone"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="11" required="true" name="builtinPhone"></td>
							</tr>
							<tr>
								<td>管理员密码：</td>
								<td><input id="TerminalPass"  style="width: 110px;"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="25" name="adminPassword"></td>
							</tr>
							<tr>
								<td>货道数量：</td>
								<td>
									<select id="aislesId" class="easyui-combobox" name="aislesNum" data-options=" editable: false,value:'请选择',panelHeight:'auto',onSelect:function(rec){
											if(rec.value==3){
												$('#contraceptive4').combobox('disable');
											}else{
												$('#contraceptive4').combobox('enable');
											}
									
									}">
										<option value="4">四货道</option>
										<option value="3" >三货道</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>药具一：</td>
								<td>
									<input id="contraceptive1" class="easyui-combobox"  name="aisles[0].contraceptive.id"  data-options=" editable: false,panelHeight:'auto',valueField:'id',textField:'name',url:'getContraceptive',load:function(){}"/>
								</td>
							</tr>
							<tr>
								<td width="80xp">药具二：</td>
								<td>
									<input id="contraceptive2" class="easyui-combobox"  name="aisles[1].contraceptive.id" data-options=" editable: false,panelHeight:'auto',valueField:'id',textField:'name',url:'getContraceptive'"/>
								</td>
							</tr>
							<tr>
								<td width="80xp">药具三：</td>
								<td>
									<input id="contraceptive3" class="easyui-combobox"  name="aisles[2].contraceptive.id"  data-options=" editable: false,panelHeight:'auto',valueField:'id',textField:'name',url:'getContraceptive'"/>
								</td>
							</tr>
							<tr>
								<td width="80xp">药具四：</td>
								<td>
									<input id="contraceptive4" class="easyui-combobox" name="aisles[3].contraceptive.id" data-options=" editable: false,panelHeight:'auto',valueField:'id',textField:'name',url:'getContraceptive'"/>
								</td>
							</tr>
							<tr>
								<td width="80px">备注：</td>
								<td><textarea id="Remark"
										class="easyui-validatebox validatebox-text" rows="3"
										validtype="length[0,300]" cols="25" name="remark" ></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form> 
		    </div>   
	    	<div data-options="region:'center',title:'百度地图'"  id="addmap"></div>   
		</div>  
	</div>
</body>

</html>