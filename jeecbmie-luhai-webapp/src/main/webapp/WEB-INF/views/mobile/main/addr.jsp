<%@ page contentType="application/uixml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ip" value="${pageContext.request.localAddr}" />
<c:set var="post" value="${pageContext.request.serverPort}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<indexbar prompt="搜索联系人" isshowheader="true" isshowicon="true" defaulticon="res:image/main4/user.png" footertext="${count}位联系人" cached="true" searchtype="2">
<c:forEach var="item" items="${map}">
	<section text="${item.key}" indextext="${item.key}">
	<c:forEach var="user" items="${item.value}">
		<item icon="http://${ip}:${post}${ctx}/accessory/avatarView/${user.img}" caption="${user.name}" sndcaption="${user.phone}" href="http://${ip}:${post}${ctx}/mobile/main/addrDetail/${user.id}"/>
	</c:forEach>
	</section>
</c:forEach>
</indexbar>