<%@page import="com.cbmie.lh.logistic.entity.OrderShipContract"%>
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
		<form id="mainform"  action="${ctx}/logistic/orderShipContract/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<input id="id" name="id" type="hidden" value="${orderShipContract.id }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">合同号</th>
						<td width="30%">
							${orderShipContract.contractNo }
						</td>
						<th width="20%">内部合同号</th>
						<td width="30%">
							${orderShipContract.innerContractNo }
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(orderShipContract.businessManager).name}
						</td>
						<th>申请日期</th>
						<td>
							<fmt:formatDate value='${orderShipContract.applyDate}' />
						</td>
					</tr>
					<tr>
						<th>用章类别</th>
						<td>
							${mytag:getEntityByCode(orderShipContract.signetCode).typeName}
						</td>
						<th  >印章管理员</th>
						<td id="sealManagerShow">
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							${orderShipContract.money }
						</td>
						<th>币种</th>
						<td>
							${fns:getDictLabelByCode(orderShipContract.moneyCurrencyCode,'currency','')}
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<fmt:formatDate value='${orderShipContract.startDate}' />
						</td>
						<th>合同结束日期</th>
						<td>
							<fmt:formatDate value='${orderShipContract.endDate}' />
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							${mytag:getAffiBaseInfoByCode(orderShipContract.traderName)}
						</td>
						<th>对方联系方式</th>
						<td>
							${orderShipContract.traderContact }
						</td>
					</tr>
					<tr>
						<th>订船类型</th>
						<td>
							${fns:getDictLabelByCode(orderShipContract.orderShipType,'orderShipType','')}
						</td>
						<th>货量(吨)</th>
						<td>
							${orderShipContract.contractQuantity }
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<c:if test="${orderShipContract.isCorporationSign eq '1'}">
							是
							</c:if>
							<c:if test="${orderShipContract.isCorporationSign eq '0'}">
							否
							</c:if>
						</td>
						<th>代理人</th>
						<td>
							${orderShipContract.agent }
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							${fns:getDictLabelByCode(orderShipContract.checkTypeCode,'checkType','')}
						</td>
						<th>是否法务审查</th>
						<td >
							<c:if test="${orderShipContract.isLegalReview eq '1'}">
							是
							</c:if>
							<c:if test="${orderShipContract.isLegalReview eq '0'}">
							否
							</c:if>
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td colspan="3">
							${mytag:getAffiBaseInfoByCode(orderShipContract.setUnit)}
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							${orderShipContract.tipContent }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${orderShipContract.createrNo }"/>
							<input type="hidden" name="createrName" value="${orderShipContract.createrName }"/>
							<input type="hidden" name="createrDept" value="${orderShipContract.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${orderShipContract.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${orderShipContract.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${orderShipContract.createrName }
						</td>
						<th  >登记部门</th>
						<td>${orderShipContract.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${orderShipContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${orderShipContract.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${orderShipContract.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${orderShipContract.changeReason }
							</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
						<legend>相关员工</legend>
						<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
					</fieldset>
			</div>
			<div data-options="title: '船明细', iconCls: false, refreshable: false">
				<%@ include file="/WEB-INF/views/logistic/orderShipContractSubList.jsp"%>
			</div>
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<table id="childTBBak"></table>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=OrderShipContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${orderShipContract.id}" />
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
		if('${orderShipContract.sealManager }'!=null && '${orderShipContract.sealManager }'!=''){
			$.ajax({
				url:'${ctx}/baseInfo/baseUtil/userNameShow?userId=${orderShipContract.sealManager }',
				type:'get',
				success:function(data){
					$('#sealManagerShow').html(data);
				},
			});
		}
		
		var childTBBak;
		$(function() {
			childTBMxBak = $('#childTBBak')
					.datagrid(
							{
								url : '${ctx}/logistic/orderShipContract/getOrderShipContractBak/${orderShipContract.id}',
								method : "get",
								fit : false,
								fitColumns : true,
								scrollbarSize : 0,
								border : false,
								striped : true,
								idField : 'id',
								rownumbers : true,
								singleSelect : true,
								extEditing : false,
								columns : [ [
										{
											field : 'changeReason',
											title : '变更原因',
											sortable : true,
											width : 15
										},
										{
											field : 'updateDate',
											title : '变更时间',
											sortable : true,
											width : 15
										},
										{field:'contractNo',title:'合同号',sortable:true,width:25}, 
										{field:'innerContractNo',title:'内部合同号',sortable:true,width:25}, 
										{field:'orderShipTypeView',title:'订船类型',sortable:true,width:20},
										{
											field : 'id',
											title : '查看记录',
											sortable : true,
											width : 15,
											formatter : function(value, row,
													index) {
												var str = "";
												str += "<a   class='easyui-linkbutton' iconCls='icon-search' plain='true' href='javascript:void(0)'  onclick='showChange("
														+ value
														+ ");'>查看变更</a>"
												return str;
											}
										}, ] ],
								enableHeaderClickMenu : false,
								enableHeaderContextMenu : false,
								enableRowContextMenu : false,
							});
		});
	});
	
	
	function showChange(idValue) {
		d_change = $("#dlg_bak").dialog(
				{
					title : '变更记录',
					width : 1000,
					height : 450,
					style : {
						borderWidth : 0
					},
					closable : false,
					href : '${ctx}/logistic/orderShipContract/showChange/'
							+ idValue,
					modal : true,
					buttons : [ {
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							d_change.panel('close');
						}
					} ]
				});

	}
</script>
</body>
</html>
