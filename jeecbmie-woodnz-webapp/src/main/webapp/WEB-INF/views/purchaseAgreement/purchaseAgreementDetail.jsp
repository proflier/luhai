<%@page import="com.cbmie.woodNZ.purchaseAgreement.entity.PurchaseAgreement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>
<body>
	<div>
		<form id="mainform"  action="${ctx}/purchase/purchaseAgreement/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '采购协议信息', iconCls: false, refreshable: false" >				
				<fieldset class="fieldsetClass" >
				<legend>采购协议信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>协议号</th>
						<td>
						${purchaseAgreement.agreementNo }
						</td>
						<th>协议性质</th>
						<td>
							${purchaseAgreement.agreementNature }
						</td>
					</tr>
					<tr>
						<th>协议客户</th>
						<td id="customer">
							
						</td>
						<th>协议第三方</th>
						<td id="thirdParty">
							
						</td>
					</tr>
					<tr>
						<th>相关单位</th>
						<td id="company">
							
						</td>
						<th>签订时间</th>
						<td>
							<fmt:formatDate value="${purchaseAgreement.signDate}" />
						</td>
					</tr>
					<tr>
						<th>我司单位</th>
						<td id="ourUnit">
							
						</td>
						<th>签订地点</th>
						<td>
							${purchaseAgreement.addr}
						</td>
					</tr>
					<tr>
						<th>协议期限</th>
						<td colspan="3">
							<fmt:formatDate value="${purchaseAgreement.startDate}" />
						至
							<fmt:formatDate value="${purchaseAgreement.endDate}" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>${purchaseAgreement.createrName }</td>
						<th>登记部门</th>
						<td>${purchaseAgreement.createrDept }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PurchaseAgreement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${purchaseAgreement.id}" />
				<div id="dgAcc"  class="tableClass"></div>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript"> 
	$(function() {	
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    }
		});
		
	});
	
	var urlValue = '${ctx}/accessory/json?filter_EQS_accParentId=' + $('#accParentId').val()
	+ '&filter_EQS_accParentEntity=' + $('#accParentEntity').val();
	var dgAcc;
	$(function() {
	dgAcc = $('#dgAcc')
			.datagrid(
					{
						method : "get",
						fit : false,
						fitColumns : true,
						border : false,
						striped : true,
						singleSelect : true,
						scrollbarSize : 0,
						url : urlValue,
						idField : 'accId',
						columns : [ [
								{
									field : 'accRealName',
									title : '附件名称',
									sortable : true,
									width : 40
								},
								{
									field : 'accAuthor',
									title : '上传人',
									sortable : true,
									width : 10
								},
								{
									field : 'accId',
									title : '操作',
									sortable : true,
									width : 20,
									formatter : function(value, row, index) {
										var str = "";
										str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
												+ value + ");'>下载</a>"
										return str;
									}
								} ] ]
					})
	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${purchaseAgreement.customer }',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#customer').text(data);
		}
	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${purchaseAgreement.thirdParty }',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#thirdParty').text(data);
		}
	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${purchaseAgreement.company }',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#company').text(data);
		}
	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${purchaseAgreement.ourUnit }',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#ourUnit').text(data);
		}
	});
</script>
</body>
</html>

