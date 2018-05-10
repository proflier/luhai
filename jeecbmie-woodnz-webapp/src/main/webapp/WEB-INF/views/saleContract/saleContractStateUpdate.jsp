<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.cbmie.woodNZ.salesContract.entity.WoodSaleContract"%>
<%@page import="com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
		<form id="mainform"  action="${ctx}/sale/saleContract/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售合同信息', iconCls: false, refreshable: false" >				
				<fieldset class="fieldsetClass" >
				<legend>销售合同信息</legend>
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toAgencyAgreement()">选择放货信息</a> -->
				<table width="98%" class="tableClass">
				<tr>
						<th>合同状态</th>
						<td>
							<input type="hidden" name="id" value="${saleContract.id}" />
							<input type="text" class="easyui-combobox" name="state_ht" value="${saleContract.state_ht}" 
								data-options="required:true,valueField: 'label',textField: 'value', panelHeight:'auto',
								data: [{label: '运行中',value: '运行中'}, {label: '关闭',value: '关闭'}]"/>
						</td>
						<th></th>
						<td>
						</td>
					</tr>
					<tr>
						<th  width="20%" >卖受人</th>
						<td  width="30%" id="sellerDetail">
						</td  width="20%" >
						<th>买受人</th>
						<td  width="30%"  id="purchaserDetail">
						</td>
					</tr>
					<tr>
						<th>签约日期</th>
						<td>
							 <fmt:formatDate value="${saleContract.signDate}" />	
						</td>
						<th>签约地点</th>
						<td id="signAddr">
						</td>
					</tr>
					<tr>
						<th>合同编号</th>
						<td>
							${saleContract.contractNo }
						</td>
						<th>协议号</th>
						<td>
							${saleContract.dealNo }
						</td>
					</tr>
					<tr>
						<th>业务部门</th>
						<td>
							${saleContract.dept }
						</td>
						<th>登记日期</th>
						<td>
							<fmt:formatDate value="${saleContract.registerDate}" />
						</td>
					</tr>
					<tr>
						<th>合同制作人</th>
						<td>
							${saleContract.draftsman }
						</td>
						<th>合同执行人</th>
						<td>
							${saleContract.executer }
						</td>
					</tr>
					<tr>
						<th>大品名</th>
						<td>
							${saleContract.dapimming }
						</td>
						<th>代理费</th>
						<td>
							${saleContract.dlf }
						</td>
					</tr>
					<tr>
						<th>贸易性质</th>
						<td id="tradeProperty">
						
						</td>
						<th>保证金/定金</th>
						<td>
							${saleContract.bail}
						</td>
					</tr>
					<tr>
						<th>收款止期</th>
						<td>
							<fmt:formatDate value="${saleContract.gatheringDate}" />
						</td>
						<th>合同金额</th>
						<td>
							${saleContract.totalMoney }
						</td>
					</tr>
					<tr>
						<th>币种</th>
						<td  id="currency">
						</td>
						<th>汇率</th>
						<td>
							${saleContract.exchangeRate }
						</td>
					</tr>
					<tr>
						<th>销售总数量</th>
						<td>
							${saleContract.total }
						</td>
						<th>数量单位</th>
						<td id="numUnit">
						</td>
					</tr>
					<tr>
						<th>报关方</th>
						<td id="takeWay">
							
						</td>
						<th>是否专案</th>
						<td id="ifZa">
							
						</td>
					</tr>
					<tr>
						<th>类别</th>
						<td id="salesType">
							
						</td>
						<th>提货地点</th>
						<td id="takeAddr">
							
						</td>
					</tr>
					<tr>
						<th>提货止期</th>
						<td>
							<fmt:formatDate value="${saleContract.takeDate}" />
						</td>
						<th>付款方式</th>
						<td id="payWay">
						</td>
					</tr>
					<tr>
						<th>提货超期单价上调条款</th>
						<td>
							${saleContract.cqjj }
						</td>
						<th></th>
						<td>
							
						</td>
					</tr>
					<tr>
						<th>溢短（%）</th>
						<td>
							${saleContract.error }
						</td>
						<th>合同失效期</th>
						<td>
							<fmt:formatDate value="${saleContract.disableDate}" /> 
						</td>
					</tr>
					<tr>
						<th>买方联系人</th>
						<td>
							${saleContract.connecter }
						</td>
						<th>买方联系人电话</th>
						<td>
							${saleContract.phone }
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align:middle;color:red" ></font>送货地址</th>
						<td colspan="3" style="height:1cm">${saleContract.addr}</td>
					</tr>
					<tr>
						<th><font style="vertical-align:middle;color:red" ></font>备注</th>
						<td colspan="3" style="height:1cm">${saleContract.remark}</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>${sessionScope.user.name}</td>
						<th>登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '销售合同子表', iconCls: false, refreshable: false">
				<table class="tableClass" id="subTable">
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
		    		successTip(data,dg);
			} 
		});
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if(!$('#mainform').form('validate')){
		    		$("#mainDiv").tabs("select", tabIndex);
		    	}else{
		    		tabIndex = index;
		    	}
		    }
		});
		
		//卖受人
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${saleContract.seller}',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#sellerDetail').text(data);
			}
		});

		//买受人
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${saleContract.purchaser}',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#purchaserDetail').html(data);
			}
		});

		//币种
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/currency/${saleContract.currency }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#currency').html(data);
			}
		});

		//签约地点
		if('${saleContract.signAddr }'!=''){
			$.ajax({
				url : '${ctx}/system/countryArea/getName/' + ${saleContract.signAddr },
				type : 'get',
				cache : false,
				success : function(data) {
					$('#signAddr').html(data);
				}
			});
		}
		
		//付款方式 
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/skfs/${saleContract.payWay }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#payWay').html(data);
			}
		});
		
		//贸易性质 
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/htlb/${saleContract.tradeProperty }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#tradeProperty').html(data);
			}
		});
		
		//数量单位 
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/sldw/${saleContract.numUnit }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#numUnit').html(data);
			}
		});
		
		//报关方
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/bgf/${saleContract.takeWay }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#takeWay').html(data);
			}
		});
		
		//是否专案
		if('${saleContract.ifZa }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/YESNO/${saleContract.ifZa }',
				type : 'get',
				cache : false,
				success : function(data) {
					$('#ifZa').html(data);
				}
			});
		}
		
		//类别
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/xslb/${saleContract.salesType }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#salesType').html(data);
			}
		});
		
		//提货地点
		$.ajax({
			url : '${ctx}/system/downMenu/getAllName/${saleContract.takeAddr}/thdd',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#takeAddr').text(data);
			}
		});
		initSubForm();
	});
	
	
	<%
		WoodSaleContract woodSaleContract = (WoodSaleContract)request.getAttribute("saleContract");
		List<WoodSaleContractSub> woodContractSubList = woodSaleContract.getSaleContractSubList();
		ObjectMapper objectMapperCus = new ObjectMapper();
		String subJson = objectMapperCus.writeValueAsString(woodContractSubList);
		request.setAttribute("subJson", subJson);
	%>

function initSubForm(value){
	$('#subTable').datagrid({
	data : JSON.parse('${subJson}'),
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
	extEditing:false,
    columns:[[
		{field:'goodsNo',title:'商品编号',width:20,editor:{type:'validatebox',options:{precision:2,required:true}}},
		{field:'goodsName',title:'商品名称',width:20,editor:{type:'validatebox',options:{precision:2}}},
		{field:'realsendVNum',title:'实发件数',width:20,editor:{type:'numberbox'}},
		{field:'piece',title:'片数',width:20,editor:{type:'numberbox',options:{precision:2}}},
		{field:'num',title:'件数',width:20,editor:{type:'numberbox'}},
		{field:'amount',title:'立方数',width:20,editor:{type:'numberbox',options:{precision:2}}},
		{field:'unitPrice',title:'单价',width:20,editor:{type:'numberbox'}},
		{field:'warehouse',title:'仓库',width:20,editor:{type:'validatebox'}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
	});
}
</script>
</body>
</html>

