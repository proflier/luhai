<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body >
	<div id="printMmainDiv" style="" align="center">
			<h1 align="center" style="font-size: 35px;font-weight: bold;letter-spacing: 5px;">发货通知单</h1>
			<div style="font-size: 20px;width: 90%;margin-top: 30px;" align="left">
				<span>制单日期:</span> <fmt:formatDate value="${saleDelivery.billDate }" pattern="yyyy-MM-dd"/>
				<span style="padding-left: 20px;">客户编号:</span> ${saleDelivery.seller}
				<span style="padding-left: 20px;">客户名称:</span> ${mytag:getAffiBaseInfoByCode(saleDelivery.seller)}
				<span style="padding-left: 20px;">发货通知号:</span> ${saleDelivery.deliveryReleaseNo}
			</div>
			<table style="width:90%;font-size: 20px;padding-top: 20px;">
				<tr>
					<td width="15%">
						<span>销售方式</span>
					</td>
					<td width="50%">
						<c:forEach var="saleModeV" items="${saleModes}">
								<input type="checkbox" name="saleMode" disabled="disabled" value="${saleModeV.code}"  
								<c:if test="${saleModeV.code eq saleDelivery.saleMode}">checked="checked"</c:if>/>${saleModeV.name}
							</c:forEach>
					</td>
					<td width="25%"><span>业务经办人</span></td>
					<td width="10%">${mytag:getUserByLoginName(saleDelivery.businessManager).name}</td>
				</tr>
				<tr>
					<td colspan="4">
						<table id="goodDetailsPrint" width="100%" data-options="nowrap:false">
						</table>
					</td>
				</tr>
			</table>
			
			<div style="width:90%;font-size: 20px;padding-top: 25px;line-height: 35px;" align="left">
				注:<span>1.交货期限:</span> <fmt:formatDate value="${saleDelivery.deliveryStartDate }" pattern="yyyy年MM月dd日"/>-<fmt:formatDate value="${saleDelivery.deliveryEndDate }" pattern="yyyy年MM月dd日"/> <br />
				<span id="deliveryModePrint">2.交接方式: </span>  <br />
				<span>3.数量验收: ${saleDelivery.numSettlementBasis }</span>  <br />
				<span>4.质量验收: ${saleDelivery.qualitySettlementBasis }</span>  <br />
				<span>5.费用承担: ${saleDelivery.riskTip }</span>  <br />
			</div>
	</div>	

<script type="text/javascript">
/****/
$(function(){
	$('#goodDetailsPrint').datagrid({  
		method: "get",
		url:'${ctx}/sale/saleDeliveryGoods/getGoods/EQL_saleDeliveryId/${saleDelivery.id}',
	  	fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		extEditing:false,
	    columns:[[    
			{field:'contractNo',title:'合同编号',width:200},
			{field:'portView',title:'仓库',width:140},
			{field:'voyView',title:'船名',width:200},
			{field:'goodsNameView',title:'名称',width:100},
			{field:'quantity',title:'数量(吨)',width:60},
			{field:'remark',title:'备注',width:260}
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
	
	if('${saleDelivery.deliveryMode}'!=''){
		$.ajax({
			url : '${ctx}/system/dictUtil/getDictNameByCode/THFS/${saleDelivery.deliveryMode}',
			type : 'get',
			cache : false,
			async: false,
			success : function(data) {
				$("#deliveryModePrint").append("<span>"+data+"</span>");
			}
		});
	}
	
	
});
</script>
</body>
</html>