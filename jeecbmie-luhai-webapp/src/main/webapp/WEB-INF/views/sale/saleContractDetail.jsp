<%@ page import="com.cbmie.lh.sale.entity.SaleContract"%>
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
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>销售合同号</th>
						<td>
							${saleContract.contractNo}
						</td>
						<th>客户合同号</th>
						<td>
							${saleContract.customerContractNo }
						</td>
					</tr>
					<tr>
						<th>帐套单位</th>
						<td>
							${mytag:getAffiBaseInfoByCode(saleContract.setUnit)}
						</td>
						<th>帐期</th>
						<td>
							${saleContract.accountStage}
						</td>
					</tr>
					<tr>
						<th width="20%">卖方</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleContract.seller)}
						</td>
						<th  width="20%">买方</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleContract.purchaser)}
						</td>
					</tr>
					<tr>
						<th>销售类型</th>
						<td>
							${fns:getDictLabelByCode(saleContract.manageMode, 'BUSINESSTYPE', '')}
						</td>
						<th>经营方式</th>
						<td>
							${fns:getDictLabelByCode(saleContract.saleMode, 'SALESMODE', '')}
						</td>
					</tr>
					<tr>
						<th>业务经办人 </th>
						<td>
							${mytag:getUserByLoginName(saleContract.businessManager).name}
						</td>
						<th>费用承担人</th>
						<td>
							${mytag:getUserByLoginName(saleContract.feeUnderwriter)}
						</td>
					</tr>
					<tr>
						<th>合同有效期</th>
						<td>
							<fmt:formatDate value="${saleContract.startDate }" />
							-
							<fmt:formatDate value="${saleContract.endDate }" />
						</td>
						<th>交货起止期</th>
						<td>
							<fmt:formatDate value="${saleContract.deliveryStartDate }" />
							-
							<fmt:formatDate value="${saleContract.deliveryEndDate }" />
						</td>					
					</tr>
					<tr>
						<th>数量溢短装</th>
						<td>
							${saleContract.numMoreOrLess }
						</td>
						<th>签约日期</th>
						<td>
							<fmt:formatDate value="${saleContract.signDate }" pattern='yyyy-MM-dd'/>
						</td>
					</tr>
					<tr>
						<th>交货方式</th>
						<td>
							${fns:getDictLabelByCode(saleContract.deliveryMode, 'THFS', '')}
						</td>
						<th>结算方式</th>
						<td>
							${fns:getDictLabelsByCodes(saleContract.settlementMode, 'SJKFS', '')}
						</td>
					</tr>
					<tr>
						<th>交货地点</th>
						<td colspan="3">
							${saleContract.deliveryAddr }
						</td>
					</tr>
					<tr>
						<th>数量验收标准</th>
						<td>
							${fns:getDictLabelByCode(saleContract.quantityAcceptance, 'QUANTITY-ACCEPTANCE-CRITERIA', '')}
						</td>
						<th>质量验收标准</th>
						<td>
							${fns:getDictLabelByCode(saleContract.qualityAcceptance, 'QUALITY-ACCEPTANCE-CRITERIA', '')}
						</td>
					</tr>
					<tr>
						<th>第三方检测机构</th>
						<td>
							${saleContract.thirdPartyTest }
						</td>
						<th>检测费用</th>
						<td>
							${fns:getDictLabelByCode(saleContract.testFee, 'FEE-UNDERTAKE', '')}
						</td>
					</tr>
					<tr>
						<th>港建费</th>
						<td>
							${fns:getDictLabelByCode(saleContract.portFee, 'FEE-UNDERTAKE', '')}
						</td>
						<th>码头费</th>
						<td>
							${fns:getDictLabelByCode(saleContract.dockFee, 'FEE-UNDERTAKE', '')}
						</td>
					</tr>
					<tr>
						<th>运费</th>
						<td>
							${fns:getDictLabelByCode(saleContract.freightFee, 'FEE-UNDERTAKE', '')}
						</td>
						<th>堆存费</th>
						<td>
							${fns:getDictLabelByCode(saleContract.storageFee, 'FEE-UNDERTAKE', '')}
						</td>
					</tr>
					<tr>
						<th>滞期/速遣费</th>
						<td>
							${fns:getDictLabelByCode(saleContract.dispatchFee, 'FEE-UNDERTAKE', '')}
						</td>
						<th>其他费用</th>
						<td>
							${fns:getDictLabelByCode(saleContract.otherFee, 'FEE-UNDERTAKE', '')}
						</td>
					</tr>
					<tr>
						<th>合同数量 </th>
						<td>
							<fmt:formatNumber value="${saleContract.contractQuantity }" pattern="#.##" minFractionDigits="2" />
						</td>
						<th>合同金额</th>
						<td>
							<fmt:formatNumber value="${saleContract.contractAmount }" pattern="#.##" minFractionDigits="2" />
						</td>
					</tr>
				<tr>
					<th>数量结算依据</th>
					<td colspan="3">
						${saleContract.numSettlementBasis}
					</td>
				</tr>
				<tr>
					<th>质量结算依据</th>
					<td colspan="3">
						${saleContract.qualitySettlementBasis}
					</td>
				</tr>
				<tr>
					<th>风险提示</th>
					<td colspan="3">
						${saleContract.riskTip}
					</td>
				</tr>
				<c:if test="${saleContract.pid ne null }">
						<tr>
							<th>变更原因</th>
							<td colspan="3">
							${saleContract.changeReason}
						</td>
						</tr>
					</c:if>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">登记人</th>
						<td width="30%">
							${saleContract.createrName }
						</td>
						<th>登记部门</th>
						<td>${saleContract.createrDept }</td>
					</tr>
					<tr>	
						<th>登记时间</th>
						<td>
							<fmt:formatDate value="${saleContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th>最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleContract.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			
			<div data-options="title: '货物明细', iconCls: false, refreshable: false">
				<table id="dgContractGoods" data-options="nowrap:false"></table>
				<script type="text/javascript">
				$(function(){
					dgContractGoods = $('#dgContractGoods').datagrid({
						method: "get",
						url: '${ctx}/sale/saleContractGoods/forContractId/${saleContract.id }',
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						singleSelect:true,
						extEditing:false,
					    columns:[[    
							{field:'id',title:'id',hidden:true},  
							{field:'goodsNameView',title:'品名',sortable:true,width:15},
							{field:'price',title:'单价',sortable:true,width:10},
							{field:'goodsQuantity',title:'数量',sortable:true,width:10},
							{field:'unitsView',title:'数量单位',sortable:true,width:20},
							{field:'amount',title:'总价',sortable:true,width:10},
							{field:'shipNo',title:'运输工具编号',width:15},
							{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					});
					
				});
				</script>
			</div>
						
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=SaleContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${saleContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<table id="childTBBak" ></table>
			</div>				
		</div>	
</div>

<script type="text/javascript">
function showChange(idValue){
	var d_change=$("#dlg_bak").dialog({   
	    title: '历史记录',    
	    width: 1000,    
	    height: 450,  
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/sale/saleContract/showContractHistory/'+idValue,
	    modal:true,
	    buttons:[
	             {
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_change.panel('close');
			}
		}]
	});
}
$(function(){
	$('#mainDiv').tabs({});
	
	$('#childTBBak').datagrid({
		url:'${ctx}/sale/saleContract/saleHistory/${saleContract.id}',
		method: "get",
	    fit : false,
		fitColumns : true,
		scrollbarSize : 0,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[
			{field:'createDate',title:'变更日期',sortable:true,width:15,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
			{field:'changeReason',title:'变更原因',width:15},
			{field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'seller',title:'卖方',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
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
			{field:'purchaser',title:'买方',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
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
	     	{field:'id',title:'变更记录',width:15,
	     		formatter : function(value, row, index) {
				var str = "";
				str += "<a   class='easyui-linkbutton' iconCls='icon-search' plain='true' href='javascript:void(0)'  onclick='showChange("
						+ value
						+ ");'>查看详情</a>"
				return str;
			}},
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		});
});

</script>
</body>
</html>