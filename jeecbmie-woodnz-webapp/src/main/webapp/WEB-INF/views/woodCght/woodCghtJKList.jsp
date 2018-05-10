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
	    <input type="text" name="filter_LIKES_interContractNo" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/>
	    <input type="text" name="filter_LIKES_hth" class="easyui-validatebox" data-options="width:150,prompt: '采购合同号'"/>
	    <input type="text" name="filter_LIKES_xyh" class="easyui-validatebox" data-options="width:150,prompt: '协议号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:woodCghtJK:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:delete">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:update">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:change">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-cv" plain="true" onclick="change();">合同变更</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:view">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:apply">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:callBack">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:trace">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:woodCghtJK:changeState">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-billing" plain="true" onclick="closeOrOpen();">合同状态修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dg"
	data-options="
				rowStyler: function(index,row){
					if (row.closeOrOpen =='关闭'){
						return 'color:#0072E3;font-style:italic;';
					}
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>
<div id="dlg_trace"></div>
<div id="searchGoodsCode"></div>
<div id="dlgProtocol"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/woodCght/woodCghtJK/json', 
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
			{field:'interContractNo',title:'综合合同号',sortable:true,width:25},
			{field:'hth',title:'采购合同号',sortable:true,width:25}, 
			{field:'xyh',title:'协议号',sortable:true,width:25},
			{field:'ghdw',title:'供货单位',sortable:true,width:25,
				formatter: function(value,row,index){
					var val;
					if(row.ghdw!=''&&row.ghdw!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.ghdw,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'dhrq',title:'订货日期 ',sortable:true,width:15},
			{field:'createrDept',title:'申请部门',sortable:true,width:20},
			{field:'ywy',title:'申请人',sortable:true,width:15},
			{field:'createDate',title:'创建时间',sortable:true,width:15},
			{field:'state',title:'流程状态',sortable:true,width:10},
			{field:'closeOrOpen',title:'运行状态',sortable:true,width:10}
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
	    title: '添加采购合同',    
	    href:'${ctx}/woodCght/woodCghtJK/create',
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				accept();
				applyAnd("create");
			}
		},{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				accept();
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
	if(row.state == "变更"){
		$.messager.alert('提示','表单变更状态，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/woodCght/woodCghtJK/delete/"+row.id,
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	if(row.closeOrOpen == "关闭" ){
		$.messager.alert('提示','表单处于关闭状态，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改采购合同',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/woodCght/woodCghtJK/update/'+row.id,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				accept();
				applyAnd("update");
			}
		},{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				accept();
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
	    title: '采购合同明细',    
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/woodCght/woodCghtJK/detail/'+row.id,
	    modal:true,
	    buttons:[
	             {
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}


//申请
function apply(){
	business_page.procStartUrl="${ctx}/woodCght/woodCghtJK/apply";
	business_page.apply(dg);
}

//撤回
function callBack(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在撤回申请！','info');
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
				url:"${ctx}/woodCght/woodCghtJK/callBack/"+row.id+"/"+row.processInstanceId,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "已成功撤回申请！", position: "bottomRight" });
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
		$.messager.alert('提示','表单未提交申请，不存在流程跟踪！','info');
		return;
	}
	d_trace=$("#dlg_trace").dialog({   
	    title: '流程跟踪',
	    width: 680,    
	    height: 450,     
	    type : 'get',
	    href:'${ctx}/workflow/trace/'+row.processInstanceId,
	    maximizable:true,
	    modal:true,
	    //onResize:function(){$(this).dialog('center');},
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_trace.panel('close');
				}
		}]
	});
}

//合同变更
function change(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.closeOrOpen == "关闭" ){
		$.messager.alert('提示','表单处于关闭状态，不能修改！','info');
		return;
	}
	if(row.state != "生效"&row.state != "变更"){
		$.messager.alert('提示','合同当前状态，不能进行变更！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '变更合同',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/woodCght/woodCghtJK/change/'+row.id,
	    modal:true,
	    buttons:[
	             {
				text:'提交申请', iconCls:'icon-hamburg-up',
				handler:function(){
					accept();
					applyAnd("change");
				}
				}, {
			text:'变更',iconCls:'icon-edit',
			handler:function(){
				accept();
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

//弹窗修改状态
function closeOrOpen(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "生效"){
		$.messager.alert('提示','合同当前状态，不能进行修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '合同状态修改',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/woodCght/woodCghtJK/closeOrOpen/'+row.id,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
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

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/woodCght/woodCghtJK/apply";
	business_page.saveAndApply(dg,d,"mainform");
}
</script>
</body>
</html>