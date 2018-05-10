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
<font class="label-left">保险合同号</font>
<font class="label-right">
	${obj.contractNo }
</font>
<hr/>
<font class="label-left">内部合同号</font>
<font class="label-right">
	${obj.innerContractNo }
</font>
<hr/>
<font class="label-left">是否已保</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.isInsurance,'YESNO','')}
</font>
<hr/>
<font class="label-left">保险公司</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.insuranceCompany)}
</font>
<hr/>
<font class="label-left">保额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.money }" />
</font>
<hr/>
<font class="label-left">保额币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.moneyCurrency,'currency','')}
</font>
<hr/>
<font class="label-left">保费</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.amount }" />
</font>
<hr/>
<font class="label-left">保费币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.amountCurrency,'currency','')}
</font>
<hr/>
<font class="label-left">费率</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.exchangeRate }" />
</font>
<hr/>
<font class="label-left">投保日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.insuranceDate}"/>
</font>
<hr/>
<font class="label-left">船名</font>
<font class="label-right">
<c:if test="${!empty obj.shipNo}">【${obj.shipNo}】</c:if>${my:getShipByNo(obj.shipNo).shipName}
</font>
<hr/>
<font class="label-left">险种</font>
<font class="label-right">
	${obj.type }
</font>
<hr/>
<font class="label-left">被保险人</font>
<font class="label-right">
	${obj.insurancePerson }
</font>
<hr/>
<font class="label-left">保险货物项目</font>
<font class="label-right">
	${obj.goodsName }
</font>
<hr/>
<font class="label-left">装载运输工具</font>
<font class="label-right">
	${obj.transportTool }
</font>
<hr/>
<font class="label-left">赔款偿付地点</font>
<font class="label-right">
	${obj.addr }
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.comment)}
</font>