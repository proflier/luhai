<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<form id="mainform" action="${ctx}/baseInfo/ck/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>单位编号</th>
			<td><input name="no" type="text" value="${woodCk.no }" class="easyui-validatebox" validType="englishCheckSub" data-options="required:true"/></td>
			<th><font style="vertical-align:middle;color:red" ></font>财务编号</th>
			<td><input name="cwbm" type="text" value="${woodCk.cwbm }" class="easyui-validatebox" validType="englishCheckSub" data-options="required:true"/></td>
		</tr>
		<input type="hidden" name="id" id="id" value="${woodCk.id }" />
		<tr>
			<th width="25%"><font style="vertical-align:middle;color:red" ></font>单位名称</th>
			<td>
			<input name="companyName" type="text" value="${woodCk.companyName }" class="easyui-validatebox"  
			data-options="required:true"  style="overflow:auto;"/>
			</td>
			<th><font style="vertical-align:middle;color:red" ></font>单位简称</th>
			<td><input name="shortName" type="text" value="${woodCk.shortName }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>国家</th>
			<td><input name="nationality" id="nationality" type="text" value="${woodCk.nationality }" /></td>
			<th width="25%"><font style="vertical-align:middle;color:red" ></font>地址</th>
			<td>
			<input name="addr" type="text" value="${woodCk.addr }" class="easyui-validatebox"  
			data-options="required:false"  style="overflow:auto;"/>
			</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>所属仓储公司</th>
			<td><input name="belongCompany" id="belongCompany" type="text" value="${woodCk.belongCompany }" data-options="required:true"/></td>
			<th><font style="vertical-align:middle;color:red" ></font>仓储费结算方案</th>
			<td><input name="balanceType" type="text" value="${woodCk.balanceType }" 
				class="easyui-combobox"  data-options="required:true,valueField: 'label',textField: 'value',panelHeight:'auto',
				data: [{label: '我司结算',value: '我司结算'},
				{label: '客户结算',value: '客户结算'}]"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>仓库类别</th>
			<td><input name="cklb" type="text" value="${woodCk.cklb }" class="easyui-combobox" 
				data-options="required:true,valueField: 'label',textField: 'value',panelHeight:'auto',
				data: [{label: '常规库',value: '常规库'},
				{label: '监管库',value: '监管库'},
				{label: '虚拟库',value: '虚拟库'}]"/></td>
			<th><font style="vertical-align:middle;color:red" ></font>联系人</th>
			<td><input name="lxr" type="text" value="${woodCk.lxr }" data-options="required:false"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>电话</th>
			<td><input name="tel" type="text" value="${woodCk.tel }" data-options="required:false"/></td>
			<th><font style="vertical-align:middle;color:red" ></font>传真</th>
			<td><input name="fax" type="text" value="${woodCk.fax }" data-options="required:false"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>企业性质</th>
			<td><input name="property" type="text" value="${woodCk.property }" 
				class="easyui-combobox" data-options="required:false,valueField: 'label',textField: 'value',panelHeight:'auto',
				data: [{label: '国营',value: '国营'},
				{label: '集体',value: '集体'},
				{label: '私营',value: '私营'},
				{label: '外资',value: '外资'}]"/></td>
			<th><font style="vertical-align:middle;color:red" ></font>法人</th>
			<td><input name="corporate" type="text" value="${woodCk.corporate }" data-options="required:false"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>仓库所在地区</th>
			<td colspan="3"><input name="area" type="text" value="${woodCk.area }" class="easyui-combobox" 
				data-options="required:true,valueField: 'label',textField: 'value',panelHeight:'auto',
				data: [{label: '上海',value: '上海'},
				{label: '天津',value: '天津'},
				{label: '大连',value: '大连'},
				{label: '青岛',value: '青岛'},
				{label: '重庆',value: '重庆'},
				{label: '岚山',value: '岚山'},
				{label: '太仓',value: '太仓'},
				{label: '常熟',value: '常熟'},
				{label: '深圳',value: '深圳'},
				{label: '常州',value: '常州'},
				{label: '福建',value: '福建'}]"/></td>
		</tr>
		<tr>
			<th >备注</th>
			<td colspan="3" style="height:1cm"><textarea  name="remark" type="text" id="remark"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${woodCk.remark }</textarea></td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>
				<input id="booker" name="booker" type="text" value="${woodCk.booker }" 
				 readonly="true" class="easyui-validatebox"/>
			</td>
			<th>登记部门</th>
			<td>
				<input id="registDept" name="registDept" type="text"  value="${woodCk.registDept }" 
				class="easyui-validatebox" readonly="true" />
 			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
$(function(){
	//国别地区
	$('#nationality').combotree({
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
// 	    panelHeight:'auto',
		panelHeight:300,
	    animate:true,
	    required:true,
	    onHidePanel:function(){}
	});
	//仓储公司
	$('#belongCompany').combobox({
		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseinfo/affiUtil/jsonBaseInfo?filter_EQS_customerType=10230007',
		valueField : 'id',
		textField : 'customerName',
	});
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


$.extend($.fn.validatebox.defaults.rules,
	{    
		englishCheckSub:{
			validator:function(value){
			return /^[a-zA-Z0-9]+$/.test(value);
			},
			message:"只能输入字母、数字."
		},
})
</script>
</body>
</html>