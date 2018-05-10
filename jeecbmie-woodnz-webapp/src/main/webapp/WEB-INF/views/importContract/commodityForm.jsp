<%@page import="com.cbmie.genMac.foreignTrade.entity.Commodity"%>
<%@page import="com.cbmie.genMac.foreignTrade.entity.ImportContract"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTB_commodity" data-options="onClickRow: onClickRow">
</table>
<%
	ImportContract importContract = (ImportContract)request.getAttribute("importContract");
	List<Commodity> gcList = importContract.getCommodityList();
	ObjectMapper objectMapper = new ObjectMapper();
	String gcJson = objectMapper.writeValueAsString(gcList);
	request.setAttribute("gcJson", gcJson);
%>
<script type="text/javascript">
var childTB_commodity;
initCommodityForm();
function initCommodityForm(value){
	childTB_commodity=$('#childTB_commodity').datagrid({
	data :value != null ? value :  JSON.parse('${gcJson}'),
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
	frozenColumns:[[
		{field:'id',title:'id',hidden:true},
		{field:'goodsCategory',title:'商品大类',width:80,
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'goodsName',
					textField:'goodsName',
					method:'get',
					url:'${ctx}/baseinfo/goodsManage/json',
					required:true,
					loadFilter:function(data){
						return data.rows;
					}
				}
			}
		},
		{field:'specification',title:'规格型号',width:80,editor:{type:'validatebox',options:{required:true}}},
		{field:'origin',title:'原产地',width:80,editor:{type:'validatebox'}},
		{field:'cname',title:'中文名',width:80,editor:{type:'validatebox',options:{required:true}}},
		{field:'ename',title:'英文名',width:80,editor:{type:'validatebox'}}
	]],
    columns:[
        [
		{"title":"合同","colspan":3},
		{field:'original',title:'原币金额',rowspan:2,align:'center',width:20},
		{"title":"关税","colspan":2},
		{"title":"消费税","colspan":2},
		{"title":"增值税","colspan":2}
        ],
        [
        {field:'amount',title:'数量',width:20,editor:{type:'numberbox',options:{precision:0,required:true}}},
		{field:'unit',title:'单位',width:20,
        	editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'name',
					textField:'name',
					method:'get',
					url:'${ctx}/system/dict/getDictByCode/DW',
					required:true
				}
			}
        },
		{field:'price',title:'单价',width:20,editor:{type:'numberbox',options:{precision:2,required:true}}},
		{field:'tax',title:'关税税额',width:20,editor:{type:'numberbox',options:{precision:2}}},
		{field:'taxRate',title:'关税税率',width:20,editor:{type:'validatebox'}},
		{field:'saleTax',title:'消费税额',width:20,editor:{type:'numberbox',options:{precision:2}}},
		{field:'saleTaxRate',title:'消费税率',width:20,editor:{type:'validatebox'}},
		{field:'vat',title:'增值税额',width:20,editor:{type:'numberbox',options:{precision:2}}},
		{field:'vatRate',title:'增值税率',width:20,editor:{type:'validatebox'}}
    	]
    ],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar',
    onEndEdit : function(index, row, changes){
    	$('#childTB_commodity').datagrid('getRows')[index]['original'] = (row.amount * row.price).toFixed(2);
    }
	});
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB_commodity').datagrid('validateRow', editIndex)){
		$('#childTB_commodity').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB_commodity').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB_commodity').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#childTB_commodity').datagrid('appendRow',{cname:''});
		editIndex = $('#childTB_commodity').datagrid('getRows').length-1;
		$('#childTB_commodity').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB_commodity').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB_commodity').datagrid('getRows');
		$('#childTB_commodity').datagrid('acceptChanges');
		$('#commodityChildJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#childTB_commodity').datagrid('selectRow', i).datagrid('beginEdit', i);
				editIndex = i;
				return false;
			}else{
				array.push(goods);
			}
		}
		calculate(rows);
		return true;
	}
}
function reject(){
	$('#childTB_commodity').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB_commodity').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function calculate(rows){
	var originalCurrency = 0;
	for(var i = 0; i < rows.length; i++){
		originalCurrency += parseFloat(rows[i].original);
	}
	$('#originalCurrency').numberbox({
		value:originalCurrency
	});
}

</script>