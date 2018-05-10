<%@page import="java.util.*"%>
<%@page import="com.cbmie.genMac.financial.entity.SaleInvoice"%>
<%@page import="com.cbmie.genMac.financial.entity.SaleInvoiceSub"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/financial/saleInvoice/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
				<fieldset class="fieldsetClass" >
				<legend>销售发票登记</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th style="width: 30%">抬头</th>
						<td >
							${saleInvoice.title}
						</td>
						<th>发票日期</th>
						<td >
							<fmt:formatDate value="${saleInvoice.invoiceDate}" />
						</td>
					</tr>
					<tr>
						<th>发票号</th>
						<td >
							${saleInvoice.invoideNo }
						</td>
						<th>下游名称地址</th>
						<td >
							${saleInvoice.downAddress }
						</td>
					</tr>
					<tr>
						<th>合同号</th>
						<td >
							${saleInvoice.contractNo }
						</td>
						<th>装运港</th>
						<td >
							${saleInvoice.srcPort }
						</td>
					</tr>
					<tr>
						<th>目的港</th>
						<td >
							${saleInvoice.destPort }
						</td>
						<th>交易模式</th>
						<td >
							${saleInvoice.transType }
						</td>
					</tr>
					<tr>
						<th>装货时间</th>
						<td >
							<fmt:formatDate value="${saleInvoice.goodsDate}" />
						</td>
						<th>提单号</th>
						<td >
							${saleInvoice.billNo }
						</td>
					</tr>
					<tr>
						<th>制单人</th>
						<td>
							${saleInvoice.createrName}
						</td>
						<th>制单日期</th>
						<td>
							<fmt:formatDate value="${saleInvoice.createDate}" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>明细</legend>
					<table class="tableClass" id="subTable"></table>
				</fieldset>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				if("update"=="${action}")
		    		successTip(data,dg);
		    	else
		    		successTip(data,dg,d);
			} 
		});
		
		initSubForm();
	});
	
	<%
		SaleInvoice saleInvoice = (SaleInvoice)request.getAttribute("saleInvoice");
	    Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> total = new HashMap<String, Object>();
		total.put("shipNo", "汇总");
		List<Map> totalList = new ArrayList<Map>();
		totalList.add(total);
		List<SaleInvoiceSub> subList = saleInvoice.getSubs();
		for(SaleInvoiceSub sub : subList){
			total.put("metricTon", (sub.getMetricTon()==null?0.0:sub.getMetricTon())+(total.get("metricTon")==null?0.0:(Double)total.get("metricTon")));
			total.put("dryTon", (sub.getDryTon()==null?0.0:sub.getDryTon())+(total.get("dryTon")==null?0.0:(Double)total.get("dryTon")));
			total.put("money", (sub.getMoney()==null?0.0:sub.getMoney())+(total.get("money")==null?0.0:(Double)total.get("money")));
			total.put("totalMoney", (sub.getTotalMoney()==null?0.0:sub.getTotalMoney())+(total.get("totalMoney")==null?0.0:(Double)total.get("totalMoney")));
		}
		result.put("total", subList.size());
		result.put("rows", subList);
		result.put("footer", totalList);
		ObjectMapper objectMapper = new ObjectMapper();
		String subJson = objectMapper.writeValueAsString(result);
		request.setAttribute("subJson", subJson);
	%>
	
	function initSubForm(value){
		$('#subTable').datagrid({
		data : JSON.parse('${subJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		showFooter:true,
	    columns:[[
			{field:'shipNo',title:'驳船号',width:20},
			{field:'metricTon',title:'公吨',width:15,editor:{type: 'numberbox', options: {precision:2}}},
			{field:'waterSale',title:'含水率',sortable:true,width:15,editor:{type: 'numberbox', options: {precision:2}}},
			{field:'dryTon',title:'干吨',sortable:true,width:15,editor:{type: 'numberbox', options: {precision:2}}},
			{field:'money',title:'美金单价',width:15,sortable:true,type: 'numberbox', options: {precision:2}},
			{field:'totalMoney',title:'总价',width:20,sortable:true,type: 'numberbox', options: {precision:2}}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar'
		});
	}
</script>
</body>
</html>
