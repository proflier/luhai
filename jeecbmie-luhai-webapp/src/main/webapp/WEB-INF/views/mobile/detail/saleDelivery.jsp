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
<font class="label-left">发货通知号</font>
<font class="label-right">
	${obj.deliveryReleaseNo}
</font>
<font class="label-left">销售合同号</font>
<a href="http://${ip}:${post}${ctx}/mobile/link/detail/saleContract/${obj.saleContractNo }" class="a-right">
	${obj.saleContractNo }
</a>
<hr/>
<font class="label-left">客户名称</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.seller)}
</font>
<hr/>
<font class="label-left">销售方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.saleMode,'SALESMODE','')}
</font>
<hr/>
<font class="label-left">交货起期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.deliveryStartDate }" />
</font>
<hr/>
<font class="label-left">交货止期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.deliveryEndDate }" />
</font>
<hr/>
<font class="label-left">数量验收</font>
<font class="label-right">
	${my:unescapeHtml(obj.numSettlementBasis)}
</font>
<hr/>
<font class="label-left">质量验收</font>
<font class="label-right">
	${my:unescapeHtml(obj.qualitySettlementBasis)}
</font>
<hr/>
<font class="label-left">交货方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.deliveryMode,'THFS','')}
</font>
<hr/>
<font class="label-left">业务经办人</font>
<font class="label-right">
	${empty obj.businessManager ? "" : my:getUserByLoginName(obj.businessManager).name}
</font>
<hr/>
<font class="label-left">数量溢短装</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.numMoreOrLess }" />
</font>
<hr/>
<font class="label-left">制单日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.billDate }" />
</font>
<hr/>
<font class="label-left">费用承担</font>
<font class="label-right">
	${my:unescapeHtml(obj.riskTip)}
</font>
<hr/>
<font class="label-left">注意事项</font>
<font class="label-right">
	${my:unescapeHtml(obj.remark)}
</font>
<div class="titlebar">
	<font>放贷明细</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="child" items="${obj.salesDeliveryGoodsList }" varStatus="state">
	<font class="label-left">合同号</font>
	<font class="label-right">
		${child.contractNo}
	</font>
	<hr/>
	<font class="label-left">品名</font>
	<font class="label-right">
		${my:getGoodsInformationByCode(child.goodsName).infoName}
	</font>
	<hr/>
	<font class="label-left">仓库</font>
	<font class="label-right">
		${my:getAffiBaseInfoByCode(child.port)}
	</font>
	<hr/>
	<font class="label-left">数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${child.quantity}" />
	</font>
	<hr/>
	<font class="label-left">数量单位</font>
	<font class="label-right">
		${fns:getDictLabelByCode(child.units,'sldw','')}
	</font>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
