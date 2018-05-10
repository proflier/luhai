<%@page import="com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods"%>
<%@page import="com.cbmie.woodNZ.salesDelivery.entity.SalesDeliveryGoods"%>
<%@page import="com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery"%>
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
<form id="mainform" action="" method="post">
<div id="mainDiv" class="" data-options="border:false">
<div data-options="title: '放货信息', iconCls: false, refreshable: false" >
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%">销售合同号</th>
		<td width="30%">
			${salesDelivery.billContractNo }
		</td>
		<th width="20%">买受人</th>
		<td width="30%" id="customerUnit">
		</td>
	</tr>
	<tr>
		<th>联系电话</th>
		<td>
			${salesDelivery.contactsNumber }
		</td>
		<th>送货地址</th>
		<td>
			${salesDelivery.deliveryAddress }
		</td>
	</tr>
	<tr>
		<th>开单日期</th>
		<td colspan="3">
			<fmt:formatDate value="${salesDelivery.billingDate }" />
		</td>
	</tr>
	<tr>
		<th >联系人</th>
		<td >
		${salesDelivery.contacts }
		</td>
		<th>合同总额</th>
		<td>
			${salesDelivery.contractTotal }
		</td>
	</tr>
	<tr>
		<th>合同累计收款金额</th>
		<td>
			${salesDelivery.contractCumulation }
		</td>
		<th>合同已经放货金额</th>
		<td>
			${salesDelivery.contractHaveLending }
		</td>
	</tr>
	<tr>
		<th>客户累计收款金额</th>
		<td>
		${salesDelivery.customerCumulation }
		</td>
		<th>客户已经放货金额</th>
		<td>
			${salesDelivery.customerHaveDelivery }
		</td>
	</tr>
	<tr>
		<th>客户累计余额</th>
		<td colspan="3">
			${salesDelivery.customerToltalOverage }
		</td>
	</tr>
	<tr>
		<th>保证金</th>
		<td>
			${salesDelivery.securityDeposit }
		</td>
		<th>本次用款付款方式</th>
		<td id="paymentMethod">
		</td>
	</tr>
	<tr>
		<th>本次明细汇总金额</th>
		<td>
			${salesDelivery.summaryAmount }
		</td>
		<th>本次明细汇总数量</th>
		<td>
			${salesDelivery.summaryNumber }
		</td>
	</tr>
	<tr>
		<th>是否专案</th>
		<td id="isProject">
		</td>
		<th>合同执行人</th>
		<td>
			${salesDelivery.contractExecutor }
		</td>
	</tr>
	<tr>
		<th>合同是否放货完成</th>
		<td id="contractDelivery">
		</td>
		<th>是否代拆</th>
		<td id="isOpen">
		</td>
	</tr>
	<tr>
		<th> 优先出货提单号 </th>
		<td colspan="3">
			${salesDelivery.priorityShippingNo }
		</td>
	</tr>
	<tr>		
		<th>备注</th>
		<td colspan="3">
			${salesDelivery.comment }
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${salesDelivery.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${salesDelivery.createrDept }</td>
	</tr>
</table>
</fieldset>
</div>
<div data-options="title: '放货商品', iconCls: false, refreshable: false">
					<input type="hidden" name="salesDeliveryGoodsJson" id="salesDeliveryGoodsJson"/>
					<table id="salesDeliveryGoods" ></table>
<%
	SalesDelivery salesDelivery = (SalesDelivery)request.getAttribute("salesDelivery");
	List<SalesDeliveryGoods> isList = salesDelivery.getSalesDeliveryGoodsList();
	ObjectMapper objectMapper = new ObjectMapper();
	String isJson = objectMapper.writeValueAsString(isList);
	request.setAttribute("isJson", isJson);
%>
</div>
<div data-options="title: '合同实际放货商品', iconCls: false, refreshable: false">
					<input type="hidden" id="realSalesDeliveryGoodsJson" name="realSalesDeliveryGoodsJson" />
					<table id="realSalesDeliveryGoods" ></table>
<%
	SalesDelivery salesDelivery1 = (SalesDelivery)request.getAttribute("salesDelivery");
	List<RealSalesDeliveryGoods> realSaleList = salesDelivery1.getRealSalesDeliveryGoodsList();
	ObjectMapper objectMapper1 = new ObjectMapper();
	String realSalesJson = objectMapper1.writeValueAsString(realSaleList);
	request.setAttribute("realSalesJson", realSalesJson);
%>
</div>
</div>
</form>
<script type="text/javascript">
$(function(){
	
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    }
	});
	
	if('${salesDelivery.customerUnit }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${salesDelivery.customerUnit }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#customerUnit').text(data);
			}
		});
	}
	
	
	if('${salesDelivery.paymentMethod }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/skfs/${salesDelivery.paymentMethod }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#paymentMethod').text(data);
			}
		});
	}
	
	
	if('${salesDelivery.isProject }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/YESNO/${salesDelivery.isProject }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#isProject').text(data);
			}
		});
	}
	
	if('${salesDelivery.contractDelivery }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/YESNO/${salesDelivery.contractDelivery }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#contractDelivery').text(data);
			}
		});
	}
	
	
	if('${salesDelivery.isOpen }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/YESNO/${salesDelivery.isOpen }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#isOpen').text(data);
			}
		});
	}
});	


