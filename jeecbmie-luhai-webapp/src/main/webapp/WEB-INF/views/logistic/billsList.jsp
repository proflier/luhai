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
		        <input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="lh:bills:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="lh:bills:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:bills:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="lh:bills:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>     
	        <shiro:hasPermission name="lh:bills:confirm">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="confirm()">确认</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission> 
		    <shiro:hasPermission name="lh:bills:review">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="review()">复核</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="lh:bills:cancleConfirm">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancleConfirm()">取消确认</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		     <shiro:hasPermission name="lh:bills:cancelReview">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelReview()">取消复核</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>     
        </div> 
        
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.confirm == '1'){
						if (row.review == '1'){
							return 'color:#00DB00;font-style:italic;';
						}else{
							return 'color:#0072E3;font-style:italic;';
						}
					}
				}
			"></table>  <!-- 首页到单列表  -->
<div id="dlg"></div>  <!-- 到单页面弹窗  -->
<div id="dlgPurchase"></div> <!-- 采购合同列表弹窗  -->
<div id="dlg_goods"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/logistic/bills/json', 
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
			{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
			{field:'shipNoView',title:'船编号',sortable:true,width:20},
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:20},
			{field:'confirm',title:'确认状态',sortable:true,width:10,
				formatter:function(value,row,index){  
					if(value==1)
						return '已确认';
					else
						return '未确认';
				}},
			{field:'review',title:'复核状态',sortable:true,width:10,
				formatter:function(value,row,index){  
					if(value=='1')
						return '已复核';
					else
						return '未复核';
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
	    title: '新增到单',    
	    fit:true,    
	    href:'${ctx}/logistic/bills/create',
	    maximizable:false,
	    modal:true,
	    buttons:[{
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
	if(row.confirm == "1"){
		$.messager.alert('提示','到单已经确认，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/bills/delete/"+row.id,
				success: function(data){
					successTipNew(data,dg);
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
		$.messager.alert('提示','到单已经确认，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改到单',    
	    fit:true,  
	    href:'${ctx}/logistic/bills/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
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
	    title: '到单明细',    
	    fit : true,    
	    href:'${ctx}/logistic/bills/detail/'+row.id,
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
	cx();
}

//弹窗加载采购合同列表
function toPurchaseList(){
	purchase=$("#dlgPurchase").dialog({   
	    title: '采购合同',    
	    width: 750,    
	    height: 350,  
	    href:'${ctx}/logistic/bills/toPurchaseList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				setGoods();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				purchase.panel('close');
			}
		}]
	});
}


//到单确认
function confirm(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm == "1"){
		$.messager.alert('提示','到单已经确认，不能重复确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '确认到单？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/bills/confirm/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}
//到单复核
function review(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm != "1"){
		$.messager.alert('提示','到单未确认，不能复核！','info');
		return;
	}
	parent.$.messager.confirm('提示', '到单复核？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/bills/review/"+row.id,
				success: function(data){
					successTip(data,dg);
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
		$.messager.alert('提示','到单未确认，不能取消确认！','info');
		return;
	}
	if(row.review == "1"){
		$.messager.alert('提示','到单已复核，不能取消确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '取消确认到单？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/bills/cancleConfirm/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}
//取消复核
function cancelReview(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.review != "1"){
		$.messager.alert('提示','到单未复核，不能取消！','info');
		return;
	}
	parent.$.messager.confirm('提示', '取消到单复核？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistic/bills/cancelReview/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}
</script>
</body>
</html>