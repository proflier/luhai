<%@ page import="com.cbmie.lh.logistic.entity.PortContract"%>
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
	<form id="mainform" action="${ctx}/logistic/portContract/${action}" method="post">
		<input id="portContractId" name="id" type="hidden"  value="${portContract.id }" />
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">合同号</th>
						<td colspan="3" >
							<input id="contractNo" name="contractNo" readonly="readonly" type="text" value="${portContract.contractNo}" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th  width="20%">免堆期天数</th>
						<td  width="30%">
							<input id="freeHeapDays" name="freeHeapDays" value="${portContract.freeHeapDays}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
						<th  width="20%">达量免堆数(万吨)</th>
						<td  width="30%">
							<input id="freeHeapCounts" name="freeHeapCounts" value="${portContract.freeHeapCounts}" class="easyui-numberbox" data-options="precision:2"/>
						</td>
					</tr>
					<tr>
						<th width="20%">合同开始日期</th>
						<td width="30%">
							<input id="startDay" name="startDay" type="text" value="<fmt:formatDate value="${portContract.startDay }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th width="20%">合同结束日期</th>
						<td width="30%">
							<input id="endDay" name="endDay" type="text" value="<fmt:formatDate value="${portContract.endDay }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th width="20%">签订单位</th>
						<td width="30%">
							<mytag:combobox name="signAffiliate" value="${portContract.signAffiliate}" type="customer" parameters="10230007" />
						</td>
						<th width="20%">联系方式</th>
						<td width="30%">
							<input name="traderContact" type="text" value="${portContract.traderContact}" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th width="20%">是否法人签订</th>
						<td width="30%">
							<mytag:combobox name="isCorporationSign" value="${portContract.isCorporationSign}" type="dict" parameters="YESNO" />
						</td>
						<th width="20%">代理人</th>
						<td width="30%">
							<input name="agent" type="text" value="${portContract.agent}" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th width="20%">审核类别</th>
						<td width="30%">
							<mytag:combobox name="checkTypeCode" value="${portContract.checkTypeCode}" type="dict" parameters="checkType" />
						</td>
						<th width="20%">是否法务审核</th>
						<td width="30%">
							<mytag:combobox name="isLegalReview" value="${portContract.isLegalReview}" type="dict" parameters="YESNO" />
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td >
							<input id="setUnit" name="setUnit"   type="text" value="${portContract.setUnit }"  class="easyui-validatebox"  />
						</td>
						<th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${portContract.businessManager}"  required="true"  type="userTree" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3" style="height:1cm"><textarea  name="remarks" type="text" id="remarks"  class="easyui-validatebox"
						style="overflow:auto;width:50%;height:100%;">${portContract.remarks}</textarea>
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
								<input type="hidden" name="createrNo" value="${portContract.createrNo }"/>
								<input type="hidden" name="createrName" value="${portContract.createrName }"/>
								<input type="hidden" name="createrDept" value="${portContract.createrDept }"/>
								<input type="hidden" name="createDate" value="<fmt:formatDate value='${portContract.createDate  }' pattern='yyyy-MM-dd'/>" />
								${portContract.createrName }</td>
							<th>部门</th>
							<td>${portContract.createrDept }</td>
							<th>制单日</th>
							<td>
								<fmt:formatDate value="${portContract.createDate  }" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<c:if test="${portContract.pid ne null }">
					<fieldset class="fieldsetClass" >
					<legend>变更原因</legend>
						<div >
							<input id="changeReason" style="width:45%;height:40px;" name="changeReason" value="${portContract.changeReason }" class="easyui-validatebox"/>
						</div>
					</fieldset>
				</c:if>
				<fieldset class="fieldsetClass" id="operateField">
					<legend>作业费</legend>
					<div>
						<div id="tb_operate" style="padding:5px;height:auto">
						     <div>	
								<a id="add_operate" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
								<span class="toolbar-item dialog-tool-separator"></span>
						      	<a id="update_operate" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
						   		<span class="toolbar-item dialog-tool-separator"></span>
						   		<a id="delete_operate" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
						     </div>
						</div>
						<table id="operateList"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" id="extraField">
					<legend>杂费</legend>
					<div>
						<div id="tb_extra" style="padding:5px;height:auto">
						     <div>	
								<a id="add_extra" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
								<span class="toolbar-item dialog-tool-separator"></span>
						      	<a id="update_extra" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
						   		<span class="toolbar-item dialog-tool-separator"></span>
						   		<a id="delete_extra" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
						     </div>
						</div>
						<table id="extraList"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>相关员工</legend>
					<%@ include file="/WEB-INF/views/permission/businessShowUsers.jsp"%>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PortContract.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${portContract.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
						
		</div>
	</form>
</div>
<script type="text/javascript">
var allCode;
	var operateExpense = {
		operateList:{},
		initOperateList:	function(){
			if($("#portContractId").val()==null || $("#portContractId").val()=="") return;
			operateExpense.operateList=$('#operateList').datagrid({
				method: "get",
				url: '${ctx}/logistic/portContract/operate/json?filter_EQL_portContractId='+$("#portContractId").val(),
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				extEditing:false,
				singleSelect:true,
			    columns:[[    
					{field:'portNoView',title:'港口',width:15},
					{field:'tradeCategoryView',title:'贸易类别',width:5},
					{field:'operateTypeView',title:'作业方式',width:10},
					{field:'expenseStart',title:'计价范围开始(万吨)',width:10},
					{field:'expenseEnd',title:'计价范围结束',width:10},
					{field:'unitPrice',title:'单价(元/吨)',width:10},
					{field:'summary',title:'备注',width:30,
						formatter: function (value) {
							if(value!=''&&value!=null){
								var returnValue = value.substring(0,20)+"...";
			                	return "<span title='" + value + "'>" + returnValue + "</span>";
							}else{
								return "";
							}
		                }
					}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			})
		},
		operateForm:'',
		operateAdd:function(){
			var idValue = $('#portContractId').val();
			if(idValue==null || idValue==""){
				$.messager.alert('提示','请先保存主表！','info');
				return;
			}
			operateExpense.operateForm = $("#dlg_operate").dialog({
				method:'GET',
			    title: '新增作业信息',    
			    width: 700,    
			    height: 400,     
			    href:'${ctx}/logistic/portContract/operate/create/'+idValue,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#operateForm').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						operateExpense.operateForm.panel('close');
					}
				}]
			});
		},
		operateUpdate:function(){
			var row = operateExpense.operateList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			operateExpense.operateForm = $("#dlg_operate").dialog({   
			    title: '修改作业信息',    
			    width: 700,    
			    height: 400,
			    href:'${ctx}/logistic/portContract/operate/update/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#operateForm').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						operateExpense.operateForm.panel('close');
					}
				}]
			});
		},
		operateDelete:function(){
			var row = operateExpense.operateList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:'${ctx}/logistic/portContract/operate/delete/'+row.id,
						success: function(data){
							var data = eval('(' + data + ')');
							if(data.returnFlag=='success'){
								operateExpense.operateList.datagrid('clearSelections');
								operateExpense.operateList.datagrid('reload');
								parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
								return true;
							}else if(data.returnFlag=='fail'){
								parent.$.messager.alert(data.returnMsg);
								return false;
							}
						}
					});
				} 
			});
		}
	};
	var extraExpense = {
				extraList:{},
				initextraList:function(){
					if($("#portContractId").val()==null || $("#portContractId").val()=="") return;
					extraExpense.extraList = $('#extraList').datagrid({
						method: "get",
						url: '${ctx}/logistic/portContract/extra/json?filter_EQL_portContractId='+$("#portContractId").val(),
					  	fit : false,
						fitColumns : true,
						scrollbarSize : 0,
						border : false,
						striped:true,
						idField : 'id',
						rownumbers:true,
						extEditing:false,
						singleSelect:true,
					    columns:[[    
							{field:'expenseTypeView',title:'费用类别',width:10},
							{field:'countUnitView',title:'计量单位',width:10},
							{field:'expenseStart',title:'计价范围开始(天)',width:10},
							{field:'expenseEnd',title:'计价范围结束',width:10},
							{field:'unitPrice',title:'单价',width:10}
					    ]],
					    sortName:'id',
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false
					})
				},
			extraForm:'',
			extraAdd:function(){
				var idValue = $('#portContractId').val();
				if(idValue==null || idValue==""){
					$.messager.alert('提示','请先保存主表！','info');
					return;
				}
				extraExpense.extraForm = $("#dlg_extra").dialog({
					method:'GET',
				    title: '新增杂费信息',    
				    width: 700,    
				    height: 400,     
				    href:'${ctx}/logistic/portContract/extra/create/'+idValue,
				    maximizable:false,
				    modal:true,
				    buttons:[{
						text:'保存',iconCls:'icon-save',
						handler:function(){
							$('#extraForm').submit(); 
						}
					},{
						text:'关闭',iconCls:'icon-cancel',
						handler:function(){
							extraExpense.extraForm.panel('close');
						}
					}]
				});
			},
			extraUpdate:function(){
				var row = extraExpense.extraList.datagrid('getSelected');
				if(rowIsNull(row)) return;
				extraExpense.extraForm = $("#dlg_extra").dialog({   
				    title: '修改作业信息',    
				    width: 700,    
				    height: 400,
				    href:'${ctx}/logistic/portContract/extra/update/'+row.id,
				    maximizable:false,
				    modal:true,
				    buttons:[{
						text:'保存',iconCls:'icon-save',
						handler:function(){
							$('#extraForm').submit(); 
						}
					},{
						text:'关闭',iconCls:'icon-cancel',
						handler:function(){
							extraExpense.extraForm.panel('close');
						}
					}]
				});
			},
			extraDelete:function(){
				var row = extraExpense.extraList.datagrid('getSelected');
				if(rowIsNull(row)) return;
				parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
					if (data){
						$.ajax({
							type:'get',
							url:'${ctx}/logistic/portContract/extra/delete/'+row.id,
							success: function(data){
								var data = eval('(' + data + ')');
								if(data.returnFlag=='success'){
									extraExpense.extraList.datagrid('clearSelections');
									extraExpense.extraList.datagrid('reload');
									parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
									return true;
								}else if(data.returnFlag=='fail'){
									parent.$.messager.alert(data.returnMsg);
									return false;
								}
							}
						});
					} 
				});
			}
	};
