<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBaseInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBankInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a> -->
<!-- 	<span class="toolbar-item dialog-tool-separator"></span> -->
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-hamburg-stop',plain:true" onclick="abolition()">停用</a>
</div>
<table id="childTB" data-options="onClickRow: onClickRow"></table>
<%
	WoodAffiBaseInfo affiBaseInfo = (WoodAffiBaseInfo)request.getAttribute("affiBaseInfo");
	List<WoodAffiBankInfo> affiBankList = affiBaseInfo.getAffiBankInfo();
	ObjectMapper objectMapper = new ObjectMapper();
	String abJson = objectMapper.writeValueAsString(affiBankList);
	request.setAttribute("abJson", abJson);
%>
<script type="text/javascript">
var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${abJson}'),
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
		{field:'bankName',title:'银行名称',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'bankNO',title:'帐号',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'partitionInfo',title:'省市信息',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'contactPerson',title:'联系人',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'phoneContact',title:'联系电话',width:20,editor:{type:'validatebox',options:{required:false}}},
		{field:'status',title:'状态',width:20,editor:{
				type:'combobox',
				options:{
					valueField: 'label',
					textField: 'value',
					panelHeight:'auto',
					required:true, 
					data: [{label: '1',value: '启用'}, 
					       {label: '0',value: '停用'}]
				}
			},
			formatter: function(value){
				if (value == 0){
					return '停用';
				} else {
					return '启用';
				}
			}
		}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB').datagrid('validateRow', editIndex)){
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#childTB').datagrid('appendRow',{bankName:''});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
		$('#affiBankJson').val(JSON.stringify(rows));
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#affiBankJson').val(JSON.stringify(rows));
	}
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
function abolition (){
 	var destObject = $('#childTB').datagrid('getEditor', {index:editIndex,field:'status'});
 	$(destObject.target).combobox('setValue',0);
	$('#childTB').datagrid('endEdit', editIndex);
}
</script>