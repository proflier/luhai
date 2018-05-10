<%@page import="com.cbmie.lh.logistic.entity.ShipmentsSettlement"%>
<%@ page import="com.cbmie.lh.sale.entity.SaleInvoice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/logistic/shipments/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${shipmentsSettlement.id }" />
		
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '运输信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">订船合同编号</th>
						<td width="30%">
							${shipmentsSettlement.contractNo}
							<input id="contractNoOld" name="contractNoOld" type="hidden" value="${shipmentsSettlement.contractNoOld}"/>
						</td>
						<th width="20%">订船类型</th>
						<td width="30%">
							${fns:getDictLabelByCode(shipmentsSettlement.tradeCategory,'orderShipType','')}
						</td>
					</tr>
					<tr>
						<th width="20%">结算日期</th>
						<td width="30%">
							<fmt:formatDate value='${shipmentsSettlement.settleDate}' />
						</td>
						<th width="20%">开户名</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(shipmentsSettlement.accountName)}
						</td>
					</tr>
					<tr>
						<th width="20%">开户行</th>
						<td>
							${mytag:getAffiBankInfoById(shipmentsSettlement.bank).bankName}
						</td>
						<th width="20%">开户账号</th>
						<td>
							${shipmentsSettlement.account}
						</td>
					</tr>
					<tr>
						<th  >业务经办人 </th>
						<td colspan="3">
							${mytag:getUserByLoginName(shipmentsSettlement.businessManager).name}
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th width="20%" >制单人</th>
							<td width="30%">
								<input type="hidden" name="createrNo" value="${shipmentsSettlement.createrNo }"/>
								<input type="hidden" name="createrName" value="${shipmentsSettlement.createrName }"/>
								<input type="hidden" name="createrDept" value="${shipmentsSettlement.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${shipmentsSettlement.createDate  }' pattern='yyyy-MM-dd'/>" />
								${shipmentsSettlement.createrName }</td>
							<th width="20%" >制单日期</th>
							<td width="30%">
								<fmt:formatDate value="${shipmentsSettlement.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table> 
				</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>结算信息</legend>
				<div>
					<table id="dgSubList"></table>
				</div>
			</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>附件信息</legend>
				<input id="accParentEntity" type="hidden"  value="<%=ShipmentsSettlement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${shipmentsSettlement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</fieldset>	
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
var sub_obj = {
		init:function(){
			this.listSubs();
		},
		reload:function(){
			this.sub_list.datagrid("reload");
		},
		sub_list:'',
		listSubs:function(){
			this.sub_list = $('#dgSubList').datagrid({
				 queryParams: {
					'filter_EQL_shipmentsSettleId': $('#id').val()
				},
				method: "get",
				url: $('#id').val() ? '${ctx}/logistic/shipmentsSub/json' : '', 
				/* data:eval('(${shipmentsSettlement.settleSubs})'), */
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				extEditing:false,
			    columns:[[    
					{field:'id',title:'id',hidden:true},
					{field:'shipNo',title:'船名',sortable:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(value!=''&& value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
									success: function(data){
										if(data != null){
											val = data;
										} else {
											val = '';
										}
									}
								});
								return val;
							}else{
								return '';
							}
						}},
					{field:'route',title:'航线',sortable:true,width:20},
					{field:'unloadDate',title:'卸货日期',sortable:true,width:10,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd");
		            	}
					},
					{field:'settleQuantity',title:'结算吨数',sortable:true,width:10},
					{field:'moneyCurrencyCode',title:'币种',width:10,
						formatter:function(value,row,index){
							if(value == null){
								return '';
							}else{
								var val = '';
								$.ajax({
									url:'${ctx}/system/dictUtil/getDictNameByCode/CURRENCY/'+value,
									type:'GET',
									async:false,
									success: function(data){
										if(data != null){
											val = data;
										} 
									}
								});
								return val;
							}
		            	}
					},
					{field:'unitPrice',title:'结算单价',sortable:true,width:10},
					{field:'totalPrice',title:'小计金额',sortable:true,width:10},
					{field:'remarks',title:'备注',sortable:true,width:20}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tbGoods'
			});
		},
		subs_form:'',
}
$(function(){
	sub_obj.init();
	
});


</script>
</body>
</html>