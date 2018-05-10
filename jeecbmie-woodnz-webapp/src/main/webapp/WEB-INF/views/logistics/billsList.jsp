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
<!--        	        <input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '上游合同号'"/> -->
<!-- 		        <span class="toolbar-item dialog-tool-separator"></span> -->
		        <input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
<!-- 		        <input type="text" name="filter_LIKES_cpContractNo" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/> -->
<!-- 		        <span class="toolbar-item dialog-tool-separator"></span> -->
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="sys:woodBills:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodBills:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodBills:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodBills:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>     
	        <shiro:hasPermission name="sys:woodBills:confirm">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="confirm()">确认</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission> 
		    <shiro:hasPermission name="sys:woodBills:cancleConfirm">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancleConfirm()">取消确认</a>
		    </shiro:hasPermission>     
        </div> 
        
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.confirm == '1'){
						return 'color:#0072E3;font-style:italic;';
					}
				}
			"></table>  <!-- 首页到单列表  -->
<div id="dlg"></div>  <!-- 到单页面弹窗  -->
<div id="dlgPurchase"></div> <!-- 采购合同列表弹窗  -->
<div id="dlgBox"></div> <!-- 箱单弹窗  -->
<div id="search4GoodsCode"><!-- 查询商品编码弹窗  -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/logistics/bills/json', 
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
			{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
			{field:'shipName',title:'船名',sortable:true,width:20},
			{field:'voyage',title:'航次',sortable:true,width:20},
			{field:'numbers',title:'提单数量',sortable:true,width:20},
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
				}},
			{field:'confirm',title:'确认状态',sortable:true,width:10,
				formatter:function(value,row,index){  
					if(value==1)
						return '已确认';
					else
						return '未确认';
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
	    href:'${ctx}/logistics/bills/create',
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
				url:"${ctx}/logistics/bills/delete/"+row.id,
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
		$.messager.alert('提示','到单已经确认，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改到单',    
	    fit:true,  
	    href:'${ctx}/logistics/bills/update/'+row.id,
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
	    title: '到单明细',    
	    fit : true,    
	    href:'${ctx}/logistics/bills/detail/'+row.id,
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

//弹窗加载采购合同列表
function toPurchaseList(){
// 	if($("#id").val() == ''){
// 		parent.$.messager.alert('提示','请先保存主表信息!!!');
// 		return ;
// 	}
	purchase=$("#dlgPurchase").dialog({   
	    title: '采购合同',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/logistics/bills/toPurchaseList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
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


//弹窗增加箱单
function addBox() {
	dBox=$("#dlgBox").dialog({  
		method:'GET',
	    title: '新增箱单',    
	    width: 700,    
	    height: 400,     
	    href:'${ctx}/logistics/bills/createBox',
	    maximizable:false,
	    modal:true,
	    onBeforeOpen: function(){    
	    	$('#createCopyBox').hide();
 	    }, 
	    buttons:[{
	    	id : 'createCopyBox',    
			text:'复制',iconCls:'icon-save',
			handler:function(){
				$('#subId').val('');//箱单主表id为空，将会执行新增方法
				$('#createCopyBox').hide();
			}
		},{
	    	id : 'addBox',    
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$('#boxform').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dBox.panel('close');
			}
		}]
	});
}

//复制增加箱单
function addCopyBox() {
	var row = dgBox.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '确定复制此条箱单信息？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/logistics/bills/addCopyBox/'+row.id,
				success: function(data){
					successTip(data,dgBox);
				}
			});
		} 
	});
}

//删除箱单
function delBox(){
	var row = dgBox.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/logistics/bills/deleteBox/'+row.id,
				success: function(data){
					successTip(data,dgBox);
				}
			});
		} 
	});
}

//弹窗修改箱单
function updBox() {
	var row = dgBox.datagrid('getSelected');
	if(rowIsNull(row)) return;
	dBox=$("#dlgBox").dialog({   
	    title: '修改箱单',    
	    width: 700,    
	    height: 400,
	    href:'${ctx}/logistics/bills/updateBox/'+row.id,
	    maximizable:false,
	    modal:true,
	    onBeforeOpen: function(){    
	    	$('#updateCopyBox').show();
 	    }, 
	    buttons:[{
	    	id : 'updateCopyBox',    
			text:'复制',iconCls:'icon-save',
			handler:function(){
				$('#subId').val('');
				$('#updateCopyBox').hide();
			}
		},{
	    	id : 'updBox', 
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				$('#boxform').submit(); 
				if($('#subId').val()==''){}
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dBox.panel('close');
			}
		}]
	});
}

function easyRightDisplay() {
// 	alert("easyRightDisplay");
// 	$('div.datagrid-toolbar a').eq(0).hide();//隐藏第一个按钮
    //获取所有的toolbar按钮
    var button=$('div.datagrid-toolbar a'); 
    for (var i = 0; i < button.length; i++) {
        var toolbar = button[i];
        var id = toolbar.text;
//         alert(id+',');
        if (id == "addBox") {  //隐藏Id为add的按钮
            $('div.datagrid div.datagrid-toolbar a').eq(i).hide();
        }
//         if (id == "delete") {  //不隐藏id为delete的按钮
//             //button.eq(i).hide();
//         }
        //如果按钮都没权限，隐藏了可直接隐藏toolbar
        //$('div.datagrid div.datagrid-toolbar').hide();
    }
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
				url:"${ctx}/logistics/bills/confirm/"+row.id,
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
	parent.$.messager.confirm('提示', '取消确认到单？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/logistics/bills/cancleConfirm/"+row.id,
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