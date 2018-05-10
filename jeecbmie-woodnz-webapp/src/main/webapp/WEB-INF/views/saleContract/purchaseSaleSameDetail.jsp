<%@page import="com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
	<div>
		<form id="mainform">
			<div id="mainDiv" data-options="border:false">
				<fieldset class="fieldsetClass" >
				<legend>采销同批信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">收文单位</th>
						<input type="hidden"  name="id" id="id" value="${purchaseSaleSame.id}" />
						<td width="30%" id="receiveCompany">
						</td>
						<th width="20%">日期</th>
						<td width="30%">
							<fmt:formatDate value="${purchaseSaleSame.applyDate}" />
						</td>
					</tr>
					<tr>
						<th>发文部门</th>
						<td>
							${purchaseSaleSame.postCompany}
						</td>
						<th>经办人</th>
						<td>
							${purchaseSaleSame.transactor }
						</td>
					</tr>
					<tr>
						<th>部门主管</th>
						<td>
							${purchaseSaleSame.deptLeader }
						</td>
						<th>主旨</th>
						<td>
							${purchaseSaleSame.keynote }
						</td>
					</tr>
					<tr>
						<th>登记人</th>
						<td>
							${purchaseSaleSame.createrName }
						</td>
						<th>登记部门</th>
						<td>
							${purchaseSaleSame.createrDept }
						</td>
					</tr>
				</table> 
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>正文</legend>
					<table width="98%" class="tableClass">
						<tr>
							<td colspan="4"><textarea  name="content" type="text" id="content"  class="easyui-validatebox"
							style="width:100%;height:290%">${purchaseSaleSame.content}</textarea></td>
						</tr>
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>采购合同明细</legend>
					<table id="dgPurchaseList" ></table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>销售合同明细</legend>
					<table id="dgSaleList" ></table> 
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>盈亏核算</legend>
					<table id="dgproflitLoss" ></table> 
				</fieldset>
			</div>
		</form>
	</div>
