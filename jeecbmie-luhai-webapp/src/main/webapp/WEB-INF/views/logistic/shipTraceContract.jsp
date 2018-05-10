<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_purchaseList">
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_PurchaseConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_PurchaseConfig()">重置</a>
	</form>
<table id="purchase_dg"></table>
<script type="text/javascript">
	var purchase_dg;
	purchase_dg=$('#purchase_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistic/orderShipContract/json?filter_EQS_state=1', 
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
    columns:[[    
			{field:'id',title: '选择',formatter: 
				function(value, row, index){
                return '<INPUT onclick="setIds(\''+row.contractNo+'\',\''+row.shipNo+'\')" name="orderShipContractNo"  type="radio">';
			}},
			{field:'contractNo',title:'合同号',sortable:true,width:20}, 
			{field:'shipNo',title:'船编号',sortable:true,width:20}, 
			{field:'ship',title:'船名',sortable:true,width:20},
			{field:'money',title:'金额',sortable:true,width:20}, 
			{field:'applyDate',title:'申请日期',sortable:true,width:20,
				formatter:function(value,row,index){  
					if(value==null){
						return null;
					}
	                    var unixTimestamp = new Date(value);  
	                    return unixTimestamp.format("yyyy-MM-dd");  
                    } 
			}
    ]],
	});
var orderShipContractNo = "";
var shipNo = "";
//保存 
function confirmSelect(){
	if(orderShipContractNo=="" || orderShipContractNo==null){
		alert(请选择订船合同);
		return false;
	}else{
		$("#mainform #orderShipContractNo").val(orderShipContractNo);
		$("#mainform #shipNo").val(shipNo);
	}
}

//创建查询对象并查询
function cx_PurchaseConfig(){
	var objPurchase=$("#searchFrom_purchaseList").serializeObject();
	purchase_dg.datagrid('reload',objPurchase);
}

function cz_PurchaseConfig(){
	$("#searchFrom_purchaseList").form('clear');
	cx_PurchaseConfig();
}

function setIds(contractNo,shipNoP){
	orderShipContractNo = contractNo;
	shipNo = shipNoP;
}

</script>
</body>
</html>