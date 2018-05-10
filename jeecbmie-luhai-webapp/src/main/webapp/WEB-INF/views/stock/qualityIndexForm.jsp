<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>
<body>
<div>
	<form id="qualityIndexForm" action="${ctx}/stock/qualityIndex/${actionIndex}" method="post">
			<table width="98%" class="tableClass">
				<tr>
					<th>指标名称(中)</th>
					<td >
						<input id="indexName" name="indexName"  type="text" value="${qualityIndex.indexName}" class="easyui-validatebox"/>
						<input type="hidden" id="qualityIndexId" name="id" value="${qualityIndex.id}" />
						<input name="qualityInspectionId" type="hidden" value="${qualityIndex.qualityInspectionId}" />
					</td>
				</tr>
				<tr>
					<th>指标名称(英)</th>
					<td >
						<input id="indexNameEn" name="indexNameEn"  type="text" value="${qualityIndex.indexNameEn}" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >检验值</th>
					<td width="30%" ><input id="inspectionValue" name="inspectionValue"  type="text" value="${qualityIndex.inspectionValue}" class="easyui-numberbox" data-options="required:true,precision:'2'"/></td>			
				</tr>
				<tr>	
					<th width="20%" >单位</th>
					<td width="30%" >
						<input id="units" name="units"  type="text" value="${qualityIndex.units}" class="easyui-validatebox" />
					</td>
				</tr>
				
			</table>
	</form>
</div>

<script type="text/javascript">


$(function(){  
	
	$('#qualityIndexForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#qualityIndexForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dgqualityIndex.datagrid('clearSelections');
	    		dgqualityIndex.datagrid('reload');
	    		$('#qualityIndexId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	
	
	
	//单位
	$('#units').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/system/dictUtil/getDictByCode/INDEXUNIT',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    });
	
	
	//指标名称
	$('#indexName').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/baseInfo/lhUtil/goodsIndexItems',
	    valueField:'id',
	    textField:'indexName', 
	    required : true,
	    onSelect:function(record){
// 	    	alert(JSON.stringify(record))
	    	$('#indexNameEn').val(record.indexNameE);
	    	$('#units').combobox('setValue',record.unit);
	    }
	});
	
	
	
});
  
</script>
</body>
</html>

