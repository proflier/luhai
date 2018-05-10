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
<font class="label-left">销售合同号</font>
<font class="label-right">
	${obj.contractNo }
</font>
<hr/>
<font class="label-left">卖方</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.seller)}
</font>
<hr/>
<font class="label-left">买方</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.purchaser)}
</font>
<hr/>
<font class="label-left">销售方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.saleMode,'SALESMODE','')}
</font>
<hr/>
<font class="label-left">业务类型</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.manageMode,'BUSINESSTYPE','')}
</font>
<hr/>
<font class="label-left">数量溢短装</font>
<font class="label-right">
	${obj.numMoreOrLess }${empty obj.numMoreOrLess ? "" : "%" }
</font>
<hr/>
<font class="label-left">客户合同号</font>
<font class="label-right">
	${obj.customerContractNo }
</font>
<hr/>
<font class="label-left">交货起期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.deliveryStartDate }" pattern="yyyy-MM-dd"/>
</font>
<hr/>
<font class="label-left">交货止期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.deliveryEndDate }" pattern="yyyy-MM-dd"/>
</font>
<hr/>
<font class="label-left">交货方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.deliveryMode,'THFS','')}
</font>
<hr/>
<font class="label-left">交款方式</font>
<font class="label-right">
	${fns:getDictLabelsByCodes(obj.settlementMode,'SJKFS','')}
</font>
<hr/>
<font class="label-left">交货地点</font>
<font class="label-right">
	${obj.deliveryAddr }
</font>
<hr/>
<font class="label-left">船编号</font>
<font class="label-right">
	${obj.ship }
</font>
<hr/>
<font class="label-left">合同数量</font>
<font class="label-right">
	${obj.contractQuantity }
</font>
<hr/>
<font class="label-left">合同金额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.contractAmount }" />
</font>
<hr/>
<font class="label-left">业务经办人</font>
<font class="label-right">
	${empty obj.businessManager ? "" : my:getUserByLoginName(obj.businessManager).name}
</font>
<hr/>
<font class="label-left">数量结算依据</font>
<font class="label-right">
	${my:unescapeHtml(obj.numSettlementBasis)}
</font>
<hr/>
<font class="label-left">质量结算依据</font>
<font class="label-right">
	${my:unescapeHtml(obj.qualitySettlementBasis)}
</font>
<hr/>
<font class="label-left">风险提示</font>
<font class="label-right">
	${my:unescapeHtml(obj.riskTip)}
</font>
<div class="titlebar">
	<font>商品信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="goods" items="${obj.saleContractSubList }" varStatus="state">
	<font class="label-left">品名</font>
	<font class="label-right">
		${my:getGoodsInformationByCode(goods.goodsName).infoName}
	</font>
	<hr/>
	<font class="label-left">单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${goods.price}" />
	</font>
	<hr/>
	<font class="label-left">数量</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${goods.goodsQuantity}" />
	</font>
	<hr/>
	<font class="label-left">金额</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${goods.amount}" />
	</font>
	<hr/>
	<div class="centershow">
		<font size="18">指标信息</font>
		<hr/>
	</div>
	<c:forEach var="index" items="${goods.goodsIndexList }" varStatus="status">
		<font class="label-left" size="15">指标 </font>
		<font class="label-right" size="15">
			${my:getGoodsIndex(index.indexName).indexName}
		</font>
		<hr color="#DDDDDD" style="width:90%"/>
		<font class="label-left" size="15">约定值 </font>
		<font class="label-right" size="15">
			${index.conventions}
		</font>
		<hr color="#DDDDDD" style="width:90%"/>
		<font class="label-left" size="15">拒收值  </font>
		<font class="label-right" size="15">
			${index.rejectionValue}
		</font>
		<hr color="#DDDDDD" style="width:90%"/>
		<font class="label-left" size="15">扣罚条款 </font>
		<font class="label-right" size="15">
			${index.terms}
		</font>
		<c:if test="${!status.last }">
			<hr color="#DDDDDD" size="2" style="width:100%"/>
		</c:if>
	</c:forEach>
<c:if test="${!state.last }">
	<hr color="#DDDDDD" size="5" style="width:100%"/>
</c:if>
</c:forEach>
