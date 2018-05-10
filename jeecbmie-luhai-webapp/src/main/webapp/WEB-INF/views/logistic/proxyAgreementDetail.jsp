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
				${proxyAgreement.contractNo }
			</td>
			<th width="20%">内部合同号</th>
			<td width="30%">
				${proxyAgreement.innerContractNo }
			</td>
		</tr>
		<tr>
			<th>甲方(委托方)</th>
			<td colspan="3">
				${mytag:getAffiBaseInfoByCode(proxyAgreement.ourUnit)}
			</td>
		</tr>
		<tr>
			<th>乙方(代理方)</th>
			<td colspan="3">
				${mytag:getAffiBaseInfoByCode(proxyAgreement.agreementUnit)}
			</td>
		</tr>
		<tr>
			<th >货代费</th>
			<td >
				${proxyAgreement.freightFee }
			</td>
			
			<th>币种</th>
			<td>
				${fns:getDictLabelByCode(proxyAgreement.currency,'currency','')}
			</td>
		</tr>
		<tr>
			<th>签约日期</th>
			<td>
				<fmt:formatDate value="${proxyAgreement.signDate}"/>	
			</td>
			<th>有效期至</th>
			<td>
				<fmt:formatDate value="${proxyAgreement.effectDate}"/>	
			</td>
		</tr>
		<tr>
			<th  >帐套单位</th>
			<td >
				${mytag:getAffiBaseInfoByCode(proxyAgreement.setUnit)}
			</td>
			<th  >业务经办人 </th>
			<td>
				${mytag:getUserByLoginName(proxyAgreement.businessManager).name}
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
				${proxyAgreement.comment }
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
$(function(){
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
	
	
});


</script>
</body>
</html>