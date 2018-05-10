<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '进口合同信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass"  id="agencyConfigInfo" >
				<%@ include file="/WEB-INF/views/importContract/agencyConfigInfo.jsp"%>
			</fieldset>
			<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<!--移动端截取标志2-->
				<table width="98%" class="tableClass" >
					<tr>
						<th>合同号</th>
						<td>${importContract.contractNO }
						</td>
						<th width="25%">合同状态:</th>
						<td>
							${importContract.state }
						</td>
					</tr>
					<tr>
						<th>业务员</th>
						<td>
							${importContract.relativePerson }
						</td>
						<th>签署日期</th>
						<td>
							<fmt:formatDate value="${importContract.signatureDate }" />
						</td>
					</tr>
					<tr>
						<th>币种</th>
						<td>
							${importContract.currency }
						</td>
						<th>原币金额</th>
						<td>
							<%-- <input name="originalCurrency" type="text" value="${importContract.originalCurrency}"   readonly="readonly" class="easyui-numberbox" 
						readonly="true" data-options="min:0,precision:2,groupSeparator:',',required:true"/> --%>
						${importContract.originalCurrency}
						</td>
					</tr>
					<tr>
						<th>供应商</th>
						<td colspan="3" width="25%">
							${importContract.supplier }
						</td>
					</tr>
				</table>
				<!--移动端截取标志2-->
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>合同条款</legend>
				<!--移动端截取标志3-->
				<table width="98%" class="tableClass">
					<tr>
						<th>价格条款</th>
						<td>
							${importContract.pricingTerm }
						</td>
						<th>最晚开立信用证日期</th>
						<td>
							<fmt:formatDate value="${importContract.openCreditDate }"/>
						</td>
					</tr>
					<tr>
						<th>国际运费USD</th>
						<td>
							${importContract.intFare }
						</td>
						<th>国际保费USD</th>
						<td>
						${importContract.intSA }
						</td>
					</tr>
					<tr>
						<th>保险系数</th>
						<td>
							${importContract.safetyFactor }
						</td>
						<th>保险费率</th>
						<td>
							${importContract.insuranceRate }
						</td>
					</tr>
					<tr>
						<th>装运港</th>
						<td>
							${importContract.transportPort }
						</td>
						<th>装运港描述</th>
						<td>
							${importContract.transportPortInfo }
						</td>
					</tr>
					<tr>
						<th>目的港</th>
						<td>
							${importContract.destinationPort }
						</td>
						<th>目的港描述</th>
						<td>
							${importContract.destinationPortInfo }
						</td>
					</tr>
					<tr>
						<th>运输方式</th>
						<td>
							${importContract.transportMode }
						</td>
						<th>装运期限</th>
						<td>
							<fmt:formatDate value="${importContract.shipmentTime }" />
						</td>
					</tr>
					<tr>
						<th>溢短装率%</th>
						<td>
							${importContract.moreOrLessRate }%
						</td>
						<th>签约地</th>
						<td id="signPosition">
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
						<th>登记人</th>
						<td>${sessionScope.user.name}</td>
						<th>登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				<!--移动端截取标志4-->
				</fieldset>
		</div>
		<div data-options="title: '商品明细', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/importContract/commodityInfo.jsp"%>
		</div>
	</div>
</div>

<script type="text/javascript">
$.ajax({
	url : '${ctx}/system/countryArea/getName/' + ${importContract.signPosition },
	type : 'get',
	cache : false,
	success : function(data) {
		$('#signPosition').text(data);
	}
});
</script>
</body>
</html>