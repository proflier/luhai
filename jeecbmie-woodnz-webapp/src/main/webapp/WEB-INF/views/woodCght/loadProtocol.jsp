<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_contract" action="">
	    <input type="text" name="filter_LIKES_hth" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <input type="text" name="filter_LIKES_xyh" class="easyui-validatebox" data-options="prompt: '协议号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a>
	</form>
<table id="contract_dg"></table>
<script type="text/javascript">
	var contract_dg;
	contract_dg=$('#contract_dg').datagrid({    
	method: "get",
	url:'${ctx}/purchase/purchaseAgreement/json?filter_EQS_state=生效', 
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
    columns:[[    
			{field:'id',title: '选择',formatter: 
				function(value, rowData, rowIndex){
                return '<input type="radio" />';
            }},
			{field:'agreementNo',title:'协议号',sortable:true,width:20}, 
			{field:'customer',title:'协议客户',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				}	
			},
			{field:'thirdParty',title:'协议第三方',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				}	
			},
			{field:'company',title:'相关单位',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				}	
			}, 
			{field:'signDate',title:'签订时间',sortable:true,width:20,
				formatter:function(value,row,index){
					if(value == null){
						return ;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}
			},
			{field:'ourUnit',title:'我司单位',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				}	
			},
			{field:'addr',title:'签订地点',sortable:true,width:20},
    ]],
    onClickRow: function(rowIndex, rowData){
    	var oradio = $("input[type='radio']");
    	for(var i=0;i<oradio.length;i++){
    	    if(oradio[i].type == "radio"){
    	       oradio[i].checked = false
    	    }
    	}
     	 var radio = $("input[type='radio']")[rowIndex];
          if(radio.checked==false){
          	$("input[type='radio']")[rowIndex].checked = true;
          }else{
          	$("input[type='radio']")[rowIndex].checked = false;
          }
     },
    sortName:'id',
    sortOrder: 'desc'
	});

//保存
function initContract(){
	var row = contract_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$('#xyh').val(row.agreementNo);
	$('#ghdw').combobox('setValue',row.customer);
	$('#dhdw').combobox('setValue',row.ourUnit);
}

//创建查询对象并查询
function cx_contract(){
	var obj=$("#searchFrom_contract").serializeObject();
	contract_dg.datagrid('reload',obj);
}

function reset_contract(){
	$("#searchFrom_contract")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_contract").serializeObject();
	contract_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>