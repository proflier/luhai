<%@page import="com.cbmie.genMac.foreignTrade.entity.AgencyAgreement" %>
<%@page import="com.cbmie.genMac.foreignTrade.entity.AgreementGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="mainDiv" class="easyui-tabs" data-options="border: false">
<div data-options="title: '协议信息', iconCls: false, refreshable: false">
<form id="mainform" action="${ctx}/foreignTrade/agencyAgreement/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<!--移动端截取标志-->
<table width="98%" class="tableClass">
	<tr>
		<th><font color="red">*</font>我司单位</th>
		<td colspan="3">
		<input type="text" id="ourUnit" name="ourUnit" value="${agencyAgreement.ourUnit }"/>
		</td>
		<th>签约地</th>
		<td>
		<input type="text" name="signedPlace" value="${agencyAgreement.signedPlace }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>代理协议号</th>
		<td>
		<input type="hidden" name="id" value="${id }"/>
		<input type="text" name="agencyAgreementNo" value="${agencyAgreement.agencyAgreementNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th><font color="red">*</font>协议类型</th>
		<td>
		<input type="text" id="agreementType" name="agreementType" value="${agencyAgreement.agreementType }"/>
		</td>
		<th><font color="red">*</font>业务员</th>
		<td>
		<input type="text" id="salesman" name="salesman" value="${agencyAgreement.salesman }" />
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>签署日期</th>
		<td>
		<input type="text" name="signingDate" value="<fmt:formatDate value="${agencyAgreement.signingDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
		<th>生效日期</th>
		<td>
		<input type="text" name="effectDate" value="<fmt:formatDate value="${agencyAgreement.effectDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
		</td>
		<th><font color="red">*</font>是否是保税区业务</th>
		<td>
		<input type="radio" name="bondedArea" value="1" style="margin-top:-2px" <c:if test="${agencyAgreement.bondedArea eq 1}">checked</c:if>/>是
		<input type="radio" name="bondedArea" value="0" style="margin-top:-2px" <c:if test="${agencyAgreement.bondedArea eq 0}">checked</c:if>/>否
		</td>
	</tr>
</table>
<!--移动端截取标志-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>委托方信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th><font color="red">*</font>委托方</th>
		<td colspan="3">
		<input type="text" id="client" name="client" value="${agencyAgreement.client }"/>
		</td>
		<th><font color="red">*</font>所属地区</th>
		<td>
		<input type="text" id="district" name="district" value="${agencyAgreement.district }"/>
		</td>
	</tr>
	<tr>
		<th>代理费方式</th>
		<td colspan="3" id="agencyFeeWay"></td>
		<th>手续费基数</th>
		<td>
		<input type="text" id="commissionBase" name="commissionBase" value="${agencyAgreement.commissionBase }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',disabled:true"/>
		</td>
	</tr>
	<tr>
		<th>每美元多少人民币</th>
		<td>
		<input type="text" id="everyDollarToRMB" name="everyDollarToRMB" value="${agencyAgreement.everyDollarToRMB }" class="easyui-numberbox" data-options="min:0,precision:2" onblur="calculate($('#agreementGoodsTB').datagrid('getRows'))"/>
		</td>
		<th>代理费率%</th>
		<td>
		<input type="text" id="agencyFee" name="agencyFee" value="${agencyAgreement.agencyFee }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'" onblur="calculate($('#agreementGoodsTB').datagrid('getRows'))"/>
		</td>
		<th>代理费收入</th>
		<td>
		<input type="text" id="agencyFeeIncome" name="agencyFeeIncome" value="${agencyAgreement.agencyFeeIncome }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>国内保证金比例%</th>
		<td>
		<input type="text" name="domesticMarginRatio" value="${agencyAgreement.domesticMarginRatio }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%',required:true"/>
		</td>
		<th><font color="red">*</font>发函人</th>
		<td>
		<input type="text" id="sentLetterPerson" name="sentLetterPerson" value="${empty agencyAgreement.sentLetterPerson ? sessionScope.user.id : agencyAgreement.sentLetterPerson }"/>
		</td>
		<th>其他保证金说明</th>
		<td>
		<input type="text" name="otherMarginExplain" value="${agencyAgreement.otherMarginExplain }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>币种</th>
		<td>
		<input type="text" id="currency" name="currency" value="${agencyAgreement.currency }"/>
		</td>
		<th><font color="red">*</font>对人民币汇率</th>
		<td>
		<input type="text" id="rmbRate" name="rmbRate" value="${agencyAgreement.rmbRate }" class="easyui-numberbox" data-options="min:0,precision:4,required:true" onblur="rmbRateOnblur()"/>
		</td>
		<th><font color="red">*</font>对美元汇率</th>
		<td>
		<input type="text" id="dollarRate" name="dollarRate" value="${agencyAgreement.dollarRate }" class="easyui-numberbox" data-options="min:0,precision:4,required:true" onblur="calculate($('#agreementGoodsTB').datagrid('getRows'))"/>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty agencyAgreement.createDate ? now : agencyAgreement.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty agencyAgreement.createrName ? sessionScope.user.name : agencyAgreement.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${agencyAgreement.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="agreementGoodsJson" id="agreementGoodsJson"/>
</form>
</div>
<div data-options="title: '协议商品', iconCls: false, refreshable: false">
<div id="agreementGoodsToolbar" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="agreementGoodsTB" data-options="onClickRow: onClickRow"></table>
<%
	AgencyAgreement agencyAgreement = (AgencyAgreement)request.getAttribute("agencyAgreement");
	List<AgreementGoods> agreementGoods = agencyAgreement.getAgreementGoods();
	ObjectMapper objectMapper = new ObjectMapper();
	String agreementGoodsJson = objectMapper.writeValueAsString(agreementGoods);
	request.setAttribute("agreementGoodsJson", agreementGoodsJson);
%>
</div>
</div>
<script type="text/javascript">
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if(!$('#mainform').form('validate')){
	    		$("#mainDiv").tabs("select", tabIndex);
	    	}else{
	    		tabIndex = index;
	    	}
	    }
	});
	
	$('#sentLetterPerson').combobox({
		method:'GET',
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/user/json',
		valueField : 'id',
		textField : 'name',
		loadFilter:function(data){
			return data.rows;
		}
	});
	
	//业务员
	$('#salesman').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/user/getRelativeUser/18',//业务员对应角色id
		valueField : 'name',
		textField : 'name',
	});
	
	$('#agreementType').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dict/getDictByCode/agreementType',
		valueField : 'name',
		textField : 'name',
	});
	
	$('#currency').combobox({
		method:'GET',
		panelHeight:'auto',
		required:true,
		url:'${ctx}/baseinfo/exchangerate/getNewExchangeRate',
		valueField : 'currency',
		textField : 'currency',
		onSelect:function(record){
			$('#rmbRate').numberbox({value:record.exchangeRateSelf});
			$('#dollarRate').numberbox({value:record.exchangeRateUS});
		}
	});
	
	$('#district').combotree({
		panelHeight:'auto',
		required:true,
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true
	});
	
	$('#ourUnit').combobox({
		width:410,
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/baseinfo/affiliates/getSelfCompany',
		valueField : 'id',
		textField : 'customerName'
	});
	
	$('#client').combobox({
		width:410,
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/baseinfo/affiliates/getCustomer',
		valueField : 'customerName',
		textField : 'customerName',
		onSelect:function(record){
			$('#district').combotree('setValue', record.country);
		}
	});
	
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/agencyFeeWay',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			$.each(data, function(index, value){
				$("#agencyFeeWay").append("<input type='radio' name='agencyFeeWay'" + ((value.name == '${agencyAgreement.agencyFeeWay }' || index == 0) ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:-2px' onclick='chooseAgencyFeeWay(this)'/>" + value.name);
			});
			chooseAgencyFeeWay($('input:radio[name="agencyFeeWay"]:checked'));
		}
	});
	
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    }
	});

	$('#agreementGoodsTB').datagrid({
		data : JSON.parse('${agreementGoodsJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		frozenColumns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'商品大类',width:100,
				editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'goodsName',
						textField:'goodsName',
						method:'get',
						url:'${ctx}/baseinfo/goodsManage/json',
						required:true,
						loadFilter:function(data){
							return data.rows;
						}
					}
				}
			},
			{field:'specification',title:'规格型号',width:100,editor:{type:'validatebox',options:{required:true}}},
			{field:'origin',title:'原产地',width:100,editor:{type:'validatebox'}},
			{field:'cname',title:'中文品名',width:100,editor:{type:'validatebox',options:{required:true}}},
			{field:'ename',title:'英文品名',width:100,editor:{type:'validatebox'}},
		]],
	    columns:[
			[
			{"title":"合同","colspan":3},  
			{"title":"合同金额","colspan":2}
			],
			[
			{field:'amount',title:'数量',width:20,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}},
			{field:'unit',title:'单位',width:20,
				editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'name',
						textField:'name',
						method:'get',
						url:'${ctx}/system/dict/getDictByCode/DW',
						required:true
					}
				}
			},
			{field:'price',title:'单价',width:20,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}},
			{field:'original',title:'原币金额',width:40,
				formatter: function(value, row, index){
					if(value != null){
						var str = value.toString().split(".");
						return str[0].replace(/\B(?=(?:\d{3})+$)/g, ',') + (str.length == 1 ? "" : "." + str[1]);
					}
				}
			},
			{field:'rmb',title:'人民币金额',width:40,
				formatter: function(value, row, index){
					if(value != null){
						var str = value.toString().split(".");
						return str[0].replace(/\B(?=(?:\d{3})+$)/g, ',') + (str.length == 1 ? "" : "." + str[1]);
					}
				}
			}
			]
		],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#agreementGoodsToolbar',
	    onEndEdit : function(index, row, changes){
	    	$(this).datagrid('getRows')[index]['original'] = (row.price * row.amount).toFixed(2);
	    	$(this).datagrid('getRows')[index]['rmb'] = (row.price * row.amount * $('#rmbRate').val()).toFixed(2);
	    }
	});
});

