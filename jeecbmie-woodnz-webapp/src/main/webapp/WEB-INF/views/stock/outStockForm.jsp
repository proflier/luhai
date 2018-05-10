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
		<form id="mainform"  action="${ctx}/stock/outStock/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '出库信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>出库单信息</legend>
				<a href="javascript:void(0)" id="button1" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSendGoods()">选择下游交单或放货信息</a>				
				<table width="98%" class="tableClass">
					<tr>
						<input type="hidden" name="id" id="id" value="${outStock.id}" />
						<input type="hidden" name="deliveryId" id="deliveryId" value="${outStock.deliveryId}" />
						<input type="hidden" name="downBillsId" id="downBillsId" value="${outStock.downBillsId}" />
						<th><font style="vertical-align: middle; color: red"></font>出库单号</th>
						<td>
							<input id="outboundNo" name="outboundNo" class="easyui-validatebox" type="text" value="${outStock.outboundNo}"
								data-options="required:true"/>	
						</td>
						<th>提货单位</th>
						<td>
							<input id="goodsUnit" name="goodsUnit" type="text" value="${outStock.goodsUnit }" 
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>货款</th>
						<td>
							<input id="payAmount" name="payAmount" type="text" readonly="true" value="${outStock.payAmount }" class="easyui-numberbox"
							data-options="required:true,precision:2"  />
						</td>
						<th>币种</th>
						<td>
							<input id="currency" name="currency" type="text" value="${outStock.currency }" 
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>实发数量</th>
						<td>
							<input id="realSendNum" name="realSendNum" type="text" value="${outStock.realSendNum}"
							  readonly="true" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th>数量单位</th>
						<td>
							<input id="unit" name="unit" type="text" value="${outStock.unit}"/>
						</td>
					</tr>
					<tr>
						<th>客户签收日期</th>
						<td>
							<input id="billDate" name="billDate" type="text"  value="<fmt:formatDate value="${outStock.billDate}" />" 
								class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>	
						</td>
						<th></th>
						<td>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>
							<input id="createrName" name="createrName" type="text" value="${outStock.createrName }" 
							 readonly="true" class="easyui-validatebox"/>
							 <input  name="createrNo" type="hidden" value="${outStock.createrNo }"/>
						</td>
						<th>登记部门</th>
						<td>
							<input id="createrDept" name="createrDept" type="text"  value="${outStock.createrDept }" 
							class="easyui-validatebox" readonly="true" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>出库明细</legend>
					<input type="hidden" name="outStackSubJson" id="outStackSubJson"/>
					<%@ include file="/WEB-INF/views/stock/outStockSubForm.jsp"%>
				</fieldset>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
// 		$('#button1').linkbutton('disable');
		
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
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if(!$('#mainform').form('validate')){
		    		$("#mainDiv").tabs("select", tabIndex);
		    	}else{
		    		tabIndex = index;
		    	}
		    }
		});
		
		//我司单位和客户列表
		$('#goodsUnit').combobox({
			panelHeight : 'auto',
			required : true,
			method:'GET',
			url : '${ctx}/system/downMenu/jsonBaseInfo?filter_LIKES_customerType=1or3',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//币种列表
		$('#currency').combobox({
			panelHeight:'auto',
			url:'${ctx}/system/downMenu/getDictByCode/currency',
		    valueField:'id',
		    textField:'name', 
		    required : true,
		    disabled:true
		});
		
		//数量单位列表
		$('#unit').combobox({
			panelHeight:'auto',
			method: "get",
			url:'${ctx}/system/downMenu/getDictByCode/sldw',
		    valueField:'id',
		    textField:'name', 
		    required : true,
		});
	});
	
	
</script>
</body>
</html>
