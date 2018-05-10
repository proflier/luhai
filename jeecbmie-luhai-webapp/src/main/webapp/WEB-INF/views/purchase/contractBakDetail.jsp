<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
</head>
<body>
<div>
	<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '采购合同主信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
					<table width="98%" class="tableClass" >
							<tr>
								<th width="20%">内部合同号</th>
								<td width="30%">${purchaseContractBak.innerContractNo } 
								<input id="id_bak"  type="hidden"  value="${purchaseContractBak.id }" />
								</td>
								<th width="20%">采购合同号</th>
								<td width="30%">${purchaseContractBak.purchaseContractNo }</td>
							</tr>
							<tr>
								<th  >帐套单位</th>
								<td >
									${mytag:getAffiBaseInfoByCode(purchaseContractBak.setUnit)}
								</td>
								<th  >经营方式 </th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.runMode,'runMode','')}
								</td>
							</tr>
							<tr>
								<th  >卖方</th>
								<td>
									${mytag:getAffiBaseInfoByCode(purchaseContractBak.deliveryUnit)}
								</td>
								<th  >买方</th>
								<td>
									${mytag:getAffiBaseInfoByCode(purchaseContractBak.receivingUnit)}
								</td>
							</tr>
							<tr>
								<th>采购方式</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.tradeWay,'tradeWay','')}
								</td>
								<th  >结算方式</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.payMode,'paymentMethod','')}
								</td>
							</tr>
							<tr id="creditDeadlineAnchor">
								<th>信用证开具期限</th>
								<td colspan="3">
									受载前${purchaseContractBak.creditDeadline }天
								</td>
							</tr>
							 <tr>
								<th  >币种</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.currency,'currency','')}
								</td>
								<th  >付款类型</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.creditType,'sxlx','')}
								</td>
							</tr>
							<tr>
								<th  >应预付额</th>
								<td>
									${purchaseContractBak.prepaidMoney }
								</td>
								<th  >价格条款</th>
								<td >
									${fns:getDictLabelByCode(purchaseContractBak.priceTerm,'jgtk','')}
								</td>
							</tr>
							<tr>
								<th  >保证金</th>
								<td>
									${purchaseContractBak.margin }
								</td>
								<th  >合同金额</th>
								<td>
									${purchaseContractBak.contractAmount }
								</td>
							</tr>
							<tr>
								<th  >订货总量</th>
								<td>
									${purchaseContractBak.contractQuantity }
								</td>
								<th  >数量单位</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.quantityUnit,'sldw','')}
								</td>
							</tr>
							<tr>
								<th  >签约日期 </th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.signingDate }" />
								</td>
								<th>合同有效期</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.contractValidityPeriod }" />
								</td>
							</tr>
							
							<tr>
								<th>运输方式</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.transportCategory,'YSFS','')}
								</td>
								<th>运输编号</th>
								<td>
									${purchaseContractBak.shipNo }
								</td>
							</tr>
							<tr id="deliveryAnchor" >
								<th>交货方式</th>
								<td colspan="3">
									${fns:getDictLabelByCode(purchaseContractBak.deliveryMode,'THFS','')}
									${fns:getDictLabelByCode(purchaseContractBak.deliveryModeDetail,'deliveryModeDetail','')}
								</td>
							</tr>
							<tr>
								<th>发运地点（装运港）</th>
								<td>
									${purchaseContractBak.sendAddr }
								</td>
								<th>交货地点（卸货港）</th>
								<td>
									${purchaseContractBak.deliveryAddr }
								</td>
							</tr>
							<tr>
								<th  >预计发货（装港）时间</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.expectSendDate }" />
								</td>
								<th  >预计到货（卸港）时间</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.expectDeliveryDate }" />
								</td>
							</tr>
							<tr>
								<th  >交货起期</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.deliveryStartDate }" />
								</td>
								<th  >交货止期</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.deliveryTerm }" />
								</td>
							</tr>
							<tr>
								<th  >受载期起始时间</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.transportStartDate }" />
								</td>
								<th  >受载期终止时间</th>
								<td>
									<fmt:formatDate value="${purchaseContractBak.transportTermDate }" />
								</td>
							</tr>
							<tr>
								<th  >业务经办人 </th>
								<td>
									${mytag:getUserByLoginName(purchaseContractBak.businessManager).name}
								</td>
								<th>费用承担人</th>
								<td>
									${mytag:getUserByLoginName(purchaseContractBak.costBearer).name}
								</td>
							</tr>
							<tr>
								<th  >印章类型 </th>
								<td>
									${mytag:getEntityByCode(purchaseContractBak.sealCategory).typeName}
								</td>
								<th  >印章管理员</th>
								<td>
									${purchaseContractBak.sealManager }
								</td>
							</tr>
							<tr>
								<th  >进口国别</th>
								<td colspan="3">
										${mytag:getCountryArea(purchaseContractBak.importCountry).name}
								</td>
							</tr>
							<tr>
								<th  >签约地点</th>
								<td>
									${mytag:getCountryArea(purchaseContractBak.signingPlace).name}
								</td>
								<th  >仲裁地</th>
								<td>
									${mytag:getCountryArea(purchaseContractBak.arbitrationPlace).name}
								</td>
							</tr>
							<tr>
								<th>数量验收标准</th>
								<td>
										${fns:getDictLabelByCode(purchaseContractBak.quantityAcceptance,'QUANTITY-ACCEPTANCE-CRITERIA','')}
								</td>
								<th>质量验收标准</th>
								<td>
										${fns:getDictLabelByCode(purchaseContractBak.qualityAcceptance,'QUALITY-ACCEPTANCE-CRITERIA','')}
								</td>
							</tr>
							<tr>
								<th  >金额溢短装</th>
								<td>
									${purchaseContractBak.moreOrLessAmount }
								</td>
								<th  >数量溢短装</th>
								<td>
									${purchaseContractBak.moreOrLessQuantity }
								</td>
							</tr>
							<tr>
								<th  >合同金额大写 </th>
								<td>
									${purchaseContractBak.blockMoney }
								</td>
								<th  >装/卸率</th>
								<td>
									${purchaseContractBak.unloadingRate }
								</td>
							</tr>
							<tr>
								<th>检测费用</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.testFee,'FEE-UNDERTAKE','')}
								</td>
								<th>港建费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.portFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							<tr>
								<th>码头费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.dockFee,'FEE-UNDERTAKE','')}
								</td>
								<th>运费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.freightFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							<tr>
								<th>堆存费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.storageFee,'FEE-UNDERTAKE','')}
								</td>
								<th>滞期/速遣费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContractBak.dispatchFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							<tr>
								<th>其他费用</th>
								<td colspan="3">
									${fns:getDictLabelByCode(purchaseContractBak.otherFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							
							<tr>
								<th  >检验机构</th>
								<td >
									${purchaseContractBak.checkOrg }
								</td>
							</tr>
							<tr>
								<th  >检验标准</th>
								<td colspan="3">
									${purchaseContractBak.checkStandard }
								</td>
							</tr>
							<tr id="stockTermsAnchor">
								<th  >货权转移条款</th>
								<td colspan="3">
									${purchaseContractBak.stockTerms }
								</td>
							</tr>
							<tr>
								<th  >结算条款 </th>
								<td>
									${purchaseContractBak.settlementTerms }
								</td>
							</tr>
							<tr>
								<th  >付款条款</th>
								<td colspan="3">
									${purchaseContractBak.paymentClause }
								</td>
							</tr>
							<tr>
								<th>备注</th>
								<td colspan="3">
									${purchaseContractBak.comment }
								</td>
							</tr>					
						</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty purchaseContractBak.createrName ? sessionScope.user.name : purchaseContractBak.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty purchaseContractBak.createrDept ? sessionScope.user.organization.orgName : purchaseContractBak.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ purchaseContractBak.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${purchaseContractBak.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '采购信息', iconCls: false, refreshable: false">
				<table id="dgContractGoods_bak" ></table>
			</div>
		</div>	
</div>
<script type="text/javascript">
	var dgContractGoods_bak;
	var idValue;
	initStreamContainer();
	function initStreamContainer(value){
		idValue = $('#id_bak').val();
		dgContractGoods_bak=$('#dgContractGoods_bak').datagrid({  
			method: "get",
			url:'${ctx}/purchase/purchaseContractGoods/getContractGodds/'+idValue,
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
				{field:'goodsNameView',title:'品名',sortable:true,width:20},
				{field:'purchasePrice',title:'采购单价',sortable:true,width:20},
				{field:'goodsQuantity',title:'数量',sortable:true,width:20},
				{field:'amount',title:'金额',sortable:true,width:20},
				{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
		    ]],
		    sortName:'id',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    toolbar:'#tbGoods'
		});
	}


// // 	进口国别
$.ajax({
		type:'get',
		url:'${ctx}/baseInfo/baseUtil/countryAreaNameShow/${purchaseContractBak.importCountry}',
		success:function(data){
			if(data!=null){
				$('#importCountry_bak').text(data);
			}
		}
	});
// //签约地点
$.ajax({
		type:'get',
		url:'${ctx}/baseInfo/baseUtil/countryAreaNameShow/${purchaseContractBak.signingPlace}',
		success:function(data){
			if(data!=null){
				$('#signingPlace_bak').text(data);
			}
		}
	});




// //供应商下拉菜单
if('${purchaseContractBak.deliveryUnit }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${purchaseContractBak.deliveryUnit }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#deliveryUnit_bak').html(data);
		}
	});
}

