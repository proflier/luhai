<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body class="easyui-layout">
    <div data-options="region:'center',split:true,border:false,title:'角色列表和对应用户'">
    		<div id="tb_role" style="padding:5px;height:auto">
			    <div>
				    <shiro:hasPermission name="sys:role:add">
				    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
				    	<span class="toolbar-item dialog-tool-separator"></span>
				   	</shiro:hasPermission>
				   	<shiro:hasPermission name="sys:role:delete">
				        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"  onclick="del()">删除</a>
				        <span class="toolbar-item dialog-tool-separator"></span>
				    </shiro:hasPermission>
				    <shiro:hasPermission name="sys:role:update">
				        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
				    </shiro:hasPermission>
			    </div>
			</div>
			<table id="dg_role" style="height:350px"></table>
			<div class="datagrid-toolbar"></div> 
			<div id="tb_user" style="padding:5px;height:auto">
			    <div>
				    <shiro:hasPermission name="sys:role:update">
				    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser();">新增用户</a>
				    	<span class="toolbar-item dialog-tool-separator"></span>
				   	</shiro:hasPermission>
				   	<shiro:hasPermission name="sys:role:update">
				        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"  onclick="delUser()">删除用户</a>
				        <span class="toolbar-item dialog-tool-separator"></span>
				    </shiro:hasPermission>
				    <a id="relation_org" href="#" class="easyui-linkbutton" iconCls="icon-cologne-home" plain="true" onclick="relationForOrg()">关联机构</a>
			    </div>
			</div>
			<table id="dg_user" style="height:250px"></table>
    </div>   
    <div data-options="region:'east',split:true,border:false,title:'权限列表'" style="width: 425px">
    	
    	<div id="tg_tb" style="padding:5px;height:auto">
		    <div>
			    <shiro:hasPermission name="sys:role:permUpd">
			    	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save();">保存授权</a>
			    	<span class="toolbar-item dialog-tool-separator"></span>
			    	 <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="back()">恢复</a>
			    </shiro:hasPermission>
		    </div>
		</div>
		
    	<table id="permissionDg"></table>
    </div>
<div id="dlg"></div>  
<script type="text/javascript">
var dg_role;	//角色datagrid
var dg_user;//用户datagrid
var d; //弹窗
var permissionDg;	//权限datagrid
var rolePerData;	//用户拥有的权限
var roleId;	//双击选中的role
$(function(){   
	dg_role=$('#dg_role').datagrid({    
		method: "get",
	    url:'${ctx}/system/role/json', 
// 	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		striped:true,
	    columns:[[    
	        {field:'id',title:'id',hidden:true,sortable:true,width:100},    
	        {field:'name',title:'角色名称',sortable:true,width:100},
	        {field:'roleCode',title:'角色编码',sortable:true,width:100},
	        {field:'description',title:'描述',sortable:true,width:100,tooltip: true},
// 	        {field : 'action',title : '操作',
// 				formatter : function(value, row, index) {
// 					return '<a href="javascript:lookP('+row.id+')"><div class="icon-hamburg-lock" style="width:16px;height:16px" title="查看权限"></div></a>';
// 				}
// 	        }
	    ]],
	    sortName:'sort',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
    	onClickRow:function(rowIndex,rowData){
	    	load_dg_user(rowData.id);
	    	lookP(rowData.id);
	    },
	    toolbar:'#tb_role'
	});
	
	
	
	permissionDg=$('#permissionDg').treegrid({   
		method: "get",
	    url:'${ctx}/system/permission/all/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		treeField:'name',
		parentField : 'pid',
		iconCls: 'icon',
		animate:true, 
		rownumbers:true,
		striped:true,
		singleSelect:false,//需设置  
	    columns:[[    
			{field:'ck',checkbox:true,width:100},   
	        {field:'id',title:'id',hidden:true,width:100},    
	        {field:'name',title:'名称',width:100},
	        {field:'description',title:'描述',width:100,tooltip: true}
	    ]],
	    onClickRow:function(row){  
            //级联选择  
            $(this).treegrid('cascadeCheck',{  
                id:row.id, //节点ID  
                deepCascade:true //深度级联  
            });  
        },
        toolbar:'#tg_tb'
	});
	
	var dg_user = $('#dg_user').datagrid({
		border : false
	});
		
});

