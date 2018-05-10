<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_saleList" action="">
		&nbsp;
	    <input type="text" name="filter_LIKES_shipNo" class="easyui-validatebox" data-options="prompt: '船编号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_ShipsConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_ShipsConfig()">重置</a>
	</form>
<table id="ships_dg"></table>
<script type="text/javascript">
var tradeC = '${tradeCategory}';
var row_t = {};
//保存 
function saveSelectShip(){
	if(row_t.shipNo){
		$("#subMainform>#subMainDiv #shipNo").val(row_t.shipNo);
		$("#subMainform>#subMainDiv #ship").val(row_t.shipName);
		$("#subMainform>#subMainDiv #settleQuantity").val(row_t.unloadQuantity);
	}
}
function insurance_save(){
	if(row_t.shipNo){
		$("#mainform>#mainDiv #shipNo").val(row_t.shipNo);
		$("#mainform>#mainDiv #shipName").val(row_t.shipName);
		$("#mainform>#mainDiv #tradeCategory").val(row_t.tradeCategory);
	}
}
function wharfSettlement_save(){
	if(row_t.shipNo){
		$("#subMainform>#subMainDiv #shipNo").val(row_t.shipNo);
		$("#subMainform>#subMainDiv #shipName").val(row_t.shipName);
	}
}
//创建查询对象并查询
function cx_ShipsConfig(){
	var objPurchase=$("#searchFrom_saleList").serializeObject();
	if(tradeC=="insurance" || tradeC=="wharfSettlement"){
	}else{
		objPurchase.filter_EQS_tradeCategory=tradeC;
	}
	saleC_dg.datagrid('reload',objPurchase);
}

function cz_ShipsConfig(){
	$("#searchFrom_saleList").form('clear');
	cx_ShipsConfig();
}

$(function(){
	var params = {};
	if(tradeC=="insurance" || tradeC=="wharfSettlement"){
	}else{
		params.filter_EQS_tradeCategory=tradeC;
	}
	saleC_dg=$('#ships_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistic/shipTrace/jsonNoPermission', 
	queryParams:params,
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
	sortName:'id',
	sortOrder:'desc',
    columns:[[    
			{field:'id',title:'',checkbox:true},  
			{field:'shipNo',title:'船编号',width:20},
			{field:'shipName',title:'船中文名',width:20},
			{field:'shipNameE',title:'船英文名',width:20}
    ]],
    onCheck:function(index,row){
    	row_t = row;
    },
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false
	});
});
	
</script>
</body>
</html>