// //订货单位
if('${purchaseContractBak.orderUnit }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${purchaseContractBak.orderUnit }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#orderUnit_bak').html(data);
		}
	});
}

// //收货单位
if('${purchaseContractBak.receivingUnit }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${purchaseContractBak.receivingUnit }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#receivingUnit_bak').html(data);
		}
	});
}

//签约地点
$.ajax({
		type:'get',
		url:'${ctx}/baseInfo/baseUtil/countryAreaNameShow/${purchaseContractBak.arbitrationPlace}',
		success:function(data){
			if(data!=null){
				$('#arbitrationPlace_bak').text(data);
			}
		}
	});
//检验机构
if('${purchaseContractBak.checkOrg }'!=''){
	$.ajax({
		url : '${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/${purchaseContractBak.checkOrg }' ,
		type : 'get',
		cache : false,
		success : function(data) {
			$('#checkOrg_bak').html(data);
		}
	});
}
function showChange(idValue){
	d_change=$("#dlg_bak").dialog({   
	    title: '变更记录',    
	    width: 1000,    
	    height: 450,  
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/purchase/purchaseContract/showChange/'+idValue,
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

if('${purchaseContractBak.moreOrLessAmount }'!=''){
	$('#moreOrLessAmount_bak').text('${purchaseContractBak.moreOrLessAmount }%');
}
if('${purchaseContractBak.moreOrLessQuantity }'!=''){
	$('#moreOrLessQuantity_bak').text('${purchaseContractBak.moreOrLessQuantity }%');
}

</script>
</body>
</html>