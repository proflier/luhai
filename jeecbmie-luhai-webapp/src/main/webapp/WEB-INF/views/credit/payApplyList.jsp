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
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '采购合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:payApply:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payApply:delete">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payApply:update">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
  	<shiro:hasPermission name="sys:payApply:change">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-cv" plain="true" onclick="change();">变更</a>
	   	<span class="toolbar-item dialog-tool-separator"></span>
    </shiro:hasPermission>
	<shiro:hasPermission name="sys:payApply:detail">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payApply:apply">
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payApply:callBack">
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payApply:trace">
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
<div id="dlgContract"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/credit/payApply/json?filter_NES_changeState=0',
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		scrollbarSize : 0,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'inteContractNo',title:'内部合同号',sortable:true,width:20},
			{field:'contractNo',title:'采购合同号',sortable:true,width:20},
			{field:'payApplyNo',title:'开证申请号',sortable:true,width:20},
			{field:'ourUnitView',title:'开证单位',sortable:true,width:20},
			{field:'supplierView',title:'供应商',sortable:true,width:20},
		    {field:'createDate',title:'创建时间',sortable:true,width:25},
		    {field:'state',title:'状态',sortable:true,width:10,formatter:function(value,row,index){  
                return business_page.stateShow(value);},
		    },
		    {field:'changeState',title:'变更状态',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='2'){
							val='变更中';
						}else {
							val='非变更';
						}
						return val;
					}else{
						return '';
					}
				}	
			},
		]],
		sortName:'id',
	    sortOrder: 'desc',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#tb',
// 		onLoadSuccess:function(data){
// 			if (data.rows.length > 0) {
// 				mergeCellsByField("dg", "contractNo");
// 				mergeCellsByField("dg", "inteContractNo");
// 			}
// 		},
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({
		fit:true,
	    title: '添加开证申请',
	    href:'${ctx}/credit/payApply/create',
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
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
				d.panel('close');
			}
		}]
	});
}

//弹窗修改
function upd() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	/* if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	} */
	d=$("#dlg").dialog({
		fit:true,
	    title: '修改开证申请',
	    href:'${ctx}/credit/payApply/update/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("update");
			}
		},{
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if(data){
			$.ajax({
				type:'get',
				url:"${ctx}/credit/payApply/delete/" + row.id,
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
	    title: '开证申请',
	    href:'${ctx}/credit/payApply/detail/' + row.id,
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

function change(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "1"){
		$.messager.alert('提示','合同当前状态，不能进行变更！','info');
		return;
	}
	parent.$.messager.confirm('提示', '是否确认变更？', function(data){
		if (data){
			d=$("#dlg").dialog({   
			    title: '变更合同',    
			    fit:true,   
			    closable:false,
			    style:{borderWidth:0},
			    href:'${ctx}/credit/payApply/change/'+row.id,
			    modal:true,
			    buttons:[
		            {
					text:'提交申请', iconCls:'icon-hamburg-up',
						handler:function(){
							applyAnd("change");
						}
					}, {
					text:'保存',iconCls:'icon-edit',
						handler:function(){
							$('#mainform').submit(); 
						}
				},{
					text:'关闭',iconCls:'icon-cancel',
						handler:function(){
							dg.datagrid('reload');
							d.panel('close');
						}
				}]
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
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//申请
function apply(){
		business_page.procStartUrl="${ctx}/credit/payApply/apply";
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
				url:"${ctx}/credit/payApply/callBack/" + row.id + "/" + row.processInstanceId,
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
	business_page.traceProc(row.processInstanceId);;
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/credit/payApply/apply";
	business_page.saveAndApply(dg,d,"mainform");
}
</script>
</body>
</html>