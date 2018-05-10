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
		<form id="mainform"  action="${ctx}/financial/serial/${action}" method="post">
			<fieldset class="fieldsetClass">
			<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%" >流水号</th>
						<td width="30%">
							<input type="hidden" id="id" name="id" value="${serial.id }" />
							<input name="serialNumber" type="text" value="${serial.serialNumber }" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th width="20%">水单抬头</th>
						<td width="30%"><input id="serialTitle" name="serialTitle" type="text" value="${serial.serialTitle }" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<th>银行</th>
						<td><input id="bank" name="bank" type="text" value="${serial.bank }"  class="easyui-combobox"/></td>
						<th>银行账号</th>
						<td><input id="bankNO" name="bankNO" type="text" value="${serial.bankNO }" readonly="true" class="easyui-validatebox" data-options="prompt:'选择银行后自动加载'"/></td>
					</tr>
					<tr>
						<th>资金类别</th>
						<td><input id="fundCategory" name="fundCategory" type="text" value="${serial.fundCategory }" class="easyui-validatebox" /></td>
						<th>金额</th>
						<td><input name="money" type="text" value="${serial.money }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',required:true"/></td>
					</tr> 
					<tr>
						<th >日期</th>
						<td>
							<input type="text" id="serialDate" name="serialDate" value="<fmt:formatDate value="${serial.serialDate }" />"
								class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
						</td>
						<th>银承到期日期</th>
						<td><input name="bankDeadline" type="text"  value="<fmt:formatDate value="${serial.bankDeadline }" />"
								class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" /></td>
					</tr>
					<tr>
						<th>资金来源</th>
						<td colspan="3"><input id="fundSource" name="fundSource"  readonly="true"  type="text" style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;" 
						 value="${serial.fundSource }" class="easyui-validatebox" /></td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
			<legend>系统信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty serial.createrName ? sessionScope.user.name : serial.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty serial.createrDept ? sessionScope.user.organization.orgName : serial.createrDept }</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>

<script type="text/javascript">

	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
	});
	
	//水单抬头
	$('#serialTitle').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			$('#bankNO').val("");
			selectBank(record.id);
			$('#bank').combobox('clear');
		}
	});
	
	//银行信息初始化 用来回显
	$('#bank').combobox({
		panelHeight : 'auto',
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBankInfo',
		valueField : 'id',
		textField : 'bankName',
		prompt:'请先选择抬头信息',
		onSelect:function(record){
			$('#bankNO').val(record.bankNO);
		}
	});
	
	//过滤我司单位的银行信息
	function selectBank(pid){
		$('#bank').combobox({
			panelHeight : 'auto',
			required : true,
			method: "get",
			url : '${ctx}/system/downMenu/selectBank/'+pid,
			valueField : 'id',
			textField : 'bankName',
			onChange:function (data) {
				$('#bankNO').val(data);
			}
		});
	}
	
	//资金类别
	$('#fundCategory').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/downMenu/getDictByCode/fundCategory',
		valueField : 'id',
		textField : 'name'
	});
	
	//资金来源
	/* $('#fundSource').combobox({
		panelHeight : 'auto',
		required : true,
		value:'银行来款'
	}); */
	$('#fundSource').val('银行来款');
	
</script>
</body>
</html>