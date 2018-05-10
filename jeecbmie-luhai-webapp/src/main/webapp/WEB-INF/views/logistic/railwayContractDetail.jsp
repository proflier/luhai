<%@page import="com.cbmie.lh.logistic.entity.RailwayContract"%>
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
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">内部合同号</th>
						<td width="30%">
							<input type="hidden" name="id" id="id" value="${railwayContract.id}" />
							${railwayContract.innerContractNo }
						</td>
						<th width="20%">合同编号</th>
						<td width="30%">
							${railwayContract.contractNo }
						</td>
					</tr>
					<tr>
						<th>申请部门</th>
						<td>
							${mytag:getOrgNameById(railwayContract.applyDeptId)}
						</td>
						<th>申请人</th>
						<td>
							${mytag:getUserById(railwayContract.applyUserId).name}
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(railwayContract.businessManager).name}
						</td>
						<th>申请日期</th>
						<td>
							<fmt:formatDate value='${railwayContract.applyDate}' />
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							${railwayContract.money }
						</td>
						<th>币种</th>
						<td>
							${fns:getDictLabelByCode(railwayContract.moneyCurrencyCode,'currency','')}
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<fmt:formatDate value='${railwayContract.startDate}' />
						</td>
						<th>合同结束日期</th>
						<td>
							<fmt:formatDate value='${railwayContract.endDate}' />
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							${mytag:getAffiBaseInfoByCode(railwayContract.traderName)}
						</td>
						<th>对方联系方式</th>
						<td>
							${railwayContract.traderContact }
						</td>
					</tr>
					<tr>
						<th>公章</th>
						<td>
							${mytag:getEntityByCode(railwayContract.signetCode).typeName}
						</td>
						<th  >印章管理员</th>
						<td>
							<c:if test="${!empty railwayContract.sealManager}">
							${mytag:getUserById(railwayContract.sealManager).name}
							</c:if>
						</td>
					</tr>
					<tr>
						<th>贸易类型</th>
						<td colspan="3">
							${fns:getDictLabelByCode(railwayContract.tradeCategory,'orderShipType','')}
						</td>
					</tr>
					<tr>
						<th>主要内容</th>
						<td colspan="3">
							${railwayContract.content }
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<c:if test="${railwayContract.isCorporationSign eq '1'}">
							是
							</c:if>
							<c:if test="${railwayContract.isCorporationSign eq '0'}">
							否
							</c:if>
						</td>
						<th>代理人</th>
						<td>
							${railwayContract.agent }
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							${fns:getDictLabelByCode(railwayContract.checkTypeCode,'checkType','')}
						</td>
						<th>是否法务审查</th>
						<td>
							<c:if test="${railwayContract.isLegalReview eq '1'}">
							是
							</c:if>
							<c:if test="${railwayContract.isLegalReview eq '0'}">
							否
							</c:if>
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							${railwayContract.tipContent }
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td colspan="3">
							${mytag:getAffiBaseInfoByCode(railwayContract.setUnit)}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">制单人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${railwayContract.createrNo }"/>
							<input type="hidden" name="createrName" value="${railwayContract.createrName }"/>
							<input type="hidden" name="createrDept" value="${railwayContract.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${railwayContract.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${railwayContract.createrName }</td>
						<th width="20%" >制单部门</th>
						<td width="30%">${railwayContract.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >制单时间</th>
						<td width="30%" colspan="3">
							<fmt:formatDate value="${railwayContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${railwayContract.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${railwayContract.changeReason }
							</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<table id="childTBBak"></table>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=RailwayContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${railwayContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
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
		
		var childTBBak;
		$(function() {
			childTBMxBak = $('#childTBBak')
					.datagrid(
							{
								url : '${ctx}/logistic/railwayContract/getRailwayContractBak/${railwayContract.id}',
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
										{field:'contractNo',title:'合同号',sortable:true,width:20}, 
										{field:'innerContractNo',title:'内部合同号',sortable:true,width:20}, 
										{field:'traderName',title:'对方单位名称',sortable:true,width:20,
											formatter: function(value,row,index){
												var val;
												if(value!=''&&value!=null){
													$.ajax({
														type:'GET',
														async: false,
														url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
														success: function(data){
															if(data != null){
																val = data;
															} else {
																val = '';
															}
														}
													});
													return val;
												}else{
													return '';
												}
											}	
										}, 
										{field:'tradeCategory',title:'贸易类型',sortable:true,width:20,
											formatter: function(value,row,index){
												var val;
												if(value!=''&&value!=null){
													$.ajax({
														type:'GET',
														async: false,
														url:"${ctx}/system/dictUtil/getDictNameByCode/orderShipType/"+value,
														success: function(data){
															if(data != null){
																val = data;
															} else {
																val = '';
															}
														}
													});
													return val;
												}else{
													return '';
												}
											}	
										}, 
										{field:'money',title:'金额',sortable:true,width:20}, 
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
					href : '${ctx}/logistic/railwayContract/showChange/'
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
