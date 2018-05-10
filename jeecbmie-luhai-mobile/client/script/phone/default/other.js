/*
 *	ExMobi4.x+ JS
 *	Version	: 1.0.0
 *	Author	: cuizq0907
 *	Email	:
 *	Weibo	:
 *	Copyright 2016 (c)
 */
function $(id){
	return document.getElementById(id);
}

function viewChildList(value, divID, buttonID) {
	var divObj, viewButton;
	if (divID == null) {
		divObj = document.getElementById("childList");
	} else {
		divObj = document.getElementById(divID);
	}
	if (buttonID == null) {
		viewButton = document.getElementById("viewButton");
	} else {
		viewButton = document.getElementById(buttonID);
	}
	if (divObj.style.display == 'none') {//如果show是隐藏的
		viewButton.value = "隐藏" + value + "信息（-）";
		divObj.style.display = "block";
	} else {//如果show是显示的
		viewButton.value = "显示" + value + "信息（+）";
		divObj.style.display = "none";
	}
}

function booleanString(obj) {
	if (typeof obj == "string") {
		if (obj == "true") {
			return true;
		} else if (obj == "false") {
			return false;
		} else {
			return false;
		}
	} else if (typeof obj == "boolean") {
		return obj;
	} else {
		return Boolean(obj);
	}
}

function createTooltip(message) {
	var toast= new Toast();
	toast.setText(message);
	toast.show();
}

var progressBar = new ProgressBar();//进度条

function $ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress) {
	//构建Ajax对象
	var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
	//发送请求
	ajax.send();
	progressBar.show(true);
}

function successFunction(response) {
	if (response.status == 200) {
		if (response.responseText == "responseMobileSessionTimeOut") {
			ClientUtil.execScript("script:reloadapp");
			return;
		}
		if (response.responseText.length > 0) {
			window.openData(response.responseText, true, false);
		}
	}
	progressBar.cancel();
}

function failFunction(response) {
	progressBar.cancel();
	var documentObj = response.responseToXml();
	if (documentObj != null && documentObj.objName == "xmldocument") {
		var rootElement = documentObj.getRootElement();
		var elementArray = rootElement.childNodes;
		var code;
		for (var i = 0; i < elementArray.length; i++) {
			if (elementArray[i].name == "faultcode") {
				code = elementArray[i].text;
			}
		}
		if (code == 1032) {
			alert("会话超时，返回应用首页！");
			ClientUtil.execScript("script:reloadapp");
		} else if (code == 7001) {
			alert("连接服务器失败，请联系管理员！");
		} else if (code == 5017) {
			alert("缺少相应的应用处理页面！");
		} else if (code == 5007) {
			alert("请求业务系统失败!");
		}
	}
}

var timeid;
function getTimeOutObj(id) {
	var obj = $(id);
	if (obj == null) {
		timeid = window.setTimeout("getTimeOutObj(" + id + ")", 1000);
	} else {
		if (timeid != null) {
			window.clearTimeout(timeid);
		}
		return obj;
	}
}

window.setInterval("loginQuartz()", 300000);
function loginQuartz() {

	var username = document.cache.getCache("username");
	var password = document.cache.getCache("password");
	
	if (username == null || password == null) {
		ClientUtil.execScript("script:reloadapp");
	} else {
		//设置提交地址
		var url = "http://luhai/a/login";
		//设置提交方式
		var method = "POST";
		//设置发送数据
		var data = "username=" + username + "&password=" + password;
		//设置自定义http头 json格式
		var requestHeader = '{"Content-Type":"application/x-www-form-urlencoded; "Accept-Language":"zh-cn","Accept-Encoding": "gzip, deflate"}';
		//设置进度条显示
		var isShowProgress = false;
		//构建Ajax对象
		var ajax = new Ajax(url, method, data, successFunction, null, requestHeader, isShowProgress);
		//发送请求
		ajax.send();
	}

}
