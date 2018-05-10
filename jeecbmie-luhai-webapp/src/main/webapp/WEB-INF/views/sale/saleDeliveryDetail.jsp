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
	<form id="mainform" action="${ctx}/sale/saleDelivery/${action}" method="post">
		<input id="id" name="id" type="hidden" value="${saleDelivery.id }" />
		<input type="hidden" id="relLoginNames" name="relLoginNames" value="${saleDelivery.relLoginNames }"/>
		<input id="saleContractNoOld" name="saleContractNoOld" type="hidden" value="${saleDelivery.saleContractNoOld }" />
		<input type="hidden" id="param" name="param" value="EQL_saleDeliveryId" />
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '放货审批', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>通知单号</th>
						<td>
							${saleDelivery.deliveryReleaseNo}
						</td>					
						<th>销售合同号</th>
						<td>
							${saleDelivery.saleContractNo }
						</td>
					</tr>
					<tr>
						<th width="20%">客户名称</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleDelivery.seller)}
						</td>
						<th width="20%">销售方式</th>
						<td width="30%">
							${fns:getDictLabelByCode(saleDelivery.saleMode,'SALESMODE','')}
						</td>
					</tr>
					<tr>
						<th>交货起止日期</th>
						<td colspan="3">
							<fmt:formatDate value='${saleDelivery.deliveryStartDate }' />
							-
							<fmt:formatDate value='${saleDelivery.deliveryEndDate }' />
						</td>
					</tr>
					<tr>
						<th>交货方式</th>
						<td>
							${fns:getDictLabelByCode(saleDelivery.deliveryMode,'THFS','')}
						</td>
						<th>数量合计</th>
						<td>
							<fmt:formatNumber value="${saleDelivery.numTotal }" pattern="#.00"/>
						</td>
					</tr>
					<tr>
						<th>帐套单位</th>
						<td>
							${mytag:getAffiBaseInfoByCode(saleDelivery.setUnit)}
						</td>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(saleDelivery.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>数量溢短装</th>
						<td>
							<fmt:formatNumber value="${saleDelivery.numMoreOrLess }" pattern="#.00%"/>
						</td>
						<th>制单日期</th>
						<td>
							<fmt:formatDate value="${saleDelivery.billDate }" />
						</td>
					</tr>
					<tr>
						<th>数量验收 </th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDelivery.numSettlementBasis)}
						</td>
					</tr>
					<tr>
						<th>质量验收</th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDelivery.qualitySettlementBasis)}
						</td>
					</tr>
					<tr>
						<th>费用承担</th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDelivery.riskTip)}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${mytag:unescapeHtml(saleDelivery.remark)}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>放货信息</legend>
					<table id="dgGoods" ></table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							${saleDelivery.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleDelivery.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleDelivery.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleDelivery.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${saleDelivery.pid ne null }">
				<fieldset id="changeReasonField"  class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${saleDelivery.changeReason }
							</div>
				</fieldset>
				</c:if>
			</div>
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<table id="childTBBak"></table>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<div id="dgAcc"  class="tableClass"></div>
			</div>
						
		</div>	
	</form>
</div>

<script type="text/javascript">
$(function(){
	
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
	    		tabIndex = index;//更换tab
	    	}else{
	    		if(index != 0){
	    			parent.$.messager.alert('提示','请先保存主表信息!!!');
	    		}
	    		$("#mainDiv").tabs("select", tabIndex);//当前tab
	    		return ;
	    	}
	    }
	});
	
	$('#dgGoods').datagrid({  
		method: "get",
		url:'${ctx}/sale/saleDeliveryGoods/getGoods/EQL_saleDeliveryId/${saleDelivery.id }',
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
	
	$('#dgAcc').datagrid({
		method : "get",
		fit : false,
		fitColumns : true,
		border : false,
		striped : true,
		singleSelect : true,
		scrollbarSize : 0,
		url :'${ctx}/accessory/jsonAll?filter_EQS_accParentId=${saleDelivery.id}&filter_EQS_accParentEntity=com_cbmie_lh_sale_entity_SaleDelivery',
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
	
	var childTBBak;
	$(function() {
		childTBMxBak = $('#childTBBak')
				.datagrid(
						{
							url : '${ctx}/sale/saleDelivery/getSaleDeliveryBaks/${saleDelivery.id}',
							method : "get",
							fit : false,
							fitColumns : true,
							scrollbarSize : 0,
							border : false,
							striped : true,
							idField : 'id',
							rownumbers : true,
							singleSelect : true,
							extEditing : false,
							columns : [ [
									{
										field : 'changeReason',
										title : '变更原因',
										sortable : true,
										width : 15
									},
									{
										field : 'updateDate',
										title : '变更时间',
										sortable : true,
										width : 15
									},
									{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
									{field:'deliveryReleaseNo',title:'发货通知号',sortable:true,width:20}, 
									{
										field : 'id',
										title : '查看记录',
										sortable : true,
										width : 15,
										formatter : function(value, row,
												index) {
											var str = "";
											str += "<a   class='easyui-linkbutton' iconCls='icon-search' plain='true' href='javascript:void(0)'  onclick='showChange("
													+ value
													+ ");'>查看变更</a>"
											return str;
										}
									}, ] ],
							enableHeaderClickMenu : false,
							enableHeaderContextMenu : false,
							enableRowContextMenu : false,
						});
	});
	
});

function showChange(idValue) {
	d_change = $("#dlg_bak").dialog(
			{
				title : '变更记录',
				width : 1000,
				height : 450,
				style : {
					borderWidth : 0
				},
				closable : false,
				href : '${ctx}/sale/saleDelivery/showChange/'
						+ idValue,
				modal : true,
				buttons : [ {
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						d_change.panel('close');
					}
				} ]
			});

}

//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
</script>
</body>
</html>