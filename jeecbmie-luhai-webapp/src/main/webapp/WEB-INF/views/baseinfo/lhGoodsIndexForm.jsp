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
		<form id="mainform"  action="${ctx}/baseInfo/goodsIndex/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品指标信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品指标信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">名称</th>
						<td width="30%">
							<input id="id" name="id" type="hidden"  value="${goodsIndex.id }" />
							<input id="indexName" name="indexName" type="text"  value="${goodsIndex.indexName }" 
							class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							<input id="indexCode" name="indexCode" type="text"  value="${goodsIndex.indexCode }" 
							class="easyui-validatebox" readonly="readonly" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>英文名称</th>
						<td>
							<input id="indexNameE" name="indexNameE" type="text" value="${goodsIndex.indexNameE }"
							class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th>显示顺序</th>
						<td>
							<input id="showOrder" name="showOrder"  value="${goodsIndex.showOrder }"  />
						</td>
					</tr>
					<tr>
						<th>单位</th>
						<td >
							<input id="unit" name="unit" type="text" value="${goodsIndex.unit }" class="easyui-validatebox" />
						</td>
						<th>状态</th>
						<td>
							<input name="status"  value="${goodsIndex.status }"  
								class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '启用'}, {label: '0',value: '停用'}]" />
						</td>
					</tr>
					<tr>
						<th >备注</th>
						<td colspan="3">
							<textarea rows="3" cols="50" id="comments" name="comments" value="${goodsIndex.comments }"></textarea>
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
							<input type="hidden" name="createrNo" value="${goodsIndex.createrNo }"/>
							<input type="hidden" name="createrName" value="${goodsIndex.createrName }"/>
							<input type="hidden" name="createrDept" value="${goodsIndex.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${goodsIndex.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${goodsIndex.createrName }</td>
						<th  >经办部门</th>
						<td>${goodsIndex.createrDept }</td>
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${goodsIndex.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
		    		$('#indexCode').val(data.currnetSequence);
		    	}
	    		$.easyui.loaded();
			} 
		});
		//单位
		$('#unit').combobox({
				panelHeight : 'auto',
				required : true,
				url : '${ctx}/system/dictUtil/getDictByCode/INDEXUNIT',
				valueField : 'code',
				textField : 'name'
			});
	});
	
</script>

</body>
</html>