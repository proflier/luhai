<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<form id="searchFrom" action="">
		<input type="text" id="shipNo" name="shipNo" class="easyui-validatebox" data-options="prompt: '船次(只输入船次)'"/>
		<input type="text" id="warehouseName" name="warehouseName" class="easyui-validatebox" data-options="prompt: '仓库'"/>
	    <input type="text" id="goodsName" name="goodsName" class="easyui-validatebox" data-options="prompt: '物料名称'"/>
    	<input type="text" id="category" name="category" class="easyui-validatebox" data-options="prompt: '类别'"/>
    	<input type="text" id="startTime" name="startTime" class="Wdate" placeholder="开始日期"/> - 
		<input type="text" id="endTime" name="endTime" class="Wdate" placeholder="结束日期"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
		<span class="toolbar-item dialog-tool-separator"></span>
<!-- 		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="excel();">导出</a> -->
	</form>
</div>
<table id="dg" ></table> 
<script type="text/javascript">
//仓库名称
$('#warehouseName').combobox({
	panelHeight : 'auto',
	method: "get",
	url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230007',
	valueField : 'customerName',
	textField : 'customerName',
//		required : true,
});


//商品名称
$('#goodsName').combobox({
//		panelHeight:'auto',
	method: "get",
	url:'${ctx}/baseInfo/lhUtil/goodsItems',
	valueField:'infoName',
    textField:'infoName', 
//	    required : true,
});

//类别
$('#category').combobox({
	panelHeight:'auto',
	valueField:'id',
    textField:'text', 
    data:[{    
        "id":0,    
        "text":"入库"   
    },{    
        "id":1,    
        "text":"调拨"   
    },{    
        "id":2,    
        "text":"盘库",    
    },{    
        "id":3,    
        "text":"出库"   
    }],
    onLoadSuccess:function(){
    	$('#category').combobox('clear');
    }
});

$('#startTime').bind('click',function(){
    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',onpicked:function(){endTime.click();}});
});
$('#endTime').bind('click',function(){
    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
});
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/stock/inAndOutAccount/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true}, 
			{field:'date',title:'日期',width:20}, 
			{field:'warehouse',title:'仓库',width:20},
			{field:'category',title:'单据类别',width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='0'){
							return "入库";
						}else if(value=="1"){
							return "调拨";
						}else if(value=="3"){
							return "上期结存";
						}else if(value=="2"){
							return "盘库";
						}else{
							return "出库";
						}
					}else{
						return '';
					}
				}	
			},
			{field:'voyage',title:'船次',width:35,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
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
			{field:'goodsName',title:'物料名称',width:10},
			{field:'inStockQuantity',title:'入库吨数',width:20,align:'right',
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.000; //如果不是数字，返回0.000
					}else{
//							value = Math.abs(value); //取绝对值
						value = parseFloat(value);
						return(value.toFixed(3)); 
					}
				}	
			},
			{field:'outStockQuantity',title:'出库吨数',width:20,align:'right',
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.000; //如果不是数字，返回0.000
					}else{
//							value = Math.abs(value); //取绝对值
						value = parseFloat(value);
						return(value.toFixed(3)); 
					}
				}	
			},
// 			{field:'surplusQuantity',title:'结余吨数',width:20}
	    ]],
	    groupField:'warehouse',
		view: groupview,
		groupFormatter:function(value, rows){
			var returnValue =0.000;
			if(value!=""){
				$.ajax({
					type:'GET',
					async: false,
					url:"${ctx}/stock/inAndOutAccount/getSurplusQuantity/"+value,
					success: function(data){
						returnValue = data;
					}
				});
			}
			return value +  "--结余吨数:<font style='color:#F00'>" +returnValue+"</font>"; 
		},
// 	    sortName:'id',
// 	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb'
	});
});

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$('#warehouseName').combobox('clear');
	$('#goodsName').combobox('clear');
	$('#category').combobox('clear');
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}


</script>
</body>
</html>