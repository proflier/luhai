<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.DownstreamGoods"%>
<%@page import="java.util.List"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.DownstreamBill"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '交单信息', iconCls: false, refreshable: false">
		<fieldset class="fieldsetClass" >
			<legend>交单信息</legend>
			<table width="98%" class="tableClass">
			 <tr>				
			 	<th width="20%">提单号</th>
				<td width="30%">
					<input type="hidden" name="id" id="id" value="${downstreamBill.id}" />
				${downstreamBill.billNo}
				</td>
				<th width="20%">二次换单提单号</th>
				<td width="30%">${downstreamBill.twoTimeBillNo}</td>
			</tr>
			<tr>
				<th >销售合同号</th>
					<td >
						${downstreamBill.saleContarteNo}
					</td>
				<th >下游客户</th>
					<td id="downStreamClient">
					</td>
				</tr>
			<tr>
				<th>供应商</th>
				<td id="supplier"></td>
				<th>我司单位</th>
				<td id="ourUnit"></td>
			</tr>
			<tr>
					<th >快递号</th>
					<td >${downstreamBill.expressNo}</td>
					<th >快递日期</th>
					<td >
					<fmt:formatDate value="${downstreamBill.expressDate}" />
					</td>
			</tr>
			<tr>
				<th>预计到港日期</th>
				<td>
				<fmt:formatDate value="${downstreamBill.expectPortDate}" />	
				</td>
				<th>装运类别</th>
				<td id="shipmentType">		
				</td>			
			</tr>
			<tr>
				<th>正本到单日期</th>
				<td>
					<fmt:formatDate value="${downstreamBill.trueToDate}"/>
				</td>
				<th>币种</th>
				<td id="currency">		
				</td>			
			</tr>
			<tr>
				<th>免箱期天数</th>
				<td>${downstreamBill.noBoxDay}
				</td>
				<th>装船日期</th>
				<td><fmt:formatDate value="${downstreamBill.shipDate}" />		
				</td>			
			</tr>
			<tr>
				<th>目的港</th>
				<td id="portName">
				</td>
				<th>集装箱数</th>
				<td>	
					${downstreamBill.containerNumber}	
				</td>			
			</tr>
			<tr>
				<th><font style="vertical-align:middle;color:red" ></font>地区</th>
				<td id="area">
				</td>
				<th><font style="vertical-align:middle;color:red" ></font>包干费单价</th>
				<td>${downstreamBill.forwarderPrice}	
				</td>			
			</tr>
			<tr>
				<th><font style="vertical-align:middle;color:red" ></font>数量</th>
				<td>${downstreamBill.numbers}
				</td>
				<th><font style="vertical-align:middle;color:red" ></font>数量单位</th>
				<td id="numberUnits">
					
				</td>			
			</tr>
			<tr>
				<th>船名</th>
				<td>${downstreamBill.shipName}
				</td>
				<th>航次</th>
				<td>
					${downstreamBill.voyage}		
				</td>			
			</tr> 
			<tr>
				<th>船公司</th>
				<td>${downstreamBill.shipCompany}
				</td>
				<th>总件数/跟数</th>
				<td>${downstreamBill.totalP}			
				</td>			
			</tr>
			<tr>
				<th>发票日期</th>
				<td>
					<fmt:formatDate value="${downstreamBill.invoiceDate}" />
				</td>
				<th>发票金额</th>
				<td>  ${downstreamBill.invoiceAmount}</td>
			</tr>
			<tr>
				<th>上游发票号</th>
				<td>
					${downstreamBill.invoiceNo}		
				</td>
				<th>下游发票号</th>
				<td>
					${downstreamBill.downInvoiceNo}			
				</td>
			</tr>
			<tr>
				<th>交单期</th>
				<td>
					<fmt:formatDate value="${downstreamBill.giveBillsDate}" />
				</td>
				<th>登记时间</th>
				<td>
				<fmt:formatDate value="${downstreamBill.createDate}" />
				</td>
			</tr>
			<tr>
				<th>登记人</th>
				<td>
					${downstreamBill.createrName }
				</td>
				<th>登记部门</th>
				<td>
					${downstreamBill.createrDept }
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3" style="height:1cm">${downstreamBill.remark}</td>
			</tr>
			</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
				<legend>到单货物明细</legend>
				<style>
				    .datagrid-footer td{font-weight: bold;}
				</style>
				<table id="childTB" class="tableClass"></table>
			</fieldset>
		</div>	
		<div data-options="title: '箱单列表', iconCls: false, refreshable: false">
				<table class="tableClass" id="dgContainer"></table>
		</div>
		</div>
