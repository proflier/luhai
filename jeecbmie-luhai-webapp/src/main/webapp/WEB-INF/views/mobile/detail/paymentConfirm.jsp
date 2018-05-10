<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<div class="titlebar">
	<font>付款信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">付款单号</font>
<font class="label-right">
	${obj.confirmNo }
</font>
<hr/>
<font class="label-left">收款单位名称</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.receiveUnitName)}
</font>
<hr/>
<font class="label-left">收款单位银行</font>
<font class="label-right">
	${my:getAffiBankInfoById(obj.remitBank).bankName}
</font>
<hr/>
<font class="label-left">银行账号</font>
<font class="label-right">
	${obj.remitAccount }
</font>
<hr/>
<font class="label-left">付款金额小写</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.payMoneyXiao }" />
</font>
<hr/>
<font class="label-left">付款金额大写</font>
<font class="label-right">
	${obj.payMoneyDa }
</font>
<hr/>
<font class="label-left">币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.currency,'currency','')}
</font>
<hr/>
<font class="label-left">付款单位名称</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.payUnit)}
</font>
<hr/>
<font class="label-left">付款内容</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.payContent,'paymentContent','')}
</font>
<hr/>
<font class="label-left">付款方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.payType,'paymentMethod','')}
</font>
<hr/>
<font class="label-left">付款说明</font>
<font class="label-right">
	${my:unescapeHtml(obj.remarks)}
</font>
<hr/>
<font class="label-left">经办人</font>
<font class="label-right">
	${obj.createrName }
</font>
<hr/>
<font class="label-left">经办部门</font>
<font class="label-right">
	${obj.createrDept }
</font>
<hr/>
<font class="label-left">确认日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.confirmDate }" />
</font>
<div class="titlebar">
	<font>费用分摊</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="child" items="${obj.paymentConfirmChildList }" varStatus="state">
	<font class="label-left">费用类别</font>
	<font class="label-right">
		${fns:getDictLabelByCode(child.feeCategory,'feeCategory','')}
	</font>
	<hr/>
	<font class="label-left">分摊</font>
	<font class="label-right">
		<c:if test="${!empty child.paymentType }">
			<c:choose>
				<c:when test="${child.paymentType eq 'sale' }">
					销售合同
				</c:when>
				<c:when test="${child.paymentType eq 'kehu' }">
					客户
				</c:when>
				<c:otherwise>
					船编号
				</c:otherwise>
			</c:choose>
		</c:if>
	</font>
	<hr/>
	<font class="label-left">摘要</font>
	<font class="label-right">
		${my:digest(child)}
	</font>
	<hr/>
	<font class="label-left">分摊金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${child.shareAmount}" />
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
