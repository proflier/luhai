<%@ page import="com.cbmie.lh.logistic.entity.LhBills"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
	<form id="mainform" action="${ctx}/logistic/bills/${action}" method="post">
	<div id="bill_mainDiv"  data-options="border:false">
		<div data-options="title: '到单信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass" >
				<legend>到单信息</legend>
				<a id="toPurchaseListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toPurchaseList()">采购合同列表</a>
				<table width="98%" class="tableClass">
				 <tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>提单号</th>
					<td width="30%" >
					<input type="hidden" name="id" id="id" value="${lhBills.id}" />
					<input type="hidden" id="relLoginNames" name="relLoginNames" value="${lhBills.relLoginNames }"/>
					<input type="hidden" id="purchaseContractIds" name="purchaseContractIds" value="${lhBills.purchaseContractIds }"/>
					<input type="hidden" id="purchaseContractIdsOld" name="purchaseContractIdsOld" value="${purchaseContractIdsOld }"/>
					<input name="billNo"  id="billNo" value="${lhBills.billNo}" class="easyui-validatebox" type="text" data-options="required:true" readonly="readonly"/>
					</td>
					<th width="20%">发货单位</th>
					<td width="30%">
<%-- 						<mytag:combobox name="sendUnit" value="${lhBills.sendUnit}" type="customer" parameters="10230002" required="false"/> --%>
						<mytag:combobox name="sendUnit" value="${lhBills.sendUnit}" type="customer" parameters="10230002" hightAuto="false" permission4User="true"/>
					</td>
					<!-- 
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>二次换单提单号</th>
					<td width="30%"><input name="twoTimeBillNo" id="twoTimeBillNo" class="easyui-validatebox"  value="${lhBills.twoTimeBillNo}" 
						data-options="required:false" type="text"  /></td>
					 -->
				</tr>
				<tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>供货单位</th>
					<td width="30%">
<%-- 						<mytag:combobox name="deliveryUnit" value="${lhBills.deliveryUnit}" type="customer" parameters="10230002" required="false" disabled="true"/> --%>
						<mytag:combobox name="deliveryUnit" value="${lhBills.deliveryUnit}" type="customer" parameters="10230002" hightAuto="false" permission4User="true"/>
					</td>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>订货单位</th>
					<td width="30%">
