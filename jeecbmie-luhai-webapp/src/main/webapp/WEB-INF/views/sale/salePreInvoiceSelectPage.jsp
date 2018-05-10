<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<table id="outSelect_dg"></table>
<script type="text/javascript">
	var outSelect_dg;
	/*  var dialog_param = $("#dlg_goods").dialog('options');
     var queryParams = dialog_param["queryParams"];
     var existIds = queryParams.existIds; */
     var invoiceIds = new Array();
     if("${invoiceIds}"!=null && "${invoiceIds}"!=""){
    	 invoiceIds = eval('(${invoiceIds})');
     }
	outSelect_dg=$('#outSelect_dg').datagrid({    
	method: "get",
	url:'${ctx}/sale/saleSettlement/getSaleInvoiceSelectItems/${settlementId}', 
    fit : false,
    singleSelect:false,
	fitColumns : true,
	border : false,
	idField : 'id',
	rownumbers:true,
	striped:true,
	 columns:[[    
				{field:'id',title:'id',checkbox:true}, 
				{field:'summary',title:'描述',width:40}, 
				{field:'goodsName',title:'品名',width:30},
				{field:'createDate',title:'制单日期',sortable:true,width:20,
					formatter:function(value,row,index){
						if(value == null){
							return null;
						}
	            		var time = new Date(value);
	            		return time.format("yyyy-MM-dd");
	            	}}
		    ]],
		    onLoadSuccess:function(data){
		 		 for(var j=0;j<invoiceIds.length;j++){
		 			outSelect_dg.datagrid("selectRecord",invoiceIds[j]);
		 		} 
			 }
	});
	
//保存 
function getSelectedInvoiceIds(){
	var row_selects = outSelect_dg.datagrid("getSelections");
	var outId_array = new Array();
	for(var i=0;i<row_selects.length;i++){
		outId_array.push(row_selects[i].id);
	}
	return outId_array;
}

</script>
</body>
</html>