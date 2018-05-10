<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBaseInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiCustomerInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar2" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendCustomer()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitCustomer()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTBCus" data-options="onClickRow: onClickRow"></table>
<%
	WoodAffiBaseInfo affiBaseInfoCus = (WoodAffiBaseInfo)request.getAttribute("affiBaseInfo");
	List<WoodAffiCustomerInfo> affiCustomerList = affiBaseInfoCus.getAffiCustomerInfo();
	ObjectMapper objectMapperCus = new ObjectMapper();
	String customerJson = objectMapperCus.writeValueAsString(affiCustomerList);
	request.setAttribute("customerJson", customerJson);
%>
<script type="text/javascript">
var childTBCus;
$(function(){
	childTBCus=$('#childTBCus').datagrid({
	data : JSON.parse('${customerJson}'),
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
		{field:'creditLine',title:'授信业务额度',width:20,editor:{type:'validatebox',options:{required:false}}},
	    {field:'checkStartTime',title:'评审有效开始日期',width:25, editor:{type:'datebox',options:{required:false}}},
        {field:'checkEndTime',title:'评审有效结束日期',width:25, editor:{type:'datebox',options:{required:false}}},
		{field:'customerAndConditions',title:'客户简介及生产经营情况等',width:20,editor:{type:'validatebox',options:{required:false}}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar2'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTBCus').datagrid('validateRow', editIndex)){
		$('#childTBCus').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTBCus').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTBCus').datagrid('selectRow', editIndex);
		}
	}
}
function appendCustomer(){
	if (endEditing()){
		$('#childTBCus').datagrid('appendRow',{creditLine:''});
		editIndex = $('#childTBCus').datagrid('getRows').length-1;
		$('#childTBCus').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeitCustomer(){
	if (editIndex == undefined){return}
	$('#childTBCus').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function acceptCustomer(){
	if (endEditing()){
		var rows = $('#childTBCus').datagrid('getRows');
		$('#affiCustomerJson').val(JSON.stringify(rows));
		$('#childTBCus').datagrid('acceptChanges');
	}
}
function rejectCustomer(){
	$('#childTBCus').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChangesCustomer(){
	var rows = $('#childTBCus').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>