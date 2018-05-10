<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<div class="titlebar">
	<font>合同信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">合同编号</font>
<font class="label-right">
	${obj.contractNo }
</font>
<hr/>
<font class="label-left">内部合同号</font>
<font class="label-right">
	${obj.innerContractNo }
</font>
<hr/>
<font class="label-left">业务经办人</font>
<font class="label-right">
	${empty obj.businessManager ? "" : my:getUserByLoginName(obj.businessManager).name}
</font>
<hr/>
<font class="label-left">申请日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.applyDate}' />
</font>
<hr/>
<font class="label-left">用章类别</font>
<font class="label-right">
	${obj.signetCode }
</font>
<hr/>
<font class="label-left">印章管理员</font>
<font class="label-right">
	${empty obj.sealManager ? "" : my:getUserById(obj.sealManager).name}
</font>
<hr/>
<font class="label-left">合同金额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.money }" />
</font>
<hr/>
<font class="label-left">币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.moneyCurrencyCode,'CURRENCY','')}
</font>
<hr/>
<font class="label-left">合同开始日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.startDate}' />
</font>
<hr/>
<font class="label-left">合同结束日期 </font>
<font class="label-right">
	<fmt:formatDate value='${obj.endDate}' />
</font>
<hr/>
<font class="label-left">对方单位全称</font>
<font class="label-right">
	${obj.traderName }
</font>
<hr/>
<font class="label-left">对方联系方式</font>
<font class="label-right">
	${obj.traderContact }
</font>
<hr/>
<font class="label-left">订船类型</font>
<font class="label-right">
	${obj.orderShipType }
</font>
<hr/>
<font class="label-left">货量(吨)</font>
<font class="label-right">
	${obj.contractQuantity }
</font>
<hr/>
<font class="label-left">是否法人签署</font>
<font class="label-right">
	${obj.isCorporationSign==1 ? "是" : "否" }
</font>
<hr/>
<font class="label-left">代理人</font>
<font class="label-right">
	${obj.agent }
</font>
<hr/>
<font class="label-left">审查类别</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.checkTypeCode,'checkType','')}
</font>
<hr/>
<font class="label-left">是否法务审查</font>
<font class="label-right">
	${obj.isLegalReview==1 ? "是" : "否" }
</font>
<hr/>
<font class="label-left">重大非常规披露</font>
<font class="label-right">
	${my:unescapeHtml(obj.tipContent)}
</font>
<div class="titlebar">
	<font>船明细</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="shipSubs" items="${obj.shipSubs }" varStatus="state">
	<font class="label-left">船编号</font>
	<font class="label-right">
		${shipSubs.shipNo}
	</font>
	<hr/>
	<font class="label-left">装货开始时间</font>
	<font class="label-right">
		<fmt:formatDate value='${shipSubs.loadBeginDate}' />
	</font>
	<hr/>
	<font class="label-left">装货结束时间</font>
	<font class="label-right">
		<fmt:formatDate value='${shipSubs.loadEndDate}' />
	</font>
	<hr/>
	<font class="label-left">数量溢短装</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${shipSubs.numMoreOrLess }" />
	</font>
	<hr/>
	<font class="label-left">运费单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${shipSubs.tradePriceRate }" />
	</font>
	<hr/>
	<font class="label-left">装率</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${shipSubs.loadRate }" />
	</font>
	<hr/>
	<font class="label-left">卸率</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${shipSubs.unloadRate }" />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
