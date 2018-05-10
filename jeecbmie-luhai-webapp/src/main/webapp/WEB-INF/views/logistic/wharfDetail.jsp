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
		<input id="id" name="id" type="hidden"  value="${wharfSettlement.id }" />
		<div id="mainDiv" class="easyui-tabs" data-options="border:false">
			<div data-options="title: '基本信息', iconCls: false, refreshable: false">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">码头合同</th>
						<td width="30%">
							${wharfSettlement.wharf}
						</td>
						<th>预付</th>
						<td>
							${wharfSettlement.prePay}
						</td>
					</tr>
					<tr>
						<th width="20%">商检水尺</th>
						<td>
							${wharfSettlement.gaugeQuantity}
						</td>
						<th width="20%">出入库日期</th>
						<td>
							<fmt:formatDate value='${wharfSettlement.stockDate}' />
						</td>
					</tr>
					<tr>
						<th>结算日期</th>
						<td>
							<fmt:formatDate value='${wharfSettlement.settleDate}' />
						</td>
						<th>结算月份</th>
						<td>
							${wharfSettlement.settleMonth}
						</td>
					</tr>
					<tr>
						<th width="20%">应付</th>
						<td>
							${wharfSettlement.shouldPay}
						</td>
						<th width="20%">已开票</th>
						<td>
							${wharfSettlement.alreadyBill}
						</td>
					</tr>
					<tr>
						<th width="20%">应开票</th>
						<td >
							${wharfSettlement.shouldBill}
						</td>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(wharfSettlement.businessManager).name}
						</td>
					</tr>
				</table>
				</fieldset>
				
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th width="20%">制单人</th>
							<td width="30%">
								<input type="hidden" name="createrNo" value="${wharfSettlement.createrNo }"/>
								<input type="hidden" name="createrName" value="${wharfSettlement.createrName }"/>
								<input type="hidden" name="createrDept" value="${wharfSettlement.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${wharfSettlement.createDate  }' pattern='yyyy-MM-dd'/>" />
								${wharfSettlement.createrName }</td>
							<th width="20%" >制单日期</th>
							<td width="30%">
								<fmt:formatDate value="${wharfSettlement.createDate  }" pattern="yyyy-MM-dd"/>
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
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=WharfSettlement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${wharfSettlement.id}" />
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