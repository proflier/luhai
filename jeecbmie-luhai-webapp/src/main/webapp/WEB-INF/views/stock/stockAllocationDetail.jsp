<%@page import="com.cbmie.lh.stock.entity.StockAllocation"%>
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
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '调拨信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>货物来源</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >调拨编码</th>
						<td >
						${stockAllocation.allocationNo }
						</td>
						<th >原仓库</th>
						<td id="sourceWarehouseView">
						</td>
					</tr>
					<tr>
						<th width="20%">商品名称</th>
						<td width="30%" id="goodsNameView">
						</td>
						<th width="20%">原仓库商品数量</th>
						<td  width="30%">
							${stockAllocation.sourceGoodsQuantity }
						</td>
					</tr>
					<tr>
						<th >调拨数量</th>
						<td>
							${stockAllocation.allocationQuantity }
						</td>
						<th >目标仓库</th>
						<td id="destWarehouseCode">
						</td>
					</tr>
					<tr>
						<th >调拨人</th>
						<td>
							${stockAllocation.allocationPerson }
						</td>
						<th >调出日期</th>
						<td >
							<fmt:formatDate value="${stockAllocation.outTime }"/>
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<td id="shipNo">
						</td>
						<th >开单船次</th>
						<td id="innerShipNo">
						</td>
					</tr>
					<tr>
						<th >接收人</th>
						<td>
							${stockAllocation.receivePerson }
						</td>
						<th >调入数量</th>
						<td >
							${stockAllocation.receiveQuantity }
						</td>
					</tr>
					<tr>
						<th >调入日期</th>
						<td >
								<fmt:formatDate value="${stockAllocation.inTime }"/>
						</td>
						<th >运输方式</th>
						<td >
							${fns:getDictLabelByCode(stockAllocation.transportType,'YSFS','')}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${stockAllocation.summary }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty stockAllocation.createrName ? sessionScope.user.name : stockAllocation.createrName }</td>
						<th  >登记部门</th>
						<td>${empty stockAllocation.createrDept ? sessionScope.user.organization.orgName : stockAllocation.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"> <fmt:formatDate value="${ stockAllocation.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"> <fmt:formatDate value="${stockAllocation.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=StockAllocation.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${stockAllocation.id}" />
				<div id="dgAcc"  class="tableClass"></div>
			</div>
		</div>	
	</div>
<script type="text/javascript">
	$(function() {
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    }
		});
			
		$.ajax({
	 		type:'GET',
	 		async: false,
	 		url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${stockAllocation.destWarehouseCode }",
	 		success: function(data){
	 			$('#destWarehouseCode').html(data);
	 		}
	 	});
		$.ajax({
	 		type:'GET',
	 		async: false,
	 		url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${stockAllocation.sourceWarehouseCode }",
	 		success: function(data){
	 			$('#sourceWarehouseView').html(data);
	 		}
	 	});
		$.ajax({
	 		type:'GET',
	 		async: false,
	 		url : '${ctx}/baseInfo/lhUtil/goodsShow?code=${stockAllocation.goodsName }',
	 		success: function(data){
	 			$('#goodsNameView').html(data);
	 		}
	 	});
		if('${stockAllocation.shipNo }'!=''){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${stockAllocation.shipNo }',
				success: function(data){
					if(data != null){
						$('#shipNo').html(data);
					} 
				}
			});
		}
		
		if('${stockAllocation.innerShipNo }'!=''){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${stockAllocation.innerShipNo }',
				success: function(data){
					if(data != null){
						$('#innerShipNo').html(data);
					} 
				}
			});
		}
		
	});
	
	/**附件信息**/
	var urlValue = '${ctx}/accessory/json?filter_EQS_accParentId=' + $('#accParentId').val()
	+ '&filter_EQS_accParentEntity=' + $('#accParentEntity').val();
	var dgAcc;
	$(function() {
	dgAcc = $('#dgAcc')
			.datagrid(
					{
						method : "get",
						fit : false,
						fitColumns : true,
						border : false,
						striped : true,
						singleSelect : true,
						scrollbarSize : 0,
						url : urlValue,
						idField : 'accId',
						columns : [ [
								{
									field : 'accRealName',
									title : '附件名称',
									sortable : true,
									width : 40
								},
								{
									field : 'accAuthor',
									title : '上传人',
									sortable : true,
									width : 10,
									formatter: function(value,row,index){
										var name = '';
										if(value!=null && value!=""){
											$.ajax({
												url : '${ctx}/system/user/getUserNameByLogin/'+value ,
												type : 'get',
												cache : false,
												async: false,
												success : function(data) {
													name = data;
												}
											});
										}
										return name;
									}
								},
								{
									field : 'accId',
									title : '操作',
									sortable : true,
									width : 20,
									formatter : function(value, row, index) {
										var str = "";
										str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
												+ value + ");'>下载</a>"
										return str;
									}
								} ] ]
					})
	});
	
	//下载附件
	function downnloadAcc(id) {
		window.open("${ctx}/accessory/download/" + id, '下载');
	}
</script>
</body>
</html>