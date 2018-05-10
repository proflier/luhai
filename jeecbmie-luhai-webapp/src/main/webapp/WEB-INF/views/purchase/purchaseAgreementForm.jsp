<%@page import="com.cbmie.lh.purchase.entity.PurchaseAgreement"%>
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
		<form id="mainform"  action="${ctx}/purchase/purchaseAgreement/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '采购协议信息', iconCls: false, refreshable: false" >				
				<fieldset class="fieldsetClass" >
				<legend>采购协议信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>协议号</th>
						<td>
						<input type="hidden" name="id" id="id" value="${purchaseAgreement.id}" />
							<input id="agreementNo" name="agreementNo" class="easyui-validatebox" type="text" value="${purchaseAgreement.agreementNo }" 
							data-options="required:true" style="overflow:auto;"/>
						</td>
						<th>协议性质</th>
						<td>
							<input id="agreementNature" name="agreementNature" class="easyui-combobox" type="text" value="${purchaseAgreement.agreementNature }" 
							data-options="required:true,valueField: 'label',textField: 'value', panelHeight:'auto',
							data:[{label:'自营',value:'自营'},{label:'联营',value:'联营'},{label:'代理',value:'代理'}]"/>
						</td>
					</tr>
					<tr>
						<th>协议客户</th>
						<td>
							<input id="customer" name="customer"  type="text" value="${purchaseAgreement.customer }" 
							data-options="required:false"/>
						</td>
						<th>协议第三方</th>
						<td>
							<input id="thirdParty" name="thirdParty" type="text" value="${purchaseAgreement.thirdParty }" 
							data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th>相关单位</th>
						<td>
							<input id="company" name="company"  type="text" value="${purchaseAgreement.company }" 
							data-options="required:false"/>
						</td>
						<th>签订时间</th>
						<td>
							<input name="signDate" type="text"  value="<fmt:formatDate value="${purchaseAgreement.signDate}" />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th>我司单位</th>
						<td>
							<input id="ourUnit" name="ourUnit"  type="text" value="${purchaseAgreement.ourUnit }" 
							data-options="required:false"/>
						</td>
						<th>签订地点</th>
						<td>
							<input id="addr" name="addr" type="text" class="esyui-validatebox" value="${purchaseAgreement.addr}"/>
						</td>
					</tr>
					<tr>
						<th>协议期限</th>
						<td colspan="3">
							<input name="startDate" type="text"  value="<fmt:formatDate value="${purchaseAgreement.startDate}" />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						至
							<input name="endDate" type="text"  value="<fmt:formatDate value="${purchaseAgreement.endDate}" />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty purchaseAgreement.createrName ? sessionScope.user.name : purchaseAgreement.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty purchaseAgreement.createrDept ? sessionScope.user.organization.orgName : purchaseAgreement.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ purchaseAgreement.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${purchaseAgreement.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PurchaseAgreement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${purchaseAgreement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
				$.easyui.loaded();
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
		
		//我司单位下拉菜单
		$('#ourUnit').combobox({
			panelHeight : 'auto',
			required : false,
			method: "get",
			url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230001',
			valueField : 'customerCode',
			textField : 'customerName',
			required : true,
		});
		
		//客户下拉菜单
		$('#company').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230003',
			valueField : 'customerCode',
			textField : 'customerName',
			required : true,
		});
		
		//供应商下拉菜单
		$('#customer').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230002',
			valueField : 'customerCode',
			textField : 'customerName',
			required : true,
		});
		
		//客户下拉菜单
		$('#thirdParty').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230003',
			valueField : 'customerCode',
			textField : 'customerName',
			required : true,
		});
		
		
	 	//签订地点
	 	$('#addr').combotree({
			method:'GET',
		    url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
		    idField : 'code',
		    textFiled : 'name',
			parentField : 'pid',
		    panelHeight:'auto',
		    animate:false,
		    multiline:false,
		    required : true,
			panelHeight:'auto',
	 	onHidePanel:function(){}
		});
	});
	
</script>
</body>
</html>

