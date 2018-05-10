<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			
			/**
			 * 流程撤回
			 */
			function callBack() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/taskHaveDone/callBack";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "activityId=${activityId}&currentActivityId=${currentActivityId}&processInstanceId=${approval.processInstanceId}&loginName=${loginName}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				$ajax(url, method, data, callBackFunction, failFunction, requestHeader, isShowProgress);
			}
			
			function callBackFunction(response){
				if (response.status == 200) {
					if (response.responseText == "responseMobileSessionTimeOut") {
						ClientUtil.execScript("script:reloadapp");
						return;
					}
					progressBar.cancel();
					createTooltip("撤回成功");
					window.openData(response.responseText, true, false);
					var listWindow = PageUtil.getWindowById("listWindow");
					listWindow.close();
					window.close();
				}
			}
			
			function trace() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/trace";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "processInstanceId=${approval.processInstanceId}&rows=100";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				$ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="详情" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="window.close();">
		<%@ include file="../detail/mainDetail.jsp"%>
	</body>
	<footer>
		<input type="button" value="流程跟踪" style="align: center;width: 40%;" onclick="trace()"/>
		<c:if test="${callBack eq true }">
			<input type="button" value="撤回" style="align: center;width: 40%;" onclick="window.confirm('是否确认撤回?', callBack)"/>
		</c:if>
	</footer>
</html>