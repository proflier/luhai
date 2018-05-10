<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div id="detailLayout" class="easyui-layout" data-options="fit:true">
		<div id="detailLayoutEast" data-options="region:'east',collapsible:true,split:true,collapsed:true,border: false,title:'关联列表'" style="width: 600px">
			<input id="innerContractNo" type="hidden" value="${shipTrace.innerContractNo }" />
			<table id="dg_relation" ></table>
			<div id="dlg_relation"></div>
		</div>
	<div onclick="centerDetail()" data-options="region:'center'">
	<form id="mainform_shipTrace" action="${ctx}/logistic/shipTrace/${action}" method="post">
		<div id="mainDiv_shipTrace" class="" data-options="border:false">
			<div data-options="title: '船舶动态', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">内部合同号</th>
						<td colspan="3">
							${shipTrace.innerContractNo}
						</td>
					</tr>
					<tr>
						<th width="20%">订船类型</th>
						<td  width="30%">
							${fns:getDictLabelByCode(shipTrace.tradeCategory,'orderShipType','')}
							<input id="id" name="id" type="hidden"  value="${shipTrace.id }" />
							<input type="hidden" id="tradeCategoryBak"  value="${shipTrace.tradeCategory }"/>
							<input type="hidden" id="tradeCategoryOld" name="tradeCategoryOld"  value="${shipTrace.tradeCategory }"/>
							<input type="hidden" id="shipNoOld" name="shipNoOld"  value="${shipTrace.shipNo }"/>
						</td>
						
						<th width="20%">船编号</th>
						<td width="30%">
							${shipTrace.shipNo}
						</td>
					</tr>
					<tr>
						<th width="20%">船名称</th>
						<td width="30%">
							${shipTrace.shipName}
						</td>
						<th width="20%">船名称(英文)</th>
						<td width="30%">
							${shipTrace.shipNameE}
						</td>
					</tr>
					<tr id="wmShipNoTr">
						<th width="20%">母船</th>
						<td  colspan="3">
							${mytag:getShipByNo(shipTrace.wmShipNo).noAndName}
						</td>
					</tr>
					<tr>
						<th width="20%">抵港时间</th>
						<td width="30%">
							<fmt:formatDate value="${shipTrace.loadPortDate }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<th width="20%">装港港口</th>
						<td width="30%">
							${mytag:getGKMByCode(shipTrace.loadPort)}
						</td>
					</tr>
					<tr>
						<th width="20%">开装时间</th>
						<td width="30%">
							<fmt:formatDate value="${shipTrace.loadBeginDate }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<th width="20%">装完时间</th>
						<td width="30%">
							<fmt:formatDate value="${shipTrace.loadEndDate }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
					</tr>
					<tr>
						<th width="20%">装货量</th>
						<td>
							${shipTrace.loadQuantity }
						</td>
						<th width="20%">卸货量</th>
						<td >
							${shipTrace.unloadQuantity }
						</td>
					</tr>
					<tr>
						<th width="20%">抵卸港时间</th>
						<td width="30%">
							<fmt:formatDate value="${shipTrace.unloadPortDate }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<th width="20%">卸港港口</th>
						<td width="30%">
							${mytag:getGKMByCode(shipTrace.unloadPort)}
						</td>
					</tr>
					<tr>
						<th width="20%">开卸时间</th>
						<td width="30%">
							<fmt:formatDate value="${shipTrace.unloadBeginDate }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<th width="20%">卸完时间</th>
						<td width="30%">
							<fmt:formatDate value="${shipTrace.unloadEndDate }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
					</tr>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3" >
							${shipTrace.remarks}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th  width="20%">登记人</th>
							<td width="30%">
								<input type="hidden" name="createrNo" value="${shipTrace.createrNo }"/>
								<input type="hidden" name="createrName" value="${shipTrace.createrName }"/>
								<input type="hidden" name="createrDept" value="${shipTrace.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${shipTrace.createDate  }' pattern='yyyy-MM-dd'/>" />
								${shipTrace.createrName }</td>
							<th width="20%" >部门</th>
							<td width="30%">${shipTrace.createrDept }</td>
						</tr>
						<tr>	
							<th  >登记日</th>
							<td colspan="3">
								<fmt:formatDate value="${shipTrace.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
				<jsp:include page="/WEB-INF/views/relationDetail/relationDetail.jsp" >
					<jsp:param value="shipTrace,saleContract,saleDelivery,outStock,saleInvoice,paymentConfirm" name="disView"/>
				</jsp:include>
			</div>
		</div>
	</form>
	</div>
	</div>

<script type="text/javascript">
$(function() {
	var tabIndex = 0;
	$('#mainDiv_shipTrace').tabs({
		onSelect : function(title, index) {
		}
	});

});
</script>
</body>
</html>