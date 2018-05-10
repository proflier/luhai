<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseInfo/goodsInfo/${action}" method="get">
	<table width="98%" class="tableClass">
		<tr>
			<th width="25%">级别</th>
			<td>${goods.classes}</td>
		
			<th width="25%">级别名称</th>
			<td>
			${goods.className}
			</td>
		</tr>
		<tr>
			<th width="25%">编码</th>
			<td>
			${goods.code}
			</td>
		
			<th width="25%">名称</th>
			<td>
			${goods.name}
			</td>
		</tr>
		<tr>
			<th width="25%">是否是濒危</th>
			<td>
			${goods.ifDanger}
			</td>
		
			<th width="25%">所属级别</th>
			<td>
			${goods.affiliated}
			</td>
		</tr>
		<tr>
			<th width="25%">换算数</th><td colspan="3" >${goods.hss}</td>
		</tr>
		<tr>
			<th width="25%">描述</th>
			<td>
			${goods.description}
			</td>
			<th width="25%"></th>
			<td>
			</td>
		</tr>
		<tr>
			<th width="25%">登记部门</th>
			<td>
			${goods.dept}
			</td>
			<th width="25%">登记人</th>
			<td>
			${goods.sqr}
			</td>
		</tr>
		<tr>
			<th >备注</th>
			<td colspan="3" style="height:1cm">${goods.remark}</textarea></td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
</script>
</body>
</html>