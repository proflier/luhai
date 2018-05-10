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
		<form id="mainform"  action="${ctx}/stock/outStock/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<input type="hidden" id="id" name="id" value="${outStock.id }" />
			<input type="hidden" id="relLoginNames" name="relLoginNames" value="${outStock.relLoginNames }"/>
			<input type="hidden" id="selectSaleDelivery" name="selectSaleDelivery" value="" />
			<input type="hidden" id="param" name="param" value="EQL_outStockId" />
			<div data-options="title: '基本信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>出库单</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >出库单编号</th>
						<td >
							<input id="outStockNo" name="outStockNo" type="text" value="${outStock.outStockNo }" readonly="readonly" class="easyui-validatebox" data-options="required:true" />
						</td>
						<th>配煤</th>
						<td>
							<mytag:combobox name="coalMix" value="${outStock.coalMix}" type="dict" parameters="YESNO"/>
						</td>
						
					</tr>
					<tr>
						<th >放货单号</th>
						<td >
							<input id="deliveryNo" name="deliveryNo" type="text" value="${outStock.deliveryNo }" class="easyui-validatebox" readonly="readonly" onfocus="loadDelivery()" data-options="required:true" />
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadDelivery()">选择放货编号</a>
						</td>
						<th >收货单位</th>
						<td >
							<mytag:combobox name="receiptCode" value="${outStock.receiptCode}" type="customer" parameters="10230003"/>
<%-- 							<mytag:combobox name="receiptCode" value="${outStock.receiptCode}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/> --%>
						</td>
					</tr>
					<!-- 20170411去除 -->
<!-- 					<tr> -->
<!-- 						<th width="20%">货款</th> -->
<!-- 						<td width="30%"> -->
<%-- 							<input id="goodsMoney" name="goodsMoney" type="text" value="${outStock.goodsMoney }"  class="easyui-numberbox"  data-options="required:true,precision:'2'"/> --%>
<!-- 						</td> -->
<!-- 						<th  width="20%">币种</th> -->
<!-- 						<td width="30%"> -->
<%-- 							<mytag:combobox name="currency" value="${outStock.currency}" type="dict" parameters="currency"/> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<th>实发数量</th>
						<td>
							<input id="realQuantity" name="realQuantity" type="text" value="${outStock.realQuantity }"  readonly="readonly" class="easyui-numberbox"  data-options="required:true,precision:'3'" />
						</td>
						<th>数量单位</th>
						<td>
							<input id="unitsMain" name="unitsMain" type="text" value="${outStock.unitsMain }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th >出库日期</th>
						<td >
							<input id="outStockDate" name="outStockDate" type="text" value="<fmt:formatDate value="${outStock.outStockDate }"/>" 
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th>客户签收日期</th>
						<td >
							<input id="receiptDate" name="receiptDate"   type="text" value="<fmt:formatDate value="${outStock.receiptDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<th >码头日期</th>
						<td colspan="3">
							<input id="warehouseDate" name="warehouseDate" type="text" value="<fmt:formatDate value="${outStock.warehouseDate }"/>" 
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="2" cols="45" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,46]']">${mytag:unescapeHtml(outStock.comment)}</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>出库信息</legend>
					<%@ include file="/WEB-INF/views/stock/outStockGoodsList.jsp"%> 
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty outStock.createrName ? sessionScope.user.name : outStock.createrName }</td>
						<th  >登记部门</th>
						<td>${empty outStock.createrDept ? sessionScope.user.organization.orgName : outStock.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ outStock.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${outStock.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
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
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dataGrid);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#outStockNo').val(data.currnetSequence);
		    	}
		    	var selectSaleDelivery = $("#selectSaleDelivery").val();
		    	if(selectSaleDelivery == '1'){
			    	saveGoods();
		    	}
		    	$("#selectSaleDelivery").val('');
				goodsWindow.listGoods();
				$.easyui.loaded();
			} 
		});
		
		//子表获取list需要参数
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
		
		
		//数量单位
		$('#unitsMain').combobox({
	 		panelHeight:'auto',
			method: "get",
			url:'${ctx}/system/dictUtil/getDictByCode/sldw',
		    valueField:'code',
		    textField:'name', 
		    required : true,
		    onLoadSuccess:function(){
		    	$('#unitsMain').combobox('setValue',10580003);
		    }
		});
	});
	
	function loadDelivery(){
		dlgBillId=$("#dlgBillId").dialog({
		  	width: 1000,    
		    height: 420, 
		    title: '选择放货单号',
		    href:'${ctx}/stock/outStock/loadDelivery/',
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
	
	//保存
	function initBillId(){
		var row = delivery_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		var parentId = $('#id').val();
		$('#deliveryNo').val(row.deliveryReleaseNo);
		$('#receiptCode').combobox("setValue",row.seller);
		$('#relLoginNames').val(row.relLoginNames);
		//设置选择到放货
		$('#selectSaleDelivery').val("1");
	}
	
	$(function(){
		//var action = '${action}';
		if('${action}' == 'view') {
			$("#tbGoods").hide();
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			//将数字框改成只读
			$("#mainform").find('.easyui-numberbox').numberbox({
			    disabled:true
			});
			//处理日期控件
			$("#mainform").find(".easyui-my97").each(function(index,element){
				$(element).attr("readonly",true);
				$(element).removeClass("easyui-my97");
				$(element).addClass("easyui-validatebox");
			});
		}
	});
	
	function saveGoods(){
		
		var parentId = $("#id").val();
		var deliveryNo = $("#deliveryNo").val();
		$.ajax({
			type:'post',
			url:"${ctx}/stock/outStock/saveGoods/"+deliveryNo+"/"+parentId,
			success: function(data){
				goodsWindow.dgGoods.datagrid('reload');
			}
		});
	}
	
	function initMainForm(data){
		var mainQuantity =0.000;
		var array = [];
		if(data.rows.length>0){
			for(var i = 0; i < data.rows.length; i++){
			    var map = data.rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='quantity'){
				    	if(!isNaN(parseFloat(currenctValue))){
				    		mainQuantity = mainQuantity + parseFloat(currenctValue);
				    	}
				    }
			    }
			}
		}
		$('#realQuantity').numberbox('setValue',mainQuantity.toFixed(3));
		var mainId = $("#id").val();
		mainQuantity = mainQuantity.toFixed(3);
		if(mainId!=""){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/stock/outStock/saveOnChangeGoods/"+mainId+"/"+mainQuantity,
				success: function(data){
				}
			});
		}
		$('#dataGrid').datagrid('reload');
	}
	
</script>
</body>
</html>