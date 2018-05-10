<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<div class="titlebar">
	<font>基础信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">合同号</font>
<font class="label-right">
	${obj.contractNo}
</font>
<hr/>
<font class="label-left">免堆期天数</font>
<font class="label-right">
	${obj.freeHeapDays}
</font>
<hr/>
<font class="label-left">达量免堆数(万吨)</font>
<font class="label-right">
	${obj.freeHeapCounts}
</font>
<hr/>
<font class="label-left">合同开始日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.startDay}' />
</font>
<hr/>
<font class="label-left">合同结束日期</font>
<font class="label-right">
	<fmt:formatDate value='${obj.endDay}' />
</font>
<hr/>
<font class="label-left">签订单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.signAffiliate)}
</font>
<hr/>
<font class="label-left">联系方式</font>
<font class="label-right">
	${obj.traderContact }
</font>
<hr/>
<font class="label-left">是否法人签订</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.isCorporationSign,'YESNO','')}
</font>
<hr/>
<font class="label-left">代理人</font>
<font class="label-right">
	${obj.agent }
</font>
<hr/>
<font class="label-left">审核类别</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.checkTypeCode,'checkType','')}
</font>
<hr/>
<font class="label-left">是否法务审核</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.isLegalReview,'YESNO','')}
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.remarks)}
</font>
<div class="titlebar">
	<font>作业费</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="operateExpenseSubs" items="${obj.operateExpenseSubs  }" varStatus="state">
	<font class="label-left">港口</font>
	<font class="label-right">
		${my:getGKMByCode(operateExpenseSubs.portNo)}
	</font>
	<hr/>
	<font class="label-left">贸易类别</font>
	<font class="label-right">
		${fns:getDictLabelByCode(operateExpenseSubs.tradeCategory,'htlb','')}
	</font>
	<hr/>
	<font class="label-left">作业方式</font>
	<font class="label-right">
		${fns:getDictLabelByCode(operateExpenseSubs.operateType,'operateType','')}
	</font>
	<hr/>
	<font class="label-left">计价范围开始(万吨)</font>
	<font class="label-right">
		${operateExpenseSubs.expenseStart}
	</font>
	<hr/>
	<font class="label-left">计价范围结束</font>
	<font class="label-right">
		${operateExpenseSubs.expenseEnd}
	</font>
	<hr/>
	<font class="label-left">单价(元/吨)</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${operateExpenseSubs.unitPrice }" />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
<div class="titlebar">
	<font>杂费</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="extraExpenseSubs" items="${obj.extraExpenseSubs  }" varStatus="state">
	<font class="label-left">费用类别</font>
	<font class="label-right">
		${fns:getDictLabelByCode(extraExpenseSubs.expenseType,'expenseType','')}
	</font>
	<hr/>
	<font class="label-left">计量单位</font>
	<font class="label-right">
		${fns:getDictLabelByCode(extraExpenseSubs.countUnit,'countUnit','')}
	</font>
	<hr/>
	<font class="label-left">计价范围开始(天)</font>
	<font class="label-right">
		${extraExpenseSubs.expenseStart}
	</font>
	<hr/>
	<font class="label-left">计价范围结束</font>
	<font class="label-right">
		${extraExpenseSubs.expenseEnd}
	</font>
	<hr/>
	<font class="label-left">单价</font>
	<font class="label-right">
		${extraExpenseSubs.unitPrice}
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
