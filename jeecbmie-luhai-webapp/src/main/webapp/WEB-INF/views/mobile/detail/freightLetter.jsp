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
<font class="label-left">主合同编号</font>
<font class="label-right">
	${obj.contractNo}
</font>
<hr/>
<font class="label-left">承运函编号</font>
<font class="label-right">
	${obj.letterNo}
</font>
<hr/>
<font class="label-left">发起码头</font>
<font class="label-right">
	${my:getGKMByCode(obj.lineFrom)}
</font>
<hr/>
<font class="label-left">收货单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.lineTo)}
</font>
<hr/>
<font class="label-left">结算单价</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.unitPrice }" />
</font>
<hr/>
<font class="label-left">数量单位</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.numberUnit,'sldw','')}
</font>
<hr/>
<font class="label-left">承运时间</font>
<font class="label-right">
	<fmt:formatDate value="${obj.transitTime}" />
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.remark)}
</font>
