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
		<input type="text" id="filter_LIKES_contractCategory" name="filter_LIKES_contractCategory" class="easyui-combobox" data-options="width:150,prompt: '合同类别'"/>
		<input type="text" name="filter_LIKES_innerContractNo" class="easyui-validatebox" data-options="width:150,prompt: '归档编号'"/>
		<input type="text" id="filter_LIKES_signAffi" name="filter_LIKES_signAffi" class="easyui-combobox" data-options="width:150,prompt: '签约方'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		<a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:contractManagement:add">
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:contractManagement:delete">
		<a id="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:contractManagement:update">
		<a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:contractManagement:view">
		<a id="view" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" >查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true" onclick="exportPdf()">导出pdf</a>
	</shiro:hasPermission>
</div>
<table id="dataGrid" ></table> 
<div id="dlg"></div>
<div id="dlgContract"></div>
<script type="text/javascript">
(function(window,$){
	var contractManagement = {
		dataGrid: {},
		init: function() {
			$("#search").click(contractManagement.getData);
			$("#reset").click(contractManagement.resetForm);
			$("#add").click(contractManagement.add);
			$("#delete").click(contractManagement.del);
			$("#update").click(contractManagement.update);
			$("#view").click(contractManagement.view);
			dataGrid = $('#dataGrid').datagrid({  
				method: "get",
			    url:'${ctx}/administration/contractManagement/json', 
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
					{field:'contractCategory',title:'合同类别',sortable:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/dictUtil/getDictNameByCode/contractCategoryAll/"+value,
									success: function(data){
										if(data != null){
											val = data;
										} else {
											val = '';
										}
									}
								});
								return val;
							}else{
								return '';
							}
						}	
					}, 
					{field:'innerContractNo',title:'归档编号',sortable:true,width:20},
					{field:'contractNo',title:'合同编号',sortable:true,width:20},
					{field:'signAffi',title:'签约方',sortable:true,width:15,
						formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
									success: function(data){
										if(data != null){
											val = data;
										} else {
											val = '';
										}
									}
								});
								return val;
							}else{
								return '';
							}
						}	
					},
					{field:'contractName',title:'合同名称',sortable:true,width:15},
					{field:'singnDate',title:'签约日期',sortable:true,width:15},
					{field:'sealDate',title:'盖章日期',sortable:true,width:15},
					{field:'receiveOrgDate',title:'收原件日期',sortable:true,width:15},
			    ]],
			    sortName:'id',
			    sortOrder: 'desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tb',
				onDblClickRow:function(rowIndex, rowData){
					contractManagement.view();
				}
			});
		},
		getData: function() {
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj);
		}, 
		resetForm: function() {
			$("#searchFrom")[0].reset();
			$('#filter_LIKES_contractCategory').combobox('clear');
			$('#filter_LIKES_signAffi').combobox('clear');
			$(".easyui-combobox").combobox('clear');
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj); 
		},
		add:function() {//弹窗增加
			d=$("#dlg").dialog({   
			    title: '添加', 
			    href:'${ctx}/administration/contractManagement/create',
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
						url:"${ctx}/administration/contractManagement/delete/"+row.id,
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
			    title: '修改合同管理',    
			    href:'${ctx}/administration/contractManagement/update/'+row.id,
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
			    title: '合同管理明细',    
			    href:'${ctx}/administration/contractManagement/detail/'+row.id,
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
	window.contractManagement = (window.contractManagement) ? window.contractManagement : contractManagement;
	$(function(){
		contractManagement.init();
	});
})(window,jQuery);

//导出pdf
function exportPdf(){
	var row = dataGrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var url = "${ctx}/administration/contractManagement/exportPdf/" + row.id;
	window.location.href = url;
}

//仓库名称
$('#filter_LIKES_signAffi').combobox({
// 	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230001or10230002or10230003',
	valueField : 'customerCode',
	textField : 'customerName',
});

//仓库名称
$('#filter_LIKES_contractCategory').combobox({
	panelHeight : 'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/contractCategoryAll',
	valueField : 'code',
	textField : 'name'
});
</script>
</body>
</html>