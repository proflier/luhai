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
				<th  width="20%">上游合同号</th>
				<td  width="30%">
				${proflitLossAccounting.contractNo }
				</td>
				<th  width="20%">材质</th>
				<td  id="material" width="30%">
				</td>
			</tr>
			<tr>
				<th>供应商</th>
				<td id="supplier" >
				</td>
				<th>采购美金单价</th>
				<td>
				${proflitLossAccounting.priceProcurementDollars }
				</td>
			</tr>
			<tr>
				<th>数量</th>
				<td>
				${proflitLossAccounting.volume }
				</td>
				<th>数量单位</th>
				<td>
					${fns:getDictLabelByCode(proflitLossAccounting.units,'sldw','')}
				</td>
				
			</tr>
			<tr>
				<th>金额</th>
				<td>
					${proflitLossAccounting.value }
				</td>
				<th>海运费</th>
				<td>
					${proflitLossAccounting.shippingFee }
				</td>
			</tr>
			<tr>
				<th>装货费</th>
				<td>
					${proflitLossAccounting.loadingCharges }
				</td>
				<th>通知费+修改费+快递费</th>
				<td>
					${proflitLossAccounting.extraFee }
				</td>
			</tr>
			<tr>
				<th>人工费</th>
				<td>
					${proflitLossAccounting.personFee }
				</td>
				<th>港口费</th>
				<td>
					${proflitLossAccounting.portFee }
				</td>
			</tr>
			<tr>
				<th>资金成本</th>
				<td>
					${proflitLossAccounting.capitalCost }
				</td>
				<th>贴现</th>
				<td>
					${proflitLossAccounting.discount }
				</td>
			</tr>
			<tr>
				<th>议付费</th>
				<td>
					${proflitLossAccounting.yeePay }
				</td>
				<th>承兑手续费</th>
				<td>
					${proflitLossAccounting.acceptanceFee }
				</td>
			</tr>
			<tr>
				<th>保险费</th>
				<td>
					${proflitLossAccounting.insurance }
				</td>
				<th>销售美金单价</th>
				<td>
					${proflitLossAccounting.unitSalesDollars }
				</td>
			</tr>
			<tr>
				<th>利润</th>
				<td>
					${proflitLossAccounting.profit }
				</td>
				<th>利润合计</th>
				<td>
					${proflitLossAccounting.totalProfit }
				</td>
			</tr>
			<tr>
				<th>目的港</th>
				<td id="destinationPort" colspan="3">
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${proflitLossAccounting.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${proflitLossAccounting.createrDept }</td>
	</tr>
	<tr>
		<th width="20%" >登记时间</th>
		<td width="30%"><fmt:formatDate value="${ proflitLossAccounting.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th width="20%" >最后修改时间</th>
		<td width="30%"><fmt:formatDate value="${proflitLossAccounting.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">

if('${proflitLossAccounting.material }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/lhUtil/goodsShow?code=${proflitLossAccounting.material }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#material').html(data);
		}
	});
}
if('${proflitLossAccounting.destinationPort }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/baseUtil/getPortName/${proflitLossAccounting.destinationPort }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#destinationPort').html(data);
		}
	});
}

if('${proflitLossAccounting.supplier }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${proflitLossAccounting.supplier }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#supplier').html(data);
		}
	});
}


</script>
</body>
</html>