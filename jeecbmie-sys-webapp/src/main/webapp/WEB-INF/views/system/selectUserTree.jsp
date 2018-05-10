<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<table id="ur_dg" class="ztree"></table>
<script type="text/javascript">
var ur_dg;
var setting = {
	check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "s", "N": "s" }
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
	}
};
$(function (){
	//获取树
	$.ajax({
		async : false,
		url : '${ctx}/system/user/orgUserList',
		type : "get",
		dataType : "json",
		success : function(datas) {
			ur_dg = $.fn.zTree.init($("#ur_dg"), setting, datas);
			ur_dg.expandAll(true);
			/*  var nodes = ur_dg.getNodes();
			for (var i = 0; i < nodes.length; i++) {
				if (nodes[i].isParent) {
					nodes[i].nocheck = true; //有子节点的去除选择框
					ur_dg.updateNode(nodes[i]);
				}
			} */ 
		},
		error : function(event, errors) {
			$.easyui.messager.alert("加载失败");
		}
	});
	 var userIds_default = '${defaultUserIds}';
	   var userIds_json= eval("("+userIds_default +")");
	 if(userIds_json.length>0){
		for(var i in userIds_json){
			var nodes = ur_dg.getNodesByParam("id", userIds_json[i]); //根据org的ID值获取符合的节点
			for (var j = 0; j < nodes.length; j++) { //对符合的节点进行遍历勾选
				ur_dg.checkNode(nodes[j], true);
			}
		}
	} 
	
});
//保存操作 (返回用户id数组)
 function getSelectedUsers(){
	 var result = new Array();
	 var nodes = ur_dg.getCheckedNodes(true);
	 for(var i = 0;i < nodes.length; i++){
		 var nodeId = nodes[i].id;
		 if(nodes[i].flag=='U'){
			 result.push(nodeId);
		 }
	 }
	 return result;
 }	
	//保存用户机构
	function saveRelationOrg() {
		var newDeptList = [];
		var data = ur_dg.getCheckedNodes(true);
		//所选的机构列表
		for (var i = 0, j = data.length; i < j; i++) {
			newDeptList.push(data[i].id);
		}
		
		$.ajax({
			async : false,
			type : 'POST',
			data : JSON.stringify(newDeptList),
			contentType : 'application/json;charset=utf-8', //必须
			url : "${ctx}/system/userRole/changeOrgs/${relationId}",
			success : function(data) {
				if (data == 'success') {
					parent.$.messager.show({
						title : "提示",
						msg : "操作成功！",
						position : "bottomRight"
					});
				} else {
					$.easyui.messager.alert(data);
				}
				$('#dg').datagrid("reload");
			}
		});
		
	}
</script>
</body>
</html>