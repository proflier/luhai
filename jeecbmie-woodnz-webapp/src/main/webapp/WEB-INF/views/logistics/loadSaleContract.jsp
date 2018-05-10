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
<legend>销售合同信息</legend>
<div   style="height:220px">
	<div id="tb_saleContract" style="padding:0px;height:auto">
		<form id="searchFrom_contract" action="">
		    <input type="text" id="selectContractNo" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '销售合同号 '"/>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_saleContract()">查询</a>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_saleContract()">重置</a>
		</form>
	</div>
	<table id="dg_saleContract"></table>
</div>	
</fieldset>
<fieldset class="fieldsetClass">
	<legend>到单信息</legend>
	<div  style="height:150px">
	<table id="dg_bill" class="tableClass">
	</table>
	</div>
</fieldset>
<script type="text/javascript">
	var dg_saleContract;
	dg_saleContract=$('#dg_saleContract').datagrid({    
	method: "get",
  	url : '${ctx}/logistics/downstreamBill/saleJson?filter_EQS_state=生效&filter_EQS_state_ht=运行中&filter_EQS_tradeProperty=147',
  	fit : true,
    singleSelect:true,
	fitColumns : true,
	border : true,
	scrollbarSize : 0,
	idField : 'id',
// 	pagination:true,
	rownumbers:true,
// 	pageNumber:1,
// 	pageSize : 5,
// 	pageList : [ 5,10,20,30],
	striped:true,
    columns:[[    
				{field:'contractNo',title:'销售合同号',width:25}, 
				{field:'purchaser',title:'买方',width:25,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				{field:'seller',title:'卖方',width:25,
						formatter: function(value,row,index){
							var val;
							if(value!=''&&value!=null){
								$.ajax({
									type:'GET',
									async: false,
									url:"${ctx}/system/downMenu/getBaseInfoName/"+value,
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
				{field:'signDate',title:'签订日期',width:15,
	            	formatter:function(value,row,index){  
	            		if(value==null){
	            			return;
	            		}
	                    var unixTimestamp = new Date(value);  
	                    return unixTimestamp.format("yyyy-MM-dd");  
	                    }  
	             },  
				{field:'totalMoney',title:'合同总额',width:15}, 
				{field:'currency',title:'币种',width:10,
					formatter: function(value,row,index){
						var val;
						if(value!=''&&value!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/downMenu/getDictName/currency/"+value,
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
				{field : 'id',title : '操作',width : 10,
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
    sortOrder: 'desc',
    toolbar:'#tb_saleContract',
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    onLoadSuccess:function(data){    
        $("a[name='opera']").linkbutton({text:'选择',plain:true,iconCls:'icon-add'});    
	}
	});
	
	var dg_bill;
	dg_bill=$('#dg_bill').datagrid({  
		method: "get",
	    data:null,
	    fit : true,
		fitColumns : true,
		border : true,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		singleSelect:true,
		rownumbers:true,
	    columns:[[    
			{field:'id',title: '选择',formatter: 
				function(value, rowData, rowIndex){
                return '<input type="radio" />';
            }},
			{field:'billNo',title:'提单号',sortable:true,width:30},
// 			{field:'title:'综合合同号',sortable:true,width:20},
			{field:'shipName',title:'船名',sortable:true,width:20},
			{field:'voyage',title:'航次',sortable:true,width:10},
			{field:'supplier',title:'供货商',sortable:true,width:25,
				formatter: function(value,row,index){
					var val;
					if(row.supplier!=''&&row.supplier!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.supplier,
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
			{field:'portName',title:'目的港',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getPortName/'+value,
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
				}},
			{field:'currency',title:'币种',sortable:true,width:15,
				formatter: function(value,row,index){
					var val;
					if(row.currency!=''&&row.currency!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getDictName/currency/'+value,
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
				}},
				{field:'expectPortDate',title:'预计到港日期',sortable:true,width:20,
					formatter:function(value,row,index){
						if(value == null){
							return null;
						}
	            		var time = new Date(value);
	            		return time.format("yyyy-MM-dd");
	            	}},
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
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

//添加提单
function addSelect(index){
	var row = $('#dg_saleContract').datagrid('getData').rows[index];
	$.ajax({
		 url:'${ctx}/sale/saleContract/json?filter_EQL_id='+row.id,
		type : 'get',
		dataType: "json",//必须定义此参数
		cache : false,
		success : function(data) {
			idvalue = (data.rows)[0].id;
			$("#saleContarteNo").val((data.rows)[0].contractNo)
			/***下面注释支持多个销售合同的叠加显示到单列表**/
// 			var currenctValue = $("#displayValue").val();
// 			var flag = true;
// 			if(currenctValue!=""){
// 				var arrayObj = new Array();
// 				arrayObj =currenctValue.split(",");
// 				for(var i=0;i<arrayObj.length;i++){
// 					if(arrayObj[i]==idvalue){
// 						flag = false;
// 					}
// 				}
// 				if(flag){
// 					$("#displayValue").val(currenctValue+","+idvalue);
// 				}
// 			}else{
// 				$("#displayValue").val(idvalue);
// 			}
			$("#displayValue").val(idvalue);
			searchBills();
		}
	});
	
}

//显示到单
function searchBills(){
	var displayValue = $("#displayValue").val();
	$.ajax({
		url:'${ctx}/logistics/downstreamBill/searchBills/'+displayValue,
		type : 'get',
		cache : false,
		dataType: "json",//必须定义此参数
		success : function(data) {
			$('#dg_bill').datagrid('loadData',data);
		}
	});
}

function initBill(){
	var saleId = $('#displayValue').val();
	var row = dg_bill.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajax({
		type:'get',
		dataType: "json",//必须定义此参数
		url:'${ctx}/logistics/downstreamBill/billJson/'+saleId+'?filter_EQL_id='+row.id,
		success: function(data){
			$("#twoTimeBillNo").removeAttr("disabled",true);
			 $("#saleContarteNo").removeAttr("disabled",true);
			 $("#downStreamClient").combobox('enable');
			 $("#supplier").combobox('enable');
			 $("#ourUnit").combobox('enable');
			 $("#expressNo").removeAttr("disabled",true);
			 $("#expressDate").combobox('enable');
			 $("#expectPortDate").combobox('enable');
			 $("#shipmentType").combobox('enable');
			 $("#trueToDate").combobox('enable');
			 $("#currency").combobox('enable');
			 $("#noBoxDay").numberbox({disabled:false});;
			 $("#shipDate").combobox('enable');
			 $("#portName").combobox('enable');
			 $("#containerNumber").numberbox({disabled:false});;
			 $("#area").combotree({disabled:false});
			 $("#forwarderPrice").numberbox({disabled:false});;
			 $("#numbers").numberbox({disabled:false});;
			 $("#numberUnits").combobox('enable');
			 $("#shipName").removeAttr("disabled",true);
			 $("#voyage").removeAttr("disabled",true);
			 $("#shipCompany").removeAttr("disabled",true);
			 $("#totalP").removeAttr("disabled",true);
			 $("#invoiceDate").combobox('enable');
			 $("#invoiceAmount").numberbox({disabled:false});;
			 $("#invoiceNo").removeAttr("disabled",true);
			 $("#downInvoiceNo").removeAttr("disabled",true);
			  $("#giveBillsDate").combobox('enable');
			  $("#createDate").combobox('enable');
			
			
			
			$('#billNo').val((data.rows)[0].billNo);
			$('#twoTimeBillNo').val((data.rows)[0].twoTimeBillNo);
			$('#supplier').combobox('setValue',(data.rows)[0].supplier);
			$('#ourUnit').combobox('setValue',(data.rows)[0].ourUnit);
			$('#shipmentType').combobox('setValue',(data.rows)[0].shipmentType);
			$('#currency').combobox('setValue',(data.rows)[0].currency);
			$('#noBoxDay').numberbox('setValue',(data.rows)[0].noBoxDay);
			$('#portName').combobox('setValue',(data.rows)[0].portName);
			$('#containerNumber').numberbox('setValue',(data.rows)[0].containerNumber);
			$('#area').combotree('setValue',(data.rows)[0].area);
			$('#forwarderPrice').numberbox('setValue',(data.rows)[0].forwarderPrice);
// 			$('#numbers').numberbox('setValue',(data.rows)[0].numbers);
			$('#numberUnits').combobox('setValue',(data.rows)[0].numberUnits);
			$('#shipName').val((data.rows)[0].shipName);
			$('#voyage').val((data.rows)[0].voyage);
			$('#shipCompany').val((data.rows)[0].shipCompany);
			$('#totalP').val((data.rows)[0].totalP);
			$('#invoiceNo').val((data.rows)[0].invoiceNo);
// 			$('#invoiceAmount').numberbox('setValue',(data.rows)[0].invoiceAmount);
			
// 			$('#expectPortDate').my97('setValue',(data.rows)[0].expectPortDate);
// 			$('#trueToDate').datebox('setValue',(data.rows)[0].trueToDate);
// 			$('#shipDate').datebox('setValue',(data.rows)[0].shipDate);
// 			$('#giveBillsDate').datebox('setValue',(data.rows)[0].giveBillsDate);
// 			$('#createDate').datebox('setValue',(data.rows)[0].createDate);
// 			$('#invoiceDate').datebox('setValue',(data.rows)[0].invoiceDate);
			
			$('#relationSaleContarte').val($('#displayValue').val());
			initStreamGoods((data.rows)[0].returnGoodsJson);//加载货物子表
			loadDownStreamContainer((data.rows)[0].id);
		}
	});
}

function loadDownStreamContainer(sourceId){
	var idValue = $('#id').val();
	$.ajax({
		url:'${ctx}/logistics/downstreamContainer/setContainerList/'+sourceId+"/"+idValue,
		type : 'post',
		cache : false,
		success : function(data) {
			$('#dgContainer').datagrid('reload');
		}
	});
}


//创建查询对象并查询
function cx_saleContract(){
	var obj=$("#searchFrom_contract").serializeObject();
	dg_saleContract.datagrid('reload',obj);
}

function reset_saleContract(){
	$("#searchFrom_contract")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_contract").serializeObject();
	dg_saleContract.datagrid('reload',obj); 
	$("#displayValue").val("");
}
</script>
</body>
</html>