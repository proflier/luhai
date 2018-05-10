<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.WoodBills"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.WoodBillsSub"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.WoodBillsGoods"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '到单信息', iconCls: false, refreshable: false">
		<fieldset class="fieldsetClass" >
			<legend>到单信息</legend>
			<table width="98%" class="tableClass">
			 <tr>				<th width="20">提单号</th>
				<td width="30">
				<input type="hidden" name="id" id="id" value="${bills.id}" />
				${bills.billNo}
				</td>
				<th width="20">二次换单提单号</th>
				<td width="30">${bills.twoTimeBillNo}</td>
			</tr>
			<tr>
				<th>供应商</th>
				<td id="supplier"></td>
				<th>我司单位</th>
				<td id="ourUnit">
			</tr>
			<tr>
				<th>预计到港日期</th>
				<td>
				<fmt:formatDate value="${bills.expectPortDate}" />	
				</td>
				<th>装运类别</th>
				<td id="shipmentType">		
				</td>			
			</tr>
			<tr>
				<th>正本到单日期</th>
				<td>
					<fmt:formatDate value="${bills.trueToDate}"/>
				</td>
				<th>币种</th>
				<td id="currency">		
				</td>			
			</tr>
			<tr>
				<th>免箱期天数</th>
				<td>${bills.noBoxDay}
				</td>
				<th>装船日期</th>
				<td><fmt:formatDate value="${bills.shipDate}" />		
				</td>			
			</tr>
			<tr>
				<th>目的港</th>
				<td id="portName">
				</td>
				<th>集装箱数</th>
				<td>	
					${bills.containerNumber}	
				</td>			
			</tr>
			<tr>
				<th><font style="vertical-align:middle;color:red" ></font>地区</th>
				<td id="area">
				</td>
				<th><font style="vertical-align:middle;color:red" ></font>包干费单价</th>
				<td>${bills.forwarderPrice}	
				</td>			
			</tr>
			<tr>
				<th><font style="vertical-align:middle;color:red" ></font>数量</th>
				<td>${bills.numbers}
				</td>
				<th><font style="vertical-align:middle;color:red" ></font>数量单位</th>
				<td id="numberUnits">
					
				</td>			
			</tr>
			<tr>
				<th>船名</th>
				<td>${bills.shipName}
				</td>
				<th>航次</th>
				<td>
					${bills.voyage}		
				</td>			
			</tr> 
			<tr>
				<th>船公司</th>
				<td>${bills.shipCompany}
				</td>
				<th>总件数/根数</th>
				<td>${bills.totalP}			
				</td>			
			</tr>
			<tr>
				<th>发票日期</th>
				<td>
					<fmt:formatDate value="${bills.invoiceDate}" />
				</td>
				<th>上游发票号</th>
				<td>${bills.invoiceNo}
				</td>			
			</tr>
			<tr>
				<th>发票金额</th>
				<td>  ${bills.invoiceAmount}</td>
				<th>登记时间</th>
				<td>
				<fmt:formatDate value="${bills.createDate}" />
				</td>
			</tr>
			<tr>
				<th>登记人</th>
				<td>
					${bills.createrName }
				</td>
				<th>登记部门</th>
				<td>
					${bills.createrDept }
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3" style="height:1cm">${bills.remark}</td>
			</tr>
			</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
				<legend>到单货物明细</legend>
				<table id="goodsTable" class="tableClass"></table>
			</fieldset>
		</div>	
		<div data-options="title: '箱单列表', iconCls: false, refreshable: false">
				<table class="tableClass" id="boxTable">
		</div>
</div>
<%
		WoodBills woodBills = (WoodBills)request.getAttribute("bills");
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> total = new HashMap<String, Object>();
		total.put("boxNo", "合计 : ");
		total.put("num", "");
		total.put("pieceNum", "");
		total.put("pNum", "");
		total.put("totalPrice", "");
		List<Map> totalList = new ArrayList<Map>();
		totalList.add(total);
		List<WoodBillsSub> subList = woodBills.getSubList();
		result.put("rows", subList);
		result.put("footer", totalList);
		ObjectMapper objectMapper = new ObjectMapper();
		String subJson = objectMapper.writeValueAsString(result);
		request.setAttribute("subJson", subJson);
		
		List<WoodBillsGoods>  woodBillsGoodsList = woodBills.getGoodsList();
		ObjectMapper objectMapperCusGoods = new ObjectMapper();
		String goodsJson = objectMapperCusGoods.writeValueAsString(woodBillsGoodsList);
		request.setAttribute("goodsJson", goodsJson);
