<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	 <table id="userTree"></table>
<script type="text/javascript">
var userData;
var userTree;
$(function(){
	userTree=$('#userTree').treegrid({  
		method: "get",
	    url:'${ctx}/system/user/orgUserList', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		treeField:'name',
		parentField : 'parendId',
		animate:true, 
		singleSelect:false,
		striped:true,
	    columns:[[    
	        {field:'id',checkbox:true,width:100},    
	        {field:'name',title:'用户',width:100},
	    ]],
	    onClickRow:function(row){  
            //级联选择  
            $(this).treegrid('cascadeCheck',{  
                id:row.id, //节点ID  
                deepCascade:true //深度级联  
            });  
        },
        onLoadSuccess:function(){
        	searchUser();
        }
		});
});

function searchUser(){
	var row = dg_role.datagrid('getSelected');
	var roleId=row.id;
	//清空勾选的权限
	if(userData){
		userTree.treegrid('unselectAll');
		userData=[];//清空
	}
	//获取角色拥有权限
	$.ajax({
		async:false,
		type:'get',
		url:"${ctx}/system/user/"+roleId+"/searchUser",
		success: function(data){
			if(typeof data=='object'){
				userData=data;
				for(var i=0,j=data.length;i<j;i++){
					userTree.treegrid('select',data[i]);
				}
			}else{
				$.easyui.messager.alert(data);
			} 
		},
		headers: {
			"Accept": "application/json" //配置返回数据类型application/xml
		}
	});
}

function saveUserInRole(roleId){
	var newUserList=[];
	var data=userTree.treegrid('getSelections');
	for(var i=0,j=data.length;i<j;i++){
		newUserList.push(data[i].id);
	}
	
	$.ajax({
		async:false,
		type:'POST',
		data:JSON.stringify(newUserList),
		contentType:'application/json;charset=utf-8',
		url:"${ctx}/system/user/"+roleId+"/updateUserInRole",
		success: function(data){
			dg_user.datagrid('reload');
		}
	});
}

function saveRelationInRole(roleId){
	var newUserList=[];
	var data=userTree.treegrid('getSelections');
	for(var i=0,j=data.length;i<j;i++){
		newUserList.push(data[i].id);
	}
	
	$.ajax({
		async:false,
		type:'POST',
		data:JSON.stringify(newUserList),
		contentType:'application/json;charset=utf-8',
		url:"${ctx}/system/user/"+roleId+"/updateUserInRole",
		success: function(data){
			dg_user.datagrid('reload');
		}
	});
}
</script>
</body>
</html>