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
       	        <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '保险合同号'"/>
       	        <input type="text" name="filter_LIKES_innerContractNo" class="easyui-validatebox" data-options="width:150,prompt: '内部合同号'"/>
       	        <input type="text" id="filter_LIKES_insuranceCompany" name="filter_LIKES_insuranceCompany" class="easyui-combobox" data-options="width:150,prompt: '保险公司'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="lh:insuranceContract:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="lh:insuranceContract:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:insuranceContract:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:insuranceContract:change">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="change()">变更</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:insuranceContract:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:insuranceContract:apply">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="lh:insuranceContract:callBack">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="lh:insuranceContract:trace">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
		    <shiro:hasPermission name="lh:insuranceContract:businessPermission">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="setBusenessPermission();">相关人员配置</a>
			</shiro:hasPermission>
	        <shiro:hasPermission name="lh:insuranceContract:view">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true" onclick="exportPDF()">导出pdf</a>
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
<div id="dlg_ships"></div>
<div id="dlg_selectUsers"></div> 
<div id="dlg_bak"></div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/logistic/insurance/json?filter_NES_changeState=0', 
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
				{field:'contractNo',title:'保险合同号',sortable:true,width:20}, 
				{field:'innerContractNo',title:'内部合同号',sortable:true,width:20}, 
	            {field:'isInsurance',title:'是否已保',sortable:true,width:15,
					 formatter: function(value,row,index){
		      				var val;
		      				if(value!=''&&value!=null){
		      					if(value=='1'){
		      						val ='是';
		      					}else{
		      						val ='否';
		      					}
		      					return val;
		      				}else{
		      					return '';
		      				}
		      			}}, 
				{field:'insuranceCompanyView',title:'保险公司',sortable:true,width:20,
// 	            	formatter: function(value,row,index){
// 						var val;
// 						if(value!=''&&value!=null){
// 							$.ajax({
// 								type:'GET',
// 								async: false,
// 								url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 								success: function(data){
// 									if(data != null){
// 										val = data;
// 									} else {
// 										val = '';
// 									}
// 								}
// 							});
// 							return val;
// 						}else{
// 							return '';
// 						}
// 					}
		      	}, 
				{field:'money',title:'保额',sortable:true,width:20}, 
				{field:'amount',title:'保费',sortable:true,width:20},
				{field:'shipNo',title:'船编号',sortable:true,width:25,
					formatter: function(value,row,index){
						var val;
						if(value!=''&& value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
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
				{field:'insuranceDate',title:'投保日期',sortable:true,width:20,
					formatter:function(value,row,index){  
						if(value==null){
							return null;
						}
		                    var unixTimestamp = new Date(value);  
		                    return unixTimestamp.format("yyyy-MM-dd");  
	                    } 
				}, 
				{field:'type',title:'险种',sortable:true,width:20},
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
	
	//保险公司
	$('#filter_LIKES_insuranceCompany').combobox({
//	 	panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230005',
		valueField : 'customerCode',
		textField : 'customerName',
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增保险合同',    
	    fit:true,    
	    href:'${ctx}/logistic/insurance/create',
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
				url:"${ctx}/logistic/insurance/delete/"+row.id,
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
	    title: '修改保险合同',    
	    fit:true,    
	    href:'${ctx}/logistic/insurance/update/'+row.id,
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
}

//合同变更
function change(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "1"){
		$.messager.alert('提示','合同当前状态，不能进行变更！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '变更合同',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/logistic/insurance/change/'+row.id,
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
//查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '保险合同明细',    
	    fit:true,  
	    href:'${ctx}/logistic/insurance/detail/'+row.id,
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
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//申请
function apply(){
	business_page.procStartUrl="${ctx}/logistic/insurance/apply";
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
				url:"${ctx}/logistic/insurance/callBack/" + row.id + "/" + row.processInstanceId,
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
	business_page.procStartUrl="${ctx}/logistic/insurance/apply";
	business_page.saveAndApply(dg,d,"mainform");
}
//弹窗加载采购合同列表(同销售放货公用)
function toShipList(tradeCategory){
	var	sale=$("#dlg_ships").dialog({   
	    title: '船舶',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/logistic/insurance/toShipList/'+tradeCategory,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				insurance_save();
				sale.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				sale.panel('close');
			}
		}]
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
					url : '${ctx}/permission/businessPerssion/updatePermission4InsuranceContract/'+row.id,
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
	var url = "${ctx}/logistic/insurance/exportPDF/" + row.innerContractNo;
	window.location.href = url;
}
</script>
</body>
</html>

