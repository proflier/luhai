<%@page import="com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div>
		<form id="mainform"  action="${ctx}/sale/purchaseSaleSame/${action}" method="post">
			<div id="mainDiv" data-options="border:false">
				<fieldset class="fieldsetClass" >
				<legend>采销同批信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%">收文单位</th>
						<td width="30%">
							<input type="hidden"  name="id" id="id" value="${purchaseSaleSame.id}" />
							<input id="receiveCompany" name="receiveCompany" type="text" value="${purchaseSaleSame.receiveCompany }" 
							data-options="required:true" />
						</td>
						<th width="20%">日期</th>
						<td width="30%">
							<input name="applyDate" type="text"  value="<fmt:formatDate value="${purchaseSaleSame.applyDate}" />" 
							class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:false" />
						</td>
					</tr>
					<tr>
						<th>发文部门</th>
						<td>
							<input name="postCompany"  id="postCompany" type="text"  value="${purchaseSaleSame.postCompany}" class="easyui-validatebox"
								 data-options="required:true" />
						</td>
						<th>经办人</th>
						<td>
							<input id="transactor" name="transactor" type="text" class="easyui-validatebox" value="${purchaseSaleSame.transactor }" 
							data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th>部门主管</th>
						<td>
							<input id="deptLeader" name="deptLeader" type="text" class="easyui-validatebox" value="${purchaseSaleSame.deptLeader }" 
							data-options="required:true"/>
						</td>
						<th>主旨</th>
						<td>
							<input id="keynote" name="keynote" type="text" value="${purchaseSaleSame.keynote }"  class="easyui-validatebox"
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>登记人</th>
						<td>
							 <input id="createrName" name="createrName" type="text" value="${purchaseSaleSame.createrName }"   
							 readonly="true" class="easyui-validatebox"/>
							 <input  name="createrNo" type="hidden" value="${purchaseSaleSame.createrNo }"/>
						</td>
						<th>登记部门</th>
						<td>
							<input id="createrDept" name="createrDept" type="text"  value="${purchaseSaleSame.createrDept }"   
							class="easyui-validatebox" readonly="true" />
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
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toPurchaseContractList()">采购合同列表</a>
					<input type="hidden" name="purchaseContractJson" id="purchaseContractJson"/>
					<table id="dgPurchaseList" ></table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>销售合同明细</legend>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toSaleContractList()">销售合同列表</a>
					<input type="hidden" name="saleContractJson" id="saleContractJson"/>
					<table id="dgSaleList" ></table> 
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>盈亏核算</legend>
					<table id="dgproflitLoss" ></table> 
				</fieldset>
			</div>
		</form>
	</div>
<!-- 	<div id="dlgSendGoods"></div>  -->
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
<script type="text/javascript">

	$(function() {
		var editor;
	    window.editor = KindEditor.create('#content',{
	          resizeType:1,      
// 	          urlType:'domain', // 带有域名的绝对路径
	          uploadJson : '${ctx}/static/plugins/kindeditor/jsp/upload_json.jsp',
	          fileManagerJson : '${ctx}/static/plugins/kindeditor/jsp/file_manager_json.jsp',
	          allowFileManager : true,
	          afterChange:function(){
	        	this.sync();  
	          },
	 	});
		    
	    
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
		
		//收文单位(我司单位)
		$('#receiveCompany').combobox({
			panelHeight : 'auto',
			required : true,
			method:'GET',
			url : '${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
			valueField : 'id',
			textField : 'customerName'
		});
		
		dgPurchaseList=$('#dgPurchaseList').datagrid({  
			method: "get",
			url:'${ctx}/sale/purchaseSaleSame/getOwnPurchaseList/'+($('#id').val()==''?'0':$('#id').val()),
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
		    	var rows = $('#dgPurchaseList').datagrid('getRows');
		    	$('#dgPurchaseList').datagrid('acceptChanges');
				$('#purchaseContractJson').val(JSON.stringify(rows));
		    },
		    sortName:'id',
		    showFooter: true,
		    sortOrder:'desc',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		});
		
		
		dgSaleList=$('#dgSaleList').datagrid({  
			method: "get",
			url:'${ctx}/sale/purchaseSaleSame/getOwnSaleList/'+($('#id').val()==''?'0':$('#id').val()), 
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
		    sortName:'id',
		    onLoadSuccess: function(data){
		    	onCountTolalFooterSale();
		    	var rows = $('#dgSaleList').datagrid('getRows');
				$('#dgSaleList').datagrid('acceptChanges');
				$('#saleContractJson').val(JSON.stringify(rows));
		    },
		    sortOrder:'desc',
		    showFooter: true,
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
	
	
	//弹窗加载采购合同列表
	  function toPurchaseContractList(){
	  	purchase=$("#dlgPurchaseContract").dialog({   
	  	    title: '采购合同',    
	  	    width: 700,    
	  	    height: 350,  
	  	    href:'${ctx}/sale/purchaseSaleSame/toPurchaseList',
	  	    modal:true,
	  	    buttons:[{
	  			text:'保存',iconCls:'icon-save',id:'button-save',
	  			handler:function(){
	  				setPurchaseContract();
	  			}
	  		},{
	  			text:'关闭',iconCls:'icon-cancel',
	  			handler:function(){
	  				purchase.panel('close');
	  			}
	  		}]
	  	});
	  }
	
	//弹窗加载销售合同列表
	  function toSaleContractList(){
	  	sale=$("#dlgSaleContract").dialog({   
	  	    title: '销售合同',    
	  	    width: 700,    
	  	    height: 350,  
	  	    href:'${ctx}/sale/purchaseSaleSame/toSaleList',
	  	    modal:true,
	  	    buttons:[{
	  			text:'保存',iconCls:'icon-save',id:'button-save',
	  			handler:function(){
	  				setSaleContract();
	  			}
	  		},{
	  			text:'关闭',iconCls:'icon-cancel',
	  			handler:function(){
	  				sale.panel('close');
	  			}
	  		}]
	  	});
	  }
</script>
</body>
</html>


