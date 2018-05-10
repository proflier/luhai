<%@ page import="com.cbmie.lh.logistic.entity.TransportSettlement"%>
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
	<form id="mainform" action="${ctx}/logistic/transport/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${transportSettlement.id }" />
		
		<div id="mainDiv" class="easyui-tabs" data-options="border:false">
			<div data-options="title: '运输信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">运输商</th>
						<td width="30%">
							<mytag:combobox name="transporter" value="${transportSettlement.transporter}" type="customer" parameters="10230009"/>
						</td>
						<th width="20%">办事处</th>
						<td width="30%">
							<input name="office" type="text" value="${transportSettlement.office}" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th width="20%">运输方式</th>
						<td>
							<mytag:combobox name="transportType" value="${transportSettlement.transportType}" type="dict" parameters="YSFS"/>
						</td>
						<th width="20%">数量单位</th>
						<td>
							<mytag:combobox name="numUnit" value="${transportSettlement.numUnit}" type="dict" parameters="sldw"/>
						</td>
					</tr>
					<tr>
						<th>运输路线</th>
						<td>
							<input id="route" name="route"  type="text" value="${transportSettlement.route }" class="easyui-validatebox"  />
						</td>
						<th>收货方</th>
						<td>
							<input id="receiver" name="receiver"  type="text" value="${transportSettlement.receiver }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th width="20%">结算开始日期</th>
						<td width="30%">
							<input id="settleBeginDate" name="settleBeginDate" type="text" value="<fmt:formatDate value='${transportSettlement.settleBeginDate}' />" 
							class="Wdate easyui-validatebox" data-options="required:true"/>
						</td>
						<th width="20%">结算结束日期</th>
						<td width="30%">
							<input id="settleEndDate" name="settleEndDate" type="text" value="<fmt:formatDate value='${transportSettlement.settleEndDate}' />" 
							class="Wdate easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th width="20%">金额单位</th>
						<td>
							<mytag:combobox name="moneyUnit" value="${transportSettlement.moneyUnit}" type="dict" parameters="moneyUnit"/>
						</td>
						<th width="20%">预付</th>
						<td>
							<input name="prePay"  id="prePay"  type="text" value="${transportSettlement.prePay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
					</tr>
					<tr>
						<th width="20%">上期结余</th>
						<td>
							<input name="priorPay"  id="priorPay"  type="text" value="${transportSettlement.priorPay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
						<th width="20%">已付</th>
						<td>
							<input name="alreadyPay"  id="alreadyPay"  type="text" value="${transportSettlement.alreadyPay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
					</tr>
					<tr>
						<th width="20%">应付</th>
						<td>
							<input name="shouldPay"  id="shouldPay"  type="text" value="${transportSettlement.shouldPay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
						<th width="20%">扣磅差费</th>
						<td>
							<input name="differPay"  id="differPay"  type="text" value="${transportSettlement.differPay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
					</tr>
					<tr>
						<th width="20%">其他费用</th>
						<td>
							<input name="elsePay"  id="elsePay"  type="text" value="${transportSettlement.elsePay}" class="easyui-numberbox" data-options="precision:2,onChange:countPay"/>
						</td>
						<th width="20%">实付</th>
						<td>
							<input name="realPay"  id="realPay"  type="text" value="${transportSettlement.realPay}" class="easyui-numberbox" data-options="precision:2,onChange:countBill"/>
						</td>
					</tr>
					<tr>
						<th width="20%">已开票</th>
						<td>
							<input name="alreadyBill"  id="alreadyBill"  type="text" value="${transportSettlement.alreadyBill}" class="easyui-numberbox" data-options="precision:2,onChange:countBill"/>
						</td>
						<th width="20%">应开票</th>
						<td>
							<input name="shouldBill"  id="shouldBill"  type="text" value="${transportSettlement.shouldBill}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
					</tr>
					<tr>
						<th  >业务经办人 </th>
						<td colspan="3">
							<mytag:combotree name="businessManager" value="${transportSettlement.businessManager}"  required="true"  type="userTree" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea name="remarks" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,1000]'],value:'${transportSettlement.remarks}'"/>
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
								<input type="hidden" name="createrNo" value="${transportSettlement.createrNo }"/>
								<input type="hidden" name="createrName" value="${transportSettlement.createrName }"/>
								<input type="hidden" name="createrDept" value="${transportSettlement.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${transportSettlement.createDate  }' pattern='yyyy-MM-dd'/>" />
								${transportSettlement.createrName }</td>
							<th  >制单日期</th>
							<td>
								<fmt:formatDate value="${transportSettlement.createDate  }" pattern="yyyy-MM-dd"/>
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
			<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>	
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=TransportSettlement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${transportSettlement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
function countPay(){
	var prePay = $("#prePay").val()?$("#prePay").val():0;
	var priorPay = $("#priorPay").val()?$("#priorPay").val():0;
	var alreadyPay = $("#alreadyPay").val()?$("#alreadyPay").val():0;
	var shouldPay = $("#shouldPay").val()?$("#shouldPay").val():0;
	var differPay = $("#differPay").val()?$("#differPay").val():0;
	var elsePay = $("#elsePay").val()?$("#elsePay").val():0;
	var realPay = 0;
	var alreadyBill = $("#alreadyBill").val()?$("#alreadyBill").val():0;
	var shouldBill = 0;
	realPay = shouldPay-prePay-priorPay-alreadyPay-differPay+elsePay*1;
	$("#realPay").numberbox('setValue',realPay);
	shouldBill = realPay-alreadyBill;
	$("#shouldBill").numberbox('setValue',shouldBill);
}
function countBill(){
	var realPay = $("#realPay").val()?$("#realPay").val():0;
	var alreadyBill = $("#alreadyBill").val()?$("#alreadyBill").val():0;
	var shouldBill = 0;
	shouldBill = realPay-alreadyBill;
	$("#shouldBill").numberbox('setValue',shouldBill);
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
					'filter_EQL_transportSettlementId': $('#id').val()
				},
				method: "get",
				url: $('#id').val() ? '${ctx}/logistic/transportSub/json' : '', 
				/* data:eval('(${transportSettlement.settleSubs})'), */
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
					{field:'outStockQuantity',title:'出库吨数',width:20},
					{field:'arrivalQuantity',title:'到货吨数',width:20},
					{field:'settleQuantity',title:'结算吨数',width:20},
					{field:'unitPrice',title:'运费单价',width:20},
					{field:'shouldPay',title:'应付',width:20},
					{field:'differPay',title:'扣磅差费',width:20},
					{field:'elsePay',title:'其他费用',width:20},
					{field:'realPay',title:'实付',width:20},
					{field:'roundup',title:'摘要',width:20},
					{field:'remarks',title:'备注',width:20}
			    ]],
			    onLoadSuccess:function(){
			    	if(sub_obj.change){
			    		sub_obj.change=false;
			    		var footerRows = sub_obj.sub_list.datagrid("getFooterRows");
				    	if(footerRows.length>0){
				    		var footRow = footerRows[0];
				    		$("#shouldPay").numberbox('setValue',footRow.shouldPay);
				    		$("#differPay").numberbox('setValue',footRow.differPay);
				    		$("#elsePay").numberbox('setValue',footRow.elsePay);
				    		$("#mainform").submit();
				    	}
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
			    href:'${ctx}/logistic/transportSub/create/'+idValue,
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
			    href:'${ctx}/logistic/transportSub/update/'+row.id,
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
						url:'${ctx}/logistic/transportSub/delete/'+row.id,
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
	$('#settleBeginDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'settleEndDate\')}',onpicked:function(){deliveryTerm.click();}});
	});
	$('#settleEndDate').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'settleBeginDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
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
	}
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		$("#mainform").find(".easyui-numberbox").attr("readonly",true);
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
	
	//收货方收货方
	$('#receiver').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/baseInfo/baseUtil/getAffiOurUnitOrCustomer',
		valueField : 'customerCode',
		textField : 'customerName',
	});
});
</script>
</body>
</html>