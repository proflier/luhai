<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<form id="operateForm" action="${ctx}/logistic/portContract/operate/${actionOperate}" method="post">
		<fieldset class="fieldsetClass" >
			<legend>作业费用信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%">港口</th>
					<td width="30%">
						<input type="hidden" id="operateExpenseId" name="id" value="${operateExpense.id}" />
						<input name="portContractId" type="hidden" value="${operateExpense.portContractId}" />
						<mytag:combobox name="portNo" type="customer" value="${operateExpense.portNo}" parameters="10230007"></mytag:combobox>
					</td>
					<th width="20%">贸易类型</th>
					<td width="30%">
						<mytag:combobox name="tradeCategory" value="${operateExpense.tradeCategory}" type="dict" parameters="htlb"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >作业方式</th>
					<td width="30%" >
						<mytag:combobox name="operateType" value="${operateExpense.operateType}" type="dict" parameters="portExpenseType"/>
					</td>
					<th width="20%" >单价(元/吨)</th>
					<td width="30%" >
						<input id="unitPrice" name="unitPrice"  type="text" value="${operateExpense.unitPrice}" class="easyui-numberbox" data-options="precision:'4'"/>
					</td>			
				</tr>
				<tr>
					<th>计价范围开始(万吨)</th>
					<td><input id="expenseStart" name="expenseStart"  type="text" value="${operateExpense.expenseStart}" class="easyui-numberbox" data-options="precision:'2'"/></td>		
					<th>计价范围结束(万吨)</th>
					<td><input id="expenseEnd" name="expenseEnd"  type="text" value="${operateExpense.expenseEnd}"  class="easyui-numberbox" data-options="precision:'2'"/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea rows="8" cols="80" id="summary" class="easyui-validatebox" name="summary"  >${operateExpense.summary }</textarea>
					</td>
				</tr>
				
			</table>
		</fieldset>
	</form>
</div>

<script type="text/javascript">
$(function(){  
	$('#operateForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#operateForm').form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },  
	    success:function(data){
	    	$.easyui.loaded();
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		operateExpense.operateList.datagrid('reload');
	    		operateExpense.operateForm.panel('close');
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
});
  
</script>
</body>
</html>

