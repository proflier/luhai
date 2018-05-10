<%-- ExMobi JSP文件，注释和取消快捷键统一为Ctrl+/ 多行注释为Ctrl+Shift+/ --%>
<%@ page language="java" import="java.util.Date" contentType="application/uixml+xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/client/adapt.jsp"%>
<%@ include file="/client/adapt_extend.jsp"%>
<%
	String username = aa.req.getParameter("username");
	String title = aa.req.getParameter("title");
	
	String pushTitle = title + "，已审批完成，正式生效。";
	
	String querySql = "select * from user_directpush where username = ?";
	//为什么用aa.db.query方法查询？因为每个用户可能有多个移动终端都使用同一个业务系统帐号登录
	List<TableRow> rows = aa.db.query("db_1", querySql, new Object[] { username });
	String esn = "";
	String clientid = "";
	String inertSql = "insert into user_directpush_mes (esn, username, clientid, content, push_date) values (?, ?, ?, ?, ?)";
%>
<aa:direct-push directPushType="notify" title="<%=pushTitle%>" titleHead="陆海资源ERP系统">
	<%
		for (TableRow row : rows) {
			esn = row.getField("esn", "");
			clientid = row.getField("clientid", "");
			int insertResult = aa.db.update("db_1", inertSql, new Object[]{esn, username, clientid, pushTitle, new Date()});
	%>
		<aa:push-receiver clientid="<%=clientid%>" esn="<%=esn%>"></aa:push-receiver>
	<%
		}
	%>
</aa:direct-push>