function load_dg_user(id){
	 dg_user = $('#dg_user').datagrid({
		method: "get",
	    url:'${ctx}/system/userRole/relations/'+id, 
// 	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		striped:true,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20 ],
		singleSelect:true,
	    columns:[[
	        {field:'id',title:'id',hidden:true},    
	        {field:'loginName',title:'帐号',width:100,
	        	formatter : function(value, row, index) {
	       			return row.user.loginName;
	        	}},    
	        {field:'name',title:'昵称',width:100,
	        	formatter : function(value, row, index) {
	       			return row.user.name;
	        	}},
	        {field:'organization',title:'部门',width:100,
	        	formatter: function(value,row,index){
					var val;
					if(row.user.id!=''&&row.user.id!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/user/getDeptName/"+row.user.id,
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
	        {field:'relationDepts',title:'关联部门',width:100,
	        	formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/organization/orgNameByIds/"+value,
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
	        {field:'gender',title:'性别',
	        	formatter : function(value, row, index) {
	       			return row.user.gender==1?'男':'女';
	        	}
	        },
	        {field:'email',title:'email',width:100,
	        	formatter : function(value, row, index) {
	       			return row.user.email;
	        	}},
	        {field:'phone',title:'电话',width:100,
	        	formatter : function(value, row, index) {
	       			return row.user.phone;
	        	}}
	    ]],
	    headerContextMenu: [
	        {
	            text: "冻结该列", disabled: function (e, field) { return dg_role.datagrid("getColumnFields", true).contains(field); },
	            handler: function (e, field) { dg_role.datagrid("freezeColumn", field); }
	        },
	        {
	            text: "关闭冻结该列", disabled: function (e, field) { return dg_role.datagrid("getColumnFields", false).contains(field); },
	            handler: function (e, field) { dg_role.datagrid("unfreezeColumn", field); }
	        }
	    ],
	    enableHeaderClickMenu: true,
	    enableHeaderContextMenu: true,
	    enableRowContextMenu: false,
	    toolbar:'#tb_user'
	});
}

//查看权限
function lookP(roleId){
	//清空勾选的权限
	if(rolePerData){
		permissionDg.treegrid('unselectAll');
		rolePerData=[];//清空
	}
	//获取角色拥有权限
	$.ajax({
		async:false,
		type:'get',
		url:"${ctx}/system/role/"+roleId+"/json",
		success: function(data){
			if(typeof data=='object'){
				rolePerData=data;
				for(var i=0,j=data.length;i<j;i++){
					permissionDg.treegrid('select',data[i]);
				}
			}else{
				$.easyui.messager.alert(data);
			} 
		},
		headers: {
			"Accept": "application/json" //配置返回数据类型application/xml
		}
	});
}

//保存修改权限
function save(){
	var row = dg_role.datagrid('getSelected');
	var roleId=row.id;
	parent.$.messager.confirm('提示', '保存要保存修改？', function(data){
		if(data){
			var newPermissionList=[];
			var data=permissionDg.treegrid('getSelections');
			for(var i=0,j=data.length;i<j;i++){
				newPermissionList.push(data[i].id);
			}
			
			if(roleId==null) {
				parent.$.messager.show({ title : "提示",msg: "请选择角色！", position: "bottomRight" });
				return;
			}
			$.ajax({
				async:false,
				type:'POST',
				data:JSON.stringify(newPermissionList),
				contentType:'application/json;charset=utf-8',
				url:"${ctx}/system/role/"+roleId+"/updatePermission",
				success: function(data){
					successTip(data);
				}
			});
		}
	});
}

//弹窗增加
function add() {
	$.ajaxSetup({type : 'GET'});
	d=$('#dlg').dialog({    
	    title: '新增角色',    
	    width: 400,    
	    height: 270,    
	    closed: false,    
	    cache: false,
	    maximizable:false,
	    resizable:true,
	    href: '${ctx}/system/role/create',
	    modal: true,
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
	var row = dg_role.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/system/role/delete/"+row.id,
				success: function(data){
					successTip(data,dg_role);
				}
			});
			//dg_role.datagrid('reload'); //grid移除一行,不需要再刷新
		} 
	});
}

//修改
function upd(){
	var row = dg_role.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var rowIndex = row.id;
	$.ajaxSetup({type : 'GET'});
	d=$("#dlg").dialog({   
	    title: '修改角色',    
	    width: 400,    
	    height: 270,      
	    href: '${ctx}/system/role/update/'+rowIndex,
	    maximizable:false,
	    modal:true,
	    buttons:[{
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

//弹窗增加用户
function addUser() {
	var row = dg_role.datagrid('getSelected');
	if(rowIsNull(row)) {
		parent.$.messager.show({ title : "提示",msg: "请选择角色！", position: "bottomRight" });
		return;
	}
	$.ajaxSetup({type : 'GET'});
	d=$('#dlg').dialog({    
	    title: '新增角色',    
	    width: 400,    
	    height: 500,    
	    closed: false,    
	    cache: false,
	    maximizable:false,
	    resizable:true,
	    href: '${ctx}/system/user/createUserRole',
	    modal: true,
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				saveUserInRole(row.id);
// 				d.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//删除用户
function delUser(){
	var rowRole = dg_role.datagrid('getSelected');
	if(rowIsNull(rowRole)) {
		parent.$.messager.show({ title : "提示",msg: "请选择角色！", position: "bottomRight" });
		return;
	}
	var row = dg_user.datagrid('getSelected');
	if(rowIsNull(row)) {
		parent.$.messager.show({ title : "提示",msg: "请选择用户！", position: "bottomRight" });
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'post',
				url:"${ctx}/system/user/deleteFromRole/"+rowRole.id+"/"+row.user.id,
				success: function(data){
					dg_user.datagrid('reload');
				}
			});
		} 
	});
}
//恢复权限选择
function back(){
	var row = dg_role.datagrid('getSelected');
	lookP(row.id);
}

//用户关联部门弹窗
function relationForOrg(){
	var row = dg_user.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	var dlg=$("#dlg").dialog({
	    title: '用户机构管理',    
	    width: 400,    
	    height: 350,
	    href:'${ctx}/system/userRole/selectOrgs/'+row.id+'/'+row.relationDepts,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				saveRelationOrg();
				dlg.panel('close');
				dg_user.datagrid('reload');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg.panel('close');
			}
		}]
	});
}
</script>
</body>
</html>