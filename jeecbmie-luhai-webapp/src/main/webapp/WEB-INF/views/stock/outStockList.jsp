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
		<input type="text" name="filter_LIKES_outStockNo" class="easyui-validatebox" data-options="width:150,prompt: '出库单编号'"/>
		<input type="text" name="filter_LIKES_deliveryNo" class="easyui-validatebox" data-options="width:150,prompt: '放货单号'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		<a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:outStock:add">
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:outStock:delete">
		<a id="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:outStock:update">
		<a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:outStock:view">
		<a id="view" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" >查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:outStock:confirm">
		<a id="confirm" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" >确认</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:outStock:cancleConfirm">
		<a id="cancleConfirm" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" >取消确认</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dataGrid" data-options="
				rowStyler: function(index,row){
					if (row.confirm == '1'){
						return 'color:#0072E3;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>
<div id="dlgBillId"></div>
<div id="dlg_goods"></div>
<script type="text/javascript">
(function(window,$){
	var outStock = {
		dataGrid: {},
		init: function() {
			$("#search").click(outStock.getData);
			$("#reset").click(outStock.resetForm);
			$("#add").click(outStock.add);
			$("#delete").click(outStock.del);
			$("#update").click(outStock.update);
			$("#view").click(outStock.view);
			$("#confirm").click(outStock.confirm);
			$("#cancleConfirm").click(outStock.cancleConfirm);
			dataGrid = $('#dataGrid').datagrid({  
				method: "get",
			    url:'${ctx}/stock/outStock/json', 
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
					{field:'outStockNo',title:'出库单编号',sortable:true,width:20}, 
// 					{field:'receiptNo',title:'提货单编号',sortable:true,width:20},receiptCode
					{field:'receiptCode',title:'收款单位',sortable:true,width:20,
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
					{field:'deliveryNo',title:'放货单号',sortable:true,width:20},
					{field:'realQuantity',title:'实发数量',sortable:true,align:'right',width:15,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.000; //如果不是数字，返回0.000
							}else{
// 								value = Math.abs(value); //取绝对值
								value = parseFloat(value);
								return(value.toFixed(3)); 
							}
						}
					},
					{field:'outStockDate',title:'出库日期',sortable:true,width:15},
					{field:'createrName',title:'登记人',sortable:true,width:15},
					{field:'determineName',title:'确认人',sortable:true,width:15},
					{field:'determineTime',title:'确认时间',sortable:true,width:15}
			    ]],
			    sortName:'confirm,outStockDate',
			    sortOrder: 'asc,desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tb',
				onDblClickRow:function(rowIndex, rowData){
					outStock.view();
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
			    title: '添加', 
			    href:'${ctx}/stock/outStock/create',
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
		},
		del:function(){//删除
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.confirm == "1"){
				$.messager.alert('提示','出库已经确认，不能修改！','info');
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/stock/outStock/delete/"+row.id,
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
			if(row.confirm == "1"){
				$.messager.alert('提示','出库已经确认，不能修改！','info');
				return;
			}
			d=$("#dlg").dialog({    
			    title: '修改出库',    
			    href:'${ctx}/stock/outStock/update/'+row.id,
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
		},
		view:function(){//查看明细
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			d=$("#dlg").dialog({   
			    title: '出库明细',    
			    href:'${ctx}/stock/outStock/detail/'+row.id,
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
		},
		confirm:function (){
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.confirm == "1"){
				$.messager.alert('提示','出库已经确认，不能重复确认！','info');
				return;
			}
			parent.$.messager.confirm('提示', '确认出库？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/stock/outStock/confirm/"+row.id,
						success: function(data){
							successTipNew(data,dataGrid);
						}
					});
				} 
			});
		},
		cancleConfirm:function (){//取消确认
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.confirm != "1"){
				$.messager.alert('提示','出库未确认，不能取消确认！','info');
				return;
			}
			parent.$.messager.confirm('提示', '取消确认入库？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/stock/outStock/cancleConfirm/"+row.id,
						success: function(data){
							successTip(data,dataGrid);
						}
					});
				} 
			});
		}
	};
	window.outStock = (window.outStock) ? window.outStock : outStock;
	$(function(){
		outStock.init();
	});
})(window,jQuery);
</script>
</body>
</html>