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
       	        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '商品名称'"/>
       	        <input type="text" name="filter_LIKES_code" class="easyui-validatebox" data-options="width:150,prompt: '商品编码'"/>
       	        <input type="text" name="filter_LIKES_classes" class="easyui-validatebox" data-options="width:150,prompt: '级别'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="sys:woodgoodsinfo:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodgoodsinfo:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodgoodsinfo:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodgoodsinfo:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="ckbm()">查看编码</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg"></div>  
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/baseInfo/goodsInfo/json', 
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
			{field:'classes',title:'級別',sortable:true,width:20},
			{field:'className',title:'級別名称',sortable:true,width:20},
			{field:'code',title:'编码',sortable:true,width:20},
			{field:'name',title:'名称',sortable:true,width:20},
			{field:'ifDanger',title:'是否是濒危',sortable:true,width:20},
			{field:'affiliated',title:'所属級別',sortable:true,width:20},
			{field:'hss',title:'换算数',sortable:true,width:20},
			{field:'description',title:'描述',sortable:true,width:20},
			{field:'sqr',title:'登记人',sortable:true,width:20},
	    ]],
	    sortName:'classes',
	    sortOrder:'asc',
		multiSort:true,
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
// 	$('#dg').datagrid('sort', 'classes');    // 排序一个列
	$('#dg').datagrid('sort', 
			{sortName: 'classes',sortOrder: 'asc'},{sortName: 'code',sortOrder: 'desc'});

});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增商品',    
	    fit : true,    
	    href:'${ctx}/baseInfo/goodsInfo/create',
	    maximizable:false,
	    modal:true,
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
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/baseInfo/goodsInfo/delete/"+row.id,
				beforeSend: function (){
					if(row.classes == '1级'){
						parent.$.messager.alert('操作失败','不能删除1级商品!','error');
						return false;
					}
					return true;
				},
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
	    title: '修改商品',    
	    fit : true,    
	    href:'${ctx}/baseInfo/goodsInfo/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
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
}

//查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '商品明细',    
	    fit : true,  
	    href:'${ctx}/baseInfo/goodsInfo/detail/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//弹窗查看编码
function ckbm() {
	d=$("#dlg").dialog({   
	    title: '查看商品编码',    
	    fit : true,   
	    href:'${ctx}/baseInfo/goodsInfo/ckbm',
	    maximizable:false,
	    modal:true,
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
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

</script>
</body>
</html>