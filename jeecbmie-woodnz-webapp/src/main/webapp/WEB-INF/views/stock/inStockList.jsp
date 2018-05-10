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
		<input type="text" name="filter_LIKES_inStockId_OR_billId" class="easyui-validatebox" data-options="width:150,prompt: '入库编号或提单号'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:inStock:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:inStock:delete">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:inStock:update">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:inStock:view">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:inStock:confirm">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="confirm()">确认</a>
        <span class="toolbar-item dialog-tool-separator"></span>
    </shiro:hasPermission> 
    <shiro:hasPermission name="sys:inStock:cancleConfirm">
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancleConfirm()">取消确认</a>
    </shiro:hasPermission>  
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.confirm == '1'){
						return 'color:#0072E3;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>
<div id="dlgBillId"></div>
<div id="searchGoodsCode_inStock"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/stock/inStock/json', 
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
			{field:'inStockId',title:'入库单号',sortable:true,width:20}, 
			{field:'billId',title:'提单号',sortable:true,width:20},
			{field:'inStockDate',title:'入库日期',sortable:true,width:15},
// 			{field:'receiveWarehouse',title:'收货仓库',sortable:true,width:20,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(row.receiveWarehouse!=''&&row.receiveWarehouse!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url : '${ctx}/system/downMenu/getDictName/warehouse/'+row.receiveWarehouse ,
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
// 			},
			{field:'companyName',title:'公司抬头',sortable:true,width:25,
				formatter: function(value,row,index){
					var val;
					if(row.companyName!=''&&row.companyName!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getBaseInfoName/'+row.companyName ,
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
			{field:'createrName',title:'登记人',sortable:true,width:15},
			{field:'determineName',title:'确认人',sortable:true,width:15},
			{field:'determineTime',title:'确认时间',sortable:true,width:15}
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
	    title: '添加入库', 
	    href:'${ctx}/stock/inStock/create',
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				if(accept()){
					$("#mainform").submit();
				}
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
	if(row.confirm == '1'){
		$.messager.alert('提示','入库已经确认，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/inStock/delete/"+row.id,
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
	if(row.confirm == "1"){
		$.messager.alert('提示','入库已经确认，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({    
	    title: '修改入库',    
	    href:'${ctx}/stock/inStock/update/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				if(accept()){
					$("#mainform").submit();
				}
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
	    title: '入库明细',    
	    href:'${ctx}/stock/inStock/detail/'+row.id,
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

//确认入库
function confirm(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm == "1"){
		$.messager.alert('提示','入库已经确认，不能重复确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '确认入库？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/inStock/confirm/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//取消确认
function cancleConfirm(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm != "1"){
		$.messager.alert('提示','入库未确认，不能取消确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '取消确认入库？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/inStock/cancleConfirm/"+row.id,
				success: function(data){
					successTip(data,dg);
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