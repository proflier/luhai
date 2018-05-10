<%@page import="com.cbmie.lh.credit.entity.PayApply"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
</head>
<body>
	<div id="detailLayout" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'east',collapsible:true,split:true,collapsed:true,border: false,title:'关联列表'" style="width: 600px">
			<input id="innerContractNo" type="hidden" value="${payApply.inteContractNo }" />
			<table id="dg_relation" ></table>
			<div id="dlg_relation"></div>
		</div>
		<div onclick="centerDetail()" data-options="region:'center'">
			<div id="payApply_mainDiv" class="" data-options="border:false">
			<div data-options="title: '开证申请', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass">
				<legend>开证申请</legend>
				<!--移动端截取标志1-->
				<table width="98%" class="tableClass">
					<tr>
						<th >开证申请号</th>
						<td >
							${payApply.payApplyNo }
						</td>
						<th>开证类型</th>
						<td>
							${fns:getDictLabelByCode(payApply.creditCategory,'creditCategory','')}
						</td>
					</tr>
					<tr>
						<th width="20%">内部合同号</th>
						<td  width="30%">
						${payApply.inteContractNo }
						</td>
						<th width="20%">采购合同号</th>
						<td width="30%">
							${payApply.contractNo }
						</td>
					</tr>
					<tr>
						<th>开证单价</th>
						<td>
							${payApply.unitPrice }
						</td>
						<th>开证数量</th>
						<td>
							<fmt:formatNumber value="${payApply.quantity }" pattern="##.000#"/> 
						</td>
					</tr>
					<tr>
						<th>开证金额</th>
						<td>
							<fmt:formatNumber value="${payApply.applyMoney }" pattern="##.000#"/> 
						</td>
						<th>币种</th>
						<td >
							${fns:getDictLabelByCode(payApply.currency,'currency','')}
						</td>
					</tr>
					<tr>
						<th>溢短装</th>
						<td >
							<c:if test="${!empty payApply.moreOrLess }">
								${payApply.moreOrLess }%
							</c:if>							
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
						<td>
							${fns:getDictLabelByCode(payApply.recognizanceCurrency,'currency','')}
						</td>
						<th>经营方式</th>
						<td >
							${fns:getDictLabelByCode(payApply.runMode,'runMode','')}
						</td>
					</tr>
					<tr>
						<th>授信类型</th>
						<td>
							${fns:getDictLabelByCode(payApply.creditType,'sxlx','')}
						</td>
						<th>开证单位</th>
						<td id="">
							${mytag:getAffiBaseInfoByCode(payApply.ourUnit)}
						</td>
					</tr>
					<tr>
						<th>有效期天数</th>
						<td>
							${payApply.usageTime }天
						</td>
						<th>收证单位</th>
						<td id="">
							${mytag:getAffiBaseInfoByCode(payApply.receiveUnit)}
						</td>
					</tr>
					<tr>
						<th>供应商</th>
						<td id="">
							${mytag:getAffiBaseInfoByCode(payApply.supplier)}
						</td>
						<th>进口国别</th>
						<td id="">
							${mytag:getCountryArea(payApply.importingCountry).name}
						</td>
					</tr>
					<tr>
						<th>国内客户</th>
						<td id="" >
							${mytag:getAffiBaseInfoByCode(payApply.internalClient)}
						</td>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(payApply.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>希望使用银行</th>
						<td colspan="3">
							${payApply.bankExpects }
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
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ payApply.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${payApply.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
				<jsp:include page="/WEB-INF/views/relationDetail/relationDetail.jsp" >
					<jsp:param value="payApply,saleContract,saleDelivery,outStock,saleInvoice,paymentConfirm" name="disView"/>
				</jsp:include>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<jsp:include page="/WEB-INF/views/accessory/childAccList4Detail.jsp" >
					<jsp:param value="${payApply.id}" name="accParentId"/>
					<jsp:param value="com_cbmie_lh_credit_entity_PayApply" name="accParentEntity"/>
					<jsp:param value="dgAcc_payApply" name="dgAcc"/>
				</jsp:include>
				<div id="dgAcc_payApply"  class="tableClass"></div>
			</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript">

var tabIndex = 0;
$('#payApply_mainDiv').tabs({
    onSelect:function(title,index){
    }
});




</script>
</body>
</html>