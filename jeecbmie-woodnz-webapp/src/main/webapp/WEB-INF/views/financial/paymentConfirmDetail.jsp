<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="act" tagdir="/WEB-INF/tags/act" %>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/financial/paymentConfirm/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '付款确认单', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >付款合同号</th>
						<td colspan="3">
							${paymentConfirm.contractNo }
						</td>
					</tr>
					<tr>
						<th width="20%" >付款单号</th>
						<td width="30%">
							${paymentConfirm.confirmNo }
						</td>
						<th  width="20%">确认日期</th>
						<td width="30%">
								<fmt:formatDate value="${paymentConfirm.confirmDate }" />
						</td>
					</tr>
					<tr>
						<th width="20%" >收款单位名称</th>
						<td width="30%">
							${paymentConfirm.receiveUnitName }
						</td>
						<th  width="20%">汇入地点</th>
						<td width="30%">
							${paymentConfirm.remitArea }
						</td>
					</tr>
					<tr>
						<th width="20%" >汇入行名称</th>
						<td width="30%">
							${paymentConfirm.remitBank }
						</td>
						<th  width="20%">账号</th>
						<td width="30%">
							${paymentConfirm.remitAccount }
						</td>
					</tr>
					<tr>
						<th width="20%" >付款金额(大写)</th>
						<td width="30%">
							${paymentConfirm.payMoneyDa }
						</td>
						<th  width="20%">小写</th>
						<td width="30%">
							${paymentConfirm.payMoneyXiao }
						</td>
					</tr>
					<tr>
						<th width="20%" >品名</th>
						<td width="30%">
							${paymentConfirm.goodsName }
						</td>
						<th  width="20%">数量</th>
						<td width="30%">
							${paymentConfirm.goodsNum }
						</td>
					</tr>
					<tr>
						<th width="20%" >付款方式</th>
						<td width="30%" id="payType">
						</td>
						<th  width="20%">付款内容</th>
						<td width="30%" id="payContent">
						</td>
					</tr>
					<tr>
						<th >备注</th>
						<td colspan="3">
							${paymentConfirm.remarks }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >经办人</th>
						<td>${paymentConfirm.createrName}</td>
						<th  >经办部门</th>
						<td>${paymentConfirm.createrDept}</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		
		
		</form>
		
	</div>
<script type="text/javascript">

	$(function() {
		//付款方式
		
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/paymentMethod/${paymentConfirm.payType }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#payType').html(data);
			}
		});
		
		//付款内容
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/paymentContent/${paymentConfirm.payContent }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#payContent').html(data);
			}
		});
	});
</script>

</body>
</html>