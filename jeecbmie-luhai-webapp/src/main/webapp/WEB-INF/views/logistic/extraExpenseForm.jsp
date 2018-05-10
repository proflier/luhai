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
	<form id="extraForm" action="${ctx}/logistic/portContract/extra/${actionExtra}" method="post">
		<fieldset class="fieldsetClass" >
			<legend>杂费信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%">费用类别</th>
					<td width="30%">
						<input type="hidden" id="extraExpenseId" name="id" value="${extraExpense.id}" />
						<input name="portContractId" type="hidden" value="${extraExpense.portContractId}" />
						<mytag:combobox name="expenseType" value="${extraExpense.expenseType}" type="dict" parameters="portExpenseType"/>
					</td>
					<th width="20%" >单价</th>
					<td width="30%" >
						<input id="unitPrice" name="unitPrice"  type="text" value="${extraExpense.unitPrice}" class="easyui-numberbox" data-options="required:true,precision:'4'"/>
					</td>
				</tr>
				<tr>
					<th>计价范围开始(天)</th>
					<td><input id="expenseStart" name="expenseStart"  type="text" value="${extraExpense.expenseStart}" class="easyui-numberbox" data-options="precision:'2'"/></td>		
					<th>计价范围结束(天)</th>
					<td><input id="expenseEnd" name="expenseEnd"  type="text" value="${extraExpense.expenseEnd}"  class="easyui-numberbox" data-options="precision:'2'"/></td>
				</tr>
				<tr>
					<th>计量单位</th>
					<td colspan="3">
						<mytag:combobox name="countUnit" value="${extraExpense.countUnit}" type="dict" parameters="countUnit" required="false"/>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3" style="height:1cm"><textarea  name="remarks" type="text" id="remarks"  class="easyui-validatebox"
					style="overflow:auto;width:90%;height:100%;">${extraExpense.remarks}</textarea></td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<script type="text/javascript">
$(function(){  
	$('#extraForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#extraForm').form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },  
	    success:function(data){
	   	 	$.easyui.loaded();
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		extraExpense.extraList.datagrid('reload');
	    		extraExpense.extraForm.panel('close');
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

