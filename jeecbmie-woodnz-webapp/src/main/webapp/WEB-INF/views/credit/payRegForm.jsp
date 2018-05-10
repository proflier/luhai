<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/credit/payReg/${action}" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th width="20%">对应付款申请</th>
				<td width="30%">
					<input type="text" id="payApplyNo" name="payApplyNo" value="${payReg.payApplyNo }" />
				</td>
				<th width="20%">信用证类型</th>
				<td width="30%">
					<input type="text" id="lcCategory" name="lcCategory" value="${payReg.lcCategory }" />
				</td>
				
			</tr>
	 		<tr>
				<th>合同号</th>
				<td>
				<input type="hidden"  id="id"  name="id" value="${payReg.id }"/>
				<input type="text" id="contractNo" name="contractNo" readonly="readonly" value="${payReg.contractNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>综合合同号</th>
				<td>
				<input type="text" id="inteContractNo" name="inteContractNo" readonly="readonly" value="${payReg.inteContractNo }"  class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>信用证号</th>
				<td>
				<input type="text" id="creditNo" name="creditNo" value="${payReg.creditNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>银行名称</th>
				<td>
				<input type="text" id="bankName" name="bankName" value="${payReg.bankName }" />
				</td>
			</tr>
			<tr>
				<th>我司单位</th>
				<td>
					<input type="text" id="ourUnit" name="ourUnit" value="${payReg.ourUnit }" />
				</td>
				<th>收款方式</th>
				<td>
					<input type="text" id="paymenMethod"  name="paymenMethod" value="${payReg.paymenMethod }" class="easyui-combobox"/>
				</td>
			</tr>
			<tr>
				<th>授信类型</th>
				<td>
					<input type="text" id="creditType" name="creditType" value="${payReg.creditType }" />
				</td>
				<th>开证金额</th>
				<td>
					<input type="hidden" id="exchangeRate" name="exchangeRate" value="${payReg.exchangeRate }" class="easyui-validatebox"/>
					<input type="text" id="creditMoney" name="creditMoney" value="${payReg.creditMoney }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
			</tr>
			<tr>
				<th>开证币种</th>
				<td>
					<input type="text" id="creditCurrency" name="creditCurrency" value="${payReg.creditCurrency }" />
				</td>
				<th>开证时间</th>
				<td>
					<input type="text" id="regTime" name="regTime" value="<fmt:formatDate value="${payReg.regTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>最晚装船期</th>
				<td>
					<input type="text" id="latestShipTime" name="latestShipTime" value="<fmt:formatDate value="${payReg.latestShipTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
				</td>
				<th>信用证有效期</th>
				<td>
					<input type="text" id="regValidity" name="regValidity" value="<fmt:formatDate value="${payReg.regValidity }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>通知行</th>
				<td colspan="3">
					<input type="text" id="noticeBank" name="noticeBank" value="${payReg.noticeBank }" />
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					<textarea id="summary" name="summary" class="easyui-validatebox" style="width: 36%;margin-top: 5px;" >${payReg.summary }</textarea>
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th  width="20%">登记人</th>
		<td width="30%">${empty payReg.createrName ? sessionScope.user.name : payReg.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${empty payReg.createrDept ? sessionScope.user.organization.orgName : payReg.createrDept }</td>
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
	
	$('#payApplyNo').combobox({
		method:'GET',
		required:true,
		url:'${ctx}/credit/payApply/json?filter_EQS_state=生效',
		valueField : 'payApplyNo',
		textField : 'payApplyNo',
		loadFilter:function(data){
			return data.rows;
		},
		onSelect:function(record){
			 $("#inteContractNo").removeAttr("disabled",true);
			 $("#contractNo").removeAttr("disabled",true);
			 $("#creditNo").removeAttr("disabled",true);
			 $("#lcCategory").combo('enable');
			 $("#bankName").combo('enable');
			 $("#ourUnit").combo('enable');
			 $("#paymenMethod").combo('enable');
			 $("#creditType").combo('enable');
			 $("#creditMoney").numberbox({disabled:false});
			 $("#creditCurrency").combo('enable');
			 $("#regTime").combo('enable');
			 $("#latestShipTime").combo('enable');
			 $("#regValidity").combo('enable');
			 $("#summary").removeAttr("disabled",true);
			 $("#noticeBank").combo('enable');
			
			$('#contractNo').val(record.contractNo);
			$('#inteContractNo').val(record.inteContractNo);
			$('#bankName').combobox('setValue', record.bankExpects);
			$('#ourUnit').combobox('setValue', record.ourUnit);
			$('#creditType').combobox('setValue', record.creditType);
			$('#creditCurrency').combobox('setValue', record.currency);
			$('#creditMoney').numberbox( 'setValue',record.applyMoney);
			$('#exchangeRate').val(record.exchangeRate);
			$('#paymenMethod').combobox('setValue', record.paymenMethod);
		}
	});
	
	
	
	
	//信用证类型
	$('#lcCategory').combobox({
		panelHeight:'auto',
		url:'${ctx}/system/downMenu/getDictByCode/lcCategory',
		valueField : 'id',
		textField : 'name'
	});
	
	//币种
	$('#creditCurrency').combobox({
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
	
	//银行信息
	$('#bankName').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=4',
		valueField : 'id',
		textField : 'customerName'
	});
	
	//收款方式 
	$('#paymenMethod').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/downMenu/getDictByCode/skfs',
		valueField : 'id',
		textField : 'name'
	});
	
	//我司单位
	$('#ourUnit').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
		valueField : 'id',
		textField : 'customerName',
	});
	
	
	//通知行 
	$('#noticeBank').combobox({
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=4',
		valueField : 'id',
		textField : 'customerName',
		onLoadSuccess: function () { 
			if("create"=="${action}"){
				 $("#inteContractNo").attr("disabled",true);
				 $("#contractNo").attr("disabled",true);
				 $("#creditNo").attr("disabled",true);
				 $("#lcCategory").combo('disable');
				 $("#bankName").combo('disable');
				 $("#ourUnit").combo('disable');
				 $("#paymenMethod").combo('disable');
				 $("#creditType").combo('disable');
				 $("#creditMoney").numberbox({disabled:true});
				 $("#creditCurrency").combo('disable');
				 $("#regTime").combo('disable');
				 $("#latestShipTime").combo('disable');
				 $("#regValidity").combo('disable');
				 $("#summary").attr("disabled",true);
				 $("#noticeBank").combo('disable');
			}
		}
	});
	
});
</script>
</body>
</html>