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
		<input type="text" name="filter_LIKES_allocationNo" class="easyui-validatebox" data-options="width:150,prompt: '调拨编码'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:stockAllocation:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增调出</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:stockAllocation:inAllocation">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="inAlloaction()">添加调入</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:stockAllocation:update">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改调出</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:stockAllocation:delete">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:stockAllocation:view">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dg" ></table> 
<div id="dlg"></div>
<div id="dlgStockGoods"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/stock/stockAllocation/json', 
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
			{field:'allocationNo',title:'调拨编码',sortable:true,width:25}, 
			{field:'shipNoView',title:'船名',sortable:true,width:35}, 
			{field:'sourceWarehouseCodeView',title:'原仓库名称',sortable:true,width:15},
			{field:'destWarehouseCodeView',title:'目标仓库名称',sortable:true,width:15},
			{field:'goodsNameView',title:'商品名称',sortable:true,width:15},
			{field:'innerShipNoView',title:'开单船次',sortable:true,width:30},
			{field:'allocationQuantity',title:'调拨数量',sortable:true,align:'right',width:15,
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.000; //如果不是数字，返回0.000
					}else{
//							value = Math.abs(value); //取绝对值
						value = parseFloat(value);
						return(value.toFixed(3)); 
					}
				}	
			},
			{field:'allocationPerson',title:'调拨人',sortable:true,width:15},
			{field:'inTime',title:'调入日期',sortable:true,width:15},
			{field:'receivePerson',title:'接收人',sortable:true,width:10},
			{field:'outTime',title:'调出日期',sortable:true,width:15},
			{field:'summary',title:'备注',sortable:true,width:15},
			
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
	    title: '添加库存调拨', 
	    href:'${ctx}/stock/stockAllocation/create',
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
				url:"${ctx}/stock/stockAllocation/delete/"+row.id,
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
	    title: '修改库存调拨',    
	    href:'${ctx}/stock/stockAllocation/update/'+row.id,
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

//弹窗-调入
function inAlloaction(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({    
	    title: '修改库存调拨',    
	    href:'${ctx}/stock/stockAllocation/inAlloaction/'+row.id,
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
	    title: '库存调拨明细',    
	    href:'${ctx}/stock/stockAllocation/detail/'+row.id,
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