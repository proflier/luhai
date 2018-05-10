<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<form id="mainform" action="${ctx}/offerManagement/proflitLossAccounting/${action}" method="post">
	<fieldset class="fieldsetClass">
	<legend>登记信息</legend>
		<table width="98%" class="tableClass">
	 		<tr>
				<th width="20%">上游合同号</th>
				<td width="30%">
				<input type="hidden" name="id" value="${proflitLossAccounting.id }"/>
				<input type="text" id="contractNo" name="contractNo" value="${proflitLossAccounting.contractNo }" class="easyui-validatebox"/>
				</td>
				<th width="20%">商品</th>
				<td width="30%">
				<input type="text" id="material" name="material" value="${proflitLossAccounting.material }"  class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>供应商</th>
				<td >
					<mytag:combobox name="supplier" value="${proflitLossAccounting.supplier}" type="customer" parameters="10230002" />
				</td>
				<th>采购美金单价</th>
				<td>
				<input type="text" id="priceProcurementDollars" name="priceProcurementDollars" value="${proflitLossAccounting.priceProcurementDollars }"  class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>数量</th>
				<td>
				<input type="text" id="volume" name="volume" value="${proflitLossAccounting.volume }" class="easyui-numberbox" 
						data-options="required:true,precision:3" />
				</td>
				<th>数量单位</th>
				<td>
					<input id="units" name="units"  type="text" value="${proflitLossAccounting.units}" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<th>金额</th>
				<td>
					<input type="text" id="value"  name="value" value="${proflitLossAccounting.value }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
				<th>海运费</th>
				<td>
					<input type="text" id="shippingFee" name="shippingFee" value="${proflitLossAccounting.shippingFee }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>装货费</th>
				<td>
					<input type="text" id="loadingCharges" name="loadingCharges" value="${proflitLossAccounting.loadingCharges }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
				<th>通知费+修改费+快递费</th>
				<td>
					<input type="text" id="extraFee" name="extraFee" value="${proflitLossAccounting.extraFee }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>人工费</th>
				<td>
					<input type="text" id="personFee" name="personFee" value="${proflitLossAccounting.personFee }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
				<th>港口费</th>
				<td>
					<input type="text" id="portFee" name="portFee" value="${proflitLossAccounting.portFee }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>资金成本</th>
				<td>
					<input type="text" id="capitalCost" name="capitalCost" value="${proflitLossAccounting.capitalCost }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
				<th>贴现</th>
				<td>
					<input type="text" id="discount" name="discount" value="${proflitLossAccounting.discount }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>议付费</th>
				<td>
					<input type="text" id="yeePay" name="yeePay" value="${proflitLossAccounting.yeePay }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
				<th>承兑手续费</th>
				<td>
					<input type="text" id="acceptanceFee" name="acceptanceFee" value="${proflitLossAccounting.acceptanceFee }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>保险费</th>
				<td>
					<input type="text" id="insurance" name="insurance" value="${proflitLossAccounting.insurance }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
				<th>销售美金单价</th>
				<td>
					<input type="text" id="unitSalesDollars" name="unitSalesDollars" value="${proflitLossAccounting.unitSalesDollars }" class="easyui-numberbox" 
						data-options="required:true,precision:2" />
				</td>
			</tr>
			<tr>
				<th>利润</th>
				<td>
					<input type="text" id="profit" name="profit" value="${proflitLossAccounting.profit }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
				<th>利润合计</th>
				<td>
					<input type="text" id="totalProfit" name="totalProfit" value="${proflitLossAccounting.totalProfit }" class="easyui-numberbox" 
						data-options="required:true,precision:2"/>
				</td>
			</tr>
			<tr>
				<th>目的港</th>
				<td colspan="3">
					<mytag:combobox name="destinationPort" type="customer" value="${proflitLossAccounting.destinationPort}" parameters="10230007" required="false"></mytag:combobox>
				</td>
			</tr>	
		</table>
	</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="20%" >登记人</th>
		<td width="30%">${empty proflitLossAccounting.createrName ? sessionScope.user.name : proflitLossAccounting.createrName }</td>
		<th width="20%" >登记部门</th>
		<td width="30%">${empty proflitLossAccounting.createrDept ? sessionScope.user.organization.orgName : proflitLossAccounting.createrDept }</td>
	</tr>
	<tr>
		<th width="20%" >登记时间</th>
		<td width="30%"><fmt:formatDate value="${ proflitLossAccounting.createDate  } " pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th width="20%" >最后修改时间</th>
		<td width="30%"><fmt:formatDate value="${proflitLossAccounting.updateDate } " pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
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
	    	successTip(data,dg,d);
			$.easyui.loaded();
	    }
	});
	
	
	
	//商品名称
	$('#material').combotree({
		method: "get",
        url:'${ctx}/baseInfo/lhUtil/goodsInfoTree',  
        idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:false,
	    multiline:false,
	    required : true,
// 		panelHeight:'auto',
		onSelect:function(node){
			//返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#material').combotree('clear');  
	        }  
		},
    	onHidePanel:function(){}
	});
	
	
	//数量单位列表
	$('#units').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/sldw',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	});
	
	//供应商
// 	$('#supplier').combobox({
// // 		panelHeight : 'auto',
// 		method: "get",
// 		url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230002',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 	});	
	
});

function calcValue(){
	var volume = parseFloat( $('#volume').val()).toFixed(3);
	var priceProcurementDollars = parseFloat( $('#priceProcurementDollars').val()).toFixed(2);
	var shippingFee = parseFloat( $('#shippingFee').val()).toFixed(2);
	var loadingCharges = parseFloat( $('#loadingCharges').val()).toFixed(2);
	var extraFee = parseFloat( $('#extraFee').val()).toFixed(2);
	var capitalCost = parseFloat( $('#capitalCost').val()).toFixed(2);
	var discount = parseFloat( $('#discount').val()).toFixed(2);
	var yeePay = parseFloat( $('#yeePay').val()).toFixed(2);
	var acceptanceFee = parseFloat( $('#acceptanceFee').val()).toFixed(2);
	var insurance = parseFloat( $('#insurance').val()).toFixed(2);
	var unitSalesDollars = parseFloat( $('#unitSalesDollars').val()).toFixed(2);
	var personFee = parseFloat( $('#personFee').val()).toFixed(2);
	var portFee = parseFloat( $('#portFee').val()).toFixed(2);
	
	if(isNaN(volume)||isNaN(priceProcurementDollars)){
	}else{
// 		alert(volume+':'+priceProcurementDollars);
		 $('#value').numberbox('setValue',volume*priceProcurementDollars);
		 $('#capitalCost').numberbox('setValue',(priceProcurementDollars*0.005).toFixed(2));
		 if(isNaN(shippingFee)||isNaN(loadingCharges)||isNaN(extraFee)||isNaN(capitalCost)||isNaN(discount)||isNaN(yeePay)||isNaN(acceptanceFee)||isNaN(insurance)||isNaN(unitSalesDollars)||isNaN(personFee)||isNaN(portFee)){
		 }else{
			 var profit = unitSalesDollars-insurance-acceptanceFee-yeePay-
			 discount-capitalCost-extraFee-loadingCharges-
			 shippingFee-priceProcurementDollars-
			 personFee-portFee;
			 $('#profit').numberbox('setValue',profit);
			 $('#totalProfit').numberbox('setValue',volume*profit);
		 }
	}
	
}
</script>
</body>
</html>