<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_inspection" action="">
	    <input type="text" name="filter_LIKES_inspectionNo_OR_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '质检编码或合同号'"/>
	    <input type="text" name="filter_LIKES_shipNo" class="easyui-validatebox" data-options="width:150,prompt: '船编号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_inspection()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_inspection()">重置</a>
	</form>
<table id="inspection_dg"></table>
<script type="text/javascript">
	var inspection_dg;
	inspection_dg=$('#inspection_dg').datagrid({    
	method: "get",
	url:'${ctx}/stock/qualityInspection/json', 
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
                return '<input name="radio" type="radio" />';
            }},
            {field:'inspectionNo',title:'质检编码',sortable:true,width:20}, 
			{field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'billOrReleaseNo',title:'提单号/放货编号',sortable:true,width:15},
			{field:'goodsNameView',title:'品名',sortable:true,width:15},
			{field:'shipNoView',title:'船编号',sortable:true,width:15},
			{field:'inspectionAgency',title:'检验机构',sortable:true,width:15},
			{field:'portOrWarehouse',title:'港口/仓库名称',sortable:true,width:15},
			{field:'createrName',title:'制单人',sortable:true,width:15},
			{field:'createDate',title:'制单日',sortable:true,width:15}
    ]],
    onClickRow: function(rowIndex, rowData){
    	var oradio = $("input[name='radio']");
    	for(var i=0;i<oradio.length;i++){
    	    if(oradio[i].type == "radio"){
    	       oradio[i].checked = false
    	    }
    	}
      	 var radio = $("input[name='radio']")[rowIndex];
           if(radio.checked==false){
           	$("input[name='radio']")[rowIndex].checked = true;
           }else{
           	$("input[name='radio']")[rowIndex].checked = false;
           }
      },
    sortName:'id',
    sortOrder: 'desc'
	});


//创建查询对象并查询
function cx_inspection(){
	var obj=$("#searchFrom_inspection").serializeObject();
	inspection_dg.datagrid('reload',obj);
}

function reset_inspection(){
	$("#searchFrom_inspection")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_inspection").serializeObject();
	inspection_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>