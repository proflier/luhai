<%@page import="com.cbmie.lh.purchase.entity.PurchaseContract"%>
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
	<form id="mainform" action="${ctx}/purchase/purchaseContract/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '采购合同主信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%" >内部合同号</th>
						<td width="30%">
							<input id="innerContractNo" name="innerContractNo" type="text" value="${purchaseContract.innerContractNo }"  style="width:70%;" readonly="readonly" class="easyui-validatebox"  data-options="required:true" />
						</td>
						<th width="20%" >采购合同号</th>
						<td width="30%">
							<input id="purchaseContractNo" name="purchaseContractNo" type="text"  value="${purchaseContract.purchaseContractNo }" class="easyui-validatebox"  data-options="required:true"/>
							<input id="id" name="id" type="hidden"  value="${purchaseContract.id }" />
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td >
							<input id="setUnit" name="setUnit"   type="text" value="${purchaseContract.setUnit }"  class="easyui-validatebox"  />
						</td>
						<th  >经营方式 </th>
						<td>
							<input id="runMode" name="runMode" type="text" value="${purchaseContract.runMode }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >卖方</th>
						<td>
							<mytag:combobox name="deliveryUnit" value="${purchaseContract.deliveryUnit}" type="customer" parameters="10230002" hightAuto="false" permission4User="true"/>
						</td>
						<th  >买方</th>
						<td>
							<input id="receivingUnit" name="receivingUnit" type="text" value="${purchaseContract.receivingUnit }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th>采购方式</th>
						<td>
							<mytag:combobox name="tradeWay" value="${purchaseContract.tradeWay}" type="dict" parameters="tradeWay" required="true"/>
						</td>
						<th  >结算方式</th>
						<td>
							<input id="payMode" name="payMode" type="text" value="${purchaseContract.payMode }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr id="creditDeadlineAnchor">
						<th>信用证开具期限</th>
						<td colspan="3">
							受载前<input id="creditDeadline" name="creditDeadline" type="text" value="${purchaseContract.creditDeadline }" class="easyui-validatebox"  />
							天
						</td>
					</tr>
					 <tr>
						<th  >币种</th>
						<td>
							<input id="currency" name="currency" type="text" value="${purchaseContract.currency }" class="easyui-validatebox"  />
						</td>
						<th  >付款类型</th>
						<td>
							<input id="creditType" name="creditType" type="text" value="${purchaseContract.creditType }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >应预付额</th>
						<td>
							<input  name="prepaidMoney" type="text" value="${purchaseContract.prepaidMoney }" class="easyui-numberbox" data-options="precision:'2',min:0,max:999999999" />
						</td>
						<th  ><div id="priceTermAnchorTh">价格条款</div></th>
						<td >
							<div id="priceTermAnchorTd" >
							<input id="priceTerm" name="priceTerm" type="text" value="${purchaseContract.priceTerm }" class="easyui-validatebox"/>
							</div>
						</td>
					</tr>
					<tr>
						<th  >保证金</th>
						<td>
							<input id="margin" name="margin" type="text" value="${purchaseContract.margin }" class="easyui-numberbox" data-options="precision:'2',min:0,max:999999999"   />
						</td>
						<th  >合同金额</th>
						<td>
							<input id="contractAmount" name="contractAmount" type="text" value="${purchaseContract.contractAmount }" readonly="readonly" onchange="contractAmountClick()" class="easyui-numberbox" data-options="prompt: '子表自动计算',precision:'2'" />
						</td>
					</tr>
					<tr>
						<th  >订货总量</th>
						<td>
							<input id="contractQuantity" name="contractQuantity" type="text" value="${purchaseContract.contractQuantity }" readonly="readonly" class="easyui-numberbox" data-options="prompt: '子表自动计算',precision:'3'"  />
						</td>
						<th  >数量单位</th>
						<td>
							<input id="quantityUnit" name="quantityUnit" type="text" value="${purchaseContract.quantityUnit }" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th  >签约日期 </th>
						<td>
							<input id="signingDate" name="signingDate"   type="text" value="<fmt:formatDate value="${purchaseContract.signingDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th>合同有效期</th>
						<td>
							<input id="contractValidityPeriod" name="contractValidityPeriod"   type="text" value="<fmt:formatDate value="${purchaseContract.contractValidityPeriod }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					
					<tr>
						<th>运输方式</th>
						<td>
							<mytag:combobox name="transportCategory" value="${purchaseContract.transportCategory}" type="dict" parameters="YSFS" required="true"/>
						</td>
						<th>运输编号</th>
						<td>
							<input id="shipNo" name="shipNo" type="text" value="${purchaseContract.shipNo }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr id="deliveryAnchor" >
						<th>交货方式</th>
						<td colspan="3">
							<mytag:combobox name="deliveryMode" value="${purchaseContract.deliveryMode}" type="dict" parameters="THFS"/>
							<mytag:combobox name="deliveryModeDetail" value="${purchaseContract.deliveryModeDetail}" type="dict" parameters="deliveryModeDetail"/>
						</td>
					</tr>
					<tr>
						<th>发运地点（装运港）</th>
						<td>
							<input id="sendAddr" name="sendAddr"  type="text" value="${purchaseContract.sendAddr }" class="easyui-validatebox"  />
						</td>
						<th>交货地点（卸货港）</th>
						<td>
							<input id="deliveryAddr" name="deliveryAddr"  type="text" value="${purchaseContract.deliveryAddr }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >预计发货（装港）时间</th>
						<td>
							<input id="expectSendDate" name="expectSendDate"   type="text" value="<fmt:formatDate value="${purchaseContract.expectSendDate }" />"  class="Wdate" />
						</td>
						<th  >预计到货（卸港）时间</th>
						<td>
							<input id="expectDeliveryDate" name="expectDeliveryDate"   type="text" value="<fmt:formatDate value="${purchaseContract.expectDeliveryDate }" />"  class="Wdate" />
						</td>
					</tr>
					<tr>
						<th  >交货起期</th>
						<td>
							<input id="deliveryStartDate" name="deliveryStartDate" value="<fmt:formatDate value="${purchaseContract.deliveryStartDate }" />" class="Wdate" />
						</td>
						<th  >交货止期</th>
						<td>
							<input id="deliveryTerm" name="deliveryTerm"   type="text" value="<fmt:formatDate value="${purchaseContract.deliveryTerm }" />"  class="Wdate" />
						</td>
					</tr>
					<tr>
						<th  >受载期起始时间</th>
						<td>
							<input id="transportStartDate" name="transportStartDate"   type="text" value="<fmt:formatDate value="${purchaseContract.transportStartDate }" />"  class="Wdate" />
						</td>
						<th  >受载期终止时间</th>
						<td>
							<input id="transportTermDate" name="transportTermDate"   type="text" value="<fmt:formatDate value="${purchaseContract.transportTermDate }" />"  class="Wdate" />
						</td>
					</tr>
					<tr>
						<th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${purchaseContract.businessManager}" type="userTree" />
						</td>
						<th>费用承担人</th>
						<td>
							<mytag:combotree name="costBearer" value="${purchaseContract.costBearer}" type="userTree" />
						</td>
					</tr>
					<tr>
						<th  >印章类型 </th>
						<td>
							<input id="sealCategory" name="sealCategory" type="text" value="${purchaseContract.sealCategory }" class="easyui-validatebox"  />
						</td>
						<th  >印章管理员</th>
						<td>
							<input id="sealManager" name="sealManager" type="text" value="${purchaseContract.sealManager }"  readonly="readonly" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >进口国别</th>
						<td colspan="3">
							<input id="importCountry" name="importCountry"  type="text" value="${purchaseContract.importCountry }"  />
						</td>
					</tr>
					<tr>
						<th  >签约地点</th>
						<td>
							<input id="signingPlace" name="signingPlace" type="text" value="${purchaseContract.signingPlace }"   />
						</td>
						<th  >仲裁地</th>
						<td>
							<input id="arbitrationPlace" name="arbitrationPlace" type="text" value="${purchaseContract.arbitrationPlace }"   />
						</td>
					</tr>
					<tr>
						<th>数量验收标准</th>
						<td>
							<mytag:combobox name="quantityAcceptance" value="${purchaseContract.quantityAcceptance}" type="dict" parameters="QUANTITY-ACCEPTANCE-CRITERIA"/>
						</td>
						<th>质量验收标准</th>
						<td>
							<mytag:combobox name="qualityAcceptance" value="${purchaseContract.qualityAcceptance}" type="dict" parameters="QUALITY-ACCEPTANCE-CRITERIA"/>
						</td>
					</tr>
					<tr>
						<th  >金额溢短装</th>
						<td>
							<input id="moreOrLessAmount" name="moreOrLessAmount" type="text" value="${purchaseContract.moreOrLessAmount }" class="easyui-numberbox" data-options="required:true,precision:'2',suffix:'%'"  />
						</td>
						<th  >数量溢短装</th>
						<td>
							<input id="moreOrLessQuantity" name="moreOrLessQuantity" type="text" value="${purchaseContract.moreOrLessQuantity }" class="easyui-numberbox" data-options="required:true,precision:'2',suffix:'%'"  />
						</td>
					</tr>
					<tr>
						<th  >合同金额大写 </th>
						<td>
							<input id="blockMoney" name="blockMoney" type="text" value="${purchaseContract.blockMoney }" readonly="readonly" class="easyui-validatebox"  />
						</td>
						<th  >装/卸率</th>
						<td>
							<input id="unloadingRate" name="unloadingRate" type="text" value="${purchaseContract.unloadingRate }" class="easyui-validatebox"   />
						</td>
					</tr>
					<tr>
						<th>检测费用</th>
						<td>
							<mytag:combobox name="testFee" value="${purchaseContract.testFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
						<th>港建费</th>
						<td>
							<mytag:combobox name="portFee" value="${purchaseContract.portFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>码头费</th>
						<td>
							<mytag:combobox name="dockFee" value="${purchaseContract.dockFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
						<th>运费</th>
						<td>
							<mytag:combobox name="freightFee" value="${purchaseContract.freightFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>堆存费</th>
						<td>
							<mytag:combobox name="storageFee" value="${purchaseContract.storageFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
						<th>滞期/速遣费</th>
						<td>
							<mytag:combobox name="dispatchFee" value="${purchaseContract.dispatchFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>其他费用</th>
						<td colspan="3">
							<mytag:combobox name="otherFee" value="${purchaseContract.otherFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					
					<tr>
						<th  >检验机构</th>
						<td >
							<input id="checkOrg" name="checkOrg"   type="text" value="${purchaseContract.checkOrg }"  class="easyui-validatebox"  />
						</td>
						
					</tr>
					<tr>
						<th  >检验标准</th>
						<td colspan="3">
							<textarea rows="6" cols="90" id="checkStandard" class="easyui-validatebox" name="checkStandard" data-options="validType:['length[0,500]']">${purchaseContract.checkStandard }</textarea>
						</td>
					</tr>
					<tr id="stockTermsAnchor">
						<th  >货权转移条款</th>
						<td colspan="3">
							<textarea rows="6" cols="90" id="stockTerms" class="easyui-validatebox" name="stockTerms" data-options="validType:['length[0,500]']">${purchaseContract.stockTerms }</textarea>
						</td>
					</tr>
					<tr>
						<th  >结算条款 </th>
						<td>
							<textarea rows="6" cols="90" id="settlementTerms" class="easyui-validatebox" name="settlementTerms" data-options="validType:['length[0,500]']">${purchaseContract.settlementTerms }</textarea>
						</td>
					</tr>
					<tr>
						<th  >付款条款</th>
						<td colspan="3">
							<textarea rows="6" cols="90" id="paymentClause" class="easyui-validatebox" name="paymentClause" data-options="validType:['length[0,500]']">${purchaseContract.paymentClause }</textarea>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="6" cols="90" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,500]']">${purchaseContract.comment }</textarea>
						</td>
					</tr>					
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty purchaseContract.createrName ? sessionScope.user.name : purchaseContract.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty purchaseContract.createrDept ? sessionScope.user.organization.orgName : purchaseContract.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ purchaseContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${purchaseContract.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${purchaseContract.pid ne null }">
				<fieldset id="changeReasonField"  class="fieldsetClass" >
				<legend>变更原因</legend>
					<div >
						<input id="changeReason" style="width:45%;height:40px;" name="changeReason" value="${purchaseContract.changeReason }" class="easyui-validatebox"/>
					</div>
				</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '采购信息', iconCls: false, refreshable: false">
					<%@ include file="/WEB-INF/views/purchase/contractGoodsList.jsp"%>
			</div>	
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PurchaseContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${purchaseContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>			
	</form>
</div>
<script type="text/javascript">
var purchase;
var dgGoods;
var dGoods;
var allCode;
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
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){  
			if('${action}'=="change"){
	    	successTipNew(data,dg,d);
			}else{
				successTipNew(data,dg);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#innerContractNo').val(data.currnetSequence);
		    	}
			}
     		initStreamContainer();
			$.easyui.loaded();
	    }
	});
	
	$.ajax({
			type:'GET',
			async: false,
			 dataType: "json", 
			url:"${ctx}/baseInfo/baseUtil/getAffiUnitAbbr",
			success: function(data){
				allCode=""+data;
				if(allCode.charAt(allCode.length-1)==','){
					allCode=allCode.substring(0,allCode.length-1);
				}
			}
		});
	//帐套单位
	$('#setUnit').combobox({
		required:true,
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/getAffiByType/10230001',
		valueField : 'customerCode',
		textField : 'customerName',
		prompt:'保存后不能修改！',
	    onSelect:function(record){
	    	if("${keyWord}"){
	    		var souceCode ="";
	    		var str = $('#innerContractNo').val();
	    		var result = allCode.split(",");
	    		for (var i = 0; i < result.length; i++) {
	    			if(str.indexOf(result[i]) != -1){
	    				souceCode = result[i];
	    			}
	    		}
	    		if(record.unitAbbr!=souceCode){
	    			str = str.replace(souceCode,record.unitAbbr);
	    		}
	    		$('#innerContractNo').val(str);
	    	}
	    	
	    	$('#receivingUnit').combobox("setValue",record.customerCode);
	    }
	});
	
	if("${action}"!="create"){
		$('#setUnit').combobox('disable');
	}
	
	
	//检验机构
	$('#checkOrg').combobox({
//			panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/getAffiByType/13',
		valueField : 'customerCode',
		textField : 'customerName', 
	});
	
	
	//币种列表
	$('#currency').combobox({
// 		panelHeight:'auto',
		url:'${ctx}/system/dictUtil/getDictByCode/currency',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onLoadSuccess: function () { 
		}
	});

	
	// 	进口国别
	$('#importCountry').combotree({
		method:'GET',
	    url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
	    idField : 'code',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:false,
	    multiline:false,
	    required : true,
// 		panelHeight:'auto',
	onHidePanel:function(){}
	});
	
	//签约地点
	$('#signingPlace').combotree({
		method:'GET',
	    url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
	    idField : 'code',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:false,
	    multiline:false,
	    required : true,
// 		panelHeight:'auto',
    	onHidePanel:function(){}
	});
	
	
	$('#deliveryStartDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'deliveryTerm\')}',onpicked:function(){deliveryTerm.click();}});
	});
	$('#deliveryTerm').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'deliveryStartDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
	
	$('#transportStartDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'transportTermDate\')}',onpicked:function(){transportTermDate.click();}});
	});
	$('#transportTermDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'transportStartDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
	
	$('#expectSendDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'expectDeliveryDate\')}',onpicked:function(){expectDeliveryDate.click();}});
	});
	$('#expectDeliveryDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'expectSendDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
	
	
	//订货单位
	$('#orderUnit').combobox({
// 		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/getAffiByType/10230002',
		valueField : 'customerCode',
		textField : 'customerName',
		required : true,
	});
	
	//收货单位
	$('#receivingUnit').combobox({
// 		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/getAffiByType/10230001',
		valueField : 'customerCode',
		textField : 'customerName',
		required : true,
		onSelect:function(){
			
		}
	});
	
	
	//数量单位列表
	$('#quantityUnit').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/sldw',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onLoadSuccess: function () { //加载完成后,设置选中项
	    	if("${action}"=="create"){
	    		$('#quantityUnit').combobox('select','10580003');
	    	}
        }
	});
	
	//付款方式
	$('#payMode').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/paymentMethod',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onSelect:function(record){
    		if("信用证"!=record.name){
    			$('#creditDeadlineAnchor').hide();   			
	    	}else{
	    		$('#creditDeadlineAnchor').show();
	    	}
	    }
	});
	
	
	//价格条款
	$('#priceTerm').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/jgtk',
	    valueField:'code',
	    textField:'name', 
