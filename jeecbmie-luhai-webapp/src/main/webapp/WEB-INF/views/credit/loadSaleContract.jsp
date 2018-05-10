<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contract" action="">
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '销售合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
<table id="saleContract_dg"></table>
<script type="text/javascript">
	var saleContract_dg;
	saleContract_dg=$('#saleContract_dg').datagrid({    
	method: "get",
	url:'${ctx}/sale/saleContract/json?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1', //&filter_EQS_state_ht=运行中
	fit : false,
    singleSelect:false,
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
			{field:'id',checkbox:true},
			{field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'sellerView',title:'卖方',sortable:true,width:20},
			{field:'purchaserView',title:'买方',sortable:true,width:20},
			{field:'signDate',title:'签订时间',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
    ]],
    sortName:'id',
    sortOrder: 'desc',
    selectOnCheck: true,
    checkOnSelect: true
	});

//保存
function initContract(){
	var rows = saleContract_dg.datagrid('getSelections');
	if(rowIsNull(rows)) return;
	var woodBillId ="";
    var relLoginNames = "";
    for (var i = 0; i < rows.length; i++) {
    	if(woodBillId!=""){
    		woodBillId = woodBillId +","+ rows[i].contractNo;
    	}else{
    		woodBillId = rows[i].contractNo;
    	}
    	if(relLoginNames!=""){
    		relLoginNames = relLoginNames +","+ rows[i].relLoginNames;
    	}else{
    		relLoginNames = rows[i].relLoginNames;
    	}
    }
    $('#relLoginNames').val(relLoginNames);
    $("#woodBillId").val(woodBillId);
}

//创建查询对象并查询
function cx_contract(){
	var obj=$("#searchFrom_contract").serializeObject();
	saleContract_dg.datagrid('reload',obj);
}

function reset_contract(){
	$("#searchFrom_contract")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_contract").serializeObject();
	saleContract_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>