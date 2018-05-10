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
		<form id="mainform"  action="${ctx}/baseInfo/signet/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品类别信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品类别信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">印章类型</th>
						<td width="30%">
							<input id="id" name="id" type="hidden"  value="${signet.id }" />
							<input id="typeCode" name="typeCode" type="text"  value="${signet.typeCode }"/>
							<input id="typeName" name="typeName" type="hidden"  value="${signet.typeName }"/>
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							<input id="signetCode" name="signetCode" type="text"  value="${signet.signetCode }" 
							class="easyui-validatebox" readonly="readonly" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>所属公司</th>
						<td>
							<input id="orgId" name="orgId" type="text" value="${signet.orgId }"/>
						</td>
						<th>印章保管人</th>
						<td>
							<input id="saveUserId" name="saveUserId" type="text"  value="${signet.saveUserId }"   />
							<input id="saveUserName" name="saveUserName" type="hidden"  value="${signet.saveUserName }"   />
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td colspan="3">
							<input name="status"  value="${signet.status }"  
								class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '启用'}, {label: '0',value: '停用'}]" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >经办人</th>
						<td>
							<input type="hidden" name="createrNo" value="${signet.createrNo }"/>
							<input type="hidden" name="createrName" value="${signet.createrName }"/>
							<input type="hidden" name="createrDept" value="${signet.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${signet.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${signet.createrName }</td>
						<th  >经办部门</th>
						<td>${signet.createrDept }</td>
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${signet.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
	$(function() {
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
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#signetCode').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
			} 
		});
		//印章类别
		$('#typeCode').combobox({
				panelHeight : 'auto',
				required : true,
				url : '${ctx}/system/dictUtil/getDictByCode/SIGNETTYPE',
				valueField : 'code',
				textField : 'name',
				onSelect:function(data){
					$('#typeName').val(data.name);
				} 
			});
		//公司
		$('#orgId').combobox({
				panelHeight : 'auto',
				required : true,
				method : 'get',
				url : '${ctx}/baseInfo/baseUtil/companyItems',
				valueField : 'id',
				textField : 'orgName'
			});
		//
		$('#saveUserId').combobox({
			panelHeight : 'auto',
			required : true,
			panelHeight : 160,
			method : 'get',
			url : '${ctx}/baseInfo/baseUtil/userItems',
			valueField : 'id',
			textField : 'name',
			onSelect:function(data){
				$('#saveUserName').val(data.name);
			} 
		});
	});
</script>

</body>
</html>