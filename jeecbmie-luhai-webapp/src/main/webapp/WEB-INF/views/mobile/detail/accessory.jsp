<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="res:script/other.js"></script>
<div class="titlebar">
	<font>附件</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:if test="${!empty accList}">
<fileset hidetitle="true">
</c:if>
<c:forEach var="acc" items="${accList}" varStatus="status">
	<item text="${my:unescapeHtml(acc.accRealName)}" href="http://${ip}:${post}${ctx}/accessory/download/${acc.accId}" options="111"/>
</c:forEach>
<c:if test="${!empty accList}">
</fileset>
</c:if>