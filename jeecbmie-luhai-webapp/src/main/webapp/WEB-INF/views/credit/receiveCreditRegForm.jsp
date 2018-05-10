<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<form id="mainform" action="${ctx}/credit/receiveCreditReg/${action}" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
	 		<tr>
	 			<th >销售合同号</th>
				<td  colspan="3">
				<input type="text" style="width:50%;" id="woodBillId" name="woodBillId" onfocus="loadSaleContract()" value="${receiveCreditReg.woodBillId }"  class="easyui-validatebox" data-options="required:true"/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadSaleContract()">选择销售合同</a>
				</td>
			</tr>
			<tr>
				<th width="20%">下游信用证号</th>
				<td width="30%" >
				<input type="hidden" id="id" name="id" value="${receiveCreditReg.id }"/>
				<input type="hidden" id="relLoginNames" name="relLoginNames" value="${receiveCreditReg.relLoginNames }"/>
				<input type="text" id="creditNo" name="creditNo" value="${receiveCreditReg.creditNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th  width="20%">开证公司</th>
				<td  width="30%">
<%-- 					<input type="text" id="creditCompany" name="creditCompany" value="${receiveCreditReg.creditCompany }" class="easyui-combobox"/> --%>
					<mytag:combobox name="creditCompany" value="${receiveCreditReg.creditCompany}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/>
				</td>
			</tr>
			<tr>
				<th>内部合同号</th>
				<td>
				<input type="text" name="inteContractNo" value="${receiveCreditReg.inteContractNo }"  class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>授信类型</th>
				<td>
					<input type="text" id="creditType" name="creditType" value="${receiveCreditReg.creditType }" class="easyui-combobox"/>
				</td>
			</tr>
			<tr>
				<th>开证日期</th>
				<td>
					<input type="text" name="creditTime" value="<fmt:formatDate value="${receiveCreditReg.creditTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
				<th>登记日期 </th>
				<td>
					<input type="text" name="regTime" value="<fmt:formatDate value="${receiveCreditReg.regTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" "/>
				</td>
			</tr>
			<tr>
				<th>最晚装船期</th>
				<td>
					<input type="text" name="lastShipTime" value="<fmt:formatDate value="${receiveCreditReg.lastShipTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
				<th>有效期 </th>
				<td colspan="3">
					<input type="text" name="effictTime" value="<fmt:formatDate value="${receiveCreditReg.effictTime }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" "/>
				</td>
			</tr>
			<tr>
				<th>开证银行</th>
				<td>
					<mytag:combobox name="issuingBank" value="${receiveCreditReg.issuingBank}" type="customer" parameters="10230004" />
				</td>
				<th>收证银行</th>
				<td>
					<mytag:combobox name="receiveBank" value="${receiveCreditReg.receiveBank}" type="customer" parameters="10230004" />
				</td>
			</tr>
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${empty receiveCreditReg.createrName ? sessionScope.user.name : receiveCreditReg.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${empty receiveCreditReg.createrDept ? sessionScope.user.organization.orgName : receiveCreditReg.createrDept }</td>
	</tr>
	<tr>
		<th width="20%" >登记时间</th>
		<td width="30%"><fmt:formatDate value="${ receiveCreditReg.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th width="20%" >最后修改时间</th>
		<td width="30%"><fmt:formatDate value="${receiveCreditReg.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
	
	//授信类型
	$('#creditType').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dictUtil/getDictByCode/sxlx',
		valueField : 'code',
		textField : 'name'
	});
	

	//开证公司
// 	$('#creditCompany').combobox({
// 		required : true,
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230003',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});
	
	//开证银行
// 	$('#issuingBank').combobox({
// // 		panelHeight:'auto',
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230004',
// 		valueField : 'customerCode',
// 		textField : 'customerName'
// 	});
	
	//收证银行
// 	$('#receiveBank').combobox({
// // 		panelHeight:'auto',
// 		method: "get",
// 		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230004',
// 		valueField : 'customerCode',
// 		textField : 'customerName'
// 	});
	
});

function loadSaleContract(){
	dlgSaleContract=$("#dlgSaleContract").dialog({
	  	width: 900,    
	    height: 400, 
	    title: '选择下游提单',
	    href:'${ctx}/credit/receiveCreditReg/toSaleContract/',
	    modal:true,
	    closable:true,
	    style:{borderWidth:0},
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				initContract();
				dlgSaleContract.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlgSaleContract.panel('close');
			}
		}]
	});
	
}
</script>
</body>
</html>