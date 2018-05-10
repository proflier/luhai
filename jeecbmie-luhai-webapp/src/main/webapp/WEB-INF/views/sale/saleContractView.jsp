<%@ page import="com.cbmie.lh.sale.entity.SaleContract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
		<div id="contractViewId" class="" data-options="border:false">
			<div data-options="title: '销售合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>销售合同号</th>
						<td colspan="3">
							<input id="view_contractId" name="view_contractId" type="hidden"  value="${saleContract.id }" />
							${saleContract.contractNo}
						</td>
					</tr>
					<tr>
						<th width="20%">卖方</th>
						<td width="30%">
							<mytag:combobox name="seller" value="${saleContract.seller}" type="customer" parameters="10230001"/>
						</td>
						<th  width="20%">买方</th>
						<td width="30%">
							<mytag:combobox name="purchaser" value="${saleContract.purchaser}" type="customer" parameters="10230003"/>
						</td>
					</tr>
					<tr>
						<th>销售方式</th>
						<td>
							<mytag:combobox name="saleMode" value="${saleContract.saleMode}" type="dict" parameters="SALESMODE"/>
						</td>
						<th>业务类型</th>
						<td>
							<mytag:combobox name="manageMode" value="${saleContract.manageMode}" type="dict" parameters="BUSINESSTYPE"/>
						</td>
					</tr>
					 <tr>
						<th>数量溢短装</th>
						<td>
							<input id="numMoreOrLess" name="numMoreOrLess" type="text" value="${saleContract.numMoreOrLess }" class="easyui-numberbox"  data-options="precision:'2',suffix:'%'" readonly="readonly"/>
						</td>
						<th>客户合同号</th>
						<td>
							${saleContract.customerContractNo }
						</td>
					</tr>
					 
					<tr>
						<th>交货起期</th>
						<td>
							<fmt:formatDate value="${saleContract.deliveryStartDate }" pattern="yyyy-MM-dd"/>
						</td>
						<th>交货止期</th>
						<td>
							<fmt:formatDate value="${saleContract.deliveryEndDate }" pattern="yyyy-MM-dd"/>
						</td>						
					</tr>
					<tr>
						<th>交货方式</th>
						<td>
							<mytag:combobox name="deliveryMode" value="${saleContract.deliveryMode}" type="dict" parameters="THFS"/>
						</td>
						<th>交款方式</th>
						<td>
							<input id="settlementMode" name="settlementMode"  type="text" value="${saleContract.settlementMode }" class="easyui-combobox"  />
						</td>
					</tr>
					<tr>
						<th>交货地点</th>
						<td>
							${saleContract.deliveryAddr }
						</td>
						<th>运输工具编号</th>
						<td>
							${saleContract.ship }
						</td>
					</tr>
					<tr>
					</tr>
					<tr>
						<th>合同数量 </th>
						<td>
							${saleContract.contractQuantity }
						</td>
						<th>合同金额</th>
						<td>
							${saleContract.contractAmount }
						</td>
					</tr>
				<tr>
					<th>数量结算依据</th>
					<td colspan="3">
						<textarea name="numSettlementBasis" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]'],value:'${saleContract.numSettlementBasis}'"/>
					</td>
				</tr>
				<tr>
					<th>质量结算依据</th>
					<td colspan="3">
						<textarea name="qualitySettlementBasis" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]'],value:'${saleContract.qualitySettlementBasis}'" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<th>风险提示</th>
					<td colspan="3">
						<textarea name="riskTip" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]'],value:'${saleContract.riskTip}'" readonly="readonly"/>
					</td>
				</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty saleContract.createrName ? sessionScope.user.name : saleContract.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty saleContract.createrDept ? sessionScope.user.organization.orgName : saleContract.createrDept }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			
			<div data-options="title: '货物明细', iconCls: false, refreshable: false">
				<table id="dgContractGoods"></table>
				<div id="dlg_goods"></div>
				<script type="text/javascript">
				$(function(){
					dgContractGoods = $('#dgContractGoods').datagrid({
						method: "get",
						url: $('#view_contractId').val() ? '${ctx}/sale/saleContractGoods/forContractId/' + $('#view_contractId').val() : '',
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
							{field:'goodsNameView',title:'品名',sortable:true,width:20},
							{field:'price',title:'单价',sortable:true,width:20},
							{field:'goodsQuantity',title:'数量',sortable:true,width:20},
							{field:'amount',title:'总价',sortable:true,width:20},
							{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					});
				});
				</script>
			</div>
						
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=SaleContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${saleContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
						
		</div>	
</div>

<script type="text/javascript">
$(function(){
	
	$('#contractViewId').tabs({
	    onSelect:function(title,index){
	    }
	});
	$('#settlementMode').combobox({
			panelHeight : 'auto',
			required : false,
			url : '${ctx}/system/dictUtil/getDictByCode/SJKFS',
			valueField : 'code',
			textField : 'name',
			multiple : true,
			value : '${saleContract.settlementMode}'==""?null:'${saleContract.settlementMode}'.split(","),
			onHidePanel:function(){}
		});
});

/****/
$(function(){
		//将输入框改成只读
		$("#contractViewId").find(".easyui-validatebox").attr("readonly",true);
		//将下拉选改成只读
		$("#contractViewId").find('.easyui-combobox').combobox({
		    disabled:true
		});
		//处理日期控件
		$("#contractViewId").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
});

</script>
</body>
</html>