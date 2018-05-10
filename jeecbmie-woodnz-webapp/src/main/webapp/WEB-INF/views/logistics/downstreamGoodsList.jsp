<%@page import="com.cbmie.woodNZ.logistics.entity.DownstreamGoods"%>
<%@page import="java.util.List"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.woodNZ.logistics.entity.DownstreamBill"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
</div>
<table id="childTB"   data-options="onClickRow: onClickRow"></table>
<%
	DownstreamBill downstreamBill = (DownstreamBill)request.getAttribute("downstreamBill");
	List<DownstreamGoods> downstreamGoodsList = downstreamBill.getDownstreamGoodsList();
	ObjectMapper objectMapperGoods = new ObjectMapper();
	String goodsJson = objectMapperGoods.writeValueAsString(downstreamGoodsList);
	request.setAttribute("goodsJson", goodsJson);
%>
<script type="text/javascript">
var childTB;
initStreamGoods();
function initStreamGoods(value){
	childTB=$('#childTB').datagrid({
// 		data : JSON.parse('${childJson}'),
		data : typeof(value) == "undefined"? JSON.parse('${goodsJson}'):(value==null?JSON.parse('[]'):JSON.parse(value) ) ,
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
			{field:'cghth',title:'采购合同号',sortable:true,width:20},
			{field:'saleContractNo',title:'销售合同号',sortable:true,width:20},
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20,editor:{type:'validatebox',options:{disabled:true}}},
			{field:'spmc',title:'商品名称',sortable:true,width:30},
			{field:'spbm',title:'商品编码',sortable:true,width:20,editor:{type:'validatebox',options:{disabled:true}}},
			{field:'sl',title:'数量',sortable:true,width:10,editor:{type: 'numberbox', options: {precision:3}}},
			{field:'js',title:'件数',sortable:true,width:10,editor:{type:'numberbox' }},
			{field:'waterRate',title:'含水率(%)',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
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
			{field:'cgdj',title:'销售价',sortable:true,width:15,editor:{type:'numberbox', options: {precision:2}}},
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
			{field:'cgje',title:'总价',sortable:true,width:15,editor:{type:'numberbox', options: {precision:2}}}
	    ]],
	    onLoadSuccess: function(data){
	    },
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onClickRow: onClickRow,
	    showFooter: true,
	    onLoadSuccess: function(data){
	    	initGoodsFooter(data);
		 },
	    toolbar:'#tbGoods'
	});
}

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
		cacleGoods( editIndex);
	}
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#downstreamGoodsJson').val(JSON.stringify(rows));
		$('#childTB').datagrid('acceptChanges');
	}
}



//加载页脚
function initGoodsFooter(data){
	var totalsl =0;
	var totaljs =0;
	var totalcgje =0;
	var array = [];
	for(var i = 0; i < data.rows.length; i++){
	    var map = data.rows[i];
	    for (var key in map){
	    	var currenctValue = map[key];
		    if(key=='sl'){
		    	if(!isNaN(parseInt(currenctValue))){
		    		totalsl = totalsl + parseInt(currenctValue);
		    	}
		    }
		    if(key=='js'){
		    	if(!isNaN(parseFloat(currenctValue))){
		    		totaljs = totaljs +parseFloat(currenctValue);
		    	}
		    }
		    if(key=='cgje'){
		    	if(!isNaN(parseFloat(currenctValue))){
		    		totalcgje = totalcgje + parseFloat(currenctValue);
		    	}
		    }
	    }
	}

	$('#childTB').datagrid('reloadFooter',[
	                              	{ cghth: '合计：',
	                              	sl:totalsl.toFixed(3),
	                              	js: totaljs,
	                              	cgje:totalcgje.toFixed(2)}
	                              ]);
	
	window.setTimeout(function(){
		$('#numbers').numberbox('setValue',totalsl.toFixed(3));
		$('#invoiceAmount').numberbox('setValue',totalcgje.toFixed(2));
	}, 1000); //需要页面元素进入一定的状态才能使用，所以延迟1秒执行

	
}


