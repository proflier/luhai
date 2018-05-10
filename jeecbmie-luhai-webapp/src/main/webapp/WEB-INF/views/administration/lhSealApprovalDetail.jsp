<%@ page import="com.cbmie.lh.administration.entity.SealApproval"%>
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
	<div id="mainDiv" class="" data-options="border:false">
		<div data-options="title: '印章管理', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>用印信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">印章公司</th>
							<td width="30%">${sealApproval.contractCategory }</td>
						<th width="20%">印章类别</th>
						<td width="30%">
							${sealApproval.typeName }
						</td>
					</tr>
					<tr>
						<th>用印方式</th>
						<td colspan="3">
							${sealApproval.sealModel }
						</td>
					</tr>
					<tr>
						<th>申请人</th>
						<td>
							${sealApproval.applicantInformation }
						</td>
						<th>申请人单位</th>
						<td>
							${sealApproval.applicantUnit }
						</td>
					</tr>
					<tr>
						<th>用印日期</th>
						<td>
							<fmt:formatDate value="${sealApproval.singnDate }" />
						</td>
						<th>份数</th>
						<td>
							${sealApproval.copies }
						</td>
					</tr>
					<tr>	
						<th>文件名及主要内容</th>
						<td colspan="3">
							<textarea name="fileNameMainContents" readonly="true" class="easyui-validatebox" id="fileNameMainContents"
							style="overflow:auto;width:90%;height:450%;">${sealApproval.fileNameMainContents }</textarea>
						</td>
					</tr>
					<tr>
						<th>事由及提交单位</th>
						<td colspan="3" style="height:1cm">
							${sealApproval.causeSubmission }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th width="20%" >登记人</th>
						<td width="30%">${empty sealApproval.createrName ? sessionScope.user.name : sealApproval.createrName }</td>
						<th width="20%" >登记部门</th>
						<td width="30%">${empty sealApproval.createrDept ? sessionScope.user.organization.orgName : sealApproval.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ sealApproval.createDate  }" /></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${sealApproval.updateDate }" /></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
			<fieldset class="fieldsetClass" >
				<legend>附件设置</legend>
			<table width="98%" class="tableClass">
			<input id="accParentEntity" type="hidden"  value="<%=SealApproval.class.getName().replace(".","_") %>" />
			<input id="accParentId" type="hidden" value="${sealApproval.id}" />
			<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</table>
			</fieldset>
			</div>
			</div>	
		<script type="text/javascript">
	$(function() {
		var editor;
	    window.editor = KindEditor.create('#fileNameMainContents',{
	          resizeType:1,      
//		          urlType:'domain', // 带有域名的绝对路径
	          uploadJson : '${ctx}/static/plugins/kindeditor/jsp/upload_json.jsp',
	          fileManagerJson : '${ctx}/static/plugins/kindeditor/jsp/file_manager_json.jsp',
	          allowFileManager : false,
	          readonlyMode : true,
	          afterChange:function(){
	        	this.sync();  
	          }
	 	});
	});
	</script>
</body>
</html>