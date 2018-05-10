<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<style>
</style>
</head>

<body>
<input type="hidden" id="id" name="id" value="${qualityInspection.id }" />
<div class="easyui-tabs" data-options="border:false">
	<div data-options="title: '基本信息', iconCls: false, refreshable: false" >
		<fieldset class="fieldsetClass" >
		<legend>基本信息</legend>
		<table width="98%" class="tableClass" >
			<tr>
				<th width="20%">质检编码</th>
				<td width="30%">
					${qualityInspection.inspectionNo }
				</td>
				<th width="20%">证书编号</th>
				<td width="30%">
					${qualityInspection.certificateNo }
				</td>
			</tr>
			<tr>
				<th>合同号</th>
				<td>
					${qualityInspection.contractNo }
				</td>
				<th>提单号/放货编号</th>
				<td>
					${qualityInspection.billOrReleaseNo }
				</td>
			</tr>
			<tr>
				<th>发票号码</th>
				<td>
					${qualityInspection.invoiceNo }
				</td>
				<th>检验单位</th>
				<td>
					${my:getAffiBaseInfoByCode(qualityInspection.inspectionUnit)}
				</td>
			</tr>
			<tr>
				<th>送检日期</th>
				<td>
					<fmt:formatDate value='${qualityInspection.sendInspectionDate }'/>
				</td>
				<th>出检日期</th>
				<td>
					<fmt:formatDate value='${qualityInspection.outInspectionDate }'/>
				</td>
			</tr>
			<tr>
				<th>采验方式</th>
				<td>
					${fns:getDictLabelByCode(qualityInspection.miningTestWay, 'MININGTESTWAY', '')}
				</td>
				<th>检验方式</th>
				<td>
					${fns:getDictLabelByCode(qualityInspection.inspectionWay, 'INSPECTIONWAY', '')}
				</td>
			</tr>
			<tr>
				<th>化验员</th>
				<td>
					${qualityInspection.labTechnicians }
				</td>
				<th>数量合计</th>
				<td>
					<fmt:formatNumber value="${qualityInspection.numTotal }" pattern="#.00"/>
				</td>
			</tr>
			<tr>
				<th>业务经办人</th>
				<td>
					${my:getUserByLoginName(qualityInspection.businessManager).name}
				</td>
				<th>帐套单位</th>
				<td>
					${my:getAffiBaseInfoByCode(qualityInspection.setUnit)}
				</td>
			</tr>
			<tr>
				<th>制单日期</th>
				<td colspan="3">
					<fmt:formatDate value='${qualityInspection.billDate }'/>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>系统信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th>登记人</th>
					<td>${qualityInspection.createrName }</td>
					<th>登记部门</th>
					<td>${qualityInspection.createrDept }</td>
				</tr>
				<tr>
					<th width="20%">登记时间</th>
					<td width="30%"><fmt:formatDate value="${ qualityInspection.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th width="20%">最后修改时间</th>
					<td width="30%"><fmt:formatDate value="${qualityInspection.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div data-options="title: '质检商品', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/stock/qualityGoodsList.jsp"%>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>
</div>	
</form>
<script type="text/javascript">
$(function() {
});
</script>
</body>
</html>