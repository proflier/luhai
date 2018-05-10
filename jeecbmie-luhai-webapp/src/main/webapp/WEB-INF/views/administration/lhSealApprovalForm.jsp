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
		<form id="mainform"  action="${ctx}/administration/sealApproval/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '印章管理', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>用印信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">印章公司</th>
						<td width="30%">
							<input id="id" name="id" type="hidden"  value="${sealApproval.id }" />
							<input id="contractCode" name="contractCode" type="text"  value="${sealApproval.contractCode }"/>
							<input id="contractCategory" name="contractCategory" type="hidden"  value="${sealApproval.contractCategory }"/>
						</td>
						<th width="20%">印章类别</th>
						<td width="30%">
							<input id="signetCode" name="signetCode" type="text"  value="${sealApproval.signetCode }"/>
							<input id="typeName" name="typeName" type="hidden"  value="${sealApproval.typeName }"/>
						</td>
					</tr>
					<tr>
						<th>用印方式</th>
						<td colspan="3">
							<input id="sealCode" name="sealCode" type="text"  value="${sealApproval.sealCode }"/>
							<input id="sealModel" name="sealModel" type="hidden"  value="${sealApproval.sealModel }"/>
						</td>
					</tr>
					<tr>
						<th>申请人</th>
						<td>
							<input id="applicantInformation" name="applicantInformation" type="text" value="${sessionScope.user.name  }" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th>申请人单位</th>
						<td>
							<input id="applicantUnit" name="applicantUnit" type="text"  value="${sessionScope.user.organization.orgName }" class="easyui-validatebox"  data-options="required:true"  />
						</td>
					</tr>
					<tr>
						<th>用印日期</th>
						<td>
							<input id="singnDate" name="singnDate" type="text" value="<fmt:formatDate value="${sealApproval.singnDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true" />
						</td>
						<th>份数</th>
						<td>
						<input type="text" id="copies" name="copies"  value="${sealApproval.copies }" class="easyui-numberbox" data-options="max:99999999999,required:true"/>
						</td>
					</tr>
					<tr>
						<th>文件名及主要内容</th>
						<td colspan="3">
							<textarea name="fileNameMainContents" class="easyui-validatebox" id="fileNameMainContents"
							style="overflow:auto;width:90%;height:450%;">${sealApproval.fileNameMainContents }</textarea>
						</td>
					</tr>
					<tr>
						<th>事由及提交单位</th>
						<td colspan="3" style="height:1cm"><textarea  name="causeSubmission" type="text" id="causeSubmission"  class="easyui-validatebox"
					style="overflow:auto;width:50%;height:100%;">${sealApproval.causeSubmission}</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">经办人</th>
						<td width="30%">${empty sealApproval.createrName ? sessionScope.user.name : sealApproval.createrName }</td>
						<th width="20%" >经办部门</th>
						<td width="30%">${empty sealApproval.createrDept ? sessionScope.user.organization.orgName : sealApproval.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ sealApproval.createDate  }"/></td>
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
		</form>
		
<script type="text/javascript">
	$(function() {
		var editor;
	    window.editor = KindEditor.create('#fileNameMainContents',{
	          resizeType:1,      
//		          urlType:'domain', // 带有域名的绝对路径
	          uploadJson : '${ctx}/static/plugins/kindeditor/jsp/upload_json.jsp',
	          fileManagerJson : '${ctx}/static/plugins/kindeditor/jsp/file_manager_json.jsp',
	          allowFileManager : false,
	          afterChange:function(){
	        	this.sync();  
	          },
	 	});
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
					$.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,operObj.list);
				$.easyui.loaded();
			} 
		});
		//印章类别
		$('#signetCode').combobox({
				panelHeight : 'auto',
				required : true,
				url : '${ctx}/system/dictUtil/getDictByCode/SIGNETTYPE',
				valueField : 'code',
				textField : 'name',
				onSelect:function(data){
					$('#typeName').val(data.name);
				} 
			});
		//印章公司
		$('#contractCode').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dictUtil/getDictByCode/sealCompany',
			valueField : 'code',
			textField : 'name',
			onSelect:function(data){
				$('#contractCategory').val(data.name);
			} 
		});
		//用印方式
		$('#sealCode').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dictUtil/getDictByCode/useSeal',
			valueField : 'code',
			textField : 'name',
			onSelect:function(data){
				$('#sealModel').val(data.name);
			} 
		});
	});
</script>

</body>
</html>