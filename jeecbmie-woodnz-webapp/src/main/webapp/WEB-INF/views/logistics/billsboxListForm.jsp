<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbBox" style="padding:5px;height:auto">
        <div>
	       	<shiro:hasPermission name="sys:woodBillsBox:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBox();">新增箱单</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodBillsBox:update">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updBox();">修改</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodBillsBox:delete">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delBox();">删除</a>
	       	</shiro:hasPermission>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	<shiro:hasPermission name="sys:woodBillsBox:add">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCopyBox();">复制新增箱单</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:woodBillsBox:add">
		       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchGoodsCode()">查看商品编码</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
        </div> 
        
</div>
<style type="text/css">
    .datagrid-footer td{font-weight: bold;color:black}
</style>
<table id="dgBox" ></table>
<script type="text/javascript">
var dgBox;
var dBox;
$(function(){
	dgBox=$('#dgBox').datagrid({  
		method: "get",
		url:'${ctx}/logistics/bills/getBoxList', 
// 		width: 1360,    
// 	    height: 507, 
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
			{field:'boxNo',title:'集装箱号',sortable:true,width:20},
			{field:'cpContractNo',title:'综合合同号',sortable:true,width:20},
			{field:'goodsName',title:'商品名称',sortable:true,width:35},
			{field:'purchasePrice',title:'采购价',sortable:true,width:20},
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
			{field:'waterRate',title:'含水率(%)',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
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
	    groupField:'boxNo',
	    view: groupview,
		groupFormatter:function(value, rows){
			var totalNum = 0;//数量和
			var totalPieceNum = 0;//件数和
			var totalPNum = 0;//片数和=片数*件数之和
			var totalPrice = 0;//总价和
			var pNumPieceNum = 0;//片数*件数
			for(var i=0;i<rows.length;i++){
				totalNum =  totalNum + parseFloat(rows[i].num);
				totalPieceNum =  totalPieceNum + parseFloat(rows[i].pieceNum);
				totalPrice =  totalPrice + parseFloat(rows[i].totalPrice);
				totalPNum = totalPNum + parseFloat(rows[i].pieceNum)*parseFloat(rows[i].pNum);//片数*件数之和
			}
			if(rows[0].boxNo == null){return '合计 : ';}
			return ' 集装箱号 ' +  '：   ' + value+' - 数量和: '+totalNum.toFixed(3)+' - 件数和: '+totalPieceNum.toFixed(3)
			+ ' - 片数和: '+totalPNum.toFixed(3)+ ' - 总价和: '+totalPrice.toFixed(2);
		},
		 onLoadSuccess: function(data){
		    	onCountBox();
		 },
	    sortName:'boxNo',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    showFooter: true,
	    toolbar:'#tbBox'
	});
});

//增加求和统计行
function onCountBox() {
	var rows = dgBox.datagrid('getFooterRows');
	rows[0]['num'] = computeBox("num");
	rows[0]['pieceNum'] = computeBox("pieceNum");
	rows[0]['pNum'] = computePieceNum();
	rows[0]['totalPrice'] = computeBox("totalPrice");
	dgBox.datagrid('reloadFooter');
	$('#invoiceAmount').numberbox('setValue', computeBox("totalPrice"));
	$('#numbers').numberbox('setValue', computeBox("num"));
 }

//片数列求和
function computePieceNum() {
    var rows = dgBox.datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
    	total += parseFloat(rows[i].pieceNum) * parseFloat(rows[i].pNum);
    }
    return total.toFixed(3);
}

//指定列求和
function computeBox(colName) {
    var rows = dgBox.datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
      total += parseFloat(rows[i][colName]);
    }
    if(colName=='totalPrice')
    	return total.toFixed(2);
    else if(colName=='num' || colName=='pieceNum' || colName=='pNum')
    	return total.toFixed(3);
    else
    	return total.toFixed(0);
}

//弹窗查看编码
function searchGoodsCode() {
	d_goodsCode=$("#search4GoodsCode").dialog({   
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
</script>