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
		<form id="mainform"  action="${ctx}/permission/group/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '讨论组', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>讨论组信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">组名</th>
						<td width="30%">
							<input id="permissionGroupId" name="id" type="hidden"  value="${permissionGroup.id }" />
							<input id="groupName" name="groupName" type="text"  value="${permissionGroup.groupName }"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" id="relField">
					<legend>组员列表</legend>
					<div style="width:98%;border:1px;" >
						<div id="tool_rel" style="padding:5px;height:auto;">
						     <div>	
								<a id="add_rel" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">批量设置人员</a>
						     </div>
						</div>
						<table id="memberList" width="98%" class="tableClass"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${permissionGroup.createrNo }"/>
							<input type="hidden" name="createrName" value="${permissionGroup.createrName }"/>
							<input type="hidden" name="createrDept" value="${permissionGroup.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${permissionGroup.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${permissionGroup.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${permissionGroup.createrName }
						</td>
						<th  >登记部门</th>
						<td>${permissionGroup.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${permissionGroup.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${permissionGroup.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
var memberRel = {
		memberList:{},
		initmemberList:	function(){
			if(($("#permissionGroupId").val())==null ||($("#permissionGroupId").val())==""){
				return;
			} 
			memberRel.memberList=$('#memberList').datagrid({
				method: "get",
				url: '${ctx}/permission/member/json?filter_EQL_permissionGroupId='+$("#permissionGroupId").val(),
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				pagination:true,
				rownumbers:true,
				pageNumber:1,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				rownumbers:true,
				extEditing:false,
			    columns:[[ 
					{field:'id',title:'',hidden:true},
					{field:'name',title:'人员名称',width:20,
						formatter: function(value,row,index){
							return row.user.name;
						}},  
					{field:'loginName',title:'人员账号',width:20,
							formatter: function(value,row,index){
								return row.user.loginName;
							}}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tool_rel'
			})
		},
		memberForm:'',
		memberAdd:function(){
			var idValue = $('#permissionGroupId').val();
			if(idValue==null || idValue==""){
				$.messager.alert('提示','请先保存主表！','info');
				return;
			}
			memberRel.memberForm = $("#dlg_rel").dialog({
				method:'GET',
			    title: '添加人员',    
			    width: 700,    
			    height: 400,     
			    href:'${ctx}/permission/member/add/'+idValue,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						var users_all = getSelectedUsers();
						memberRel.memberForm.panel('close');
						memberRel.memberUpdate(users_all);
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						memberRel.memberForm.panel('close');
					}
				}]
			});
		},
		memberUpdate:function(allUsers){
			$.ajax({
				async:false,
				type:'post',
				contentType:'application/json;charset=utf-8',
				url:'${ctx}/permission/member/batchUpdate/'+$('#permissionGroupId').val(),
				data:JSON.stringify(allUsers),
				success: function(data){
					var data = eval('(' + data + ')');
			    	if(data.returnFlag=='success'){
			    		memberRel.memberList.datagrid('reload');
			    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
			    		return true;
				}
			    }
			});
		}/* ,
		memberSet:function(){
			var rows = memberRel.memberList.datagrid('getChecked');
			if(rowIsNull(rows)) return;
			var memberIds = new Array();
			for(var i=0;i<rows.length;i++){
				memberIds.push(rows[i].id);
			}
			parent.$.messager.confirm('提示', '您确定设置关键人员？', function(data){
				if (data){
					$.ajax({
						async:false,
						type:'post',
						contentType:'application/json;charset=utf-8',
						url:'${ctx}/permission/member/batchSet/'+$('#permissionGroupId').val(),
						data:JSON.stringify(memberIds),
						success: function(data){
							var data = eval('(' + data + ')');
					    	if(data.returnFlag=='success'){
					    		memberRel.memberList.datagrid('reload');
					    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
					    		return true;
						}
					    }
					});
				} 
			});
		} */
	};
	$(function() {
		memberRel.initmemberList();
		$("#add_rel").on("click",memberRel.memberAdd);
		//$("#set_rel").on("click",memberRel.memberSet);
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
		    		$('#permissionGroupId').val(data.returnId);
		    		memberRel.initmemberList();
		    		groupObj.list.datagrid('reload');
		    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
		    		return true;
				}else{
					parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
				}
		    } 
		});
		if('${action}' == 'view') {
			$("#tool_rel").hide();
			$("#mainform").find('input').attr("readonly",true);
		}
	});
</script>

</body>
</html>