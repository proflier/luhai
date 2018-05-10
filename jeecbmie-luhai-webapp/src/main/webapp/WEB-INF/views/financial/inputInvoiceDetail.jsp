<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>

<body>
		<form id="mainform"  action="${ctx}/financial/inputInvoice/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '进项管理', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>进项发票</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">发票分类</th>
						<td width="30%">
						<input id="id" name="id" type="hidden"  value="${inputInvoice.id }" />
							${inputInvoice.invoiceClassModel }
						</td>
						<th width="20%">发票类型</th>
						<td width="30%">
							${inputInvoice.invoiceTypeModel }
						</td>
					</tr>
					<tr>
						<th>贸易方式</th>
						<td>
							${inputInvoice.tradeMode }
						</td>
						<th>客户名</th>
						<td>
							${mytag:getAffiBaseInfoByCode(inputInvoice.userName)}
						</td>
					</tr>
					<tr>
						<th>账户</th>
						<td>
							${inputInvoice.account }
						</td>
						<th>开户行</th>
						<td>
							${inputInvoice.accountBank }
						</td>
					</tr>
					<tr>
						<th>开票单位</th>
						<td>
							${mytag:getAffiBaseInfoByCode(inputInvoice.issuingOffice)}
						</td>
						<th>结算中心</th>
						<td>
							${sessionScope.user.organization.orgName }
						</td>
					</tr>
					<tr>
						<th>税票张数</th>
						<td>
							${inputInvoice.numStamps }
						</td>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(inputInvoice.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td style="height:1cm" colspan="3">${inputInvoice.remarks}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">制单人</th>
						<td width="30%">${empty inputInvoice.createrName ? sessionScope.user.name : inputInvoice.createrName }</td>
						<th width="20%" >制单部门</th>
						<td width="30%">${empty inputInvoice.createrDept ? sessionScope.user.organization.orgName : inputInvoice.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ inputInvoice.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${inputInvoice.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>发票信息</legend>
				<div>
					<div id="tbGoods" style="padding:5px;height:auto">
					</div>
					<table id="dgInputInvoiceSub"></table>
				</div>
				</fieldset>	
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
	var sub_obj = {
			init:function(){
				this.listSubs();
			},
			reload:function(){
				this.sub_list.datagrid("reload");
			},
			sub_list:{},
			listSubs:function(){
				var url = '';
				this.sub_list = $('#dgInputInvoiceSub').datagrid({
					method: "get",
					url: '${ctx}/financial/inputInvoiceSub/json?filter_EQL_inputInvoiceSubId='+$("#id").val(),
				  	fit : false,
					fitColumns : true,
					scrollbarSize : 0,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
					showFooter:true,
				    columns:[[    
						{field:'id',title:'id',hidden:true},
						{field:'purchaseOrderNumber',title:'采购订单号',sortable:true,width:20},
						{field:'relatedSalesOrder',title:'相关销售订单',sortable:true,width:20},
						{field:'invoiceNo',title:'发票号',sortable:true,width:20},
						{field:'billingDate',title:'开票日期',sortable:true,width:15},
						{field:'productName',title:'品名',sortable:true,width:10},
						{field:'unitMeasurementView',title:'计量单位',sortable:true,width:10},
						{field:'mount',title:'数量',sortable:true,width:10},
						{field:'prices',title:'单价',sortable:true,width:15},
						{field:'allPrices',title:'开票金额',sortable:true,width:20},
						{field:'taxMoney',title:'税额',sortable:true,width:10},
						{field:'rebateRate',title:'退税率',sortable:true,width:10}
				    ]],
				    enableHeaderClickMenu: false,
				    enableHeaderContextMenu: false,
				    enableRowContextMenu: false,
				    toolbar:'#tbGoods'
				});
			},
	}
	$(function(){
		sub_obj.init();
		sub_obj.reload();
		sub_obj.listSubs();
	});
</script>

</body>
</html>