<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
</head>
<body>
<div>
<fieldset class="fieldsetClass" >
<legend>基本信息</legend>
<table width="98%" class="tableClass" >
	<tr>
		<th width="20%">流水号</th>
		<td width="30%">${serial.serialNumber }</td>
		<th width="20%">水单抬头</th>
		<td width="30%" >
			${mytag:getAffiBaseInfoByCode(serial.serialTitle)}
		</td>
	</tr>
	<tr>
		<th>银行</th>
		<td >
			${mytag:getAffiBankInfoById(serial.bank).bankName}
		</td>
		<th>银行账号</th>
		<td>${serial.bankNO }</td>
	</tr> 
	<tr>
		<th>资金类别</th>
		<td >
			${fns:getDictLabelByCode(serial.fundCategory,'fundCategory','')}
		</td>
		<th>金额</th>
		<td><fmt:formatNumber type="number" value="${serial.money }"/></td>
	</tr>
	<tr>
		<th >收款日期</th>
		<td>
			<fmt:formatDate value="${serial.serialDate }" />
		</td>
		<th>银承到期日期</th>
		<td><fmt:formatDate value="${serial.bankDeadline }" /></td>
	</tr>
	<tr>
		<th>资金来源</th>
		<td colspan="3">${serial.fundSource }</td>
	</tr> 
	
</table>
</fieldset>
<fieldset class="fieldsetClass" >
<legend>认领信息</legend>
<table width="98%" class="tableClass" >
	<tr>
		<th width="20%">合同号</th>
		<td width="30%">
			${serial.contractNo }
		</td>
		<th width="20%">水单类型</th>
		<td width="30%">
			${fns:getDictLabelByCode(serial.serialCategory,'serialCategory','')}
		</td>
	</tr>
	<tr>
		<th>认领人</th>
		<td>${serial.claimPerson }</td>
		<th>认领日期</th>
		<td>
			<fmt:formatDate value="${ serial.claimDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
	<tr>
		<th>发票号</th>
		<td>${serial.invoiceNo }</td>
		<th>认领人备注</th>
		<td>
			${serial.comments }
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass" >
	<legend>系统信息</legend>
	<table width="98%" class="tableClass" >
		<tr>
			<th width="20%">登记人</th>
			<td width="30%">${serial.createrName}</td>
			<th width="20%">登记部门</th>
			<td width="30%">${serial.createrDept }</td>
		</tr>
		<tr>
			<th width="20%" >登记时间</th>
			<td width="30%"><fmt:formatDate value="${ serial.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<th width="20%" >最后修改时间</th>
			<td width="30%"><fmt:formatDate value="${serial.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>
</fieldset>
</div>
<script type="text/javascript">
</script>
</body>
</html>