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
	<form id="goodsForm" action="${ctx}/stock/outStockGoods/${actionGoods}" method="post">
			<input id="contractNo" name="contractNo"  type="hidden" value="${saleDeliveryGoods.contractNo}" class="easyui-validatebox"/>
						<input type="hidden" id="saleDeliveryGoodsId" name="id" value="${saleDeliveryGoods.id}" />
						<input name="saleDeliveryId" type="hidden" value="${saleDeliveryGoods.saleDeliveryId}" />
						<input name="outStockId" type="hidden" value="${saleDeliveryGoods.outStockId}" />
						<input id="checkMaxValue" type="hidden" value="" />
			<table width="98%" class="tableClass">
				<%-- <tr>	
					<th >煤种</th>
					<td  ><input id="category" name="category"  type="text" value="${saleDeliveryGoods.category}" class="easyui-validatebox" /></td>
				</tr> --%>
				<tr>	
					<th >仓库</th>
					<td >
<%-- 						<mytag:combobox name="port" value="${saleDeliveryGoods.port}" type="customer" parameters="10230007"/> --%>
						<input id="port" name="port"  type="text" value="${saleDeliveryGoods.port}" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<th>商品名称</th>
					<td >
						<input id="goodsName" name="goodsName"  type="text" value="${saleDeliveryGoods.goodsName}" class="easyui-validatebox"/>
					</td>
				</tr>
				
				<tr>
					<th width="40%" >数量</th>
					<td width="50%" ><input id="quantity" name="quantity"  type="text" value="${saleDeliveryGoods.quantity}" onchange="checkValue()" class="easyui-numberbox" data-options="required:true,precision:'3'"/></td>			
				</tr>
				<tr>	
					<th  >数量单位</th>
					<td >
						<mytag:combobox name="units" value="${saleDeliveryGoods.units}" type="dict" parameters="sldw" required="false"/>
					</td>
				</tr>
				<tr>	
					<th >船名 </th>
					<input id="voy" name="voy"  type="hidden" value="${saleDeliveryGoods.voy}"  class="easyui-validatebox" />
					<input id="shipNoAndName" name="shipNoAndName"  type="hidden" value="${saleDeliveryGoods.shipNoAndName}"  class="easyui-validatebox" />
					<td  id="shipNoView">
					</td>
				</tr>
				<tr>	
					<th  >运输方式</th>
					<td >
						<mytag:combobox name="transType" value="${saleDeliveryGoods.transType}" type="dict" parameters="YSFS" required="false"/>
					</td>
				</tr>
				<tr>	
					<th  >开单船次</th>
					<td >
						<input id="billVoyage" name="billVoyage" type="text" value="${saleDeliveryGoods.billVoyage }"  class="easyui-validatebox"  />
					</td>
				</tr>
			</table>
	</form>
