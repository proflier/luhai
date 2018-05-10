<%@page import="com.cbmie.woodNZ.stock.entity.InStockGoods"%>
<%@page import="com.cbmie.woodNZ.stock.entity.InStock"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .datagrid-footer td{font-weight: bold;}
</style>
<table id="inStockGoods" ></table>
<%
	InStock inStock = (InStock)request.getAttribute("inStock");
	List<InStockGoods> isList = inStock.getInStockGoods();
	ObjectMapper objectMapper = new ObjectMapper();
	String isJson = objectMapper.writeValueAsString(isList);
	request.setAttribute("isJson", isJson);
%>
<script type="text/javascript">
var inStockGoods;
initInStockGoods(); //初始化
function initInStockGoods(value){
	inStockGoods=$('#inStockGoods').datagrid({
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
			{field:'cpContractNo',title:'综合合同号',width:20,editor:{type:'validatebox'}},
			{field:'boxNo',title:'集装箱号',width:20,editor:{type:'validatebox'}},
			{field:'goodsNo',title:'商品编码',width:20,editor:{type:'validatebox'}},
			{field:'goodsName',title:'商品名称',width:20,editor:{type:'validatebox'}},
			{field:'pieceNum',title:'应入件数',width:20,editor:{type:'validatebox'}},
			{field:'realNumber',title:'实入件数',width:20,editor:{type:'validatebox'}},
			{field:'pNum',title:'应入片数',width:20,editor:{type:'validatebox'}},
			{field:'realPiece',title:'实入片数',width:20,editor:{type:'validatebox'}},
			{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
			{field:'numberUnits',title:'数量单位',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.numberUnits!=''&&row.numberUnits!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getDictName/sldw/"+row.numberUnits,
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
						method: "get",
						url:'${ctx}/system/downMenu/getDictByCode/sldw',
					    valueField:'id',
					    textField:'name', 
					    required:true
					}
				}
			},
			{field:'num',title:'数量',width:20,editor:{type:'validatebox'}},
			{field:'goodsLoss',title:'商品定损',width:20,editor:{type:'validatebox'}},
			{field:'purchasePrice',title:'采购价',width:20,editor:{type:'validatebox'}},
			{field:'lockSellingPrice',title:'锁定销售单价',width:20,editor:{type:'numberbox',options:{precision:2}}},
			{field:'contractNo',title:'锁定销售合同号',width:20,editor:{type:'validatebox'}}
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar',
	 	showFooter: true,
	    onLoadSuccess: function(data){
	    	initFooter(data);
		 }
	});
}

//加载页脚
function initFooter(data){
	var totalboxNo =0;
	var totalrealNumber =0;
	var totalrealPiece =0;
	var totalpieceNum =0;
	var totalpNum = 0;
	var totalnum = 0;
	var array = [];
	for(var i = 0; i < data.rows.length; i++){
	    var map = data.rows[i];
	    for (var key in map){
	    	var currenctValue = map[key];
	    	if(key=='boxNo'){
	    		if(array.indexOf(currenctValue) <0){
	    			totalboxNo = totalboxNo + 1;
	    			array.push(currenctValue);
	    		}
		    }
		    if(key=='pNum'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalpNum = totalpNum + parseInt(currenctValue);
		    	}
		    }
		    if(key=='num'){
		    	if(!isNaN(parseFloat(currenctValue))){
		    	totalnum = totalnum +parseFloat(currenctValue);
		    	}
		    }
		    if(key=='pieceNum'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalpieceNum = totalpieceNum + parseInt(currenctValue);
		    	}
		    }
		    if(key=='realNumber'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalrealNumber = totalrealNumber + parseInt(currenctValue);
		    	}
		    }
		    if(key=='realPiece'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalrealPiece = totalrealPiece + parseInt(currenctValue);
		    	}
		    }
	    }
	}
	$('#inStockGoods').datagrid('reloadFooter',[
	                              	{ boxNo: '集装箱数：'+totalboxNo,
	                              	pieceNum:totalpieceNum,
	                              	realNumber:totalrealNumber,
	                              	realPiece:totalrealPiece,
	                              	pNum: totalpNum,
	                              	num:totalnum.toFixed(3)}
	                              ]);
	
}
</script>