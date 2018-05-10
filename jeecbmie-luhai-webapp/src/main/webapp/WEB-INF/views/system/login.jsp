<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
request.setAttribute("error", error);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	
	<title>登录</title>
	<meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<link rel="icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />

    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/bglogin.css" />
    <script src="${ctx}/static/plugins/easyui/jquery/jquery-1.11.1.min.js"></script>
    <style type="text/css">
        body, h1, h2, h3, h4, h5, h6, p, input, select {font: 100% Microsoft Yahei,arial;padding: 0;margin: 0;}
        input{border-style: none;outline: none;}
        *{margin:0;padding:0;}
        a{text-decoration: none;}
        a:hover{cursor: pointer;color:#c82a29;}
        ul,ol,li{list-style: none;}
        img{border: none;}
        .lh_login{width: 450px;height: 350px;background: #fff;border: 1px solid #ddd;margin:auto}
        body{background:url("${ctx}/static/images/lhlogin_1.jpg");background-position: center center;background-size: 100% 100%;background-attachment: fixed;background-repeat: no-repeat }
        .lh_login form ul{border: 1px solid #d5d5d5;border-radius: 5px;width: 300px;height: 50px;line-height:50px;margin-left: 75px;margin-top: 30px;}
        .lh_login form ul input{width: 185px;height: 22px;line-height: 22px;color: #666;padding-left: 15px;margin: 10px 0 10px 0;font-size: 19px}
        .lh_login form ul button.submit_but{width: 185px;height: 22px;line-height: 22px;color: #666;padding-left: 15px;margin: 10px 0 10px 0;font-size: 19px;border:none}
        p.copyright{text-align: center;font-family: sans-serif Arial;font-size: 12px;color: #fff;margin-top: 5px;}
    </style>
    
	<script>
	var captcha;
	function refreshCaptcha(){  
	    document.getElementById("img_captcha").src="${ctx}/static/images/kaptcha.jpg?t=" + Math.random();  
	}  
	</script>
	    
</head>

<body style="width:100%;height:100%;position:absolute;top:0;left:0;overflow:hidden">

	<p style="width: 450px;margin: 8% auto 30px;font-size: 38px;text-align: center;color: #24abed;"><b style="color: #fff">中建材陆海投资</b>ERP系统</p>
	
    <div class="lh_login">
        <img src="${ctx}/static/images/lhlogin_2.png" style="margin: 37px 0 0 75px"/>
        <form id="loginForm" action="${ctx}/a/login" method="post">
            <ul>　　用户名 :<input type="text" id="username" name="username"/></ul>
            <ul>　　密　码 :<input type="password" id="password" name="password" value=""/></ul>
            <div class="login_main_errortip">&nbsp;</div>
            <ul style="background: #24abed">
            	<div class="login_main_submit">
                	<button class="submit_but" style="color: #fff; background: #24abed;width:100%;padding-left: 0;height: 100%;line-height: 50px;margin: 0;border-radius: 5px" onclick="">登录</button>
                </div>
            </ul>
        </form>
    </div>
    <div style="width: 450px;border: 0px solid #ddd;margin:auto;text-align: center;padding-top: 10px;color:white;font-size: 14px;">
    	 <div style="display:inline-block;">苹<br/>果<br/>下<br/>载</div><img src="${ctx}/static/images/IOS.gif" style="width:80px;hight:80px;margin-right: 20px;"/>
    	 <div style="display:inline-block;">安<br/>卓<br/>下<br/>载</div><img src="${ctx}/static/images/android.gif" style="width:80px;hight:80px;"/>
    </div>
	<p class="copyright">Copyright　© 2017　中建材集团进出口公司　技术支持：010-68796804</p>

	<c:choose>
		<c:when test="${error eq 'com.cbmie.system.utils.CaptchaException'}">
			<script>
				$(".login_main_errortip").html("验证码错误，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'com.cbmie.system.utils.PhonePermissionException'}">
			<script>
				$(".login_main_errortip").html("没有手机登陆权限，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'org.apache.shiro.authc.UnknownAccountException'}">
			<script>
				$(".login_main_errortip").html("用户名不存在，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'org.apache.shiro.authc.IncorrectCredentialsException'}">
			<script>
				$(".login_main_errortip").html("密码错误，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'com.cbmie.system.utils.UserEffectException'}">
			<script>
				$(".login_main_errortip").html("当前账号已停用，请联系管理员");
			</script>
		</c:when>
	</c:choose>
	
</body>
</html>
