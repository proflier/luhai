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
		<div>
			<div id="businessInfo"/>
		</div>
	</div>
	<script type="text/javascript">
		$('#businessInfo').panel({
			border: false,
			href: '${ctx}/${approvalOpinion.businessInfoReturnUrl}/${approvalOpinion.businessKey}'
		});
		
		var dlg_h = $('#dlg').panel('panel').outerHeight();
		var dlg_w = $('#dlg').panel('panel').outerWidth();
		$('#businessInfo').panel('resize',{	width:dlg_w-5,height: dlg_h-50});
	</script>
</body>
</html>