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
				function(value, row, index){
                return '<INPUT onclick="setIds(\''+row.innerContractNo+'\',\''+(row.deliveryUnit==''?0:row.deliveryUnit)+'\',\''+(row.contractCategory==''?0:row.contractCategory)+'\')"  type=checkbox>';
			}},
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:30}, 
			{field:'contractCategoryView',title:'合同类别',sortable:true,width:10},
// 			{field:'agreementNo',title:'协议号',sortable:true,width:25},
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:30},
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:8},
			{field:'contractAmount',title:'合同金额',sortable:true,width:15},
    ]],
	});
	
//保存 
function setGoods(){
	if(validateUnique(ghdwArray)){alert("请选择相同供货商的合同！！！");return;};
	if(validateUnique(htlbArray)){alert("请选择相同贸易类别的合同！！！");return;};
	if(purchaseArray.length==0){alert("请选择！！！");return;}
	$.ajax({
		 type:"post",
		 url:"${ctx}/logistic/bills/setGoods?purchaseArray="+purchaseArray,
		 dataType:'json',
		 success:function(data){
			 if(data.state=='success'){
				 $("#sendUnit").combobox('setValue',data.deliveryUnit);//发货单位
				 $("#deliveryUnit").combobox('setValue',data.deliveryUnit);//供应商赋值
				 $("#orderUnit").combobox('setValue',data.orderUnit);//订货商赋值
				 $("#receivingUnit").combobox('setValue',data.receivingUnit);//收货商赋值
				 $("#currency").combobox('setValue',data.currency);//货币赋值
				$("#purchaseContractIds").val(data.purchaseContractIds);//
				$("#checkOrg").combobox('setValue',data.checkOrg);
				$("#checkStandard").val(data.checkStandard);
				$('#relLoginNames').val(data.relLoginNames);
				shipNoItems();
				purchase.panel('close');
				return true;
		 	}else{
		 		return false;
		 	}
		 }
	});
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

var purchaseArray=new Array();//存放采购合同id
var ghdwArray=new Array();//存放采购合同供货单位
var htlbArray=new Array();//存放采购合同合同类别
function setIds(id,ghdw,htlb){//ghdw=0代表供货单位为空
	deleteUnique(id,ghdw,htlb);//id重复，说明双击选项，意图取消勾选，从数组中删除次重复id
}

function deleteUnique(id,ghdw,htlb){
	var newId = id;
	for(var i=0;i< purchaseArray.length;i++){
		if(purchaseArray[i]==newId){
			purchaseArray.remove(purchaseArray[i]);
			ghdwArray.remove(ghdwArray[i]);
			htlbArray.remove(htlbArray[i]);
			return;
		}
	}
	purchaseArray.push(id);
	ghdwArray.push(ghdw);
	htlbArray.push(htlb);
}

//验证list中元素是否完全一样，完全一样返回false，有不同元素返回true
function validateUnique(arr){
	for(var i=0;i<arr.length;i++){
		if( arr[0] != arr[i] ){
			return true;
		}
	}
	return false;
}

</script>
</body>
</html>