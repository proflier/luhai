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
<font class="label-left">运输商</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.transporter)}
</font>
<hr/>
<font class="label-left">办事处</font>
<font class="label-right">
	${obj.office }
</font>
<hr/>
<font class="label-left">运输方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.transportType,'YSFS','')}
</font>
<hr/>
<font class="label-left">数量单位</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.numUnit,'sldw','')}
</font>
<hr/>
<font class="label-left">运输路线</font>
<font class="label-right">
	${obj.route }
</font>
<hr/>
<font class="label-left">收货方</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.receiver)}
</font>
<hr/>
<font class="label-left">结算开始日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.settleBeginDate }" />
</font>
<hr/>
<font class="label-left">结算结束日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.settleEndDate }" />
</font>
<hr/>
<font class="label-left">金额单位</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.moneyUnit,'moneyUnit','')}
</font>
<hr/>
<font class="label-left">预付</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.prePay }" />
</font>
<hr/>
<font class="label-left">上期结余</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.priorPay }" />
</font>
<hr/>
<font class="label-left">已付</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.alreadyPay }" />
</font>
<hr/>
<font class="label-left">应付</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.shouldPay }" />
</font>
<hr/>
<font class="label-left">扣磅差费</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.differPay }" />
</font>
<hr/>
<font class="label-left">其他费用</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.elsePay }" />
</font>
<hr/>
<font class="label-left">实付</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.realPay }" />
</font>
<hr/>
<font class="label-left">已开票</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.alreadyBill }" />
</font>
<hr/>
<font class="label-left">应开票</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.shouldBill }" />
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.remarks)}
</font>
<div class="titlebar">
	<font>本次开票信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="sub" items="${obj.settleSubs }" varStatus="state">
	<font class="label-left">出库吨数</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.outStockQuantity}" />
	</font>
	<hr/>
	<font class="label-left">到货吨数</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.arrivalQuantity}" />
	</font>
	<hr/>
	<font class="label-left">结算吨数</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.settleQuantity}" />
	</font>
	<hr/>
	<font class="label-left">运费单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.unitPrice}" />
	</font>
	<hr/>
	<font class="label-left">应付</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.shouldPay}" />
	</font>
	<hr/>
	<font class="label-left">扣磅差费</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.differPay}" />
	</font>
	<hr/>
	<font class="label-left">其他费用</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.elsePay}" />
	</font>
	<hr/>
	<font class="label-left">实付</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${sub.realPay}" />
	</font>
	<hr/>
	<font class="label-left">摘要</font>
	<font class="label-right">
		${my:unescapeHtml(sub.roundup)}
	</font>
	<hr/>
	<font class="label-left">备注</font>
	<font class="label-right">
		${my:unescapeHtml(sub.remarks)}
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>