// 	    required : true,
	});
	
	//付款类型
	$('#creditType').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/sxlx',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	});
	
	
	//合同类别
	$('#contractCategory').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/htlb',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onSelect:function(record){
   			var str = $('#innerContractNo').val();
    		if("内贸"==record.name){
				str = str.replace(/OV/,"DM");    			
	    	}else{
	    		str = str.replace(/DM/,"OV");
	    	}
    		$('#innerContractNo').val(str);
	    }
	});
	
	//经营方式
	$('#runMode').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/runMode',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onSelect:function(record){
	    	var str = $('#innerContractNo').val();
    		if("内贸"==record.name){
    			$('#priceTermAnchorTh').hide();
    			$('#priceTermAnchorTd').hide();
    			str = str.replace(/OV/,"DM");   
	    	}else{
    			$('#priceTermAnchorTh').show();
    			$('#priceTermAnchorTd').show();
    			str = str.replace(/DM/,"OV");
	    	}
    		$('#innerContractNo').val(str);
    		if("进口"==record.name){
    			$('#deliveryAnchor').hide();
    			$('#stockTermsAnchor').hide();
	    	}else{
    			$('#deliveryAnchor').show();
    			$('#stockTermsAnchor').show();
	    	}
    		
	    }
	});
	
	//结算条款