<%-- 					<mytag:combobox name="orderUnit" value="${lhBills.orderUnit}" type="customer" parameters="1or3" required="false" disabled="true"/> --%>
						<input id="orderUnit" name="orderUnit"  type="text" value="${lhBills.orderUnit }" class="easyui-validatebox"  />
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>预计到港日期</th>
					<td>
					<input name="expectPortDate" type="text"  value="<fmt:formatDate value="${lhBills.expectPortDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>	
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>装运类别</th>
					<td>
						<mytag:combobox name="shipmentType" value="${lhBills.shipmentType}" type="dict" parameters="shipmentType"/>
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>正本到单日期</th>
					<td>
						<input name="trueToDate" type="text"  value="<fmt:formatDate value="${lhBills.trueToDate}"/>"  
						class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>	
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>币种</th>
					<td>
						<mytag:combobox name="currency" value="${lhBills.currency}" type="dict" parameters="currency" required="false" disabled="true"/>
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>目的港</th>
					<td><input id="portName" name="portName" type="text" class="easyui-combobox" value="${lhBills.portName}" />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>船公司</th>
					<td><input id="shipCompany" name="shipCompany" type="text" value="${lhBills.shipCompany}"  class="easyui-validatebox" 
							data-options="required:false"  />
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>免堆期天数</th>
					<td><input id="freeDays" name="freeDays" type="text" value="${lhBills.freeDays}"  class="easyui-validatebox"/>
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>装船日期</th>
					<td><input name="shipDate" type="text"  value="<fmt:formatDate value="${lhBills.shipDate}" />" 
						class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>			
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>数量</th>
					<td><input name="numbers" id="numbers"   type="text" value="${lhBills.numbers}" class="easyui-numberbox" 
						data-options="precision:'2',required:false"  readonly="readonly" />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>数量单位</th>
					<td>
						<mytag:combobox name="numberUnits" value="${lhBills.numberUnits}" type="dict" parameters="sldw"/>
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>船编号</th>
					<td>
						<input id="shipNo" name="shipNo" type="text" value="${lhBills.shipNo}"   class="easyui-combobox" data-options="required:true"/>
					</td>
					<th>检验机构</th>
					<td>
						<mytag:combobox name="checkOrg" value="${lhBills.checkOrg}" type="customer" parameters="10230013"/>
					</td>
				</tr> 
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>发票日期</th>
					<td>
						<input name="invoiceDate" type="text"  value="<fmt:formatDate value="${lhBills.invoiceDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>上游发票号</th>
					<td><input name="invoiceNo" type="text" value="${lhBills.invoiceNo}" class="easyui-validatebox"   data-options="required:true"
						 />				
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>发票金额</th>
					<td>  <input name="invoiceAmount" id="invoiceAmount" readonly="true" type="text" value="${lhBills.invoiceAmount}" class="easyui-numberbox" 
						data-options="precision:'2',required:false"  />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>交单期</th>
					<td>
					<input name="giveBillsDate" type="text"  value="<fmt:formatDate value="${lhBills.giveBillsDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>收货单位</th>
					<td>
						<mytag:combobox name="receivingUnit" value="${lhBills.receivingUnit}" type="customer" parameters="10230001" required="false" disabled="true"/>
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>登记时间</th>
					<td>
					<input name="createDate" type="text"  value="<fmt:formatDate value="${lhBills.createDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
					</td>
				</tr>
				<tr>
					<th>登记人</th>
					<td>
						 <input id="createrName" name="createrName" type="text" value="${lhBills.createrName }"   
						 readonly="true" class="easyui-validatebox"/>
						 <input  name="createrNo" type="hidden" value="${lhBills.createrNo }"/>
					</td>
					<th>登记部门</th>
					<td>
						<input id="createrDept" name="createrDept" type="text"  value="${lhBills.createrDept }"   
						class="easyui-validatebox" readonly="true" />
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>检验标准</th>
					<td colspan="3" style="height:1cm"><textarea  name="checkStandard" type="text" id="checkStandard"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${lhBills.checkStandard}</textarea></td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>备注</th>
					<td colspan="3" style="height:1cm"><textarea  name="remark" type="text" id="remark"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${lhBills.remark}</textarea></td>
				</tr>
				</table>
			</fieldset>
		</div>	
		<div data-options="title: '货物明细', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/logistic/billsGoodsList.jsp"%>
		</div>
		<div data-options="title: '附件信息', iconCls: false, refreshable: false">
			<input id="accParentEntity" type="hidden"  value="<%=LhBills.class.getName().replace(".","_") %>" />
			<input id="accParentId" type="hidden" value="${lhBills.id}" />
			<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
		</div>	
	</div>
	</form>
<script type="text/javascript">
$(function(){
	var tabIndex = 0;
	$('#bill_mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#id").val() == '') && ($("#purchaseContractIds").val() == $("#purchaseContractIdsOld").val())){//主表校验通过且已经保存数据
	    		tabIndex = index;//更换tab
	    	}else{
	    		if(index != 0){
	    			parent.$.messager.alert('提示','请先保存主表信息!!!');
	    		}
	    		$("#bill_mainDiv").tabs("select", tabIndex);//当前tab
	    		return ;
	    	}
	    }
	});
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){  
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dg.datagrid('clearSelections');
	    		dg.datagrid('reload');
	    		$('#id').val(data.returnId);
	    		$("#numbers").numberbox('setValue',data.numbers);
	    		$("#invoiceAmount").numberbox('setValue',data.invoiceAmount);
	    		if(data.currentSequence!=null){
		    		$('#billNo').val(data.currentSequence);
		    	}
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		$("#purchaseContractIdsOld").val($("#purchaseContractIds").val());
	     		billGoodsObj.idValue = $("#id").val();
	     		billGoodsObj.getList();
				$.easyui.loaded();
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
				$.easyui.loaded();
	    		return false;
	    	}
	    }
	});
	shipNoItems();
	//港口列表
	$('#portName').combobox({
		method: "get",
		url:'${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230007', 
	    valueField:'customerCode',
	    textField:'customerName',
	    multiple:true,
	    value : '${lhBills.portName}'==""?null:'${lhBills.portName}'.split(","),
	    onHidePanel:function(){}
	});
	//订货单位
	$('#orderUnit').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/baseInfo/baseUtil/getAffiOurUnitOrCustomer',
		valueField : 'customerCode',
		textField : 'customerName',
	});
	
});
$(function(){
	if('${action}' == 'view') {
		$("#tbGoods").hide();
		$("#toPurchaseListId").hide();
	}
	//var action = '${action}';
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		$("#mainform").find('input').attr("readonly",true);
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
});
function shipNoItems(){
	if($("#purchaseContractIds").val()){
		var shipnoC =$('#mainform #shipNo').combobox({
			method: "get",
			editable:false,
			url:'${ctx}/baseInfo/lhUtil/shipsByPurchaseInnerNo/'+$("#purchaseContractIds").val(), 
		    valueField:'shipNo',
		    textField:'noAndName',
		    multiple:false,
		    onLoadSuccess:function(){
		    	var datas = shipnoC.combobox('getData');
		    	var value_t = shipnoC.val();
		    	for(var i=0;i<datas.length;i++){
		    		if(value_t == datas[i].shipNo){
		    			return;
		    		}
		    	}
		    	shipnoC.combobox('clear');
		    },
		    onHidePanel:function(){}
		});
	}
} 
</script>
</body>
</html>

