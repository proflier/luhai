<%@page import="com.cbmie.lh.stock.entity.InStock"%>
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
						<th width="20%">入库编号</th>
						<td width="30%">
						<input id="inStockId" name="inStockId" type="text" value="${inStock.inStockId }"  readonly="readonly" class="easyui-validatebox" data-options="required:true" />
						<input type="hidden" id="id" name="id" value="${inStock.id }" />
						<input type="hidden" id="relLoginNames" name="relLoginNames" value="${inStock.relLoginNames }"/>
						<input type="hidden" id="selectBill" name="selectBill" value="" />
						</td>
						<th width="20%">入库日期</th>
						<td width="30%">
							<input id="inStockDate" name="inStockDate" type="text" value="<fmt:formatDate value="${inStock.inStockDate }"/>" 
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						
					</tr>
					<tr>
						<th >提单号</th>
						<td >
							<input id="billNo" name="billNo" type="text" value="${inStock.billNo }" readonly="readonly" onfocus="loadBillId()" class="easyui-validatebox" data-options="prompt: '请先选择提单号',required:true"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadBillId()">选择提单号</a>
						</td>
						<th>提单数量</th>
						<td>
							<input id="numbers" name="numbers" type="text" value="${inStock.numbers }" readonly="readonly" class="easyui-numberbox" data-options="precision:'3'" />
						</td>
					</tr>
					<tr>
						<th >船名</th>
						<input id="shipNo" name="shipNo" type="hidden" readonly="readonly" value="${inStock.shipNo }"  class="easyui-validatebox" />
						<input id="shipName" name="shipName" type="hidden" readonly="readonly" value="${inStock.shipName }"  class="easyui-validatebox" />
						<input id="shipNoName" type="hidden"  value="${inStock.shipNo }${inStock.shipName }"  class="easyui-validatebox" data-options="prompt: '自动加载'"/>
						<td colspan="3" id="shipNoView">
						</td>
					</tr>
					<tr>	
						<th  >入库类型</th>
						<td colspan="3">
							<input id="inStockType" name="inStockType" type="text" value="${inStock.inStockType }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						
					</tr>
<!-- 					<tr> -->
<!-- 						<th >质检编码</th> -->
<!-- 						<td colspan="3"> -->
<%-- 							<input id="inspectionNo" name="inspectionNo" type="text" value="${inStock.inspectionNo }" readonly="readonly" onfocus="loadInspectionNo()" class="easyui-validatebox" data-options="prompt: '请先选择质检编码',required:true"/> --%>
<!-- 							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadInspectionNo()">加载质检编码</a> -->
<!-- 						</td>					 -->
<!-- 					</tr> -->
					<tr>
						<th>仓库验收人</th>
						<td>
							<input id="warehouseAccept" name="warehouseAccept" type="text" value="${inStock.warehouseAccept }"  class="easyui-validatebox" />
						</td>
						<th >制单日期</th>
						<td >
							<input id="createStockDate" name="createStockDate" type="text" value="<fmt:formatDate value="${inStock.createStockDate }"/>" 
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>确认人</th>
						<td>
							${inStock.determineName }
						</td>
						<th>确认日期</th>
						<td>
							<fmt:formatDate value="${inStock.determineTime }" />
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
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ inStock.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${inStock.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '入库商品', iconCls: false, refreshable: false">
				<input type="hidden" name="inStockGoodsJson" id="inStockGoodsJson"/>
				<%@ include file="/WEB-INF/views/stock/inStockGoodsList.jsp"%> 
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InStock.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${inStock.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
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
				successTipNew(data,dg);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#inStockId').val(data.currnetSequence);
		    	}
		    	var selectBill = $("#selectBill").val();
		    	if(selectBill == '1'&&data.returnFlag=='success'){
			    	saveGoods();
		    	}
		    	$("#selectBill").val('');
				$.easyui.loaded();
			} 
		});
		
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
		
		//入库类型
		$('#inStockType').combobox({
			panelHeight : 'auto',
			required:true,
			url : '${ctx}/system/dictUtil/getDictByCode/inStockType',
			valueField : 'code',
			textField : 'name'
		});
		
		if('${inStock.shipNo }'!=''){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${inStock.shipNo }',
				success: function(data){
					if(data != null){
						$('#shipNoView').html(data);
					} 
				}
			});
		}
		
	});
	
	function loadBillId(){
		dlgBillId=$("#dlgBillId").dialog({
		  	width: 1000,    
		    height: 400, 
		    title: '选择到单',
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
	
	//质检选择
	function loadInspectionNo(){
		dlgInspectionNo=$("#dlgInspectionNo").dialog({
		  	width: 1000,    
		    height: 400, 
		    title: '选择质检',
		    href:'${ctx}/stock/inStock/loadInspectionNo/',
		    modal:true,
		    closable:true,
		    style:{borderWidth:0},
		    buttons:[{
				text:'确认',iconCls:'icon-ok',
				handler:function(){
					initInspectionNo();
					dlgInspectionNo.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlgInspectionNo.panel('close');
				}
			}]
		});
		
	}
	
	//到单加载
	function initBillId(){
		var rowbill = bill_dg.datagrid('getSelected');
		if(rowIsNull(rowbill)) return;
		var parentId = $('#id').val();
		$('#billNo').val(rowbill.billNo);
		$('#numbers').numberbox('setValue',rowbill.numbers);
		$('#shipNo').val(rowbill.shipNo);
		$('#shipName').val(rowbill.shipName);
		$('#shipNoName').val(rowbill.shipNo+rowbill.shipName);
		$('#relLoginNames').val(rowbill.relLoginNames);
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/logistic/shipTrace/shipNameShow/"+rowbill.shipNo,
			success: function(data){
				if(data != null){
					$('#shipNoView').html(data);
				} 
			}
		});
		//设置选择到放货
		$('#selectBill').val("1");
		
	}
	
	
	//质检加载
	function initInspectionNo(){
		var row = inspection_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$('#inspectionNo').val(row.inspectionNo);
	}
	
	function saveGoods(){
		var parentId = $("#id").val();
		var billNo = $("#billNo").val();
		$.ajax({
			type:'post',
			url:"${ctx}/stock/inStockGoods/saveGoods/"+billNo+"/"+parentId,
			success: function(data){
				var idValue = $('#id').val();
				if(idValue!=""){
					dgInStockGoods.datagrid({
				        url :  '${ctx}/stock/inStockGoods/getInstockGodds/'+idValue
				    });
				}
				dgInStockGoods.datagrid('clearSelections');
				dgInStockGoods.datagrid('reload');
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
		$('#numbers').numberbox('setValue',mainQuantity.toFixed(3));
		var mainId = $("#id").val();
		mainQuantity = mainQuantity.toFixed(3);
		if(mainId!=""){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/stock/inStock/saveOnChangeGoods/"+mainId+"/"+mainQuantity,
				success: function(data){
				}
			});
		}
		$('#dg').datagrid('reload');
	}
	
</script>
</body>
</html>