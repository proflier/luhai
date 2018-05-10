<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
	<form id="mainform_shipTrace" action="${ctx}/logistic/shipTrace/${action}" method="post">
		<div id="mainDiv_shipTrace" class="" data-options="border:false">
			<div data-options="title: '船舶动态', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						
						<th width="20%">内部合同号</th>
						<td colspan="3">
							<input id="innerContractNo" name="innerContractNo" value="${shipTrace.innerContractNo}" readonly="readonly" style="width: 400px" class="easyui-validatebox" data-options="required:true"/>
							<a id="purchaseContractListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toPurchaseList()">采购合同列表</a>
						</td>
					</tr>
					<tr>
						<th width="20%">运输类型</th>
						<td width="30%" >
							<input id="tradeCategory" name="tradeCategory" value="${shipTrace.tradeCategory }" class="easyui-combobox" />
							<input id="id" name="id" type="hidden"  value="${shipTrace.id }" />
							<input type="hidden" id="tradeCategoryBak"  value="${shipTrace.tradeCategory }"/>
							<input type="hidden" id="tradeCategoryOld" name="tradeCategoryOld"  value="${shipTrace.tradeCategory }"/>
							<input type="hidden" id="shipNoOld" name="shipNoOld"  value="${shipTrace.shipNo }"/>
						</td>
						<th width="20%">运输方式</th>
						<td width="30%">
							<mytag:combobox name="transportCategory" value="${shipTrace.transportCategory}" type="dict" parameters="YSFS"/>
						</td>
					</tr>
					<tr>
						<th  >供货单位</th>
						<td>
							<mytag:combobox name="deliveryUnit" value="${shipTrace.deliveryUnit}" type="customer" parameters="10230002" hightAuto="false" permission4User="true"/>
						</td>
						<th width="20%">运输编号(内部)</th>
						<td width="30%">
							<input id="shipNo" name="shipNo" value="${shipTrace.shipNo}" class="easyui-validatebox" data-options="required:true" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<th width="20%">运输工具名称</th>
						<td width="30%">
							<input id="shipName" name="shipName" value="${shipTrace.shipName}" class="easyui-validatebox" data-options="required:true,prompt: '船名、车牌号、火车车次'"/>
						</td>
						<th width="20%">英文名</th>
						<td width="30%">
							<input id="shipNameE" name="shipNameE" value="${shipTrace.shipNameE}" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>装载量</th>
						<td colspan="3">
							<input id="loading" name="loading" type="text" value="${shipTrace.loading }"  class="easyui-numberbox" data-options="precision:'3',suffix:'T'" />
						</td>
					</tr>
					<tr id="wmShipNoTr">
						<th width="20%">源运输工具编号</th>
						<td  colspan="3">
							<input id="wmShipNo" size='30' name="wmShipNo" type="text" value="${shipTrace.wmShipNo }" class="easyui-combogrid" />
						</td>
					</tr>
					<tr>
						<th width="20%">抵港时间</th>
						<td width="30%">
							<input id="loadPortDate" name="loadPortDate" type="text" value="<fmt:formatDate value="${shipTrace.loadPortDate }" pattern="yyyy-MM-dd HH:mm"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
						</td>
						<th width="20%">装货港口</th>
						<td width="30%">
							<mytag:combobox name="loadPort" value="${shipTrace.loadPort}" type="port" hightAuto="false"/>
						</td>
					</tr>
					<tr>
						<th width="20%">开装时间</th>
						<td width="30%">
							<input id="loadBeginDate" name="loadBeginDate" type="text" value="<fmt:formatDate value="${shipTrace.loadBeginDate }" pattern="yyyy-MM-dd HH:mm"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
						</td>
						<th width="20%">装完时间</th>
						<td width="30%">
							<input id="loadEndDate" name="loadEndDate" type="text" value="<fmt:formatDate value="${shipTrace.loadEndDate }" pattern="yyyy-MM-dd HH:mm"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
						</td>
					</tr>
					<tr>
						<th width="20%">装货量</th>
						<td>
							<input id="loadQuantity" name="loadQuantity" type="text" value="${shipTrace.loadQuantity }" class="easyui-numberbox" data-options="precision:'2'" />
						</td>
						<th width="20%">卸货量</th>
						<td >
							<input id="unloadQuantity" name="unloadQuantity" type="text" value="${shipTrace.unloadQuantity }" class="easyui-numberbox" data-options="precision:'2'" />
						</td>
					</tr>
					<tr>
						<th width="20%">抵卸港时间</th>
						<td width="30%">
							<input id="unloadPortDate" name="unloadPortDate" type="text" value="<fmt:formatDate value="${shipTrace.unloadPortDate }" pattern="yyyy-MM-dd HH:mm"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
						</td>
						<th width="20%">卸货港口</th>
						<td width="30%">
							<mytag:combobox name="unloadPort" value="${shipTrace.unloadPort}" type="port" hightAuto="false"/>
						</td>
					</tr>
					<tr>
						<th width="20%">开卸时间</th>
						<td width="30%">
							<input id="unloadBeginDate" name="unloadBeginDate" type="text" value="<fmt:formatDate value="${shipTrace.unloadBeginDate }" pattern="yyyy-MM-dd HH:mm"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
						</td>
						<th width="20%">卸完时间</th>
						<td width="30%">
							<input id="unloadEndDate" name="unloadEndDate" type="text" value="<fmt:formatDate value="${shipTrace.unloadEndDate }" pattern="yyyy-MM-dd HH:mm"/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
						</td>
					</tr>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3" >
							<textarea  name="remarks" type="text" id="remarks"  class="easyui-validatebox"
							style="overflow:auto;width:50%;height:100%;">${shipTrace.remarks}</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th  >登记人</th>
							<td>
								<input type="hidden" name="createrNo" value="${shipTrace.createrNo }"/>
								<input type="hidden" name="createrName" value="${shipTrace.createrName }"/>
								<input type="hidden" name="createrDept" value="${shipTrace.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${shipTrace.createDate  }' pattern='yyyy-MM-dd'/>" />
								${shipTrace.createrName }</td>
							<th  >部门</th>
							<td>${shipTrace.createrDept }</td>
							<th  >登记日</th>
							<td>
								<fmt:formatDate value="${shipTrace.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
				<c:if test="${action=='view'}">
				<jsp:include page="/WEB-INF/views/relationDetail/relationDetail.jsp" >
					<jsp:param value="shipTrace,saleContract,saleDelivery,outStock,saleInvoice,paymentConfirm" name="disView"/>
				</jsp:include>
			</c:if>
			</div>
		</div>
	</form>

