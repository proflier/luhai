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
       	        <input type="text" name="cghth" class="easyui-validatebox" data-options="width:150,prompt: '采购合同'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="cpContractNo" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
<!--        	        <input type="text" name="saleContarteNo" class="easyui-validatebox" data-options="width:150,prompt: '下游合同号'"/> -->
<!--        	        <span class="toolbar-item dialog-tool-separator"></span> -->
      		 	<input type="text" name="billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '上游发票号'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
<!--        	        <input type="text" name="downInvoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '下游发票号'"/> -->
<!--    	         	<span class="toolbar-item dialog-tool-separator"></span> -->
   	         	<input type="text" name="expressNo" class="easyui-validatebox" data-options="width:150,prompt: '快递单号'"/>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
		</form>
		<div>
		<a href="#" class="easyui-menubutton" menu="#mm1" iconCls="icon-standard-page-excel">导出Excel</a>
		</div>
		<div id="mm1" style="width:150px;">
			<div onclick="exportExcel()">导出当前</div>
			<div onclick="exportExcelAll()">导出所有</div>
		</div>
    </div>
<table id="dg"></table> 
<div id="dlg"></div>  
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({    
    url:'${ctx}/reportForm/downstreamBill/json', 
    fit : true,
	fitColumns : true,
	border : false,
	striped:true,	
	scrollbarSize : 0,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:false,
    columns:[[    
			{field:'cghth',title:'采购合同号',width:20},
			{field:'cpContractNo',title:'综合合同号',width:20},  
			{field:'billNo',title:'提单号',width:20},
			{field:'saleContarteNo',title:'销售合同号',width:20},
			{field:'invoiceNo',title:'上游发票号',width:20},
			{field:'expressNo',title:'快递号',hidden:true},
			{field:'downInvoiceNo',title:'下游发票号',width:20},
			{field:'goodName',title:'商品名称',width:10},
			{field:'area',title:'区域',hidden:true},
			{field:'forwarderPrice',title:'包干费单价',hidden:true},
			{field:'currency',title:'币种',width:10},
			{field:'invoiceAmount',title:'发票金额',width:10},
			{field:'noBoxDay',title:'免箱期天数',hidden:true},
			{field:'numberUnits',title:'数量单位',hidden:true},
			{field:'numbers',title:'数量',width:10},
			{field:'ourUnit',title:'我司单位',hidden:true},
			{field:'portName',title:'目的港',hidden:true},
			{field:'supplier',title:'供应商',width:20},
			{field:'twoTimeBillNo',title:'二次换单提单号',hidden:true},
			{field:'downStreamClient',title:'下游客户',width:20},
			{field:'createDate',title:'登记时间',hidden:true},
			]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    showFooter: true,
//     rowTooltip: true,
    toolbar:'#tb'
	});
});

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('load',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//导出excel
function exportExcel() {
	var url = "${ctx}/reportForm/downstreamBill/exportExcel";
	$("#searchFrom").attr("action", url);  
    //触发submit事件，提交表单   
    $("#searchFrom").submit();
    $("#searchFrom").attr("action", "");  
}
function exportExcelAll() {
	var url = "${ctx}/reportForm/downstreamBill/exportExcelAll";
	$("#searchFrom").attr("action", url);  
    //触发submit事件，提交表单   
    $("#searchFrom").submit();
    $("#searchFrom").attr("action", "");  
}

</script>
</body>
</html>