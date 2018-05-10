<%@ page import="com.cbmie.lh.logistic.entity.PortContract"%>
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
		<input id="portContractIdChange" name="id" type="hidden"  value="${portContractChange.id }" />
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">合同号</th>
						<td colspan="3" >
							${portContractChange.contractNo}
						</td>
					</tr>
					<tr>
						<th  width="20%">免堆期天数</th>
						<td  width="30%">
							${portContractChange.freeHeapDays}
						</td>
						<th  width="20%">达量免堆数(万吨)</th>
						<td  width="30%">
							${portContractChange.freeHeapCounts}
						</td>
					</tr>
					<tr>
						<th width="20%">合同开始日期</th>
						<td width="30%">
							<fmt:formatDate value="${portContractChange.startDay }" />
						</td>
						<th width="20%">合同结束日期</th>
						<td width="30%">
							<fmt:formatDate value="${portContractChange.endDay }" />
						</td>
					</tr>
					<tr>
						<th width="20%">签订单位</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(portContractChange.signAffiliate)}
						</td>
						<th width="20%">联系方式</th>
						<td width="30%">
							${portContractChange.traderContact}
						</td>
					</tr>
					<tr>
						<th width="20%">是否法人签订</th>
						<td width="30%">
							${fns:getDictLabelByCode(portContractChange.isCorporationSign,'YESNO','')}
						</td>
						<th width="20%">代理人</th>
						<td width="30%">
							${portContractChange.agent}
						</td>
					</tr>
					<tr>
						<th width="20%">审核类别</th>
						<td width="30%">
							${fns:getDictLabelByCode(portContractChange.checkTypeCode,'checkType','')}
						</td>
						<th width="20%">是否法务审核</th>
						<td width="30%">
							${fns:getDictLabelByCode(portContractChange.isLegalReview,'YESNO','')}
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td >
							${mytag:getAffiBaseInfoByCode(portContractChange.setUnit)}
						</td>
						<th  >业务经办人 </th>
						<td>
							${mytag:getUserByLoginName(portContractChange.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${portContractChange.remarks}
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
								<input type="hidden" name="createrNo" value="${portContractChange.createrNo }"/>
								<input type="hidden" name="createrName" value="${portContractChange.createrName }"/>
								<input type="hidden" name="createrDept" value="${portContractChange.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${portContractChange.createDate  }' pattern='yyyy-MM-dd'/>" />
								${portContractChange.createrName }</td>
							<th width="20%">部门</th>
							<td width="30%">${portContractChange.createrDept }</td>
						</tr>
						<tr>	
							<th width="20%">制单日</th>
							<td width="30%" colspan="3">
								<fmt:formatDate value="${portContractChange.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<c:if test="${purchaseContract.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${portContractChange.changeReason }
							</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" id="operateField">
					<legend>作业费</legend>
					<div>
						<table id="operateListChange"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" id="extraField">
					<legend>杂费</legend>
					<div>
						<table id="extraListChange"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<span >${themeMembersChange}</span>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>附件信息</legend>
					<div id="dgAccChange"  class="tableClass"></div>
				</fieldset>
			</div>
		</div>
</div>
<script type="text/javascript">
$(function(){
	$('#operateListChange').datagrid({
		method: "get",
		url: '${ctx}/logistic/portContract/operate/json?filter_EQL_portContractId='+$("#portContractIdChange").val(),
	  	fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		extEditing:false,
	    columns:[[    
			{field:'portNoView',title:'港口',width:15},
			{field:'tradeCategoryView',title:'贸易类别',width:5},
			{field:'operateTypeView',title:'作业方式',width:10},
			{field:'expenseStart',title:'计价范围开始(万吨)',width:10},
			{field:'expenseEnd',title:'计价范围结束',width:10},
			{field:'unitPrice',title:'单价(元/吨)',width:10},
			{field:'summary',title:'备注',width:30,
				formatter: function (value) {
					if(value!=''&&value!=null){
						var returnValue = value.substring(0,20)+"...";
	                	return "<span title='" + value + "'>" + returnValue + "</span>";
					}else{
						return "";
					}
                }
			}
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
$('#extraListChange').datagrid({
				method: "get",
				url: '${ctx}/logistic/portContract/extra/json?filter_EQL_portContractId='+$("#portContractIdChange").val(),
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				extEditing:false,
			    columns:[[    
					{field:'expenseTypeView',title:'费用类别',width:10},
					{field:'countUnitView',title:'计量单位',width:10},
					{field:'expenseStart',title:'计价范围开始(天)',width:10},
					{field:'expenseEnd',title:'计价范围结束',width:10},
					{field:'unitPrice',title:'单价',width:10}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
			
			
			$('#dgAccChange').datagrid({
				method : "get",
				fit : false,
				fitColumns : true,
				border : false,
				striped : true,
				singleSelect : true,
				scrollbarSize : 0,
				url : '${ctx}/accessory/jsonAll?filter_EQS_accParentId=${portContractChange.id}&filter_EQS_accParentEntity=com_cbmie_lh_logistic_entity_PortContract',
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
							str += "<a style='text-decoration:none' href='#' onclick='downnloadAcc1(" + value + ");'>下载</a>"
							return str;
						}
					} 
				]]
			});

	
	
	
});

function downnloadAcc1(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
</script>
</body>
</html>