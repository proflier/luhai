<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_goods" action="">
	    <input type="text" id="warehouseName" name="warehouseName" class="easyui-validatebox" data-options="prompt: '仓库'"/>
	    <input type="text" id="goodsNameSearch" name="goodsName" class="easyui-validatebox" data-options="prompt: '商品名称'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_goods()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_goods()">重置</a>
	</form>
<table id="goods_dg"></table>
<script type="text/javascript">

	//仓库名称
	$('#warehouseName').combobox({
		panelHeight : 'auto',
		method: "get",
		url : '${ctx}/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=10230007',
		valueField : 'customerCode',
		textField : 'customerName',
// 		required : true,
	});
	
	
	//商品名称
	$('#goodsNameSearch').combobox({
// 		panelHeight:'auto',
		method: "get",
		url:'${ctx}/baseInfo/lhUtil/goodsItems',
		valueField:'infoCode',
	    textField:'infoName', 
// 	    required : true,
	});
	

	var goods_dg;
	goods_dg=$('#goods_dg').datagrid({    
	method: "get",
	url:'${ctx}/stock/inStockGoods/getAll', 
    fit : false,
    singleSelect:true,
	fitColumns : true,
	border : false,
	idField : 'id',
// 	pagination:true,
	rownumbers:true,
// 	pageNumber:1,
// 	pageSize : 10,
// 	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
    columns:[[    
			{field:'id',title: '选择',formatter: 
				function(value, rowData, rowIndex){
                return '<input type="radio" />';
            }}, 
            {field:'warehouseName',title:'仓库',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
			{field:'shipNo',title:'船名',sortable:true,width:60,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
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
			{field:'goodsName',title:'商品名称',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/baseInfo/lhUtil/goodsShow?code='+value ,
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
			{field:'quantity',title:'数量',sortable:true,width:20,align:'right',
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.000; //如果不是数字，返回0.000
					}else{
//							value = Math.abs(value); //取绝对值
						value = parseFloat(value);
						return(value.toFixed(3)); 
					}
				}	
			},
			{field:'units',title:'数量单位',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/dictUtil/getDictNameByCode/sldw/'+value ,
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
// 			{field:'instockCategory',title:'属性',sortable:true,width:40,
// 				formatter: function(value,row,index){
// 					var val;
// 					if(value!=''&&value!=null){
// 						if(value=='0'){
// 							return "原始入库";
// 						}else if(value=="1"){
// 							return "调配库存";
// 						}
// 					}else{
// 						return '';
// 					}
// 				}	
// 			}
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


//创建查询对象并查询
function cx_goods(){
	var obj=$("#searchFrom_goods").serializeObject();
	goods_dg.datagrid('reload',obj);
}

function reset_goods(){
	$("#searchFrom_goods")[0].reset();
	$('#warehouseName').combobox('clear');
	$('#goodsNameSearch').combobox('clear');
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_goods").serializeObject();
	goods_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>