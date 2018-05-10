<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/credit/mortgageReg/${action}" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th>上游到单号</th>
				<td colspan="3">
					<input type="text" id="woodBillId" name="woodBillId"  value="${mortgageReg.woodBillId }" class="easyui-validatebox" data-options="required:true"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadBillId_mortgage()">选择到单</a>
				</td>
			</tr>
	 		<tr>
				<th width="20%">信用证号</th>
				<td width="30%">
				<input type="hidden" id="id" name="id" value="${mortgageReg.id }"/>
				<input type="text" id="creditNo" name="creditNo" value="${mortgageReg.creditNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th width="20%">综合合同号</th>
				<td width="30%">
				<input type="text" name="inteContractNo" value="${mortgageReg.inteContractNo }"  class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
		
			<tr>
				<th>银行名称</th>
				<td>
				<input type="text" id="bankName" name="bankName" value="${mortgageReg.bankName }" />
				</td>
				<th>银行编号 </th>
				<td>
				<input type="text" id="bankNo" name="bankNo" value="${mortgageReg.bankNo }" class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>授信类型</th>
				<td>
					<input type="text" id="creditType" name="creditType" value="${mortgageReg.creditType }" />
				</td>
				<th>押汇金额</th>
				<td>
					<input type="text" id="mortgageMoney" name="mortgageMoney" value="${mortgageReg.mortgageMoney }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
			</tr>
			<tr>
				<th>押汇币种</th>
				<td>
					<input type="text" id="mortgageCurrency" name="mortgageCurrency" value="${mortgageReg.mortgageCurrency }" />
				</td>
				<th>汇率</th>
				<td>
					<input type="text" id="exchangeRate" name="exchangeRate" value="${mortgageReg.exchangeRate }" class="easyui-numberbox" 
						data-options="required:true,precision:3"/>
				</td>
			</tr>
			<tr>
				<th>押汇日期</th>
				<td>
					<input type="text" name="mortgageTime" value="<fmt:formatDate value="${mortgageReg.mortgageTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
				</td>
				<th>是否最后一次押汇</th>
				<td>
					<input type="text" id="lastMortgage" name="lastMortgage" value="${mortgageReg.lastMortgage }" />
				</td>
			</tr>
			<tr>
				<th>预计还押汇日期</th>
				<td>
					<input type="text" name="payMortgageTime" value="<fmt:formatDate value="${mortgageReg.payMortgageTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
				</td>
				<th>实际还押汇日期 </th>
				<td>
					<input type="text" name="payMortgageTimeReal" value="<fmt:formatDate value="${mortgageReg.payMortgageTimeReal }" />" class="easyui-my97" datefmt="yyyy-MM-dd" "/>
				</td>
			</tr>
			<tr>
				<th>押汇利率</th>
				<td>
					<input type="text" id="mortgageRate" name="mortgageRate" value="${mortgageReg.mortgageRate }" class="easyui-numberbox" 
						data-options="required:true,precision:3"/>
				</td>
				<th>人民币金额 </th>
				<td>
					<input type="text" id="rmbMoney" name="rmbMoney" value="${mortgageReg.rmbMoney }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th  width="20%">登记人</th>
		<td width="30%">${empty mortgageReg.createrName ? sessionScope.user.name : mortgageReg.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${empty mortgageReg.createrDept ? sessionScope.user.organization.orgName : mortgageReg.createrDept }</td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTipNew(data,dg);
	    }
	});
	
	
	$('#woodBillId').attr("readonly",true);
	//币种
	$('#mortgageCurrency').combobox({
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/currency',
		valueField : 'id',
		textField : 'name'
	});
	
	//授信类型
	$('#creditType').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/sxlx',
		valueField : 'id',
		textField : 'name'
	});
	
	//银行名称
	$('#bankName').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=4',
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			$('#bankNo').val(record.customerCode)
		}
	});
	
	//是否最后一次押汇
	$('#lastMortgage').combobox({
		panelHeight : 'auto',
		required : true,
		url:'${ctx}/system/downMenu/getDictByCode/YESNO',
		valueField : 'id',
		textField : 'name',
	});
	
});

function loadBillId_mortgage(){
	dlg_loadbillid_mortgage=$("#dlg_loadbillid_mortgage").dialog({
	  	width: 900,    
	    height: 400, 
	    title: '选择到单',
	    href:'${ctx}/credit/mortgageReg/toBillId/',
	    modal:true,
	    closable:true,
	    style:{borderWidth:0},
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				initContract();
				dlg_loadbillid_mortgage.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_loadbillid_mortgage.panel('close');
			}
		}]
	});
	
}
</script>
</body>
</html>