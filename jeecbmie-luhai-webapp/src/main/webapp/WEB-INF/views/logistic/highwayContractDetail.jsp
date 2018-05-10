<%@page import="com.cbmie.lh.logistic.entity.HighwayContract"%>
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
		<form id="mainform"  action="${ctx}/logistic/highwayContract/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">内部合同号</th>
						<td width="30%">
							<input type="hidden" name="id" id="id" value="${highwayContract.id}" />
							${highwayContract.innerContractNo }
						</td>
						<th width="20%">合同号</th>
						<td width="30%">
							${highwayContract.contractNo }
						</td>
					</tr>
					<tr>
						<th>申请部门</th>
						<td>
							${mytag:getOrgNameById(highwayContract.applyDeptId)}
						</td>
						<th>申请人</th>
						<td>
							${mytag:getUserById(highwayContract.applyUserId).name}
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(highwayContract.businessManager).name}
						</td>
						<th>申请日期</th>
						<td>
							<fmt:formatDate value='${highwayContract.applyDate}' />
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							${highwayContract.money }
						</td>
						<th>币种</th>
						<td>
							${fns:getDictLabelByCode(highwayContract.moneyCurrencyCode,'currency','')}
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<fmt:formatDate value='${highwayContract.startDate}' />
						</td>
						<th>合同结束日期</th>
						<td>
							<fmt:formatDate value='${highwayContract.endDate}' />
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							${mytag:getAffiBaseInfoByCode(highwayContract.traderName)}
						</td>
						<th>对方联系方式</th>
						<td>
							${highwayContract.traderContact }
						</td>
					</tr>
					<tr>
						<th>公章</th>
						<td>
							${mytag:getEntityByCode(highwayContract.signetCode).typeName}
						</td>
						<th  >印章管理员</th>
						<td >
							<c:if test="${!empty highwayContract.sealManager}">
							${mytag:getUserById(highwayContract.sealManager).name}
							</c:if>
						</td>
					</tr>
					<tr>
						<th>贸易类型</th>
						<td colspan="3">
							${fns:getDictLabelByCode(highwayContract.tradeCategory,'orderShipType','')}
						</td>
					</tr>
					<tr>
						<th>主要内容</th>
						<td colspan="3">
							${highwayContract.content }
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<c:if test="${highwayContract.isCorporationSign eq '1'}">
							是
							</c:if>
							<c:if test="${highwayContract.isCorporationSign eq '0'}">
							否
							</c:if>
						</td>
						<th>代理人</th>
						<td>
							${highwayContract.agent }
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							${fns:getDictLabelByCode(highwayContract.checkTypeCode,'checkType','')}
						</td>
						<th>是否法务审查</th>
						<td>
							<c:if test="${highwayContract.isLegalReview eq '1'}">
							是
							</c:if>
							<c:if test="${highwayContract.isLegalReview eq '0'}">
							否
							</c:if>
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							${highwayContract.tipContent }
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td colspan="3">
							${mytag:getAffiBaseInfoByCode(highwayContract.setUnit)}
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
							<input type="hidden" name="createrNo" value="${highwayContract.createrNo }"/>
							<input type="hidden" name="createrName" value="${highwayContract.createrName }"/>
							<input type="hidden" name="createrDept" value="${highwayContract.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${highwayContract.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${highwayContract.createrName }</td>
						<th width="20%" >制单部门</th>
						<td width="30%">${highwayContract.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >制单时间</th>
						<td width="30%" colspan="3">
							<fmt:formatDate value="${highwayContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${highwayContract.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${highwayContract.changeReason }
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
				<input id="accParentEntity" type="hidden"  value="<%=HighwayContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${highwayContract.id}" />
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
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#innerContractNo').val(data.currnetSequence);
		    	}
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
		$('#applyDeptId').combotree({
			method:'GET',
		    url: '${ctx}/system/organization/json',
		    idField : 'id',
		    textFiled : 'orgName',
			parentField : 'pid',
			panelHeight:300,
		    animate:true,
		    required:true,
		    onChange:function(){
		    	$('#applyUserId').combobox("clear");
		    	$('#applyUserId').combobox('reload','${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#applyDeptId').combotree("getValue"));
		    },
		    onHidePanel:function(){}
		});
		$('#applyUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#applyDeptId').val(),
			valueField : 'id',
			textField : 'name'
		});
		$('#handlingUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItems',
			valueField : 'id',
			textField : 'name'
		});
		if('${highwayContract.sealManager }'!=null && '${highwayContract.sealManager }'!=''){
			$.ajax({
				url:'${ctx}/baseInfo/baseUtil/userNameShow?userId=${highwayContract.sealManager }',
				type:'get',
				success:function(data){
					$('#sealManagerShow').html(data);
				},
			});
		};
		//印章类型 
		$('#signetCode').combogrid({    
		    panelWidth:450,    
		    method: "get",
		    idField:'signetCode',    
		    textField:'typeName',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
			url:'${ctx}/baseInfo/lhUtil/getSignetList', 
		    columns:[[    
					{field:'id',title:'id',hidden:true},  
					{field:'signetCode',title:'编码',sortable:true,width:25},
					{field:'typeCode',title:'印章类型',sortable:true,width:25,
						formatter: function(value,row,index){
							var unit_t = '';
							$.ajax({
								url : '${ctx}/system/dictUtil/getDictNameByCode/SIGNETTYPE/'+value ,
								type : 'get',
								cache : false,
								async: false,
								success : function(data) {
									unit_t = data;
								}
							});
							return unit_t;
							}
					},
					{field:'orgId',title:'所属公司',sortable:true,width:25,
						formatter: function(value,row,index){
						var unit_t = '';
						$.ajax({
							url : '${ctx}/baseInfo/baseUtil/orgNameShow?orgId='+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								unit_t = data;
							}
						});
						return unit_t;
						}	
					}
		    ]],
		    onSelect:function(rowIndex, rowData){
		    	$('#sealManager').val(rowData.saveUserId);
		    	$('#sealManagerShow').val(rowData.saveUserName);
		    }
		}); 
		$('#startDate').bind('click',function(){
		    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'endDate\')}',onpicked:function(){deliveryTerm.click();}});
		});
		$('#endDate').bind('click',function(){
		    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
		});
		
		var childTBBak;
		$(function() {
			childTBMxBak = $('#childTBBak')
					.datagrid(
							{
								url : '${ctx}/logistic/highwayContract/getHighwayContractBak/${highwayContract.id}',
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
	$(function(){
		if('${action}' == 'view') {
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			$("#mainform").find('.easyui-combotree').combotree({
			    disabled:true
			});
			$("#mainform").find('.easyui-combogrid').combogrid({
			    disabled:true
			});
			$("#mainform").find('input').attr("readonly",true);
			//处理日期控件
			$("#mainform").find(".easyui-my97").each(function(index,element){
				$(element).attr("readonly",true);
				$(element).removeClass("easyui-my97");
				$(element).addClass("easyui-validatebox");
			});
		}
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
					href : '${ctx}/logistic/highwayContract/showChange/'
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
