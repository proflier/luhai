<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/plugins/artTemplate/test/js/ajaxfileupload.js"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
<form id="searchFrom" action="">
		<input type="text" id="filter_LIKES_billVoyage" name="filter_LIKES_billVoyage" class="easyui-validatebox" data-options="prompt: '开单航次'"/>
		 <input type="text" id="filter_EQS_goodsName" name="filter_EQS_goodsName" class="easyui-combobox" data-options="prompt: '物料名称'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</form>
<input  type="file" id="file" name="file" />
<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="ajaxFileUpload()" plain="true">上传</a> 
</div>
<table id="dgDetail" ></table> 
<script type="text/javascript">
var dgDetail;
var urlValueDetail =  '${ctx}/stock/outStock/outStockDetail/';
$(function(){   
	var idValue;
	idValue = $('#id').val();
	dgDetail=$('#dgDetail').datagrid({  
		method: "get",
   		url:urlValueDetail,
	    fit : true,
		fitColumns : false,
		border : false,
		striped : true,
		singleSelect : true,
		scrollbarSize : 0,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 40, 80 ],
		frozenColumns:[[
				{field:'id',title:'id',hidden:true},  
// 				{field:'numbering',title:'序号',sortable:true,width:60}, 
				{field:'shipment',title:'码头',sortable:true,width:60}, 
				{field:'shipTime',title:'发货日期',sortable:true,width:80},
				{field:'billTime',title:'开单日期',sortable:true,width:80},
				{field:'saleContractNo',title:'销售合同编号',sortable:true,width:80},
				{field:'deleveryNo',title:'发货通知编号',sortable:true,width:80},
				{field:'mainVoyage',title:'主航次',sortable:true,width:80},
		]],
	    columns:[[    
			{field:'billVoyage',title:'开单航次',sortable:true,width:100},
			{field:'transferVoyage',title:'调拨航次',sortable:true,width:100},
			{field:'goodsName',title:'物料名称',sortable:true,width:80},
			{field:'customerShort',title:'客户简称',sortable:true,width:80},
			{field:'saleCategory',title:'销售方式',sortable:true,width:80},
			{field:'transType',title:'运输方式',sortable:true,width:80},
			{field:'shipCompany',title:'承运商',sortable:true,width:60}, 
			{field:'poundsNo',title:'磅单号',sortable:true,width:60},
			{field:'license',title:'车牌号/<br/>火车卡号<br/>/船名',sortable:true,width:60},
			{field:'carNumber',title:'卡数/车数',sortable:true,width:60},
			{field:'trainWeight',title:'火车标重',sortable:true,width:60},
			{field:'railwayTrackScale',title:'火车轨道衡',sortable:true,width:70},
			{field:'electQuantity',title:'装车楼电子数',sortable:true,width:60},
			{field:'deliveryQuantity',title:'发货数量',sortable:true,width:60},
			{field:'chargeableWeight',title:'计费重量',sortable:true,width:60},
			{field:'shipmentMode',title:'码头作业方式',sortable:true,width:80},
			{field:'tractor',title:'是否拖头',sortable:true,width:60},
			{field:'transportTime',title:'承运时间',sortable:true,width:60},
			{field:'receiptDate',title:'收货日期',sortable:true,width:80},
			{field:'receiptQunatity',title:'收货数量',sortable:true,width:60},
			{field:'poundsDifference',title:'磅差',sortable:true,width:60}, 
			{field:'transportFee',title:'扣运费',sortable:true,width:60}, 
			{field:'planNo',title:'配煤计划号',sortable:true,width:60}, 
			{field:'checkNo',title:'检验批号',sortable:true,width:60}, 
			{field:'shipmentSpace',title:'码头作业场地',sortable:true,width:100}, 
			{field:'comment',title:'备注',sortable:true,width:100}, 
	    ]],
	    sortName:'id',
	    toolbar:'#tb'
	});
});

//商品名称
$('#filter_EQS_goodsName').combobox({
//		panelHeight:'auto',
	method: "get",
	url:'${ctx}/baseInfo/lhUtil/goodsItems',
	valueField:'infoName',
    textField:'infoName', 
//	    required : true,
});

//上传
function ajaxFileUpload(){
		parent.$.messager.progress({  
	        title : '提示',  
	        text : '数据处理中，请稍后....'  
	    });
		//重新绑定change事件
		$("#file").change(function(){  
            var filename = $(this).val();  
            $("#file").val(filename);  
        });		
		$.ajaxFileUpload({
            url:"${ctx}/stock/outStock/readExcel/",//用于文件上传的服务器端请求地址
            secureuri:false ,//一般设置为false
            fileElementId:'file',//文件上传控件的id属性  <input type="file" id="file" name="file" />
            dataType: 'JSON',//返回值类型 一般设置为json
            cache:false,
            success: function (data, status){  //服务器成功响应处理函数
            	if(data=='success'){
					parent.$.messager.show({ title : "提示",msg: "上传成功！", position: "bottomRight" });
					successTip(status,dgDetail);
            	}else{
            		parent.$.messager.show({ title : "提示",msg: "数据错误，上传失败！", position: "bottomRight" });
            	}
				parent.$.messager.progress('close');
            },
            complete: function(xmlHttpRequest) {
            	//重新绑定change事件
            	$("#file").replaceWith('<input type="file" id="file" name="file"/>');
            	$("#file").on("change", function(){  
                    var filename = $(this).val();  
                    $("#file").val(filename);  
                });
            },
            error: function (data, status, e){//服务器响应失败处理函数
            	parent.$.messager.alert({ title : "提示",msg: "服务器无响应，上传失败！", position: "bottomRight" });
				parent.$.messager.progress('close');
            }
        });
    return false;
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dgDetail.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dgDetail.datagrid('reload',obj); 
}

</script>
</body>
</html>