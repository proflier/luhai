<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
        	 	<input type="text" name="filter_LIKES_typeName" class="easyui-validatebox" data-options="width:150,prompt: '名称'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="base:goodsType:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="base:goodsType:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="base:goodsType:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="base:goodsType:detail">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg" ></div>  
<script type="text/javascript">
var operObj = {};
$(function(){   
	operObj.list=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/baseInfo/goodsType/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 5,10,15,20 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'typeName',title:'名称',sortable:true,width:20},  
			{field:'typeCode',title:'编码',sortable:true,width:20},
			{field:'createDate',title:'登记时间',sortable:true,width:25},
			{field:'status',title:'状态',sortable:true,width:25,
				formatter: function(value,row,index){
					if (row.status == 0){
						return '停用';
					} else{
						return '启用';
					}
				}	
			}
	    ]],
	    sortName:'typeCode',
	    sortOrder:'asc',
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
	operObj.form=$("#dlg").dialog({   
	    title: '新增商品类别',    
	    href:'${ctx}/baseInfo/goodsType/create',
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				operObj.form.panel('close');
			}
		}]
	});
}

//删除
function del(){
	var row = operObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/baseInfo/goodsType/delete/"+row.id,
				success: function(data){
					successTipNew(data,operObj.list);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = operObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	operObj.form=$("#dlg").dialog({   
	    title: '修改商品类别',    
	    href:'${ctx}/baseInfo/goodsType/update/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$('#mainform').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				operObj.form.panel('close');
			}
		}]
	});
}

//查看明细
function detail(){
	var row = operObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	operObj.form=$("#dlg").dialog({   
	    title: '商品类别明细',    
	    href:'${ctx}/baseInfo/goodsType/detail/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				operObj.form.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	operObj.list.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	cx(); 
}
</script>
</body>
</html>