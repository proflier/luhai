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
	    <input type="text" name="filter_LIKES_woodBillId" class="easyui-validatebox" data-options="prompt: '到单号'"/>
	    <input type="text" name="filter_LIKES_creditNo" class="easyui-validatebox" data-options="prompt: '信用证号'"/>
	    <input type="text" name="filter_LIKES_inteContractNo" class="easyui-validatebox" data-options="prompt: '财务编号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:mortgageReg:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:mortgageReg:delete">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:mortgageReg:update">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:mortgageReg:view">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dg" ></table>
<div id="dlg"></div>
<div id="dlg_loadbillid_mortgage"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/credit/mortgageReg/json',
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
			{field:'woodBillId',title:'到单号',sortable:true,width:20},
			{field:'creditNo',title:'信用证号',sortable:true,width:20},
		    {field:'inteContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'bankName',title:'银行名称',sortable:true,width:20,
		    	formatter: function(value,row,index){
					var val;
					if(row.bankName!=''&&row.bankName!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getBaseInfoName/'+row.bankName ,
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
			{field:'mortgageMoney',title:'押汇金额',sortable:true,width:20},
		    {field:'createDate',title:'创建时间',sortable:true,width:25},
		    {field:'mortgageTime',title:'押汇日期',sortable:true,width:20}
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
	    title: '添加押汇登记',
	    href:'${ctx}/credit/mortgageReg/create',
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
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
	d=$("#dlg").dialog({
		fit:true,
	    title: '修改押汇登记',
	    href:'${ctx}/credit/mortgageReg/update/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
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

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if(data){
			$.ajax({
				type:'get',
				url:"${ctx}/credit/mortgageReg/delete/" + row.id,
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
	    href:'${ctx}/credit/mortgageReg/detail/' + row.id,
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