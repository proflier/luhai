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
		<form id="mainform"  action="${ctx}/foreignTrade/importContract/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '进口合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass"   >
				<legend>协议信息</legend>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toAgencyAgreement()">协议配置</a>
				<div id="agencyConfigInfo">
				<%@ include file="/WEB-INF/views/importContract/agencyConfigInfo.jsp"%>
				</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>合同号</th>
						<td><input id="contractNO" name="contractNO" type="text" value="${importContract.contractNO }" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>供应商</th>
						<td >
							<input id="supplier" name="supplier" type="text" value="${importContract.supplier }" class="easyui-validatebox"
							data-options="required:true" />
						</td>
							<input type="hidden" name="id" value="${importContract.id }" /> 
						<th><font style="vertical-align: middle; color: red">*</font>签署日期</th>
						<td>
							<input type="text" id="signatureDate" name="signatureDate" value="<fmt:formatDate value="${importContract.signatureDate }" />"
							class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>业务员</th>
						<td>
							<input id="relativePerson" name="relativePerson" type="text"value="${importContract.relativePerson }"/>
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>币种</th>
						<td>
							<input id="currency" name="currency" type="text" value="${importContract.currency }" />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>原币金额</th>
						<td>
							<input id="originalCurrency" name="originalCurrency" type="text" value="${importContract.originalCurrency }" class="easyui-numberbox" 
							data-options="min:0,precision:2,groupSeparator:',',disabled:true"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>合同条款</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>价格条款</th>
						<td >
							<input id="pricingTerm" name="pricingTerm" type="text" value="${importContract.pricingTerm }" class="easyui-validatebox"
							data-options="required:true" />
						</td>
						<th>国际运费USD</th>
						<td>
							<input id="intFare" name="intFare" type="text" value="${importContract.intFare }" class="easyui-numberbox" 
							data-options="min:0,precision:2,groupSeparator:',' "/>
						</td>
						<th>国际保费USD</th>
						<td>
							<input id="intSA" name="intSA" type="text" value="${importContract.intSA }" class="easyui-numberbox" 
							data-options="min:0,precision:2,groupSeparator:',' "/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>保险系数</th>
						<td>
							<input id="safetyFactor" name="safetyFactor" type="text" value="${importContract.safetyFactor }" class="easyui-numberbox"
							data-options="required:true,precision:2" />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>保险费率</th>
						<td>
							<input id="insuranceRate" name="insuranceRate" type="text" value="${importContract.insuranceRate }" class="easyui-numberbox" 
							data-options="required:true,precision:4,value:0.0015" />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>溢短装率%</th>
						<td>
							<input id="moreOrLessRate" name="moreOrLessRate" type="text" value="${importContract.moreOrLessRate }" class="easyui-numberbox"
							data-options="required:true,precision:2,suffix:'%',value:0" />
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>装运港</th>
						<td>
							<input id="transportPort" name="transportPort" type="text" value="${importContract.transportPort }" />
						</td>
						<th>装运港描述</th>
						<td>
							<input id="transportPortInfo" name="transportPortInfo" type="text" value="${importContract.transportPortInfo }" class="easyui-validatebox" />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>签约地</th>
						<td>
							<input id="signPosition" name="signPosition" class="easyui-combotree" value="${importContract.signPosition }" 
							data-options="url: '${ctx}/system/countryArea/json',method:'get',required:true,textFiled : 'name',panelHeight : 'auto'"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>目的港</th>
						<td>
							<input id="destinationPort" name="destinationPort" type="text" value="${importContract.destinationPort }" />
						</td>
						<th>目的港描述</th>
						<td>
							<input id="destinationPortInfo" name="destinationPortInfo" type="text" value="${importContract.destinationPortInfo }"class="easyui-validatebox" />
						</td>
						<th>装运期限</th>
						<td>
							<input id="shipmentTime" name="shipmentTime" type="text" value="<fmt:formatDate value="${importContract.shipmentTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red">*</font>运输方式</th>
						<td>
							<input id="transportMode" name="transportMode" type="text" value="${importContract.transportMode }" />
						</td>
						<th><font style="vertical-align: middle; color: red">*</font>最晚开立信用证的日期</th>
						<td colspan="3">
							<input id="openCreditDate" name="openCreditDate" type="text" value="<fmt:formatDate value="${importContract.openCreditDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>${sessionScope.user.name}</td>
						<th>登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '商品明细', iconCls: false, refreshable: false">
				<input type="hidden" name="commodityChildJson" id="commodityChildJson"/>
				<%@ include file="/WEB-INF/views/importContract/commodityForm.jsp"%>
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
		
		//币种
		$('#currency').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/CURRENCY',
			valueField : 'name',
			textField : 'name'
		});

		//运输方式
		$('#transportMode').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YSFS',
			valueField : 'name',
			textField : 'name'
		});

		//装运港
		$('#transportPort').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/baseinfo/port/getPortList',
			valueField : 'portName',
			textField : 'portName'
		});

		//供应商（国外）
		$('#supplier').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/baseinfo/affiliates/getSupplier',
			valueField : 'customerName',
			textField : 'customerName',
		});
		
		//业务员
		$('#relativePerson').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/user/getRelativeUser/18',//业务员对应角色id
			valueField : 'name',
			textField : 'name',
		});
		
		//目的港
		$('#destinationPort').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/baseinfo/port/getPortList',
			valueField : 'portName',
			textField : 'portName',
			/***加载页面所有信息后如果是新增则将基本信息置为不可选***/
			onLoadSuccess: function () { 
				if("create"=="${action}"){
					$("#contractNO").attr("disabled",true);
					$("#supplier").combobox('disable');
					$("#relativePerson").combobox('disable');
					$("#signatureDate").combobox('disable');
 					$("#currency").combobox('disable');
 					$("#originalCurrency").attr("disabled",true);
 					$("#pricingTerm").attr("disabled",true);
					$("#intFare").attr("disabled",true);
 					$("#intSA").attr("disabled",true);
 					$("#safetyFactor").attr("disabled",true);
 					$("#insuranceRate").attr("disabled",true);
 					$("#moreOrLessRate").attr("disabled",true);
 					$("#transportPort").combobox('disable');
 					$("#transportPortInfo").attr("disabled",true);
 					$("#signPosition").combotree("disable");
 					$("#destinationPort").combobox('disable');
 					$("#destinationPortInfo").attr("disabled",true);
 					$("#shipmentTime").combo({disabled:true});
 					$("#transportMode").combobox('disable');
 					$("#openCreditDate").combo({disabled:true});
				}
			}
		});
	});
</script>
</body>
</html>