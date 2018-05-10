<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contract" action="">
	    <input type="text" name="filter_LIKES_innerContractNo" class="easyui-validatebox" data-options="prompt: '内部合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
<table id="contract_dg" style="height:300px"></table>
<script type="text/javascript">
	var contract_dg;
	contract_dg=$('#contract_dg').datagrid({    
	method: "get",
// 	url:'${ctx}/purchase/purchaseContract/jsonNoPermission?filter_EQS_state=1&filter_EQS_closeOrOpen=1&filter_EQS_changeState=1',
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
                return '<input type="radio" />';
            }},
            {field:'innerContractNo',title:'内部合同号',sortable:true,width:25},
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:25},
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
			{field:'createrDept',title:'申请部门',sortable:true,width:20},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:15},
			{field:'createDate',title:'创建时间',sortable:true,width:15}
    ]],
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
   sortName:'id',
   sortOrder: 'desc'
	});

//保存
function initContract(){
	var row = contract_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$('#inteContractNo').val(row.innerContractNo);
	$('#contractNo').val(row.purchaseContractNo);
	$('#moreOrLess').numberbox('setValue',row.moreOrLessAmount);
	$('#supplier').combobox('setValue',row.deliveryUnit);
	$('#ourUnit').combobox('setValue',row.orderUnit);
	$('#importingCountry').combotree('setValue',row.importCountry);
	$('#currency').combobox('setValue', row.currency);
	$('#creditType').combobox('setValue', row.creditType);
	var applyMoney =	row.contractAmount*1.00;
	var quantity = row.contractQuantity*1.000;
	$('#applyMoney').numberbox('setValue',applyMoney);
	$('#quantity').numberbox('setValue',quantity);
	if(quantity!=0){
		$("#unitPrice").numberbox('setValue',parseFloat(applyMoney/quantity).toFixed(2));
	}else{
		$.messager.alert('警告','开证数量为0，注意修改！');
	}
	$('#runMode').combobox('setValue', row.runMode);
	$('#relLoginNames').val(row.relLoginNames);
	$('#businessManager').combotree('setValue',row.businessManager);
}

//创建查询对象并查询
function cx_contract(){
	var obj=$("#searchFrom_contract").serializeObject();
	contract_dg.datagrid('reload',obj);
}

function reset_contract(){
	$("#searchFrom_contract")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_contract").serializeObject();
	contract_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>