<!-- <div id="dlgPurchaseDetail"></div> --><!-- 	采销同批 - 采购合同查看明细弹窗 -->
<!-- <div id="dlgSaleDetail"></div> 采销同批 - 销售合同查看明细弹窗 -->
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
<script type="text/javascript">
	$(function() {
		//收文单位(我司单位)
		if('${purchaseSaleSame.receiveCompany }'!=''){
			$.ajax({
				url : '${ctx}/baseinfo/affiliates/typeAjax/${purchaseSaleSame.receiveCompany }',
				type : 'get',
				cache : false,
				success : function(data) {
					$('#receiveCompany').text(data);
				}
			});
		}
		
		
		var editor;
	    window.editor = KindEditor.create('#content',{
	          resizeType:1,      
	          urlType:'domain', // 带有域名的绝对路径
	          readonlyMode : true
	 	});
	    
	    dgPurchaseList=$('#dgPurchaseList').datagrid({  
			method: "get",
			url:'${ctx}/sale/purchaseSaleSame/getOwnPurchaseList/'+$('#id').val(), 
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
				{field:'hth',title:'合同号',sortable:true,width:20},
				{field:'ghdw',title:'供货单位',sort:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(row.ghdw!=''&&row.ghdw!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getBaseInfoName/"+row.ghdw,
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
				{field:'dhzl',title:'订货总量 ',sortable:true,width:20},
				{field:'sldw',title:'数量单位 ',sortable:true,width:20,
					formatter: function(value,row,index){
						var val;
						if(row.sldw!=''&&row.sldw!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getDictName/sldw/"+row.sldw,
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
				{field:'hk',title:'货款',sortable:true,width:20},
				{field : 'action',title : '操作',width:5,
					formatter : function(value, row, index) {
						if(row.hth=='合计：'){//统计行不显示查看按钮
							return null;
						}
						return '<a href="javascript:purchaseDetail('+row.id+')"><div  style="width:16px;height:16px">查看</div></a>';
					}
		        }
		    ]],
		    onLoadSuccess: function(data){
		    	onCountTolalFooter();
		    },
		    showFooter: true,
		    sortName:'id',
		    sortOrder:'desc',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		});
	    
	    
	    
	    dgSaleList=$('#dgSaleList').datagrid({  
			method: "get",
			url:'${ctx}/sale/purchaseSaleSame/getOwnSaleList/'+$('#id').val(), 
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
				 {field:'contractNo',title:'合同号',sortable:true,width:20},
					{field:'seller',title:'卖受人',sort:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(row.seller!=''&&row.seller!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/downMenu/getBaseInfoName/"+row.seller,
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
					{field:'purchaser',title:'买受人',sort:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(row.purchaser!=''&&row.purchaser!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/downMenu/getBaseInfoName/"+row.purchaser,
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
					{field:'total',title:'销售总数量',sortable:true,width:20},
					{field:'numUnit',title:'数量单位',sortable:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(row.numUnit!=''&&row.numUnit!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/downMenu/getDictName/sldw/"+row.numUnit,
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
					{field:'totalMoney',title:'合同总额',sortable:true,width:20},
					{field : 'action',title : '操作',width:5,
						formatter : function(value, row, index) {
							if(row.contractNo=='合计：'){//统计行不显示查看按钮
								return null;
							}
							return '<a href="javascript:saleDetail('+row.id+')"><div  style="width:16px;height:16px">查看</div></a>';
						}
			        }
		    ]],
		    onLoadSuccess: function(data){
		    	onCountTolalFooterSale();
		    },
		    showFooter: true,
		    sortName:'id',
		    sortOrder:'desc',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		});
	});
	
	 proflitLoss=$('#dgproflitLoss').datagrid({
			method: "get",
			url:'${ctx}/sale/purchaseSaleSame/getOwnProflitLoss/'+($('#id').val()==''?'0':$('#id').val()), 
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
				{field:'contractNo',title:'合同号',sortable:true,width:20},
				{field:'material',title:'材质',sortable:true,width:20},
			    {field:'volume',title:'体积',sortable:true,width:25},
				{field:'priceProcurementDollars',title:'采购美金单价',sortable:true,width:20},
				{field:'value',title:'金额',sortable:true,width:20},
				{field:'unitSalesDollars',title:'销售美金单价',sortable:true,width:20},
				{field:'profit',title:'利润',sortable:true,width:20},
			    {field:'totalProfit',title:'利润合计',sortable:true,width:20},
			]],
			groupField:'contractNo',
			view: groupview,
			groupFormatter:function(value, rows){
				var returnVlue = 0;
				var totalProfit = 0;
				for(var i=0;i<rows.length;i++){
					returnVlue =  returnVlue + parseFloat(rows[i].value);
					totalProfit =  totalProfit + parseFloat(rows[i].totalProfit);
				}
				return value +  '--金额合计 :' +returnVlue.toFixed(2) 
				             + '--利润合计:' + totalProfit.toFixed(2);
			},
			sortName:'id',
		    sortOrder:'desc',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		});
	 
	
	function purchaseDetail(id){
		if(id==undefined){
			return;
		}
		var purchaseDetail=$("#dlgPurchaseDetail").dialog({   
		    title: '采购合同',    
		    width: 900,    
		    height: 400,  
		    href:'${ctx}/sale/purchaseSaleSame/getPurchaseDetail/'+id,
		    buttons:[
		             {
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					purchaseDetail.panel('close');
				} 
			}]
		});
	}
	
	
	function saleDetail(id){
		if(id==undefined){
			return;
		}
		var saleDetail=$("#dlgSaleDetail").dialog({   
		    title: '销售合同',    
		    width: 900,    
		    height: 400,  
		    href:'${ctx}/sale/purchaseSaleSame/getSaleDetail/'+id,
		    modal:true,
		    buttons:[ 
		             {
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					saleDetail.panel('close');
				}
			}]
		});
	}
	
	 function onCountTolalFooter(){
		  $('#dgPurchaseList').datagrid('reloadFooter',[
	           	{ hth: '合计：',
	           		hk:computePurchase("hk"),
	           		dhzl:computePurchase("dhzl")}
	           ]);
	 }
	 
	 function onCountTolalFooterSale(){
		  $('#dgSaleList').datagrid('reloadFooter',[
	           	{ contractNo: '合计：',
	           		totalMoney:computeSale("totalMoney"),
	           		total:computeSale("total")}
	           ]);
	 }
	 
	//指定列求和
	  function computePurchase(colName) {
	    var rows = $('#dgPurchaseList').datagrid('getRows');
	    var total = 0;
	    for (var i = 0; i < rows.length; i++) {
	      total += parseFloat(rows[i][colName]==null?0:rows[i][colName]);
	    }
	    if(colName=='dhzl'){
	    	return total.toFixed(3);
	    }
	    if(total >= 0)
	    	return total.toFixed(2);
	    else
	   		return '0';
	  }
	 
	//指定列求和
	  function computeSale(colName) {
	    var rows = $('#dgSaleList').datagrid('getRows');
	    var total = 0;
	    for (var i = 0; i < rows.length; i++) {
	      total += parseFloat(rows[i][colName]==null?0:rows[i][colName]);
	    }
	    if(colName=='total'){
	    	return total.toFixed(3);
	    }
	    if(total >= 0)
	    	return total.toFixed(2);
	    else
	   		return '0';
	  }
</script>
</body>
</html>


