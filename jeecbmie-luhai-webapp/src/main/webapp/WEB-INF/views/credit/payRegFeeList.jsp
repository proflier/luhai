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
	    <input type="text" name="filter_LIKES_payApplyNo" class="easyui-validatebox" data-options="prompt: '开证申请号'"/>
	    <input type="text" name="filter_LIKES_inteContractNo" class="easyui-validatebox" data-options="prompt: '内部合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:payRegFee:reg">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="reg()">登记</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:payRegFee:view">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.payTime != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/credit/payReg/json?filter_EQS_state=已登记',
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
			{field:'payApplyNo',title:'开证申请号',sortable:true,width:20},
			{field:'inteContractNo',title:'内部合同号',sortable:true,width:20},
		    {field:'ourUnitView',title:'开证单位',sortable:true,width:20,
// 		    	formatter: function(value,row,index){
// 					var val;
// 					if(row.ourUnit!=''&&row.ourUnit!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/'+row.ourUnit ,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}	
		    },
		    {field:'bankNameView',title:'银行名称',sortable:true,width:20,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(row.bankName!=''&&row.bankName!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/'+row.bankName ,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}		
		    },
 		    {field:'creditMoney',title:'开证金额',sortable:true,width:20},
 		    {field:'payTime',title:'付费时间',sortable:true,width:20},
 			{field:'regTime',title:'开证日期',sortable:true,width:20}
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

//弹窗登记
function reg() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.payTime != null){
		$.messager.confirm('确认对话框', '已经登记，确认重新登记？', function(r){
			if (r){
				d=$("#dlg").dialog({
					fit:true,
				    title: '开证费登记',
				    href:'${ctx}/credit/payReg/payRegFee/' + row.id,
				    modal:true,
				    closable:false,
				    style:{borderWidth:0},
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
		});
	}else{
		d=$("#dlg").dialog({
			fit:true,
		    title: '开证费登记',
		    href:'${ctx}/credit/payReg/payRegFee/' + row.id,
		    modal:true,
		    closable:false,
		    style:{borderWidth:0},
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
}

//弹窗查看
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		fit:true,
	    title: '查看明细',
	    href:'${ctx}/credit/payReg/payRegFeeDetail/' + row.id,
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


</script>
</body>
</html>