<script type="text/javascript">
$(function(){
	var tabIndex = 0;
	$('#mainDiv_shipTrace').tabs({
	    onSelect:function(title,index){
	    }
	});
	
	$('#mainform_shipTrace').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		beforeSubmit();
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
     		successTipNew(data,logistic_shipTraces_page.entity_list);
     		$("#tradeCategoryOld").val($("#tradeCategory").combobox('getValue'));
     		var data = eval('(' + data + ')');
	    	if(data.currentSequence!=null){
	    		$('#shipNo').val(data.currentSequence);
	    		$("#shipNoOld").val(data.currentSequence)
	    	}
			$.easyui.loaded();
	    }
	});
	//贸易类别
	$('#tradeCategory').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/orderShipType',
	    valueField:'code',
	    textField:'name',
	    required : true,
	    onChange:function(newValue,oldValue){
	    	if(newValue=="10850003" || newValue=="10850004"){
	    		$('#innerContractNo').validatebox({required:false});
	    	}else{
	    		$('#innerContractNo').validatebox({required:true});
	    	}
	    	//母船处理
	    	if(newValue=="10850003" || newValue=="10850004"){
	    		$("#wmShipNoTr").show();
	    		$('#wmShipNo').combogrid({required:true});
	    		/* $("#wmShipNo").combobox({
	    			panelHeight:'auto',
	    			method: "get",
	    			url:'${ctx}/logistic/shipTrace/outerShipTrace',
	    		    valueField:'shipNo',
	    		    textField:'noAndName',
	    		    required : true
	    		}); */
	    	}else{
	    		/* $('#wmShipNo').combobox({ required : false});
	    		$('#wmShipNo').combobox('clear'); */
	    		$('#wmShipNo').combogrid({ required : false});
	    		$('#wmShipNo').combogrid('clear');
	    		$("#wmShipNoTr").hide();
	    	}
	    	var bak = $("#tradeCategoryBak").val();
	    	var changeFlag = "0";
	    	if(bak==""){
	    		changeFlag='1';
	    	}else if((bak=='10850002' && newValue!='10850002') || (bak!='10850002' && newValue=='10850002')){
	    		changeFlag='1';
	    	}
	    	if(changeFlag=='1'){
	    		$("#tradeCategoryBak").val(newValue);
	    		var url='${ctx}/system/sequenceCode/nextCodeNoSave/';
		    	if(newValue=="10850002"){
		    		url=url+'001014';
		    	}else{
		    		url=url+'001027';
		    	}
		    	$.ajax({
					type:'get',
					async:false,
					url:url,
					success:function(data){
						if(data!=null){
							$("#shipNo").val(data);
						}
					}
				});
	    	}
	    },
	    onLoadSuccess:function(){
	    	if('${shipNoEditFlag}'=="0"){
	    		$('#tradeCategory').combobox('disable');
	    	}
	    }
	});
	getOutShip();
	if('${shipTrace.tradeCategory }'=="10850002"){
		$('#wmShipNo').combogrid({required:false});
		$("#wmShipNoTr").hide();
	}
	if('${shipTrace.tradeCategory }'=="10850003" || '${shipTrace.tradeCategory }'=="10850004"){
		$('#innerContractNo').validatebox({required:false});
	}
	
	if('${shipNoEditFlag}'=="0"){
		$("#purchaseContractListId").hide();
		$('#shipNo').attr("readonly",true);	
		$('#tradeCategory').combobox('disable');
	}
	
	if('${action}' == 'view') {
		$("#purchaseContractListId").hide();
		//将输入框改成只读
		$("#mainform_shipTrace").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform_shipTrace").find(".easyui-validatebox").removeAttr("onclick");
		$("#mainform_shipTrace").find(".easyui-numberbox").attr("readonly",true);
		//将下拉选改成只读
		$("#mainform_shipTrace").find('.easyui-combobox').combobox({
		    disabled:true
		});
		//处理日期控件
		$("#mainform_shipTrace").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
});
function beforeSubmit(){
	var tradeCategoryOld = $("#tradeCategoryOld").val();
	var shipNoOld = $("#shipNoOld").val();
	var shipNo = $("#shipNo").val();
	if(shipNoOld!="" && shipNo!=shipNoOld){
		var p = '';
		if("10850002"==tradeCategoryOld){
			p = '001014';
		}else{
			p = '001027';
		}
		$.ajax({
			type:'get',
			async:false,//system/sequenceCode
			url:'${ctx}/system/sequenceCode/isNewCode/'+p+'/'+shipNoOld,
			success:function(data){
			}
		});
	}
}
function getOutShip(){
	$('#wmShipNo').combogrid({    
	    panelWidth:450,    
	    method: "get",
	    idField:'shipNo',    
	    textField:'noAndName',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
		required : true,
	    url:'${ctx}/logistic/shipTrace/outerShipTrace', 
	    columns:[[
				{field:'shipNo',title:'船编号',width:20},
				{field:'shipName',title:'船中文名',width:20},
				{field:'shipNameE',title:'船英文名',width:20},
				{field:'loadPortView',title:'装港港口',width:20,
// 					formatter: function(value,row,index){
// 						var val;
// 						if(value!=''&&value!=null){
// 							$.ajax({
// 								type:'GET',
// 								async: false,
// 								url:"${ctx}/baseInfo/baseUtil/getPortNameByCode/"+value,
// 								success: function(data){
// 									if(data != null){
// 										val = data;
// 									} else {
// 										val = '';
// 									}
// 								}
// 							});
// 							return val;
// 						}else{
// 							return '';
// 						}
// 					}
				}
	 		]]
	});
}
</script>
</body>
</html>