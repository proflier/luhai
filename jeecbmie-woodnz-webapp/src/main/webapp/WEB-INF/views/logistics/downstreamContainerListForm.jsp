<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.DownstreamContainer"%>
<%@page import="java.util.List"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.DownstreamBill"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
<div id="tbContainer" style="padding:5px;height:auto">
     <div>	
     		<shiro:hasPermission name="sys:downstreamBill:addContainer">
     			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addContainer();">新增箱单</a>
     			<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:downstreamBill:updateContainer">
		       	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateContainer();">修改</a>
	     		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
       	 	<shiro:hasPermission name="sys:downstreamBill:deleteContainer">
	     		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteContainer();">删除</a>
	       	</shiro:hasPermission>
     </div> 
</div>
<table id="dgContainer" ></table>
<script type="text/javascript">
var dgContainer;
var idValue;
initStreamContainer();
function initStreamContainer(value){
	//进行编辑是使用方法
	var urlValue;
	idValue = $('#id').val();
	if(idValue!=null&&idValue!=""){
		urlValue =  '${ctx}/logistics/downstreamContainer/getContainerList/'+idValue;
	}else{
		urlValue="";
	}
	dgContainer=$('#dgContainer').datagrid({  
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
			{field:'boxNo',title:'集装箱号',sortable:true,width:20},
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'goodsName',title:'商品名称',sortable:true,width:35},
			{field:'purchasePrice',title:'销售价',sortable:true,width:20},
			{field:'num',title:'数量',sortable:true,width:20},
			{field:'numberUnits',title:'数量单位',sortable:true,width:10,
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
			{field:'pieceNum',title:'件数',sortable:true,width:20},
			{field:'pNum',title:'片数',sortable:true,width:20},
			{field:'waterRate',title:'含水率',sortable:true,width:20},
			{field:'currency',title:'币种',sortable:true,width:10,
				formatter: function(value,row,index){
					var val;
					if(row.currency!=''&&row.currency!=null){
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
			{field:'totalPrice',title:'总价',sortable:true,width:20}
	    ]],
	    sortName:'boxNo',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    showFooter: true,
	    onLoadSuccess: function(data){
	    	initContainerFooter(data);
		 },
	    toolbar:'#tbContainer'
	});
}
//加载页脚
function initContainerFooter(data){
	var totalboxNo =0;
	var totalrealNumber =0;
	var totalrealPiece =0;
	var totalpieceNum =0;
	var totalnum = 0;
	var array = [];
	for(var i = 0; i < data.rows.length; i++){
	    var map = data.rows[i];
	    for (var key in map){
	    	var currenctValue = map[key];
	    	if(key=='boxNo'){
	    		if(array.indexOf(currenctValue) <0){
	    			totalboxNo = totalboxNo + 1;
	    			array.push(currenctValue);
	    		}
		    }
		    if(key=='num'){
		    	if(!isNaN(parseFloat(currenctValue))){
		    	totalnum = totalnum +parseFloat(currenctValue);
		    	}
		    }
		    if(key=='pieceNum'){
		    	if(!isNaN(parseInt(currenctValue))){
		    	totalpieceNum = totalpieceNum + parseInt(currenctValue);
		    	}
		    }
	    }
	}
	$('#dgContainer').datagrid('reloadFooter',[
	                              	{ boxNo: '集装箱数：'+totalboxNo,
	                              	pieceNum:totalpieceNum,
	                              	num:totalnum.toFixed(3)}
	                              ]);
	
	
}

</script>