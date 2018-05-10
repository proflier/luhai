<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<form id="searchFrom" action="">
	    <input type="text" name="cpContractNo" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a id="search" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
	    <a id="reset" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">重置</a>
	    <!--  
	    <a id="viewSelections" href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo">查看选择</a>
	    -->
	</form>
</div>
<table id="pdrTable"></table>
<script type="text/javascript">
(function(window,$){
	var payableAccount = {
		dataGrid: {},
		init: function() {
			$("#search").click(payableAccount.getData);
			$("#reset").click(payableAccount.resetForm);
			$("#viewSelections").click(payableAccount.getSelections);
			dataGrid = $("#pdrTable").datagrid({
				method: "get",
			    url:'${ctx}/account/payableAccount/data', 
			    fit : true,
				fitColumns : true,
				border : false,
				striped:true,	
				scrollbarSize : 0,
				idField : 'id',
				pagination:true,
				rownumbers:true,
				pageNumber:1,
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:false,
			    columns:[[ 
					{field:'customername',title:'客户名称',rowspan:2,sortable:true,width:25},					 
					{field:'days',title:'账龄',rowspan:2,sortable:true,width:25},					 
					{field:'currency',title:'币种',rowspan:2,sortable:true,width:25,
						formatter: function(value,row,index){
							var val;
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/downMenu/getDictName/CURRENCY/' + value,
								success: function(data){
									val = data;
								}
							});
							return val;
						}},
					{field:'submoney',title:'余额',rowspan:2,sortable:true,width:25},
					{field:'rmbmoney',title:'人民币余额',rowspan:2,sortable:true,width:25},
					{field:'usmoney',title:'欧元余额',rowspan:2,sortable:true,width:25},
					{field:'nzmoney',title:'纽币余额',rowspan:2,sortable:true,width:25},
					{field:'saleContractNo',title:'销售合同号',rowspan:2,sortable:true,width:25},
					{field:'cpContractNo',title:'综合合同号',rowspan:2,sortable:true,width:25}					 
				]],
				remoteSort:false,
				multiSort:false, 
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    showFooter: true,
			    toolbar:'#tb'
			});
		},
		getData: function() {
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj);
		}, 
		resetForm: function() {
			$("#searchFrom")[0].reset();
			var obj=$("#searchFrom").serializeObject();
			dataGrid.datagrid('reload',obj); 
		},
        getSelections: function(){
            var ss = [];
            var rows = dataGrid.datagrid('getSelections');
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                ss.push('<span>'+row.id+"--"+row.contract+"--"+row.goodName+'</span>');
            }
            $.messager.alert('Info', ss.join('<br/>'));
        }
	};
	window.payableAccount = (window.payableAccount) ? window.payableAccount : payableAccount;
	$(function(){
		payableAccount.init();
	});
})(window,jQuery);

</script>
</body>
</html>