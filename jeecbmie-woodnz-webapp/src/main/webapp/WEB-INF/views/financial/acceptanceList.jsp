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
	    <input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="prompt: '提单号'"/>
	    <input type="text" name="filter_LIKES_creditNo" class="easyui-validatebox" data-options="prompt: '信用证号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:acceptance:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:acceptance:delete">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:acceptance:update">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:acceptance:detail">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:acceptance:apply">
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:acceptance:callBack">
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:acceptance:trace">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
	</shiro:hasPermission>
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table>
<div id="dlg"></div>
<div id="traceDlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/financial/acceptance/json',
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'invoiceNo',title:'提单号',sortable:true,width:20},
			{field:'creditNo',title:'信用证号',sortable:true,width:20},
			{field:'prompt',title:'付款期限',sortable:true,width:20},
		    {field:'documentaryBillsDate',title:'押汇日期',sortable:true,width:20},
		    {field:'documentaryBillsExpectBackTime',title:'预计还押汇日期',sortable:true,width:20},
		    {field:'createDate',title:'创建时间',sortable:true,width:25},
		    {field:'state',title:'状态',sortable:true,width:20,
		    	formatter: function(value,row,index){
					return getState('${ctx}/workflow/findCurrentTaskList/' + row.processInstanceId, value);
		    	}
		    }
		]],
		sortName:'id',
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
		fit:true,
	    title: '添加承兑',
	    href:'${ctx}/financial/acceptance/create',
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("create");
			}
		},{
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

//弹窗修改
function upd() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({
		fit:true,
	    title: '修改承兑',
	    href:'${ctx}/financial/acceptance/update/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("update");
			}
		},{
			text:'修改',iconCls:'icon-edit',
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/acceptance/delete/" + row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗查看
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		fit:true,
	    title: '查看明细',
	    href:'${ctx}/financial/acceptance/detail/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
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
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//申请
function apply(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能重复提交！', 'info');
		return;
	}
	parent.$.messager.confirm('提示', '您确定要撤回申请？', function(data){
		if (data){
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
			$.ajax({
				type:'get',
				url:"${ctx}/financial/acceptance/apply/" + row.id,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title:"提示", msg:"流程已启动！", position:"bottomRight" });
					}
			    	if(data=='no deployment'){
			    		parent.$.messager.show({ title:"提示", msg:"没有部署流程！", position:"bottomRight" });
			    	}
			    	if(data=='start fail'){
			    		parent.$.messager.show({ title:"提示", msg:"启动流程失败！", position:"bottomRight" });
			    	}
			    	parent.$.messager.progress('close');
				}
			});
		}
	});
}

//撤回
function callBack(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在撤回申请！', 'info');
		return;
	}
	parent.$.messager.confirm('提示', '您确定要撤回申请？', function(data){
		if (data){
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
			$.ajax({
				type:'get',
				url:"${ctx}/financial/acceptance/callBack/" + row.id + "/" + row.processInstanceId,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title:"提示", msg:"已成功撤回申请！", position:"bottomRight" });
					}
					parent.$.messager.progress('close');
				}
			});
		}
	});
}

//流程跟踪
function trace() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在流程跟踪！', 'info');
		return;
	}
	$.ajaxSetup({type : 'GET'});
	d=$("#traceDlg").dialog({   
	    title: '流程跟踪',
	    width: 680,    
	    height: 300,    
	    href:'${ctx}/workflow/trace/'+row.processInstanceId,
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

//提交表单并提交申请
function applyAnd(action){
	var isValid = $("#mainform").form('validate');//验证表单
	if(!isValid){
		return false;
	}
	parent.$.messager.progress({  
        title : '提示',  
        text : '数据处理中，请稍后....'  
    });
	var jsonData = $("#mainform").serializeArray();//将页面表单序列化成一个JSON结构的对象
	jsonData.push({"name":"apply","value":"true"});//向JSON对象里放入提交申请标志
	$.post('${ctx}/financial/acceptance/' + action, jsonData, function(data){
		successTip(data,dg,d);
		parent.$.messager.progress('close');
	});
}
</script>
</body>
</html>