$(function(){
	operateExpense.initOperateList();
	extraExpense.initextraList();
	 $("#add_operate").on("click", operateExpense.operateAdd); 
	$("#update_operate").on("click", operateExpense.operateUpdate);
	$("#delete_operate").on("click", operateExpense.operateDelete);
	 $("#add_extra").on("click", extraExpense.extraAdd); 
	$("#update_extra").on("click", extraExpense.extraUpdate);
	$("#delete_extra").on("click", extraExpense.extraDelete);
});
$(function(){
	if('${action}' == 'view') {
		$("#tb_operate").hide();
		$("#tb_extra").hide();
	}
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if( $('#mainform').form('validate') && (!$("#portContractId").val() == '') ){//主表校验通过且已经保存数据
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
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		portContract.contractList.datagrid('reload');
	    		$('#portContractId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		if(data.currnetSequence!=null){
		    		$('#contractNo').val(data.currnetSequence);
		    	}
	    		operateExpense.initOperateList();
	     		extraExpense.initextraList();
	     		$.easyui.loaded();
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		$.easyui.loaded();
	    		return false;
	    	}  
	    }
	});
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox,.easyui-numberbox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
	

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
    	}
    }
});
		
		if("${action}"!="create"){
			$('#setUnit').combobox('disable');
		}
});
</script>
</body>
</html>