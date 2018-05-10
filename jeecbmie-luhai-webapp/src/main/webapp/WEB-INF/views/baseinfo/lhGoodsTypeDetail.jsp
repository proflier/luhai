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
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品类别信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品类别信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">名称</th>
						<td width="30%">
							${goodsType.typeName }
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							${goodsType.typeCode }
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td colspan="3">
							${goodsType.status == 0?'停用':'启用'}
						</td>
					</tr>
					<tr>
						<th >备注</th>
						<td colspan="3">
							${goodsType.comments }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>经办人</th>
						<td>${goodsType.createrName }</td>
						<th  >经办部门</th>
						<td>${goodsType.createrDept }</td>
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${goodsType.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
</body>
</html>