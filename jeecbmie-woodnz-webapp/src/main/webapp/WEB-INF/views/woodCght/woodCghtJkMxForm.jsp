<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMx"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchGoodsCode()">查看商品编码</a>
	<span class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="childTB" data-options="onClickRow: onClickRow"></table>
<%
	WoodCghtJk woodCghtJk = (WoodCghtJk)request.getAttribute("woodCghtJk");
	List<WoodCghtJkMx> woodCghtJkMxList = woodCghtJk.getWoodCghtJkMxList();
	ObjectMapper objectMapperCus = new ObjectMapper();
	String childJson = objectMapperCus.writeValueAsString(woodCghtJkMxList);
	request.setAttribute("childJson", childJson);
%>

<script type="text/javascript">
var childTB;
var d_goodsCode;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${childJson}'),
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
		{field:'spbm',title:'商品编码',width:25,editor:{type:'validatebox',options:{required:true}}},
	    {field:'spmc',title:'商品名称',width:25, editor:{type:'validatebox',options:{required:true}}},
        {field:'spms',title:'商品描述',width:25, editor:{type:'validatebox'}},
   		{field:'sfbwwz',title:'是否濒危物种',width:15, 
        	editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/YESNO'
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.sfbwwz!=''&&row.sfbwwz!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/YESNO/"+row.sfbwwz,
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
     	{field:'dptj',title:'单片体积',width:15, editor:{type:'numberbox',options:{min:0,precision:3}}},
     	{field:'ps',title:'片数',width:15, editor:{type:'numberbox',options:{min:0}}},
     	{field:'js',title:'件数 ',width:15, editor:{type:'numberbox',options:{min:0}}},
     	{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
     	{field:'lfs',title:'数量 ',width:15, editor:{type:'numberbox',options:{min:0,precision:3}}},
     	{field:'sldw',title:'数量单位',width:10, 
     		editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/sldw',
					required:true
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.sldw!=''&&row.sldw!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/sldw/"+row.sldw,
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
     	{field:'cgdj',title:'采购单价',width:15,  editor:{type:'numberbox',options:{min:0,precision:2}}},
     	{field:'cgbz',title:'币种 ',width:10,
     		editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/CURRENCY',
					required:true
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.cgbz!=''&&row.cgbz!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/CURRENCY/"+row.cgbz,
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
     	{field:'cgje',title:'采购金额 ',width:15,editor:{type:'numberbox',options:{min:0,precision:2}}},
     	{field:'yjhdgg',title:'预计货到港口',width:15,
     		formatter: function(value,row,index){
				var val;
				if(row.yjhdgg!=''&&row.yjhdgg!=null){
					$.ajax({
						type:'GET',
						async: false,
						url : '${ctx}/system/downMenu/getPortName/'+row.yjhdgg ,
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
			},
			editor:{
				type:'combobox',
				options:{
					method: "get",
					url : '${ctx}/system/downMenu/jsonGK',
					valueField : 'id',
					textField : 'gkm',
				}
			}			
     	}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB').datagrid('validateRow', editIndex)){
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}



function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB').datagrid('selectRow', editIndex);
		}
		initSPBM( editIndex);
	}
}
function append(){
	if (endEditing()){
		$('#childTB').datagrid('appendRow',{spmc:''});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
		initSPBM( editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#woodCghtJkMxJson').val(JSON.stringify(rows));
		$('#childTB').datagrid('acceptChanges');
	}
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

//弹窗查看编码
function searchGoodsCode() {
	d_goodsCode=$("#searchGoodsCode").dialog({   
	    title: '查看商品编码',    
	    width: 600,    
	    height: 355,    
	    href:'${ctx}/baseInfo/goodsInfo/ckbm',
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_goodsCode.panel('close');
			}
		}]
	});
}

function initSPBM( editIndex){
	var currenctObject = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'spbm' });
	currenctObject.target.change(function () {
        var spbm = $(currenctObject.target).val();
        if (spbm.replace(/[ ]/g,"") ==""){
        	spbm = $(currenctObject.target).val("");
       		return;
       	} 
        if(spbm.length>13){
        	$.messager.alert('提示','编码长度超过13位！请检查！','info');
        }else{
        	 var destObject = $('#childTB').datagrid('getEditor', {index:editIndex,field:'spmc'});
        	 if(spbm!=""&&spbm!=null){
        		 $.ajax({
      				url : '${ctx}/baseInfo/goodsInfo/getNameByCode/' + spbm,
      				type : 'get',
      				cache : false,
      				success : function(data) {
      					$(destObject.target).val(data);
      				}
      			});
        	 }
        }
	});
	
	//采购金额计算
	var currenctLFS = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'lfs' });
	currenctLFS.target.change(function () {
        var lfs = $(currenctLFS.target).val();
       	var currenctCGDJ = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgdj'});
        var cgdj = $(currenctCGDJ.target).val();
        var currenctCGJE = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgje'});
        if(isNaN(parseFloat(lfs)*parseFloat(cgdj))){}else{
        $(currenctCGJE.target).numberbox('setValue', (parseFloat(lfs)*parseFloat(cgdj)).toFixed(2));
        }
	});
	
   	var currenctCGDJ = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgdj'});
   	currenctCGDJ.target.change(function () {
		var currenctLFS = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'lfs' });
        var lfs = $(currenctLFS.target).val();
        var cgdj = $(currenctCGDJ.target).val();
        var currenctCGJE = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgje'});
        if(isNaN(parseFloat(lfs)*parseFloat(cgdj))){}else{
            $(currenctCGJE.target).numberbox('setValue', (parseFloat(lfs)*parseFloat(cgdj)).toFixed(2));

            }
	});
	
	
	
}
</script>