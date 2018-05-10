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
<div>
	<form id="inStockNoticeGoodsForm" action="${ctx}/stock/inStockNoticeGoods/${actionGoods}" method="post">
			<table width="98%" class="tableClass">
				<tr>
					<th>内部合同号</th>
					<td >
						<input id="innerContractNo" name="innerContractNo"  type="text" value="${inStockNoticeGoods.innerContractNo}" class="easyui-validatebox"/>
						<input type="hidden" id="inStockNoticeGoodsId" name="id" value="${inStockNoticeGoods.id}" />
						<input name="inStockNoticeId" type="hidden" value="${inStockNoticeGoods.inStockNoticeId}" />
					</td>
				</tr>
				<tr>
					<th>商品名称</th>
					<td >
						<input id="goodsName" name="goodsName"  type="text" value="${inStockNoticeGoods.goodsName}" />
					</td>
				</tr>
				<tr>
					<th width="20%" >数量</th>
					<td width="30%" ><input id="quantity" name="quantity"  type="text" value="${inStockNoticeGoods.quantity}" class="easyui-numberbox" data-options="required:true,min:0,precision:'3'"/></td>			
				</tr>
				<tr>
					<th width="20%" >CIQ数量</th>
					<td width="30%" ><input id="quantity" name="quantity"  type="text" value="${inStockNoticeGoods.quantity}" class="easyui-numberbox" data-options="required:true,min:0,precision:'3'"/></td>			
				</tr>
				<tr>	
					<th width="20%" >数量单位</th>
					<td width="30%" ><input id="units" name="units"  type="text" value="${inStockNoticeGoods.units}" class="easyui-validatebox" /></td>
				</tr>
				<tr>	
					<th width="20%" >仓库名称</th>
					<td width="30%" >
						<mytag:combobox name="warehouseName" value="${inStockNoticeGoods.warehouseName}" type="customer" parameters="10230007" hightAuto="false"/>
					</td>
				</tr>
				<tr>
					<th>运输方式</th>
					<td>
						<mytag:combobox name="transportCategory" value="${inStockNoticeGoods.transportCategory}" type="dict" parameters="YSFS" required="true"/>
					</td>
					</tr>
				<tr>
					<th>运输编号</th>
					<td>
						<input id="shipNo" name="shipNo" type="text" value="${inStockNoticeGoods.shipNo }" class="easyui-validatebox"/>
					</td>
				</tr>
			</table>
	</form>
</div>

<script type="text/javascript">


$(function(){  
	
	$('#inStockNoticeGoodsForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#inStockNoticeGoodsForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dgInStockNoticeGoods.datagrid('clearSelections');
	    		dgInStockNoticeGoods.datagrid('reload');
	    		$('#inStockNoticeGoodsId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	
	
// 	//商品名称
	$('#goodsName').combotree({
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
	            $('#goodsName').combotree('clear');  
	        }  
		},
    	onHidePanel:function(){}
	});
	
	//数量单位列表
	$('#units').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/sldw',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onLoadSuccess : function(data) {
	    	for(var i = 0; i < data.length; i++){
	    		if(data[i].name =='吨'){
	    			$('#units').combobox('setValue', data[i].code);
	    		}
	    	}
        }
	});
	
	//仓库名称
// 	$('#warehouseName').combobox({
// 		panelHeight : 'auto',
// 		method: "get",
// 		url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230007',
// 		valueField : 'customerCode',
// 		textField : 'customerName',
// 		required : true,
// 	});
	
});
  
</script>
</body>
</html>

