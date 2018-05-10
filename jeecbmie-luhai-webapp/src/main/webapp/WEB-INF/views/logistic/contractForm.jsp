<%@page import="com.cbmie.lh.logistic.entity.Contract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/logistic/contract/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>审批表流水号</th>
						<td>
							<input type="hidden" name="id" id="id" value="${contract.id}" />
							<input id="serialnumber" name="serialnumber" type="text" value="${contract.serialnumber }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
						<th>公章</th>
						<td>
							<mytag:combobox name="signetCode" value="${contract.signetCode}" type="dict" parameters="SIGNETTYPE"/>
						</td>
					</tr>
					<tr>
						<th>申请部门</th>
						<td>
							<input id="applyDeptId" class="easyui-combotree" name="applyDeptId" type="text" value="${contract.applyDeptId }"/>
						</td>
						<th>申请人</th>
						<td>
							<input id="applyUserId" class="easyui-combobox" name="applyUserId" type="text" value="${contract.applyUserId }"/>
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							<input id="handlingUserId" class="easyui-combobox" name="handlingUserId" type="text" value="${contract.handlingUserId }"/>
						</td>
						<th>申请日期</th>
						<td>
							<input id="applyDate" name="applyDate" type="text" value="<fmt:formatDate value='${contract.applyDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>合同编号</th>
						<td>
							<input id="contractNo" name="contractNo" type="text" value="${contract.contractNo }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
						<th>合同类别</th>
						<td>
							<mytag:combobox name="contractTypeCode" value="${contract.contractTypeCode}" type="dict" parameters="contractType"/>
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							<input id="money" name="money" type="text" value="${contract.money }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
						<th>币种</th>
						<td>
							<mytag:combobox name="moneyCurrencyCode" value="${contract.moneyCurrencyCode}" type="dict" parameters="currency"/>
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<input id="startDate" name="startDate" type="text" value="<fmt:formatDate value='${contract.startDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
						</td>
						<th>合同结束日期</th>
						<td>
							<input id="endDate" name="endDate" type="text" value="<fmt:formatDate value='${contract.endDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							<mytag:combobox name="traderName" value="${contract.traderName}" type="customer" parameters="10230003"/>
						</td>
						<th>对方联系方式</th>
						<td>
							<input id="traderContact" name="traderContact" type="text" value="${contract.traderContact }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>贸易类型</th>
						<td colspan="3">
							<mytag:combobox name="tradeCategory" value="${contract.tradeCategory}" type="dict" parameters="orderShipType"/>
						</td>
					</tr>
					<tr>
						<th>主要内容</th>
						<td colspan="3">
							<textarea rows="3" cols="80" id="content" class="easyui-validatebox" name="content" value="${contract.content }"></textarea>
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<input id="isCorporationSign" name="isCorporationSign" type="text" value="${contract.isCorporationSign }" class="easyui-combobox"
							data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '是'}, {label: '0',value: '否'}]"/>
						</td>
						<th>代理人</th>
						<td>
							<input id="agent" name="agent" type="text" value="${contract.agent }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							<mytag:combobox name="checkTypeCode" value="${contract.checkTypeCode}" type="dict" parameters="checkType"/>
						</td>
						<th>是否法务审查</th>
						<td>
							<input id="isLegalReview" name="isLegalReview" type="text" value="${contract.isLegalReview }" class="easyui-combobox"
							data-options="valueField: 'label',textField: 'value',panelHeight:'auto', required:true,data: [{label: '1',value: '是'}, {label: '0',value: '否'}]"/>
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							<textarea rows="3" cols="80" id="tipContent" class="easyui-validatebox" name="tipContent" value="${contract.tipContent }"></textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >制单人</th>
						<td>
							<input type="hidden" name="createrNo" value="${contract.createrNo }"/>
							<input type="hidden" name="createrName" value="${contract.createrName }"/>
							<input type="hidden" name="createrDept" value="${contract.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${contract.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${contract.createrName }</td>
						<th  >制单部门</th>
						<td>${contract.createrDept }</td>
						<th  >制单时间</th>
						<td>
							<fmt:formatDate value="${contract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=Contract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${contract.id}" />
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
		$('#applyDeptId').combotree({
			method:'GET',
		    url: '${ctx}/system/organization/json',
		    idField : 'id',
		    textFiled : 'orgName',
			parentField : 'pid',
			panelHeight:300,
		    animate:true,
		    required:true,
		    onSelect:function(){
		    	$('#applyUserId').combobox("clear");
		    	$('#applyUserId').combobox('reload','${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#applyDeptId').combotree("getValue"));
		    },
		    onHidePanel:function(){}
		});
		$('#applyUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#applyDeptId').val(),
			valueField : 'id',
			textField : 'name'
		});
		$('#handlingUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItems',
			valueField : 'id',
			textField : 'name'
		});
		
	});
	$(function(){
		if('${action}' == 'view') {
			$("#tbGoods").hide();
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
			$("#mainform").find('.easyui-combotree').combotree({
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
