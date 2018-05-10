<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<div class="titlebar">
	<font>基本信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">是否预开票</font>
<font class="label-right">
	${obj.preBilling=='0' ? "否" : "是" }
</font>
<hr/>
<font class="label-left">销售合同号</font>
<a href="http://${ip}:${post}${ctx}/mobile/link/detail/saleContract/${obj.saleContractNo }" class="a-right">
	${obj.saleContractNo }
</a>
<hr/>
<font class="label-left">结算编号</font>
<a href="http://${ip}:${post}${ctx}/mobile/link/detail/saleSettlement/${obj.invoiceNo }" class="a-right">
	${obj.invoiceNo }
</a>
<hr/>
<font class="label-left">客户名称</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.customerName)}
</font>
<hr/>
<font class="label-left">品名</font>
<font class="label-right">
	${obj.goodsName}
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.remark)}
</font>
<div class="titlebar">
	<font>出库信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="settlement" items="${my:getOutStackListSale(obj.id)}" varStatus="state">
	<font class="label-left">仓库</font>
	<font class="label-right">
		${my:getAffiBaseInfoByCode(settlement.port)}
	</font>
	<hr/>
	<font class="label-left">船名</font>
	<font class="label-right">
		${settlement.voy }
	</font>
	<hr/>
	<font class="label-left">出库单号</font>
	<font class="label-right">
		${settlement.outStockNo }
	</font>
	<hr/>
	<font class="label-left">出库日期</font>
	<font class="label-right">
		<fmt:formatDate value="${settlement.outStockDate }" />
	</font>
	<hr/>
	<font class="label-left">品名</font>
	<font class="label-right">
<%-- 		${my:getGoodsInformationByCode(settlement.goodsName).infoName} --%>
		${settlement.goodsName }
	</font>
	<hr/>
	<font class="label-left">数量</font>
	<font class="label-right">
		${settlement.quantity }
	</font>
	<hr/>
	<font class="label-left">运输方式</font>
	<font class="label-right">
		${fns:getDictLabelByCode(settlement.transType,'YSFS','')}
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>结算信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="saleInvoice" items="${my:SaleInvoicepubList(obj.id)}" varStatus="state">
	<font class="label-left">销售合同号</font>
	<font class="label-right">
		${saleInvoice.saleContractNo }
	</font>
	<hr/>
	<font class="label-left">品名</font>
	<font class="label-right">
		${my:getGoodsInformationByCode(saleInvoice.goodsName).infoName}
	</font>
	<hr/>
	<font class="label-left">发货数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.sendQuantity }" />
	</font>
	<hr/>
	<font class="label-left">收货数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.receiveQuantity }" />
	</font>
	<hr/>
	<font class="label-left">结算数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.settlementQuantity }" />
	</font>
	<hr/>
	<font class="label-left">结算单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.settlementPrice }" />
	</font>
	<hr/>
	<font class="label-left">扣减金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.deductionPrice }" />
	</font>
	<hr/>
	<font class="label-left">已收金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.receivedPrice }" />
	</font>
	<hr/>
	<font class="label-left">应收金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleInvoice.receivablePrice }" />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>进项发票</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="inputInvoice" items="${my:getInputInvoiceSubBySaleNo(obj.saleContractNo)}" varStatus="state">
	<font class="label-left">采购订单号</font>
	<font class="label-right">
		${inputInvoice.purchaseOrderNumber }
	</font>
	<hr/>
	<font class="label-left">相关销售订单</font>
	<font class="label-right">
		${inputInvoice.relatedSalesOrder }
		
	</font>
	<hr/>
	<font class="label-left">发票号</font>
	<font class="label-right">
		${inputInvoice.invoiceNo }
	</font>
	<hr/>
	<font class="label-left">开票日期</font>
	<font class="label-right">
		<fmt:formatDate value='${inputInvoice.billingDate}' />
	</font>
	<hr/>
	<font class="label-left">品名</font>
	<font class="label-right">
		${my:getGoodsInformationByCode(inputInvoice.relatedSalesOrder).infoName}
	</font>
	<hr/>
	<font class="label-left">计量单位</font>
	<font class="label-right">
		${fns:getDictLabelByCode(inputInvoice.unitMeasurement,'sldw','')}
	</font>
	<hr/>
	<font class="label-left">数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${inputInvoice.mount }" />
	</font>
	<hr/>
	<font class="label-left">单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${inputInvoice.prices }" />
	</font>
	<hr/>
	<font class="label-left">开票金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${inputInvoice.allPrices }" />
	</font>
	<hr/>
	<font class="label-left">税额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${inputInvoice.taxMoney }" />
	</font>
	<hr/>
	<font class="label-left">退税率</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${inputInvoice.rebateRate }" />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>水单信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="serialBySaleNo" items="${my:getSerialBySaleNo(obj.saleContractNo)}" varStatus="state">
	<font class="label-left">流水号</font>
	<font class="label-right">
		${serialBySaleNo.serialNumber }
	</font>
	<hr/>
	<font class="label-left">合同号</font>
	<font class="label-right">
		${serialBySaleNo.contractNo }
		
	</font>
	<hr/>
	<font class="label-left">水单抬头</font>
	<font class="label-right">
		${my:getAffiBaseInfoByCode(serialBySaleNo.serialTitle)}
	</font>
	<hr/>
	<font class="label-left">资金类别</font>
	<font class="label-right">
		${fns:getDictLabelByCode(serialBySaleNo.fundCategory,'fundCategory','')}
	</font>
	<hr/>
	<font class="label-left">签署日期</font>
	<font class="label-right">
		<fmt:formatDate value='${serialBySaleNo.serialDate}' />
	</font>
	<hr/>
	<font class="label-left">银承到期日期</font>
	<font class="label-right">
		<fmt:formatDate value='${serialBySaleNo.bankDeadline}' />
	</font>
	<hr/>
	<font class="label-left">金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${serialBySaleNo.money }" />
	</font>
	<hr/>
	<font class="label-left">创建时间</font>
	<font class="label-right">
		<fmt:formatDate value='${serialBySaleNo.createDate}' />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>已开票信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="sale" items="${obj.saleInvoiceSubs }" varStatus="state">
	<font class="label-left">销售合同号</font>
	<font class="label-right">
		${sale.saleContractNo}
	</font>
	<hr/>
	<font class="label-left">开票数量</font>
	<font class="label-right">
		${sale.billQuantity}
	</font>
	<hr/>
	<font class="label-left">开票金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sale.billMoney }" />
	</font>
	<hr/>
	<font class="label-left">开票单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sale.billPrice }" />
	</font>
	<hr/>
	<font class="label-left">开票日期</font>
	<font class="label-right">
		<fmt:formatDate value='${sale.billDate}' />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>本次开票信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="sales" items="${my:getSaleInvoiceSubService(obj.id)}" varStatus="state">
	<font class="label-left">销售合同号</font>
	<font class="label-right">
		${sales.saleContractNo}
	</font>
	<hr/>
	<font class="label-left">开票数量</font>
	<font class="label-right">
		${sales.billQuantity}
	</font>
	<hr/>
	<font class="label-left">开票金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sales.billMoney }" />
	</font>
	<hr/>
	<font class="label-left">开票单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sales.billPrice }" />
	</font>
	<hr/>
	<font class="label-left">开票日期</font>
	<font class="label-right">
		<fmt:formatDate value='${sales.billDate}' />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>


