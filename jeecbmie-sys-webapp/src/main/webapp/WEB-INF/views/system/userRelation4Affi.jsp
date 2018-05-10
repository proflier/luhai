<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="affi_tool">   
	<form id="searchFrom_affi" action="">
	    <input type="text" name="customerName" class="easyui-validatebox" data-options="prompt: '客户名称'"/>
	    <input type="text" name="customerCode" class="easyui-validatebox" data-options="width:150,prompt: '客户编码'"/>
	    <input type="text" name="customerType" class="easyui-combobox" data-options="width:150,valueField: 'label',textField: 'value',panelHeight:'auto',prompt: '客户类型',data: [{label: '10230002',value: '供应商'}, {label: '10230003',value: '客户'}]" />
	    <input type="text" name="Hperson" class="easyui-validatebox" data-options="width:150,prompt: '业务经办人'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
</div>
<table id="affi_dg"></table>
<script type="text/javascript">
	var affi_dg;
	affi_dg=$('#affi_dg').datagrid({    
	method: "get",
	url:'${ctx}/baseinfo/affiliates/getOurUnitAndCustomer',
	fit : false,
    singleSelect:false,
	fitColumns : true,
	border : false,
	idField : 'id',
	striped:true,
    columns:[[    
			{field:'id',checkbox:true},
			{field:'customerCode',title:'客户编码',width:10},  
			{field:'customerName',title:'客户名称',width:15},
			{field:'customerType',title:'客户类别',width:10},
			{field:'Hperson',title:'业务经办人',width:10},
// 			{field:'ranges',title:'相关人员',width:10,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(row.customerCode!=''&&row.customerCode!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url : '${ctx}/system/customerPerssion/getRanges/'+row.customerCode,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						if(val.length>20){
// 							var returnValue = val.substring(0,20)+"...";
// 		                	return "<span title='" + val + "'>" + returnValue + "</span>";
// 						}else{return val;}
// 					}else{
// 						return '';
// 					}
// 				}},
    ]],
    onLoadSuccess:function(){
    	var data = eval('${affiCodes}');
         if(data){
                for(var i = 0;i<data.length;i++){ 
                	affi_dg.datagrid("selectRecord", data[i]);
                }
         }
    },
    sortName:'id',
    sortOrder: 'desc',
    selectOnCheck: true,
    checkOnSelect: true,
    toolbar:'#affi_tool'
	});

//保存
function saveRealtion(){
	var rows = affi_dg.datagrid('getSelections');
	var row = $('#dg').datagrid('getSelected');
	if(rowIsNull(rows)) return;
	var param= new Array();
    for (var i = 0; i < rows.length; i++) {
    		param.push(rows[i].customerCode);
    }
    $.ajax({
        url: "${ctx}/system/customerPerssion/saveRealtion/"+row.loginName,
        type: "GET",
        data: {
            "customerCodes": param,
        },
        traditional: true,//这里设置为true
        success: function(data) {
            //do sth...
        }
    });
    
}

//创建查询对象并查询
function cx_contract(){
	var obj=$("#searchFrom_affi").serializeObject();
	affi_dg.datagrid('reload',obj);
}

function reset_contract(){
	$("#searchFrom_affi")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_affi").serializeObject();
	affi_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>