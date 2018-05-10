<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_agencyConfig" action="">
	    <input type="text" name="filter_LIKES_agencyAgreementNo" class="easyui-validatebox" data-options="prompt: '代理协议号'"/>
	    <input type="text" name="filter_LIKES_client" class="easyui-validatebox" data-options="prompt: '委托方'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_agencyConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_agencyConfig()">重置</a>
	</form>
<table id="agency_dg"></table>
<script type="text/javascript">
	var agency_dg;
	agency_dg=$('#agency_dg').datagrid({    
	method: "get",
	url:'${ctx}/foreignTrade/agencyAgreement/json', 
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
			{field:'agencyAgreementNo',title:'代理协议号',sortable:true,width:20},
			{field:'agreementType',title:'协议类型',sortable:true,width:20},
		    {field:'salesman',title:'业务员',sortable:true,width:20},
		    {field:'client',title:'委托方',sortable:true,width:20},
		    {field:'createDate',title:'创建时间',sortable:true,width:25},
		    {field:'updateDate',title:'修改时间',sortable:true,width:25}
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
function agencyConfig(){
	var row = agency_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajax({
		type:'post',
		url:'${ctx}/foreignTrade/importContract/agencyConfig/'+row.id,
		success: function(data){
			//更新div数据替换代理协议表
			//alert(JSON.stringify(data));
			 $("#agencyConfigInfo").html(data);
			 //加载更新页面数据
			 initCommodity();
			 accept();
			 /**当是新增数据时选择协议信息后将商品信息置为可填写**/
			 if("create"==$("#currentAction").val()){
				 $("#contractNO").removeAttr("disabled",true);
				$("#supplier").combobox('enable');
				$("#relativePerson").combobox('enable');
				$("#signatureDate").combobox('enable');
				$("#currency").combobox('enable');
				//$("#originalCurrency").removeAttr("disabled",true);
				$("#pricingTerm").removeAttr("disabled",true);
				$("#intFare").removeAttr("disabled",true);
				$("#intSA").removeAttr("disabled",true);
				$("#safetyFactor").removeAttr("disabled",true);
				$("#insuranceRate").removeAttr("disabled",true);
				$("#moreOrLessRate").removeAttr("disabled",true);
				$("#transportPort").combobox('enable');
				$("#transportPortInfo").removeAttr("disabled",true);
				$("#signPosition").combotree("enable");
				$("#destinationPort").combobox('enable');
				$("#destinationPortInfo").removeAttr("disabled",true);
				$("#shipmentTime").combo({disabled:false});
				$("#transportMode").combobox('enable');
				$("#openCreditDate").combo({disabled:false});
			 }
			
		}
		
	});
	d_agen.panel('close');
}

//创建查询对象并查询
function cx_agencyConfig(){
	var obj=$("#searchFrom_agencyConfig").serializeObject();
	agency_dg.datagrid('reload',obj);
}

function reset_agencyConfig(){
	$("#searchFrom_agencyConfig")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_agencyConfig").serializeObject();
	agency_dg.datagrid('reload',obj); 
}
</script>
</body>
</html>