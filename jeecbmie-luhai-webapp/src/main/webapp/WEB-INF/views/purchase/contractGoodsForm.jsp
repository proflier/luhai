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
	<form id="contractGoodsForm" action="${ctx}/purchase/purchaseContractGoods/${actionGoods}" method="post">
		<fieldset class="fieldsetClass" >
			<legend>采购信息</legend>
			<input type="hidden" id="purchaseContractGoodsId" name="id" value="${purchaseContractGoods.id}" />
			<input name="purchaseContractId" type="hidden" value="${purchaseContractGoods.purchaseContractId}" />
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%" >品名</th>
					<td width="30%" ><input id="goodsName" name="goodsName"  type="text" value="${purchaseContractGoods.goodsName}" class="easyui-validatebox"/></td>
					<th width="20%" >数量</th>
					<td width="30%" ><input id="goodsQuantity" name="goodsQuantity"  type="text" value="${purchaseContractGoods.goodsQuantity}" /></td>			
				</tr>
				<tr>
					<th>采购单价</th>
					<td><input id="purchasePrice" name="purchasePrice"  type="text" value="${purchaseContractGoods.purchasePrice}" /></td>		
					<th>金额</th>
					<td><input id="amount" name="amount" readonly="readonly" type="text" value="${purchaseContractGoods.amount}"  class="easyui-numberbox" data-options="required:true,min:0,precision:'2'"/></td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>指标信息</legend>
			<input type="hidden" name="goodsIndexJson" id="goodsIndexJson"/>
			<%@ include file="/WEB-INF/views/purchase/purchaseGoodsIndexForm.jsp"%>
		</fieldset>
	</form>
</div>

<script type="text/javascript">


$(function(){  
	
	$('#contractGoodsForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#contractGoodsForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dgContractGoods.datagrid('clearSelections');
	    		dgContractGoods.datagrid('reload');
	    		$('#purchaseContractGoodsId').val(data.returnId);
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
	    animate:false,
	    multiline:false,
	    required : true,
// 		panelHeight:'auto',
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
	
	
	$('#goodsQuantity').numberbox({
		required:true,
		min:0,
		max:999999999,
		precision:'3',
		onChange:function(newValue,oldValue){
			var goodsQuantity = newValue;
			var purchasePrice = $('#purchasePrice').numberbox('getValue');
		 	$('#amount').numberbox('setValue',(goodsQuantity*purchasePrice).toFixed(2));
		}
	});
	
	$('#purchasePrice').numberbox({
		required:true,
		min:0,
		max:999999999,
		precision:'2',
		onChange:function(newValue,oldValue){
			var goodsQuantity = $('#goodsQuantity').numberbox('getValue');
			var purchasePrice = newValue;
		 	$('#amount').numberbox('setValue',(goodsQuantity*purchasePrice).toFixed(2));
		}
	});
	
	
});

  
</script>
</body>
</html>

