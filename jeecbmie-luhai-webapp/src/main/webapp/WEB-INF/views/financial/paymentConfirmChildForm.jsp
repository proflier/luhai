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
	<form id="childForm" action="${ctx}/financial/paymentConfirmChild/${actionChild}" method="post">
			<input type="hidden" id="paymentConfirmChildId" name="id" value="${paymentConfirmChild.id}" />
			<input type="hidden" id="relLoginNames" name="relLoginNames" value="${paymentConfirmChild.relLoginNames }"/>
			<input name="paymentConfirmId" type="hidden" value="${paymentConfirmChild.paymentConfirmId}" />
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%" >费用类别</th>
					<td width="30%" >
						<mytag:combobox name="feeCategory" value="${paymentConfirmChild.feeCategory}" type="dict" parameters="feeCategory" required="false" disabled="true"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >分摊</th>
					<td width="30%" >
					<input id="paymentType" name="paymentType"  type="text" value="${paymentConfirmChild.paymentType}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >摘要</th>
					<td width="30%" >
						<input id="code" name="code"  type="text" value="${paymentConfirmChild.code}" class="easyui-combogrid"/>
					</td>
				</tr>
				<tr>
					<th width="20%" >分摊金额</th>
					<td width="30%" >
						<input id="shareAmount" name="shareAmount"  type="text" value="<fmt:formatNumber value="${paymentConfirmChild.shareAmount}" pattern="#.00"/>" class="easyui-validatebox" data-options="required:true"/>
					</td>
				</tr>
			</table>
	</form>
</div>
<script type="text/javascript">


$(function(){  
	
	$('#childForm').form({
	    onSubmit: function(){    
	    	var isValid = $('#childForm').form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dgChild.datagrid('clearSelections');
	    		dgChild.datagrid('reload');
	    		$('#paymentConfirmChildId').val(data.returnId);
	    		initStreamContainer();
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	
	$('#paymentType').combobox({
		valueField: 'label',
		textField: 'value',
		panelHeight:'auto',
		data: [{label: 'ship',value: '船编号'}, 
		       {label: 'sale',value: '销售合同'},
		       {label: 'kehu',value: '客户'}],
       onSelect:function (value){
			if("ship"==value.label){
				loadShip();
			}else if("sale"==value.label) {
				loadSale();
			}else if("kehu"==value.label){
				loadKehu();
			}
			$('#code').combogrid('clear');
		}
	});
	
	$('#code').combogrid({required : true});
	
	var payContent = $('#payContent').combobox('getValue');
	
	$.ajax({
			type:'GET',
			url:'${ctx}/financial/paymentConfirmChild/getPaymentRelation/'+payContent,
		 	dataType: "json",
			success: function(data){
				if(data.hasArrow!='1'){
					$('#paymentType').combobox('disable');
				}
				if(data.actionResource=="ship"){
					if('create'=='${actionChild}'){
						loadShip();
					}else{
						loadCurrent();
					}
		    	}else if(data.actionResource=="sale"){
		    		if('create'=='${actionChild}'){
			    		loadSale();
		    		}else{
						loadCurrent();
					}
		    	}else if(data.actionResource=="kehu"){
		    		if('create'=='${actionChild}'){
			    		loadKehu();
		    		}else{
						loadCurrent();
					}
		    	}
			}
		});
	
});

function loadSale(){
	$('#feeCategory').combobox('setValue','11090002');
	$('#paymentType').combobox('setValue','sale');
	$('#code').combogrid({    
	    panelWidth:500,    
	    method: "get",
	    idField:'contractNo',    
	    textField:'contractNo',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
		url:'${ctx}/sale/saleContract/json?filter_EQS_state=1&filter_EQS_closedFlag=0&filter_EQS_changeState=1', 
	    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'contractNo',title:'合同号',sortable:true,width:20},
				{field:'sellerView',title:'卖方',sortable:true,width:20},
				{field:'purchaserView',title:'买方',sortable:true,width:20}
	    ]],
	    onSelect:function(rowIndex, rowData){
	    	$('#relLoginNames').val(rowData.relLoginNames)
	    }
	});
}

function loadShip(){
	$('#feeCategory').combobox('setValue','11090001');
	$('#paymentType').combobox('setValue','ship');
	$('#code').combogrid({    
	    panelWidth:600,
	    method: "get",
	    idField:'shipNo',    
	    textField:'shipNo',
	    required : true,
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
	    url:'${ctx}/logistic/shipTrace/json', 
	    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'shipNo',title:'船编号',width:10},
				{field:'shipName',title:'船中文名',width:10},
				{field:'shipNameE',title:'船英文名',width:10},
				{field:'innerContractNo',title:'内部合同号',sortable:true,width:20},
				{field:'loadPortView',title:'装港港口',sortable:true,width:15,
// 					formatter: function(value,row,index){
// 						var val;
// 						if(value!=''&&value!=null){
// 							$.ajax({
// 								type:'GET',
// 								async: false,
// 								url:"${ctx}/baseInfo/baseUtil/getPortNameByCode/"+value,
// 								success: function(data){
// 									if(data != null){
// 										val = data;
// 									} else {
// 										val = '';
// 									}
// 								}
// 							});
// 							return val;
// 						}else{
// 							return '';
// 						}
// 					}
				},
	    ]],
	    onSelect:function(rowIndex, rowData){
	    	$('#relLoginNames').val(rowData.relLoginNames)
	    }
	}); 
}
function loadKehu(){
	$('#feeCategory').combobox('setValue','11090002');
	$('#paymentType').combobox('setValue','kehu');
	$('#code').combogrid({    
	    panelWidth:450,    
	    method: "get",
	    idField:'customerCode',    
	    textField:'customerName',
	    fit : false,
	    singleSelect:true,
		fitColumns : true,
	    url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=10230003',
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'customerName',title:'客户名称',sortable:true,width:20},
			{field:'customerCode',title:'客户编码',sortable:true,width:20},  
			{field:'customerEnName',title:'英文名称',sortable:true,width:15},
	    ]],
	    onSelect:function(rowIndex, rowData){
	    	$.ajax({
				type:'GET',
				url:'${ctx}/system/customerPerssion/getUserLoginsByCode/'+rowData.customerCode,
				success: function(data){
						$('#relLoginNames').val(data)
					}
				});
	    	
	    }
	});
}
function loadCurrent(){
	var currentValue = $('#paymentType').combobox('getValue');
	if("ship"==currentValue){
		loadShip();
	}else if("sale"==currentValue) {
		loadSale();
	}else if("kehu"==currentValue){
		loadKehu();
	}
}
  
</script>
</body>
</html>

