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
	 			<th>销售合同号</th>
				<td colspan="3">
				${receiveCreditReg.woodBillId }
				</td>
			</tr>
			<tr>
				<th width="20%">综合合同号</th>
				<td width="30%">
				${receiveCreditReg.inteContractNo }
				</td>
				<th width="20%">授信类型</th>
				<td width="30%" id="creditType">
				</td>
			</tr>
			<tr>
				<th>开证日期</th>
				<td>
					<fmt:formatDate value="${receiveCreditReg.creditTime }" />
				</td>
				<th>登记日期 </th>
				<td>
				<fmt:formatDate value="${receiveCreditReg.regTime }" />
				</td>
			</tr>
			<tr>
				<th >下游信用证号</th>
				<td>
				<input type="hidden" name="id" value="${receiveCreditReg.id }"/>
					${receiveCreditReg.creditNo }
				</td>
				<th>开证公司</th>
				<td id="creditCompany">
				</td>
			</tr>
			<tr>
				<th>最晚装船期</th>
				<td>
					<fmt:formatDate value="${receiveCreditReg.lastShipTime }" />
				</td>
				<th>有效期 </th>
				<td>
					<fmt:formatDate value="${receiveCreditReg.effictTime }" />
				</td>
			</tr>
			<tr>
				<th>开证银行</th>
				<td id="issuingBank">
				</td>
				<th>收证银行</th>
				<td id="receiveBank">
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${receiveCreditReg.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${receiveCreditReg.createrDept }</td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
$(function(){
if('${receiveCreditReg.creditType }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/sxlx/${receiveCreditReg.creditType }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#creditType').html(data);
		}
	});
}

if('${receiveCreditReg.creditCompany }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${receiveCreditReg.creditCompany }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#creditCompany').html(data);
		}
	});
}

if('${receiveCreditReg.issuingBank }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${receiveCreditReg.issuingBank }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#issuingBank').html(data);
		}
	});
}

if('${receiveCreditReg.receiveBank }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${receiveCreditReg.receiveBank }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#receiveBank').html(data);
		}
	});
}

});
</script>
</body>
</html>