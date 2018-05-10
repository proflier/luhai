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
		<input type="text" name="filter_LIKES_offerNo" class="easyui-validatebox" data-options="width:150,prompt: '报盘单号'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="lh:saleOffer:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="lh:saleOffer:delete">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="lh:saleOffer:update">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="lh:saleOffer:view">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="lh:saleOffer:view">
	    <span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true" onclick="">导出pdf</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<div id="dlg_form"></div>
<div id="dlg_goods"></div>
<div id="dlg_menu"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/sale/saleOffer/json', 
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
			{field:'id',title:'id',hidden:true},
			{field:'offerNo',title:'报盘单号',sortable:true,width:20},
			{field:'customer',title:'客户名称',sortable:true,width:20},
			{field:'contactPerson',title:'联系人',sortable:true,width:20},
			{field:'phoneContact',title:'联系电话',sortable:true,width:20},
			{field:'businessManager',title:'业务经办人',sortable:true,width:20},
			{field:'billDate',title:'制单日期',sortable:true,width:20}
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
	    title: '添加销售报盘', 
	    href:'${ctx}/sale/saleOffer/create',
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			},
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/sale/saleOffer/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({    
	    title: '修改销售报盘',    
	    href:'${ctx}/sale/saleOffer/update/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
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

//查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '查看销售报盘',    
	    href:'${ctx}/sale/saleOffer/detail/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[
	             {
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
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}
</script>
</body>
</html>