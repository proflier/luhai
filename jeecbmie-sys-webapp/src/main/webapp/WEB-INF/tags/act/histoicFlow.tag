<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="procInsId" type="java.lang.String" required="true" description="流程实例ID"%>
<%@ attribute name="startAct" type="java.lang.String" required="false" description="开始活动节点名称"%>
<%@ attribute name="endAct" type="java.lang.String" required="false" description="结束活动节点名称"%>

<fieldset class="fieldsetClass">
	<legend>流转信息</legend>
	<div style="height:200px">
		<table id="histoicFlowList" class="easyui-datagrid" title="审核记录"></table>
	</div>
</fieldset>

<script type="text/javascript">
	$(function () {
		$('#histoicFlowList').datagrid({
			method: "get",
		    url:'/woodNZ/workflow/trace/list/${procInsId}', 
		    fit : true,
			fitColumns : true,
			border : true,
			idField : 'id',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			pageNumber:1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			striped:true,
		    columns:[[
				{field:'id',title:'id',hidden:true},  
				{field:'name',title:'审批节点',sortable:true,width:50},
		        {field:'assignee',title:'处理人 ',sortable:true,width:50},
		        {field:'startTime',title:'任务开始时间',sortable:true},
		        {field:'endTime',title:'任务结束时间',sortable:true},
		        {field:'comments',title:'审批意见',sortable:true,width:100}
		    ]],
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    remoteSort:false
		});
	});
</script>
