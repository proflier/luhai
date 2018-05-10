<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contract" action="">
	    <input type="text" name="filter_LIKES_cpContractNo" class="easyui-validatebox" data-options="prompt: '综合合同号'"/>
	    <input type="text" name="filter_LIKES_billNo" class="easyui-validatebox" data-options="prompt: '提单号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
<table id="bill_dg"></table>
<script type="text/javascript">
	var bill_dg;
	bill_dg=$('#bill_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistics/bills/json?filter_EQS_confirm=1', 
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
            {field:'billNo',title:'提单号',sortable:true,width:20},
// 			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
			{field:'shipName',title:'船名',sortable:true,width:20},
			{field:'voyage',title:'航次',sortable:true,width:20},
			{field:'numbers',title:'提单数量',sortable:true,width:20},
			{field:'supplier',title:'供货商',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.supplier!=''&&row.supplier!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.supplier,
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
			{field:'containerNumber',title:'集装箱数',sortable:true,width:20},
			{field:'portName',title:'目的港',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getPortName/'+value,
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
			{field:'currency',title:'币种',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.currency!=''&&row.currency!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getDictName/currency/'+value,
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

//保存
function initBillId(){
	var row = bill_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajax({
		type:'get',
		dataType: "json",//必须定义此参数
		url:'${ctx}/logistics/bills/json?filter_EQL_id='+row.id,
		success: function(data){
			 $("#inStockId").removeAttr("disabled",true);
			 $("#inStockDate").combo('enable');
			 $("#receiveWarehouse").combobox('enable');
			 $("#companyName").combobox('enable');
			 $("#payDept").removeAttr("disabled",true);
			 $("#shipName").removeAttr("disabled",true);
			 $("#voyage").removeAttr("disabled",true);
			 $("#lockSellingPrice").removeAttr("disabled",true);
			 $("#lockContract").combobox('enable');
			 $("#isOpen").combobox('enable');
			 $("#inStockType").combobox('enable');
			 $("#createStockDate").combo('enable');
			 $("#warehouseAccept").removeAttr("disabled",true);
			 $("#determineName").removeAttr("disabled",true);
			 $("#determineTime").combo('enable');
			 $("#currency").combo('enable');
// 			alert(JSON.stringify(data));
			$('#billId').val((data.rows)[0].billNo);
			$('#shipName').val((data.rows)[0].shipName);
			$('#voyage').val((data.rows)[0].voyage);
			$('#companyName').combobox('setValue',(data.rows)[0].ourUnit);
			$("#currency").combobox('setValue',(data.rows)[0].currency);
			initInStockGoods((data.rows)[0].returnJson);
			
			
			
		}
	});
}

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