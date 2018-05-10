<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table width="98%" class="tableClass" >
		<tr>
			<th><font color="red">*</font>代理协议号</th>
			<td>
			<input type="hidden" id="agencyAgreementIdbase" name="agencyAgreementId" value="${agencyAgreement.id }" />
			<%-- <input type="text" readonly="true"  style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;" value="${agencyAgreement.agencyAgreementNo }"/> --%>
			${agencyAgreement.agencyAgreementNo }
			</td>
			<th><font color="red">*</font>委托方</th>
			<td>
			<%-- <input type="text" readonly="true"  style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;" value="${agencyAgreement.client }" /> --%>
			${agencyAgreement.client }
			</td>
			<th><font color="red">*</font>代理费方式</th>
			<td>
			<%-- <input type="text" readonly="true"  style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;" value="${agencyAgreement.agencyFeeWay }" /> --%>
			${agencyAgreement.agencyFeeWay }
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>手续费基数</th>
			<td>
			${agencyAgreement.commissionBase }
			</td>
			<th><font color="red">*</font>每美元多少RMB</th>
			<td>
			${agencyAgreement.everyDollarToRMB   }
			</td>
			<th>代理费率%</th>
			<td>
			${agencyAgreement.agencyFee   }
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>代理费收入</th>
			<td>
			${agencyAgreement.agencyFeeIncome }
			</td>
			<th><font color="red">*</font>国内保证金比例%</th>
			<td>
			${agencyAgreement.domesticMarginRatio }
			</td>
			<th>币种</th>
			<td>
			${agencyAgreement.currency }
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>对人民币汇率</th>
			<td>
			${agencyAgreement.rmbRate }
			</td>
			<th><font color="red">*</font>对美元汇率</th>
			<td  colspan="3">
			${agencyAgreement.dollarRate }
			</td>
		</tr>
	</table>
<script type="text/javascript">
	$("#agencyAgreementId").val($("#agencyAgreementId").val());
	$("#relativePerson").val('${agencyAgreement.salesman }');
	$('#currency').combobox({value:'${agencyAgreement.currency }'});
	function initCommodity(){
		initCommodityForm(${gcJson });
	}
</script>