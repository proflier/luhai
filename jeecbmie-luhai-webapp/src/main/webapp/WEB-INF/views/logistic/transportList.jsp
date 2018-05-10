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
       	        <input type="text" id="transporteror" name="filter_LIKES_transporter" class="easyui-combobox" data-options="width:150,prompt: '运输商'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="logistic:transport:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="logistic:transport:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="logistic:transport:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="logistic:transport:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
	         <shiro:hasPermission name="logistic:transport:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="logistic:transport:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="logistic:transport:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="logistic:transport:businessPermission">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="setBusenessPermission();">相关人员配置</a>
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
<div id="dlg_sub"></div>  
<div id="dlg_selectUsers"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/logistic/transport/json', 
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
				{field:'transporter',title:'运输商',width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
				{field:'office',title:'办事处',sortable:true,width:20}, 
				{field:'transportType',title:'运输方式',width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/dictUtil/getDictNameByCode/YSFS/"+value,
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
				{field:'settleBeginDate',title:'结算开始日期',sortable:true,width:20,
					formatter:function(value,row,index){  
						if(value==null){
							return null;
						}
		                    var unixTimestamp = new Date(value);  
		                    return unixTimestamp.format("yyyy-MM-dd");  
	                    } 
				},
				{field:'settleEndDate',title:'结算结束日期',sortable:true,width:20,
					formatter:function(value,row,index){  
						if(value==null){
							return null;
						}
		                    var unixTimestamp = new Date(value);  
		                    return unixTimestamp.format("yyyy-MM-dd");  
	                    } 
				},
				{field:'state',title:'状态',width:10,formatter:function(value,row,index){  
                    return business_page.stateShow(value);
                }} 
				
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    toolbar:'#tb',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	//运营商查询
	$('#transporteror').combobox({
		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230009',
		valueField : 'customerCode',
		textField : 'customerName'
	});

});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增',    
	    fit:true,    
	    href:'${ctx}/logistic/transport/create',
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
		},
	      {
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
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/transport/delete/"+row.id,
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改',    
	    fit:true,    
	    href:'${ctx}/logistic/transport/update/'+row.id,
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
	    title: '明细',    
	    fit:true,  
	    href:'${ctx}/logistic/transport/detail/'+row.id,
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
	business_page.procStartUrl="${ctx}/logistic/transport/apply";
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
				url:"${ctx}/logistic/transport/callBack/" + row.id + "/" + row.processInstanceId,
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
function setBusenessPermission(){
	var row = dg.datagrid('getSelected');
	var themeMemberIds = "";
	var themeMemberKeyIds = "";
	var themeMembers = "";
	var themeMemberKeys = "";
	if(row.relLoginNames){
		themeMemberIds = row.relLoginNames;
	}
	if(row.relLoginNames){
		$.ajax({
			async : false,
			url : '${ctx}/permission/businessPerssion/getUserNamesString/'+themeMemberIds,
			type : "get",
			dataType:"json",  //数据类型是JSON
			success : function(data) {
				themeMembers=data.themeMembers;
				themeMemberIds = data.themeMemberIds;
				}
			});
	}
	//todo
	var selectUsers_dg=$("#dlg_selectUsers").dialog({   
	    title: '人员选择',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/permission/businessPerssion/businessSelectUsers/',
	    modal:true,
	    queryParams:{'themeMemberIds':themeMemberIds,'themeMemberKeyIds':themeMemberKeyIds,'themeMembers':themeMembers,'themeMemberKeys':themeMemberKeys},
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				themeMemberIds = users_show.saveSelect();
				//更新相关人员
				$.ajax({
					async : false,
					url : '${ctx}/permission/businessPerssion/updatePermission4HightOrRail/'+row.id,
					type : "get",
					data:{
						"themeMemberIds":	themeMemberIds,
					},
					success : function(data) {}
					});
				selectUsers_dg.panel('close');
				dg.datagrid('reload');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				selectUsers_dg.panel('close');
			}
		}]
	});
}
//流程跟踪
function trace() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未发起，不存在流程跟踪！','info');
		return;
	}
	business_page.traceProc(row.processInstanceId);
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/logistic/transport/apply";
	business_page.saveAndApply(dg,d,"mainform");
}
</script>
</body>
</html>

