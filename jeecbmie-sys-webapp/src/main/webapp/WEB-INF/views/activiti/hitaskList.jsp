<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<fieldset class="fieldsetClass" >
	<legend>流程图</legend>
		<img id="tracePhoto" style="-webkit-user-select: none" src="${ctx}/workflow/trace/photo/${processDefinitionId}/${pid}"></img>
	</fieldset>
	
	<fieldset class="fieldsetClass" >
	<legend>办理记录</legend>
		<table id="trace_task" width="90%" class="tableClass" data-options="nowrap:false"></table>
	</fieldset>	
	
	<script type="text/javascript">
		(function($) {
			if('${trace}'=='true'){
				$("#traceButtonId").linkbutton('enable');
			}
			$('#trace_task').datagrid({
				method : "get",
				url : '${ctx}/workflow/trace/list/${pid}',
				fit : false,
				fitColumns : true,
				border : true,
				idField : 'id',
				pagination : true,
				rownumbers : true,
				singleSelect : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				striped : true,
				columns : [ [  {
					field : 'name',
					title : '审批节点',
					sortable : true,
					width : 60
				}, {
					field : 'assignee',
					title : '处理人 ',
					width : 50
				},{
					field : 'passUsers',
					title : '传阅人 ',
					width : 50,
					formatter: function(value,row,index){
						if (row.passUsers.length>10){
							return row.passUsers.substr(0,10)+"...";
						} else {
							return row.passUsers;
						}
					}
				}, {
					field : 'startTime',
					title : '任务开始时间',
					width : 90,
					sortable : true
				}, {
					field : 'endTime',
					title : '任务结束时间',
					width : 90,
					sortable : true
				}, {
					field : 'distanceTimes',
					title : '耗时',
					sortable : true
				}, {
					field : 'comments',
					title : '审批意见',
					sortable : true,
					width : 110
				} ] ],
				enableHeaderClickMenu : false,
				enableHeaderContextMenu : false,
				enableRowContextMenu : false,
				remoteSort : false
			});
		})(jQuery);
	</script>
</body>
</html>
