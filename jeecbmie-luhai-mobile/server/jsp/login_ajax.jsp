<%@ page language="java" import="java.util.Date" contentType="application/uixml+xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/client/adapt.jsp"%>
<%
	String username = aa.req.getParameter("username");
	String password = aa.req.getParameter("password");
	//String chptcha = aa.getReqParameterValue("chptcha");
%>
<aa:http id="logincheck" keepreqdata="false" method="post" url="http://luhai/a/login">
	<aa:param name="username" value="<%=username%>"/>
	<aa:param name="password" value="<%=password%>"/>
	<%--<aa:param name="chptcha" value="<%=chptcha %>"/>--%>
	<aa:param name="rememberMe" value=""/>
</aa:http>

<%
	//String str = aa.regex("\\$\\(\".login_main_errortip\"\\)", "logincheck");
	String str = aa.regex(".*", "logincheck");
%>

<%
	if (str.length() == 0) {
		
		String esn = aa.req.getParameter("esn");//获取终端esn号
		String clientid = aa.req.getParameter("clientid");//获取客户端clientid
		
		String os = clientid.split("-")[1];//获取客户端操作系统
		if (!os.equals("pc")) {
			String queryRecordSql = "select * from user_directpush where username = ?";
			String inertSql = "insert into user_directpush (esn, username, clientid, login_date) values (?, ?, ?, ?)";
			String updateSql = "update user_directpush set esn = ?, clientid = ?, login_date = ? where username = ?";
			int insertResult;
			
			TableRow record = aa.db.queryRow("db_1", queryRecordSql, new Object[]{username});
			if (record == null) {//插入
				insertResult = aa.db.update("db_1", inertSql, new Object[]{esn, username, clientid, new Date()});
			} else {//修改
				insertResult = aa.db.update("db_1", updateSql, new Object[]{esn, clientid, new Date(), username});
			}
		}
		
		out.write("true");
	} else {
		out.write(str);
	}
%>