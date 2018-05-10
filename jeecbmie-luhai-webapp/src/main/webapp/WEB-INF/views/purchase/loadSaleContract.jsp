<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contract" action="">
	    <input type="text" name="contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
<table id="contract_dg"></table>
<script type="text/javascript">
	var contract_dg;
	contract_dg=$('#contract_dg').datagrid({    
	method: "get",
	url:'${ctx}/purchase/purchaseContract/getSaleShip', 
    fit : false,
//     singleSelect:true,
	fitColumns : true,
// 	scrollbarSize : 0,
	border : false,
	idField : 'id',
	rownumbers:true,
	striped:true,
    columns:[[    
			{field:'id',title:'',checkbox:true},
			{field:'contractNo',title:'合同号',sortable:true,width:30},
			{field:'sellerView',title:'卖方',sortable:true,width:25,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&& value!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}	
			},
			{field:'purchaserView',title:'买方',sortable:true,width:30,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&& value!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}	
			},
			{field:'goodsNameView',title:'品名',sortable:true,width:10,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&&value!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}
			},
			{field:'price',title:'采购单价',sortable:true,width:10},
			{field:'goodsQuantity',title:'数量',sortable:true,width:15},
			{field:'amount',title:'金额',sortable:true,width:15}
    ]],
    sortName:'id',
    sortOrder: 'desc'
	});

//保存
function initSaleContract(){
	var rows = contract_dg.datagrid('getSelections');
	if(rowIsNull(rows)) return;
	var saleGoodsIds ="";
    for (var i = 0; i < rows.length; i++) {
    	if(saleGoodsIds!=""){
    		saleGoodsIds = saleGoodsIds +","+ rows[i].id;
    	}else{
    		saleGoodsIds = rows[i].id;
    	}
    }
    var purchaseNo = $('#purchaseContractNo').val();
	$.ajax({
		type:'GET',
		url:'${ctx}/purchase/purchaseContract/saveSaleRelation/'+purchaseNo+"/"+saleGoodsIds,
		success: function(data){
			dgContractGoods.datagrid('reload');
		}
	});
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