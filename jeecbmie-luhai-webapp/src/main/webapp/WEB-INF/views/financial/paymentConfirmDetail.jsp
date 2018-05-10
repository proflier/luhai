<%@page import="com.cbmie.lh.financial.entity.PaymentConfirm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
<style>
</style>
</head>

<body>
			<div >
				<fieldset class="fieldsetClass" >
				<legend>付款信息</legend>
				<table id="tableConfirm" width="97%" class="tableClass" >
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
					  	<td colspan="3" >
					  		${mytag:getAffiBaseInfoByCode(paymentConfirm.receiveUnitName)}
					 	</td>
					  	<td  rowspan="7" align="center"  >付款说明:<br/>
					  		<textarea rows="8" cols="50" id="remarks" readonly="readonly" class="easyui-validatebox" name="remarks" data-options="validType:['length[0,216]']">${paymentConfirm.remarks }</textarea>
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
					  	<td >
					  		${mytag:getAffiBaseInfoByCode(paymentConfirm.payUnit)}
					 	 </td>
					 	 <th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(paymentConfirm.businessManager).name}
						</td>
					 </tr>
					 <tr>
						<td align="right" style="border: none;" height="40px;" width="15%">制单人:</td>
						<td align="left" style="border: none;">${paymentConfirm.createrName }</td>
						<td align="right" style="border: none;">制单部门:</td>
						<td align="left"  style="border: none;">${paymentConfirm.createrDept }</td>
						<td align="right" style="border: none;">确认日期:</td>
						<td align="left" style="border: none;">
							<fmt:formatDate value="${paymentConfirm.confirmDate }"  pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset style="width: 96%;" class="fieldsetClass" >
				<legend>费用分摊</legend>
					<table id="dgChild"  class="tableClass" ></table>
				</fieldset>
				<fieldset style="width: 96%;" class="fieldsetClass" >
				<legend>办理记录</legend>
					<table id="handler_payment" class="tableClass" data-options="nowrap:false"></table>
				</fieldset>	
			</div>
			<fieldset style="width: 96%;" class="fieldsetClass" >
				<legend>附件信息</legend>
				<input id="accParentEntity" type="hidden"  value="<%=PaymentConfirm.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${paymentConfirm.id}" />
				<div id="dgAcc"  class="tableClass"></div>
			</fieldset>		
<script type="text/javascript">
var dgChild;
var idValue;
initStreamContainer();
function initStreamContainer(value){
	//进行编辑时使用方法
	var urlValue;
	idValue = $('#id').val();
	if(idValue!=null&&idValue!=""){
		urlValue =  '${ctx}/financial/paymentConfirmChild/getChilds/'+idValue;
	}else{
		urlValue="";
	}
	dgChild=$('#dgChild').datagrid({  
		method: "get",
		url:urlValue,
	  	fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : true,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[    
				{field:'id',title:'id',hidden:true},
				{field:'feeCategory',title:'费用类别',sortable:true,width:200,
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
				{field:'paymentType',title:'分摊',sortable:true,width:200,
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
				{field:'code',title:'摘要',sortable:true,width:200,
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
				{field:'shareAmount',title:'分摊金额',sortable:true,width:200}
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tbGoods'
	});
}
if('${paymentConfirm.processInstanceId}'!=""&&'${paymentConfirm.processInstanceId}'!=null){
	$('#handler_payment').datagrid({
		method : "get",
		url : '${ctx}/workflow/trace/list/${paymentConfirm.processInstanceId}',
		fit : false,
		fitColumns : true,
		border : true,
		idField : 'id',
		scrollbarSize : 0,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		columns : [ [  {
			field : 'name',
			title : '审批节点',
			sortable : true,
			width : 100
		}, {
			field : 'assignee',
			title : '处理人 ',
			width : 100
		},{
			field : 'passUsers',
			title : '传阅人 ',
			width : 100,
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
			width : 180,
			sortable : true,
		}, {
			field : 'comments',
			title : '审批意见',
			sortable : true,
			width : 300
		} ] ],
		enableHeaderClickMenu : false,
		enableHeaderContextMenu : false,
		enableRowContextMenu : false,
		remoteSort : false
	});
}

/**附件信息**/
var urlValue = '${ctx}/accessory/json?filter_EQS_accParentId=' + $('#accParentId').val()
+ '&filter_EQS_accParentEntity=' + $('#accParentEntity').val();
var dgAcc;
$(function() {
dgAcc = $('#dgAcc')
		.datagrid(
				{
					method : "get",
					fit : false,
					fitColumns : true,
					border : false,
					striped : true,
					singleSelect : true,
					scrollbarSize : 0,
					url : urlValue,
					idField : 'accId',
					columns : [ [
							{
								field : 'accRealName',
								title : '附件名称',
								sortable : true,
								width : 40
							},
							{
								field : 'accAuthor',
								title : '上传人',
								sortable : true,
								width : 10,
								formatter: function(value,row,index){
									var name = '';
									if(value!=null && value!=""){
										$.ajax({
											url : '${ctx}/system/user/getUserNameByLogin/'+value ,
											type : 'get',
											cache : false,
											async: false,
											success : function(data) {
												name = data;
											}
										});
									}
									return name;
								}
							},
							{
								field : 'accId',
								title : '操作',
								sortable : true,
								width : 20,
								formatter : function(value, row, index) {
									var str = "";
									str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
											+ value + ");'>下载</a>"
									return str;
								}
							} ] ]
				})
});


//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}

</script>

</body>
</html>