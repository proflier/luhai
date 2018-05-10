<%@page import="com.cbmie.woodNZ.stock.entity.OutStock"%>
<%@page import="com.cbmie.woodNZ.stock.entity.OutStockSub"%>
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
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/stock/outStock/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
				<fieldset class="fieldsetClass" >
				<legend>出库单信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">出库单号</th>
						<td width="30%">
							${outStock.outboundNo}
						</td>
						<th width="20%">客户签收日期</th>
						<td width="30%">
							<fmt:formatDate value="${outStock.billDate}" />
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>货款</th>
						<td>
							${outStock.payAmount }
						</td>
						<th>提货单位</th>
						<td id="goodsUnit">
						</td>
					</tr>
					<tr>
						<th>实发数量</th>
						<td>
							${outStock.realSendNum }
						</td>
						<th>数量单位</th>
						<td id="unit">
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>${outStock.createrName }</td>
						<th>登记部门</th>
						<td>${outStock.createrDept }</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>出库明细</legend>
					<table class="tableClass" id="subTable">
				</fieldset>
					
		</div>	
		</form>
	</div>
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
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
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if(!$('#mainform').form('validate')){
		    		$("#mainDiv").tabs("select", tabIndex);
		    	}else{
		    		tabIndex = index;
		    	}
		    }
		});
		initSubForm();
	});
	
	//提货单位
	if('${outStock.goodsUnit }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${outStock.goodsUnit }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#goodsUnit').text(data);
			}
		});
	}

	//数量单位
	if('${outStock.unit }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/sldw/${outStock.unit }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#unit').html(data);
			}
		});
	}
	
	
	
	<%
		OutStock outStock = (OutStock)request.getAttribute("outStock");
		List<OutStockSub> subList = outStock.getSubList();
		ObjectMapper objectMapper = new ObjectMapper();
		String subJson = objectMapper.writeValueAsString(subList);
		request.setAttribute("subDetailJson", subJson);
	%>
	
	function initSubForm(value){
		$('#subTable').datagrid({
		data : JSON.parse('${subDetailJson}'),
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'goodsNo',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		 columns:[[
		   		{field:'goodsNo',title:'商品编号',width:10},
		   		{field:'goodsName',title:'商品名称',width:10},
		   		{field:'pieceAvailable',title:'应发片数',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
		   		{field:'numAvailable',title:'应发件数',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
		   		{field:'amount',title:'应发立方数',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
		   		{field:'realsendPNum',title:'实发件数',sortable:true,width:10},
		   		{field:'waterRate',title:'含水率',sortable:true,width:10},
		   		{field:'realsendVNum',title:'实发数量',sortable:true,width:10},
		   		{field:'unitPrice',title:'单价',width:10},
		   		{field:'saleContractNo',title:'销售合同号',width:10},
		   		{field:'cpContractNo',title:'綜合合同号',width:10},
		   		{field:'money',title:'金额',width:10,sortable:true,type: 'numberbox', options: {precision:2}},
// 		   		{field:'warehouse',title:'仓库',width:10,
// 		   			formatter: function(value,row,index){
// 		   				var val;
// 		   				if(row.warehouse!=''&&row.warehouse!=null){
// 		   					$.ajax({
// 		   						type:'GET',
// 		   						async: false,
// 		   						url:"${ctx}/system/downMenu/getWarehouseName/"+row.warehouse,
// 		   						success: function(data){
// 		   							if(data != null){
// 		   								val = data;
// 		   							} else {
// 		   								val = '';
// 		   							}
// 		   						}
// 		   					});
// 		   					return val;
// 		   				}else{
// 		   					return '';
// 		   				}
// 		   			}},
		   		{field:'warehouse',title:'仓库',width:10,
					formatter: function(value,row,index){
						var val;
						if(row.warehouse==''|| row.warehouse==null){
							return '';
						}
						else if(row.dataSource =='放货'){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getWarehouseName/"+row.warehouse,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}
						else if(row.dataSource =='下游交单'){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/stock/outStock/getWarehouseName/"+row.warehouse,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}
					}},
		   		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onLoadSuccess: function(data){
	    	onCountTolalFooter();
	    },
	    showFooter: true
		});
	}
	
	
	 function onCountTolalFooter(){
		  $('#subTable').datagrid('reloadFooter',[
	           	{ goodsNo: '合计：',
	           	realsendPNum:computeDetail("realsendPNum"),
	           	realsendVNum:computeDetail("realsendVNum"),
	           	pieceAvailable:computeDetail("pieceAvailable"),
	           	numAvailable: computeDetail("numAvailable"),
	           	amount:computeDetail("amount"),
	           	unitPrice:'',//无奈，单价列统计无法删除，只能先置空了
	           	money:computeDetail("money")}
	           ]);
	  }
	 
	//指定列求和
	  function computeDetail(colName) {
	    var rows =  $('#subTable').datagrid('getRows');
	    var total = 0;
	    for (var i = 0; i < rows.length; i++) {
	      total += parseFloat(rows[i][colName]);
	    }
	    if(colName == "money" && total >= 0)//金额保留两位小数
	    	return total.toFixed(2);
	    if(total >= 0)
	    	return total.toFixed(3);//数量保留三位小数
	    else
	   		return '0';
	  }
</script>
</body>
</html>
