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
		        <input type="text" name="filter_LIKES_saleContractNo" class="easyui-validatebox" data-options="width:150,prompt: '销售合同号'"/>
		        <input type="text" name="filter_LIKES_deliveryReleaseNo" class="easyui-validatebox" data-options="width:150,prompt: '发货通知号'"/>
		        <input type="text" id="seller_cx" name="filter_LIKES_seller" class="easyui-combobox" data-options="width:180,prompt: '客户名称'"/>
		        <input type="text" id="saleMode_cx" name="filter_LIKES_saleMode" class="easyui-combobox" data-options="width:180,prompt: '销售方式'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sd_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sd_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="sale:saleDelivery:add">
		   		<a id="sd_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sale:saleDelivery:delete">
		        <a id="sd_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleDelivery:update">
		        <a id="sd_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleDelivery:change">
		     <a id="sd_change" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">变更</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleDelivery:view">
		        <a id="sd_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleDelivery:print">
			     <a id="sd_print" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">打印</a>
			     <span class="toolbar-item dialog-tool-separator"></span>
		     </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleDelivery:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleDelivery:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleDelivery:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:saleDelivery:changeState">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-billing" plain="true" onclick="closeOrOpen();">放货状态修改</a>
			</shiro:hasPermission>
	    </div>
	</div>
	<table id="dg" data-options="
					rowStyler: function(index,row){
						if (row.closeOrOpen == '0'){
							return 'color:#0072E3;font-style:italic;';
						}
						if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
						}
					}
				"></table>  
	<div id="dlg"></div> 
	<div id="dlg_goods"></div>
	<div id="dlg_sale"></div>
	<div id="dlg_print"></div>
	<div id="dlg_bak"></div>
<script type="text/javascript">
$("#seller_cx").combobox({
	required : false,
// 	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=10230003',
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230003',
	valueField : 'customerCode',
	textField : 'customerName',
	method:'get'
});

$("#saleMode_cx").combobox({
	panelHeight : 'auto',
	required : false,
	url : '${ctx}/system/dictUtil/getDictByCode/SALESMODE',
	valueField : 'code',
	textField : 'name',
	method:'get'
});
//弹窗修改状态
function closeOrOpen(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "1"){
		$.messager.alert('提示','销售放货当前状态，不能进行修改！','info');
		return;
	}
	var this_tip = "";
	if("0"==row.closeOrOpen){
		this_tip = "确认打开？";
	}else{
		this_tip = "确认关闭？";
	}
	parent.$.messager.confirm('提示', this_tip, function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/sale/saleDelivery/closeOrOpen/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/sale/saleDelivery/json?filter_NES_changeState=0', 
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
			{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
			{field:'deliveryReleaseNo',title:'发货通知号',sortable:true,width:20},
			{field:'sellerView',title:'客户名称',sortable:true,width:20},
			{field:'billDate',title:'制单日期',sortable:true,width:20,
// 				formatter:function(value,row,index){
// 					if(value == null){
// 						return null;
// 					}
//             		var time = new Date(value);
//             		return time.format("yyyy-MM-dd");
//             	}
			},
			{field:'saleModeView',title:'销售方式',sortable:true,width:20},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:20},
			{field:'state',title:'流程状态',sortable:true,width:10,formatter:function(value,row,index){  
                return business_page.stateShow(value);
            }},
			{field:'closeOrOpen',title:'运行状态',sortable:true,width:20,
				formatter:function(value,row,index){
					if("0"==value){
						return "停止";
					}
            		return "运行";
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
   			}
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
	$("#sd_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	//重置
	$("#sd_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		$(".easyui-combobox").combobox('clear');
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('reload',obj);
	});
	$("#sd_print").on("click",function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		var print = $("#dlg_print").dialog({
			title: '放货打印',    
		    fit:false, 
		    width: 900,    
		    height: 600,
		    href:'${ctx}/sale/saleDelivery/toPrintPage/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'打印', iconCls:'icon-hamburg-up',
			    handler:function(){
			    	$('#printMmainDiv').jqprint();
				}
			},
		    {
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					print.panel('close');
				}
			}]
		});
	});
	//增加
	$("#sd_add").on("click", function(){
		d=$("#dlg").dialog({   
		    title: '新增放货',    
		    fit:true,    
		    href:'${ctx}/sale/saleDelivery/create',
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
	});
	//删除
	$("#sd_del").on("click", function(){
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
					url:"${ctx}/sale/saleDelivery/delete/"+row.id,
					success: function(data){
						successTipNew(data,dg);
					}
				});
			} 
		});
	});
	//修改
	$("#sd_upd").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		if(row.processInstanceId != null){
			$.messager.alert('提示','表单已提交申请，不能修改！','info');
			return;
		}
		d=$("#dlg").dialog({   
		    title: '修改放货',    
		    fit:true,  
		    href:'${ctx}/sale/saleDelivery/update/'+row.id,
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
				text:'保存',iconCls:'icon-save',id:'button-save',
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
	
	$("#sd_change").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		if(row.state != '1'){
			$.messager.alert('提示','合同流程未结束，不能变更！','info');
			return;
		}
		parent.$.messager.confirm('提示', '是否确认变更？', function(data){
			if (data){
//	 			$('#scl_change').linkbutton('disable');
//	 			$("#scl_change").unbind("click");
				d=$("#dlg").dialog({
					type:'get',
				    title: '变更合同',    
				    fit:true,  
				    href:'${ctx}/sale/saleDelivery/change/'+row.id,
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
						}
					}]
				});
			} 
		});
	});
	
	//查看明细
	$("#sd_detail").on("click", function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		d=$("#dlg").dialog({   
		    title: '放货明细',    
		    fit : true,    
		    href:'${ctx}/sale/saleDelivery/detail/'+row.id,
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
	business_page.procStartUrl="${ctx}/sale/saleDelivery/apply";
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
				url:"${ctx}/sale/saleDelivery/callBack/" + row.id + "/" + row.processInstanceId,
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
	business_page.procStartUrl="${ctx}/sale/saleDelivery/apply";
	business_page.saveAndApply(dg,d,"mainform",afterMainSubmitSuccess);
}

//弹窗加载采购合同列表
function toSaleList(){
	var	sale=$("#dlg_sale").dialog({   
	    title: '销售合同',    
	    width: 680,    
	    height: 450,  
	    href:'${ctx}/sale/saleDelivery/toSaleDeliverySaleContractList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				//setSaleContractNos();
				saveSaleDeliveryGoods();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				sale.panel('close');
			}
		}]
	});
}
</script>
</body>
</html>