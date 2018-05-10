<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css"/>
		<link rel="stylesheet" type="text/css" href="res:css/other.css"/>
		<style type="text/css">
		</style>
		<script type="text/javascript" src="res:script/other.js"></script>
		<script type="text/javascript">
			<![CDATA[

			
			
			]]>
		</script>
	</head>
	<header>
		<titlebar title="详情" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="window.close();">
		<h style="padding:10 5%;">
			<v style="text-valign:top;">
				<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
				<c:choose>
					<c:when test="${empty shipTrace.loadPortDate }">
						<img src="res:image/detail/vertical_gray.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot_gray.png" style="width:8;height:8;"/>
					</c:when>
					<c:otherwise>
						<img src="res:image/detail/vertical.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty shipTrace.loadBeginDate }">
						<img src="res:image/detail/vertical_gray.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot_gray.png" style="width:8;height:8;"/>
					</c:when>
					<c:otherwise>
						<img src="res:image/detail/vertical.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty shipTrace.loadEndDate }">
						<img src="res:image/detail/vertical_gray.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot_gray.png" style="width:8;height:8;"/>
					</c:when>
					<c:otherwise>
						<img src="res:image/detail/vertical.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty shipTrace.unloadPortDate }">
						<img src="res:image/detail/vertical_gray.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot_gray.png" style="width:8;height:8;"/>
					</c:when>
					<c:otherwise>
						<img src="res:image/detail/vertical.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty shipTrace.unloadBeginDate }">
						<img src="res:image/detail/vertical_gray.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot_gray.png" style="width:8;height:8;"/>
					</c:when>
					<c:otherwise>
						<img src="res:image/detail/vertical.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty shipTrace.unloadEndDate }">
						<img src="res:image/detail/vertical_gray.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot_gray.png" style="width:8;height:8;"/>
					</c:when>
					<c:otherwise>
						<img src="res:image/detail/vertical.png" style="width:8;height:130;margin:3 0;"/>
						<img src="res:image/detail/dot.png" style="width:8;height:8;"/>
					</c:otherwise>
				</c:choose>
			</v>
			<v style="text-valign:top;">
				<v style="height:138;">
					<font>
						船名：【${shipTrace.shipNo}】${shipTrace.shipName}
					</font>
					<font>
						类型：${fns:getDictLabelByCode(shipTrace.tradeCategory,'orderShipType','')}
					</font>
					<c:if test="${!empty shipTrace.innerContractNo }">
						<font>采购合同号：${shipTrace.innerContractNo}</font>
					</c:if>
					<c:if test="${!empty shipTrace.wmShipNo }">
						<font>母船：【${shipTrace.wmShipNo}】${my:getShipByNo(shipTrace.wmShipNo).shipName}</font>
					</c:if>
					<font>
						<fmt:formatDate value="${shipTrace.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</font>
				</v>
				<v style="height:138;">
					<font>抵港</font>
					<c:if test="${!empty shipTrace.loadPort }">
						<font>港口：${my:getGKMByCode(shipTrace.loadPort)}</font>
					</c:if>
					<font><fmt:formatDate value="${shipTrace.loadPortDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
				</v>
				<v style="height:138;">
					<font>开装</font>
					<font><fmt:formatDate value="${shipTrace.loadBeginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
				</v>
				<v style="height:138;">
					<font>装完</font>
					<c:if test="${!empty shipTrace.loadQuantity }">
						<font>装货量：${shipTrace.loadQuantity }</font>
					</c:if>
					<font><fmt:formatDate value="${shipTrace.loadEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
				</v>
				<v style="height:138;">
					<font>抵卸港</font>
					<c:if test="${!empty shipTrace.unloadPort }">
						<font>卸港港口：${my:getGKMByCode(shipTrace.unloadPort)}</font>
					</c:if>
					<font><fmt:formatDate value="${shipTrace.unloadPortDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
				</v>
				<v style="height:138;">
					<font>开卸</font>
					<font><fmt:formatDate value="${shipTrace.unloadBeginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
				</v>
				<v style="height:138;">
					<font>卸完</font>
					<c:if test="${!empty shipTrace.unloadQuantity }">
						<font>卸货量：${shipTrace.unloadQuantity }</font>
					</c:if>
					<font><fmt:formatDate value="${shipTrace.unloadEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
				</v>
			</v>
		</h>
	</body>
	<footer>
		<input type="button" value="关闭" style="align: center;width: 40%;" onclick="script:close"/>
	</footer>
</html>