</div>
<%
	DownstreamBill downstreamBill = (DownstreamBill)request.getAttribute("downstreamBill");
	List<DownstreamGoods> downstreamGoodsList = downstreamBill.getDownstreamGoodsList();
	ObjectMapper objectMapperGoods = new ObjectMapper();
	String goodsJson = objectMapperGoods.writeValueAsString(downstreamGoodsList);
	request.setAttribute("goodsJson", goodsJson);
%>
<script type="text/javascript">
var childTB;
initStreamGoods();
function initStreamGoods(value){
	childTB=$('#childTB').datagrid({
// 		data : JSON.parse('${childJson}'),
		data : typeof(value) == "undefined"? JSON.parse('${goodsJson}'):(value==null?JSON.parse('[]'):JSON.parse(value) ) ,
	    fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[ 
			{field:'id',title:'id',hidden:true}, 
			{field:'cghth',title:'采购合同号',sortable:true,width:20},
			{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'spmc',title:'商品名称',sortable:true,width:30},
			{field:'spbm',title:'商品编码',sortable:true,width:20},
			{field:'sl',title:'数量',sortable:true,width:10},
			{field:'js',title:'件数',sortable:true,width:10},
			{field:'waterRate',title:'含水率(%)',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
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
			{field:'cgdj',title:'销售价',sortable:true,width:15},
			{field:'cgbz',title:'币种',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.cgbz!=''&&row.cgbz!=null){
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
				}},
			{field:'cgje',title:'总价',sortable:true,width:15}
	    ]],
	    onLoadSuccess: function(data){
	    },
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    showFooter: true,
	    onLoadSuccess: function(data){
	    	initGoodsFooter(data);
		 },
	    toolbar:'#tbGoods'
	});
}

var dgContainer;
var idValue;
initStreamContainer();
function initStreamContainer(value){
	//进行编辑是使用方法
	var urlValue;
	idValue = $('#id').val();
	if(idValue!=null&&idValue!=""){
		urlValue =  '${ctx}/logistics/downstreamContainer/getContainerList/'+idValue;
	}else{
		urlValue="";
	}
	dgContainer=$('#dgContainer').datagrid({  
		method: "get",
		url:urlValue,
	  	fit : false,
		fitColumns : true,
		scrollbarSize : 0,
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
			{field:'purchasePrice',title:'销售价',sortable:true,width:20},
			{field:'num',title:'数量',sortable:true,width:20},
			{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
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
			{field:'currency',title:'币种',sortable:true,width:10,
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
				}},
			{field:'totalPrice',title:'总价',sortable:true,width:20}
	    ]],
	    sortName:'boxNo',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    showFooter: true,
	    onLoadSuccess: function(data){
	    	initContainerFooter(data);
		 },
	    toolbar:'#tbContainer'
	});
}
//加载页脚
function initContainerFooter(data){
	var totalboxNo =0;
	var totalrealNumber =0;
	var totalrealPiece =0;
	var totalpieceNum =0;
	var totalpNum = 0;
	var totalnum = 0;
	var totalPrice = 0;
	var array = [];
	for(var i = 0; i < data.rows.length; i++){
	    var map = data.rows[i];
	    for (var key in map){
	    	var currenctValue = map[key];
	    	if(key=='boxNo'){
	    		if(array.indexOf(currenctValue) <0){
	    			totalboxNo = totalboxNo + 1;
	    			array.push(currenctValue);
	    		}
		    }
		    if(key=='pNum'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalpNum = totalpNum + parseInt(currenctValue);
		    	}
		    }
		    if(key=='num'){
		    	if(!isNaN(parseFloat(currenctValue))){
		    	totalnum = totalnum +parseFloat(currenctValue);
		    	}
		    }
		    if(key=='pieceNum'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalpieceNum = totalpieceNum + parseInt(currenctValue);
		    	}
		    }
		    
		    if(key=='totalPrice'){
		    	if(!isNaN(parseInt(currenctValue))){
		    		totalPrice = totalPrice + parseFloat(currenctValue);
		    	}
		    }
	    }
	}
	$('#dgContainer').datagrid('reloadFooter',[
	                              	{ boxNo: '集装箱数：'+totalboxNo,
	                              	pieceNum:totalpieceNum,
	                              	pNum: totalpNum,
	                              	num:totalnum,
	                              	totalPrice:totalPrice.toFixed(2)}
	                              ]);
	
	//加载主表信息
	$('#numbers').numberbox('setValue',totalnum);
	$('#invoiceAmount').numberbox('setValue',totalPrice);
	
}

