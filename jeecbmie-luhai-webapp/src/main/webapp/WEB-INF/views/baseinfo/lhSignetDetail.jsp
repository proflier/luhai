<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
		<form id="mainform"  action="${ctx}/baseInfo/signet/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品类别信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品类别信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">印章类型</th>
						<td width="30%">
							${fns:getDictLabelByCode(signet.typeCode,'SIGNETTYPE','')}
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							${signet.signetCode }
						</td>
					</tr>
					<tr>
						<th>所属公司</th>
						<td id="orgId">
							
						</td>
						<th>印章保管人</th>
						<td id="saveUserId">
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td colspan="3">
							${signet.status == 0?'停用':'启用'}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>经办人</th>
						<td>${signet.createrName }</td>
						<th>经办部门</th>
						<td>${signet.createrDept }</td>
						<th>登记时间</th>
						<td>
							<fmt:formatDate value="${signet.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : '${ctx}/baseInfo/baseUtil/orgNameShow?orgId=${signet.orgId }' ,
			type : 'get',
			cache : false,
			async: true,
			success : function(data) {
				$("#orgId").html(data);
			}
		});
		$.ajax({
			url : '${ctx}/baseInfo/baseUtil/userNameShow?userId=${signet.saveUserId }' ,
			type : 'get',
			cache : false,
			async: true,
			success : function(data) {
				$("#saveUserId").html(data);
			}
		});
	});
	
</script>

</body>
</html>