	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
		<div class="container">
			<header class="clearfix">
<!-- 				<h1>销售合同相关单据</h1> -->
			</header>	
			<div class="main">
				<ul id="cbp-ntaccordion" class="cbp-ntaccordion">
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">采购合同<font style='color:blue'>${purchaseContractsSize }条</font></h3>
						<div class="cbp-ntcontent">
							<table id="dg_purchaseContracts" ></table>
						</div>
					</li>
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">销售合同<font style='color:blue'>${saleContractsSize }条</font></h3>
						<div class="cbp-ntcontent">
							<table id="dg_saleContracts" ></table>
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
							</ul>
						</div>
					</li>
					<li class="cbp-ntopen">
						<h3 class="cbp-nttrigger">资金收付<font style='color:blue'>${payApplysSize+saleInvoicesSize+paymentConfirmsSize }条</font></h3>
						<div class="cbp-ntcontent">
							<ul class="cbp-ntsubaccordion">
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
				
				$('#dg_saleContracts').datagrid({
					method: "get",
					data : JSON.parse('${saleContracts}'),
					fit : false,
					fitColumns : true,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
				    columns:[[    
						{field:'contractNo',title:'合同号',sortable:true,width:20},
						{field:'sellerView',title:'卖方',sortable:true,width:20},
						{field:'purchaserView',title:'买方',sortable:true,width:20},
						{field:'signDate',title:'签约日期',sortable:true,width:20},
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
		function initPaymentConfirmsFooter(data) {
			$('#dg_paymentConfirms').datagrid('reloadFooter',[
			                              	{ 
			                              		payMoneyXiao: getCurrentValue('payMoneyXiao',data)
			                              	}
			                              ]);
		 }
		</script>
