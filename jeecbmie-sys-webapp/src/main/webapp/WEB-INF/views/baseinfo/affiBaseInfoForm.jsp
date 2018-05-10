<%@page import="com.cbmie.baseinfo.entity.WoodAffiBaseInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/affiliates/${action}" method="post">
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '单位基本信息', iconCls: false, refreshable: false">
			<table width="98%" class="tableClass">
				<tr>
					<th>
						<font style="vertical-align:middle;color:red" ></font>客户编码
					</th>
					<td>
						<input name="customerCode" id="customerCode" type="text" readonly="readonly" value="${affiBaseInfo.customerCode }" class="easyui-validatebox"
					 		validType="englishCheckSub" style="overflow:auto;"  data-options="required:true" />
					</td>
					<th>
						<font style="vertical-align:middle;color:red" ></font>客户名称
					</th>
					<td>
						<input type="hidden" name="id" id="id" value="${affiBaseInfo.id }" />
						<input name="customerName" type="text" value="${affiBaseInfo.customerName }" class="easyui-validatebox"   data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>客户类型</th>
					<td>
						<input id="affiBaseCusType" name="customerType"  value="${affiBaseInfo.customerType }"  />
					 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelect()"></a>
					</td>
					<th>单位简称</th>
					<td><input name="unitAbbr" type="text" value="${affiBaseInfo.unitAbbr }" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>法定代表人姓名</th>
					<td width="30%"><input name="legalName" type="text" value="${affiBaseInfo.legalName }" class="easyui-validatebox"  data-options="required:false"/></td>
				 	<th width="20%">法定代表人身份号码</th>
					<td width="30%"><input name="idCardNO" type="text" value="${affiBaseInfo.idCardNO }" class="easyui-validatebox" validtype="idCard"/></td>
				</tr>
				<tr>
					<th>客户英文名</th>
					<td><input name="customerEnName" type="text" value="${affiBaseInfo.customerEnName }" class="easyui-validatebox"/></td>
					<th>传真</th>
					<td><input name="fax" type="text" value="${affiBaseInfo.fax }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>国别地区</th>
					<td>
						<input id="affiBaseCountry" name="country" type="text" value="${affiBaseInfo.country }"/>
					</td>
					<th>国内税务编号</th>
					<td><input name="internalTaxNO" type="text" value="${affiBaseInfo.internalTaxNO }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<th>业务范围</th>
					<td><input name="businessScope" type="text" value="${affiBaseInfo.businessScope }" class="easyui-validatebox" /></td>
					<th>网址</th>
					<td><input name="internetSite" type="text" value="${affiBaseInfo.internetSite }" class="easyui-validatebox"  validtype="url"/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input name="contactPerson" type="text" value="${affiBaseInfo.contactPerson }"  class="easyui-validatebox"  /></td>
					<th>联系电话</th>
					<td><input name="phoneContact" type="text" value="${affiBaseInfo.phoneContact }"  class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<th>地址</th>
					<td ><input name="address" type="text" value="${affiBaseInfo.address }" class="easyui-validatebox"  data-options="required:false" /></td>
					<th>邮编</th>
					<td><input name="zipCode" type="text" value="${affiBaseInfo.zipCode }" class="easyui-validatebox"  /></td>
				</tr>
				<tr>
					<th>邮箱</th>
					<td>
						<input name="email" type="text" value="${affiBaseInfo.email }" class="easyui-validatebox"  />
					</td>
					<th>状态</th>
					<td>
						<input name="status"  value="${affiBaseInfo.status }"  
							class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '启用'}, {label: '0',value: '停用'}]" />
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3" style="height:1cm"><textarea  name="comments" type="text" id="comments"  class="easyui-validatebox"
					style="overflow:auto;width:95%;height:100%;">${affiBaseInfo.comments }</textarea></td>
				</tr>
				<tr>
					<th>登记人</th>
					<td>
						<input id="createrName" name="createrName" type="text" value="${affiBaseInfo.createrName }" 
						 readonly="true" class="easyui-validatebox"/>
						<input  name="createrNo" type="hidden" value="${affiBaseInfo.createrNo }"/>
					</td>
					<th>登记部门</th>
					<td>
						<input id="createrDept" name="createrDept" type="text"  value="${affiBaseInfo.createrDept }" 
						class="easyui-validatebox" readonly="true" />
					</td>
				</tr>
			</table>
		</div>
		<div data-options="title: '联系人信息', iconCls: false, refreshable: false">
			<input type="hidden" name="affiContactJson" id="affiContactJson"/>
			<%@ include file="/WEB-INF/views/baseinfo/affiContactForm.jsp"%>
		</div>	
		<div data-options="title: '银行信息', iconCls: false, refreshable: false">
			<input type="hidden" name="affiBankJson" id="affiBankJson"/>
			<%@ include file="/WEB-INF/views/baseinfo/affiBankForm.jsp"%>
		</div>		
		
		<div data-options="title: '客户评审', iconCls: false, refreshable: false">
			<input type="hidden" name="affiCustomerJson" id="affiCustomerJson"/>
			<%@ include file="/WEB-INF/views/baseinfo/affiCustomerForm.jsp"%>
		</div>
		<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=WoodAffiBaseInfo.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${affiBaseInfo.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">
$(function(){
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
	    	var data = eval('(' + data + ')');
	    	if(data.currnetSequence!=null){
	    		$('#customerCode').val(data.currnetSequence);
	    	}
			$.easyui.loaded();
	    }
	});
	
	//客户类型
	$('#affiBaseCusType').combobox({
		method:'GET',
		url:'${ctx}/system/dict/getDictByCode/KHLX',
	    textField : 'name',
	    valueField:'code',
	    multiple:true,
	    width:250,
	    multiline:true,
		editable:false,
	    required:true,
	    prompt: '此选项可以多选，点击X重置选择',
	    onLoadSuccess: function () { //加载完成后,设置选中第一项 
	        var val = $(this).combobox("getData"); 
// 	    alert(JSON.stringify(val))
	        $(this).combobox("clear");
	   		var curValue = new Array();
	   		curValue = this.value.split(',');
// 	    		alert(curValue+"______________");
	   		for(var j=0;j<curValue.length;j++){
// 	    			alert(curValue[j]+" ~~~~~~~~~");
	   		 	for(var i = 0;i<val.length;i++ ){ 
// 	 	        	 alert(val[i].value+"!!!!!!!!");
		            if (val[i].code==curValue[j]) { 
		                $(this).combobox("select",curValue[j]); 
		            } 
		        } 
	   		}
	    },
	    onHidePanel:function(){},
	    onSelect:function(){
	    	console.info($(this).combobox('getValues'));
	    }
	});
	//国别地区
	$('#affiBaseCountry').combotree({
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
	
});

//清除选择
function clearSelect() {
	$('#affiBaseCusType').combobox("clear");
}

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