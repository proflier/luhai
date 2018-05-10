<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/financial/payTaxes/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<!--移动端截取标志-->
<table width="98%" class="tableClass">
	<tr>
		<th><font color="red">*</font>提单号</th>
		<td>
		<input type="hidden" name="id" value="${id }"/>
		<input type="text" id="invoiceNo" name="invoiceNo" value="${payTaxes.invoiceNo }"/>
		</td>
		<th>币种(进口合同)</th>
		<td>
		<input type="text" id="currency" name="currency" value="${payTaxes.currency }" class="easyui-validatebox" readonly="true" style="background: #eee"/>
		</td>
		<th>提单总额(人民币金额)</th>
		<td>
		<input type="text" id="invoiceMoney" name="invoiceMoney" value="${payTaxes.invoiceMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',disabled:true"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>税号</th>
		<td>
		<input type="text" name="taxNo" value="${payTaxes.taxNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th><font color="red">*</font>税率</th>
		<td>
		<input type="text" id="taxRate" name="taxRate" value="${payTaxes.taxRate }" class="easyui-numberbox" data-options="precision:4,required:true,onChange:function(newValue,oldValue){getTaxMoney(newValue)}"/>
		</td>
		<th><font color="red">*</font>税金</th>
		<td>
		<input type="text" id="taxMoney" name="taxMoney" value="${payTaxes.taxMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>划税日期</th>
		<td colspan="5">
		<input type="text" name="rowTaxDate" value="<fmt:formatDate value="${payTaxes.rowTaxDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>收款单位</th>
		<td>
		<input type="text" id="receivingUnit" name="receivingUnit" value="${payTaxes.receivingUnit }"/>
		</td>
		<th><font color="red">*</font>账号</th>
		<td>
		<input type="text" id="account" name="account" value="${payTaxes.account }" class="easyui-validatebox" data-options="required:true,disabled:true"/>
		</td>
		<th><font color="red">*</font>汇入行名称</th>
		<td>
		<input type="text" id="bank" name="bank" value="${payTaxes.bank }" class="easyui-validatebox" readonly="true" style="background: #eee" data-options="required:true"/>
		</td>
	</tr>
</table>
<!--移动端截取标志-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty payTaxes.createDate ? now : payTaxes.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty payTaxes.createrName ? sessionScope.user.name : payTaxes.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${payTaxes.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    }
	});
	
	$('#invoiceNo').combobox({
		method:'GET',
		panelHeight:'auto',
		required:true,
		url:'${ctx}/logistics/invoiceReg/json',
		valueField : 'invoiceNo',
		textField : 'invoiceNo',
		loadFilter:function(data){
			return data.rows;
		},
		onSelect:function(record){
			$('#currency').val(record.currency); //币种
			$('#invoiceMoney').numberbox({value:record.rmb}); //提单金额
			$('#receivingUnit').combobox("select", record.customsDeclarationUnit);
			getTaxMoney(); //税金
		}
	}); //提单号
	
	$('#receivingUnit').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/baseinfo/affiliates/getCusDecCompany',
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			$('#account').combobox({
				disabled:false,
				panelHeight : 'auto',
				url : '${ctx}/baseinfo/affiliates/selectBank/' + record.id,
				valueField : 'bankNO',
				textField : 'bankNO',
				onSelect:function(record){
					$('#bank').val(record.bankName)//汇入行名称
				}
			});//账号
		}
	});
	
	if('${action}' == "update"){
		$('#receivingUnit').combobox("clear");
		window.setTimeout("$('#receivingUnit').combobox('select', '${payTaxes.receivingUnit }');", 1000); //需要页面元素进入一定的状态才能使用，所以延迟1秒执行
	}
});

function getTaxMoney(newTaxRate){
	$('#taxMoney').numberbox({value : $('#invoiceMoney').numberbox('getValue') * (newTaxRate==null?$('#taxRate').numberbox('getValue'):newTaxRate)});
}
</script>
</body>
</html>