<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/credit/payApply/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>开证申请</legend>
<!--移动端截取标志1-->
<table width="98%" class="tableClass">
	<tr>
		<th width="20%">采购合同号</th>
		<td width="30%" >
			${payApply.contractNo }
		</td>
		<th width="20%">开证申请号</th>
		<td width="30%">
			${payApply.payApplyNo }
		</td>
	</tr>
	<tr>
		<th>综合合同号</th>
		<td>
		${payApply.inteContractNo }
		</td>
		<th>开证金额</th>
		<td>
			${payApply.applyMoney }
		</td>
	</tr>
	<tr>
		<th>预计付款时间</th>
		<td >
			<fmt:formatDate value="${payApply.forecastPayTime }" />
		</td>
		<th>币种</th>
		<td id="currency">
		</td>
	</tr>
	<tr>
		<th>溢短装</th>
		<td>
			${payApply.moreOrLess }
		</td>
		<th>汇率</th>
		<td>
			${payApply.exchangeRate }
		</td>
	</tr>
	<tr>
		<th>客户保证金</th>
		<td>
			${payApply.handelRecognizance }
		</td>
		<th>已到帐保证金</th>
		<td>
		${payApply.arrivalRecognizance }
		</td>
	</tr>
	<tr>
		<th>保证金币种</th>
		<td id="recognizanceCurrency">
		</td>
		<th>希望使用银行</th>
		<td id="bankExpects">
		</td>
	</tr>
	<tr>
		<th>收款方式</th>
		<td id="paymenMethod">
		</td>
		<th>授信类型</th>
		<td id="creditType">
		</td>
	</tr>
	<tr>
		<th>使用时间</th>
		<td>
			${payApply.usageTime }天
		</td>
		<th>我司单位</th>
		<td id="ourUnit">
		</td>
	</tr>
	<tr>
		<th>供应商</th>
		<td id="supplier">
		</td>
		<th>进口国别</th>
		<td id="importingCountry">
		</td>
	</tr>
	<tr>
		<th>国内客户</th>
		<td id="internalClient">
		</td>
		<th>经营方式</th>
		<td id="runMode">
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="3">
			${payApply.comment }
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${payApply.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${payApply.createrDept }</td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">

if('${payApply.currency }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/currency/${payApply.currency }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#currency').html(data);
		}
	});
}

if('${payApply.recognizanceCurrency }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/currency/${payApply.recognizanceCurrency }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#recognizanceCurrency').html(data);
		}
	});
}

if('${payApply.paymenMethod }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/skfs/${payApply.paymenMethod }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#paymenMethod').html(data);
		}
	});
}

if('${payApply.bankExpects }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${payApply.bankExpects }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#bankExpects').html(data);
		}
	});
}

if('${payApply.creditType }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/sxlx/${payApply.creditType }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#creditType').html(data);
		}
	});
}

if('${payApply.ourUnit }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${payApply.ourUnit }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#ourUnit').html(data);
		}
	});
}

if('${payApply.supplier }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${payApply.supplier }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#supplier').html(data);
		}
	});
}

$(function () {
	$.ajax({
		url : '${ctx}/system/countryArea/getName/${payApply.importingCountry }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#importingCountry').html(data);
		}
	});
});

if('${payApply.internalClient }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getBaseInfoName/${payApply.internalClient }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#internalClient').html(data);
		}
	});
}

if('${payApply.runMode }'!=''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/htlb/${payApply.runMode }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#runMode').html(data);
		}
	});
}

</script>
</body>
</html>