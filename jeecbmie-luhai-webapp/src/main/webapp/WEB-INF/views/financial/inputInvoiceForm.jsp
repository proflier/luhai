<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
		<form id="mainform"  action="${ctx}/financial/inputInvoice/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '进项管理', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>进项发票</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">发票分类</th>
						<td width="30%">
							<input id="id" name="id" type="hidden"  value="${inputInvoice.id }" />
							<input id="invoiceClassCode" name="invoiceClassCode" type="text"  value="${inputInvoice.invoiceClassCode }"/>
							<input id="invoiceClassModel" name="invoiceClassModel" type="hidden"  value="${inputInvoice.invoiceClassModel }"/>
						</td>
						<th width="20%">发票类型</th>
						<td width="30%">
							<input id="invoiceTypeCode" name="invoiceTypeCode" type="text"  value="${inputInvoice.invoiceTypeCode }"/>
							<input id="invoiceTypeModel" name="invoiceTypeModel" type="hidden"  value="${inputInvoice.invoiceTypeModel }"/>
						</td>
					</tr>
					<tr>
						<th>贸易方式</th>
						<td>
							<input id="tradeCode" name="tradeCode" type="text"  value="${inputInvoice.tradeCode }"/>
							<input id="tradeMode" name="tradeMode" type="hidden"  value="${inputInvoice.tradeMode }"/>
						</td>
						<th>客户名</th>
						<td>
							<mytag:combobox name="userName" value="${inputInvoice.userName}" type="customer" parameters="10230002" hightAuto="false"  permission4User="true"/>
						</td>
					</tr>
					<tr>
						<th>账户</th>
						<td>
							<input id="account" name="account" type="text"  value="${inputInvoice.account }" class="easyui-validatebox"  />
						</td>
						<th>开户行</th>
						<td>
							<input id="accountBank" name="accountBank" type="text"  value="${inputInvoice.accountBank }" class="easyui-validatebox"   />
						</td>
					</tr>
					<tr>
						<th>开票单位</th>
						<td>
							<mytag:combobox name="issuingOffice" value="${inputInvoice.issuingOffice}" type="customer" parameters="10230001" hightAuto="false"/>
						</td>
						<th>结算中心</th>
						<td>
							<input id="centerSettlement" name="centerSettlement" type="text" value="${inputInvoice.centerSettlement }"/>
						</td>
					</tr>
					<tr>
						<th>税票张数</th>
						<td>
							<input id="numStamps" name="numStamps" type="text" class="easyui-numberbox" data-options="min:0,max:1000000" value="${inputInvoice.numStamps }"/>
						</td>
						<th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${inputInvoice.businessManager}"  required="true"  type="userTree" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td style="height:1cm" colspan="3"><textarea  name="remarks" type="text" id="remarks"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${inputInvoice.remarks}</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">制单人</th>
						<td width="30%">${empty inputInvoice.createrName ? sessionScope.user.name : inputInvoice.createrName }</td>
						<th width="20%" >制单部门</th>
						<td width="30%">${empty inputInvoice.createrDept ? sessionScope.user.organization.orgName : inputInvoice.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ inputInvoice.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${inputInvoice.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>发票信息</legend>
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
					<table id="dgInputInvoiceSub"></table>
				</div>
				</fieldset>	
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,operObj.list);
				sub_obj.init();
				$.easyui.loaded();
			} 
		});
		//发票分类
		$('#invoiceClassCode').combobox({
				panelHeight : 'auto',
				required : true,
				url : '${ctx}/system/dictUtil/getDictByCode/invoiceClass',
				valueField : 'code',
				textField : 'name',
				onSelect:function(data){
					$('#invoiceClassModel').val(data.name);
				} 
			});
		//发票类型
		$('#invoiceTypeCode').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dictUtil/getDictByCode/invoiceType',
			valueField : 'code',
			textField : 'name',
			onSelect:function(data){
				$('#invoiceTypeModel').val(data.name);
			} 
		});
		//贸易方式
		$('#tradeCode').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dictUtil/getDictByCode/tradeClass',
			valueField : 'code',
			textField : 'name',
			onSelect:function(data){
				$('#tradeMode').val(data.name);
			} 
		});
		//开票单位
		$('#issuingOffice').combobox({
			onSelect: function () {  
		        var centerSettlement = $("#issuingOffice").combobox('getText')  
				$("#centerSettlement").val(centerSettlement);
		    }  
		});
		
	});
	var sub_obj = {
			init:function(){
				this.listSubs();
			},
			reload:function(){
				this.sub_list.datagrid("reload");
			},
			sub_list:{},
			listSubs:function(){
				var url = '';
				if($("#id").val()==null || $("#id").val()==""){
				}else{
					url='${ctx}/financial/inputInvoiceSub/json?filter_EQL_inputInvoiceSubId='+$("#id").val();
				}
				this.sub_list = $('#dgInputInvoiceSub').datagrid({
					method: "get",
					url: url,
				  	fit : false,
					fitColumns : true,
					scrollbarSize : 0,
					border : false,
					striped:true,
					idField : 'id',
					rownumbers:true,
					singleSelect:true,
					extEditing:false,
					showFooter:true,
				    columns:[[    
						{field:'id',title:'id',hidden:true},
						{field:'purchaseOrderNumber',title:'采购订单号',sortable:true,width:20},
						{field:'relatedSalesOrder',title:'相关销售订单',sortable:true,width:20},
						{field:'invoiceNo',title:'发票号',sortable:true,width:20},
						{field:'billingDate',title:'开票日期',sortable:true,width:15},
						{field:'productNameView',title:'品名',sortable:true,width:10},
						{field:'unitMeasurementView',title:'计量单位',sortable:true,width:10},
						{field:'mount',title:'数量',sortable:true,width:10},
						{field:'prices',title:'单价',sortable:true,width:15},
						{field:'allPrices',title:'开票金额',sortable:true,width:20},
						{field:'taxMoney',title:'税额',sortable:true,width:10},
						{field:'rebateRate',title:'退税率',sortable:true,width:10}
				    ]],
				    onLoadSuccess:function(){
				    	if(sub_obj.change){
				    		sub_obj.change=false;
				    		var footerRows = sub_obj.sub_list.datagrid("getFooterRows");
					    	if(footerRows.length>0){
					    		var footRow = footerRows[0];
					    		$("#mount").numberbox('setValue',footRow.mount);
					    		$("#prices").numberbox('setValue',footRow.prices);
					    		$("#allPrices").numberbox('setValue',footRow.allPrices);
					    		$("#taxMoney").numberbox('setValue',footRow.taxMoney);
					    		$("#mainform").submit();
					    	}
				    	}
				    },
				    enableHeaderClickMenu: false,
				    enableHeaderContextMenu: false,
				    enableRowContextMenu: false,
				    toolbar:'#tbGoods'
				});
			},
			sub_form:'',
			addSubs:function(){
				var idValue = $('#id').val();
				if(idValue=="" || idValue==null){
					$.messager.alert('提示','请先保存主表信息！','info');
					return;
				}
				sub_obj.sub_form = $("#dlg_sub").dialog({
					method:'GET',
				    title: '新增信息',    
				    width: 700,    
				    height: 400,     
				    href:'${ctx}/financial/inputInvoiceSub/create/'+idValue,
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
							sub_obj.sub_form.panel('close');
						}
					}]
				});
			},
			updateSubs:function(){
				var row = sub_obj.sub_list.datagrid('getSelected');
				if(rowIsNull(row)) return;
				sub_obj.sub_form = $("#dlg_sub").dialog({   
				    title: '修改信息',    
				    width: 700,    
				    height: 400,
				    href:'${ctx}/financial/inputInvoiceSub/update/'+row.id,
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
							sub_obj.sub_form.panel('close');
						}
					}]
				});
			},
			deleteSubs:function(){
				var row = sub_obj.sub_list.datagrid('getSelected');
				if(rowIsNull(row)) return;
				parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
					if (data){
						$.ajax({
							type:'get',
							url:'${ctx}/financial/inputInvoiceSub/delete/'+row.id,
							success: function(data){
						    	if(data.returnFlag=='success'){
						    		sub_obj.subs_form.panel('close');
						    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
						    	}else if(data.returnFlag=='fail'){
						    		parent.$.messager.alert(data.returnMsg);
						    	}  
								sub_obj.reload();
							}
						});
					} 
				});
			}
	}
	$(function(){
		sub_obj.init();
		 $("#addSubs").on("click", sub_obj.addSubs); 
		$("#updateSubs").on("click", sub_obj.updateSubs);
		$("#deleteSubs").on("click", sub_obj.deleteSubs);
	});
	//弹窗加载采购合同列表
	function toSaleList(){
		var	sale=$("#dlg_hetong").dialog({   
		    title: '销售合同',    
		    width: 680,    
		    height: 450,  
		    href:'${ctx}/sale/saleDelivery/toSaleDeliverySaleContractList',
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					//setSaleContractNos();
					selectObj.init();
					selectObj.recordCount();
					if(selectObj.contractCount==0){
						return false;
					}
					if(selectObj.contractCount>1){
						alert("请选择同一个销售合同下的货物");
						return false;
					}
					$("#relatedSalesOrder").val(selectObj.saleContractNo);
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
</script>

</body>
</html>