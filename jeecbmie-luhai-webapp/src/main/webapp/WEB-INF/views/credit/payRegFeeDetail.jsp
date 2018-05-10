<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
</head>
<body>
<form id="mainform" action="" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
	 		<tr>
				<th >开证申请号</th>
				<td colspan="3">
					${payReg.payApplyNo }
				</td>
			</tr>
	 		<tr>
				<th width="20%">内部合同号</th>
				<td  width="30%">
				${payReg.inteContractNo }
				</td>
				<th width="20%">信用证类型</th>
				<td width="30%" >
					${fns:getDictLabelByCode(payReg.lcCategory,'lcCategory','')}
				</td>
			</tr>
		
			<tr>
				<th>采购合同号</th>
				<td>
				${payReg.contractNo }
				</td>
				<th>银行名称</th>
				<td id="">
					${mytag:getAffiBaseInfoByCode(payReg.bankName)}
				</td>
			</tr>
			<tr>
				<th>开证单位</th>
				<td id="">
					${mytag:getAffiBaseInfoByCode(payReg.ourUnit)}
				</td>
				<th>付款方式</th>
				<td >
					${fns:getDictLabelByCode(payReg.paymenMethod,'skfs','')}
				</td>
			</tr>
			<tr>
				<th>授信类型</th>
				<td >
					${fns:getDictLabelByCode(payReg.creditType,'sxlx','')}
				</td>
				<th>开证金额</th>
				<td>
					${payReg.creditMoney }
				</td>
			</tr>
			<tr>
				<th>开证币种</th>
				<td >
					${fns:getDictLabelByCode(payReg.creditCurrency,'currency','')}
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
		<td colspan="3">
			${fns:getDictLabelByCode(payReg.feeCurrency,'currency','')}
		</td>
		</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
</script>
</body>
</html>