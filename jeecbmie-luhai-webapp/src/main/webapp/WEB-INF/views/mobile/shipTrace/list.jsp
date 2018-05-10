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

			function show(id) {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/shipTrace/detail/" + id;
				//设置提交方式
				var method = "GET";
				//设置发送数据
				var data = "";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				$ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
			}

			//当前页码
			var page = 1;
			//每页行数
            var rows = 10;
			//获取List对象
            var list = document.getElementById("shipTraceList");
            //获取ListData对象
            var dp = list.getDataProvider();

            function initList() {
            	//设置提交地址
            	var url = "http://${ip}:${post}${ctx}/mobile/shipTrace/initList";
            	//设置提交方式
            	var method = "POST";
            	//设置发送数据
            	var data = "page=" + page + "&rows=" + rows + "&filter_LIKES_shipNo_OR_shipName="+ $("shipNo_OR_shipName").value;
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
            		var data = ClientUtil.stringToJson(response.responseText);
            		for (i = 0; i < data.length; i++) {
			            var myJSONObject = {
			            	"listitem":{
			            		"href":"javascript:show(" + data[i].id + ");"
			            	},
			            	"title":{
			            		"text":"【" + data[i].shipNo + "】" + data[i].shipName
			            	},
			            	"detail":{
			            		"text":data[i].innerContractNo
			            	}
			            };
			            dp.appendItemObj(myJSONObject);
            		}
            		dp.refreshData();
					page++;
					progressBar.cancel();
            	}
            }

			function cx() {
				page = 1;
				//清空列表
				dp.clear();
				initList();
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="船舶动态" iconhref="window.close();" hidericon="true"/>
		<input type="text" id="shipNo_OR_shipName" prompt="船编号或船名" hideborder="false" style="width:75%;"/>
		<input type="button" value="查询" style="width:20%;align:right;" onclick="cx();"/>
	</header>
	<body onload="initList();" backbind="window.close();" onl2rside="window.close();">
		<list id="shipTraceList" onscrollbottom="initList();">
			<item data-role="listheader">
				<hr/>
			</item>
			<item data-role="listitem" style="background-click-color:#e5e5e5">
				<br size="8"/>
				<font id="title" style="font-weight:bold;">船编号、船名</font>
				<br size="4"/>
				<font id="detail" style="color:#5f5f5f;font-size:16;">内部合同号</font>
				<br size="8"/>
				<hr/>
			</item>
			<item data-role="listfooter">
	            <hr/>
	        </item>
	        <item data-role="listbottomdrag">
	        	<img src="res:image/list/down.png" style="margin:0 0 0 20"/>
	        	<font style="font-size:small;color:gray;margin:0 0 0 40">上拉可以刷新</font>
	        </item>
	        <item data-role="listbottomrelease">
	        	<img src="res:image/list/up.png" style="margin:0 0 0 20"/> 
	        	<font style="font-size:small;color:gray;margin:0 0 0 40">松开即可刷新</font>
	        </item>
	        <item data-role="listtoprefresh">
			    <img src="res:image/list/timg.gif" style="margin:0 0 0 20"/>
			    <font style="font-size:small;color:gray;margin:0 0 0 40">加载中......</font>
			</item>
	        <item data-role="listbottomrefresh">
	            <img src="res:image/list/timg.gif" style="margin:0 0 0 20"/>
	            <font style="font-size:small;color:gray;margin:0 0 0 40">加载中......</font>
	        </item>
		</list>
	</body>
	<footer></footer>
</html>