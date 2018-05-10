<%@ page contentType="application/uixml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}" />
<c:set var="post" value="${pageContext.request.serverPort}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<grid id="mainGrid" col="3" style="color:#666666;click-color:white;current-color:#666666;iconwidth:48;iconheight:48;cell-background-click-color:#2371af;cell-background-opacity:1;">
	<cell icon="res:image/main4/write.png" text="待办" href="http://luhai/mobile/taskTodo/list"/>
	<cell icon="res:image/main4/know.png" text="已办" href="http://luhai/mobile/taskHaveDone/list"/>
	<cell icon="res:image/main4/doc.png" text="传阅" href="http://luhai/mobile/procPass/list"/>
	<cell icon="res:image/main4/feedback.png" text="反馈版" href="http://luhai/mobile/feedback/list"/>
	<cell icon="res:image/main4/delegate.png" text="流程委托" href="http://luhai/mobile/delegate/list"/>
	<cell icon="res:image/main4/sound.png" text="公告" href="alert('开发中敬请期待')"/>
</grid>
<br size="8"/>
<c:if test="${!empty shipTraceList && my:shiro('mobile:index:ship') }">
<hr size="5" style="width: 100%"/>
<br size="8"/>
	<div style="margin: 0 5%;">
		<img src="res:image/main4/ship.png" style="width:20dp;height:20dp;"/><font style="font-weight:bold;">船舶动态</font>
		<a href="http://luhai/mobile/shipTrace/list" style="color:#6f6f6f;align:right;color-focus:#e34a42;">更多</a>
		<br size="8"/>
	</div>
	<c:forEach var="shipTrace" items="${shipTraceList }" begin="0" end="4" varStatus="status">
	<a href="http://luhai/mobile/shipTrace/detail/${shipTrace.id}" style="color:black;color-focus:#e34a42;width:50%;margin:8 5%;text-decoration:none;">
	<c:set var="shipNoShipName" value="【${shipTrace.shipNo}】${shipTrace.shipName}"/>
	<c:choose>
		<c:when test="${fn:length(shipNoShipName) >= 10}">
			${fn:substring(shipNoShipName, 0, 10)}...
		</c:when>
		<c:otherwise>${shipNoShipName}</c:otherwise>
	</c:choose>
	</a>
	<font style="text-align:right;width:50%;color:#6f6f6f;margin:8 5%;">
		<fmt:formatDate value="${shipTrace.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</font>
	<c:if test="${!status.last }">
		<hr/>
	</c:if>
	</c:forEach>
</c:if>
<c:if test="${!empty activeList }">
<hr size="5" style="width: 100%"/>
<br size="8"/>
	<div style="margin: 0 5%;">
		<img src="res:image/main4/active.png" style="width:20dp;height:20dp;"/><font style="font-weight:bold;">活跃排行</font>
		<a href="script:popmenu(activeOpen);" style="color:#6f6f6f;align:right;color-focus:#e34a42;">. . .</a>
		<contextmenu id="activeOpen" showtype="menu" style="current-color:#e34a42;">
			<option caption="一天内" icon="res:image/main4/time.png" onclick="changeDays(1);"/>
			<option caption="一周内" icon="res:image/main4/time.png" onclick="changeDays(7);"/>
			<option caption="一月内" icon="res:image/main4/time.png" onclick="changeDays(30);"/>
		</contextmenu>
		<br size="8"/>
	</div>
	<div id="activeDiv" style="margin: 0 5%;">
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
	</div>
</c:if>