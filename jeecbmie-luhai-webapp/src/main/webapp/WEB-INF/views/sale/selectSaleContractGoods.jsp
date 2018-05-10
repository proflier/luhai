<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
	<form id="searchFrom_saleList" action="">
		&nbsp;
	     <input type="text" name="contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_SaleContractConfig()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="cz_SaleContractConfig()">重置</a> 
	</form>
<table id="saleC_dg" ></table>
<script type="text/javascript">
var selectObj = {
		contractCount:0, //合同数量
		saleContractNo:'',
		deliveryStartDate:'',
		deliveryEndDate:'',
		numSettlementBasis:'',
		qualitySettlementBasis:'',
		seller:'',
		saleMode:'',
		riskTip:'',
		businessManager:'',
		deliveryMode:'',
		relLoginNames:'',
		goodsIds:new Array(),
		init:function(){
			this.contractCount=0;
			this.saleContractNo='';
			this.deliveryStartDate='';
			this.deliveryEndDate='';
			this.numSettlementBasis='';
			this.qualitySettlementBasis='';
			this.seller='';
			this.saleMode='';
			this.riskTip='';
			this.businessManager='';
			this.deliveryMode='';
			this.relLoginNames='';
			this.setUnit='';
		},
		recordCount:function(){
			var rows = saleC_dg.datagrid("getSelections");
			for(var i=0;i<rows.length;i++){
				var contractNo_t = rows[i].saleContract.contractNo;
				if(this.saleContractNo==''){
					this.contractCount=1;
					this.saleContractNo=contractNo_t;
					this.deliveryStartDate=rows[i].saleContract.deliveryStartDate;
					this.deliveryEndDate=rows[i].saleContract.deliveryEndDate;
					this.numSettlementBasis=rows[i].saleContract.numSettlementBasis;
					this.qualitySettlementBasis=rows[i].saleContract.qualitySettlementBasis;
					this.seller=rows[i].saleContract.purchaser;
					this.saleMode=rows[i].saleContract.saleMode;
					this.riskTip=rows[i].saleContract.riskTip;
					this.businessManager=rows[i].saleContract.businessManager;
					this.deliveryMode=rows[i].saleContract.deliveryMode;
					this.relLoginNames=rows[i].saleContract.relLoginNames;
					this.setUnit=rows[i].saleContract.setUnit;
				}else{
					if(this.saleContractNo!=contractNo_t){
						this.contractCount=2;
						break;
					}
				}
			}
		},
		fillGoodsIds:function(){
			var rows = saleC_dg.datagrid("getSelections");
			for(var i=0;i<rows.length;i++){
				this.goodsIds.push(rows[i].id);
			}
		},
		fillGoodsIds_form:function(){
			$("#mainform").find("input[name='saleGoodsIds']").remove();
			if(this.goodsIds.length>0){
				for(var i=0;i<this.goodsIds.length;i++){
					$("#mainform").append("<input type=hidden name='saleGoodsIds' value='"+this.goodsIds[i]+"'/>");
				}
			}
		}
};

//保存 
function saveSaleDeliveryGoods(){
	selectObj.init();
	selectObj.recordCount();
	if(selectObj.contractCount==0){
		return false;
	}
	if(selectObj.contractCount>1){
		alert("请选择同一个销售合同下的货物");
		return false;
	}
	selectObj.fillGoodsIds();
	$("#saleContractNo").val(selectObj.saleContractNo);
	$("#deliveryStartDate").val(selectObj.deliveryStartDate);
	$("#deliveryEndDate").val(selectObj.deliveryEndDate);
	$("#numSettlementBasis").val(selectObj.numSettlementBasis);
	$("#qualitySettlementBasis").val(selectObj.qualitySettlementBasis);
	$("#seller").combobox('setValue',selectObj.seller);
	$("#saleMode").combobox('setValue',selectObj.saleMode);
	$("#riskTip").val(selectObj.riskTip);
	$("#businessManager").combotree('setValue', selectObj.businessManager);
	$('#deliveryMode').combobox('setValue', selectObj.deliveryMode);
	$("#saleGoodsIds").val(JSON.stringify(selectObj.goodsIds));
	$("#relLoginNames").val(selectObj.relLoginNames);
	$("#manageMode").val(selectObj.manageMode);
	$("#setUnit").combobox('setValue', selectObj.setUnit);//账套单位
	
	selectObj.fillGoodsIds_form();
}
function saveBasedSaleSettlement(){
	if(row_t.contractNo){
		$("#saleContractNo").val(row_t.contractNo);
	}
}
//创建查询对象并查询
function cx_SaleContractConfig(){
	var objPurchase=$("#searchFrom_saleList").serializeObject();
	saleC_dg.datagrid('reload',objPurchase);
}

function cz_SaleContractConfig(){
	$("#searchFrom_saleList").form('clear');
	cx_SaleContractConfig();
}

$(function(){
	saleC_dg=$('#saleC_dg').datagrid({    
	method: "get",
	url:'${ctx}/sale/saleContractGoods/getGoodsCasContract', 
    fit : false,
    singleSelect:false,
	fitColumns : false,
	border : false,
	idField : 'id',
	rownumbers:true,
	pagination:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20],
	striped:true,
	frozenColumns:[[
	        {field:'id',title:'',checkbox:true},
			{field:'contractNo',title:'合同号',width:150,
				formatter: function(value,row,index){
					return row.saleContract.contractNo;
				}}]],
    columns:[[    
			{field:'shipNo',title:'运输工具编号',width:130},
			{field:'purchaser',title:'买方',width:150,
				formatter: function(value,row,index){
					var val;
					if(row.saleContract.purchaser!=''&& row.saleContract.purchaser!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+row.saleContract.purchaser,
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
			{field:'saleMode',title:'销售方式',width:80,
				formatter: function(value,row,index){
      				var val;
      				if(row.saleContract.saleMode!=''&&row.saleContract.saleMode!=null){
      					$.ajax({
      						type:'GET',
      						async: false,
      						url:"${ctx}/system/dictUtil/getDictNameByCode/SALESMODE/"+row.saleContract.saleMode,
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
     		{field:'deliveryEndDate',title:'交货止期',width:80,
   				formatter:function(value,row,index){
   					if(row.saleContract.deliveryEndDate == null){
   						return null;
   					}
               		var time = new Date(row.saleContract.deliveryEndDate);
               		return time.format("yyyy-MM-dd");
               	}},
            {field:'businessManager',title:'业务经办人',width:60,
				formatter: function(value,row,index){
					var name = '';
					if(row.saleContract.businessManager!=null && row.saleContract.businessManager!=""){
						$.ajax({
							url : '${ctx}/system/user/getUserNameByLogin/'+row.saleContract.businessManager ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								name = data;
							}
						});
					}
					return name;
				}
			},
			{field:'goodsName',title:'商品',width:80,
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
			{field:'goodsQuantity',title:'数量',width:50}
			
    ]],
    onCheck:function(index,row){
    	row_t = row;
    },
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false
	});
});
	
</script>
</body>
</html>