<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/static/plugins/kindeditor/themes/default/default.css" />
<script src="${ctx}/static/plugins/kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/kindeditor/lang/zh-CN.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
        	 	<input type="text" id="selInvoiceClassModel" name="filter_EQS_invoiceClassModel" class="easyui-combobox" data-options="width:150,prompt: '发票分类'"/>
        	 	<input type="text" id="selInvoiceTypeModel" name="filter_EQS_invoiceTypeModel" class="easyui-combobox" data-options="width:150,prompt: '发票类型'" />
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="sys:inputInvoice:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:inputInvoice:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:inputInvoice:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:inputInvoice:detail">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
	        
	        <shiro:hasPermission name="sys:inputInvoice:apply">
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:inputInvoice:callBack">
				<a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:inputInvoice:trace">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
			</shiro:hasPermission>
        </div> 
        
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg" ></div>  
<div id="dlg_sub"></div>
<div id="dlgCredit"></div>
<div id="dlg_hetong"></div>
<script type="text/javascript">
var operObj = {};
$(function(){   
	operObj.list=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/financial/inputInvoice/json', 
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
			{field:'invoiceClassModel',title:'发票分类',sortable:true,width:30},
			{field:'invoiceTypeModel',title:'发票类型',sortable:true,width:25}, 
			{field:'tradeMode',title:'贸易方式',sortable:true,width:25},
			{field:'userNameView',title:'客户名',sortable:true,width:25},
			{field:'createrDept',title:'经办部门',sortable:true,width:25},
			{field:'createDate',title:'创建日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}
			},
			{field:'state',title:'流程状态',sortable:true,width:10,formatter:function(value,row,index){  
                return business_page.stateShow(value);
            }}	
	    ]],
	    sortName:'invoiceClassModel',
	    sortOrder:'asc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	//发票分类查询
	$('#selInvoiceClassModel').combobox({
		method:'GET',
		url:'${ctx}/system/dictUtil/getDictByCode/invoiceClass',
		valueField : 'name',
	    textField : 'name',
	    multiple:false,
	    width:250,
	    multiline:true,
// 		panelHeight:'auto',
		editable:false,
	    prompt: '发票分类',
	    onHidePanel:function(){}
	});
	//发票类型
	$('#selInvoiceTypeModel').combobox({
		method:'GET',
		url:'${ctx}/system/dictUtil/getDictByCode/invoiceType',
		valueField : 'name',
	    textField : 'name',
	    multiple:false,
	    width:250,
	    multiline:true,
// 		panelHeight:'auto',
		editable:false,
	    prompt: '发票类型',
	    onHidePanel:function(){}
	})
});

//弹窗增加
function add() {
	operObj.form=$("#dlg").dialog({   
	    title: '新增用印信息',    
	    href:'${ctx}/financial/inputInvoice/create',
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("create");
			}
		},{
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单流程已发起，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/inputInvoice/delete/"+row.id,
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
	if(row.state != "2"){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	operObj.form=$("#dlg").dialog({   
	    title: '修改用印信息',    
	    href:'${ctx}/financial/inputInvoice/update/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("update");
			}
		},{
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
	    title: '商品指标明细',    
	    href:'${ctx}/financial/inputInvoice/detail/'+row.id,
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
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	operObj.list.datagrid('reload',obj); 
}

//申请
function apply(){
		business_page.procStartUrl="${ctx}/financial/inputInvoice/apply";
		business_page.apply(operObj.list);
}

//撤回
function callBack(){
	var row = operObj.list.datagrid('getSelected');
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
				url:"${ctx}/financial/inputInvoice/callBack/" + row.id + "/" + row.processInstanceId,
				success: function(data){
			    	successTip(data,operObj.list);
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
	var row = operObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在流程跟踪！', 'info');
		return;
	}
	business_page.traceProc(row.processInstanceId);;
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/financial/inputInvoice/apply";
	business_page.saveAndApply(operObj.list,operObj.form,"mainform");
}
</script>
</body>
</html>