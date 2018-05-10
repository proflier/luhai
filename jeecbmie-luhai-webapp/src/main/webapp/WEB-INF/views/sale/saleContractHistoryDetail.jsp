<%@ page import="com.cbmie.lh.sale.entity.SaleContract"%>
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
		<div class="" data-options="border:false">
			<div data-options="title: '销售合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>销售合同号</th>
						<td>
							${saleContractHis.contractNo}
						</td>
						<th>客户合同号</th>
						<td>
							${saleContractHis.customerContractNo }
						</td>
					</tr>
					<tr>
						<th width="20%">卖方</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleContractHis.seller)}
						</td>
						<th  width="20%">买方</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleContractHis.purchaser)}
						</td>
					</tr>
					<tr>
						<th>销售方式</th>
						<td>
							${fns:getDictLabelByCode(saleContractHis.saleMode, 'SALESMODE', '')}
						</td>
						<th>业务类型</th>
						<td>
							${fns:getDictLabelByCode(saleContractHis.manageMode, 'BUSINESSTYPE', '')}
						</td>
					</tr>
					 <tr>
						<th>数量溢短装</th>
						<td>
							${saleContractHis.numMoreOrLess }
						</td>
						<th>签约日期</th>
						<td>
							<fmt:formatDate value="${saleContractHis.signDate }" pattern='yyyy-MM-dd'/>
						</td>
					</tr>
					 
					<tr>
						<th>交货起期</th>
						<td>
							<fmt:formatDate value="${saleContractHis.deliveryStartDate }" pattern='yyyy-MM-dd'/>
						</td>
						<th>交货止期</th>
						<td>
							<fmt:formatDate value="${saleContractHis.deliveryEndDate }" pattern='yyyy-MM-dd'/>
						</td>						
					</tr>
					<tr>
						<th>交货方式</th>
						<td>
							${fns:getDictLabelByCode(saleContractHis.deliveryMode, 'THFS', '')}
						</td>
						<th>交款方式</th>
						<td>
							${fns:getDictLabelsByCodes(saleContractHis.settlementMode, 'SJKFS', '')}
						</td>
					</tr>
					<tr>
						<th>交货地点</th>
						<td>
							${saleContractHis.deliveryAddr }
						</td>
						<th>业务经办人 </th>
						<td>
							${mytag:getUserByLoginName(saleContractHis.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>合同数量 </th>
						<td>
							<fmt:formatNumber value="${saleContractHis.contractQuantity }" pattern="#.##" minFractionDigits="2" />
						</td>
						<th>合同金额</th>
						<td>
							<fmt:formatNumber value="${saleContractHis.contractAmount }" pattern="#.##" minFractionDigits="2" />
						</td>
					</tr>
				<tr>
					<th>帐期</th>
					<td >
						${saleContractHis.accountStage}
					</td>
					<th  >帐套单位</th>
					<td >
						${mytag:getAffiBaseInfoByCode(saleContractHis.setUnit)}
					</td>
				</tr>
				<tr>
					<th>数量结算依据</th>
					<td colspan="3">
						${saleContractHis.numSettlementBasis}
					</td>
				</tr>
				<tr>
					<th>质量结算依据</th>
					<td colspan="3">
						${saleContractHis.qualitySettlementBasis}
					</td>
				</tr>
				<tr>
					<th>风险提示</th>
					<td colspan="3">
						${saleContractHis.riskTip}
					</td>
				</tr>
				<c:if test="${saleContract.pid ne null }">
						<tr>
							<th>变更原因</th>
							<td colspan="3">
							${saleContract.changeReason}
						</td>
						</tr>
					</c:if>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							${saleContractHis.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleContractHis.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleContractHis.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleContractHis.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<fieldset class="fieldsetClass" >
				<legend>货物明细</legend>
				<table id="dgContractGoodsHis" data-options="nowrap:false"></table>
			</fieldset>
				<script type="text/javascript">
				$(function(){
					$('#dgContractGoodsHis').datagrid({
						method: "get",
						url: '${ctx}/sale/saleContractGoods/forContractId/${saleContractHis.id }',
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
							{field:'goodsNameView',title:'品名',sortable:true,width:15},
							{field:'price',title:'单价',sortable:true,width:10},
							{field:'goodsQuantity',title:'数量',sortable:true,width:10},
							{field:'unitsView',title:'数量单位',sortable:true,width:20},
							{field:'amount',title:'总价',sortable:true,width:10},
							{field:'shipNoView',title:'运输工具编号',width:15},
							{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					});
					
				});
				</script>
			<fieldset class="fieldsetClass" >
				<legend>附件</legend>
				<div id="dgAccHis"  class="tableClass"></div>
			</fieldset>
		</div>	
</div>

<script type="text/javascript">
function downnloadAcc1(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
$(function(){
	$('#dgAccHis').datagrid({
		method : "get",
		fit : false,
		fitColumns : true,
		border : false,
		striped : true,
		singleSelect : true,
		scrollbarSize : 0,
		url : '${ctx}/accessory/jsonAll?filter_EQS_accParentId=${saleContractHis.id}&filter_EQS_accParentEntity=com_cbmie_lh_sale_entity_SaleContract',
		idField : 'accId',
		columns : [[
			{
				field : 'accRealName',
				title : '附件名称',
				sortable : true,
				width : 40
			},
			{
				field : 'accAuthor',
				title : '上传人',
				sortable : true,
				width : 10,
				formatter: function(value,row,index){
					var name = '';
					if(value!=null && value!=""){
						$.ajax({
							url : '${ctx}/system/user/getUserNameByLogin/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								name = data;
							}
						});
					}
					return name;
				}
			},
			{
				field : 'accId',
				title : '操作',
				sortable : true,
				width : 20,
				formatter : function(value, row, index) {
					var str = "<a style='text-decoration:none' href='#' onclick='downnloadAcc1(" + value + ");'>下载</a>"
					return str;
				}
			} 
		]]
	})
});

</script>
</body>
</html>