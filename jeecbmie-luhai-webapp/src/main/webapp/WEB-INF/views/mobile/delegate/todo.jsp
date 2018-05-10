<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
			
			function save() {
            	
				var itemArray = $("mandatary").getSelectedItem();
				if (itemArray == null || itemArray.length == 0) {
					createTooltip("请选择受委托人");
					return;
				}

				$("beginDate").value = $("bDate").value + " " + $("bTime").value;
				$("endDate").value = $("eDate").value + " " + $("eTime").value;
            
            	var formObj = document.getElementById("mainForm");
            
            	formObj.action = "http://${ip}:${post}${ctx}/mobile/delegate/save";
            
            	formObj.enctype = "application/x-www-form-urlencode";
            
            	formObj.method = "post";
            
            	formObj.target = "_blank";
            
            	formObj.isHideProcess = true;
            
            	formObj.success = successFunction;
            
            	formObj.fail = failFunction;
            
            	formObj.submit();
            
            }
			
			function successFunction(response){
				if (response.status == 200) {
					if (response.responseText == "responseMobileSessionTimeOut") {
						ClientUtil.execScript("script:reloadapp");
						return;
					}
					progressBar.cancel();
					createTooltip("操作成功");
					window.openData(response.responseText, true, false);
					var listWindow = PageUtil.getWindowById("listWindow");
					listWindow.close();
					window.close();
				}
			}

			function checkProcDefKey(num){
				var treeObj = document.getElementById("procDefKey");
				var itemArray = treeObj.getSelectedItem();
				if (itemArray == null) {
					return;
				}
				if(num == 0){
					for (var i = 0; i < itemArray.length; i++) {
						itemArray[i].isSelected = false;
					}
					treeObj.getTreeItemById("procDefKeyAll").isSelected = true;
				}else{
					treeObj.getTreeItemById("procDefKeyAll").isSelected = false;
				}
			}

			function checkTreeItem(itemId){
				var treeObj = document.getElementById("mandatary");
				var itemArray = treeObj.getSelectedItem();
				if (itemArray == null) {
					return;
				}
				for (var i = 0; i < itemArray.length; i++) {
					itemArray[i].isSelected = false;
				}
				treeObj.getTreeItemById("" + itemId).isSelected = true;
			}

			function stop(){
				$("activateFlag").value = "0";
				save();
			}
			
			]]>
		</script>
	</head>
	<header>
		<titlebar title="流程委托" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="window.close();">
		<form id="mainForm">
			<input type="hidden" name="id" value="${obj.id}"/>
			<div class="titlebar">
				<font>基本信息</font>
				<br size="10"/>
				<hr color="#e34a42" size="3" style="width:30%"/>
			</div>
			<div style="margin:8 5%;">
				<tree id="procDefKey" name="procDefKey" caption="请选择" title="委托模块">
					<item id="procDefKeyAll" caption="全部" value="" checkbox="true" checked="${empty obj.procDefKey ? true : false}" checkboxhref="checkProcDefKey(0);"/>
					<c:forEach var="procIns" items="${my:getAllProcIns()}">
						<item caption="${procIns.value}" value="${procIns.id}" checkbox="true" checked="${fn:contains(obj.procDefKey, procIns.id) ? true : false}" checkboxhref="checkProcDefKey(1);"/>
					</c:forEach>
				</tree>
			</div>
			<hr/>
			<font class="label-left">委托人</font>
			<div class="label-right">
				<input type="hidden" name="consigner" value="${obj.consigner}"/>
				${my:getUserByLoginName(obj.consigner).name}
			</div>
			<hr/>
			<div style="margin:8 5%;">
				${my:getCompanyUser(obj.mandatary)}
			</div>
			<hr/>
			<font class="label-left">委托开始时间</font>
			<div class="label-right">
				<input type="hidden" id="beginDate" name="beginDate" value=""/>
				<object type="date" id="bDate" prompt="日期" isshowclear="true" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${obj.beginDate}'/>" validate="required" validatemsg="委托开始日期不能为空" style="background-color:#ffffff;border-size:1;"/>
				<object type="time" id="bTime" prompt="时间" format="hh:mm:ss" value="<fmt:formatDate pattern='HH:mm:ss' value='${obj.beginDate}'/>" validate="required" validatemsg="委托开始时间不能为空"/>
			</div>
			<hr/>
			<font class="label-left">委托结束时间</font>
			<div class="label-right">
				<input type="hidden" id="endDate" name="endDate" value=""/>
				<object type="date" id="eDate" prompt="日期" isshowclear="true" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${obj.endDate}'/>" validate="required" validatemsg="委托结束日期不能为空" style="background-color:#ffffff;border-size:1;"/>
				<object type="time" id="eTime" prompt="时间" format="hh:mm:ss" value="<fmt:formatDate pattern='HH:mm:ss' value='${obj.endDate}'/>" validate="required" validatemsg="委托结束时间不能为空"/>
			</div>
			<input type="hidden" id="activateFlag" name="activateFlag" value="${obj.activateFlag}"/>
			<%@ include file="../detail/system.jsp"%>
		</form>
	</body>
	<footer>
		<c:choose>
			<c:when test="${obj.consigner eq sessionScope.user.loginName }">
				<input type="button" value="保存" style="align: center;width: 40%;" onclick="save();"/>
				<c:if test="${obj.activateFlag eq '1' && !empty obj.id}">
					<input type="button" value="终止" style="align: center;width: 40%;" onclick="stop();"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<input type="button" value="关闭" style="align: center;width: 40%;" onclick="window.close();"/>
			</c:otherwise>
		</c:choose>
	</footer>
</html>