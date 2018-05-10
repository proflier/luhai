<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>

<!DOCTYPE html>
<html>
<head>
	<title>系统消息</title>
</head>

<body>
	<h2 align="center"><%=request.getAttribute("mesg")%></h2>
</body>
</html>