function rmbRateOnblur(){
	var rows = $('#agreementGoodsTB').datagrid('getRows');
	for(var i = 0; i < rows.length; i++){
		$('#agreementGoodsTB').datagrid('updateRow', {
			index: i,
			row: {
				rmb: (rows[i].price * rows[i].amount * $('#rmbRate').val()).toFixed(2)
			}
		});
	}
	calculate(rows);
}

function chooseAgencyFeeWay(agencyFeeWay){
	if($(agencyFeeWay).val() == "代理费率"){
		$('#agencyFee').numberbox({
			disabled: false,
			required: true
		});
		$('#everyDollarToRMB').numberbox({
			disabled: true,
			required: false
		});
		$('#agencyFeeIncome').numberbox({
			disabled: true,
			required: false
		});
	}
	if($(agencyFeeWay).val() == "固定代理费"){
		$('#agencyFee').numberbox({
			disabled: true,
			required: false
		});
		$('#everyDollarToRMB').numberbox({
			disabled: true,
			required: false
		});
		$('#agencyFeeIncome').numberbox({
			disabled: false,
			required: true
		});
	}
	if($(agencyFeeWay).val() == "每美元多少人民币"){
		$('#agencyFee').numberbox({
			disabled: true,
			required: false
		});
		$('#everyDollarToRMB').numberbox({
			disabled: false,
			required: true
		});
		$('#agencyFeeIncome').numberbox({
			disabled: true,
			required: false
		});
	}
}

