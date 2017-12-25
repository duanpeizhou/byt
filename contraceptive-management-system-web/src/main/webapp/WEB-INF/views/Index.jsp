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
<script src="js/jquery-easyui-1.3.5/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="FusionCharts/FusionCharts.js" type="text/javascript"></script>
<script src="js/commonJS1.js" type="text/javascript"></script>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<style type="text/css">

/* .diva:hover
				{
					background-color:#ddd;
					font-size: 18px;
					border: dotted 1px black;
				} */
* {
	font-size: 13px;
	font-family: Tahoma, Verdana, 微软雅黑, 新宋体;
}

#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 15px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
/* .diva {
				padding-top: 10px;
				padding-left: 5px;
				text-align: left;
				} */
.diva {
	margin: 2px 0px;
	padding-left: 10px;
	padding-top: 2px;
}

.diva :selected {
	border: 1px solid #99BBE8;
	background: #E0ECFF;
	cursor: default;
}

.diva a {
	text-decoration: none;
	padding: 3px;
	color: black;
	line-height: 20px;
}

.diva img {
	vertical-align: bottom;
	margin-right: 3px;
}

.diva.selected {
	border: 1px solid #99BBE8;
	background: #E0ECFF;
	cursor: default;
}

.diva.selected a {
	color: #416AA3;
	font-weight: bold;
	line-height: 20px;
}

.diva:hover {
	border: 1px dashed #99BBE8;
	background: #E0ECFF;
	cursor: pointer;
}

.diva a:hover {
	color: #416AA3;
}
</style>
<script type="text/javascript">
	$.extend($.fn.combobox.defaults, {
		panelHeight : true
	});
	//写cookies
	function setCookie(name, value) {
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires="
				+ exp.toGMTString();
	}
	//读取cookies
	function getCookie(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

		if (arr = document.cookie.match(reg))

			return unescape(arr[2]);
		else
			return null;
	}

	//删除cookies
	function delCookie(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		if (cval != null)
			document.cookie = name + "=" + cval + ";expires="
					+ exp.toGMTString();
	}

	function formatDate(date, fmt) { //author: meizz 
		var o = {
			"M+" : date.getMonth() + 1, //月份 
			"d+" : date.getDate(), //日 
			"h+" : date.getHours(), //小时 
			"m+" : date.getMinutes(), //分 
			"s+" : date.getSeconds(), //秒 
			"q+" : Math.floor((date.getMonth() + 3) / 3), //季度 
			"S" : date.getMilliseconds()
		//毫秒 
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}

	$(function() {
		// 		tabCloseEven();
		var time = formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
		if (getCookie("name") == undefined) {
			setCookie("name", time);
		}
		$('#time').text(getCookie("name"));

	});
	function updatePassword() {
		$('#passwordDialog').dialog('open');
	}
	function savePassword() {
		$('#fm').form('submit', {
			url : "updatePassword",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (!result) {
					$.messager.alert('提示', '修改密码错误!');
				} else {
					$('#passwordDialog').dialog('close'); // close the dialog
					$.messager.alert('提示', '修改密码成功!');
				}
			}
		});
	}
	$.extend($.fn.validatebox.defaults.rules, {
		oldPassword : {
			validator : function(value) {
				var old = '${sessionScope.session_manager.password}';
				return old == value;
			},
			message : '原密码错误！'
		},
		confirmPassword : {
			validator : function(value) {
				var new1 = $('#new1').val();
				return new1 == value;
			},
			message : '密码不一致！'
		}
	});
	/* $(function(){
	   	
	   	Pie3D.swf
	   	Column3D
	   	
	   	$.post('chartData',function(data){
	   		var arr=data.split(";");
	   		columnChart(arr);
	   		stockPieChart(arr);
	   		pieChart(arr);
	   	}); 
	 }); */
	function con() {
		var f = window.confirm("您确认想要退出吗？");
		if (f) {
			delCookie("name");
		}
		return f;
	}

	function tabClose() {
		/*双击关闭TAB选项卡*/
		/* 	     $(".tabs-inner").unbind("dblclick");
		 $(".tabs-inner").unbind("contextmenu"); */
		$(".tabs-inner").dblclick(function() {
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex', tab);
			if (index != 0) {
				$('#tabs').tabs('close', index);
			}
			return false;
		});

		$(".tabs-inner").bind('contextmenu', function(e) {
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY,
			});
			var subtitle = $(this).children("span").text();
			$('#mm').data("currtab", subtitle);
			return false;
		});
	}
	//绑定右键菜单事件
	function tabCloseEven() {
		//关闭当前
		$('#mm-tabclose').click(function(e) {
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex', tab);
			var currtab_title = $('#mm').data("currtab");
			if (index != 0) {
				$('#tabs').tabs('close', index);
			}
			return false;
		})
		//全部关闭
		$('#mm-tabcloseall').click(function(e) {
			$('.tabs-inner').each(function(i, n) {
				if (i != 0) {
					$('#tabs').tabs('close', 1);
				}
			});
		});
		//关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function() {
			var currtab_title = $('#mm').data("currtab");
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex', tab);
			$('.tabs-inner').each(function(i, n) {
				if (i > index && i != 0) {
					$('#tabs').tabs('close', index + 1);
				}
			});
			$('.tabs-inner').each(function(i, n) {
				if (i < index && i != 0) {
					$('#tabs').tabs('close', 1);
				}
			});
			$("#tabs").tabs("select", 1);
		});

		//关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function() {
			var currtab_title = $('#mm').data("currtab");
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex', tab);
			$('.tabs-inner').each(function(i, n) {
				if (i > index && i != 0) {
					$('#tabs').tabs('close', index + 1);
				}
			});
			return false;
		});
		//关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function() {
			var currtab_title = $('#mm').data("currtab");
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex', tab);
			$('.tabs-inner').each(function(i, n) {
				if (i < index && i != 0) {
					$('#tabs').tabs('close', 1);
				}
			});
			$("#tabs").tabs("select", 1);
			return false;
		});
	}

	/*  $(function(){
		$('.tabs li.tabs-selected a.tabs-inner:hover').click( function () { alert("Hello World!"); });
	 });*/
