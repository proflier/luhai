<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<div class="titlebar">
	<font>基本信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<font class="label-left">开证申请号</font>
<font class="label-right">
	${obj.payApplyNo }
</font>
<hr/>
<font class="label-left">开证类型</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.creditCategory,'creditCategory','')}
</font>
<hr/>
<font class="label-left">内部合同号</font>
<font class="label-right">
	${obj.inteContractNo }
</font>
<hr/>
<font class="label-left">采购合同号</font>
<font class="label-right">
	${obj.contractNo }
</font>
<hr/>
<font class="label-left">开证单价</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.unitPrice }" />
</font>
<hr/>
<font class="label-left">开证数量</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.quantity }" />
</font>
<hr/>
<font class="label-left">开证金额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.applyMoney }" />
</font>
<hr/>
<font class="label-left">币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.currency,'currency','')}
</font>
<hr/>
<font class="label-left">溢短装</font>
<font class="label-right">
	${obj.moreOrLess }${empty obj.moreOrLess ? "" : "%" }
</font>
<hr/>
<font class="label-left">汇率</font>
<font class="label-right">
	${obj.exchangeRate }
</font>
<hr/>
<font class="label-left">客户保证金</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.handelRecognizance }" />
</font>
<hr/>
<font class="label-left">已到帐保证金</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.arrivalRecognizance }" />
</font>
<hr/>
<font class="label-left">保证金币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.recognizanceCurrency,'currency','')}
</font>
<hr/>
<font class="label-left">希望使用银行</font>
<font class="label-right">
	obj.bankExpects
</font>
<hr/>
<font class="label-left">授信类型</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.creditType,'sxlx','')}
</font>
<hr/>
<font class="label-left">开证单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.ourUnit)}
</font>
<hr/>
<font class="label-left">有效期天数</font>
<font class="label-right">
	${obj.usageTime }天
</font>
<hr/>
<font class="label-left">收证单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.receiveUnit)}
</font>
<hr/>
<font class="label-left">供应商</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.supplier)}
</font>
<hr/>
<font class="label-left">进口国别</font>
<font class="label-right">
	${my:getCountryArea(obj.importingCountry).name}
</font>
<hr/>
<font class="label-left">国内客户 </font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.internalClient)}
</font>
<hr/>
<font class="label-left">经营方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.runMode,'runMode','')}
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.comment)}
</font>