<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addInStockGoods();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateInStockGoods();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteInStockGoods();">删除</a>
     </div> 
</div>
<table id="dgInStockGoods" ></table>
<script type="text/javascript">
var urlValue =  '${ctx}/stock/inStockGoods/getInstockGodds/'+$('#id').val();
var dgInStockGoods;
$(function() {
	dgInStockGoods=$('#dgInStockGoods').datagrid({  
		method: "get",
		url:$('#id').val()!=''?urlValue:'',
	  	fit : false,
		fitColumns : true,
// 		scrollbarSize : 0,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'innerContractNo',title:'内部合同号',sortable:true,width:20},
			{field:'billNo',title:'提单号',sortable:true,width:20},
			{field:'goodsNameView',title:'商品名称',sortable:true,width:20},
			{field:'quantity',title:'数量',sortable:true,align:'right',width:20,
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.000; //如果不是数字，返回0.000
					}else{
						value = parseFloat(value);
						return(value.toFixed(3)); 
					}
				}	
			},
			{field:'unitsView',title:'数量单位',sortable:true,width:20},
			{field:'warehouseNameView',title:'仓库',sortable:true,width:20},
			{field:'instockCategory',title:'属性',sortable:true,width:15,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='0'){
							return "原始入库";
						}else if(value=="1"){
							return "调配库存";
						}else if(value=="2"){
							return "盘库库存";
						}
					}else{
						return '';
					}
				}	
			},
			{field:'updateDate',title:'当前库存生成时间',sortable:true,width:15,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}	
			}
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
	})
});

//弹窗增加入库明细
function addInStockGoods() {
	var idValue = $('#id').val();
	if(idValue!=""){
		dgInStockGoods.datagrid({
	        url :  '${ctx}/stock/inStockGoods/getInstockGodds/'+idValue
	    });
		dlg_instock_goods=$("#dlg_instock_goods").dialog({  
			method:'GET',
		    title: '新增入库明细',    
		    width: 300,    
		    height: 250,       
		    href:'${ctx}/stock/inStockGoods/create/'+idValue,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$('#inStockGoodsForm').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg_instock_goods.panel('close');
				}
			}]
		});
	}else{
		parent.$.messager.alert('提示','请先保存主表信息!!!');
	}
	
}


//删除入库明细
function deleteInStockGoods(){
	var row = dgInStockGoods.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.instockCategory != "0"){
		$.messager.alert('提示','非原始入库，不能删除！','info');
		return;
	}
	if(row.instockCategory != "0"){
		$.messager.alert('提示','没有权限删除调配入库！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/stock/inStockGoods/delete/'+row.id,
				success: function(data){
					successTip(data,dgInStockGoods);
				}
			});
		} 
	});
}

//弹窗修改入库商品
function updateInStockGoods() {
	var row = dgInStockGoods.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.instockCategory != "0"){
		$.messager.alert('提示','非原始入库，不能修改！','info');
		return;
	}
	dlg_instock_goods=$("#dlg_instock_goods").dialog({   
	    title: '修改入库明细',    
	    width: 300,    
	    height: 250,    
	    href:'${ctx}/stock/inStockGoods/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$('#inStockGoodsForm').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_instock_goods.panel('close');
			}
		}]
	});
}

//加载footer
function initGoodsFooter(data) {
	$('#dgInStockGoods').datagrid('reloadFooter',[
	                              	{ 
	                              		quantity: +getCurrentValue('quantity',data)
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