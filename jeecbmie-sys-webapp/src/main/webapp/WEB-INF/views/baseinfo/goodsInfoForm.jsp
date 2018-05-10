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
	<form id="mainform" action="${ctx}/baseInfo/goodsInfo/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>级别</th>
			<td>
			<input type="hidden" name="id" id="id" value="${goods.id}" />
			<input name="classes"  id="classes" value="${goods.classes}" type="text" data-options="required:true" />
			</td>
			<th><font style="vertical-align:middle;color:red" ></font>级别名称</th>
			<td><input name="className" id="className"  class="easyui-combobox" value="${goods.className}" type="text" disabled="true"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>编码</th>
			<td><input name="code" id="code" type="text" value="${goods.code}" class="easyui-validatebox" 
			data-options="required:true,validType:'minLength[2]'"  style="overflow:auto;"/></td>
		
			<th><font style="vertical-align:middle;color:red" ></font>名称</th>
			<td><input name="name" type="text" value="${goods.name}" class="easyui-validatebox" data-options="required:true" style="overflow:auto;"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>是否是濒危</th>
			<td><input name="ifDanger" type="text" value="${goods.ifDanger}" class="easyui-combobox" data-options="required:true,valueField: 'label',textField: 'value', panelHeight:'auto',
			data: [{label: '是',value: '是'}, 
				{label: '否',value: '否'}]"/>
			</td>
			<th><font style="vertical-align:middle;color:red" ></font>所属级别</th>
			<td><input id= "affiliated" name="affiliated" type="text" value="${goods.affiliated}" class="easyui-combobox"/>				
			</td>			
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>换算数</th>
			<td colspan="3"><input name="hss" style="overflow:auto;width:14.5%;" type="text" value="${goods.hss}" class="easyui-validatebox" data-options="required:false"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>描述</th>
			<td colspan="3" style="height:1cm"><textarea  name="description" type="text" id="description"  class="easyui-validatebox"
			style="overflow:auto;width:50%;height:100%;">${goods.description }</textarea></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>备注</th>
			<td colspan="3" style="height:1cm"><textarea  name="remark" type="text" id="remark"  class="easyui-validatebox"
			style="overflow:auto;width:50%;height:100%;">${goods.remark }</textarea></td>
			
		</tr>
		<tr>
			<th>登记人</th>
			<td>
				<input id="sqr" name="sqr" type="text" value="${goods.sqr }" 
				 readonly="true" class="easyui-validatebox"/>
			</td>
			<th>登记部门</th>
			<td>
				<input id="dept" name="dept" type="text"  value="${goods.dept }" 
				class="easyui-validatebox" readonly="true" />
			</td>
		</tr>
		<tr>
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
	$('#classes').combobox({
		panelHeight:'auto',
	    valueField:'label',
	    textField:'value' ,
	    data: 	[{label: '1级',value: '1级'}, 
				{label: '2级',value: '2级'},
				{label: '3级',value: '3级'},
				{label: '4级',value: '4级'},
				{label: '5级',value: '5级'},
				{label: '6级',value: '6级'}],
	    onSelect: function(data){
	    	if(data.value == '1级'){
	    		$('#affiliated').combobox('clear');
	    		$('#affiliated').combobox({ required: false });
	    		$('#affiliated').combobox('disable');
	    		$('#className').combobox('setValue', '类别');
	    		$('#code').validatebox('options').validType="minLength[2]"; 
	    	}else if(data.value == '2级'){
	    		$('#affiliated').combobox('enable');
				$('#affiliated').combobox({ required: true });
	    		$('#className').combobox('setValue', '材种');
	    		$('#code').validatebox('options').validType="minLength[2]"; 
	    	}else if(data.value == '3级'){
	    		$('#affiliated').combobox('enable');
	    		$('#affiliated').combobox({ required: true });
	    		$('#className').combobox('setValue', '等级');
	    		$('#code').validatebox('options').validType="minLength[2]"; 
	    	}else if(data.value == '4级'){
	    		$('#affiliated').combobox('enable');
	    		$('#affiliated').combobox({ required: true });
	    		$('#className').combobox('setValue', '厚*宽');
	    		$('#code').validatebox('options').validType="minLength[2]"; 
	    	}else if(data.value == '5级'){
	    		$('#affiliated').combobox('enable');
	    		$('#affiliated').combobox({ required: true });
	    		$('#className').combobox('setValue', '长度');
	    		$('#code').validatebox('options').validType="minLength[2]"; 
	    	}else if(data.value == '6级'){
	    		$('#affiliated').combobox('enable');
	    		$('#affiliated').combobox({ required: true });
	    		$('#className').combobox('setValue', '品牌');
	    		$('#code').validatebox('options').validType="minLength[3]";  
	    	}
	    }
	});
	
	$('#affiliated').combobox({
		url:'${ctx}/baseInfo/goodsInfo/getType',
		panelHeight:'auto',
	    valueField:'name',
	    textField:'name' ,
	    onSelect: function(data){
	    	brandCode=data.code;
	    	brandName=' '+data.name;
	    	
	    }
	});
	
	$.extend($.fn.validatebox.defaults.rules, {    
	    minLength: {    
	        validator: function(value, param){  
	        	var one = (value.length == param[0]);
	        	var two = (/^[a-zA-Z0-9]+$/.test(value));
	            return (one&&two);    
	        },    
	        message: '请输入 {0} 个字符并且不能为汉字.' 
	    } , 
// 		englishCheckSub:{
// 			validator:function(value){
// 				return /^[a-zA-Z0-9]+$/.test(value);
// 			},
// 			message:"只能输入字母、数字."
// 		},
	});   

});
</script>
</body>
</html>