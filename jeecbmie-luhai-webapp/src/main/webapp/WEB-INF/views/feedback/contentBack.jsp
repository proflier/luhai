<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!--引入引入kindeditor编辑器相关文件--> 
<link rel="stylesheet" href="${ctx}/static/plugins/kindeditor/themes/default/default.css" />
<script src="${ctx}/static/plugins/kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/kindeditor/lang/zh-CN.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<%-- <form id="searchFrom" action="">
        	 	<input type="text" name="filter_LIKES_title" class="easyui-validatebox" data-options="width:150,prompt: '主题'"/>
        	 	<input type="hidden" name="filter_EQI_userId" value="${curUserId}"/>
        	 	<input type="hidden" name="filter_EQI_dutyUser" value="${curUserId}"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form> --%>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="advice()">进入主题</a>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg" ></div>
<div id="dlg_rel"></div>  
<script type="text/javascript">
var themeObj = {};
$(function(){   
	themeObj.list=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/feedback/theme/list', 
	    fit : true,
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
      			}},
			{field:'createDate',title:'创建日期',width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
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

//弹窗修改
function advice(){
	var row = themeObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	themeObj.form=$("#dlg").dialog({   
	    title: '意见',    
	    href:'${ctx}/feedback/content/list/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				themeObj.form.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	themeObj.list.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	cx(); 
}
</script>
</body>
</html>