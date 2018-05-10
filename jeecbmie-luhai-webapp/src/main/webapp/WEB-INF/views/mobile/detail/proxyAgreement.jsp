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
<font class="label-left">甲方(委托方)</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.ourUnit)}
</font>
<hr/>
<font class="label-left">乙方(代理方)</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.agreementUnit)}
</font>
<hr/>
<font class="label-left">货代费</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.freightFee }" />
</font>
<hr/>
<font class="label-left">币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.currency,'currency','')}
</font>
<hr/>
<font class="label-left">签约日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.signDate }" />
</font>
<hr/>
<font class="label-left">有效期至</font>
<font class="label-right">
	<fmt:formatDate value="${obj.effectDate }" />
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.comment)}
</font>