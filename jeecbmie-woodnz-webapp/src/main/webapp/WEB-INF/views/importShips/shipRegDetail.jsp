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
	<legend>船舶登记信息</legend>
	<table width="98%" class="tableClass">
			<tr>
				<th width="20%">船编号</th>
				<td width="30%">
					${shipReg.shipNo }
				</td>
				<th width="20%">船名</th>
				<td width="30%">
					${shipReg.shipName }
				</td>
			</tr>
			<tr>
				<th>航次</th>
				<td>
					${shipReg.voyage }
				</td>
				<th>装船时间</th>
				<td>
					<fmt:formatDate value="${shipReg.shipLoadTime }" />
				</td>
			</tr>
			<tr>
				<th>开船时间</th>
				<td>
					<fmt:formatDate value="${shipReg.sailingTime }" />
				</td>
				<th>运输单位</th>
				<td id="transportPart">
				</td>
			</tr>
			<tr>
				<th>最后更新日期</th>
				<td>
					<fmt:formatDate value="${shipReg.lastUpdateTime }" />
				</td>
				<th>登记人</th>
				<td>
					${shipReg.createrName }
				</td>
			</tr>
			<tr>
				<th>采购合同号</th>
				<td colspan="3">
					${shipReg.importContractNo }
				</td>
			</tr>
		</table>
	</fieldset>
</form>
<script type="text/javascript">
$(function () {
	if('${shipReg.transportPart }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${shipReg.transportPart }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#transportPart').html(data);
			}
		});
	}
});
</script>
</body>
</html>