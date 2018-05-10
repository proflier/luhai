<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 -->
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
		        <div id="bills_superSearch" float:left;">
		      		<input type="text" name="filter_LIKES_cpContractNo" id="filter_LIKES_cpContractNo" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/>
	      		 	<span class="toolbar-item dialog-tool-separator"></span>
	      		 	<input type="text" name="filter_LIKES_billNo" id="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
	       	        <span class="toolbar-item dialog-tool-separator"></span>
	       	        <input type="text" name="filter_LIKES_shipName" id="filter_LIKES_shipName" class="easyui-validatebox" data-options="width:150,prompt: '船名'"/>
	       	      	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
			        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
				</div>
			</form>
			<div>
			<a href="#" class="easyui-menubutton" menu="#menu" iconCls="icon-standard-page-excel">导出Excel</a>
	<!--        	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="exportExcel()">导出Excel</a> -->
			</div>
			<div id="menu" style="width:150px;">
				<div onclick="exportExcel()">导出当前</div>
				<div onclick="exportExcelAll()">导出所有</div>
			</div>
		
        </div> 
        
</div>
<table id="dg"></table>  <!-- 首页到单列表  -->
<div id="dlg"></div>  <!-- 到单页面弹窗  -->
<div id="dlgPurchase"></div> <!-- 采购合同列表弹窗  -->
<div id="dlgBox"></div> <!-- 箱单弹窗  -->
<div id="search4GoodsCode"><!-- 查询商品编码弹窗  -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/reportForm/bill/json', 
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
		singleSelect:true,
	    columns:[[    
			{field:'id',hidden:true,width:20},  
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},  
			{field:'cateforyName',title:'品名',sortable:true,width:20},
			{field:'contractNo',title:'上游合同号',sortable:true,width:20},
			{field:'supplier',title:'上游客户',sortable:true,width:20},
			{field:'downStreamClient',title:'下游客户',sortable:true,width:20},
			{field:'goodsDesc',title:'货物描述',sortable:true,width:20},
			{field:'sl',title:'采购数量',sortable:true,width:20},
// 			{field:'sl',title:'实际到单数量',sortable:true,width:20},
			{field:'invoiceAmount',title:'采购总金额',sortable:true,width:20},
			{field:'invoiceNo',title:'上游发票号',sortable:true,width:20},
			{field:'containerNumber',title:'集装箱数量',sortable:true,width:20},
			{field:'billNo',title:'提单号',sortable:true,width:20},
			{field:'portName',title:'目的港',sortable:true,width:20},
			{field:'shipDate',title:'装船日期',sortable:true,width:20},
			{field:'expectPortDate',title:'到港日期',sortable:true,width:20},
			{field:'giveBillsDate',title:'收到正本日期',sortable:true,width:20},
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
	});
});



//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}


function superSearch(){
	if($("#bills_superSearch").css("display")=='none'){//如果show是隐藏的
		$("#filter_LIKES_billNo_OR_shipName").val('');
		$("#bills_search").css("display","none");//不显示
		$("#bills_superSearch").css("display","block");//show的display属性设置为block（显示）
// 		$("#viewSuperSearch").text("关闭高级搜索");
	}else{//如果show是显示的
		$("#filter_LIKES_billNo").val('');
		$("#filter_LIKES_shipName").val('');
		$("#bills_superSearch").css("display","none");//show的display属性设置为none（隐藏）
		$("#bills_search").css("display","block");//（显示）
// 		$("#viewSuperSearch").text("高级搜索");
	}
}

//导出excel
function exportExcel() {
	var pageNo = $('#dg').datagrid('getPager').data("pagination").options.pageNumber;
	var url = "${ctx}/reportForm/bill/exportExcel/"+pageNo;
	console.info(pageNo);
	$("#searchFrom").attr("action", url);  
    //触发submit事件，提交表单   
    $("#searchFrom").submit();
    $("#searchFrom").attr("action", "");  
}   

//导出excel
function exportExcelAll() {
	var pageNo = $('#dg').datagrid('getPager').data("pagination").options.pageNumber;
	var url = "${ctx}/reportForm/bill/exportExcelAll";
	$("#searchFrom").attr("action", url);  
    //触发submit事件，提交表单   
    $("#searchFrom").submit();
    $("#searchFrom").attr("action", "");  
}   
</script>
</body>
</html>