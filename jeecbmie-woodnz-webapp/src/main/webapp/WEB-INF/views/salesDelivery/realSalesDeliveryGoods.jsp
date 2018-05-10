<%@page import="com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods"%>
<%@page import="com.cbmie.woodNZ.salesDelivery.entity.SalesDeliveryGoods"%>
<%@page import="com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="realChildToolbar" style="padding:5px;height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="searchStock()">添加商品</a>	
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="realSalesDeliveryGoods" data-options="onClickRow: onClickRow"></table>
<%
	SalesDelivery salesDelivery1 = (SalesDelivery)request.getAttribute("salesDelivery");
	List<RealSalesDeliveryGoods> realSaleList = salesDelivery1.getRealSalesDeliveryGoodsList();
	ObjectMapper objectMapper1 = new ObjectMapper();
	String realSalesJson = objectMapper1.writeValueAsString(realSaleList);
	request.setAttribute("realSalesJson", realSalesJson);
%>
<script type="text/javascript">
var realSalesDeliveryGoods;
initSalesGoods1(); //初始化
function initSalesGoods1(value){
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
			{field:'cpContractNo',title:'综合合同号',width:20},
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

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#realSalesDeliveryGoods').datagrid('validateRow', editIndex)){
		$('#realSalesDeliveryGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#realSalesDeliveryGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#realSalesDeliveryGoods').datagrid('selectRow', editIndex);
		}
		initSPBM( editIndex);
	}
}
function accept(){
	if (endEditing()){
		var rows = $('#realSalesDeliveryGoods').datagrid('getRows');
		$('#realSalesDeliveryGoods').datagrid('acceptChanges');
		$('#realSalesDeliveryGoodsJson').val(JSON.stringify(rows));
		return true;
	}
}
function reject(){
	$('#realSalesDeliveryGoods').datagrid('rejectChanges');
	editIndex = undefined;
}


function initSPBM( editIndex){
	var currenctObject = $('#salesDeliveryGoods').datagrid('getEditor', { index: editIndex, field: 'goodsNo' });
	currenctObject.target.change(function () {
        var goodsNo = $(currenctObject.target).val();
        if (goodsNo.replace(/[ ]/g,"") ==""){
       		goodsNo = $(currenctObject.target).val("");
       		return;
       	} 
        if(goodsNo.length>13){
        	$.messager.alert('提示','编码长度超过13位！请检查！','info');
        }else{
        	 var destObject = $('#salesDeliveryGoods').datagrid('getEditor', {index:editIndex,field:'goodsName'});
        	 if(goodsNo!=null&&goodsNo!=""){
        		 $.ajax({
      				url : '${ctx}/baseInfo/goodsInfo/getNameByCode/' + goodsNo,
      				type : 'get',
      				cache : false,
      				success : function(data) {
      					$(destObject.target).val(data);
      				}
      			}); 
        	 }
 			
        }
	});
	
	//采购金额计算
	var currenctAmount = $('#salesDeliveryGoods').datagrid('getEditor', { index: editIndex, field: 'amount' });
	currenctAmount.target.change(function () {
        var amount = $(currenctAmount.target).val();
       	var currenctUnitPrice = $('#salesDeliveryGoods').datagrid('getEditor', {index:editIndex,field:'unitPrice'});
        var unitPrice = $(currenctUnitPrice.target).val();
        var currenctMoney = $('#salesDeliveryGoods').datagrid('getEditor', {index:editIndex,field:'money'});
        if(isNaN(parseFloat(amount)*parseFloat(unitPrice))){}else{
        $(currenctMoney.target).val(parseFloat(amount)*parseFloat(unitPrice));
        }
	});
	
   	var currenctUnitPrice = $('#salesDeliveryGoods').datagrid('getEditor', {index:editIndex,field:'unitPrice'});
   	currenctUnitPrice.target.change(function () {
		var currenctAmount = $('#salesDeliveryGoods').datagrid('getEditor', { index: editIndex, field: 'amount' });
        var amount = $(currenctAmount.target).val();
        var unitPrice = $(currenctUnitPrice.target).val();
        var currenctMoney = $('#salesDeliveryGoods').datagrid('getEditor', {index:editIndex,field:'money'});
        if(isNaN(parseFloat(amount)*parseFloat(unitPrice))){}else{
            $(currenctMoney.target).val((parseFloat(amount)*parseFloat(unitPrice)).toFixed(2));
            }
	});
	
	
	
}



//选择库存商品
function searchStock() {
	d_goodsCode=$("#searchStock").dialog({   
	    title: '选择库存商品',    
	    width: 800,    
	    height: 600,    
	    href:'${ctx}/salesDelivery/salesDelivery/loadStockStatistic',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'确定',iconCls:'icon-ok',
			handler:function(){
				var rows =  $('#dg_stockStatistic_selected').datagrid('getRows');
				var jsonParam = JSON.stringify(rows);
				$.ajax({
					 url:'${ctx}/salesDelivery/salesDelivery/matchStock/'+jsonParam,
					type : 'GET',
					dataType: "json",//必须定义此参数
					cache : false,
					success : function(data) {
						initSalesGoods1(data);
					}
				});
				d_goodsCode.panel('close');
				reject();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_goodsCode.panel('close');
			}
		}]
	});
}


</script>