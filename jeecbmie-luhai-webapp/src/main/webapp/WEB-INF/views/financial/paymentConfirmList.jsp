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
	    <input type="text" name="filter_LIKES_confirmNo" class="easyui-validatebox" data-options="width:150,prompt: '确认单号'"/>
	    <input type="text" name="filter_LIKES_createrName" class="easyui-validatebox" data-options="width:150,prompt: '经办人'"/>
	    <input type="text" id="filter_EQS_receiveUnitName" name="filter_EQS_receiveUnitName" class="easyui-combobox" data-options="width:200,prompt: '收款单位'"/>
	    <input type="text" id="filter_EQS_payContent" name="filter_EQS_payContent" class="easyui-combobox" data-options="width:150,prompt: '付款内容'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:paymentConfirm:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:paymentConfirm:delete">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:paymentConfirm:update">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:paymentConfirm:detail">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="print()">打印</a>
    	<span class="toolbar-item dialog-tool-separator"></span>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-acrobat" plain="true" onclick="exportPdf()">导出pdf</a>
    	<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:paymentConfirm:apply">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:paymentConfirm:callBack">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:paymentConfirm:trace">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace();">流程跟踪</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
</div>
<table id="dg"
	data-options="
				rowStyler: function(index,row){
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>
<div id="dlg_view"></div>
<div id="dlg_trace"></div>
<div id="dlg_goods"></div>
<div id="searchGoodsCode">
<div id="dlgContract"></div>
<div id="dlg_print"></div>
</div>
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/financial/paymentConfirm/json', 
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
			{field:'confirmNo',title:'确认单号',sortable:true,width:20},
			{field:'payUnitView',title:'付款单位',sortable:true,width:20},
			{field:'payMoneyXiao',title:'金额',sortable:true,align:'right',width:15,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(isNaN(value)||value==null){
							val = 0.00; //如果不是数字，返回0.000
						}else{
							value = parseFloat(value);
							value = value.toFixed(2); 
							val = value;
						}
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'currencyView',title:'币种',sortable:true,align:'left',width:10},
			{field:'confirmDate',title:'确认日期',sortable:true,width:15},
			{field:'receiveUnitNameView',title:'收款单位',sortable:true,width:20}, 
			{field:'payContentView',title:'付款内容',sortable:true,width:20},
			{field:'createrName',title:'经办人',sortable:true,width:15},
			{field:'createDate',title:'创建时间',sortable:true,width:15},
			{field:'state',title:'流程状态',sortable:true,width:10,formatter:function(value,row,index){  
                return business_page.stateShow(value);}}
	    ]],
	    sortName:'id',
	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	//收款单位
	$('#filter_EQS_receiveUnitName').combobox({
		method: "get",
		url:'${ctx}/financial/paymentConfirm/getListNoYH',
		valueField : 'customerCode',
		textField : 'customerName',
	});
	
	$('#filter_EQS_payContent').combobox({
		url : '${ctx}/system/dictUtil/getDictByCode/paymentContent',
		valueField : 'code',
		textField : 'name',
	});
});
//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加审核',   
	    fit:true,
	    href:'${ctx}/financial/paymentConfirm/create',
	    modal:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("create");
			}
		},{
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
		$.messager.alert('提示','表单流程已发起，不能删除！','info');
		return;
	}
	if(row.state == "变更"){
		$.messager.alert('提示','表单变更状态，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/paymentConfirm/delete/"+row.id,
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
	if(row.state != "2"){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改审核',    
	    fit:true,   
	    closable:false,
	    style:{borderWidth:0},
	    href:'${ctx}/financial/paymentConfirm/update/'+row.id,
	    modal:true,
	    buttons:[{
			text:'提交申请', iconCls:'icon-hamburg-up',
			handler:function(){
				applyAnd("update");
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

//查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '付款明细',  
	    fit:true,
// 	    width: 900,    
// 	    height: 370, 
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/financial/paymentConfirm/detail/'+row.id,
	    modal:true,
	    buttons:[
				
	             {
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						d.panel('close');
					}
				}
		 ]
	});
}

//打印
function print(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var dlg_print=$("#dlg_print").dialog({   
	    title: '打印',  
	    width: 1000,    
	    height: 600, 
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/financial/paymentConfirm/print/'+row.id,
	    modal:true,
	    buttons:[
				{
					text:'打印',iconCls:'icon-hamburg-docs',
					handler:function(){
						$('#printDiv').jqprint();
					}
				},
	             {
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						dlg_print.panel('close');
					}
				}
		 ]
	});
}

//导出pdf
function exportPdf(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var url = "${ctx}/financial/paymentConfirm/exportPdf/" + row.id;
	window.location.href = url;
}

//撤回
function callBack(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在撤回申请！','info');
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
				url:"${ctx}/financial/paymentConfirm/callBack/"+row.id+"/"+row.processInstanceId,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "已成功撤回申请！", position: "bottomRight" });
					}
					parent.$.messager.progress('close');
				}
			});
		}
	});
}
//申请
function apply(){
	business_page.procStartUrl="${ctx}/financial/paymentConfirm/apply";
	business_page.apply(dg);
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

//提交表单并提交申请
function applyAnd(){
	business_page.procStartUrl="${ctx}/financial/paymentConfirm/apply";
	business_page.saveAndApply(dg,d,"mainform");
}

</script>
</body>
</html>