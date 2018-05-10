<%@page import="com.cbmie.lh.credit.entity.PayApply"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<form id="mainform" action="${ctx}/credit/payApply/${action}" method="post">
	<div id="mainDiv" class="" data-options="border:false">
	<div data-options="title: '开证申请', iconCls: false, refreshable: false" >
	<input type="hidden" id="id" name="id" value="${payApply.id }"/>
	<input type="hidden" id="relLoginNames" name="relLoginNames" value="${payApply.relLoginNames }"/>
	<fieldset class="fieldsetClass">
	<legend>基本信息</legend>
	<table width="98%" class="tableClass">
		<tr>
			<th width="20%">开证申请号</th>
			<td width="30%">
				<input type="text" id="payApplyNo" name="payApplyNo"  value="${payApply.payApplyNo }" readonly="readonly" class="easyui-validatebox"/>
			</td>
			<th width="20%">开证类型</th>
			<td width="30%">
				<input type="text" id="creditCategory" name="creditCategory" value="${payApply.creditCategory }" class="easyui-combobox" />
			</td>
		</tr>
		<tr>
			<th>内部合同号</th>
			<td >
				<input type="text" id="inteContractNo" name="inteContractNo" readonly="readonly" style="width:50%;min-width:200px" onfocus="loadContract()" value="${payApply.inteContractNo }" class="easyui-validatebox" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadContract()">选择内部合同号</a>
			</td>
			<th >采购合同号</th>
			<td  >
				<input type="text" id="contractNo" name="contractNo"  value="${payApply.contractNo }" readonly="readonly"  class="easyui-validatebox" />
			</td>
		</tr>
		<tr>
			<th >开证金额</th>
			<td >
				<input type="text" id="applyMoney" name="applyMoney"  value="${payApply.applyMoney }"  onchange="setApplyMoney()" class="easyui-numberbox" 
					data-options="required:true,precision:2"/>
			</td>
			
			<th>开证数量</th>
			<td>
				<input type="text" id="quantity" name="quantity" value="${payApply.quantity }" onchange="setApplyMoney()" class="easyui-numberbox" data-options="precision:3"/>
			</td>
		</tr>
		<tr>
			<th>开证单价</th>
			<td>
				<input type="text" id="unitPrice" name="unitPrice" value="${payApply.unitPrice }" readonly="readonly" class="easyui-numberbox" data-options="precision:2"/>
			</td>
			<th>币种</th>
			<td>
				<input type="text" id="currency" name="currency" value="${payApply.currency }" class="easyui-combobox" />
			</td>
		</tr>
		<tr>
			<th>溢短装</th>
			<td>
				<input type="text" id="moreOrLess"   name="moreOrLess" value="${payApply.moreOrLess }"class="easyui-numberbox" data-options="required:true,precision:'2',suffix:'%'" />
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
			<th>经营方式</th>
			<td>
				<input type="text" id="runMode" name="runMode" value="${payApply.runMode }" class="easyui-combobox"/>
			</td>
		</tr>
		<tr>
			<th>授信类型</th>
			<td>
				<input type="text" id="creditType" name="creditType" value="${payApply.creditType }" class="easyui-combobox"/>
			</td>
			<th>开证单位</th>
			<td>
				<mytag:combobox name="ourUnit" value="${payApply.ourUnit}" type="customer" parameters="10230001" />
			</td>
		</tr>
		<tr>
			<th>有效期天数</th>
			<td>
				<input type="text" id="usageTime" name="usageTime" value="${payApply.usageTime }" class="easyui-numberbox" data-options="required:true"/>天
			</td>
			<th>收证单位</th>
			<td>
<%-- 				<input type="text" id="receiveUnit" name="receiveUnit" value="${payApply.receiveUnit }" class="easyui-combobox"/> --%>
				<mytag:combobox name="receiveUnit" value="${payApply.receiveUnit}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/>
			</td>
		</tr>
		<tr>
			<th>供应商</th>
			<td>
