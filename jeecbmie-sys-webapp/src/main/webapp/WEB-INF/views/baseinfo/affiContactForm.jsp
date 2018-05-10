<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBaseInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBankInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiContactInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childContactToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendContact()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitContact()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTBSub" data-options="onClickRow: onClickRowContact"></table>
<%
	WoodAffiBaseInfo affiBase = (WoodAffiBaseInfo)request.getAttribute("affiBaseInfo");
	List<WoodAffiContactInfo> affiContactList = affiBase.getAffiContactInfo();
	ObjectMapper objectMapper2 = new ObjectMapper();
	String abcJson = objectMapper2.writeValueAsString(affiContactList);
	request.setAttribute("abcJson", abcJson);
%>
<script type="text/javascript">
var childTBSub;
$(function(){
	childTBSub=$('#childTBSub').datagrid({
	data : JSON.parse('${abcJson}'),
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
    columns:[[    
		{field:'id',title:'id',hidden:true},
		{field:'name',title:'姓名',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'addr',title:'地址',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'position',title:'职位',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'tel',title:'电话',width:20,editor:{type:'validatebox',options:{required:false,validType:'telOrMobile'}}},
		{field:'fax',title:'传真',width:20,editor:{type:'validatebox',options:{required:false,validType:'fax'}}},
		{field:'email',title:'email',width:20,editor:{type:'validatebox',options:{required:false,validType:'email'}}},
		{field:'mobil',title:'手机',width:20,editor:{type:'validatebox',options:{required:false,validType:'telOrMobile'}}},
		{field:'breed',title:'主营品种',width:20,editor:{type:'validatebox',options:{required:false}}},
		]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childContactToolbar'
	});
});

var editIndex = undefined;
function endEditingContact(){
	if (editIndex == undefined){return true}
	if ($('#childTBSub').datagrid('validateRow', editIndex)){
		$('#childTBSub').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowContact(index){
	if (editIndex != index){
		if (endEditingContact()){
			$('#childTBSub').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTBSub').datagrid('selectRow', editIndex);
		}
	}
}
function appendContact(){
	if (endEditingContact()){
		$('#childTBSub').datagrid('appendRow',{name:''});
		editIndex = $('#childTBSub').datagrid('getRows').length-1;
		$('#childTBSub').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeitContact(){
	if (editIndex == undefined){return}
	$('#childTBSub').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function acceptContact(){
	if (endEditingContact()){
		var rows = $('#childTBSub').datagrid('getRows');
		$('#childTBSub').datagrid('acceptChanges');
		$('#affiContactJson').val(JSON.stringify(rows));
	}
}
function rejectContact(){
	$('#childTBSub').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChangesContact(){
	var rows = $('#childTBSub').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>