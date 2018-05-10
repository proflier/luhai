<%@page import="com.cbmie.lh.logistic.entity.InsuranceContract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
	<div>
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '保险合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>保险合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%"><font style="vertical-align: middle; color: red"></font>保险合同号</th>
						<td width="30%">
							<input type="hidden" name="id" id="insuranceContractIdChange" value="${insuranceContractChange.id}" />
							${insuranceContractChange.contractNo }
						</td>
						<th width="20%"><font style="vertical-align: middle; color: red"></font>内部合同号</th>
						<td width="30%">
							${insuranceContractChange.innerContractNo }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>是否已保</th>
						<td>
							${fns:getDictLabelByCode(insuranceContractChange.isInsurance,'YESNO','')}
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保险公司</th>
						<td>
							${mytag:getAffiBaseInfoByCode(insuranceContractChange.insuranceCompany)}
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保额</th>
						<td>
							${insuranceContractChange.money }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保额币种</th>
						<td>
							${fns:getDictLabelByCode(insuranceContractChange.moneyCurrency,'currency','')}
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保费</th>
						<td>
							${insuranceContractChange.amount }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保费币种</th>
						<td>
							${fns:getDictLabelByCode(insuranceContractChange.amountCurrency,'currency','')}
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>费率</th>
						<td>
							<c:if test="${!empty insuranceContractChange.exchangeRate}">
							${ insuranceContractChange.exchangeRate}%
							</c:if>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>投保日期</th>
						<td>
							<fmt:formatDate value="${insuranceContractChange.insuranceDate}" />
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>船名</th>
						<td>
							${mytag:getShipByNo(insuranceContractChange.shipNo).noAndName}
						</td>
						<th>险种</th>
						<td>
							${insuranceContractChange.type }
						</td> 
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>被保险人</th>
						<td>
							${insuranceContractChange.insurancePerson }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保险货物项目</th>
						<td>
							${insuranceContractChange.goodsName }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>装载运输工具</th>
						<td>
							${insuranceContractChange.transportTool }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>赔款偿付地点</th>
						<td>
							${insuranceContractChange.addr }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>合同类别</th>
						<td >
							${fns:getDictLabelByCode(insuranceContractChange.tradeCategory, 'htlb', '')}
						</td>
						<th  >帐套单位</th>
						<td >
							${mytag:getAffiBaseInfoByCode(insuranceContractChange.setUnit)}
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td colspan="3">
							${mytag:getUserByLoginName(insuranceContractChange.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${insuranceContractChange.comment }
						</td>
					</tr>						
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">登记人</th>
						<td width="30%">
							 ${insuranceContractChange.createrName }
							 <input  name="createrNo" type="hidden" value="${insuranceContractChange.createrNo }"/>
						</td>
						<th width="20%">登记部门</th>
						<td width="30%">
							${insuranceContractChange.createrDept }
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${insuranceContractChange.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${insuranceContractChange.changeReason }
							</div>
					</fieldset>
				</c:if>
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
$('#dgAccChange').datagrid({
	method : "get",
	fit : false,
	fitColumns : true,
	border : false,
	striped : true,
	singleSelect : true,
	scrollbarSize : 0,
	url : '${ctx}/accessory/jsonAll?filter_EQS_accParentId=${insuranceContractChange.id}&filter_EQS_accParentEntity=com_cbmie_lh_logistic_entity_InsuranceContract',
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
