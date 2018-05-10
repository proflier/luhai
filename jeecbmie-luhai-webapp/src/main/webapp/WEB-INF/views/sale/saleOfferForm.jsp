<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.cbmie.lh.sale.entity.SaleOffer" %>
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
<form id="mainform"  action="${ctx}/sale/saleOffer/${action}" method="post">
<input type="hidden" id="id" name="id" value="${saleOffer.id }" />
<div id="mainDiv" class="" data-options="border:false">
	<div data-options="title: '基本信息', iconCls: false, refreshable: false" >
		<fieldset class="fieldsetClass" >
		<legend>基本信息</legend>
		<table width="98%" class="tableClass" >
			<tr>
				<th width="20%">报盘单号</th>
				<td width="30%">
					<input id="offerNo" name="offerNo" type="text" value="${saleOffer.offerNo }" readonly="readonly" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th width="20%">客户名称</th>
				<td width="30%">
					<mytag:combobox name="customer" value="${saleOffer.customer}" type="customer" parameters="10230003" width="200" onSelectFunctionName="selectCustomer"/>
				</td>
			</tr>
			<tr>
				<th>联系人</th>
				<td>
					<input id="contactPerson" name="contactPerson" type="text" value="${saleOffer.contactPerson }" class="easyui-validatebox"/>
				</td>
				<th>电话</th>
				<td>
					<input id="phoneContact" name="phoneContact" type="text" value="${saleOffer.phoneContact }"  class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td>
					<input id="faxContact" name="faxContact" type="text" value="${saleOffer.faxContact }"  class="easyui-validatebox"/>
				</td>
				<th>邮箱</th>
				<td>
					<input id="emailContact" name="emailContact" type="text" value="${saleOffer.emailContact }"  class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>结算方式</th>
				<td>
					<mytag:combobox name="payMode" value="${saleOffer.payMode}" type="dict" parameters="SJKFS"/>
				</td>
				<th>交货期限</th>
				<td>
					<input id="deliveryStartDate" name="deliveryStartDate" type="text" value="<fmt:formatDate value='${saleOffer.deliveryStartDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					-
					<input id="deliveryEndDate" name="deliveryEndDate" type="text" value="<fmt:formatDate value='${saleOffer.deliveryEndDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</td>
			</tr>
			<tr>
				<th>交货方式</th>
				<td>
					<mytag:combobox name="deliveryMode" value="${saleOffer.deliveryMode}" type="dict" parameters="THFS"/>
				</td>
				<th>交货地点</th>
				<td>
					<input name="deliveryAddr" type="text" value="${saleOffer.deliveryAddr }"  class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>业务经办人</th>
				<td>
					<mytag:combotree name="businessManager" value="${saleOffer.businessManager}" type="userTree" onSelectFunctionName="selectBusinessManager"/>
				</td>
				<th>电话</th>
				<td>
					<input id="phoneBusiness" name="phoneBusiness" type="text" value="${saleOffer.phoneBusiness }"  class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td>
					<input id="faxBusiness" name="faxBusiness" type="text" value="${saleOffer.faxBusiness }"  class="easyui-validatebox"/>
				</td>
				<th>邮箱</th>
				<td>
					<input id="emailBusiness" name="emailBusiness" type="text" value="${saleOffer.emailBusiness }"  class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>报盘日期</th>
				<td>
					<input name="offerDate" type="text" value="<fmt:formatDate value='${saleOffer.offerDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</td>
				<th>报盘有效期</th>
				<td>
					<input name="validityDate" type="text" value="<fmt:formatDate value='${saleOffer.validityDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</td>
			</tr>
			<tr>
				<th>制单日期</th>
				<td colspan="3">
					<jsp:useBean id="now" class="java.util.Date" scope="page"/>
					<input name="billDate" value="<fmt:formatDate value='${empty saleOffer.billDate ? now : saleOffer.billDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>其他说明</th>
				<td colspan="3">
					<textarea name="otherExplain" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,500]']">${mytag:unescapeHtml(saleOffer.otherExplain)}</textarea>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>系统信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th>登记人</th>
					<td>${empty saleOffer.createrName ? sessionScope.user.name : saleOffer.createrName }</td>
					<th>登记部门</th>
					<td>${empty saleOffer.createrDept ? sessionScope.user.organization.orgName : saleOffer.createrDept }</td>
				</tr>
				<tr>
					<th width="20%">登记时间</th>
					<td width="30%"><fmt:formatDate value="${saleOffer.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th width="20%">最后修改时间</th>
					<td width="30%"><fmt:formatDate value="${saleOffer.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div data-options="title: '报盘商品', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/sale/saleOfferGoodsList.jsp"%>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
		<input id="accParentEntity" type="hidden"  value="<%=SaleOffer.class.getName().replace(".","_") %>" />
		<input id="accParentId" type="hidden" value="${saleOffer.id}" />
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>
</div>	
</form>
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
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#offerNo').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
			} 
		});
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if( $('#mainform').form('validate') && (!$("#id").val() == '')){//主表校验通过且已经保存数据
		    		tabIndex = index;//更换tab
		    	}else{
		    		if(index != 0){
		    			parent.$.messager.alert('提示','请先保存主表信息!!!');
		    		}
		    		$("#mainDiv").tabs("select", tabIndex);//当前tab
		    		return;
				}
		    }
		});
		
	});
	
	function selectCustomer(record) {
		$('#contactPerson').val(record.contactPerson);
		$('#phoneContact').val(record.phoneContact);
		$('#faxContact').val(record.fax);
		$('#emailContact').val(record.email);
	}

	function selectBusinessManager(record) {
		$.ajax({
			type:'get',
			async:false,
			url:"${ctx}/system/user/getUserByLoginName/" + record.loginName,
			success: function(data){
				$('#phoneBusiness').val(data.phone);
				$('#faxBusiness').val(data.fax);
				$('#emailBusiness').val(data.email);
			}
		});
		
	}
</script>
</body>
</html>