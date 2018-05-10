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
<link  href="${ctx}/static/cbp-folder/css/component.css" rel="stylesheet" >
<script src="${ctx}/static/cbp-folder/js/jquery.cbpNTAccordion.js"></script>
<script src="${ctx}/static/cbp-folder/js/modernizr.custom.js"></script>
</head>
<body>
	<div id="tb" style="padding:5px;height:auto">
	    <div>
	    	<form id="searchFrom" action="">
		        <input type="text" name="filter_LIKES_shipNo" class="easyui-validatebox" data-options="width:150,prompt: '船编号'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="sf_search" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
		        <a id="sf_reset" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
			</form>
		   	<shiro:hasPermission name="logistic:shipTrace:add">
		   		<a id="ssl_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="logistic:shipTrace:delete">
		        <a id="ssl_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="logistic:shipTrace:update">
		        <a id="ssl_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="logistic:shipTrace:view">
		        <a id="ssl_detail" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看明细</a>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="logistic:shipTrace:businessPermission">
				<a id="ssl_permission"  class="easyui-linkbutton" iconCls="icon-search" plain="true" >相关人员配置</a>
			</shiro:hasPermission>
			<a id="ship_relation" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">船舶相关单据</a>
	    </div>
	</div>
	<table id="dg"></table>  <!-- 首页到单列表  -->
	<div id="dlg"></div>  <!-- 到单页面弹窗  -->
	<div id="dlgContract"></div>
	<div id="dlg_selectUsers"></div> 
<script type="text/javascript">
var logistic_shipTraces_page = {
		entity_list : '',
		listEntity:function(){
			this.entity_list=$('#dg').datagrid({
				method: "get",
			    url:'${ctx}/logistic/shipTrace/json', 
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
					{field:'shipNo',title:'运输编号',width:20},
					{field:'transportCategoryView',title:'运输方式',width:20},
					{field:'shipName',title:'运输工具名称',width:20},
					{field:'shipNameE',title:'英文名',width:20},
					{field:'innerContractNo',title:'内部合同号',sortable:true,width:35},
					{field:'loadPortView',title:'装货港口',width:20,
					},
					{field:'loadPortDate',title:'抵港时间',sortable:true,width:20,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd HH:mm");
		            	}},
	            	{field:'loadBeginDate',title:'开装时间',sortable:true,width:20,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd HH:mm");
		            	}}
			    ]],
			    sortName:'id',
			    sortOrder:'desc',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tb',
				onDblClickRow:function(rowIndex, rowData){
					logistic_shipTraces_page.detailEntity();
				}
			});
		},
		entity_form:'',
		addEntity:function(){
			logistic_shipTraces_page.entity_form=$("#dlg").dialog({   
			    title: '新增',    
			    fit:true,    
			    href:'${ctx}/logistic/shipTrace/create',
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$("#mainform_shipTrace").submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						logistic_shipTraces_page.entity_form.panel('close');
					}
				}]
			});
		},
		updateEntity:function(){
			var row = logistic_shipTraces_page.entity_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			logistic_shipTraces_page.entity_form=$("#dlg").dialog({   
			    title: '修改',    
			    fit:true,  
			    href:'${ctx}/logistic/shipTrace/update/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#mainform_shipTrace').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						logistic_shipTraces_page.entity_form.panel('close');
					}
				}]
			});
		},
		deleteEntity:function(){
			var row = logistic_shipTraces_page.entity_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:"${ctx}/logistic/shipTrace/delete/"+row.id,
						success: function(data){
							successTipNew(data,logistic_shipTraces_page.entity_list);
						}
					});
				} 
			});
		},
		entity_detail:'',
		detailEntity:function(){
			var row = logistic_shipTraces_page.entity_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			logistic_shipTraces_page.entity_detail=$("#dlg").dialog({   
			    title: '明细',    
			    fit : true,    
			    href:'${ctx}/logistic/shipTrace/detail/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						logistic_shipTraces_page.entity_detail.panel('close');
					}
				}]
			});
		},
		setBusenessPermission:function (){
			var row = logistic_shipTraces_page.entity_list.datagrid('getSelected');
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
							url : '${ctx}/permission/businessPerssion/updatePermission4ShipTrace/'+row.id,
							type : "get",
							data:{
								"themeMemberIds":	themeMemberIds,
							},
							success : function(data) {}
							});
						selectUsers_dg.panel('close');
						logistic_shipTraces_page.entity_list.datagrid('reload');
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						selectUsers_dg.panel('close');
					}
				}]
			});
		}
};
$(function(){
	logistic_shipTraces_page.listEntity();
	//查询
	$("#sf_search").on("click", function(){
		var obj=$("#searchFrom").serializeObject();
		logistic_shipTraces_page.entity_list.datagrid('reload',obj);
	});
	//重置
	$("#sf_reset").on("click", function(){
		$("#searchFrom")[0].reset();
		var obj=$("#searchFrom").serializeObject();
		logistic_shipTraces_page.entity_list.datagrid('reload',obj);
	});
	//增加
	$("#ssl_add").on("click", logistic_shipTraces_page.addEntity);
	//删除
	$("#ssl_del").on("click", logistic_shipTraces_page.deleteEntity);
	//修改
	$("#ssl_upd").on("click", logistic_shipTraces_page.updateEntity);
	//查看明细
	$("#ssl_detail").on("click", logistic_shipTraces_page.detailEntity);
	//相关人员设置
	$("#ssl_permission").on("click", logistic_shipTraces_page.setBusenessPermission);
});
//弹窗加载采购合同列表
function toPurchaseList(){
	purchase=$("#dlgContract").dialog({   
	    title: '采购合同',    
	    width: 700,    
	    height: 350,  
	    href:'${ctx}/logistic/shipTrace/toPurchaseList',
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				saveForShipTrace();
				purchase.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				purchase.panel('close');
			}
		}]
	});
}

$("#ship_relation").on("click", function(){
	var row = logistic_shipTraces_page.entity_list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: "船<font style='color:#F00'>"+row.shipNo+row.shipName +"</font>船舶动态",    
	    fit : true,    
	    href:'${ctx}/logistic/shipTrace/shipRelation/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
});
</script>
</body>
</html>