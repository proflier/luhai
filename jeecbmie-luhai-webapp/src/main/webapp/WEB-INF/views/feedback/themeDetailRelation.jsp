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
						<table id="fileList" width="98%" class="tableClass"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" id="relField">
					<legend>反馈内容</legend>
					<div style="width:98%;border:1px;" >
						<table id="contentList" width="98%" class="tableClass"></table>
					</div>
				</fieldset>
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
$('#fileList').datagrid({
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
			});
$('#contentList').datagrid({
	method: "get",
	url: '${ctx}/feedback/content/json?filter_EQL_feedbackThemeId='+$("#feedbackThemeId").val(),
  	fit : false,
	fitColumns : true,
	scrollbarSize : 0,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	extEditing:false,
    columns:[[ 
		{field:'deptId',title:'部门',width:10,
			formatter: function(value,row,index){
				var val;
				if(row.userId!=''&&row.userId!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/user/getDeptName/"+row.userId,
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
		{field:'userId',title:'用户',width:10,
  				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/baseInfo/baseUtil/userNameShow?userId="+value,
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
				}	},
  			{field:'publishDate',title:'发表时间',width:15,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}},
        	{field:'content',title:'发表内容',width:50,
            		rowStyler: function(index,row){
            			return 'overflow:hidden;'; 
            		}
            },
        	{field:'id',title:'附件',width:20,
        		formatter:function(value,row,index){
        			var val="";
        			$.ajax({
  						type:'GET',
  						async: false,
  						dataType:'json',
  						url:'${ctx}/accessory/listByPidE/'+value+'/com_cbmie_lh_feedback_entity_FeedbackContent',
  						success: function(data){
  							if(data != null){
  								for(var i=0;i<data.length;i++){
  									val+="<a style='text-decoration:none' href='#' onclick='downnloadAcc(" + data[i].accId + ");'>"+data[i].accRealName+"</a>";
  									 if(i+1<data.length){
  										val+="<br/>";
  									} 
  								}
  							} 
  						}
  					});
        			return val;
            	}}
    ]],
    sortName:'id',
    sortOrder:'desc',
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
});
	
</script>

</body>
</html>