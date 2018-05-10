<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="subMainform" action="${ctx}/sale/invoiceSignRecordSub/${actionSub}" method="post">
		<input type="hidden" name="id" id="invoiceSignRecordSubid" value="${invoiceSignRecordSub.id}" />
		<input type="hidden" id="invoiceSignRecordId" name="invoiceSignRecordId" value="${invoiceSignRecordSub.invoiceSignRecordId }"/>
	<div id="subMainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '发票信息', iconCls: false, refreshable: false">
		
			<fieldset class="fieldsetClass" >
				<legend>发票信息</legend>
				<table width="98%" class="tableClass">
				 <tr>
					<th width="20%">数量(吨)</th>
					<td width="30%">
						<input name="quantity"  id="quantity"  type="text" value="${invoiceSignRecordSub.quantity}" class="easyui-numberbox" data-options="precision:2,onChange:count_sub"/>				
					</td>
					<th width="20%">单价</th>
					<td width="30%">
						<input name="unitPrice"  id="unitPrice"  type="text" value="${invoiceSignRecordSub.unitPrice}" class="easyui-numberbox" data-options="precision:2,onChange:count_sub"/>				
					</td>
				</tr>
				<tr>
					<th>价税合计(元)</th>
					<td><input name="totalPrice"  id="totalPrice"  type="text" value="${invoiceSignRecordSub.totalPrice}" class="easyui-numberbox" data-options="precision:2"/>				
					</td>
					<th>发票号码</th>
					<td>
						<input name="invoiceNo"  id="invoiceNo"  type="text" value="${invoiceSignRecordSub.invoiceNo}" class="easyui-validatebox" data-options="required:true"/>				
					</td>
				</tr>
				<tr>
					<th>份数</th>
					<td colspan="3">
					<input name="invoiceCopies"  id="invoiceCopies"  type="text" value="${invoiceSignRecordSub.invoiceCopies}" class="easyui-numberbox" data-options="precision:0"/>	
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea  name="remarks" type="text" id="remarks"  class="easyui-validatebox"
							style="overflow:auto;width:80%;height:100%;">${invoiceSignRecordSub.remarks}</textarea>
					</td>
				</tr>
				</table>
			</fieldset>
		</div>	
	</div>
	</form>
	
</div>
<script type="text/javascript">
function count_sub(){
	var unitPrice = $('#unitPrice').numberbox('getValue');
	var quantity = $('#quantity').numberbox('getValue');
	var amount = 0;
	amount = unitPrice*quantity;
	$("#totalPrice").numberbox('setValue',amount);
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
	    		sub_obj.sub_form.panel('close');
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

