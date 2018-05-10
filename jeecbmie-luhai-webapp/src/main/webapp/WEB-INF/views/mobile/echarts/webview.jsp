<%@ page contentType="application/uixml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ip" value="${pageContext.request.localAddr}" />
<c:set var="post" value="${pageContext.request.serverPort}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.nj.fiberhome.com.cn/exmobi.dtd">
<html isbridge="true">
	<head>
		<meta content="charset=utf-8"/>
		<title show="false"/>
		<script type="text/javascript">
			<![CDATA[
				
			]]>
		</script>
	</head>
	<header>
		<titlebar title="图表" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body>
		<webview url="http://${ip}:${post}${ctx}/mobile/echarts/test" showerrorpage="true" transparent="true" zoom="true"/>
	</body>

</html>