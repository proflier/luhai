<%@page import="com.cbmie.lh.logistic.entity.LhBillsGoodsIndex"%>
<%@page import="com.cbmie.lh.logistic.entity.LhBillsGoods"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTB" data-options="onClickRow: onClickRow"></table>
<%
	LhBillsGoods billsGoods = (LhBillsGoods)request.getAttribute("lhBillsGoods");
	List<LhBillsGoodsIndex> billsGoodsIndexList = billsGoods.getGoodsIndexList();
	ObjectMapper objectMapperCus = new ObjectMapper();
	String childJson = objectMapperCus.writeValueAsString(billsGoodsIndexList);
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
		{field:'indexName',title:'指标值（中文）',width:30,
     		editor:{
				type:'combobox',
				options:{
// 					panelHeight:'auto',
					valueField:'id',
					textField:'indexName',
					required:true,
					method:'get',
					url:'${ctx}/baseInfo/lhUtil/goodsIndexItems',
					onSelect:function(record){
						var indexNameEobj =  $('#childTB').datagrid('getEditor', {index:editIndex,field:'indexNameE'});
						if(indexNameEobj != null){
							$.ajax({
				 				url : '${ctx}/baseInfo/lhUtil/goodsindexNameEShow/' + record.id,
				 				type : 'get',
				 				success : function(data) {
		 							$(indexNameEobj.target).val(data);
				 				}
				 			});
						}
						var indexUnitobj =  $('#childTB').datagrid('getEditor', {index:editIndex,field:'unit'});
						if(indexUnitobj != null){
							$.ajax({
				 				url : '${ctx}/baseInfo/lhUtil/goodsUnithowCode/' + record.id,
				 				type : 'get',
				 				success : function(data) {
		 							$(indexUnitobj.target).val(data);
				 				}
				 			});
						}
					}
				}
			},
			formatter: function(value,row,index){
				var val;
				if(value!=''&&value!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/baseInfo/lhUtil/goodsIndexNameShow/"+value,
						success: function(data){
							if(data != null){
								val = data;
							} else {
								val = '';
							}
						}
					});
					return val;
					
				}else{
					return '';
				}
			}
     	},
		{field:'indexNameE',title:'指标值（英文）',width:25,editor:{type:'validatebox',options:{disabled:true}}},
     	{field:'unit',title:'单位',width:10,editor:{type:'validatebox',options:{disabled:true}}},
     	{field:'conventions',title:'约定值',width:20,editor:{type:'numberbox',options:{min:0,precision:2}}},
     	{field:'rejectionValue',title:'拒收值',width:20,editor:{type:'numberbox',options:{min:0,precision:2,}}},
     	{field:'terms',title:'扣罚条款',width:25,editor:{type:'validatebox'}}
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
		$('#childTB').datagrid('appendRow',{conventions:''});
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
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#goodsIndexJson').val(JSON.stringify(rows));
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


</script>