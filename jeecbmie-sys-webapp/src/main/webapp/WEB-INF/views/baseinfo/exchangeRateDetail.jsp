<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/exchangerate/${action}" method="get">
	<table width="98%" class="tableClass">
		<tr>
			<th width="35%"><font style="vertical-align:middle;color:red" ></font>币种</th>
			<td>
			<input type="hidden" name="id" value="${exchangeRate.id }" />
			${exchangeRate.currency }
			</td>
		</tr> 
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>年</th>
			<td>${exchangeRate.year }</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>月</th>
			<td>
			${exchangeRate.month}</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>对美元汇率</th>
			<td>${exchangeRate.rateUS }</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>对欧元汇率</th>
			<td>${exchangeRate.rateUN }</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>对新西兰元汇率</th>
			<td>${exchangeRate.rateNZ }</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>${exchangeRate.createrName }</td>
		</tr>
		<tr>
			<th>登记部门</th>
			<td>${exchangeRate.createrDept }</td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td>
				<fmt:formatDate value="${exchangeRate.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">

</script>
</body>
</html>