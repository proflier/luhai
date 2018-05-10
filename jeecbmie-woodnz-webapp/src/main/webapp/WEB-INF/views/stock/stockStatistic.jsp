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
		<table width="70%" class="tableClass">
			<tr>
				<th><font style="vertical-align:middle;color:red" ></font>品名</th>
				<td>
					<select class="easyui-combobox" id="filter_EQS_goodsType" name="filter_EQS_goodsType" data-options="required:true" style="width:177px;">
						<option value="01" selected>板材</option>
						<option value="02" >原木</option>
						<option value="03" >其他</option>
					</select>
				</td>
				<th><font style="vertical-align:middle" ></font>仓库</th>
				<td colspan="2"><input name="filter_LIKES_warehouseName" class="easyui-validatebox" type="text"/></td>
			</tr>
			<tr>
				<th><font style="vertical-align:middle" ></font>商品编码</th>
				<td><input type="text" name="filter_LIKES_goodsNo" class="easyui-validatebox"/></td>
				<th><font style="vertical-align:middle" ></font>商品名称</th>
				<td><input type="text" name="filter_LIKES_goodsName" class="easyui-validatebox"/></td>
				<td>
					<span class="toolbar-item dialog-tool-separator"></span>
	  			   <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	   			   <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
				</td>
			</tr>
		</table>
		
	    
	</form>
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.creditNo != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		queryParams: {
			filter_EQS_goodsType: '01'
		},
		url:'${ctx}/stock/stockStatistic/json',
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		scrollbarSize : 0,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'goodsNo',title:'商品编码',width:20},
			{field:'goodsName',title:'商品名称',width:20},
		    {field:'piece',title:'片数',width:10,align:'right'},
			{field:'num',title:'件数/根数(绑定)',width:10,align:'right',
		    	formatter: function(value,row,index){
		    		return row.num.toFixed(3)+"  ("+row.bindNum.toFixed(3)+")";
		    	}},
			{field:'amount',title:'数量(绑定)',width:10,align:'right',
		    		formatter: function(value,row,index){
			    		return row.amount.toFixed(3)+"  ("+row.bindAmount.toFixed(3)+")";
			    	}},
			{field:'unit',title:'单位',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.unit!=''&&row.unit!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getDictName/sldw/"+row.unit,
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
				}}
		]],
		groupField:'warehouse',
		view: groupview,
		groupFormatter:function(value, rows){
			return rows[0].warehouseName +  '--总剩余数:' +rows[0].totalAmount.toFixed(3); 
		},
		sortName:'warehouse',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#tb',
		/* onDblClickRow:function(rowIndex, rowData){
			detail();
		} */
	});
});


//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj);
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	$('#filter_EQS_goodsType').combobox('setValue', '01');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}
</script>
</body>
</html>