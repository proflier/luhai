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
		        <input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '结算编号'"/>
		        <input type="text" name="filter_LIKES_saleContractNo" class="easyui-validatebox" data-options="width:150,prompt: '销售合同号'"/>
		        <input type="text" id="customerName_cx" name="filter_LIKES_customerName" class="easyui-validatebox" data-options="width:180,prompt: '客户名称'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sf_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sf_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="sale:saleInvoice:add">
		   		<a id="scl_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sale:saleInvoice:delete">
		        <a id="scl_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleInvoice:update">
		        <a id="scl_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleInvoice:view">
		        <a id="scl_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		    </shiro:hasPermission>
		      <shiro:hasPermission name="sale:saleInvoice:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleInvoice:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleInvoice:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
	    </div>
	</div>
	<table id="dg" data-options="
					rowStyler: function(index,row){
						if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
						}
					}
				"></table>  <!-- 首页到单列表  -->
	<div id="dlg"></div>  <!-- 到单页面弹窗  -->
	<div id="dlgPurchase"></div> <!-- 采购合同列表弹窗  -->
	<div id="dlg_goods"></div>
	<div id="dlgSaleContract"></div>
<script type="text/javascript">
$("#customerName_cx").combobox({
	required : false,
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=10230003',
	valueField : 'customerCode',
	textField : 'customerName',
	method:'get'
});
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/sale/saleInvoice/json', 
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
			{field:'invoiceNo',title:'结算编号',sortable:true,width:20},
			{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
			{field:'customerNameView',title:'客户名称',sortable:true,width:20,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&& value!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
			{field:'preBilling',title:'是否预开票',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						val = value=='0'?'否':'是';
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'createDate',title:'制单日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
            	{field:'state',title:'流程状态',sortable:true,width:10,formatter:function(value,row,index){  
                    return business_page.stateShow(value);
                }}
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
	
	//查询
	$("#sf_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//重置
	$("#sf_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		$("#customerName_cx").combobox('clear');
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//增加
	$("#scl_add").on("click", function(){
		d=$("#dlg").dialog({   
		    title: '新增发票',    
		    fit:true,    
		    href:'${ctx}/sale/saleInvoice/create',
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
					url:"${ctx}/sale/saleInvoice/delete/"+row.id,
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
		    title: '修改发票',    
		    fit:true,  
		    href:'${ctx}/sale/saleInvoice/update/'+row.id,
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
	//查看明细
	$("#scl_detail").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		d=$("#dlg").dialog({   
		    title: '发票明细',    
		    fit : true,    
		    href:'${ctx}/sale/saleInvoice/detail/'+row.id,
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
});
//申请
function apply(){
	business_page.procStartUrl="${ctx}/sale/saleInvoice/apply";
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
				url:"${ctx}/sale/saleInvoice/callBack/" + row.id + "/" + row.processInstanceId,
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
	business_page.procStartUrl="${ctx}/sale/saleInvoice/apply";
	business_page.saveAndApply(dg,d,"mainform");
}
</script>
</body>
</html>