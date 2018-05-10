<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<form id="mainform" action="${ctx}/credit/payMortgageReg/${action}" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th>到单号</th>
				<td colspan="3">
					<input type="text" id="woodBillId" name="woodBillId" value="${payMortgageReg.woodBillId }" onfocus="loadBillId_pay_mortgage()" class="easyui-validatebox" data-options="required:true"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadBillId_pay_mortgage()">选择到单</a>
				</td>
			</tr>
			<tr>
				<th width="20%">还汇日期</th>
				<td width="30%">
					<input type="hidden" id="id" name="id" value="${payMortgageReg.id }"/>
					<input type="hidden" id="relLoginNames" name="relLoginNames" value="${payMortgageReg.relLoginNames }"/>
					<input type="text" name="payMortgageTime" value="<fmt:formatDate value="${payMortgageReg.payMortgageTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
				<th width="20%">押汇利息（外币） </th>
				<td width="30%">
					<input type="text" id="mortgageInterestO" name="mortgageInterestO" value="${payMortgageReg.mortgageInterestO }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
			</tr>
			<tr>
				<th>汇率</th>
				<td>
					<input type="text" id="exchangeRate" name="exchangeRate" value="${payMortgageReg.exchangeRate }" class="easyui-numberbox" 
						data-options="required:true,precision:3"/>
				</td>
				<th>押汇利息（人民币） </th>
				<td>
					<input type="text" id="mortgageInterestRMB" name="mortgageInterestRMB" value="${payMortgageReg.mortgageInterestRMB }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
			</tr>
			<tr>
				<th>本息合计（人民币）</th>
				<td >
					<input type="text" id="allMoney" name="allMoney" value="${payMortgageReg.allMoney }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
				<th>还汇币种</th>
				<td>
					<mytag:combobox name="currency" value="${payMortgageReg.currency}" type="dict" parameters="currency"/>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					<textarea rows="2" cols="45" id="summary" class="easyui-validatebox" name="summary" data-options="validType:['length[0,46]']">${payMortgageReg.summary }</textarea>
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${empty payMortgageReg.createrName ? sessionScope.user.name : payMortgageReg.createrName }</td>
		<th  width="20%">登记部门</th>
		<td width="30%">${empty payMortgageReg.createrDept ? sessionScope.user.organization.orgName : payMortgageReg.createrDept }</td>
	</tr>
	<tr>
		<th width="20%" >登记时间</th>
		<td width="30%"><fmt:formatDate value="${ payMortgageReg.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th width="20%" >最后修改时间</th>
		<td width="30%"><fmt:formatDate value="${payMortgageReg.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTipNew(data,dg);
			$.easyui.loaded();
	    }
	});
	
	$('#woodBillId').attr("readonly",true);
});

function loadBillId_pay_mortgage(){
	dlg_loadbillid_pay_mortgage=$("#dlg_loadbillid_pay_mortgage").dialog({
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
				dlg_loadbillid_pay_mortgage.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_loadbillid_pay_mortgage.panel('close');
			}
		}]
	});
	
}
</script>
</body>
</html>