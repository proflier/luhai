<%@page import="com.cbmie.woodNZ.salesContract.entity.WoodSaleContract"%>
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
		<form id="mainform"  action="${ctx}/sale/saleContract/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售合同信息', iconCls: false, refreshable: false" >				
				<fieldset class="fieldsetClass" >
				<legend>销售信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">卖受人</th>
						<td width="30%">
							<input type="hidden"  name="id" id="id" value="${woodSaleContract.id}" />
							<input id="seller" name="seller" type="text" value="${woodSaleContract.seller }" 
							data-options="required:true" />
						</td>
						<th width="20%">买受人</th>
						<td width="30%">
							<input id="purchaser" name="purchaser" type="text" value="${woodSaleContract.purchaser }" 
							data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th>签约日期</th>
						<td>
							<input name="signDate" type="text"  value="<fmt:formatDate value="${woodSaleContract.signDate}" />" class="easyui-my97" 
								datefmt="yyyy-MM-dd" data-options="required:true" />
						</td>
						<th>签约地点</th>
						<td>
							<input id="signAddr" name="signAddr" type="text" value="${woodSaleContract.signAddr }" 
							data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th>合同编号</th>
						<td>
							<input id="contractNo" name="contractNo" type="text" class="easyui-validatebox" value="${woodSaleContract.contractNo }" 
							data-options="required:true"/>
						</td>
						<th>协议号</th>
						<td>
							<input id="dealNo" name="dealNo" type="text" value="${woodSaleContract.dealNo }"  class="easyui-validatebox"
							data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th>业务部门</th>
						<td>
							<input id="dept" name="dept" type="text" value="${woodSaleContract.dept }"  class="easyui-validatebox"
							data-options="required:false"/>
						</td>
						<th>登记日期</th>
						<td>
							<input name="registerDate" type="text"  value="<fmt:formatDate value="${woodSaleContract.registerDate}" />" 
							class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false" />
						</td>
					</tr>
					<tr>
						<th>合同制作人</th>
						<td>
							<input id="draftsman" name="draftsman" type="text" value="${woodSaleContract.draftsman }"  class="easyui-validatebox"
							data-options="required:false"/>
						</td>
						<th>合同执行人</th>
						<td>
							<input id="executer" name="executer" type="text" class="easyui-validatebox" value="${woodSaleContract.executer }" 
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>大品名</th>
						<td>
							<input id="dapimming" name="dapimming" type="text" class="easyui-validatebox" value="${woodSaleContract.dapimming }" 
							data-options="required:true"/>
						</td>
						<th>代理费</th>
						<td>
							<input id="dlf" name="dlf" type="text" value="${woodSaleContract.dlf }"  class="easyui-numberbox"
							data-options="required:false,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th>贸易性质</th>
						<td>
							<input id="tradeProperty" name="tradeProperty" type="text" value="${woodSaleContract.tradeProperty }" />
						</td>
						<th>保证金/定金</th>
						<td>
							<input id="bail" name="bail" type="text" value="${woodSaleContract.bail }"  class="easyui-numberbox"
							data-options="required:true,precision:'2'"/>
						</td>
					</tr>
					<tr>
						<th>收款止期</th>
						<td>
							<input name="gatheringDate" type="text"  value="<fmt:formatDate value="${woodSaleContract.gatheringDate}" />" 
							class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false" />
						</td>
						<th>合同金额</th>
						<td>
							<input id="totalMoney" name="totalMoney" type="text" readonly="true" class="easyui-numberbox" value="${woodSaleContract.totalMoney }" 
							data-options="required:false,precision:'2'"/>  
							*从销售子表计算
						</td>
					</tr>
					<tr>
						<th>币种</th>
						<td>
							<input id="currency" name="currency" type="text" value="${woodSaleContract.currency }" 
							data-options="required:true"/>
						</td>
						<th>汇率</th>
						<td>
							<input id="exchangeRate" name="exchangeRate" type="text" value="${woodSaleContract.exchangeRate }"  class="easyui-numberbox"
							data-options="required:false,precision:'3'" />
						</td>
					</tr>
					<tr>
						<th>销售总数量</th>
						<td>
							<input id="total" name="total" type="text" readonly="true" value="${woodSaleContract.total }"  class="easyui-numberbox"
							data-options="required:false,precision:'3'"/>
							*从销售子表计算
						</td>
						<th>数量单位</th>
						<td>
							<input id="numUnit" name="numUnit" type="text" value="${woodSaleContract.numUnit }"  />
						</td>
					</tr>
					<tr>
						<th>报关方</th>
						<td>
							<input id="takeWay" name="takeWay"  type="text" value="${woodSaleContract.takeWay }" />
						</td>
						<th>是否专案</th>
						<td>
							<input id="ifZa" name="ifZa"  type="text" value="${woodSaleContract.ifZa }" />
						</td>
					</tr>
					<tr>
						<th>类别</th>
						<td>
							<input id="salesType" name="salesType"  type="text" value="${woodSaleContract.salesType }" /> 
						</td>
						<th>提货地点</th>
						<td>
							<input id="takeAddr" name="takeAddr"  value="${woodSaleContract.takeAddr }"/>
							 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelect()"></a>
						</td>
					</tr>
					<tr>
						<th>提货止期</th>
						<td>
							<input name="takeDate" type="text"  value="<fmt:formatDate value="${woodSaleContract.takeDate}" />" 
							class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false" />
						</td>
						<th>付款方式</th>
						<td>
							<input id="payWay" name="payWay" class="easyui-combobox" type="text" value="${woodSaleContract.payWay }" />
						</td>
					</tr>
					<tr>
						<th>提货超期单价上调条款</th>
						<td>
							<input id="cqjj" name="cqjj" type="text" value="${woodSaleContract.cqjj }"  class="easyui-validatebox"
							data-options="required:false"/>
						</td>
						<th></th>
						<td>
							
						</td>
					</tr>
					<tr>
						<th>溢短（%）</th>
						<td>
							<input id="error" name="error" type="text" value="${woodSaleContract.error }" 
								class="easyui-numberbox" data-options="required:false,precision:'2',suffix:'%'"/>
						</td>
						<th>合同失效期</th>
						<td>
							<input name="disableDate" type="text"  value="<fmt:formatDate value="${woodSaleContract.disableDate}" />" 
							class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false" />
						</td>
					</tr>
					<tr>
						<th>买方联系人</th>
						<td>
							<input id="connecter" name="connecter" type="text" value="${woodSaleContract.connecter }"  class="easyui-validatebox"
							data-options="required:false"/>
						</td>
						<th>买方联系人电话</th>
						<td>
							<input id="phone" name="phone" type="text" value="${woodSaleContract.phone }"  class="easyui-validatebox"
							data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align:middle;color:red" ></font>送货地址</th>
						<td colspan="3" style="height:1cm"><textarea name="addr" type="text" id="addr" style="overflow:auto;width:49%;height:100%;">${woodSaleContract.addr}</textarea></td>
					</tr>
					<tr>
						<th><font style="vertical-align:middle;color:red" ></font>备注</th>
						<td colspan="3" style="height:1cm"><textarea  name="remark" type="text" id="remark"  class="easyui-validatebox"
						style="overflow:auto;width:49%;height:100%;">${woodSaleContract.remark}</textarea></td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>
							 <input id="createrName" name="createrName" type="text" value="${woodSaleContract.createrName }" 
							 readonly="true" class="easyui-validatebox"/>
							 <input  name="createrNo" type="hidden" value="${woodSaleContract.createrNo }"/>
						</td>
						<th>登记部门</th>
						<td>
							<input id="createrDept" name="createrDept" type="text"  value="${woodSaleContract.createrDept }" 
							class="easyui-validatebox" readonly="true" />
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '销售合同子表', iconCls: false, refreshable: false">
				<input type="hidden" name="contractSubJson" id="contractSubJson"/>
				<%@ include file="/WEB-INF/views/saleContract/saleContractSubForm.jsp"%>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=WoodSaleContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${woodSaleContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
	<div id="dlgSendGoods"></div> 
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				compute('amount');//计算销售总数量和合同总金额
		    	compute('money');
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if(title=='附件信息' && $('#accParentId').val() == ''){
		    		parent.$.messager.alert('提示','请先保存主表信息!!!');
		    		$("#mainDiv").tabs("select", tabIndex);
		    	}
		    	else{
		    		tabIndex = index;
		    	}
		    }
		});
		
		//我司单位
		$('#seller').combobox({
			panelHeight : 'auto',
			required : true,
			method:'GET',
			url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//客户
		$('#purchaser').combobox({
			panelHeight : 'auto',
			required : true,
			method:'GET',
			url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=3',
			valueField : 'id',
			textField : 'customerName'
		});
		//国别地区
		$('#signAddr').combotree({
			method:'GET',
		    url: '${ctx}/system/countryArea/json',
		    idField : 'id',
		    textFiled : 'name',
			parentField : 'pid',
		    panelHeight:'auto',
		    animate:true,
		    onHidePanel:function(){}
		});
		//资金币种
		$('#currency').combobox({
			panelHeight : 'auto',
			url : '${ctx}/system/downMenu/getDictByCode/CURRENCY',
			valueField : 'id',
			textField : 'name'
		});
		
		//付款方式 
		$('#payWay').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/skfs',
			valueField : 'id',
			textField : 'name'
		});
		
		//贸易性质 
		$('#tradeProperty').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/htlb',
			valueField : 'id',
			textField : 'name'
		});
		

		//数量单位
		$('#numUnit').combobox({
			panelHeight : 'auto',
			required : true,
			url:'${ctx}/system/downMenu/getDictByCode/sldw',
			valueField : 'id',
			textField : 'name'
		});
		
		//报关方
		$('#takeWay').combobox({
			panelHeight : 'auto',
			required : true,
			url:'${ctx}/system/downMenu/getDictByCode/bgf',
			valueField : 'id',
			textField : 'name'
		});
		
		//是否专案
		$('#ifZa').combobox({
			panelHeight : 'auto',
			required : false,
			url:'${ctx}/system/downMenu/getDictByCode/YESNO',
			valueField : 'id',
			textField : 'name'
		});
		
		//类别
		$('#salesType').combobox({
			panelHeight : 'auto',
			required : true,
			url:'${ctx}/system/downMenu/getDictByCode/xslb',
			valueField : 'id',
			textField : 'name'
		});
		
		//提货地点
		$('#takeAddr').combobox({
			method:'GET',
			url:'${ctx}/system/downMenu/getDictByCode/thdd',
		    textField : 'name',
		    multiple:true,
// 		    width:250,
		    multiline:true,
			panelHeight:'auto',
			editable:false,
		    required:true,
		    prompt: '此选项可以多选，点击X重置选择',
		    onLoadSuccess: function () { //加载完成后,设置选中第一项 
		        var val = $(this).combobox("getData"); 
		        $(this).combobox("clear");
		   		var curValue = new Array();
		   		curValue = this.value.split(',');
//	 	    		alert(curValue+"______________");
		   		for(var j=0;j<curValue.length;j++){
//		    			alert(curValue[j]+" ~~~~~~~~~");
		   		 	for(var i = 0;i<val.length;i++ ){ 
//		 	        	 alert(val[i].value+"!!!!!!!!");
			            if (val[i].value==curValue[j]) { 
			                $(this).combobox("select",curValue[j]); 
			            } 
			        } 
		   		}
		    },
		    onHidePanel:function(){}
		});
		
	});
	
	//清除选择
	function clearSelect() {
		$('#takeAddr').combobox("clear");
	}
	
	  //指定列求和
	  function compute(colName) {
		    var rows = childTB.datagrid('getRows');
		    var total = 0;
		    for (var i = 0; i < rows.length; i++) {
		      total += parseFloat(rows[i][colName]);
		    }
		    if(colName=='amount' && total >= 0){
		    	$('#total').numberbox('setValue', total.toFixed(3));//销售总数量赋值
		    }
		    if(colName=='money' && total >= 0){
		    	$('#totalMoney').numberbox('setValue', total.toFixed(2));//合同总金额赋值
		    }
	  }
</script>
</body>
</html>

