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
					${mortgageReg.woodBillId }
				</td>
			</tr>
	 		<tr>
				<th width="20%">信用证号</th>
				<td width="30%">
				${mortgageReg.creditNo }
				</td>
				<th width="20%">综合合同号</th>
				<td width="30%">
				${mortgageReg.inteContractNo }
				</td>
			</tr>
			<tr>
				<th>银行名称</th>
				<td id="bankName">
				</td>
				<th>银行编号 </th>
				<td>
				${mortgageReg.bankNo }
				</td>
			</tr>
			<tr>
				<th>授信类型</th>
				<td id="creditType">
				</td>
				<th>押汇金额</th>
				<td>
					${mortgageReg.mortgageMoney }
				</td>
			</tr>
			<tr>
				<th>押汇币种</th>
				<td id="mortgageCurrency">
				</td>
				<th>汇率</th>
				<td>
					${mortgageReg.exchangeRate }
				</td>
			</tr>
			<tr>
				<th>押汇日期</th>
				<td>
					<fmt:formatDate value="${mortgageReg.mortgageTime }" />
				</td>
				<th>是否最后一次押汇</th>
				<td id="lastMortgage">
				</td>
			</tr>
			<tr>
				<th>预计还押汇日期</th>
				<td>
					<fmt:formatDate value="${mortgageReg.payMortgageTime }" />
				</td>
				<th>实际还押汇日期 </th>
				<td>
					<fmt:formatDate value="${mortgageReg.payMortgageTimeReal }" />
				</td>
			</tr>
			<tr>
				<th>押汇利率</th>
				<td>
					${mortgageReg.mortgageRate }
				</td>
				<th>人民币金额 </th>
				<td>
					${mortgageReg.rmbMoney }
				</td>
			</tr>
			
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${mortgageReg.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${mortgageReg.createrDept }</td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">

if('${mortgageReg.bankName }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${mortgageReg.bankName }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#bankName').html(data);
		}
	});
}

if('${mortgageReg.creditType }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/sxlx/${mortgageReg.creditType }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#creditType').html(data);
		}
	});
}


if('${mortgageReg.mortgageCurrency }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/currency/${mortgageReg.mortgageCurrency }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#mortgageCurrency').html(data);
		}
	});
}

if('${mortgageReg.lastMortgage }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/YESNO/${mortgageReg.lastMortgage }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#lastMortgage').html(data);
		}
	});
}

</script>
</body>
</html>