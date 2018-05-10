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
<input type="hidden" id="id" name="id" value="${saleOffer.id }" />
<div class="easyui-tabs" data-options="border:false">
	<div data-options="title: '基本信息', iconCls: false, refreshable: false" >
		<fieldset class="fieldsetClass" >
		<legend>基本信息</legend>
		<table width="98%" class="tableClass" >
			<tr>
				<th width="20%">报盘单号</th>
				<td width="30%">
					${saleOffer.offerNo }
				</td>
				<th width="20%">客户名称</th>
				<td width="30%">
					${my:getAffiBaseInfoByCode(saleOffer.customer)}
				</td>
			</tr>
			<tr>
				<th>联系人</th>
				<td>
					${saleOffer.contactPerson }
				</td>
				<th>电话</th>
				<td>
					${saleOffer.phoneContact }
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td>
					${saleOffer.faxContact }
				</td>
				<th>邮箱</th>
				<td>
					${saleOffer.emailContact }
				</td>
			</tr>
			<tr>
				<th>结算方式</th>
				<td>
					${fns:getDictLabelByCode(saleOffer.payMode, 'SJKFS', '')}
				</td>
				<th>交货期限</th>
				<td>
					<fmt:formatDate value='${saleOffer.deliveryStartDate }'/>
					-
					<fmt:formatDate value='${saleOffer.deliveryEndDate }'/>
				</td>
			</tr>
			<tr>
				<th>交货方式</th>
				<td>
					${fns:getDictLabelByCode(saleOffer.deliveryMode, 'THFS', '')}
				</td>
				<th>交货地点</th>
				<td>
					${saleOffer.deliveryAddr }
				</td>
			</tr>
			<tr>
				<th>业务经办人</th>
				<td>
					${my:getUserByLoginName(saleOffer.businessManager).name}
				</td>
				<th>电话</th>
				<td>
					${saleOffer.phoneBusiness }
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td>
					${saleOffer.faxBusiness }
				</td>
				<th>邮箱</th>
				<td>
					${saleOffer.emailBusiness }
				</td>
			</tr>
			<tr>
				<th>报盘日期</th>
				<td>
					<fmt:formatDate value='${saleOffer.offerDate }'/>
				</td>
				<th>报盘有效期</th>
				<td>
					<fmt:formatDate value='${saleOffer.validityDate }'/>
				</td>
			</tr>
			<tr>
				<th>制单日期</th>
				<td colspan="3">
					<fmt:formatDate value='${saleOffer.billDate }'/>
				</td>
			</tr>
			<tr>
				<th>其他说明</th>
				<td colspan="3">
					${my:unescapeHtml(saleOffer.otherExplain)}
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>系统信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th>登记人</th>
					<td>${saleOffer.createrName }</td>
					<th>登记部门</th>
					<td>${saleOffer.createrDept }</td>
				</tr>
				<tr>
					<th width="20%">登记时间</th>
					<td width="30%"><fmt:formatDate value="${ saleOffer.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th width="20%">最后修改时间</th>
					<td width="30%"><fmt:formatDate value="${saleOffer.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div data-options="title: '报盘商品', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/sale/saleOfferGoodsList.jsp"%>
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