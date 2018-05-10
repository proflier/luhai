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
	<form id="searchFrom" action="">
		<input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '合同号'"/>
		<input type="text" name="filter_LIKES_innerContractNo" class="easyui-validatebox" data-options="width:150,prompt: '内部合同号'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		<a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	</form>
	
	<shiro:hasPermission name="logistic:proxyAgreement:add">
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="logistic:proxyAgreement:delete">
		<a id="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="logistic:proxyAgreement:update">
		<a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="logistic:proxyAgreement:view">
		<a id="view" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" >查看明细</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="logistic:proxyAgreement:apply">
		<a id="apply" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" >提交申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="logistic:proxyAgreement:callBack">
		<a id="callBack" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" >撤回申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="logistic:proxyAgreement:trace">
		<a id="trace" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" >流程跟踪</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dataGrid" data-options="
				rowStyler: function(index,row){
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>
<!-- <div id="dlg_goods"></div> -->
<script type="text/javascript">
(function(window,$){
	var proxyAgreement = {
		dataGrid: {},
		init: function() {
			$("#search").click(proxyAgreement.getData);
			$("#reset").click(proxyAgreement.resetForm);
			$("#add").click(proxyAgreement.add);
			$("#delete").click(proxyAgreement.del);
			$("#update").click(proxyAgreement.update);
			$("#view").click(proxyAgreement.view);
			$("#apply").click(proxyAgreement.apply);
			$("#callBack").click(proxyAgreement.callBack);
			$("#trace").click(proxyAgreement.trace);
			dataGrid = $('#dataGrid').datagrid({  
				method: "get",
			    url:'${ctx}/logistic/proxyAgreement/json', 
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
					{field:'contractNo',title:'合同编号',sortable:true,width:20}, 
					{field:'innerContractNo',title:'内部合同号',sortable:true,width:20}, 
					{field:'ourUnit',title:'委托方',sortable:true,width:20,
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
						}	
					},
					{field:'agreementUnit',title:'代理方',sortable:true,width:20,
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
						}	
					},
					{field:'freightFee',title:'货代费',sortable:true,align:'right',width:15,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.000; //如果不是数字，返回0.000
							}else{
// 								value = Math.abs(value); //取绝对值
								value = parseFloat(value);
								return(value.toFixed(3)); 
							}
						}
					},
					{field:'signDate',title:'签约时间',sortable:true,width:15},
					{field:'state',title:'流程状态',sortable:true,width:10,formatter:function(value,row,index){  
		                   return business_page.stateShow(value);
		               }},
			    ]],
			    sortName:'id',
			    sortOrder: 'desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tb',
				onDblClickRow:function(rowIndex, rowData){
					proxyAgreement.view();
				}
			});
		},
		getData: function() {
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj);
		}, 
		resetForm: function() {
			$("#searchFrom")[0].reset();
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj); 
		},
		add:function() {//弹窗增加
			d=$("#dlg").dialog({   
			    title: '添加', 
			    href:'${ctx}/logistic/proxyAgreement/create',
			    modal:true,
			    fit:true,
			    style:{borderWidth:0},
			    closable:false,
			    buttons:[{
					text:'保存',iconCls:'icon-save',id:'button-save',
					handler:function(){
						$("#mainform").submit();
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						d.panel('close');
					},
				}]
			});
		},
		del:function(){//删除
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId != null){
				$.messager.alert('提示','表单已提交申请，不能修改！','info');
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/logistic/proxyAgreement/delete/"+row.id,
						success: function(data){
							successTip(data,dataGrid);
						}
					});
				} 
			});
		},
		update:function(){//弹窗修改
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId != null){
				$.messager.alert('提示','表单已提交申请，不能修改！','info');
				return;
			}
			d=$("#dlg").dialog({    
			    title: '修改',    
			    href:'${ctx}/logistic/proxyAgreement/update/'+row.id,
			    modal:true,
			    fit:true,
			    style:{borderWidth:0},
			    closable:false,
			    buttons:[{
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
		},
		view:function(){//查看明细
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			d=$("#dlg").dialog({   
			    title: '明细',    
			    href:'${ctx}/logistic/proxyAgreement/detail/'+row.id,
			    modal:true,
			    fit:true,
			    style:{borderWidth:0},
			    closable:false,
			    buttons:[
			             {
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						d.panel('close');
					}
				}]
			});
		},
		apply:function (){
			business_page.procStartUrl="${ctx}/logistic/proxyAgreement/apply";
			business_page.apply(dataGrid);
		},
		callBack:function (){
			var row = dataGrid.datagrid('getSelected');
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
						url:"${ctx}/logistic/proxyAgreement/callBack/" + row.id + "/" + row.processInstanceId,
						success: function(data){
					    	successTip(data,dataGrid);
							if(data=='success'){
								parent.$.messager.show({ title:"提示", msg:"已成功撤回申请！", position:"bottomRight" });
							}
							parent.$.messager.progress('close');
						}
					});
				}
			});
		},
		trace: function () {
			var row = dataGrid.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId == null){
				$.messager.alert('提示','表单未提交申请，不存在流程跟踪！', 'info');
				return;
			}
			business_page.traceProc(row.processInstanceId);;
		}
	};
	window.proxyAgreement = (window.proxyAgreement) ? window.proxyAgreement : proxyAgreement;
	$(function(){
		proxyAgreement.init();
	});
})(window,jQuery);
</script>
</body>
</html>