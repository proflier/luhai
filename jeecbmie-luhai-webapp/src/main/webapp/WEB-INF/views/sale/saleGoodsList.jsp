<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="billGoodsObj.updateGoods();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="billGoodsObj.deleteGoods();">删除</a>
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
					}},
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
	},
	updateGoods : function(){
		var row = this.billsGoodsList.datagrid('getSelected');
		if(rowIsNull(row)) return;
		this.billsGoodsFormPage=$("#dlg_goods").dialog({   
		    title: '修改到单商品信息',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistic/billsGoods/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$('#goodsForm').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					billGoodsObj.billsGoodsFormPage.panel('close');
				}
			}]
		});
	},
	deleteGoods : function(){
		var row = this.billsGoodsList.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:'${ctx}/logistic/billsGoods/delete/'+row.id,
					success: function(data){
						var data = eval('(' + data + ')');
						if(data.returnFlag=='success'){
							billGoodsObj.billsGoodsList.datagrid('clearSelections');
							billGoodsObj.billsGoodsList.datagrid('reload');
							parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
							return true;
						}else if(data.returnFlag=='fail'){
							parent.$.messager.alert(data.returnMsg);
							return false;
						}
					}
				});
			} 
		});
	}
};
$(function(){
	billGoodsObj.getList();
});
</script>