</script>
</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>

	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 107px; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="qy_toplrk">
			<tr>
				<td width="401" height="73" align="left" valign="middle"
					class="qy_top2bg"><img src="images/index/qyxx_r3_c2.jpg"
					width="401" height="73" /></td>
				<td align="right" valign="middle" class="qy_top2bg">
					<table width="350" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><a href="index" target="_parent"> <img
									src="images/index/qyxx_r3_c21.jpg" width="72" height="73"
									border="0" alt="" /></a></td>
							<td><a href="javascript:updatePassword();" id="editpass">
									<img src="images/index/qyxx_r3_c221.jpg" width="67" height="73"
									border="0" alt="" />
							</a></td>
							<td><a href="logout" target="_parent"
								onclick="return confirm('你确认要重新登陆吗?');"> <img
									src="images/index/qyxx_r3_c23.jpg" width="65" height="73"
									border="0" alt="你确认要重新登陆吗" /></a></td>
							<td><a href="logout" id='logout' target="_parent"
								onclick="return con();"> <img
									src="images/index/qyxx_r3_c24.jpg" width="67" height="73"
									border="0" alt="你确认要退出吗" /></a></td>
							<td><a href="javascript:void(0)"
								onclick="javascript:alert('请使用  Ctrl+D 组合键');"> <img
									src="images/index/qyxx_r3_c22.jpg" width="67" height="73"
									border="0" alt="" /></a></td>
							<td><a
								href="http://wpa.qq.com/msgrd?v=3&amp;uin=1877541665&amp;site=qq&amp;menu=yes"
								target="_blank"> <img border="0" src="images/QQonline01.jpg"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td height="27" colspan="2" class="qy_topmenubor">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						bgcolor="#F0F5F9">
						<tr>
							<td height="27" align="left" valign="middle"
								class="qy_menuleftbg" style="width: 880px">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="26" align="right" valign="middle"><img
											src="images/index/340.gif" width="16" height="16" /></td>
										<td class="qy_topk2px" colspan="2">&nbsp;用户名：<span
											class="qy_lsfont">${sessionScope.session_manager.username}</span>
											&nbsp;&nbsp;姓名：<span class="qy_lsfont">${sessionScope.session_manager.name}</span>&nbsp;&nbsp;登陆时间：&nbsp;&nbsp;<span
											id='time'></span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div id="passwordDialog" class="easyui-dialog"
			buttons="#dialogButtons" data-options="closed:true,title:'修改密码'"
			style="width: 380px; height: 280px; padding: 30px 20px;">
			<form id="fm" method="post" novalidate>
				<input type="hidden" name="id"
					value="${sessionScope.session_manager.id}" />
				<%-- <input type="text"  value="${sessionScope.session_manager.password}"/> --%>
				<div class="fitem">
					<label>原密码：</label> <input id="old" class="easyui-validatebox"
						required="true" data-options="validType:'oldPassword'" />
				</div>
				<div class="fitem">
					<label>新密码：</label> <input id="new1" class="easyui-validatebox"
						required="true" />
				</div>
				<div class="fitem">
					<label>确认：</label> <input id="new2" name="newPassword"
						class="easyui-validatebox" required="true"
						data-options="validType:'confirmPassword'" />
				</div>
			</form>
		</div>
		<div id="dialogButtons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="savePassword()">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#passwordDialog').dialog('close')">取消</a>
		</div>
	</div>
	<div region="west" hide="false" split="true" title="导航菜单"
		style="width: 180px;" id="west"></div>
	<script type="text/javascript">
		/*
		 * 状态的显示
		 */
		function stateFormatter(value, row, index) {
			if (value == "OpenDoor") {
				return "<font color='red'>开门</font>";
			} else if (value == "CloseDoor") {
				return "<font color='green'>关门</font>";
			} else if (value == "Online") {
				return "<font color='green'>在线</font>";
			} else if (value == "OffineLine") {
				return "<font color='red'>离线</font>";
			} else if (value == "Overtemperature") {
				return "<font color='red'>超温</font>";
			} else if (value == "TemperatureRecovery") {
				return "<font color='red'>温度恢复</font>";
			} else if (value == "OutStock") {
				return "<font color='red'>缺货</font>";
			} else if (value == "Replenishment") {
				return "<font color='green'>补货</font>";
			} else if (value == "AisleFailure") {
				return "<font color='red'>货道故障</font>";
			} else if (value == "AisleFailureRecovery") {
				return "<font color='green'>货道故障恢复</font>";
			} else if (value == "CardReaderFailure") {
				return "<font color='red'>读卡器故障</font>";
			} else if (value == "CardReaderFailureRecovery") {
				return "<font color='green'>读卡器故障恢复</font>";
			}
		}

		$(function() {
			$
					.post(
							'menus',
							function(result) {
								var rootMenu = result;
								var west = $(".easyui-layout").layout('panel',
										'west');
								west.panel('setTitle', rootMenu.name);
								var accordion = $(
										"<div id='accordion' class='easyui-accordion' fit='true' border='false' title='导航内容'></div> ")
										.appendTo($("#west"));

								var menus = rootMenu.childMenu;
								for ( var i = 0; i < menus.length; i++) {
									var accordionMenu = $(
											"<div data-options=\"iconCls:'icon-sys'\"></div>")
											.attr('title', menus[i].name)
											.appendTo(accordion);
									if (menus[i].childMenu) {
										for ( var j = 0; j < menus[i].childMenu.length; j++) {
											var menu = menus[i].childMenu[j];
											var div = $(
													"<div class='diva' style='width: 130px;height: 25px;margin: 4px;' onclick='clickMenuDiv(this)'></div>")
													.appendTo(accordionMenu);
											var a = $("<a href='javascript:void(0);' class='myA'></a>");

											a
													.attr("action", menu.url)
													.html(menu.name)
													.appendTo(div)
													.click(
															function() {
																var title = $(
																		this)
																		.html();
																var action = $(
																		this)
																		.attr(
																				'action');
																addTabs(title,
																		action);
																tabClose();
																// 																closeTab();

															});

										}
									}
								}

								$(".myA")
										.prepend(
												"<img src='images/menu.png' style='border:0px'/>");
								$.parser.parse(west);
								tabClose();
								// 								closeTab();
								tabCloseEven();
							});

		});
		function closeTab() {
			$(".tabs-inner").dblclick(function() {
				var tab = $('#tabs').tabs('getSelected');
				var index = $('#tabs').tabs('getTabIndex', tab);
				if (index != 0) {
					$('#tabs').tabs('close', index);
				}
			});
		}
		function clickMenuDiv(diva) {
			$(".selected").removeClass("selected");
			$(diva).addClass("selected");
		}

		/*
			定时刷新数据
		 */
		function refreshDatagrid() {
			$('#dg').datagrid({
				reload : "machineryEquipmentStateChangeRecords"
			});
		}

		function addTabs(title, url) {
			if ($('#tabs').tabs('exists', title)) {
				$('#tabs').tabs('select', title);
			} else {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'
						+ url + '" style="width:100%;height:100%;"></iframe>';
				$('#tabs').tabs('add', {
					title : title,
					content : content,
					closable : true,
					fit : true,
				});
			}
			//closeTab
		}
	</script>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"
			style="margin: 0px;">
			<div title="缺货实时监控" closable="false">
				<iframe scrolling="auto" frameborder="0"
					src="stockoutReplenishMonitoringUI"
					style="width: 100%; height: 100%;"></iframe>
			</div>
			<script type="text/javascript">
				function formatterDeviNo(value, row, index) {
					if (row.machineryEquipment.deviceNo) {
						return "<font color='red'>"
								+ row.machineryEquipment.deviceNo + "</font>";
					}
				}
				function formatterType(value, row, index) {
					if (row.machineryEquipment.terminalType) {
						return row.machineryEquipment.terminalType;
					}
				}

				function formatterDistributionPoints(value, row, index) {
					if (row.machineryEquipment.distributionPoints) {
						return row.machineryEquipment.distributionPoints;
					}
				}
			</script>
		</div>
	</div>
	<!-- <div id="mm" class="easyui-menu" title="多标签右键菜单" style="width: 150px; display: none">
        <div id="mm-tabupdate">
            刷新</div>
        <div class="menu-sep">
        </div>
        <div id="mm-tabclose">
            关闭</div>
        <div id="mm-tabcloseall">
            全部关闭</div>
        <div id="mm-tabcloseother">
            除此之外全部关闭</div>
        <div class="menu-sep">
        </div>
        <div id="mm-tabcloseright">
            当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft">
            当前页左侧全部关闭</div>
        <div class="menu-sep">
        </div>
        <div id="mm-exit">
            退出</div>
    </div> -->
	<!-- <div id="win_changepwd" class="easyui-window" closed="true" title="修改密码" icon="icon-save"
        style="width: 350px; height: 200px; padding: 5px; background: #fafafa;">
        <div class="easyui-layout" fit="true" id="password" style="display: none">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <form id="win_changepwd_form" name="win_changepwd_form" method="post">
                <table cellpadding="3" align="center">
                    <tr>
                        <td>
                            旧密码：
                        </td>
                        <td>
                            <input id="OldPassword" name="OldPassword" type="password" class="easyui-validatebox"
                                required="true" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            新密码：
                        </td>
                        <td>
                            <input id="NewPassword" name="NewPassword" type="password" class="easyui-validatebox"
                                required="true" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            确认密码：
                        </td>
                        <td>
                            <input id="NewPasswordRe" name="NewPasswordRe" type="password" class="easyui-validatebox"
                                required="true" validtype="equalTo['#NewPassword']" />
                        </td>
                    </tr>
                </table>
                </form>
            </div>
            <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">
                    关闭</a>
            </div>
        </div>
    </div> 
    <form id="form1" runat="server">
    </form>-->
	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>
</body>
</html>
