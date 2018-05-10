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
		<form id="mainform"  action="${ctx}/financial/expense/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '费用支付信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%"><font style="vertical-align: middle; color: red">*</font>付款申请号</th>
						<td><input id="expenseId" name="expenseId" type="text" value="${expense.expenseId }" class="easyui-validatebox" data-options="required:true" />
						</td>
						<th width="20%"><font style="vertical-align: middle; color: red">*</font>业务员</th>
						<td >
							<input id="expenseClerk" name="expenseClerk" type="text" value="${expense.expenseClerk }" />
						</td>
						
						<input type="hidden" name="id" value="${expense.id }" />
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>币种</th>
						<td>
							<input name="currency" readonly="readonly" type="text" value="人民币" />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>支付方式</th>
						<td>
							<input id="payModel" name="payModel" type="text" value="${expense.payModel }" />
						</td>
					</tr>
					<tr>	 
						<th><font style="vertical-align: middle; color: red">*</font>签署日期</th>
						<td colspan="3">
							<input type="text" id="applyPayDate" name="applyPayDate" value="<fmt:formatDate value='${expense.applyPayDate }' />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
						</td>
					</tr>
					
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>收款方信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%"><font style="vertical-align: middle; color: red">*</font>收款方信息</th>
						<td >
							<input id="payeeInformation" name="payeeInformation" type="text" value="${expense.payeeInformation }" class="easyui-validatebox"/>
						</td>
						<th width="20%">英文信息</th>
						<td >
							<input id="payeeEnName" name="payeeEnName" readonly="readonly"  type="text" value="${expense.payeeEnName }" class="easyui-validatebox" data-options="prompt: '选择收款方自动加载'"/>
						</td>
					</tr>
					<tr>	
						<th><font style="vertical-align: middle; color: red">*</font>对方银行</th>
						<td >
							<input id="payeeBank" name="payeeBank" type="text" value="${expense.payeeBank }" class="easyui-validatebox"  />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>对方账号</th>
						<td >
							<input id="payeeBankNo" name="payeeBankNo" readonly="readonly"  type="text" value="${expense.payeeBankNo }" class="easyui-validatebox" data-options="prompt: '选择银行后自动加载'"/>
						</td>
					</tr>
					
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>金额信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">金额</th>
						<td >
							<input id="originalCurrency" name="originalCurrency" readonly="readonly" type="text" value="${expense.originalCurrency }" class="easyui-numberbox"
							data-options="min:0,precision:2,groupSeparator:',',prompt: '填写付款明细后自动加载'" />
						</td>
						<th width="20%">财务实付日期</th>
						<td >
							<input type="text" id="payDate" name="payDate" value="<fmt:formatDate value='${expense.payDate }' />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
						</td>
					</tr>
					<tr>	
						<th>实付金额</th>
						<td >
							<input id="payCurrency" name="payCurrency" type="text" value="${expense.payCurrency }" 
							  onblur="if(this.value == '')this.value='0.00';" onclick="if(this.value == '0.00')this.value='';"
							  onchange="setOweCurrency();"
							class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" />
						</td>
						<th>未付金额</th>
						<td >
							<input id="oweCurrency" name="oweCurrency" type="text" readonly="readonly" value="${expense.oweCurrency }" class="easyui-numberbox"
							data-options="min:0,precision:2,groupSeparator:',' ,prompt: '填写实付金额后自动计算'" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">登记人</th>
						<td>${sessionScope.user.name}</td>
						<th width="20%">登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '付款明细', iconCls: false, refreshable: false">
				<input type="hidden" name="paymentChildJson" id="paymentChildJson"/>
				<%@ include file="/WEB-INF/views/financial/paymentForm.jsp"%> 
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">

	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				if("update"=="${action}")
		    		successTip(data,dg);
		    	else
		    		successTip(data,dg,d);
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

		
		//业务员
		$('#expenseClerk').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/user/getRelativeUser/18',//业务员对应角色id
			valueField : 'name',
			textField : 'name',
		});

		//支付方式
		$('#payModel').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/PAYMODEL',
			valueField : 'name',
			textField : 'name'
		});

		
		
		//收款方信息
		$('#payeeInformation').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/baseinfo/affiliates/getSelfCompany',
			valueField : 'id',
			textField : 'customerName',
			onChange:function (data) {
				$('#payeeBankNo').val("");
				//$('#bank').combobox("clear");
				selectBank(data);
				$('#payeeEnName').val(setPayeeEnName(data));
				}			
		});
		
		//银行信息初始化 用来回显
		$('#payeeBank').combobox({
			panelHeight : 'auto',
			required : true,
			url :'${ctx}/baseinfo/affiliates/selectBank',
			valueField : 'bankNO',
			textField : 'bankName',
			prompt: '请先选择收款方信息'
		});
		
		
		//过滤收款方银行信息
		function selectBank(pid){
			$('#payeeBank').combobox({
				panelHeight : 'auto',
				required : true,
				url : '${ctx}/baseinfo/affiliates/selectBank/'+pid,
				valueField : 'bankNO',
				textField : 'bankName',
				onChange:function (data) {
					$('#payeeBankNo').val(data);
				}
			});
		}
		
		//获取英文信息
		function setPayeeEnName(id){
			var val;
			$.ajax({
				type:'POST',
				async: false,
				url:"${ctx}/baseinfo/affiliates/getCustomerEnName/"+id,
				contentType:'application/json;charset=utf-8',
				success: function(data){
					if(data != null){
						val = data;
					} else {
						val = '';
					}
				}
			});
			return val;
		}
		
	});
	 
	function setOweCurrency(){
		var originalCurrency = $('#originalCurrency').val();
		var oweCurrency;
		var payCurrency = $('#payCurrency').val(); 

		if(originalCurrency==''){
			$('#payCurrency').val('');
			$.messager.alert('提示','应付金额为空！请填写付款信息后再输入！','info');
		}
		oweCurrency = originalCurrency - payCurrency;
		if(oweCurrency<0){
			$('#payCurrency').numberbox('setValue',0);
			$('#oweCurrency').numberbox('setValue',originalCurrency);
			$.messager.alert('提示','实付金额大于应付金额，请核对！','info');
		}else{
			$('#oweCurrency').numberbox('setValue',oweCurrency);
		}
	}
	
	
</script>
</body>
</html>