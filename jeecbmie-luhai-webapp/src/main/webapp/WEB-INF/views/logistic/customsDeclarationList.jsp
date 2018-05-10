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
	<div>
		<form id="searchFrom" action="">
	        <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '合同协议号'"/>
	        <span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
		</form>
		
	   	<shiro:hasPermission name="lh:customsDeclaration:add">
	   		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	   	</shiro:hasPermission>
	   	<shiro:hasPermission name="lh:customsDeclaration:delete">
	        <span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="lh:customsDeclaration:update">
	    	<span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="lh:customsDeclaration:view">
	    	<span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="lh:customsDeclaration:view">
	    	<span class="toolbar-item dialog-tool-separator"></span>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true" onclick="exportPDF()">导出pdf</a>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="lh:customsDeclaration:update">
	    	<span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateFieldDate('declaration')">商检申报</a>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="lh:customsDeclaration:update">
	    	<span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateFieldDate('inspectionRelease')">商检放行</a>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="lh:customsDeclaration:update">
	    	<span class="toolbar-item dialog-tool-separator"></span>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateFieldDate('customsRelease')">海关放行</a>
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
<div id="dlg"></div>  
<div id="dlg_trace"></div>
<div id="dlg_goods"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/logistic/customsDeclaration/json', 
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
			{field:'contractNo',title:'合同协议号',sortable:true,width:25}, 
			{field:'consignee',title:'收发货人',sortable:true,width:25}
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
	
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({
	    title: '新增报关单',    
	    fit:true,    
	    href:'${ctx}/logistic/customsDeclaration/create',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
		    handler:function(){
				parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
					if(data){
						applyAnd("create");
					}
				});
			}
		}
	    ,{
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
	/* if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能删除！','info');
		return;
	} */
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/customsDeclaration/delete/"+row.id,
				success: function(data){
					successTipNew(data,dg);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	/* if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	} */
	d=$("#dlg").dialog({   
	    title: '修改报关单',    
	    fit:true,    
	    href:'${ctx}/logistic/customsDeclaration/update/' + row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
					if(data){
						applyAnd("update");
					}
				});
			}
		},{
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
	    title: '报关单明细',    
	    fit:true,  
	    href:'${ctx}/logistic/customsDeclaration/detail/' + row.id,
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
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}


//申请
function apply(){
	business_page.procStartUrl="${ctx}/logistic/customsDeclaration/apply";
	business_page.apply(dg);
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
				url:"${ctx}/logistic/customsDeclaration/callBack/" + row.id + "/" + row.processInstanceId,
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
		$.messager.alert('提示', '表单未发起，不存在流程跟踪！', 'info');
		return;
	}
	business_page.traceProc(row.processInstanceId);
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/logistic/customsDeclaration/apply";
	business_page.saveAndApply(dg,d,"mainform");
}

//导出pdf
function exportPDF(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var url = "${ctx}/logistic/customsDeclaration/exportPDF/" + row.id;
	window.location.href = url;
}

//撤回
function updateFieldDate(name){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.progress({  
        title : '提示',  
        text : '数据处理中，请稍后....'  
    });
	$.ajax({
		type:'get',
		url:"${ctx}/logistic/customsDeclaration/updateFieldDate/" + name + "/" + row.id,
		success: function(data){
			var data = JSON.parse(data);
	    	successTip(data.returnFlag, dg);
			if(data.returnFlag == 'success'){
				parent.$.messager.show({ title:"提示", msg:data.returnMsg, position:"bottomRight" });
			}
			parent.$.messager.progress('close');
		}
	});
}
</script>
</body>
</html>
