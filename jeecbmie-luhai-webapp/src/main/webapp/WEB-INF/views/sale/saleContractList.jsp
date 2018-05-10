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
<link  href="${ctx}/static/cbp-folder/css/component.css" rel="stylesheet" >
<script src="${ctx}/static/cbp-folder/js/jquery.cbpNTAccordion.js"></script>
<script src="${ctx}/static/cbp-folder/js/modernizr.custom.js"></script>

</head>
<body>
	<div id="tb" style="padding:5px;height:auto">
	    <div>
	    	<form id="searchFrom" action="">
		        <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '合同号'"/>
		        <input type="text" id="purchaser_cx" name="filter_LIKES_purchaser" class="easyui-validatebox" data-options="width:180,prompt: '买方'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sf_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sf_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="sale:saleContract:add">
		   		<a id="scl_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sale:saleContract:delete">
		        <a id="scl_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleContract:update">
		        <a id="scl_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleContract:change">
		     <a id="scl_change" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">变更</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleContract:view">
		        <a id="scl_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		    </shiro:hasPermission>
		      <shiro:hasPermission name="sale:saleContract:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleContract:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleContract:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleContract:close">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeContract()">关闭</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission> 
		    <shiro:hasPermission name="sale:saleContract:start">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="startContract()">开启</a>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleContract:businessPermission">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="setBusenessPermission();">相关人员配置</a>
			</shiro:hasPermission>
	        <shiro:hasPermission name="sale:saleContract:view">
	            <a id="export_PDF" href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true">导出pdf</a>
	        </shiro:hasPermission>
	        
	        <a id="sale_relation" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">销售相关单据</a>
	    </div>
	</div>
	<table id="dg" data-options="
					rowStyler: function(index,row){
						if (row.closedFlag == '1'){
							return 'color:#0072E3;font-style:italic;';
						}
						if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
						}
					}
				"></table>  <!-- 首页到单列表  -->
	<div id="dlg"></div>  <!-- 到单页面弹窗  -->
	<div id="dlgPurchase"></div> <!-- 采购合同列表弹窗  -->
	<div id="dlg_goods"></div>
	<div id="dlg_bak"></div>
	<div id="dlg_selectUsers"></div> 
<script type="text/javascript">
$("#purchaser_cx").combobox({
	required : false,
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=10230003',
	valueField : 'customerCode',
	textField : 'customerName',
	method:'get'
});
function changeSaleContract(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != '1'){
		$.messager.alert('提示','合同流程未结束，不能变更！','info');
		return;
	}
	if(row.closedFlag == '1'){
		$.messager.alert('提示','合同已经关闭，不能变更！','info');
		return;
	}
	parent.$.messager.confirm('提示', '是否确认变更？', function(data){
		if (data){
// 			$('#scl_change').linkbutton('disable');
// 			$("#scl_change").unbind("click");
			d=$("#dlg").dialog({
				type:'get',
			    title: '变更合同',    
			    fit:true,  
			    href:'${ctx}/sale/saleContract/change/'+row.id,
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
						dg.datagrid('reload');
						d.panel('close');
// 						$('#scl_change').linkbutton('enable');
// 						$("#scl_change").bind("click", changeSaleContract);
					}
				}]
			});
		} 
	});
};
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/sale/saleContract/json?filter_NES_changeState=0', 
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
			{field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'sellerView',title:'卖方',sortable:true,width:20},
			{field:'purchaserView',title:'买方',sortable:true,width:20},
			{field:'ship',title:'运输工具编号',sortable:false,width:15,
				formatter: function(value,row,index){
					var shipName = '';
					if(row.id!=null && row.id!=""){
						$.ajax({
							url : '${ctx}/sale/saleContract/getShipNameById/'+row.id ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								shipName = data;
							}
						});
					}
					if(shipName!=''){
						if(shipName.length>10){
//								var returnValue = shipName.substring(0,10)+"...";
		                	return "<span title='" + shipName + "'>" + shipName + "</span>";
						}else{
							return shipName;
						}
						
					}else{
						return "";
					}
				}	
			},
			{field:'signDate',title:'签约日期',sortable:true,width:20},
           	{field:'state',title:'流程状态',sortable:true,width:10,formatter:function(value,row,index){  
                   return business_page.stateShow(value);
               }},
           	{field:'closedFlag',title:'是否关闭',width:10,
           		formatter:function(value,row,index){
   					if(value == '1'){
   						return '是';
   					}else{
   						return '否';
   					}
               	}},
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
   			}
	    ]],
	    sortName:'contractNo',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	//查询
	$("#sf_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//重置
	$("#sf_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		$("#purchaser_cx").combobox('clear');
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//增加
	$("#scl_add").on("click", function(){
		d=$("#dlg").dialog({   
		    title: '新增合同',    
		    fit:true,    
		    href:'${ctx}/sale/saleContract/create',
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
			}
		    ,{
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
	});
	//删除
	$("#scl_del").on("click", function(){
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
					url:"${ctx}/sale/saleContract/delete/"+row.id,
					success: function(data){
						successTipNew(data,dg);
					}
				});
			} 
		});
	});
	//修改
	$("#scl_upd").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		if(row.processInstanceId != null){
			$.messager.alert('提示','表单已提交申请，不能修改！','info');
			return;
		}
		d=$("#dlg").dialog({   
		    title: '修改合同',    
		    fit:true,  
		    href:'${ctx}/sale/saleContract/update/'+row.id,
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
	});
	//变更
	$("#scl_change").bind("click", changeSaleContract);
	
	//查看明细
	$("#scl_detail").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		d=$("#dlg").dialog({   
		    title: '合同明细',    
		    fit : true,    
		    href:'${ctx}/sale/saleContract/detail/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	});
	
	//导出pdf
	$("#export_PDF").on("click", function() {
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		var url = "${ctx}/sale/saleContract/exportPDF/" + row.id;
		window.location.href = url;
	});
});
//申请
function apply(){
	business_page.procStartUrl="${ctx}/sale/saleContract/apply";
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
				url:"${ctx}/sale/saleContract/callBack/" + row.id + "/" + row.processInstanceId,
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
		$.messager.alert('提示','表单未发起，不存在流程跟踪！','info');
		return;
	}
	business_page.traceProc(row.processInstanceId);
}
//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/sale/saleContract/apply";
	business_page.saveAndApply(dg,d,"mainform",afterMainSubmitSuccess);
}
function closeContract(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "1"){
		$.messager.alert('提示','合同未生效，不能关闭！','info');
		return;
	}
	if(row.closedFlag == "1"){
		$.messager.alert('提示','合同已关闭，不能重复关闭！','info');
		return;
	}
	parent.$.messager.confirm('提示', '确认关闭合同？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/sale/saleContract/closeContract/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

function startContract(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.closedFlag != "1"){
		$.messager.alert('提示','合同处于启用状态','info');
		return;
	}
	parent.$.messager.confirm('提示', '启用合同？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/sale/saleContract/startContract/"+row.id,
				success: function(data){
					successTip(data,dg);
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
					url : '${ctx}/permission/businessPerssion/updatePermission4Sale/'+row.id,
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

$("#sale_relation").on("click", function(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: "销售合同<font style='color:#F00'>"+row.contractNo +"</font>相关单据",    
	    fit : true,    
	    href:'${ctx}/sale/saleContract/saleRelation/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
});
</script>
</body>
</html>