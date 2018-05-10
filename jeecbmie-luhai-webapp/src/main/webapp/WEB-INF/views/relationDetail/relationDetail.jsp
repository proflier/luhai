<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String disView = request.getParameter("disView");
%>
<fieldset id="relationField" style="display: none;" class="fieldsetClass">
	<legend>关联信息</legend>
	<div >
		<input id="modeType" type="hidden" value="" />
		<input id="disView" type="hidden" value="<%=disView%>" />
		<a id="purchaseContract" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-finished" plain="true" onclick="toPurchase();">采购合同</a>
		<a id="payApply" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-cost" plain="true" onclick="toCredit();">开证申请</a>
		<a id="lhBills" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-shipping" plain="true" onclick="toBills();">上游到单</a>
		<a id="shipTrace" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-world" plain="true" onclick="toShipTrack();">船舶动态</a>
		<a id="inStock" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-sign-in" plain="true" onclick="toInStock();">入库明细</a>
		<a id="feeBack" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-sign-in" plain="true" onclick="toFeeBack();">反馈内容</a>
		<a id="paymentConfirm" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-bank" plain="true" onclick="toPayConfirm();">付款信息</a>
		<a id="saleContract" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-billing" plain="true" onclick="toSale();">销售合同审批</a>
		<a id="saleDelivery" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-finished" plain="true" onclick="toDelivery();">销售放货审批</a>
		<a id="outStock" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-sign-out" plain="true" onclick="toOutStock();">出库管理</a>
		<a id="saleInvoice" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-billing" plain="true" onclick="toSaleInvoice();">销售发票</a>
	</div>					
</fieldset>						

<script type="text/javascript">
var innerContractNo;
var mainUrl;
$(function(){
	$("#relationField").show();//显示关联模块，二次弹窗后此js失效采用默认值hidden
	var disView = new Array()
	disView=$('#disView').val().split(",");
	for(var i=0;i<disView.length;i++){	
			$("#"+disView[i]).hide();
	}
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	mainUrl = localhostPaht+projectName;
});
	
