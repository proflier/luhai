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
<form id="goodsForm" action="${ctx}/logistic/customsDeclarationGoods/${action }" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th width="20%">项号</th>
		<td width="30%">
			<input type="hidden" name="id" value="${customsDeclarationGoods.id}" />
			<input type="hidden" name="pid" value="${customsDeclarationGoods.pid}" />
			<input name="itemNo" value="${customsDeclarationGoods.itemNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th width="20%">商品编号</th>
		<td width="30%">
			<input id="goodsCode" name="goodsCode" value="${customsDeclarationGoods.goodsCode }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>商品名称</th>
		<td>
			<input id="goodsName" name="goodsName" value="${customsDeclarationGoods.goodsName }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>规格型号</th>
		<td>
			<input name="specifications" value="${customsDeclarationGoods.specifications }" class="easyui-validatebox" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th>数量</th>
		<td>
			<input id="amount" name="amount" value="${customsDeclarationGoods.amount }" class="easyui-numberbox" data-options="required:true,precision:2,
			onChange:function(newValue, oldValue){
				$('#total').numberbox('setValue', $('#price').numberbox('getValue') * newValue);
			}"/>
		</td>
		<th>单位</th>
		<td>
			<mytag:combobox name="unit" value="${customsDeclarationGoods.unit}" type="dict" parameters="sldw"/>
		</td>
	</tr>
	<tr>
		<th>原产国（地区）</th>
		<td colspan="3">
			<mytag:combotree name="originCountry" value="${customsDeclarationGoods.originCountry}" type="countryAreaTree"/>
		</td>
	</tr>
	<tr>
		<th>单价</th>
		<td>
			<input id="price" name="price" value="${customsDeclarationGoods.price }" class="easyui-numberbox" data-options="required:true,precision:2,
			onChange:function(newValue, oldValue){
				$('#total').numberbox('setValue', $('#amount').numberbox('getValue') * newValue);
			}"/>
		</td>
		<th>总价</th>
		<td>
			<input id="total" name="total" value="${customsDeclarationGoods.total }" class="easyui-numberbox" data-options="required:true,precision:2"/>
		</td>
	</tr>
	<tr>
		<th>币制</th>
		<td>
			<mytag:combobox name="monetary" value="${customsDeclarationGoods.monetary}" type="dict" parameters="CURRENCY"/>
		</td>
		<th>征免</th>
		<td>
			<mytag:combobox name="shallBe" value="${customsDeclarationGoods.shallBe}" type="dict" parameters="SHALLBE"/>
		</td>
	</tr>
</table>
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
	    		goodsObj.goodsFormPage.panel('close');
	    		parent.$.messager.show({ title : "提示", msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	});
	
	//商品编码
	$('#goodsCode').combogrid({
	    panelWidth:450,
	    method: "get",
	    idField:'infoCode',
	    textField:'infoCode',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
		required : true,
	    url:'${ctx}/baseInfo/lhUtil/goodsItems',
	    columns:[[
			{field:'goodsTypeCode',title:'商品类别编码',width:20},
			{field:'infoCode',title:'商品编码',width:20},
			{field:'infoName',title:'商品名称',width:20}
	 	]],
	 	onSelect : function (rowIndex, rowData) {
	 		$("#goodsName").val(rowData.infoName);
	 	}
	});
});
  
</script>
</body>
</html>

