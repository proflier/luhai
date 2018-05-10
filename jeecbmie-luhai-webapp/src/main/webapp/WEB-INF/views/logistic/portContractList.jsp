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
       	        <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '合同号'"/>
       	        <input type="text" id="filter_LIKES_signAffiliate" name="filter_LIKES_signAffiliate" class="easyui-combobox" data-options="width:150,prompt: '签订单位'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="lh:portContract:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="portContract.contractAdd();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="lh:portContract:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="portContract.contractDel()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:portContract:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="portContract.contractUpd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:portContract:change">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="portContract.contractChange()">变更</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:portContract:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="portContract.contractDetail()">查看明细</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:portContract:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="lh:portContract:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="lh:portContract:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
		    <shiro:hasPermission name="lh:portContract:businessPermission">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="setBusenessPermission();">相关人员配置</a>
			</shiro:hasPermission>
	        <shiro:hasPermission name="lh:portContract:view">
	            <a id="export_PDF" href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true">导出pdf</a>
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
<div id="dlg_operate"></div>
<div id="dlg_extra"></div>
<div id="dlg_selectUsers"></div> 
<div id="dlg_bak"></div>
<script type="text/javascript">
var portContract = {
		contractList : $('#dg').datagrid({  
			method: "get",
		    url:'${ctx}/logistic/portContract/json?filter_NES_changeState=0', 
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
					{field:'signAffiliateView',title:'签订单位',sortable:true,width:20}, 
					{field:'freeHeapDays',title:'免堆单天数',sortable:true,width:20}, 
					{field:'freeHeapCounts',title:'达量免堆数',sortable:true,width:20}, 
					{field:'checkTypeCodeView',title:'审核类别',sortable:true,width:20}, 
					{field:'startDay',title:'开始日期',sortable:true,width:20,
						formatter:function(value,row,index){  
							if(value==null){
								return null;
							}
			                    var unixTimestamp = new Date(value);  
			                    return unixTimestamp.format("yyyy-MM-dd");  
		                    } 
					}, 
					{field:'state',title:'流程状态',sortable:true,width:10,
						formatter:function(value,row,index){  
			                    return business_page.stateShow(value);
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
        			},
		    ]],
		    sortName:'id',
		    sortOrder:'desc',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    toolbar:'#tb',
			onDblClickRow:function(rowIndex, rowData){
				portContract.contractDetail();
			}
		}),
		contractFormDiv:{},
		contractAdd:function(){
			portContract.contractFormDiv = $("#dlg").dialog({   
			    title: '新增合同',    
			    fit:true,    
			    href:'${ctx}/logistic/portContract/create',
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
						portContract.contractFormDiv.panel('close');
					}
				}]
			});
		},
		contractUpd:function(){
			var row = portContract.contractList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId != null){
				$.messager.alert('提示','表单已提交申请，不能修改！','info');
				return;
			}
			portContract.contractFormDiv=$("#dlg").dialog({   
			    title: '修改合同',    
			    fit:true,    
			    href:'${ctx}/logistic/portContract/update/'+row.id,
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
						portContract.contractFormDiv.panel('close');
					}
				}]
			});
		},
		contractDetail:function(){
			var row = portContract.contractList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			portContract.contractFormDiv=$("#dlg").dialog({   
			    title: '合同明细',    
			    fit:true,  
			    href:'${ctx}/logistic/portContract/detail/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						portContract.contractFormDiv.panel('close');
					}
				}]
			});
		},
		contractDel:function(){
			var row = portContract.contractList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.processInstanceId != null){
				$.messager.alert('提示','表单已提交申请，不能删除！','info');
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/logistic/portContract/delete/"+row.id,
						success: function(data){
							successTipNew(data,portContract.contractList);
						}
					});
				} 
			});
		},
		contractChange:function(){
			var row = portContract.contractList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			if(row.state != '1'){
				$.messager.alert('提示','合同流程未结束，不能变更！','info');
				return;
			}
			parent.$.messager.confirm('提示', '是否确认变更？', function(data){
				if (data){
					portContract.contractFormDiv=$("#dlg").dialog({
						type:'get',
					    title: '变更合同',    
					    fit:true,  
					    href:'${ctx}/logistic/portContract/change/'+row.id,
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
								$('#dg').datagrid("reload");
								portContract.contractFormDiv.panel('close');
							}
						}]
					});
				} 
			});
		},
};

$('#filter_LIKES_signAffiliate').combobox({
// 	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230007',
	valueField : 'customerCode',
	textField : 'customerName',
});

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	portContract.contractList.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	cx(); 
}

//申请
function apply(){
	business_page.procStartUrl="${ctx}/logistic/portContract/apply";
	business_page.apply(portContract.contractList);
}

//撤回
function callBack(){
	var row = portContract.contractList.datagrid('getSelected');
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
				url:"${ctx}/logistic/portContract/callBack/" + row.id + "/" + row.processInstanceId,
				success: function(data){
			    	successTip(data,portContract.contractList);
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
	var row = $('#dg').datagrid('getSelected');
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
					url : '${ctx}/permission/businessPerssion/updatePermission4PortContract/'+row.id,
					type : "get",
					data:{
						"themeMemberIds":	themeMemberIds,
					},
					success : function(data) {}
					});
				selectUsers_dg.panel('close');
				$('#dg').datagrid('reload');
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
	var row = portContract.contractList.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未发起，不存在流程跟踪！','info');
		return;
	}
	business_page.traceProc(row.processInstanceId);
}

//提交表单并提交申请
function applyAnd(action){
	business_page.procStartUrl="${ctx}/logistic/portContract/apply";
	business_page.saveAndApply(portContract.contractList,portContract.contractFormDiv,"mainform");
}

//导出pdf
$("#export_PDF").on("click", function() {
	var row = portContract.contractList.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var url = "${ctx}/logistic/portContract/exportPDF/" + row.contractNo;
	window.location.href = url;
});	
</script>
</body>
</html>

