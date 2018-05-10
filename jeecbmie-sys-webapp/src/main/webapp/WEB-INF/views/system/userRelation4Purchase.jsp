<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="tool">   
<!-- 	<form id="searchFrom_business" action=""> -->
<!-- 	    <input type="text" name="filter_LIKES_innerContractNo_OR_purchaseContractNo_OR_agreementNo_OR_businessManager" class="easyui-validatebox" data-options="width:300,prompt: '请输入内部合同号或采购合同号'"/> -->
<!-- 	    <span class="toolbar-item dialog-tool-separator"></span> -->
<!-- 	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_contract()">查询</a> -->
<!-- 	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_contract()">重置</a> -->
<!-- 	</form> -->
</div>
<table id="business_dg"></table>
<script type="text/javascript">
	var business_dg;
	business_dg=$('#business_dg').datagrid({    
	method: "get",
	url:'${ctx}/purchase/purchaseContract/jsonNoPermission?filter_NES_changeState=0',
	fit : false,
    singleSelect:false,
	fitColumns : true,
	border : false,
	idField : 'id',
	striped:true,
    columns:[[    
			{field:'id',checkbox:true},
			{field:'innerContractNo',title:'内部合同号',sortable:true,width:25},
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:25},
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
			{field:'state',title:'流程状态',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='0'){
							val='作废';
						}else if(value=='1'){
							val='生效';
						}else if(value=='2'){
							val='草稿';
						}else if (value =='3'){
							val='已提交';
						}
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'closeOrOpen',title:'运行状态',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='0'){
							val='关闭';
						}else {
							val='运行中';
						}
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'changeState',title:'变更状态',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='2'){
							val='变更中';
						}else {
							val='非变更';
						}
						return val;
					}else{
						return '';
					}
				}	
			},
    ]],
    onLoadSuccess:function(){
    	var data = eval('${purchaseIds}');
         if(data){
                for(var i = 0;i<data.length;i++){ 
                	business_dg.datagrid("selectRecord", data[i]);
                }
         }
    },
    sortName:'id',
    sortOrder: 'desc',
    selectOnCheck: true,
    checkOnSelect: true,
    toolbar:'#tool'
	});

//保存
function saveRealtion(){
	var rows = business_dg.datagrid('getSelections');
	var row = $('#dg').datagrid('getSelected');
	if(rowIsNull(rows)) return;
	var param= new Array();
    for (var i = 0; i < rows.length; i++) {
    		param.push(rows[i].id);
    }
    $.ajax({
        url: "${ctx}/permission/businessPerssion/saveRealtion4Purchase/"+row.loginName,
        type: "GET",
        data: {
            "ids": param,
        },
        traditional: true,//这里设置为true
        success: function(data) {
            //do sth...
        }
    });
    
}

//创建查询对象并查询
function cx_contract(){
	var obj=$("#searchFrom_business").serializeObject();
	business_dg.datagrid('reload',obj);
}

function reset_contract(){
	$("#searchFrom_business")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_business").serializeObject();
	business_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>