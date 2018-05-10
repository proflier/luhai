<%@page import="com.cbmie.lh.stock.entity.InStock"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div id="detailLayout" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'east',collapsible:true,split:true,collapsed:true,border: false,title:'关联列表'" style="width: 600px">
			<input id="innerContractNo" type="hidden" value="" />
			<table id="dg_relation" ></table>
			<div id="dlg_relation"></div>
		</div>
		<div onclick="centerDetail()" data-options="region:'center'">
			<div id="instock_mainDiv" class="" data-options="border:false">
				<div data-options="title: '入库信息', iconCls: false, refreshable: false" >
					<fieldset class="fieldsetClass" >
					<legend>入库单</legend>
					<table width="98%" class="tableClass" >
						<tr>
							<th width="20%">入库编号</th>
							<td width="30%">
								${inStock.inStockId }
							</td>
							<th width="20%">入库日期</th>
							<td width="30%">
								<fmt:formatDate value="${inStock.inStockDate }"/>
							</td>
						</tr>
						<tr>
							<th>提单号</th>
							<td>
							<input type="hidden" id="id" name="id" value="${inStock.id }" />
								${inStock.billNo }
							</td>
							<th>提单数量</th>
							<td>
								${inStock.numbers }
							</td>
						</tr>
						<tr>
							<th >船名</th>
							<td colspan="3" id="shipNo_instock">
							</td>
						</tr>
						<tr>	
							<th>入库类型</th>
							<td colspan="3" >
								${fns:getDictLabelByCode(inStock.inStockType,'inStockType','')}
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
							<th >制单日期</th>
							<td >
								<fmt:formatDate value="${inStock.createStockDate }"/>
							</td>
							<th>仓库验收人</th>
							<td >
								${inStock.warehouseAccept }
							</td>
						</tr>
						<tr>
							<th>确认人</th>
							<td>
								${inStock.determineName }
							</td>
							<th>确认日期</th>
							<td>
								<fmt:formatDate value="${inStock.determineTime }" />
							</td>
						</tr>
					</table>
					</fieldset>
					<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th  width="20%" >登记人</th>
							<td>${inStock.createrName }</td>
							<th   width="20%">登记部门</th>
							<td>${inStock.createrDept }</td>
						</tr>
						<tr>
							<th width="20%" >登记时间</th>
							<td width="30%"><fmt:formatDate value="${ inStock.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<th width="20%" >最后修改时间</th>
							<td width="30%"><fmt:formatDate value="${inStock.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</table>
					</fieldset>
					<jsp:include page="/WEB-INF/views/relationDetail/relationDetail.jsp" >
						<jsp:param value="inStock,saleContract,saleDelivery,outStock,saleInvoice,paymentConfirm" name="disView"/>
					</jsp:include>
				</div>
				<div data-options="title: '入库明细', iconCls: false, refreshable: false">
					<table id="dgInStockGoods" ></table>
				</div>
				<div data-options="title: '附件信息', iconCls: false, refreshable: false">
					<jsp:include page="/WEB-INF/views/accessory/childAccList4Detail.jsp" >
						<jsp:param value="${inStock.id}" name="accParentId"/>
						<jsp:param value="com_cbmie_lh_stock_entity_InStock" name="accParentEntity"/>
						<jsp:param value="dgAcc_instock" name="dgAcc"/>
					</jsp:include>
				<div id="dgAcc_instock"  class="tableClass"></div>
				</div>
			</div>
		</div>	
	</div>
<script type="text/javascript">

	var dgInStockGoods;
	$(function() {
		var tabIndex = 0;
		$('#instock_mainDiv').tabs({
		    onSelect:function(title,index){
		    }
		});
		
		var idValue;
		idValue = '${inStock.id}';
		dgInStockGoods=$('#dgInStockGoods').datagrid({  
			method: "get",
			url:'${ctx}/stock/inStockGoods/getInstockGodds/'+idValue,
		  	fit : false,
			fitColumns : true,
// 			scrollbarSize : 0,
			border : false,
			striped:true,
			idField : 'id',
			rownumbers:true,
			singleSelect:true,
			extEditing:false,
		    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'innerContractNo',title:'内部合同号',sortable:true,width:20},
				{field:'billNo',title:'提单号',sortable:true,width:20},
				{field:'goodsNameView',title:'商品名称',sortable:true,width:20},
				{field:'quantityCIQ',title:'CIQ数量',sortable:true,width:20},
				{field:'quantity',title:'入库数量',sortable:true,width:20},
				{field:'unitsView',title:'数量单位',sortable:true,width:20},
				{field:'warehouseNameView',title:'仓库',sortable:true,width:20},
				{field:'instockCategory',title:'属性',sortable:true,width:15,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							if(value=='0'){
								return "原始入库";
							}else if(value=="1"){
								return "调配库存";
							}else if(value=="2"){
								return "盘库库存";
							}else if(value=="3"){
								return "历史库存";
							}
						}else{
							return '';
						}
					}	
				},
				{field:'updateDate',title:'当前库存生成时间',sortable:true,width:15}
		    ]],
		    onLoadSuccess:function(data){
		    	var allInnerContactNo="";
				if (data.rows.length > 0) {
					for(var i=0;i<data.rows.length;i++){
						if(allInnerContactNo!=""){
						allInnerContactNo = allInnerContactNo+","+data.rows[i].innerContractNo;
						}else{
							
							allInnerContactNo= data.rows[i].innerContractNo;
						}
					}
				}
				$('#innerContractNo').val(allInnerContactNo);
			},
		    sortName:'id',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		});
		
		if('${inStock.shipNo }'!=''){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${inStock.shipNo }',
				success: function(data){
					if(data != null){
						$('#shipNo_instock').html(data);
					} 
				}
			});
		}
	});
	
	
	
</script>
</body>
</html>