//加载页脚
function initGoodsFooter(data){
	var totalsl =0;
	var totaljs =0;
	var totalcgje =0;
	var array = [];
	for(var i = 0; i < data.rows.length; i++){
	    var map = data.rows[i];
	    for (var key in map){
	    	var currenctValue = map[key];
		    if(key=='sl'){
		    	if(!isNaN(parseInt(currenctValue))){
		    		totalsl = totalsl + parseInt(currenctValue);
		    	}
		    }
		    if(key=='js'){
		    	if(!isNaN(parseFloat(currenctValue))){
		    		totaljs = totaljs +parseFloat(currenctValue);
		    	}
		    }
		    if(key=='cgje'){
		    	if(!isNaN(parseInt(currenctValue))){
		    		totalcgje = totalcgje + parseInt(currenctValue);
		    	}
		    }
	    }
	}
	$('#childTB').datagrid('reloadFooter',[
	                              	{ cghth: '合计：',
	                              	sl:totalsl,
	                              	js: totaljs,
	                              	cgje:totalcgje.toFixed(2)}
	                              ]);
}
	
	
	//我司单位
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/typeAjax/${downstreamBill.ourUnit}',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#ourUnit').text(data);
		}
	});
	
	//供应商
	if('${downstreamBill.downStreamClient }'!=''){
		$.ajax({
			url : '${ctx}/baseinfo/affiliates/typeAjax/${downstreamBill.downStreamClient}',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#downStreamClient').text(data);
			}
		});
	}
	//供应商
	if('${downstreamBill.supplier }'!=''){
		$.ajax({
			url : '${ctx}/baseinfo/affiliates/typeAjax/${downstreamBill.supplier}',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#supplier').text(data);
			}
		});
	}
	
	//币种
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/currency/${downstreamBill.currency}',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#currency').text(data);
		}
	});
	
	//港口
	$.ajax({
		url : '${ctx}/system/downMenu/getPortName/${downstreamBill.portName}' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#portName').html(data);
		}
	});
	
	//装运类别
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/shipmentType/${downstreamBill.shipmentType}' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#shipmentType').html(data);
		}
	});

	//数量单位
	$.ajax({
		url : '${ctx}/system/downMenu/getDictName/sldw/${downstreamBill.numberUnits}' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#numberUnits').html(data);
		}
	});
	
	$.ajax({
		url : '${ctx}/system/countryArea/getName/${downstreamBill.area}',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#area').html(data);
		}
	});
	

//增加求和统计行
function onCountTolalDetail() {
      //添加“合计”列
      if($('#goodsTable').datagrid('getRows').length == 0)
    	  return ;
      $('#goodsTable').datagrid('appendRow',{
	   	  spbm: '<span class="subtotalDetail">合计:</span>',
	      sl: '<span class="subtotalDetail">' + compute("sl") + '</span>',
	      js: '<span class="subtotalDetail">' + compute("js") + '</span>',
	      cgje: '<span class="subtotalDetail">'+compute("cgje") +'</span>',
		  //Rate: '<span class="subtotal">' + ((compute("TotalOrderScore") / compute("TotalTrailCount")) * 100).toFixed(2) + '</span>'
    });
 }
 
//指定列求和
function compute(colName) {
	    var rows = $('#goodsTable').datagrid('getRows');
	    var total = 0;
	    for (var i = 0; i < rows.length; i++) {
	      total += parseFloat(rows[i][colName]);
	    }
	    if(colName=='sl')
	    	return total.toFixed(3);
	    else if(colName=='cgje')
	    	return total.toFixed(2);
	    else
	    	return total.toFixed(0);
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