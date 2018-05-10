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
		<input id="id" name="id" type="hidden"  value="${transportSettlement.id }" />
		<div id="mainDiv" class="easyui-tabs" data-options="border:false">
			<div data-options="title: '运输信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">运输商</th>
						<td width="30%">
							${mytag:getAffiBaseInfoByCode(transportSettlement.transporter)}
						</td>
						<th width="20%">办事处</th>
						<td width="30%">
							${transportSettlement.office}
						</td>
					</tr>
					<tr>
						<th width="20%">运输方式</th>
						<td>
							${fns:getDictLabelByCode(transportSettlement.transportType,'YSFS','')}
						</td>
						<th width="20%">数量单位</th>
						<td>
							${fns:getDictLabelByCode(transportSettlement.numUnit,'sldw','')}
						</td>
					</tr>
					<tr>
						<th>运输路线</th>
						<td>
							${transportSettlement.route }
						</td>
						<th>收货方</th>
						<td>
							${mytag:getAffiBaseInfoByCode(transportSettlement.receiver)}
						</td>
					</tr>
					<tr>
						<th width="20%">结算开始日期</th>
						<td width="30%">
							<fmt:formatDate value='${transportSettlement.settleBeginDate}' />
						</td>
						<th width="20%">结算结束日期</th>
						<td width="30%">
							<fmt:formatDate value='${transportSettlement.settleEndDate}' />
						</td>
					</tr>
					<tr>
						<th width="20%">金额单位</th>
						<td>
							${fns:getDictLabelByCode(transportSettlement.moneyUnit,'moneyUnit','')}
						</td>
						<th width="20%">预付</th>
						<td>
							${transportSettlement.prePay}
						</td>
					</tr>
					<tr>
						<th width="20%">上期结余</th>
						<td>
							${transportSettlement.priorPay}
						</td>
						<th width="20%">已付</th>
						<td>
							${transportSettlement.alreadyPay}
						</td>
					</tr>
					<tr>
						<th width="20%">应付</th>
						<td>
							${transportSettlement.shouldPay}
						</td>
						<th width="20%">扣磅差费</th>
						<td>
							${transportSettlement.differPay}
						</td>
					</tr>
					<tr>
						<th width="20%">其他费用</th>
						<td>
							${transportSettlement.elsePay}
						</td>
						<th width="20%">实付</th>
						<td>
							${transportSettlement.realPay}
						</td>
					</tr>
					<tr>
						<th width="20%">已开票</th>
						<td>
							${transportSettlement.alreadyBill}
						</td>
						<th width="20%">应开票</th>
						<td>
							${transportSettlement.shouldBill}
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td colspan="3">
							${mytag:getUserByLoginName(transportSettlement.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${transportSettlement.remarks}
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th width="20%" >制单人</th>
							<td width="30%">
								<input type="hidden" name="createrNo" value="${transportSettlement.createrNo }"/>
								<input type="hidden" name="createrName" value="${transportSettlement.createrName }"/>
								<input type="hidden" name="createrDept" value="${transportSettlement.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${transportSettlement.createDate  }' pattern='yyyy-MM-dd'/>" />
								${transportSettlement.createrName }</td>
							<th width="20%" >制单日期</th>
							<td width="30%">
								<fmt:formatDate value="${transportSettlement.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table> 
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>本次开票信息</legend>
					<div>
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
</div>

<script type="text/javascript">
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
	sub_obj.init();
});
</script>
</body>
</html>