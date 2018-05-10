<%@page import="com.cbmie.lh.stock.entity.StockAllocation"%>
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
		<form id="mainform"  action="${ctx}/stock/stockAllocation/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '调拨信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>货物来源</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >调拨编码</th>
						<td >
						<input id="allocationNo" name="allocationNo" type="text" value="${stockAllocation.allocationNo }" class="easyui-validatebox" readonly="readonly" data-options="required:true" />
						<input type="hidden" id="id" name="id" value="${stockAllocation.id }" />
						<input type="hidden" id="relLoginNames" name="relLoginNames" value="${stockAllocation.relLoginNames }"/>
						<input type="hidden" id="sourceStockGoodsId" name="sourceStockGoodsId" value="${stockAllocation.sourceStockGoodsId }" />
						<input type="hidden" id="sourceWarehouseCode" name="sourceWarehouseCode" value="${stockAllocation.sourceWarehouseCode }" />
						<input type="hidden" id="goodsName" name="goodsName" value="${stockAllocation.goodsName }" />
						</td>
						<th >原仓库</th>
						<td>
							<input id="sourceWarehouseView"  readonly="readonly" onfocus="loadStockGodds()" type="text" class="easyui-validatebox" data-options="required:true"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadStockGodds()">选择源仓库</a>
						</td>
					</tr>
					<tr>
						<th width="20%">商品名称</th>
						<td width="30%" id="goodsNameView">
						</td>
						<th width="20%">原仓库商品数量</th>
						<td  width="30%">
							<input id="sourceGoodsQuantity" name="sourceGoodsQuantity" type="text" value="${stockAllocation.sourceGoodsQuantity }"  readonly="readonly" class="easyui-numberbox" data-options="required:true,precision:'3'"   />
						</td>
					</tr>
					<tr>
						<th >调拨数量</th>
						<td>
							<input id="allocationQuantity" name="allocationQuantity" type="text" value="${stockAllocation.allocationQuantity }"  class="easyui-numberbox" data-options="required:true,min:0,precision:'3'" />
						</td>
						<th >目标仓库</th>
						<td>
							<input id="destWarehouseCode" name="destWarehouseCode" type="text" value="${stockAllocation.destWarehouseCode }"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th >调拨人</th>
						<td>
							<input  name="allocationPerson" type="text" value="${stockAllocation.allocationPerson }"  class="easyui-validatebox" />
						</td>
						<th >调出日期</th>
						<td >
							<input id="outTime" name="outTime" type="text" value="<fmt:formatDate value="${stockAllocation.outTime }"/>" 
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<input id="shipNo" name="shipNo" type="hidden" value="${stockAllocation.shipNo }" class="easyui-validatebox" />
						<input id="shipNoAndName" name="shipNoAndName" type="hidden" value="${stockAllocation.shipNoAndName }" class="easyui-validatebox"/>
						<td id="shipNoView">
						</td>
						<th >开单船次</th>
						<td >
							<input id="innerShipNo" name="innerShipNo" type="text" value="${stockAllocation.innerShipNo }"  class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th >运输方式</th>
						<td colspan="3">
							<mytag:combobox name="transportType" value="${stockAllocation.transportType}" type="dict" parameters="YSFS"/>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="2" cols="90" id="summary" class="easyui-validatebox" name="summary" data-options="validType:['length[0,255]']">${stockAllocation.summary }</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty stockAllocation.createrName ? sessionScope.user.name : stockAllocation.createrName }</td>
						<th  >登记部门</th>
						<td>${empty stockAllocation.createrDept ? sessionScope.user.organization.orgName : stockAllocation.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"> <fmt:formatDate value="${ stockAllocation.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"> <fmt:formatDate value="${stockAllocation.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=StockAllocation.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${stockAllocation.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		
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
		    	if(isValid){
		    		if(isValid){
			    		 $.easyui.loading({ msg: "正在加载..." });
			    	}
		    	}
				return isValid;	// 返回false终止表单提交
		    },
		    success:function(data){
		    	successTipNew(data,dg);
		    	var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#allocationNo').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
		    }
		});
		
		if('${action}'=='update'){
			$("#allocationQuantity").numberbox({
				max:($("#sourceGoodsQuantity").val())*1.000
			});
		}else{
			$("#allocationQuantity").numberbox({
				max:999999
			});
		}
		
		$('#innerShipNo').combogrid({    
		    panelWidth:450,
		    required:true,
		    method: "get",
		    idField:'shipNo',    
		    textField:'noAndName',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    url:'${ctx}/logistic/shipTrace/allShipTrace', 
// 		    url:'${ctx}/stock/stockAllocation/getInnnerShipTrace',
		    columns:[[
					{field:'shipNo',title:'船编号',width:20},
					{field:'shipName',title:'船中文名',width:20},
					{field:'shipNameE',title:'船英文名',width:20},
					{field:'loadPortView',title:'装港港口',width:20,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&&value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url:"${ctx}/baseInfo/baseUtil/getPortNameByCode/"+value,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}
					}
		 		]]
		});
			
		//仓库名称
		$('#destWarehouseCode').combobox({
			panelHeight : 'auto',
			method: "get",
			url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230007',
			valueField : 'customerCode',
			textField : 'customerName',
			required : true,
			onLoadSuccess:function (){
				if('${action}'!='create'){
					$.ajax({
				 		type:'GET',
				 		async: false,
				 		url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+$('#sourceWarehouseCode').val(),
				 		success: function(data){
				 			$('#sourceWarehouseView').val(data);
				 		}
				 	});
					$.ajax({
				 		type:'GET',
				 		async: false,
				 		url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+$('#goodsName').val(),
				 		success: function(data){
				 			$('#goodsNameView').html(data);
				 		}
				 	});
				}
		 	
			}
		});
		
	});
	if('${stockAllocation.shipNo }'!=''){
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${stockAllocation.shipNo }',
			success: function(data){
				if(data != null){
					$('#shipNoView').html(data);
				} 
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
					initStockAllocation();
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
	
	//确认
	function initStockAllocation(){
		var row = goods_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$('#sourceWarehouseCode').val(row.warehouseName);
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+row.warehouseName,
			success: function(data){
				$('#sourceWarehouseView').val(data);
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
		$('#sourceGoodsQuantity').numberbox('setValue', row.quantity);
		$('#allocationQuantity').numberbox({    
		    max:row.quantity*1.000,    
		});
		$('#sourceStockGoodsId').val(row.id);
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
		$('#relLoginNames').val(row.relLoginNames);
		
		
	}

	
</script>
</body>
</html>