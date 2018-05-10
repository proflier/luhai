<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div>
			<fieldset class="fieldsetClass">
			<legend>审批操作</legend>
			<form id="process_audit_form" action="" method="post">
				<table width="100%" class="fstableClass">
					<tr>
						<td width="25%" style="font-weight: bold">
							当前节点
							<input type="hidden" id="actionType" name="actionType" value="${approval.actionType}"/>
							<input type="hidden" name="taskId" value="${approval.taskId }" />
							<input type="hidden" name="businessKey" value="${approval.businessKey }" />
							<input type="hidden" name="key" value="${approval.key }" />
							<input type="hidden" name="processInstanceId" value="${approval.processInstanceId }" />
						</td>
						<td >
							${curActivity.properties.name}
						</td>
					</tr>
					<tr>
						<td width="25%" style="font-weight: bold">
							审核动作
						</td>
						<td >
							<c:forEach var="back" items="${nexttransitions}">
								<input type="radio" name="nextId" value="${back.id}" style="margin-top:-2px"/>${back.properties.name}
							</c:forEach>
							<c:if test="${backActivity ne null}">
								<input type="radio" id="activitiTypeId" name="nextId" value="${backActivity.id}" style="margin-top:-2px" />驳回上一节点
								<c:if test="${backActivity.id ne 'apply'}">
									<input type="radio" id="applyTypeId" name="nextId" value="apply" style="margin-top:-2px" />退回起草人
								</c:if>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="25%" style="font-weight: bold">
							办理人
						</td>
						<td  id="dealerId">
							
						</td>
					</tr>
					<tr>
						<td width="25%" style="font-weight: bold">
							传阅人
						</td>
						<td  id="readerId">
							
						</td>
						<%-- <td >
							<input id="passUserIds" name='passUserIds' value="${approval.passUserIds}"/>
						</td> --%>
					</tr>
					<tr>
						<td width="25%" style="font-weight: bold">
							优先级
						</td>
						<td >
							<input type="radio" name="priority" value="50" checked="checked" style="margin-top:-2px"/>普通
							<input type="radio" name="priority" value="100" style="margin-top:-2px"/>加急
							<input type="radio" name="priority" value="150" style="margin-top:-2px"/>紧急
						</td>
					</tr>
					<!-- 
					<tr>
						<td width="25%" style="font-weight: bold">
							限时完成
						</td>
						<td >
							<input id="limitDate" name="limitDate"   type="text" value="<fmt:formatDate value="${approval.limitDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					 -->
					<tr>
						<td width="25%" style="font-weight: bold">
							提醒方式
						</td>
						<td >
							<input type="checkbox" name="email" value="1"/>邮件提醒
							<input type="checkbox" name="sms" value="1"/>短信提醒
						</td>
					</tr>
					<tr>
						<td width="25%" style="font-weight: bold">
							处理意见
						</td>
						<td>
							<textarea id="comments" name="comments" rows="6" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="required:true,validType:['length[0,2000]']" onkeyup="getRemain()"/>
							<br />
							<center>文字最大长度: 2000 还剩: <font id="remain" color="red">2000</font></center>
						</td>
					</tr>
				</table>
			</form>
			</fieldset>
	</div>
	<div style="margin-top: 10px;">
		<center>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cologne-sign-in" onclick="activitiSend();">发送</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" onclick="activitiClose();">关闭</a>
		</center>
	</div>
	<script type="text/javascript">
		
	$(function() {
		$('#process_audit_form').form({
		    onSubmit: function(){
		    	var nextId = $("input[name='nextId']:checked").val();
		    	if(nextId=="" || nextId==undefined){
					alert("请选择审核动作");
					return false;
				}
		    	var isValid = $(this).form('validate');
				return isValid;	// 返回false终止表单提交
		    },    
		    success:function(data){   
				if(data!=null && data=='success'){
					business_page.afterCommit();
				}
		    }
		});
		$("input[name='nextId']").click(getDealer);
		/* $('#passUserIds').combotree({
			width:180,
			method:'GET',
			url:'${ctx}/workflow/getCompanyUser',
		    idField : 'id',
		    textFiled : 'name',
			parentField : 'pid',
			multiple:true,
			onlyLeafCheck:true,
		    onHidePanel:function(){}
		}); */
	});
	function getDealer(){
		var taskId = ${approval.taskId};
		var nextId = $("input[name='nextId']:checked").val();
		var nextType="1";
		$("#dealerId").empty();
		$("#readerId").empty();
		if(nextId=="" || nextId==undefined){
			alert("请选择审核动作");
			return false;
		}
		if($("#activitiTypeId:checked").val()!=undefined || $("#applyTypeId:checked").val()!=undefined){
			nextType="2";
		}
		$("#actionType").val(nextType);
		$.ajax({
			type : 'get',
			url : "${ctx}/workflow/task/prepareDealer/"+taskId+"/"+nextId+"/"+nextType,
			dataType:'json',
			success : function(data_all) {
				if (data_all.dealer != null) {
					var data = data_all.dealer;
					var input2 = $("<input id='directNextId' name='directNextId' type='hidden'/>");
					input2.val(data.directKey);
					$("#dealerId").append(input2);
					var input1 = $("<input id='goId' name='goId' type='hidden'/>");
					input1.val(data.key);
					$("#dealerId").append(input1);
					if(data.selectType == 'A'){
						$.each(data.selectItems,function(i,n){
							$("#dealerId").append("<span>"+n+"</span>");
							if (i != null && i.length > 0) {
								var $i = $("<input type='hidden' name='candidateUserIds'/>").val(i);
								$("#dealerId").append($i);
							}
						});
					}else if(data.selectType == 'S'){
						var input = $("<input id='candidateUserIds' name='candidateUserIds'/>");
						$("#dealerId").append(input);
						if(data.selectRange=='C'){
							$('#candidateUserIds').combotree({
								width:180,
								method:'GET',
								url:'${ctx}/workflow/getCompanyUser',
							    idField : 'id',
							    textFiled : 'name',
								parentField : 'pid',
								onBeforeSelect:function(node){
									if(node.type!='2'){
										return false;
									}
								},
							    onHidePanel:function(){}
							});
						}else{
							var defaultV = null;
							for( var i in data.selectItems){
								defaultV = i;
								break;
							}
							$('#candidateUserIds').combobox({
							    valueField:'id',
							    textField:'name',
							    value:defaultV,
							    data:$.map(data.selectItems,function(key,value){
							    	return {id:value,name:key};
							    })
							});
						}
					}else if(data.selectType == 'M'){
						var input = $("<input id='candidateUserIds' name='candidateUserIds'/>");
						$("#dealerId").append(input);
						if(data.selectRange=='C'){
							$('#candidateUserIds').combotree({
								width:180,
								method:'GET',
								url:'${ctx}/workflow/getCompanyUser',
							    idField : 'id',
							    textFiled : 'name',
								parentField : 'pid',
								multiple:true,
								onlyLeafCheck:true,
							    onHidePanel:function(){}
							});
						}else{
							var defaultV = null;
							for( var i in data.selectItems){
								defaultV = i;
								break;
							}
							$('#candidateUserIds').combobox({
							    valueField:'id',
							    textField:'name',
							    multiple:true,
							    value:defaultV,
							    onHidePanel:function(){},
							    data:$.map(data.selectItems,function(key,value){
							    	return {id:value,name:key};
							    })
							});
						}
					}
				}
				if(data_all.reader!=null){
					var data = data_all.reader;
					if(data.selectType == 'A'){
						$.each(data.selectItems,function(i,n){
							$("#readerId").append("<span>"+n+"</span>");
							var $i = $("<input type='hidden' name='passUserIds'/>").val(i);
							$("#readerId").append($i);
						});
					}else if(data.selectType == 'S'){
						var input = $("<input id='passUserIds' name='passUserIds'/>");
						$("#readerId").append(input);
						if(data.selectRange=='C'){
							$('#passUserIds').combotree({
								width:180,
								method:'GET',
								url:'${ctx}/workflow/getCompanyUser',
							    idField : 'id',
							    textFiled : 'name',
								parentField : 'pid',
								onBeforeSelect:function(node){
									if(node.type!='2'){
										return false;
									}
								},
							    onHidePanel:function(){}
							});
						}else{
							$('#passUserIds').combobox({
							    valueField:'id',
							    textField:'name',
							    data:$.map(data.selectItems,function(key,value){
							    	return {id:value,name:key};
							    })
							});
						}
					}else if(data.selectType == 'M'){
						var input = $("<input id='passUserIds' name='passUserIds'/>");
						$("#readerId").append(input);
						if(data.selectRange=='C'){
							$('#passUserIds').combotree({
								width:180,
								method:'GET',
								url:'${ctx}/workflow/getCompanyUser',
							    idField : 'id',
							    textFiled : 'name',
								parentField : 'pid',
								multiple:true,
								onlyLeafCheck:true,
							    onHidePanel:function(){}
							});
						}else{
							var defaultV = null;
							for( var i in data.selectItems){
								defaultV = i;
								break;
							}
							$('#passUserIds').combobox({
							    valueField:'id',
							    textField:'name',
							    multiple:true,
							    value:defaultV,
							    onHidePanel:function(){},
							    data:$.map(data.selectItems,function(key,value){
							    	return {id:value,name:key};
							    })
							});
						}
					}
				}
			}
		});
	}
	function getRemain() {
		$("#remain").text(2000 - $("#comments").val().length);
	}
	
	function activitiSend(){
		$("#process_audit_form").attr("action","${ctx}/workflow/send");
		$("#process_audit_form").submit();
	}
	
	function activitiClose(){
		business_page.close();
	}
	</script>
</body>
</html>