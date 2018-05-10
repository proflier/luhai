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
		<form id="mainform"  action="${ctx}/baseInfo/goodsType/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品类别信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品类别信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">名称</th>
						<td width="30%">
							<input id="id" name="id" type="hidden"  value="${goodsType.id }" />
							<input id="typeName" name="typeName" type="text"  value="${goodsType.typeName }" 
							class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							<input id="typeCode" name="typeCode" type="text"  value="${goodsType.typeCode }" 
							class="easyui-validatebox" readonly="readonly" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<!-- 
						<th>对应财务编码</th>
						<td>
							<input id="financeCode" name="financeCode" type="text" value="${goodsType.financeCode }"/>
						</td>
						 -->
						<th>状态</th>
						<td colspan="3">
							<input name="status"  value="${goodsType.status }"  
							class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '启用'}, {label: '0',value: '停用'}]" />
						</td>
					</tr>
					<tr>
						<th >备注</th>
						<td colspan="3">
							<textarea rows="3" cols="50" id="comments" name="comments" value="${goodsType.comments }"></textarea>
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
							<input type="hidden" name="createrNo" value="${goodsType.createrNo }"/>
							<input type="hidden" name="createrName" value="${goodsType.createrName }"/>
							<input type="hidden" name="createrDept" value="${goodsType.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${goodsType.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${goodsType.createrName }</td>
						<th  >经办部门</th>
						<td>${goodsType.createrDept }</td>
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${goodsType.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
		    		$('#typeCode').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
			} 
		});
	});
</script>

</body>
</html>