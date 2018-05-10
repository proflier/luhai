<%@ page import="com.cbmie.lh.sale.entity.SaleInvoice"%>
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
	<form id="mainform" action="${ctx}/sale/saleInvoice/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${saleInvoice.id }" />
		<input type="hidden" id="relLoginNames" name="relLoginNames" value="${saleInvoice.relLoginNames }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售发票信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">是否预开票</th>
						<td>
							<c:if test="${saleInvoice.preBilling eq 1 }">
							是
							</c:if>
							<c:if test="${saleInvoice.preBilling eq 0 }">
							否
							</c:if>
						</td>
						<th width="20%">销售合同号</th>
						<td width="30%">
							${saleInvoice.saleContractNo}
						</td>
					</tr>
					<tr id="settlementTr">
						<th width="20%">结算编号</th>
						<td width="30%">
							${saleInvoice.invoiceNo}
						</td>
						
						<th width="20%">业务经办人</th>
						<td width="30%">
							${mytag:getUserByLoginName(saleInvoice.businessManager).name}
						</td>
					</tr>
					<tr>
						<th width="20%">客户名称</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(saleInvoice.customerName)}
						</td>
						<th width="20%">品名</th>
						<td>
							${saleInvoice.goodsName }
						</td>
					</tr>
					<tr>
					<th>备注</th>
					<td colspan="3">
						${saleInvoice.remark}
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
							${saleInvoice.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleInvoice.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleInvoice.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleInvoice.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					</table> 
				</fieldset>
			<fieldset class="fieldsetClass" >
			<legend>本次开票信息</legend>
				<div>
					<table id="dgInvoiceGoods"></table>
				</div>
			</fieldset>	
			<fieldset class="fieldsetClass">
			<legend>出库信息</legend>
				<table id="outStockDetailList"></table>
			</fieldset>
			<fieldset class="fieldsetClass">
			<legend>结算信息</legend>
				<table id="settlementDetailList"></table>
			</fieldset>
			<fieldset class="fieldsetClass">
			<legend>进项发票</legend>
				<table id="inputInvoiceDetailList"></table>
			</fieldset>
			<fieldset class="fieldsetClass">
			<legend>水单信息</legend>
				<table id="serialDetailList"></table>
			</fieldset>
			<fieldset class="fieldsetClass">
			<legend>已开票信息</legend>
				<table id="dgOldInvoiceGoods"></table>
			</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<div id="dgAcc"  class="tableClass"></div>
			</div>		
		</div>
	</form>
</div>

<script type="text/javascript">
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
		url :'${ctx}/accessory/jsonAll?filter_EQS_accParentId=${saleInvoice.id}&filter_EQS_accParentEntity=com_cbmie_lh_sale_entity_SaleInvoice',
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
var sale_invoice_main = {
	init:function(){
		this.init_saleContract();
		if("${saleInvoice.preBilling}"=='0'){
			$("#settleGoods").hide();
			this.init_settlement();
		}else{
			$("#settlementTr").hide();
		}
	},
	preChange:function(newValue,oldValue){
		if(newValue=="0"){
			$("#settlementTr").show();
			$("#settleGoods").hide();
			$('#saleContractNo_main').combogrid('disable');
			sale_invoice_main.init_settlement();
		}else{
			$("#settleGoods").show();
			$('#saleContractNo_main').combogrid('enable');
			$('#invoiceNo').combogrid({required:false,value:''});
			$("#settlementTr").hide();
			sale_invoice_main.init_saleContract();
		}
	},
	init_saleContract:function(){
		$('#saleContractNo_main').combogrid({    
		    panelWidth:600,    
		    method: "get",
		    idField:'contractNo',    
		    textField:'contractNo',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
			onLoadSuccess:function(){
				if($('#preBilling').combobox('getValue')=='0'){
					$('#saleContractNo_main').combogrid('disable');
				}
			},
		    url:'${ctx}/sale/saleContract/json?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1', 
		    columns:[[    
		  			{field:'id',title:'id',hidden:true},
		              {field:'contractNo',title:'合同号',sortable:true,width:20},
		  			{field:'contractQuantity',title:'合同数量',sortable:true,width:20,
		            	  formatter: function(value,row,index){
								if(isNaN(value)||value==null){
									return 0.000; //如果不是数字，返回0.000
								}else{
									value = parseFloat(value);
									return(value.toFixed(3)); 
								}
							}	  
		  			},
		  			{field:'contractAmount',title:'合同金额',sortable:true,width:20,
		  				formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.00; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(2)); 
							}
						}	
		  			},
		  			{field:'purchaserView',title:'买方',sortable:true,width:20}
		      ]],
			onSelect:function(index,row){
				$("#customerName").combobox('setValue',row.purchaser);
				$('#relLoginNames').val(row.relLoginNames);
			}
		});
	},
	init_settlement:function(){
		$('#invoiceNo').combogrid({    
		    panelWidth:600,    
		    method: "get",
		    idField:'saleSettlementNo',    
		    textField:'saleSettlementNo',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
			required:true,
		    url:'${ctx}/sale/saleSettlement/json?filter_EQS_state=1', 
		    columns:[[    
						{field:'id',title:'id',hidden:true},  
						{field:'saleSettlementNo',title:'结算编号',width:20},
						{field:'saleContractNo',title:'销售合同编号',width:20},
						{field:'receivingUnitView',title:'收货单位',width:20,
// 							formatter: function(value,row,index){
// 								var val;
// 								if(value!=''&& value!=null){
// 									$.ajax({
// 										type:'GET',
// 										async: false,
// 										url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 										success: function(data){
// 											if(data != null){
// 												val = data;
// 											} else {
// 												val = '';
// 											}
// 										}
// 									});
// 									return val;
// 								}else{
// 									return '';
// 								}
// 							}	
						},
						{field:'settlementDate',title:'结算日期',width:20,
							formatter:function(value,row,index){
								if(value == null){
									return null;
								}
			            		var time = new Date(value);
			            		return time.format("yyyy-MM-dd");
			            	}}
				    ]],
			onSelect:function(index,row){
				$('#saleContractNo_main').combogrid('setValue',row.saleContractNo)
				$("#customerName").combobox('setValue',row.receivingUnit);
			}
		});
	}
}
var sale_invoice_sub = {
		init:function(){
			this.listOutDetails();
			this.listSettlementDetail();
			this.listInputInvoiceDetail();
			this.listSerialDetail();
			this.listOldGoods();
			this.listGoods();
		},
		reload:function(){
			this.outStockDetailList.datagrid("reload");
			this.old_goods_list.datagrid("reload");
			this.goods_list.datagrid("reload");
		},
		outStockDetailList:{},
		listOutDetails:function(){
			this.outStockDetailList = $('#outStockDetailList').datagrid({
				method: "get",
				url: '${ctx}/sale/invoiceOutRel/listOutsForInvoice/${saleInvoice.id }',
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
							{field:'relId',title:'relId',hidden:true}, 
							{field:'portView',title:'仓库',width:20,
// 								formatter: function(value,row,index){
// 									var val;
// 									if(value!=''&&value!=null){
// 										$.ajax({
// 											type:'GET',
// 											async: false,
// 											url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
							{field:'voyView',title:'船名',width:40,
// 			            		formatter: function(value,row,index){
// 									var val;
// 									if(value!=''&& value!=null){
// 										$.ajax({
// 											type:'GET',
// 											async: false,
// 											url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
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
// 								formatter: function(value,row,index){
// 									var val;
// 									if(value!=''&&value!=null){
// 										$.ajax({
// 											type:'GET',
// 											async: false,
// 											url : '${ctx}/system/dictUtil/getDictNameByCode/YSFS/'+value ,
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
							}
					    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		settlementDetailList:{},
		listSettlementDetail:function(){
			this.settlementDetailList = $('#settlementDetailList').datagrid({
				method: "get",
				url:'${ctx}/sale/saleInvoiceSub/saleSettlementGoodsList/${saleInvoice.id }' ,
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				extEditing:false,
				//view:emptyView,
				emptyMsg:'暂无对应数据',
			    columns:[[    
					{field:'id',title:'id',hidden:true},
					{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
					{field:'goodsNameView',title:'品名',sortable:true,width:20,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&&value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}		
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
			    onLoadSuccess: function(data){
			    	initSettlementFooter(data);
				},
		    	showFooter: true,
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		inputInvoiceDetailList:{},
		listInputInvoiceDetail:function(){
			this.inputInvoiceDetailList = $('#inputInvoiceDetailList').datagrid({
				method: "get",
				url: '${ctx}/sale/saleInvoiceSub/inputInvoiceList/${saleInvoice.id }' ,
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
						{field:'purchaseOrderNumber',title:'采购订单号',sortable:true,width:20},
						{field:'relatedSalesOrder',title:'相关销售订单',sortable:true,width:20},
						{field:'invoiceNo',title:'发票号',sortable:true,width:20},
						{field:'billingDate',title:'开票日期',sortable:true,width:15},
						{field:'productNameView',title:'品名',sortable:true,width:10,
// 							formatter: function(value,row,index){
// 								var val;
// 								if(value!=''&&value!=null){
// 									$.ajax({
// 										type:'GET',
// 										async: false,
// 										url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
// 										success: function(data){
// 											if(data != null){
// 												val = data;
// 											} else {
// 												val = '';
// 											}
// 										}
// 									});
// 									return val;
// 								}else{
// 									return '';
// 								}
// 							}	
						},
						{field:'unitMeasurementView',title:'计量单位',sortable:true,width:10,
// 							formatter: function(value,row,index){
// 								var val;
// 								if(value!=''&&value!=null){
// 									$.ajax({
// 										type:'GET',
// 										async: false,
// 										url : '${ctx}/system/dictUtil/getDictNameByCode/sldw/'+value ,
// 										success: function(data){
// 											if(data != null){
// 												val = data;
// 											} else {
// 												val = '';
// 											}
// 										}
// 									});
// 									return val;
// 								}else{
// 									return '';
// 								}
// 							}
						},
						{field:'mount',title:'数量',sortable:true,width:10,
							formatter: function(value,row,index){
								if(isNaN(value)||value==null){
									return 0.000; //如果不是数字，返回0.000
								}else{
									value = parseFloat(value);
									return(value.toFixed(3)); 
								}
							}	
						},
						{field:'prices',title:'单价',sortable:true,width:15,
							formatter: function(value,row,index){
								if(isNaN(value)||value==null){
									return 0.00; //如果不是数字，返回0.000
								}else{
									value = parseFloat(value);
									return(value.toFixed(2)); 
								}
							}	
						},
						{field:'allPrices',title:'开票金额',sortable:true,width:20,
							formatter: function(value,row,index){
								if(isNaN(value)||value==null){
									return 0.00; //如果不是数字，返回0.000
								}else{
									value = parseFloat(value);
									return(value.toFixed(2)); 
								}
							}	
						},
						{field:'taxMoney',title:'税额',sortable:true,width:10,
							formatter: function(value,row,index){
								if(isNaN(value)||value==null){
									return 0.00; //如果不是数字，返回0.000
								}else{
									value = parseFloat(value);
									return(value.toFixed(2)); 
								}
							}	
						},
						{field:'rebateRate',title:'退税率',sortable:true,width:10}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		serialDetailList:{},
		listSerialDetail:function(){
			this.serialDetailList = $('#serialDetailList').datagrid({
				method: "get",
				url:'${ctx}/sale/saleInvoiceSub/serialDetailList/${saleInvoice.id }' ,
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
					{field:'serialNumber',title:'流水号',sortable:true,width:20}, 
					{field:'contractNo',title:'合同号',sortable:true,width:20},
					{field:'serialTitleView',title:'水单抬头',sortable:true,width:20,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&&value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}
					},
					{field:'fundCategoryView',title:'资金类别',sortable:true,width:15,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&&value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url:"${ctx}/system/dictUtil/getDictNameByCode/fundCategory/"+value,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}
					},
					{field:'serialDate',title:'签署日期',sortable:true,width:20},
					{field:'bankDeadline',title:'银承到期日期',sortable:true,width:20},
		 			{field:'money',title:'金额',sortable:true,width:20,
						formatter: function(value, row, index){
							if(value != null){
								var str = value.toString().split(".");
								return str[0].replace(/\B(?=(?:\d{3})+$)/g, ',') + (str.length == 1 ? "" : "." + str[1]);
							}
						}	
		 			},
					{field:'createDate',title:'创建时间',sortable:true,width:25}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		old_goods_list:{},
		listOldGoods:function(){
			this.old_goods_list = $('#dgOldInvoiceGoods').datagrid({
				method: "get",
				url: '${ctx}/sale/saleInvoiceSub/oldJson/${saleInvoice.id }',
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
					{field:'billQuantity',title:'开票数量',sortable:true,width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.000; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(3)); 
							}
						}	
					},
					{field:'billPrice',title:'开票单价',sortable:true,width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.00; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(2)); 
							}
						}	
					},
					{field:'billMoney',title:'开票金额',sortable:true,width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.00; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(2)); 
							}
						}	
					},
					{field:'billDate',title:'开票日期',sortable:true,width:20,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd");
		            	}}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		goods_list:'',
		listGoods:function(){
			this.goods_list = $('#dgInvoiceGoods').datagrid({
				queryParams: {
					'filter_EQL_saleInvoiceId': $('#id').val()
				},
				method: "get",
				url:'${ctx}/sale/saleInvoiceSub/jsonNoPermission',
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
					{field:'billQuantity',title:'开票数量',sortable:true,width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.000; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(3)); 
							}
						}	
					},
					{field:'billPrice',title:'开票单价',sortable:true,width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.00; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(2)); 
							}
						}	
					},
					{field:'billMoney',title:'开票金额',sortable:true,width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.00; //如果不是数字，返回0.000
							}else{
								value = parseFloat(value);
								return(value.toFixed(2)); 
							}
						}	
					},
					{field:'billDate',title:'开票日期',sortable:true,width:20,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd");
		            	}}
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
	sale_invoice_sub.init();
	sale_invoice_main.init();
	
});
//加载footer
function initSettlementFooter(data) {
	$('#settlementDetailList').datagrid('reloadFooter',[
	                              	{ 
                              		sendQuantity: getCurrentValue('sendQuantity',data,3),
                              		receiveQuantity: getCurrentValue('receiveQuantity',data,3),
                              		settlementQuantity: getCurrentValue('settlementQuantity',data,3),
                              		deductionPrice: getCurrentValue('deductionPrice',data,2),
                              		receivedPrice: getCurrentValue('receivedPrice',data,2),
                              		receivablePrice: getCurrentValue('receivablePrice',data,2),
	                              	}
	                              ]);
 }
 
function getCurrentValue(colName,data,num) {
    var total = 0.000;
    for (var i = 0; i < data.rows.length; i++) {
    	if(data.rows[i][colName]){
      		total += parseFloat(data.rows[i][colName]);
    	}
    }
   	return total.toFixed(num);
}

//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
</script>
</body>
</html>