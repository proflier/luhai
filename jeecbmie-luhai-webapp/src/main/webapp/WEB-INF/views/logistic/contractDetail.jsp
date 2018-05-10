<%@page import="com.cbmie.lh.logistic.entity.Contract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>审批表流水号</th>
						<td>
							${contract.serialnumber }
						</td>
						<th>内部合同号</th>
						<td>
							${contract.innerContractNo }
						</td>
					</tr>
					<tr>
						<th>申请部门</th>
						<td id="applyDeptId">
						</td>
						<th>申请人</th>
						<td id="applyUserId">
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td id="handlingUserId">
						</td>
						<th>申请日期</th>
						<td>
							<fmt:formatDate value='${contract.applyDate }' pattern='yyyy-MM-dd'/>
						</td>
					</tr>
					<tr>
						<th>合同编号</th>
						<td>
							${contract.contractNo }
						</td>
						<th>合同类别</th>
						<td>
							${fns:getDictLabelByCode(contract.contractTypeCode,'contractType','')}
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							${contract.money }
						</td>
						<th>币种</th>
						<td>
							${fns:getDictLabelByCode(contract.moneyCurrencyCode,'currency','')}
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<fmt:formatDate value='${contract.startDate }' pattern='yyyy-MM-dd'/>
						</td>
						<th>合同结束日期</th>
						<td>
							<fmt:formatDate value='${contract.endDate }' pattern='yyyy-MM-dd'/>
						</td>
					</tr>
					<tr>
						<th>公章</th>
						<td colspan="3">
							${fns:getDictLabelByCode(contract.signetCode,'SIGNETTYPE','')}
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							${contract.traderName }
						</td>
						<th>对方联系方式</th>
						<td>
							${contract.traderContact }
						</td>
					</tr>
					<tr>
						<th>贸易类型</th>
						<td colspan="3">
							${fns:getDictLabelByCode(contract.tradeCategory,'orderShipType','')}
						</td>
					</tr>
					<tr>
						<th>主要内容</th>
						<td colspan="3">
							${contract.content }
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							${contract.isCorporationSign eq '1'?'是':'否'}
						</td>
						<th>代理人</th>
						<td>
							${contract.agent }
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							${fns:getDictLabelByCode(contract.checkTypeCode,'checkType','')}
						</td>
						<th>是否法务审查</th>
						<td>
							${contract.isLegalReview eq '1'?'是':'否'}
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							${contract.tipContent }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >制单人</th>
						<td>
							${contract.createrName }</td>
						<th  >制单部门</th>
						<td>${contract.createrDept }</td>
						<th  >制单时间</th>
						<td>
							<fmt:formatDate value="${contract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=Contract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${contract.id}" />
				<div id="dgAcc"  class="tableClass"></div>
			</div>
		</div>	
	</div>
<script type="text/javascript">
var urlValue = '${ctx}/accessory/json?filter_EQS_accParentId=' + $('#accParentId').val()
+ '&filter_EQS_accParentEntity=' + $('#accParentEntity').val();
var dgAcc;
$(function() {
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    }
	});
	
	dgAcc = $('#dgAcc')
		.datagrid(
				{
					method : "get",
					fit : false,
					fitColumns : true,
					border : false,
					striped : true,
					singleSelect : true,
					scrollbarSize : 0,
					url : urlValue,
					idField : 'accId',
					columns : [ [
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
									str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
											+ value + ");'>下载</a>"
									return str;
								}
							} ] ]
				})
});
	$(function() {
		$.ajax({
			url : '${ctx}/baseInfo/baseUtil/orgNameShow?orgId=${contract.applyDeptId }' ,
			type : 'get',
			cache : false,
			async: false,
			success : function(data) {
				$("#applyDeptId").html(data);
			}
		});
		$.ajax({
			url : '${ctx}/baseInfo/baseUtil/userNameShow?userId=${contract.applyUserId }' ,
			type : 'get',
			cache : false,
			async: false,
			success : function(data) {
				$("#applyUserId").html(data);
			}
		});
		$.ajax({
			url : '${ctx}/baseInfo/baseUtil/userNameShow?userId=${contract.handlingUserId }' ,
			type : 'get',
			cache : false,
			async: false,
			success : function(data) {
				$("#handlingUserId").html(data);
			}
		});
	});
	
</script>
</body>
</html>
