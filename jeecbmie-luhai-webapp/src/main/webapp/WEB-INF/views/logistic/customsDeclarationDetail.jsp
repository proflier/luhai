<%@page import="com.cbmie.lh.logistic.entity.CustomsDeclaration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<style>
</style>
</head>

<body>
<input type="hidden" id="id" name="id" value="${customsDeclaration.id }" />
<div class="easyui-tabs" data-options="border:false">
	<div data-options="title: '报关单信息', iconCls: false, refreshable: false" >	
		<fieldset class="fieldsetClass" >
		<legend>报关单信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th>船名</th>
				<td colspan="3">
					${customsDeclaration.shipNo }
				</td>
				<th>海关编号</th>
				<td colspan="3">
					${customsDeclaration.customsCode }
				</td>
			</tr>
			<tr>
				<th>收发货人</th>
				<td>
					${customsDeclaration.consignee }
				</td>
				<th>进口口岸</th>
				<td>
					${customsDeclaration.importPort }
				</td>
				<th>进口日期</th>
				<td>
					<fmt:formatDate value='${customsDeclaration.importDate }'/>
				</td>
				<th>申报日期</th>
				<td>
					<fmt:formatDate value='${customsDeclaration.declarationDate }'/>
				</td>
			</tr>
			<tr>
				<th>消费使用单位</th>
				<td>
					${customsDeclaration.consumptionUnit }
				</td>
				<th>运输方式</th>
				<td>
					${fns:getDictLabelByCode(customsDeclaration.transportation, 'YSFS', '')}
				</td>
				<th>运输工具名称</th>
				<td>
					${customsDeclaration.transportName }
				</td>
				<th>提运单号</th>
				<td>
					${customsDeclaration.deliveryNumber }
				</td>
			</tr>
			<tr>
				<th>申报单位</th>
				<td>
					${customsDeclaration.applicationUnit }
				</td>
				<th>监管方式</th>
				<td>
					${fns:getDictLabelByCode(customsDeclaration.regulatoryWay, 'REGULATORYWAY', '')}
				</td>
				<th>征免性质</th>
				<td>
					${fns:getDictLabelByCode(customsDeclaration.natureExemption, 'NATUREEXEMPTION', '')}
				<th>备案号</th>
				<td>
					${customsDeclaration.record }
				</td>
			</tr>
			<tr>
				<th>贸易国（地区）</th>
				<td>
					${my:getCountryArea(customsDeclaration.tradeNation).name}
				</td>
				<th>启运国（地区）</th>
				<td>
					${my:getCountryArea(customsDeclaration.beginNation).name}
				</td>
				<th>装货港</th>
				<td>
					${customsDeclaration.loadingPort }
				</td>
				<th>境内目的地</th>
				<td>
					${customsDeclaration.domesticDestination }
				</td>
			</tr>
			<tr>
				<th>许可证号</th>
				<td>
					${customsDeclaration.license }
				</td>
				<th>成交方式</th>
				<td>
					${fns:getDictLabelByCode(customsDeclaration.termsDelivery, 'TERMSDELIVERY', '')}
				</td>
				<th>运费</th>
				<td>
					${customsDeclaration.freightFee }
				</td>
				<th>保费</th>
				<td>
					${customsDeclaration.premium }
				</td>
			</tr>
			<tr>
				<th>杂费</th>
				<td>
					${customsDeclaration.pettyFee }
				</td>
				<th>合同协议号</th>
				<td>
					${customsDeclaration.contractNo }
				</td>
				<th>件数</th>
				<td>
					${customsDeclaration.number }
				</td>
				<th>包装种类</th>
				<td>
					${fns:getDictLabelByCode(customsDeclaration.packageType, 'PACKAGETYPE', '')}
				</td>
			</tr>
			<tr>
				<th>毛重</th>
				<td>
					${customsDeclaration.grossWeight }
				</td>
				<th>净重</th>
				<td>
					${customsDeclaration.suttle }
				</td>
				<th>集装箱号</th>
				<td>
					${customsDeclaration.containerNo }
				</td>
				<th>随附单证</th>
				<td>
					${customsDeclaration.accompanyingDocument }
				</td>
			</tr>
			<tr>
				<th>代理公司</th>
				<td colspan="3">
					${my:getAffiBaseInfoByCode(customsDeclaration.agencyCompany)}
				</td>
				<th>海关放行日期</th>
				<td>
					<fmt:formatDate value='${customsDeclaration.customsReleaseDate }'/>
				</td>
				<th>商检放行日期</th>
				<td>
					<fmt:formatDate value='${customsDeclaration.inspectionReleaseDate }'/>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
		<legend>系统信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th width="20%">登记人</th>
				<td width="30%">
					${customsDeclaration.createrName }
				</td>
				<th>登记部门</th>
				<td>${customsDeclaration.createrDept }</td>
			</tr>
			<tr>	
				<th>登记时间</th>
				<td>
					<fmt:formatDate value="${customsDeclaration.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>最后修改时间</th>
				<td>
					<fmt:formatDate value="${customsDeclaration.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</table>
		</fieldset>
	</div>
	<div data-options="title: '商品信息', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/logistic/customsDeclarationGoodsList.jsp"%>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>
</div>	
<script type="text/javascript">
$(function() {
});
</script>
</body>
</html>
