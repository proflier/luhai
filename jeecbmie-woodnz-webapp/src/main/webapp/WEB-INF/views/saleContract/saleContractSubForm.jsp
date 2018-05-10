<%@page import="com.cbmie.woodNZ.salesContract.entity.WoodSaleContract"%>
<%@page import="com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchGoodsCode()">查看商品编码</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTB" data-options="onClickRow: onClickRow"></table>
<%
	WoodSaleContract woodSaleContract = (WoodSaleContract)request.getAttribute("woodSaleContract");
	List<WoodSaleContractSub> woodContractSubList = woodSaleContract.getSaleContractSubList();
	ObjectMapper objectMapperCus = new ObjectMapper();
	String childJson = objectMapperCus.writeValueAsString(woodContractSubList);
	request.setAttribute("childJson", childJson);
%>
<script type="text/javascript">
var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${childJson}'),
    fit : false,
	fitColumns : true,
	scrollbarSize : 0,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
    columns:[[    
		{field:'id',title:'id',hidden:true},
		{field:'goodsNo',title:'商品编码',width:25,editor:{type:'validatebox',options:{required:true}}
		},
	    {field:'goodsName',title:'商品名称',width:25, editor:{type:'validatebox',options:{required:true}}},
        {field:'packages',title:'包装',width:25, editor:{type:'validatebox'}},
        {field:'num',title:'销售件数',width:25, editor:{type:'numberbox'}},
        {field:'piece',title:'销售片数',width:25, editor:{type:'numberbox'}},
        {field:'amount',title:'销售数量',width:25, editor:{type:'numberbox',options:{min:0,precision:3}}},
        {field:'unit',title:'数量单位',width:15, editor:{
			type:'combobox',
			options:{
				panelHeight:'auto',
				valueField:'id',
				textField:'name',
				method:'get',
				url:'${ctx}/system/downMenu/getDictByCode/sldw',
				required:true
			}
		},
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
 	{field:'waterRate',title:'含水率',sortable:true,width:15,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
        {field:'unitPrice',title:'单价',width:25, editor:{type:'numberbox',options:{min:0,precision:2}}},
        {field:'money',title:'金额',width:25, editor:{type:'numberbox',options:{min:0,precision:2}}},
        {field:'currency',title:'币种 ',width:15,
     		editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/CURRENCY',
					required:true
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.currency!=''&&row.currency!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/CURRENCY/"+row.currency,
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
        {field:'taxRate',title:'增值税率',width:20, editor:{type:'numberbox'}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB').datagrid('validateRow', editIndex)){
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
// 	alert("onClickRow");
	if (editIndex != index){
		if (endEditing()){
			$('#childTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB').datagrid('selectRow', editIndex);
		}
		initSPBM(editIndex);
		countAmount(editIndex);
	}
}
function append(){
	if (endEditing()){
		$('#childTB').datagrid('appendRow',{goodsNo:''});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
		var rows = $('#childTB').datagrid('getRows');
		$('#contractSubJson').val(JSON.stringify(rows));
		initSPBM( editIndex);
		countAmount(editIndex);
	}	
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#contractSubJson').val(JSON.stringify(rows));
		return true;
	}
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function calculate(rows){
	var originalCurrency = 0;
	for(var i = 0; i < rows.length; i++){
		originalCurrency += parseFloat(rows[i].original);
	}
	$('#originalCurrency').numberbox({
		value:originalCurrency
	});
}

//弹窗查看编码
function searchGoodsCode() {
	d_goodsCode=$("#searchGoodsCode").dialog({   
	    title: '查看商品编码',    
	    width: 600,    
	    height: 355,    
	    href:'${ctx}/sale/saleContract/ckbm',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_goodsCode.panel('close');
			}
		}]
	});
}

function initSPBM(editIndex){
	var currenctObject = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'goodsNo' });
	currenctObject.target.change(function () {
        var spbm = $(currenctObject.target).val();
        if(spbm.length>13){
        	$.messager.alert('提示','编码长度超过13位！请检查！','info');
        	var spbm = '';
        }else{
        	 var destObject = $('#childTB').datagrid('getEditor', {index:editIndex,field:'goodsName'});
 			$.ajax({
 				url : '${ctx}/baseInfo/goodsInfo/getNameByCode/' + spbm,
 				type : 'get',
 				cache : false,
 				success : function(data) {
 					$(destObject.target).val(data);
 				}
 			});
        }
	});
}

function countAmount(editIndex){
	var amountObject = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'amount' });//数量
	var priceObject = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'unitPrice' });//单价
	var moneyObject = $('#childTB').datagrid('getEditor', {index:editIndex,field:'money'});//金额
	amountObject.target.change(function () {
        var amount = $(amountObject.target).val();
        var price = $(priceObject.target).val();
        if(isNaN(parseFloat(amount)*parseFloat(price))){}
        else{
        	$(moneyObject.target).val(parseFloat(amount)*parseFloat(price));
	     }
	});
	priceObject.target.change(function () {
        var amount = $(amountObject.target).val();
        var price = $(priceObject.target).val();
        if(isNaN(parseFloat(amount)*parseFloat(price))){}
        else{
        	$(moneyObject.target).val(parseFloat(amount)*parseFloat(price));
	     }
	});
}
</script>