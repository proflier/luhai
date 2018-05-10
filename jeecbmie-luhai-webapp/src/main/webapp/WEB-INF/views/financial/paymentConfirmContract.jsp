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
	url:'${ctx}/purchase/purchaseContract/json?filter_EQS_state=1',
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
                return '<input type="radio" name="selectRadio" id="selectRadio"' + rowIndex + '    value="' + rowData.oid + '" />';
            }},
            {field:'innerContractNo',title:'内部合同号',sortable:true,width:25},
			{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:25}, 
			{field:'agreementNo',title:'协议号',sortable:true,width:25},
			{field:'deliveryUnitView',title:'供货单位',sortable:true,width:25},
			{field:'signingDate',title:'签约日期 ',sortable:true,width:15},
			{field:'createrDept',title:'申请部门',sortable:true,width:20},
			{field:'businessManagerView',title:'业务经办人',sortable:true,width:15},
			{field:'createDate',title:'创建时间',sortable:true,width:15},
    ]],
    onLoadSuccess: function(data){
        //加载完毕后获取所有的checkbox遍历
        if (data.rows.length > 0) {
            //循环判断操作为新增的不能选择
            for (var i = 0; i < data.rows.length; i++) {
                //根据operate让某些行不可选
                if (data.rows[i].operate == "false") {
                    $("input[type='radio']")[i].disabled = true;
                }
            }
        }
    },
    onClickRow: function(rowIndex, rowData){
        //加载完毕后获取所有的checkbox遍历
        var radio = $("input[type='radio']")[rowIndex].disabled;
        //如果当前的单选框不可选，则不让其选中
        if (radio!= true) {
            //让点击的行单选按钮选中
            $("input[type='radio']")[rowIndex].checked = true;
        }
        else {
            $("input[type='radio']")[rowIndex].checked = false;
        }
    }
	});

//保存
function initContract(){
	var row = contract_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$('#contractNo').val(row.purchaseContractNo);
	$('#interContractNo').val(row.innerContractNo);
// 	fillGoodsCode(row.id);
}

// function fillGoodsCode(id){
// 	$.ajax({
// 		   type: "GET",
// 		   url: "${ctx}/woodCght/woodCghtJK/goodsCode/"+id,
// 		   success: function(data){
// 			   $("#goodsClasses").val(data);
// 			   $.ajax({
// 				   type: "GET",
// 				   url: "${ctx}/baseInfo/goodsInfo/getNameByCode/"+data,
// 				   success:function(d){
// 					   $("#goodsName").val(d);
// 				   }
// 			   });
// 		   }
// 		});
// }
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