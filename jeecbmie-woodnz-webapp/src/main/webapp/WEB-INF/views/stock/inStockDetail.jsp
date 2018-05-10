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
	<div>
		<form id="mainform"  action="${ctx}/stock/inStock/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '入库信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>入库单</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">入库编号</th>
						<td width="30%">
						${inStock.inStockId }
						</td>
						<th width="20%">制单日期</th>
						<td width="30%">
							<fmt:formatDate value="${inStock.createStockDate }"/>
						</td>
					</tr>
					<tr>
						<th width="20%">入库日期</th>
						<td >
							<fmt:formatDate value="${inStock.inStockDate }"/>
						</td>
						<th width="20%">收货仓库</th>
						<td id="receiveWarehouse">
						</td>
					</tr>
					<tr>
						<th>币种</th>
						<td id="currency">
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>公司抬头</th>
						<td id="companyName">
						</td>
						<th>交货部门</th>
						<td>
							${inStock.payDept }
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<td>
							${inStock.shipName }
						</td>
						<th>航次</th>
						<td>
							${inStock.voyage }
						</td>
					</tr>
					<tr>
						<th>锁定销售单价（元）</th>
						<td>
							${inStock.lockSellingPrice }
						</td>
						<th>锁定销售合同</th>
						<td id="lockContract">
						</td>
					</tr>
					<tr>
						<th>是否代拆</th>
						<td id="isOpen">
						</td>
						<th>入库类型</th>
						<td id="inStockType">
						</td>
					</tr>
					<tr>
						<th>提单号</th>
						<td>
							${inStock.billId }
						</td>
						<th>仓库验收人</th>
						<td>
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
				</table>
				</fieldset>
			</div>
			<div data-options="title: '入库商品', iconCls: false, refreshable: false">
				<input type="hidden" name="inStockGoodsJson" id="inStockGoodsJson"/>
				<%@ include file="/WEB-INF/views/stock/inStockGoodsInfo.jsp"%> 
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">

	$(function() {
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    }
		});
		
		if('${inStock.receiveWarehouse }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getWarehouseName/${inStock.receiveWarehouse }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#receiveWarehouse').html(data);
				}
			});
		}
		
		
		if('${inStock.companyName }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getBaseInfoName/${inStock.companyName }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#companyName').html(data);
				}
			});
		}
		
// 		if('${inStock.receiveWarehouse }'!=''){
// 			$.ajax({
// 				url : '${ctx}/system/downMenu/getDictName/warehouse/${inStock.receiveWarehouse }' ,
// 				type : 'get',
// 				cache : false,
// 				success : function(data) {
// 					$('#receiveWarehouse').html(data);
// 				}
// 			});
// 		}
		
		//币种
		if('${inStock.currency }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/currency/${inStock.currency}',
				type : 'get',
				cache : false,
				success : function(data) {
					$('#currency').text(data);
				}
			});
		}
		if('${inStock.isOpen }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/YESNO/${inStock.isOpen }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#isOpen').html(data);
				}
			});
		}
		
		if('${inStock.inStockType }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/inStockType/${inStock.inStockType }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#inStockType').html(data);
				}
			});
		}
		
		
		if('${inStock.lockContract }'!=''){
			$.ajax({
				url:'${ctx}/sale/saleContract/json?filter_EQL_id=${inStock.lockContract }', 
				type : 'get',
				dataType: "json",
				cache : false,
				success : function(data) {
// 					alert(JSON.stringify(data));
					$('#lockContract').html((data.rows)[0].contractNo);
				}
			});
		}
		
	});
	
	
</script>
</body>
</html>