<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html type="pop">
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css"/>
		<link rel="stylesheet" type="text/css" href="res:css/other.css"/>
		<style type="text/css">
		</style>
		<script type="text/javascript" src="res:script/other.js"></script>
		<script type="text/javascript">
			<![CDATA[

			/**
			 * 刷新反馈记录
			 */
			function successFunction(response){
				if (response.status == 200) {
					if (response.responseText == "responseMobileSessionTimeOut") {
						ClientUtil.execScript("script:reloadapp");
						return;
					}
					PageUtil.getWindowById("todoWindow").document.getElementById('record').innerHTML = response.responseText;
					window.close();
					createTooltip("提交成功");
				}
			}
			
			]]>
		</script>
	</head>
	<body>
		<form id="content_form" action="http://${ip}:${post}${ctx}/mobile/feedback/create" method="post" success="successFunction" fail="failFunction">
			<input id="feedbackThemeId" name="feedbackThemeId" type="hidden" value="${feedbackThemeId}" />
			<textarea id="content" name="content" prompt="在此输入您的意见" validate="required" validatemsg="反馈意见不能为空"></textarea>
			<input type="submit" value="提交" style="width:40%;align:center;"/>
			<input type="reset" value="重置" style="width:40%;align:center;"/>
		</form>
	</body>
</html>