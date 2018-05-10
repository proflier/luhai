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
	<form id="subMainform" action="${ctx}/financial/inputInvoiceSub/${actionSub}" method="post">
	<input name="id"  id="inputId"  type="hidden" value="${inputInvoiceSub.id}"/>
	<input type="hidden" id="relLoginNames" name="relLoginNames" value="${inputInvoiceSub.relLoginNames }" />
	<input name="inputInvoiceSubId" id="inputInvoiceSubId" type="hidden" value="${inputInvoiceSub.inputInvoiceSubId}" />
	<div id="subMainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '结算信息', iconCls: false, refreshable: false">
		
			<fieldset class="fieldsetClass" >
				<legend>子表信息</legend>
				<table width="98%" class="tableClass">
				<tr>
					<th width="20%">采购订单号</th>
					<td>
						<input type="text" id="purchaseOrderNumber" name="purchaseOrderNumber" value="${inputInvoiceSub.purchaseOrderNumber }" readonly="readonly" onfocus="loadCredit()" class="easyui-validatebox" data-options="required:true"/>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadCredit()">选择采购订单号</a>
					</td>
					<th width="20%">相关销售订单 </th>
					<td>
						<input id="relatedSalesOrder" name="relatedSalesOrder" type="text" value="${inputInvoiceSub.relatedSalesOrder }" readonly="readonly"
							class="easyui-validatebox" data-options="required:true"/>
							<a id="toSaleListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSaleList()">销售合同</a>
					</td>
				</tr>
				<tr>
					<th width="20%">发票号</th>
					<td>
						<input name="invoiceNo"  id="invoiceNo_sub"  type="text" value="${inputInvoiceSub.invoiceNo}" class="easyui-validatebox" data-options="required:true" />
					</td>
					<th width="20%">开票日期</th>
					<td>
						<input id="billingDate_sub" name="billingDate" type="text" value="<fmt:formatDate value="${inputInvoiceSub.billingDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th width="20%">品名</th>
					<td>
						<input id="productName" name="productName"  type="text" value="${inputInvoiceSub.productName}" class="easyui-validatebox"/>
					</td>
					<th width="20%">计量单位</th>
					<td>
						<input id="unitMeasurement_sub" name="unitMeasurement"  type="text" value="${inputInvoiceSub.unitMeasurement}" class="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<th width="20%">数量</th>
					<td>
						<input name="mount"  id="mount_sub"  type="text" value="${inputInvoiceSub.mount}" class="easyui-numberbox" data-options="onChange:countQuantity_sub"/>
					</td>
					<th width="20%">开票单价 </th>
					<td>
						<input name="prices"  id="prices_sub"  type="text" value="${inputInvoiceSub.prices}" class="easyui-numberbox" data-options="precision:2,onChange:countQuantity_sub"/>
					</td>
				</tr>
				<tr>
					<th width="20%">开票金额 </th>
					<td>
						<input name="allPrices"  id="allPrices_sub"  type="text" value="${inputInvoiceSub.allPrices}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th width="20%">税额 </th>
					<td>
						<input name="taxMoney"  id="taxMoney_sub"  type="text" value="${inputInvoiceSub.taxMoney}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th>退税率</th>
					<td colspan="3">
						<input name="rebateRate"  id="rebateRate_sub"  type="text" value="${inputInvoiceSub.rebateRate}" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				</table>
			</fieldset>
		</div>	
	</div>
	</form>
	
</div>
<script type="text/javascript">
function loadCredit(){
	dlgCredit=$("#dlgCredit").dialog({
	  	width: 900,    
	    height: 400, 
	    title: '选择进口合同',
	    href:'${ctx}/credit/payApply/toContract',
	    modal:true,
	    closable:true,
	    style:{borderWidth:0},
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				initCredit();
				dlgCredit.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlgCredit.panel('close');
			}
		}]
	});
}
//保存
function initCredit(){
	var row = contract_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$('#purchaseOrderNumber').val(row.innerContractNo);
	$('#relLoginNames').val(row.relLoginNames);
	
}
function countQuantity_sub(){
	var mount = $("#mount_sub").val()?$("#mount_sub").val():0;
	var prices = $("#prices_sub").val()?$("#prices_sub").val():0;
	allPrices = prices*mount;
	$("#allPrices_sub").numberbox('setValue',allPrices);
}
$(function(){
	$('#subMainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(dataMotion){
	    	var data = eval('('+dataMotion+')');
	    	if(data.returnFlag=='success'){
	    		sub_obj.sub_form.panel('close');
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    	}  
	    	sub_obj.reload();
	    }
	});
	//计量单位
	$('#unitMeasurement_sub').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/sldw',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onLoadSuccess : function(data) {
	    	for(var i = 0; i < data.length; i++){
	    		if(data[i].name =='吨'){
	    			$('#unitMeasurement_sub').combobox('setValue', data[i].code);
	    		}
	    	}
        }
	});
	//商品名称
	$('#productName').combotree({
		method: "get",
        url:'${ctx}/baseInfo/lhUtil/goodsInfoTree',  
        idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    panelHeight:'auto',
	    animate:false,
	    multiline:false,
	    required : true,
		panelHeight:'auto',
		onSelect:function(node){
			//返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#productName').combotree('clear');  
	        }  
		},
    	onHidePanel:function(){}
	});
});

</script>
</body>
</html>

