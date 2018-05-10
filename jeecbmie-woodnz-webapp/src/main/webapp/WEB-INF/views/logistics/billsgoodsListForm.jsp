<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
</div>
<style type="text/css">
    .subtotal { font-weight: bold }/*合计单元格样式*/
  </style>
<table id="dgGoods" ></table>
<script type="text/javascript">
$(function(){
	dgGoods=$('#dgGoods').datagrid({  
		method: "get",
		url:'${ctx}/logistics/bills/getGoodsList', 
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[    
			{field:'id',title:'id',hidden:true}, 
			{field:'cghth',title:'采购合同号',sortable:true,width:20},
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'spmc',title:'商品名称',sortable:true,width:20},
			{field:'spbm',title:'商品编码',sortable:true,width:20},
			{field:'sl',title:'数量',sortable:true,width:20,editor:{type: 'numberbox', options: {precision:3}}},
			{field:'js',title:'件数',sortable:true,width:20,editor:{type:'numberbox', options: {precision:3}}},
			{field:'sldw',title:'数量单位',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getDictName/sldw/'+value,
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
			{field:'waterRate',title:'含水率(%)',sortable:true,width:15,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
			{field:'cgdj',title:'采购价',sortable:true,width:20},
			{field:'cgbz',title:'币种',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(row.cgbz!=''&&row.cgbz!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/downMenu/getDictName/currency/'+value,
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
			{field:'cgje',title:'总价',sortable:true,width:20,options: {precision:2}},
			{field : 'action',title : '操作',
				formatter : function(value, row, index) {
					if(row.cghth==undefined){//统计行不显示删除按钮
						return null;
					}
					return '<a href="javascript:removeit('+row.id+','+index+')"><div  style="width:16px;height:16px">删除</div></a>';
				}
	        }
	    ]],
	    onLoadSuccess: function(data){
	    	endEditing();
	    	onCountTolalFooter();
	    },
	    sortName:'id',
	    sortOrder:'desc',
	    showFooter: true,
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onClickRow: onClickRow,
	});
	
});
	
	//删除
	function delGoods(){
		var row = dgGoods.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:'${ctx}/logistics/bills/deleteGoods/'+row.id,
					success: function(data){
						successTip(data,dgGoods);
					}
				});
			} 
		});
	}
	
	//弹窗修改
	function updGoods() {
		var row = dgGoods.datagrid('getSelected');
		if(rowIsNull(row)) return;
		dGoods=$("#dlgGoods").dialog({   
		    title: '修改箱单',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistics/bills/updateGoods/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',id:'button-save',
				handler:function(){
					$('#Goodsform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dGoods.panel('close');
				}
			}]
		});
	}
	//开启行编辑
	var editIndex = undefined;
	var slEditor = "0";
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				dgGoods.datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex = index;
			} else {
				dgGoods.datagrid('selectRow', editIndex);
			}
		}
		
		//数量
		var sl = dgGoods.datagrid('getEditor', { index: editIndex, field: 'sl' });
		sl.target.change(function () {
			dgGoods.datagrid('acceptChanges');
			 editIndex = undefined;
			 onCountTolalFooter();
		});
		
		//件数
		var js = dgGoods.datagrid('getEditor', { index: editIndex, field: 'js' });
		js.target.change(function () {
			dgGoods.datagrid('acceptChanges');
			 editIndex = undefined;
			 onCountTolalFooter();
		});
	}
	
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#dgGoods').datagrid('validateRow', editIndex)){
			$('#dgGoods').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function append(){
		if (endEditing()){
			$('#dgGoods').datagrid('appendRow',{goodsNo:''});
			editIndex = $('#dgGoods').datagrid('getRows').length-1;
			$('#dgGoods').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
		}	
	}
	
	function accept(){
		if(!$('#mainform').form('validate'))//到单主表校验未通过
			return;
		if (endEditing()){
			var rows = $('#dgGoods').datagrid('getRows');
			$('#dgGoods').datagrid('acceptChanges');
			$('#billGoodsJson').val(JSON.stringify(rows));
			return true;
		}
	}
	
  
  function removeit(rowId,index){
	  if($("#id").val() == ''){//如果未保存主表，则从json中删除
		  removeGoods();
		  return;
	  }
	  parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){//如果保存主表，则从数据库中删除
			if (data){
				$.ajax({
					type:'get',
					url:'${ctx}/logistics/bills/deleteGoods/'+rowId,
					success: function(returnData){
						if(returnData=='success'){
							if(dg!=null){
								dgGoods.datagrid('clearSelections');
								dgGoods.datagrid('reload');
							}
							parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
							return true;
						}else{
							parent.$.messager.alert(returnData);
							return false;
						}  
					}
				});
			} 
	});		
  }
  
  function removeGoods(){
	  parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				if (editIndex == undefined){return}
				$('#dgGoods').datagrid('cancelEdit', editIndex)
						.datagrid('deleteRow', editIndex);
				editIndex = undefined;
				$('#billGoodsJson').val(JSON.stringify(rows));
			}
	  });
	}
  
  //指定列求和
  function compute(colName) {
    var rows = dgGoods.datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
      total += parseFloat(rows[i][colName]);
    }
    if(colName=='cgje'){
    	return total.toFixed(2);
    }
    if(total >= 0)
    	return total.toFixed(3);
    else
   		return '0';
  }
  
//增加求和统计行
  function onCountTolalFooter(){
	  dgGoods.datagrid('reloadFooter',[
           	{   spbm: '合计：',
	           	sl:compute("sl"),
	           	realsendVNum:compute("realsendVNum"),
	           	js:compute("js"),
	           	cgje: compute("cgje")}
           ]);
  }
  
</script>
