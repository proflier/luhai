<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty action || action ne 'view' }">
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
		var urlValue = '${ctx}/stock/qualityGoods/list/';
		this.goodsList=$('#dgGoods').datagrid({
			method : "get",
			url : $('#id').val() == "" ? '' : (urlValue + $('#id').val()),
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
				{field:'goodsName',title:'商品名称',sortable:true,width:10},
				{field:'specifications',title:'规格型号',sortable:true,width:10},
				{field:'amount',title:'数量',sortable:true,width:10},
				{field:'unit',title:'单位',sortable:true,width:10,
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
				{field:'transportation',title:'运输方式',sortable:true,width:10,
					formatter: function(value, row, index){
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
				},
				{field:'indexList',title:'质量指标',sortable:true,width:40,
					formatter: function(value, row, index){
						var strIndex = "<table width='98%' class='tableClass'><tr><th>指标名称(英)</th><th>指标名称(中)</th><th>单位</th><th>检验值</th></tr>";
						for (var i = 0; i < value.length; i++) {
							
							var unitView;
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/system/dictUtil/getDictNameByCode/INDEXUNIT/" + value[i].unit,
								success: function(data){
									unitView = data;
								}
							});
							
							strIndex += "<tr><th>" + value[i].indexNameEn + "</th><th>" + value[i].indexName + "</th><th>" + unitView + "</th><th>" + value[i].inspectionValue + "</th></tr>";
						}
						strIndex += "</table>";
						return strIndex;
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
		    },
		    onLoadSuccess : function (data) {
		    	if ($('#numTotal').length > 0) {
		    		var numTotal = 0;
			    	for (var i = 0; i < data.rows.length; i++) {
			    		numTotal += data.rows[i].amount;
			    	}
			    	$('#numTotal').numberbox('setValue', numTotal);
		    	}
		    }
		});
	},
	addGoods : function(){
		this.goodsFormPage=$("#dlg_quality_goods").dialog({
		    title: '添加商品',    
		    width: 700,
		    height: 500,
		    href:'${ctx}/stock/qualityGoods/create/' + $("#id").val(),
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					if(accept()){
						$('#goodsForm').submit(); 
					}
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
		this.goodsFormPage=$("#dlg_quality_goods").dialog({
		    title: '修改商品',    
		    width: 700,    
		    height: 500,
		    href:'${ctx}/stock/qualityGoods/update/' + row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					if(accept()){
						$('#goodsForm').submit(); 
					}
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
			if(data){
				$.ajax({
					type:'get',
					dataType:'json',
					url:'${ctx}/stock/qualityGoods/delete/' + row.id,
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