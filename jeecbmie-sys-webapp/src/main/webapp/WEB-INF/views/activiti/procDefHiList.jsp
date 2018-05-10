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
	<legend>历史记录</legend>
		<table id="trace_task"></table>
	</fieldset>	
	<fieldset class="fieldsetClass" >
	<legend>流程图</legend>
		<img id="tracePhoto" style="-webkit-user-select: none" src="${ctx}/process/history/img/${procDefId}"></img>
	</fieldset>
	<script type="text/javascript">
		(function($) {
			$('#trace_task').datagrid({
				method : "get",
				url : '${ctx}/process/history/list/${procKey}',
				fitColumns : true,
				border : true,
				idField : 'id',
				pagination : true,
				rownumbers : true,
				singleSelect : true,
				pageNumber : 1,
				pageSize : 5,
				pageList : [ 5, 10],
				striped : true,
				columns : [ [ 
					{field:'id',title:'id',hidden:true},  
					{field:'key',title:'KEY',sortable:false,width:20},
					{field:'name',title:'名称',sortable:false,width:20},
					{field:'version',title:'版本',sortable:false,width:20},
					{field:'deployTime',title:'部署时间',sortable:false,width:20},
					{field:'active',title:'是否激活',hidden:true	}   
				             ] ],
				enableHeaderClickMenu : false,
				enableHeaderContextMenu : false,
				enableRowContextMenu : false,
				onClickRow:function(index,row){
					$("#tracePhoto").attr("src","${ctx}/process/history/img/"+row.id);
				},
				remoteSort : false
			});
		})(jQuery);
	</script>
</body>
</html>
