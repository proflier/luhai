<%@page import="com.cbmie.genMac.foreignTrade.entity.AgencyAgreement" %>
<%@page import="com.cbmie.genMac.foreignTrade.entity.AgreementGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div class="easyui-tabs" data-options="border: false">
<div data-options="title: '协议信息', iconCls: false, refreshable: false">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<!--移动端截取标志1-->
<table width="98%" class="tableClass">
	<tr>
		<th>代理协议号</th>
		<td>
		${agencyAgreement.agencyAgreementNo }
		</td>
		<th>协议类型</th>
		<td>
		${agencyAgreement.agreementType }
		</td>
		<th>我司单位</th>
		<td id="ourUnit"></td>
	</tr>
	<tr>
		<th>业务员</th>
		<td>
		${agencyAgreement.salesman }
		</td>
		<th>签署日期</th>
		<td>
		<fmt:formatDate value="${agencyAgreement.signingDate }" />
		</td>
		<th>生效日期</th>
		<td>
		<fmt:formatDate value="${agencyAgreement.effectDate }" />
		</td>
	</tr>
	<tr>
		<th>是否是保税区业务</th>
		<td>
		${agencyAgreement.bondedArea == '1' ? '是' : '否' }
		</td>
		<th>签约地</th>
		<td colspan="3">
		${agencyAgreement.signedPlace }
		</td>
	</tr>
</table>
<!--移动端截取标志1-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>委托方信息</legend>
<!--移动端截取标志2-->
<table width="98%" class="tableClass">
	<tr>
		<th>委托方</th>
		<td colspan="3">
		${agencyAgreement.client }
		</td>
		<th>所属地区</th>
		<td id="district">
		</td>
	</tr>
	<tr>
		<th>代理费方式</th>
		<td colspan="3">
		${agencyAgreement.agencyFeeWay }
		</td>
		<th>手续费基数</th>
		<td>
		<fmt:formatNumber type="number" value="${agencyAgreement.commissionBase }" />
		</td>
	</tr>
	<tr>
		<th>每美元多少人民币</th>
		<td>
		${agencyAgreement.everyDollarToRMB }
		</td>
		<th>代理费率%</th>
		<td>
		${agencyAgreement.agencyFee }<c:if test="${agencyAgreement.agencyFee != null}">%</c:if>
		</td>
		<th>代理费收入</th>
		<td>
		<fmt:formatNumber type="number" value="${agencyAgreement.agencyFeeIncome }" />
		</td>
	</tr>
	<tr>
		<th>国内保证金比例%</th>
		<td>
		${agencyAgreement.domesticMarginRatio }<c:if test="${agencyAgreement.domesticMarginRatio != null}">%</c:if>
		</td>
		<th>发函人</th>
		<td id="sentLetterPerson">
		</td>
		<th>其他保证金说明</th>
		<td>
		${agencyAgreement.otherMarginExplain }
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
		${agencyAgreement.currency }
		</td>
		<th>对人民币汇率</th>
		<td>
		${agencyAgreement.rmbRate }
		</td>
		<th>对美元汇率</th>
		<td>
		${agencyAgreement.dollarRate }
		</td>
	</tr>
</table>
<!--移动端截取标志2-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<!--移动端截取标志3-->
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${agencyAgreement.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${agencyAgreement.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${agencyAgreement.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<!--移动端截取标志3-->
</fieldset>
</div>
<div data-options="title: '协议商品', iconCls: false, refreshable: false">
<table id="agreementGoodsTB"></table>
<%
	AgencyAgreement agencyAgreement = (AgencyAgreement)request.getAttribute("agencyAgreement");
	List<AgreementGoods> agreementGoods = agencyAgreement.getAgreementGoods();
	ObjectMapper objectMapper = new ObjectMapper();
	String agreementGoodsJson = objectMapper.writeValueAsString(agreementGoods);
	request.setAttribute("agreementGoodsJson", agreementGoodsJson);
%>
</div>
</div>
<script type="text/javascript">
$.ajax({
	url : '${ctx}/system/countryArea/getName/${agencyAgreement.district }',
	type : 'get',
	cache : false,
	success : function(data) {
		$('#district').text(data);
	}
});

$.ajax({
	url : '${ctx}/system/user/json?filter_EQI_id=${agencyAgreement.sentLetterPerson }',
	type : 'get',
	cache : false,
	dataType : 'json',
	success : function(data) {
		$('#sentLetterPerson').text((data.rows)[0].name);
	}
});

$.ajax({
	url : '${ctx}/baseinfo/affiliates/json?filter_EQL_id=${agencyAgreement.ourUnit }',
	type : 'get',
	cache : false,
	dataType : 'json',
	success : function(data) {
		$('#ourUnit').text((data.rows)[0].customerName);
	}
});

$(function(){
	$('#agreementGoodsTB').datagrid({
		data : JSON.parse('${agreementGoodsJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		frozenColumns:[[
			{field:'goodsCategory',title:'商品大类',width:100},
			{field:'specification',title:'规格型号',width:100},
			{field:'origin',title:'原产地',width:100},
			{field:'cname',title:'中文名',width:100},
			{field:'ename',title:'英文名',width:100}
		]],
	    columns:[
			[
			{"title":"合同","colspan":3},  
			{"title":"合同金额","colspan":2}
			],
			[
			{field:'amount',title:'数量',width:20},
			{field:'unit',title:'单位',width:20},
			{field:'price',title:'单价',width:20},
			{field:'original',title:'原币金额',width:40,
				formatter: function(value, row, index){
					if(value != null){
						var str = value.toString().split(".");
						return str[0].replace(/\B(?=(?:\d{3})+$)/g, ',') + (str.length == 1 ? "" : "." + str[1]);
					}
				}	
			},
			{field:'rmb',title:'人民币金额',width:40,
				formatter: function(value, row, index){
					if(value != null){
						var str = value.toString().split(".");
						return str[0].replace(/\B(?=(?:\d{3})+$)/g, ',') + (str.length == 1 ? "" : "." + str[1]);
					}
				}	
			}
			]
		],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>
</body>
</html>