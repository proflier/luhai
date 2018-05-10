<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
	<form id="settlementGoodsForm" action="${ctx}/sale/saleSettlementGoods/${actionGoods}" method="post">
		<input type="hidden" id="saleSettlementGoodsId" name="id" value="${saleSettlementGoods.id}" />
		<input type="hidden" name="saleSettlementId" value="${saleSettlementGoods.saleSettlementId}" />
		<input id="ship" name="ship" type="hidden" value="${saleSettlementGoods.ship}"/>
		<fieldset class="fieldsetClass" >
			<legend>结算信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%" >品名</th>
					<td colspan="3" >
					<input id="goodsName" name="goodsName" type="text" value="${saleSettlementGoods.goodsName}" class="easyui-combobox easyui-validatebox"/>
					</td>
				</tr>
				<%-- <tr>
					<th width="20%" >发货日期</th>
					<td width="30%" >
						<input id="sendDate" name="sendDate" type="text" value="<fmt:formatDate value="${saleSettlement.sendDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
					<th width="20%" >收货日期</th>
					<td width="30%" >
						<input id="receiveDate" name="receiveDate" type="text" value="<fmt:formatDate value="${saleSettlement.receiveDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>			
				</tr> --%>
				<tr>
					<th width="20%" >发货数量</th>
					<td width="30%" >
						<input id="sendQuantity" name="sendQuantity"  type="text" value="${saleSettlementGoods.sendQuantity}" class="easyui-numberbox" data-options="precision:'3'"/>
					</td>
					<th width="20%" >收货数量</th>
					<td width="30%" >
						<input id="receiveQuantity" name="receiveQuantity"  type="text" value="${saleSettlementGoods.receiveQuantity}" class="easyui-numberbox" data-options="precision:'3'"/>
					</td>			
				</tr>
				<tr>
					<th width="20%" >结算数量</th>
					<td width="30%" >
						<input id="settlementQuantity" name="settlementQuantity"  type="text" value="${saleSettlementGoods.settlementQuantity}" class="easyui-numberbox" data-options="required:true,precision:'2',onChange:countPrice_sub"/>
					</td>
					<th width="20%" >结算金额</th>
					<td width="30%" >
						<input id="settlementTotalPrice" name="settlementTotalPrice"  type="text" value="${saleSettlementGoods.settlementTotalPrice}" class="easyui-numberbox" data-options="required:true,precision:'2',onChange:countPrice_sub"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >基准单价</th>
					<td width="30%" >
						<input id="basePrice" name="basePrice"  type="text" value="${saleSettlementGoods.basePrice}" class="easyui-numberbox" data-options="precision:'2'"/>
					</td>
					<th width="20%" >结算单价</th>
					<td width="30%" >
						<input id="settlementPrice" name="settlementPrice"  type="text" value="${saleSettlementGoods.settlementPrice}" class="easyui-numberbox" data-options="required:true,precision:'2'"/>
					</td>			
				</tr>
				<tr>
					<th>扣减金额</th>
					<td><input id="deductionPrice" name="deductionPrice"  type="text" value="${saleSettlementGoods.deductionPrice}" class="easyui-numberbox" data-options="precision:'2',onChange:receivablePrice_count"/></td>		
					<th>已收金额</th>
					<td><input id="receivedPrice" name="receivedPrice"  type="text" value="${saleSettlementGoods.receivedPrice}"  class="easyui-numberbox" data-options="precision:'2',onChange:receivablePrice_count"/></td>
				</tr>
				<tr>
					<th>本次应收余额</th>
					<td colspan="3">
						<input id="receivablePrice" name="receivablePrice"  type="text" value="${saleSettlementGoods.receivablePrice}" class="easyui-numberbox" data-options="required:true,precision:'2'"/>
					</td>		
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea name="remark" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]'],value:'${saleSettlementGoods.remark}'"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<script type="text/javascript">
function countPrice_sub(){
	var settlementTotalPrice = $("#settlementTotalPrice").val()?$("#settlementTotalPrice").val():0;
	var settlementQuantity = ($("#settlementQuantity").val()==null || $("#settlementQuantity").val()=="")?0:$("#settlementQuantity").val();
	if(settlementQuantity==0){
		$("#settlementPrice").numberbox('setValue',0);
	}else{
		var settlementPrice = 0;
		settlementPrice = (settlementTotalPrice/settlementQuantity).toFixed(2);
		$("#settlementPrice").numberbox('setValue',settlementPrice);
	}
	receivablePrice_count();
}
function receivablePrice_count(){
	var settlementTotalPrice = $("#settlementTotalPrice").val()?$("#settlementTotalPrice").val():0;
	var deductionPrice = $("#deductionPrice").val()?$("#deductionPrice").val():0;
	var receivedPrice = $("#receivedPrice").val()?$("#receivedPrice").val():0;
	var receivablePrice = 0;
	receivablePrice = settlementTotalPrice-deductionPrice-receivedPrice;
	$("#receivablePrice").numberbox('setValue',receivablePrice);
}
$(function(){
	$('#settlementGoodsForm').form({
	    onSubmit: function(){
	    	var isValid = $('#settlementGoodsForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		sale_settlement_goods.goods_list.datagrid('clearSelections');
	    		sale_settlement_goods.goods_list.datagrid('reload');
	    		$('#saleSettlementGoodsId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	});
	//商品类别
	/*  $('#goodsCategory').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/baseInfo/lhUtil/goodsTypeItems',
	    valueField:'typeCode',
	    textField:'typeName', 
	    required : true,
	    onSelect: function(data){
	    	$('#goodsName').combobox({
	     		panelHeight:'auto',
	    		method: "get",
	    		url:'${ctx}/baseInfo/lhUtil/goodsItemsByCode?code='+data.typeCode,
	    	    valueField:'infoCode',
	    	    textField:'infoName', 
	    	    required : true,
	    	}).combobox('clear');
	    }
	});	
	
	var typeCode = $('#goodsCategory').combobox('getValue');
	$('#goodsName').combobox({
		method: "get",
		url: typeCode ? '${ctx}/baseInfo/lhUtil/goodsItemsByCode?code='+typeCode : '',
	    valueField:'infoCode',
	    textField:'infoName', 
	    required : true
	});  */
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
});
</script>
</body>
</html>

