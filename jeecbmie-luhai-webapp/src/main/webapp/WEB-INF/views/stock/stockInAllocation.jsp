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
						${stockAllocation.allocationNo }
						<input type="hidden" id="id" name="id" value="${stockAllocation.id }" />
						<input type="hidden" id="onload_allocation"  value="" />
						</td>
						<th >原仓库</th>
						<td id="sourceWarehouseView">
						</td>
					</tr>
					<tr>
						<th width="20%">商品名称</th>
						<td width="30%" id="goodsNameView">
						</td>
						<th width="20%">原仓库商品数量</th>
						<td  width="30%">
							${stockAllocation.sourceGoodsQuantity }
						</td>
					</tr>
					<tr>
						<th >调拨数量</th>
						<td>
							${stockAllocation.allocationQuantity }
						</td>
						<th >目标仓库</th>
						<td id="destWarehouseCode">
						</td>
					</tr>
					<tr>
						<th >调拨人</th>
						<td>
							${stockAllocation.allocationPerson }
						</td>
						<th >调出日期</th>
						<td >
							<fmt:formatDate value="${stockAllocation.outTime }"/>
						</td>
					</tr>
					<tr>
						<th>船名</th>
						<td id="shipNo">
						</td>
						<th >开单船次</th>
						<td id="innerShipNo">
						</td>
					</tr>
					<tr>
						<th >接收人</th>
						<td>
							<input  name="receivePerson" type="text" value="${stockAllocation.receivePerson }"  class="easyui-validatebox" />
						</td>
						<th >调入数量</th>
						<td >
							<input id="receiveQuantity" name="receiveQuantity" type="text" value="${stockAllocation.receiveQuantity }"  class="easyui-numberbox" data-options="required:true,min:0,precision:'3'" />
						</td>
					</tr>
					
					<tr>
						<th >调入日期</th>
						<td >
								<input id="inTime" name="inTime" type="text" value="<fmt:formatDate value="${stockAllocation.inTime }"/>" 
									class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th >运输方式</th>
						<td>
							${fns:getDictLabelByCode(stockAllocation.transportType,'YSFS','')}
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
						<td width="30%"><fmt:formatDate value="${ stockAllocation.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${stockAllocation.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
	 				$("#button-save").linkbutton('disable');
	 				$("#button-save").linkbutton({    
	 				    iconCls: 'icon-hamburg-refresh',
	 				    text:'保存中'
	 				});
		    	}
				return isValid;	// 返回false终止表单提交
		    },
		    success:function(data){
		    	successTipNew(data,dg);
		    	$("#button-save").linkbutton('enable');
				$("#button-save").linkbutton({    
				    iconCls: 'icon-save',
				    text:'保存'
				});
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
		
			
		$.ajax({
	 		type:'GET',
	 		async: false,
	 		url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${stockAllocation.destWarehouseCode }",
	 		success: function(data){
	 			$('#destWarehouseCode').html(data);
	 		}
	 	});
		$.ajax({
	 		type:'GET',
	 		async: false,
	 		url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${stockAllocation.sourceWarehouseCode }",
	 		success: function(data){
	 			$('#sourceWarehouseView').html(data);
	 		}
	 	});
		$.ajax({
	 		type:'GET',
	 		async: false,
	 		url : '${ctx}/baseInfo/lhUtil/goodsShow?code=${stockAllocation.goodsName }',
	 		success: function(data){
	 			$('#goodsNameView').html(data);
	 		}
	 	});
		
		if('${stockAllocation.innerShipNo }'!=''){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${stockAllocation.innerShipNo }',
				success: function(data){
					if(data != null){
						$('#innerShipNo').html(data);
					} 
				}
			});
		}
		
		
		if('${stockAllocation.shipNo }'!=''){
			$.ajax({
				type:'GET',
				async: false,
				url:"${ctx}/logistic/shipTrace/shipNameShow/"+'${stockAllocation.shipNo }',
				success: function(data){
					if(data != null){
						$('#shipNo').html(data);
					} 
				}
			});
		}
		

		
	});
	
	

	
</script>
</body>
</html>