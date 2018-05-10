<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html id="todoWindow">
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css"/>
		<link rel="stylesheet" type="text/css" href="res:css/other.css"/>
		<style type="text/css">
		</style>
		<script type="text/javascript" src="res:script/other.js"></script>
		<script type="text/javascript">
			<![CDATA[
			
			function feedback() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/feedback/create/${feedbackTheme.id}";
				//设置提交方式
				var method = "GET";
				//设置发送数据
				var data = "";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				$ajax(url, method, data, feedbackFunction, failFunction, requestHeader, isShowProgress);
			}

			function feedbackFunction(response) {
				if (response.status == 200) {
					if (response.responseText == "responseMobileSessionTimeOut") {
						ClientUtil.execScript("script:reloadapp");
						return;
					}
					progressBar.cancel();
					window.openData(response.responseText, true, true, "opacity:0.5;width:80%;height:30%;location:normal;");
				}
			}
			
			]]>
		</script>
	</head>
	<header>
		<titlebar title="意见" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="window.close();">
		<div class="titlebar">
			<font>反馈主题</font>
			<br size="10"/>
			<hr color="#e34a42" size="3" style="width:30%"/>
		</div>
		<font class="label-left">主题</font>
		<font class="label-right">
			${feedbackTheme.title }
		</font>
		<hr/>
		<font class="label-left">反馈分类</font>
		<font class="label-right">
			${fns:getDictLabelByCode(feedbackTheme.classification,'feedbackClassification','')}
		</font>
		<hr/>
		<font class="label-left">反馈类型</font>
		<font class="label-right">
			${fns:getDictLabelByCode(feedbackTheme.types,'feedbackTypes','')}
		</font>
		<hr/>
		<font class="label-left">状态</font>
		<font class="label-right">
			${fns:getDictLabelByCode(feedbackTheme.state,'feedbackState','')}
		</font>
		<hr/>
		<font class="label-left">重要性</font>
		<font class="label-right">
			${fns:getDictLabelByCode(feedbackTheme.importance,'feedbackImportance','')}
		</font>
		<hr/>
		<font class="label-left">反馈对象类型</font>
		<font class="label-right">
			${fns:getDictLabelByCode(feedbackTheme.feedbackObjectType,'feedbackObjectType','')}
		</font>
		<hr/>
		<font class="label-left">讨论组</font>
		<font class="label-right">
			${empty feedbackTheme.discussGroupId ? "" : my:getDiscussGroup(feedbackTheme.discussGroupId).groupName }
		</font>
		<hr/>
		<font class="label-left">反馈对象</font>
		<font class="label-right">
			${feedbackTheme.feedbackObject }
		</font>
		<hr/>
		<font class="label-left">公共反馈</font>
		<font class="label-right">
			${feedbackTheme.feedbackPublic eq '0'?'否':'是'}
		</font>
		<hr/>
		<font class="label-left">部门</font>
		<font class="label-right">
			${empty feedbackTheme.dutyUser ? "" : my:getUserById(feedbackTheme.dutyUser).organization.orgName }
		</font>
		<hr/>
		<font class="label-left">负责员工</font>
		<font class="label-right">
			${empty feedbackTheme.dutyUser ? "" : my:getUserById(feedbackTheme.dutyUser).name }
		</font>
		<hr/>
		<font class="label-left">解决期限</font>
		<font class="label-right">
			<fmt:formatDate value="${feedbackTheme.terminalDate}" pattern="yyyy-MM-dd"/>
		</font>
		<hr/>
		<font class="label-left">关闭人员</font>
		<font class="label-right">
			${feedbackTheme.closeUser }
		</font>
		<hr/>
		<font class="label-left">创建人</font>
		<font class="label-right">
			${feedbackTheme.createrName }
		</font>
		<hr/>
		<font class="label-left">创建时间</font>
		<font class="label-right">
			<fmt:formatDate value="${feedbackTheme.createDate}" pattern="yyyy-MM-dd"/>
		</font>
		<hr/>
		<font class="label-left">修改人</font>
		<font class="label-right">
			${feedbackTheme.updaterName }
		</font>
		<hr/>
		<font class="label-left">修改时间</font>
		<font class="label-right">
			<fmt:formatDate value="${feedbackTheme.updateDate}" pattern="yyyy-MM-dd"/>
		</font>
		<hr/>
		<font class="label-left">描述</font>
		<font class="label-right">
			${my:getGoodsInformationByCode(feedbackTheme.description)}
		</font>
		<hr/>
		<font class="label-left">处理结果</font>
		<font class="label-right">
			${my:getGoodsInformationByCode(feedbackTheme.dealResult)}
		</font>
		<%@ include file="../detail/accessory.jsp"%>
		<div class="titlebar">
			<font>归档列表</font>
			<br size="10"/>
			<hr color="#e34a42" size="3" style="width:30%"/>
		</div>
		<c:forEach var="file" items="${feedbackTheme.files }">
			<listitem type="twoline" caption="类别：${fns:getDictLabelByCode(file.fileTypes,'feedbackFileTypes','')}" sndcaption="目标：${my:getAffiBaseInfoByCode(file.fileTarget)}"/>
		</c:forEach>
		<div class="titlebar">
			<font>反馈记录</font>
			<br size="10"/>
			<hr color="#e34a42" size="3" style="width:30%"/>
		</div>
		<div id="record">
		<c:forEach var="content" items="${feedbackTheme.contents }" varStatus="state">
			<div class="label-left">
				<img src="res:image/login8/user.png" style="width:20dp;height:20dp;"/>
				${my:getUserById(content.userId).name}
			</div>
			<font class="label-right">
				<fmt:formatDate value="${content.publishDate}" />
			</font>
			<br size="6"/>
			<font style="margin:8 5%;">
				${my:unescapeHtml(my:replaceHtml(content.content))}
			</font>
			<br size="6"/>
			<fileset hidetitle="true">
			<c:forEach var="acc" items="${my:getAccessory(content.id, 'com_cbmie_lh_feedback_entity_FeedbackContent')}" varStatus="status">
				<item text="${acc.accRealName}" href="http://${ip}:${post}${ctx}/accessory/download/${acc.accId}" options="111"/>
			</c:forEach>
			</fileset>
			<c:if test="${!state.last }">
				<hr style="width:100%;"/>
			</c:if>
		</c:forEach>
		</div>
	</body>
	<footer>
		<input type="button" value="反馈" style="align: center;width: 40%;" onclick="feedback();"/>
		<input type="button" value="关闭" style="align: center;width: 40%;" onclick="window.close();"/>
	</footer>
</html>