<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cbmie.lh.feedback.entity.FeedbackContent"%>
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
	<form id="childForm" action="${ctx}/feedback/content/${action}" method="post">
			<input type="hidden" id="feedbackContentId" name="id" value="${feedbackContent.id}" />
			<input id="contentThemeId" name="feedbackThemeId" type="hidden" value="${feedbackContent.feedbackThemeId}" />
			<table style="width:98%;" class="tableClass">
				<tr>
					<th width="20%" >发表人</th>
					<td width="30%" >
						<input type="text" id="contentUserName" readonly="readonly">
						<input type="hidden" id="contentUserId" name="userId" value="${feedbackContent.userId}">
						<input type="hidden" name="deptId" value="${feedbackContent.deptId}">
					</td>
					<th width="20%" >发表时间</th>
					<td width="30%" >
						<input name="publishDate" type="text" readonly value='<fmt:formatDate value="${feedbackContent.publishDate}" pattern="yyyy-MM-dd HH:ss"/>' />
					</td>
				</tr>
				<tr>
					<th width="20%" >反馈内容</th>
					<td colspan="3">
							<textarea  name="content"  class="easyui-validatebox" id="contentContent"
							style="overflow:auto;width:90%;height:450%;">${feedbackContent.content}</textarea>
						</td>
				</tr>
				<tr>
					<th width="20%" >附件</th>
					<td colspan="3">
							<input id="accParentEntity" type="hidden"  value="<%=FeedbackContent.class.getName().replace(".","_") %>" />
							<input id="accParentId" type="hidden" value="${feedbackContent.id}" />
							<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
						</td>
				</tr>
			</table>
	</form>
</div>

<script type="text/javascript">
function initContent(){
	if($("#contentUserId").val()!=null && $("#contentUserId").val()!=""){
		$.ajax({
			type:'GET',
			async: false,
			url:"${ctx}/baseInfo/baseUtil/userNameShow?userId="+$("#contentUserId").val(),
			success: function(data){
				$("#contentUserName").val(data);
			}
		});
	}
}
$(function(){
	initContent();
	$('#childForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#childForm').form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
    		$.easyui.loaded();
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		//附件刷新
	    		$("#accParentId").val(data.returnId);
	    		$("#feedbackContentId").val(data.returnId);
	    		var urlTmp = '${ctx}/accessory/json?filter_EQS_accParentId='+data.returnId+'&filter_EQS_accParentEntity=com_cbmie_lh_feedback_entity_FeedbackContent';
	    		dgAcc.datagrid({url:urlTmp});
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	var editor;
    window.editor = KindEditor.create('#contentContent',{
          resizeType:1,      
//	          urlType:'domain', // 带有域名的绝对路径
          uploadJson : '${ctx}/static/plugins/kindeditor/jsp/upload_json.jsp',
          fileManagerJson : '${ctx}/static/plugins/kindeditor/jsp/file_manager_json.jsp',
          allowFileManager : false,
          afterChange:function(){
        	this.sync();  
          },
 	});
});
</script>
</body>
</html>

