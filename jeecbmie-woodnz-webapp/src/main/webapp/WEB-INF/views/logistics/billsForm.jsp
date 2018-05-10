<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/logistics/bills/${action}" method="post">
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '到单信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass" >
				<legend>到单信息</legend>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toPurchaseList()">采购合同列表</a>
				<table width="98%" class="tableClass">
				 <tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>提单号</th>
					<td width="30%">
					<input type="hidden" name="id" id="id" value="${bills.id}" />
					<input name="billNo"  id="billNo" value="${bills.billNo}" class="easyui-validatebox" type="text" data-options="required:true" 
						 />
					</td>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>二次换单提单号</th>
					<td width="30%"><input name="twoTimeBillNo" id="twoTimeBillNo" class="easyui-validatebox"  value="${bills.twoTimeBillNo}" 
						data-options="required:false" type="text"  /></td>
				</tr>
				<tr>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>供应商</th>
					<td width="30%"><input id="supplier" name="supplier" type="text" value="${bills.supplier}"/></td>
					<th width="20%"><font style="vertical-align:middle;color:red" ></font>我司单位</th>
					<td width="30%"><input name="ourUnit" id="ourUnit" type="text"  value="${bills.ourUnit}" />
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>预计到港日期</th>
					<td>
					<input name="expectPortDate" type="text"  value="<fmt:formatDate value="${bills.expectPortDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>	
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>装运类别</th>
					<td><input name="shipmentType"  id="shipmentType"  type="text" value="${bills.shipmentType}" />				
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>正本到单日期</th>
					<td>
						<input name="trueToDate" type="text"  value="<fmt:formatDate value="${bills.trueToDate}"/>"  
						class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>	
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>币种</th>
					<td><input id="currency" name="currency" type="text" value="${bills.currency}" data-options="required:false, panelHeight:'auto'"/>				
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>免箱期天数</th>
					<td><input name="noBoxDay" type="text" value="${bills.noBoxDay}" class="easyui-numberbox" data-options="required:false"
						 />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>装船日期</th>
					<td><input name="shipDate" type="text"  value="<fmt:formatDate value="${bills.shipDate}" />" 
						class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>			
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>目的港</th>
					<td><input id="portName" name="portName" type="text" value="${bills.portName}" />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>集装箱数</th>
					<td>	
						<input id="containerNumber" name="containerNumber"  class="easyui-numberbox"   
						type="text" value="${bills.containerNumber}"  data-options="precision:'3',required:false" />		
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>地区</th>
					<td><input id="area" name="area" type="text" value="${bills.area}"  data-options="required:false"/>
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>包干费单价</th>
					<td><input name="forwarderPrice" type="text" value="${bills.forwarderPrice}" class="easyui-numberbox"   
						 data-options="precision:'2',required:false" />				
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>数量</th>
					<td><input name="numbers" id="numbers"  readonly="true"  type="text" value="${bills.numbers}" class="easyui-numberbox" 
						data-options="precision:'3',required:false"   />
						*从箱单信息计算
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>数量单位</th>
					<td>
						<input name="numberUnits" id="numberUnits" type="text" value="${bills.numberUnits}"/>
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>船名</th>
					<td><input name="shipName" type="text" value="${bills.shipName}"   class="easyui-validatebox" data-options="required:false"
							 />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>航次</th>
					<td>
						<input name="voyage" type="text" value="${bills.voyage}" class="easyui-validatebox" data-options="required:false"
							 />				
					</td>			
				</tr> 
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>船公司</th>
					<td><input id="shipCompany" name="shipCompany" type="text" value="${bills.shipCompany}"  class="easyui-validatebox" 
							data-options="required:false"  />
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>总件数/根数</th>
					<td><input name="totalP" type="text" value="${bills.totalP}" class="easyui-validatebox" data-options="required:false"
						 />				
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>发票日期</th>
					<td>
						<input name="invoiceDate" type="text" readonly="false" value="<fmt:formatDate value="${bills.invoiceDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>上游发票号</th>
					<td><input name="invoiceNo" type="text" value="${bills.invoiceNo}" class="easyui-validatebox"   data-options="required:true"
						 />				
					</td>			
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>发票金额</th>
					<td>  <input name="invoiceAmount" id="invoiceAmount" readonly="true" type="text" value="${bills.invoiceAmount}" class="easyui-numberbox" 
						data-options="precision:'2',required:false"  />
						*从箱单信息计算
					</td>
					<th><font style="vertical-align:middle;color:red" ></font>交单期</th>
					<td>
					<input name="giveBillsDate" type="text" readonly="false" value="<fmt:formatDate value="${bills.giveBillsDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>登记时间</th>
					<td>
					<input name="createDate" type="text" readonly="false" value="<fmt:formatDate value="${bills.createDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false"/>
					</td><th></th><td></td>
				</tr>
				<tr>
					<th>登记人</th>
					<td>
						 <input id="createrName" name="createrName" type="text" value="${bills.createrName }"   
						 readonly="true" class="easyui-validatebox"/>
						 <input  name="createrNo" type="hidden" value="${bills.createrNo }"/>
					</td>
					<th>登记部门</th>
					<td>
						<input id="createrDept" name="createrDept" type="text"  value="${bills.createrDept }"   
						class="easyui-validatebox" readonly="true" />
					</td>
				</tr>
				<tr>
					<th><font style="vertical-align:middle;color:red" ></font>备注</th>
					<td colspan="3" style="height:1cm"><textarea  name="remark" type="text" id="remark"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${bills.remark}</textarea></td>
				</tr>
				</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
				<legend>到单货物明细</legend>
				<input type="hidden" name="billGoodsJson" id="billGoodsJson"/>
				<%@ include file="/WEB-INF/views/logistics/billsgoodsListForm.jsp"%>
			</fieldset>
		</div>	
		<div data-options="title: '箱单信息', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/logistics/billsboxListForm.jsp"%>
		</div>	
	</div>
	</form>
</div>
<script type="text/javascript">
var purchase;
var dgGoods;
var dGoods;
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
	    		tabIndex = index;//更换tab
	    	}else{
	    		if(index != 0){
	    			parent.$.messager.alert('提示','请先保存主表信息!!!');
	    		}
	    		$("#mainDiv").tabs("select", tabIndex);//当前tab
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
     		successTipNew(data,dg);
     		dgGoods.datagrid('reload');
	    }
	});
	
	function successTipBills(data,dg,d){
		if(data=='success'){
			if(dg!=null){
				dg.datagrid('clearSelections');
				dg.datagrid('reload');
				dgGoods.datagrid('reload');
			}
			parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
			return true;
		}else{
			parent.$.messager.alert(data);
			return false;
		}  
	}
	
	//币种列表
	$('#currency').combobox({
		panelHeight:'auto',
		url:'${ctx}/system/downMenu/getDictByCode/currency',
	    valueField:'id',
	    textField:'name', 
	    required : true,
	    disabled:true
	});
	//国别地区
	$('#area').combotree({
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    panelHeight:'auto',
	    animate:false,
	    multiline:false,
	    required : true,
		panelHeight:'auto',
    	onHidePanel:function(){}
	});
	
	//港口列表
	$('#portName').combobox({
		method: "get",
		panelHeight:'auto',
		url:'${ctx}/system/downMenu/jsonGK', 
	    valueField:'id',
	    textField:'gkm', 
	    required : true,
	});
	
	//供应商下拉菜单
	$('#supplier').combobox({
		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=2',
		valueField : 'id',
		textField : 'customerName',
		disabled:true,
		required : true,
	});
	
	//我司单位列表
	$('#ourUnit').combobox({
		panelHeight:'auto',
		method: "get",
		url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
	    valueField:'id',
	    textField:'customerName', 
	    disabled:false,
	    required : true,
	});
	
	//数量单位列表
	$('#numberUnits').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/downMenu/getDictByCode/sldw',
	    valueField:'id',
	    textField:'name', 
	    required : true,
	});
	
	
	//装运类别列表
	$('#shipmentType').combobox({
		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/downMenu/getDictByCode/shipmentType',
	    valueField:'id',
	    textField:'name',
	    required : true,
	});
	
});

</script>
</body>
</html>

