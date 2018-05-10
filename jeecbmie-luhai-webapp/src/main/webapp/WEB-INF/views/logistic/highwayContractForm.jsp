<%@page import="com.cbmie.lh.logistic.entity.HighwayContract"%>
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
		<form id="mainform"  action="${ctx}/logistic/highwayContract/${action}" method="post">
		<input type="hidden" id="currentAction" value="${action }"/>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>内部合同号</th>
						<td>
							<input type="hidden" name="id" id="id" value="${highwayContract.id}" />
							<input id="innerContractNo" readonly="readonly"  name="innerContractNo" type="text" value="${highwayContract.innerContractNo }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
						<th>合同号</th>
						<td>
							<input id="contractNo" name="contractNo" type="text" value="${highwayContract.contractNo }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>申请部门</th>
						<td>
							<input id="applyDeptId" class="easyui-combotree" name="applyDeptId" type="text" value="${highwayContract.applyDeptId }"/>
						</td>
						<th>申请人</th>
						<td>
							<input id="applyUserId" class="easyui-combobox" name="applyUserId" type="text" value="${highwayContract.applyUserId }"/>
						</td>
					</tr>
					<tr>
						<th>业务经办人</th>
						<td>
							<mytag:combotree name="businessManager" value="${highwayContract.businessManager}"  required="true"  type="userTree" />
						</td>
						<th>申请日期</th>
						<td>
							<input id="applyDate" name="applyDate" type="text" value="<fmt:formatDate value='${highwayContract.applyDate}' />" 
							class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"	data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>合同金额</th>
						<td>
							<input id="money" name="money" type="text" value="${highwayContract.money }" class="easyui-numberbox"
							data-options="precision:'2',required:true"/>
						</td>
						<th>币种</th>
						<td>
							<mytag:combobox name="moneyCurrencyCode" value="${highwayContract.moneyCurrencyCode}" type="dict" parameters="currency"/>
						</td>
					</tr>
					<tr>
						<th>合同开始日期</th>
						<td>
							<input id="startDate" name="startDate" type="text" value="<fmt:formatDate value='${highwayContract.startDate}' />" 
							class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th>合同结束日期</th>
						<td>
							<input id="endDate" name="endDate" type="text" value="<fmt:formatDate value='${highwayContract.endDate}' />" 
							class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>对方单位全称</th>
						<td>
							<mytag:combobox name="traderName" value="${highwayContract.traderName}" type="customer" parameters="10230003" hightAuto="false"/>
						</td>
						<th>对方联系方式</th>
						<td>
							<input id="traderContact" name="traderContact" type="text" value="${highwayContract.traderContact }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>公章</th>
						<td>
							<input id="signetCode" name="signetCode" type="text" value="${highwayContract.signetCode }" class="easyui-combogrid"  />
						</td>
						<th  >印章管理员</th>
						<td>
							<input id="sealManagerShow" type="text" readonly="readonly"/>
							<input id="sealManager" name="sealManager" type="hidden" value="${highwayContract.sealManager }"/>
						</td>
					</tr>
					<tr>
						<th>贸易类型</th>
						<td colspan="3">
							<mytag:combobox name="tradeCategory" value="${highwayContract.tradeCategory}" type="dict" parameters="orderShipType"/>
						</td>
					</tr>
					<tr>
						<th>主要内容</th>
						<td colspan="3">
							<textarea rows="3" cols="80" id="content" class="easyui-validatebox" name="content">${highwayContract.content }</textarea>
						</td>
					</tr>
					<tr>
						<th>是否法人签署 </th>
						<td>
							<input id="isCorporationSign" name="isCorporationSign" type="text" value="${highwayContract.isCorporationSign }" class="easyui-combobox"
							data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '是'}, {label: '0',value: '否'}]"/>
						</td>
						<th>代理人</th>
						<td>
							<input id="agent" name="agent" type="text" value="${highwayContract.agent }" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>审查类别 </th>
						<td>
							<mytag:combobox name="checkTypeCode" value="${highwayContract.checkTypeCode}" type="dict" parameters="checkType"/>
						</td>
						<th>是否法务审查</th>
						<td>
							<input id="isLegalReview" name="isLegalReview" type="text" value="${highwayContract.isLegalReview }" class="easyui-combobox"
							data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '是'}, {label: '0',value: '否'}]"/>
						</td>
					</tr>
					<tr>
						<th>重大非常规披露</th>
						<td colspan="3">
							<textarea rows="3" cols="80" id="tipContent" class="easyui-validatebox" name="tipContent">${highwayContract.tipContent }</textarea>
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td colspan="3">
							<input id="setUnit" name="setUnit"   type="text" value="${highwayContract.setUnit }"  class="easyui-validatebox"  />
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
							<input type="hidden" name="createrNo" value="${highwayContract.createrNo }"/>
							<input type="hidden" name="createrName" value="${highwayContract.createrName }"/>
							<input type="hidden" name="createrDept" value="${highwayContract.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${highwayContract.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${highwayContract.createrName }</td>
						<th  >制单部门</th>
						<td>${highwayContract.createrDept }</td>
						<th  >制单时间</th>
						<td>
							<fmt:formatDate value="${highwayContract.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<c:if test="${highwayContract.pid ne null }">
					<fieldset class="fieldsetClass" >
					<legend>变更原因</legend>
						<div >
							<input id="changeReason" style="width:45%;height:40px;" name="changeReason" value="${highwayContract.changeReason }" class="easyui-validatebox"/>
						</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=HighwayContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${highwayContract.id}" />
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
		$('#applyDeptId').combotree({
			method:'GET',
		    url: '${ctx}/system/organization/json',
		    idField : 'id',
		    textFiled : 'orgName',
			parentField : 'pid',
			panelHeight:300,
		    animate:true,
		    required:true,
		    onChange:function(){
		    	$('#applyUserId').combobox("clear");
		    	$('#applyUserId').combobox('reload','${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#applyDeptId').combotree("getValue"));
		    },
		    onHidePanel:function(){}
		});
		$('#applyUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#applyDeptId').val(),
			valueField : 'id',
			textField : 'name'
		});
		$('#handlingUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItems',
			valueField : 'id',
			textField : 'name'
		});
		if('${highwayContract.sealManager }'!=null && '${highwayContract.sealManager }'!=''){
			$.ajax({
				url:'${ctx}/baseInfo/baseUtil/userNameShow?userId=${highwayContract.sealManager }',
				type:'get',
				success:function(data){
					$('#sealManagerShow').val(data);
				},
			});
		};
		//印章类型 
		$('#signetCode').combogrid({    
		    panelWidth:450,    
		    method: "get",
		    idField:'signetCode',    
		    textField:'typeName',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
			url:'${ctx}/baseInfo/lhUtil/getSignetList', 
		    columns:[[    
					{field:'id',title:'id',hidden:true},  
					{field:'signetCode',title:'编码',sortable:true,width:25},
					{field:'typeCode',title:'印章类型',sortable:true,width:25,
						formatter: function(value,row,index){
							var unit_t = '';
							$.ajax({
								url : '${ctx}/system/dictUtil/getDictNameByCode/SIGNETTYPE/'+value ,
								type : 'get',
								cache : false,
								async: false,
								success : function(data) {
									unit_t = data;
								}
							});
							return unit_t;
							}
					},
					{field:'orgId',title:'所属公司',sortable:true,width:25,
						formatter: function(value,row,index){
						var unit_t = '';
						$.ajax({
							url : '${ctx}/baseInfo/baseUtil/orgNameShow?orgId='+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								unit_t = data;
							}
						});
						return unit_t;
						}	
					}
		    ]],
		    onSelect:function(rowIndex, rowData){
		    	$('#sealManager').val(rowData.saveUserId);
		    	$('#sealManagerShow').val(rowData.saveUserName);
		    }
		}); 
		$('#startDate').bind('click',function(){
		    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'endDate\')}',onpicked:function(){deliveryTerm.click();}});
		});
		$('#endDate').bind('click',function(){
		    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
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
			$("#mainform").find('.easyui-combotree').combotree({
			    disabled:true
			});
			$("#mainform").find('.easyui-combogrid').combogrid({
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
