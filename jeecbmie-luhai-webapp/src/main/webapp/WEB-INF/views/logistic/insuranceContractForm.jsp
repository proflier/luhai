<%@page import="com.cbmie.lh.logistic.entity.InsuranceContract"%>
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
	<div>
		<form id="mainform"  action="${ctx}/logistic/insurance/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '保险合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>保险合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%"><font style="vertical-align: middle; color: red"></font>保险合同号</th>
						<td width="30%">
							<input type="hidden" name="id" id="id" value="${insuranceContract.id}" />
							<input id="contractNo" name="contractNo" type="text" value="${insuranceContract.contractNo }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
						<th width="20%" ><font style="vertical-align: middle; color: red"></font>内部合同号</th>
						<td width="30%">
							<input id="innerContractNo" name="innerContractNo" type="text" style="width:50%;" value="${insuranceContract.innerContractNo }" class="easyui-validatebox"
							data-options="required:true" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>是否已保</th>
						<td>
							<mytag:combobox name="isInsurance" value="${insuranceContract.isInsurance}" type="dict" parameters="YESNO"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保险公司</th>
						<td>
							<mytag:combobox name="insuranceCompany" value="${insuranceContract.insuranceCompany}" type="customer" parameters="10230005"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保额</th>
						<td>
							<input id="money" name="money" type="text" value="${insuranceContract.money }" class="easyui-numberbox"
							data-options="required:false"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保额币种</th>
						<td>
							<mytag:combobox name="moneyCurrency" value="${insuranceContract.moneyCurrency}" type="dict" parameters="currency"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>保费</th>
						<td>
							<input id="amount" name="amount" type="text" value="${insuranceContract.amount }" class="easyui-numberbox"
							data-options="required:false"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保费币种</th>
						<td>
							<mytag:combobox name="amountCurrency" value="${insuranceContract.amountCurrency}" type="dict" parameters="currency"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>费率</th>
						<td>
							<input id="exchangeRate" name="exchangeRate" type="text" value="${insuranceContract.exchangeRate }" class="easyui-validatebox"
							data-options="required:false,validType:['numeric']"/>%
						</td>
						<th><font style="vertical-align: middle; color: red"></font>投保日期</th>
						<td>
							<input name="insuranceDate" type="text"  value="<fmt:formatDate value="${insuranceContract.insuranceDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>船名</th>
						<td>
							<input id="shipNo" name="shipNo" type="text" value="${insuranceContract.shipNo }" readonly="readonly" class="easyui-validatebox"/>
							<a id="shipNo_clear" href="#" title="清除船名" class="easyui-linkbutton" plain="true" iconCls="icon-standard-cross"></a>
							<!--  
							<a id="shipListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toShipList('insurance')">船列表</a>	
							-->
						</td>
						<th>险种</th>
						<td>
							<input id="type" name="type" type="text" value="${insuranceContract.type }" class="easyui-validatebox"/>
						</td> 
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>被保险人</th>
						<td>
							<input id="insurancePerson" name="insurancePerson" type="text" value="${insuranceContract.insurancePerson }" class="easyui-validatebox"/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>保险货物项目</th>
						<td>
							<input id="goodsName" name="goodsName" type="text" value="${insuranceContract.goodsName }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>装载运输工具</th>
						<td>
							<input id="transportTool" name="transportTool" type="text" value="${insuranceContract.transportTool }"  class="easyui-validatebox"
								/>
						</td>
						<th><font style="vertical-align: middle; color: red"></font>赔款偿付地点</th>
						<td>
							<input id="addr" name="addr" type="text" value="${insuranceContract.addr }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th><font style="vertical-align: middle; color: red"></font>合同类别</th>
						<td >
							<input id="tradeCategory" name="tradeCategory" type="text" value="${insuranceContract.tradeCategory}"/>
						</td>
						<th  >帐套单位</th>
						<td >
							<input id="setUnit" name="setUnit"   type="text" value="${insuranceContract.setUnit }"  class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >业务经办人 </th>
						<td colspan="3">
							<mytag:combotree name="businessManager" value="${insuranceContract.businessManager}"  required="true"  type="userTree" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="4" cols="80" id="comment" class="easyui-validatebox" name="comment" >${insuranceContract.comment }</textarea>
						</td>
					</tr>						
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>登记人</th>
						<td>
							 <input id="createrName" name="createrName" type="text" value="${insuranceContract.createrName }" 
							 readonly="true" class="easyui-validatebox"/>
							 <input  name="createrNo" type="hidden" value="${insuranceContract.createrNo }"/>
						</td>
						<th>登记部门</th>
						<td>
							<input id="createrDept" name="createrDept" type="text"  value="${insuranceContract.createrDept }" 
							class="easyui-validatebox" readonly="true" />
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${insuranceContract.pid ne null }">
				<fieldset class="fieldsetClass" >
					<legend>变更原因</legend>
					<div >
						<input id="changeReason" style="width:45%;height:40px;" name="changeReason" value="${insuranceContract.changeReason }" class="easyui-validatebox"/>
					</div>
				</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InsuranceContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${insuranceContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
var allCode;
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
				successTipNew(data,dg);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#innerContractNo').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
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
		
		$('#shipNo').combogrid({
		    panelWidth:450,
		    method: "get",
		    idField:'shipNo',    
		    textField:'noAndName',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    url:'${ctx}/logistic/shipTrace/allShipTrace', 
		    columns:[[
					{field:'shipNo',title:'船编号',width:20},
					{field:'shipName',title:'船中文名',width:20},
					{field:'shipNameE',title:'船英文名',width:20},
					{field:'loadPortView',title:'装港港口',width:20,
// 						formatter: function(value,row,index){
// 							var val;
// 							if(value!=''&&value!=null){
// 								$.ajax({
// 									type:'GET',
// 									async: false,
// 									url:"${ctx}/baseInfo/baseUtil/getPortNameByCode/"+value,
// 									success: function(data){
// 										if(data != null){
// 											val = data;
// 										} else {
// 											val = '';
// 										}
// 									}
// 								});
// 								return val;
// 							}else{
// 								return '';
// 							}
// 						}
					}
		 		]]
		});	
		
		$('#shipNo_clear').click(function(){
			$('#shipNo').combogrid('clear');
		});
		
		//合同类别
		$('#tradeCategory').combobox({
//	 		panelHeight:'auto',
			method: "get",
			url:'${ctx}/system/dictUtil/getDictByCode/htlb',
		    valueField:'code',
		    textField:'name', 
		    required : true,
		    onSelect:function(record){
	   			var str = $('#innerContractNo').val();
	    		if("内贸"==record.name){
					str = str.replace(/FT/,"IT");    			
		    	}else{
		    		str = str.replace(/IT/,"FT");
		    	}
	    		$('#innerContractNo').val(str);
		    }
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
	    		var str = $('#innerContractNo').val();
	    		var result = allCode.split(",");
	    		for (var i = 0; i < result.length; i++) {
	    			if(str.indexOf(result[i]) != -1){
	    				souceCode = result[i];
	    			}
	    		}
	    		if(record.unitAbbr!=souceCode){
	    			str = str.replace(souceCode,record.unitAbbr);
	    		}
	    		$('#innerContractNo').val(str);
	    	}
	    }
	});
			
			if("${action}"!="create"){
				$('#setUnit').combobox('disable');
			}
		
	});
	
	$(function(){
		if('${action}' == 'view') {
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			$("#mainform").find('input').attr("readonly",true);
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
