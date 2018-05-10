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
<!-- 	    <input type="text" name="" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/> -->
	    <input id="receiveWarehouse" name="receiveWarehouse" type="text"  class="easyui-validatebox"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
	    <a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	    <a href="#" class="easyui-menubutton" menu="#mm1" iconCls="icon-standard-page-excel">导出Excel</a>
<!--        	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="exportExcel()">导出Excel</a> -->
		</div>
		<div id="mm1" style="width:150px;">
			<div id="exportExcel">导出当前</div>
			<div id="exportExcelAll">导出所有</div>
		</div>
	</form>
</div>
<table id="stockDetailsTable"></table>
<script type="text/javascript">
(function(window,$){
	var stockDetailsReport = {
		dataGrid: {},
		init: function() {
			//收货仓库 
			$('#receiveWarehouse').combobox({
				panelHeight : 'auto',
				method: "get",
				url : '${ctx}/system/downMenu/jsonCK',
				valueField : 'id',
				textField : 'companyName',
				prompt: '仓库名称'
			});
			$("#search").click(stockDetailsReport.getData);
			$("#reset").click(stockDetailsReport.resetForm);
			$("#exportExcel").click(stockDetailsReport.exportExcel);
			$("#exportExcelAll").click(stockDetailsReport.exportExcelAll);
			dataGrid = $("#stockDetailsTable").datagrid({
				method: "get",
			    url:'${ctx}/reportForm/stockDetail/data', 
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
			{field:'goodname',title:'商品名称',align:'center',rowspan:2,sortable:true,width:25},
			{field:'tradetype',title:'贸易类型',align:'center',rowspan:2,sortable:true,width:25},
			{field:'tradeway',title:'贸易方式',align:'center',rowspan:2,sortable:true,width:25},
			{field:'warehouse',title:'仓库',align:'center',rowspan:2,sortable:true,width:25,},
			{field:'unit',title:'实物量单位',align:'center',align:'center',rowspan:2,sortable:true,width:25},	
			{field:'realstock',title:'业务实存数量',align:'center',align:'right',rowspan:2,sortable:true,width:25,
				formatter: function(val,rowData,rowIndex){
			        if(Number(val)!=null)
			        	return Number(val).toFixed(2);
		   		}
		   },
		  {field:'currency',title:'币种',align:'center',rowspan:2,sortable:true,width:25,},
		  {title:'库存余额',align:'center',colspan:3},	
		  {title:'采购成本账龄',align:'center',colspan:4}],
			[
				{field:'instockfee',title:'库存合计',align:'center',sortable:true,width:25,
					formatter: function(val,rowData,rowIndex){
					    if(Number(val)!=null)
					        return Number(val).toFixed(2);
				   	}
				},	
				{field:'price',title:'进价',align:'center',sortable:true,width:25,
					formatter: function(val,rowData,rowIndex){
					    if(Number(val)!=null)
					        return Number(val).toFixed(2);
				   	}
				},
				{field:'transpot',title:'运杂费',align:'center',sortable:true,width:25,
					formatter: function(val,rowData,rowIndex){
						if(Number(val)!=null)
						    return Number(val).toFixed(2);
					}
				},
				{field:'mouth',title:'1个月以内',align:'center',rowspan:2,sortable:true,width:25},
				{field:'season',title:'1-3个月',align:'center',rowspan:2,sortable:true,width:25},
				{field:'secondSeason',title:'3-6个月',align:'center',rowspan:2,sortable:true,width:25},
				{field:'sixMouth',title:'6个月以上',align:'center',rowspan:2,sortable:true,width:25}
			 ]		],
				remoteSort:false,
				multiSort:false,
			    //sortName:'a.cp_contract_no',
			    //sortOrder: 'desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    showFooter: true,
			    toolbar:'#tb'
			});
		},
		getData: function() {
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj);
		}, 
		resetForm: function() {
			$("#searchFrom")[0].reset();
			$('#receiveWarehouse').combobox("clear");
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj); 
		},
		exportExcel:function(){
			var url = "${ctx}/reportForm/stockDetail/exportExcel";
			$("#searchFrom").attr("action", url);  
			var obj=$("#searchFrom").serializeObject();
		    //触发submit事件，提交表单   
		    $("#searchFrom").submit();
		    $("#searchFrom").attr("action", ""); 
		},
		exportExcelAll:function(){
			var url = "${ctx}/reportForm/stockDetail/exportExcelAll";
			$("#searchFrom").attr("action", url);  
			var obj=$("#searchFrom").serializeObject();
		    //触发submit事件，提交表单   
		    $("#searchFrom").submit();
		    $("#searchFrom").attr("action", ""); 
		}
	};
	window.stockDetailsReport = (window.stockDetailsReport) ? window.stockDetailsReport : stockDetailsReport;
	$(function(){
		stockDetailsReport.init();
	});
})(window,jQuery);

</script>
</body>
</html>