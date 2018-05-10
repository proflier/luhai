<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<!--移动端截取标志1-->
<table width="98%" class="tableClass">
	<tr>
		<th>提单号</th>
		<td>
		${payTaxes.invoiceNo }
		</td>
		<th>币种(进口合同)</th>
		<td>
		${payTaxes.currency }
		</td>
		<th>提单总额(人民币金额)</th>
		<td>
		<fmt:formatNumber type="number" value="${payTaxes.invoiceMoney }" />
		</td>
	</tr>
	<tr>
		<th>税号</th>
		<td>
		${payTaxes.taxNo }
		</td>
		<th>税率</th>
		<td>
		${payTaxes.taxRate }
		</td>
		<th>税金</th>
		<td>
		<fmt:formatNumber type="number" value="${payTaxes.taxMoney }" />
		</td>
	</tr>
	<tr>
		<th>划税日期</th>
		<td colspan="5">
		<fmt:formatDate value="${payTaxes.rowTaxDate }" />
		</td>
	</tr>
	<tr>
		<th>收款单位</th>
		<td id="receivingUnit">
		</td>
		<th>账号</th>
		<td>
		${payTaxes.account }
		</td>
		<th>汇入行名称</th>
		<td>
		${payTaxes.bank }
		</td>
	</tr>
</table>
<!--移动端截取标志1-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<!--移动端截取标志2-->
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${payTaxes.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${payTaxes.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${payTaxes.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<!--移动端截取标志2-->
</fieldset>
<script type="text/javascript">
$.ajax({
	url : '${ctx}/baseinfo/affiliates/json?filter_EQL_id=${payTaxes.receivingUnit }',
	type : 'get',
	cache : false,
	async : false,
	dataType : 'json',
	success : function(data) {
		$("#receivingUnit").text((data.rows)[0].customerName);
	}
});
</script>
</body>
</html>