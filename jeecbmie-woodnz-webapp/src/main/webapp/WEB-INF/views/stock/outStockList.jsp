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
       	        <input type="text" name="filter_LIKES_outboundNo" class="easyui-validatebox" data-options="width:150,prompt: '出库单号'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
			
	       	<shiro:hasPermission name="sys:woodOutStock:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodOutStock:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodOutStock:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodOutStock:view">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
	         <shiro:hasPermission name="sys:woodOutStock:confirm">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="confirm()">确认</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission> 
		    <shiro:hasPermission name="sys:woodOutStock:cancleConfirm">
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
			"></table> 
<div id="dlg"></div>  
<div id="dlgSendGoods"></div>  <!-- 选择列表弹窗 -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/stock/outStock/json', 
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
				{field:'outboundNo',title:'出库单号',sortable:true,width:10}, 
				{field:'billDate',title:'客户签收日期',sortable:true,width:10,
					formatter:function(value,row,index){  
						if(value==null){
							return ;
						}
	                    var unixTimestamp = new Date(value);  
	                    return unixTimestamp.format("yyyy-MM-dd");  
	                    }  
	             },
				{field:'goodsUnit',title:'提货单位',sortable:true,width:10,
	            	 formatter: function(value,row,index){
	 					var val;
	 					if(row.goodsUnit!=''&&row.goodsUnit!=null){
	 						$.ajax({
	 							type:'GET',
	 							async: false,
	 							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.goodsUnit,
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
				{field:'payAmount',title:'货款',sortable:true,width:10},
				{field:'realSendNum',title:'实发立方数',sortable:true,width:10},
				{field:'confirm',title:'确认状态',sortable:true,width:10,
					formatter:function(value,row,index){  
						if(value==1)
							return '已确认';
						else
							return '未确认';
					} }
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
	    title: '新增出库',    
	    fit:true,    
	    href:'${ctx}/stock/outStock/create',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				endEditing();
				accept();
				computeRealSendNum();//计算实发立方数和
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
		$.messager.alert('提示','出库已经确认，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/outStock/delete/"+row.id,
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
		$.messager.alert('提示','出库已经确认，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({   
	    title: '修改出库',    
	    fit:true,    
	    href:'${ctx}/stock/outStock/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				endEditing();
				computeRealSendNum();//计算实发立方数和
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
	    title: '出库明细',    
	    fit:true,  
	    href:'${ctx}/stock/outStock/detail/'+row.id,
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

//选择列表弹框
function toSendGoods(){
	d_sendGoods=$("#dlgSendGoods").dialog({   
	    title: '放货和下游交单列表',    
	    width: 700,    
	    height: 417,  
	    href:'${ctx}/stock/outStock/toSendGoods/',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				setGoodsInfo();
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_sendGoods.panel('close');
			}
		}]
	});
}

//到单确认
function confirm(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.confirm == "1"){
		$.messager.alert('提示','出库已经确认，不能重复确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '确认出库？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/outStock/confirm/"+row.id,
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
		$.messager.alert('提示','出库未确认，不能取消确认！','info');
		return;
	}
	parent.$.messager.confirm('提示', '取消确认出库？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/outStock/cancleConfirm/"+row.id,
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
