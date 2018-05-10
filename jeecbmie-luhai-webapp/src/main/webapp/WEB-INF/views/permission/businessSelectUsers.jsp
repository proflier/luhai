<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<div>
		<input type="text" id="filter_LIKES_userName" name="filter_LIKES_userName" class="easyui-validatebox" data-options="width:150,prompt: '用户名称'"/>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_selectUser()">查询</a>
	    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_selectUser()">重置</a>
	</div>
	<div class="divFrame">
		<table id="group_dg_ztree" class="ztree"></table> 
	</div>
	<div class="divFrame">
		<table id="ur_dg_ztree" class="ztree"></table>
	</div>
	<div class="divFrame">
		<ul id="userShowDiv" style="padding-left: 20px;">
		</ul>
	</div>
</div>
<script type="text/javascript">
var ur_dg_ztree;
var group_dg_ztree;
 function onClick_ztree(event, treeId, treeNode, clickFlag) {
	if(treeNode.flag=='U'){
		users_show.addTreeNode(treeNode);
	}else if(treeNode.flag=='G'){
		var nodes = group_dg_ztree.getNodesByParam('flag','U',treeNode);
		for(var i=0;i<nodes.length;i++){
			users_show.addTreeNode(nodes[i]);
		}
	}else if(treeNode.flag=='O'){
		var nodes = group_dg_ztree.getNodesByParam('flag','U',treeNode);
		for(var i=0;i<nodes.length;i++){
			users_show.addTreeNode(nodes[i]);
		}
	}
} 
var ur_setting = {
	check: {
			enable: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parendId"
		},
		key: {
			name: "name"
		}
	},
	callback: {
		onClick:onClick_ztree
	}
};
 var group_setting = {
		check: {
				enable: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			},
			key: {
				name: "name"
			}
		},
		callback: {
			onClick:onClick_ztree
		}
	};
	var users_show={
		userPs : [],
		 init : function(){
			 var obj = $('#dlg_selectUsers').dialog('options');
	            var queryParams = obj["queryParams"];
	            var themeMemberIds = queryParams['themeMemberIds'];
				var themeMembers = queryParams['themeMembers'];
				var themeMemberKeyIds = queryParams['themeMemberKeyIds'];
				var themeMemberKeys = queryParams['themeMemberKeys'];
				var themeMemberIds_array = new Array();
				var themeMembers_array =  new Array();
				var themeMemberKeyIds_array = new Array();
				var themeMemberKeys_array = new Array();
				if(themeMemberIds!=""){
					themeMemberIds_array = themeMemberIds.split(",");
					themeMembers_array = themeMembers.split(",");
				}
				if(themeMemberKeyIds!=""){
					themeMemberKeyIds_array = themeMemberKeyIds.split(",");
					themeMemberKeys_array = themeMemberKeys.split(",");
				}
				for(var i=0;i<themeMemberIds_array.length;i++){
					var curUserP={};
					curUserP.userId = themeMemberIds_array[i];
					curUserP.userName= themeMembers_array[i];
					curUserP.keyFlag='0';
					for(var j=0;j<themeMemberKeyIds_array.length;j++){
						if(curUserP.userId==themeMemberKeyIds_array[j]){
							curUserP.keyFlag='1';
						}
					}
					this.addUsers(curUserP);
				}
		 },
		 addTreeNode: function(treeNode){
			var userP = {};
			userP.userId = treeNode.id;
			userP.userName = treeNode.name;
			users_show.addUsers(userP);
		 },
      	 addUsers : function(userP){
      		for(var i=0,j=this.userPs.length;i<j;i++){
       			if(userP.userId==this.userPs[i].userId){
       				return;
       			}
       		}
    	   var $userShowDiv =$("#userShowDiv");
	   		var $li = $("<li id="+userP.userId+"></li>");
	   		$li.append("<span class='content'>"+userP.userName+"</span>");
	   		$li.append("<a href='#' onclick='users_show.deleteUser(\""+userP.userId+"\")'><span class='del'/></a>");
	   		$userShowDiv.append($li);
	   		this.userPs.push(userP);
       	},
       	deleteUser : function(userId){
       		for(var i=0,j=this.userPs.length;i<j;i++){
       			if(userId==this.userPs[i].userId){
       				this.userPs.splice(i,1);
       				break;
       			}
       		}
       		$("#"+userId).remove();
       	},
		showUsers : function(userPs_param){
			for(var i=0,j=userPs_param.length;i<j;i++){
				this.addUsers(userPs_param[i]);
			}
		},
		saveSelect : function(){
			var themeMemberIds = "";
			var themeMembers = "";
			var themeMemberKeyIds = "";
			var themeMemberKeys = "";
			for(var i=0;i<this.userPs.length;i++){
				var curUserP = this.userPs[i];
				themeMemberIds = themeMemberIds+curUserP.userId+",";
				themeMembers = themeMembers+curUserP.userName+",";
				if(curUserP.keyFlag=='1'){
					themeMemberKeyIds = themeMemberKeyIds+curUserP.userId+",";
					themeMemberKeys = themeMemberKeys+curUserP.userName+",";
				}
			}
			themeMemberIds = deleteLastChar(themeMemberIds);
			themeMembers = deleteLastChar(themeMembers);
			themeMemberKeyIds = deleteLastChar(themeMemberKeyIds);
			themeMemberKeys = deleteLastChar(themeMemberKeys);
			$("#themeMemberIds").val(themeMemberIds);
			$("#themeMemberKeyIds").val(themeMemberKeyIds);
			$("#themeMembers").text(themeMembers);
			$("#themeMemberKeys").text(themeMemberKeys);
			//更新相关人员
			return themeMemberIds;
		}
	}; 
	function deleteLastChar(str){
		if(str!=""){
			return str.substring(0,str.length-1);
		}else{
			return "";
		}
	}
	$(function (){
		//var t = [	{id:1, userId:1, userName:"赵大",keyFlag:'0'}];
		users_show.init();
		$.ajax({
			async : false,
			url : '${ctx}/permission/group/groupsSelf',
			type : "get",
			dataType : "json",
			success : function(datas) {
				datas.push({'id':'G_0','name':'讨论组'});
				group_dg_ztree = $.fn.zTree.init($("#group_dg_ztree"), group_setting, datas);
				var nodes = group_dg_ztree.getNodes();
	            for (var i = 0; i < nodes.length; i++) { //设置节点展开
	            	group_dg_ztree.expandNode(nodes[i], true, false, true);
	            }
			},
			error : function(event, errors) {
				$.easyui.messager.alert("加载失败");
			}
		});
		//获取树
		$.ajax({
			async : false,
			url : '${ctx}/system/user/orgUserLoginNameList',
			type : "get",
			dataType : "json",
			success : function(datas) {
				ur_dg_ztree = $.fn.zTree.init($("#ur_dg_ztree"), ur_setting, datas);
				var nodes = ur_dg_ztree.getNodes();
	            for (var i = 0; i < nodes.length; i++) { //设置节点展开
	            	ur_dg_ztree.expandNode(nodes[i], true, false, true);
	            }
			},
			error : function(event, errors) {
				$.easyui.messager.alert("加载失败");
			}
		});
	});
	
	function cx_selectUser(){
		$.ajax({
			async : false,
			url : '${ctx}/system/user/orgUserLoginNameList',
			type : "get",
			data : {filter_LIKES_userName: $("#filter_LIKES_userName").val()},
			dataType : "json",
			success : function(datas) {
				ur_dg_ztree = $.fn.zTree.init($("#ur_dg_ztree"), ur_setting, datas);
				var nodes = ur_dg_ztree.getNodes();
	            for (var i = 0; i < nodes.length; i++) { //设置节点展开
	            	ur_dg_ztree.expandNode(nodes[i], true, false, true);
	            }
			},
			error : function(event, errors) {
				$.easyui.messager.alert("加载失败");
			}
		});
	}

	function reset_selectUser(){
		 $("#filter_LIKES_userName").val("");
		$.ajax({
			async : false,
			url : '${ctx}/system/user/orgUserLoginNameList',
			type : "get",
			data : {filter_LIKES_userName: $("#filter_LIKES_userName").val()},
			dataType : "json",
			success : function(datas) {
				ur_dg_ztree = $.fn.zTree.init($("#ur_dg_ztree"), ur_setting, datas);
				var nodes = ur_dg_ztree.getNodes();
	            for (var i = 0; i < nodes.length; i++) { //设置节点展开
	            	ur_dg_ztree.expandNode(nodes[i], true, false, true);
	            }
			},
			error : function(event, errors) {
				$.easyui.messager.alert("加载失败");
			}
		});
	}
</script>
</body>
</html>