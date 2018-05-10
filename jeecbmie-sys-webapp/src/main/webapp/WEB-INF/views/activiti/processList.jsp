<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>  
<head>  
<title></title>  
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>  
  
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<shiro:hasPermission name="sys:process:active">
        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="updateProc('active');">激活</a>
        	</shiro:hasPermission>
        	<shiro:hasPermission name="sys:process:suspend">
		       	<span class="toolbar-item dialog-tool-separator"></span>
		       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="updateProc('suspend');">挂起</a>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:process:output">
		       	<span class="toolbar-item dialog-tool-separator"></span>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportProcImage()">历史</a>
        	</shiro:hasPermission>
        	<shiro:hasPermission name="sys:process:input">
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="uploadProc()">导入流程</a>
        	</shiro:hasPermission>
        	<shiro:hasPermission name="sys:process:convert">
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="convertProc()">转换为模型</a>
        	</shiro:hasPermission>
        	<shiro:hasPermission name="sys:process:delete">
        	<span class="toolbar-item dialog-tool-separator"></span>
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteProc();">删除</a>
	       	</shiro:hasPermission>
        </div> 
</div>
<table id="dg"></table>
<div id="dlg"></div>
<div id="history"></div> 
<script type="text/javascript">  
var dg;
$(function(){
	dg=$('#dg').datagrid({    
	method: "get",
    url:'${ctx}/process/list', 
    fit : true,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[    
		{field:'id',title:'id',hidden:true},  
		{field:'key',title:'KEY',sortable:true,width:20},
		{field:'name',title:'名称',sortable:true,width:20},
        {field:'version',title:'版本',sortable:true,width:20},
        {field:'deployTime',title:'部署时间',sortable:true,width:20},
        {field:'desc',title:'描述',width:20},
        {field:'active',title:'是否激活',width:20,
        	formatter: function(value,row,index){
				var val='';
				if(value=="1"){
						val = "是";
					}else if(value="0"){
						val = "否";
					}else{
					}
					return val;
			}
        }
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tb'
});
});
function exportProcImage(){  
    var row = dg.datagrid('getSelected'); 
    if(rowIsNull(row)) return;
    var tDialog=$("#history").dialog({   
	    title: '历史版本',
	    width: 1000,    
	    height: 600,    
	    href:"${ctx}/process/history?procDefId="+row.id+"&procKey="+row.key,
	    maximizable:true,
	    resizable:true,
	    modal:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				tDialog.panel('close');
				}
		}]
	});
}
function updateProc(changeType){
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)) return;
    var tip = "";
    var url = "";
	if("suspend" == changeType){
		tip = "您确定要挂起流程吗？";
		url = '${ctx}/process/update/suspend';
	}else if("active" == changeType){
		tip = "您确定要激活流程吗？";
		url = '${ctx}/process/update/active';
	}else{
		return;
	}
    parent.$.messager.confirm('提示', tip, function(data){
    	if (data){
    		parent.$.messager.progress({  
	            title : '提示',  
	            text : '处理过程中，请稍后....'  
	        });
		    $.post(url, {procDefId:row.id}, function(j) {  
		        if (j.success) {  
		            dg.datagrid('load');  
		        }
		        dg.datagrid('uncheckAll');  
		        parent.$.messager.progress('close');
		        $.messager.show({  
		            title : '提示',  
		            msg : j.msg, 
		            position: "bottomRight"
		        });  
		    }, 'json');
    	}
    });
}
//转换
function convertProc(){
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)) return;
    var tip = "";
    var url = "";
    parent.$.messager.confirm('提示', "您确定转换为模型吗?", function(data){
    	if (data){
    		parent.$.messager.progress({  
	            title : '提示',  
	            text : '处理过程中，请稍后....'  
	        });
		    $.post('${ctx}/process/convert', {procDefId:row.id}, function(j) {  
		        if (j.success) {  
		            dg.datagrid('load');  
		        }
		        dg.datagrid('uncheckAll');  
		        parent.$.messager.progress('close');
		        $.messager.show({  
		            title : '提示',  
		            msg : j.msg, 
		            position: "bottomRight"
		        });  
		    }, 'json');
    	}
    });
}
//流程导入
function uploadProc() {
	d=$("#dlg").dialog({   
	    title: '流程上传',
	    method: "get",
	    width: 750,    
	    height: 250,    
	    href:'${ctx}/process/uploadProc',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$("#uploadForm").submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}
function deleteProc(){
	var row = dg.datagrid('getSelected');
    if(rowIsNull(row) || row.deploymentId==null) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
		    $.ajax({
		    	type:'get',
				async:false,
				url:'${ctx}/process/delete/'+row.deploymentId,
				success: function(data){
					if(data!=null && data=='success'){
						parent.$.messager.show({ title : "提示",msg: "删除成功！", position: "bottomRight" });
						dg.datagrid('reload');
					} 
				} 
		    });
		}
    });
}
</script>  
</body>  
</html>  