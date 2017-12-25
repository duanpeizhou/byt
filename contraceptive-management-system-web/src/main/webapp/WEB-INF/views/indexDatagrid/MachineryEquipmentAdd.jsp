<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>发放机设备管理</title>
<link href="js/jquery-easyui-1.3.4/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="js/jquery-easyui-1.3.4/themes/icon.css" rel="stylesheet"
	type="text/css" />
<script src="js/jquery-easyui-1.3.4/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=39d01498d77eb3144433767a17e3f796"></script>
 <script src="js/baidumap.js" type="text/javascript"></script>
<script type="text/javascript">
$.extend($.fn.combobox.defaults, {panelHeight:true});
	$(function() {
		Map.newMap("map");
		$('#country').combobox({
			value : "全部",
			url : 'countryMenu?id=2',
			onSelect : function(rec) {

				$('#townshipStreet').combobox({

					url : "townshipStreetMenu?id=" + rec.id,
					value : "全部",

				});
			}
		});

		$('#townshipStreet').combobox({
			onSelect : function(rec) {
				$('#community').combobox({
					url : 'communityMenu?id=' + rec.id,
					value : "全部"

				});
			}
		});

	});

	function submitForm() {
		alert(3);
		$('#edit_form').form({
			url : "machineryEquipmentAdd",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$.messager.show({
					title : '提示',
					msg : data
				});
			}
		});
	}
</script>

<style type="text/css">
* {
	font-family: Tahoma, Verdana, 微软雅黑, 新宋体;
	font-size: 12px;
}

.panel {
	font-size: 12px;
}

element.style {
	cursor: default;
}

