<%@page import="com.cbmie.genMac.financial.entity.Payment"%>
<%@page import="com.cbmie.genMac.financial.entity.Expense"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="childTB"></table>
<%
Expense expense = (Expense)request.getAttribute("expense");
List<Payment> gcList = expense.getPaymentList();
ObjectMapper objectMapper = new ObjectMapper();
String gcJson = objectMapper.writeValueAsString(gcList);
request.setAttribute("gcJson", gcJson);
%>
<script type="text/javascript">
var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
		data : JSON.parse('${gcJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		columns:[[
		    {field:'documentNO',title:'单据号',width:80,editor:{type:'validatebox'}},
		{field:'paymentChildType',title:'付款子类型',width:80,
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'name',
					textField:'name',
					method:'get',
					url:'${ctx}/system/dict/getDictByCode/paymentChildType',
					required:true
				}
			}
		},
		{field:'expenseIdentifyWay',title:'费用认定方式',width:80,
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'name',
					textField:'name',
					method:'get',
					url:'${ctx}/system/dict/getDictByCode/expenseIdentifyWay',
					required:true
				}
			}	
		},
		{field:'importContract',title:'进口合同',width:80,
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'contractNO',
					textField:'contractNO',
					method:'get',
					url:'${ctx}/foreignTrade/importContract/getInvoiceRegImportContract',
					required:true
				}
			}	
		},
		{field:'expenseNature',title:'费用性质',width:80,
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'name',
					textField:'name',
					method:'get',
					url:'${ctx}/system/dict/getDictByCode/expenseNature',
					required:true
				}
			}		
		},
		{field:'billingCustomer',title:'结算客户',width:80,
			formatter: function(value,row,index){
				var val;
				$.ajax({
					type:'POST',
					async: false,
					url:"${ctx}/baseinfo/affiliates/typeAjax/"+row.billingCustomer,
					contentType:'application/json;charset=utf-8',
					success: function(data){
						if(data != null){
							val = data;
						} else {
							val = '';
						}
					}
				});
				return val;
			}		
		},
		{field:'originalCurrency',title:'金额',width:80,editor:{type:'numberbox',options:{min:0,precision:2,groupSeparator:',' ,required:true}}},
		{field:'departmentStaff',title:'部门人员',width:80,editor:{type:'validatebox',options:{required:true}}},
		{field:'prepayCustomer',title:'代垫客户',width:80,
			formatter: function(value,row,index){
				var val;
				$.ajax({
					type:'POST',
					async: false,
					url:"${ctx}/baseinfo/affiliates/typeAjax/"+row.prepayCustomer,
					contentType:'application/json;charset=utf-8',
					success: function(data){
						if(data != null){
							val = data;
						} else {
							val = '';
						}
					}
				});
				return val;
			}			
		}
			]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>