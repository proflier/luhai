<%@page import="com.cbmie.woodNZ.salesDelivery.entity.SalesDeliveryGoods"%>
<%@page import="com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="salesDeliveryGoods" ></table>
<%
	SalesDelivery salesDelivery = (SalesDelivery)request.getAttribute("salesDelivery");
	List<SalesDeliveryGoods> isList = salesDelivery.getSalesDeliveryGoodsList();
	ObjectMapper objectMapper = new ObjectMapper();
	String isJson = objectMapper.writeValueAsString(isList);
	request.setAttribute("isJson", isJson);
%>
<script type="text/javascript">
var salesDeliveryGoods;
initSalesGoods(); //初始化
function initSalesGoods(value){
	salesDeliveryGoods=$('#salesDeliveryGoods').datagrid({
// 		data : value != null ? JSON.parse(value) : JSON.parse('${isJson}'),
// 		data : typeof(value) == "undefined" ? JSON.parse('${isJson}'): (value= null?'': JSON.parse(value)) ,
		data : typeof(value) == "undefined"? JSON.parse('${isJson}'):(value==null?JSON.parse('[]'):JSON.parse(value) ) ,
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


function acceptD(){
		var rows = $('#salesDeliveryGoods').datagrid('getRows');
		$('#salesDeliveryGoodsJson').val(JSON.stringify(rows));
	}
</script>