<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="titlebar">
	<font>系统信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">登记人</font>
<font class="label-right">
	${obj.createrName }
</font>
<hr/>
<font class="label-left">登记部门</font>
<font class="label-right">
	${obj.createrDept }
</font>
<hr/>
<font class="label-left">登记时间</font>
<font class="label-right">
	<fmt:formatDate value="${obj.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
</font>
<hr/>
<font class="label-left">最后修改时间</font>
<font class="label-right">
	<fmt:formatDate value="${obj.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
</font>