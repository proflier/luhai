<%@ page import="com.cbmie.lh.sale.entity.SaleDelivery"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '放货审批', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>发货通知号</th>
						<td>
							${saleDeliveryChange.deliveryReleaseNo}
						</td>					
						<th>销售合同号</th>
						<td>
							${saleDeliveryChange.saleContractNo }
						</td>
					</tr>
					<tr>
						<th width="20%">客户名称</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleDeliveryChange.seller)}
						</td>
						<th  width="20%">销售方式</th>
						<td width="30%">
							${fns:getDictLabelByCode(saleDeliveryChange.saleMode,'SALESMODE','')}
						</td>
					</tr>
					<tr>
						<th>交货起期</th>
						<td>
							<fmt:formatDate value="${saleDeliveryChange.deliveryStartDate }" />
						</td>
						<th>交货止期</th>
						<td>
							<fmt:formatDate value="${saleDeliveryChange.deliveryEndDate }" />
						</td>						
					</tr>
					<tr>
						<th>交货方式</th>
						<td>
							${fns:getDictLabelByCode(saleDeliveryChange.deliveryMode,'THFS','')}
						</td>
						<th>运输方式</th>
						<td>
							${fns:getDictLabelByCode(saleDeliveryChange.transType,'YSFS','')}
						</td>
					</tr>
					<tr>
						<th>数量溢短装</th>
						<td>
							<c:if test="${!empty saleDeliveryChange.numMoreOrLess}">
							${ saleDeliveryChange.numMoreOrLess}%
							</c:if>
						</td>
						<th>制单日期</th>
						<td>
							<fmt:formatDate value="${saleDeliveryChange.billDate }" />
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td colspan="3">
							${saleDeliveryChange.businessManager}
						</td>
					</tr>
					<tr>
						<th>数量验收 </th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDeliveryChange.numSettlementBasis)}
						</td>
					</tr>
					<tr>
						<th>质量验收</th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDeliveryChange.qualitySettlementBasis)}
						</td>
					</tr>
					<tr>
						<th>费用承担</th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDeliveryChange.riskTip)}
						</td>
					</tr>
					<tr>
						<th>注意事项</th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDeliveryChange.remark)}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>放货信息</legend>
					<table id="dgGoods_change" ></table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							${saleDeliveryChange.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleDeliveryChange.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleDeliveryChange.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleDeliveryChange.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${saleDeliveryChange.pid ne null }">
				<fieldset id="changeReasonField"  class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${saleDeliveryChange.changeReason }
							</div>
				</fieldset>
				</c:if>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<div id="dgAcc_change"  class="tableClass"></div>
			</div>
						
		</div>	
</div>

<script type="text/javascript">
$(function(){
	
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    }
	});
	
	$('#dgGoods_change').datagrid({  
		method: "get",
		url:'${ctx}/sale/saleDeliveryGoods/getGoods/EQL_saleDeliveryId/${saleDeliveryChange.id }',
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
			{field:'portView',title:'仓库',sortable:true,width:20},
			{field:'voyView',title:'船名',sortable:true,width:20},
			{field:'goodsNameView',title:'品名',sortable:true,width:20},
			{field:'quantity',title:'数量',sortable:true,width:20},
			{field:'unitsView',title:'数量单位',sortable:true,width:20}
	    ]],
	});
	
	$('#dgAcc_change').datagrid({
		method : "get",
		fit : false,
		fitColumns : true,
		border : false,
		striped : true,
		singleSelect : true,
		scrollbarSize : 0,
		url :'${ctx}/accessory/jsonAll?filter_EQS_accParentId=${saleDeliveryChange.id}&filter_EQS_accParentEntity=com_cbmie_lh_sale_entity_SaleDelivery',
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
					var str = "";
					str += "<a style='text-decoration:none' href='#' onclick='downnloadAcc(" + value + ");'>下载</a>"
					return str;
				}
			} 
		]]
	});
	
});

//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
</script>
</body>
</html>