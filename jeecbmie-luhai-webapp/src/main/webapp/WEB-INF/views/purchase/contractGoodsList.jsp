<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addContractGoods();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateContractGoods();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteContractGoods();">删除</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="add_saleRelation();">选择销售合同</a>
     </div> 
</div>
<table id="dgContractGoods" ></table>
<script type="text/javascript">
var dgContractGoods;
var idValue;
initStreamContainer();
function initStreamContainer(value){
	//进行编辑时使用方法
	var urlValue;
	idValue = $('#id').val();
	if(idValue!=null&&idValue!=""){
		urlValue =  '${ctx}/purchase/purchaseContractGoods/getContractGodds/'+idValue;
	}else{
		urlValue="";
	}
	dgContractGoods=$('#dgContractGoods').datagrid({  
		method: "get",
		url:urlValue,
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
			{field:'goodsNameView',title:'品名',sortable:true,width:20},
			{field:'purchasePrice',title:'采购单价',sortable:true,width:20},
			{field:'goodsQuantity',title:'数量',sortable:true,width:20},
			{field:'amount',title:'金额',sortable:true,width:20},
			{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
	    ]],
	    onLoadSuccess: function(data){
		    	initMainForm(data);
		    	initGoodsFooter(data);
		},
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    showFooter: true,
	    toolbar:'#tbGoods'
	});
}

//加载footer
function initGoodsFooter(data) {
	$('#dgContractGoods').datagrid('reloadFooter',[
	                              	{ purchasePrice: "<font style='color:#F00'>合计：</font>",
	                              	goodsQuantity:"<font style='color:#F00'>"+getCurrentValue('goodsQuantity',data)+"</font>",
	                              	amount: "<font style='color:#F00'>"+getCurrentValue('amount',data)+"</font>"
	                              	}
	                              ]);
 }
 
function getCurrentValue(colName,data) {
    var total = 0.000;
    for (var i = 0; i < data.rows.length; i++) {
      total += parseFloat(data.rows[i][colName]);
    }
   	return total.toFixed(3);
}


</script>