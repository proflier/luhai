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
	<form id="subMainform" action="${ctx}/logistic/orderShipContractSub/${actionSub}" method="post">
		<input type="hidden" name="id" id="orderShipContractSubid" value="${orderShipContractSub.id}" />
		<input type="hidden" id="orderShipContractId" name="orderShipContractId" value="${orderShipContractSub.orderShipContractId }"/>
	<div id="subMainDiv" data-options="border:false">
			<fieldset class="fieldsetClass" >
				<legend>子表信息</legend>
				<table width="98%" class="tableClass">
				<tr>
					<th>船名</th>
					<td colspan="3">
						<input id="shipNo" size='30' name="shipNo" type="text" value="${orderShipContractSub.shipNo }" class="easyui-combogrid"/>
					</td>
				</tr>
				<tr>
					<th>装船开始日期</th>
					<td>
						<input id="loadBeginDate" name="loadBeginDate" type="text" value="<fmt:formatDate value='${orderShipContractSub.loadBeginDate}' />" 
						class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
					</td>
					<th>装船结束日期</th>
					<td>
						<input id="loadEndDate" name="loadEndDate" type="text" value="<fmt:formatDate value='${orderShipContractSub.loadEndDate}' />" 
						class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th>溢短装</th>
					<td>
						<input id="numMoreOrLess" name="numMoreOrLess" type="text" value="${orderShipContractSub.numMoreOrLess }" class="easyui-numberbox"  class="easyui-numberbox" data-options="required:true,precision:'2',suffix:'%'"/>
					</td>
					<th>运费单价</th>
					<td>
						<input id="tradePriceRate" name="tradePriceRate" type="text" value="${orderShipContractSub.tradePriceRate }" class="easyui-numberbox" data-options="required:true,precision:'2'"  />
					</td>
				</tr>
				<tr>
						<th>装港</th>
						<td>
							<mytag:combobox name="loadPort" value="${orderShipContractSub.loadPort}" type="port" hightAuto="false"/>
						</td>
						<th>卸港</th>
						<td>
							<mytag:combobox name="unloadPort" value="${orderShipContractSub.unloadPort}" type="port" hightAuto="false"/>
						</td>
					</tr>
					<tr>
						<th>装率(吨/天)</th>
						<td>
							<input id="loadRate" name="loadRate" type="text" value="${orderShipContractSub.loadRate }" class="easyui-numberbox" data-options="required:false,precision:'2'"  />
						</td>
						<th>卸率(吨/天)</th>
						<td>
							<input id="unloadRate" name="unloadRate" type="text" value="${orderShipContractSub.unloadRate }" class="easyui-numberbox" data-options="required:false,precision:'2'"  />
						</td>
					</tr>
					<tr>
						<th>滯期费率(美金/天)</th>
						<td>
							<input id="demurrageRate" name="demurrageRate" type="text" value="${orderShipContract.demurrageRate }" class="easyui-numberbox" data-options="required:false,precision:'3'"  />
						</td>
						<th>速遣费率(美金/天)</th>
						<td>
							<input id="dispatchPriceRate" name="dispatchPriceRate" type="text" value="${orderShipContract.dispatchPriceRate }" class="easyui-numberbox" data-options="required:false,precision:'3'"  />
						</td>
					</tr>
					<tr>
						<th>吊机</th>
						<td >
							<input name="craneFlag"  value="${orderShipContract.craneFlag }"  
							class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto', required:true,data: [{label: '1',value: '有'}, {label: '0',value: '无'}]" />
						</td>
						<th>执行货种</th>
						<td >
							<input id="product" name="product" type="text" value="${orderShipContract.product }" class="easyui-validatebox" />
						</td>
					</tr>
				</table>
			</fieldset>
	</div>
	</form>
	
</div>
<script type="text/javascript">
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
	    	sub_obj.listSubs();
	    }
	});
	$('#shipNo').combogrid({    
	    panelWidth:450,
	    required:true,
	    method: "get",
	    idField:'shipNo',    
	    textField:'noAndName',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
	    url:'${ctx}/logistic/shipTrace/allShipTrace', 
	    columns:[[
				{field:'shipNo',title:'船编号',width:20},
				{field:'shipName',title:'船中文名',width:20},
				{field:'shipNameE',title:'船英文名',width:20},
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

