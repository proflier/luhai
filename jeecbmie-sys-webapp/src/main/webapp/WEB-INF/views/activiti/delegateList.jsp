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
		        <input type="text" id="procDefKey_search" name="filter_LIKES_procDefKey" class="easyui-validatebox" />
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sd_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sd_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="activiti:delegate:add">
		   		<a id="sd_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="activiti:delegate:delete">
		        <a id="sd_del" href="#" class="easyui-linkbutton" iconCls="icon-standard-stop" plain="true">终止</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="activiti:delegate:update">
		        <a id="sd_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="activiti:delegate:view">
		        <a id="sd_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
	    </div>
	</div>
	<table id="dg" ></table>  
<script type="text/javascript">
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/activiti/delegate/json', 
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
			{field:'procDefKey',title:'流程模块',width:30,
				formatter: function(value,row,index){
					var name = '';
					if(value!=null && value!=""){
						$.ajax({
							url : '${ctx}/process/moduleNameByKey/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								name = data;
							}
						});
					}
					return name;
				}},
			{field:'consigner',title:'委托人',width:20,
				formatter: function(value,row,index){
					var name = '';
					if(value!=null && value!=""){
						$.ajax({
							url : '${ctx}/system/user/getUserNameByLogin/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								name = data;
							}
						});
					}
					return name;
				}
			},
			{field:'mandatary',title:'受委托人',sortable:true,width:20,
				formatter: function(value,row,index){
					var name = '';
					if(value!=null && value!=""){
						$.ajax({
							url : '${ctx}/system/user/getUserNameByLogin/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								name = data;
							}
						});
					}
					return name;
				}
			},
			{field:'beginDate',title:'开始日期',width:30,
				formatter:function(value,row,index){
					 if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd HH:mm:ss"); 
            	}
			},
			{field:'endDate',title:'结束日期',width:30,
				formatter:function(value,row,index){
					 if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd HH:mm:ss"); 
            	}
			},
			{field:'activateFlag',title:'运行标志',width:30,
				formatter:function(value,row,index){
					if("0"==value){
						var time = new Date(row.terminatingDate);
						return "停止(" + time.format("yyyy-MM-dd HH:mm:ss") + ")";
					}
            		return "运行";
            	}
			}
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb'
	});
	$('#procDefKey_search').combobox({
		panelHeight :300,
		method: "get",
		url : '${ctx}/process/allProcIns',
		valueField : 'id',
		textField : 'value',
		prompt: '模块'
	});
	//查询
	$("#sd_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//重置
	$("#sd_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		$("#procDefKey_search").combobox('clear');
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//增加
	$("#sd_add").on("click", function(){
	var add_form =$("<div/>").dialog({   
		    title: '新增委托',    
		    fit:true,    
		    href:'${ctx}/activiti/delegate/create',
		    maximizable:false,
		    modal:true,
		    buttons:[
		     {
				text:'保存',iconCls:'icon-save',id:'button-save',
				handler:function(){
					$("#mainform").submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					add_form.dialog('destroy');
				}
			}]
		});
	});
	//终止
	$("#sd_del").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		if(row.consigner != '${curLoginName}'){
			$.messager.alert('提示','您不能终止他人的委托！','info');
			return;
		}else if(row.activateFlag == 0){
			$.messager.alert('提示','该流程委托已终止！','info');
			return;
		}
		parent.$.messager.confirm('提示', '终止后无法恢复您确定要终止？', function(data){
			if (data){
				$.ajax({
					type:'get',
					data:{id:row.id},
					url:"${ctx}/activiti/delegate/stop",
					success: function(data){
						successTip(data,dg);
					}
				});
			} 
		});
	});
	//修改
	$("#sd_upd").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		if(!(row.consigner == '${curLoginName}'||'${curLoginName}'=='admin')){
			$.messager.alert('提示','您不能修改他人的委托！','info');
			return;
		}
	var update_form=$("<div/>").dialog({   
		    title: '修改委托',    
		    fit:true,  
		    href:'${ctx}/activiti/delegate/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',id:'button-save',
				handler:function(){
					$('#mainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					update_form.panel('destroy');
				}
			}]
		});
	});
	//查看明细
	$("#sd_detail").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		var detail_form=$("<div/>").dialog({   
		    title: '委托明细',    
		    fit : true,    
		    href:'${ctx}/activiti/delegate/detail/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					detail_form.panel('destroy');
				}
			}]
		});
	});
});
</script>
</body>
</html>