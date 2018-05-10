<%@page import="com.cbmie.woodNZ.stock.entity.InStockGoods"%>
<%@page import="com.cbmie.woodNZ.stock.entity.InStock"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="childToolbar" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchGoodsCode()">查看商品编码</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<style>
    .datagrid-footer td{font-weight: bold;}
</style>
<table id="inStockGoods" data-options="onClickRow: onClickRow"></table>
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
// 	alert(JSON.stringify(value));
	inStockGoods=$('#inStockGoods').datagrid({
// 		data :value != null ? value :  JSON.parse('${isJson}'),
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
			{field:'boxNo',title:'集装箱号',width:20,editor:{type:'validatebox'}},
			{field:'cpContractNo',title:'综合合同号',width:20,editor:{type:'validatebox'}},
			{field:'goodsNo',title:'商品编码',width:20,editor:{type:'validatebox'}},
			{field:'goodsName',title:'商品名称',width:30,editor:{type:'validatebox'}},
			{field:'pieceNum',title:'应入件数',width:15,editor:{type:'numberbox'}},
			{field:'realNumber',title:'实入件数',width:15,editor:{type:'numberbox'}},
			{field:'pNum',title:'应入片数',width:15,editor:{type:'numberbox'}},
			{field:'realPiece',title:'实入片数',width:15,editor:{type:'numberbox'}},
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
			{field:'num',title:'数量',width:20,editor:{type:'numberbox',options:{precision:3}}},
			{field:'goodsLoss',title:'商品定损',width:15,editor:{type:'validatebox'}},
			{field:'purchasePrice',title:'采购价',width:15,editor:{type:'numberbox',options:{precision:2}}},
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

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#inStockGoods').datagrid('validateRow', editIndex)){
		$('#inStockGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#inStockGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#inStockGoods').datagrid('selectRow', editIndex);
		}
		initSPBM( editIndex);
	}
}
function append(){
	if (endEditing()){
		$('#inStockGoods').datagrid('appendRow',{goodsNo:''});
		editIndex = $('#inStockGoods').datagrid('getRows').length-1;
		$('#inStockGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
		initSPBM( editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#inStockGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#inStockGoods').datagrid('getRows');
		$('#inStockGoodsJson').val(JSON.stringify(rows));
		$('#inStockGoods').datagrid('acceptChanges');
		return true;
	}
}
function reject(){
	$('#inStockGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#inStockGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
//弹窗查看编码
function searchGoodsCode() {
	d_goodsCode=$("#searchGoodsCode_inStock").dialog({   
	    title: '查看商品编码',    
	    width: 600,    
	    height: 355,    
	    href:'${ctx}/baseInfo/goodsInfo/ckbm',
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

function initSPBM( editIndex){
	var currenctObject = $('#inStockGoods').datagrid('getEditor', { index: editIndex, field: 'goodsNo' });
	currenctObject.target.change(function () {
        var goodsNo = $(currenctObject.target).val();
       	if (goodsNo.replace(/[ ]/g,"") ==""){
       		goodsNo = $(currenctObject.target).val("");
       		return;
       	} 	
        if(goodsNo.length>13){
        	$.messager.alert('提示','编码长度超过13位！请检查！','info');
        }else{
        	 var destObject = $('#inStockGoods').datagrid('getEditor', {index:editIndex,field:'goodsName'});
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
	
	//应入件数
	var currenctpieceNum = $('#inStockGoods').datagrid('getEditor', { index: editIndex, field: 'pieceNum' });
	currenctpieceNum.target.change(function () {
		 var pieceNum = $(currenctpieceNum.target).val();
		 var source = $('#inStockGoods').datagrid('getSelected').pieceNum;
		 if(isNaN(parseInt(source))){
			 source = 0;
		 }
		 if(isNaN(parseInt(pieceNum))){
			 pieceNum = 0;
		 }
		 var changeData = parseInt(pieceNum) - parseInt(source);
		 var rows = $('#inStockGoods').datagrid('getRows');
	 	 var totalpieceNum =0;
			for(var i = 0; i < rows.length; i++){
			    var map = rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='pieceNum'){
				    	if(!isNaN(parseInt(currenctValue))){
				    	totalpieceNum = totalpieceNum + parseInt(currenctValue);
				    	}
				    }
			    }
			}
			totalpieceNum = totalpieceNum + changeData;
			 var rowsFooter = $('#inStockGoods').datagrid('getFooterRows');
			 rowsFooter[0]['pieceNum'] = totalpieceNum;
		     $('#inStockGoods').datagrid('reloadFooter');	
	});
	
	//实入件数
	var currenctrealNumber = $('#inStockGoods').datagrid('getEditor', { index: editIndex, field: 'realNumber' });
	currenctrealNumber.target.change(function () {
		 var realNumber = $(currenctrealNumber.target).val();
		 var source = $('#inStockGoods').datagrid('getSelected').realNumber;
		 if(isNaN(parseInt(source))){
			 source = 0;
		 }
		 if(isNaN(parseInt(realNumber))){
			 realNumber = 0;
		 }
		 var changeData = parseInt(realNumber) - parseInt(source);
		 var rows = $('#inStockGoods').datagrid('getRows');
	 	 var totalrealNumber =0;
			for(var i = 0; i < rows.length; i++){
			    var map = rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='realNumber'){
				    	if(!isNaN(parseInt(currenctValue))){
				    	totalrealNumber = totalrealNumber + parseInt(currenctValue);
				    	}
				    }
			    }
			}
			totalrealNumber = totalrealNumber + changeData;
			 var rowsFooter = $('#inStockGoods').datagrid('getFooterRows');
			 rowsFooter[0]['realNumber'] = totalrealNumber;
		     $('#inStockGoods').datagrid('reloadFooter');	
	});
	
	//数量 
	var currenctnum = $('#inStockGoods').datagrid('getEditor', { index: editIndex, field: 'num' });
	currenctnum.target.change(function () {
		 var num = $(currenctnum.target).val();
		 var source = $('#inStockGoods').datagrid('getSelected').num;
		 if(isNaN(parseFloat(source))){
			 source = 0;
		 }
		 if(isNaN(parseFloat(num))){
			 num = 0.000;
		 }
		 var changeData = parseFloat(num) - parseFloat(source);
		 var rows = $('#inStockGoods').datagrid('getRows');
	 	 var totalnum =0;
			for(var i = 0; i < rows.length; i++){
			    var map = rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='num'){
				    	if(!isNaN(parseFloat(currenctValue))){
				    	totalnum = totalnum + parseFloat(currenctValue);
				    	}
				    }
			    }
			}
			totalnum = totalnum + changeData;
			 var rowsFooter = $('#inStockGoods').datagrid('getFooterRows');
			 rowsFooter[0]['num'] = totalnum.toFixed(3);
		     $('#inStockGoods').datagrid('reloadFooter');	
	});
}

//加载页脚
function initFooter(data){
	var totalboxNo =0;
	var totalrealNumber =0;
	var totalpieceNum =0;
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
		    
	    }
	}
	$('#inStockGoods').datagrid('reloadFooter',[
	                              	{ boxNo: '集装箱数：'+totalboxNo,
	                              	pieceNum:totalpieceNum,
	                              	realNumber:totalrealNumber,
	                              	num:totalnum.toFixed(3)}
	                              ]);
}

</script>