<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="tbGoods" style="padding:5px;height:auto">
     <div>	
     	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="sub_obj.addSubs();">添加</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="sub_obj.updateSubs();">修改</a>
   		<span class="toolbar-item dialog-tool-separator"></span>
   		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="sub_obj.deleteSubs();">删除</a>
     </div> 
</div>
<table id="dg_subs" ></table>
<script type="text/javascript">
var sub_obj = {
	subs_list : '',
	subs_form : '',
	reload:function(){
		this.subs_list.datagrid('reload');
	},
	listSubs : function(){
		var urlValue;
		this.subs_list=$('#dg_subs').datagrid({  
			method: "get",
			url:$('#id').val() ? '${ctx}/logistic/orderShipContractSub/json' : '', 
		  	fit : false,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			striped:true,
			idField : 'id',
			rownumbers:true,
			singleSelect:true,
			extEditing:false,
			queryParams: {
				'filter_EQL_orderShipContractId': $('#id').val()
			},
		    columns:[[    
				{field:'id',title:'id',hidden:true},
				{field:'shipNo',title:'船编号',width:20},
				{field:'ship',title:'船名',width:30,
					formatter: function(value,row,index){
						var val;
						if(row.shipNo!=''&& row.shipNo!=null){
							$.ajax({
								type:'GET',
								async: false,
								url:"${ctx}/logistic/shipTrace/shipNameShow/"+row.shipNo,
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
				{field:'loadBeginDate',title:'装货开始时间',width:20,
					formatter:function(value,row,index){
						if(value == null){
							return null;
						}
	            		var time = new Date(value);
	            		return time.format("yyyy-MM-dd");
	            	}}, 
				{field:'loadEndDate',title:'装货结束时间',width:20,
	            		formatter:function(value,row,index){
	    					if(value == null){
	    						return null;
	    					}
	                		var time = new Date(value);
	                		return time.format("yyyy-MM-dd");
	                	}}, 
				{field:'numMoreOrLess',title:'溢短装%',width:20}, 
				{field:'tradePriceRate',title:'运费单价',width:20}, 
				{field:'loadRate',title:'装率',width:20}, 
				{field:'unloadRate',title:'卸率',width:20}, 
				{field:'demurrageRate',title:'滯期率',width:20}
		    ]],
		    sortName:'id',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    toolbar:'#tbGoods'
		});
	},
	addSubs : function(){
		this.subs_form=$("#dlg_subs").dialog({   
		    title: '添加船信息',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistic/orderShipContractSub/create/'+$("#id").val(),
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$('#subMainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					sub_obj.subs_form.panel('close');
				}
			}]
		});
	},
	updateSubs : function(){
		var row = this.subs_list.datagrid('getSelected');
		if(rowIsNull(row)) return;
		this.subs_form=$("#dlg_subs").dialog({   
		    title: '修改船信息',    
		    width: 700,    
		    height: 400,
		    href:'${ctx}/logistic/orderShipContractSub/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$('#subMainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					sub_obj.subs_form.panel('close');
				}
			}]
		});
	},
	deleteSubs : function(){
		var row = this.subs_list.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					dataType:'json',
					url:'${ctx}/logistic/orderShipContractSub/delete/'+row.id,
					success: function(data){
						if(data.returnFlag=='success'){
							if(sub_obj.subs_list!=null){
								sub_obj.subs_list.datagrid('clearSelections');
								sub_obj.subs_list.datagrid('reload');
							}
							parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
							return true;
						}else if(data.returnFlag=='fail'){
							parent.$.messager.alert(data.returnMsg);
							return false;
						}  
					}
				});
			} 
		});
	}
};
$(function(){
	sub_obj.listSubs();
});
</script>