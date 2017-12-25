<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${basePath}"/>
    <title>用户登陆</title>
    <link href="images/login/css.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="js/jquery-easyui-1.3.5/jquery.min.js"></script>
    	<script type="text/javascript" src="js/login.js">
	</script>
	
</head>
<body>
	
    <form id="login_form" action="login" method="post">
    <div>
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="100">
                    &nbsp;
                </td>
            </tr>
        </table>
        <table width="0" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center">
                    <img src="images/login/image_1.jpg" width="928" height="114" />
                </td>
            </tr>
        </table>
        <table width="928" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="40">
                    &nbsp;
                </td>
                <td align="center" valign="top">
                   <table border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td rowspan="2" align="center" valign="top">
                                <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                                    width="597" height="280">
                                    <param name="movie" value="images/login/1.swf" />
                                    <param name="quality" value="high" />
                                    <param name="wmode" value="transparent" />
                                    <embed src="images/login/1.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="597" height="280" wmode="transparent"></embed>
                                </object>
                            </td>
                            <td align="center" background="images/login/bj_2.jpg">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="top">
                                <table width="0" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <img src="images/login/image_3.jpg" width="295" height="30" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="230" align="center" valign="top" background="images/login/bj_2.jpg">
                                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td height="40">
                                                        &nbsp;
                                                    </td>
                                                </tr>
                                            </table>
                                            <table width="0" border="0" cellspacing="0" cellpadding="0">
                                            	<tr>
                                                	<td></td>
                                                    <td align="left" colspan="2" style="text-align: left;">
                                                    	<span style="color: red">${error }</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="55" height="33" class="A1">
                                                  	 用户名：
                                                    </td>
                                                    <td align="left" colspan="2">
                                                       <input type="text" name="username"  style="width:125px;"  value="${username}"/>
                                                    </td>
                                                </tr>
                                                 <tr>
                                                	<td></td>
                                                    <td align="left" colspan="2" style="text-align: left;">
                                                    	<span id="username_msg" style="color: red"></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td  width="55"  height="33" class="A1">
                                                                                                                                             密 码：
                                                    </td>
                                                    <td   align="left" colspan="2">
                                                     <input type="password" name="password" style="width:125px;" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                	<td></td>
                                                    <td align="left" colspan="2" style="text-align: left;">
                                                    	<span id="password_msg" style="color: red"></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td height="66" colspan="3" align="center">
                                                        <table width="0" border="0" cellspacing="0" cellpadding="0" align="center">
                                                            <tr>
                                                                <td align="center" valign="bottom">
                                                                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="IbtnEnter" value="" type="submit"src="images/login/image_4.jpg"  style="border:0px;border-radius: 0px;width: 110px;height: 36px;background-image: url('images/login/image_4.jpg');"/>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td background="images/login/image_2.jpg" width="295" height="17">
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="80" align="center">
                </td>
            </tr>
        </table>
        <table width="0" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center" class="A2">
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
