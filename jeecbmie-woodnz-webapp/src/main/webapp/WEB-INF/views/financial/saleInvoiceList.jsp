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
			
	       	<shiro:hasPermission name="sys:woodSaleInvoice:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodSaleInvoice:delete">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodSaleInvoice:update">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:woodSaleInvoice:detail">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg"></div>  
<div id="dlgSendGoods"></div>  <!-- 放货列表弹窗 -->
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/financial/saleInvoice/json', 
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
				{field:'title',title:'抬头',sortable:true,width:20}, 
				{field:'invoiceDate',title:'发票日期',sortable:true,width:20,
					formatter:function(value,row,index){  
						if(value==null){
							return ;
						}
	                    var unixTimestamp = new Date(value);  
	                    return unixTimestamp.format("yyyy-MM-dd");  
	                    }  
	             },
	            {field:'invoideNo',title:'发票号',sortable:true,width:20}, 
				{field:'contractNo',title:'合同号',sortable:true,width:20}, 
				{field:'srcPort',title:'装运港',sortable:true,width:20}, 
				{field:'destPort',title:'目的港',sortable:true,width:20},
				{field:'billNo',title:'提单号',sortable:true,width:20},
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
var dialogExtend = {
		open : function(open_target){
	    	$(window).on('resize',{'targ':open_target},this.range_auto_change);
		},
		close : function(){
			$(window).off('resize',this.range_auto_change);
		},
		range_auto_change : function(event){
			 if(event.data.targ){
					event.data.targ.dialog("resize");
				} 
		}
};
//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增发票',    
	    fit:true,    
	    href:'${ctx}/financial/saleInvoice/create',
	    maximizable:false,
	    modal:true,
	    onOpen:function(){
	    	dialogExtend.open($(this));
	    },
	    onClose:function(){
	    	dialogExtend.close();
	    },
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				endEditing();
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
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/financial/saleInvoice/delete/"+row.id,
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
	d=$("#dlg").dialog({   
	    title: '修改出库',    
	    fit:true,    
	    href:'${ctx}/financial/saleInvoice/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    onOpen:function(){
	    	dialogExtend.open($(this));
	    },
	    onClose:function(){
	    	dialogExtend.close();
	    },
	    buttons:[{
			text:'保存',iconCls:'icon-save',id:'button-save',
			handler:function(){
				endEditing();
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
	    title: '销售发票',    
	    fit:true,
	    onOpen:function(){
	    	dialogExtend.open($(this));
	    },
	    onClose:function(){
	    	dialogExtend.close();
	    },
	    href:'${ctx}/financial/saleInvoice/detail/'+row.id,
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

</script>
</body>
</html>
