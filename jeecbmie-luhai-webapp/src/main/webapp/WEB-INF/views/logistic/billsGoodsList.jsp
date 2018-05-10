<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
     	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="billGoodsObj.addGoods();">添加</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="billGoodsObj.updateGoods();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="billGoodsObj.deleteGoods();">删除</a>
     </div> 
</div>
<table id="dgBillsGoods" ></table>
<script type="text/javascript">
var billGoodsObj = {
	billsGoodsList : '',
	billsGoodsFormPage : '',
	idValue : '${lhBills.id}',
	change:false,
	backMain:function(){
		this.change=true;
	},
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
			showFooter:true,
		    columns:[[    
				{field:'id',title:'id',hidden:true},  
				{field:'purchaseContractNo',title:'采购合同号',sortable:true,width:20},
				{field:'goodsNameView',title:'品名',sortable:true,width:20},
				{field:'purchasePrice',title:'采购单价',sortable:true,width:20},
				{field:'goodsQuantity',title:'数量',sortable:true,width:20,
					formatter: function(value,row,index){
						if(isNaN(value)||value==null){
							return 0.000; //如果不是数字，返回0.000
						}else{
							value = parseFloat(value);
							return(value.toFixed(3)); 
						}
					}		
				},
				{field:'amount',title:'金额',sortable:true,width:20,
					formatter: function(value,row,index){
						if(isNaN(value)||value==null){
							return 0.00; //如果不是数字，返回0.000
						}else{
							value = parseFloat(value);
							return(value.toFixed(2)); 
						}
					}},
				{field:'indicatorInformation',title:'指标信息',sortable:true,width:40}
		    ]],
		    sortName:'id',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    toolbar:'#tbGoods'
		});
	},
	addGoods : function(){
		this.billsGoodsFormPage=$("#dlg_goods").dialog({   
		    title: '添加到单商品信息',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistic/billsGoods/create/'+$("#id").val(),
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					accept();
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
					accept();
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
					dataType:'json',
					url:'${ctx}/logistic/billsGoods/delete/'+row.id,
					success: function(data){
						if(data.returnFlag=='success'){
				    		billGoodsObj.backMain();
				    		billGoodsObj.billsGoodsList.datagrid('clearSelections');
				    		billGoodsObj.billsGoodsList.datagrid('reload');
				    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
				    	}else if(data.returnFlag=='fail'){
				    		parent.$.messager.alert(data.returnMsg);
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