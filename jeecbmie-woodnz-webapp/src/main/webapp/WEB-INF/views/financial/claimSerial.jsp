<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<form id="cliam_form"  action="${ctx}/financial/serial/claim" method="post">
				<table width="98%" class="tableClass" >
					<tr>
						<th>合同号</th>
						<td>
							<input type="hidden" name="id" value="${serial.id }" />
							<input  id="contractNo" name="contractNo" type="text" value="${serial.contractNo }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>水单类型</th>
						<td><input id="serialCategory" name="serialCategory" type="text" value="${serial.serialCategory }" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<th>发票号</th>
						<td><input id="invoiceNo" name="invoiceNo" type="text" value="${serial.invoiceNo }" class="easyui-combobox"   /></td>
					</tr>
					<tr>
						<th width="25%">认领人备注</th>
						<td>
							<textarea   name="comments" type="text" value="${serial.comments }" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>
		</form>
	</div>

<script type="text/javascript">

	$(function() {
		$('#cliam_form').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
// 		    	successTip(data,dg,d_claim);
		    	successTipNew(data,dg,d_claim);
			} 
		});
	});
	
	//合同号
	$('#contractNo').combobox({
		method : 'get',
// 		panelHeight : 'auto',
		required : true,
		url : '${ctx}/sale/saleContract/json?filter_EQS_state=生效&filter_EQS_state_ht=运行中',
		valueField : 'id',
		textField : 'contractNo',
		loadFilter : function(data){
			return data.rows;
		},
		onSelect:function(record){
			selectInvoice(record.id);
			$('#invoiceNo').combobox('clear');
		}
	});
	
	
	//资金类别
	$('#serialCategory').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/downMenu/getDictByCode/serialCategory',
		valueField : 'id',
		textField : 'name'
	});
	
	
	
// 	//发票号 初始化显示
// 	$('#invoiceNo').combobox({
// 		method : 'get',
// 		required : true,
// 		url:'${ctx}/logistics/downstreamBill/json?filter_EQS_confirm=1', //确认状态为确认
// 		valueField : 'invoiceNo',
// 		textField : 'invoiceNo',
// 		loadFilter : function(data){
// 			return data.rows;
// 		}
// 	});
	
	function selectInvoice(id){
		$('#invoiceNo').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/system/downMenu/getInvoiceNo/'+id,
			valueField : 'invoiceNo',
			textField : 'invoiceNo',
		});
	}
</script>
</body>
</html>