// 	$('#settlementTerms').combobox({
// // 		panelHeight:'auto',
// 		method: "get",
// 		url:'${ctx}/system/dictUtil/getDictByCode/settlementTerms',
// 	    valueField:'code',
// 	    textField:'name', 
// 	    required : true,
// 	});
	
	//仲裁地
	$('#arbitrationPlace').combotree({
		method:'GET',
	    url: '${ctx}/baseInfo/baseUtil/countryAreaItems',
	    idField : 'code',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:false,
	    multiline:false,
	    required : true,
// 		panelHeight:'auto',
    	onHidePanel:function(){}
	});
	
	
	//印章类型 
	$('#sealCategory').combogrid({    
	    panelWidth:450,    
	    method: "get",
	    idField:'signetCode',    
	    textField:'typeName',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
		url:'${ctx}/baseInfo/lhUtil/getSignetList', 
	    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'signetCode',title:'编码',sortable:true,width:25},
				{field:'typeCode',title:'印章类型',sortable:true,width:25,
					formatter: function(value,row,index){
						var unit_t = '';
						$.ajax({
							url : '${ctx}/system/dictUtil/getDictNameByCode/SIGNETTYPE/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								unit_t = data;
							}
						});
						return unit_t;
						}
				},
				{field:'orgId',title:'所属公司',sortable:true,width:25,
					formatter: function(value,row,index){
					var unit_t = '';
					$.ajax({
						url : '${ctx}/baseInfo/baseUtil/orgNameShow?orgId='+value ,
						type : 'get',
						cache : false,
						async: false,
						success : function(data) {
							unit_t = data;
						}
					});
					return unit_t;
					}	
				}
	    ]],
	    onSelect:function(rowIndex, rowData){
	    	$('#sealManager').val(rowData.saveUserName);
	    }
	}); 
	
});


