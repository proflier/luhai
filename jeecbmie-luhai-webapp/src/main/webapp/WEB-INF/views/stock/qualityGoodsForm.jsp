<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<form id="goodsForm" action="${ctx}/stock/qualityGoods/${actionGoods}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="30%">商品名称</th>
			<td>
				<input type="hidden" id="qualityGoodsId" name="id" value="${qualityGoods.id}" />
				<input type="hidden" name="pid" value="${qualityGoods.pid}" />
				<input type="hidden" id="goodsCode" name="goodsCode" value="${qualityGoods.goodsCode}" />
				<input id="goodsName" name="goodsName" value="${qualityGoods.goodsName }" class="easyui-validatebox"/>
			</td>
			<th>规格型号</th>
			<td>
				<input name="specifications" value="${qualityGoods.specifications }" class="easyui-validatebox" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<th>数量</th>
			<td>
				<input id="amount" name="amount" value="${qualityGoods.amount }" class="easyui-numberbox" data-options="required:true,precision:2"/>
			</td>
			<th>单位</th>
			<td>
				<mytag:combobox name="unit" value="${qualityGoods.unit}" type="dict" parameters="sldw"/>
			</td>
		</tr>
		<tr>
			<th>运输方式</th>
			<td colspan="3">
				<mytag:combobox name="transportation" value="${qualityGoods.transportation}" type="dict" parameters="YSFS"/>
			</td>
		</tr>
	</table>
	<fieldset class="fieldsetClass" >
		<legend>质量指标</legend>
		<input type="hidden" name="goodsIndexJson" id="goodsIndexJson"/>
		<%@ include file="/WEB-INF/views/stock/qualityIndexList.jsp"%>
	</fieldset>
</form>
<script type="text/javascript">

$(function(){
	$('#goodsForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#goodsForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		goodsObj.getList();
	    		$('#qualityGoodsId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示", msg : data.returnMsg, position : "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
		}
	});
	
	if (contractGoodsData.length > 0) {
		$('#goodsName').combogrid({
		    panelWidth:450,
		    idField:'goodsNameView',
		    textField:'goodsNameView',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
			required : true,
			data : contractGoodsData,
		    columns:[[
				{field:'goodsName',title:'商品编码',width:20},
				{field:'goodsNameView',title:'商品名称',width:20},
				{field:'goodsQuantity',title:'数量',width:20},
				{field:'unitsView',title:'单位',width:20}
		 	]],
		 	onSelect : function (rowIndex, rowData) {
				$("#amount").numberbox('setValue', rowData.goodsQuantity);
				$("#unit").combobox('setValue', rowData.units);
		 	}
		});
	} else {
		var urlGrid = "";
		if ($('#contractNo').val().indexOf("-C(SA)") > 0) {//销售合同
			urlGrid = "${ctx}/sale/saleContract/getByNo/" + $('#contractNo').val();
		} else {//采购合同
			urlGrid = "${ctx}/purchase/purchaseContract/getByNo/" + $('#contractNo').val();
		}
		if (urlGrid == "") {
			parent.parent.$.messager.alert("该合同下无商品信息，请确认！");
		} else {
			$('#goodsName').combogrid({
			    panelWidth:450,
			    method: "get",
			    url: urlGrid,
			    idField:'goodsNameView',
			    textField:'goodsNameView',
			    fit : false,
			    singleSelect:true,
				fitColumns : true,
				required : true,
			    columns:[[
					{field:'goodsName',title:'商品编码',width:20},
					{field:'goodsNameView',title:'商品名称',width:20},
					{field:'goodsQuantity',title:'数量',width:20},
					{field:'unitsView',title:'单位',width:20}
			 	]],
			 	onSelect : function (rowIndex, rowData) {
			 		$('#goodsCode').val(rowData.goodsName);
					$("#amount").numberbox('setValue', rowData.goodsQuantity);
					$("#unit").combobox('setValue', rowData.units);
				},
				loadFilter: function(data) {
					var rtnData = {};
					if (data.saleContractSubList == null) {
						rtnData.rows = data.purchaseContractGoodsList;
					} else {
						rtnData.rows = data.saleContractSubList;
					}
					return rtnData;
				}
			});
		}
	}
});
  
</script>
</body>
</html>

