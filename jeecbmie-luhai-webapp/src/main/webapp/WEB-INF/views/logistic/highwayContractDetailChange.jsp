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
							<input type="hidden" name="id" id="id" value="${highwayContractChange.id}" />
							${highwayContractChange.innerContractNo }
						</td>
						<th width="20%">合同号</th>
						<td width="30%">
							${highwayContractChange.contractNo }
						</td>
					</tr>
					<tr>
						<th>申请部门</th>
						<td>
							${mytag:getOrgNameById(highwayContractChange.applyDeptId)}
						</td>
						<th>申请人</th>
						<td>
							${mytag:getUserById(highwayContractChange.applyUserId).name}
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(highwayContract.businessManager).name}
						</td>
						<th>申请日期</th>
						<td>
							<fmt:formatDate value='${highwayContractChange.applyDate}' />
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							${highwayContractChange.money }
						</td>
						<th>币种</th>
						<td>
							${fns:getDictLabelByCode(highwayContractChange.moneyCurrencyCode,'currency','')}
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<fmt:formatDate value='${highwayContractChange.startDate}' />
						</td>
						<th>合同结束日期</th>
						<td>
							<fmt:formatDate value='${highwayContractChange.endDate}' />
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							${mytag:getAffiBaseInfoByCode(highwayContractChange.traderName)}
						</td>
						<th>对方联系方式</th>
						<td>
							${highwayContractChange.traderContact }
						</td>
					</tr>
					<tr>
						<th>公章</th>
						<td>
							${mytag:getEntityByCode(highwayContractChange.signetCode).typeName}
						</td>
						<th  >印章管理员</th>
						<td >
							<c:if test="${!empty highwayContractChange.sealManager}">
							${mytag:getUserById(highwayContractChange.sealManager).name}
							</c:if>
						</td>
					</tr>
					<tr>
						<th>贸易类型</th>
						<td colspan="3">
							${fns:getDictLabelByCode(highwayContractChange.tradeCategory,'orderShipType','')}
						</td>
					</tr>
					<tr>
						<th>主要内容</th>
						<td colspan="3">
							${highwayContractChange.content }
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<c:if test="${highwayContractChange.isCorporationSign eq '1'}">
							是
							</c:if>
							<c:if test="${highwayContractChange.isCorporationSign eq '0'}">
							否
							</c:if>
						</td>
						<th>代理人</th>
						<td>
							${highwayContractChange.agent }
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							${fns:getDictLabelByCode(highwayContractChange.checkTypeCode,'checkType','')}
						</td>
						<th>是否法务审查</th>
						<td>
							<c:if test="${highwayContractChange.isLegalReview eq '1'}">
							是
							</c:if>
							<c:if test="${highwayContractChange.isLegalReview eq '0'}">
							否
							</c:if>
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							${highwayContractChange.tipContent }
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td colspan="3">
							${mytag:getAffiBaseInfoByCode(highwayContractChange.setUnit)}
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
							<input type="hidden" name="createrNo" value="${highwayContractChange.createrNo }"/>
							<input type="hidden" name="createrName" value="${highwayContractChange.createrName }"/>
							<input type="hidden" name="createrDept" value="${highwayContractChange.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${highwayContractChange.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${highwayContractChange.createrName }</td>
						<th width="20%" >制单部门</th>
						<td width="30%">${highwayContractChange.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >制单时间</th>
						<td width="30%" colspan="3">
							<fmt:formatDate value="${highwayContractChange.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<span >${themeMembersChange}</span>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>附件信息</legend>
					<div id="dgAccChange"  class="tableClass"></div>
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
		
		$('#dgAccChange').datagrid({
			method : "get",
			fit : false,
			fitColumns : true,
			border : false,
			striped : true,
			singleSelect : true,
			scrollbarSize : 0,
			url : '${ctx}/accessory/jsonAll?filter_EQS_accParentId=${railwayContractChange.id}&filter_EQS_accParentEntity=com_cbmie_lh_logistic_entity_RailwayContract',
			idField : 'accId',
			columns : [[
				{
					field : 'accRealName',
					title : '附件名称',
					sortable : true,
					width : 40
				},
				{
					field : 'accAuthor',
					title : '上传人',
					sortable : true,
					width : 10,
					formatter: function(value,row,index){
						var name = '';
						if(value!=null && value!=""){
							$.ajax({
								url : '${ctx}/system/user/getUserNameByLogin/'+value ,
								type : 'get',
								cache : false,
								async: false,
								success : function(data) {
									name = data;
								}
							});
						}
						return name;
					}
				},
				{
					field : 'accId',
					title : '操作',
					sortable : true,
					width : 20,
					formatter : function(value, row, index) {
						var str = "";
						str += "<a style='text-decoration:none' href='#' onclick='downnloadAcc1(" + value + ");'>下载</a>"
						return str;
					}
				} 
			]]
		});
	});
	
	function downnloadAcc1(id) {
		window.open("${ctx}/accessory/download/" + id, '下载');
	}
</script>
</body>
</html>
