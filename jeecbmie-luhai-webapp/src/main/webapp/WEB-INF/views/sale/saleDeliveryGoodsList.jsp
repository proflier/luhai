<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
		<a id="addGoods" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
      	<a id="updateGoods" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a id="deleteGoods" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
     </div> 
</div>
<table id="dgGoods" ></table>
<script type="text/javascript">
var goodsWindow = {
		dgGoods: {},
		listGoods: function() {
			var idValue;
			var urlValue;
			var param = $('#param').val();
			idValue = $('#id').val();
			if(idValue!=null&&idValue!=""){
				urlValue =  '${ctx}/sale/saleDeliveryGoods/getGoods/'+param+"/"+idValue;
			}else{
				urlValue="";
			};
			goodsWindow.dgGoods=$('#dgGoods').datagrid({  
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
					{field:'portView',title:'仓库',sortable:true,width:20},
					{field:'goodsNameView',title:'商品名称',sortable:true,width:20},
					{field:'specifications',title:'规格型号',sortable:true,width:20},
					{field:'quantity',title:'数量',sortable:true,width:20},
					{field:'unitsView',title:'单位',sortable:true,width:20},
					{field:'transportation',title:'运输方式',sortable:true,width:20,
						formatter: function(value,row,index){
							if(value!=''&&value!=null){
	 							$.ajax({
	 								type:'GET',
	 								async: false,
	 								url:"${ctx}/system/dictUtil/getDictNameByCode/YSFS/" + value,
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
					}
			    ]],
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tbGoods',
			    onLoadSuccess : function (data) {
			    	var numTotal = 0;
			    	for (var i = 0; i < data.rows.length; i++) {
			    		numTotal += data.rows[i].quantity;
			    	}
			    	$('#numTotal').numberbox('setValue', numTotal);
			    }
			});
		},
		dlg_goods:{},
		addGoods:function () {//弹窗增加商品
			var idValue = $('#id').val();
			var param = $('#param').val();
			var saleContractNo = $("#saleContractNo").val();
			if(saleContractNo!=""&&saleContractNo!=null&&idValue!=""){
				goodsWindow.dlg_goods=$("#dlg_goods").dialog({  
					method:'GET',
				    title: '新增明细',    
				    width: 600,    
				    height: 500,       
				    href:'${ctx}/sale/saleDeliveryGoods/create/'+param+'/'+idValue,
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
							goodsWindow.dlg_goods.panel('close');
						}
					}]
				});
			}else{
				parent.$.messager.alert('提示','请先保存主表信息!!!');
				return;
			}
		},
		deleteGoods:function (){//删除商品
			var row = goodsWindow.dgGoods.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:'${ctx}/sale/saleDeliveryGoods/delete/'+row.id,
						success: function(data){
							successTip(data,goodsWindow.dgGoods);
						}
					});
				} 
			});
		},
		updateGoods:function (){//弹窗修改商品
			var row = goodsWindow.dgGoods.datagrid('getSelected');
			if(rowIsNull(row)) return;
			goodsWindow.dlg_goods=$("#dlg_goods").dialog({   
			    title: '修改明细',    
			    width: 300,    
			    height: 230,    
			    href:'${ctx}/sale/saleDeliveryGoods/update/'+row.id,
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
						goodsWindow.dlg_goods.panel('close');
					}
				}]
			});
		}
	};
$(function(){
	goodsWindow.listGoods();
	$("#addGoods").click(goodsWindow.addGoods);
	$("#deleteGoods").click(goodsWindow.deleteGoods);
	$("#updateGoods").click(goodsWindow.updateGoods);
})


</script>