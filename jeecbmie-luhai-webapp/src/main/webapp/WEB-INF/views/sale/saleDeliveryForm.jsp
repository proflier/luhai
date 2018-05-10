<%@ page import="com.cbmie.lh.sale.entity.SaleDelivery"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/sale/saleDelivery/${action}" method="post">
		<input id="id" name="id" type="hidden" value="${saleDelivery.id }" />
		<input type="hidden" id="relLoginNames" name="relLoginNames" value="${saleDelivery.relLoginNames }"/>
		<input type="hidden" id="manageMode" name="manageMode" value="${saleDelivery.manageMode }"/>
		<input id="saleContractNoOld" name="saleContractNoOld" type="hidden" value="${saleDelivery.saleContractNoOld }" />
		<input type="hidden" id="param" name="param" value="EQL_saleDeliveryId" />
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '放货审批', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>通知单号</th>
						<td>
							<input name="deliveryReleaseNo" type="text" value="${saleDelivery.deliveryReleaseNo}" readonly="readonly"/>
						</td>					
						<th>销售合同号</th>
						<td>
							<input id="saleContractNo" name="saleContractNo" type="text" value="${saleDelivery.saleContractNo }" readonly="readonly"
							class="easyui-validatebox" data-options="required:true"/>
							<a id="toSaleListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSaleList()">销售合同</a>
						</td>
					</tr>
					<tr>
						<th width="20%">客户名称</th>
						<td width="30%">
							<mytag:combobox name="seller" value="${saleDelivery.seller}" type="customer" parameters="10230003" hightAuto="false" disabled="true"/>
<%-- 							<mytag:combobox name="seller" value="${saleDelivery.seller}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/> --%>
						</td>
						<th  width="20%">销售方式</th>
						<td width="30%">
							<mytag:combobox name="saleMode" value="${saleDelivery.saleMode}" type="dict" parameters="SALESMODE"/>
						</td>
					</tr>
					<tr>
						<th>交货起止日期</th>
						<td colspan="3">
							<input id="deliveryStartDate" name="deliveryStartDate" type="text" value="<fmt:formatDate value='${saleDelivery.deliveryStartDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							-
							<input id="deliveryEndDate" name="deliveryEndDate" type="text" value="<fmt:formatDate value='${saleDelivery.deliveryEndDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<th>交货方式</th>
						<td>
							<mytag:combobox name="deliveryMode" value="${saleDelivery.deliveryMode}" type="dict" parameters="THFS"/>
						</td>
						<th>数量合计</th>
						<td>
							<input id="numTotal" name="numTotal" type="text" value="${saleDelivery.numTotal }" class="easyui-numberbox"/>
						</td>
					</tr>
					<tr>
						<th>帐套单位</th>
						<td>
							<mytag:combobox name="setUnit" value="${saleDelivery.setUnit}" type="customer" parameters="10230001" width="200"/>
						</td>
						<th>业务经办人</th>
						<td>
							<mytag:combotree name="businessManager" value="${saleDelivery.businessManager}" type="userTree" />
						</td>
					</tr>
					<tr>
						<th>数量溢短装</th>
						<td>
							<input id="numMoreOrLess" name="numMoreOrLess" type="text" value="${saleDelivery.numMoreOrLess }" class="easyui-numberbox"  data-options="value:6,precision:'2',suffix:'%'"/>
						</td>
						<th>制单日期</th>
						<td>
							<input id="billDate" name="billDate" type="text" value="<fmt:formatDate value="${saleDelivery.billDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>数量验收 </th>
						<td colspan="3">
							<textarea id="numSettlementBasis" name="numSettlementBasis" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,255]']">${mytag:unescapeHtml(saleDelivery.numSettlementBasis)}</textarea>
						</td>
					</tr>
					<tr>
						<th>质量验收</th>
						<td colspan="3">
							<textarea id="qualitySettlementBasis" name="qualitySettlementBasis" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,255]']">${mytag:unescapeHtml(saleDelivery.qualitySettlementBasis)}</textarea>
						</td>
					</tr>
					<tr>
						<th>费用承担</th>
						<td colspan="3">
							<textarea id="riskTip" name="riskTip" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,255]']">${mytag:unescapeHtml(saleDelivery.riskTip)}</textarea>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea name="remark" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,255]']">${mytag:unescapeHtml(saleDelivery.remark)}</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass">
				<legend>放货信息</legend>
				<%@ include file="/WEB-INF/views/sale/saleDeliveryGoodsList.jsp"%> 
				</fieldset>
				<fieldset class="fieldsetClass">
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${saleDelivery.createrNo }"/>
							<input type="hidden" name="createrName" value="${saleDelivery.createrName }"/>
							<input type="hidden" name="createrDept" value="${saleDelivery.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${saleDelivery.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${saleDelivery.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${saleDelivery.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleDelivery.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleDelivery.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleDelivery.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${saleDelivery.pid ne null }">
				<fieldset id="changeReasonField"  class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								<input id="changeReason" style="width:45%;height:40px;" name="changeReason" value="${saleDelivery.changeReason }" class="easyui-validatebox"/>
							</div>
				</fieldset>
				</c:if>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=SaleDelivery.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${saleDelivery.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
						
		</div>	
	</form>
</div>

<script type="text/javascript">
function afterMainSubmitSuccess(){
	$("#saleContractNoOld").val($("#saleContractNo").val());
		$("#mainform").find("input[name='saleGoodsIds']").remove();
		goodsWindow.listGoods();
}
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
	    		$('#deliveryReleaseNo').val(data.currnetSequence);
	    	}
     		$("#saleContractNoOld").val($("#saleContractNo").val());
     		$("#mainform").find("input[name='saleGoodsIds']").remove();
     		goodsWindow.listGoods();
			$.easyui.loaded();
	    }
	});
	
});

</script>
</body>
</html>