<%@page import="com.cbmie.lh.logistic.entity.ProxyAgreement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<form id="mainform" action="${ctx}/logistic/proxyAgreement/${action}" method="post">
	<div id="mainDiv" class="" data-options="border:false">
	<div data-options="title: '报关代理协议', iconCls: false, refreshable: false" >
	<input type="hidden" id="id" name="id" value="${proxyAgreement.id }"/>
	<fieldset class="fieldsetClass">
	<legend>基本信息</legend>
	<table width="98%" class="tableClass">
		<tr>
			<th width="20%">合同编号</th>
			<td width="30%">
				<input type="text" id="contractNo" name="contractNo"  value="${proxyAgreement.contractNo }"  class="easyui-validatebox" data-options="required:true"/>
			</td>
			<th width="20%">内部合同号</th>
			<td width="30%">
				<input type="text" id="innerContractNo" name="innerContractNo"  style="width:70%;" value="${proxyAgreement.innerContractNo }" readonly="readonly" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr>
			<th>甲方(委托方)</th>
			<td colspan="3">
				<mytag:combobox name="ourUnit" value="${proxyAgreement.ourUnit}" type="customer" parameters="10230001" required="true" disabled="false"/>
			</td>
		</tr>
		<tr>
			<th>乙方(代理方)</th>
			<td colspan="3">
				<mytag:combobox name="agreementUnit" value="${proxyAgreement.agreementUnit}" type="customer" parameters="10230006" required="true" disabled="false"/>
			</td>
		</tr>
		<tr>
			<th >货代费</th>
			<td >
				<input  id="freightFee" name="freightFee"  value="${proxyAgreement.freightFee }" type="text" class="easyui-numberbox" data-options="required:true,precision:2"/>
			</td>
			
			<th>币种</th>
			<td>
				<mytag:combobox name="currency" value="${proxyAgreement.currency}" type="dict" parameters="currency" required="false" disabled="false"/>
			</td>
		</tr>
		<tr>
			<th>签约日期</th>
			<td>
				<input name="signDate" type="text"  value="<fmt:formatDate value="${proxyAgreement.signDate}"/>"  class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>	
			</td>
			<th>有效期至</th>
			<td>
				<input name="effectDate" type="text"  value="<fmt:formatDate value="${proxyAgreement.effectDate}"/>"  class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>	
			</td>
		</tr>
		<tr>
			<th  >帐套单位</th>
			<td >
				<input id="setUnit" name="setUnit"   type="text" value="${proxyAgreement.setUnit }"  class="easyui-validatebox"  />
			</td>
			<th  >业务经办人 </th>
			<td>
				<mytag:combotree name="businessManager" value="${proxyAgreement.businessManager}"  required="true"  type="userTree" />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
				<textarea rows="2" cols="90" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,200]']">${proxyAgreement.comment }</textarea>
			</td>
		</tr>
	</table>
	</fieldset>
	<fieldset class="fieldsetClass">
	<legend>系统信息</legend>
	<table width="98%" class="tableClass">
		<tr>
			<th  width="20%">登记人</th>
			<td width="30%">${empty proxyAgreement.createrName ? sessionScope.user.name : proxyAgreement.createrName }</td>
			<th  width="20%">登记部门</th>
			<td width="30%">${empty proxyAgreement.createrDept ? sessionScope.user.organization.orgName : proxyAgreement.createrDept }</td>
		</tr>
		<tr>
			<th width="20%" >登记时间</th>
			<td width="30%"><fmt:formatDate value="${ proxyAgreement.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<th width="20%" >最后修改时间</th>
			<td width="30%"><fmt:formatDate value="${proxyAgreement.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>
	</fieldset>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=ProxyAgreement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${proxyAgreement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>
	</div>
</form>
<script type="text/javascript">
var allCode;
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
	    	successTipNew(data,dataGrid);
	    	var data = eval('(' + data + ')');
	    	if(data.currnetSequence!=null){
	    		$('#innerContractNo').val(data.currnetSequence);
	    	}
			$.easyui.loaded();
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
	
	$.ajax({
		type:'GET',
		async: false,
		 dataType: "json", 
		url:"${ctx}/baseInfo/baseUtil/getAffiUnitAbbr",
		success: function(data){
			allCode=""+data;
		}
	});
//帐套单位
$('#setUnit').combobox({
	required:true,
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/getAffiByType/10230001',
	valueField : 'customerCode',
	textField : 'customerName',
	prompt:'保存后不能修改！',
    onSelect:function(record){
    	if("${keyWord}"){
    		var souceCode ="";
    		var str = $('#innerContractNo').val();
    		var result = allCode.split(",");
    		for (var i = 0; i < result.length; i++) {
    			if(str.indexOf(result[i]) != -1){
    				souceCode = result[i];
    			}
    		}
    		if(record.unitAbbr!=souceCode){
    			str = str.replace(souceCode,record.unitAbbr);
    		}
    		$('#innerContractNo').val(str);
    	}
    }
});
	
	if("${action}"!="create"){
		$('#setUnit').combobox('disable');
	}
	
});

$(function(){
	if('${action}' == 'update') {
		$("#contractNo").attr("readonly",true);
	}
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		$("#mainform").find('input').attr("readonly",true);
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
});

</script>
</body>
</html>