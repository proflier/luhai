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
	    <input type="text" name="filter_LIKES_goodsNameSpecification" class="easyui-validatebox" data-options="width:150,prompt: '商品名称或规格型号'"/>
		<input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	<shiro:hasPermission name="sys:planStock:planStock">
		<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-zoom" plain="true" onclick="planStock()">盘库</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/stock/planStock/json',
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
		frozenColumns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsNameSpecification',title:'商品名称/规格型号',width:150},
			{field:'invoiceNo',title:'提单号',width:150}
		]],
	    columns:[
	    	[
			{"title":"入库","colspan":3},
			{"title":"出库","colspan":3},
			{field:'remark',title:'备注',rowspan:2,align:'center',width:30},
	  	    ],
	    	[
			{field:'inStockId',title:'编号',width:20},
			{field:'inStockCount',title:'数量',width:20},
			{field:'inStockDate',title:'日期',width:20},
			{field:'sendGoodsNo',title:'编号',width:20},
			{field:'sendGoodsCount',title:'数量',width:20},
			{field:'sendDate',title:'时间',width:20}
	    	]
	   	],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb'
	});
});

function planStock(){
	parent.$.messager.confirm('提示', '您确定要盘库？', function(data){
		if (data){
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
			$.ajax({
				url : '${ctx}/stock/planStock/planStock',
				type : 'get',
				async : false,
				cache : false,
				dataType : 'json',
				success : function(data) {
					successTip(data,dg);
					parent.$.messager.progress('close');
				}
			});
		}
	});
}

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
</script>
</body>
</html>