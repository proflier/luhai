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
				<th width="20%">对应付款申请</th>
				<td width="30%">
					${payReg.payApplyNo }
				</td>
				<th width="20%">信用证类型</th>
				<td width="30%" id="lcCategory">
				</td>
			</tr>
	 		<tr>
				<th>合同号</th>
				<td>
				${payReg.contractNo }
				</td>
				<th>综合合同号</th>
				<td>
				${payReg.inteContractNo }
				</td>
			</tr>
		
			<tr>
				<th>信用证号</th>
				<td>
				${payReg.creditNo }
				</td>
				<th>银行名称</th>
				<td id="bankName">
				</td>
			</tr>
			<tr>
				<th>我司单位</th>
				<td id="ourUnit">
				</td>
				<th>收款方式</th>
				<td id="paymenMethod">
				</td>
			</tr>
			<tr>
				<th>授信类型</th>
				<td id="creditType">
				</td>
				<th>开证金额</th>
				<td>
					${payReg.creditMoney }
				</td>
			</tr>
			<tr>
				<th>开证币种</th>
				<td id="creditCurrency">
				</td>
				<th>开证时间</th>
				<td>
					<fmt:formatDate value="${payReg.regTime }" />
				</td>
			</tr>
			<tr>
				<th>最晚装船期</th>
				<td>
					<fmt:formatDate value="${payReg.latestShipTime }" />
				</td>
				<th>信用证有效期</th>
				<td>
					<fmt:formatDate value="${payReg.regValidity }" />
				</td>
			</tr>
			<tr>
				<th>通知行</th>
				<td colspan="3" id="noticeBank">
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					${payReg.summary }
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>开证费信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >付费时间</th>
		<td width="30%">
			<fmt:formatDate value="${payReg.payTime }" />
		</td>
		<th width="20%" >开证费率</th>
		<td  width="30%">
			${payReg.payApplyRate }
		</td>
	</tr>
	<tr>
		<th  >外币金额</th>
		<td>
			${payReg.moneyOut }			
		</td>
		<th  >人民币金额</th>
		<td>
			${payReg.moneyRMB }
		</td>
	</tr>
	<tr>
		<th  >开征费币种</th>
		<td id="feeCurrency" colspan="3">
		</td>
		</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
$(function(){ 
	if('${payReg.lcCategory }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/lcCategory/${payReg.lcCategory } ' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#lcCategory').html(data);
			}
		});
	}
	if('${payReg.bankName }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${payReg.bankName }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#bankName').html(data);
			}
		});
	}

	if('${payReg.ourUnit }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${payReg.ourUnit }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#ourUnit').html(data);
			}
		});
	}

	if('${payReg.paymenMethod } '!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/skfs/${payReg.paymenMethod } ' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#paymenMethod').html(data);
			}
		});
	}
	
	if('${payReg.creditType }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/sxlx/${payReg.creditType }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#creditType').html(data);
			}
		});
	}

	
	if('${payReg.feeCurrency }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/currency/${payReg.feeCurrency }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#feeCurrency').html(data);
			}
		});
	}
	
	if('${payReg.creditCurrency }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/currency/${payReg.creditCurrency }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#creditCurrency').html(data);
			}
		});
	}


	if('${payReg.noticeBank }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${payReg.noticeBank }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#noticeBank').html(data);
			}
		});
	}
	
	$('#payApplyRate').numberbox({
		required:true,
		precision:4,
		formatter:function(value){
			return value;
		}
	});
});
</script>
</body>
</html>