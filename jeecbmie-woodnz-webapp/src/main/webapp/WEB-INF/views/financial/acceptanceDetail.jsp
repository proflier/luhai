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
		${acceptance.invoiceNo }
		</td>
		<th>信用证号</th>
		<td>
		${acceptance.creditNo }
		</td>
		<th>银行名称</th>
		<td>
		${acceptance.bank }
		</td>
	</tr>
	<tr>
		<th>付款期限</th>
		<td>
		${acceptance.prompt }
		</td>
		<th>押汇金额</th>
		<td>
		<fmt:formatNumber type="number" value="${acceptance.documentaryBillsMoney }" />
		</td>
		<th>押汇币种</th>
		<td>
		${acceptance.documentaryBillsCurrency }
		</td>
	</tr>
	<tr>
		<th>汇率</th>
		<td>
		${acceptance.rate }
		</td>
		<th>押汇日期</th>
		<td>
		<fmt:formatDate value="${acceptance.documentaryBillsDate }" />
		<th>是否最后一次押汇</th>
		<td>
		${acceptance.documentaryBillsLast eq 1 ? '是' : '否' }
		</td>
	</tr>
	<tr>
		<th>预计还押汇日期</th>
		<td>
		<fmt:formatDate value="${acceptance.documentaryBillsExpectBackTime }" />
		</td>
		<th>实际还押汇日期</th>
		<td>
		<fmt:formatDate value="${acceptance.documentaryBillsRealityBackTime }" />
		</td>
		<th>押汇利率</th>
		<td>
		${acceptance.documentaryBillsRate }
		</td>
	</tr>
	<tr>
		<th>人民币金额</th>
		<td colspan="5">
		<fmt:formatNumber type="number" value="${acceptance.rmb }" />
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="5">
		${acceptance.comment }
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
		<fmt:formatDate value="${acceptance.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${acceptance.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${acceptance.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<!--移动端截取标志2-->
</fieldset>
<script type="text/javascript">
</script>
</body>
</html>