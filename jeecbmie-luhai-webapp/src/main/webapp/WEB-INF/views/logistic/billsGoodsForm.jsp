<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>
<body>
<div>
	<form id="goodsForm" action="${ctx}/logistic/billsGoods/${actionGoods}" method="post">
		<fieldset class="fieldsetClass" >
			<legend>采购信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%">采购合同号</th>
					<td width="30%">
						<input type="hidden" id="billsGoodsId" name="id" value="${lhBillsGoods.id}" />
						<input name="parentId" type="hidden" value="${lhBillsGoods.parentId}" />
						<input id="purchaseContractNo" name="purchaseContractNo"  type="text" value="${lhBillsGoods.purchaseContractNo}" class="easyui-validatebox"/>
					</td>
					<th width="20%">内部合同号</th>
					<td width="30%">
						<input id="innerContractNo" name="innerContractNo"  type="text" value="${lhBillsGoods.innerContractNo}" class="easyui-combobox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >品名</th>
					<td width="30%" >
						<input id="goodsName" name="goodsName"  type="text" value="${lhBillsGoods.goodsName}" class="easyui-validatebox"/></td>
					<th width="20%" >数量</th>
					<td width="30%" ><input id="goodsQuantity" name="goodsQuantity"  type="text" value="${lhBillsGoods.goodsQuantity}" class="easyui-numberbox" data-options="required:true,precision:'2',onChange:countAmount_sub"/></td>			
				</tr>
				<tr>
					<th>采购单价</th>
					<td><input id="purchasePrice" name="purchasePrice"  type="text" value="${lhBillsGoods.purchasePrice}" class="easyui-numberbox" data-options="required:true,precision:'2',onChange:countAmount_sub"/></td>		
					<th>金额</th>
					<td><input id="amount" name="amount"  type="text" value="${lhBillsGoods.amount}"  class="easyui-numberbox" data-options="required:true,precision:'2'"/></td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>指标信息</legend>
			<input type="hidden" name="goodsIndexJson" id="goodsIndexJson"/>
			<%@ include file="/WEB-INF/views/logistic/billsGoodsIndexForm.jsp"%>
		</fieldset>
	</form>
</div>

<script type="text/javascript">
function countAmount_sub(){
	var goodsQuantity = $("#goodsQuantity").val()?$("#goodsQuantity").val():0;
	var purchasePrice = $("#purchasePrice").val()?$("#purchasePrice").val():0;
	var amount = 0;
	amount = goodsQuantity*purchasePrice;
	$("#amount").numberbox('setValue',amount);
}

$(function(){  
	
	$('#goodsForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#goodsForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },  
	    beforeSubmit: function(){
	    	accept();
	    },
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		billGoodsObj.billsGoodsList.datagrid('clearSelections');
	    		billGoodsObj.billsGoodsList.datagrid('reload');
	    		$('#billsGoodsId').val(data.returnId);
	    		$("#numbers").numberbox('setValue',data.numbers);
	    		$("#invoiceAmount").numberbox('setValue',data.invoiceAmount);
	    		billGoodsObj.billsGoodsFormPage.panel('close');
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	//商品名称
	$('#goodsName').combotree({
		method: "get",
        url:'${ctx}/baseInfo/lhUtil/goodsInfoTree',  
        idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    panelHeight:'auto',
	    animate:false,
	    multiline:false,
	    required : true,
		panelHeight:'auto',
		onSelect:function(node){
			//返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#goodsName').combotree('clear');  
	        }  
		},
    	onHidePanel:function(){}
	});
	$('#innerContractNo').combobox({
		method: "get",
		editable:false,
		panelHeight:'auto',
		url:'${ctx}/baseInfo/lhUtil/innerContractNoByPurchaseNo/'+$("#purchaseContractIds").val(), 
	    valueField:'innerNo',
	    textField:'innerNo',
	    multiple:false,
	    onHidePanel:function(){}
	});
});
  
</script>
</body>
</html>