var salesDeliveryGoods;
initInStockGoods(); //初始化
function initInStockGoods(value){
	salesDeliveryGoods=$('#salesDeliveryGoods').datagrid({
		data : value != null ? value : JSON.parse('${isJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[
	        [
			{field:'goodsNo',title:'商品编号',width:20,editor:{type:'validatebox'}},
			{field:'goodsName',title:'商品名称',width:20,editor:{type:'validatebox'}},
			{field:'piece',title:'片数',width:20,editor:{type:'validatebox'}},
			{field:'num',title:'件数',width:20,editor:{type:'validatebox'}},
			{field:'amount',title:'数量',width:20,editor:{type:'validatebox'}},
			{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
			{field:'unit',title:'数量单位',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.unit!=''&&row.unit!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getDictName/sldw/"+row.unit,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				},
				editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'id',
						textField:'name',
						method:'get',
						url:'${ctx}/system/downMenu/getDictByCode/sldw',
						required:true
					}
				}	
			},
			{field:'unitPrice',title:'单价',width:20,editor:{type:'numberbox',options:{precision:2}}},
			{field:'money',title:'总价',width:20,editor:{type:'numberbox',options:{precision:2}}},
			{field:'warehouse',title:'仓库',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.warehouse!=''&&row.warehouse!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getWarehouseName/'+row.warehouse ,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				},
				editor:{
					type:'combobox',
					options:{
						method: "get",
						url : '${ctx}/system/downMenu/jsonCK',
						valueField : 'id',
						textField : 'companyName',
					}
				}
			}
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar'
	});
}

var realSalesDeliveryGoods;
initSalesGoods(); //初始化
function initSalesGoods(value){
	realSalesDeliveryGoods=$('#realSalesDeliveryGoods').datagrid({
// 		data : value != null ? JSON.parse(value) : JSON.parse('${realSalesJson}'),
// 		data : typeof(value) == "undefined" ? JSON.parse('${realSalesJson}'): (value= null?'': JSON.parse(value)) ,
		data : typeof(value) == "undefined"? JSON.parse('${realSalesJson}'):(value==null?JSON.parse('[]'):value ) ,
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[
	        [
			{field:'goodsNo',title:'商品编号',width:20},
			{field:'goodsName',title:'商品名称',width:20},
			{field:'piece',title:'片数',width:20,editor:{type:'validatebox'}},
			{field:'num',title:'件数',width:20,editor:{type:'validatebox'}},
			{field:'amount',title:'数量',width:20,editor:{type:'validatebox'}},
			{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
			{field:'unit',title:'数量单位',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.unit!=''&&row.unit!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getDictName/sldw/"+row.unit,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'unitPrice',title:'单价',width:20,editor:{type:'numberbox',options:{precision:2}}},
			{field:'money',title:'总价',width:20,editor:{type:'numberbox',options:{precision:2}}},
			{field:'warehouse',title:'仓库',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.warehouse!=''&&row.warehouse!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getWarehouseName/'+row.warehouse ,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				}
			}
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#realChildToolbar'
	});
}


</script>
</body>
</html>