<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addChild();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateChild();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteChild();">删除</a>
     </div> 
</div>
<table id="dgChild" ></table>
<script type="text/javascript">
var dgChild;
var idValue;
initStreamContainer();
function initStreamContainer(value){
	//进行编辑时使用方法
	var urlValue;
	idValue = $('#id').val();
	if(idValue!=null&&idValue!=""){
		urlValue =  '${ctx}/financial/paymentConfirmChild/getChilds/'+idValue;
	}else{
		urlValue="";
	}
	dgChild=$('#dgChild').datagrid({  
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
			{field:'feeCategoryView',title:'费用类别',sortable:true,width:20},
			{field:'paymentType',title:'分摊',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&&value!=null){
						if(value=='sale'){
							val="销售合同";
						}else if(value=='kehu'){
							val="客户";
						}else{
							val="船编号";
						}
						return val;
					}else{
						return '';
					}
				}	
			},
			{field:'code',title:'摘要',sortable:true,width:20,
				formatter: function(value,row,index){
					var val;
					if(value!=''&& value!=null&&row.paymentType=='ship'){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/logistic/shipTrace/shipNameShow/"+value,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else if(value!=''&& value!=null&&row.paymentType=='sale'){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/sale/saleContract/getContractNoCustomer/"+value,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else if(value!=''&& value!=null&&row.paymentType=='kehu'){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
						return "";
					}
				}	
			},
			{field:'shareAmount',title:'分摊金额',sortable:true,width:20}
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tbGoods'
	});
}


//弹窗增加
function addChild() {
	var idValue = $('#id').val();
	if(idValue == ''){
		$.messager.alert('提示','请先保存付款信息!!!','info');
		return;
	}
	dlg_goods=$("#dlg_goods").dialog({  
		method:'GET',
	    title: '新增付款详情',    
	    width: 300,    
	    height:190,     
	    href:'${ctx}/financial/paymentConfirmChild/create/'+idValue,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$('#childForm').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_goods.panel('close');
			}
		}]
	});
}


//删除商品
function deleteChild(){
	var row = dgChild.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:'${ctx}/financial/paymentConfirmChild/delete/'+row.id,
				success: function(data){
					successTip(data,dgChild);
				}
			});
		} 
	});
}

//弹窗修改采购信息
function updateChild() {
	var row = dgChild.datagrid('getSelected');
	if(rowIsNull(row)) return;
	dlg_goods=$("#dlg_goods").dialog({   
	    title: '修改付款详情',    
	    width: 300,    
	    height: 190,
	    href:'${ctx}/financial/paymentConfirmChild/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				$('#childForm').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				dlg_goods.panel('close');
			}
		}]
	});
}

</script>