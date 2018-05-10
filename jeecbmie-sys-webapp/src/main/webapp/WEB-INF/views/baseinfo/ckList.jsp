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
       	        <input type="text" name="filter_LIKES_no" class="easyui-validatebox" data-options="width:150,prompt: '单位编码'"/>
       	        <input type="text" name="filter_LIKES_companyName" class="easyui-validatebox" data-options="width:150,prompt: '单位名称'"/>
       	        <input type="text" name="filter_LIKES_area" class="easyui-validatebox" data-options="width:150,prompt: '地区'"/>
       	        <input type="text" id = "search_property" name="filter_LIKES_property" class="easyui-combobox" data-options="width:150,prompt:'企业性质',
       	        valueField: 'label',textField: 'value',panelHeight:'auto',
				data: [{label: '国营',value: '国营'},
				{label: '集体',value: '集体'},
				{label: '私营',value: '私营'},
				{label: '外资',value: '外资'}]"/>
				<input type="text" name="filter_LIKES_belongCompany" class="easyui-validatebox" data-options="width:150,prompt: '所属仓储公司'"/>
				<input type="text" id = "search_cklb" name="filter_LIKES_cklb" class="easyui-combobox" data-options="width:150,prompt:'仓库类别',
       	        valueField: 'label',textField: 'value',panelHeight:'auto',
				data: [{label: '常规库',value: '常规库'},
				{label: '监管库',value: '监管库'},
				{label: '虚拟库',value: '虚拟库'}]"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="sys:woodck:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodck:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodck:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodck:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg"></div>  
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/baseInfo/ck/json', 
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
			{field:'no',title:'单位编号',sortable:true,width:20},  
			{field:'companyName',title:'单位名称',sortable:true,width:20},
			{field:'shortName',title:'单位简称',sortable:true,width:15},
			{field:'area',title:'所在地区',sortable:true,width:20},
			{field:'belongCompany',title:'所属仓储公司',sortable:true,width:15,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/baseinfo/affiUtil/getBaseInfoName/"+value,
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
				}},
			{field:'cklb',title:'仓库类别',sortable:true,width:15},
			{field:'balanceType',title:'仓储费结算方法',sortable:true,width:15},
// 			{field:'type',title:'单位类型',sortable:true,width:15},
			{field:'addr',title:'地址',sortable:true,width:15}
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
	    title: '新增仓库',    
	    fit : true,   
	    href:'${ctx}/baseInfo/ck/create',
	    maximizable:false,
	    modal:true,
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
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/baseInfo/ck/delete/"+row.id,
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
	d=$("#dlg").dialog({   
	    title: '修改仓库',    
	    fit : true, 
	    href:'${ctx}/baseInfo/ck/update/'+row.id,
	    maximizable:false,
	    modal:true,
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
	    title: '仓库明细',    
	    fit : true,   
	    href:'${ctx}/baseInfo/ck/detail/'+row.id,
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
	$("#search_property").combobox('clear');
	$("#search_cklb").combobox('clear');
	$("#searchFrom")[0].reset();
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

</script>
</body>
</html>