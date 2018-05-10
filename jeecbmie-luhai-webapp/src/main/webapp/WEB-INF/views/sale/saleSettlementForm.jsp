<%@ page import="com.cbmie.lh.sale.entity.SaleSettlement"%>
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
	<form id="mainform" action="${ctx}/sale/saleSettlement/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${saleSettlement.id }" />
		<input type="hidden" id="relLoginNames" name="relLoginNames" value="${saleSettlement.relLoginNames }"/>
		<input type="hidden" id="manageMode" name="manageMode" value="${saleSettlement.manageMode }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售结算信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">结算编号</th>
						<td width="30%">
							<input id="saleSettlementNo" name="saleSettlementNo" type="text" value="${saleSettlement.saleSettlementNo}" class="easyui-validatebox" data-options="required:true" />
						</td>
						<th width="20%">销售合同号</th>
						<td width="30%">
							<input id="saleContractNo" readonly="readonly" name="saleContractNo" type="text" value="${saleSettlement.saleContractNo}" class="easyui-validatebox" data-options="required:true" />
							<a id="toSaleListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSaleList()">销售合同</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-zoom" plain="true" onclick="toSaleContract()"/>
						</td>
					</tr>
					<tr>
						<th width="20%">结算日期</th>
						<td width="30%">
							<input id="settlementDate" name="settlementDate" type="text" value="<fmt:formatDate value="${saleSettlement.settlementDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th  width="20%">收货单位</th>
						<td width="30%">
<%-- 							<mytag:combobox name="receivingUnit" value="${saleSettlement.receivingUnit}" type="customer" parameters="10230003" hightAuto="false"/> --%>
							<mytag:combobox name="receivingUnit" value="${saleSettlement.receivingUnit}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/>
						</td>
					</tr>
					<tr>
						<th width="20%">发货开始日期</th>
						<td width="30%">
							<input id="sendBeginDate" name="sendBeginDate" type="text" value="<fmt:formatDate value="${saleSettlement.sendBeginDate }" />"
								class="Wdate" />
						</td>
						<th  width="20%">发货结束日期</th>
						<td width="30%">
							<input id="sendEndDate" name="sendEndDate" type="text" value="<fmt:formatDate value="${saleSettlement.sendEndDate }" />"  
								class="Wdate" />
						</td>
					</tr>
					<tr>
						<th width="20%">收货开始日期</th>
						<td width="30%">
							<input id="receiveBeginDate" name="receiveBeginDate" type="text" value="<fmt:formatDate value="${saleSettlement.receiveBeginDate }" />"
								class="Wdate" />
						</td>
						<th  width="20%">收货结束日期</th>
						<td width="30%">
							<input id="receiveEndDate" name="receiveEndDate" type="text" value="<fmt:formatDate value="${saleSettlement.receiveEndDate }" />" 
								class="Wdate" />
						</td>
					</tr>
					<tr>
						<th  width="20%">奖罚金额</th>
						<td >
							<input id="sanctionPrice" name="sanctionPrice" value="${saleSettlement.sanctionPrice}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
						<th  >业务经办人 </th>
						<td>
						<mytag:combotree name="businessManager" value="${saleSettlement.businessManager}"  required="true" disabled="true" type="userTree" />
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" id="goodsField">
					<legend>结算明细</legend>
					<div id="tbGoods" style="padding:5px;height:auto">
				     <div>	
						<a id="addGoods" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						<span class="toolbar-item dialog-tool-separator"></span>
				      	<a id="updateGoods" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				   		<span class="toolbar-item dialog-tool-separator"></span>
				   		<a id="deleteGoods" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
				   		<span class="toolbar-item dialog-tool-separator"></span>
				   		<a id="settleGoods" href="#" class="easyui-linkbutton" iconCls="icon-standard-accept" plain="true">关联出库</a>
				   		<span class="toolbar-item dialog-tool-separator"></span>
				   		<a id="relationPreInvoice" href="#" class="easyui-linkbutton" iconCls="icon-standard-page-edit" plain="true">关联预开票</a>
				     </div>
					</div>
					<table id="dgSettlementGoods"></table>
				</fieldset>
				<fieldset class="fieldsetClass" id="outStockField">
					<legend>出库信息</legend>
					<table width="98%" class="tableClass" id="OutStockList">
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" id="preInvoiceField">
					<legend>预开票信息</legend>
					<table width="98%" class="tableClass" id="preInvoiceList">
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" id="qualityInspectionField">
					<legend>质检信息</legend>
					<table width="98%" class="tableClass" id="qualityInspectionList">
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${saleSettlement.createrNo }"/>
							<input type="hidden" name="createrName" value="${saleSettlement.createrName }"/>
							<input type="hidden" name="createrDept" value="${saleSettlement.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${saleSettlement.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${saleSettlement.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${saleSettlement.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleSettlement.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleSettlement.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleSettlement.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					</table>
				</fieldset>
			</div>
			
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=SaleSettlement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${saleSettlement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
						
		</div>
	</form>
</div>
<script type="text/javascript">
		function afterMainSubmitSuccess(){
			sale_settlement_goods.init();
		}
		var sale_settlement_goods = {
				init:function(){
					this.listGoods();
					this.listOutStock();
					this.listPreInvoice();
					this.listQualityInspect();
				},
				outStock_list:{},
				listOutStock:function(){
					this.outStock_list = $('#OutStockList').datagrid({
						method: "get",
						url: $('#id').val() ? '${ctx}/sale/settlementOutRel/listOutsForSettlement/' + $('#id').val() : '',
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						extEditing:false,
						showFooter: true,
						 columns:[[    
									{field:'relId',title:'relId',hidden:true}, 
									{field:'portView',title:'仓库',width:20,
// 										formatter: function(value,row,index){
// 											var val;
// 											if(value!=''&&value!=null){
// 												$.ajax({
// 													type:'GET',
// 													async: false,
// 													url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 													success: function(data){
// 														if(data != null){
// 															val = data;
// 														} else {
// 															val = '';
// 														}
// 													}
// 												});
// 												return val;
// 											}else{
// 												return '';
// 											}
// 										}	
									},
									{field:'voyView',title:'船名',width:40,
// 					            		formatter: function(value,row,index){
// 											var val;
// 											if(value!=''&& value!=null){
// 												$.ajax({
// 													type:'GET',
// 													async: false,
// 													url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
// 													success: function(data){
// 														if(data != null){
// 															val = data;
// 														} else {
// 															val = '';
// 														}
// 													}
// 												});
// 												return val;
// 											}else{
// 												return '';
// 											}
// 										}
									},
									{field:'outStockNo',title:'出库单号',width:20},
									{field:'outStockDate',title:'出库日期',sortable:true,width:20,
										formatter:function(value,row,index){
											if(value == null){
												return null;
											}
						            		var time = new Date(value);
						            		return time.format("yyyy-MM-dd");
						            	}},
									{field:'goodsNoView',title:'品名',width:20,
// 										formatter: function(value,row,index){
// 											var val;
// 											if(value!=''&&value!=null){
// 												$.ajax({
// 													type:'GET',
// 													async: false,
// 													url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
// 													success: function(data){
// 														if(data != null){
// 															val = data;
// 														} else {
// 															val = '';
// 														}
// 													}
// 												});
// 												return val;
// 											}else{
// 												return '';
// 											}
// 										}		
									},
									{field:'quantity',title:'数量',width:20,align:'right',
										formatter: function(value,row,index){
											if(isNaN(value)||value==null){
												return 0.000; //如果不是数字，返回0.000
											}else{
												value = parseFloat(value);
												return(value.toFixed(3)); 
											}
										}		
									},
									{field:'transTypeView',title:'运输方式',width:20,
// 										formatter: function(value,row,index){
// 											var val;
// 											if(value!=''&&value!=null){
// 												$.ajax({
// 													type:'GET',
// 													async: false,
// 													url : '${ctx}/system/dictUtil/getDictNameByCode/YSFS/'+value ,
// 													success: function(data){
// 														if(data != null){
// 															val = data;
// 														} else {
// 															val = '';
// 														}
// 													}
// 												});
// 												return val;
// 											}else{
// 												return '';
// 											}
// 										}		
									}
							    ]],
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					});
				},
				preInvoice_list:{},
				listPreInvoice:function(){
					this.preInvoice_list = $('#preInvoiceList').datagrid({
						method: "get",
						url: $('#saleSettlementNo').val() ? '${ctx}/sale/saleInvoice/jsonNoPermission' : '',
						queryParams:{filter_EQS_invoiceNo:$('#saleSettlementNo').val(),filter_EQS_preBilling:'1'},
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						extEditing:false,
						 columns:[[    
									{field:'summary',title:'描述',width:40}, 
									{field:'goodsName',title:'品名',width:30},
									{field:'createDate',title:'制单日期',sortable:true,width:20,
										formatter:function(value,row,index){
											if(value == null){
												return null;
											}
						            		var time = new Date(value);
						            		return time.format("yyyy-MM-dd");
						            	}
									}
							    ]],
							    sortName:'id',
							    enableHeaderClickMenu: false,
							    enableHeaderContextMenu: false,
							    enableRowContextMenu: false
					});
				},
				qualityInspect_list:{},
				listQualityInspect:function(){
					this.qualityInspect_list = $('#qualityInspectionList').datagrid({
						method: "get",
						url: $('#saleContractNo').val() ? '${ctx}/stock/qualityInspection/outStockList' : '',
						queryParams:{filter_EQS_contractNo:$('#saleContractNo').val(),filter_EQS_inspectionLink:'ck'},
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						extEditing:false,
					    columns:[[    
							{field:'inspectionNo',title:'质检编码',sortable:true,width:10}, 
							{field:'contractNo',title:'合同号',sortable:true,width:10},
							{field:'billOrReleaseNo',title:'提单号/放货编号',sortable:true,width:10},
							{field:'goodsNameView',title:'品名',sortable:true,width:10,
// 								formatter: function(value,row,index){
// 									var val;
// 									if(value!=''&&value!=null){
// 										$.ajax({
// 											type:'GET',
// 											async: false,
// 											url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
// 											success: function(data){
// 												if(data != null){
// 													val = data;
// 												} else {
// 													val = '';
// 												}
// 											}
// 										});
// 										return val;
// 									}else{
// 										return '';
// 									}
// 								}	
							},
							{field:'shipNoView',title:'运输工具编号',width:10},
							{field:'inspectionAgency',title:'检验机构',width:10},
							{field:'portOrWarehouse',title:'港口/仓库名称',width:10},
							{field:'qualityIndexStr',title:'质检信息',width:30}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					});
				},
				goods_list:'',
				listGoods:function(){
					this.goods_list = $('#dgSettlementGoods').datagrid({
						method: "get",
						url: $('#id').val() ? '${ctx}/sale/saleSettlementGoods/list/' + $('#id').val() : '',
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						singleSelect:true,
						extEditing:false,
						showFooter: true,
					    columns:[[    
							{field:'id',title:'id',hidden:true},
							{field:'goodsNameView',title:'品名',sortable:true,width:20,
// 								formatter: function(value,row,index){
// 									var val;
// 									if(value!=''&&value!=null){
// 										$.ajax({
// 											type:'GET',
// 											async: false,
// 											url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
// 											success: function(data){
// 												if(data != null){
// 													val = data;
// 												} else {
// 													val = '';
// 												}
// 											}
// 										});
// 										return val;
// 									}else{
// 										return '';
// 									}
// 								}		
							},
							{field:'sendQuantity',title:'发货数量',sortable:true,width:20,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.000; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(3)); 
									}
								}	
							},
							{field:'receiveQuantity',title:'收货数量',sortable:true,width:20,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.000; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(3)); 
									}
								}	
							},
							{field:'settlementQuantity',title:'结算数量',sortable:true,width:20,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.000; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(3)); 
									}
								}	
							},
							{field:'settlementPrice',title:'结算单价',sortable:true,width:20,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.00; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(2)); 
									}
								}	
							},
							{field:'deductionPrice',title:'扣减金额',sortable:true,width:20,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.00; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(2)); 
									}
								}	
							},
							{field:'receivedPrice',title:'已收金额',sortable:true,width:20,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.00; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(2)); 
									}
								}	
							},
							{field:'receivablePrice',title:'应收金额',sortable:true,width:40,
								formatter: function(value,row,index){
									if(isNaN(value)||value==null){
										return 0.00; //如果不是数字，返回0.000
									}else{
										value = parseFloat(value);
										return(value.toFixed(2)); 
									}
								}	
							}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false,
					    toolbar:'#tbGoods'
					});
				},
				goods_form:'',
				addGoods:function(){
					var idValue = $('#id').val();
					if(idValue=="" || idValue==null){
						$.messager.alert('提示','请先保存主表信息！','info');
						return;
					}
					sale_settlement_goods.goods_form = $("#dlg_goods").dialog({
						method:'GET',
					    title: '新增结算信息',    
					    width: 700,    
					    height: 400,     
					    href:'${ctx}/sale/saleSettlementGoods/create/'+idValue,
					    maximizable:false,
					    modal:true,
					    buttons:[{
							text:'保存',iconCls:'icon-save',
							handler:function(){
								$('#settlementGoodsForm').submit(); 
							}
						},{
							text:'关闭',iconCls:'icon-cancel',
							handler:function(){
								sale_settlement_goods.goods_form.panel('close');
							}
						}]
					});
				},
				settleGoods:function(){
					var idValue = $('#id').val();
					if(idValue=="" || idValue==null){
						$.messager.alert('提示','请先保存主表信息！','info');
						return;
					}
					var rows_rel = sale_settlement_goods.outStock_list.datagrid("getRows");
					var outstockDetailId_array = new Array();
					for(var i=0;i<rows_rel.length;i++){
						outstockDetailId_array.push(rows_rel[i].outstockDetailId);
					}
					sale_settlement_goods.goods_form = $("#dlg_goods").dialog({
						method:'GET',
					    title: '出库信息',    
					    width: 700,    
					    height: 400,     
					    href:'${ctx}/sale/settlementOutRel/toOutsSelectPage/'+idValue,
					    maximizable:false,
					    modal:true,
					    queryParams: { existIds: outstockDetailId_array },
					    buttons:[{
							text:'保存',iconCls:'icon-save',
							handler:function(){
								var outStockIds = getSelectedOutIds();
								$.ajax({
									async : false,
									type : 'POST',
									data : JSON.stringify(outStockIds),
									contentType : 'application/json;charset=utf-8', //必须
									url : "${ctx}/sale/settlementOutRel/saveRel/"+$('#id').val(),
									success : function(data) {
										if (data == 'success') {
											sale_settlement_goods.outStock_list.datagrid("reload");
											parent.$.messager.show({
												title : "提示",
												msg : "操作成功！",
												position : "bottomRight"
											});
										} else {
											$.easyui.messager.alert(data);
										}
									}
								});
								sale_settlement_goods.goods_form.panel('close');
							}
						},{
							text:'关闭',iconCls:'icon-cancel',
							handler:function(){
								sale_settlement_goods.goods_form.panel('close');
							}
						}]
					});
				},
				relationPreInvoice:function(){
					var idValue = $('#id').val();
					if(idValue=="" || idValue==null){
						$.messager.alert('提示','请先保存主表信息！','info');
						return;
					}
					sale_settlement_goods.goods_form = $("#dlg_goods").dialog({
						method:'GET',
					    title: '发票信息',    
					    width: 700,    
					    height: 400,     
					    href:'${ctx}/sale/saleSettlement/toPreInvoicePage/'+idValue,
					    maximizable:false,
					    modal:true,
					    buttons:[{
							text:'保存',iconCls:'icon-save',
							handler:function(){
								var invoiceIds = getSelectedInvoiceIds();
								$.ajax({
									async : false,
									type : 'POST',
									data : JSON.stringify(invoiceIds),
									contentType : 'application/json;charset=utf-8', //必须
									url : "${ctx}/sale/saleSettlement/backRelationInvoice/"+$('#id').val(),
									success : function(data) {
										if (data == 'success') {
											parent.$.messager.show({
												title : "提示",
												msg : "操作成功！",
												position : "bottomRight"
											});
											sale_settlement_goods.preInvoice_list.datagrid("reload");
										} else {
											$.easyui.messager.alert(data);
										}
									}
								});
								sale_settlement_goods.goods_form.panel('close');
							}
						},{
							text:'关闭',iconCls:'icon-cancel',
							handler:function(){
								sale_settlement_goods.goods_form.panel('close');
							}
						}]
					});
				},
				updateGoods:function(){
					var row = sale_settlement_goods.goods_list.datagrid('getSelected');
					if(rowIsNull(row)) return;
					sale_settlement_goods.goods_form = $("#dlg_goods").dialog({   
					    title: '修改结算信息',    
					    width: 700,    
					    height: 400,
					    href:'${ctx}/sale/saleSettlementGoods/update/'+row.id,
					    maximizable:false,
					    modal:true,
					    buttons:[{
							text:'保存',iconCls:'icon-save',
							handler:function(){
								$('#settlementGoodsForm').submit(); 
							}
						},{
							text:'关闭',iconCls:'icon-cancel',
							handler:function(){
								sale_settlement_goods.goods_form.panel('close');
							}
						}]
					});
				},
				deleteGoods:function(){
					var row = sale_settlement_goods.goods_list.datagrid('getSelected');
					if(rowIsNull(row)) return;
					parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
						if (data){
							$.ajax({
								type:'get',
								url:'${ctx}/sale/saleSettlementGoods/delete/'+row.id,
								success: function(data){
									var data = eval('(' + data + ')');
									if(data.returnFlag=='success'){
										sale_settlement_goods.goods_list.datagrid('clearSelections');
										sale_settlement_goods.goods_list.datagrid('reload');
										parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
										return true;
									}else if(data.returnFlag=='fail'){
										parent.$.messager.alert(data.returnMsg);
										return false;
									}
								}
							});
						} 
					});
				}
		}
