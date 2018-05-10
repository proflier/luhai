<%@ page import="com.cbmie.lh.logistic.entity.WharfSettlement"%>
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
	<form id="mainform" action="${ctx}/logistic/wharf/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${wharfSettlement.id }" />
		<input type="hidden" id="relLoginNames" name="relLoginNames" value="${wharfSettlement.relLoginNames }"/>
		<div id="mainDiv" class="easyui-tabs" data-options="border:false">
			<div data-options="title: '基本信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">码头合同</th>
						<td width="30%">
							<input id="wharf" name="wharf" type="text" value="${wharfSettlement.wharf}" readonly="readonly" class="easyui-validatebox" onclick="toContractList()" data-options="required:true"/>
							<a id="toPortContractId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toContractList()">码头合同列表</a>
						</td>
						<th>预付</th>
						<td>
							<input name="prePay"  id="prePay"  type="text" value="${wharfSettlement.prePay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
					</tr>
					<tr>
						<th width="20%">商检水尺</th>
						<td>
							<input name="gaugeQuantity"  id="gaugeQuantity"  type="text" value="${wharfSettlement.gaugeQuantity}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
						<th width="20%">出入库日期</th>
						<td>
							<input id="stockDate" name="stockDate" type="text" value="<fmt:formatDate value='${wharfSettlement.stockDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>结算日期</th>
						<td>
							<input id="settleDate" name="settleDate" type="text" value="<fmt:formatDate value='${wharfSettlement.settleDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th>结算月份</th>
						<td>
							<input id="settleMonth" name="settleMonth" type="text" value="${wharfSettlement.settleMonth}" 
							class="easyui-combobox" data-options="valueField: 'label',textField: 'value',data: [{label: '1',value: '1'},{label: '2',value: '2'},{label: '3',value: '3'}
							,{label: '4',value: '4'},{label: '5',value: '5'},{label: '6',value: '6'},{label: '7',value: '7'},{label: '8',value: '8'},{label: '9',value: '9'},
							{label: '10',value: '10'},{label: '11',value: '11'},{label: '12',value: '12'}]" />
						</td>
					</tr>
					<tr>
						<th width="20%">应付</th>
						<td>
							<input name="shouldPay"  id="shouldPay"  type="text" value="${wharfSettlement.shouldPay}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
						<th width="20%">已开票</th>
						<td>
							<input name="alreadyBill"  id="alreadyBill"  type="text" value="${wharfSettlement.alreadyBill}" class="easyui-numberbox" data-options="precision:2,onChange:countBill"/>
						</td>
					</tr>
					<tr>
						<th width="20%">应开票</th>
						<td >
							<input name="shouldBill"  id="shouldBill"  type="text" value="${wharfSettlement.shouldBill}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
						<th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${wharfSettlement.businessManager}"  required="true" disabled="true" type="userTree" />
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th>制单人</th>
							<td>
								<input type="hidden" name="createrNo" value="${wharfSettlement.createrNo }"/>
								<input type="hidden" name="createrName" value="${wharfSettlement.createrName }"/>
								<input type="hidden" name="createrDept" value="${wharfSettlement.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${wharfSettlement.createDate  }' pattern='yyyy-MM-dd'/>" />
								${wharfSettlement.createrName }</td>
							<th  >制单日期</th>
							<td>
								<fmt:formatDate value="${wharfSettlement.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table> 
				</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>本次开票信息</legend>
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
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=WharfSettlement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${wharfSettlement.id}" />
			<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
		</div>
		</div>
	</form>
</div>

