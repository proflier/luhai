<%@ page import="com.cbmie.lh.feedback.entity.FeedbackTheme"%>
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
<!--引入引入kindeditor编辑器相关文件--> 
<link rel="stylesheet" href="${ctx}/static/plugins/kindeditor/themes/default/default.css" />
<script src="${ctx}/static/plugins/kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/kindeditor/lang/zh-CN.js" type="text/javascript"></script>
</head>

<body>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '反馈主题', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>反馈主题</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">主题</th>
						<td colspan="3" >
							<input id="feedbackThemeId" name="id" type="hidden"  value="${feedbackTheme.id }" />
							${feedbackTheme.title }
						</td>
					</tr>
					<tr>
						<th width="20%">反馈分类</th>
						<td width="30%" >
							${fns:getDictLabelByCode(feedbackTheme.classification,'feedbackClassification','')}
						</td>
						<th width="20%">反馈类型</th>
						<td width="30%" >
							${fns:getDictLabelByCode(feedbackTheme.types,'feedbackTypes','')}
						</td>
					</tr>
					<tr>
						<th width="20%">状态</th>
						<td width="30%" >
							${fns:getDictLabelByCode(feedbackTheme.state,'feedbackState','')}
						</td>
						<th width="20%">重要性</th>
						<td width="30%" >
							${fns:getDictLabelByCode(feedbackTheme.importance,'feedbackImportance','')}
						</td>
					</tr>
					<tr>
						<th width="20%">反馈对象类型</th>
						<td width="30%" >
							${fns:getDictLabelByCode(feedbackTheme.feedbackObjectType,'feedbackObjectType','')}
						</td>
						<th width="20%">讨论组</th>
						<td width="30%" id="themeDiscussGroupId">
						</td>
					</tr>
					<tr>
						<th width="20%">反馈对象</th>
						<td width="30%" >
							${feedbackTheme.feedbackObject }
						</td>
						<th width="20%">公共反馈</th>
						<td width="30%" >
							${feedbackTheme.feedbackPublic eq '0'?'否':'是'}
						</td>
					</tr>
					<tr>
						<th width="20%">部门</th>
						<td width="30%" id="themeDutyDept">
						</td>
						<th width="20%">负责员工</th>
						<td width="30%" id="themeDutyUser">
						</td>
					</tr>
					<tr>
						<th width="20%">解决期限</th>
						<td width="30%" >
						 	<fmt:formatDate value="${feedbackTheme.terminalDate}" pattern="yyyy-MM-dd"/>
						</td>
						<th width="20%">关闭人员</th>
						<td width="30%" >
						 	${feedbackTheme.closeUser }
						</td>
					</tr>
					<tr>
						<th width="20%">创建人</th>
						<td width="30%" >
							${feedbackTheme.createrName }
						</td>
						<th width="20%">创建时间</th>
						<td width="30%" >
							<fmt:formatDate value="${feedbackTheme.createDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr>
						<th width="20%">修改人</th>
						<td width="30%" >
							${feedbackTheme.updaterName }
						</td>
						<th width="20%">修改时间</th>
						<td width="30%" >
							<fmt:formatDate value="${feedbackTheme.updateDate}" pattern="yyyy-MM-dd"/>
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
						</td>
					</tr>
					<tr>
						<th>描述</th>
						<td colspan="3" style="height:1cm">
							${feedbackTheme.description}
						</td>
					</tr>
					<tr>
						<th>处理结果</th>
						<td colspan="3" style="height:1cm">
							${feedbackTheme.dealResult}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>附件</legend>
				<div id="themeAttach"  class="tableClass">
					<script type="text/javascript">
					$('#themeAttach').datagrid({
						method : "get",
						fit : false,
						fitColumns : true,
						border : false,
						striped : true,
						singleSelect : true,
						scrollbarSize : 0,
						url : "${ctx}/accessory/json?filter_EQS_accParentId=${feedbackTheme.id}&filter_EQS_accParentEntity=com_cbmie_lh_feedback_entity_FeedbackTheme",//
						idField : 'accId',
						columns : [[
							{
								field : 'accRealName',
								title : '附件名称',
								sortable : true,
								width : 40
							},
							{
								field : 'accId',
								title : '操作',
								sortable : true,
								width : 20,
								formatter : function(value, row, index) {
									var str = "<a style='text-decoration:none' href='#' onclick='downnloadAcc(" + value + ");'>下载</a>"
									return str;
								}
							} 
						]]
					})
					</script>
				</div>
				</fieldset>
				<fieldset class="fieldsetClass" id="relField">
					<legend>归档列表</legend>
					<div style="width:98%;border:1px;" >
						<table id="fileList" width="98%" class="tableClass"></table>
					</div>
				</fieldset>
			</div>
		</div>	
		<fieldset class="fieldsetClass" >
			<legend>回馈</legend>
			<div id="tool_content" style="padding:5px;height:auto;">
				<c:if test="${feedbackTheme.state ne '10990003'}">
					<div>	
						<a id="add_content" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">反馈意见</a>
						<!-- <span class="toolbar-item dialog-tool-separator"></span> -->
			     	</div>
				</c:if>
			</div>
			<div style="width:98%;border:1px;" >
				<table id="contentList" width="98%" class="tableClass" data-options="nowrap:false"></table>
			</div>
		</fieldset>
<script type="text/javascript">
//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}
var fileRel = {
		fileList:{},
		initfileList:	function(){
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
	        				}
	          			}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			})
		}
	};
	var contentList = {
			fileList:{},
			initfileList:	function(){
				contentList.fileList=$('#contentList').datagrid({
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
		                    		return time.format("yyyy-MM-dd HH:mm");
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
				    toolbar:'#tool_content'
				})
			},
			fileForm:'',
			fileAdd:function(){
				var idValue = $('#feedbackThemeId').val();
				contentList.fileForm = $("#dlg_rel").dialog({
					method:'GET',
				    title: '新增',    
				    width: 900,    
				    height: 400,     
				    href:'${ctx}/feedback/content/create/'+idValue,
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
							contentList.fileList.datagrid('reload');
							contentList.fileForm.panel('close');
						}
					}]
				});
			}
	}
	$(function() {
		fileRel.initfileList();
		contentList.initfileList();
		$("#add_content").on("click",contentList.fileAdd);
		if('${feedbackTheme.dutyUser }'!="" && '${feedbackTheme.dutyUser }'!=null){
			$.ajax({
				type:'GET',
				url:"${ctx}/baseInfo/baseUtil/userNameShow?userId=${feedbackTheme.dutyUser }",
				success: function(data){
					$("#themeDutyUser").html(data);
				}
			});
			$.ajax({
				type:'GET',
				url:"${ctx}/system/user/getDeptName/${feedbackTheme.dutyUser }",
				success: function(data){
					$("#themeDutyDept").html(data);
				}
			});
		}
		if('${feedbackTheme.discussGroupId }'!=null && '${feedbackTheme.discussGroupId }'!=""){
			$.ajax({
				type:'GET',
				url:"${ctx}/discuss/group/groupName/${feedbackTheme.discussGroupId }",
				success: function(data){
					$("#themeDiscussGroupId").html(data);
				}
			});
		}
	});
</script>

</body>
</html>