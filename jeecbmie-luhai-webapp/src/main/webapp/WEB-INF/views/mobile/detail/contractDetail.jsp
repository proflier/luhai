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
<font class="label-left">内部合同号</font>
<font class="label-right">
	${obj.innerContractNo }
</font>
<hr/>
<font class="label-left">采购合同号</font>
<font class="label-right">
	${obj.purchaseContractNo }
</font>
<hr/>
<font class="label-left">供货单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.deliveryUnit)}
</font>
<hr/>
<font class="label-left">进口国别</font>
<font class="label-right">
	${my:getCountryArea(obj.importCountry).name}
</font>
<hr/>
<font class="label-left">价格条款</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.priceTerm,'jgtk','')}
</font>
<hr/>
<font class="label-left">应预付额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.prepaidMoney }" />
</font>
<hr/>
<font class="label-left">订货总量</font>
<font class="label-right">
	${obj.contractQuantity }
</font>
<hr/>
<font class="label-left">数量单位</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.quantityUnit,'sldw','')}
</font>
<hr/>
<font class="label-left">保证金</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.margin }" />
</font>
<hr/>
<font class="label-left">合同金额</font>
<font class="label-right">
	<fmt:formatNumber type="number" value="${obj.contractAmount }" />
</font>
<hr/>
<font class="label-left">币种</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.currency,'currency','')}
</font>
<hr/>
<font class="label-left">签约日期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.signingDate }" />
</font>
<hr/>
<font class="label-left">付款方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.payMode,'paymentMethod','')}
</font>
<hr/>
<font class="label-left">付款类型</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.creditType,'sxlx','')}
</font>
<hr/>
<font class="label-left">交货起期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.deliveryStartDate }" />
</font>
<hr/>
<font class="label-left">交货止期</font>
<font class="label-right">
	<fmt:formatDate value="${obj.deliveryTerm }" />
</font>
<hr/>
<font class="label-left">订货单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.orderUnit)}
</font>
<hr/>
<font class="label-left">收货单位</font>
<font class="label-right">
	${my:getAffiBaseInfoByCode(obj.receivingUnit)}
</font>
<hr/>
<font class="label-left">业务经办人</font>
<font class="label-right">
	${empty obj.businessManager ? "" : my:getUserByLoginName(obj.businessManager).name}
</font>
<hr/>
<font class="label-left">合同类别</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.contractCategory,'htlb','')}
</font>
<hr/>
<font class="label-left">印章类型 </font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.sealCategory,'SIGNETTYPE','')}
</font>
<hr/>
<font class="label-left">印章管理员</font>
<font class="label-right">
	${obj.sealManager }
</font>
<hr/>
<font class="label-left">经营方式</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.runMode,'runMode','')}
</font>
<hr/>
<font class="label-left">签约地点</font>
<font class="label-right">
	${my:getCountryArea(obj.signingPlace).name}
</font>
<hr/>
<font class="label-left">结算条款</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.settlementTerms,'settlementTerms','')}
</font>
<hr/>
<font class="label-left">仲裁地</font>
<font class="label-right">
	${my:getCountryArea(obj.arbitrationPlace).name}
</font>
<hr/>
<font class="label-left">金额溢短装</font>
<font class="label-right">
	${obj.moreOrLessAmount }${empty obj.moreOrLessAmount ? "" : "%" }
</font>
<hr/>
<font class="label-left">数量溢短装</font>
<font class="label-right">
	${obj.moreOrLessQuantity }${empty obj.moreOrLessQuantity ? "" : "%" }
</font>
<hr/>
<font class="label-left">合同金额大写 </font>
<font class="label-right">
	${obj.blockMoney }
</font>
<hr/>
<font class="label-left">装/卸率</font>
<font class="label-right">
	${obj.unloadingRate }
</font>
<hr/>
<font class="label-left">受载期起始时间</font>
<font class="label-right">
	<fmt:formatDate value="${obj.transportStartDate }" />
</font>
<hr/>
<font class="label-left">受载期终止时间</font>
<font class="label-right">
	<fmt:formatDate value="${obj.transportTermDate }" />
</font>
<hr/>
<font class="label-left">检验机构</font>
<font class="label-right">
	${fns:getDictLabelByCode(obj.checkOrg,'inspectionAgency','')}
</font>
<hr/>
<font class="label-left">检验标准</font>
<font class="label-right">
	${my:unescapeHtml(obj.checkStandard)}
</font>
<hr/>
<font class="label-left">备注</font>
<font class="label-right">
	${my:unescapeHtml(obj.comment)}
</font>
<div class="titlebar">
	<font>采购信息</font>
	<br size="10"/>
	<hr color="#e34a42" size="3" style="width:30%"/>
</div>
<c:forEach var="goods" items="${obj.purchaseContractGoodsList }" varStatus="state">
	<font class="label-left">品名</font>
	<font class="label-right">
		${my:getGoodsInformationByCode(goods.goodsName).infoName}
	</font>
	<hr/>
	<font class="label-left">采购单价</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${goods.purchasePrice}" />
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
	<c:forEach var="index" items="${goods.purchaseGoodsIndexList }" varStatus="status">
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