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
	<form id="mainform" action="${ctx}/sale/invoiceSignRecord/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${invoiceSignRecord.id }" />
		
		<div id="mainDiv" class="" data-options="border:false">
			<div>
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">客户名称</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(invoiceSignRecord.customer)}
						</td>
						<th width="20%">寄票日期</th>
						<td width="30%">
							<fmt:formatDate value="${invoiceSignRecord.mailDate }" />
						</td>
					</tr>
					<tr>
						<th width="20%">签收人</th>
						<td width="30%">
							${invoiceSignRecord.signer}
						</td>
						<th>签收日期</th>
						<td>
							<fmt:formatDate value="${invoiceSignRecord.signDate }" pattern='yyyy-MM-dd' />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							${invoiceSignRecord.createrName }
						</td>
						<th  >登记部门</th>
						<td>${invoiceSignRecord.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${invoiceSignRecord.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${invoiceSignRecord.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					</table> 
				</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>详细信息</legend>
				<div>
					<table id="dgInvoiceSignRecordSub"></table>
				</div>
			</fieldset>	
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){
	if('${action}' == 'view') {
		$("#tbGoods").hide();
	}
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
     		successTipNew(data,dg);
     		sub_obj.init();
	    }
	});
});
var sub_obj = {
		init:function(){
			this.listSubs();
		},
		reload:function(){
			this.sub_list.datagrid("reload");
		},
		sub_list:{},
		listSubs:function(){
			this.sub_list = $('#dgInvoiceSignRecordSub').datagrid({
				method: "get",
				url: $('#id').val() ? '${ctx}/sale/invoiceSignRecordSub/list/'+$('#id').val() : '',
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				extEditing:false,
				showFooter:true,
			    columns:[[    
					{field:'id',title:'id',hidden:true},
					{field:'quantity',title:'数量(吨)',sortable:true,width:10},
					{field:'unitPrice',title:'单价',sortable:true,width:10},
					{field:'totalPrice',title:'价税合计(元)',sortable:true,width:10},
					{field:'invoiceNo',title:'发票号码',sortable:true,width:20},
					{field:'invoiceCopies',title:'发票份数',sortable:true,width:10},
					{field:'remarks',title:'备注',sortable:true,width:40}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		sub_form:'',
}
$(function(){
	sub_obj.init();
});
</script>
</body>
</html>