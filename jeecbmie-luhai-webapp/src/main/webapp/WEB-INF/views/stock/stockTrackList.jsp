<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<form id="searchFrom" action="">
	    <input type="text" id="contractNo" name="contractNo" class="easyui-validatebox" data-options="prompt: '合同编号'"/>
	    <input type="text" id="deliveryNo" name="deliveryNo" class="easyui-validatebox" data-options="prompt: '放货通知编号'"/>
	    <input type="text" id="goodsName" name="goodsName" class="easyui-validatebox" data-options="prompt: '物料名称'"/>
	    <input type="text" id="receiptCode" name="receiptCode" class="easyui-validatebox" data-options="prompt: '客户名称'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
		<span class="toolbar-item dialog-tool-separator"></span>
<!-- 		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="excel();">导出</a> -->
	</form>
</div>
<table id="dg" ></table> 
<script type="text/javascript">
//客户名称
$('#receiptCode').combobox({
// 	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230003',
	valueField : 'customerName',
	textField : 'customerName',
});


//商品名称
$('#goodsName').combobox({
//		panelHeight:'auto',
	method: "get",
	url:'${ctx}/baseInfo/lhUtil/goodsItems',
	valueField:'infoName',
    textField:'infoName', 
//	    required : true,
	onSelect:function(data){
// 		alert(data.infoCode)
	}
});

var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/stock/outStock/stockTrackJson', 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
// 		pagination:true,
		rownumbers:true,
// 		pageNumber:1,
// 		pageSize : 20,
// 		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'contractNo',title:'合同编号',width:30},
			{field:'goodsName',title:'商品名称',width:10},
			{field:'contractQuantity',title:'合同数量',width:10},
			{field:'deliveryNo',title:'放货通知编号',width:20},
			{field:'date',title:'日期',width:15}, 
			{field:'port',title:'出库码头',width:20},
			{field:'customer',title:'客户名称',width:30},
			{field:'quantity',title:'放货数量',width:10},
			{field:'shipNo',title:'船次',width:30},
// 			{field:'quantityHave',title:'已发货数量',width:20},
// 			{field:'quantityless',title:'剩余放货数量',width:20},
	    ]],
	    groupField:'contractNo',
		view: groupview,
		groupFormatter:function(value, rows){
			var quantityHave = 0;//已发
			var quantityless = 0;//剩余
			var quantityAll = 0;//所有
			var allgoods =new Array();
			for(var i=0;i<rows.length;i++){
				var containFlag = false;
				for(var j=0;j<allgoods.length;j++){
					if(allgoods[j]==rows[i].goodsName){
						containFlag = true;
					}
				}
				if(!containFlag){
					allgoods.push(rows[i].goodsName)
					quantityAll = quantityAll + parseFloat(rows[i].contractQuantity);
				}
				quantityHave =  quantityHave + parseFloat(rows[i].quantity);
			}
			quantityless = quantityAll-quantityHave;
			
			return "已发货数量" +"<font style='color:#F00'>"+quantityHave.toFixed(3)+"</font>"+  '吨--剩余放货数量:' +"<font style='color:#F00'>"+quantityless.toFixed(3)+"</font>"+'吨'; 
		},
		onLoadSuccess:function(data){
			if (data.rows.length > 0) {
				mergeCellsByField("dg", "contractNo");
				mergeCellsByField("dg", "goodsName");
			}
		},
// 	    sortName:'id',
// 	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb'
	});
});

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$('#receiptCode').combobox('clear');
	$('#goodsName').combobox('clear');
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}


</script>
</body>
</html>