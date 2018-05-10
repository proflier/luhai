<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/logistics/downstreamBill/${action}" method="post">
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '交单信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass" >
				<legend>交单信息</legend>
				<table width="98%" class="tableClass">
				 <tr>
					<th width="20%">提单号</th>
					<td width="30%">
						<input type="hidden" name="relationSaleContarte" id="relationSaleContarte" value="${downstreamBill.relationSaleContarte}" />
						<input type="hidden" name="id" id="id" value="${downstreamBill.id}" />
						<input name="billNo"  id="billNo" readonly="readonly" value="${downstreamBill.billNo}" class="easyui-validatebox" type="text" data-options="required:true"  />
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSaleList()">销售合同列表</a>
					</td>
					<th width="20%">二次换单提单号</th>
					<td width="30%"><input name="twoTimeBillNo" id="twoTimeBillNo" class="easyui-validatebox"  value="${downstreamBill.twoTimeBillNo}" type="text"  /></td>
				</tr>
				<tr>
					<th >销售合同号</th>
					<td >
						<input name="saleContarteNo" id="saleContarteNo" class="easyui-validatebox"  value="${downstreamBill.saleContarteNo}" type="text"  />
					</td>
					<th >下游客户</th>
					<td >
					<input id="downStreamClient" name="downStreamClient" type="text" value="${downstreamBill.downStreamClient}"/>
					</td>
				</tr>
				<tr>
					<th width="20%">供应商</th>
					<td width="30%"><input id="supplier" name="supplier" type="text" value="${downstreamBill.supplier}"/></td>
					<th width="20%">我司单位</th>
					<td width="30%"><input name="ourUnit" id="ourUnit" type="text"  value="${downstreamBill.ourUnit}" />
					</td>
				</tr>
				<tr>
					<th width="20%">快递号</th>
					<td width="30%"><input id="expressNo" name="expressNo" type="text" value="${downstreamBill.expressNo}" class="easyui-validatebox" /></td>
					<th width="20%">快递日期</th>
					<td width="30%">
					<input id="expressDate" name="expressDate" type="text"  value="<fmt:formatDate value="${downstreamBill.expressDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<th>预计到港日期</th>
					<td>
					<input id="expectPortDate" name="expectPortDate" type="text"  value="<fmt:formatDate value="${downstreamBill.expectPortDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" />	
					</td>
					<th>装运类别</th>
					<td><input name="shipmentType"  id="shipmentType"  type="text" value="${downstreamBill.shipmentType}" />				
					</td>			
				</tr>
				<tr>
					<th>正本到单日期</th>
					<td>
						<input id="trueToDate" name="trueToDate" type="text"  value="<fmt:formatDate value="${downstreamBill.trueToDate}"/>"  
						class="easyui-my97" datefmt="yyyy-MM-dd" />	
					</td>
					<th>币种</th>
					<td><input id="currency" name="currency" type="text" value="${downstreamBill.currency}" />				
					</td>			
				</tr>
				<tr>
					<th>免箱期天数</th>
					<td><input id="noBoxDay"name="noBoxDay" type="text" value="${downstreamBill.noBoxDay}" class="easyui-numberbox" />
					</td>
					<th>装船日期</th>
					<td><input id="shipDate" name="shipDate" type="text"  value="<fmt:formatDate value="${downstreamBill.shipDate}" />" 
						class="easyui-my97" datefmt="yyyy-MM-dd" />			
					</td>			
				</tr>
				<tr>
					<th>目的港</th>
					<td><input id="portName" name="portName" type="text" value="${downstreamBill.portName}" />
					</td>
					<th>集装箱数</th>
					<td>	
						<input id="containerNumber" name="containerNumber"  class="easyui-numberbox"   
						type="text" value="${downstreamBill.containerNumber}"  data-options="precision:'3'" />		
					</td>			
				</tr>
				<tr>
					<th>地区</th>
					<td><input id="area" name="area" type="text" value="${downstreamBill.area}"  />
					</td>
					<th>包干费单价</th>
					<td><input id="forwarderPrice" name="forwarderPrice" type="text" value="${downstreamBill.forwarderPrice}" class="easyui-numberbox"   
						 data-options="precision:'2'" />				
					</td>			
				</tr>
				<tr>
					<th>数量</th>
					<td><input name="numbers" id="numbers"  readonly="true"  type="text" value="${downstreamBill.numbers}" class="easyui-numberbox" 
						data-options="precision:'3'"   />
						*从到单货物明细中计算
					</td>
					<th>数量单位</th>
					<td>
						<input name="numberUnits" id="numberUnits" type="text" value="${downstreamBill.numberUnits}"/>
					</td>			
				</tr>
				<tr>
					<th>船名</th>
					<td><input id="shipName" name="shipName" type="text" value="${downstreamBill.shipName}"   class="easyui-validatebox" 
							 />
					</td>
					<th>航次</th>
					<td>
						<input id="voyage" name="voyage" type="text" value="${downstreamBill.voyage}" class="easyui-validatebox" 
							 />				
					</td>			
				</tr> 
				<tr>
					<th>船公司</th>
					<td><input id="shipCompany" name="shipCompany" type="text" value="${downstreamBill.shipCompany}"  class="easyui-validatebox" />
					</td>
					<th>总件数/跟数</th>
					<td><input id="totalP" name="totalP" type="text" value="${downstreamBill.totalP}" class="easyui-validatebox" />				
					</td>			
				</tr>
				<tr>
					<th>发票日期</th>
					<td>
						<input id="invoiceDate" name="invoiceDate" type="text"  value="<fmt:formatDate value="${downstreamBill.invoiceDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
					</td>
					<th>发票金额</th>
					<td>  
						<input name="invoiceAmount" id="invoiceAmount"   readonly="readonly" type="text" value="${downstreamBill.invoiceAmount}" class="easyui-numberbox" 
						data-options="precision:'2'"  />
						*从到单货物明细中计算
					</td>
								
				</tr>
				<tr>
					<th>上游发票号</th>
					<td>
						<input id="invoiceNo" name="invoiceNo"  type="text" value="${downstreamBill.invoiceNo}" class="easyui-validatebox" data-options="required:true"/>				
					</td>
					<th>下游发票号</th>
					<td>
						<input id="downInvoiceNo" name="downInvoiceNo"  type="text" value="${downstreamBill.downInvoiceNo}" class="easyui-validatebox" data-options="required:true"/>				
					</td>
				</tr>
				<tr>
					<th>交单期</th>
					<td>
						<input id="giveBillsDate" name="giveBillsDate" type="text"  value="<fmt:formatDate value="${downstreamBill.giveBillsDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>
					</td>
					<th>登记时间</th>
					<td>
						<input name="createDate" type="text" value="<fmt:formatDate value="${downstreamBill.createDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<th width="20%">登记人</th>
						<td width="30%">${empty downstreamBill.createrName ? sessionScope.user.name : downstreamBill.createrName }</td>
					<th width="20%">登记部门</th>
						<td width="30%">${empty downstreamBill.createrDept ? sessionScope.user.organization.orgName : downstreamBill.createrDept }</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3" style="height:1cm"><textarea  name="remark" type="text" id="remark"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${downstreamBill.remark}</textarea></td>
				</tr>
				</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
				<legend>到单货物明细</legend>
				<input type="hidden" name="downstreamGoodsJson" id="downstreamGoodsJson"/>
				<%@ include file="/WEB-INF/views/logistics/downstreamGoodsList.jsp"%>
			</fieldset>
		</div>	
		<div data-options="title: '箱单信息', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/logistics/downstreamContainerListForm.jsp"%>
		</div>	
	</div>
	</form>
</div>
<script type="text/javascript">
var purchase;
var dgGoods;
var dGoods;
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
	    		tabIndex = index;//更换tab
	    	}else{
	    		if(index != 0){
	    			parent.$.messager.alert('提示','请先保存主表信息!!!');
	    		}
	    		$("#mainDiv").tabs("select", tabIndex);//当前tab
	    		return ;
	    	}
	    }
	});
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){  
     		successTipNew(data,dg);
	    }
	});
	
	function successTipBills(data,dg,d){
		if(data=='success'){
			if(dg!=null){
				dg.datagrid('clearSelections');
				dg.datagrid('reload');
				dgGoods.datagrid('reload');
			}
			parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
			return true;
		}else{
			parent.$.messager.alert(data);
			return false;
		}  
	}
	
	//币种列表
	$('#currency').combobox({
		panelHeight:'auto',
		url:'${ctx}/system/downMenu/getDictByCode/currency',
	    valueField:'id',
	    textField:'name', 
	    required : true,
	    onLoadSuccess: function () { 
			if("create"=="${action}"){
				 $("#twoTimeBillNo").attr("disabled",true);
				 $("#saleContarteNo").attr("disabled",true);
				 $("#downStreamClient").combobox('disable');
				 $("#supplier").combobox('disable');
				 $("#ourUnit").combobox('disable');
				 $("#expressNo").attr("disabled",true);
				 $("#expressDate").combo('disable');
				 $("#expectPortDate").combo('disable');
				 $("#shipmentType").combobox('disable');
				 $("#trueToDate").combo('disable');
				 $("#currency").combobox('disable');
				 $("#noBoxDay").numberbox({disabled:true});
				 $("#shipDate").combo('disable');
				 $("#portName").combobox('disable');
				 $("#containerNumber").numberbox({disabled:true});
				 $("#area").combotree({disabled:true});
				 $("#forwarderPrice").numberbox({disabled:true});
				 $("#numbers").numberbox({disabled:true});
				 $("#numberUnits").combobox('disable');
				 $("#shipName").attr("disabled",true);
				 $("#voyage").attr("disabled",true);
				 $("#shipCompany").attr("disabled",true);
				 $("#totalP").attr("disabled",true);
				 $("#invoiceDate").combo('disable');
				 $("#invoiceAmount").numberbox({disabled:true});
				 $("#invoiceNo").attr("disabled",true);
				 $("#downInvoiceNo").attr("disabled",true);
				  $("#giveBillsDate").combo('disable');
				  $("#createDate").combo('disable');
			}
		}
	});
	//国别地区
	$('#area').combotree({
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    panelHeight:'auto',
	    animate:false,
	    multiline:false,
	    required : true,
		panelHeight:'auto',
    	onHidePanel:function(){}
	});
	
	//港口列表
	$('#portName').combobox({
		method: "get",
		panelHeight:'auto',
		url:'${ctx}/system/downMenu/jsonGK', 
	    valueField:'id',
	    textField:'gkm', 
	    required : true,
	});
	
	//下游客户下拉菜单
	$('#downStreamClient').combobox({
		method: "get",
		url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=3',
		valueField : 'id',
		textField : 'customerName',
	});
	
	//供应商下拉菜单
	$('#supplier').combobox({
		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=2',
		valueField : 'id',
		textField : 'customerName',
		required : true,
	});
	
	//我司单位列表
	$('#ourUnit').combobox({
		panelHeight:'auto',
		method: "get",
		url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
	    valueField:'id',
	    textField:'customerName', 
	    disabled:false,
	    required : true,
	});
	
	//数量单位列表
	$('#numberUnits').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/downMenu/getDictByCode/sldw',
	    valueField:'id',
	    textField:'name', 
	    required : true,
	});
	
	
	//装运类别列表
	$('#shipmentType').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/downMenu/getDictByCode/shipmentType',
	    valueField:'id',
	    textField:'name',
	    required : true,
	});
	
});

</script>
</body>
</html>

