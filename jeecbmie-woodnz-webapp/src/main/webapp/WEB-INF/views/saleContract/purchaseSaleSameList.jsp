<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!--引入引入kindeditor编辑器相关文件--> 
<link rel="stylesheet" href="${ctx}/static/plugins/kindeditor/themes/default/default.css" />
<script src="${ctx}/static/plugins/kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/kindeditor/lang/zh-CN.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
       	        <input type="text" name="filter_LIKES_keynote" class="easyui-validatebox" data-options="width:150,prompt: '主旨'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="sys:woodPurchaseSaleSame:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodPurchaseSaleSame:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodPurchaseSaleSame:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodPurchaseSaleSame:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodPurchaseSaleSame:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:woodPurchaseSaleSame:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:woodPurchaseSaleSame:trace">
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
			"></table>
<div id="dlg"></div> <!-- 新增采销同批弹窗  -->
<div id="dlg_trace"></div>
<div id="dlgSaleContract"></div> <!-- 销售合同列表弹窗  -->
<div id="dlgPurchaseContract"></div> <!-- 采购合同列表弹窗  -->
<div id="dlgSaleDetail"></div> <!-- 销售合同查看明细弹窗 -->
<div id="dlgPurchaseDetail"></div><!--  采购合同查看明细弹窗 -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/sale/purchaseSaleSame/json', 
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
				{field:'id',title:'id',hidden:true} ,
				{field:'receiveCompany',title:'收文单位',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(row.receiveCompany!=''&&row.receiveCompany!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getBaseInfoName/"+row.receiveCompany,
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
				{field:'postCompany',title:'发文单位',sortable:true,width:20},
				{field:'applyDate',title:'日期',sortable:true,width:20,
					formatter:function(value,row,index){
						if(value == null){
							return null;
						}
	            		var time = new Date(value);
	            		return time.format("yyyy-MM-dd");
	            	}},
				{field:'transactor',title:'经办人',sortable:true,width:20},
				{field:'keynote',title:'主旨',sortable:true,width:20},
				{field:'state',title:'流程状态',sortable:true,width:10},
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
	    title: '新增采销同批',    
	    fit:true,    
	    href:'${ctx}/sale/purchaseSaleSame/create',
	    maximizable:false,
	    modal:true,
// 	    style:{borderWidth:0},
// 	    closable:false,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
		    handler:function(){
				parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
					if(data){
						applyAnd("create");
					}
				});
			}
		},{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
// 				convertToJson();
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
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/sale/purchaseSaleSame/delete/"+row.id,
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改采销同批',    
	    fit:true,    
	    href:'${ctx}/sale/purchaseSaleSame/update/'+row.id,
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
// 				convertToJson();
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
	    title: '采销同批明细',    
	    fit:true,  
	    href:'${ctx}/sale/purchaseSaleSame/detail/'+row.id,
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
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//申请
// function apply(){
// 	var row = dg.datagrid('getSelected');
// 	if(rowIsNull(row)) return;
// 	if(row.processInstanceId != null){
// 		$.messager.alert('提示','表单已提交申请，不能重复提交！', 'info');
// 		return;
// 	}
// 	parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
// 		if (data){
// 			parent.$.messager.progress({  
// 		        title : '提示',  
// 		        text : '数据处理中，请稍后....'  
// 		    });
// 			$.ajax({
// 				type:'get',
// 				url:"${ctx}/sale/purchaseSaleSame/apply/" + row.id,
// 				success: function(data){
// 			    	successTip(data,dg);
// 					if(data=='success'){
// 						parent.$.messager.show({ title:"提示", msg:"流程已启动！", position:"bottomRight" });
// 					}
// 			    	if(data=='no deployment'){
// 			    		parent.$.messager.show({ title:"提示", msg:"没有部署流程！", position:"bottomRight" });
// 			    	}
// 			    	if(data=='start fail'){
// 			    		parent.$.messager.show({ title:"提示", msg:"启动流程失败！", position:"bottomRight" });
// 			    	}
// 			    	parent.$.messager.progress('close');
// 				}
// 			});
// 		}
// 	});
// }


function apply(){
	business_page.procStartUrl="${ctx}/sale/purchaseSaleSame/apply";
	business_page.apply(dg);
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/sale/purchaseSaleSame/apply";
	business_page.saveAndApply(dg,d,"mainform");
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
				url:"${ctx}/sale/purchaseSaleSame/callBack/" + row.id + "/" + row.processInstanceId,
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
// function trace() {
// 	var row = dg.datagrid('getSelected');
// 	if(rowIsNull(row)) return;
// 	if(row.processInstanceId == null){
// 		$.messager.alert('提示','表单未提交申请，不存在流程跟踪！', 'info');
// 		return;
// 	}
// 	$.ajaxSetup({type : 'GET'});
// 	d=$("#dlg_trace").dialog({   
// 	    title: '流程跟踪',
// 	    width: 680,    
// 	    height: 450,    
// 	    href:'${ctx}/workflow/trace/'+row.processInstanceId,
// 	    maximizable:true,
// 	    modal:true,
// 	    buttons:[{
// 			text:'关闭',iconCls:'icon-cancel',
// 			handler:function(){
// 				d.panel('close');
// 			}
// 		}]
// 	});
// }

//提交表单并提交申请
// function applyAnd(action){
// 	var isValid = $("#mainform").form('validate');//验证表单
// 	if(!isValid){
// 		return false;
// 	}
// 	parent.$.messager.progress({  
//         title : '提示',  
//         text : '数据处理中，请稍后....'  
//     });
// 	var jsonData = $("#mainform").serializeArray();//将页面表单序列化成一个JSON结构的对象
// 	jsonData.push({"name":"apply","value":"true"});//向JSON对象里放入提交申请标志
// 	$.post('${ctx}/sale/purchaseSaleSame/' + action, jsonData, function(data){
// 		successTipNew(data,dg,d);
// 		parent.$.messager.progress('close');
// 	});
// }

//弹窗加载采购合同列表
function toPurchaseContractList(){
	purchase=$("#dlgPurchaseContract").dialog({   
	    title: '采购合同',    
	    width: 700,    
	    height: 350,  
	    href:'${ctx}/sale/purchaseSaleSame/toPurchaseList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				setPurchaseContract();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				purchase.panel('close');
			}
		}]
	});
}

//弹窗加载销售合同列表
function toSaleContractList(){
	sale=$("#dlgSaleContract").dialog({   
	    title: '销售合同',    
	    width: 700,    
	    height: 350,  
	    href:'${ctx}/sale/purchaseSaleSame/toSaleList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				setSaleContract();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				sale.panel('close');
			}
		}]
	});
}

// function convertToJson(){
// 	var rows1 = $('#dgPurchaseList').datagrid('getRows');
// 	$('#dgPurchaseList').datagrid('acceptChanges');
// 	$('#purchaseContractJson').val(JSON.stringify(rows1));
// 	var rows2 = $('#dgSaleList').datagrid('getRows');
// 	$('#dgSaleList').datagrid('acceptChanges');
// 	$('#saleContractJson').val(JSON.stringify(rows2));
// }

</script>
</body>
</html>

