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
<font class="label-left">码头</font>
<font class="label-right">
	${obj.wharf}
</font>
<hr/>
<font class="label-left">预付</font>
<font class="label-right">
	${obj.prePay}
</font>
<hr/>
<font class="label-left">商检水尺</font>
<font class="label-right">
	${obj.gaugeQuantity}
</font>
<hr/>
<font class="label-left">出入库日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.stockDate}' />
</font>
<hr/>
<font class="label-left">结算日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.settleDate}' />
</font>
<hr/>
<font class="label-left">结算月份</font>
<font class="label-right">
	${obj.settleMonth}
</font>
<hr/>
<font class="label-left">应付</font>
<font class="label-right">
	${obj.shouldPay }
</font>
<hr/>
<font class="label-left">已开票</font>
<font class="label-right">
	${obj.alreadyBill }
</font>
<hr/>
<font class="label-left">应开票</font>
<font class="label-right">
	${obj.shouldBill }
</font>
<div class="titlebar">
	<font>本次开票信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>

<c:forEach var="subList" items="${obj.subList  }" varStatus="state">
	<font class="label-left">船编号</font>
	<font class="label-right">
		${subList.shipNo }
	</font>
	<hr/>
	<font class="label-left">费用类别</font>
	<font class="label-right">
		${fns:getDictLabelByCode(subList.portExpenseType,'portExpenseType','')}
	</font>
	<hr/>
	<font class="label-left">计费吨数</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${subList.settleQuantity }" />
	</font>
	<hr/>
	<font class="label-left">结算单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${subList.unitPrice }" />
	</font>
	<hr/>
	<font class="label-left">金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${subList.totalPrice }" />
	</font>
	<hr/>
	<font class="label-left">摘要</font>
	<font class="label-right">
		${subList.roundup}
	</font>
	<hr/>
	<font class="label-left">备注</font>
	<font class="label-right">
		${subList.remarks}
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
