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
	    <input type="text" name="interContractNo" class="easyui-validatebox" data-options="width:150,prompt: '综合合同号'"/>
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
	var profitDetailsReport = {
		dataGrid: {},
		init: function() {
			$("#search").click(profitDetailsReport.getData);
			$("#reset").click(profitDetailsReport.resetForm);
			$("#viewSelections").click(profitDetailsReport.getSelections);
			dataGrid = $("#pdrTable").datagrid({
				method: "get",
			    url:'${ctx}/report/profitDetails/data', 
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
					//{field:'id',title:'id',rowspan:2,hidden:false,width:25},
					{field:'goodType',title:'商品类别',rowspan:2,sortable:true,width:25,
						formatter: function(value,row,index){
							if(value == '01') {
								return '板材';
							} else if(value == '02'){
								return '原木';
							} else {
								return value;
							}
						}},
					{field:'goodName',title:'商品名称',rowspan:2,sortable:true,width:25,
							formatter: function(value,row,index){
								if(value == '01') {
									return '板材';
								} else if(value == '02'){
									return '原木';
								} else {
									return value;
								}
							}},
					{field:'contract',title:'综合合同号',rowspan:2,sortable:true,width:25},
					{field:'tradeType',title:'贸易类型',rowspan:2,sortable:true,width:25,
						formatter: function(value,row,index){
							var val;
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/downMenu/getDictName/htlb/' + value,
								success: function(data){
									val = data;
								}
							});
							return val;
						}},
					{field:'tradeWay',title:'贸易方式',rowspan:2,sortable:true,width:25},
					{field:'unit',title:'实物量单位',rowspan:2,sortable:true,width:25,
						formatter: function(value,row,index){
							var val;
							$.ajax({
								type:'GET',
								async: false,
								url : '${ctx}/system/downMenu/getDictName/sldw/' + value,
								success: function(data){
									val = data;
								}
							});
							return val;
						}},
					{field:'salesQuantity',title:'销售数量',align:'right',rowspan:2,sortable:true,width:25,
						formatter: function(val,rowData,rowIndex){
					        if(Number(val)!=null)
					        	return Number(val).toFixed(2);
				   		}},
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
					{field:'income',title:'主营业务收入',align:'right',rowspan:2,sortable:true,width:25,
						formatter: function(val,rowData,rowIndex){
						    if(Number(val)!=null)
						        return Number(val).toFixed(2);
					   	}},
					{title:'主营业务成本',align:'center',colspan:2},
					{field:'profit',title:'商品利润',align:'right',rowspan:2,sortable:true,width:25,
						formatter: function(val,rowData,rowIndex){
						    if(Number(val)!=null)
						        return Number(val).toFixed(2);
					   	}}
				],[
					{field:'price',title:'进价',align:'right',sortable:true,width:25,
						formatter: function(val,rowData,rowIndex){
						    if(Number(val)!=null)
						        return Number(val).toFixed(2);
					   	}},
					{field:'transpot',title:'运杂费',align:'right',sortable:true,width:25,
						formatter: function(val,rowData,rowIndex){
							if(Number(val)!=null)
							    return Number(val).toFixed(2);
						}}
				]],
				remoteSort:false,
				multiSort:false,
			    //sortName:'a.cp_contract_no',
			    //sortOrder: 'desc',
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
	window.profitDetailsReport = (window.profitDetailsReport) ? window.profitDetailsReport : profitDetailsReport;
	$(function(){
		profitDetailsReport.init();
	});
})(window,jQuery);

</script>
</body>
</html>