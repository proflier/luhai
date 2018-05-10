<%@page import="com.cbmie.lh.sale.entity.SaleContractGoodsIndex"%>
<%@page import="com.cbmie.lh.sale.entity.SaleContractGoods"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
	<form id="contractGoodsForm" action="${ctx}/sale/saleContractGoods/${actionGoods}" method="post">
		<input type="hidden" id="saleContractGoodsId" name="id" value="${saleContractGoods.id}" />
		<input type="hidden" id="saleContractId" name="saleContractId" value="${saleContractGoods.saleContractId}" />
		
		<fieldset class="fieldsetClass" >
			<legend>采购信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%" >品名</th>
					<td width="30%" ><input id="goodsName" name="goodsName" type="text" value="${saleContractGoods.goodsName}" class="easyui-validatebox"/></td>
					<th width="20%" >数量</th>
					<td width="30%" ><input id="goodsQuantity" name="goodsQuantity"  type="text" value="${saleContractGoods.goodsQuantity}" class="easyui-numberbox" data-options="required:true,precision:'2',onChange:countAmount_sub"/></td>			
				</tr>
				<tr>
					<th  >数量单位</th>
					<td >
						<mytag:combobox name="units" value="${saleContractGoods.units}" type="dict" parameters="sldw" required="true"/>
					</td>
					<th>单价</th>
					<td><input id="price" name="price"  type="text" value="${saleContractGoods.price}" class="easyui-numberbox" data-options="required:true,precision:'2',onChange:countAmount_sub"/></td>		
				</tr>
				<tr>
					<th>金额</th>
					<td><input id="amount" name="amount"  type="text" value="${saleContractGoods.amount}"  class="easyui-numberbox" data-options="required:true,precision:'2'" readonly="readonly"/></td>
					<th>运输工具编号</th>
					<td colspan="3">
						<input id="shipNo" name="shipNo"  type="text" value="${saleContractGoods.shipNo }" class="easyui-combogrid"  />
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>指标信息</legend>
			<input type="hidden" name="goodsIndexJson" id="goodsIndexJson"/>
			
			<div id="childToolbar" style="padding:5px;height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</div>
			
			<table id="childTB" data-options="onClickRow: onClickRow,nowrap:false"></table>
			
			<%
				SaleContractGoods saleContractGoods = (SaleContractGoods)request.getAttribute("saleContractGoods");
				List<SaleContractGoodsIndex> saleGoodsIndexList = saleContractGoods.getGoodsIndexList();
				ObjectMapper objectMapperCus = new ObjectMapper();
				String childJson = objectMapperCus.writeValueAsString(saleGoodsIndexList);
				request.setAttribute("childJson", childJson);
			%>

			<script type="text/javascript">
			function countAmount_sub(){
				var goodsQuantity = $("#goodsQuantity").val()?$("#goodsQuantity").val():0;
				var price = $("#price").val()?$("#price").val():0;
				var amount = 0;
				amount = goodsQuantity*price;
				$("#amount").numberbox('setValue',amount);
			}
			var childTB;
			$(function(){
				childTB=$('#childTB').datagrid({
				data : JSON.parse('${childJson}'),
			    fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				extEditing:false,
			    columns:[[    
					{field:'id',title:'id',hidden:true},
					{field:'indexName',title:'指标值（中文）',width:20,
			     		editor:{
							type:'combobox',
							options:{
								valueField:'id',
								textField:'indexName',
								required:true,
								method:'get',
								url:'${ctx}/baseInfo/lhUtil/goodsIndexItems',
								onSelect:function(record){
									var indexNameEobj =  $('#childTB').datagrid('getEditor', {index:editIndex,field:'indexNameE'});
									if(indexNameEobj != null){
										$.ajax({
							 				url : '${ctx}/baseInfo/lhUtil/goodsindexNameEShow/' + record.id,
							 				type : 'get',
							 				success : function(data) {
					 							$(indexNameEobj.target).val(data);
							 				}
							 			});
									}
									var indexUnitobj =  $('#childTB').datagrid('getEditor', {index:editIndex,field:'unit'});
									$(indexUnitobj.target).combobox('setValue', record.unit);
								}
							}
						},
						formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/baseInfo/lhUtil/goodsIndexNameShow/"+value,
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
					{field:'indexNameE',title:'指标值（英文）',width:25,editor:{type:'validatebox',options:{disabled:true}}},
			     	{field:'unit',title:'单位',width:10,
						editor:{
							type:'combobox',
							options:{
								panelHeight : 'auto',
								required : true,
								url : '${ctx}/system/dictUtil/getDictByCode/INDEXUNIT',
								valueField : 'code',
								textField : 'name',
								disabled : false
							}
						},
						  formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/dictUtil/getDictNameByCode/INDEXUNIT/"+value,
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
						} },
			     	{field:'conventions',title:'约定值',width:20,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}},
			     	{field:'rejectionValue',title:'拒收值',width:20,editor:{type:'numberbox',options:{min:0,precision:2,required:false}}},
			     	{field:'terms',title:'扣罚条款',width:25,editor:{type:'validatebox'}}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#childToolbar'
				});
			});
			
			var editIndex = undefined;
			function endEditing(){
				if (editIndex == undefined){return true}
				if ($('#childTB').datagrid('validateRow', editIndex)){
					$('#childTB').datagrid('endEdit', editIndex);
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}
			
			function onClickRow(index){
				if (editIndex != index){
					if (endEditing()){
						$('#childTB').datagrid('selectRow', index)
								.datagrid('beginEdit', index);
						editIndex = index;
					} else {
						$('#childTB').datagrid('selectRow', editIndex);
					}
				}
			}
			function append(){
				if (endEditing()){
					$('#childTB').datagrid('appendRow',{conventions:'',unit:''});
					editIndex = $('#childTB').datagrid('getRows').length-1;
					$('#childTB').datagrid('selectRow', editIndex)
							.datagrid('beginEdit', editIndex);
				}
			}
			function removeit(){
				if (editIndex == undefined){return}
				$('#childTB').datagrid('cancelEdit', editIndex)
						.datagrid('deleteRow', editIndex);
				editIndex = undefined;
			}
			function accept(){
				if (endEditing()){
					var rows = $('#childTB').datagrid('getRows');
					$('#childTB').datagrid('acceptChanges');
					$('#goodsIndexJson').val(JSON.stringify(rows));
				}
			}
			function reject(){
				$('#childTB').datagrid('rejectChanges');
				editIndex = undefined;
			}
			function getChanges(){
				var rows = $('#childTB').datagrid('getChanges');
				$.messager.alert('提示', rows.length+' 行被修改！', 'info');
			}
			</script>			
		</fieldset>
	</form>
</div>

<script type="text/javascript">
$(function(){
	$('#contractGoodsForm').form({
	    onSubmit: function(){
	    	accept();
	    	var isValid = $('#contractGoodsForm').form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	$.easyui.loaded();
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dgContractGoods.datagrid('clearSelections');
	    		dgContractGoods.datagrid('reload');
	    		$('#saleContractGoodsId').val(data.returnId);
	    		$('#mainform #contractQuantity').numberbox('setValue', data.contractQuantity);
	    		$('#mainform #contractAmount').numberbox('setValue', data.contractAmount);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
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
	 $('#shipNo').combogrid({    
		    panelWidth:450,    
		    method: "get",
		    idField:'shipNo',    
		    textField:'noAndName',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    url:'${ctx}/logistic/shipTrace/allShipTrace', 
		    columns:[[    
					{field:'shipNo',title:'运输工具编号',width:20},
					{field:'shipName',title:'船中文名',width:20},
					{field:'shipNameE',title:'船英文名',width:20},
					{field:'loadPortView',title:'装港港口',width:20,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&&value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url:"${ctx}/baseInfo/baseUtil/getPortNameByCode/"+value,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}
					}
		 		]]
		});
});
</script>
</body>
</html>

