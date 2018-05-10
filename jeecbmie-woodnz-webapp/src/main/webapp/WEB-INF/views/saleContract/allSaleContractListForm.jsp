<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_saleList" action="">
		&nbsp
	    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_SaleConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_SaleConfig()">重置</a>
	</form>
<table id="sale_dg"></table>
<script type="text/javascript">
	var sale_dg;
	sale_dg=$('#sale_dg').datagrid({    
	method: "get",
	//选择审批通过，状态为运行中，转口类型的销售合同
	url:'${ctx}/sale/purchaseSaleSame/getAllSaleList/'+($('#id').val()==''?'0':$('#id').val())+
	'?filter_EQS_state=生效&filter_EQS_state_ht=运行中&filter_EQS_tradeProperty=147&filter_EQS_state_cx=0',
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
				function(value, row, index){
                return '<INPUT onclick="setSaleIds('+value+')"  type=checkbox>';
			}},
            {field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'dealNo',title:'协议号',sortable:true,width:20},
			{field:'seller',title:'卖受人',sort:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.seller!=''&&row.seller!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.seller,
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
			{field:'purchaser',title:'买受人',sort:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.purchaser!=''&&row.purchaser!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.purchaser,
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
			{field:'totalMoney',title:'合同总额',sortable:true,width:20},
    ]],
	});

//创建查询对象并查询
function cx_SaleConfig(){
	var objSale=$("#searchFrom_saleList").serializeObject();
	sale_dg.datagrid('reload',objSale);
}

function cz_SaleConfig(){
	$("#searchFrom_saleList").form('clear');
	cx_SaleConfig();
}

var saleArray=new Array();//存放销售合同id
function setSaleIds(id){
	pushUnique(id);//id重复，说明双击选项，意图取消勾选，从数组中删除次重复id
}

function pushUnique(id,ghdw){
	var newId = id;
	for(var i=0;i< saleArray.length;i++){
		if(saleArray[i]==newId){
			saleArray.remove(saleArray[i]);
			return;
		}
	}
	saleArray.push(id);
}

//验证list中元素是否完全一样，完全一样返回false，有不同元素返回true
function validateUnique(arr){
	for(var i=0;i<arr.length;i++){
		if( arr[0] != arr[i] ){
			return true;
		}
	}
	return false;
}


//保存（销售列表弹框） 
function setSaleContract(){
	if(saleArray.length==0){alert("请选择！！！");return;}
	$.ajax({
		 type:"post",
		 url:"${ctx}/sale/purchaseSaleSame/setSaleContract?saleArray="+saleArray+'&mainId='+$("#id").val(),
		 success:function(dataMsg){
			var dataMsg = eval('(' + dataMsg + ')');
			if(dataMsg.returnFlag=='success'){
				$('#dgSaleList').datagrid('loadData',dataMsg.returnData);
				var rows = $('#dgSaleList').datagrid('getRows');
				$('#dgSaleList').datagrid('acceptChanges');
				$('#saleContractJson').val(JSON.stringify(rows));
				sale.panel('close');
				return true;
			}
			else if(dataMsg.returnFlag=='fail'){
				parent.$.messager.alert(dataMsg.returnMsg);
				return false;
			}  
		 }
	});
}
</script>
</body>
</html>