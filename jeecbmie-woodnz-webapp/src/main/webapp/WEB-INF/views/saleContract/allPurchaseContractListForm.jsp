<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_purchaseList" action="">
		&nbsp
	    <input type="text" name="filter_LIKES_hth" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_PurchaseConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_PurchaseConfig()">重置</a>
	</form>
<table id="purchase_dg"></table>
<script type="text/javascript">
	var purchase_dg;
	purchase_dg=$('#purchase_dg').datagrid({    
	method: "get",
	//选择审批通过，状态为运行中，转口类型的采购合同
	url:'${ctx}/sale/purchaseSaleSame/getAllPurchaseList/'+($('#id').val()==''?'0':$('#id').val())+
	'?filter_EQS_state=生效&filter_EQS_closeOrOpen=运行中&filter_EQS_htlb=147&filter_EQS_state_cx=0',
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
                return '<INPUT onclick="setPurchaseIds('+value+')"  type=checkbox>';
			}},
			{field:'hth',title:'合同号',sortable:true,width:20},
			{field:'xyh',title:'协议号',sortable:true,width:20},
			{field:'ghdw',title:'供货单位',sort:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.ghdw!=''&&row.ghdw!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.ghdw,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'hk',title:'货款',sortable:true,width:20},
    ]],
	});

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
function setPurchaseIds(id){
	pushUnique(id);//id重复，说明双击选项，意图取消勾选，从数组中删除次重复id
}

function pushUnique(id){
	var newId = id;
	for(var i=0;i< purchaseArray.length;i++){
		if(purchaseArray[i]==newId){
			purchaseArray.remove(purchaseArray[i]);
			return;
		}
	}
	purchaseArray.push(id);
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


//保存（采购列表弹框） 
function setPurchaseContract(){
	if(purchaseArray.length==0){alert("请选择！！！");return;}
	$.ajax({
		 type:"post",
		 url:"${ctx}/sale/purchaseSaleSame/setPurchaseContract?purchaseArray="+purchaseArray+'&mainId='+$("#id").val(),
		success:function(dataMsg){
			var dataMsg = eval('(' + dataMsg + ')');
			if(dataMsg.returnFlag=='success'){
				$('#dgPurchaseList').datagrid('loadData',dataMsg.returnPurchaseData);
				$('#dgproflitLoss').datagrid('loadData',dataMsg.returnProflitData);
				var rows = $('#dgPurchaseList').datagrid('getRows');
				$('#dgPurchaseList').datagrid('acceptChanges');
				$('#purchaseContractJson').val(JSON.stringify(rows));
				purchase.panel('close');
				return true;
			}
			else if(dataMsg.returnFlag=='fail'){
				parent.$.messager.alert(dataMsg.returnMsg);
				return false;
			}  
		 }
	});
}
</script>
</body>
</html>