<%-- 				<input type="text" id="supplier" name="supplier"  value="${payApply.supplier }" /> --%>
				<mytag:combobox name="supplier" value="${payApply.supplier}" type="customer" parameters="10230002" hightAuto="false" permission4User="true"/>
			</td>
			<th>进口国别</th>
			<td>
				<input type="text" id="importingCountry" name="importingCountry" value="${payApply.importingCountry }"/>
			</td>
		</tr>
		<tr>
			<th>国内客户</th>
			<td >
				<mytag:combobox name="internalClient" value="${payApply.internalClient}" type="customer" parameters="10230003" />
			</td>
			<th  >业务经办人 </th>
			<td>
				<mytag:combotree name="businessManager" value="${payApply.businessManager}"  required="true" disabled="true" type="userTree" />
			</td>
		</tr>
		<tr>
			<th>希望使用银行</th>
			<td colspan="3">
				<textarea rows="2" cols="90" id="bankExpects" class="easyui-validatebox" name="bankExpects" data-options="validType:['length[0,46]']">${payApply.bankExpects }</textarea>
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
				<textarea rows="2" cols="90" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,46]']">${payApply.comment }</textarea>
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
		<tr>
			<th width="20%" >登记时间</th>
			<td width="30%"><fmt:formatDate value="${ payApply.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<th width="20%" >最后修改时间</th>
			<td width="30%"><fmt:formatDate value="${payApply.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>
	</fieldset>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PayApply.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${payApply.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>
	</div>
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
	    	var data = eval('(' + data + ')');
	    	if(data.currnetSequence!=null){
	    		$('#payApplyNo').val(data.currnetSequence);
	    	}
	    }
	});
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
	    		tabIndex = index;//更换tab
	    	}else{
	    		if(index != 0){
	    			parent.$.messager.alert('提示','请先保存主表信息!!!');
	    		}
	    		$("#mainDiv").tabs("select", tabIndex);//当前tab
	    		return ;
	    	}
	    }
	});
	
	
	//开证类型
	$('#creditCategory').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dictUtil/getDictByCode/creditCategory',
		valueField : 'code',
		textField : 'name'
	});
	
	//收款方式 
	$('#paymenMethod').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dictUtil/getDictByCode/skfs',
		valueField : 'code',
		textField : 'name'
	});
	
	//币种
	$('#currency').combobox({
		url:'${ctx}/system/dictUtil/getDictByCode/currency',
		valueField : 'code',
		textField : 'name',
		required:true,
		onLoadSuccess: function () { 
		}
	});
	
	//授信类型
	$('#creditType').combobox({
// 		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dictUtil/getDictByCode/sxlx',
		valueField : 'code',
		textField : 'name'
	});
	
	//保证金币种
	$('#recognizanceCurrency').combobox({
		url:'${ctx}/system/dictUtil/getDictByCode/currency',
		valueField : 'code',
		textField : 'name'
	});
	
	//经营方式
	$('#runMode').combobox({
// 		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dictUtil/getDictByCode/runMode',
		valueField : 'code',
		textField : 'name'
	});
	
	//进口国别
	$('#importingCountry').combotree({
		method:'GET',
	  	url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
	    idField : 'code',
	    textFiled : 'name',
		parentField : 'pid',
// 	    panelHeight:'auto',
	    animate:true,
	    required:true,
	    onHidePanel:function(){
	    }
	});
	
	

// 	//开证单位
// 	$('#ourUnit').combobox({
// 		required : true,
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230001',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
// 	//收证单位
// 	$('#receiveUnit').combobox({
// 		required : true,
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230002',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
// 	//供应商
// 	$('#supplier').combobox({
// 		required : true,
// 		method: "get",
// 		cache : true,
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230002',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
// 	//国内客户
// 	$('#internalClient').combobox({
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230003',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
	//银行信息
	/* $('#bankExpects').combobox({
		required : true,
		method: "get",
		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230004',
		valueField : 'customerCode',
		textField : 'customerName'
	}); */
});

function loadContract(){
	dlgContract=$("#dlgContract").dialog({
	  	width: 900,    
	    height: 400, 
	    title: '选择采购合同',
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

function setApplyMoney(){
var applyMoney =	$("#applyMoney").val() *1.00;
var quantity = $("#quantity").val() *1.000;
if(quantity==0){
	$.messager.alert('警告','开证数量为0，注意修改！');
}else{
	$("#unitPrice").numberbox('setValue',parseFloat(applyMoney/quantity).toFixed(2));
}
	
}
</script>
</body>
</html>