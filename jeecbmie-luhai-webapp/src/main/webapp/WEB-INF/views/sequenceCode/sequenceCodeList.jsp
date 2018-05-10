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
		<input type="text" name="filter_LIKES_moduleName" class="easyui-validatebox" data-options="width:150,prompt: '名称'"/>
		<input type="text" name="filter_LIKES_sequenceName" class="easyui-validatebox" data-options="width:150,prompt: '编码'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		<a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:sequenceCode:add">
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:sequenceCode:delete">
		<a id="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:sequenceCode:update">
		<a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:sequenceCode:view">
		<a id="view" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" >查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dataGrid" ></table> 
<div id="dlg"></div>
<script type="text/javascript">
(function(window,$){
	var sequenceCode = {
		dataGrid: {},
		init: function() {
			$("#search").click(sequenceCode.getData);
			$("#reset").click(sequenceCode.resetForm);
			$("#add").click(sequenceCode.add);
			$("#delete").click(sequenceCode.del);
			$("#update").click(sequenceCode.update);
			$("#view").click(sequenceCode.view);
			dataGrid = $('#dataGrid').datagrid({  
				method: "get",
			    url:'${ctx}/system/sequenceCode/json', 
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
					{field:'moduleName',title:'名称',sortable:true,width:20}, 
					{field:'sequenceName',title:'编码',sortable:true,width:20}, 
					{field:'formula',title:'公式',sortable:true,width:60}
			    ]],
			    sortName:'id',
			    sortOrder: 'desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tb',
				onDblClickRow:function(rowIndex, rowData){
					sequenceCode.view();
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
			    title: '添加编码规则', 
			    href:'${ctx}/system/sequenceCode/create',
			    modal:true,
			    width: 550,    
			    height: 330,
			    style:{borderWidth:0},
			    closable:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
							$("#mainform").submit();
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						d.panel('close');
						dataGrid.datagrid('reload');
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
						url:"${ctx}/system/sequenceCode/delete/"+row.id,
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
			    title: '修改编码规则',    
			    href:'${ctx}/system/sequenceCode/update/'+row.id,
			    modal:true,
			    width: 550,    
			    height: 330,    
			    style:{borderWidth:0},
			    closable:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$("#mainform").submit();
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						d.panel('close');
						dataGrid.datagrid('reload');
					}
				}]
			});
		},
		view:function(){//查看明细
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			d=$("#dlg").dialog({   
			    title: '编码规则明细',    
			    href:'${ctx}/system/sequenceCode/detail/'+row.id,
			    modal:true,
			    width: 550,    
			    height: 330,    
			    style:{borderWidth:0},
			    closable:true,
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
	window.sequenceCode = (window.sequenceCode) ? window.sequenceCode : sequenceCode;
	$(function(){
		sequenceCode.init();
	});
})(window,jQuery);
</script>
</body>
</html>