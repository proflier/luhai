<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<input id="displayValue" type="hidden" class="easyui-validatebox" ></input>
<fieldset class="fieldsetClass">
<legend>库存信息</legend>
<div   style="height:355px">
	<div id="tb_stockStatistic" style="padding:0px;height:auto">
		<form id="searchFrom_stockStatistic" action="">
		    <input type="text" name="filter_LIKES_goodsNo" class="easyui-validatebox" data-options="width:150,prompt: '商品编码'"/>
		    <input type="text" name="filter_LIKES_warehouseName" class="easyui-validatebox" data-options="width:150,prompt: '仓库名称'"/>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_stockStatistic()">查询</a>
		    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_stockStatistic()">重置</a>
		</form>
	</div>
	<table id="dg_stockStatistic" class="tableClass">
	</table>
</div>	
</fieldset>
<fieldset class="fieldsetClass">
	<legend>已选库存信息</legend>
	<div  style="height:200px">
	<table id="dg_stockStatistic_selected" class="tableClass">
	</table>
	</div>
</fieldset>
<script type="text/javascript">
var dg_stockStatistic;
var dg_stockStatistic_selected;
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTipNew(data,dg);
	    }
	});
	
	dg_stockStatistic=$('#dg_stockStatistic').datagrid({  
		method: "get",
		url:'${ctx}/stock/stockStatistic/json',
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,	
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'goodsNo',title:'商品编码',width:15},
			{field:'goodsName',title:'商品名称',width:25},
		    {field:'piece',title:'片数',width:10,align:'right'},
			{field:'num',title:'件数/根数(绑定)',width:15,align:'right',
		    	formatter: function(value,row,index){
		    		return row.num.toFixed(3)+"  ("+row.bindNum.toFixed(3)+")";
		    	}},
			{field:'amount',title:'数量(绑定)',width:15,align:'right',
		    		formatter: function(value,row,index){
			    		return row.amount.toFixed(3)+"  ("+row.bindAmount.toFixed(3)+")";
			    	}},
			{field:'unit',title:'单位',width:10,
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
				}
			 },
			{field : 'id',title : '操作',sortable : true,width : 10,
				formatter : function(value, row, index) {
					var str = "";
					str += "<a  name='opera'  href='javascript:void(0)'  onclick='addSelect("
							+ index
							+ ");'></a> &nbsp"
					return str;
				}
			} 
	    ]],
	    sortName:'id',
	    groupField:'warehouse',
	    view: groupview,
	    groupFormatter:function(value, rows){
	    	return rows[0].warehouseName +  '--总剩余数:' +rows[0].totalAmount.toFixed(3); 
		},
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb_stockStatistic',
	    onLoadSuccess:function(data){    
	        $("a[name='opera']").linkbutton({text:'添加',plain:true,iconCls:'icon-add'});    
		}
	});
	
	$('#dg_stockStatistic_selected').datagrid({  
		method: "get",
		data:null,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,	
		idField : 'id',
		rownumbers:true,
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
				}
			 },
			{field : 'id',title : '操作',sortable : true,width : 10,
				formatter : function(value, row, index) {
					var str = "";
					str += "<a  name='opera1'  href='javascript:void(0)'  onclick='removeSelect("
							+value
							+ ");'>删除</a> &nbsp"
					return str;
				}
			} 
	    ]],
	    sortName:'id',
	    groupField:'warehouseId',
	    view: groupview,
	    groupFormatter:function(value, rows){
	    	return rows[0].warehouseName +  '--总剩余数:' +rows[0].totalAmount.toFixed(3);
		},
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
// 	    onLoadSuccess:function(data){    
// 	        $("a[name='opera1']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});    
// 		}
	});
	
	
	
});

function cx_stockStatistic(){
	var obj=$("#searchFrom_stockStatistic").serializeObject();
	dg_stockStatistic.datagrid('reload',obj);
}

function reset_stockStatistic(){
	$("#searchFrom_stockStatistic")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_stockStatistic").serializeObject();
	dg_stockStatistic.datagrid('reload',obj); 
}
function addSelect(index){
	var row = $('#dg_stockStatistic').datagrid('getData').rows[index];
	$.ajax({
		 url:'${ctx}/stock/stockStatistic/json?filter_EQL_id='+row.id,
		type : 'get',
		dataType: "json",//必须定义此参数
		cache : false,
		success : function(data) {
			idvalue = (data.rows)[0].id;
			var currenctValue = $("#displayValue").val();
			var flag = true;
			if(currenctValue!=""){
				var arrayObj = new Array();
				arrayObj =currenctValue.split(",");
				for(var i=0;i<arrayObj.length;i++){
					if(arrayObj[i]==idvalue){
						flag = false;
					}
				}
				if(flag){
					$("#displayValue").val(currenctValue+","+idvalue);
					$('#dg_stockStatistic_selected').datagrid('appendRow',row);
				}
			}else{
				$("#displayValue").val(idvalue);
				$('#dg_stockStatistic_selected').datagrid('appendRow',row);
			}
		}
	});
}

function removeSelect(idValue){
	var currenctValue = $("#displayValue").val();
	if(currenctValue!=""){
		var arrayObj = new Array();
		arrayObj =currenctValue.split(",");
		for(var i=0;i<arrayObj.length;i++){
			if(arrayObj[i]==idValue){
				arrayObj.splice(i, 1);
				break;
			}
		}
		var realIndex = $('#dg_stockStatistic_selected').datagrid('getRowIndex', idValue);
		$('#dg_stockStatistic_selected').datagrid('deleteRow', realIndex);
		$("#displayValue").val(arrayObj.toString());
	}
}

//清除选择
function clearSelect() {
	$('#displayValue').val("");
	$('#realId').val("");
	$('#dg_stockStatistic_selected').datagrid('reload');
}
</script>
</body>
</html>