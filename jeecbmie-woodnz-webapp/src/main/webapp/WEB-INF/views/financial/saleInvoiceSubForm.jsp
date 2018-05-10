<%@page import="com.cbmie.woodNZ.stock.entity.OutStock"%>
<%@page import="com.cbmie.woodNZ.stock.entity.OutStockSub"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .subtotal { font-weight: bold; }/*合计单元格样式*/
  </style>
<div id="childToolbar" style="padding:5px;height:auto">
 	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a> 
<!-- 	<span class="toolbar-item dialog-tool-separator"></span> -->
 	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a> 
</div>
<table id="sale_invoice_sub">
</table>
<script type="text/javascript">
var childTB;
initSubForm();
function initSubForm(value){
	childTB=$('#sale_invoice_sub').datagrid({
	method:'get',
	url:'${ctx}/financial/saleInvoice/getSaleInvoiceSubList', 
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
	pagination:false,
	showFooter:true,
    columns:[[
		{field:'shipNo',title:'驳船号',width:20,editor:{type:'text'}},
		{field:'metricTon',title:'公吨',width:15,editor:{type: 'numberbox', options: {precision:2}}},
		{field:'waterSale',title:'含水率%',sortable:true,width:15,editor:{type: 'numberbox', options: {precision:2}}},
		{field:'dryTon',title:'干吨',sortable:true,width:15,editor:{type: 'numberbox', options: {precision:2}}},
		{field:'money',title:'美金单价',width:15,sortable:true,editor:{type: 'numberbox', options: {precision:2}}},
		{field:'totalMoney',title:'总价',width:20,sortable:true,editor:{type: 'numberbox', options: {precision:2}}}
		]],
	onClickRow: onClickRow,
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    onLoadSuccess: function(data){
    	//onCountTolal();
    },
    toolbar:'#childToolbar'
	});
}

//开启行编辑
var editIndex = undefined;
var slEditor = "0";
function onClickRow(index){
	if (editIndex != index){
		var data=$('#sale_invoice_sub').datagrid('getData');
		if (endEditing()){
			$('#sale_invoice_sub').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#sale_invoice_sub').datagrid('selectRow', editIndex);
		}
	}
}

function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#sale_invoice_sub').datagrid('validateRow', editIndex)){
		$('#sale_invoice_sub').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function append(){
	if (endEditing()){
		$('#sale_invoice_sub').datagrid('appendRow',{shipNo:''});
		editIndex = $('#sale_invoice_sub').datagrid('getRows').length-1;
		$('#sale_invoice_sub').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
		var rows = $('#sale_invoice_sub').datagrid('getRows');
		$('#saleInvoiceSubJson').val(JSON.stringify(rows));
	}	
}
function removeit(){
	if (editIndex == undefined){return}
	$('#sale_invoice_sub').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
	var rows = $('#sale_invoice_sub').datagrid('getRows');
	$('#saleInvoiceSubJson').val(JSON.stringify(rows));
}
function accept(){
	if(!$('#mainform').form('validate'))//主表校验未通过
		return;
	if (endEditing()){
		var rows = $('#sale_invoice_sub').datagrid('getRows');
		$('#sale_invoice_sub').datagrid('acceptChanges');
		$('#saleInvoiceSubJson').val(JSON.stringify(rows));
		return true;
	}
}

function reject(){
	$('#sale_invoice_sub').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#sale_invoice_sub').datagrid('getChanges');
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
/**
function onCountTolal() {
    //添加“合计”列
      if(childTB.datagrid('getRows').length == 0)
	    	  return ;
      childTB.datagrid('appendRow', {
    	  shipNo: '<span class="subtotal">合计:</span>',
    	  metricTon: '<span class="subtotal">' + compute("metricTon") + '</span>',
    	  waterSale: '<span class="subtotal">--</span>',
    	  dryTon: '<span class="subtotal">' + compute("dryTon") + '</span>',
    	  money: '<span class="subtotal">' + compute("money") + '</span>',
    	  totalMoney: '<span class="subtotal">' + compute("totalMoney") + '</span>',
    });
  }
  //指定列求和
  function compute(colName) {
    var rows = childTB.datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
      total += parseFloat(rows[i][colName]);
    }
    if(total >= 0)
    	return total.toFixed(2);
    else
   		return '0';
  }
  */
</script>
