<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contract" action="">
	     <input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
<table id="bill_dg"></table>
<script type="text/javascript">
	var bill_dg;
	bill_dg=$('#bill_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistic/bills/json?filter_EQS_confirm=1', 
// 	url:'${ctx}/stock/inStock/selectBillsNoRepeict/'+billNo, 
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
	scrollbarSize : 0,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
    columns:[[    
			{field:'id',title: '选择',formatter: 
				function(value, rowData, rowIndex){
                return '<input type="radio" />';
            }},
            {field:'billNo',title:'到单号',sortable:true,width:20},
			{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
           	{field:'shipNoView',title:'船名称',sortable:true,width:20},
			{field:'numbers',title:'提单数量',sortable:true,width:20},
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:20},
			{field:'confirm',title:'确认状态',sortable:true,width:10,
				formatter:function(value,row,index){  
					if(value==1)
						return '已确认';
					else
						return '未确认';
				}}
    ]],
    onClickRow: function(rowIndex, rowData){
    	var oradio = $("input[type='radio']");
    	for(var i=0;i<oradio.length;i++){
    	    if(oradio[i].type == "radio"){
    	       oradio[i].checked = false
    	    }
    	}
      	 var radio = $("input[type='radio']")[rowIndex];
           if(radio.checked==false){
           	$("input[type='radio']")[rowIndex].checked = true;
           }else{
           	$("input[type='radio']")[rowIndex].checked = false;
           }
      },
    sortName:'id',
    sortOrder: 'desc'
	});


//创建查询对象并查询
function cx_contract(){
	var obj=$("#searchFrom_contract").serializeObject();
	bill_dg.datagrid('reload',obj);
}

function reset_contract(){
	$("#searchFrom_contract")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_contract").serializeObject();
	bill_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>