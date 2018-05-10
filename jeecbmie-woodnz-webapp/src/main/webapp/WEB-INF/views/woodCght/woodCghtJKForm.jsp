<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="act" tagdir="/WEB-INF/tags/act" %>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/woodCght/woodCghtJK/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '采购合同主信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th   >综合合同号</th>
						<td colspan="3">
							<input id="interContractNo" name="interContractNo" type="text" value="${woodCghtJk.interContractNo }" class="easyui-validatebox"  data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th width="20%" >采购合同号</th>
						<td width="30%">
							<input id="hth" name="hth" type="text" value="${woodCghtJk.hth }" class="easyui-validatebox"  data-options="required:true"/>
							<input id="id" name="id" type="hidden"  value="${woodCghtJk.id }" />
						</td>
						<th  width="20%">协议号</th>
						<td width="30%">
							<input id="xyh" name="xyh" type="text" readonly="readonly" value="${woodCghtJk.xyh }" class="easyui-validatebox"  />
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadProtocol()">选择采购协议</a>
						</td>
					</tr>
					<tr>
						<th  >供货单位</th>
						<td>
							<input id="ghdw" name="ghdw" type="text" value="${woodCghtJk.ghdw }" class="easyui-validatebox" />
						</td>
						<th  >进口国别</th>
						<td>
							<input id="jkgb" name="jkgb"  type="text" value="${woodCghtJk.jkgb }"  />
						</td>
					</tr>
					<tr>
						<th  >价格条款</th>
						<td>
							<input id="jgtk" name="jgtk" type="text" value="${woodCghtJk.jgtk }" />
						</td>
						<th  >应预付额</th>
						<td>
							<input id="yyfe" name="yyfe" type="text" value="${woodCghtJk.yyfe }" class="easyui-numberbox" data-options="precision:'2'" />
						</td>
					</tr>
					<tr>
						<th  >订货总量</th>
						<td>
							<input id="dhzl" name="dhzl" type="text" value="${woodCghtJk.dhzl }" class="easyui-numberbox" data-options="required:true,precision:'3'"  />
						</td>
						<th  >数量单位</th>
						<td>
							<input id="sldw" name="sldw" type="text" value="${woodCghtJk.sldw }" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th  >定金</th>
						<td>
							<input id="dj" name="dj" type="text" value="${woodCghtJk.dj }" class="easyui-numberbox" data-options="precision:'2'"   />
						</td>
						<th  >货款</th>
						<td>
							<input id="hk" name="hk" type="text" value="${woodCghtJk.hk }" class="easyui-numberbox" data-options="required:true,precision:'2'" />
						</td>
					</tr>
					 <tr>
						<th  >币种</th>
						<td>
							<input id="cgbz" name="cgbz" type="text" value="${woodCghtJk.cgbz }" class="easyui-validatebox"  />
						</td>
						<th  >汇率</th>
						<td>
							<input id="hl" name="hl" type="text" value="${woodCghtJk.hl }" class="easyui-numberbox" data-options="precision:'5'"/>
						</td>
					</tr>
					<tr>
						<th  >付款方式</th>
						<td>
							<input id="skfs" name="skfs" type="text" value="${woodCghtJk.skfs }" class="easyui-validatebox"  />
						</td>
						<th  >授信类型</th>
						<td>
							<input id="sxlx" name="sxlx" type="text" value="${woodCghtJk.sxlx }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >溢短（%）</th>
						<td>
							<input id="yd" name="yd" type="text" value="${woodCghtJk.yd }" class="easyui-numberbox" data-options="required:true,precision:'2',suffix:'%'"/>
						</td>
						<th  >订货日期</th>
						<td>
							<input id="dhrq" name="dhrq"   type="text" value="<fmt:formatDate value="${woodCghtJk.dhrq }" />" datefmt="yyyy-MM-dd" 
								class="easyui-my97" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th  >交货起期</th>
						<td>
							<input id="jhqq" name="jhqq"   type="text" value="<fmt:formatDate value="${woodCghtJk.jhqq }" />" datefmt="yyyy-MM-dd" class="easyui-my97" />
						</td>
						<th  >交货止期</th>
						<td>
							<input id="jhzq" name="jhzq"   type="text" value="<fmt:formatDate value="${woodCghtJk.jhzq }" />" datefmt="yyyy-MM-dd" class="easyui-my97" />
						</td>
					</tr>
					<tr>
						<th  >订货单位  </th>
						<td>
							<input id="dhdw" name="dhdw" type="text" value="${woodCghtJk.dhdw }" class="easyui-validatebox"  />
						</td>
						<th  >收货单位</th>
						<td>
							<input id="sfdw" name="sfdw" type="text" value="${woodCghtJk.sfdw }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th  >业务员 </th>
						<td>
							<input id="ywy" name="ywy" readonly="readonly" type="text" value="${empty woodCghtJk.ywy ? sessionScope.user.name : woodCghtJk.ywy }" class="easyui-validatebox"  />
						</td>
						<th  >合同类别</th>
						<td>
							<input id="htlb" name="htlb" type="text" value="${woodCghtJk.htlb }" class="easyui-validatebox" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty woodCghtJk.createrName ? sessionScope.user.name : woodCghtJk.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty woodCghtJk.createrDept ? sessionScope.user.organization.orgName : woodCghtJk.createrDept }</td>
					</tr>
				</table>
				</fieldset>
				<fieldset id="changeReasonField" style="display:none;" class="fieldsetClass" >
				<legend>变更原因</legend>
					<div >
						<input id="changeReason" style="width:45%;height:40px;" name="changeReason" value="${woodCghtJk.changeReason }" class="easyui-validatebox"/>
					</div>
				</fieldset>
			</div>
			<div data-options="title: '子表信息', iconCls: false, refreshable: false">
					<input type="hidden" name="woodCghtJkMxJson" id="woodCghtJkMxJson"/>
					<%@ include file="/WEB-INF/views/woodCght/woodCghtJkMxForm.jsp"%>
			</div>	
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=WoodCghtJk.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${woodCghtJk.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		<!-- 
		<c:if test="${not empty woodCghtJk.processInstanceId}">
			<act:histoicFlow procInsId="${woodCghtJk.processInstanceId }" />
		</c:if>
		 -->
		</form>
		
	</div>
<script type="text/javascript">

	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
// 				alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
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

		if("change"=="${action}"){
			$('#changeReasonField').show();
		}
			
		
		
		//供货单位
		$('#ghdw').combobox({
			required : true,
			method: "get",
			url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=2',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//价格条款
		$('#jgtk').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/jgtk',
			valueField : 'id',
			textField : 'name'
		});
		
		//国别地区
		$('#jkgb').combotree({
			method:'GET',
		    url: '${ctx}/system/countryArea/json',
		    idField : 'id',
		    textFiled : 'name',
			parentField : 'pid',
		    panelHeight:'auto',
		    animate:true,
		    required:true,
		    onHidePanel:function(){
		    }
		});
		
		//币种
		$('#cgbz').combobox({
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/CURRENCY',
			valueField : 'id',
			textField : 'name'
		});
		
		//数量单位
		$('#sldw').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/sldw',
			valueField : 'id',
			textField : 'name'
		});
		
		//收款方式 
		$('#skfs').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/skfs',
			valueField : 'id',
			textField : 'name'
		});
		
		//授信类型
		$('#sxlx').combobox({
			panelHeight : 'auto',
			url : '${ctx}/system/downMenu/getDictByCode/sxlx',
			valueField : 'id',
			textField : 'name'
		});
		
		//订货单位 
		$('#dhdw').combobox({
			required : true,
			method: "get",
			url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//收货单位
		$('#sfdw').combobox({
			required : true,
			method: "get",
			url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=1',
			valueField : 'id',
			textField : 'customerName'
		});
		
		//合同类别
		$('#htlb').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/htlb',
			valueField : 'id',
			textField : 'name'
		});
		
	});
	
	function loadProtocol(){
		dlgProtocol=$("#dlgProtocol").dialog({
		  	width: 900,    
		    height: 400, 
		    title: '选择采购协议',
		    href:'${ctx}/woodCght/woodCghtJK/loadProtocol/',
		    modal:true,
		    closable:true,
		    style:{borderWidth:0},
		    buttons:[{
				text:'确认',iconCls:'icon-ok',
				handler:function(){
					initContract();
					dlgProtocol.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlgProtocol.panel('close');
				}
			}]
		});
		
	}
</script>

</body>
</html>