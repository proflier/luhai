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
		<form id="mainform"  action="${ctx}/logistics/insurance/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '保险合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>保险合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<input type="hidden" name="id" id="id" value="${insuranceContract.id}" />
						<th><font style="vertical-align: middle; color: red"></font>保险合同号</th>
						<td>
							<input id="contractNo" name="contractNo" type="text" value="${insuranceContract.contractNo }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>是否已保</th>
						<td>
							<input id="isInsurance" name="isInsurance" type="text" value="${insuranceContract.isInsurance }"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保险公司</th>
						<td>
							<input id="insuranceCompany" name="insuranceCompany" type="text" value="${insuranceContract.insuranceCompany }" 
								/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>投保日期</th>
						<td>
							<input name="insuranceDate" type="text"  value="<fmt:formatDate value="${insuranceContract.insuranceDate}" />" class="easyui-my97" 
							datefmt="yyyy-MM-dd" data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保额</th>
						<td>
							<input id="money" name="money" type="text" value="${insuranceContract.money }" class="easyui-numberbox"
							data-options="required:false"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保额币种</th>
						<td>
							<input id="moneyCurrency" name="moneyCurrency" type="text" value="${insuranceContract.moneyCurrency }"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保费</th>
						<td>
							<input id="amount" name="amount" type="text" value="${insuranceContract.amount }" class="easyui-numberbox"
							data-options="required:false"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保费币种</th>
						<td>
							<input id="amountCurrency" name="amountCurrency" type="text" value="${insuranceContract.amountCurrency }"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>费率</th>
						<td>
							<input id="exchangeRate" name="exchangeRate" type="text" value="${insuranceContract.exchangeRate }" class="easyui-numberbox"
							data-options="required:false"/>%
						</td>
						<th><font style="vertical-align: middle; color: red"></font>船名</th>
						<td>
							<input id="shipName" name="shipName" type="text" value="${insuranceContract.shipName }" class="easyui-validatebox"
								/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>船次</th>
						<td>
							<input id="shipTime" name="shipTime" type="text" value="${insuranceContract.shipTime }" class="easyui-validatebox"
							data-options="required:false"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>险种</th>
						<td>
							<input id="type" name="type" type="text" value="${insuranceContract.type }" class="easyui-validatebox"
								/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>被保险人</th>
						<td>
							<input id="insurancePerson" name="insurancePerson" type="text" value="${insuranceContract.insurancePerson }" class="easyui-validatebox"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>包装及数量</th>
						<td>
							<input id="packAndNum" name="packAndNum" type="text" value="${insuranceContract.packAndNum }" class="easyui-validatebox"
								/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保险货物项目</th>
						<td>
							<input id="goodsName" name="goodsName" type="text" value="${insuranceContract.goodsName }" class="easyui-validatebox"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>装载运输工具</th>
						<td>
							<input id="transportTool" name="transportTool" type="text" value="${insuranceContract.transportTool }"  class="easyui-validatebox"
								/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>赔款偿付地点</th>
						<td>
							<input id="addr" name="addr" type="text" value="${insuranceContract.addr }" class="easyui-validatebox"/>
						</td>	
						<th><font style="vertical-align: middle; color: red"></font>综合合同号</th>
						<td>
							<input id="cpContractNo" name="cpContractNo" type="text" value="${insuranceContract.cpContractNo }" class="easyui-validatebox"
							data-options="required:true"/>
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
							 <input id="createrName" name="createrName" type="text" value="${insuranceContract.createrName }" 
							 readonly="true" class="easyui-validatebox"/>
							 <input  name="createrNo" type="hidden" value="${insuranceContract.createrNo }"/>
						</td>
						<th>登记部门</th>
						<td>
							<input id="createrDept" name="createrDept" type="text"  value="${insuranceContract.createrDept }" 
							class="easyui-validatebox" readonly="true" />
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InsuranceContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${insuranceContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
		
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
		
		//保险公司
		$('#insuranceCompany').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=5',
			valueField : 'id',
			textField : 'customerName',
			required : true,
		});
		
		//币种菜单
		$('#moneyCurrency').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/system/downMenu/getDictByCode/currency',
			valueField : 'id',
			textField : 'name',
			required : true,
		});
		
		//币种菜单
		$('#amountCurrency').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/system/downMenu/getDictByCode/currency',
			valueField : 'id',
			textField : 'name',
			required : true,
		});
		
		//是否已保
		$('#isInsurance').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/system/downMenu/getDictByCode/YESNO',
			valueField : 'id',
			textField : 'name',
			required : false,
		});
		
	});
	
</script>
</body>
</html>
