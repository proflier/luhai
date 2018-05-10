<%@ page contentType="application/uixml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html id="listWindow">
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css" />
		<style type="text/css">
		</style>
		<script type="text/javascript" src="res:script/other.js"></script>
		<script type="text/javascript">
			<![CDATA[

			function show(taskId, processInstanceId, businessKey, key, status) {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/detail";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "taskId=" + taskId + "&processInstanceId=" + processInstanceId + "&businessKey=" + businessKey + "&key=" + key + "&status=" + encodeURI(status) + "&loginName=${loginName}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				$ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
			}

			//获取List对象
            var list = document.getElementById("doList");
            //获取ListData对象
            var dp = list.getDataProvider();

			function initList() {
            	//设置提交地址
            	var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/initList";
            	//设置提交方式
            	var method = "POST";
            	//设置发送数据
            	var data = "loginName=${loginName}";
            	//设置自定义http头 json格式
            	var requestHeader = {};
            	//设置进度条显示
            	var isShowProgress = false;
            	//构建Ajax对象
            	$ajax(url, method, data, initListSuccessFunction, failFunction, requestHeader, isShowProgress);
            }
            
            function initListSuccessFunction(response) {
            	if (response.status == 200) {
					if (response.responseText == "responseMobileSessionTimeOut") {
						ClientUtil.execScript("script:reloadapp");
						return;
					}
					//清空列表
					dp.clear();
            		var data = ClientUtil.stringToJson(response.responseText);
            		for (i = 0; i < data.length; i++) {
			        	
			        	var src;
				        if (data[i].priority == 100) {
				        	src = "res:image/list/priority-2.png";
				        } else if (data[i].priority == 150) {
				        	src = "res:image/list/priority-1.png";
				        } else {
				        	src = "res:image/list/priority-3.png";
				        }
				        
				        var myJSONObject = {
				        	"listitem":{
				        		"href":"javascript:show('" + data[i].id + "', '" + data[i].pid + "', '" + data[i].businessKey + "', '" + data[i].key + "', '" + data[i].status + "');"
				        	},
				        	"title":{
				        		"text":data[i].businessInfo
				        	},
				        	"detail":{
				        		"text":"办理节点：" + data[i].name + " | " + "流程名称：" + data[i].pdname
				        	},
				        	"priority":{
				        		"src":src
				        	}
				        };
				        dp.appendItemObj(myJSONObject);

            		}
            		dp.refreshData();
					progressBar.cancel();
            	}
            }

			]]>
		</script>
	</head>
	<header>
		<titlebar title="待办事项" iconhref="window.close();" hidericon="true"/>
	</header>
	<body onload="initList();" backbind="window.close();" onl2rside="window.close();">
		<list id="doList" onscrolltop="initList();">
			<item data-role="listtopdrag">
				<img src="res:image/list/up.png" style="margin:0 0 0 20"/>
				<font style="font-size:small;color:gray;margin:0 0 0 40">下拉可以刷新</font> 
			</item>
			<item data-role="listtoprelease">
				<img src="res:image/list/down.png" style="margin:0 0 0 20"/> 
				<font style="font-size:small;color:gray;margin:0 0 0 40">松开即可刷新</font>
			</item>
			<item data-role="listtoprefresh">
			    <img src="res:image/list/timg.gif" style="margin:0 0 0 20"/>
			    <font style="font-size:small;color:gray;margin:0 0 0 40">加载中......</font>
			</item>
			<item data-role="listheader">
				<hr/>
			</item>
			<item data-role="listitem">
				<br size="8"/>
				<div style="width:92%;">
					<font id="title" style="font-weight:bold;">业务信息</font>
					<br size="4"/>
					<font id="detail" style="color:#5f5f5f;font-size:16;">办理节点</font>
				</div>
				<img id="priority" style="width:8%"/>
				<br size="8"/>
				<hr/>
			</item>
		</list>
	</body>
	<footer></footer>
</html>