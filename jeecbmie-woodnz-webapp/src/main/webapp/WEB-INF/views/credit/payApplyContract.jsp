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
<table id="contract_dg" style="height:300px"></table>
<script type="text/javascript">
	var contract_dg;
	contract_dg=$('#contract_dg').datagrid({    
	method: "get",
	url:'${ctx}/woodCght/woodCghtJK/json?filter_EQS_state=生效&filter_EQS_closeOrOpen=运行中',
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
                return '<input type="radio" />';
            }},
            {field:'hth',title:'合同号',sortable:true,width:15}, 
            {field:'interContractNo',title:'综合合同号',sortable:true,width:15}, 
			{field:'hk',title:'货款',sortable:true,width:15},
			{field:'skfs',title:'收款方式',sortable:true,width:8,
				formatter: function(value,row,index){
					var val;
					if(row.skfs!=''&&row.skfs!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getDictName/skfs/"+row.skfs,
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
			{field:'ghdw',title:'供货单位',sortable:true,width:20,
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
			{field:'dhrq',title:'订货日期 ',sortable:true,width:10},
			{field:'ywy',title:'申请人',sortable:true,width:8}
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

//保存
function initContract(){
	var row = contract_dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
// 	$.ajax({
// 		type:'get',
// 		dataType: "json",//必须定义此参数
// 		url:'${ctx}/woodCght/woodCghtJK/json?filter_EQS_hth='+row.hth,
// 		success: function(data){
// // 			alert(JSON.stringify(data));
// // 			for(var i = 0; i < data.rows.length; i++){
// // 			    var map = data.rows[i];
// // 			    for (var key in map){
// // 			    alert("key:"+key+"  value:"+map[key]);
// // 			    }
// // 			}
// 		}
// 	});	
			 $("#inteContractNo").removeAttr("disabled",true);
			 $("#payApplyNo").removeAttr("disabled",true);
			 $("#applyMoney").numberbox({disabled:false});
			 $("#forecastPayTime").combo('enable');
			 $("#currency").combobox('enable');
			 $("#moreOrLess").removeAttr("disabled",true);
			 $("#exchangeRate").numberbox({disabled:false});
			 $("#handelRecognizance").numberbox({disabled:false});
			 $("#arrivalRecognizance").numberbox({disabled:false});
			 $("#recognizanceCurrency").combobox('enable');
			 $("#bankExpects").combobox('enable');
			 $("#paymenMethod").combobox('enable');
			 $("#creditType").combobox('enable');
			 $("#usageTime").numberbox({disabled:false});
			 $("#ourUnit").combobox('enable');
			 $("#supplier").combobox('enable');
			 $("#importingCountry").combotree({disabled:false});
			 $("#internalClient").combo('enable');
			 $("#runMode").combobox('enable');
			 $("#comment").removeAttr("disabled",true);

			$('#inteContractNo').val(row.interContractNo);
			$('#contractNo').val(row.hth);
 			$('#exchangeRate').numberbox('setValue',row.hl);
			$('#moreOrLess').val(row.yd);
 			$('#supplier').combobox('setValue',row.ghdw);
 			$('#ourUnit').combobox('setValue',row.dhdw);
			$('#importingCountry').combotree('setValue',row.jkgb);
			$('#currency').combobox('setValue', row.cgbz);
			$('#creditType').combobox('setValue', row.sxlx);
			$('#paymenMethod').combobox('setValue', row.skfs);
			$('#applyMoney').numberbox('setValue',row.hk);
			$('#handelRecognizance').numberbox('setValue',row.dj);
			$('#runMode').combobox('setValue', row.htlb);
// 			$('#bankExpects').combobox('setValue', $('#bankExpects').combobox('getValue'));
			
	
}

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