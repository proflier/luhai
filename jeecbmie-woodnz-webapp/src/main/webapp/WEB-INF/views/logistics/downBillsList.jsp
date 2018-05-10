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
        		<input type="text" name="filter_LIKES_billNo_OR_saleContarteNo_OR_invoiceNo_OR_downInvoiceNo_OR_voyage" class="easyui-validatebox" data-options="width:150,prompt: '请输入关键字'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
       	       	<div id="downbills_superSearch" style="display:none;float:right;">
	      		 	<input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
	       	        <span class="toolbar-item dialog-tool-separator"></span>
	       	        <input type="text" name="filter_LIKES_saleContarteNo" class="easyui-validatebox" data-options="width:150,prompt: '销售合同号'"/>
	       	        <span class="toolbar-item dialog-tool-separator"></span>
	       	        <input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '上游发票号'"/>
	       	        <span class="toolbar-item dialog-tool-separator"></span>
	       	        <input type="text" name="filter_LIKES_downInvoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '下游发票号'"/>
       	         	<span class="toolbar-item dialog-tool-separator"></span>
	       	        <input type="text" name="filter_LIKES_voyage" class="easyui-validatebox" data-options="width:150,prompt: '航次'"/>
       	       	</div>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a   href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="superSearch()">高级搜索</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="sys:downstreamBill:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:downstreamBill:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:downstreamBill:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:downstreamBill:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>  
	        <shiro:hasPermission name="sys:downstreamBill:confirm">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="confirm()">确认</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission> 
	        <shiro:hasPermission name="sys:downstreamBill:cancleConfirm">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancleConfirm()">取消确认</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission> 
        </div> 
        
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.confirm == '1'){
						return 'color:#0072E3;font-style:italic;';
					}
				}
			"></table> <!-- 首页到单列表  -->
<div id="dlg"></div>  <!-- 到单页面弹窗  -->
<div id="dlg_sale"></div> <!-- 销售合同列表弹窗  -->
<div id="dlg_container"></div> <!-- 箱单弹窗  -->
<div id="dlg_search"></div> <!-- 搜索弹窗  -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/logistics/downstreamBill/json', 
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
			{field:'billNo',title:'提单号',sortable:true,width:20},
// 			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'saleContarteNo',title:'销售合同号',sortable:true,width:20},
			{field:'voyage',title:'航次',sortable:true,width:20},
			{field:'invoiceNo',title:'上游发票号',sortable:true,width:20},
			{field:'downInvoiceNo',title:'下游发票号',sortable:true,width:20},
			{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
			{field:'supplier',title:'供货商',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.supplier!=''&&row.supplier!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.supplier,
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
			{field:'containerNumber',title:'集装箱数',sortable:true,width:20},
			{field:'portName',title:'目的港',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getPortName/'+value,
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
			{field:'currency',title:'币种',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.currency!=''&&row.currency!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getDictName/currency/'+value,
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
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增交单',    
	    fit:true,    
	    href:'${ctx}/logistics/downstreamBill/create',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				accept();
				$("#mainform").submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				if($("#billNo").val()!=null&&$("#billNo").val()!=''){
					d.panel('close');
				}else{
					var idValue = $("#id").val();
					$.ajax({
						type:'get',
						url:"${ctx}/logistics/downstreamBill/delete/"+idValue,
						success: function(data){
						}
					});
					d.panel('close');
				}
				
			}
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm == '1'){
		$.messager.alert('提示','交单已经确认，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistics/downstreamBill/delete/"+row.id,
				beforeSend: function (){
					if(row.classes == '1级'){
						parent.$.messager.alert('操作失败','不能删除1级商品!','error');
						return false;
					}
					return true;
				},
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
	if(row.confirm == "1"){
		$.messager.alert('提示','交单已经确认，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改交单',    
	    fit:true,  
	    href:'${ctx}/logistics/downstreamBill/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				accept();
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
	    title: '交单明细',    
	    fit : true,    
	    href:'${ctx}/logistics/downstreamBill/detail/'+row.id,
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

//确认到单
function confirm(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm == "1"){
		$.messager.alert('提示','交单已经确认，不能重复确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '确认交单？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistics/downstreamBill/confirm/"+row.id,
				success: function(data){
// 					successTip(data,dg);
					successTipNew(data,dg);
				}
			});
		} 
	});
}

//取消确认
function cancleConfirm(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm != "1"){
		$.messager.alert('提示','交单未确认，不能取消确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '取消确认交单？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistics/downstreamBill/cancleConfirm/"+row.id,
				success: function(data){
// 					successTip(data,dg);
					successTipNew(data,dg);
				}
			});
		} 
	});
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

// function superSearch(){
	
// 	if($("#downbills_superSearch").css("display")=='none'){//如果show是隐藏的
// 		$("#downbills_superSearch").css("display","block");//show的display属性设置为block（显示）
// 	}else{//如果show是显示的
// 		$("#downbills_superSearch").css("display","none");//show的display属性设置为none（隐藏）
// 		}
// // 	$('#downbills_superSearch').show();
	
// }




function reset(){
	$("#searchFrom")[0].reset();
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//弹窗加载销售合同列表
function toSaleList(){
	dlg_sale=$("#dlg_sale").dialog({   
	    title: '销售合同',    
	    width: 900,    
	    height: 400,  
	    href:'${ctx}/logistics/downstreamBill/toSaleList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				parent.$.messager.confirm('提示', '点击确定后将更新箱单信息，确认？', function(r){
					if(r){
						initBill();
						dlg_sale.panel('close');
					}else{
					}
				});
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_sale.panel('close');
			}
		}]
	});
}


//弹窗增加箱单
function addContainer() {
	var idValue = $('#id').val();
	dlg_container=$("#dlg_container").dialog({  
		method:'GET',
	    title: '新增箱单',    
	    width: 700,    
	    height: 400,     
	    href:'${ctx}/logistics/downstreamContainer/create/'+idValue,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$('#containerform').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_container.panel('close');
			}
		}]
	});
}


//删除箱单
function deleteContainer(){
	var row = dgContainer.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/logistics/downstreamContainer/delete/'+row.id,
				success: function(data){
					successTip(data,dgContainer);
				}
			});
		} 
	});
}

//弹窗修改箱单
function updateContainer() {
	var row = dgContainer.datagrid('getSelected');
	if(rowIsNull(row)) return;
	dlg_container=$("#dlg_container").dialog({   
	    title: '修改箱单',    
	    width: 700,    
	    height: 400,
	    href:'${ctx}/logistics/downstreamContainer/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$('#containerform').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_container.panel('close');
			}
		}]
	});
}

function superSearch(){
	dlg_search=$("#dlg_search").dialog({   
	    title: '高级搜索',    
	    width: 600,    
	    height: 200,
	    href:'${ctx}/logistics/downstreamBill/superSearch/',
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

</script>
</body>
</html>