<%@page import="com.cbmie.genMac.foreignTrade.entity.Commodity"%>
<%@page import="com.cbmie.genMac.foreignTrade.entity.ImportContract"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="childTB"></table>
<%
ImportContract importContract = (ImportContract)request.getAttribute("importContract");
List<Commodity> gcList = importContract.getCommodityList();
ObjectMapper objectMapper = new ObjectMapper();
String gcJson = objectMapper.writeValueAsString(gcList);
request.setAttribute("gcJson", gcJson);
%>
<script type="text/javascript">
var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
		data : JSON.parse('${gcJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		frozenColumns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'商品大类',width:80},
			{field:'specification',title:'规格型号',width:80},
			{field:'origin',title:'原产地',width:80},
			{field:'cname',title:'中文名',width:80},
			{field:'ename',title:'英文名',width:80}
		]],
		columns:[
		    [
			{"title":"合同","colspan":3},
			{field:'original',title:'原币金额',rowspan:2,align:'center',width:20},
			{"title":"关税","colspan":2},
			{"title":"消费税","colspan":2},
			{"title":"增值税","colspan":2}
		    ],
		    [
		    {field:'amount',title:'数量',width:20},
			{field:'unit',title:'单位',width:20},
			{field:'price',title:'单价',width:20},
			{field:'tax',title:'关税税额',width:20},
			{field:'taxRate',title:'关税税率',width:20},
			{field:'saleTax',title:'消费税额',width:20},
			{field:'saleTaxRate',title:'消费税率',width:20},
			{field:'vat',title:'增值税额',width:20},
			{field:'vatRate',title:'增值税率',width:20}
			]
		],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>