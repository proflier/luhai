<%@page import="com.cbmie.lh.financial.entity.PaymentConfirm"%>
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
		<form id="mainform"  action="${ctx}/financial/paymentConfirm/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '付款确认单', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>付款信息</legend>
				<table id="tableConfirm" width="97%" class="tableClass" >
					<tr>
						<td colspan="6" align="center" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">付款申请单</td>
					</tr>
					<tr>
						<td colspan="3" align="left" style="font-weight: bold;border: none;">
							<input id="id" name="id" type="hidden"  value="${paymentConfirm.id }" />
							<input id="interContractNo" name="interContractNo" type="hidden"  value="${paymentConfirm.interContractNo }" />
							<input id="goodsClasses" name="goodsClasses" type="hidden"  value="${paymentConfirm.goodsClasses }"/>
						</td>
						<td  colspan="3" align="right" style="font-weight: bold;border: none;">
							付款单号:${paymentConfirm.confirmNo }
						</td>
					</tr>
					<tr >
					  	<td rowspan="3" >收款单位</td>
					  	<th >名称</th>
					  	<td colspan="3" id="receiveUnitName">
					 	</td>
					  	<td  rowspan="7" align="center"  >付款说明:<br/>
					  		<textarea rows="8" cols="50" id="remarks" readonly="readonly" class="easyui-validatebox" name="remarks" data-options="validType:['length[0,216]'],">${paymentConfirm.remarks }</textarea>
					  	</td>
					 </tr>
					 <tr >
						 <th >银行</th>
						 <td colspan="3" id="remitBank" >
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
						 <td width="16.7%" id="currency">
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
						 <td id="payContent">
						 </td>
						 <th >付款方式</th>
						 <td >
						 	<mytag:combobox name="payType" value="${paymentConfirm.payType}" type="dict" parameters="paymentMethod"/>
						 </td>
					 </tr>
					 <tr >
				 		<th >付款单位</th>
					  	<td colspan="3" id="payUnit">
					 	 </td>
					 </tr>
					 <tr>
						<td align="right" style="border: none;" height="40px;" width="15%">经办人:</td>
						<td align="left" style="border: none;">${paymentConfirm.createrName }</td>
						<td align="right" style="border: none;">经办部门:</td>
						<td align="left"  style="border: none;">${paymentConfirm.createrDept }</td>
						<td align="right" style="border: none;">确认日期:</td>
						<td align="left" style="border: none;">
							<fmt:formatDate value="${paymentConfirm.confirmDate }" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>费用分摊</legend>
					<table id="dgChild"  width="97%" class="tableClass" ></table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<legend>附件信息</legend>
				<input id="accParentEntity" type="hidden"  value="<%=PaymentConfirm.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${paymentConfirm.id}" />
				<div id="dgAcc"  class="tableClass"></div>
			</div>
		</div>	
<%-- 		<c:if test="${not empty paymentConfirm.processInstanceId}"> --%>
<%-- 			<act:histoicFlow procInsId="${paymentConfirm.processInstanceId }" /> --%>
<%-- 		</c:if> --%>
		</form>
		
<script type="text/javascript">
	$(function() {
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    }
		});
		
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data);
				$.easyui.loaded();
			} 
		});
		
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
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				extEditing:false,
			    columns:[[    
					{field:'id',title:'id',hidden:true},  
					{field:'code',title:'编码',sortable:true,width:800},
					{field:'shareAmount',title:'分摊金额',sortable:true,width:800}
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
					sortable : true
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


		if('${paymentConfirm.receiveUnitName }'!=''){
			$.ajax({
				url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${paymentConfirm.receiveUnitName }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#receiveUnitName').text(data);
				}
			});
		}
		if('${paymentConfirm.payUnit }'!=''){
			$.ajax({
				url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${paymentConfirm.payUnit }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#payUnit').text(data);
				}
			});
		}

		if('${paymentConfirm.remitBank }'!=''){
			$.ajax({
				url : '${ctx}/baseInfo/baseUtil/getBankInfoName/${paymentConfirm.remitBank }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#remitBank').text(data);
				}
			});
		}

		if('${paymentConfirm.payContent }'!=''){
			$.ajax({
				url : '${ctx}/system/dictUtil/getDictNameByCode/paymentContent/${paymentConfirm.payContent }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#payContent').html(data);
				}
			});
		}

		if('${paymentConfirm.currency }'!=''){
			$.ajax({
				url : '${ctx}/system/dictUtil/getDictNameByCode/currency/${paymentConfirm.currency }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#currency').html(data);
				}
			});
		}

		
	});
</script>

</body>
</html>