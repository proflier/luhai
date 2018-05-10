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
		<form id="mainform"  action="${ctx}/stock/inStock/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '入库信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>货物来源</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">提单号</th>
						<td width="30%">
							<input id="billId" name="billId" type="text" value="${inStock.billId }" readonly="readonly" class="easyui-validatebox" data-options="prompt: '请先选择提单号'"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadBillId()">选择提单号</a>
						</td>
						<th width="20%">入库编号</th>
						<td width="30%">
						<input id="inStockId" name="inStockId" type="text" value="${inStock.inStockId }" class="easyui-validatebox" data-options="required:true" />
						<input type="hidden" id="id"name="id" value="${inStock.id }" />
						</td>
					</tr>
					<tr>
						<th width="20%">入库日期</th>
						<td >
							<input id="inStockDate" name="inStockDate" type="text" value="<fmt:formatDate value="${inStock.inStockDate }"/>" 
								datefmt="yyyy-MM-dd" class="easyui-my97" data-options="required:true"/>
						</td>
						<th width="20%">收货仓库</th>
						<td >
							<input id="receiveWarehouse" name="receiveWarehouse" type="text" value="${inStock.receiveWarehouse }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>币种</th>
						<td>
						<input id="currency" name="currency" type="text" value="${inStock.currency}" />	
						</td>
						<th></th>
						<td></td>
					</tr>
								
					<tr>
						<th>公司抬头</th>
						<td>
							<input id="companyName" name="companyName" type="text" value="${inStock.companyName }" class="easyui-validatebox"/>
						</td>
						<th>交货部门</th>
						<td>
							<input id="payDept" name="payDept" type="text" value="${inStock.payDept }" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<td>
							<input id="shipName" name="shipName" type="text" value="${inStock.shipName }"  class="easyui-validatebox" />
						</td>
						<th>航次</th>
						<td>
							<input id="voyage" name="voyage" type="text" value="${inStock.voyage }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>锁定销售单价（元）</th>
						<td>
							<input id="lockSellingPrice" name="lockSellingPrice" type="text" value="${inStock.lockSellingPrice }"  class="easyui-validatebox" />
						</td>
						<th>锁定销售合同</th>
						<td>
							<input id="lockContract" name="lockContract" type="text" value="${inStock.lockContract }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>是否代拆</th>
						<td>
							<input id="isOpen" name="isOpen" type="text" value="${inStock.isOpen }"  class="easyui-validatebox" />
						</td>
						<th>入库类型</th>
						<td>
							<input id="inStockType" name="inStockType" type="text" value="${inStock.inStockType }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th >制单日期</th>
						<td >
							<input id="createStockDate" name="createStockDate" type="text" value="<fmt:formatDate value="${inStock.createStockDate }"/>" 
								datefmt="yyyy-MM-dd" class="easyui-my97" data-options="required:true"/>
						</td>
						<th>仓库验收人</th>
						<td>
							<input id="warehouseAccept" name="warehouseAccept" type="text" value="${inStock.warehouseAccept }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>确认人</th>
						<td>
							<input id="determineName" name="determineName" type="text" value="${inStock.determineName }"  class="easyui-validatebox" />
						</td>
						<th>确认日期</th>
						<td>
							<input id="determineTime" name="determineTime"   type="text" value="<fmt:formatDate value="${inStock.determineTime }" />" datefmt="yyyy-MM-dd" 
							class="easyui-my97" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty inStock.createrName ? sessionScope.user.name : inStock.createrName }</td>
						<th  >登记部门</th>
						<td>${empty inStock.createrDept ? sessionScope.user.organization.orgName : inStock.createrDept }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '入库商品', iconCls: false, refreshable: false">
				<input type="hidden" name="inStockGoodsJson" id="inStockGoodsJson"/>
				<%@ include file="/WEB-INF/views/stock/inStockGoodsForm.jsp"%> 
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
// 				alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
		
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
		
		//公司抬头
		$('#companyName').combobox({
			required : true,
			method: "get",
			url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//是否代拆
		$('#isOpen').combobox({
			panelHeight : 'auto',
			url : '${ctx}/system/downMenu/getDictByCode/YESNO',
			valueField : 'id',
			textField : 'name'
		});
		
		//入库类型
		$('#inStockType').combobox({
			panelHeight : 'auto',
			required:true,
			url : '${ctx}/system/downMenu/getDictByCode/inStockType',
			valueField : 'id',
			textField : 'name'
		});
		
		//收货仓库 
		$('#receiveWarehouse').combobox({
			required:true,
			method: "get",
			url : '${ctx}/system/downMenu/jsonCK',
			valueField : 'id',
			textField : 'companyName',
		});
		
		//公司抬头
		$('#companyName').combobox({
			method: "get",
			url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//币种列表
		$('#currency').combobox({
			panelHeight:'auto',
			url:'${ctx}/system/downMenu/getDictByCode/currency',
		    valueField:'id',
		    textField:'name', 
		    required : true
		});
		
		//锁定销售合同
		$('#lockContract').combobox({
			method: "get",
		 	url:'${ctx}/sale/saleContract/json?filter_EQS_state=生效', 
			valueField : 'id',
			textField : 'contractNo',
			loadFilter:function(data){
				return data.rows;
			},
			onLoadSuccess: function () { 
				if("create"=="${action}"){
					 $("#inStockId").attr("disabled",true);
					 $("#inStockDate").combo('disable');
					 $("#receiveWarehouse").combobox('disable');
					 $("#companyName").combobox('disable');
					 $("#payDept").attr("disabled",true);
					 $("#shipName").attr("disabled",true);
					 $("#voyage").attr("disabled",true);
					 $("#lockSellingPrice").attr("disabled",true);
					 $("#lockContract").combobox('disable');
					 $("#isOpen").combobox('disable');
					 $("#inStockType").combobox('disable');
					 $("#createStockDate").combo('disable');
					 $("#warehouseAccept").attr("disabled",true);
					 $("#determineName").attr("disabled",true);
					 $("#determineTime").combo('disable');
					 $("#currency").combo('disable');
				}
			}
		});
	});
	
	function loadBillId(){
		dlgBillId=$("#dlgBillId").dialog({
		  	width: 1000,    
		    height: 400, 
		    title: '选择进口合同',
		    href:'${ctx}/stock/inStock/loadBillId/',
		    modal:true,
		    closable:true,
		    style:{borderWidth:0},
		    buttons:[{
				text:'确认',iconCls:'icon-ok',
				handler:function(){
					initBillId();
					dlgBillId.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlgBillId.panel('close');
				}
			}]
		});
		
	}
</script>
</body>
</html>