</div>

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
	    		goodsWindow.dgGoods.datagrid('clearSelections');
	    		goodsWindow.dgGoods.datagrid('reload');
	    		$('#saleDeliveryGoodsId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	
	
	//数量单位列表
	$('#units').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/sldw',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onLoadSuccess:function(){
	    	if("${actionGoods}"=="create"){
	    	$('#units').combobox('setValue',10580003);
	    	}
	    }
	});
	
	
	if('${saleDeliveryGoods.voy }'!=''){
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${saleDeliveryGoods.voy }',
			success: function(data){
				if(data != null){
					$('#shipNoView').html(data);
				} 
			}
		});
	}
	
	
	
	//发货码头
	/* $('#port').combobox({
// 		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/jsonGK',
		valueField : 'bm',
		textField : 'gkm',
		required : false
	}); */
	
	//商品类别
	/* $('#category').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/baseInfo/lhUtil/goodsTypeItems',
	    valueField:'typeCode',
	    textField:'typeName', 
	    required : true,
	    onSelect: function(data){
	    	//商品名称
	    	$('#goodsName').combobox({
	     		panelHeight:'auto',
	    		method: "get",
	    		url:'${ctx}/baseInfo/lhUtil/goodsItemsByCode?code='+data.typeCode,
	    	    valueField:'infoCode',
	    	    textField:'infoName', 
	    	    required : true,
	    	}).combobox('clear');  
	    }
	});
	
	var typeCode = $('#goodsName').val();
	
	$('#goodsName').combobox({
		method: "get",
		url:typeCode!=""?'${ctx}/baseInfo/lhUtil/goodsItemsByCode?code='+typeCode:'',
	    valueField:'infoCode',
	    textField:'infoName', 
	    required : true,
	}); */
	var deliveryNo = $('#deliveryNo').val();
	$('#port').combogrid({    
	    panelWidth:600,    
	    method: "get",
	    idField:'warehouseName',    
	    textField:'warehouseNameText',
	    fit : false,
	    required : true,
	    singleSelect:true,
		fitColumns : true,
	    url:'${ctx}/stock/inStockGoods/getAllByDeliveryNo/'+deliveryNo,
	    prompt: '请选择仓库',
	    columns:[[    
				{field:'warehouseName',title:'仓库',sortable:true,width:40,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else{
							return '';
						}
					}	
				},   
				{field:'shipNo',title:'船名',sortable:true,width:70,
					formatter: function(value,row,index){
						var val;
						if(value!=''&& value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else{
							return '';
						}
					}
				},
				{field:'goodsName',title:'商品名称',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else{
							return '';
						}
					}	
				},
				{field:'quantity',title:'数量',sortable:true,width:20},
				{field:'units',title:'数量单位',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/dictUtil/getDictNameByCode/sldw/'+value ,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else{
							return '';
						}
					}		
				},
	    ]],
	    onLoadSuccess:function(rowIndex,rowData){
	    	var g = $('#port').combogrid('grid');	// 获取数据表格对象
	    	var r = g.datagrid('getSelected');	// 获取选择的行
	    	$('#checkMaxValue').val(r.quantity);
	    },
	    onSelect:function(rowIndex, rowData){
	    	$('#goodsName').combotree('setValue', rowData.goodsName);
	    	$('#quantity').numberbox('setValue', rowData.quantity);
	    	$('#checkMaxValue').val(rowData.quantity);
	    	$('#voy').val(rowData.shipNo);
	    	$('#shipNoAndName').val(rowData.shipNoAndName);
	    	$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+rowData.shipNo,
				success: function(data){
					if(data != null){
						$('#shipNoView').html(data);
					} 
				}
			});
	    }
	}); 
	
	
	//商品名称
	$('#goodsName').combotree({
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
	            $('#goodsName').combotree('clear');  
	        }  
		},
    	onHidePanel:function(){}
	});
	
	$('#billVoyage').combogrid({    
	    panelWidth:450,
	    required:true,
	    method: "get",
	    idField:'shipNo',    
	    textField:'noAndName',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
	    url:'${ctx}/logistic/shipTrace/allShipTrace',
	    prompt: '请选择船编号',
	    columns:[[
// 				{field:'shipNo',title:'船编号',width:20},
// 				{field:'shipName',title:'船中文名',width:20},
				{field:'noAndName',title:'船编号',width:40},
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
	
	
		$('#goodsName').combotree({disabled:true});
	if("${actionGoods}"=="update"){
		$('#units').combobox({disabled:true});
		$('#port').combogrid({ disabled:true});
	}
});
  
  function checkValue(){
	  		var checkMaxValue = $('#checkMaxValue').val()*1.00;
	  		var quantity = $('#quantity').val()*1.00;
		  if(checkMaxValue==''){
			  $.messager.alert('警告','尚未选择仓库！或仓库不存在!'); 
		  }else{
			  if(quantity>checkMaxValue){
				  $.messager.alert('警告',"输入值超出仓库最大值"+checkMaxValue); 
			  }
		  }
  }
</script>
</body>
</html>

