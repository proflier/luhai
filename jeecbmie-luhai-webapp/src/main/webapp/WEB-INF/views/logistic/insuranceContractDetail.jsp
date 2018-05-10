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
							<input type="hidden" name="id" id="insuranceContractId" value="${insuranceContract.id}" />
							${insuranceContract.contractNo }
						</td>
						<th width="20%"><font style="vertical-align: middle; color: red"></font>内部合同号</th>
						<td width="30%">
							${insuranceContract.innerContractNo }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>是否已保</th>
						<td>
							${fns:getDictLabelByCode(insuranceContract.isInsurance,'YESNO','')}
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保险公司</th>
						<td>
							${mytag:getAffiBaseInfoByCode(insuranceContract.insuranceCompany)}
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保额</th>
						<td>
							${insuranceContract.money }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保额币种</th>
						<td>
							${fns:getDictLabelByCode(insuranceContract.moneyCurrency,'currency','')}
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保费</th>
						<td>
							${insuranceContract.amount }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保费币种</th>
						<td>
							${fns:getDictLabelByCode(insuranceContract.amountCurrency,'currency','')}
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>费率</th>
						<td>
							<c:if test="${!empty insuranceContract.exchangeRate}">
							${ insuranceContract.exchangeRate}%
							</c:if>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>投保日期</th>
						<td>
							<fmt:formatDate value="${insuranceContract.insuranceDate}" />
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>船名</th>
						<td>
							${mytag:getShipByNo(insuranceContract.shipNo).noAndName}
						</td>
						<th>险种</th>
						<td>
							${insuranceContract.type }
						</td> 
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>被保险人</th>
						<td>
							${insuranceContract.insurancePerson }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保险货物项目</th>
						<td>
							${insuranceContract.goodsName }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>装载运输工具</th>
						<td>
							${insuranceContract.transportTool }
						</td>
						<th><font style="vertical-align: middle; color: red"></font>赔款偿付地点</th>
						<td>
							${insuranceContract.addr }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>合同类别</th>
						<td >
							${fns:getDictLabelByCode(insuranceContract.tradeCategory, 'htlb', '')}
						</td>
						<th  >帐套单位</th>
						<td >
							${mytag:getAffiBaseInfoByCode(insuranceContract.setUnit)}
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td colspan="3">
							${mytag:getUserByLoginName(insuranceContract.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${insuranceContract.comment }
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
							 ${insuranceContract.createrName }
							 <input  name="createrNo" type="hidden" value="${insuranceContract.createrNo }"/>
						</td>
						<th width="20%">登记部门</th>
						<td width="30%">
							${insuranceContract.createrDept }
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${insuranceContract.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${insuranceContract.changeReason }
							</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<table id="childTBBak"></table>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InsuranceContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${insuranceContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
	</div>
<script type="text/javascript">
	$(function() {
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if(!$('#mainform').form('validate')){
		    		$("#mainDiv").tabs("select", tabIndex);
		    	}else{
		    		tabIndex = index;
		    	}
		    }
		});
		
		var idValue = $("#insuranceContractId").val();
		var childTBBak;
		$(function() {
			childTBMxBak = $('#childTBBak')
					.datagrid(
							{
								url : '${ctx}/logistic/insurance/getInsuranceContractBak/'
										+ idValue,
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
										{field:'contractNo',title:'保险合同号',sortable:true,width:20}, 
										{field:'innerContractNo',title:'内部合同号',sortable:true,width:20}, 
										{field:'insuranceCompany',title:'保险公司',sortable:true,width:20,
							            	formatter: function(value,row,index){
												var val;
												if(value!=''&&value!=null){
													$.ajax({
														type:'GET',
														async: false,
														url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
					href : '${ctx}/logistic/insurance/showChange/'
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
</script>
</body>
</html>