function cacleGoods( editIndex){
	  

	//金额计算
	//数量	
	var currenctSL = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'sl' });
	//总价
	var currenctcgje = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'cgje' });
	//销售价
	var currenctCGDJ = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgdj'});
	
	currenctSL.target.change(function () {
        var sl = $(currenctSL.target).val();
       	var currenctCGDJ = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgdj'});
        var cgdj = $(currenctCGDJ.target).val();
        var currenctCGJE = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgje'});
        if(isNaN(parseFloat(sl)*parseFloat(cgdj))){}else{
        $(currenctCGJE.target).numberbox('setValue', (parseFloat(sl)*parseFloat(cgdj)).toFixed(2));
        }
        
        //动态footer
        //数量
        var source = $('#childTB').datagrid('getSelected').sl;
        if(isNaN(parseFloat(source))){
			 source = 0.000;
		 }
		 if(isNaN(parseFloat(sl))){
			 sl = 0.000;
		 }
		 var changeData = parseFloat(sl) - parseFloat(source);
		 var rows = $('#childTB').datagrid('getRows');
	 	 var totalsl =0;
			for(var i = 0; i < rows.length; i++){
			    var map = rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='sl'){
				    	if(!isNaN(parseFloat(currenctValue))){
				    	totalsl = totalsl + parseFloat(currenctValue);
				    	}
				    }
			    }
			}
			totalsl = totalsl + changeData;
			 var rowsFooter = $('#childTB').datagrid('getFooterRows');
			 rowsFooter[0]['sl'] = totalsl.toFixed(3);
			 $('#numbers').numberbox('setValue',totalsl.toFixed(3));
		     $('#childTB').datagrid('reloadFooter');
		     
		     //总价计算完成之后 动态 总价footer
		     var cgje = (parseFloat(sl)*parseFloat(cgdj)).toFixed(2)
			 var source1 = $('#childTB').datagrid('getSelected').cgje;
		     if(isNaN(parseFloat(source1))){
		    	 source1 = 0.00;
			 }
			 if(isNaN(parseFloat(cgje))){
				 cgje = 0.00;
			 }
			 var changeData1 = parseFloat(cgje) - parseFloat(source1);
			 var rows = $('#childTB').datagrid('getRows');
		 	 var totalcgje =0;
				for(var i = 0; i < rows.length; i++){
				    var map = rows[i];
				    for (var key in map){
				    	var currenctValue = map[key];
					    if(key=='cgje'){
					    	if(!isNaN(parseFloat(currenctValue))){
					    	totalcgje = totalcgje + parseFloat(currenctValue);
					    	}
					    }
				    }
				}
				totalcgje = totalcgje + changeData1;
				 var rowsFooter = $('#childTB').datagrid('getFooterRows');
				 rowsFooter[0]['cgje'] = totalcgje.toFixed(2);
				 $('#invoiceAmount').numberbox('setValue',totalcgje.toFixed(2));
			     $('#childTB').datagrid('reloadFooter');
	});
	
   	
   	currenctCGDJ.target.change(function () {
		var currenctSL = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'sl' });
        var sl = $(currenctSL.target).val();
        var cgdj = $(currenctCGDJ.target).val();
        var currenctCGJE = $('#childTB').datagrid('getEditor', {index:editIndex,field:'cgje'});
        //箱单相关
		var currenctcpContractNo = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'cpContractNo' });
		var currenctspbm = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'spbm' });
		var cpContractNo = $(currenctcpContractNo.target).val();
		var spbm = $(currenctspbm.target).val();
		//计算
        if(isNaN(parseFloat(sl)*parseFloat(cgdj))){}else{
            $(currenctCGJE.target).numberbox('setValue', (parseFloat(sl)*parseFloat(cgdj)).toFixed(2));
          //设置箱单单价
          var parentId = $('#id').val();
            $.ajax({
        		url:'${ctx}/logistics/downstreamContainer/setCGDJ/'+parentId+"/"+cpContractNo+"/"+spbm+"/"+cgdj,
        		type : 'get',
        		cache : false,
        		success : function(data) {
        			$('#dgContainer').datagrid('reload');
        		}
        	});
            
          //计算完成后 动态 总价footer
            var cgje = (parseFloat(sl)*parseFloat(cgdj)).toFixed(2)
			 var source1 = $('#childTB').datagrid('getSelected').cgje;
            if(isNaN(parseFloat(source1))){
		    	 source1 = 0.00;
			 }
			 if(isNaN(parseFloat(cgje))){
				 cgje = 0.00;
			 }
			 var changeData1 = parseFloat(cgje) - parseFloat(source1);
			 var rows = $('#childTB').datagrid('getRows');
		 	 var totalcgje =0;
				for(var i = 0; i < rows.length; i++){
				    var map = rows[i];
				    for (var key in map){
				    	var currenctValue = map[key];
					    if(key=='cgje'){
					    	if(!isNaN(parseFloat(currenctValue))){
					    	totalcgje = totalcgje + parseFloat(currenctValue);
					    	}
					    }
				    }
				}
				totalcgje = totalcgje + changeData1;
				 var rowsFooter = $('#childTB').datagrid('getFooterRows');
				 rowsFooter[0]['cgje'] = totalcgje.toFixed(2);
				 $('#invoiceAmount').numberbox('setValue',totalcgje.toFixed(2));
			     $('#childTB').datagrid('reloadFooter');
          
            }
	});
   	
  	//件数
	var currenctjs = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'js' });
	currenctjs.target.change(function () {
		 var js = $(currenctjs.target).val();
		 var source = $('#childTB').datagrid('getSelected').js;
		 if(isNaN(parseInt(source))){
			 source = 0;
		 }
		 if(isNaN(parseInt(js))){
			 js = 0;
		 }
		 var changeData = parseInt(js) - parseInt(source);
		 var rows = $('#childTB').datagrid('getRows');
	 	 var totaljs =0;
			for(var i = 0; i < rows.length; i++){
			    var map = rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='js'){
				    	if(!isNaN(parseInt(currenctValue))){
				    	totaljs = totaljs + parseInt(currenctValue);
				    	}
				    }
			    }
			}
			totaljs = totaljs + changeData;
			 var rowsFooter = $('#childTB').datagrid('getFooterRows');
			 rowsFooter[0]['js'] = totaljs;
		     $('#childTB').datagrid('reloadFooter');	
	});
	
	
	currenctcgje.target.change(function () {
		 var cgje = $(currenctcgje.target).val();
		 var source = $('#childTB').datagrid('getSelected').cgje;
		 if(isNaN(parseFloat(source))){
			 source = 0.00;
		 }
		 if(isNaN(parseFloat(cgje))){
			 cgje = 0.00;
		 }
		 var changeData = parseFloat(cgje) - parseFloat(source);
		 var rows = $('#childTB').datagrid('getRows');
	 	 var totalcgje =0;
			for(var i = 0; i < rows.length; i++){
			    var map = rows[i];
			    for (var key in map){
			    	var currenctValue = map[key];
				    if(key=='cgje'){
				    	if(!isNaN(parseFloat(currenctValue))){
				    	totalcgje = totalcgje + parseFloat(currenctValue);
				    	}
				    }
			    }
			}
			totalcgje = totalcgje + changeData;
			 var rowsFooter = $('#childTB').datagrid('getFooterRows');
			 rowsFooter[0]['cgje'] = totalcgje.toFixed(2);
			 $('#invoiceAmount').numberbox('setValue',totalcgje.toFixed(2));
		     $('#childTB').datagrid('reloadFooter');	
	});
	
}

</script>
