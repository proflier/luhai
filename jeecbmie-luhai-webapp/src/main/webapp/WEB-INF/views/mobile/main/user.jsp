<%@ page contentType="application/uixml+xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ip" value="${pageContext.request.localAddr}" />
<c:set var="post" value="${pageContext.request.serverPort}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="titlebar">
	<font>基本信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font style="text-align:left;margin:8 5%;">头像</font>
<img src="http://${ip}:${post}${ctx}/accessory/avatarView/${user.img}" defaultsrc="res:image/main4/user.png" style="align:right;margin:8 5%;width:100;"/>
<hr/>
<font class="label-left">用户名</font>
<font class="label-right">
	${user.loginName}
</font>
<hr/>
<font class="label-left">姓名</font>
<font class="label-right">
	${user.name}
</font>
<hr/>
<font class="label-left">性别</font>
<font class="label-right">
	${user.gender eq 1 ? "男" : "女"}
</font>
<hr/>
<font class="label-left">出生日期</font>
<font class="label-right">
	<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>
</font>
<hr/>
<font class="label-left">部门</font>
<font class="label-right">
	${user.organization.orgName}
</font>
<hr/>
<font class="label-left">Email</font>
<font class="label-right">
	${user.email}
</font>
<hr/>
<font class="label-left">电话</font>
<font class="label-right">
	${user.phone}
</font>
<hr/>
<font class="label-left">描述</font>
<font class="label-right">
	${user.description}
</font>
<div class="titlebar">
	<font>系统信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">登录次数</font>
<font class="label-right">
	${user.loginCount}
</font>
<hr/>
<font class="label-left">最后一次登录</font>
<font class="label-right">
	<fmt:formatDate value="${user.previousVisit}" pattern="yyyy-MM-dd HH:mm:ss"/>
</font>