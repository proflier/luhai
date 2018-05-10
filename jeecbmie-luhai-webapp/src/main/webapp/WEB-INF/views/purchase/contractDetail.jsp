<%@page import="com.cbmie.lh.purchase.entity.PurchaseContract"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld"%>
</head>
<body>
	<div id="detailLayout" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'east',collapsible:true,split:true,collapsed:true,border: false,title:'关联列表'" style="width: 600px">
			<input id="innerContractNo" type="hidden" value="${purchaseContract.innerContractNo }" />
			<table id="dg_relation" ></table>
			<div id="dlg_relation"></div>
		</div>
		<div onclick="centerDetail()" data-options="region:'center'">
			<div id="mainDiv" class="" data-options="border:false">
				<div
					data-options="title: '采购合同主信息', iconCls: false, refreshable: false">
					<fieldset class="fieldsetClass">
						<legend>基本信息</legend>
						<table width="98%" class="tableClass" >
							<tr>
								<th width="20%">内部合同号</th>
								<td width="30%">${purchaseContract.innerContractNo } 
								<input id="pruchaseId" name="id" type="hidden" value="${purchaseContract.id }" />
								</td>
								<th width="20%">采购合同号</th>
								<td width="30%">${purchaseContract.purchaseContractNo }</td>
							</tr>
							<tr>
								<th  >帐套单位</th>
								<td >
									${mytag:getAffiBaseInfoByCode(purchaseContract.setUnit)}
								</td>
								<th  >经营方式 </th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.runMode,'runMode','')}
								</td>
							</tr>
							<tr>
								<th  >卖方</th>
								<td>
									${mytag:getAffiBaseInfoByCode(purchaseContract.deliveryUnit)}
								</td>
								<th  >买方</th>
								<td>
									${mytag:getAffiBaseInfoByCode(purchaseContract.receivingUnit)}
								</td>
							</tr>
							<tr>
								<th>采购方式</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.tradeWay,'tradeWay','')}
								</td>
								<th  >结算方式</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.payMode,'paymentMethod','')}
								</td>
							</tr>
							<tr id="creditDeadlineAnchor">
								<th>信用证开具期限</th>
								<td colspan="3">
									受载前${purchaseContract.creditDeadline }天
								</td>
							</tr>
							 <tr>
								<th  >币种</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.currency,'currency','')}
								</td>
								<th  >付款类型</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.creditType,'sxlx','')}
								</td>
							</tr>
							<tr>
								<th  >应预付额</th>
								<td>
									${purchaseContract.prepaidMoney }
								</td>
								<th  >价格条款</th>
								<td >
									${fns:getDictLabelByCode(purchaseContract.priceTerm,'jgtk','')}
								</td>
							</tr>
							<tr>
								<th  >保证金</th>
								<td>
									${purchaseContract.margin }
								</td>
								<th  >合同金额</th>
								<td>
									${purchaseContract.contractAmount }
								</td>
							</tr>
							<tr>
								<th  >订货总量</th>
								<td>
									${purchaseContract.contractQuantity }
								</td>
								<th  >数量单位</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.quantityUnit,'sldw','')}
								</td>
							</tr>
							<tr>
								<th  >签约日期 </th>
								<td>
									<fmt:formatDate value="${purchaseContract.signingDate }" />
								</td>
								<th>合同有效期</th>
								<td>
									<fmt:formatDate value="${purchaseContract.contractValidityPeriod }" />
								</td>
							</tr>
							
							<tr>
								<th>运输方式</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.transportCategory,'YSFS','')}
								</td>
								<th>运输编号</th>
								<td>
									${purchaseContract.shipNo }
								</td>
							</tr>
							<tr id="deliveryAnchor" >
								<th>交货方式</th>
								<td colspan="3">
									${fns:getDictLabelByCode(purchaseContract.deliveryMode,'THFS','')}
									${fns:getDictLabelByCode(purchaseContract.deliveryModeDetail,'deliveryModeDetail','')}
								</td>
							</tr>
							<tr>
								<th>发运地点（装运港）</th>
								<td>
									${purchaseContract.sendAddr }
								</td>
								<th>交货地点（卸货港）</th>
								<td>
									${purchaseContract.deliveryAddr }
								</td>
							</tr>
							<tr>
								<th  >预计发货（装港）时间</th>
								<td>
									<fmt:formatDate value="${purchaseContract.expectSendDate }" />
								</td>
								<th  >预计到货（卸港）时间</th>
								<td>
									<fmt:formatDate value="${purchaseContract.expectDeliveryDate }" />
								</td>
							</tr>
							<tr>
								<th  >交货起期</th>
								<td>
									<fmt:formatDate value="${purchaseContract.deliveryStartDate }" />
								</td>
								<th  >交货止期</th>
								<td>
									<fmt:formatDate value="${purchaseContract.deliveryTerm }" />
								</td>
							</tr>
							<tr>
								<th  >受载期起始时间</th>
								<td>
									<fmt:formatDate value="${purchaseContract.transportStartDate }" />
								</td>
								<th  >受载期终止时间</th>
								<td>
									<fmt:formatDate value="${purchaseContract.transportTermDate }" />
								</td>
							</tr>
							<tr>
								<th  >业务经办人 </th>
								<td>
									${mytag:getUserByLoginName(purchaseContract.businessManager).name}
								</td>
								<th>费用承担人</th>
								<td>
									${mytag:getUserByLoginName(purchaseContract.costBearer).name}
								</td>
							</tr>
							<tr>
								<th  >印章类型 </th>
								<td>
									${mytag:getEntityByCode(purchaseContract.sealCategory).typeName}
								</td>
								<th  >印章管理员</th>
								<td>
									${purchaseContract.sealManager }
								</td>
							</tr>
							<tr>
								<th  >进口国别</th>
								<td colspan="3">
										${mytag:getCountryArea(purchaseContract.importCountry).name}
								</td>
							</tr>
							<tr>
								<th  >签约地点</th>
								<td>
									${mytag:getCountryArea(purchaseContract.signingPlace).name}
								</td>
								<th  >仲裁地</th>
								<td>
									${mytag:getCountryArea(purchaseContract.arbitrationPlace).name}
								</td>
							</tr>
							<tr>
								<th>数量验收标准</th>
								<td>
										${fns:getDictLabelByCode(purchaseContract.quantityAcceptance,'QUANTITY-ACCEPTANCE-CRITERIA','')}
								</td>
								<th>质量验收标准</th>
								<td>
										${fns:getDictLabelByCode(purchaseContract.qualityAcceptance,'QUALITY-ACCEPTANCE-CRITERIA','')}
								</td>
							</tr>
							<tr>
								<th  >金额溢短装</th>
								<td>
									${purchaseContract.moreOrLessAmount }
								</td>
								<th  >数量溢短装</th>
								<td>
									${purchaseContract.moreOrLessQuantity }
								</td>
							</tr>
							<tr>
								<th  >合同金额大写 </th>
								<td>
									${purchaseContract.blockMoney }
								</td>
								<th  >装/卸率</th>
								<td>
									${purchaseContract.unloadingRate }
								</td>
							</tr>
							<tr>
								<th>检测费用</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.testFee,'FEE-UNDERTAKE','')}
								</td>
								<th>港建费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.portFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							<tr>
								<th>码头费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.dockFee,'FEE-UNDERTAKE','')}
								</td>
								<th>运费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.freightFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							<tr>
								<th>堆存费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.storageFee,'FEE-UNDERTAKE','')}
								</td>
								<th>滞期/速遣费</th>
								<td>
									${fns:getDictLabelByCode(purchaseContract.dispatchFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							<tr>
								<th>其他费用</th>
								<td colspan="3">
									${fns:getDictLabelByCode(purchaseContract.otherFee,'FEE-UNDERTAKE','')}
								</td>
							</tr>
							
							<tr>
								<th  >检验机构</th>
								<td >
									${purchaseContract.checkOrg }
								</td>
							</tr>
							<tr>
								<th  >检验标准</th>
								<td colspan="3">
									${purchaseContract.checkStandard }
								</td>
							</tr>
							<tr id="stockTermsAnchor">
								<th  >货权转移条款</th>
								<td colspan="3">
									${purchaseContract.stockTerms }
								</td>
							</tr>
							<tr>
								<th  >结算条款 </th>
								<td>
									${purchaseContract.settlementTerms }
								</td>
							</tr>
							<tr>
								<th  >付款条款</th>
								<td colspan="3">
									${purchaseContract.paymentClause }
								</td>
							</tr>
							<tr>
								<th>备注</th>
								<td colspan="3">
									${purchaseContract.comment }
								</td>
							</tr>					
						</table>
					</fieldset>
					<fieldset class="fieldsetClass">
						<legend>系统信息</legend>
						<table width="98%" class="tableClass">
							<tr>
								<th width="20%">登记人</th>
								<td width="30%">${empty purchaseContract.createrName ? sessionScope.user.name : purchaseContract.createrName }</td>
								<th width="20%">登记部门</th>
								<td width="30%">${empty purchaseContract.createrDept ? sessionScope.user.organization.orgName : purchaseContract.createrDept }</td>
							</tr>
							<tr>
								<th width="20%">登记时间</th>
								<td width="30%"><fmt:formatDate
										value="${ purchaseContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<th width="20%">最后修改时间</th>
								<td width="30%"><fmt:formatDate
										value="${purchaseContract.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</table>
					</fieldset>
					<c:if test="${purchaseContract.pid ne null }">
					<fieldset id="changeReasonField"  class="fieldsetClass" >
						<legend>变更原因</legend>
							<div >
								${purchaseContract.changeReason }
							</div>
					</fieldset>
					</c:if>
					<fieldset id="closeOrOpenField" style="display: none;"
						class="fieldsetClass">
						<legend>合同状态</legend>
						<div>
							<input id="closeOrOpen" name="closeOrOpen" type="text"
								value="${purchaseContract.closeOrOpen }" class="easyui-combobox"
								data-options="valueField: 'id',textField: 'value',data: [{id: '1',value: '运行中'},{id: '0',value: '关闭'}]" />
						</div>
					</fieldset>
					<fieldset class="fieldsetClass" >
						<legend>相关员工</legend>
						<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
					</fieldset>
<%-- 					<%@ include file="/WEB-INF/views/relationDetail/relationDetail.jsp"%> --%>
					<jsp:include page="/WEB-INF/views/relationDetail/relationDetail.jsp" >
						<jsp:param value="purchaseContract,saleContract,saleDelivery,outStock,saleInvoice,paymentConfirm" name="disView"/>
					</jsp:include>
				</div>
				<div
					data-options="title: '采购信息', iconCls: false, refreshable: false">
					<table id="dgContractGoods"></table>
				</div>
				<div
					data-options="title: '变更记录', iconCls: false, refreshable: false">
					<table id="childTBBak"></table>
					<div id="dlg_bak"></div>
				</div>
				<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<jsp:include page="/WEB-INF/views/accessory/childAccList4Detail.jsp" >
					<jsp:param value="${purchaseContract.id}" name="accParentId"/>
					<jsp:param value="com_cbmie_lh_purchase_entity_PurchaseContract" name="accParentEntity"/>
					<jsp:param value="dgAcc_purchase" name="dgAcc"/>
				</jsp:include>
				<div id="dgAcc_purchase"  class="tableClass"></div>
			</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var tabIndex = 0;
			$('#mainDiv').tabs({
				onSelect : function(title, index) {
					// 	    	if("到单信息"==title){
					// 	    		toBills();
					// 	    	}
				}
			});
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTipNew(data, dg);
				}
			});

			if ("closeOrOpen" == "${action}") {
				$('#closeOrOpenField').show();
			}

		});
		/**模块质检关联用字段*/
		var innerContractNo = '${purchaseContract.innerContractNo }';
		var dgContractGoods;
		var idValue;
		initStreamContainer();
		function initStreamContainer(value) {
			//进行编辑时使用方法
			var urlValue;
			idValue = $('#pruchaseId').val();
			if (idValue != null && idValue != "") {
				urlValue = '${ctx}/purchase/purchaseContractGoods/getContractGodds/'
						+ idValue;
			} else {
				urlValue = "";
			}
			dgContractGoods = $('#dgContractGoods')
					.datagrid(
							{
								method : "get",
								url : urlValue,
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
											field : 'id',
											title : 'id',
											hidden : true
										},
										{
											field : 'goodsNameView',
											title : '品名',
											sortable : true,
											width : 20}, 
										{
											field : 'purchasePrice',
											title : '采购单价',
											sortable : true,
											width : 20
										}, {
											field : 'goodsQuantity',
											title : '数量',
											sortable : true,
											width : 20
										}, {
											field : 'amount',
											title : '金额',
											sortable : true,
											width : 20
										}, {
											field : 'indicatorInformation',
											title : '指标信息',
											sortable : true,
											width : 40
										} ] ],
								sortName : 'id',
								enableHeaderClickMenu : false,
								enableHeaderContextMenu : false,
								enableRowContextMenu : false,
								toolbar : '#tbGoods'
							});
		}

		var childTBBak;
		$(function() {
			childTBMxBak = $('#childTBBak')
					.datagrid(
							{
								url : '${ctx}/purchase/purchaseContract/getPurchaseContractBak/'
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
										{
											field : 'innerContractNo',
											title : '内部合同号',
											sortable : true,
											width : 25
										},
										{
											field : 'purchaseContractNo',
											title : '采购合同号',
											sortable : true,
											width : 25
										},
										{
											field : 'deliveryUnit',
											title : '供货单位',
											sortable : true,
											width : 25,
											formatter : function(value, row,
													index) {
												var val;
												if (value != ''
														&& value != null) {
													$
															.ajax({
																type : 'GET',
																async : false,
																url : "${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"
																		+ value,
																success : function(
																		data) {
																	if (data != null) {
																		val = data;
																	} else {
																		val = '';
																	}
																}
															});
													return val;
												} else {
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
						href : '${ctx}/purchase/purchaseContract/showChange/'
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