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
	    <input type="text" name="filter_LIKES_serialNumber" class="easyui-validatebox" data-options="width:150,prompt: '请输入流水号'"/>
	    <input type="text" id="filter_EQS_serialTitle"  name="filter_EQS_serialTitle" class="easyui-combobox" data-options="width:200,prompt: '请选择水单抬头'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:serial:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:serial:delete">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:serial:update">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:serial:view">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	 <shiro:hasPermission name="sys:serial:claim">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-issue" plain="true" onclick="claim();">认领</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:serial:cancelClaim">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-arrow-undo" plain="true" onclick="cancelClaim();">取消认领</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	 <shiro:hasPermission name="sys:serial:split">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-exchange" plain="true" onclick="split();">拆分</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:serial:cancelSplit">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-refresh" plain="true" onclick="cancelSplit();">取消拆分</a>
	</shiro:hasPermission>
</div>
<table id="dg"
	data-options="
				rowStyler: function(index,row){
					if (row.contractNo != null){
						return 'color:#0066CC;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>
<div id="dlg_claim"></div>
<div id="d_split"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
// 	    url:'${ctx}/financial/serial/json?filter_NES_splitStatus=parent', 
	    url:'${ctx}/financial/serial/jsonNew', 
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
			{field:'serialNumber',title:'流水号',sortable:true,width:20},
			{field:'serialTitleView',title:'水单抬头',sortable:true,width:20},
			{field:'contractNo',title:'认领合同号',sortable:true,width:20}, 
			{field:'fundCategoryView',title:'资金类别',sortable:true,width:15},
			{field:'serialDate',title:'收款日期',sortable:true,width:15},
			{field:'bankDeadline',title:'银承到期日期',sortable:true,width:15},
 			{field:'money',title:'金额',sortable:true,align:'right',width:20,
// 				formatter: function(value, row, index){
// 					if(value != null){
// 						var str = value.toString().split(".");
// 						return str[0].replace(/\B(?=(?:\d{3})+$)/g, ',') + (str.length == 1 ? "" : "." + str[1]);
// 					}
// 				}	
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.00; //如果不是数字，返回0.000
					}else{
						value = parseFloat(value);
						return(value.toFixed(2)); 
					}
				}
 			},
//  			{field:'claimPerson',title:'认领人',sortable:true,width:15,
//  				formatter: function(value,row,index){
// 					var name = '';
// 					if(value!=null && value!=""){
// 						$.ajax({
// 							url : '${ctx}/system/user/getUserNameByLogin/'+value ,
// 							type : 'get',
// 							cache : false,
// 							async: false,
// 							success : function(data) {
// 								name = data;
// 							}
// 						});
// 					}
// 					return name;
// 				}	
//  			},
 			{field:'claimDate',title:'认领时间',sortable:true,width:15},
			{field:'createDate',title:'创建时间',sortable:true,width:25}
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
	
	//水单抬头
	$('#filter_EQS_serialTitle').combobox({
		method: "get",
		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=10230003',
		valueField : 'customerCode',
		textField : 'customerName',
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加水单', 
	    href:'${ctx}/financial/serial/create',
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
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
	if(row.contractNo != null){
		$.messager.alert('提示','水单已认领，不能删除！','info');
		return;
	}
	if(row.fundSource == "内部拆分"){
		$.messager.alert('提示','水单已拆分，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/serial/delete/"+row.id,
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
	if(row.contractNo != null){
		$.messager.alert('提示','水单已认领，不能修改！','info');
		return;
	}
	if(row.fundSource == "内部拆分"){
		$.messager.alert('提示','水单已拆分，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改水单',    
	    href:'${ctx}/financial/serial/update/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
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
	    title: '水单明细',    
	    href:'${ctx}/financial/serial/detail/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[
	             {
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//认领
function claim(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.contractNo != null){
		$.messager.alert('提示','此记录已经认领！','info');
		return;
	}
	d_claim=$("#dlg_claim").dialog({   
	    title: '水单认领',    
	    width: 400,    
	    height: 260,
	    modal:true,
	    closable:true,
	    href:'${ctx}/financial/serial/claim/'+row.id,
	    buttons:[{
			text:'认领',iconCls:'icon-hamburg-issue',
			handler:function(){
				$('#cliam_form').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_claim.panel('close');
			}
		}]
	});
}

//取消认领
function cancelClaim(){
	var row = dg.datagrid('getSelected');
	if(row.contractNo == null){
		$.messager.alert('提示','此记录未认领，不存在撤销！','info');
		return;
	}
	if(rowIsNull(row)) return;
	
	parent.$.messager.confirm('提示', '取消后无法恢复您确定要取消？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/serial/cancelClaim/"+row.id,
				success: function(data){
					successTipNew(data,dg);
				}
			});
		} 
	});
}


//取消拆分
function cancelSplit(){
	var row = dg.datagrid('getSelected');
	if(row.contractNo != null){
		$.messager.alert('提示','此记录已经认领,不能取消拆分！','info');
		return;
	}
	if(row.splitStatus == 'default'){
		$.messager.alert('提示','此记录未拆分，不存在撤销！','info');
		return;
	}
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '取消后无法恢复您确定要取消？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/serial/cancelSplit/"+row.id,
				success: function(data){
					successTipNew(data,dg);
				}
			});
		} 
	});
}

//拆分
function split(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.contractNo != null){
		$.messager.alert('提示','此记录已经认领,不能拆分！','info');
		return;
	}
	if(row.splitStatus!='default'){
		$.messager.alert('提示','此记录已经拆分,不能拆分！','info');
		return;
	}
	d_split=$("#d_split").dialog({   
	    title: '水单拆分',    
	    width: 400,    
	    height: 220,
	    modal:true,
	    closable:true,
	    href:'${ctx}/financial/serial/split/'+row.id,
	    buttons:[{
			text:'拆分',iconCls:'icon-hamburg-exchange',
			handler:function(){
				$('#split_form').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_split.panel('close');
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
</script>
</body>
</html>