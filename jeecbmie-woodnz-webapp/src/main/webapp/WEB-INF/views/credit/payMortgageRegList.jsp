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
	    <input type="text" name="filter_LIKES_woodBillId" class="easyui-validatebox" data-options="prompt: '到单号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:payMortgageReg:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payMortgageReg:delete">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payMortgageReg:update">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payMortgageReg:view">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dg" ></table>
<div id="dlg"></div>
<div id="dlg_loadbillid_pay_mortgage"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/credit/payMortgageReg/json',
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		scrollbarSize : 0,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'woodBillId',title:'到单号',sortable:true,width:20},
		    {field:'mortgageInterestO',title:'押汇利息（外币）',sortable:true,width:20},
			{field:'mortgageInterestRMB',title:'押汇利息（人民币）',sortable:true,width:20},
			{field:'allMoney',title:'本息合计',sortable:true,width:20},
		    {field:'payMortgageTime',title:'还汇日期',sortable:true,width:20},
		    {field:'createDate',title:'创建时间',sortable:true,width:25}
		]],
		sortName:'id',
	    sortOrder: 'desc',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({
		fit:true,
	    title: '添加付汇登记',
	    href:'${ctx}/credit/payMortgageReg/create',
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//弹窗修改
function upd() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		fit:true,
	    title: '修改付汇登记',
	    href:'${ctx}/credit/payMortgageReg/update/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if(data){
			$.ajax({
				type:'get',
				url:"${ctx}/credit/payMortgageReg/delete/" + row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗查看
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		fit:true,
	    title: '查看明细',
	    href:'${ctx}/credit/payMortgageReg/detail/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}



//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj);
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}
</script>
</body>
</html>