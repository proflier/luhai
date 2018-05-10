<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body >
	<div id="printMmainDiv" style="" align="center">
			<h1 align="center" style="font-size: 35px;font-weight: bold;letter-spacing: 5px;">发票签收单</h1>
			<div style="font-size: 20px;width: 90%;margin-top: 30px;margin-bottom: 30px;" align="left">
				<span>客户名称:</span> ${invoiceSignRecord.customer}
				<span style="padding-left: 20px;">寄票日期:</span> <fmt:formatDate value="${invoiceSignRecord.mailDate }" />
			</div>
			<table id="dgInvoiceSignRecords" style="font-size: 20px;width: 850px;">
			</table>
			<div style="width:90%;font-size: 20px;margin-top: 25px;line-height: 35px;" align="left">
				<span>敬请查收，核对无误后请确认盖章回传,传真号0755-83885266</span> 
			</div>
	</div>	

<script type="text/javascript">
/****/
$(function(){
	 $('#dgInvoiceSignRecords').datagrid({
			method: "get",
			url: '${ctx}/sale/invoiceSignRecordSub/list/${invoiceSignRecord.id}',
			fit : false,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			striped:true,
			idField : 'id',
			rownumbers:true,
			extEditing:false,
			showFooter:true,
		    columns:[[    
				{field:'quantity',title:'数量(吨)',width:120},
				{field:'unitPrice',title:'单价',width:140},
				{field:'totalPrice',title:'价税合计(元)',width:140},
				{field:'invoiceNo',title:'发票号码',width:140},
				{field:'invoiceCopies',title:'发票份数',width:140},
				{field:'remarks',title:'备注',width:170}
		    ]],
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false
		});
});
</script>
</body>
</html>