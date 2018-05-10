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
		        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '昵称'"/>
         		<span class="toolbar-item dialog-tool-separator"></span>	
		        <input type="text" name="filter_LIKES_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
	        	<span class="toolbar-item dialog-tool-separator"></span>
		        <input name="filter_EQI_loginStatus" class="easyui-combobox" 
		        	data-options="valueField: 'label',textField: 'value',panelHeight:'auto', data: [{label: '1',value: '启用'}, {label: '0',value: '停用'}],prompt: '登陆状态'" />
		       	<input type="text" id="filter_EQI_orgId" name="filter_EQI_orgId" class="easyui-combotree" />
<!-- 		        <input type="text" name="filter_GTD_createDate" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="width:150,prompt: '开始日期'"/> -->
<!-- 		        - <input type="text" name="filter_LTD_createDate" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="width:150,prompt: '结束日期'"/> -->
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="user_select" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		        <a id="user_reset" href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true">重置</a>
			</form>
			
		   	<shiro:hasPermission name="sys:user:add">
		   		<a id="user_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sys:user:delete">
		        <a id="user_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false">删除</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sys:user:update">
		        <a id="user_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <a id="user_init" href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true">初始化密码</a>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <shiro:hasPermission name="sys:user:roleView">
				<a id="user_role" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true">用户角色</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:orgView">
				<a id="user_org" href="#" class="easyui-linkbutton" iconCls="icon-cologne-home" plain="true">用户所属机构</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:relation">
				<a id="user_relation" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关关联单位</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			
			<a id="user_purchase" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关采购</a>
			<span class="toolbar-item dialog-tool-separator"></span>
			
			<a id="user_sale" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关销售</a>
			<span class="toolbar-item dialog-tool-separator"></span>
			
			<a id="user_orderShip" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关订船</a>
			<span class="toolbar-item dialog-tool-separator"></span>
			
			<a id="user_insurance" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关保险</a>
			<span class="toolbar-item dialog-tool-separator"></span>
			
			<a id="user_highway" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关汽运</a>
			<span class="toolbar-item dialog-tool-separator"></span>
			
			<a id="user_railway" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关铁运</a>
			<span class="toolbar-item dialog-tool-separator"></span>
			
			<a id="user_port" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-my-account" plain="true">用户相关码头</a>
			<span class="toolbar-item dialog-tool-separator"></span>
				
		</div>
	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
<script type="text/javascript">

/**
var sys = window.sys || {};
sys.UserTest = new function() {
  var self_ = this;
  var name = 'world';
  self_.sayHello = function(_name) {
    return 'Hello ' + (_name || name);
  };
  
  var test = function(){alert("test");}
  var abc = function(){
	  //alert(3333);
	  //alert(self_.sayHello("qwe"));
	  $("#user_test").click(test);
  }
  //alert(self_.sayHello("qwe"));
  abc();
};
**/
$('#filter_EQI_orgId').combotree({
	method:'GET',
  	url: '${ctx}/system/organization/list',
    idField : 'id',
    textFiled : 'orgName',
	parentField : 'pid',
//	    panelHeight:'auto',
    animate:true,
    prompt: '公司/部门',
    onHidePanel:function(){
    }
});