function contractAmountClick(){
	var num = $("#contractAmount").val();
	$("#blockMoney").val(NoToChinese(num));
}
function NoToChinese(num) { 
		if (!/^\d*(\.\d*)?$/.test(num)) { alert("Number is wrong!"); return "Number is wrong!"; } 
		var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); 
		var BB = new Array("", "拾", "佰", "仟", "万", "亿", "点", ""); 
		var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = ""; 
		for (var i = a[0].length - 1; i >= 0; i--) { 
		switch (k) { 
		case 0: re = BB[7] + re; break; 
		case 4: if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0])) 
		re = BB[4] + re; break; 
		case 8: re = BB[5] + re; BB[7] = BB[5]; k = 0; break; 
		} 
		if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0) re = AA[0] + re; 
		if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re; k++; 
		} 
	
		if (a.length > 1) //加上小数部分(如果有小数部分) 
		{ 
		re += BB[6]; 
		for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)]; 
		} 
		return re; 
	} 
	
//弹窗加载采购协议
function loadProtocol(){
		dlgProtocol=$("#dlgProtocol").dialog({
		  	width: 900,    
		    height: 400, 
		    title: '选择采购协议',
		    href:'${ctx}/purchase/purchaseContract/loadProtocol/',
		    modal:true,
		    closable:true,
		    style:{borderWidth:0},
		    buttons:[{
				text:'确认',iconCls:'icon-ok',
				handler:function(){
					initContract();
					dlgProtocol.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlgProtocol.panel('close');
				}
			}]
		});
		
	}
	
