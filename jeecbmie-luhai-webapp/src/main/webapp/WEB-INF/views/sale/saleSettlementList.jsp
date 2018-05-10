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
		        <input type="text" name="filter_LIKES_saleContractNo" class="easyui-validatebox" data-options="width:150,prompt: '销售合同编号'"/>
		        <input type="text" name="filter_LIKES_saleSettlementNo" class="easyui-validatebox" data-options="width:150,prompt: '结算编号'"/>
		        <input type="text" id="receivingUnit_cx" name="filter_LIKES_receivingUnit" class="easyui-validatebox" data-options="width:180,prompt: '收货单位'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sf_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sf_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="sale:saleSettlement:add">
		   		<a id="ssl_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sale:saleSettlement:delete">
		        <a id="ssl_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleSettlement:update">
		        <a id="ssl_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sale:saleSettlement:view">
		        <a id="ssl_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		     <shiro:hasPermission name="sale:saleSettlement:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleSettlement:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sale:saleSettlement:trace">
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
	<div id="dlgPurchase"></div> 
	<div id="dlg_sale"></div>
	<div id="dlg_saleContract"></div>
	<div id="dlg_goods"></div>
<script type="text/javascript">
$("#receivingUnit_cx").combobox({
	required : false,
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=10230003',
	valueField : 'customerCode',
	textField : 'customerName',
	method:'get'
});
var sale_settlement_page = {
		settlement_list : '',
		listSettlement:function(){
			this.settlement_list=$('#dg').datagrid({
				method: "get",
			    url:'${ctx}/sale/saleSettlement/json', 
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
					{field:'saleSettlementNo',title:'结算编号',sortable:true,width:20},
					{field:'saleContractNo',title:'销售合同编号',sortable:true,width:20},
					{field:'receivingUnitView',title:'收货单位',width:20,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&& value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}	
					},
					{field:'settlementDate',title:'结算日期',sortable:true,width:20,
// 						formatter:function(value,row,index){
// 							if(value == null){
// 								return null;
// 							}
// 		            		var time = new Date(value);
// 		            		return time.format("yyyy-MM-dd");
// 		            	}
					},
		            	{field:'state',title:'流程状态',sortable:true,width:10,
		            		formatter:function(value,row,index){  
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
		},
		settlement_form:'',
		addSettlement:function(){
			sale_settlement_page.settlement_form=$("#dlg").dialog({   
			    title: '新增结算',    
			    fit:true,    
			    href:'${ctx}/sale/saleSettlement/create',
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
						sale_settlement_page.settlement_form.panel('close');
					}
				}]
			});
		},
		updateSettlement:function(){
			var row = sale_settlement_page.settlement_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId != null){
				$.messager.alert('提示','表单已提交申请，不能修改！','info');
				return;
			}
			sale_settlement_page.settlement_form=$("#dlg").dialog({   
			    title: '修改结算',    
			    fit:true,  
			    href:'${ctx}/sale/saleSettlement/update/'+row.id,
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
						sale_settlement_page.settlement_form.panel('close');
					}
				}]
			});
		},
		deleteSettlement:function(){
			var row = sale_settlement_page.settlement_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId != null){
				$.messager.alert('提示','表单已提交申请，不能删除！','info');
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/sale/saleSettlement/delete/"+row.id,
						success: function(data){
							successTipNew(data,sale_settlement_page.settlement_list);
						}
					});
				} 
			});
		},
		settlement_detail:'',
		detailSettlement:function(){
			var row = sale_settlement_page.settlement_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			sale_settlement_page.settlement_detail=$("#dlg").dialog({   
			    title: '结算明细',    
			    fit : true,    
			    href:'${ctx}/sale/saleSettlement/detail/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sale_settlement_page.settlement_detail.panel('close');
					}
				}]
			});
		}
};
$(function(){
	sale_settlement_page.listSettlement();
	//查询
	$("#sf_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		sale_settlement_page.settlement_list.datagrid('reload',obj);
	});
	//重置
	$("#sf_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		$("#receivingUnit_cx").combobox('clear');
		var obj=$("#searchFrom").serializeObject();
		sale_settlement_page.settlement_list.datagrid('reload',obj);
	});
	//增加
	$("#ssl_add").on("click", sale_settlement_page.addSettlement);
	//删除
	$("#ssl_del").on("click", sale_settlement_page.deleteSettlement);
	//修改
	$("#ssl_upd").on("click", sale_settlement_page.updateSettlement);
	//查看明细
	$("#ssl_detail").on("click", sale_settlement_page.detailSettlement);
});
//申请
function apply(){
	business_page.procStartUrl="${ctx}/sale/saleSettlement/apply";
	business_page.apply(sale_settlement_page.settlement_list);
}

//撤回
function callBack(){
	var row = sale_settlement_page.settlement_list.datagrid('getSelected');
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
				url:"${ctx}/sale/saleSettlement/callBack/" + row.id + "/" + row.processInstanceId,
				success: function(data){
			    	successTip(data,sale_settlement_page.settlement_list);
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
	var row = sale_settlement_page.settlement_list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未发起，不存在流程跟踪！','info');
		return;
	}
	business_page.traceProc(row.processInstanceId);
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/sale/saleSettlement/apply";
	business_page.saveAndApply(sale_settlement_page.settlement_list,sale_settlement_page.settlement_form,"mainform",afterMainSubmitSuccess);
}
// 弹窗加载采购合同列表(同销售放货公用)
function toSaleList(){
	var	sale=$("#dlg_sale").dialog({   
	    title: '销售合同',    
	    width: 600,    
	    height: 500,  
	    href:'${ctx}/sale/saleSettlement/toSaleContractList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				saveBasedSaleSettlement();
				sale.panel('close');
				sale_settlement_goods.listOutStock();
				sale_settlement_goods.listQualityInspect();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				sale.panel('close');
			}
		}]
	});
}
//弹窗加载采购合同列表(同销售放货公用)
function toSaleContract(){
	var saleContractNo = $("#mainform>#mainDiv #saleContractNo").val();
	if(saleContractNo==null || saleContractNo==""){
		return;
	}else{
		 var	sale=$("#dlg_saleContract").dialog({   
		    title: '销售合同',    
		    width: 620,    
		    height: 390,  
		    href:'${ctx}/sale/saleContract/detailByNo/'+saleContractNo,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					sale.panel('close');
				}
			}]
		}); 
	}
}
</script>
</body>
</html>