function toSale(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("saleContract");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/saleDetail/'+innerContractNo, 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'contractNo',title:'合同号',sortable:true,width:20}, 
				{field:'contractTypeCode',title:'合同类型',sortable:true,width:20,
	            	formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:mainUrl+"/system/dictUtil/getDictNameByCode/contractType/"+value,
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
					}}, 
				{field:'money',title:'金额',sortable:true,width:20}, 
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toPurchase(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("purchaseContract");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/purchaseDetail/'+innerContractNo, 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,	
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
			{field:'deliveryUnit',title:'供货单位',sortable:true,width:25,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:mainUrl+"/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
	    ]],
	    sortName:'id',
	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toCredit(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("payApply");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({
		method: "get",
		url:mainUrl+'/realtion/creditDetail/'+innerContractNo, 
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		scrollbarSize : 0,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'contractNo',title:'采购合同号',sortable:true,width:20},
			{field:'payApplyNo',title:'开证申请号',sortable:true,width:20},
			{field:'ourUnit',title:'开证单位',sortable:true,width:20,
		    	formatter: function(value,row,index){
					var val;
					if(row.ourUnit!=''&&row.ourUnit!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : mainUrl+'/baseInfo/baseUtil/affiBaseInfoNameByCode/'+row.ourUnit ,
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
			{field:'supplier',title:'供应商',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.supplier!=''&&row.supplier!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : mainUrl+'/baseInfo/baseUtil/affiBaseInfoNameByCode/'+row.supplier ,
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
		]],
		sortName:'id',
	    sortOrder: 'desc',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
	
}

function toBills(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("lhBills");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/billsDetail/'+innerContractNo, 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'billNo',title:'到单号',sortable:true,width:20},
			{field:'shipNo',title:'船编号',sortable:true,width:20,
            		formatter: function(value,row,index){
						var val;
						if(value!=''&& value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:mainUrl+"/logistic/shipTrace/shipNameShow/"+value,
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
					}},
			{field:'deliveryUnit',title:'供货单位',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.deliveryUnit!=''&&row.deliveryUnit!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:mainUrl+"/baseInfo/baseUtil/affiBaseInfoNameByCode/"+row.deliveryUnit,
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
			}
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
	
}

function toPayConfirm(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("paymentConfirm");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/paymentDetail/'+innerContractNo, 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,	
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'confirmNo',title:'确认单号',sortable:true,width:20},
			{field:'receiveUnitName',title:'收款单位',sortable:true,width:20}, 
			{field:'payContent',title:'付款内容',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.payContent!=''&&row.payContent!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:mainUrl+"/system/dictUtil/getDictNameByCode/paymentContent/"+row.payContent,
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
	    ]],
	    sortName:'id',
	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toInStock(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("inStock");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/inStockDetail/'+innerContractNo,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'inStockId',title:'入库单号',sortable:true,width:20}, 
			{field:'billNo',title:'提单号',sortable:true,width:20},
			{field:'inStockDate',title:'入库日期',sortable:true,width:15},
			{field:'shipNo',title:'船名',sortable:true,width:25,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:mainUrl+"/logistic/shipTrace/shipNameShow/"+value,
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
			}
	    ]],
	    sortName:'id',
	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toFeeBack(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("feeBack");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/feeBackDetail/'+innerContractNo,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'title',title:'主题',width:20}, 
				{field:'classification',title:'反馈分类',width:20,
					formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:mainUrl+"/system/dictUtil/getDictNameByCode/feedbackClassification/"+value,
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
						}},
				{field:'types',title:'反馈类型',width:20,
							formatter: function(value,row,index){
								var val;
								if(value!=''&&value!=null){
									$.ajax({
										type:'GET',
										async: false,
										url:mainUrl+"/system/dictUtil/getDictNameByCode/feedbackTypes/"+value,
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
							}},
				{field:'createDate',title:'创建日期',width:20,
					formatter:function(value,row,index){
						if(value == null){
							return null;
						}
						var time = new Date(value);
						return time.format("yyyy-MM-dd");
					}},
				]],
	    sortName:'id',
	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toDelivery(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("saleDelivery");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({
		method: "get",
	    url:mainUrl+'/realtion/deliveryStockDetail/'+innerContractNo,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true}, 
			{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
			{field:'deliveryReleaseNo',title:'发货通知号',sortable:true,width:20},
			{field:'seller',title:'客户名称',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:mainUrl+"/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
			{field:'billDate',title:'制单日期',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}
			},
			{field:'saleMode',title:'销售方式',sortable:true,width:10,
				formatter: function(value,row,index){
					var unit_t = '';
					if(value!=null && value!=""){
						if(value=='%'){value='%25';}
						$.ajax({
							url : mainUrl+'/system/dictUtil/getDictNameByCode/SALESMODE/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								unit_t = data;
							}
						});
					}
					return unit_t;
				}},
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toOutStock(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("outStock");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation = $('#dg_relation').datagrid({  
		method: "get",
	    url:mainUrl+'/realtion/outStockDetail/'+innerContractNo,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'outStockNo',title:'出库单号',sortable:true,width:20}, 
			{field:'deliveryNo',title:'放货单号',sortable:true,width:20},
			{field:'realQuantity',title:'实发数量',sortable:true,width:15},
			{field:'outStockDate',title:'出库日期',sortable:true,width:15},
	    ]],
	    sortName:'id',
	    sortOrder: 'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toShipTrack(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("shipTrace");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({
		method: "get",
	    url:mainUrl+'/realtion/shipTrackDetail/'+innerContractNo,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'innerContractNo',title:'内部合同号',sortable:true,width:30},
			{field:'shipNo',title:'船编号',width:20},
			{field:'shipName',title:'船中文名',width:20},
			{field:'loadPortView',title:'装港港口',sortable:true,width:20,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&&value!=null){
// 						$.ajax({
// 							type:'GET',
// 							async: false,
// 							url:mainUrl+"/baseInfo/baseUtil/getPortNameByCode/"+value,
// 							success: function(data){
// 								if(data != null){
// 									val = data;
// 								} else {
// 									val = '';
// 								}
// 							}
// 						});
// 						return val;
// 					}else{
// 						return '';
// 					}
// 				}
			},
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

function toSaleInvoice(){
	$('#detailLayout').layout('expand','east');
	$('#modeType').val("saleInvoice");
	innerContractNo = $('#innerContractNo').val();
	if(innerContractNo==''){
		return;
	}
	dg_relation=$('#dg_relation').datagrid({
		method: "get",
	    url:mainUrl+'/realtion/saleInvoiceDetail/'+innerContractNo,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'invoiceNo',title:'编号',sortable:true,width:20},
			{field:'customerName',title:'客户名称',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:mainUrl+"/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
			{field:'preBilling',title:'是否预开票',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						val = value=='0'?'否':'是';
						return val;
					}else{
						return '';
					}
				}	
			}
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
		onDblClickRow:function(rowIndex, rowData){
			detailRelation();
		}
	});
}

//点击空白处收缩关联列表
function centerDetail(){
	$('#detailLayout').layout('collapse','east');
}

//查看明细
function detailRelation(){
	var modeType = $('#modeType').val();
	var title = document.getElementById(modeType).innerText;
	var row_relation = dg_relation.datagrid('getSelected');
	if(rowIsNull(row_relation)) return;
	d_relation=$("#dlg_relation").dialog({   
	    title: title,    
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    href:mainUrl+'/realtion/detail/'+modeType+'/'+row_relation.id,
	    modal:true,
	    buttons:[
	             {
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_relation.panel('close');
			}
		}]
	});
	d_relation.panel('close');
	d_relation=$("#dlg_relation").dialog({   
	    title: title,    
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    href:mainUrl+'/realtion/detail/'+modeType+'/'+row_relation.id,
	    modal:true,
	    buttons:[
	             {
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_relation.panel('close');
			}
		}]
	});
	
	
}
</script>
</body>
</html>