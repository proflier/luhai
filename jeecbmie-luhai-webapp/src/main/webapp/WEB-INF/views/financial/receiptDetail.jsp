<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
	<div>
			<input type="hidden" id="id" name="id" value="${receipt.id }" />
				<fieldset class="fieldsetClass" >
				<legend>预收票信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th  width="20%">预收票编号</th>
						<td  width="30%">
							${receipt.receiptNo }
						</td>
						<th  width="20%">批次船名</th>
						<td  width="30%">
							${receipt.shipName }
						</td>
					</tr>
					<tr>
						<th >供应商</th>
						<td >
							${mytag:getAffiBaseInfoByCode(receipt.supply)}
						</td>
						<th >所属月份</th>
						<td >
							<fmt:formatDate value="${receipt.mouth }"/>
						</td>
					</tr>
					<tr>
						<th >开票期间</th>
						<td >
							${receipt.receiptPeriod }
						</td>
						<th >发票号</th>
						<td >
							${receipt.invoiceNo }
						</td>
					</tr>
					<tr>
						<th >信用证号</th>
						<td >
							${receipt.creditNo }
						</td>
						<th >煤种</th>
						<td >
							${mytag:getGoodsTypeByCode(receipt.coalType).typeName}
						</td>
					</tr>
					<tr>
						<th >目的港</th>
						<td >
							
							${mytag:getGKMByCode(receipt.destPort)}
						</td>
						<th >入库数量</th>
						<td >
							${receipt.inStockQuantity }
						</td>
					</tr>
					<tr>
						<th >结算数量</th>
						<td >
							${receipt.settleQuantity }
						</td>
						<th >结算单价</th>
						<td >
							${receipt.settleUnitPrice }
						</td>
					</tr>
					<tr>
						<th >装卸港结算</th>
						<td >
							${fns:getDictLabelByCode(receipt.settlePort,'settlePort','')}
						</td>
						<th >合同热值</th>
						<td >
							${receipt.contractCalorific }
						</td>
					</tr>
					<tr>
						<th >结算热值</th>
						<td >
							${receipt.settleCalorific }
						</td>
						<th >结算方式</th>
						<td >
							${fns:getDictLabelByCode(receipt.settleMode,'jgtk','')}
						</td>
					</tr>
					<tr>
						<th >金额USD</th>
						<td >
							${receipt.amountUSD }
						</td>
						<th >开证日期</th>
						<td >
							<fmt:formatDate value="${receipt.creditDate }"/>
						</td>
					</tr>
					<tr>
						<th >到单日</th>
						<td >
							<fmt:formatDate value="${receipt.billDate }"/>
						</td>
						<th >汇率</th>
						<td >
							${receipt.exchangeRate }
						</td>
					</tr>
					<tr>
						<th >汇率调整金额</th>
						<td >
							${receipt.exchangeRateChange }
						</td>
						<th >金额RMB</th>
						<td >
							${receipt.amountRMB }
						</td>
					</tr>
					<tr>
						<th >进口关税</th>
						<td >
							${receipt.importTariff }
						</td>
						<th >进口增值税</th>
						<td >
							${receipt.importVAT }
						</td>
					</tr>
					<tr>
						<th >保险费</th>
						<td >
							${receipt.insurance }
						</td>
						<th >海运费</th>
						<td >
							${receipt.shippingFee }
						</td>
					</tr>
					<tr>
						<th >商检费</th>
						<td >
							${receipt.inspectionFee }
						</td>
						<th >商检进项金额</th>
						<td >
							${receipt.inspectionEntryFee }
						</td>
					</tr>
					<tr>
						<th >汇差合计</th>
						<td >
							${receipt.differenceRate }
						</td>
						<th >开证手续费</th>
						<td >
							${receipt.creditApplyFee }
						</td>
					</tr>
					<tr>
						<th >押汇利息</th>
						<td >
							${receipt.mortgageInterest }
						</td>
						<th >代理费</th>
						<td >
							${receipt.agencyFee }
						</td>
					</tr>
					<tr>
						<th >开票金额</th>
						<td >
							${receipt.creditAmount }
						</td>
						<th >暂付金额</th>
						<td >
							${receipt.prepaidAmount }
						</td>
					</tr>
					<tr>
						<th >应付金额</th>
						<td colspan="3">
							${receipt.payableAmount }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty receipt.createrName ? sessionScope.user.name : receipt.createrName }</td>
						<th  >登记部门</th>
						<td>${empty receipt.createrDept ? sessionScope.user.organization.orgName : receipt.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ receipt.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${receipt.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
	</div>
<script type="text/javascript">
	
	
	
</script>
</body>
</html>