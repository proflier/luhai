<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>  
<head>  
<title></title>  
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>  
<body>
<form id="delegateTaskForm" action="${ctx}/workflow/task/delegate" method="post">
<div id="formDiv" align="left" style="margin-top: 10px;margin-left: 5px;">
	<span>委托人:</span>
	<input id='delegateUserId' name='delegateUserId' class="easyui-combobox" data-options="required:true"/>
	
</div>
<div style="height: 230px;margin-top: 10px;">
	<table id="delegatetasks"></table>
</div>
	
</form>
<script type="text/javascript">
$(function() {
	$('#delegateUserId').combotree({
		width:180,
		method:'GET',
		url:'${ctx}/workflow/getCompanyUser',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
		onBeforeSelect:function(node){
			if(node.type!='2'){
				return false;
			}
		},
	    onHidePanel:function(){}
	});
	$('#delegateTaskForm').form({
	    onSubmit: function(){
	    	
	    	var isValid = $(this).form('validate');
			return isValid;
	    },    
	    success:function(data){   
				successTipNew(data,dg);
				d_delegate.panel('close');
	    }
	});
	$('#delegatetasks').datagrid({    
		method: "get",
		data : JSON.parse('${taskList}'),
	    fit : true,
		fitColumns : true,
		border : false,
		rownumbers:true,
		striped:true,
		idField : 'id',
	    columns:[[
			{field:'id',title:'id',checkbox:true},
			{field:'name',title:'任务名称',width:10},
			{field:'assignee',title:'当前办理人',width:20},
			{field:'createTime',title:'创建时间'}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    remoteSort:false,
	    checkOnSelect:true,
	    onLoadSuccess:function(){
	    	$(".datagrid-header-check").html("");
	    },
	    onSelect:function(index,row){
	    	var t = $('<input name="delegateTaskId" type="hidden"/>').val(row.id);
	    	t.attr("id","task"+index);
	    	$('#formDiv').append(t);
	    },
	    onUnselect:function(index,row){
	    	$('#task'+index).remove();
	    },
	});
});
</script>
</body>
</html>