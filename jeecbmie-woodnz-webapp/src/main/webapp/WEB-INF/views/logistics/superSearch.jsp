<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 -->
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<form id="superSearch" action="">
		<table width="98%" class="tableClass">
			 <tr>
				<th width="20%">提单号</th>
				<td width="30%">
					<input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
				</td>
				<th width="20%">销售合同号</th>
				<td width="30%">
					 <input type="text" name="filter_LIKES_saleContarteNo" class="easyui-validatebox" data-options="width:150,prompt: '销售合同号'"/>
				</td>
			</tr>
			<tr>
				<th>航次</th>
				<td>
					<input type="text" name="filter_LIKES_voyage" class="easyui-validatebox" data-options="width:150,prompt: '航次'"/>
				</td>
				<th>上游发票号</th>
				<td>
					<input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '上游发票号'"/>
				</td>
			</tr>
			<tr>
				<th>下游发票号</th>
				<td>
					<input type="text" name="filter_LIKES_downInvoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '下游发票号'"/>
				</td>
				<th>供货商</th>
				<td>
					<input type="text" id="supplier_search" name="filter_LIKES_supplier" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>目的港</th>
				<td>
					<input type="text" id="portName_search" name="filter_LIKES_portName" class="easyui-validatebox" />
				</td>
				<th>币种</th>
				<td>
					<input type="text" id="currency_search" name="filter_LIKES_currency" class="easyui-validatebox" />
				</td>
			</tr>
			
			
		</table>
</form>
<script type="text/javascript">
$('#supplier_search').combobox({
	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=2',
	valueField : 'id',
	textField : 'customerName',
	prompt: '供货商'
});

//港口列表
$('#portName_search').combobox({
	method: "get",
	panelHeight:'auto',
	url:'${ctx}/system/downMenu/jsonGK', 
    valueField:'id',
    textField:'gkm', 
    prompt: '目的港'
});

//币种列表
$('#currency_search').combobox({
	panelHeight:'auto',
	url:'${ctx}/system/downMenu/getDictByCode/currency',
    valueField:'id',
    textField:'name', 
    prompt: '币种'
});


</script>
</body>
</html>