<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>  
<head>  
<title></title>  
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>  
  
<body>
<div>
	<form id="uploadForm" action="${ctx}/process/uploadProc" method="post" enctype="multipart/form-data">
		<table width="98%" class="tableClass">
			<tr>
				<th width="25%"><label class="control-label">流程文件：</label></th>
				<td>
					<input type="file" id="file" name="file" class="required"/>
					<span class="help-inline">支持文件格式：zip、bar、bpmn、bpmn20.xml</span>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg"></div> 
<script type="text/javascript">
		$(function() {
			$('#uploadForm').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTip(data, dg, d);
				}
			});
		});
	</script> 
</body>  
</html>  