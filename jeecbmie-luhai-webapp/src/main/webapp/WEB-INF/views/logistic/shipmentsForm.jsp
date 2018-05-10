<%@page import="com.cbmie.lh.logistic.entity.ShipmentsSettlement"%>
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
	<form id="mainform" action="${ctx}/logistic/shipments/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${shipmentsSettlement.id }" />
		<input type="hidden" id="relLoginNames" name="relLoginNames" value="${shipmentsSettlement.relLoginNames }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '运输信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">订船合同编号</th>
						<td width="30%">
							<input id="contractNo" name="contractNo" type="text" value="${shipmentsSettlement.contractNo}" class="easyui-validatebox" data-options="required:true" readonly="readonly"/>
							<input id="contractNoOld" name="contractNoOld" type="hidden" value="${shipmentsSettlement.contractNoOld}"/>
							<a id="toContractListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toContractList()">订船合同列表</a>
						</td>
						<th width="20%">订船类型</th>
						<td width="30%">
							<mytag:combobox name="tradeCategory" value="${shipmentsSettlement.tradeCategory}" type="dict" parameters="orderShipType" disabled="true"/>
						</td>
					</tr>
					<tr>
						<th width="20%">结算日期</th>
						<td width="30%">
							<input id="settleDate" name="settleDate" type="text" value="<fmt:formatDate value='${shipmentsSettlement.settleDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
						</td>
						<th width="20%">开户名</th>
						<td width="30%">
							<input id="accountName" name="accountName" type="text" readonly="readonly" value="${shipmentsSettlement.accountName}" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th width="20%">开户行</th>
						<td>
							<input id="bank" name="bank" type="text" value="${shipmentsSettlement.bank}" class="easyui-validatebox" />
						</td>
						<th width="20%">开户账号</th>
						<td>
							<input id="account" name="account" readonly="readonly" type="text" value="${shipmentsSettlement.account}" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th  >业务经办人 </th>
						<td colspan="3">
							<mytag:combotree name="businessManager" value="${shipmentsSettlement.businessManager}"  required="true" disabled="true" type="userTree" />
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th  >制单人</th>
							<td>
								<input type="hidden" name="createrNo" value="${shipmentsSettlement.createrNo }"/>
								<input type="hidden" name="createrName" value="${shipmentsSettlement.createrName }"/>
								<input type="hidden" name="createrDept" value="${shipmentsSettlement.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${shipmentsSettlement.createDate  }' pattern='yyyy-MM-dd'/>" />
								${shipmentsSettlement.createrName }</td>
							<th  >制单日期</th>
							<td>
								<fmt:formatDate value="${shipmentsSettlement.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table> 
				</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>结算信息</legend>
				<div>
					<div id="tbGoods" style="padding:5px;height:auto">
					     <div>	
							<a id="addSubs" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
							<span class="toolbar-item dialog-tool-separator"></span>
					      	<a id="updateSubs" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
					   		<span class="toolbar-item dialog-tool-separator"></span>
					   		<a id="deleteSubs" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
					     </div>
					</div>
					<table id="dgSubList"></table>
				</div>
			</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>附件信息</legend>
				<input id="accParentEntity" type="hidden"  value="<%=ShipmentsSettlement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${shipmentsSettlement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</fieldset>	
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
var sub_obj = {
		init:function(){
			this.listSubs();
		},
		reload:function(){
			this.sub_list.datagrid("reload");
		},
		sub_list:'',
		listSubs:function(){
			this.sub_list = $('#dgSubList').datagrid({
				 queryParams: {
					'filter_EQL_shipmentsSettleId': $('#id').val()
				},
				method: "get",
				url: $('#id').val() ? '${ctx}/logistic/shipmentsSub/json' : '', 
				/* data:eval('(${shipmentsSettlement.settleSubs})'), */
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
					{field:'shipNo',title:'船名',sortable:true,width:20,
						formatter: function(value,row,index){
							var val;
							if(value!=''&& value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
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
					{field:'route',title:'航线',sortable:true,width:20},
					{field:'unloadDate',title:'卸货日期',sortable:true,width:10,
						formatter:function(value,row,index){
							if(value == null){
								return null;
							}
		            		var time = new Date(value);
		            		return time.format("yyyy-MM-dd");
		            	}
					},
					{field:'settleQuantity',title:'结算吨数',sortable:true,width:10},
					{field:'moneyCurrencyCode',title:'币种',width:10,
						formatter:function(value,row,index){
							if(value == null){
								return '';
							}else{
								var val = '';
								$.ajax({
									url:'${ctx}/system/dictUtil/getDictNameByCode/CURRENCY/'+value,
									type:'GET',
									async:false,
									success: function(data){
										if(data != null){
											val = data;
										} 
									}
								});
								return val;
							}
		            	}
					},
					{field:'unitPrice',title:'结算单价',sortable:true,width:10},
					{field:'totalPrice',title:'小计金额',sortable:true,width:10},
					{field:'remarks',title:'备注',sortable:true,width:20}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tbGoods'
			});
		},
		subs_form:'',
		addSubs:function(){
			var idValue = $('#id').val();
			if(idValue=="" || idValue==null || $("#contractNo").val()!=$("#contractNoOld").val()){
				$.messager.alert('提示','请先保存主表信息！','info');
				return;
			}
			sub_obj.subs_form = $("#dlg_sub").dialog({
				method:'GET',
			    title: '新增结算信息',    
			    width: 800,    
			    height: 400,     
			    href:'${ctx}/logistic/shipmentsSub/create/'+idValue,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#subMainform').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sub_obj.subs_form.panel('close');
					}
				}]
			});
		},
		updateSubs:function(){
			if($("#contractNo").val()!=$("#contractNoOld").val()){
				$.messager.alert('提示','请先保存主表信息！','info');
				return;
			}
			var row = sub_obj.sub_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			sub_obj.subs_form = $("#dlg_sub").dialog({   
			    title: '修改结算信息',    
			    width: 800,    
			    height: 400,
			    href:'${ctx}/logistic/shipmentsSub/update/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#subMainform').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sub_obj.subs_form.panel('close');
					}
				}]
			});
		},
		deleteSubs:function(){
			if($("#contractNo").val()!=$("#contractNoOld").val()){
				$.messager.alert('提示','请先保存主表信息！','info');
				return;
			}
			var row = sub_obj.sub_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:'${ctx}/logistic/shipmentsSub/delete/'+row.id,
						success: function(data){
							successTip(data,sub_obj.sub_list);
							sub_obj.reload();
						}
					});
				} 
			});
		},
		// 弹窗加载采购合同列表(同销售放货公用)
		toShipList:function(tradeCategory){
			var	sale=$("#dlg_ships").dialog({   
			    title: '船舶',    
			    width: 580,    
			    height: 350,  
			    href:'${ctx}/logistic/shipmentsSub/toShipList/'+tradeCategory,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						saveSelectShip();
						sale.panel('close');
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sale.panel('close');
					}
				}]
			});
		}
}
$(function(){
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
	    	$("#contractNoOld").val($("#contractNo").val());
     		successTipNew(data,dg);
     		sub_obj.init();
     		var action = document.getElementById("mainform").action;
     		document.getElementById("mainform").action = action.replace("create","update");
			$.easyui.loaded();
	    }
	});
	sub_obj.init();
	
	$('#accountName').combobox({
		 disabled:true,
		method: "get",
		url:'${ctx}/financial/paymentConfirm/getListNoYH',
		valueField : 'customerCode',
		textField : 'customerName',
		onChange:function(record){
			$('#account').val("");
			selectBank(record);
			$('#bank').combobox('clear');
		}
	});
	
	$('#bank').combobox({
		required : true,
		 disabled:true,
		method: "get",
		url:'${ctx}/baseInfo/baseUtil/jsonBankInfo',
		valueField : 'id',
		textField : 'bankName',
		prompt:'请先选择订船合同',
		onSelect:function(record){
			$('#account').val(record.bankNO);
		}
	});
	
	if('${action}' == 'update') {
		var code = $('#accountName').combobox('getValue');
		$('#bank').combobox({
//			panelHeight : 'auto',
		required : true,
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/selectBankByCode/'+code,
		valueField : 'id',
		textField : 'bankName'
	});
	}
	
	
	
	//弹窗增加采购信息
	 $("#addSubs").on("click", sub_obj.addSubs); 
	//弹窗修改采购信息
	$("#updateSubs").on("click", sub_obj.updateSubs);
	//删除商品
	$("#deleteSubs").on("click", sub_obj.deleteSubs);
	if('${action}' == 'view') {
		$("#tbGoods").hide();
	}
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		$("#toContractListId").hide();
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
	
});

function selectBank(code){
	$('#bank').combobox({
		required : true,
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/selectBankByCode/'+code,
		valueField : 'id',
		textField : 'bankName'
	});
}

</script>
</body>
</html>