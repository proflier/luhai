<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_sendGoodsList" >
		&nbsp;
	    <input type="text" id="filter_LIKES_saleContractNo" name="filter_LIKES_saleContractNo" class="easyui-validatebox" data-options="prompt: '销售合同号'"/>
	    <input type="text" id="dataSource" name="dataSource"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_PurchaseConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_PurchaseConfig()">重置</a>
	</form>
<table id="sendGoods_dg"></table>
<script type="text/javascript">
	var resultList;
	var sendGoods_dg;
	sendGoods_dg=$('#sendGoods_dg').datagrid({    
	method: "get",
	url:'${ctx}/stock/outStock/getSendGoodsList', 
    singleSelect:true,
	border : false,
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
    columns:[[    
			{field:'saleContractNo',title:'销售合同号',sortable:true}
    ]],
    frozenColumns:[[ 
		{field:'id',title: '选择',formatter: //id > 0代表放货数据源，id<0代表下游交单数据源
				function(value, rowData, rowIndex){
                return '<input type="radio" />';
         }},
		{field:'outId',title:'关联id',sortable:true,hidden:true},
		{field:'customerUnit',title:'客户单位',sortable:true,
			formatter: function(value,row,index){
				var val;
				if(row.customerUnit!=''&&row.customerUnit!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getBaseInfoName/"+row.customerUnit,
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
			}	},
		{field:'money',title:'金额',sort:true},
		{field:'state',title:'数据来源',sortable:true,
			formatter:function(value,row,index){  
        		if(row.id > 0){
        			return '放货';
        		}else{
        			return '下游交单';
        		}
         }},
		{field:'billNo',title:'提单号',sortable:true,
			formatter:function(value,row,index){  
        		if(row.id > 0){
        			return null;
        		}else{
        			return value;
        		}
         }}
	]] ,
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
	});
	

//创建查询对象并查询
function cx_PurchaseConfig(){
	var dataSource = $('#dataSource').combobox('getValue');
	var saleContractNo = $('#filter_LIKES_saleContractNo').val();
	if(saleContractNo==''){
		parent.$.messager.alert("请填写销售合同号！！");
		return;
	}
	if(dataSource==''){
		parent.$.messager.alert("请选择数据来源！");
		return;
	}
	var obj=$("#searchFrom_sendGoodsList").serializeObject();
	sendGoods_dg.datagrid('reload',obj);
}

function reset_PurchaseConfig(){
	$("#searchFrom_sendGoodsList")[0].reset();
	$('#dataSource').combobox('clear');
	var obj=$("#searchFrom_sendGoodsList").serializeObject();
	sendGoods_dg.datagrid('reload',obj);
}

var state;//存放state:1代表放货数据源，2代表下游交单数据源

//保存 
function setGoodsInfo(){
	var row = sendGoods_dg.datagrid('getSelected');
	if(rowIsNull(row)){return;}
	state = (row.id > 0 ?'1':'2');
	if(state=='1'){
		$('#deliveryId').val(row.id);//储存放货主表id
		$('#downBillsId').val("");
	}
	else if(state=='2'){
		$('#downBillsId').val(-row.id);//储存下游交单主表id
		$('#deliveryId').val("");
	}
	$.ajax({
		 type:"post",
		 dataType : 'json',
		 url:"${ctx}/stock/outStock/setGoodsInfo?id="+row.outId+'&state='+state ,
		 success:function(data){
				 if(data.result=='success'){
					$("#currency").combobox('setValue',data.currency);//币种赋值
					childTB.datagrid('reload');
					d_sendGoods.panel('close');
					return true;
			 	}else{
			 		return false;
			 	}
		 }
	});
}

//数据来源：放货，下游交单
$('#dataSource').combobox({
	panelHeight:'auto',
    valueField:'value',
    textField:'label',
    data: [{label: '放货',value: '放货'},
			{label: '下游交单',value: '下游交单'}],
	prompt: '数据来源'
});
</script>
</body>
</html>