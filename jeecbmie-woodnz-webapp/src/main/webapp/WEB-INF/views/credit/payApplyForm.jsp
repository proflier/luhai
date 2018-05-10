<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/credit/payApply/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th >采购合同号</th>
		<td  >
			<input type="hidden" id="id" name="id" value="${payApply.id }"/>
			<input type="text" id="contractNo" name="contractNo" readonly="readonly" value="${payApply.contractNo }" class="easyui-validatebox" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadContract()">选择采购合同号</a>
		</td>
		<th>开证申请号</th>
		<td>
			<input type="text" id="payApplyNo" name="payApplyNo"  value="${payApply.payApplyNo }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th width="20%">综合合同号</th>
		<td width="30%">
			<input type="text" id="inteContractNo" name="inteContractNo" readonly="readonly" value="${payApply.inteContractNo }" class="easyui-validatebox" />
		</td>
		<th width="20%">开证金额</th>
		<td width="30%">
			<input type="text" id="applyMoney" name="applyMoney" value="${payApply.applyMoney }" class="easyui-numberbox" 
				data-options="required:true,precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<th>预计付款时间</th>
		<td >
			<input type="text" id="forecastPayTime" name="forecastPayTime" value="<fmt:formatDate value="${payApply.forecastPayTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
		</td>
		<th>币种</th>
		<td>
			<input type="text" id="currency" name="currency" value="${payApply.currency }" class="easyui-combobox" />
		</td>
	</tr>
	<tr>
		<th>溢短装</th>
		<td>
			<input type="text" id="moreOrLess"  readonly="readonly" name="moreOrLess" value="${payApply.moreOrLess }" class="easyui-validatebox" />
		</td>
		<th>汇率</th>
		<td>
			<input type="text" id="exchangeRate"  name="exchangeRate" value="${payApply.exchangeRate }" class="easyui-numberbox" data-options="precision:'5'"/>
		</td>
	</tr>
	<tr>
		<th>客户保证金</th>
		<td>
			<input type="text" id="handelRecognizance" name="handelRecognizance" value="${payApply.handelRecognizance }" class="easyui-numberbox" 
				data-options="precision:2"/>
		</td>
		<th>已到帐保证金</th>
		<td>
			<input type="text" id="arrivalRecognizance" name="arrivalRecognizance" value="${payApply.arrivalRecognizance }" class="easyui-numberbox"
				data-options="precision:2"/>
		</td>
	</tr>
	<tr>
		<th>保证金币种</th>
		<td>
			<input type="text" id="recognizanceCurrency" name="recognizanceCurrency" value="${payApply.recognizanceCurrency }" class="easyui-combobox" />
		</td>
		<th>希望使用银行</th>
		<td>
			<input type="text" id="bankExpects" name="bankExpects" value="${payApply.bankExpects }" class="easyui-combobox"/>
		</td>
	</tr>
	<tr>
		<th>收款方式</th>
		<td>
			<input type="text" id="paymenMethod" name="paymenMethod" value="${payApply.paymenMethod }" class="easyui-combobox" />
		</td>
		<th>授信类型</th>
		<td>
			<input type="text" id="creditType" name="creditType" value="${payApply.creditType }" class="easyui-combobox"/>
		</td>
	</tr>
	<tr>
		<th>使用时间</th>
		<td>
			<input type="text" id="usageTime" name="usageTime" value="${payApply.usageTime }" class="easyui-numberbox" data-options="required:true"/>天
		</td>
		<th>我司单位</th>
		<td>
			<input type="text" id="ourUnit" name="ourUnit" value="${payApply.ourUnit }" class="easyui-combobox"/>
		</td>
	</tr>
	<tr>
		<th>供应商</th>
		<td>
			<input type="text" id="supplier" name="supplier"  value="${payApply.supplier }" />
		</td>
		<th>进口国别</th>
		<td>
			<input type="text" id="importingCountry" name="importingCountry" value="${payApply.importingCountry }"/>
		</td>
	</tr>
	<tr>
		<th>国内客户</th>
		<td>
			<input type="text" id="internalClient" name="internalClient" value="${payApply.internalClient }" class="easyui-combobox"/>
		</td>
		<th>经营方式</th>
		<td>
			<input type="text" id="runMode" name="runMode" value="${payApply.runMode }" class="easyui-combobox"/>
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="3">
			<textarea id="comment" name="comment" class="easyui-validatebox" style="width: 36%;margin-top: 5px;" >${payApply.comment }</textarea>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th  width="20%">登记人</th>
		<td width="30%">${empty payApply.createrName ? sessionScope.user.name : payApply.createrName }</td>
		<th  width="20%">登记部门</th>
		<td width="30%">${empty payApply.createrDept ? sessionScope.user.organization.orgName : payApply.createrDept }</td>
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
	

//收款方式 
	$('#paymenMethod').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/downMenu/getDictByCode/skfs',
		valueField : 'id',
		textField : 'name'
	});
	
	//币种
	$('#currency').combobox({
		url:'${ctx}/system/downMenu/getDictByCode/currency',
		valueField : 'id',
		textField : 'name',
		required:true,
		onLoadSuccess: function () { 
			if("create"=="${action}"){
				 $("#inteContractNo").attr("disabled",true);
				 $("#payApplyNo").attr("disabled",true);
				 $("#applyMoney").numberbox({disabled:true});
				 $("#forecastPayTime").combo('disable');
				 $("#currency").combobox('disable');
				 $("#moreOrLess").attr("disabled",true);
				 $("#exchangeRate").numberbox({disabled:true});
				 $("#handelRecognizance").numberbox({disabled:true});
				 $("#arrivalRecognizance").numberbox({disabled:true});
				 $("#recognizanceCurrency").combobox('disable');
				 $("#bankExpects").combobox('disable');
				 $("#paymenMethod").combobox('disable');
				 $("#creditType").combobox('disable');
				 $("#usageTime").numberbox({disabled:true});
				 $("#ourUnit").combobox('disable');
				 $("#supplier").combobox('disable');
				 $("#importingCountry").combotree({disabled:true});
				 $("#internalClient").combobox('disable');
				 $("#runMode").combobox('disable');
				 $("#comment").attr("disabled",true);
			}
		}
	});
	
	//授信类型
	$('#creditType').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/sxlx',
		valueField : 'id',
		textField : 'name'
	});
	
	//保证金币种
	$('#recognizanceCurrency').combobox({
		url:'${ctx}/system/downMenu/getDictByCode/currency',
		valueField : 'id',
		textField : 'name'
	});
	
	//经营方式
	$('#runMode').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/htlb',
		valueField : 'id',
		textField : 'name'
	});
	
	//进口国别
	$('#importingCountry').combotree({
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    panelHeight:'auto',
	    animate:true,
	    required:true,
	    onHidePanel:function(){
	    }
	});
	
	
	//我司单位
	$('#ourUnit').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
		valueField : 'id',
		textField : 'customerName',
	});
	
	//供应商
	$('#supplier').combobox({
		required : true,
		method: "get",
		cache : true,
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=2',
		valueField : 'id',
		textField : 'customerName',
	});
	
	//国内客户
	$('#internalClient').combobox({
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=3',
		valueField : 'id',
		textField : 'customerName',
	});
	
	//银行信息
	$('#bankExpects').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=4',
		valueField : 'id',
		textField : 'customerName'
	});
});

function loadContract(){
	dlgContract=$("#dlgContract").dialog({
	  	width: 900,    
	    height: 400, 
	    title: '选择进口合同',
	    href:'${ctx}/credit/payApply/toContract/',
	    modal:true,
	    closable:true,
	    style:{borderWidth:0},
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				initContract();
				dlgContract.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlgContract.panel('close');
			}
		}]
	});
	
}
</script>
</body>
</html>