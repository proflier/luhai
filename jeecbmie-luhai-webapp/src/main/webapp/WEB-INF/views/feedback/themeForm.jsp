<%@ page import="com.cbmie.lh.feedback.entity.FeedbackTheme"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>

<body>
		<form id="mainform"  action="${ctx}/feedback/theme/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '反馈主题', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>反馈主题</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">主题</th>
						<td colspan="3" >
							<input id="feedbackThemeId" name="id" type="hidden"  value="${feedbackTheme.id }" />
							<input id="title" size="120" name="title" type="text"  value="${feedbackTheme.title }" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th width="20%">反馈分类</th>
						<td width="30%" >
							<mytag:combobox name="classification" value="${feedbackTheme.classification}" type="dict" parameters="feedbackClassification"/>
						</td>
						<th width="20%">反馈类型</th>
						<td width="30%" >
							<mytag:combobox name="types" value="${feedbackTheme.types}" type="dict" parameters="feedbackTypes"/>
						</td>
					</tr>
					<tr>
						<th width="20%">状态</th>
						<td width="30%" >
							<mytag:combobox name="state" value="${feedbackTheme.state}" type="dict" parameters="feedbackState" disabled="true"/>
						</td>
						<th width="20%">重要性</th>
						<td width="30%" >
							<mytag:combobox name="importance" value="${feedbackTheme.importance}" type="dict" required="false" parameters="feedbackImportance"/>
						</td>
					</tr>
					<tr>
						<th width="20%">反馈对象类型</th>
						<td width="30%" >
							<mytag:combobox name="feedbackObjectType" value="${feedbackTheme.feedbackObjectType}" type="dict" parameters="feedbackObjectType" required="false"/>
						</td>
						<th width="20%">反馈对象</th>
						<td width="30%" >
							<input id="feedbackObject" name="feedbackObject" type="text"  value="${feedbackTheme.feedbackObject }"/>
						</td>
					</tr>
					<tr>
						<th width="20%">部门</th>
						<td width="30%" >
							<input id="dutyDept" name="dutyDept" class="easyui-combotree" type="text" value="${feedbackTheme.dutyDept }" />
						</td>
						<th width="20%">负责员工</th>
						<td width="30%" >
						 	<input id="dutyUser"  name="dutyUser" class="easyui-combobox" value="${feedbackTheme.dutyUser }"/>
						</td>
					</tr>
					<tr>
						<th width="20%">解决期限</th>
						<td width="30%" >
						 	<input name="terminalDate" type="text" value="<fmt:formatDate value="${feedbackTheme.terminalDate}" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						</td>
						<th width="20%">关闭人员</th>
						<td width="30%" >
							<input class="easyui-validatebox" type="text" id="closeUserName" readonly="readonly"/>
						 	<input class="easyui-validatebox" name="closeUser" type="hidden" value="${feedbackTheme.closeUser }"/>
						</td>
					</tr>
					<tr>
						<th width="20%">创建人</th>
						<td width="30%" >
							<input id="createrName" class="easyui-validatebox" name="createrName" type="text" value="${feedbackTheme.createrName }" readonly/>
						 	<input  name="createrNo" type="hidden" value="${feedbackTheme.createrNo }"/>
						</td>
						<th width="20%">创建时间</th>
						<td width="30%" >
							<input name="createDate" class="easyui-validatebox" type="text" readonly value='<fmt:formatDate value="${feedbackTheme.createDate}" pattern="yyyy-MM-dd"/>' />
						</td>
					</tr>
					<tr>
						<th width="20%">修改人</th>
						<td width="30%" >
							<input id="createrName" class="easyui-validatebox" name="updaterName" type="text" value="${feedbackTheme.updaterName }" readonly/>
						 	<input  name="updaterNo" type="hidden" value="${feedbackTheme.updaterNo }"/>
						</td>
						<th width="20%">修改时间</th>
						<td width="30%" >
							<input name="updateDate" class="easyui-validatebox" type="text" readonly value='<fmt:formatDate value="${feedbackTheme.updateDate}" pattern="yyyy-MM-dd"/>' />
						</td>
					</tr>
					<tr>
						<th width="20%">公共反馈</th>
						<td width="30%" >
							<input name="feedbackPublic"  value="${feedbackTheme.feedbackPublic }"  
							class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto', data: [{label: '0',value: '否'}, {label: '1',value: '是'}]" />
						</td>
						<th width="20%">讨论组</th>
						<td width="30%" >
							<%-- <input id="discussGroupId" name="discussGroupId" type="text"  value="${feedbackTheme.discussGroupId }" class="easyui-combobox"/> --%>
						</td>
					</tr>
					<tr>
						<th>讨论组人员
							<input type="hidden" id="themeMemberIds" name="themeMemberIds" value="${feedbackTheme.themeMemberIds}"/>
							<input type="hidden" id="themeMemberKeyIds" name="themeMemberKeyIds" value="${feedbackTheme.themeMemberKeyIds}"/>
						</th>
						<td colspan="3" style="height:1cm" >
							<div style="width: 90%;display: block;float: left;">
								<div>
									<span style="font-weight: bold;">关键人员:</span>
									<span id="themeMemberKeys">${feedbackTheme.themeMemberKeys}</span>
								</div>
								<div>
									<span style="font-weight: bold;">所有人员:</span>
									<span id="themeMembers">${feedbackTheme.themeMembers}</span>
								</div>
							</div>
							<div style="width: 8%;display: block;float: left;">
								<a id="toPurchaseListId" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="selectDiscussers();">选择人员</a>
							</div>
						</td>
					</tr>
					<tr>
						<th>描述</th>
						<td colspan="3" style="height:1cm">
							<textarea  name="description" id="description"  class="easyui-validatebox"
							style="overflow:auto;width:50%;height:100%;">${feedbackTheme.description}</textarea>
						</td>
					</tr>
					<tr>
						<th>处理结果</th>
						<td colspan="3" style="height:1cm">
							<textarea  name="dealResult" id="dealResult"  class="easyui-validatebox"
							style="overflow:auto;width:50%;height:100%;">${feedbackTheme.dealResult}</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>附件</legend>
				<input id="accParentEntity" type="hidden"  value="<%=FeedbackTheme.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${feedbackTheme.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
				</fieldset>
				<fieldset class="fieldsetClass" id="relField">
					<legend>归档列表</legend>
					<div style="width:98%;border:1px;" >
						<div id="tool_rel" style="padding:5px;height:auto;">
						     <div>	
								<a id="add_rel" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a>
								<span class="toolbar-item dialog-tool-separator"></span>
								<a id="update_rel" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
								<span class="toolbar-item dialog-tool-separator"></span>
						   		<a id="delete_rel" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
						     </div>
						</div>
						<table id="fileList" width="98%" class="tableClass"></table>
					</div>
				</fieldset>
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
var fileRel = {
		fileList:{},
		initfileList:	function(){
			if(($("#feedbackThemeId").val())==null ||($("#feedbackThemeId").val())==""){
				return;
			} 
			fileRel.fileList=$('#fileList').datagrid({
				method: "get",
				url: '${ctx}/feedback/file/list/'+$("#feedbackThemeId").val(),
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
					{field:'id',title:'',hidden:true},
					{field:'fileTypes',title:'类别',width:20,
						formatter: function(value,row,index){
	          				var val;
	          				if(value!=''&&value!=null){
	          					$.ajax({
	          						type:'GET',
	          						async: false,
	          						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackFileTypes/"+value,
	          						success: function(data){
	          							if(data != null){
	          								val = data;
	          							} else {
	          								val = '';
	          							}
	          						}
	          					});
	          					return val;
	          				}else{
	          					return '';
	          				}
	          			}},  
					{field:'fileTarget',title:'目标',width:20,
	          				formatter: function(value,row,index){
	        					var val;
	        					if(value!=''&&value!=null){
	        						if(row.fileTypes!='11020005' && row.fileTypes!='11020006'){
	        							$.ajax({
	        								type:'GET',
	        								async: false,
	        								url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
	        								success: function(data){
	        									if(data != null){
	        										val = data;
	        									} else {
	        										val = '';
	        									}
	        								}
	        							});
	        						}else{
	        							val = value; 
	        						}
	        					} 
	        					return val;
	        				}	}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tool_rel'
			})
		},
		fileForm:'',
		fileAdd:function(){
			var idValue = $('#feedbackThemeId').val();
			if(idValue==null || idValue==""){
				$.messager.alert('提示','请先保存主表！','info');
				return;
			}
			fileRel.fileForm = $("#dlg_rel").dialog({
				method:'GET',
			    title: '新增',    
			    width: 400,    
			    height: 200,     
			    href:'${ctx}/feedback/file/create/'+idValue,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#childForm').submit();
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						fileRel.fileForm.panel('close');
					}
				}]
			});
		},
		fileUpdate:function(){
			var row = fileRel.fileList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			fileRel.fileForm = $("#dlg_rel").dialog({
				method:'GET',
			    title: '修改',    
			    width: 700,    
			    height: 400,     
			    href:'${ctx}/feedback/file/update/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#childForm').submit();
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						fileRel.fileForm.panel('close');
					}
				}]
			});
		},
		fileDelete:function(){
			var row = fileRel.fileList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '您确定删除嘛？', function(data){
				if (data){
					$.ajax({
						async:false,
						type:'post',
						url:'${ctx}/feedback/file/delete/'+row.id,
						success: function(data){
							var data = eval('(' + data + ')');
					    	if(data.returnFlag=='success'){
					    		fileRel.fileList.datagrid('reload');
					    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
					    		return true;
						}
					    }
					});
				} 
			});
		}
	};
	$(function() {
		fileRel.initfileList();
		$("#add_rel").on("click",fileRel.fileAdd);
		$("#update_rel").on("click",fileRel.fileUpdate);
		$("#delete_rel").on("click",fileRel.fileDelete);
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				$.easyui.loaded();
				var data = eval('(' + data + ')');
		    	if(data.returnFlag=='success'){
		    		$('#feedbackThemeId').val(data.returnId);
		    		$("#accParentId").val(data.returnId);
		    		fileRel.initfileList();
		    		themeObj.list.datagrid('reload');
		    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
		    		return true;
				}
		    } 
		});
		if('${action}' == 'view') {
			$("#tool_rel").hide();
			$("#toPurchaseListId").hide();
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
		if("${feedbackTheme.closeUser }"!=""){
			$.ajax({
				type:'get',
				async:false,
				url:'${ctx}/baseInfo/baseUtil/userNameShow?userId=${feedbackTheme.closeUser }',
				success:function(data){
					$("#closeUserName").val(data);
				},
				error :function(){
				}
			});
		}
		//select
		$('#discussGroupId').combobox({
			method: "get",
			required:true,
			panelHeight:'auto',
			url:'${ctx}/discuss/group/groupsSelect', 
		    valueField:'id',
		    textField:'groupName',
		    onHidePanel:function(){}
		});
		$('#dutyDept').combotree({
			method:'GET',
		    url: '${ctx}/system/organization/json',
		    idField : 'id',
		    textFiled : 'orgName',
			parentField : 'pid',
			panelHeight:300,
		    animate:true,
		    required:true,
		    onChange:function(newValue,oldValue){
		    	if(newValue!=oldValue){
		    		$('#dutyUser').combobox("clear");
			    	$('#dutyUser').combobox('reload','${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#dutyDept').combotree("getValue"));
		    	}
		    },
		    onHidePanel:function(){}
		});
		$('#dutyUser').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 300,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItemsByOrgId?orgId='+$('#dutyDept').val(),
			valueField : 'id',
			textField : 'name'
		});
	});
</script>

</body>
</html>