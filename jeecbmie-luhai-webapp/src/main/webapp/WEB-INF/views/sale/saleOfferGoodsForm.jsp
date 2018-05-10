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
<form id="goodsForm" action="${ctx}/sale/saleOfferGoods/${actionGoods}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="30%">商品名称</th>
			<td>
				<input type="hidden" id="saleOfferGoodsId" name="id" value="${saleOfferGoods.id}" />
				<input type="hidden" name="pid" value="${saleOfferGoods.pid}" />
				<input type="hidden" id="goodsName" name="goodsName" value="${saleOfferGoods.goodsName}" />
				<input id="goodsCode" name="goodsCode" value="${saleOfferGoods.goodsCode }" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr>
			<th>数量</th>
			<td>
				<input id="amount" name="amount" value="${saleOfferGoods.amount }" class="easyui-numberbox" data-options="required:true,precision:2"/>
			</td>
		</tr>
		<tr>
			<th>单位</th>
			<td>
				<mytag:combobox name="unit" value="${saleOfferGoods.unit}" type="dict" parameters="sldw"/>
			</td>
		</tr>
		<tr>
			<th>报价</th>
			<td>
				<input name="offer" value="${saleOfferGoods.offer }" class="easyui-numberbox" data-options="required:true,precision:2"/>
			</td>
		</tr>
	</table>
	<fieldset class="fieldsetClass" >
		<legend>质量指标</legend>
		<input type="hidden" name="goodsIndexJson" id="goodsIndexJson"/>
		<%@ include file="/WEB-INF/views/sale/saleOfferIndexList.jsp"%>
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
	    		$('#saleOfferGoodsId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示", msg : data.returnMsg, position : "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
		}
	});
	
	//商品名称
	$('#goodsCode').combotree({
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
	            $('#goodsCode').combotree('clear');  
	        } else {
	        	$('#goodsName').val(node.name);
	        }
		},
    	onHidePanel:function(){}
	});
});
  
</script>
</body>
</html>

