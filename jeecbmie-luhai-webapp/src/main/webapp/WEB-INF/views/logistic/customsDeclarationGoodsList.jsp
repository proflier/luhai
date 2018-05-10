<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${action ne 'view' }">
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
     	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="goodsObj.addGoods();">添加</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="goodsObj.updateGoods();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="goodsObj.deleteGoods();">删除</a>
     </div> 
</div>
</c:if>
<table id="dgGoods"></table>
<script type="text/javascript">
var goodsObj = {
	goodsList : '',
	goodsFormPage : '',
	change:false,
	backMain:function(){
		this.change = true;
	},
	getList : function(){
		var urlValue = '${ctx}/logistic/customsDeclarationGoods/getByPid/';
		this.goodsList=$('#dgGoods').datagrid({
			method : "get",
			url : $('#id').val() == '' ? '' : (urlValue + $('#id').val()),
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
				{field:'itemNo',title:'项号',sortable:true,width:25},
				{field:'goodsCode',title:'商品编码',sortable:true,width:25},
				{field:'goodsName',title:'商品名称',sortable:true,width:25},
				{field:'specifications',title:'规格型号',sortable:true,width:25},
				{field:'amount',title:'数量',sortable:true,width:25},
				{field:'unit',title:'单位',sortable:true,width:25,
					formatter: function(value,row,index){
						if(value!=''&&value!=null){
 							$.ajax({
 								type:'GET',
 								async: false,
 								url:"${ctx}/system/dictUtil/getDictNameByCode/sldw/" + value,
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
				{field:'originCountry',title:'原产国',sortable:true,width:25,
					formatter: function(value,row,index){
						if(value!=''&&value!=null){
 							$.ajax({
 								type:'GET',
 								async: false,
 								url:"${ctx}/system/countryArea/getName/" + value,
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
				{field:'price',title:'单价',sortable:true,width:25},
				{field:'total',title:'总价',sortable:true,width:25},
				{field:'monetary',title:'币制',sortable:true,width:25,
					formatter: function(value,row,index){
						if(value!=''&&value!=null){
 							$.ajax({
 								type:'GET',
 								async: false,
 								url:"${ctx}/system/dictUtil/getDictNameByCode/CURRENCY/" + value,
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
				{field:'shallBe',title:'征税',sortable:true,width:25,
					formatter: function(value,row,index){
						if(value!=''&&value!=null){
 							$.ajax({
 								type:'GET',
 								async: false,
 								url:"${ctx}/system/dictUtil/getDictNameByCode/SHALLBE/" + value,
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
		    loadFilter: function(data){
		    	var nDate = [];
		    	nDate.rows = data;
		    	return nDate;
		    }
		});
	},
	addGoods : function(){
		this.goodsFormPage=$("#dlg_goods").dialog({   
		    title: '添加报关商品',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistic/customsDeclarationGoods/create/' + $("#id").val(),
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					//accept();
					$('#goodsForm').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					goodsObj.goodsFormPage.panel('close');
				}
			}]
		});
	},
	updateGoods : function(){
		var row = this.goodsList.datagrid('getSelected');
		if(rowIsNull(row)) return;
		this.goodsFormPage=$("#dlg_goods").dialog({
		    title: '修改报关商品',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistic/customsDeclarationGoods/update/' + row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					//accept();
					$('#goodsForm').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					goodsObj.goodsFormPage.panel('close');
				}
			}]
		});
	},
	deleteGoods : function(){
		var row = this.goodsList.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					dataType:'json',
					url:'${ctx}/logistic/customsDeclarationGoods/delete/' + row.id,
					success: function(data){
						if(data.returnFlag=='success'){
				    		goodsObj.backMain();
				    		goodsObj.goodsList.datagrid('clearSelections');
				    		goodsObj.goodsList.datagrid('reload');
				    		parent.$.messager.show({ title : "提示", msg: data.returnMsg, position: "bottomRight" });
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
	goodsObj.getList();
});
</script>