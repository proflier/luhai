<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
     </div> 
</div>
<table id="dgBillsGoods" ></table>
<div id="dlg_goods"></div>
<script type="text/javascript">
var billGoodsObj = {
	billsGoodsList : '',
	billsGoodsFormPage : '',
	idValue : '${lhBills.id}',
	getList : function(){
		var urlValue;
		if(this.idValue!=null && this.idValue!=""){
			urlValue =  '${ctx}/logistic/billsGoods/getRelationGoodsByPid/'+this.idValue;
		}else{
			urlValue="";
		}
		this.billsGoodsList=$('#dgBillsGoods').datagrid({  
			method: "get",
			url:urlValue,
		  	fit : false,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			striped:true,
			idField : 'id',
			rownumbers:true,
			singleSelect:true,
			extEditing:false,
		    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'goodsName',title:'品名',sortable:true,width:20,
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
				{field:'purchasePrice',title:'采购单价',sortable:true,width:20},
				{field:'goodsQuantity',title:'数量',sortable:true,width:20},
				{field:'amount',title:'金额',sortable:true,width:20},
				{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
		    ]],
		    sortName:'id',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    toolbar:'#tbGoods'
		});
	}
};
$(function(){
	billGoodsObj.getList();
});
</script>