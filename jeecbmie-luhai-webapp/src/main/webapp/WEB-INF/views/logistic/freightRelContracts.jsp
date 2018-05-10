<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_saleList" action="">
		&nbsp;
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同编号'"/>
	    <input type="hidden" name="filter_EQS_state" value="1"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_Config()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_Config()">重置</a>
	</form>
<table id="show_dg"></table>
<script type="text/javascript">
var row_t = {};
//保存 
function saveSelectContract(){
	if(row_t.contractNo){
		$("#mainform>#mainDiv #contractNo").val(row_t.contractNo);
		$("#mainform>#mainDiv #businessManager").combotree('setValue',row_t.businessManager);
	}
}
//创建查询对象并查询
function cx_Config(){
	var objPurchase=$("#searchFrom_saleList").serializeObject();
	saleC_dg.datagrid('reload',objPurchase);
}

function cz_Config(){
	$("#searchFrom_saleList").form('clear');
	cx_Config();
}

$(function(){
	saleC_dg=$('#show_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistic/highwayContract/json?filter_EQS_state=1', 
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
	sortName:'id',
	sortOrder:'desc',
    columns:[[    
			{field:'id',title:'',checkbox:true},  
			{field:'contractNo',title:'合同编号',width:20},
			{field:'money',title:'合同金额',width:20},
			{field:'moneyCurrencyCode',title:'保额币种',width:20,formatter: function(value,row,index){
				var val;
				if(value!=''&&value!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/dictUtil/getDictNameByCode/CURRENCY/"+value,
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
			}	}
    ]],
    onCheck:function(index,row){
    	row_t = row;
    },
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false
	});
});
	
</script>
</body>
</html>