<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
	<form id="subMainform" action="${ctx}/logistic/shipmentsSub/${actionSub}" method="post">
		<input type="hidden" name="id" id="shipmentsSubid" value="${shipmentsSettlementSub.id}" />
		<input type="hidden" id="shipmentsSettleId" name="shipmentsSettleId" value="${shipmentsSettlementSub.shipmentsSettleId }"/>
	<div id="subMainDiv">
			<fieldset class="fieldsetClass" >
				<legend>结算信息</legend>
				<table width="98%" class="tableClass">
				<tr>
					<th>船编号</th>
					<td colspan="3"><input name="shipNo"  id="shipNo"  type="text" value="${shipmentsSettlementSub.shipNo}" class="easyui-validatebox" data-options="required:true" readonly="readonly"/>
					<a id="shipListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="sub_obj.toShipList('${tradeCategory}')">船列表</a>				
					</td>
					<%-- <th>船名</th>
					<td><input name="ship"  id="ship"  type="text" value="${shipmentsSettlementSub.ship}" class="easyui-validatebox" data-options="required:true" readonly="readonly"/>
					</td> --%>
				</tr>
				<tr>
					<th width="20%">卸货日期</th>
					<td width="30%">
						<input id="unloadDate" name="unloadDate" type="text" value="<fmt:formatDate value='${shipmentsSettlementSub.unloadDate}' />" 
						class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
					<th width="20%">结算吨数</th>
					<td width="30%">
						<input name="settleQuantity"  id="settleQuantity"  type="text" value="${shipmentsSettlementSub.settleQuantity}" class="easyui-numberbox" data-options="precision:2"/>				
					</td>
				</tr>
				 <tr>
				 	<th width="20%">结算单价</th>
					<td width="30%">
						<input name="unitPrice"  id="unitPrice"  type="text" value="${shipmentsSettlementSub.unitPrice}" class="easyui-numberbox" data-options="precision:2"/>				
					</td>
					<th width="20%">小计金额</th>
					<td width="30%">
						<input name="totalPrice"  id="totalPrice"  type="text" value="${shipmentsSettlementSub.totalPrice}" class="easyui-numberbox" data-options="precision:2"/>				
					</td>
				</tr>
				<tr>
				 	<th width="20%">币种</th>
					<td width="30%">
						<mytag:combobox name="moneyCurrencyCode" value="${shipmentsSettlementSub.moneyCurrencyCode}" type="dict" parameters="currency"/>
					</td>
					<th width="20%">航线</th>
					<td width="30%">
						<input name="route"  id="route"  type="text" value="${shipmentsSettlementSub.route}" class="easyui-validatebox"/>				
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea  name="remarks"  id="remarks"  class="easyui-validatebox"
							style="overflow:auto;width:80%;height:100%;">${shipmentsSettlementSub.remarks}</textarea>
					</td>
				</tr>
				</table>
			</fieldset>
	</div>
	</form>
	
</div>
<script type="text/javascript">
$(function(){
	$('#subMainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		sub_obj.subs_form.panel('close');
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    	}  
	    	sub_obj.reload();
	    }
	});
});
</script>
</body>
</html>

