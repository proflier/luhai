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
	<div id="detailLayout" class="easyui-layout" data-options="fit:true">
		<div id="detailLayoutEast" data-options="region:'east',collapsible:true,split:true,collapsed:true,border: false,title:'关联列表'" style="width: 600px">
			<input id="innerContractNo" type="hidden" value="${lhBills.purchaseContractIds }" />
			<table id="dg_relation" ></table>
			<div id="dlg_relation"></div>
		</div>
	<div onclick="centerDetail()" data-options="region:'center'">
	<form id="mainform" action="${ctx}/logistic/bills/${action}" method="post">
	<div id="bill_mainDiv"  data-options="border:false">
		<div data-options="title: '到单信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass" >
				<legend>到单信息</legend>
				<table width="98%" class="tableClass">
				 <tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>提单号</th>
					<td width="30%" >
					<input type="hidden" name="id" id="id" value="${lhBills.id}" />
					<input type="hidden" id="purchaseContractIds" name="purchaseContractIds" value="${lhBills.purchaseContractIds }"/>
					<input type="hidden" id="purchaseContractIdsOld" name="purchaseContractIdsOld" value="${purchaseContractIdsOld }"/>
					${lhBills.billNo}
					</td>
					<th width="20%">发货单位</th>
					<td width="30%">
						${mytag:getAffiBaseInfoByCode(lhBills.sendUnit)}
					</td>
				</tr>
				<tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>供货单位</th>
					<td width="30%">
						${mytag:getAffiBaseInfoByCode(lhBills.deliveryUnit)}
					</td>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>订货单位</th>
					<td width="30%">
						${mytag:getAffiBaseInfoByCode(lhBills.orderUnit)}
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>预计到港日期</th>
					<td>
						<fmt:formatDate value="${lhBills.expectPortDate}" />	
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>装运类别</th>
					<td>
						${fns:getDictLabelByCode(lhBills.shipmentType,'shipmentType','')}
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>正本到单日期</th>
					<td>
						<fmt:formatDate value="${lhBills.trueToDate}"/>
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>币种</th>
					<td>
						${fns:getDictLabelByCode(lhBills.currency,'currency','')}
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>目的港</th>
					<td>
						${mytag:getAffiBaseInfoByCode(lhBills.portName)}
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>船公司</th>
					<td>
						${lhBills.shipCompany}
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>免堆期天数</th>
					<td>
						${lhBills.freeDays}
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>装船日期</th>
					<td>
						<fmt:formatDate value="${lhBills.shipDate}" />
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>数量</th>
					<td>
						${lhBills.numbers}
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>数量单位</th>
					<td>
						${fns:getDictLabelByCode(lhBills.numberUnits,'sldw','')}
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>船编号</th>
					<td>
						${mytag:getShipByNo(lhBills.shipNo).noAndName}
					</td>
					<th>检验机构</th>
					<td>
						${mytag:getAffiBaseInfoByCode(lhBills.checkOrg)}
					</td>
				</tr> 
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>发票日期</th>
					<td>
						<fmt:formatDate value="${lhBills.invoiceDate}" />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>上游发票号</th>
					<td>
						${lhBills.invoiceNo}
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>发票金额</th>
					<td>  
						${lhBills.invoiceAmount}
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>交单期</th>
					<td>
						<fmt:formatDate value="${lhBills.giveBillsDate}" />
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>收货单位</th>
					<td>
						${mytag:getAffiBaseInfoByCode(lhBills.receivingUnit)}
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>登记时间</th>
					<td>
						<fmt:formatDate value="${lhBills.createDate}" />
					</td>
				</tr>
				<tr>
					<th>登记人</th>
					<td>
						 ${lhBills.createrName }
						 <input  name="createrNo" type="hidden" value="${lhBills.createrNo }"/>
					</td>
					<th>登记部门</th>
					<td>
						${lhBills.createrDept }
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>检验标准</th>
					<td colspan="3" style="height:1cm">
						${lhBills.checkStandard}
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>备注</th>
					<td colspan="3" style="height:1cm">
						${lhBills.remark}
					</td>
				</tr>
				</table>
			</fieldset>
			<jsp:include page="/WEB-INF/views/relationDetail/relationDetail.jsp" >
				<jsp:param value="lhBills,saleContract,saleDelivery,outStock,saleInvoice,paymentConfirm" name="disView"/>
			</jsp:include>
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
	</div>
	</div>
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
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	});
});

$(function(){
	if('${action}' == 'view') {
		$("#tbGoods").hide();
	}
});
</script>
</body>
</html>

