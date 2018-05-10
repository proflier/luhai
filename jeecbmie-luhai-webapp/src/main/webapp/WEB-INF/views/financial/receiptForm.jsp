<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/financial/receipt/${action}" method="post">
			<input type="hidden" id="id" name="id" value="${receipt.id }" />
				<fieldset class="fieldsetClass" >
				<legend>预收票信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >预收票编号</th>
						<td >
							<input id="receiptNo" name="receiptNo" type="text" value="${receipt.receiptNo }" class="easyui-validatebox" data-options="required:true" />
						</td>
						<th >批次船名</th>
						<td >
							<input id="shipName" name="shipName" type="text" value="${receipt.shipName }" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th >供应商</th>
						<td >
<%-- 							<mytag:combobox name="supply" value="${receipt.supply}" type="customer" parameters="10230002"/> --%>
							<mytag:combobox name="supply" value="${receipt.supply}" type="customer" parameters="10230002" hightAuto="false"  permission4User="true"/>
						</td>
						<th >所属月份</th>
						<td >
							<input id="mouth" name="mouth" type="text" value="<fmt:formatDate value="${receipt.mouth }"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<th >开票期间</th>
						<td >
							<input id="receiptPeriod" name="receiptPeriod" type="text" value="${receipt.receiptPeriod }" class="easyui-validatebox"  />
						</td>
						<th >发票号</th>
						<td >
							<input id="invoiceNo" name="invoiceNo" type="text" value="${receipt.invoiceNo }" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th >信用证号</th>
						<td >
							<input id="creditNo" name="creditNo" type="text" value="${receipt.creditNo }" class="easyui-validatebox" data-options="required:true" />
						</td>
						<th >煤种</th>
						<td >
							<mytag:combobox name="coalType" value="${receipt.coalType}" type="goodsType" />
						</td>
					</tr>
					<tr>
						<th >目的港</th>
						<td >
							<mytag:combobox name="destPort" value="${receipt.destPort}" type="port" />
						</td>
						<th >入库数量</th>
						<td >
							<input id="inStockQuantity" name="inStockQuantity" type="text" value="${receipt.inStockQuantity }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >结算数量</th>
						<td >
							<input id="settleQuantity" name="settleQuantity" type="text" value="${receipt.settleQuantity }"  class="easyui-numberbox"  data-options="required:true,precision:'3'"/>
						</td>
						<th >结算单价</th>
						<td >
							<input id="settleUnitPrice" name="settleUnitPrice" type="text" value="${receipt.settleUnitPrice }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >装卸港结算</th>
						<td >
							<mytag:combobox name="settlePort" value="${receipt.settlePort}" type="dict"  parameters="settlePort"/>
						</td>
						<th >合同热值</th>
						<td >
							<input id="contractCalorific" name="contractCalorific" type="text" value="${receipt.contractCalorific }"  class="easyui-numberbox"  data-options="required:true,precision:'3'"/>
						</td>
					</tr>
					<tr>
						<th >结算热值</th>
						<td >
							<input id="settleCalorific" name="settleCalorific" type="text" value="${receipt.settleCalorific }"  class="easyui-numberbox"  data-options="required:true,precision:'3'"/>
						</td>
						<th >结算方式</th>
						<td >
							<mytag:combobox name="settleMode" value="${receipt.settleMode}" type="dict" parameters="jgtk"/>
						</td>
					</tr>
					<tr>
						<th >金额USD</th>
						<td >
							<input id="amountUSD" name="amountUSD" type="text" value="${receipt.amountUSD }" onchange="calc()" class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >开证日期</th>
						<td >
							<input id="creditDate" name="creditDate" type="text" value="<fmt:formatDate value="${receipt.creditDate }"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<th >到单日</th>
						<td >
							<input id="billDate" name="billDate" type="text" value="<fmt:formatDate value="${receipt.billDate }"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
						<th >汇率</th>
						<td >
							<input id="exchangeRate" name="exchangeRate" type="text" value="${receipt.exchangeRate }" onchange="calc()" class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >汇率调整金额</th>
						<td >
							<input id="exchangeRateChange" name="exchangeRateChange" type="text" value="${receipt.exchangeRateChange }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >金额RMB</th>
						<td >
							<input id="amountRMB" name="amountRMB" type="text" readonly="readonly" value="${receipt.amountRMB }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >进口关税</th>
						<td >
							<input id="importTariff" name="importTariff" type="text" value="${receipt.importTariff }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >进口增值税</th>
						<td >
							<input id="importVAT" name="importVAT" type="text" value="${receipt.importVAT }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >保险费</th>
						<td >
							<input id="insurance" name="insurance" type="text" value="${receipt.insurance }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >海运费</th>
						<td >
							<input id="shippingFee" name="shippingFee" type="text" value="${receipt.shippingFee }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >商检费</th>
						<td >
							<input id="inspectionFee" name="inspectionFee" type="text" value="${receipt.inspectionFee }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >商检进项金额</th>
						<td >
							<input id="inspectionEntryFee" name="inspectionEntryFee" type="text" value="${receipt.inspectionEntryFee }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >汇差合计</th>
						<td >
							<input id="differenceRate" name="differenceRate" type="text" value="${receipt.differenceRate }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >开证手续费</th>
						<td >
							<input id="creditApplyFee" name="creditApplyFee" type="text" value="${receipt.creditApplyFee }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >押汇利息</th>
						<td >
							<input id="mortgageInterest" name="mortgageInterest" type="text" value="${receipt.mortgageInterest }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >代理费</th>
						<td >
							<input id="agencyFee" name="agencyFee" type="text" value="${receipt.agencyFee }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >开票金额</th>
						<td >
							<input id="creditAmount" name="creditAmount" type="text" value="${receipt.creditAmount }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
						<th >暂付金额</th>
						<td >
							<input id="prepaidAmount" name="prepaidAmount" type="text" value="${receipt.prepaidAmount }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th >应付金额</th>
						<td colspan="3">
							<input id="payableAmount" name="payableAmount" type="text" value="${receipt.payableAmount }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty receipt.createrName ? sessionScope.user.name : receipt.createrName }</td>
						<th  >登记部门</th>
						<td>${empty receipt.createrDept ? sessionScope.user.organization.orgName : receipt.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ receipt.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${receipt.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dataGrid);
				$.easyui.loaded();
			} 
		});
		
	});
	
	function calc(){
		var amountUSD =	$("#amountUSD").val() *1.00;
		var exchangeRate = $("#exchangeRate").val() *1.00;
		$("#amountRMB").numberbox('setValue',parseFloat(amountUSD*exchangeRate).toFixed(2));
			
	}
	
	$(function(){
		//var action = '${action}';
		if('${action}' == 'view') {
			$("#tbGoods").hide();
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			//将数字框改成只读
			$("#mainform").find('.easyui-numberbox').numberbox({
			    disabled:true
			});
			
			$("#mainform").find('.easyui-numberspinner').numberbox({
			    disabled:true
			});
			//处理日期控件
			$("#mainform").find(".easyui-my97").each(function(index,element){
				$(element).attr("readonly",true);
				$(element).removeClass("easyui-my97");
				$(element).addClass("easyui-validatebox");
			});
			
// 			$('#mouth').spinner('readonly', true);
		}
	});
	
</script>
</body>
</html>