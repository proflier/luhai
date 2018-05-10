<%@page import="com.cbmie.genMac.financial.entity.Payment"%>
<%@page import="com.cbmie.genMac.financial.entity.Expense"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTB_payment" data-options="onClickRow: onClickRow">
</table>
<%
	Expense expense = (Expense)request.getAttribute("expense");
	List<Payment> gcList = expense.getPaymentList();
	ObjectMapper objectMapper = new ObjectMapper();
	String gcJson = objectMapper.writeValueAsString(gcList);
	request.setAttribute("gcJson", gcJson);
%>
<script type="text/javascript">
var childTB_payment;
$(function(){
	childTB_payment=$('#childTB_payment').datagrid({
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
		{field:'id',title:'id',hidden:true},
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
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'customerName',
					method:'get',
					url:'${ctx}/baseinfo/affiliates/getSelfCompany',
					required:true
				}
			}		
		},
		{field:'originalCurrency',title:'金额',width:80,editor:{type:'numberbox',options:{min:0,precision:2,groupSeparator:',' ,required:true}}},
		{field:'departmentStaff',title:'部门人员',width:80,editor:{type:'validatebox',options:{required:true}}},
		{field:'prepayCustomer',title:'代垫客户',width:80,
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'customerName',
					method:'get',
					url:'${ctx}/baseinfo/affiliates/getSelfCompany',
					required:true
				}
			}			
		}
	]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB_payment').datagrid('validateRow', editIndex)){
		$('#childTB_payment').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB_payment').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB_payment').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#childTB_payment').datagrid('appendRow',{documentNO:''});
		editIndex = $('#childTB_payment').datagrid('getRows').length-1;
		$('#childTB_payment').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB_payment').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB_payment').datagrid('getRows');
		$('#childTB_payment').datagrid('acceptChanges');
		$('#paymentChildJson').val(JSON.stringify(rows));
	}
	calculate();
}
function reject(){
	$('#childTB_payment').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB_payment').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function calculate(){
	var rows =  $('#childTB_payment').datagrid('getRows');
	var originalCurrency = 0;
	for(var i = 0; i < rows.length; i++){
		originalCurrency += parseFloat(rows[i].originalCurrency);
	}
	$('#originalCurrency').numberbox({
		value:originalCurrency
	});
	setOweCurrency();
}

</script>