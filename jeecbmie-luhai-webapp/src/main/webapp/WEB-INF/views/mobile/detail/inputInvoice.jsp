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
<font class="label-left">发票分类</font>
<font class="label-right">
	${obj.invoiceClassModel }
</font>
<hr/>
<font class="label-left">发票类型</font>
<font class="label-right">
	${obj.invoiceTypeModel }
</font>
<hr/>
<font class="label-left">贸易方式</font>
<font class="label-right">
	${obj.tradeMode }
</font>
<hr/>
<font class="label-left">客户名称</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.userName)}
</font>
<hr/>
<font class="label-left">账户</font>
<font class="label-right">
	${obj.account }
</font>
<hr/>
<font class="label-left">开户行</font>
<font class="label-right">
	${obj.accountBank }
</font>
<hr/>
<font class="label-left">开票单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.issuingOffice)}
</font>
<hr/>
<font class="label-left">结算中心</font>
<font class="label-right">
	${sessionScope.user.organization.orgName }
</font>
<hr/>
<font class="label-left">税票张数</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.numStamps }" />
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.remarks)}
</font>
<div class="titlebar">
	<font>发票信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="sub" items="${obj.settleSubs }" varStatus="state">
	<font class="label-left">采购订单号</font>
	<font class="label-right">
		${sub.purchaseOrderNumber }
	</font>
	<hr/>
	<font class="label-left">相关销售订单</font>
	<font class="label-right">
		${sub.relatedSalesOrder }
	</font>
	<hr/>
	<font class="label-left">发票号</font>
	<font class="label-right">
		${sub.invoiceNo }
	</font>
	<hr/>
	<font class="label-left">开票日期</font>
	<font class="label-right">
		<fmt:formatDate value="${sub.billingDate }" pattern="yyyy-MM-dd"/>
	</font>
	<hr/>
	<font class="label-left">品名</font>
	<font class="label-right">
		${sub.productName }
	</font>
	<hr/>
	<font class="label-left">计量单位</font>
	<font class="label-right">
		${sub.unitMeasurement }
	</font>
	<hr/>
	<font class="label-left">数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.mount }"/>
	</font>
	<hr/>
	<font class="label-left">单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.prices }"/>
	</font>
	<hr/>
	<font class="label-left">开票金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.allPrices }"/>
	</font>
	<hr/>
	<font class="label-left">税额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.taxMoney }"/>
	</font>
	<hr/>
	<font class="label-left">退税率</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.rebateRate }"/>
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>