var editIndex = undefined;
function endEditing(){
	if(editIndex == undefined){return true}
	if($('#agreementGoodsTB').datagrid('validateRow', editIndex)){
		$('#agreementGoodsTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if(editIndex != index){
		if (endEditing()){
			$('#agreementGoodsTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#agreementGoodsTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if(endEditing()){
		$('#agreementGoodsTB').datagrid('appendRow',{});
		editIndex = $('#agreementGoodsTB').datagrid('getRows').length-1;
		$('#agreementGoodsTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if(editIndex == undefined){return}
	$('#agreementGoodsTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if(endEditing()){
		var rows = $('#agreementGoodsTB').datagrid('getRows');
		$('#agreementGoodsTB').datagrid('acceptChanges');
		$('#agreementGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#agreementGoodsTB').datagrid('selectRow', i).datagrid('beginEdit', i);
				editIndex = i;
				return false;
			}else{
				array.push(goods);
			}
		}
		calculate(rows);
		return true;
	}
}
function reject(){
	$('#agreementGoodsTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#agreementGoodsTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function calculate(rows){
	var agencyFeeWay = $('input:radio[name="agencyFeeWay"]:checked').val();//获取代理方式
	//计算手续费基数
	var commissionBase = 0;
	var original = 0;
	for(var i = 0; i < rows.length; i++){
		commissionBase += parseFloat(rows[i].rmb);
		original += parseFloat(rows[i].original);
	}
	$('#commissionBase').numberbox({
		value:commissionBase
	});
	//计算代理费收入
	if(agencyFeeWay == "代理费率"){
		$('#agencyFeeIncome').numberbox({
			value : parseFloat($('#agencyFee').val()) * 0.01 * commissionBase
		});
	}else if(agencyFeeWay == "每美元多少人民币"){
		$('#agencyFeeIncome').numberbox({
			value : $('#everyDollarToRMB').val() * $('#dollarRate').val() * original
		});
	}
}
</script>
</body>
</html>