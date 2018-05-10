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
        	 	<input type="text" name="filter_LIKES_groupName" class="easyui-validatebox" data-options="width:150,prompt: '组名'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="permission:permissionGroup:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	        <shiro:hasPermission name="permission:permissionGroup:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="permission:permissionGroup:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="permission:permissionGroup:detail">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg" ></div>
<div id="dlg_rel"></div>  
<script type="text/javascript">
var groupObj = {};
$(function(){   
	groupObj.list=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/permission/group/json', 
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
			{field:'groupName',title:'组名',sortable:true,width:20}, 
			{field:'createDate',title:'创建日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}}
	    ]],
	    sortName:'id',
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
	groupObj.form=$("#dlg").dialog({   
	    title: '新增讨论组',    
	    href:'${ctx}/permission/group/create',
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
				groupObj.form.panel('close');
			}
		}]
	});
}

//删除
function del(){
	var row = groupObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/permission/group/delete/"+row.id,
				success: function(data){
					successTipNew(data,groupObj.list);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = groupObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	groupObj.form=$("#dlg").dialog({   
	    title: '修改讨论组',    
	    href:'${ctx}/permission/group/update/'+row.id,
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
				groupObj.form.panel('close');
			}
		}]
	});
}

//查看明细
function detail(){
	var row = groupObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	groupObj.form=$("#dlg").dialog({   
	    title: '讨论组明细',    
	    href:'${ctx}/permission/group/detail/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				groupObj.form.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	groupObj.list.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	cx(); 
}
</script>
</body>
</html>