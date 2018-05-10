<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<!--移动端截取标志1-->
<table width="98%" class="tableClass">
	<tr>
		<th width="20%">合同编号</th>
		<td width="30%">
		${bookShip.contractNo }
		</td>
		<th width="20%">船名 </th>
		<td width="30%">
			${bookShip.shipName }
		</td>
	</tr>
	<tr>
		<th>船龄</th>
		<td >
			${bookShip.shipYear }
		</td>
		<th>船次 </th>
		<td>
			${bookShip.shipNo }
		</td>
	</tr>
	<tr>
		<th>船运公司</th>
		<td id="shipCompany">
		</td>
		<th>载重吨 </th>
		<td>
			${bookShip.loading}
		</td>
	</tr>
	<tr>
		<th>定船费用</th>
		<td >
			${bookShip.bookShipFee }
		</td>
		<th>币种</th>
		<td id="currency">
		</td>
	</tr>
	<tr>
		<th>装港</th>
		<td id="portOfShipment">
		</td>
		<th>预计装港日期 </th>
		<td>
			<fmt:formatDate value='${bookShip.expectShipTime }' />
		</td>
	</tr>
	<tr>
		<th>目的港</th>
		<td id="destinationPort"  >
		</td>
		<th>预计卸港日期 </th>
		<td>
			<fmt:formatDate value='${bookShip.expectUnloadTime }' />
		</td>
	</tr>
	<tr>
		<th>受载期</th>
		<td >
			<fmt:formatDate value='${bookShip.shipingTime }' />
		</td>
		<th>签订时间</th>
		<td>
			<fmt:formatDate value='${bookShip.signedTime }' />
		</td>
	</tr>
	<tr>
		<th>是否提示派检</th>
		<td  id="pointInspection">
		</td>
		<th>运费率</th>
		<td>
			${bookShip.transportFeeRate }
		</td>
	</tr>
	<tr>
		<th>经济人</th>
		<td >
			${bookShip.broker }
		</td>
		<th>滞期费率</th>
		<td>
			${bookShip.demurrageRates }
		</td>
	</tr>
	<tr>
		<th>速遣费率</th>
		<td >
			${bookShip.despatchRates }
		</td>
		<th>装船期限</th>
		<td>
			${bookShip.loadDeadline }
		</td>
	</tr>
	<tr>
		<th>卸船期限</th>
		<td >
			${bookShip.unloadDeadline }
		</td>
		<th>吃水</th>
		<td>
			${bookShip.draft }
		</td>
	</tr>
	<tr>
		<th>流水号</th>
		<td colspan="3">
			${bookShip.serialNumber }
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="3">
			${bookShip.comment }
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${bookShip.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${bookShip.createrDept }</td>
	</tr>
</table>
</fieldset>
<script type="text/javascript">

$(function () {
	if('${bookShip.shipCompany }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${bookShip.shipCompany }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#shipCompany').html(data);
			}
		});
	}
	
	if('${bookShip.currency }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/currency/${bookShip.currency }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#currency').html(data);
			}
		});
	}

	if('${bookShip.portOfShipment }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getPortName/${bookShip.portOfShipment }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#portOfShipment').html(data);
			}
		});
	}
	
	if('${bookShip.destinationPort }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getPortName/${bookShip.destinationPort }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#destinationPort').html(data);
			}
		});
	}
	
	if('${bookShip.pointInspection }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/YESNO/${bookShip.pointInspection }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#pointInspection').html(data);
			}
		});
	}
	
	
	

});
</script>
</body>
</html>