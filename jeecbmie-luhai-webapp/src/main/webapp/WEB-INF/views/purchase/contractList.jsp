<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 -->
<!DOCTYPE html>
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
        		<input type="text" name="filter_LIKES_innerContractNo_OR_purchaseContractNo_OR_agreementNo_OR_businessManager" class="easyui-validatebox" data-options="width:300,prompt: '请输入内部合同号或采购合同号'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
<!-- 		        <a   href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="superSearch()">高级搜索</a> -->
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="sys:purchaseContract:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:delete">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
			    <span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:update">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
			    <span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:change">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-cv" plain="true" onclick="change();">合同变更</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:view">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
			    <span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:changeState">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-billing" plain="true" onclick="closeOrOpen();">合同状态修改</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:purchaseContract:businessPermission">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="setBusenessPermission();">相关人员配置</a>
			</shiro:hasPermission>
	        <shiro:hasPermission name="sys:purchaseContract:view">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true" onclick="exportPDF()">导出pdf</a>
	        </shiro:hasPermission>
        </div> 
	</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.closeOrOpen =='关闭'){
						return 'color:#0072E3;font-style:italic;';
					}
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table> <!-- 首页到单列表  -->
<div id="dlg"></div>  <!-- 到单页面弹窗  -->
<div id="dlgProtocol"></div><!-- 采购协议列表弹窗  -->
<div id="dlg_search"></div> <!-- 搜索弹窗  -->
<div id="dlg_selectUsers"></div> 
<div id="dlg_goods"></div> <!-- 采购子表弹窗  -->
<div id="dlg_saleContract"></div> <!-- 销售弹窗  -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/purchase/purchaseContract/json?filter_NES_changeState=0', 
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
			{field:'innerContractNo',title:'内部合同号',sortable:true,width:25},
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
// 			{field:'agreementNo',title:'协议号',sortable:true,width:25},
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:25},
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
			{field:'createrDept',title:'申请部门',sortable:true,width:20},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:15},
			{field:'createDate',title:'创建时间',sortable:true,width:15},
			{field:'state',title:'流程状态',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='0'){
							val='作废';
						}else if(value=='1'){
							val='生效';
						}else if(value=='2'){
							val='草稿';
						}else if (value =='3'){
							val='已提交';
						}
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'closeOrOpen',title:'运行状态',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='0'){
							val='关闭';
						}else {
							val='运行中';
						}
						return val;
					}else{
						return '';
					}
				}	
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
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加采购合同',    
	    href:'${ctx}/purchase/purchaseContract/create',
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
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
				url:"${ctx}/purchase/purchaseContract/delete/"+row.id,
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
	if(row.closeOrOpen == "0" ){
		$.messager.alert('提示','表单处于关闭状态，不能修改！','info');
		return;
	}
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	
	d=$("#dlg").dialog({   
	    title: '修改采购合同',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/purchase/purchaseContract/update/'+row.id,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("update");
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
	    title: '采购合同明细',    
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/purchase/purchaseContract/detail/'+row.id,
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
	business_page.procStartUrl="${ctx}/purchase/purchaseContract/apply";
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
				url:"${ctx}/purchase/purchaseContract/callBack/"+row.id+"/"+row.processInstanceId,
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
	business_page.traceProc(row.processInstanceId);;
}

//合同变更
function change(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.closeOrOpen == "0" ){
		$.messager.alert('提示','表单处于关闭状态，不能修改！','info');
		return;
	}
	if(row.state != "1"){
		$.messager.alert('提示','合同当前状态，不能进行变更！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '变更合同',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/purchase/purchaseContract/change/'+row.id,
	    modal:true,
	    buttons:[
            {
			text:'提交申请', iconCls:'icon-hamburg-up',
				handler:function(){
					parent.$.messager.confirm('提示', '变更无法撤销，确定变更并提交申请？', function(data){
						if (data){
							applyAnd("change");
						} 
					});
				}
			}, {
			text:'变更',iconCls:'icon-edit',
				handler:function(){
					parent.$.messager.confirm('提示', '变更无法撤销，确定变更？', function(data){
						if (data){
							$('#mainform').submit(); 
						} 
					});
					
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

//弹窗修改状态
function closeOrOpen(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "1"){
		$.messager.alert('提示','合同当前状态，不能进行修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '合同状态修改',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/purchase/purchaseContract/closeOrOpen/'+row.id,
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


//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/purchase/purchaseContract/apply";
	business_page.saveAndApply(dg,d,"mainform");
}



function superSearch(){
	dlg_search=$("#dlg_search").dialog({   
	    title: '高级搜索',    
	    width: 600,    
	    height: 350,
	    href:'${ctx}/purchase/purchaseContract/superSearch/',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				var obj=$("#superSearch").serializeObject();
				dg.datagrid('reload',obj); 
				dlg_search.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_search.panel('close');
			}
		}]
	});
}

// function superSearch(){
// 	if($("#downbills_superSearch").css("display")=='none'){//如果show是隐藏的
// 		$("#downbills_superSearch").css("display","block");//show的display属性设置为block（显示）
// 	}else{//如果show是显示的
// 		$("#downbills_superSearch").css("display","none");//show的display属性设置为none（隐藏）
// 		}
// // 	$('#downbills_superSearch').show();
// }

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
					url : '${ctx}/permission/businessPerssion/updatePermission4Purchase/'+row.id,
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

//导出pdf
function exportPDF(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var url = "${ctx}/purchase/purchaseContract/exportPDF/" + row.innerContractNo;
	window.location.href = url;
}

</script>
</body>
</html>