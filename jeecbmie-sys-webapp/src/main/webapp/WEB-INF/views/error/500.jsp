<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
	<title>系统内部错误</title>
</head>

<body>
	<h2>系统发生内部错误.</h2><%=request.getAttribute("mesg")==null?"":request.getAttribute("mesg")%>
</body>
</html>
