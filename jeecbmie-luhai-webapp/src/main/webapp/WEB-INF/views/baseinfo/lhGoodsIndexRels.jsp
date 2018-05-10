<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	 <table id="index_dg"></table>
<script type="text/javascript">
var userData;
var index_dg={};
$(function(){
	index_dg=$('#index_dg').datagrid({    
	method: "get",
	url:'${ctx}/baseInfo/goodsIndex/jsonAll', 
    fit : false,
	fitColumns : true,
	border : false,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	striped:true,
    columns:[[    
			{field:'id',title: '',checkbox:true},
			{field:'indexName',title:'名称',sortable:true,width:20},  
			{field:'indexCode',title:'编码',sortable:true,width:20},
			{field:'indexNameE',title:'英文名称',sortable:true,width:15},
			{field:'unit',title:'单位',sortable:true,width:20,
				formatter: function(value,row,index){
					var unit_t = '';
					if(value!=null && value!=""){
						if(value=='%'){value='%25';}
						$.ajax({
							url : '${ctx}/system/dictUtil/getDictNameByCode/INDEXUNIT/'+value ,
							type : 'get',
							cache : false,
							async: false,
							success : function(data) {
								unit_t = data;
							}
						});
					}
					return unit_t;
				}	
			}
    ]],
    checkOnSelect:false,
    onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
        if (data.rows.length > 0) {
        	var t = eval('(${indexIds})');
        	$("#dlg_rel input[type='checkbox']")[0].disabled = true;
            //循环判断操作为新增的不能选择
            for (var i = 0; i < data.rows.length; i++) {
            	for (j in t) {
            	    if (t[j] == data.rows[i].id){
            	    	$("input[type='checkbox']")[i + 1].disabled = true;
            	    }
            	  }
            }
        }
    },
	});
});

function saveIndex(){
	var selections = index_dg.datagrid("getSelections");
	var indexIds=[];
	for(var i=0,j=selections.length;i<j;i++){
		indexIds.push(selections[i].id);
	}
	$.ajax({
		async:false,
		type:'POST',
		data:JSON.stringify(indexIds),
		contentType:'application/json;charset=utf-8',
		url:"${ctx}/baseInfo/goodsInfo/rel/create/indexs/${infoId}",
		success: function(data){
			infoIndexRel.relList.datagrid('reload');
			infoIndexRel.relForm.panel('close');
		}
	});
}
</script>
</body>
</html>