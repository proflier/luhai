<%-- <%@page import="java.util.Date"%> --%>
<%-- <%@page import="com.cbmie.common.utils.StringUtils"%> --%>
<%-- <%@page import="com.cbmie.genMac.baseinfo.entity.ExchangeRate"%> --%>
<%-- <%@page import="java.text.SimpleDateFormat"%> --%>
<%-- <%@page import="java.text.DateFormat"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%-- <%@page import="com.cbmie.system.entity.User"%> --%>
<%-- <%@page import="com.cbmie.system.utils.UserUtil"%> --%>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/exchangerate/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="35%"><font style="vertical-align:middle;color:red" ></font>币种</th>
			<td>
			<input type="hidden" name="id" id="id" value="${exchangeRate.id }" />
			<input name="currency" type="text" value="${exchangeRate.currency }" class="easyui-validatebox"  data-options="required:true" />
			</td>
		</tr> 
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>年</th>
			<td><input name="year" type="text" value="${exchangeRate.year }" class="easyui-numberbox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>月</th>
			<td>
			<input name="month" type="text" value="${exchangeRate.month}" class="easyui-combobox" data-options="required:true,valueField: 'label',textField: 'value', panelHeight:'auto',
			data: [{label: '1',value: '1'},
				{label: '2',value: '2'},
				{label: '3',value: '3'},
				{label: '4',value: '4'},
				{label: '5',value: '5'},
				{label: '6',value: '6'},
				{label: '7',value: '7'},
				{label: '8',value: '8'},
				{label: '9',value: '9'},
				{label: '10',value: '10'},
				{label: '11',value: '11'},
				{label: '12',value: '12'}]"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>对美元汇率</th>
			<td><input name="rateUS" type="text" value="${exchangeRate.rateUS }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>对欧元汇率</th>
			<td><input name="rateUN" type="text" value="${exchangeRate.rateUN }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>对新西兰元汇率</th>
			<td><input name="rateNZ" type="text" value="${exchangeRate.rateNZ }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
<!--
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>当前汇率时间</th>
			<td><input name="showTime" type="text" value="${exchangeRate.showTime }" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  data-options="required:false" style="width:170px"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" >*</font>对本币汇率</th>
			<td><input name="exchangeRateSelf" type="text" value="${exchangeRate.exchangeRateSelf }" class="easyui-numberbox" data-options="required:true,precision:'6'"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" >*</font>美元汇率</th>
			<td><input name="exchangeRateUS" type="text" value="${exchangeRate.exchangeRateUS }" class="easyui-numberbox" data-options="required:true,precision:'6'"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" >*</font>对本币汇率买入价</th>
			<td><input name="buyingRateSelf" type="text" value="${exchangeRate.buyingRateSelf }" class="easyui-numberbox" data-options="required:true,precision:'6'"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" >*</font>对美元汇率买入价</th>
			<td><input name="buyingRateUS" type="text" value="${exchangeRate.buyingRateUS }" class="easyui-numberbox" data-options="required:true,precision:'6'"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" >*</font>对本币汇率卖出价</th>
			<td><input name="sellingRateSelf" type="text" value="${exchangeRate.sellingRateSelf }" class="easyui-numberbox" data-options="required:true,precision:'6'"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" >*</font>对美元汇率卖出价</th>
			<td><input name="sellingRateUS" type="text" value="${exchangeRate.sellingRateUS }" class="easyui-numberbox" data-options="required:true,precision:'6'"/></td>
		</tr>  -->
	
		<tr>
			<th>登记人</th>
			<td>
				<input id="createrName" name="createrName" type="text" value="${exchangeRate.createrName }" 
				 readonly="true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr>
			<th>登记部门</th>
			<td>
				<input id="createrDept" name="createrDept" type="text"  value="${exchangeRate.createrDept }" 
				class="easyui-validatebox" readonly="true" />
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<th>创建时间</th> -->
<%-- 			<td>${createrDate }</td> --%>
<!-- 		</tr> -->
	</table>
	</form>
</div>
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
});
</script>
</body>
</html>