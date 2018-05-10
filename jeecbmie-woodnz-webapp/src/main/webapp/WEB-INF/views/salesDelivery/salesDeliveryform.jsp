<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/salesDelivery/salesDelivery/${action}" method="post">
	<div id="mainDiv" class="" data-options="border:false">
		<div data-options="title: '放货信息', iconCls: false, refreshable: false" >
			<fieldset class="fieldsetClass">
			<legend>基本信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%">销售合同号</th>
					<td width="30%">
						<input type="text" id="billContractNo"  readonly="readonly" name="billContractNo" value="${salesDelivery.billContractNo }" class="easyui-validatebox" />
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadSaleContract()">选择销售合同号</a>
					</td>
					<th width="20%">买受人</th>
					<td width="30%">
					<input type="hidden" id="id"name="id" value="${salesDelivery.id }"/>
					<input type="text" id="customerUnit" name="customerUnit" value="${salesDelivery.customerUnit }" class="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td>
						<input type="text" id="contactsNumber"   name="contactsNumber" value="${salesDelivery.contactsNumber }" validtype="telOrMobile" class="easyui-validatebox" />
					</td>
					<th>送货地址</th>
					<td>
						<input type="text" id="deliveryAddress"  name="deliveryAddress" value="${salesDelivery.deliveryAddress }" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<th>开单日期</th>
					<td colspan="3">
						<input type="text" id="billingDate" name="billingDate" value="<fmt:formatDate value="${salesDelivery.billingDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" "/>
					</td>
				</tr>
				<tr>
					<th width="20%">联系人</th>
					<td width="30%">
						<input type="text" id="contacts" name="contacts" value="${salesDelivery.contacts }" class="easyui-validatebox" />
					</td>
					<th>合同总额</th>
					<td>
						<input type="text" id="contractTotal"  name="contractTotal" value="${salesDelivery.contractTotal }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th>合同累计收款金额</th>
					<td>
						<input type="text" id="contractCumulation"   name="contractCumulation" value="${salesDelivery.contractCumulation }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th>合同已经放货金额</th>
					<td>
						<input type="text" id="contractHaveLending"  name="contractHaveLending" value="${salesDelivery.contractHaveLending }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th>客户累计收款金额</th>
					<td>
						<input type="text" id="customerCumulation"   name="customerCumulation" value="${salesDelivery.customerCumulation }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th>客户已经放货金额</th>
					<td>
						<input type="text" id="customerHaveDelivery"  name="customerHaveDelivery" value="${salesDelivery.customerHaveDelivery }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th>客户累计余额</th>
					<td colspan="3">
						<input type="text" id="customerToltalOverage"   name="customerToltalOverage" value="${salesDelivery.customerToltalOverage }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<th>保证金</th>
					<td>
						<input type="text" id="securityDeposit"   name="securityDeposit" value="${salesDelivery.securityDeposit }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th>本次用款付款方式</th>
					<td>
						<input type="text" id="paymentMethod"  name="paymentMethod" value="${salesDelivery.paymentMethod }" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<th>本次明细汇总金额</th>
					<td>
						<input type="text" id="summaryAmount"   name="summaryAmount" value="${salesDelivery.summaryAmount }" class="easyui-numberbox" data-options="precision:2"/>
					</td>
					<th>本次明细汇总数量</th>
					<td>
						<input type="text" id="summaryNumber"  name="summaryNumber" value="${salesDelivery.summaryNumber }" class="easyui-numberbox" data-options="precision:3"/>
					</td>
				</tr>
				<tr>
					<th>是否专案</th>
					<td>
						<input type="text" id="isProject"   name="isProject" value="${salesDelivery.isProject }" class="easyui-validatebox" />
					</td>
					<th>合同执行人</th>
					<td>
						<input type="text" id="contractExecutor"  name="contractExecutor" value="${salesDelivery.contractExecutor }" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<th>合同是否放货完成</th>
					<td>
						<input type="text" id="contractDelivery"   name="contractDelivery" value="${salesDelivery.contractDelivery }" class="easyui-validatebox" />
					</td>
					<th>是否代拆</th>
					<td>
						<input type="text" id="isOpen"   name="isOpen" value="${salesDelivery.isOpen }" class="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<th> 优先出货提单号 </th>
					<td colspan="3">
						<input type="text" id="priorityShippingNo"  name="priorityShippingNo" value="${salesDelivery.priorityShippingNo }" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>	
					<th>备注</th>
					<td colspan="3">
						<textarea id="comment" name="comment" class="easyui-validatebox" style="width: 36%;margin-top: 5px;" >${salesDelivery.comment }</textarea>
					</td>
				</tr>
			</table>
			</fieldset>
			<fieldset class="fieldsetClass">
			<legend>系统信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%" >登记人</th>
					<td width="30%">${empty salesDelivery.createrName ? sessionScope.user.name : salesDelivery.createrName }</td>
					<th width="20%" >登记部门</th>
					<td width="30%">${empty salesDelivery.createrDept ? sessionScope.user.organization.orgName : salesDelivery.createrDept }</td>
				</tr>
			</table>
			</fieldset>
		</div>
		<div data-options="title: '合同预计放货商品', iconCls: false, refreshable: false">
					<input type="hidden" id="salesDeliveryGoodsJson" name="salesDeliveryGoodsJson" />
					<%@ include file="/WEB-INF/views/salesDelivery/salesDeliveryGoodsForm.jsp"%> 
		</div>
		<div data-options="title: '合同实际放货商品', iconCls: false, refreshable: false">
					<input type="hidden" id="realSalesDeliveryGoodsJson" name="realSalesDeliveryGoodsJson" />
					<%@ include file="/WEB-INF/views/salesDelivery/realSalesDeliveryGoods.jsp"%>
		</div>
	</div>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTipNew(data,dg);
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
	
	//客户单位
	$('#customerUnit').combobox({
		required : true,
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=3',
		valueField : 'id',
		textField : 'customerName'
	});	
	
	
	
	//本次用款付款方式
	$('#paymentMethod').combobox({
		panelHeight:'auto',
		required:true,
		url : '${ctx}/system/downMenu/getDictByCode/skfs',
		valueField : 'id',
		textField : 'name'
	});
	
	//是否专案
	$('#isProject').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/YESNO',
		valueField : 'id',
		textField : 'name'
	});
	
	//合同是否放货完成
	$('#contractDelivery').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/YESNO',
		valueField : 'id',
		textField : 'name'
	});
	
	//是否代拆
	$('#isOpen').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/downMenu/getDictByCode/YESNO',
		valueField : 'id',
		textField : 'name',
		onLoadSuccess: function () { 
			if("create"=="${action}"){
				 $("#customerUnit").combobox('disable');
				 $("#contacts").attr("disabled",true);
				 $("#contactsNumber").attr("disabled",true);
				 $("#deliveryAddress").attr("disabled",true);
				 $("#billingDate").combo('disable');
				 $("#contractTotal").numberbox({disabled:true});
				 $("#contractCumulation").numberbox({disabled:true});
				 $("#contractHaveLending").numberbox({disabled:true});
				 $("#customerCumulation").numberbox({disabled:true});
				 $("#customerHaveDelivery").numberbox({disabled:true});
				 $("#customerToltalOverage").numberbox({disabled:true});
				 $("#securityDeposit").numberbox({disabled:true});
				 $("#paymentMethod").combobox('disable');
				 $("#summaryAmount").numberbox({disabled:true});
				 $("#summaryNumber").numberbox({disabled:true});
				 $("#isProject").combobox('disable');
				 $("#contractExecutor").attr("disabled",true);
				 $("#contractDelivery").combobox('disable');
				 $("#comment").attr("disabled",true);
				 $("#isOpen").combobox('disable');
				 $("#priorityShippingNo").attr("disabled",true);
			}
		}
	});
	
	
});

function loadSaleContract(){
	dlgContract=$("#dlgContract").dialog({
	  	width: 800,    
	    height: 400, 
	    title: '选择进口合同',
	    href:'${ctx}/salesDelivery/salesDelivery/loadSaleContract/',
	    modal:true,
	    closable:true,
	    style:{borderWidth:0},
	    buttons:[{
			text:'确认',iconCls:'icon-ok',
			handler:function(){
				initGoods();
				dlgContract.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlgContract.panel('close');
			}
		}]
	});
	
}
</script>
</body>
</html>