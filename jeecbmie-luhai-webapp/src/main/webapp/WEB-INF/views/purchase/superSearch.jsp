<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 -->
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<form id="superSearch" action="">
		<table width="98%" class="tableClass" >
					<tr>
						<th  >内部合同号</th>
						<td >
							<input  name="filter_LIKES_innerContractNo" type="text"  class="easyui-validatebox"   />
						</td>
						<th  >币种</th>
						<td >
							<input id="currency" name="filter_LIKES_currency" type="text"  class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th width="20%" >采购合同号</th>
						<td width="30%">
							<input  name="filter_LIKES_purchaseContractNo" type="text" class="easyui-validatebox"  />
						</td>
						<th  width="20%">协议号</th>
						<td width="30%">
							<input  name="filter_LIKES_agreementNo" type="text"   class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >供货单位</th>
						<td>
							<input id="deliveryUnit" name="filter_LIKES_deliveryUnit" type="text"  class="easyui-validatebox" />
						</td>
						<th  >进口国别</th>
						<td>
							<input  id="importCountry" name="filter_LIKES_importCountry"  type="text" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >价格条款</th>
						<td>
							<input id="priceTerm" name="filter_LIKES_priceTerm" type="text"  class="easyui-validatebox"/>
						</td>
						<th  >应预付额</th>
						<td>
							<input  name="filter_LIKES_prepaidMoney" type="text"  class="easyui-numberbox" data-options="precision:'2'" />
						</td>
					</tr>
					<tr>
						<th  >订货总量</th>
						<td>
							<input  name="filter_LIKES_contractQuantity" type="text"  class="easyui-numberbox" data-options="precision:'3'"  />
						</td>
						<th  >数量单位</th>
						<td>
							<input id="prepaidMoney" name="filter_LIKES_quantityUnit" type="text"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th  >保证金</th>
						<td>
							<input  name="filter_LIKES_margin" type="text"  class="easyui-numberbox" data-options="precision:'2'"   />
						</td>
						<th  >合同金额</th>
						<td>
							<input  name="filter_LIKES_contractAmount" type="text"  class="easyui-numberbox" data-options="precision:'2'" />
						</td>
					</tr>
					<tr>
						<th  >付款方式</th>
						<td>
							<input id="payMode" name="filter_LIKES_payMode" type="text"  class="easyui-validatebox"  />
						</td>
						<th  >授信类型</th>
						<td>
							<input id="creditType" name="filter_LIKES_creditType" type="text" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >订货单位  </th>
						<td>
							<input id="orderUnit" name="filter_LIKES_orderUnit" type="text"  class="easyui-validatebox"  />
						</td>
						<th  >收货单位</th>
						<td>
							<input id="receivingUnit" name="filter_LIKES_receivingUnit" type="text"  class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >经营方式 </th>
						<td>
							<input id="runMode" name="filter_LIKES_runMode" type="text"  class="easyui-validatebox"  />
						</td>
						<th  >签约地点</th>
						<td>
							<input id="signingPlace" name="filter_LIKES_signingPlace" type="text"  class="easyui-validatebox" />
						</td>
					</tr>
				</table>
</form>
<script type="text/javascript">
//币种列表
$('#currency').combobox({
	panelHeight:'auto',
	url:'${ctx}/system/dictUtil/getDictByCode/currency',
    valueField:'id',
    textField:'name', 
});

// 	进口国别
$('#importCountry').combotree({
	method:'GET',
    url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
    idField : 'id',
    textFiled : 'name',
	parentField : 'pid',
    panelHeight:'auto',
    animate:false,
    multiline:false,
	panelHeight:'auto',
onHidePanel:function(){}
});
//签约地点
$('#signingPlace').combotree({
	method:'GET',
    url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
    idField : 'id',
    textFiled : 'name',
	parentField : 'pid',
    panelHeight:'auto',
    animate:false,
    multiline:false,
	panelHeight:'auto',
	onHidePanel:function(){}
});




//供应商下拉菜单
$('#deliveryUnit').combobox({
	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230002',
	valueField : 'id',
	textField : 'customerName',
});

//订货单位
$('#orderUnit').combobox({
	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230002',
	valueField : 'id',
	textField : 'customerName',
});

//收货单位
$('#receivingUnit').combobox({
	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230001',
	valueField : 'id',
	textField : 'customerName',
});


//数量单位列表
$('#quantityUnit').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/sldw',
    valueField:'id',
    textField:'name', 
});

//付款方式
$('#payMode').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/paymentMethod',
    valueField:'id',
    textField:'name', 
});


//价格条款
$('#priceTerm').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/jgtk',
    valueField:'id',
    textField:'name', 
});

//授信类型
$('#creditType').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/sxlx',
    valueField:'id',
    textField:'name', 
});


//合同类别
$('#contractCategory').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/htlb',
    valueField:'id',
    textField:'name', 
});

//经营方式
$('#runMode').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/runMode',
    valueField:'id',
    textField:'name', 
});

//印章类型 
$('#sealCategory').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/dictUtil/getDictByCode/SIGNETTYPE',
    valueField:'id',
    textField:'name',
});

</script>
</body>
</html>