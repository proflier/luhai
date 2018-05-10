<%@page import="com.cbmie.lh.stock.entity.InventoryStock"%>
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
			<div data-options="title: '盘库信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>货物来源</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">盘点编码</th>
						<td width="30%">
							${inventoryStock.inventoryNo }
						</td>
						<th  width="20%">仓库名称</th>
						<td id="warehouseCode">
						</td>
					</tr>
					<tr>
						<th width="20%">商品名称</th>
						<td id="goodsName">
						</td>
						<th width="20%">盘库日期</th>
						<td width="30%">
						<fmt:formatDate value="${inventoryStock.inventoryDate }" />
						</td>
					</tr>
					<tr>
						<th >原始数量</th>
						<td>
							${inventoryStock.sourceQuantity }
						</td>
						<th >盘点数量</th>
						<td>
							${inventoryStock.inventoryQuantity }
						</td>
					</tr>
					<tr>
						<th >数量单位</th>
						<td id="units">
							${fns:getDictLabelByCode(inventoryStock.units,'sldw','')}
						</td>
						<th>负责人</th>
						<td>
							${inventoryStock.inventoryPerson }
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<td colspan="3" id="shipNo">
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${inventoryStock.summary }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty inventoryStock.createrName ? sessionScope.user.name : inventoryStock.createrName }</td>
						<th  >登记部门</th>
						<td>${empty inventoryStock.createrDept ? sessionScope.user.organization.orgName : inventoryStock.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ inventoryStock.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${inventoryStock.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
				</div>
				<div data-options="title: '附件信息', iconCls: false, refreshable: false">
					<input id="accParentEntity" type="hidden"  value="<%=InventoryStock.class.getName().replace(".","_") %>" />
					<input id="accParentId" type="hidden" value="${inventoryStock.id}" />
					<div id="dgAcc"  class="tableClass"></div>
				</div>
			</div>
	</div>
<script type="text/javascript">
		
		
var tabIndex = 0;
$('#mainDiv').tabs({
    onSelect:function(title,index){
    }
});

$.ajax({
		type:'GET',
		async: false,
		url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${inventoryStock.warehouseCode }",
		success: function(data){
			$('#warehouseCode').html(data);
		}
	});
	
	
$.ajax({
		type:'GET',
		async: false,
		url : '${ctx}/baseInfo/lhUtil/goodsShow?code=${inventoryStock.goodsName }',
		success: function(data){
			$('#goodsName').html(data);
		}
	});
	
if('${inventoryStock.shipNo }'!=''){
	$.ajax({
		type:'GET',
		async: false,
		url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${inventoryStock.shipNo }',
		success: function(data){
			if(data != null){
				$('#shipNo').html(data);
			} 
		}
	});
}
		
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