var sys = window.sys || {};
sys.UserManager = new function() {
	var self_ = this;
	var dlg;
	//用户列表
	var dg = $('#dg').datagrid({
		method: "get",
	    url:'${ctx}/system/user/jsonNew', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		striped:true,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[
	        {field:'id',title:'id',hidden:true},    
	        {field:'loginName',title:'帐号',sortable:true,width:15},    
	        {field:'name',title:'昵称',sortable:true,width:10},
	        {field:'img',title:'公司',sortable:true,width:15,
	        	formatter: function(value,row,index){
					var val;
					if(row.id!=''&&row.id!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/user/getCompName/"+row.id,
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
	        {field:'organization',title:'部门',sortable:true,width:15,
	        	formatter: function(value,row,index){
					var val;
					if(row.id!=''&&row.id!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/user/getDeptName/"+row.id,
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
	        {field:'gender',title:'性别',sortable:true,width:5,
	        	formatter : function(value, row, index) {
	       			return value==1?'男':'女';
	        	}
	        },
	        {field:'phonePermission',title:'手机可登陆',sortable:true,width:5,
	        	formatter : function(value, row, index) {
	       			return value==1?'是':'否';
	        	}
	        },
	        {field:'loginStatus',title:'账号状态',sortable:true,width:5,
	        	formatter : function(value, row, index) {
	       			return value==1?'启用':'停用';
	        	}
	        },
	        {field:'email',title:'email',sortable:true,width:10},
	        {field:'phone',title:'电话',sortable:true,width:10},
	        {field:'loginCount',title:'登录次数',sortable:true,width:10},
	        {field:'lastVisit',title:'最后一次登录',sortable:true,width:10,
	        	formatter : function(value, row, index) {
	        		var res = "";
	        		if(value!=null){
	        			var d = new Date()
	        			d.setTime(value);
	        			res = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	        		}
	       			return res;
	        	}}
	    ]],
	    headerContextMenu: [
	        {
	            text: "冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", true).contains(field); },
	            handler: function (e, field) { dg.datagrid("freezeColumn", field); }
	        },
	        {
	            text: "关闭冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", false).contains(field); },
	            handler: function (e, field) { dg.datagrid("unfreezeColumn", field); }
	        }
	    ],
	    enableHeaderClickMenu: true,
	    enableHeaderContextMenu: true,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});

	//弹窗增加
	self_.add = function() {
		dlg=$("#dlg").dialog({   
		    title: '新增用户',    
		    width: 420,    
		    height: 410,    
		    href:'${ctx}/system/user/create',
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
					dlg.panel('close');
				}
			}]
		});
	}

	//弹窗修改
	self_.upd = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		dlg=$("#dlg").dialog({   
		    title: '修改用户',    
		    width: 400,    
		    height: 380,   
		    href:'${ctx}/system/user/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'修改',iconCls:'icon-edit',
				handler:function(){
					$('#mainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}

	//查看
	self_.detail = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		dlg=$("#dlg").dialog({
		    title: '查看用户',    
		    width: 380,    
		    height: 380,    
		    href:'${ctx}/system/user/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//删除
	self_.del = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:"${ctx}/system/user/delete/"+row.id,
					success: function(data){
						successTip(data,dg);
					}
				});
			} 
		});
	}
	
	//用户角色弹窗
	self_.userForRole = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({   
		    title: '用户角色管理',    
		    width: 580,    
		    height: 350,  
		    href:'${ctx}/system/user/'+row.id+'/userRole',
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveUserRole();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	//用户机构弹窗
	self_.userForOrg = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户机构管理',    
		    width: 400,    
		    height: 350,
		    href:'${ctx}/system/user/'+row.id+'/userOrg',
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveUserOrg();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//用户关联
	self_.userRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联单位',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Affi/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//采购关联
	self_.purchaseRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联采购合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Purchase/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//销售关联
	self_.saleRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联销售合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Sale/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//订船合同关联
	self_.orderShipRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联订船合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4OrderShip/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//保险合同关联
	self_.insuranceRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联保险合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Insurance/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	//汽运合同关联
	self_.highwayRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联汽运合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Highway/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//铁运合同关联
	self_.railwayRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联铁运合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Railway/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	
	//码头合同关联
	self_.portRelation = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户关联码头合同',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/system/user/toUserRelation4Port/'+row.loginName,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveRealtion();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//初始化用户密码
	self_.initPwd = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.messager.confirm('提示', '确定初始化该用户密码吗?', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:"${ctx}/system/user/initPwd/"+row.id,
					success: function(data){
						successTip(data,dg);
					}
				});
			} 
		});
	}

	//创建查询对象并查询
	self_.cx = function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('load',obj);
	}
	
	//重置
	self_.reset = function(){
		$("#searchFrom")[0].reset();
		$(".easyui-combobox").combobox('clear');
		$(".easyui-combotree").combotree('clear');
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('load',obj);
	}
	
	function init(){
		//绑定表单按钮事件-----------------
		$("#user_select").click(self_.cx);
		$("#user_reset").click(self_.reset);
		$("#user_add").click(self_.add);
		$("#user_del").click(self_.del);
		$("#user_upd").click(self_.upd);
		$("#user_init").click(self_.initPwd);
		$("#user_role").click(self_.userForRole);
		$("#user_org").click(self_.userForOrg);
		$("#user_relation").click(self_.userRelation);
		$("#user_purchase").click(self_.purchaseRelation);
		$("#user_sale").click(self_.saleRelation);
		$("#user_orderShip").click(self_.orderShipRelation);
		$("#user_insurance").click(self_.insuranceRelation);
		$("#user_highway").click(self_.highwayRelation);
		$("#user_railway").click(self_.railwayRelation);
		$("#user_port").click(self_.portRelation);
		//---------------------------------
	}
	
	init();
};


(function($) {})(jQuery);


</script>
</body>
</html>