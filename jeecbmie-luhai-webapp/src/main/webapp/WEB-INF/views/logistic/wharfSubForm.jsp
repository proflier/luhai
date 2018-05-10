<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
	<form id="subMainform" action="${ctx}/logistic/wharfSub/${actionSub}" method="post">
		<input type="hidden" name="id" id="wharfSubid" value="${wharfSettlementSub.id}" />
		<input type="hidden" id="wharfSettlementId" name="wharfSettlementId" value="${wharfSettlementSub.wharfSettlementId }"/>
	<div id="subMainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '结算信息', iconCls: false, refreshable: false">
		
			<fieldset class="fieldsetClass" >
				<legend>子表信息</legend>
				<table width="98%" class="tableClass">
				<tr>
					<th width="20%">船编号</th>
					<td colspan="3">
						<input name="shipNo"  id="shipNo" size='50'  type="text" value="${wharfSettlementSub.shipNo}" class="easyui-combogrid"/>
					<!-- <a id="shipListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="sub_obj.toShipList()">船列表</a> -->
					</td>
				</tr>
				<tr>
					<th width="20%">计费吨数</th>
					<td>
						<input name="settleQuantity"  id="settleQuantity_sub"  type="text" value="${wharfSettlementSub.settleQuantity}" class="easyui-numberbox" data-options="precision:2,onChange:countPay_sub"/>
					</td>
					<th width="20%">结算单价</th>
					<td>
						<input name="unitPrice"  id="unitPrice_sub"  type="text" value="${wharfSettlementSub.unitPrice}" class="easyui-numberbox" data-options="precision:4,onChange:countPay_sub"/>
					</td>
				</tr>
				<tr>
					<th width="20%">金额</th>
					<td >
						<input name="totalPrice"  id="totalPrice_sub"  type="text" value="${wharfSettlementSub.totalPrice}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th width="20%">港口费用类别</th>
					<td >
						<mytag:combobox name="portExpenseType" value="${wharfSettlementSub.portExpenseType}" type="dict" parameters="portExpenseType"/>
					</td>
				</tr>
				<tr>
					<th>摘要</th>
					<td colspan="3">
						<textarea  name="roundup"  id="roundup_sub"  class="easyui-validatebox"
							style="overflow:auto;width:80%;height:100%;">${wharfSettlementSub.roundup}</textarea>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea  name="remarks"  id="remarks_sub"  class="easyui-validatebox"
							style="overflow:auto;width:80%;height:100%;">${wharfSettlementSub.remarks}</textarea>
					</td>
				</tr>
				</table>
			</fieldset>
		</div>	
	</div>
	</form>
	
</div>
<script type="text/javascript">
function countPay_sub(){
	var settleQuantity = $("#settleQuantity_sub").val()?$("#settleQuantity_sub").val():0;
	var unitPrice = $("#unitPrice_sub").val()?$("#unitPrice_sub").val():0;
	$("#totalPrice_sub").numberbox('setValue',settleQuantity*unitPrice);
}
$(function(){
	$('#subMainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		sub_obj.subs_form.panel('close');
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    	}  
	    	sub_obj.backMain();
	    	sub_obj.reload();
	    }
	});
	$('#shipNo').combogrid({    
	    panelWidth:550,    
	    method: "get",
	    idField:'shipNo',    
	    textField:'noAndName',
	    required:true,
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
	    url:'${ctx}/logistic/shipTrace/allShipTrace', 
	    columns:[[
				{field:'shipNo',title:'船编号',width:30},
				{field:'shipName',title:'船中文名',width:30},
				{field:'shipNameE',title:'船英文名',width:15},
				{field:'loadPortView',title:'装港港口',width:20,
// 					formatter: function(value,row,index){
// 						var val;
// 						if(value!=''&&value!=null){
// 							$.ajax({
// 								type:'GET',
// 								async: false,
// 								url:"${ctx}/baseInfo/baseUtil/getPortNameByCode/"+value,
// 								success: function(data){
// 									if(data != null){
// 										val = data;
// 									} else {
// 										val = '';
// 									}
// 								}
// 							});
// 							return val;
// 						}else{
// 							return '';
// 						}
// 					}
				}
	 		]]
	});
});
</script>
</body>
</html>

