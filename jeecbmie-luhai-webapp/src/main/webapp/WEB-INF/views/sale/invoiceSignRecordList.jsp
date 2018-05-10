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
		        <input type="text" name="filter_LIKES_signer" class="easyui-validatebox" data-options="width:150,prompt: '签收人'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sf_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sf_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="sale:invoiceSignRecord:add">
		   		<a id="scl_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sale:invoiceSignRecord:delete">
		        <a id="scl_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:invoiceSignRecord:update">
		        <a id="scl_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:invoiceSignRecord:print">
			     <a id="scl_print" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">打印</a>
			     <span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
		    <shiro:hasPermission name="sale:invoiceSignRecord:view">
		        <a id="scl_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		    </shiro:hasPermission>
	    </div>
	</div>
	<table id="dg"></table>  <!-- 首页到单列表  -->
	<div id="dlg"></div>  <!-- 到单页面弹窗  -->
	<div id="dlg_sub"></div>
	<div id="dlg_print"></div>
<script type="text/javascript">
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/sale/invoiceSignRecord/json', 
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
			{field:'customerView',title:'客户名称',sortable:true,width:20,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&& value!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}
			},
			{field:'mailDate',title:'寄票日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
           	{field:'signer',title:'签收人',sortable:true,width:20},
   			{field:'signDate',title:'签收日期',sortable:true,width:20,
   				formatter:function(value,row,index){
   					if(value == null){
   						return null;
   					}
               		var time = new Date(value);
               		return time.format("yyyy-MM-dd");
               	}}
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	//查询
	$("#sf_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//重置
	$("#sf_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//增加
	$("#scl_add").on("click", function(){
		d=$("#dlg").dialog({   
		    title: '新增',    
		    fit:true,    
		    href:'${ctx}/sale/invoiceSignRecord/create',
		    maximizable:false,
		    modal:true,
		    buttons:[
		     {
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
	});
	//删除
	$("#scl_del").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:"${ctx}/sale/invoiceSignRecord/delete/"+row.id,
					success: function(data){
						successTipNew(data,dg);
					}
				});
			} 
		});
	});
	//修改
	$("#scl_upd").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		d=$("#dlg").dialog({   
		    title: '修改',    
		    fit:true,  
		    href:'${ctx}/sale/invoiceSignRecord/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[
		     {
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$('#mainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	});
	$("#scl_print").on("click",function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		var print = $("#dlg_print").dialog({
			title: '放货打印',    
		    fit:false, 
		    width: 900,    
		    height: 600,
		    href:'${ctx}/sale/invoiceSignRecord/toPrintPage/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'打印', iconCls:'icon-hamburg-up',
			    handler:function(){
			    	$('#printMmainDiv').jqprint();
				}
			},
		    {
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					print.panel('close');
				}
			}]
		});
	});
	//查看明细
	$("#scl_detail").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		d=$("#dlg").dialog({   
		    title: '发票明细',    
		    fit : true,    
		    href:'${ctx}/sale/invoiceSignRecord/detail/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	});
});

</script>
</body>
</html>