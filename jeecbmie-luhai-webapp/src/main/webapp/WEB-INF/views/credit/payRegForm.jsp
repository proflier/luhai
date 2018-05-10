<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<form id="mainform" action="${ctx}/credit/payReg/${action}" method="post">
	<input type="hidden"  id="id"  name="id" value="${payReg.id }"/>
	<input type="hidden" id="relLoginNames" name="relLoginNames" value="${payReg.relLoginNames }"/>
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th >开证申请号</th>
				<td colspan="3">
					<input type="text" id="payApplyNo" name="payApplyNo" value="${payReg.payApplyNo }" readonly="readonly" onfocus="loadCredit()" class="easyui-validatebox" data-options="required:true"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadCredit()">选择开证申请号</a>
				</td>
			</tr>
	 		<tr>
				<th width="20%">内部合同号</th>
				<td width="30%">
					<input type="text" id="inteContractNo" name="inteContractNo" readonly="readonly" value="${payReg.inteContractNo }"  class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th width="20%">信用证类型</th>
				<td width="30%">
					<input type="text" id="lcCategory" name="lcCategory" value="${payReg.lcCategory }" />
				</td>
			</tr>
			<tr>
				<th>采购合同号</th>
				<td>
				<input type="text" id="contractNo" name="contractNo"  value="${payReg.contractNo }" readonly="readonly" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>银行名称</th>
				<td>
					<mytag:combobox name="bankName" value="${payReg.bankName}" type="customer" parameters="10230004" />
				</td>
			</tr>
			<tr>
				<th>开证单位</th>
				<td>
					<mytag:combobox name="ourUnit" value="${payReg.ourUnit}" type="customer" parameters="10230001" />
				</td>
				<th>付款方式</th>
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
					<input type="text" id="regTime" name="regTime" value="<fmt:formatDate value="${payReg.regTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>最晚装船期</th>
				<td>
					<input type="text" id="latestShipTime" name="latestShipTime" value="<fmt:formatDate value="${payReg.latestShipTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
				<th>信用证有效期</th>
				<td>
					<input type="text" id="regValidity" name="regValidity" value="<fmt:formatDate value="${payReg.regValidity }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					<textarea rows="2" cols="45" id="summary" class="easyui-validatebox" name="summary" data-options="validType:['length[0,46]']">${payReg.summary }</textarea>
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
	<tr>
		<th width="20%" >登记时间</th>
		<td width="30%"><fmt:formatDate value="${ payReg.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th width="20%" >最后修改时间</th>
		<td width="30%"><fmt:formatDate value="${payReg.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
	
	//信用证类型
	$('#lcCategory').combobox({
// 		panelHeight:'auto',
		url:'${ctx}/system/dictUtil/getDictByCode/lcCategory',
		valueField : 'code',
		textField : 'name'
	});
	
	//币种
	$('#creditCurrency').combobox({
		required:true,
		url:'${ctx}/system/dictUtil/getDictByCode/currency',
		valueField : 'code',
		textField : 'name'
	});
	
	//授信类型
	$('#creditType').combobox({
// 		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dictUtil/getDictByCode/sxlx',
		valueField : 'code',
		textField : 'name'
	});
	
// 	//银行信息
// 	$('#bankName').combobox({
// 		required : true,
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230004',
// 		valueField : 'customerCode',
// 		textField : 'customerName'
// 	});
	
	//付款方式
	$('#paymenMethod').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dictUtil/getDictByCode/skfs',
		valueField : 'code',
		textField : 'name',
		onLoadSuccess: function () { //加载完成后,设置选中项
	    	$('#paymenMethod').combobox('select','10600001');
        }
		
	});
	
	//开证单位
// 	$('#ourUnit').combobox({
// 		required : true,
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230001',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
	
	//通知行 
// 	$('#noticeBank').combobox({
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230004',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
});

function loadCredit(){
	dlgCredit=$("#dlgCredit").dialog({
	  	width: 900,    
	    height: 400, 
	    title: '选择进口合同',
	    href:'${ctx}/credit/payReg/toCredit/',
	    modal:true,
	    closable:true,
	    style:{borderWidth:0},
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				initCredit();
				dlgCredit.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlgCredit.panel('close');
			}
		}]
	});
}

//保存
function initCredit(){
	var row = credit_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$('#payApplyNo').val(row.payApplyNo);
	$('#contractNo').val(row.contractNo);
	$('#inteContractNo').val(row.inteContractNo);
	$('#bankName').combobox('setValue', row.bankExpects);
	$('#ourUnit').combobox('setValue', row.ourUnit);
	$('#creditType').combobox('setValue', row.creditType);
	$('#creditCurrency').combobox('setValue', row.currency);
	$('#creditMoney').numberbox( 'setValue',row.applyMoney);
	$('#exchangeRate').val(row.exchangeRate);
	$('#paymenMethod').combobox('setValue', row.paymenMethod);
	$('#relLoginNames').val(row.relLoginNames);
}
</script>
</body>
</html>