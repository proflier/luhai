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
		<input type="text" name="filter_LIKES_receiptNo" class="easyui-validatebox" data-options="width:150,prompt: '预收票编号'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		<a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:receipt:add">
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:receipt:delete">
		<a id="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:receipt:update">
		<a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:receipt:view">
		<a id="view" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" >查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dataGrid" ></table> 
<div id="dlg"></div>
<script type="text/javascript">
(function(window,$){
	var receipt = {
		dataGrid: {},
		init: function() {
			$("#search").click(receipt.getData);
			$("#reset").click(receipt.resetForm);
			$("#add").click(receipt.add);
			$("#delete").click(receipt.del);
			$("#update").click(receipt.update);
			$("#view").click(receipt.view);
			dataGrid = $('#dataGrid').datagrid({  
				method: "get",
			    url:'${ctx}/financial/receipt/json', 
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
					{field:'receiptNo',title:'预收票编号',sortable:true,width:20}, 
					{field:'shipName',title:'批次船名',sortable:true,width:20},
					{field:'supplyView',title:'供应商',sortable:true,width:15},
					{field:'invoiceNo',title:'发票号',sortable:true,width:15},
					{field:'creditNo',title:'信用证号',sortable:true,width:15},
					{field:'createrName',title:'登记人',sortable:true,width:15},
			    ]],
			    sortName:'id',
			    sortOrder: 'desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tb',
				onDblClickRow:function(rowIndex, rowData){
					receipt.view();
				}
			});
		},
		getData: function() {
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj);
		}, 
		resetForm: function() {
			$("#searchFrom")[0].reset();
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj); 
		},
		add:function() {//弹窗增加
			d=$("#dlg").dialog({   
			    title: '添加预收票', 
			    href:'${ctx}/financial/receipt/create',
			    modal:true,
			    fit:true,
			    style:{borderWidth:0},
			    closable:false,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
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
		},
		del:function(){//删除
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/financial/receipt/delete/"+row.id,
						success: function(data){
							successTip(data,dataGrid);
						}
					});
				} 
			});
		},
		update:function(){//弹窗修改
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			d=$("#dlg").dialog({    
			    title: '修改预收票',    
			    href:'${ctx}/financial/receipt/update/'+row.id,
			    modal:true,
			    fit:true,
			    style:{borderWidth:0},
			    closable:false,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
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
		},
		view:function(){//查看明细
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			d=$("#dlg").dialog({   
			    title: '预收票明细',    
			    href:'${ctx}/financial/receipt/detail/'+row.id,
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
		
	};
	window.receipt = (window.receipt) ? window.receipt : receipt;
	$(function(){
		receipt.init();
	});
})(window,jQuery);
</script>
</body>
</html>