.window {
	font-size: 12px;
}
</style>
</head>
<body style="margin: 0px; padding: 0px;" class="easyui-layout">

	<div data-options="region:'center',title:'数据维护窗口'">
		<div class="panel layout-panel layout-panel-west"
			style="left: 0px; top: 0px; width: 400px;">
			<div
				class="layout-body panel-body panel-body-noheader panel-body-noborder"
				style="width: 378px; padding: 10px; background: none repeat scroll 0% 0% rgb(255, 255, 255); border: 1px solid rgb(204, 204, 204);"
				border="false" region="west" title="">

				<form id="edit_form" method="post" name="edit_form">
					<div title="隐藏参数">
						<input id="TerminalID" type="hidden" value="" name="TerminalID">
					</div>
					<table align="center" cellpadding="3">
						<tbody>
							<tr>
								<td>标注：</td>
								<td><input id="btnsetpoint" type="button" value="点击标注"
									onclick="clickbtnsetpoint();"></td>
							</tr>
							<tr>
								<td>坐标：</td>
								<td>X： <input id="Longitude" type="text"
									style="width: 103px" name="machineryEquipment.longitude">
									<br> Y： <input id="Dimension" type="text"
									style="width: 103px" name="machineryEquipment.latitude"></td>
							</tr>
							<tr>
								<td>设备别名：</td>
								<td><input id="TheAlias"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="50" name="machineryEquipment.alias"></td>
							</tr>
							<tr>
								<td>设备类型：</td>
								<td><input id="TerminalType"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="30" required="true"
									name="machineryEquipment.terminalType"></td>
							</tr>
							<tr>
								<td>内置手机号码：</td>
								<td><input id="BuiltinPhone"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="11" required="true"
									name="machineryEquipment.builtinPhone"></td>
							</tr>
							<tr>
								<td>管理员密码：</td>
								<td><input id="TerminalPass" style="width: 110px;"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="25" name="machineryEquipment.adminPassword"></td>
							</tr>
							<tr>
								<td>所在区县：</td>
								<td><input id="country" class="easyui-combobox"
									value="请选择所在的县" name="id"
									data-options="valueField:'id',textField:'name'" /></td>
							</tr>
							<tr>
								<td>所在街道：</td>
								<td><input id="townshipStreet" class="easyui-combobox"
									value="请选择所在的街道" name="id"
									data-options="valueField:'id',textField:'name'" />
							</tr>
							<tr>
								<td>所在社区：</td>
								<td><input id="community" class="easyui-combobox"
									value="请选择所在的社区" name="id"
									data-options="valueField:'id',textField:'name'" /></td>
							</tr>
							<tr>
								<td>发放点：</td>
								<td><input id="DistributionPoints"
									class="easyui-validatebox validatebox-text" type="text"
									maxlength="300" required="true" style="width: 200px;"
									name="machineryEquipment.distributionPoints"></td>
							</tr>
							<tr>
								<td>地图地位：</td>
								<td><input id="btnMapBZ" type="button" value="定位"
									onclick="BtnGetLocalSearch();"></td>
							</tr>
							<tr>
								<td>货道数量：</td>
								<td><select id="CargoRoadNum" style="width: 120px;"
									name="aisles.id">
										<option value="3">三货道</option>
										<option value="4">四货道</option>
								</select></td>
							</tr>
							<tr>
								<td>药具一：</td>
								<td><select id="ContraceptivesID1" style="width: 120px;"
									name="ContraceptivesID1">
										<option value="0">请选择</option>
										<option value="1">避孕套（大）</option>
										<option value="2">避孕套（中）</option>
										<option value="3">避孕栓</option>
										<option value="4">避孕栓</option>
								</select></td>
							</tr>
							<tr>
								<td width="80xp">药具二：</td>
								<td><select id="ContraceptivesID2" style="width: 120px;"
									name="ContraceptivesID2">
										<option value="0">请选择</option>
										<option value="1">避孕套（大）</option>
										<option value="2">避孕套（中）</option>
										<option value="3">避孕栓</option>
										<option value="4">避孕栓</option>
								</select></td>
							</tr>
							<tr>
								<td width="80xp">药具三：</td>
								<td><select id="ContraceptivesID3" style="width: 120px;"
									name="ContraceptivesID3">
										<option value="0">请选择</option>
										<option value="1">避孕套（大）</option>
										<option value="2">避孕套（中）</option>
										<option value="3">避孕栓</option>
										<option value="4">避孕栓</option>
								</select></td>
							</tr>
							<tr>
								<td width="80xp">药具四：</td>
								<td><select id="ContraceptivesID4" style="width: 120px;"
									name="ContraceptivesID4">
										<option value="0">请选择</option>
										<option value="1">避孕套（大）</option>
										<option value="2">避孕套（中）</option>
										<option value="3">避孕栓</option>
										<option value="4">避孕栓</option>
								</select></td>
							</tr>
							<tr>
								<td width="80xp">备注：</td>
								<td><textarea id="Remark"
										class="easyui-validatebox validatebox-text" rows="3"
										validtype="length[0,300]" cols="40"
										name="machineryEquipment.remark"></textarea></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>


		<div
			class="panel layout-panel layout-panel-center layout-split-center"
			style="float: right; left: 400px; top: 0px; width: 1324px; cursor: default;">
			<div class="panel-header" style="width: 1312px;">
				<div class="panel-title">百度地图</div>
				<div class="panel-tool">
					<div class=""></div>
				</div>
			</div>
			<div class="layout-body panel-body" style="padding: 0px;" title=""
				split="true" region="center">
				<div id="BaiduMapEdit" align="center"
					style="width: 100%; height: 100%; overflow: hidden; margin: 0px; position: relative; z-index: 0; background-color: rgb(243, 241, 236); color: rgb(0, 0, 0); text-align: left;">
				</div>
			</div>

		</div>
	</div>

	<div data-options="region:'south',split:true" style="height: 50px;">
		<div
			class="layout-body panel-body panel-body-noheader panel-body-noborder"
			style="text-align: center; height: 30px; line-height: 30px; display: block;"
			border="false" region="south" title="">

			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save'" onclick="submitForm()">确定</a> <a
				href="machineryEquipmentui" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'">关闭</a>

		</div>
	</div>
</body>
</html>