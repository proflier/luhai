<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contractList" action="">
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_config()">重置</a>
	</form>
<table id="contract_dg"></table>
<script type="text/javascript">
	var contract_dg;
	contract_dg=$('#contract_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistic/orderShipContract/json?filter_EQS_state=1', 
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
	singleSelect:true,
	sortName:'id',
    columns:[[
            {field:'id',checkbox:true,width:20},
            {field:'contractNo',title:'合同号',width:20}, 
            {field:'innerContractNo',title:'内部合同号',width:20}, 
			{field:'money',title:'金额',width:20},
			{field:'orderShipType',title:'订船类型',width:20,
				formatter: function(value,row,index){
					var unit_t = '';
					if(value!=null && value!=""){
						if(value=='%'){value='%25';}
						$.ajax({
							url : '${ctx}/system/dictUtil/getDictNameByCode/orderShipType/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								unit_t = data;
							}
						});
					}
					return unit_t;
				}},
			{field:'applyDate',title:'申请日期',sortable:true,width:20,
				formatter:function(value,row,index){  
					if(value==null){
						return null;
					}
	                    var unixTimestamp = new Date(value);  
	                    return unixTimestamp.format("yyyy-MM-dd");  
                    } 
			}
    ]],
	});
	
//保存 
function saveOrderShipContract(){
	var row_select = contract_dg.datagrid("getSelected");
	if(row_select!=null){
		$("#contractNo").val(row_select.innerContractNo);
		$('#tradeCategory').combobox('setValue', row_select.orderShipType);
		$("#accountName").combobox('setValue', row_select.traderName);
		$("#bank").combobox('enable');
		$("#relLoginNames").val(row_select.relLoginNames);
		$('#businessManager').combotree('setValue',row_select.businessManager);
	}
}

//创建查询对象并查询
function cx_config(){
	var objPurchase=$("#searchFrom_contractList").serializeObject();
	contract_dg.datagrid('reload',objPurchase);
}

function cz_config(){
	$("#searchFrom_contractList").form('clear');
	cx_config();
}

</script>
</body>
</html>