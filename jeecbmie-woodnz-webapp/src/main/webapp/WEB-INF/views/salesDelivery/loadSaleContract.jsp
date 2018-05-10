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
	url : '${ctx}/sale/saleContract/json?filter_EQS_state=生效&filter_EQS_state_ht=运行中&filter_NES_tradeProperty=147',
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
                return '<input type="radio"  />';
            }},
            {field:'contractNo',title:'销售合同号',sortable:true,width:20}, 
			{field:'purchaser',title:'买方',sortable:true,width:20,
            	formatter: function(value,row,index){
					var val;
					if(row.purchaser!=''&&row.purchaser!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getBaseInfoName/'+row.purchaser ,
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
			{field:'seller',title:'卖方',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.seller!=''&&row.seller!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getBaseInfoName/'+row.seller ,
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
			{field:'totalMoney',title:'合同总额',sortable:true,width:20}
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
function initGoods(){
	var row = saleContract_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajax({
		type:'get',
		dataType: "json",//必须定义此参数
		url:'${ctx}/sale/saleContract/json?filter_EQL_id='+row.id,
		success: function(data){
			 $("#customerUnit").combobox('enable');
			 $("#contacts").removeAttr("disabled",true);
			 $("#contactsNumber").removeAttr("disabled",true);
			 $("#deliveryAddress").removeAttr("disabled",true);
			 $("#billingDate").combo('enable');
			 $("#contractTotal").numberbox({disabled:false});
			 $("#contractCumulation").numberbox({disabled:false});
			 $("#contractHaveLending").numberbox({disabled:false});
			 $("#customerCumulation").numberbox({disabled:false});
			 $("#customerHaveDelivery").numberbox({disabled:false});
			 $("#customerToltalOverage").numberbox({disabled:false});
			 $("#securityDeposit").numberbox({disabled:false});
			 $("#paymentMethod").combobox('enable');
			 $("#summaryAmount").numberbox({disabled:false});
			 $("#summaryNumber").numberbox({disabled:false});
			 $("#isProject").combobox('enable');
			 $("#contractExecutor").removeAttr("disabled",true);
			 $("#contractDelivery").combobox('enable');
			 $("#comment").removeAttr("disabled",true);
			 $("#isOpen").combobox('enable');
			 $("#priorityShippingNo").removeAttr("disabled",true);
			$('#billContractNo').val((data.rows)[0].contractNo);
			$('#customerUnit').combobox('setValue',(data.rows)[0].purchaser);
			$('#contractTotal').numberbox('setValue', (data.rows)[0].totalMoney);
			$('#securityDeposit').numberbox('setValue', (data.rows)[0].bail);
			$('#isProject').combobox('setValue',(data.rows)[0].ifZa);
			$('#contractExecutor').val((data.rows)[0].executer);
			$('#paymentMethod').combobox('setValue',(data.rows)[0].payWay);
			
			initSalesGoods((data.rows)[0].returnJson);
// 			alert(JSON.stringify(data));
			
			
			
		}
	});
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