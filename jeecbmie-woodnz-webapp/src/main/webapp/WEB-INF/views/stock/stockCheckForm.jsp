<%@page import="com.cbmie.genMac.stock.entity.EnterpriseStockCheck"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/stock/enterpriseStockCheck/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '仓储企业考擦信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>评审编号</th>
						<td>
							<input id="checkId" name="checkId" type="text" value="${enterpriseStockCheck.checkId }" class="easyui-validatebox"  data-options="required:true"/>
							<input name="id" type="hidden"  value="${enterpriseStockCheck.id }" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>企业名称</th>
						<td>
							<input id="enterpriseName" name="enterpriseName" type="text" value="${enterpriseStockCheck.enterpriseName }" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>仓库地址</th>
						<td >
							<input id="warehouseAddress" name="warehouseAddress" type="text" value="${enterpriseStockCheck.warehouseAddress }" class="easyui-validatebox"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>联系人</th>
						<td>
							<input id="contactPerson" name="contactPerson" type="text" value="${enterpriseStockCheck.contactPerson }" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>联系电话</th>
						<td >
							<input id="phoneContact" name="phoneContact" type="text" value="${enterpriseStockCheck.phoneContact }" class="easyui-validatebox"  validtype="telOrMobile" data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>传真</th>
						<td>
							<input id="fax" name="fax" type="text" value="${enterpriseStockCheck.fax }" class="easyui-validatebox"  validtype="fax" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>申请部门</th>
						<td>
							<input id="applyDepart" name="applyDepart" type="text" value="${enterpriseStockCheck.applyDepart }" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>申请人</th>
						<td >
							<input id="applyPerson" name="applyPerson" type="text" value="${enterpriseStockCheck.applyPerson }" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>申请时间</th>
						<td >
							<input id="applyDate" name="applyDate"   type="text" value="<fmt:formatDate value="${enterpriseStockCheck.applyDate }" />" datefmt="yyyy-MM-dd" 
							class="easyui-my97" data-options="required:true"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>企业实力</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>企业性质</th>
						<td>
							<input id="enterpriseProperty" name="enterpriseProperty" type="text" value="${enterpriseStockCheck.enterpriseProperty }" class="easyui-validatebox" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>资金币种</th>
						<td >
							<input id="capitalCurrency" name="capitalCurrency" type="text" value="${enterpriseStockCheck.capitalCurrency }" class="easyui-validatebox" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>注册资金</th>
						<td >
							<input id="registeredCapital" name="registeredCapital" type="text" value="${enterpriseStockCheck.registeredCapital }"    class="easyui-numberbox" data-options="required:true"/>万元
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>是否经营仓储品</th>
						<td>
							<input id="isSealGoods" name="isSealGoods" type="text" value="${enterpriseStockCheck.isSealGoods }" class="easyui-validatebox" />
						</td>
						<th  >经营规模</th>
						<td >
							<input id="sealScale" name="sealScale" type="text" value="${enterpriseStockCheck.sealScale }" class="easyui-validatebox"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>可提供的担保</th>
						<td >
							<input id="guarantee" name="guarantee" type="text" value="${enterpriseStockCheck.guarantee }" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">	
					<tr>	
						<th>营业年限</th>
						<th  ><font style="vertical-align: middle; color: red">*</font>成立日期</th>
						<td>
							<input id="businessLifeFound" name="businessLifeFound"   type="text" value="<fmt:formatDate value="${enterpriseStockCheck.businessLifeFound }" />" datefmt="yyyy-MM-dd" 
							class="easyui-my97" data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>营业期限</th>
						<td>
							<input id="businessLifeDealline" name="businessLifeDealline" type="text" value="${enterpriseStockCheck.businessLifeDealline }" class="easyui-numberbox" data-options="required:true"/>年
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>已营业年限</th>
						<td>
							<input id="businessLife" name="businessLife" type="text" value="${enterpriseStockCheck.businessLife }" class="easyui-numberbox" data-options="required:true"/>年
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">		
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>仓库所有权类型</th>
						<td >
							<input id="warehouseOw" name="warehouseOw" type="text" value="${enterpriseStockCheck.warehouseOw }" class="easyui-validatebox"/>
						</td>
						<td id="aaa" >
						租赁年限<input id="leasePeriod" name="leasePeriod" type="text"  value="${enterpriseStockCheck.leasePeriod }" class="easyui-validatebox"/>
						&nbsp;&nbsp;&nbsp;
						已使用年限<input id="passPeriod" name="passPeriod" type="text"   value="${enterpriseStockCheck.passPeriod }" class="easyui-validatebox"/>
						</td>
						<td id="bbb" >
						仓库建筑时间<input id="buildTime" name="buildTime"   type="text"  value="<fmt:formatDate value="${enterpriseStockCheck.buildTime }" />" datefmt="yyyy-MM-dd" 
						class="easyui-my97" />
						</td>
					</tr>
					
				</table>	
				<table width="98%" class="tableClass">		
					<tr>
						<th >仓库基本情况</th>
						<th  >总资产</th>
						<td  >
							<input id="warehouseTotal" name="warehouseTotal" type="text" value="${enterpriseStockCheck.warehouseTotal }"    class="easyui-numberbox" data-options="required:true"/>元
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>面积</th>
						<td  >
							<input id="warehouseArea" name="warehouseArea" type="text" value="${enterpriseStockCheck.warehouseArea }"  class="easyui-numberbox" data-options="required:true"/>亩
						</td>
						<th  >主要仓储品种</th>
						<td  >
							<input id="warehouseInstockCategory" name="warehouseInstockCategory" type="text" value="${enterpriseStockCheck.warehouseInstockCategory }" class="easyui-validatebox" />
						</td>
						<th  >其他</th>
						<td  >
							<input id="warehouseBaseInfo" name="warehouseBaseInfo" type="text" value="${enterpriseStockCheck.warehouseBaseInfo }" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>专业能力</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="10%"  >设施设备</th>
						<th width="5%"  >软件</th>
						<td width="26%">
							<input id="majorEquipmentSoftware" name="majorEquipmentSoftware" type="text" value="${enterpriseStockCheck.majorEquipmentSoftware }" class="easyui-validatebox" />
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelect1()"></a>
						</td>
						<th width="5%"  >硬件</th>
						<td>
							<input id="majorEquipmentHardware" name="majorEquipmentHardware" type="text" value="${enterpriseStockCheck.majorEquipmentHardware }" class="easyui-validatebox" />
						</td>
						<th width="5%"  >其他</th>
						<td>
							<input id="majorEquipment" name="majorEquipment" type="text" value="${enterpriseStockCheck.majorEquipment }" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>	
						<th width="10%" rowspan="2" >可用资源</th>
						<th width="5%"  >码头</th>
						<td width="26%">
							<input id="port" name="port" type="text" value="${enterpriseStockCheck.port }" class="easyui-validatebox"/>
						</td>
						<th width="5%"  >铁路</th>
						<td >
							<input id="railway" name="railway" type="text" value="${enterpriseStockCheck.railway }" class="easyui-validatebox"/>
						</td>
						<th width="6%"  >室内厂房</th>
						<td >
							<input id="indoorPlants" name="indoorPlants" type="text" value="${enterpriseStockCheck.indoorPlants }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>	
						<th  >其他</th>
						<td colspan="5">
							<input id="warehouseResources" name="warehouseResources" width="90%" type="text" value="${enterpriseStockCheck.warehouseResources }" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">	
					<tr>
						<th  >雇佣人数</th>
						<td>
							<input id="totalWorker"  readonly="readonly" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>雇佣详情</th>
						<td >
							仓管员<input id="warehouseWorkerNumber" name="warehouseWorkerNumber" type="text" value="${enterpriseStockCheck.warehouseWorkerNumber }"  onchange="calculate()" validtype="integer" data-options="required:true" class="easyui-numberbox"/>人
						</td>
						<td >
							装卸工<input id="dockerNumber" name="dockerNumber" type="text" value="${enterpriseStockCheck.dockerNumber }" onchange="calculate()" validtype="integer"  data-options="required:true" class="easyui-numberbox"/>人
						</td>
						<td >
							管理员<input id="adminNumber" name="adminNumber" type="text" value="${enterpriseStockCheck.adminNumber }" onchange="calculate()" validtype="integer" data-options="required:true" class="easyui-numberbox"/>人
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>经营现状</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="15%" >仓库流动性</th>
						<th width="15%"  ><font style="vertical-align: middle; color: red">*</font>上年度周转货物总量</th>
						<td width="27%" >
							<input id="lastYearQuantity" name="lastYearQuantity" type="text" value="${enterpriseStockCheck.lastYearQuantity }" class="easyui-numberbox" data-options="required:true"/>吨
						</td>
						<th width="15%" >近三个月出库平均量</th>
						<td >
							<input id="recent3MouthPerDayQuantity" name="recent3MouthPerDayQuantity" type="text" value="${enterpriseStockCheck.recent3MouthPerDayQuantity }" class="easyui-numberbox" data-options="required:true"/>吨/天
						</td>
					</tr>
					<tr>
						<th >库存现状</th>
						<th ><font style="vertical-align: middle; color: red">*</font>现有库存</th>
						<td>
							<input id="existingStocks" name="existingStocks" type="text" value="${enterpriseStockCheck.existingStocks }" class="easyui-numberbox" data-options="required:true"/>吨
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>最大库存</th>
						<td >
							<input id="maxStocks" name="maxStocks" type="text" value="${enterpriseStockCheck.maxStocks }" class="easyui-numberbox" data-options="required:true"/>吨
						</td>
					</tr>
					<tr>
						<th >仓库经营稳定性</th>
						<th ><font style="vertical-align: middle; color: red">*</font>是否领导层变动</th>
						<td >
							<input id="isLeaderChange" name="isLeaderChange" type="text" value="${enterpriseStockCheck.isLeaderChange }" class="easyui-validatebox"/>
						</td>
						<th   ><font style="vertical-align: middle; color: red">*</font>近两年经营情况</th>
						<td>
							<input id="businessConditions" name="businessConditions" type="text" value="${enterpriseStockCheck.businessConditions }" class="easyui-validatebox" />
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">	
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>仓储企业主要合作客户</th>
						<td>
							<input id="mainPartner" name="mainPartner" type="text" value="${enterpriseStockCheck.mainPartner }" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>盈利点</th>
						<td >
							<input id="profitPoint" name="profitPoint" type="text" value="${enterpriseStockCheck.profitPoint }" class="easyui-validatebox"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>土地抵押情况</th>
						<td>
							<input id="islandMortgage" name="islandMortgage" type="text" value="${enterpriseStockCheck.islandMortgage }" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>是否有质押仓单业务</th>
						<td>
							<input id="isMortgageBusiness" name="isMortgageBusiness" type="text" value="${enterpriseStockCheck.isMortgageBusiness }" class="easyui-validatebox" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>合作银行</th>
						<td >
							<input id="cooperationBank" name="cooperationBank" type="text" value="${enterpriseStockCheck.cooperationBank }" class="easyui-validatebox"/>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>土地抵押资金用途</th>
						<td >
							<input id="landMortgageUse" name="landMortgageUse" type="text" value="${enterpriseStockCheck.landMortgageUse }" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>银行其他贷款</th>
						<td colspan="5">
							<input id="mortgageBank" name="mortgageBank" type="text" value="${enterpriseStockCheck.mortgageBank }" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>效率信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="9%" rowspan="2" >工作时间</th>
						<th  width="9%"><font style="vertical-align: middle; color: red">*</font>上班时间</th>
						<td  width="21%">
							<input id="starWorkTime"  name="starWorkTime" type="text" class="easyui-timespinner"  value="${enterpriseStockCheck.starWorkTime }"  data-options="min:'08:30',showSeconds:true,required:true">
						</td>
						<th  width="9%"><font style="vertical-align: middle; color: red">*</font>下班时间</th>
						<td >
							<input id="endWorkTime" name="endWorkTime" type="text" class="easyui-timespinner" value="${enterpriseStockCheck.endWorkTime }" data-options="min:'18:30',showSeconds:true,required:true"/>
						</td>
						<th width="15%" ><font style="vertical-align: middle; color: red">*</font>周六日是否可以提货</th>
						<td >
							<input id="isWeekendDelivery" name="isWeekendDelivery" type="text" value="${enterpriseStockCheck.isWeekendDelivery }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>最晚提货时间</th>
						<td>
							<input id="latestDeliveryTime" name="latestDeliveryTime" type="text" class="easyui-timespinner" value="${enterpriseStockCheck.latestDeliveryTime }" data-options="min:'18:30',showSeconds:true,required:true"/>
						</td>
					</tr>
					<tr>
						<th width="9%">收费情况</th>
						<th  width="9%"><font style="vertical-align: middle; color: red">*</font>仓储费用</th>
						<td>
							<input id="warehouseFee" name="warehouseFee" type="text" value="${enterpriseStockCheck.warehouseFee }"   class="easyui-numberbox" data-options="required:true,precision:'2'" />元/吨/天
						</td>
						<th width="9%" ><font style="vertical-align: middle; color: red">*</font>出库费用</th>
						<td >
							<input id="outStockFee" name="outStockFee" type="text" value="${enterpriseStockCheck.outStockFee }"    class="easyui-numberbox" data-options="required:true,precision:'2'"/>元/吨
						</td>
						<th  width="15%">其他费用</th>
						<td>
							<input id="extraFee" name="extraFee" type="text" value="${enterpriseStockCheck.extraFee }"    class="easyui-numberbox" data-options="precision:'2'"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>客户情况</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>仓库是否客户推荐</th>
						<td>
							<input id="isRecommend" name="isRecommend" type="text" value="${enterpriseStockCheck.isRecommend }" class="easyui-validatebox" />
						</td>
						<th  >准备合作客户</th>
						<td >
							<input id="prepareCooperationCustomer" name="prepareCooperationCustomer" type="text" value="${enterpriseStockCheck.prepareCooperationCustomer }" class="easyui-validatebox"/>
						</td>
						<th  >业务描述</th>
						<td >
							<input id="businessDescription" name="businessDescription" type="text" value="${enterpriseStockCheck.businessDescription }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>客户与仓储企业的关系</th>
						<td>
							<input id="customer2Enterprise" name="customer2Enterprise" type="text" value="${enterpriseStockCheck.customer2Enterprise }" class="easyui-validatebox" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>客户是含有否库存</th>
						<td >
							<input id="isHaveInstock" name="isHaveInstock" type="text" value="${enterpriseStockCheck.isHaveInstock }" class="easyui-validatebox"/>
						</td>
						<th  >客户在库现有库存</th>
						<td>
							<input id="haveInstock" name="haveInstock" type="text" value="${enterpriseStockCheck.haveInstock }" class="easyui-validatebox" />吨
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>手续效力</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>仓储企业已提供的资料</th>
						<td >
							<input id="enterpriseWarehouseData" name="enterpriseWarehouseData" type="text" value="${enterpriseStockCheck.enterpriseWarehouseData }" class="easyui-validatebox"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelect2()"></a>
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>合同是否已签订</th>
						<td>
							<input id="isContract" name="isContract" type="text" value="${enterpriseStockCheck.isContract }" class="easyui-validatebox" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>哪方面版本</th>
						<td >
							<input id="contractCategory" name="contractCategory" type="text" value="${enterpriseStockCheck.contractCategory }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th  ><font style="vertical-align: middle; color: red">*</font>是否当面办理</th>
						<td>
							<input id="isFaceToFace" name="isFaceToFace" type="text" value="${enterpriseStockCheck.isFaceToFace }" class="easyui-validatebox" />
						</td>
						<th  ><font style="vertical-align: middle; color: red">*</font>现场考察及班里人意见</th>
						<td colspan="3" >
							<input id="sitePersonnel" name="sitePersonnel" type="text" value="${enterpriseStockCheck.sitePersonnel }" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty enterpriseStockCheck.createrName ? sessionScope.user.name : enterpriseStockCheck.createrName }</td>
						<th  >登记部门</th>
						<td>${empty enterpriseStockCheck.createrDept ? sessionScope.user.organization.orgName : enterpriseStockCheck.createrDept }</td>
					</tr>
				</table>
				</fieldset>
			</div>	
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentId" type="hidden"  value="<%=EnterpriseStockCheck.class.getName().replace(".","_") %>" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">

	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				if("update"=="${action}")
		    		successTip(data,dg);
		    	else
		    		successTip(data,dg);
			} 
		});
		
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

		
		//企业性质
		$('#enterpriseProperty').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/QYXZ',
			valueField : 'name',
			textField : 'name'
		});

		//资金币种
		$('#capitalCurrency').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/CURRENCY',
			valueField : 'name',
			textField : 'name'
		});
		
		
		//是否经营仓储品
		$('#isSealGoods').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name',
			onSelect:function(record){
				if(record.name=="是"){
					$("#sealScale").removeAttr("disabled",true);
				}else{
					$("#sealScale").attr("disabled",true);
					$("#sealScale").val("");
				}
			}
		});
		//可提供的担保
		$('#guarantee').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/DB',
			valueField : 'name',
			textField : 'name'
		});
		
		//仓库所有权类型
		$('#warehouseOw').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/warehouseOw',
			valueField : 'name',
			textField : 'name',
			onSelect:function(record){
				if(record.name=="承保/租赁经营"||record.name=="仓库自有，土地租赁"){
					$("#aaa").show();
					$("#leasePeriod").removeAttr("disabled",true);
					$("#passPeriod").removeAttr("disabled",true);
					$("#bbb").hide();
					$("#buildTime").attr("disabled",true);
				}else if(record.name=="仓库自有，土地自有"){
					 $("#aaa").hide();
					 $("#leasePeriod").attr("disabled",true);
					 $("#passPeriod").attr("disabled",true);
					 $("#bbb").show();
					 $("#buildTime").combo({disabled:false});
				}
			},
			onLoadSuccess: function () { 
				if($('#warehouseOw').val()=="承保/租赁经营"||$('#warehouseOw').val()=="仓库自有，土地租赁"){
					$("#aaa").show();
					$("#bbb").hide();
				}else if($('#warehouseOw').val()=="仓库自有，土地自有"){
					$("#aaa").hide();
					 $("#bbb").show();
				}else if($('#warehouseOw').val()==""){
					$("#leasePeriod").attr("disabled",true);
	 				$("#passPeriod").attr("disabled",true);
	 				$("#buildTime").combo({disabled:true});
				}
			}
		});
		
		//设施设备-软件
		$('#majorEquipmentSoftware').combobox({
			method:'GET',
			url : '${ctx}/system/dict/getDictByCode/SBSOFT',
		    textField : 'name',
		    editable:false,
		    required : true,
		    multiple:true,
		    width:250,
		    multiline:true,
			panelHeight:'auto',
		    prompt: '此选项可以多选，点击X重置选择',
		    onLoadSuccess: function () { //加载完成后,设置选中第一项 
		        var val = $(this).combobox("getData"); 
		        $(this).combobox("clear");
		   		var curValue = new Array();
		   		curValue = this.value.split(',');
		   		for(var j=0;j<curValue.length;j++){
		   		 	for(var i = 0;i<val.length;i++ ){ 
			            if (val[i].value==curValue[j]) { 
			                $(this).combobox("select",curValue[j]); 
			            } 
			        } 
		   		}
		        
		    } ,
		    onHidePanel:function(){
		    }
		});
		
		//码头port 
		$('#port').combobox({
			panelHeight : 'auto',
			url : '${ctx}/baseinfo/port/getPortList',
			valueField : 'portName',
			textField : 'portName'
		});
		
		//室内厂房indoorPlants
		$('#indoorPlants').combobox({
			panelHeight : 'auto',
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		
		//总人数
		var warehouseWorkerNumber = $('#warehouseWorkerNumber').val();
		var dockerNumber = $('#dockerNumber').val();
		var adminNumber = $('#adminNumber').val();
		var totalWorker = 0;
		if(warehouseWorkerNumber!=""){
			totalWorker+=parseInt(warehouseWorkerNumber);
		}
		if(dockerNumber!=""){
			totalWorker+=parseInt(dockerNumber);
		}
		if(adminNumber!=""){
			totalWorker+=parseInt(adminNumber);
		}
		$('#totalWorker').val(totalWorker);
		
		// 是否领导层变动 
		$('#isLeaderChange').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		//近两年经营状况
		$('#businessConditions').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/JYZK',
			valueField : 'name',
			textField : 'name'
		});
		//盈利点
		$('#profitPoint').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YLD',
			valueField : 'name',
			textField : 'name'
		});
		//是否有质押仓单业务
		$('#isMortgageBusiness').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name',
			onSelect:function(record){
				if(record.name=="是"){
					$("#cooperationBank").removeAttr("disabled",true);
				}else{
					$("#cooperationBank").attr("disabled",true);
					$("#cooperationBank").val("");
				}
			}
		});
		//周六日是否可以提货
		$('#isWeekendDelivery').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		//仓库是否客户推荐 
		$('#isRecommend').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		//客户与仓储企业的关系 
		$('#customer2Enterprise').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/customer2Enterprise',
			valueField : 'name',
			textField : 'name'
		});
		//客户是含有否库存
		$('#isHaveInstock').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name',
			onSelect:function(record){
				if(record.name=="是"){
					$("#haveInstock").removeAttr("disabled",true);
				}else{
					$("#haveInstock").attr("disabled",true);
					$("#haveInstock").val("");
				}
			}
		});
		//仓储企业已提供的资料 
		$('#enterpriseWarehouseData').combobox({
			method:'GET',
			url : '${ctx}/system/dict/getDictByCode/enterpriseWarehouseData',
		    textField : 'name',
		    editable:false,
		    multiple:true,
		    width:250,
		    multiline:true,
		    required : true,
			panelHeight:'auto',
		    prompt: '此选项可以多选，点击X重置选择',
		    onLoadSuccess: function () { //加载完成后,设置选中第一项 
		        var val = $(this).combobox("getData"); 
		        $(this).combobox("clear");
		   		var curValue = new Array();
		   		curValue = this.value.split(',');
		   		for(var j=0;j<curValue.length;j++){
		   		 	for(var i = 0;i<val.length;i++ ){ 
			            if (val[i].value==curValue[j]) { 
			                $(this).combobox("select",curValue[j]); 
			            } 
			        } 
		   		}
		        
		    } ,
		    onHidePanel:function(){
		    }
		});
		
		//合同是否已签订
		$('#isContract').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		
		//哪方面版本
		$('#contractCategory').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/contractCategory',
			valueField : 'name',
			textField : 'name'
		});
		
		//是否当面办理
		$('#isFaceToFace').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		
	});
	//清除选择
	function clearSelect1() {
		$('#majorEquipmentSoftware').combobox("clear");
	}
	function clearSelect2() {
		$('#enterpriseWarehouseData').combobox("clear");
	}
	
	function calculate(){
		var warehouseWorkerNumber = $('#warehouseWorkerNumber').val();
		var dockerNumber = $('#dockerNumber').val();
		var adminNumber = $('#adminNumber').val();
		$('#totalWorker').val(parseInt(warehouseWorkerNumber)+parseInt(dockerNumber)+parseInt(adminNumber));
		var totalWorker = 0;
		if(warehouseWorkerNumber!=""){
			totalWorker+=parseInt(warehouseWorkerNumber);
		}
		if(dockerNumber!=""){
			totalWorker+=parseInt(dockerNumber);
		}
		if(adminNumber!=""){
			totalWorker+=parseInt(adminNumber);
		}
		$('#totalWorker').val(totalWorker);
	}
</script>
</body>
</html>