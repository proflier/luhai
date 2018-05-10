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
		<input type="hidden" id="currentAction" value="${action }"/>
		<input id="changeId" name="id" type="hidden" value="${orderShipContractChange.id }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">合同号</th>
						<td width="30%">
							${orderShipContractChange.contractNo }
						</td>
						<th width="20%">内部合同号</th>
						<td width="30%">
							${orderShipContractChange.innerContractNo }
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(orderShipContractChange.businessManager).name}
						</td>
						<th>申请日期</th>
						<td>
							<fmt:formatDate value='${orderShipContractChange.applyDate}' />
						</td>
					</tr>
					<tr>
						<th>用章类别</th>
						<td>
							${mytag:getEntityByCode(orderShipContractChange.signetCode).typeName}
						</td>
						<th  >印章管理员</th>
						<td id="sealManagerShowChange">
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							${orderShipContractChange.money }
						</td>
						<th>币种</th>
						<td>
							${fns:getDictLabelByCode(orderShipContractChange.moneyCurrencyCode,'currency','')}
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<fmt:formatDate value='${orderShipContractChange.startDate}' />
						</td>
						<th>合同结束日期</th>
						<td>
							<fmt:formatDate value='${orderShipContractChange.endDate}' />
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							${mytag:getAffiBaseInfoByCode(orderShipContractChange.traderName)}
						</td>
						<th>对方联系方式</th>
						<td>
							${orderShipContractChange.traderContact }
						</td>
					</tr>
					<tr>
						<th>订船类型</th>
						<td>
							${fns:getDictLabelByCode(orderShipContractChange.orderShipType,'orderShipType','')}
						</td>
						<th>货量(吨)</th>
						<td>
							${orderShipContractChange.contractQuantity }
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<c:if test="${orderShipContractChange.isCorporationSign eq '1'}">
							是
							</c:if>
							<c:if test="${orderShipContractChange.isCorporationSign eq '0'}">
							否
							</c:if>
						</td>
						<th>代理人</th>
						<td>
							${orderShipContractChange.agent }
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							${fns:getDictLabelByCode(orderShipContractChange.checkTypeCode,'checkType','')}
						</td>
						<th>是否法务审查</th>
						<td >
							<c:if test="${orderShipContractChange.isLegalReview eq '1'}">
							是
							</c:if>
							<c:if test="${orderShipContractChange.isLegalReview eq '0'}">
							否
							</c:if>
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td colspan="3">
							${mytag:getAffiBaseInfoByCode(orderShipContractChange.setUnit)}
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							${orderShipContractChange.tipContent }
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
							<input type="hidden" name="createrNo" value="${orderShipContractChange.createrNo }"/>
							<input type="hidden" name="createrName" value="${orderShipContractChange.createrName }"/>
							<input type="hidden" name="createrDept" value="${orderShipContractChange.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${orderShipContractChange.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${orderShipContractChange.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${orderShipContractChange.createrName }
						</td>
						<th  >登记部门</th>
						<td>${orderShipContractChange.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${orderShipContractChange.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${orderShipContractChange.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${orderShipContractChange.pid ne null }">
					<fieldset   class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${orderShipContractChange.changeReason }
							</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<span >${themeMembersChange}</span>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>船明细</legend>
					<table id="dg_subs_change" ></table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>附件信息</legend>
					<div id="dgAccChange"  class="tableClass"></div>
				</fieldset>
			</div>
		</div>	
	</div>
<script type="text/javascript">
	$(function() {
		
		if('${orderShipContractChange.sealManager }'!=null && '${orderShipContractChange.sealManager }'!=''){
			$.ajax({
				url:'${ctx}/baseInfo/baseUtil/userNameShow?userId=${orderShipContractChange.sealManager }',
				type:'get',
				success:function(data){
					$('#sealManagerShowChange').html(data);
				},
			});
		}
		
		
		$('#dg_subs_change').datagrid({  
			method: "get",
			url:$('#changeId').val() ? '${ctx}/logistic/orderShipContractSub/json' : '', 
		  	fit : false,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			striped:true,
			idField : 'id',
			rownumbers:true,
			singleSelect:true,
			extEditing:false,
			queryParams: {
				'filter_EQL_orderShipContractId': $('#changeId').val()
			},
		    columns:[[    
				{field:'id',title:'id',hidden:true},
				{field:'shipNo',title:'船编号',width:20},
				{field:'ship',title:'船名',width:30,
					formatter: function(value,row,index){
						var val;
						if(row.shipNo!=''&& row.shipNo!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/logistic/shipTrace/shipNameShow/"+row.shipNo,
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
				{field:'loadBeginDate',title:'装货开始时间',width:20,
					formatter:function(value,row,index){
						if(value == null){
							return null;
						}
	            		var time = new Date(value);
	            		return time.format("yyyy-MM-dd");
	            	}}, 
				{field:'loadEndDate',title:'装货结束时间',width:20,
	            		formatter:function(value,row,index){
	    					if(value == null){
	    						return null;
	    					}
	                		var time = new Date(value);
	                		return time.format("yyyy-MM-dd");
	                	}}, 
				{field:'numMoreOrLess',title:'溢短装%',width:20}, 
				{field:'tradePriceRate',title:'运费单价',width:20}, 
				{field:'loadRate',title:'装率',width:20}, 
				{field:'unloadRate',title:'卸率',width:20}, 
				{field:'demurrageRate',title:'滯期率',width:20}
		    ]],
		});
		
		

		$('#dgAccChange').datagrid({
			method : "get",
			fit : false,
			fitColumns : true,
			border : false,
			striped : true,
			singleSelect : true,
			scrollbarSize : 0,
			url : '${ctx}/accessory/jsonAll?filter_EQS_accParentId=${orderShipContractChange.id}&filter_EQS_accParentEntity=com_cbmie_lh_logistic_entity_OrderShipContract',
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