function initMainForm(data){
	var contractQuantity =0.000;
	var contractAmount =0.00;
	var array = [];
	if(data.rows.length>0){
		for(var i = 0; i < data.rows.length; i++){
		    var map = data.rows[i];
		    for (var key in map){
		    	var currenctValue = map[key];
			    if(key=='goodsQuantity'){
			    	if(!isNaN(parseFloat(currenctValue))){
			    		contractQuantity = contractQuantity + parseFloat(currenctValue);
			    	}
			    }
			    if(key=='amount'){
			    	if(!isNaN(parseFloat(currenctValue))){
			    		contractAmount = contractAmount +parseFloat(currenctValue);
			    	}
			    }
		    }
		}
	}
	$('#contractQuantity').numberbox('setValue',contractQuantity.toFixed(3));
	$('#contractAmount').numberbox('setValue',contractAmount.toFixed(2));
	var purchaseContractId = $("#id").val();
	contractQuantity = contractQuantity.toFixed(3);
	contractAmount = contractAmount.toFixed(2);
	if(purchaseContractId!=""){
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/purchase/purchaseContract/saveOnChangeGoods/"+purchaseContractId+"/"+contractQuantity+"/"+contractAmount,
			success: function(data){
			}
		});
	}
	contractAmountClick();
}

