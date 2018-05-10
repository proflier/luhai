<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
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
		<td width="30%" id="serialTitle"></td>
	</tr>
	<tr>
		<th>银行</th>
		<td id="bank"></td>
		<th>银行账号</th>
		<td>${serial.bankNO }</td>
	</tr> 
	<tr>
		<th>资金类别</th>
		<td id="fundCategory"></td>
		<th>金额</th>
		<td><fmt:formatNumber type="number" value="${serial.money }"/></td>
	</tr>
	<tr>
		<th width="25%">日期</th>
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
	<legend>系统信息</legend>
	<table width="98%" class="tableClass" >
		<tr>
			<th width="20%">登记人</th>
			<td width="30%">${serial.createrName}</td>
			<th width="20%">登记部门</th>
			<td width="30%">${serial.createrDept }</td>
		</tr>
	</table>
</fieldset>
</div>
<script type="text/javascript">

if('${serial.serialTitle }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${serial.serialTitle }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#serialTitle').text(data);
		}
	});
}

if('${serial.bank }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBankInfoName/${serial.bank }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#bank').text(data);
		}
	});
}



if('${serial.fundCategory }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/fundCategory/${serial.fundCategory }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#fundCategory').html(data);
		}
	});
}


</script>
</body>
</html>