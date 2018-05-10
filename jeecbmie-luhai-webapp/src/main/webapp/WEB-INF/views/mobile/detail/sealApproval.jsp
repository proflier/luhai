<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<div class="titlebar">
	<font>用印信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">印章公司</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.contractCode,'sealCompany','')}
</font>
<hr/>
<font class="label-left">印章类别</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.signetCode,'SIGNETTYPE','')}
</font>
<hr/>
<font class="label-left">用印方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.sealCode,'useSeal','')}
</font>
<hr/>
<font class="label-left">申请人</font>
<font class="label-right">
	${obj.applicantInformation}
</font>
<hr/>
<font class="label-left">申请人单位</font>
<font class="label-right">
	${obj.applicantUnit }
</font>
<hr/>
<font class="label-left">用印日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.singnDate }" />
</font>
<hr/>
<font class="label-left">份数</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.copies }" />
</font>
<hr/>
<font class="label-left">文件名及主要内容</font>
<font class="label-right">
	${obj.fileNameMainContents }
</font>
<hr/>
<font class="label-left">事由及提交单位</font>
<font class="label-right">
	${obj.causeSubmission }
</font>