<%@page import="com.cbmie.lh.stock.entity.QualityGoods"%>
<%@page import="com.cbmie.lh.stock.entity.QualityIndex"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
</div>
<table id="childTB" data-options="onClickRow: onClickRow"></table>
<%
	QualityGoods goods = (QualityGoods)request.getAttribute("qualityGoods");
	List<QualityIndex> indexList = goods.getIndexList();
	ObjectMapper objectMapper = new ObjectMapper();
	String childJson = objectMapper.writeValueAsString(indexList);
	request.setAttribute("childJson", childJson);
%>

<script type="text/javascript">
var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${childJson}'),
    fit : false,
	fitColumns : true,
	scrollbarSize : 0,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
    columns:[[    
		{field:'id',title:'id',hidden:true},
		{field:'indexName',title:'指标名称（中文）',width:30,
     		editor:{
				type:'combobox',
				options:{
					valueField:'indexName',
					textField:'indexName',
					required:true,
					method:'get',
					url:'${ctx}/baseInfo/lhUtil/goodsIndexItems',
					onSelect:function(record){
						var indexNameEnObj =  $('#childTB').datagrid('getEditor', {index:editIndex,field:'indexNameEn'});
						
						$(indexNameEnObj.target).val(record.indexNameE);
						
						var unitObj =  $('#childTB').datagrid('getEditor', {index:editIndex,field:'unit'});
						
						unitObj.target.combobox('setValue', record.unit);
						
					}
				}
			}
     	},
		{field:'indexNameEn',title:'指标名称（英文）',width:25,editor:{type:'validatebox',options:{disabled:true}}},
     	{field:'unit',title:'单位',width:10,
			editor:{
     			type:'combobox',
     			options:{
     				valueField:'code',
					textField:'name',
					required:true,
					method:'get',
					url:"${ctx}/system/dictUtil/getDictByCode/INDEXUNIT"
     			}
     		},
     		formatter: function(value, row, index){
     			$.ajax({
					type:'GET',
					async: false,
					url:"${ctx}/system/dictUtil/getDictNameByCode/INDEXUNIT/" + value,
					success: function(data){
						val = data;
					}
				});
				return val;
     		}
     	},
     	{field:'inspectionValue',title:'检验值',width:20,editor:{type:'numberbox',options:{min:0,precision:2}}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var editIndex = undefined;
function endEditing(){
	if(editIndex == undefined){return true}
	if($('#childTB').datagrid('validateRow', editIndex)){
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickRow(index){
	if(editIndex != index){
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
	if(endEditing()){
		$('#childTB').datagrid('appendRow',{});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if(endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#goodsIndexJson').val(JSON.stringify(rows));
	}
	return true;
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length + ' 行被修改！', 'info');
}
</script>