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
<font class="label-left">结算编号</font>
<font class="label-right">
	${obj.saleSettlementNo }
</font>
<hr/>
<font class="label-left">销售合同号</font>
<a href="http://${ip}:${post}${ctx}/mobile/link/detail/saleContract/${obj.saleContractNo }" class="a-right">
	${obj.saleContractNo }
</a>
<hr/>
<font class="label-left">结算日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.settlementDate }" />
</font>
<hr/>
<font class="label-left">收货单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.receivingUnit)}
</font>
<hr/>
<font class="label-left">发货开始日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.sendBeginDate }" />
</font>
<hr/>
<font class="label-left">发货结束日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.sendEndDate }" />
</font>
<hr/>
<font class="label-left">收货开始日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.receiveBeginDate }" />
</font>
<hr/>
<font class="label-left">收货结束日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.receiveEndDate }" />
</font>
<hr/>
<font class="label-left">奖罚金额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.sanctionPrice }" />
</font>
<div class="titlebar">
	<font>结算明细</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>

<c:forEach var="saleSettlementSubList" items="${obj.saleSettlementSubList }" varStatus="state"> 
	<font class="label-left">品名</font>
	<font class="label-right">
		${my:getGoodsInformationByCode(saleSettlementSubList.goodsName).infoName}
	</font>
	<hr/>
	<font class="label-left">发货数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.sendQuantity }" />
	</font>
	<hr/>
	<font class="label-left">收货数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.receiveQuantity }" />
	</font>
	<hr/>
	<font class="label-left">结算数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.settlementQuantity }" />
	</font>
	<hr/>
	<font class="label-left">结算单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.settlementPrice }" />
	</font>
	<hr/>
	<font class="label-left">扣减金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.deductionPrice }" />
	</font>
	<hr/>
	<font class="label-left">已收金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.receivedPrice }" />
	</font>
	<hr/>
	<font class="label-left">应收金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${saleSettlementSubList.receivablePrice }" />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>出库信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="settlement" items="${my:getOutStackList(obj.id)}" varStatus="state">
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
		${my:getGoodsInformationByCode(settlement.goodsName).infoName}
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
