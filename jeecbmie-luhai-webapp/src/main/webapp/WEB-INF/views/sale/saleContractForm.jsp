<%@ page import="com.cbmie.lh.sale.entity.SaleContract"%>
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
	<form id="mainform" action="${ctx}/sale/saleContract/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${saleContract.id }" />
		
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '销售合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>销售合同号</th>
						<td>
							<input id="contractNo" name="contractNo" type="text" value="${saleContract.contractNo}" style="width:70%;" readonly="readonly" class="easyui-validatebox"  />
						</td>
						<th>客户合同号</th>
						<td>
							<input id="customerContractNo" name="customerContractNo" type="text" value="${saleContract.customerContractNo }" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>帐套单位</th>
						<td>
							<input id="setUnit" name="setUnit"   type="text" value="${saleContract.setUnit }"  class="easyui-validatebox"  />
						</td>
						<th>帐期</th>
						<td >
							<input id="accountStage" name="accountStage" type="text" value="${saleContract.accountStage }" class="easyui-numberbox" data-options="min:0,max:90,precision:0"/>
						</td>
					</tr>
					<tr>
						<th width="20%">卖方</th>
						<td width="30%">
							<input id="seller" name="seller" type="text" value="${saleContract.seller }" class="easyui-validatebox" readonly="readonly"/>
						</td>
						<th  width="20%">买方</th>
						<td>
							<mytag:combobox name="purchaser" value="${saleContract.purchaser}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/>
							信用评级：<input name="purchaserCredit" type="text" value="${saleContract.purchaserCredit }"  class="easyui-validatebox"  readonly="readonly"/>
						</td>
					</tr>
					<!-- 
					<tr>
						<th>印章类型</th>
						<td>
							<mytag:combobox name="signetCode" value="${saleContract.signetCode}" type="dict" parameters="SIGNETTYPE"/>
						</td>
						<th>印章管理员</th>
						<td>
						</td>
					</tr>
					 -->
					<tr>
						<th>销售类型</th>
						<td>
							<mytag:combobox name="saleMode" value="${saleContract.saleMode}" type="dict" parameters="tradeWay"/>
						</td>
						<th>经营方式</th>
						<td>
							<mytag:combobox name="manageMode" value="${saleContract.manageMode}" type="dict" parameters="runMode" onSelectFunctionName="onselectManageMode"/>
						</td>
					</tr>
					<tr>
						<th>业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${saleContract.businessManager}" type="userTree" />
						</td>
						<th>费用承担人</th>
						<td>
							<mytag:combotree name="feeUnderwriter" value="${saleContract.feeUnderwriter}" type="userTree"/>
						</td>
					</tr>
					<!-- 
					<tr>
						<th>签订地点</th>
						<td>
							<input id="signAddr" name="signAddr" type="text" value="${saleContract.signAddr }" class="easyui-validatebox"/>
						</td>
						<th>签订日期</th>
						<td>
							<input id="signDate" name="signDate" type="text" value="<fmt:formatDate value="${saleContract.signDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					-->
					<tr>
						<th>合同有效期</th>
						<td>
							<input id="startDate" name="startDate" type="text" value="<fmt:formatDate value='${saleContract.startDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/> 至
							<input id="endDate" name="endDate" type="text" value="<fmt:formatDate value='${saleContract.endDate }' />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th>交货起止期</th>
						<td>
							<input id="deliveryStartDate" name="deliveryStartDate" type="text" value="<fmt:formatDate value="${saleContract.deliveryStartDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> 至
							<input id="deliveryEndDate" name="deliveryEndDate" type="text" value="<fmt:formatDate value="${saleContract.deliveryEndDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>					
					</tr>
					</tr>
					 <tr>
						<th>数量溢短装</th>
						<td>
							<input id="numMoreOrLess" name="numMoreOrLess" type="text" value="${saleContract.numMoreOrLess }" class="easyui-numberbox"  data-options="precision:'2',suffix:'%'"/>
						</td>
						<th>签约日期</th>
						<td>
							<input id="signDate" name="signDate" type="text" value="<fmt:formatDate value="${saleContract.signDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>交货地点</th>
						<td>
							<input id="deliveryAddr" name="deliveryAddr"  type="text" value="${saleContract.deliveryAddr }" class="easyui-validatebox"  />
						</td>
						<th>结算方式</th>
						<td>
							<input id="settlementMode" name="settlementMode"  type="text" value="${saleContract.settlementMode }" class="easyui-combobox"  />
						</td>
					</tr>
					<tr id="deliveryModeTr">
						<th>交货方式</th>
						<td colspan="3">
							<mytag:combobox name="deliveryMode" value="${saleContract.deliveryMode}" type="dict" parameters="THFS" required="false"/>
						</td>
					</tr>
					<tr>
						<th>数量验收标准</th>
						<td>
							<mytag:combobox name="quantityAcceptance" value="${saleContract.quantityAcceptance}" type="dict" parameters="QUANTITY-ACCEPTANCE-CRITERIA"/>
						</td>
						<th>质量验收标准</th>
						<td>
							<mytag:combobox name="qualityAcceptance" value="${saleContract.qualityAcceptance}" type="dict" parameters="QUALITY-ACCEPTANCE-CRITERIA"/>
						</td>
					</tr>
					<tr>
						<th>第三方检测机构</th>
						<td>
							<input name="thirdPartyTest"  type="text" value="${saleContract.thirdPartyTest }" class="easyui-validatebox"/>
						</td>
						<th>检测费用</th>
						<td>
							<mytag:combobox name="testFee" value="${saleContract.testFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>港建费</th>
						<td>
							<mytag:combobox name="portFee" value="${saleContract.portFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
						<th>码头费</th>
						<td>
							<mytag:combobox name="dockFee" value="${saleContract.dockFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>运费</th>
						<td>
							<mytag:combobox name="freightFee" value="${saleContract.freightFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
						<th>堆存费</th>
						<td>
							<mytag:combobox name="storageFee" value="${saleContract.storageFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>滞期/速遣费</th>
						<td>
							<mytag:combobox name="dispatchFee" value="${saleContract.dispatchFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
						<th>其他费用</th>
						<td>
							<mytag:combobox name="otherFee" value="${saleContract.otherFee}" type="dict" parameters="FEE-UNDERTAKE"/>
						</td>
					</tr>
					<tr>
						<th>合同数量 </th>
						<td>
							<input id="contractQuantity" name="contractQuantity"  type="text" value="${saleContract.contractQuantity }" class="easyui-numberbox"  readonly="readonly"/>
						</td>
						<th>合同金额</th>
						<td>
							<input id="contractAmount" name="contractAmount" type="text" value="${saleContract.contractAmount }" class="easyui-numberbox" data-options="min:0,precision:2" readonly="readonly"/>
						</td>
					</tr>
				<tr id="transferClauseTr">
					<th>货权转移条款</th>
					<td colspan="3">
						<textarea name="transferClause" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]']">${saleContract.transferClause}</textarea>
					</td>
				</tr>
				<tr>
					<th>数量结算依据</th>
					<td colspan="3">
						<textarea name="numSettlementBasis" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]']">${saleContract.numSettlementBasis}</textarea>
					</td>
				</tr>
				<tr>
					<th>质量结算依据</th>
					<td colspan="3">
						<textarea name="qualitySettlementBasis" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]']">${saleContract.qualitySettlementBasis}</textarea>
					</td>
				</tr>
				<tr>
					<th>风险提示</th>
					<td colspan="3">
						<textarea name="riskTip" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]']">${saleContract.riskTip}</textarea>
					</td>
				</tr>
				<c:if test="${saleContract.pid ne null }">
					<tr>
						<th>变更原因</th>
						<td colspan="3">
							<textarea name="changeReason" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
								data-options="validType:['length[0,255]']">${saleContract.changeReason}</textarea>
						</td>
					</tr>
				</c:if>
				<tr>
					<th>结算条款</th>
					<td colspan="3">
						<textarea name="settlementClause" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]']">${saleContract.settlementClause}</textarea>
					</td>
				</tr>
				<tr>
					<th>付款条款</th>
					<td colspan="3">
						<textarea name="paymentClause" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" 
							data-options="validType:['length[0,255]']">${saleContract.paymentClause}</textarea>
					</td>
				</tr>
				</table>
			</fieldset>
				
			<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${saleContract.createrNo }"/>
							<input type="hidden" name="createrName" value="${saleContract.createrName }"/>
							<input type="hidden" name="createrDept" value="${saleContract.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${saleContract.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${saleContract.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${saleContract.createrName }
						</td>
						<th  >登记部门</th>
						<td>${saleContract.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${saleContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${saleContract.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
			</fieldset>
		</div>
			
			<div data-options="title: '货物明细', iconCls: false, refreshable: false">
				<div id="tbGoods" style="padding:5px;height:auto">
				     <div>	
						<a id="addGoods" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						<span class="toolbar-item dialog-tool-separator"></span>
				      	<a id="updateGoods" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				   		<span class="toolbar-item dialog-tool-separator"></span>
				   		<a id="deleteGoods" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
				     </div>
				</div>
				<table id="dgContractGoods" data-options="nowrap:false"></table>
				<script type="text/javascript">
				$(function(){
					dgContractGoods = $('#dgContractGoods').datagrid({
						method: "get",
						url: $('#id').val() ? '${ctx}/sale/saleContractGoods/forContractId/' + $('#id').val() : '',
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
							{field:'goodsNameView',title:'品名',sortable:true,width:15},
							{field:'price',title:'单价',sortable:true,width:10},
							{field:'goodsQuantity',title:'数量',sortable:true,width:10},
							{field:'unitsView',title:'数量单位',sortable:true,width:20},
							{field:'amount',title:'总价',sortable:true,width:10},
							{field:'shipNoView',title:'运输工具编号',width:15},
							{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false,
					    toolbar:'#tbGoods'
					});
					
					//弹窗增加采购信息
					$("#addGoods").on("click", function(){
						var idValue = $('#id').val();
						dlg_goods_dialog = $("#dlg_goods").dialog({
							method:'GET',
						    title: '新增采购信息',    
						    width: 700,    
						    height: 400,     
						    href:'${ctx}/sale/saleContractGoods/create/'+idValue,
						    maximizable:false,
						    modal:true,
						    buttons:[{
								text:'保存',iconCls:'icon-save',
								handler:function(){
									$('#contractGoodsForm').submit(); 
								}
							},{
								text:'关闭',iconCls:'icon-cancel',
								handler:function(){
									dlg_goods_dialog.panel('close');
								}
							}]
						});
					});
					//弹窗修改采购信息
					$("#updateGoods").on("click", function(){
						var row = dgContractGoods.datagrid('getSelected');
						if(rowIsNull(row)) return;
						dlg_goods_dialog = $("#dlg_goods").dialog({   
						    title: '修改采购信息',    
						    width: 700,    
						    height: 400,
						    href:'${ctx}/sale/saleContractGoods/update/'+row.id,
						    maximizable:false,
						    modal:true,
						    buttons:[{
								text:'保存',iconCls:'icon-save',
								handler:function(){
									$('#contractGoodsForm').submit(); 
								}
							},{
								text:'关闭',iconCls:'icon-cancel',
								handler:function(){
									dlg_goods_dialog.panel('close');
								}
							}]
						});
					});
					//删除商品
					$("#deleteGoods").on("click", function(){
						var row = dgContractGoods.datagrid('getSelected');
						if(rowIsNull(row)) return;
						parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
							if (data){
								$.ajax({
									type:'get',
									dataType:'json',
									url:'${ctx}/sale/saleContractGoods/delete/'+row.id,
									success: function(data){
										if(data.returnFlag=='success'){
								    		dgContractGoods.datagrid('clearSelections');
								    		dgContractGoods.datagrid('reload');
								    		$('#mainform #contractQuantity').numberbox('setValue', data.contractQuantity);
								    		$('#mainform #contractAmount').numberbox('setValue', data.contractAmount);
								    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
								    		return true;
										}
									}
								});
							} 
						});
					});
				});
				</script>
			</div>
						
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=SaleContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${saleContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
						
		</div>	
	</form>
</div>

<script type="text/javascript">
var allCode;
function afterMainSubmitSuccess(){
	dgContractGoods.datagrid({url: $('#id').val() ? '${ctx}/sale/saleContractGoods/forContractId/' + $('#id').val() : ''});
}
$(function(){
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
     		var data = eval('(' + data + ')');
	    	if(data.currnetSequence!=null){
	    		$('#contractNo').val(data.currnetSequence);
	    	}
     		afterMainSubmitSuccess();
			$.easyui.loaded();
	    }
	});
 	$('#settlementMode').combobox({
		panelHeight : 'auto',
		required : false,
		url : '${ctx}/system/dictUtil/getDictByCode/SJKFS',
		valueField : 'code',
		textField : 'name',
		multiple : true,
		value : '${saleContract.settlementMode}'==""?null:'${saleContract.settlementMode}'.split(","),
		onHidePanel:function(){}
	});
 	
 	$.ajax({
		type:'GET',
		async: false,
		 dataType: "json", 
		url:"${ctx}/baseInfo/baseUtil/getAffiUnitAbbr",
		success: function(data){
			allCode=""+data;
		}
	});
 	
	//帐套单位
	$('#setUnit').combobox({
		required:true,
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/getAffiByType/10230001',
		valueField : 'customerCode',
		textField : 'customerName',
		prompt:'保存后不能修改！',
	    onSelect:function(record){
	    	if("${keyWord}"){
	    		var souceCode ="";
	    		var str = $('#contractNo').val();
	    		var result = allCode.split(",");
	    		for (var i = 0; i < result.length; i++) {
	    			if(str.indexOf(result[i]) != -1){
	    				souceCode = result[i];
	    			}
	    		}
	    		if(record.unitAbbr!=souceCode){
	    			str = str.replace(souceCode,record.unitAbbr);
	    		}
	    		$('#contractNo').val(str);
	    		$('#seller').val(record.customerName);
	    	}
	    }
	});
	
	//业务经办人选择带出费用承担人
	$('#businessManager').combotree({
	    onSelect:function(node){
	    	$('#feeUnderwriter').combotree('setValue', node.loginName);
	    }
	});
	
	if("${action}"!="create"){
		$('#setUnit').combobox('disable');
	}
});

/****/
$(function(){
	//var action = '${action}';
	if('${action}' == 'view') {
		$("#tbGoods").hide();
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		$("#mainform").find('.easyui-numberbox').numberbox({
		    disabled:true
		});
		$("#mainform").find('.easyui-combotree').combotree({
		    disabled:true
		});
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
});

//经营方式选择
function onselectManageMode(record) {
	if (record.code == "10650001") {//转口隐藏
		$("#deliveryModeTr").hide();
		$("#transferClauseTr").hide();
	} else {
		$("#deliveryModeTr").show();
		$("#transferClauseTr").show();
	}
}

</script>
</body>
</html>