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
	<form id="mainform" action="${ctx}/baseInfo/woodGk/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>编码</th>
			<td><input name="bm" type="text" value="${woodGk.bm }" validType="englishCheckSub"  
				class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th width="25%"><font style="vertical-align:middle;color:red" ></font>港口名称</th>
			<td>
			<input type="hidden" name="id" id="id" value="${woodGk.id }" />
			<input name="gkm" type="text" value="${woodGk.gkm }" class="easyui-validatebox"  data-options="required:true" />
			</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>国家</th>
			<td>
			<input type="text" id="gj" name="gj" value="${woodGk.gj}" readonly="true"/>
			</td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>地区</th>
			<td><input name="dq" type="text" value="${woodGk.dq }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th>免堆期天数</th>
			<td><input name="freeDays" type="text" value="${woodGk.freeDays }" class="easyui-validatebox"/></td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
$(function(){
/*	$('#gj').combobox({
		panelHeight:'auto',
		url:'${ctx}/system/dict/getDictByCode/country',
	    valueField:'name',
	    textField:'name', 
	});*/
	//国别地区
	$('#gj').combotree({
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
// 		panelHeight:'auto',
		panelHeight:300,
	    animate:true,
	    required:true,
	    onHidePanel:function(){}
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