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
						<th  >评审编号</th>
						<td>
							${enterpriseStockCheck.checkId }
						</td>
						<th  >企业名称</th>
						<td>
							${enterpriseStockCheck.enterpriseName }
						</td>
						<th  >仓库地址</th>
						<td >
							${enterpriseStockCheck.warehouseAddress }
						</td>
					</tr>
					<tr>
						<th  >联系人</th>
						<td>
							${enterpriseStockCheck.contactPerson }
						</td>
						<th  >联系电话</th>
						<td >
							${enterpriseStockCheck.phoneContact }
						</td>
						<th  >传真</th>
						<td>
							${enterpriseStockCheck.fax }
						</td>
					</tr>
					<tr>
						<th  >申请部门</th>
						<td>
							${enterpriseStockCheck.applyDepart }
						</td>
						<th  >申请人</th>
						<td >
							${enterpriseStockCheck.applyPerson }
						</td>
						<th  >申请时间</th>
						<td >
							<fmt:formatDate value="${enterpriseStockCheck.applyDate }" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>企业实力</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th  >企业性质</th>
						<td>
							${enterpriseStockCheck.enterpriseProperty }
						</td>
						<th  >资金币种</th>
						<td >
							${enterpriseStockCheck.capitalCurrency }
						</td>
						<th  >注册资金</th>
						<td >
							${enterpriseStockCheck.registeredCapital }万元
						</td>
					</tr>
					<tr>
						<th  >是否经营仓储品</th>
						<td>
							${enterpriseStockCheck.isSealGoods }
						</td>
						<th  >经营规模</th>
						<td >
							${enterpriseStockCheck.sealScale }
						</td>
						<th  >可提供的担保</th>
						<td >
							${enterpriseStockCheck.guarantee }
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">	
					<tr>	
						<th>营业年限</th>
						<th  >成立日期</th>
						<td>
							<fmt:formatDate value="${enterpriseStockCheck.businessLifeFound }" />
						</td>
						<th  >营业期限</th>
						<td>
							${enterpriseStockCheck.businessLifeDealline }年
						</td>
						<th  >已营业年限</th>
						<td>
							${enterpriseStockCheck.businessLife }年
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">		
				<%-- 	<tr>
						<th  >仓库所有权类型</th>
						<td >
							<input id="warehouseOw" name="warehouseOw" type="text" value="${enterpriseStockCheck.warehouseOw }" class="easyui-validatebox"/>
							租赁年限<input id="leasePeriod" name="leasePeriod" type="text" value="${enterpriseStockCheck.leasePeriod }" class="easyui-validatebox"/>
							已使用年限<input id="passPeriod" name="passPeriod" type="text" value="${enterpriseStockCheck.passPeriod }" class="easyui-validatebox"/>
							仓库建筑时间<input id="buildTime" name="buildTime" type="text" value="${enterpriseStockCheck.buildTime }" class="easyui-validatebox"/>
						</td>
					</tr> --%>
					
				</table>	
				<table width="98%" class="tableClass">		
					<tr>
						<th >仓库基本情况</th>
						<th  >总资产</th>
						<td  >
							${enterpriseStockCheck.warehouseTotal }元
						</td>
						<th  >面积</th>
						<td  >
							${enterpriseStockCheck.warehouseArea }亩
						</td>
						<th  >主要仓储品种</th>
						<td  >
							${enterpriseStockCheck.warehouseInstockCategory }
						</td>
						<th  >其他</th>
						<td  >
							${enterpriseStockCheck.warehouseBaseInfo }
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
						<td width="26%" id="majorEquipmentSoftware">
						</td>
						<th width="5%"  >硬件</th>
						<td>
						${enterpriseStockCheck.majorEquipmentHardware }
						<th width="5%"  >其他</th>
						<td>
							${enterpriseStockCheck.majorEquipment }
						</td>
					</tr>
					<tr>	
						<th width="10%" rowspan="2" >可用资源</th>
						<th width="5%"  >码头</th>
						<td width="26%">
							${enterpriseStockCheck.port }
						</td>
						<th width="5%"  >铁路</th>
						<td >
							${enterpriseStockCheck.railway }
						</td>
						<th width="6%"  >室内厂房</th>
						<td >
						${enterpriseStockCheck.indoorPlants }
						</td>
					</tr>
					<tr>	
						<th  >其他</th>
						<td colspan="5">
							${enterpriseStockCheck.warehouseResources }
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">	
					<tr>
						<th  >雇佣人数</th>
						<td id="totalWorker" >
							<input id="totalWorker"  readonly="readonly" />人
						</td>
						<th  >雇佣详情</th>
						<td >
							仓管员${enterpriseStockCheck.warehouseWorkerNumber }人
						</td>
						<td >
							装卸工${enterpriseStockCheck.dockerNumber }人
						</td>
						<td >
							管理员${enterpriseStockCheck.adminNumber }人
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>经营现状</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="15%" >仓库流动性</th>
						<th width="15%"  >上年度周转货物总量</th>
						<td width="27%" >
							${enterpriseStockCheck.lastYearQuantity }吨
						</td>
						<th width="15%" >近三个月出库平均量</th>
						<td >
							${enterpriseStockCheck.recent3MouthPerDayQuantity }吨/天
						</td>
					</tr>
					<tr>
						<th >库存现状</th>
						<th >现有库存</th>
						<td>
							${enterpriseStockCheck.existingStocks }吨
						</td>
						<th  >最大库存</th>
						<td >
							${enterpriseStockCheck.maxStocks }吨
						</td>
					</tr>
					<tr>
						<th >仓库经营稳定性</th>
						<th >是否领导层变动</th>
						<td >
							${enterpriseStockCheck.isLeaderChange }
						</td>
						<th   >近两年经营情况</th>
						<td>
							${enterpriseStockCheck.businessConditions }
						</td>
					</tr>
				</table>	
				<table width="98%" class="tableClass">	
					<tr>
						<th  >仓储企业主要合作客户</th>
						<td>
							${enterpriseStockCheck.mainPartner }
						</td>
						<th  >盈利点</th>
						<td >
							${enterpriseStockCheck.profitPoint }
						</td>
						<th  >土地抵押情况</th>
						<td>
							${enterpriseStockCheck.islandMortgage }
						</td>
					</tr>
					<tr>
						<th  >是否有质押仓单业务</th>
						<td>
							${enterpriseStockCheck.isMortgageBusiness }
						</td>
						<th  >合作银行</th>
						<td >
							${enterpriseStockCheck.cooperationBank }
						</td>
						<th  >土地抵押资金用途</th>
						<td >
							${enterpriseStockCheck.landMortgageUse }
						</td>
					</tr>
					<tr>
						<th  >银行其他贷款</th>
						<td colspan="5">
							${enterpriseStockCheck.mortgageBank }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>效率信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="9%" rowspan="2" >工作时间</th>
						<th  width="9%">上班时间</th>
						<td  width="21%">
							${enterpriseStockCheck.starWorkTime }
						</td>
						<th  width="9%">下班时间</th>
						<td >
							${enterpriseStockCheck.endWorkTime }
						</td>
						<th width="15%" >周六日是否可以提货</th>
						<td >
							${enterpriseStockCheck.isWeekendDelivery }
						</td>
					</tr>
					<tr>
						<th>最晚提货时间</th>
						<td>
						${enterpriseStockCheck.latestDeliveryTime }
						</td>
					</tr>
					<tr>
						<th width="9%">收费情况</th>
						<th  width="9%">仓储费用</th>
						<td>
							${enterpriseStockCheck.warehouseFee }元/吨/天
						</td>
						<th width="9%" >出库费用</th>
						<td >
							${enterpriseStockCheck.outStockFee }元/吨
						</td>
						<th  width="15%">其他费用</th>
						<td>
							${enterpriseStockCheck.extraFee }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>客户情况</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >仓库是否客户推荐</th>
						<td>
							${enterpriseStockCheck.isRecommend }
						</td>
						<th  >准备合作客户</th>
						<td >
							${enterpriseStockCheck.prepareCooperationCustomer }
						</td>
						<th  >业务描述</th>
						<td >
							${enterpriseStockCheck.businessDescription }
						</td>
					</tr>
					<tr>
						<th  >客户与仓储企业的关系</th>
						<td>
							${enterpriseStockCheck.customer2Enterprise }
						</td>
						<th  >客户是含有否库存</th>
						<td >
							${enterpriseStockCheck.isHaveInstock }
						</td>
						<th  >客户在库现有库存</th>
						<td id="haveInstock">
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>手续效力</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >仓储企业已提供的资料</th>
						<td id="enterpriseWarehouseData">
						</td>
						<th  >合同是否已签订</th>
						<td>
							${enterpriseStockCheck.isContract }
						</td>
						<th  >哪方面版本</th>
						<td >
							${enterpriseStockCheck.contractCategory }
						</td>
					</tr>
					<tr>
						<th  >是否当面办理</th>
						<td>
							${enterpriseStockCheck.isFaceToFace }
						</td>
						<th  >现场考察及班里人意见</th>
						<td colspan="3" >
							${enterpriseStockCheck.sitePersonnel }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${sessionScope.user.name}</td>
						<th  >登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				</fieldset>
			</div>	
			<div data-options="title: '附件', iconCls: false, refreshable: false">
			<input id="accParentId" type="hidden"  value="<%=EnterpriseStockCheck.class.getName().replace(".","_") %>" />
			<div id="dgAcc"  class="tableClass"></div>
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
		    		successTip(data,dg,d);
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


		var haveInstock = ${enterpriseStockCheck.haveInstock };
		if(haveInstock!=""){ 
			$('#haveInstock').html(haveInstock+"吨")
		}else{
			$('#haveInstock').html("&nbsp;");
		}
	
		$(function () {
			$.ajax({
				url : '${ctx}/system/dict/getAllName/' + '${enterpriseStockCheck.majorEquipmentSoftware }'+"/"+"SBSOFT",
				type : 'get',
				cache : false,
				success : function(data) {
					$('#majorEquipmentSoftware').text(data);
				}
			});
		});
		
		$(function () {
			$.ajax({
				url : '${ctx}/system/dict/getAllName/' + '${enterpriseStockCheck.enterpriseWarehouseData }'+"/"+"enterpriseWarehouseData",
				type : 'get',
				cache : false,
				success : function(data) {
					$('#enterpriseWarehouseData').text(data);
				}
			});
		});
		
		//总人数
		var warehouseWorkerNumber = ${enterpriseStockCheck.warehouseWorkerNumber };
		var dockerNumber = ${enterpriseStockCheck.dockerNumber };
		var adminNumber = ${enterpriseStockCheck.adminNumber };
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
		$('#totalWorker').html(totalWorker);
	
		
	});
	
	var urlValue = "${ctx}/accessory/json?filter_EQS_accParentId="
		+ $('#accParentId').attr('value');
	var dgAcc;
	$(function() {
	dgAcc = $('#dgAcc')
			.datagrid(
					{
						method : "get",
						fit : false,
						fitColumns : true,
						border : false,
						striped : true,
						singleSelect : true,
						scrollbarSize : 0,
						url : urlValue,
						idField : 'accId',
						columns : [ [
								{
									field : 'accRealName',
									title : '附件名称',
									sortable : true,
									width : 40
								},
								{
									field : 'accAuthor',
									title : '上传人',
									sortable : true,
									width : 10
								},
								{
									field : 'accId',
									title : '操作',
									sortable : true,
									width : 20,
									formatter : function(value, row, index) {
										var str = "";
										str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
												+ value + ");'>下载</a>"
										return str;
									}
								} ] ]
					})
	});

	
	
</script>
</body>
</html>