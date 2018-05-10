<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_delivery" action="">
	    <input type="text" name="filter_LIKES_deliveryReleaseNo" class="easyui-validatebox" data-options="prompt: '发货通知号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_delivery()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_delivery()">重置</a>
	</form>
<table id="delivery_dg"></table>
<script type="text/javascript">
	var delivery_dg;
	delivery_dg=$('#delivery_dg').datagrid({    
	method: "get",
	url:'${ctx}/sale/saleDelivery/json?filter_EQS_state=1&filter_EQS_closeOrOpen=1', 
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
            {field:'deliveryReleaseNo',title:'发货通知号',sortable:true,width:20},
			{field:'sellerView',title:'客户名称',sortable:true,width:20},
			{field:'billDate',title:'制单日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}
			},
			{field:'saleModeView',title:'销售方式',sortable:true,width:20},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:20},
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
function cx_delivery(){
	var obj=$("#searchFrom_delivery").serializeObject();
	delivery_dg.datagrid('reload',obj);
}

function reset_delivery(){
	$("#searchFrom_delivery")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_delivery").serializeObject();
	delivery_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>