$(function(){
	sale_settlement_goods.init();
	//弹窗增加信息
	 $("#addGoods").on("click", sale_settlement_goods.addGoods); 
	//弹窗选择信息
	 $("#settleGoods").on("click", sale_settlement_goods.settleGoods); 
	 
	 $("#relationPreInvoice").on("click", sale_settlement_goods.relationPreInvoice); 
	//弹窗修改信息
	$("#updateGoods").on("click", sale_settlement_goods.updateGoods);
	//删除商品
	$("#deleteGoods").on("click", sale_settlement_goods.deleteGoods);
	$('#sendBeginDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'sendEndDate\')}',onpicked:function(){deliveryTerm.click();}});
	});
	$('#sendEndDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'sendBeginDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
	$('#receiveBeginDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'receiveEndDate\')}',onpicked:function(){deliveryTerm.click();}});
	});
	$('#receiveEndDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'receiveBeginDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
});
$(function(){
	if('${action}' == 'view') {
		$("#tbGoods").hide();
		$("#toSaleListId").hide();
	}
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
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
     		successTipNew(data,sale_settlement_page.settlement_list);
     		var data = eval('(' + data + ')');
	    	if(data.currnetSequence!=null){
	    		$('#saleSettlementNo').val(data.currnetSequence);
	    	}
     		afterMainSubmitSuccess();
			$.easyui.loaded();
	    }
	});
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
});
</script>
</body>
</html>