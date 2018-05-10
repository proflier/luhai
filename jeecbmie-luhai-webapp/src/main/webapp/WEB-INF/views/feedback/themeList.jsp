<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<style type="text/css">
	.divFrame {
		margin-top:5px;
		margin-left:2%;
		width: 30%;
		height:270px;
		display: block;
		float: left;
		overflow:auto;
		border:1px solid #95B8E7
	}
	#userShowDiv li {
		width:120px;
		list-style-type:none;
	}
	#userShowDiv li a{
		margin-left: 5px;
		margin-right: 5px;
	}
	#userShowDiv li span.keyperson{
		line-height: 0;
		width: 16px;
		height: 16px;
		display: inline-block;
		vertical-align: middle;
		border: 0 none;
		cursor: pointer;
		background-color: transparent;
		background-repeat: no-repeat;
		background-attachment: scroll;
		background-image: url("${ctx}/static/plugins/ztree/css/zTreeStyle/img/zTreeStandard.png");
		background-position: -126px -64px;
	}
	#userShowDiv li span.nokeyperson{
		line-height: 0;
		width: 16px;
		height: 16px;
		display: inline-block;
		vertical-align: middle;
		border: 0 none;
		cursor: pointer;
		background-color: transparent;
		background-repeat: no-repeat;
		background-attachment: scroll;
		background-image: url("${ctx}/static/plugins/ztree/css/zTreeStyle/img/zTreeStandard.png");
		background-position: -126px -80px;
	}
	#userShowDiv li span.del{
		line-height: 0;
		width: 15px;
		height: 15px;
		display: inline-block;
		vertical-align: middle;
		border: 0 none;
		cursor: pointer;
		background-color: transparent;
		background-repeat: no-repeat;
		background-attachment: scroll;
		background-image: url("${ctx}/static/plugins/ztree/css/zTreeStyle/img/zTreeStandard.png");
		background-position: -110px -64px;
	}
	#userShowDiv li span.content{
		width: 60px;
		display: inline-block;
	}
</style>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
        	 	<input type="text" name="filter_LIKES_title" class="easyui-validatebox" data-options="width:150,prompt: '主题'"/>
        	 	<input type="text" id="filter_EQS_classification" name="filter_EQS_classification" class="easyui-combobox" data-options="width:150,prompt: '反馈分类'"/>
        	 	<input type="text" id="filter_EQS_types" name="filter_EQS_types" class="easyui-combobox" data-options="width:150,prompt: '反馈类型'"/>
        	 	<input type="hidden" name="filter_EQS_userId" value="${curUserId}"/>
        	 	<input type="hidden" name="filter_EQI_dutyUser" value="${curUserId}"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="feedback:theme:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="feedback:theme:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	       	<shiro:hasPermission name="feedback:theme:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="feedback:theme:detail">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="feedback:theme:end">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="closeFeedback()">结束主题</a>
	        </shiro:hasPermission>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg" ></div>
<div id="dlg_rel"></div> 
<div id="dlg_selectUsers"></div> 
<script type="text/javascript">
var themeObj = {};
$(function(){   
	themeObj.list=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/feedback/theme/json', 
	    fit : true,
	      queryParams: {
	    	filter_EQS_userId_OR_dutyUser: '${curUserId}',
		},  
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 5,10,15,20 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'title',title:'主题',width:20}, 
			{field:'classification',title:'反馈分类',width:20,
				formatter: function(value,row,index){
      				var val;
      				if(value!=''&&value!=null){
      					$.ajax({
      						type:'GET',
      						async: false,
      						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackClassification/"+value,
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
			{field:'types',title:'反馈类型',width:20,
      				formatter: function(value,row,index){
          				var val;
          				if(value!=''&&value!=null){
          					$.ajax({
          						type:'GET',
          						async: false,
          						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackTypes/"+value,
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
			{field:'createDate',title:'创建日期',width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
           	{field:'state',title:'状态',width:20,
     				formatter: function(value,row,index){
         				var val;
         				if(value!=''&&value!=null){
         					$.ajax({
         						type:'GET',
         						async: false,
         						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackState/"+value,
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
	
	$('#filter_EQS_classification').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/feedbackClassification',
	    valueField:'code',
	    textField:'name', 
	});
	
	$('#filter_EQS_types').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/feedbackTypes',
	    valueField:'code',
	    textField:'name', 
	});
	
});

//弹窗增加
function add() {
	themeObj.form=$("#dlg").dialog({   
	    title: '新增主题',    
	    href:'${ctx}/feedback/theme/create',
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				themeObj.form.panel('close');
			}
		}]
	});
}

//删除
function del(){
	var row = themeObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复，您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/feedback/theme/delete/"+row.id,
				success: function(data){
					successTipNew(data,themeObj.list);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = themeObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state == "10990003"){
		$.messager.alert('提示','反馈主题已结束，不能修改！','info');
		return;
	}
	themeObj.form=$("#dlg").dialog({   
	    title: '修改主题',    
	    href:'${ctx}/feedback/theme/update/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$('#mainform').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				themeObj.form.panel('close');
			}
		}]
	});
}

//查看明细
function detail(){
	var row = themeObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	themeObj.form=$("#dlg").dialog({   
	    title: '讨论组明细',    
	    href:'${ctx}/feedback/theme/detail/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				themeObj.form.panel('close');
			}
		}]
	});
}
function closeFeedback(){
	var row = themeObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state == "10990003"){
		$.messager.alert('提示','反馈主题已结束，不能重复操作！','info');
		return;
	}
	parent.$.messager.confirm('提示', '确认结束反馈主题？', function(data){
		if (data){
			$.ajax({
				type:'get',
				async:false,
				datatype:'json',
				url:"${ctx}/feedback/content/checkKey/"+row.id,
				success: function(data){
					 if(data=="1"){
						$.ajax({
							type:'get',
							async:false,
							url:"${ctx}/feedback/theme/end/"+row.id,
							success: function(data){
								if(data){
									successTip(data,themeObj.list);
								}
							}
						});
					}else{
						$.messager.alert('提示','反馈主题关键人员未发言，不能结束！','info');
					} 
				}
			});
		} 
	});
}
//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	themeObj.list.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	cx(); 
}
function selectDiscussers(){
	var themeId = $("#feedbackThemeId").val();
	if(themeId==""){
		themeId=0;
	}
	var themeMemberIds = $("#mainform #themeMemberIds").val();
	var themeMemberKeyIds = $("#mainform #themeMemberKeyIds").val();
	var themeMembers = $("#mainform #themeMembers").text();
	var themeMemberKeys = $("#mainform #themeMemberKeys").text();
	//todo
	var selectUsers_dg=$("#dlg_selectUsers").dialog({   
	    title: '人员选择',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/feedback/theme/toSelectUsers/'+themeId,
	    modal:true,
	    queryParams:{'themeMemberIds':themeMemberIds,'themeMemberKeyIds':themeMemberKeyIds,'themeMembers':themeMembers,'themeMemberKeys':themeMemberKeys},
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				users_show.saveSelect();
				selectUsers_dg.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				selectUsers_dg.panel('close');
			}
		}]
	});
}
</script>
</body>
</html>