<script type="text/javascript">
function countPay(){
	var prePay = $("#prePay").val()?$("#prePay").val():0;
	var footerRows = sub_obj.sub_list.datagrid("getFooterRows");
	var totalPay = 0;
	if(footerRows.length>0){
		var footRow = footerRows[0];
		totalPay = footRow.totalPrice;
	}
	$("#shouldPay").numberbox('setValue',totalPay-prePay);
	var alreadyBill = $("#alreadyBill").val()?$("#alreadyBill").val():0;
	$("#shouldBill").numberbox('setValue',totalPay-alreadyBill);
}
function countBill(){
	var footerRows = sub_obj.sub_list.datagrid("getFooterRows");
	var totalPay = 0;
	if(footerRows.length>0){
		var footRow = footerRows[0];
		totalPay = footRow.totalPrice;
	}
	var alreadyBill = $("#alreadyBill").val()?$("#alreadyBill").val():0;
	$("#shouldBill").numberbox('setValue',totalPay-alreadyBill);
}
var sub_obj = {
		init:function(){
			this.listSubs();
		},
		reload:function(){
			this.sub_list.datagrid("reload");
		},
		change:false,
		backMain:function(){
			this.change=true;
		},
		sub_list:'',
		listSubs:function(){
			this.sub_list = $('#dgSubList').datagrid({
				 queryParams: {
					'filter_EQL_wharfSettlementId': $('#id').val()
				},
				method: "get",
				url: $('#id').val() ? '${ctx}/logistic/wharfSub/json' : '', 
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				showFooter:true,
				extEditing:false,
			    columns:[[    
					{field:'id',title:'id',hidden:true},
					{field:'shipNo',title:'船编号',width:40,
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
					{field:'portExpenseType',title:'费用类别',width:20,
						formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/dictUtil/getDictNameByCode/portExpenseType/"+value,
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
					{field:'settleQuantity',title:'计费吨数',width:10},
					{field:'unitPrice',title:'结算单价',width:10},
					{field:'totalPrice',title:'金额',width:10},
					{field:'roundup',title:'摘要',width:30},
					{field:'remarks',title:'备注',width:30}
			    ]],
			    onLoadSuccess:function(){
			    	if(sub_obj.change){
			    		sub_obj.change=false;
			    		countPay();
			    	}
			    },
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
			if(idValue=="" || idValue==null){
				$.messager.alert('提示','请先保存主表信息！','info');
				return;
			}
			sub_obj.subs_form = $("#dlg_sub").dialog({
				method:'GET',
			    title: '新增结算信息',    
			    width: 700,    
			    height: 400,     
			    href:'${ctx}/logistic/wharfSub/create/'+idValue,
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
			var row = sub_obj.sub_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			sub_obj.subs_form = $("#dlg_sub").dialog({   
			    title: '修改结算信息',    
			    width: 700,    
			    height: 400,
			    href:'${ctx}/logistic/wharfSub/update/'+row.id,
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
			var row = sub_obj.sub_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:'${ctx}/logistic/wharfSub/delete/'+row.id,
						success: function(data){
							var data = eval('(' + data + ')');
					    	if(data.returnFlag=='success'){
					    		sub_obj.subs_form.panel('close');
					    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
					    	}else if(data.returnFlag=='fail'){
					    		parent.$.messager.alert(data.returnMsg);
					    	}  
							sub_obj.backMain();
							sub_obj.reload();
						}
					});
				} 
			});
		}
		// 弹窗加载采购合同列表(同销售放货公用)
		/* toShipList:function(){
			var	sale=$("#dlg_ships").dialog({   
			    title: '船舶',    
			    width: 580,    
			    height: 350,  
			    href:'${ctx}/logistic/shipmentsSub/toShipList/wharfSettlement',
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						wharfSettlement_save();
						sale.panel('close');
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sale.panel('close');
					}
				}]
			});
		} */
}
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && $("#id").val() != ''){//主表校验通过且已经保存数据
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
     		successTipNew(data,dg);
     		sub_obj.init();
			$.easyui.loaded();
	    }
	});
	sub_obj.init();
	//弹窗增加采购信息
	 $("#addSubs").on("click", sub_obj.addSubs); 
	//弹窗修改采购信息
	$("#updateSubs").on("click", sub_obj.updateSubs);
	//删除商品
	$("#deleteSubs").on("click", sub_obj.deleteSubs);
	if('${action}' == 'view') {
		$("#tbGoods").hide();
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		$("#mainform").find(".easyui-numberbox").attr("readonly",true);
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		$("#toPortContractId").hide();
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