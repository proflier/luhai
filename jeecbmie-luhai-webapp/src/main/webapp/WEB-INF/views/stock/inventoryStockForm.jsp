<%@page import="com.cbmie.lh.stock.entity.InventoryStock"%>
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
		<form id="mainform"  action="${ctx}/stock/inventoryStock/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<input type="hidden" id="id" name="id" value="${inventoryStock.id }" />
			<input type="hidden" id="relLoginNames" name="relLoginNames" value="${inventoryStock.relLoginNames }"/>
			<input type="hidden" id="warehouseCode" name="warehouseCode" value="${inventoryStock.warehouseCode }" />
			<input type="hidden" id="stockGoodsId" name="stockGoodsId" value="${inventoryStock.stockGoodsId }" />
			<input id="goodsName" name="goodsName" type="hidden" value="${inventoryStock.goodsName }" />
			<div data-options="title: '盘库信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>货物来源</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">盘点编码</th>
						<td width="30%">
							<input id="inventoryNo" name="inventoryNo" type="text" value="${inventoryStock.inventoryNo }" readonly="readonly" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th  width="20%">仓库名称</th>
						<td>
							<input id="warehouseView"  readonly="readonly" onfocus="loadStockGodds()" type="text" class="easyui-validatebox" data-options="prompt: '选择仓库',required:true"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadStockGodds()">选择仓库</a>
						</td>
						
					</tr>
					<tr>
						<th width="20%">商品名称</th>
						<td width="30%" id="goodsNameView">
						</td>
						<th width="20%">盘库日期</th>
						<td width="30%">
							<input id="inventoryDate" name="inventoryDate"   type="text" value="<fmt:formatDate value="${inventoryStock.inventoryDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th >原始数量</th>
						<td>
							<input id="sourceQuantity" name="sourceQuantity" type="text" value="${inventoryStock.sourceQuantity }" readonly="readonly" class="easyui-numberbox" data-options="precision:'2'" />
						</td>
						<th >盘点数量</th>
						<td>
							<input id="inventoryQuantity" name="inventoryQuantity" type="text" value="${inventoryStock.inventoryQuantity }"  class="easyui-numberbox" data-options="required:true,precision:'2',prompt: '盘亏（负数）或盘盈（正数）数量'" />
						</td>
					</tr>
					<tr>
						<th >数量单位</th>
						<td>
							<input id="units" name="units" type="text" value="${inventoryStock.units }"  class="easyui-validatebox" />
						</td>
						<th>负责人</th>
						<td>
							<input id="inventoryPerson" name="inventoryPerson" type="text" value="${inventoryStock.inventoryPerson }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<input id="shipNo" name="shipNo" type="hidden" value="${inventoryStock.shipNo }" class="easyui-validatebox" />
						<input id="shipNoAndName" name="shipNoAndName" type="hidden" value="${inventoryStock.shipNoAndName }" class="easyui-validatebox" />
						<td colspan="3" id="shipNoView">
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="2" cols="90" id="summary" class="easyui-validatebox" name="summary" data-options="validType:['length[0,255]']">${inventoryStock.summary }</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty inventoryStock.createrName ? sessionScope.user.name : inventoryStock.createrName }</td>
						<th  >登记部门</th>
						<td>${empty inventoryStock.createrDept ? sessionScope.user.organization.orgName : inventoryStock.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ inventoryStock.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${inventoryStock.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InventoryStock.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${inventoryStock.id}" />
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
		    		$('#inventoryNo').val(data.currnetSequence);
		    	}
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
		
		
		
		
		//数量单位列表
		$('#units').combobox({
// 			panelHeight:'auto',
			method: "get",
			url:'${ctx}/system/dictUtil/getDictByCode/sldw',
		    valueField:'code',
		    textField:'name', 
// 		    required : true,
			disabled:true
		});
		
	});
	if('${inventoryStock.warehouseCode }'!=''){
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${inventoryStock.warehouseCode }",
			success: function(data){
				$('#warehouseView').val(data);
			}
		});
	}
	if('${inventoryStock.shipNo }'!=''){
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${inventoryStock.shipNo }',
			success: function(data){
				if(data != null){
					$('#shipNoView').html(data);
				} 
			}
		});
	}
	
	if('${inventoryStock.goodsName }'!=''){
		$.ajax({
			type:'GET',
			async: false,
			url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+'${inventoryStock.goodsName }' ,
			success: function(data){
				$('#goodsNameView').html(data);
			}
		});
	}
	
	
	
	function loadStockGodds(){
		dlgStockGoods=$("#dlgStockGoods").dialog({
		  	width: 800,    
		    height: 400, 
		    title: '选择入库明细',
		    href:'${ctx}/stock/stockAllocation/loadStockGodds/',
		    modal:true,
		    closable:true,
		    style:{borderWidth:0},
		    buttons:[{
				text:'确认',iconCls:'icon-ok',
				handler:function(){
					initInvebtoryStock();
					dlgStockGoods.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlgStockGoods.panel('close');
				}
			}]
		});
		
	}
	
	//保存
	function initInvebtoryStock(){
		var row = goods_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$('#warehouseCode').val(row.warehouseName);
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+row.warehouseName,
			success: function(data){
				$('#warehouseView').val(data);
			}
		});
// 		$('#goodsName').combotree('setValue', row.goodsName);
		$('#sourceQuantity').numberbox('setValue', row.quantity);
		$('#units').combobox('setValue', row.units);
		$('#inventoryQuantity').numberbox('setValue', "");
		$('#stockGoodsId').val( row.id);
		$('#shipNo').val(row.shipNo);
		$('#shipNoAndName').val(row.shipNoAndName);
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/logistic/shipTrace/shipNameShow/"+row.shipNo,
			success: function(data){
				if(data != null){
					$('#shipNoView').html(data);
				} 
			}
		});
		$('#goodsName').val( row.goodsName);
		$.ajax({
			type:'GET',
			async: false,
			url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+row.goodsName ,
			success: function(data){
				$('#goodsNameView').html(data);
			}
		});
		$('#relLoginNames').val(row.relLoginNames);
	}
</script>
</body>
</html>