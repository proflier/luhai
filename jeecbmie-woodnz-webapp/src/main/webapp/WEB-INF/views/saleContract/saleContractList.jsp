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
       	        <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '销售合同号'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="sys:woodSaleContract:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodSaleContract:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodSaleContract:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodSaleContract:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodSaleContract:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:woodSaleContract:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:woodSaleContract:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:woodSaleContract:changeState">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-billing" plain="true" onclick="updateState();">合同状态修改</a>
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
<div id="dlg"></div>  
<div id="dlg_trace"></div>
<div id="searchGoodsCode">
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/sale/saleContract/json', 
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
				{field:'purchaser',title:'买方',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				{field:'seller',title:'卖方',sortable:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
	            {field:'contractNo',title:'销售合同号',sortable:true,width:20}, 
				{field:'signDate',title:'签订日期',sortable:true,width:20,
	            	formatter:function(value,row,index){  
	            		if(value==null){
	            			return;
	            		}
	                    var unixTimestamp = new Date(value);  
	                    return unixTimestamp.format("yyyy-MM-dd");  
	                    }  
	             },  
				{field:'totalMoney',title:'合同总额',sortable:true,width:20}, 
				{field:'currency',title:'币种',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getDictName/currency/"+value,
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
						}}
				},
				{field:'draftsman',title:'合同制作人',sortable:true,width:20},
				{field:'executer',title:'合同执行人',sortable:true,width:20},
				{field:'state',title:'流程状态',sortable:true,width:10},
				{field:'state_ht',title:'合同状态',sortable:true,width:10}
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
	    title: '新增销售',    
	    fit:true,    
	    href:'${ctx}/sale/saleContract/create',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
		    handler:function(){
				parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
					if(data){
						acceptSubForm();
// 						compute('amount');
// 						compute('money');
						applyAnd("create");
					}
				});
			}
		},{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				accept();
				compute('amount');
				compute('money');
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
				url:"${ctx}/sale/saleContract/delete/"+row.id,
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
	    title: '修改销售',    
	    fit:true,    
	    href:'${ctx}/sale/saleContract/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
		    handler:function(){
				parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
					if(data){
						acceptSubForm();
// 						compute('amount');
// 						compute('money');
						applyAnd("update");
					}
				});
			}
		},{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				accept();
				compute('amount');
				compute('money');
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
	    title: '销售明细',    
	    fit:true,  
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
// 				url:"${ctx}/sale/saleContract/apply/" + row.id,
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

//申请
function apply(){
	business_page.procStartUrl="${ctx}/sale/saleContract/apply";
	business_page.apply(dg);
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/sale/saleContract/apply";
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
// 	$.post('${ctx}/sale/saleContract/' + action, jsonData, function(data){
// 		successTipNew(data,dg,d);
// 		parent.$.messager.progress('close');
// 	});
// }

//弹窗修改状态
function updateState(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != '生效'){
		$.messager.alert('提示','合同未生效，不能修改状态！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '合同状态修改',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/sale/saleContract/updateState/'+row.id,
	    modal:true,
	    buttons:[{
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
}

function acceptSubForm(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#contractSubJson').val(JSON.stringify(rows));
		return true;
	}
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
</script>
</body>
</html>

