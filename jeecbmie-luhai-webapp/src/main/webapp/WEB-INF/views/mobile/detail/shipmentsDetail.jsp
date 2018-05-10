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
<font class="label-left">订船合同编号</font>
<font class="label-right">
	${obj.contractNoOld}
</font>
<hr/>
<font class="label-left">订船类型</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.tradeCategory,'orderShipType','')}
</font>
<hr/>
<font class="label-left">结算日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.settleDate}' />
</font>
<hr/>
<font class="label-left">开户名</font>
<font class="label-right">
	${obj.accountName}
</font>
<hr/>
<font class="label-left">开户行</font>
<font class="label-right">
	${obj.bank }
</font>
<hr/>
<font class="label-left">开户账号</font>
<font class="label-right">
	${obj.account }
</font>
<div class="titlebar">
	<font>结算信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>

<c:forEach var="settleSubs" items="${obj.settleSubs  }" varStatus="state">
	<font class="label-left">船名</font>
	<font class="label-right">
		${settleSubs.ship}
	</font>
	<hr/>
	<font class="label-left">航线</font>
	<font class="label-right">
		${settleSubs.route}
	</font>
	<hr/>
	<font class="label-left">卸货日期</font>
	<font class="label-right">
		<fmt:formatDate value='${settleSubs.unloadDate}' />
	</font>
	<hr/>
	<font class="label-left">结算吨数</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${settleSubs.settleQuantity }" />
	</font>
	<hr/>
	<font class="label-left">币种</font>
	<font class="label-right">
		${fns:getDictLabelByCode(settleSubs.moneyCurrencyCode,'currency','')}
	</font>
	<hr/>
	<font class="label-left">结算单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${settleSubs.unitPrice }" />
	</font>
	<hr/>
	<font class="label-left">小计金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${settleSubs.totalPrice }" />
	</font>
	<hr/>
	<font class="label-left">备注</font>
	<font class="label-right">
		${settleSubs.remarks}
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
