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
						</td>
						<td colspan="3">
							${curActivity.properties.name}
						</td>
					</tr>
					<tr>
						<td width="25%" style="font-weight: bold">同意,<!-- 选择 -->下一办理节点和办理人</td>
						<td width="25%">
							<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cologne-sign-in" onclick="agree();">同意</a>
						</td>
						<td width="25%" style="font-weight: bold">不同意,选择驳回</td>
						<td width="25%">
							<a id="back" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cologne-sign-out" onclick="disagree();">驳回</a>
						</td>
					</tr>
					<tr>
						<c:if test="${empty nextActivity}">
							<td>办理节点：无</td>
							<td colspan="3">
								办理人：无
							</td>
						</c:if>
						<c:forEach var="next" items="${nextActivity}">
							<td>
								办理节点：${next.properties.name}
							</td>
							<%--  <td colspan="3">
							 	办理人：<input id="process_users${next.id}" name="candidateUserIdMap[${next.id}]" type="text"  data-options="width: 150" value="${approval.candidateUserIdMap[next.properties.name]}"/>
							 </td> --%>
							 <td colspan="3">
							 	<c:if test="${selects[next.id].selectContent == 'G'}">
							 		办理组：
							 	</c:if>
							 	<c:if test="${selects[next.id].selectContent == 'U'}">
							 		办理人：
							 	</c:if>
							 	<c:if test="${selects[next.id].selectType == 'A'}">
							 		<c:forEach var="a" items="${selects[next.id].selectItems}">
							 			${a.value}
							 		</c:forEach>
							 	</c:if>
							 	<c:if test="${selects[next.id].selectType == 'S'}">
							 		<select class="easyui-combobox" name="candidateIdMap[${selects[next.id].exp}]" value="${approval.candidateIdMap[selects[next.id].exp]}" style="width:200px;">
							 			<c:forEach var="a" items="${selects[next.id].selectItems}">
								 			<option value="${a.key}">${a.value}</option>
								 		</c:forEach>
									</select>
							 	</c:if>
							 	<c:if test="${selects[next.id].selectType == 'M'}">
							 		<select class="easyui-combobox" name="candidateIdMap[${selects[next.id].exp}]" data-options="multiple:true,multiline:true,onHidePanel:function(){}"
							 			value="${approval.candidateIdMap[selects[next.id].exp]}" style="width:200px;">
							 			<c:forEach var="a" items="${selects[next.id].selectItems}">
								 			<option value="${a.key}">${a.value}</option>
								 		</c:forEach>
									</select>
							 	</c:if>
							 </td>
						</c:forEach>
					</tr>
					<tr>
						<c:if test="${empty backActivity}">
							<td colspan="4">驳回节点: 无</td>
						</c:if>
						<c:if test="${!empty backActivity}">
							<td colspan="4">
								驳回节点:
								<c:forEach var="back" items="${backActivity}">
									<input type="radio" name="backId" value="${back.id}" style="margin-top:-2px"/>${back.properties.name} 
								</c:forEach>
							</td>
						</c:if>
					</tr>
					<!-- - 
					<tr>
						<td>
							办理节点：<c:if test="${empty nextActivity}">无</c:if>
							<c:forEach var="next" items="${nextActivity}">
								${next.properties.name} 
							</c:forEach>
						</td>
						<td>
							办理人：<c:if test="${empty approval.candidateUserIds}">无</c:if>
							<span id="candidateUserIds">
								<c:forEach var="candidateUserId" items="${approval.candidateUserIds}">
									<input type="checkbox" name="candidateUserIds" value="${candidateUserId}" style="margin-top:-2px;" checked="checked"/>${candidateUserId}
								</c:forEach>
							</span>
						</td>
						<td colspan="2">
							驳回节点：
							<c:if test="${empty backActivity}">无</c:if>
							<c:forEach var="back" items="${backActivity}">
								<input type="radio" name="backId" value="${back.id}" style="margin-top:-2px"/>${back.properties.name} 
							</c:forEach>
						</td>
					</tr>
					-->
					<tr>
						<td colspan="4">
							<font style="font-weight: bold">处理意见</font>
							<br />
							<input type="hidden" name="taskId" value="${approval.taskId }" />
							<input type="hidden" name="businessKey" value="${approval.businessKey }" />
							<input type="hidden" name="key" value="${approval.key }" />
							<textarea id="comments" name="comments" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="required:true,validType:['length[0,255]']" onkeyup="getRemain()"/>
							<br />
							<center>文字最大长度: 255 还剩: <font id="remain" color="red">255</font></center>
							<!-- 
							<center style="margin-top: 5px;">
								邮件提醒：
								<input type="radio" name="email" value="1" style="margin-top:-2px"/>是
								<input type="radio" name="email" value="0" checked="checked" style="margin-top:-2px"/>否
								&nbsp;&nbsp;&nbsp;&nbsp;
								短信提醒：
								<input type="radio" name="sms" value="1" style="margin-top:-2px"/>是
								<input type="radio" name="sms" value="0" checked="checked" style="margin-top:-2px"/>否
							</center>
							 -->
						</td>
					</tr>
				</table>
			</form>
			</fieldset>
	</div>
	<script type="text/javascript">
		
	$(function() {
		var goId = $('input[name="goId"]');
		var backId = $('input[name="backId"]');
		if(goId.size() != 0){
			goId.eq(0).attr("checked", "true");
		}
		if(backId.size() != 0){
			backId.eq(backId.size()-1).attr("checked", "true");
		}else{
			$('#back').linkbutton({
				disabled: true
			});
		}
		
		var candidateUserIds = $('input[name="candidateUserIds"]');
		if(candidateUserIds.size() == 1){
			candidateUserIds[0].style.display = "none";
		}
		
		$('#process_audit_form').form({
		    onSubmit: function(){
		    	var isValid = $(this).form('validate');
		    	//alert(isValid);
				return isValid;	// 返回false终止表单提交
		    },    
		    success:function(data){   
		    	successTip(data,dgToDo,d);
		    	dgHaveDone.datagrid('reload');
				dgRunning.datagrid('reload');
				$("#process_audit_dialog").dialog("close");
				approvalFormDeal.dialog("close");
				//$("#process_audit_form .easyui-linkbutton").removeAttr("onclick");
		    }
		});
		
	});
	
	function goActivity(candidateUserIdExpressions) {
		if(typeof candidateUserIdExpressions == "string"){
			$('#candidateUserIds').html(candidateUserIdExpressions.substring(1, candidateUserIdExpressions.length - 1));
		}else{
			var str;
			for(var i = 0; i < candidateUserIdExpressions.length; i++){
				str += candidateUserIdExpressions[i] + ",";
			}
			$('#candidateUserIds').html(str.substring(0, str.length - 1));
		}
	}
	
	function getRemain() {
		$("#remain").text(255 - $("#comments").val().length);
	}
	
	function agree(){
		$("#process_audit_form").attr("action","${ctx}/workflow/agree/");
		$("#process_audit_form").submit();
	}
	
	function disagree(){
		$("#process_audit_form").attr("action","${ctx}/workflow/back/");
		$("#process_audit_form").submit();
	}
	//此处先单选，后期可改为复选框
	$(function(){
		$("input[name^='candidateUserIdMap']").combobox({
			method:'GET',
		    url: '${ctx}/system/user/select',
		    valueField:'loginName',
		    textField:'name',
//	 		panelHeight:'auto',
			panelHeight:300,
		    animate:true,
		    required:true,
		    onHidePanel:function(){}
		});
	});	
		
	</script>
</body>
</html>