//弹窗增加采购信息
function addContractGoods() {
	var idValue = $('#id').val();
	dlg_goods=$("#dlg_goods").dialog({  
		method:'GET',
	    title: '新增采购信息',    
	    width: 700,    
	    height: 400,     
	    href:'${ctx}/purchase/purchaseContractGoods/create/'+idValue,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				accept();
				$('#contractGoodsForm').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_goods.panel('close');
			}
		}]
	});
}


//删除商品
function deleteContractGoods(){
	var row = dgContractGoods.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/purchase/purchaseContractGoods/delete/'+row.id,
				success: function(data){
					successTip(data,dgContractGoods);
				}
			});
		} 
	});
}

//弹窗修改采购信息
function updateContractGoods() {
	var row = dgContractGoods.datagrid('getSelected');
	if(rowIsNull(row)) return;
	dlg_goods=$("#dlg_goods").dialog({   
	    title: '修改采购信息',    
	    width: 700,    
	    height: 400,
	    href:'${ctx}/purchase/purchaseContractGoods/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				accept();
				$('#contractGoodsForm').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_goods.panel('close');
			}
		}]
	});
}

//弹窗增加采购信息
function add_saleRelation() {
	dlg_saleContract=$("#dlg_saleContract").dialog({  
		method:'GET',
	    title: '选择销售信息',    
	    width: 900,    
	    height: 420,     
	    href:'${ctx}/purchase/purchaseContract/loadSaleContract',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				initSaleContract();
				dlg_saleContract.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_saleContract.panel('close');
			}
		}]
	});
}

</script>
</body>
</html>

