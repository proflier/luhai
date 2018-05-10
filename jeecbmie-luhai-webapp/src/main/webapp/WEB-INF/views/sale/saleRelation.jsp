	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
		<div class="container">
			<header class="clearfix">
<!-- 				<h1>销售合同相关单据</h1> -->
			</header>	
			<div class="main">
				<ul id="cbp-ntaccordion" class="cbp-ntaccordion">
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">物流信息  <font style='color:blue'>${billListSize +shipTractsSize+saleDelarysSize}条</font></h3>
						<div class="cbp-ntcontent">
							<ul class="cbp-ntsubaccordion">
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">上游到单<font style='color:blue'>${billListSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_bills" ></table>
									</div>
								</li>
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">船舶动态<font style='color:blue'>${shipTractsSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_shipTracts" ></table>
									</div>
								</li>
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">销售放货<font style='color:blue'>${saleDelarysSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_saleDelarys" ></table>
									</div>
								</li>
							</ul>
						</div>
					</li>
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">采购合同<font style='color:blue'>${purchaseContractsSize }条</font></h3>
						<div class="cbp-ntcontent">
							<table id="dg_purchaseContracts" ></table>
						</div>
					</li>
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">业务结算<font style='color:blue'>${saleSettlementsSize+inputInvoicesSize }条</font></h3>
						<div class="cbp-ntcontent">
							<ul class="cbp-ntsubaccordion">
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">销售结算<font style='color:blue'>${saleSettlementsSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_saleSettlements" ></table>
									</div>
								</li>
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">进项发票<font style='color:blue'>${inputInvoicesSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_inputInvoices" ></table>
									</div>
								</li>
							</ul>
						</div>
					</li>
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">资金收付<font style='color:blue'>${payApplysSize+saleInvoicesSize+paymentConfirmsSize }条</font></h3>
						<div class="cbp-ntcontent">
							<ul class="cbp-ntsubaccordion">
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">开证申请<font style='color:blue'>${payApplysSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_payApplys" ></table>
									</div>
								</li>
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">销售发票<font style='color:blue'>${saleInvoicesSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_saleInvoices" ></table>
									</div>
								</li>
								<li class="cbp-ntopen">
									<h4 class="cbp-nttrigger">付款申请<font style='color:blue'>${paymentConfirmsSize }条</font></h4>
									<div class="cbp-ntcontent">
										<table id="dg_paymentConfirms" ></table>
									</div>
								</li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<script>
			
		
		$( function() {
				/*
				 how to call the plugin:
				$( selector ).cbpNTAccordion( [options] );
				- destroy:
				$( selector ).cbpNTAccordion( 'destroy' );
				*/

				$( '#cbp-ntaccordion' ).cbpNTAccordion();
				
				$('#dg_bills').datagrid({
					data : JSON.parse('${billList}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'id',title:'id',hidden:true},  
								{field:'billNo',title:'提单号',sortable:true,width:20},
								{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
									formatter:function(value,row,index){
										if(value == null){
											return null;
										}
					            		var time = new Date(value);
					            		return time.format("yyyy-MM-dd");
					            	}},
								{field:'shipNoView',title:'运输工具编号',sortable:true,width:20},
								{field:'deliveryUnitView',title:'供货单位',sortable:true,width:20},
						    ]],
					});
				
				
				$('#dg_shipTracts').datagrid({
					data : JSON.parse('${shipTracts}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'innerContractNo',title:'内部合同号',sortable:true,width:20},
								{field:'shipNo',title:'运输工具编号',width:20,
									formatter:function(value,row,index){
										return row.shipNo+row.shipName;
					            	}	
								},
								{field:'shipNameE',title:'船英文名',width:20},
								{field:'loadPortView',title:'装港港口',sortable:true,width:20,},
								{field:'loadPortDate',title:'抵港时间',sortable:true,width:20,
									formatter:function(value,row,index){
										if(value == null){
											return null;
										}
					            		var time = new Date(value);
					            		return time.format("yyyy-MM-dd HH:mm");
					            	}},
				            	{field:'loadBeginDate',title:'开装时间',sortable:true,width:20,
									formatter:function(value,row,index){
										if(value == null){
											return null;
										}
					            		var time = new Date(value);
					            		return time.format("yyyy-MM-dd HH:mm");
					            	}}
						    ]],
					});
				
				
				$('#dg_saleDelarys').datagrid({
					data : JSON.parse('${saleDelarys}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
					showFooter: true,
				 	columns:[[    
								{field:'deliveryReleaseNo',title:'发货通知号',sortable:true,width:20},
								{field:'customerName',title:'客户名称',sortable:true,width:20},
								{field:'shipNo',title:'运输工具编号',sortable:true,width:20,},
								{field:'goodsName',title:'商品名称',sortable:true,width:20},
								{field:'quantity',title:'数量',sortable:true,width:20},
								{field:'createrdate',title:'创建时间',sortable:true,width:20}
						    ]],
				    onLoadSuccess: function(data){
				    	initSaleDelarysFooter(data);
					},
					});
				
				
				$('#dg_purchaseContracts').datagrid({
					data : JSON.parse('${purchaseContracts}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'innerContractNo',title:'内部合同号',sortable:true,width:25},
								{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
								//	{field:'agreementNo',title:'协议号',sortable:true,width:25},
								{field:'deliveryUnitView',title:'供货单位',sortable:true,width:25},
								{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
								{field:'createrDept',title:'申请部门',sortable:true,width:20},
								{field:'businessManagerView',title:'业务经办人',sortable:true,width:15},
								{field:'createDate',title:'创建时间',sortable:true,width:15},
						    ]],
					});
				
				$('#dg_saleSettlements').datagrid({
					data : JSON.parse('${saleSettlements}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'saleSettlementNo',title:'结算编号',sortable:true,width:20},
								{field:'saleContractNo',title:'销售合同编号',sortable:true,width:20},
								{field:'receivingUnitView',title:'收货单位',width:20},
								{field:'settlementDate',title:'结算日期',sortable:true,width:20},
						    ]],
					});
				
				$('#dg_inputInvoices').datagrid({
					data : JSON.parse('${inputInvoices}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'invoiceClassModel',title:'发票分类',sortable:true,width:30},
								{field:'invoiceTypeModel',title:'发票类型',sortable:true,width:25}, 
								{field:'tradeMode',title:'贸易方式',sortable:true,width:25},
								{field:'userNameView',title:'客户名',sortable:true,width:25},
								{field:'createrDept',title:'经办部门',sortable:true,width:25},
								{field:'createDate',title:'创建日期',sortable:true,width:20,
									formatter:function(value,row,index){
										if(value == null){
											return null;
										}
										var time = new Date(value);
										return time.format("yyyy-MM-dd");
									}
								},
						    ]],
					});
				
				
				$('#dg_payApplys').datagrid({
					data : JSON.parse('${payApplys}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'inteContractNo',title:'内部合同号',sortable:true,width:20},
								{field:'contractNo',title:'采购合同号',sortable:true,width:20},
								{field:'payApplyNo',title:'开证申请号',sortable:true,width:20},
								{field:'ourUnitView',title:'开证单位',sortable:true,width:20},
								{field:'supplierView',title:'供应商',sortable:true,width:20},
								{field:'createDate',title:'创建时间',sortable:true,width:25},
						    ]],
					});
				
				$('#dg_saleInvoices').datagrid({
					data : JSON.parse('${saleInvoices}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'invoiceNo',title:'结算编号',sortable:true,width:20},
								{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
								{field:'customerNameView',title:'客户名称',sortable:true,width:20},
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
								},
								{field:'createDate',title:'制单日期',sortable:true,width:20,
									formatter:function(value,row,index){
										if(value == null){
											return null;
										}
										var time = new Date(value);
										return time.format("yyyy-MM-dd");
									}},
						    ]],
					});
				
				$('#dg_paymentConfirms').datagrid({
					data : JSON.parse('${paymentConfirms}'),
				    fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				 	columns:[[    
								{field:'confirmNo',title:'确认单号',sortable:true,width:20},
								{field:'payUnitView',title:'付款单位',sortable:true,width:20},
								{field:'payMoneyXiao',title:'金额',sortable:true,align:'right',width:15,
									formatter: function(value,row,index){
										var val;
										if(value!=''&&value!=null){
											if(isNaN(value)||value==null){
												val = 0.00; //如果不是数字，返回0.000
											}else{
												value = parseFloat(value);
												value = value.toFixed(2); 
												val = value;
											}
											return val;
										}else{
											return '';
										}
									}	
								},
								{field:'currencyView',title:'币种',sortable:true,align:'left',width:10},
								{field:'confirmDate',title:'确认日期',sortable:true,width:15},
								{field:'receiveUnitNameView',title:'收款单位',sortable:true,width:20}, 
								{field:'payContentView',title:'付款内容',sortable:true,width:20},
								{field:'createrName',title:'经办人',sortable:true,width:15},
								{field:'createDate',title:'创建时间',sortable:true,width:15},
						    ]],
						    showFooter: true,
						    onLoadSuccess: function(data){
						    	initPaymentConfirmsFooter(data);
							},
					});
				

			} );
		
		function getCurrentValue(colName,data) {
		    var total = 0.000;
		    for (var i = 0; i < data.rows.length; i++) {
		      total += parseFloat(data.rows[i][colName]);
		    }
		   	return total.toFixed(3);
		}

		//加载footer
		function initSaleDelarysFooter(data) {
			$('#dg_saleDelarys').datagrid('reloadFooter',[
			                              	{ 
			                              	quantity: getCurrentValue('quantity',data)
			                              	}
			                              ]);
		 }
		
		//加载footer
		function initPaymentConfirmsFooter(data) {
			$('#dg_paymentConfirms').datagrid('reloadFooter',[
			                              	{ 
			                              		payMoneyXiao: getCurrentValue('payMoneyXiao',data)
			                              	}
			                              ]);
		 }
		</script>
