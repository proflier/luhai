<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:forEach var="content" items="${feedbackTheme.contents }" varStatus="state">
	<div class="label-left">
		<img src="res:image/login8/user.png" style="width:20dp;height:20dp;"/>
		${my:getUserById(content.userId).name}
	</div>
	<font class="label-right">
		<fmt:formatDate value="${content.publishDate}" />
	</font>
	<br size="6"/>
	<font style="margin:8 5%;">
		${my:unescapeHtml(my:replaceHtml(content.content))}
	</font>
	<br size="6"/>
	<fileset hidetitle="true">
	<c:forEach var="acc" items="${my:getAccessory(content.id, 'com_cbmie_lh_feedback_entity_FeedbackContent')}" varStatus="status">
		<item text="${acc.accRealName}" href="http://${ip}:${post}${ctx}/accessory/download/${acc.accId}" options="111"/>
	</c:forEach>
	</fileset>
	<c:if test="${!state.last }">
		<hr style="width:100%;"/>
	</c:if>
</c:forEach>