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
		<form id="cliam_form"  action="${ctx}/financial/serial/claim" method="post">
				<table width="98%" class="tableClass" >
					<tr>
						<th>水单类型</th>
						<td><input id="serialCategory" name="serialCategory" type="text" value="${serial.serialCategory }" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<th>合同号</th>
						<td>
							<input type="hidden" name="id" value="${serial.id }" />
							<input type="hidden" id="relLoginNames" name="relLoginNames" value="${serial.relLoginNames }" />
							<input  id="contractNo" name="contractNo" type="text" value="${serial.contractNo }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>发票号</th>
						<td><input id="invoiceNo" name="invoiceNo" type="text" value="${serial.invoiceNo }" class="easyui-validatebox"   /></td>
					</tr>
					<tr>
						<th>认领人</th>
						<td>
							<mytag:combotree name="claimPerson" value="${serial.claimPerson}" type="userTree" disabled="true" />
						</td>
					</tr>
					<tr>
						<th>认领时间</th>
						<td>
							<input id="claimDate" name="claimDate" value="<fmt:formatDate value="${serial.claimDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"  type="text"
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th width="25%">认领人备注</th>
						<td>
							<textarea   name="comments"  class="easyui-validatebox">${serial.comments }</textarea>
						</td>
					</tr>
				</table>
		</form>
	</div>

<script type="text/javascript">

	$(function() {
		$('#cliam_form').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
// 		    	successTip(data,dg,d_claim);
		    	successTipNew(data,dg,d_claim);
				$.easyui.loaded();
			} 
		});
	});
	var serialTitle = dg.datagrid('getSelected').serialTitle;
	
	//合同号
	$('#contractNo').combogrid({    
	    panelWidth:800,
	    required:true,
	    method: "get",
	    idField:'contractNo',    
	    textField:'contractNo',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
	    url : '${ctx}/sale/saleContract/json?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1&filter_EQS_purchaser='+serialTitle,
	    columns:[[
				{field:'contractNo',title:'合同号',sortable:true,width:25},
				{field:'purchaserView',title:'买方',sortable:true,width:20},
				{field:'sellerView',title:'卖方',sortable:true,width:20},
				{field:'contractQuantity',title:'数量',sortable:true,width:15},
				{field:'contractAmount',title:'金额',sortable:true,width:15},
				{field:'settlementModeView',title:'交款方式',sortable:true,width:10}
				
	 		]],
	 		onSelect:function(rowIndex, rowData){
	 			$('#relLoginNames').val(rowData.relLoginNames)
	 		}
	});
	
	
	
	//资金类别
	$('#serialCategory').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dictUtil/getDictByCode/serialCategory',
		valueField : 'code',
		textField : 'name'
	});
	
	
	
// 	//发票号 初始化显示
// 	$('#invoiceNo').combobox({
// 		method : 'get',
// 		required : true,
// 		url:'${ctx}/logistics/downstreamBill/json?filter_EQS_confirm=1', //确认状态为确认
// 		valueField : 'invoiceNo',
// 		textField : 'invoiceNo',
// 		loadFilter : function(data){
// 			return data.rows;
// 		}
// 	});
	
// 	function selectInvoice(id){
// 		$('#invoiceNo').combobox({
// 			panelHeight : 'auto',
// 			method: "get",
// 			url : '${ctx}/baseInfo/baseUtil/getInvoiceNo/'+id,
// 			valueField : 'invoiceNo',
// 			textField : 'invoiceNo',
// 		});
// 	}
</script>
</body>
</html>