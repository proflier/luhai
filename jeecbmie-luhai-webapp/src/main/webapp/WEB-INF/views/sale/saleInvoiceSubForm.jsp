<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="subMainform" action="${ctx}/sale/saleInvoiceSub/${actionSub}" method="post">
	<div id="subMainDiv">
			<fieldset class="fieldsetClass" >
				<legend>发票信息</legend>
				<table width="98%" class="tableClass">
				 <%-- <tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>销售合同号</th>
					<td colspan="3" >
					
					<input name="saleContractNo"  id="saleContractNo" value="${saleInvoiceSub.saleContractNo}" readOnly class="easyui-validatebox" type="text" data-options="required:true" 
						 />
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSaleContractList()">销售合同列表</a>
					</td>
				</tr> --%>
				<tr>
					<th>开票日期</th>
					<td>
					<input type="hidden" name="id" id="saleInvoiceSubid" value="${saleInvoiceSub.id}" />
					<input type="hidden" id="saleInvoiceId" name="saleInvoiceId" value="${saleInvoiceSub.saleInvoiceId }"/>
					<input name="billDate" type="text"  value="<fmt:formatDate value="${saleInvoiceSub.billDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>	
					</td>
					<th>开票数量</th>
					<td><input name="billQuantity"  id="billQuantity"  type="text" value="${saleInvoiceSub.billQuantity}" class="easyui-numberbox" data-options="precision:2,onChange:count_sub,required:true"/>				
					</td>			
				</tr>
				<tr>
					<th>开票金额</th>
					<td><input id="billMoney" name="billMoney" type="text" value="${saleInvoiceSub.billMoney}" class="easyui-numberbox"  data-options="precision:2,onChange:count_sub,required:true"/>				
					</td>
					<th>开票单价</th>
					<td>
						<input name="billPrice"  id="billPrice"  type="text" value="${saleInvoiceSub.billPrice}" class="easyui-numberbox" data-options="precision:9" readonly="readonly"/>	
					</td>
				</tr>
				</table>
			</fieldset>
	</div>
	</form>
	
</div>
<script type="text/javascript">
function count_sub(){
	var billQuantity = $('#billQuantity').numberbox('getValue');
	var billMoney = $('#billMoney').numberbox('getValue');
	var amount = 0;
	amount = billMoney/(billQuantity?billQuantity:1);
	$("#billPrice").numberbox('setValue',amount);
}
$(function(){
	$('#subMainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		sale_invoice_sub.goods_form.panel('close');
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    	}  
     		sale_invoice_sub.reload();
	    }
	});
});
//弹窗加载采购合同列表
/* function toSaleContractList(){
	var customer = $('#mainform #customerName').combobox('getValue');
	purchase=$("#dlgSaleContract").dialog({   
	    title: '销售合同',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/sale/saleInvoiceSub/toSaleContractList/'+customer,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				setSaleContractNo();
				purchase.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				purchase.panel('close');
			}
		}]
	});
} */

</script>
</body>
</html>

