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
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售结算信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">结算编号</th>
						<td width="30%">
							${saleSettlement.saleSettlementNo}
						</td>
						<th width="20%">销售合同号</th>
						<td width="30%">
							${saleSettlement.saleContractNo}
						</td>
					</tr>
					<tr>
						<th width="20%">结算日期</th>
						<td width="30%">
							<fmt:formatDate value="${saleSettlement.settlementDate }" />
						</td>
						<th  width="20%">收货单位</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleSettlement.receivingUnit)}
						</td>
					</tr>
					<tr>
						<th width="20%">发货开始日期</th>
						<td width="30%">
							<fmt:formatDate value="${saleSettlement.sendBeginDate }" />
						</td>
						<th  width="20%">发货结束日期</th>
						<td width="30%">
							<fmt:formatDate value="${saleSettlement.sendEndDate }" />
						</td>
					</tr>
					<tr>
						<th width="20%">收货开始日期</th>
						<td width="30%">
							<fmt:formatDate value="${saleSettlement.receiveBeginDate }" />
						</td>
						<th  width="20%">收货结束日期</th>
						<td width="30%">
							<fmt:formatDate value="${saleSettlement.receiveEndDate }" />
						</td>
					</tr>
					<tr>
						<th  width="20%">奖罚金额</th>
						<td >
							 <fmt:formatNumber value="${saleSettlement.sanctionPrice }" pattern="##.00#"/> 
						</td>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(saleSettlement.businessManager).name}
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" id="goodsField">
					<legend>结算明细</legend>
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
				<div id="dgAcc"  class="tableClass"></div>
			</div>
						
		</div>
	</form>
</div>
<script type="text/javascript">
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
						url: '${ctx}/sale/settlementOutRel/listOutsForSettlement/${saleSettlement.id }',
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
						url:  '${ctx}/sale/saleInvoice/jsonNoPermission',
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
						url: '${ctx}/stock/qualityInspection/outStockList',
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
		}
$(function(){
	sale_settlement_goods.init();
});
$(function(){
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
	
	$('#dgAcc').datagrid({
		method : "get",
		fit : false,
		fitColumns : true,
		border : false,
		striped : true,
		singleSelect : true,
		scrollbarSize : 0,
		url :'${ctx}/accessory/jsonAll?filter_EQS_accParentId=${saleSettlement.id }&filter_EQS_accParentEntity=com_cbmie_lh_sale_entity_SaleSettlement',
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
					str += "<a style='text-decoration:none' href='#' onclick='downnloadAcc(" + value + ");'>下载</a>"
					return str;
				}
			} 
		]]
	});
});


//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
</script>
</body>
</html>