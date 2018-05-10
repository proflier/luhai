<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<v>
	<h>
	<c:forEach var="active" items="${activeList }" begin="0" end="4">
		<c:set var="user" value="${my:getUserByLoginName(active.CREATER_LOGIN_NAME)}"/>
		<div style="width:20%;text-align:center;">
			<img src="http://${ip}:${post}${ctx}/accessory/avatarView/${user.img}" defaultsrc="res:image/main4/user.png" href="http://${ip}:${post}${ctx}/mobile/main/addrDetail/${user.id}" style="width:40dp;height:40dp;"/>
		</div>
	</c:forEach>
	</h>
	<h>
	<c:forEach var="active" items="${activeList }" begin="0" end="4">
		<div style="width:20%;text-align:center;">
			<a href="http://${ip}:${post}${ctx}/mobile/main/addrDetail/${my:getUserByLoginName(active.CREATER_LOGIN_NAME).id}" style="color:#6f6f6f;text-decoration:none;color-focus:#e34a42;">${active.CREATER}</a>
		</div>
	</c:forEach>
	</h>
</v>