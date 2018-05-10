<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<form id="childForm" action="${ctx}/feedback/file/${actionFile}" method="post">
			<input type="hidden" id="feedbackFileId" name="id" value="${feedbackFile.id}" />
			<input name="themeId" type="hidden" value="${feedbackFile.themeId}" />
			<table style="width:98%;" class="tableClass">
				<tr>
					<th width="20%" >类别</th>
					<td width="30%" >
						<input type="text" id="fileTypes" name="fileTypes" value="${feedbackFile.fileTypes}" class="easyui-combobox">
						<%-- <mytag:combobox name="fileTypes" value="${feedbackFile.fileTypes}" type="dict" parameters="feedbackFileTypes" functionName="listFileTarget"/> --%>
					</td>
				</tr>
				<tr>
					<th width="20%" >目标</th>
					<td width="30%" >
						<input id="fileTarget" name="fileTarget"  type="text" value="${feedbackFile.fileTarget}" class="easyui-validatebox"/>
					</td>
				</tr>
			</table>
	</form>
</div>

<script type="text/javascript">
function listFileTarget(){
	//
	var file_types = $('#fileTypes').combobox('getValue');
	if(file_types==null || file_types==""){
		$('#fileTarget').combogrid({url:null});
		return;
	}
	if(file_types=="11020001" || file_types=="11020004" || file_types=="11020002"){
		var customer;
		//客户
		if(file_types=="11020001"){
			customer = "^([0-9]*,)*3(,[0-9]*)*$";
		}else if(file_types=="11020004"){ //供应商
			customer = "^([0-9]*,)*2(,[0-9]*)*$";
		}else{
			customer = "^([0-9]*,)*[^2-3](,[0-9]*)*$";
		}
		
		$('#fileTarget').combogrid({    
		    panelWidth:600,    
		    method: "get",
		    idField:'customerCode',    
		    textField:'customerName',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    url:'${ctx}/baseInfo/baseUtil/regexpBaseInfo/'+customer, 
		    columns:[[    
					{field:'id',title:'id',hidden:true},  
					{field:'customerCode',title:'客户编码',sortable:true,width:20},  
					{field:'customerName',title:'客户名称',sortable:true,width:40},
					{field:'customerEnName',title:'英文名称',sortable:true,width:30},
					{field:'businessScope',title:'业务范围',sortable:true,width:20},
					{field:'contactPerson',title:'联系人',sortable:true,width:15},
					{field:'createDate',title:'创建时间',sortable:true,width:25}
		    ]]
		}); 
	}else if(file_types=="11020005"){ //sale
		$('#fileTarget').combogrid({    
		    panelWidth:550,    
		    method: "get",
		    idField:'contractNo',    
		    textField:'contractNo',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    url:'${ctx}/sale/saleContract/jsonNoPermission?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1', 
		    columns:[[    
		  			{field:'id',title:'id',hidden:true},  
		  			{field:'contractNo',title:'合同号',sortable:true,width:40},
		  			{field:'sellerView',title:'卖方',sortable:true,width:40},
		  			{field:'purchaserView',title:'买方',sortable:true,width:40}
		  	    ]]
		}); 
	}else if(file_types=="11020006"){ //purchase
		$('#fileTarget').combogrid({    
		    panelWidth:900,    
		    method: "get",
		    idField:'purchaseContractNo',    
		    textField:'purchaseContractNo',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    url:'${ctx}/purchase/purchaseContract/jsonNoPermission?filter_EQS_state=1&filter_EQS_closeOrOpen=1&filter_EQS_changeState=1',
		    columns:[[    
		  			{field:'id',title:'id',hidden:true},  
		  			{field:'innerContractNo',title:'内部合同号',sortable:true,width:30},
		  			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
		  			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:40},
		  			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
		  			{field:'createrDept',title:'申请部门',sortable:true,width:10},
		  			{field:'businessManagerView',title:'业务经办人',sortable:true,width:8},
		  			{field:'createDate',title:'创建时间',sortable:true,width:15}
		  	    ]]
		}); 
	}else{
		$('#fileTarget').combogrid("load",{});
	}
}

$(function(){  
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
	    		fileRel.fileList.datagrid('reload');
	    		$('#feedbackFileId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		fileRel.fileForm.panel('close');
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	//select
	$('#fileTypes').combobox({
		method: "get",
		required:true,
		panelHeight:'auto',
		url:'${ctx}/system/dictUtil/getDictByCode/feedbackFileTypes', 
	    valueField:'code',
	    textField:'name',
	    onChange:function(){
	    	listFileTarget();
	    },
	    onLoadSuccess:function(){
	    	listFileTarget();
	    },
	    onHidePanel:function(){}
	});
});
</script>
</body>
</html>

