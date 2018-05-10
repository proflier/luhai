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
			    columns:[[    
					{field:'id',title:'id',hidden:true}, 
					{field:'portView',title:'仓库',sortable:true,width:30},
					{field:'voyView',title:'船名',sortable:true,width:25},
					{field:'goodsNameView',title:'品名',sortable:true,width:20},
					{field:'quantity',title:'数量',sortable:true,align:'right',width:20,
						formatter: function(value,row,index){
							if(isNaN(value)||value==null){
								return 0.000; //如果不是数字，返回0.000
							}else{
// 								value = Math.abs(value); //取绝对值
								value = parseFloat(value);
								return(value.toFixed(3)); 
							}
						}
					},
					{field:'unitsView',title:'数量单位',sortable:true,width:20},
					{field:'transTypeView',title:'运输方式',sortable:true,width:20},
					{field:'billVoyageView',title:'开单航次',sortable:true,width:40},
			    ]],
			    onLoadSuccess: function(data){
			    	initMainForm(data);
			    	initGoodsFooter(data);
				},
		    	showFooter: true,
			    sortName:'id',
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false,
			    toolbar:'#tbGoods'
			});
		},
		dlg_goods:{},
		addGoods:function () {//弹窗增加商品
			var idValue = $('#id').val();
			var param = $('#param').val();
			var deliveryNo = $("#deliveryNo").val();
			if(idValue!=""&&idValue!=null){
				goodsWindow.dlg_goods=$("#dlg_goods").dialog({  
					method:'GET',
				    title: '新增明细',    
				    width: 300,    
				    height: 280,       
				    href:'${ctx}/stock/outStockGoods/create/'+param+'/'+idValue,
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
			    height: 280,    
			    href:'${ctx}/stock/outStockGoods/update/'+row.id,
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


//加载footer
function initGoodsFooter(data) {
	goodsWindow.dgGoods.datagrid('reloadFooter',[
	                              	{ 
	                              	quantity: getCurrentValue('quantity',data)
	                              	}
	                              ]);
 }
 
function getCurrentValue(colName,data) {
    var total = 0.000;
    for (var i = 0; i < data.rows.length; i++) {
      total += parseFloat(data.rows[i][colName]);
    }
   	return total.toFixed(3);
}


</script>