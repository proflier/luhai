<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/financial/expense/${action}" method="get">
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '费用支付信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<!--移动端截取标志1-->
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">付款申请号</th>
						<td width="26%">${expense.expenseId }
						</td>
						<th width="20%">业务员</th>
						<td>
							${expense.expenseClerk }
						</td>
					</tr>
					<tr>
						<th>币种</th>
						<td>
							${expense.currency }
						</td>
						<th>支付方式</th>
						<td>
							${expense.payModel }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>签署日期</th>
						<td colspan="3">
							<fmt:formatDate value="${expense.applyPayDate }" />
						</td>
					</tr>
				</table>
				<!--移动端截取标志1-->
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>收款方信息</legend>
				<!--移动端截取标志2-->
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">收款方信息</th>
						<td id="payeeInformation" width="26%">
						</td>
						<th width="20%">英文信息</th>
						<td >
							${expense.payeeEnName }
						</td>
					</tr>
					<tr>	
						<th>对方银行</th>
						<td id="payeeBank" >
						</td>
						<th>对方账号</th>
						<td >
							${expense.payeeBankNo }
						</td>
					</tr>
					
				</table>
				<!--移动端截取标志2-->
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>金额信息</legend>
				<!--移动端截取标志3-->
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">金额</th>
						<td width="26%">
							${expense.originalCurrency }
						</td>
						<th width="20%">财务实付日期</th>
						<td >
							<fmt:formatDate value='${expense.payDate }' />
						</td>
					</tr>
					<tr>	
						<th>实付金额</th>
						<td >
							${expense.payCurrency }
						</td>
						<th>未付金额</th>
						<td >
							${expense.oweCurrency }
						</td>
					</tr>
				</table>
				<!--移动端截取标志3-->
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<!--移动端截取标志4-->
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">登记人</th>
						<td>${sessionScope.user.name}</td>
						<th width="20%">登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				<!--移动端截取标志4-->
				</fieldset>
		</div>
		<div data-options="title: '付款明细', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/financial/paymentInfo.jsp"%>
		</div>
		</div>
	</form>
</div>

<script type="text/javascript">
//银行信息
$.ajax({
	url : '${ctx}/baseinfo/affiliates/getAffiBankInfoByNO/' + ${expense.payeeBank },
	type : 'get',
	cache : false,
	success : function(data) {
		$('#payeeBank').text(data);
	}
});



//收款方信息
$.ajax({
	url : '${ctx}/baseinfo/affiliates/typeAjax/' + ${expense.payeeInformation },
	type : 'get',
	cache : false,
	success : function(data) {
		$('#payeeInformation').text(data);
	}
});




</script>
</body>
</html>