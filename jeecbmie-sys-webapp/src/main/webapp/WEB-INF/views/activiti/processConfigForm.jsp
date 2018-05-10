<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/activiti/config/${action}" method="post">
	<fieldset class="fieldsetClass">
	<legend>配置信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th>业务名称</th>
				<td >
					<input type="hidden" id="id" name="id" value="${processConfig.id }"/>
					<input type="text" id="processName" name="processName" value="${processConfig.processName }" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>业务对应实体</th>
				<td >
					<input type="text" id="entityView" name="entityView" value="${processConfig.entityView }" class="easyui-validatebox" style="width:90%;" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>关键字段</th>
				<td >
					<input type="text" id="keyWord" name="keyWord" value="${processConfig.keyWord }" class="easyui-validatebox"  data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td >
					<textarea rows="2" cols="30" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,46]']">${processConfig.comment }</textarea>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTipNew(data,dg);
			$.easyui.loaded();
	    }
	});
	
});
</script>
</body>
</html>