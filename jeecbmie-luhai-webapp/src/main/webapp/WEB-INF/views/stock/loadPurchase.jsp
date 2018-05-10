<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_purchaseList" action="">
	    <input type="text" name="filter_LIKES_innerContractNo" class="easyui-validatebox" data-options="prompt: '内部合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_PurchaseConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_PurchaseConfig()">重置</a>
	</form>
<table id="purchase_dg"></table>
<script type="text/javascript">
	var purchase_dg;
	purchase_dg=$('#purchase_dg').datagrid({    
	method: "get",
	url:'${ctx}/purchase/purchaseContract/json?filter_EQS_state=1&filter_EQS_closeOrOpen=1&filter_EQS_changeState=1', 
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
				function(value, rowData, rowIndex){
                return '<input type="radio"  />';
            }},
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:30}, 
			{field:'contractCategoryView',title:'合同类别',sortable:true,width:10},
// 			{field:'agreementNo',title:'协议号',sortable:true,width:25},
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:30},
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:8},
			{field:'contractAmount',title:'合同金额',sortable:true,width:15},
    ]],
    sortName:'id',
    sortOrder: 'desc',
    onClickRow: function(rowIndex, rowData){
    	var oradio = $("input[type='radio']");
    	for(var i=0;i<oradio.length;i++){
    	    if(oradio[i].type == "radio"){
    	       oradio[i].checked = false
    	    }
    	}
    	 var radio = $("input[type='radio']")[rowIndex];
         if(radio.checked==false){
         	$("input[type='radio']")[rowIndex].checked = true;
         }else{
         	$("input[type='radio']")[rowIndex].checked = false;
         }
    },
	});
	

//创建查询对象并查询
function cx_PurchaseConfig(){
	var objPurchase=$("#searchFrom_purchaseList").serializeObject();
	purchase_dg.datagrid('reload',objPurchase);
}


</script>
</body>
</html>