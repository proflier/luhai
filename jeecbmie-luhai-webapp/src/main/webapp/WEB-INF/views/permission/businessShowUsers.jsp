<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<table width="98%" class="tableClass" >
		<tr>
			<th width="20%">相关业务人员
				<input type="hidden" id="themeMemberIds" name="themeMemberIds" value="${themeMemberIds}"/>
				<input type="hidden" id="themeMemberKeyIds" name="themeMemberKeyIds" value="${themeMemberKeyIds}"/>
			</th>
			<td width="80%" colspan="3" style="height:1cm" >
				<div style="width: 90%;display: block;float: left;">
<!-- 					<div> -->
<!-- 						<span style="font-weight: bold;">关键人员:</span> -->
<%-- 						<span id="themeMemberKeys" >${currentSelectUsers.themeMemberKeys}</span> --%>
<!-- 					</div> -->
					<div>
						&nbsp;
						<span id="themeMembers">${themeMembers}</span>
					</div>
				</div>
				<div id="businessShowBlock" style="width: 8%;display: none;float: left;">
					<a id="" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="selectBusinessers();">选择人员</a>
				</div>
			</td>
		</tr>
	</table>
<script type="text/javascript">
if('${action}'!='view'){
	$("#businessShowBlock").show();
}
function selectBusinessers(){
	var themeMemberIds = $("#themeMemberIds").val();
	var themeMemberKeyIds = $("#themeMemberKeyIds").val();
	var themeMembers = $("#themeMembers").text();
	var themeMemberKeys = $("#themeMemberKeys").text();
	//todo
	var selectUsers_dg=$("#dlg_selectUsers").dialog({   
	    title: '人员选择',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/permission/businessPerssion/businessSelectUsers/',
	    modal:true,
	    queryParams:{'themeMemberIds':themeMemberIds,'themeMemberKeyIds':themeMemberKeyIds,'themeMembers':themeMembers,'themeMemberKeys':themeMemberKeys},
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				users_show.saveSelect();
				selectUsers_dg.panel('close');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				selectUsers_dg.panel('close');
			}
		}]
	});
}
</script>