%>
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
<script type="text/javascript">
var dgBox;
var dBox;
$(function(){
	dgBox=$('#boxTable').datagrid({  
		method: "get",
		data:JSON.parse('${subJson}'), 
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
			{field:'boxNo',title:'集装箱号',sortable:true,width:20},
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'goodsName',title:'商品名称',sortable:true,width:35},
			{field:'purchasePrice',title:'采购价',sortable:true,width:20},
			{field:'num',title:'数量',sortable:true,width:20},
			{field:'numberUnits',title:'数量单位',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getDictName/sldw/'+value,
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
			{field:'pieceNum',title:'件数',sortable:true,width:20},
			{field:'pNum',title:'片数',sortable:true,width:20},
			{field:'waterRate',title:'含水率(%)',sortable:true,width:10},
			{field:'currency',title:'币种',sortable:true,width:10},
			{field:'totalPrice',title:'总价',sortable:true,width:20}
	    ]],
	    groupField:'boxNo',
	    view: groupview,
		groupFormatter:function(value, rows){
			var totalNum = 0;//数量和
			var totalPieceNum = 0;//件数和
			var totalPNum = 0;//片数和=片数*件数之和
			var totalPrice = 0;//总价和
			var pNumPieceNum = 0;//片数*件数
			for(var i=0;i<rows.length;i++){
				totalNum =  totalNum + parseFloat(rows[i].num);
				totalPieceNum =  totalPieceNum + parseFloat(rows[i].pieceNum);
// 				totalPNum =  totalPNum + parseFloat(rows[i].pNum);
				totalPrice =  totalPrice + parseFloat(rows[i].totalPrice);
				totalPNum = totalPNum + parseFloat(rows[i].pieceNum)*parseFloat(rows[i].pNum);//片数*件数之和
			}
			return ' 集装箱号 ' +  '：   ' + value+' - 数量和: '+totalNum.toFixed(3)+' - 件数和: '+totalPieceNum.toFixed(3)
			+ ' - 片数和: '+totalPNum.toFixed(3)+ ' - 总价和: '+totalPrice.toFixed(2);
		},
	    sortName:'boxNo',
	    onLoadSuccess: function(data){
	    	onCountBoxDetail();
	 	},
	 	showFooter: true,
	 	enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	});
	
	$('#goodsTable').datagrid({  
		method: "get",
		data:JSON.parse('${goodsJson}'), 
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
				{field:'cghth',title:'采购合同号',sortable:true,width:20},
				{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
				{field:'spmc',title:'商品名称',sortable:true,width:20},
				{field:'spbm',title:'商品编码',sortable:true,width:20},
				{field:'sl',title:'数量',sortable:true,width:20},
				{field:'js',title:'件数',sortable:true,width:20},
				{field:'sldw',title:'数量单位',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/downMenu/getDictName/sldw/'+value,
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
				{field:'waterRate',title:'含水率(%)',sortable:true,width:10},
				{field:'cgdj',title:'采购价',sortable:true,width:20},
				{field:'cgbz',title:'币种',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/downMenu/getDictName/CURRENCY/'+value,
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
				{field:'cgje',title:'总价',sortable:true,width:20},
					
			    ]],
	    sortName:'id',
	    onLoadSuccess: function(data){
	    	onCountTolalDetail();
	    },
	    sortOrder:'desc',
	    showFooter: true,
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	});

	//我司单位
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${bills.ourUnit}',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#ourUnit').text(data);
		}
	});
	//供应商
	if('${bills.supplier }'!=''){
		$.ajax({
			url : '${ctx}/baseinfo/affiliates/typeAjax/${bills.supplier}',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#supplier').text(data);
			}
		});
	}
	
	//币种
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/currency/${bills.currency}',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#currency').text(data);
		}
	});
	
	//港口
	$.ajax({
		url : '${ctx}/system/downMenu/getPortName/${bills.portName}' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#portName').html(data);
		}
	});
	
	//装运类别
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/shipmentType/${bills.shipmentType}' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#shipmentType').html(data);
		}
	});

	//数量单位
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/sldw/${bills.numberUnits}' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#numberUnits').html(data);
		}
	});
	
	$.ajax({
		url : '${ctx}/system/countryArea/getName/' + ${bills.area},
		type : 'get',
		cache : false,
		success : function(data) {
			$('#area').html(data);
		}
	});
	
});

//指定列求和
function compute(colName) {
  var rows = $('#goodsTable').datagrid('getRows');
  var total = 0;
  for (var i = 0; i < rows.length; i++) {
    total += parseFloat(rows[i][colName]);
  }
  if(colName=='cgje'){
  	return total.toFixed(2);
  }
  if(total >= 0)
  	return total.toFixed(3);
  else
 		return '0';
}

//增加求和统计行
function onCountTolalDetail(){
	$('#goodsTable').datagrid('reloadFooter',[
         	{   spbm: '合计：',
	           	sl:compute("sl"),
	           	realsendVNum:compute("realsendVNum"),
	           	js:compute("js"),
	           	cgje: compute("cgje")}
         ]);
}




//增加求和统计行
function onCountBoxDetail() {
	var rows = $('#boxTable').datagrid('getFooterRows');
	rows[0]['num'] = computeBoxDetail("num");
	rows[0]['pieceNum'] = computeBoxDetail("pieceNum");
	rows[0]['pNum'] = computePieceNumDetail();
	rows[0]['totalPrice'] = computeBoxDetail("totalPrice");
	$('#boxTable').datagrid('reloadFooter');
	$('#invoiceAmount').numberbox('setValue', computeBoxDetail("totalPrice"));
	$('#numbers').numberbox('setValue', computeBoxDetail("num"));
 }
 
//指定列求和
function computeBoxDetail(colName) {
    var rows = $('#boxTable').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
      total += parseFloat(rows[i][colName]);
    }
    if(colName=='totalPrice')
    	return total.toFixed(2);
    else if(colName=='num' || colName=='pieceNum' || colName=='pNum')
    	return total.toFixed(3);
    else
    	return total.toFixed(0);
}

//片数列求和
function computePieceNumDetail() {
    var rows = $('#boxTable').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
    	total += parseFloat(rows[i].pieceNum) * parseFloat(rows[i].pNum);
    }
    return total.toFixed(3);
}
</script>
</body>
</html>