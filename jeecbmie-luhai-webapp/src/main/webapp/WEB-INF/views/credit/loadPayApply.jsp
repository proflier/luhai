<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_credit" action="">
	    <input type="text" name="filter_LIKES_payApplyNo" class="easyui-validatebox" data-options="prompt: '开证申请号'"/>
	    <input type="text" name="filter_LIKES_inteContractNo" class="easyui-validatebox" data-options="prompt: '内部合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_credit()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_credit()">重置</a>
	</form>
<table id="credit_dg"></table>
<script type="text/javascript">
	var credit_dg;
	credit_dg=$('#credit_dg').datagrid({    
	method: "get",
	url:'${ctx}/credit/payApply/json?filter_EQS_state=1',
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
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
                return '<input type="radio"  />';
            }},
        	{field:'inteContractNo',title:'内部合同号',sortable:true,width:20},
            {field:'contractNo',title:'采购合同号',sortable:true,width:20},
			{field:'payApplyNo',title:'开证申请号',sortable:true,width:20},
			{field:'ourUnitView',title:'开证单位',sortable:true,width:20},
			{field:'supplierView',title:'供应商',sortable:true,width:20}
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
function cx_credit(){
	var obj=$("#searchFrom_credit").serializeObject();
	credit_dg.datagrid('reload',obj);
}

function reset_credit(){
	$("#searchFrom_credit")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_credit").serializeObject();
	credit_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>