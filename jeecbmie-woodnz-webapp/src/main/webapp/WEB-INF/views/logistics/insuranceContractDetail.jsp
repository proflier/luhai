<%@page import="com.cbmie.woodNZ.logistics.entity.InsuranceContract"%>
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
		<form id="mainform"  action="" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >			
				<fieldset class="fieldsetClass" >
				<legend>保险合同信息</legend>
				<table width="98%" class="tableClass">
					<tr >
						<input type="hidden" name="id" id="id" value="${insuranceContract.id}" />
						<th  width="20%" >保险合同号</th>
						<td width="30%">
							${insuranceContract.contractNo }
						</td>
						<th width="20%">是否已保</th>
						<td width="30%" id="isInsurance">
						</td>
					</tr>
					<tr>
						<th>保险公司</th>
						<td id="insuranceCompany">
							
						</td>
						<th>投保日期</th>
						<td>
							<fmt:formatDate value="${insuranceContract.insuranceDate}"/>
						</td>
					</tr>
					<tr>
						<th>保额</th>
						<td>
							${insuranceContract.money }
						</td>
						<th>保额币种</th>
						<td id="moneyCurrency">
						</td>
					</tr>
					<tr>
						<th>保费</th>
						<td>
							${insuranceContract.amount }
						</td>
						<th>保费币种</th>
						<td id="amountCurrency">
						</td>
					</tr>
					<tr>
						<th>费率</th>
						<td>
							${insuranceContract.exchangeRate }
						</td>
						<th>船名</th>
						<td>
							${insuranceContract.shipName }
						</td>
					</tr>
					<tr>
						<th>船次</th>
						<td>
							${insuranceContract.shipTime }
						</td>
						<th>险种</th>
						<td>
							${insuranceContract.type }
						</td>
					</tr>
					<tr>
						<th>被保险人</th>
						<td>
							${insuranceContract.insurancePerson }
						</td>
						<th>包装及数量</th>
						<td>
							${insuranceContract.packAndNum }
						</td>
					</tr>
					<tr>
						<th>保险货物项目</th>
						<td>
							${insuranceContract.goodsName }
						</td>
						<th>装载运输工具</th>
						<td>
							${insuranceContract.transportTool }
						</td>
					</tr>
					<tr>
						<th>赔款偿付地点</th>
						<td>
							${insuranceContract.addr }
						</td>	
						<th>综合合同号</th>
						<td>
							${insuranceContract.cpContractNo }
						</td>	
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>
							${insuranceContract.createrName }
						</td>
						<th>登记部门</th>
						<td>
							${insuranceContract.createrDept }
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InsuranceContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${insuranceContract.id}" />
				<div id="dgAcc"  class="tableClass"></div>
			</div>
		</div>	
		</form>
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
								width : 10
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


//保险公司
$.ajax({
	url:'${ctx}/system/downMenu/getBaseInfoName/${insuranceContract.insuranceCompany }',
	type : 'get',
	cache : false,
	success : function(data) {
		$('#insuranceCompany').text(data);
	}
});

//币种菜单
$.ajax({
	url : '${ctx}/system/downMenu/getDictName/currency/${insuranceContract.moneyCurrency }',
	type : 'get',
	cache : false,
	success : function(data) {
		$('#moneyCurrency').text(data);
	}
});


//是否已保
if('${insuranceContract.isInsurance }' != ''){
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/YESNO/${insuranceContract.isInsurance }',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#isInsurance').text(data);
		}
	});
}
</script>
</body>
</html>
