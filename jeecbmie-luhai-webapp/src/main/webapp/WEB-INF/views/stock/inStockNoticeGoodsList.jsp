<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div id="goodsTools">	
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addInStockNoticeGoods();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateInStockNoticeGoods();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteInStockNoticeGoods();">删除</a>
     </div> 
</div>
<table id="dgInStockNoticeGoods" ></table>
<script type="text/javascript">
var urlValue =  '${ctx}/stock/inStockNoticeGoods/getInstockNoticeGoods/'+$('#id').val();
var dgInStockNoticeGoods;
$(function() {
	dgInStockNoticeGoods=$('#dgInStockNoticeGoods').datagrid({  
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
			{field:'goodsNameView',title:'商品名称',width:20},
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
			{field:'quantityCIQ',title:'CIQ数量',sortable:true,align:'right',width:20,
				formatter: function(value,row,index){
					if(isNaN(value)||value==null){
						return 0.000; //如果不是数字，返回0.000
					}else{
						value = parseFloat(value);
						return(value.toFixed(3)); 
					}
				}	
			},
			{field:'unitsView',title:'数量单位',width:20},
			{field:'transportCategoryView',title:'运输方式',width:20},
			{field:'warehouseNameView',title:'仓库',width:20},
	    ]],
	    onLoadSuccess: function(data){
	    	initGoodsFooter(data);
		},
	    showFooter: true,
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tbGoods'
	})
	
	if("${action }"==""){
		$('#dgInStockNoticeGoods').datagrid({toolbar:''});
	}
});

//弹窗增加入库明细
function addInStockNoticeGoods() {
	var idValue = $('#id').val();
	if(idValue!=""){
		dgInStockNoticeGoods.datagrid({
	        url :  '${ctx}/stock/inStockNoticeGoods/getInstockNoticeGoods/'+idValue
	    });
		dlg_instock_notice_goods=$("#dlg_instock_notice_goods").dialog({  
			method:'GET',
		    title: '新增入库明细',    
		    width: 450,    
		    height: 330,       
		    href:'${ctx}/stock/inStockNoticeGoods/create/'+idValue,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$('#inStockNoticeGoodsForm').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg_instock_notice_goods.panel('close');
				}
			}]
		});
	}else{
		parent.$.messager.alert('提示','请先保存主表信息!!!');
	}
	
}


//删除入库明细
function deleteInStockNoticeGoods(){
	var row = dgInStockNoticeGoods.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/stock/inStockNoticeGoods/delete/'+row.id,
				success: function(data){
					successTip(data,dgInStockNoticeGoods);
				}
			});
		} 
	});
}

//弹窗修改入库商品
function updateInStockNoticeGoods() {
	var row = dgInStockNoticeGoods.datagrid('getSelected');
	if(rowIsNull(row)) return;
	dlg_instock_notice_goods=$("#dlg_instock_notice_goods").dialog({   
	    title: '修改入库明细',    
	    width: 450,    
	    height: 330,     
	    href:'${ctx}/stock/inStockNoticeGoods/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$('#inStockNoticeGoodsForm').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_instock_notice_goods.panel('close');
			}
		}]
	});
}

//加载footer
function initGoodsFooter(data) {
	$('#dgInStockNoticeGoods').datagrid('reloadFooter',[
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