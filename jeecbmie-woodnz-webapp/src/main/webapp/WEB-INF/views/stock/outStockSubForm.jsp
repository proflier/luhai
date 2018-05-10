<%@page import="com.cbmie.woodNZ.stock.entity.OutStock"%>
<%@page import="com.cbmie.woodNZ.stock.entity.OutStockSub"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
<div id="childToolbar" style="padding:5px;height:auto">
</div>
<table id="out_stock_sub">
</table>
<script type="text/javascript">
var childTB;
initSubForm();
function initSubForm(value){
	childTB=$('#out_stock_sub').datagrid({
	method:'get',
	url:'${ctx}/stock/outStock/getOutStockSubList', 
	fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'goodsNo',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
    columns:[[
		{field:'goodsNo',title:'商品编号',width:10},
		{field:'goodsName',title:'商品名称',width:10},
		{field:'pieceAvailable',title:'应发片数',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
		{field:'numAvailable',title:'应发件数',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
		{field:'amount',title:'应发立方数',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
		{field:'realsendPNum',title:'实发件数',sortable:true,width:10,editor:{type: 'numberbox', options: {precision:3}}},
		{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
		{field:'realsendVNum',title:'实发数量',sortable:true,width:10,editor:{type: 'numberbox', options: {precision:3}}},
		{field:'unitPrice',title:'单价',width:10},
		{field:'saleContractNo',title:'销售合同号',width:10},
		{field:'cpContractNo',title:'綜合合同号',width:10},
		{field:'money',title:'金额',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
// 		{field:'dataSource',title:'数据源',width:10},
// 		{field:'warehouse',title:'仓库',width:10,
// 			formatter: function(value,row,index){
// 				var val;
// 				if(row.warehouse==''|| row.warehouse==null){
// 					return '';
// 				}
// 				else if(row.dataSource =='放货'){
// 					$.ajax({
// 						type:'GET',
// 						async: false,
// 						url:"${ctx}/system/downMenu/getWarehouseName/"+row.warehouse,
// 						success: function(data){
// 							if(data != null){
// 								val = data;
// 							} else {
// 								val = '';
// 							}
// 						}
// 					});
// 					return val;
// 				}
// 				else if(row.dataSource =='下游交单'){
// 					$.ajax({
// 						type:'GET',
// 						async: false,
// 						url:"${ctx}/stock/outStock/getWarehouseName/"+row.warehouse,
// 						success: function(data){
// 							if(data != null){
// 								val = data;
// 							} else {
// 								val = '';
// 							}
// 						}
// 					});
// 					return val;
// 				}
// 			}},
		{field:'warehouse',title:'仓库',width:10,
			formatter: function(value,row,index){
				var val;
				if(row.warehouse==''|| row.warehouse==null){
					return '';
				}
				$.ajax({
					type:'GET',
					async: false,
					url:"${ctx}/system/downMenu/getWarehouseName/"+row.warehouse,
					success: function(data){
						if(data != null){
							val = data;
						} else {
							val = '';
						}
					}
				});
				return val;
			}}
		]],
	onClickRow: onClickRow,
	showFooter: true,
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    onLoadSuccess: function(data){
    	endEditing();
    	onCountTolalFooter();
    },
    toolbar:'#childToolbar'
	});
}

//开启行编辑
var editIndex = undefined;
var slEditor = "0";
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#out_stock_sub').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#out_stock_sub').datagrid('selectRow', editIndex);
		}
	}
	
	//实发数量
	var realsendPNum = $('#out_stock_sub').datagrid('getEditor', { index: editIndex, field: 'realsendPNum' });
	realsendPNum.target.change(function () {
		 $('#out_stock_sub').datagrid('acceptChanges');
		 editIndex = undefined;
		 onCountTolalFooter();
	});
	
	//实发件数
	var realsendVNum = $('#out_stock_sub').datagrid('getEditor', { index: editIndex, field: 'realsendVNum' });
	realsendVNum.target.change(function () {
		 $('#out_stock_sub').datagrid('acceptChanges');
		 editIndex = undefined;
		 onCountTolalFooter();
	});
}

function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#out_stock_sub').datagrid('validateRow', editIndex)){
		$('#out_stock_sub').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function accept(){
	if(!$('#mainform').form('validate'))//主表校验未通过
		return;
	if (endEditing()){
		var rows = $('#out_stock_sub').datagrid('getRows');
		$('#out_stock_sub').datagrid('acceptChanges');
		$('#outStackSubJson').val(JSON.stringify(rows));
		return true;
	}
}

  //指定列求和
  function compute(colName) {
    var rows = childTB.datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
      total += parseFloat(rows[i][colName]);
    }
    if(colName=='realsendVNum'){
    	$('#realSendNum').val(total.toFixed(3));
    }
    if(colName=='money'){
    	$('#payAmount').numberbox('setValue',total.toFixed(2));
    	return total.toFixed(2);
    }
    if(total >= 0)
    	return total.toFixed(3);
    else
   		return '0';
  }
  
  //计算数量和
  function computeRealSendNum() {
  	if(!$('#mainform').form('validate'))//主表校验未通过
		return;
    var rows = $('#out_stock_sub').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
      total += parseFloat(rows[i]["realsendVNum"]);
    }
    if(total >= 0)
    	 $('#realSendNum').val(total.toFixed(3));
    else
    	$('#realSendNum').val('0');
  }
  
  function onCountTolalFooter(){
	  $('#out_stock_sub').datagrid('reloadFooter',[
           	{ goodsNo: '合计：',
           	realsendPNum:compute("realsendPNum"),
           	realsendVNum:compute("realsendVNum"),
           	pieceAvailable:compute("pieceAvailable"),
           	numAvailable: compute("numAvailable"),
           	amount:compute("amount"),
           	money:compute("money")}
           ]);
  }
  
</script>
