<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainformD" action="${ctx}/baseinfo/port/${action}" method="get">
	<table width="98%" class="tableClass">
		<tr>
			<th width="25%">编码</th>
			<td>${woodGk.bm }</td>
		</tr>
		<tr>
			<th width="25%">港口名称</th>
			<td>
			${woodGk.gkm }
			</td>
		</tr>
		<tr>
			<th>国家</th>
			<td id="gj"></td>
		</tr>
		<tr>
			<th>地区</th>
			<td>${woodGk.dq }</td>
		</tr>
		<tr>
			<th>免堆期天数</th>
			<td>${woodGk.freeDays }</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
$.ajax({
	url : '${ctx}/system/countryArea/getName/${woodGk.gj }',
	type : 'get',
	cache : false,
	success : function(data) {
		$('#gj').html(data);
	}
});
</script>
</body>
</html>