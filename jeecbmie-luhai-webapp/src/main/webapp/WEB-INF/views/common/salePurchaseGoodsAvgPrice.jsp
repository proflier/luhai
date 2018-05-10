<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div style="margin: 5px;">
		<input type="radio" name="configRadio" style="margin-top:-2px" onclick="config_list(0)" checked="checked"/>销售
		<input type="radio" name="configRadio" style="margin-top:-2px" onclick="config_list(1)"/>采购
		<form id="searchFrom_config" action="">
			<input type="text" id="filter_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_config()">重置</a> 
		</form>
	</div>
<table id="dg_config"></table>
<script type="text/javascript">
var dg_config;

var flag = 0;
function config_list(value) {
	if (value == 0) {
		flag = 0;
		saleDg();
	} else {
		flag = 1;
		purchaseDg();
	}
}

function saleDg() {
	var row = goodsObj.goodsList.datagrid('getSelected');
	dg_config=$('#dg_config').datagrid({
		method: "get",
		url:'${ctx}/sale/saleContractGoods/jsonNoPermission?filter_EQS_goodsName=' + row.goodsCode,
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		singleSelect:true,
		enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		columns:[[
			{field:'saleContractId',title:'销售合同号',width:40,
				formatter: function(value, row, index){
					var rtnData;
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/sale/saleContract/getById/" + value,
						success: function(data){
							rtnData = data.contractNo;
						}
					});
					return rtnData;
				}
			},
			{field:'goodsNameView',title:'商品名称',width:20},
			{field:'price',title:'单价',width:20},
		    //{field:'unitsView',title:'单位',width:20},
		    {field:'goodsQuantity',title:'数量',width:20}
		]],
		sortName:'id',
		groupField:'goodsNameView',
	    view:groupview,
	    groupFormatter:function(value, rows){
	    	var priceTotal = 0;
	    	for(var i = 0; i < rows.length; i++){
	    		priceTotal += Number(rows[i].price);
	    	}
	    	var priceAvg = priceTotal / (rows.length);
	    	return value + "，平均销售单价：" + priceAvg.toFixed(2);
	    }
	});
}

function purchaseDg() {
	var row = goodsObj.goodsList.datagrid('getSelected');
	dg_config=$('#dg_config').datagrid({
		method: "get",
		url:'${ctx}/purchase/purchaseContractGoods/jsonNoPermission?filter_EQS_goodsName=' + row.goodsCode,
		fit : false,
		fitColumns : true,
		border : false,
		striped: true,
		idField : 'id',
		singleSelect:true,
		enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		columns:[[
			{field:'purchaseContractId',title:'采购合同号',width:40,
				formatter: function(value, row, index){
					var rtnData;
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/purchase/purchaseContract/getById/" + value,
						success: function(data){
							rtnData = data.purchaseContractNo;
						}
					});
					return rtnData;
				}
			},
			{field:'goodsNameView',title:'商品名称',width:20},
			{field:'purchasePrice',title:'单价',width:20},
		    //{field:'unitsView',title:'单位',width:20},
		    {field:'goodsQuantity',title:'数量',width:20}
		]],
		sortName:'id',
		groupField:'goodsNameView',
	    view:groupview,
	    groupFormatter:function(value, rows){
	    	var priceTotal = 0;
	    	for(var i = 0; i < rows.length; i++){
	    		priceTotal += Number(rows[i].purchasePrice);
	    	}
	    	var priceAvg = priceTotal / (rows.length);
	    	return value + "，平均采购单价：" + priceAvg.toFixed(2);
	    }
	});
}

$(function(){
	saleDg();
});

//创建查询对象并查询
function cx_config() {
	if ($('#filter_contractNo').val() == "") {
		return;
	}
	if (flag == 0) {
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/sale/saleContract/getByNo/" + $('#filter_contractNo').val(),
			success: function(data){
				if (typeof data == "string") {
					parent.$.messager.alert('提示', '合同号不存在!');
				} else {
					dg_config.datagrid('reload', {filter_EQL_saleContractId:data.id});
				}
			}
		});
	} else {
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/purchase/purchaseContract/getByNo",
			data : {purchaseNo:$('#filter_contractNo').val()},
			success: function(data){
				if (typeof data == "string") {
					parent.$.messager.alert('提示', '合同号不存在!');
				} else {
					dg_config.datagrid('reload', {filter_EQL_purchaseContractId:data.id});
				}
			}
		});
	}
}

function reset_config() {
	$("#searchFrom_config")[0].reset();
	dg_config.datagrid('reload', {});
}
</script>
</body>
</html>