<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
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
function send() {

	var nextId = getchecked("nextId");
	if(nextId=="" || nextId==undefined){
		createTooltip("请选择审核动作");
		return false;
	}

	var formObj = document.getElementById("process_audit_form");

	formObj.action = "http://${ip}:${post}${ctx}/mobile/taskTodo/send";

	formObj.enctype = "application/x-www-form-urlencode";

	formObj.method = "post";

	formObj.target = "_blank";

	formObj.isHideProcess = true;

	formObj.success = successFunction;

	formObj.fail = failFunction;

	formObj.submit();

}

function successFunction(response) {

	if (response.status == 200) {
		if (response.responseText == "responseMobileSessionTimeOut") {
			ClientUtil.execScript("script:reloadapp");
			return;
		}
		createTooltip("发送成功");
		window.openData(response.responseText, true, false);
		var listWindow = PageUtil.getWindowById("listWindow");
		var todoWindow = PageUtil.getWindowById("todoWindow");
		listWindow.close();
		todoWindow.close();
		window.close();
	}

}

function getchecked(name) {
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			return obj[i].value;
		}
	}
	return "";
}

function getSelected(id) {
	var obj = document.getElementById(id);
	var index = obj.selectedIndex;
	var text = obj.options[index].text;
	var value = obj.options[index].value;
	return value;
}

function getDealer() {
	var taskId = ${approval.taskId};
	var nextId = getchecked("nextId");
	var nextType = "1";

	var activitiTypeId = document.getElementById("activitiTypeId");
	if(activitiTypeId != null && activitiTypeId.checked == true && activitiTypeId.value != undefined){
		nextType = "2";
	}
	var applyTypeId = document.getElementById("applyTypeId");
	if(applyTypeId != null && applyTypeId.checked == true && applyTypeId.value != undefined){
		nextType = "2";
	}

	var actionType = document.getElementById("actionType");
	actionType.value = nextType;
	
	//设置提交地址
    var url = "http://${ip}:${post}${ctx}/workflow/task/prepareDealer/"+taskId+"/"+nextId+"/"+nextType;
    //设置提交方式
    var method = "GET";
    //设置发送数据
    var data = "";
    //设置自定义http头 json格式
    var requestHeader = "";
    //设置进度条显示
    var isShowProgress = false;
    //构建Ajax对象
    var ajax = new Ajax(url, method, data, successDealerFunction, failFunction, requestHeader, isShowProgress);
    //发送请求
    ajax.send();
}

function successDealerFunction(response) {
	if (response.status == 200) {
	    if (response.responseText == "responseMobileSessionTimeOut") {
	    	ClientUtil.execScript("script:reloadapp");
	    	return;
	    }
        
	    var data_all = ClientUtil.stringToJson(response.responseText);
	    
	    var dealer = document.getElementById('dealerId');
	    var reader = document.getElementById('readerId');
	    
	    if (data_all.dealer != null) {
	    	var data = data_all.dealer;
	    	var str = "";
	    	str += "<input id='directNextId' name='directNextId' type='hidden' value='" + data.directKey + "'/>";
	    	str += "<input id='goId' name='goId' type='hidden' value='" + data.key + "'/>";
	    	if(data.selectType == 'A'){
	    		for( var i in data.selectItems){
	    			str += "<font>" + data.selectItems[i] + "</font>";
					if (i != null && i.length > 0) {
	    				str += "<input type='hidden' name='candidateUserIds' value='" + i + "'/>";
					}
	    		}
	    	}else if(data.selectType == 'S'){
	    		if(data.selectRange == 'C'){
	    			
	    		}else{
	    			var defaultV = null;
	    			for( var i in data.selectItems){
	    				defaultV = i;
	    				break;
	    			}
	    			str += "<select id='candidateUserIds' name='candidateUserIds'>";
	    			for( var i in data.selectItems){
	    				str += "<option value='" + i +"'>" + data.selectItems[i] + "</option>";
	    			}
	    			str += "</select>";
	    		}
	    	}else if(data.selectType == 'M'){
	    		
	    	}
	    	dealer.innerHTML = str;
	    }
	    if (data_all.reader != null) {
	    	var data = data_all.reader;
	    	var str = "";
	    	if(data.selectType == 'A'){
	    		for( var i in data.selectItems){
	    			str += "<font>" + data.selectItems[i] + "</font>";
	    			str += "<input type='hidden' name='passUserIds' value='" + i + "'/>";
	    		}
	    	}else if(data.selectType == 'S'){
	    		if(data.selectRange=='C'){
	    			
	    		}else{
	    			str += "<select id='passUserIds' name='passUserIds'>";
	    			for( var i in data.selectItems){
	    				str += "<option value='" + i +"'>" + data.selectItems[i] + "</option>";
	    			}
	    			str += "</select>";
	    		}
	    	}else if(data.selectType == 'M'){
	    		
	    	}
	    	reader.innerHTML = str;
	    }
	}
}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="办理" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="window.close();">
		<form id="process_audit_form">
		<table class="tableClass">
			<tr>
				<td class="label-left">
					当前节点
					<input type="hidden" id="actionType" name="actionType" value="${approval.actionType}"/>
					<input type="hidden" name="taskId" value="${approval.taskId }" />
					<input type="hidden" name="businessKey" value="${approval.businessKey }" />
					<input type="hidden" name="key" value="${approval.key }" />
					<input type="hidden" name="processInstanceId" value="${approval.processInstanceId }" />
					<input type="hidden" name="loginName" value="${loginName }" />
				</td>
				<td class="label-right">
					${curActivity.properties.name}
				</td>
			</tr>
			<tr>
				<td class="label-left">
					审核动作
				</td>
				<td class="label-right">
					<c:forEach var="back" items="${nexttransitions}">
						<input type="radio" name="nextId" value="${back.id}" caption="${back.properties.name}" onclick="getDealer();"/>
					</c:forEach>
					<c:if test="${backActivity ne null}">
						<input type="radio" id="activitiTypeId" name="nextId" value="${backActivity.id}" caption="驳回上一节点" onclick="getDealer();"/>
						<c:if test="${backActivity.id ne 'apply'}">
							<input type="radio" id="applyTypeId" name="nextId" value="apply" caption="退回起草人" onclick="getDealer();"/>
						</c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="label-left">
					办理人
				</td>
				<td class="label-right">
					<div id="dealerId"></div>
				</td>
			</tr>
			<tr>
				<td class="label-left">
					传阅人
				</td>
				<td class="label-right">
					<div id="readerId"></div>
				</td>
			</tr>
			<tr>
				<td class="label-left">
					优先级
				</td>
				<td class="label-right">
					<input type="radio" name="priority" value="50" checked="true" caption="普通"/>
					<input type="radio" name="priority" value="100" caption="加急"/>
					<input type="radio" name="priority" value="150" caption="紧急"/>
				</td>
			</tr>
			<tr>
				<td class="label-left">
					提醒方式
				</td>
				<td class="label-right">
					<input type="checkbox" name="email" value="1" caption="邮件提醒"/>
					<input type="checkbox" name="sms" value="1" caption="短信提醒"/>
				</td>
			</tr>
			<tr>
				<td class="label-left">
					处理意见
				</td>
				<td class="label-right">
					<textarea id="comments" name="comments" prompt="处理意见" validate="required" validatemsg="处理意见不能为空"></textarea>
				</td>
			</tr>
		</table>
		</form>
	</body>
	<footer>
		<input type="button" value="发送" style="align: center;width: 40%;" onclick="send();"/>
		<input type="button" value="关闭" style="align: center;width: 40%;" onclick="window.close();"/>
	</footer>
</html>