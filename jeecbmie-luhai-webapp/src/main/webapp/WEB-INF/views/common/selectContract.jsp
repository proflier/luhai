<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div style="margin: 5px;">
		<input type="radio" name="configRadio" style="margin-top:-2px" onclick="config_list(0)" checked="checked"/>销售合同
		<input type="radio" name="configRadio" style="margin-top:-2px" onclick="config_list(1)"/>采购合同
		<form id="searchFrom_config" action="">
			<input type="text" id="filter_config_contractNo" name="filter_EQS_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_config()">重置</a> 
		</form>
	</div>
<table id="dg_config"></table>
<script type="text/javascript">
var dg_config;

function config_list(value) {
	if (value == 0) {
		$('#filter_config_contractNo').attr("name", "filter_EQS_contractNo");
		saleDg();
	} else {
		$('#filter_config_contractNo').attr("name", "filter_EQS_purchaseContractNo");
		purchaseDg();
	}
}

function saleDg() {
	dg_config=$('#dg_config').datagrid({
		method: "get",
		url:'${ctx}/sale/saleContract/json?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1',
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20 ],
		singleSelect:true,
		enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		columns:[[
		    {field:'id',title: '选择',
				formatter:function(value, rowData, rowIndex){
					return '<input type="radio" name="selectRadio" id="selectRadio" value="' + rowData.id + '" />';
	        	}
		    },
			{field:'contractNo',title:'销售合同号',sortable:true},
			{field:'sellerView',title:'卖受人',sortable:true},
			{field:'purchaserView',title:'买受人',sortable:true},
		    {field:'createDate',title:'创建时间',sortable:true}
		]],
		sortName:'createDate',
		onClickRow : function(rowIndex, rowData) {
			$("input[name='selectRadio']")[rowIndex].checked = true;
		}
	});
}

function purchaseDg() {
	dg_config=$('#dg_config').datagrid({
		method: "get",
		url:'${ctx}/purchase/purchaseContract/json?filter_EQS_state=1&filter_EQS_changeState=1',
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20 ],
		singleSelect:true,
		enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		columns:[[
		    {field:'id',title: '选择',
				formatter:function(value, rowData, rowIndex){
					return '<input type="radio" name="selectRadio" id="selectRadio" value="' + rowData.id + '" />';
	        	}
		    },
			{field:'purchaseContractNo',title:'采购合同号',sortable:true},
			{field:'deliveryUnitView',title:'购货单位',sortable:true},
			{field:'businessManagerView',title:'业务经办人',sortable:true},
		    {field:'createDate',title:'创建时间',sortable:true}
		]],
		sortName:'createDate',
		onClickRow : function(rowIndex, rowData) {
			$("input[name='selectRadio']")[rowIndex].checked = true;
		}
	});
}

$(function(){
	saleDg();
});

//创建查询对象并查询
function cx_config() {
	var obj = $("#searchFrom_config").serializeObject();
	dg_config.datagrid('reload', obj);
}

function reset_config() {
	$("#searchFrom_config")[0].reset();
	var obj = $("#searchFrom_config").serializeObject();
	dg_config.datagrid('reload', obj);
}
</script>
</body>
</html>