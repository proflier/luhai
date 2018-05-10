<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/importShips/bookShip/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%">合同编号</th>
		<td width="30%">
		<input  id="id" name="id" type="hidden" value="${bookShip.id }"/>
		<input type="text" id="contractNo" name="contractNo" value="${bookShip.contractNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th width="20%">船名 </th>
		<td width="30%">
			<input type="text" id="shipName" name="shipName" value="${bookShip.shipName }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>船龄</th>
		<td >
			<input type="text" id="shipYear" name="shipYear" value="${bookShip.shipYear }" class="easyui-validatebox" />
		</td>
		<th>船次 </th>
		<td>
			<input type="text" id="shipNo" name="shipNo" value="${bookShip.shipNo }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>船运公司</th>
		<td >
			<input type="text" id="shipCompany" name="shipCompany" value="${bookShip.shipCompany }" class="easyui-validatebox" />
		</td>
		<th>载重吨 </th>
		<td>
			<input type="text" id="loading" name="loading" value="${bookShip.loading}" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>定船费用</th>
		<td >
			<input type="text" id="bookShipFee" name="bookShipFee" value="${bookShip.bookShipFee }" class="easyui-validatebox" />
		</td>
		<th>币种</th>
		<td>
			<input type="text" id="currency" name="currency" value="${bookShip.currency }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>装港</th>
		<td >
			<input type="text" id="portOfShipment" name="portOfShipment" value="${bookShip.portOfShipment }" class="easyui-validatebox" />
		</td>
		<th>预计装港日期 </th>
		<td>
			<input type="text" id="expectShipTime" name="expectShipTime" value="<fmt:formatDate value='${bookShip.expectShipTime }' />" class="easyui-my97" datefmt="yyyy-MM-dd"  />
		</td>
	</tr>
	<tr>
		<th>目的港</th>
		<td >
			<input type="text" id="destinationPort" name="destinationPort" value="${bookShip.destinationPort }" class="easyui-validatebox" />
		</td>
		<th>预计卸港日期 </th>
		<td>
			<input type="text" id="expectUnloadTime" name="expectUnloadTime" value="<fmt:formatDate value='${bookShip.expectUnloadTime }' />" class="easyui-my97" datefmt="yyyy-MM-dd"  />
		</td>
	</tr>
	<tr>
		<th>受载期</th>
		<td >
			<input type="text" id="shipingTime" name="shipingTime" value="<fmt:formatDate value='${bookShip.shipingTime }' />" class="easyui-my97" datefmt="yyyy-MM-dd"  />
		</td>
		<th>签订时间</th>
		<td>
			<input type="text" id="signedTime" name="signedTime" value="<fmt:formatDate value='${bookShip.signedTime }' />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
		</td>
	</tr>
	<tr>
		<th>是否提示派检</th>
		<td >
			<input type="text" id="pointInspection" name="pointInspection" value="${bookShip.pointInspection }" class="easyui-validatebox" />
		</td>
		<th>运费率</th>
		<td>
			<input type="text" id="transportFeeRate" name="transportFeeRate" value="${bookShip.transportFeeRate }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>经济人</th>
		<td >
			<input type="text" id="broker" name="broker" value="${bookShip.broker }" class="easyui-validatebox" />
		</td>
		<th>滞期费率</th>
		<td>
			<input type="text" id="demurrageRates" name="demurrageRates" value="${bookShip.demurrageRates }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>速遣费率</th>
		<td >
			<input type="text" id="despatchRates" name="despatchRates" value="${bookShip.despatchRates }" class="easyui-validatebox" />
		</td>
		<th>装船期限</th>
		<td>
			<input type="text" id="loadDeadline" name="loadDeadline" value="${bookShip.loadDeadline }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>卸船期限</th>
		<td >
			<input type="text" id="unloadDeadline" name="unloadDeadline" value="${bookShip.unloadDeadline }" class="easyui-validatebox" />
		</td>
		<th>吃水</th>
		<td>
			<input type="text" id="draft" name="draft" value="${bookShip.draft }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>流水号</th>
		<td colspan="3">
			<input type="text" id="serialNumber" name="serialNumber" value="${bookShip.serialNumber }" class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="3">
			<textarea id="comment" name="comment" class="easyui-validatebox" style="width: 36%;margin-top: 5px;" >${bookShip.comment }</textarea>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th  width="20%">登记人</th>
		<td width="30%">${empty bookShip.createrName ? sessionScope.user.name : bookShip.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${empty bookShip.createrDept ? sessionScope.user.organization.orgName : bookShip.createrDept }</td>
	</tr>
</table>
</fieldset>
</form>
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
	
	
	
	//船运公司
	$('#shipCompany').combobox({
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=8',
		valueField : 'id',
		textField : 'customerName',
	});
	
	//币种
	$('#currency').combobox({
		method: "get",
		url:'${ctx}/system/downMenu/getDictByCode/currency',
		valueField : 'id',
		textField : 'name',
	});
	
	//是否提示派检
	$('#pointInspection').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/downMenu/getDictByCode/YESNO',
		valueField : 'id',
		textField : 'name',
	});
	
	//装港
	$('#portOfShipment').combobox({
		method: "get",
		url : '${ctx}/system/downMenu/jsonGK',
		method: "get",
		valueField : 'id',
		textField : 'gkm',
	});
	
	//目的港
	$('#destinationPort').combobox({
		method: "get",
		url : '${ctx}/system/downMenu/jsonGK',
		valueField : 'id',
		textField : 'gkm',
	});
	
	
});

</script>
</body>
</html>