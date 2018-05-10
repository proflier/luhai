<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<table id="port_dg"></table>
<script type="text/javascript">
	var port_dg;
	port_dg=$('#port_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistic/portContract/json', 
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	striped:true,
    columns:[[
            {field:'id',checkbox:true,width:20},
            {field:'contractNo',title:'合同号',sortable:true,width:20}, 
            {field:'signAffiliate',title:'签订单位',sortable:true,width:20,
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
			{field:'freeHeapDays',title:'免堆单天数',sortable:true,width:20}, 
			{field:'freeHeapCounts',title:'达量免堆数',sortable:true,width:20}, 
    ]],
	});
	
//保存 
function saveWharfSettlement(){
	var row_select = port_dg.datagrid("getSelected");
	if(row_select!=null){
		$("#mainform #wharf").val(row_select.contractNo);
		$("#mainform #relLoginNames").val(row_select.relLoginNames);
		$('#mainform #businessManager').combotree('setValue',row_select.businessManager);
	}
}

</script>
</body>
</html>