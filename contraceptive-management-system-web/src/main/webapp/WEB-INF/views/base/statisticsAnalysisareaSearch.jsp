<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:choose>
	<c:when test="${sessionScope.session_manager.superManager }">
		<input id="country" class="easyui-combobox" value="全部" style="width: 100px;" name="id" data-options="panelHeight:300,valueField:'id',loadFilter:filterDataFun,textField:'name',url:'countryMenuOptions?id=<spring:message code="City.no"/>',onSelect:onSelectcountry"/>
		<input id="townshipStreet" class="easyui-combobox" value="全部" name="id" style="width: 100px;" data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun,onSelect:onSelecttTownshipStreet"/>
		<input id="community" class="easyui-combobox" value="全部" name="id"  style="width: 100px;" data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun"/>
	</c:when>
	<c:when test="${!empty sessionScope.session_manager.townshipStreet}">
		<select id="country" class="easyui-combobox" value="${sessionScope.session_manager.townshipStreet.parentArea.id}" style="width: 100px;" name="id" data-options="">
			<option value="${sessionScope.session_manager.townshipStreet.parentArea.id}">${sessionScope.session_manager.townshipStreet.parentArea.name}</option>
		</select>
		<select id="townshipStreet" class="easyui-combobox"  style="width: 100px;" value="${sessionScope.session_manager.townshipStreet.id}" name="id" data-options="valueField:'id',textField:'name',onSelect:onSelecttTownshipStreet">
			<option value="${sessionScope.session_manager.townshipStreet.id}">${sessionScope.session_manager.townshipStreet.name}</option>
		</select>
		<input id="community" class="easyui-combobox" value="全部"  style="width: 100px;" name="id" data-options="panelHeight:300,valueField:'id',textField:'name',url:'communityMenu?id=${sessionScope.session_manager.townshipStreet.id}',loadFilter:filterDataFun"/>
	</c:when>
	<c:when test="${!empty sessionScope.session_manager.county}">
		<select id="country" class="easyui-combobox" value="${sessionScope.session_manager.county.id}" style="width: 100px;" name="id" data-options="">
			<option value="${sessionScope.session_manager.county.id}">${sessionScope.session_manager.county.name}</option>
		</select>
		<input id="townshipStreet" class="easyui-combobox"  style="width: 100px;" value="全部" name="id" data-options="onSelect:'onSelecttTownshipStreet()',panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun,onSelect:onSelecttTownshipStreet,url:'townshipStreetMenu?id=${sessionScope.session_manager.county.id}'"/>
		<input id="community" class="easyui-combobox"  style="width: 100px;" value="全部" name="id" data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun"/>
	</c:when>
	<c:otherwise>
		<input id="country" class="easyui-combobox" value="全部" style="width: 100px;" name="id" data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun,onSelect:onSelectcountry"/>
		<input id="townshipStreet" class="easyui-combobox"  style="width: 100px;" value="全部" name="id" data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun,onSelect:onSelecttTownshipStreet"/>
		<input id="community" class="easyui-combobox" value="全部" name="id" style="width: 100px;" data-options="panelHeight:300,valueField:'id',textField:'name',loadFilter:filterDataFun"/>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
function onSelectcountry(rec){
	if(rec.id!=-1){
 		$('#townshipStreet').combobox({
 			 url:'townshipStreetMenu?id='+rec.id,
			 value:"全部"
 		});
 		$('#community').combobox('loadData',new Array());
 		$('#community').combobox('clear');
		}else {
			$('#townshipStreet').combobox('loadData',new Array());
			$('#townshipStreet').combobox('clear');
			$('#community').combobox('loadData',new Array());
 		$('#community').combobox('clear');
		}
}
function onSelecttTownshipStreet(rec){
	if(rec.id!=-1){
 		$('#community').combobox({
 			 url:'communityMenu?id='+rec.id,
		     value:"全部"
 			
 		});
		}else {
			$('#community').combobox('loadData',new Array());
 		$('#community').combobox('clear');
		}
}
/**
 * 初始化下拉选
 * @param data
 * @returns {Array}
 */
function filterDataFun(data){
	if(data)
		data.unshift({id:-1,name:"全部"});
	else
		data = new Array();
	return data;
}
</script>
