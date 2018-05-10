<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
	<table width="98%" class="tableClass">
			<tr>
				<th>到单编号</th>
				<td colspan="3">
					${payMortgageReg.woodBillId }
				</td>
			</tr>
			<tr>
				<th width="20%">还汇日期</th>
				<td width="30%">
					<fmt:formatDate value="${payMortgageReg.payMortgageTime }" />
				</td>
				<th width="20%">押汇利息（外币） </th>
				<td width="30%">
					${payMortgageReg.mortgageInterestO }
				</td>
			</tr>
			<tr>
				<th>汇率</th>
				<td>
					${payMortgageReg.exchangeRate }
				</td>
				<th>押汇利息（人民币） </th>
				<td>
					${payMortgageReg.mortgageInterestRMB }
				</td>
			</tr>
			<tr>
				<th>本息合计（人民币）</th>
				<td >
					${payMortgageReg.exchangeRate }
				</td>
				<th>还汇币种</th>
				<td >
					${fns:getDictLabelByCode(payMortgageReg.currency,'currency','')}
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					${payMortgageReg.summary }
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${payMortgageReg.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${payMortgageReg.createrDept }</td>
	</tr>
	<tr>
		<th width="20%" >登记时间</th>
		<td width="30%"><fmt:formatDate value="${ payMortgageReg.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th width="20%" >最后修改时间</th>
		<td width="30%"><fmt:formatDate value="${payMortgageReg.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">

	
</script>
</body>
</html>