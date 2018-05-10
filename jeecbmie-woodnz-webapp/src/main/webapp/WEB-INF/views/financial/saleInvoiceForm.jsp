<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/financial/saleInvoice/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售发票登记', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>销售发票登记</legend>
				<table width="98%" class="tableClass">
					<tr>
						<input type="hidden" name="id" id="id" value="${saleInvoice.id}" />
						<th><font style="vertical-align: middle; color: red;"></font>抬头</th>
						<td >
							<input id="title" name="title" class="easyui-validatebox" type="text" value="${saleInvoice.title}"
								data-options="required:true"/>	
						</td>
						<th><font style="vertical-align: middle; color: red"></font>发票日期</th>
						<td >
							<input name="invoiceDate" type="text"  value="<fmt:formatDate value="${saleInvoice.invoiceDate}" />" class="easyui-my97" 
							datefmt="yyyy-MM-dd" data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>发票号</th>
						<td >
							<input id="invoideNo" name="invoideNo" type="text" value="${saleInvoice.invoideNo }" 
							data-options="required:true"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>下游名称地址</th>
						<td >
							<input id="downAddress" name="downAddress" type="text" value="${saleInvoice.downAddress }"  class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>合同号</th>
						<td >
							<input id="contractNo" name="contractNo" type="text" value="${saleInvoice.contractNo }"  class="easyui-validatebox"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>装运港</th>
						<td >
							<input id="srcPort" name="srcPort" type="text" value="${saleInvoice.srcPort }"  class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>目的港</th>
						<td >
							<input id="destPort" name="destPort" type="text" value="${saleInvoice.destPort }"  class="easyui-validatebox"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>交易模式</th>
						<td >
							<input id="transType" name="transType" type="text" value="${saleInvoice.transType }"  class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>装货时间</th>
						<td >
							<input name="goodsDate" type="text"  value="<fmt:formatDate value="${saleInvoice.goodsDate}" />" class="easyui-my97" 
							datefmt="yyyy-MM-dd" data-options="required:false"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>提单号</th>
						<td >
							<input id="billNo" name="billNo" type="text" value="${saleInvoice.billNo }"  class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>制单人</th>
						<td>
						${saleInvoice.createrName}
						</td>
						<th>制单日期</th>
						<td>
							<fmt:formatDate value="${saleInvoice.createDate}" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass">
					<legend>明细</legend>
					<input type="hidden" name="saleInvoiceSubJson" id="saleInvoiceSubJson"/>
					<%@ include file="/WEB-INF/views/financial/saleInvoiceSubForm.jsp"%>
				</fieldset>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
				childTB.datagrid('reload');
			} 
		});
		
		function successTipNew(data,dg,d){
			var data = eval('(' + data + ')');
			if(data.returnFlag=='success'){
				if(dg!=null){
					dg.datagrid('clearSelections');
					dg.datagrid('reload');
					childTB.datagrid('reload');
				}
				if(d!=null)
					d.panel('close');
				$('#id').val(data.returnId);
				parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
				return true;
			}else if(data.returnFlag=='fail'){
				parent.$.messager.alert(data.returnMsg);
				return false;
			}  
		}
		
	});
	
	
</script>
</body>
</html>
