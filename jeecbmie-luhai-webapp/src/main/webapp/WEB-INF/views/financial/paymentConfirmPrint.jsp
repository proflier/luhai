<%@page import="com.cbmie.lh.financial.entity.PaymentConfirm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
<style type="text/css" media="all">
.hrLine{
      vertical-align:middle; display:inline-block;
 }
</style>
</head>

<body>
			<div id="printDiv">
				<table  class="tableClass" style="font-size: 20px;width: 950px;" >
					<tr>
						<td colspan="6" align="center" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">付款申请单</td>
					</tr>
					<tr>
						<td colspan="3" align="left" style="font-weight: bold;border: none;">
							${paymentConfirm.interContractNo }
						</td>
						<td  colspan="3" align="right" style="font-weight: bold;border: none;">
						<input id="id" name="id" type="hidden"  value="${paymentConfirm.id }" />
							付款单号:${paymentConfirm.confirmNo }
						</td>
					</tr>
					<tr >
					  	<td rowspan="3" >收款单位</td>
					  	<th >名称</th>
					  	<td colspan="3" id="">
					  	${mytag:getAffiBaseInfoByCode(paymentConfirm.receiveUnitName)}
					 	</td>
					  	<td  rowspan="7" width="20%" align="center"  >付款说明:<br/>
					  		<textarea rows="8" cols="50" id="remarks" readonly="readonly" class="easyui-validatebox" style="font-size: 16px;" name="remarks" data-options="validType:['length[0,216]']">${paymentConfirm.remarks }</textarea>
					  	</td>
					 </tr>
					 <tr >
						 <th >银行</th>
						 <td colspan="3" id="" >
						 ${mytag:getAffiBankInfoById(paymentConfirm.remitBank).bankName}			
						 </td>
					 </tr>
					  <tr >
						 <th >账号</th>
						 <td colspan="3" >
						 	${paymentConfirm.remitAccount }
						 </td>
					 </tr>
					 <tr >
						 <td rowspan='2'  width="16.7%" >付款金额</td>
						 <th width="8.3%">小写</th>
						 <td width="16.7%">
						 	<fmt:formatNumber value="${paymentConfirm.payMoneyXiao }" pattern="#.00"/> 
						 </td>
						 <th width="8.3%">币种</th>
						 <td width="16.7%" id="">
						 ${fns:getDictLabelByCode(paymentConfirm.currency,'currency','')}
						 </td>
					 </tr>
					 <tr >
				 		<th >大写</th>
					  	<td colspan="3" >
					  		${paymentConfirm.payMoneyDa }
					 	 </td>
					 </tr>
					 <tr >
						 <td rowspan='2'>付款信息</td>
						 <th >付款内容</th>
						 <td id="">
						 	${fns:getDictLabelByCode(paymentConfirm.payContent,'paymentContent','')}
						 </td>
						 <th >付款方式</th>
						 <td id="">
						 	${fns:getDictLabelByCode(paymentConfirm.payType,'paymentMethod','')}
						 </td>
					 </tr>
					 <tr >
				 		<th >付款单位</th>
					  	<td colspan="3" id="">
					  		${mytag:getAffiBaseInfoByCode(paymentConfirm.payUnit)}
					 	 </td>
					 </tr>
					 <tr>
						<td align="right" style="border: none;" height="40px;" width="15%">经办人:</td>
						<td align="left" style="border: none;">${paymentConfirm.createrName }</td>
						<td align="right" style="border: none;">经办部门:</td>
						<td align="left"  style="border: none;">${paymentConfirm.createrDept }</td>
						<td align="right" style="border: none;">确认日期:</td>
						<td align="left" style="border: none;">
							<fmt:formatDate value="${paymentConfirm.confirmDate }"  pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</table>
				<br />
						<br />
						<table id="dgChild_print"   style="font-size: 20px;width: 950px;" ></table>
						<br />
						<table id="handler_payment_print" style="font-size: 20px;width: 950px;height: 270px"></table>
			</div>
<script type="text/javascript">
var dgChild_print;
var idValue;
initStreamContainer();
function initStreamContainer(value){
	//进行编辑时使用方法
	var urlValue;
	idValue = '${paymentConfirm.id }';
	if(idValue!=null&&idValue!=""){
		urlValue =  '${ctx}/financial/paymentConfirmChild/getChilds/'+idValue;
	}else{
		urlValue="";
	}
	dgChild_print=$('#dgChild_print').datagrid({  
		method: "get",
		url:urlValue,
	  	fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : true,
		striped:true,
		idField : 'id',
// 		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[    
// 				{field:'id',title:'id',hidden:true},
				{field:'feeCategory',title:'费用类别',sortable:true,width:237.5,
					formatter: function(value,row,index){
						var val = '';
						if(value!=null && value!=""){
							$.ajax({
								url : '${ctx}/system/dictUtil/getDictNameByCode/feeCategory/'+value ,
								type : 'get',
								cache : false,
								async: false,
								success : function(data) {
									val = data;
								}
							});
						}
						return val;
					}	
				},
				{field:'paymentType',title:'分摊',sortable:true,width:237.5,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							if(value=='sale'){
								val="销售合同";
							}else if(value=='kehu'){
								val="客户";
							}else{
								val="船编号";
							}
							return val;
						}else{
							return '';
						}
					}	
				},
				{field:'code',title:'摘要',sortable:true,width:237.5,
					formatter: function(value,row,index){
						var val;
						if(value!=''&& value!=null&&row.paymentType=='ship'){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else if(value!=''&& value!=null&&row.paymentType=='sale'){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/sale/saleContract/getContractNoCustomer/"+value,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else if(value!=''&& value!=null&&row.paymentType=='kehu'){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
								success: function(data){
									if(data != null){
										val = data;
									} else {
										val = '';
									}
								}
							});
							return val;
						}else{
							return "";
						}
					}	
				},
				{field:'shareAmount',title:'分摊金额',sortable:true,width:237.5}
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tbGoods'
	});
}
if('${paymentConfirm.processInstanceId}'!=""&&'${paymentConfirm.processInstanceId}'!=null){
	$('#handler_payment_print').datagrid({
		method : "get",
		url : '${ctx}/workflow/trace/list/${paymentConfirm.processInstanceId}',
		fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : true,
		nowrap:false,
		striped:true,
		idField : 'id',
// 		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		columns : [ [  {
			field : 'name',
			title : '审批节点',
			sortable : true,
			width : 100
		}, {
			field : 'assignee',
			title : '处理人 ',
			width : 70
		},{
			field : 'passUsers',
			title : '传阅人 ',
			width : 70,
			formatter: function(value,row,index){
				if (row.passUsers.length>10){
					return row.passUsers.substr(0,10)+"...";
				} else {
					return row.passUsers;
				}
			}
		}, {
			field : 'startTime',
			title : '任务开始时间',
			width : 180,
			sortable : true
		}, {
			field : 'endTime',
			title : '任务结束时间',
			width : 180,
			sortable : true
		}, {
			field : 'distanceTimes',
			title : '耗时',
			width : 120,
			sortable : true,
		}, {
			field : 'comments',
			title : '审批意见',
			sortable : true,
			width : 230
		} ] ],
		enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	});
}




</script>

</body>
</html>