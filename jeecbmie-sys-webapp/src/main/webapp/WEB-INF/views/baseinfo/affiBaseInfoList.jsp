<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
       	        <input type="text" name="filter_LIKES_customerCode_OR_customerName" class="easyui-validatebox" data-options="width:150,prompt: '客户编码或客户名称'"/>
       	         <span class="toolbar-item dialog-tool-separator"></span>
		        <input type="text" id="filter_customerType" name="filter_customerType" data-options="width:150,prompt: '客户类别'"/>
		         <span class="toolbar-item dialog-tool-separator"></span>
		        <input type="text" id="filter_Hperson" name="filter_Hperson" class="easyui-validatebox" data-options="width:150,prompt: '业务经办人'"/>
		         <span class="toolbar-item dialog-tool-separator"></span>
		        <input type="text" id="filter_Rperson" name="filter_Rperson" class="easyui-combotree" data-options="width:250,prompt: '相关人员'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
			</form>
	       	<shiro:hasPermission name="sys:affiliates:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:affiliates:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:affiliates:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:affiliates:detail">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	        </shiro:hasPermission>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="setBusenessPermission()">相关人员</a>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg" ></div>  
<div id="dlg_selectUsers"></div> 
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/baseinfo/affiliates/jsonNew', 
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'customerCode',title:'客户编码',width:10},  
			{field:'customerName',title:'客户名称',width:15},
			{field:'customerEnName',title:'英文名称',width:15},
			{field:'customerType',title:'客户类别',width:10,
				formatter: function(value,row,index){
					var val;
					if(row.customerType!=''&&row.customerType!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/dict/getAllName/'+value+'/KHLX',
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
			{field:'updaterName',title:'业务经办人',width:10,
				formatter: function(value,row,index){
					var val;
					if(row.customerCode!=''&&row.customerCode!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/customerPerssion/getBusinessManager/'+row.customerCode,
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
			{field:'updaterNo',title:'相关人员',width:20,
				formatter: function(value,row,index){
					var val;
					if(row.customerCode!=''&&row.customerCode!=null){
						$.ajax({
							type:'GET',
							async: false,
							url : '${ctx}/system/customerPerssion/getRanges/'+row.customerCode,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						if(val.length>20){
							var returnValue = val.substring(0,20)+"...";
		                	return "<span title='" + val + "'>" + returnValue + "</span>";
						}else{return val;}
					}else{
						return '';
					}
				}	
			},
			{field:'contactPerson',title:'联系人',width:15},
			{field:'createDate',title:'创建时间',width:10},
			{field:'status',title:'状态',width:5,
				formatter: function(value,row,index){
					if (row.status == 0){
						return '停用';
					} else{
						return '启用';
					}
				}	
			}
	    ]],
	    sortName:'id',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	$('#filter_Rperson').combotree({   
		idField : 'loginName',
	    textFiled : 'name',
	    parentField : 'pid',
		method:'GET',
	    url : '${ctx}/workflow/getCompanyUser',
//		    multiple:true,
	    lines:true,
	    editable:false,
	    onSelect:function(node){
			//返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#filter_Rperson').combotree('clear');  
	        }  
		},
	    onHidePanel:function(){
	    },
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增单位信息',    
	    href:'${ctx}/baseinfo/affiliates/create',
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				accept();
				acceptCustomer();
				acceptContact();
				$("#mainform").submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/baseinfo/affiliates/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改关联单位信息',    
	    href:'${ctx}/baseinfo/affiliates/update/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				accept();
				acceptCustomer();
				acceptContact();
				$('#mainform').submit(); 
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '关联单位明细',    
	    href:'${ctx}/baseinfo/affiliates/detail/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit:true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}


function setBusenessPermission(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(!(row.customerType.match(/^([0-9]*,)*10230002(,[0-9]*)*$/) ||row.customerType.match(/^([0-9]*,)*10230003(,[0-9]*)*$/))){
		$.messager.alert('提示','当前客户无需配置相关人员','info');
		return;
	}
	var themeMemberIds = "";
	var themeMemberKeyIds = "";
	var themeMembers = "";
	var themeMemberKeys = "";
	$.ajax({
		async : false,
		url : '${ctx}/system/customerPerssion/initSelect/'+row.customerCode,
		type : "get",
		dataType:"json",  //数据类型是JSON
		success : function(data) {
			themeMembers=data.themeMembers;
			themeMemberIds = data.themeMemberIds;
			themeMemberKeys = data.themeMemberKeys;
			themeMemberKeyIds = data.themeMemberKeyIds;
			}
		});
	//todo
	var selectUsers_dg=$("#dlg_selectUsers").dialog({   
	    title:"  <font color='#FF4500'>" +row.customerName+'</font>客户相关人员',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/system/customerPerssion/userRealtion/',
	    modal:true,
	    queryParams:{'themeMemberIds':themeMemberIds,'themeMemberKeyIds':themeMemberKeyIds,'themeMembers':themeMembers,'themeMemberKeys':themeMemberKeys},
	    buttons:[{
			text:'保存',iconCls:'icon-save',
			handler:function(){
				users_show.saveSelect(row);
				selectUsers_dg.panel('close');
				dg.datagrid('reload');
			}
		},{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				selectUsers_dg.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function cx(){
	/* var array = new Array();
	$("input[name='customerType']").each(function(index, item){
		array.push(item.value);
	});
	var customerType = array.join(","); */
	var obj=$("#searchFrom").serializeObject();
	obj.filter_customerType = $('#filter_customerType').combobox('getValues').join(",");
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$('#filter_customerType').combobox('clear');
	$('#filter_Rperson').combotree('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}


//客户类型
$('#filter_customerType').combobox({
	method:'GET',
	url:'${ctx}/system/dict/getDictByCode/KHLX',
    textField : 'name',
    valueField:'code',
    multiple:true,
    width:250,
	panelHeight:'auto',
	editable:false,
    prompt: '此选项可以多选，点击X重置选择',
   /*  onLoadSuccess: function () { 
        var val = $(this).combobox("getData"); 
        $(this).combobox("clear");
   		var curValue = new Array();
   		curValue = this.value.split(',');
   		for(var j=0;j<curValue.length;j++){
   		 	for(var i = 0;i<val.length;i++ ){ 
	            if (val[i].value==curValue[j]) { 
	                $(this).combobox("select",curValue[j]); 
	            } 
	        } 
   		}
    }, */
    onHidePanel:function(){}
});
</script>
</body>
</html>