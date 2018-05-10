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
	 var dialog_param = $("#dlg_goods").dialog('options');
     var queryParams = dialog_param["queryParams"];
     var existIds = queryParams.existIds;
	outSelect_dg=$('#outSelect_dg').datagrid({    
	method: "get",
	url:'${ctx}/sale/invoiceOutRel/getOutsSelectItem/${saleContractNo}/${invoiceId}', 
    fit : false,
    singleSelect:false,
	fitColumns : true,
	border : false,
	idField : 'id',
	rownumbers:true,
	striped:true,
	 columns:[[    
				{field:'id',title:'id',checkbox:true}, 
				{field:'port',title:'仓库',sortable:true,width:20,
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
				{field:'voy',title:'船名',sortable:true,width:20,
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
					}},
					{field:'outStockNo',title:'出库单号',width:25},
					{field:'outStockDate',title:'出库日期',width:20,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd");
		            	}},
				{field:'goodsNo',title:'品名',sortable:true,width:20,
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
				{field:'quantity',title:'数量',sortable:true,width:15},
				{field:'transType',title:'运输方式',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/dictUtil/getDictNameByCode/YSFS/'+value ,
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
				}
		    ]],
		    onLoadSuccess:function(data){
		 		for(var j=0;j<existIds.length;j++){
		 			outSelect_dg.datagrid("selectRecord",existIds[j]);
		 		}
			 }
	});
	
//保存 
function getSelectedOutIds(){
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