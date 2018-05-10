<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainformD" action="${ctx}/baseinfo/port/${action}" method="get">
	<table width="98%" class="tableClass">
		<tr>
			<th width="20%">单位编号</th>
			<td width="30%">${woodCk.no }</td>
			<th width="20%">财务编号</th>
			<td width="30%">${woodCk.cwbm }</td>
		</tr>
		<tr>
			<th width="25%">单位名称</th>
			<td colspan="3">${woodCk.companyName }</td>
		</tr>
		<tr>
			<th width="25%">单位简称</th>
			<td>${woodCk.shortName }</td>
			<th width="25%">国籍</th>
			<td id="nationality"></td>
		</tr>
		<tr>
			<th width="25%">地址</th>
			<td colspan="3">${woodCk.addr }</td>
		</tr>
		<tr>
			<th width="25%">所属仓储公司</th>
			<td id="belongCompany"></td>
			<th width="25%">仓储费结算方案</th>
			<td>${woodCk.balanceType }</td>
		</tr>
		<tr>
			<th width="25%">仓库类别</th>
			<td>${woodCk.cklb }</td>
			<th width="25%">联系人</th>
			<td>${woodCk.lxr }</td>
		</tr>
		<tr>
			<th width="25%">电话</th>
			<td>${woodCk.tel }</td>
			<th width="25%">传真</th>
			<td>${woodCk.fax }</td>
		</tr>
		<tr>
			<th width="25%">企业性质</th>
			<td>${woodCk.property }</td>
			<th width="25%">法人</th>
			<td>${woodCk.corporate }</td>
		</tr>
		<tr>
			<th width="25%">仓库所在地区</th>
			<td colspan="3">${woodCk.area }</td>
		</tr>
		<tr>
			<th >备注</th>
			<td colspan="3" style="height:1cm">${woodCk.remark }</textarea></td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>
				${woodCk.booker }
			</td>
			<th>登记部门</th>
			<td>
				${woodCk.registDept }
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
// 	$.ajax({
// 		url : '${ctx}/baseinfo/affiUtil/getDictName/khlx/${woodCk.belongCompany}' ,
// 		type : 'get',
// 		cache : false,
// 		success : function(data) {
// 			$('#belongCompany').html(data);
// 		}
// 	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${woodCk.belongCompany}',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#belongCompany').text(data);
		}
	});
	
	$.ajax({
		url : '${ctx}/system/countryArea/getName/' + ${woodCk.nationality },
		type : 'get',
		cache : false,
		success : function(data) {
			$('#nationality').html(data);
		}
	});
</script>
</body>
</html>