<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String accParentId = request.getParameter("accParentId");
String accParentEntity = request.getParameter("accParentEntity");
String dgAcc = "#"+request.getParameter("dgAcc");
%>
<script type="text/javascript">
var curWwwPath = window.document.location.href;
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
var localhostPaht = curWwwPath.substring(0, pos);
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
var mainUrl = localhostPaht+projectName;
var urlValue = mainUrl+'/accessory/jsonAll?filter_EQS_accParentId=' + "<%=accParentId%>"+ '&filter_EQS_accParentEntity=' + "<%=accParentEntity%>";
		$('<%=dgAcc%>').datagrid({
			method : "get",
			fit : false,
			fitColumns : true,
			border : false,
			striped : true,
			singleSelect : true,
			scrollbarSize : 0,
			url : urlValue,//
			idField : 'accId',
			columns : [[
				{
					field : 'accRealName',
					title : '附件名称',
					sortable : true,
					width : 40
				},
				{
					field : 'accAuthor',
					title : '上传人',
					sortable : true,
					width : 10,
					formatter: function(value,row,index){
						var name = '';
						if(value!=null && value!=""){
							$.ajax({
								url : mainUrl+'/system/user/getUserNameByLogin/'+value ,
								type : 'get',
								cache : false,
								async: false,
								success : function(data) {
									name = data;
								}
							});
						}
						return name;
					}
				},
				{
					field : 'accId',
					title : '操作',
					sortable : true,
					width : 20,
					formatter : function(value, row, index) {
						var str = "";
						str += "<a style='text-decoration:none' href='#' onclick='downnloadAcc(" + value + ");'>下载</a>"
						return str;
					}
				} 
			]]
		})
		
	//下载附件
	function downnloadAcc(id) {
		window.open(mainUrl+"/accessory/download/" + id, '下载');
	}
</script>