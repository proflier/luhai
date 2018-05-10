<%@page import="com.cbmie.lh.logistic.entity.CustomsDeclaration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<form id="mainform"  action="${ctx}/logistic/customsDeclaration/${action}" method="post">
<input id="id" name="id" type="hidden" value="${customsDeclaration.id }"/>
<div id="mainDiv" data-options="border:false">
	<div data-options="title: '报关单信息', iconCls: false, refreshable: false" >	
		<fieldset class="fieldsetClass" >
		<legend>报关单信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th>船名</th>
				<td colspan="3">
					<input id="shipNo" name="shipNo" type="text" value="${customsDeclaration.shipNo }" class="easyui-combogrid" data-options="required:true"/>
				</td>
				<th>海关编号</th>
				<td colspan="3">
					<input name="customsCode" value="${customsDeclaration.customsCode }" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>收发货人</th>
				<td>
					<input name="consignee" value="${customsDeclaration.consignee }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>进口口岸</th>
				<td>
					<input name="importPort" value="${customsDeclaration.importPort }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>进口日期</th>
				<td>
					<input name="importDate" value="<fmt:formatDate value='${customsDeclaration.importDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
				<th>申报日期</th>
				<td>
					<input name="declarationDate" value="<fmt:formatDate value='${customsDeclaration.declarationDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>消费使用单位</th>
				<td>
					<input name="consumptionUnit" value="${customsDeclaration.consumptionUnit }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>运输方式</th>
				<td>
					<mytag:combobox name="transportation" value="${customsDeclaration.transportation}" type="dict" parameters="YSFS"/>
				</td>
				<th>运输工具名称</th>
				<td>
					<input name="transportName" value="${customsDeclaration.transportName }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>提运单号</th>
				<td>
					<input name="deliveryNumber" value="${customsDeclaration.deliveryNumber }" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>申报单位</th>
				<td>
					<input name="applicationUnit" value="${customsDeclaration.applicationUnit }" class="easyui-validatebox"/>
				</td>
				<th>监管方式</th>
				<td>
					<mytag:combobox name="regulatoryWay" value="${customsDeclaration.regulatoryWay}" type="dict" parameters="REGULATORYWAY"/>
				</td>
				<th>征免性质</th>
				<td>
					<mytag:combobox name="natureExemption" value="${customsDeclaration.natureExemption}" type="dict" parameters="NATUREEXEMPTION"/>
				</td>
				<th>备案号</th>
				<td>
					<input name="record" value="${customsDeclaration.record }" class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>贸易国（地区）</th>
				<td>
					<mytag:combotree name="tradeNation" value="${customsDeclaration.tradeNation}" type="countryAreaTree"/>
				</td>
				<th>启运国（地区）</th>
				<td>
					<mytag:combotree name="beginNation" value="${customsDeclaration.beginNation}" type="countryAreaTree"/>
				</td>
				<th>装货港</th>
				<td>
					<input name="loadingPort" value="${customsDeclaration.loadingPort }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>境内目的地</th>
				<td>
					<input name="domesticDestination" value="${customsDeclaration.domesticDestination }" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>许可证号</th>
				<td>
					<input name="license" value="${customsDeclaration.license }" class="easyui-validatebox"/>
				</td>
				<th>成交方式</th>
				<td>
					<mytag:combobox name="termsDelivery" value="${customsDeclaration.termsDelivery}" type="dict" parameters="TERMSDELIVERY"/>
				</td>
				<th>运费</th>
				<td>
					<input type="text" name="freightFee" value="${customsDeclaration.freightFee }" class="easyui-numberbox" data-options="precision:2"/>
				</td>
				<th>保费</th>
				<td>
					<input type="text" name="premium" value="${customsDeclaration.premium }" class="easyui-numberbox" data-options="precision:2"/>
				</td>
			</tr>
			<tr>
				<th>杂费</th>
				<td>
					<input type="text" name="pettyFee" value="${customsDeclaration.pettyFee }" class="easyui-numberbox" data-options="precision:2"/>
				</td>
				<th>合同协议号</th>
				<td>
					<input name="contractNo" value="${customsDeclaration.contractNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>件数</th>
				<td>
					<input type="text" name="number" value="${customsDeclaration.number }" class="easyui-numberbox" data-options="required:true,precision:2"/>
				</td>
				<th>包装种类</th>
				<td>
					<mytag:combobox name="packageType" value="${customsDeclaration.packageType}" type="dict" parameters="PACKAGETYPE"/>
				</td>
			</tr>
			<tr>
				<th>毛重</th>
				<td>
					<input name="grossWeight" value="${customsDeclaration.grossWeight }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>净重</th>
				<td>
					<input name="suttle" value="${customsDeclaration.suttle }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th>集装箱号</th>
				<td>
					<input name="containerNo" value="${customsDeclaration.containerNo }" class="easyui-validatebox"/>
				</td>
				<th>随附单证</th>
				<td>
					<input name="accompanyingDocument" value="${customsDeclaration.accompanyingDocument }" class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>代理公司</th>
				<td colspan="3">
					<mytag:combobox name="agencyCompany" value="${customsDeclaration.agencyCompany}" type="customer" parameters="10230006" width="300"/>
				</td>
				<th>海关放行日期</th>
				<td>
					<input name="customsRelease" value="<fmt:formatDate value='${customsDeclaration.customsReleaseDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
				<th>商检放行日期</th>
				<td>
					<input name="inspectionRelease" value="<fmt:formatDate value='${customsDeclaration.inspectionReleaseDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
		<legend>系统信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th width="20%">登记人</th>
				<td width="30%">
					<input type="hidden" name="createrNo" value="${customsDeclaration.createrNo }"/>
					<input type="hidden" name="createrName" value="${customsDeclaration.createrName }"/>
					<input type="hidden" name="createrDept" value="${customsDeclaration.createrDept }"/>
					<input type="hidden" name="createDate" value="<fmt:formatDate value='${customsDeclaration.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
					<input type="hidden" name="updateDate" value="<fmt:formatDate value='${customsDeclaration.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
					${customsDeclaration.createrName }
				</td>
				<th>登记部门</th>
				<td>${customsDeclaration.createrDept }</td>
			</tr>
			<tr>	
				<th>登记时间</th>
				<td>
					<fmt:formatDate value="${customsDeclaration.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>最后修改时间</th>
				<td>
					<fmt:formatDate value="${customsDeclaration.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</table>
		</fieldset>
	</div>
	<div data-options="title: '商品信息', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/logistic/customsDeclarationGoodsList.jsp"%>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
		<input id="accParentEntity" type="hidden"  value="<%=CustomsDeclaration.class.getName().replace(".","_") %>" />
		<input id="accParentId" type="hidden" value="${customsDeclaration.id}" />
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
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
		},
		success : function(data) {
			successTipNew(data,dg);
			var data = eval('(' + data + ')');
			$.easyui.loaded();
		} 
	});
	
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
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
	
	//船名
	$('#shipNo').combogrid({
	    panelWidth:450,    
	    method: "get",
	    idField:'shipNo',    
	    textField:'noAndName',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
		required : true,
	    url:'${ctx}/logistic/shipTrace/outerShipTrace',
	    columns:[[
			{field:'shipNo',title:'船编号',width:20},
			{field:'shipName',title:'船中文名',width:20},
			{field:'shipNameE',title:'船英文名',width:20},
			{field:'loadPortView',title:'装港港口',width:20}
	 	]]
	});
});
		
</script>
</body>
</html>
