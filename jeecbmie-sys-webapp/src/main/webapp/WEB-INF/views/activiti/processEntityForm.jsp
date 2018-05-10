<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/model/set" method="post">
			<table width="98%" class="tableClass">
				<tr>
					<th width="25%"><font color="red">*</font>流程key</th>
					<td>
						${procEntity.procDefKey}
						<input type="hidden" name="id" value="${procEntity.id}"/>
						<input type="hidden" name="modeId" value="${procEntity.modeId}"/>
						<input type="hidden" name="procDefKey" id="procDefKey"  value="${procEntity.procDefKey}" />
					</td>
				</tr>
				<tr>
					<th><font color="red">*</font>实体名称(全写)</th>
					<td><input name="clazzFullname" id="clazzFullname" class="easyui-validatebox" value="${procEntity.clazzFullname}"
						data-options="width: 450,required:true,missingMessage:'实体名称必填'" /></td>
				</tr>
				<tr>
					<th><font color="red">*</font>视图(form和view逗号分隔)</th>
					<td>
						<input name="entityView" id="entityView" class="easyui-validatebox" value="${procEntity.entityView}"
						data-options="width: 450,required:true,missingMessage:'视图必填'" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#mainform').form({
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