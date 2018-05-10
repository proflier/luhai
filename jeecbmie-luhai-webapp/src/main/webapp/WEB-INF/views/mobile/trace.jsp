<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<html>
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css" />
		<link rel="stylesheet" type="text/css" href="res:css/other.css"/>
		<style type="text/css">
		</style>
		<script type="text/javascript">
		</script>
	</head>
	<header>
		<titlebar title="流程跟踪" iconhref="window.close();" hidericon="true"/>
	</header>
	<body onl2rside="window.close();">
		<div class="titlebar">
			<font>流程图</font>
			<br size="10"/>
			<hr color="#e34a42" size="3" style="width:30%"/>
		</div>
		<img src="http://${ip}:${post}${ctx}/workflow/trace/photo/${processDefinitionId}/${param.processInstanceId}" style="margin:8 5 8 5;width:100%;;"/>
		<div class="titlebar">
			<font>审批记录</font>
			<br size="10"/>
			<hr color="#e34a42" size="3" style="width:30%"/>
		</div>
		<c:forEach var="item" items="${traceList}" varStatus="status">
		<font class="label-left">审批节点</font>
		<font class="label-right">
			${item.name}
		</font>
		<hr/>
		<font class="label-left">处理人</font>
		<font class="label-right">
			${item.assignee}
		</font>
		<hr/>
		<font class="label-left">传阅人</font>
		<font class="label-right">
			${item.passUsers}
		</font>
		<hr/>
		<font class="label-left">开始时间</font>
		<font class="label-right">
			${item.startTime}
		</font>
		<hr/>
		<font class="label-left">结束时间</font>
		<font class="label-right">
			${item.endTime}
		</font>
		<hr/>
		<font class="label-left">耗时</font>
		<font class="label-right">
			${item.distanceTimes}
		</font>
		<hr/>
		<font class="label-left">审批意见</font>
		<font class="label-right">
			${my:unescapeHtml(item.comments)}
		</font>
		<c:if test="${!status.last }">
			<hr/>
			<br size="8"/>
			<hr color="#DDDDDD" size="5" style="width:100%"/>
			<br size="8"/>
		</c:if>
		</c:forEach>
	</body>
	<footer>
		<input type="button" value="关闭" style="align: center;width: 40%;" onclick="window.close();"/>
	</footer>
</html>