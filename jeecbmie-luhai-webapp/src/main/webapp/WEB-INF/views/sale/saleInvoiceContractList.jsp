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
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_SaleContractConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_SaleContractConfig()">重置</a>
	</form>
<table id="saleC_dg"></table>
<script type="text/javascript">
var saleC_dg;
var saleContractNoT="";
//保存 
function setSaleContractNo(){
	if(saleContractNoT == ""){
		alert("请选择！！！");return;
	}else{
		$("#saleContractNo").val(saleContractNoT);
	}
}

//创建查询对象并查询
function cx_SaleContractConfig(){
	var objPurchase=$("#searchFrom_saleList").serializeObject();
	saleC_dg.datagrid('reload',objPurchase);
}

function cz_SaleContractConfig(){
	$("#searchFrom_saleList").form('clear');
	cx_SaleContractConfig();
}

function setIds(id){
	saleContractNoT= id;
}
$(function(){
	saleC_dg=$('#saleC_dg').datagrid({    
	method: "get",
	url:'${ctx}/sale/saleContract/jsonNoPermission?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1&filter_EQS_purchaser=${customer}', 
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
	idField : 'id',
	pagination:false,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
    columns:[[    
			{field:'id',title:'',width:5,
				formatter: 
					function(value, row, index){
	                return '<INPUT name="contactNoId" onclick="setIds(\''+row.contractNo+'\')"  type=radio>';
				}},
            {field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'contractQuantity',title:'合同数量',sortable:true,width:20},
			{field:'contractAmount',title:'合同金额',sortable:true,width:20},
			{field:'sellerView',title:'卖方',sortable:true,width:20}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false
	});
});
	
</script>
</body>
</html>