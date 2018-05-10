<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_purchaseList" action="">
		&nbsp
	    <input type="text" name="filter_LIKES_hth" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_PurchaseConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_PurchaseConfig()">重置</a>
	</form>
<table id="purchase_dg"></table>
<script type="text/javascript">
	var purchase_dg;
	purchase_dg=$('#purchase_dg').datagrid({    
	method: "get",
	url:'${ctx}/logistics/bills/getPurchaseList?filter_EQS_state=生效&filter_EQS_closeOrOpen=运行中', 
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
                return '<INPUT onclick="setIds('+value+','+(row.ghdw==''?0:row.ghdw)+','+(row.htlb==''?0:row.htlb)+')"  type=checkbox>';
			}},
            {field:'hth',title:'合同号',sortable:true,width:20},
			{field:'xyh',title:'协议号',sortable:true,width:20},
			{field:'ghdw',title:'供货单位',sort:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.ghdw!=''&&row.ghdw!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.ghdw,
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
			{field:'htlb',title:'贸易类别',sort:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.htlb!=''&&row.htlb!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getDictName/htlb/"+row.htlb,
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
			{field:'hk',title:'货款',sortable:true,width:20},
    ]],
	});
	
//保存 
function setGoods(){
	if(validateUnique(ghdwArray)){alert("请选择相同供货商的合同！！！");return;};
	if(validateUnique(htlbArray)){alert("请选择相同贸易类别的合同！！！");return;};
	if(purchaseArray.length==0){alert("请选择！！！");return;}
	$.ajax({
		 type:"post",
		 url:"${ctx}/logistics/bills/setGoods?purchaseArray="+purchaseArray,
		 success:function(data){
			 data = $.parseJSON(data);  //json字符串转换成 json对象
			 if(data.state=='success'){
				$("#supplier").combobox('setValue',data.ghdw);//供应商赋值
				$("#ourUnit").combobox('setValue',data.sfdw);//我司单位赋值
				$("#currency").combobox('setValue',data.currency);//币种赋值
				dgGoods.datagrid('reload');
				purchase.panel('close');
				return true;
		 	}else{
		 		return false;
		 	}
		 }
	});
}

//创建查询对象并查询
function cx_PurchaseConfig(){
	var objPurchase=$("#searchFrom_purchaseList").serializeObject();
	purchase_dg.datagrid('reload',objPurchase);
}

function cz_PurchaseConfig(){
	$("#searchFrom_purchaseList").form('clear');
	cx_PurchaseConfig();
}

var purchaseArray=new Array();//存放采购合同id
var ghdwArray=new Array();//存放采购合同供货单位
var htlbArray=new Array();//存放采购合同合同类别
function setIds(id,ghdw,htlb){//ghdw=0代表供货单位为空
	deleteUnique(id,ghdw,htlb);//id重复，说明双击选项，意图取消勾选，从数组中删除次重复id
}

function deleteUnique(id,ghdw,htlb){
	var newId = id;
	for(var i=0;i< purchaseArray.length;i++){
		if(purchaseArray[i]==newId){
			purchaseArray.remove(purchaseArray[i]);
			ghdwArray.remove(ghdwArray[i]);
			htlbArray.remove(htlbArray[i]);
			return;
		}
	}
	purchaseArray.push(id);
	ghdwArray.push(ghdw);
	htlbArray.push(htlb);
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

</script>
</body>
</html>