<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="subMainform" action="${ctx}/logistic/transportSub/${actionSub}" method="post">
		<input type="hidden" name="id" id="transportSubid" value="${transportSettlementSub.id}" />
		<input type="hidden" id="transportSettlementId" name="transportSettlementId" value="${transportSettlementSub.transportSettlementId }"/>
	<div id="subMainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '结算信息', iconCls: false, refreshable: false">
		
			<fieldset class="fieldsetClass" >
				<legend>子表信息</legend>
				<table width="98%" class="tableClass">
				<tr>
					<th width="20%">出库吨数</th>
					<td>
						<input name="outStockQuantity"  id="outStockQuantity_sub"  type="text" value="${transportSettlementSub.outStockQuantity}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th width="20%">到货吨数</th>
					<td>
						<input name="arrivalQuantity"  id="arrivalQuantity_sub"  type="text" value="${transportSettlementSub.arrivalQuantity}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th width="20%">结算吨数</th>
					<td>
						<input name="settleQuantity"  id="settleQuantity_sub"  type="text" value="${transportSettlementSub.settleQuantity}" class="easyui-numberbox" data-options="precision:2,onChange:countQuantity_sub"/>
					</td>
					<th width="20%">应付</th>
					<td>
						<input name="shouldPay"  id="shouldPay_sub"  type="text" value="${transportSettlementSub.shouldPay}" class="easyui-numberbox" data-options="precision:2,onChange:countQuantity_sub"/>
					</td>
				</tr>
				<tr>
					<th width="20%">运费单价(含税)</th>
					<td>
						<input name="unitPrice"  id="unitPrice_sub"  type="text" value="${transportSettlementSub.unitPrice}" class="easyui-numberbox" readonly="readonly" data-options="precision:9"/>
					</td>
					<th width="20%">扣磅差费</th>
					<td>
						<input name="differPay"  id="differPay_sub"  type="text" value="${transportSettlementSub.differPay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay_sub"/>
					</td>
				</tr>
				<tr>
					<th width="20%">其他费用</th>
					<td>
						<input name="elsePay"  id="elsePay_sub"  type="text" value="${transportSettlementSub.elsePay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay_sub"/>
					</td>
					<th width="20%">实付</th>
					<td>
						<input name="realPay"  id="realPay_sub"  type="text" value="${transportSettlementSub.realPay}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th>摘要</th>
					<td colspan="3">
						<textarea  name="roundup"  id="roundup_sub"  class="easyui-validatebox"
							style="overflow:auto;width:80%;height:100%;">${transportSettlementSub.roundup}</textarea>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea  name="remarks"  id="remarks_sub"  class="easyui-validatebox"
							style="overflow:auto;width:80%;height:100%;">${transportSettlementSub.remarks}</textarea>
					</td>
				</tr>
				</table>
			</fieldset>
		</div>	
	</div>
	</form>
	
</div>
<script type="text/javascript">
function countQuantity_sub(){
	var settleQuantity = $("#settleQuantity_sub").val()?$("#settleQuantity_sub").val():1;
	var shouldPay_sub = $('#shouldPay_sub').val()?$("#shouldPay_sub").val():0;
	var unitPrice = 0;
	unitPrice = shouldPay_sub/settleQuantity;
	$("#unitPrice_sub").numberbox('setValue',unitPrice);
	countPay_sub();
}
function countPay_sub(){
	var shouldPay = $("#shouldPay_sub").val()?$("#shouldPay_sub").val():0;
	var differPay = $("#differPay_sub").val()?$("#differPay_sub").val():0;
	var elsePay = $("#elsePay_sub").val()?$("#elsePay_sub").val():0;
	var realPay = 0;
	realPay = shouldPay-differPay+elsePay*1;
	$("#realPay_sub").numberbox('setValue',realPay);
}
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
	    	sub_obj.backMain();
	    	sub_obj.reload();
	    }
	});
});
</script>
</body>
</html>

