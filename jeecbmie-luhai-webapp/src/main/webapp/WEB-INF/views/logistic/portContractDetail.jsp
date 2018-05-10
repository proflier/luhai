<%@ page import="com.cbmie.lh.logistic.entity.PortContract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
		<input id="portContractId" name="id" type="hidden"  value="${portContract.id }" />
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">合同号</th>
						<td colspan="3" >
							${portContract.contractNo}
						</td>
					</tr>
					<tr>
						<th  width="20%">免堆期天数</th>
						<td  width="30%">
							${portContract.freeHeapDays}
						</td>
						<th  width="20%">达量免堆数(万吨)</th>
						<td  width="30%">
							${portContract.freeHeapCounts}
						</td>
					</tr>
					<tr>
						<th width="20%">合同开始日期</th>
						<td width="30%">
							<fmt:formatDate value="${portContract.startDay }" />
						</td>
						<th width="20%">合同结束日期</th>
						<td width="30%">
							<fmt:formatDate value="${portContract.endDay }" />
						</td>
					</tr>
					<tr>
						<th width="20%">签订单位</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(portContract.signAffiliate)}
						</td>
						<th width="20%">联系方式</th>
						<td width="30%">
							${portContract.traderContact}
						</td>
					</tr>
					<tr>
						<th width="20%">是否法人签订</th>
						<td width="30%">
							${fns:getDictLabelByCode(portContract.isCorporationSign,'YESNO','')}
						</td>
						<th width="20%">代理人</th>
						<td width="30%">
							${portContract.agent}
						</td>
					</tr>
					<tr>
						<th width="20%">审核类别</th>
						<td width="30%">
							${fns:getDictLabelByCode(portContract.checkTypeCode,'checkType','')}
						</td>
						<th width="20%">是否法务审核</th>
						<td width="30%">
							${fns:getDictLabelByCode(portContract.isLegalReview,'YESNO','')}
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td >
							${mytag:getAffiBaseInfoByCode(portContract.setUnit)}
						</td>
						<th  >业务经办人 </th>
						<td>
							${mytag:getUserByLoginName(portContract.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${portContract.remarks}
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th width="20%" >制单人</th>
							<td width="30%">
								<input type="hidden" name="createrNo" value="${portContract.createrNo }"/>
								<input type="hidden" name="createrName" value="${portContract.createrName }"/>
								<input type="hidden" name="createrDept" value="${portContract.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${portContract.createDate  }' pattern='yyyy-MM-dd'/>" />
								${portContract.createrName }</td>
							<th width="20%">部门</th>
							<td width="30%">${portContract.createrDept }</td>
						</tr>
						<tr>	
							<th width="20%">制单日</th>
							<td width="30%" colspan="3">
								<fmt:formatDate value="${portContract.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<c:if test="${portContract.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${portContract.changeReason }
							</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
					<legend>作业费</legend>
					<div>
						<table id="operateList"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>杂费</legend>
					<div>
						<table id="extraList"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<table id="childTBBak"></table>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PortContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${portContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
						
		</div>
</div>
<script type="text/javascript">
	var operateExpense = {
		operateList:{},
		initOperateList:	function(){
			if($("#portContractId").val()==null || $("#portContractId").val()=="") return;
			operateExpense.operateList=$('#operateList').datagrid({
				method: "get",
				url: '${ctx}/logistic/portContract/operate/json?filter_EQL_portContractId='+$("#portContractId").val(),
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				extEditing:false,
				singleSelect:true,
			    columns:[[    
					{field:'portNoView',title:'港口',width:15},
					{field:'tradeCategoryView',title:'贸易类别',width:5},
					{field:'operateTypeView',title:'作业方式',width:10},
					{field:'expenseStart',title:'计价范围开始(万吨)',width:10},
					{field:'expenseEnd',title:'计价范围结束',width:10},
					{field:'unitPrice',title:'单价(元/吨)',width:10},
					{field:'summary',title:'备注',width:30,
						formatter: function (value) {
							if(value!=''&&value!=null){
								var returnValue = value.substring(0,20)+"...";
			                	return "<span title='" + value + "'>" + returnValue + "</span>";
							}else{
								return "";
							}
		                }
					}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			})
		},
		operateForm:'',
	};
	var extraExpense = {
				extraList:{},
				initextraList:function(){
					if($("#portContractId").val()==null || $("#portContractId").val()=="") return;
					extraExpense.extraList = $('#extraList').datagrid({
						method: "get",
						url: '${ctx}/logistic/portContract/extra/json?filter_EQL_portContractId='+$("#portContractId").val(),
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						extEditing:false,
						singleSelect:true,
					    columns:[[    
							{field:'expenseTypeView',title:'费用类别',width:10},
							{field:'countUnitView',title:'计量单位',width:10},
							{field:'expenseStart',title:'计价范围开始(天)',width:10},
							{field:'expenseEnd',title:'计价范围结束',width:10},
							{field:'unitPrice',title:'单价',width:10}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					})
				},
			extraForm:'',
	};
$(function(){
	operateExpense.initOperateList();
	extraExpense.initextraList();
});
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#portContractId").val() == '') ){//主表校验通过且已经保存数据
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
	var idValue = $("#portContractId").val();
	var childTBBak;
	$(function() {
		childTBMxBak = $('#childTBBak')
				.datagrid(
						{
							url : '${ctx}/logistic/portContract/getPortContractBak/'
									+ idValue,
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
									{field:'signAffiliate',title:'签订单位',sortable:true,width:20,
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
				href : '${ctx}/logistic/portContract/showChange/'
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