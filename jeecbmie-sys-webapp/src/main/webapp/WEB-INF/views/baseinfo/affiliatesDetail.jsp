<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBaseInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiBankInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiCustomerInfo"%>
<%@page import="com.cbmie.baseinfo.entity.WoodAffiContactInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/affiliates/${action}" method="post">
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '单位基本信息', iconCls: false, refreshable: false">
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%">客户编码</th>
					<td colspan="3">${affiBaseInfo.customerCode }</td>
				</tr>
				<tr>
					<th width="20%">客户名称</th>
					<td width="30%">${affiBaseInfo.customerName }</td>
					<th width="20%">客户英文名</th>
					<td width="30%">${affiBaseInfo.customerEnName }</td>
				</tr>
				
				<tr>
					<th>法定代表人姓名</th>
					<td>${affiBaseInfo.legalName }</td>
					<th>法定代表人身份号码</th>
					<td>${affiBaseInfo.idCardNO }</td>
				</tr>
				<tr>
					<th>联系人</th>
					<td>${affiBaseInfo.contactPerson }</td>
					<th>联系电话</th>
					<td>${affiBaseInfo.phoneContact }</td>
				</tr>
				<tr>
					<th>传真</th>
					<td>${affiBaseInfo.fax }</td>
					<th>邮编</th>
					<td>${affiBaseInfo.zipCode }</td>
				</tr>
				<tr>
					<th>国别地区</th>
					<td id="affiBaseCountry" ></td>
					<th>国内税务编号</th>
					<td>${affiBaseInfo.internalTaxNO }</td>
				</tr>
				<tr>
					<th>业务范围</th>
					<td>${affiBaseInfo.businessScope }</td>
					<th>网址</th>
					<td>${affiBaseInfo.internetSite }</td>
				</tr>
				<tr>
					<th>地址</th>
					<td >${affiBaseInfo.address }</td>
					<th>邮箱</th>
					<td>${affiBaseInfo.email }</td>
				</tr>
				<tr>
					<th>客户类型</th>
					<td id="affiBaseCusType">
					</td>
					<th>状态</th>
					<td>
						${affiBaseInfo.status == 0?'停用':'启用'}
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">${affiBaseInfo.comments }</td>
				</tr>
				<tr>
				<th>登记人</th>
				<td>
					${affiBaseInfo.createrName }
				</td>
				<th>登记部门</th>
				<td>
					${affiBaseInfo.createrDept }
				</td>
			</tr>
			</table>
		</div>
		
		<div data-options="title: '联系人信息', iconCls: false, refreshable: false">
			<input type="hidden" name="affiContactJson" id="affiContactJson"/>
			<table id="childTBContact" ></table>
			<%
				WoodAffiBaseInfo affiBaseInfoCon = (WoodAffiBaseInfo)request.getAttribute("affiBaseInfo");
							List<WoodAffiContactInfo> affiContactList = affiBaseInfoCon.getAffiContactInfo();
							ObjectMapper objectMapper2 = new ObjectMapper();
							String abcJson = objectMapper2.writeValueAsString(affiContactList);
							request.setAttribute("abcJson", abcJson);
			%>
		</div>	
		
		<div data-options="title: '银行信息', iconCls: false, refreshable: false">
			<input type="hidden" name="affiBankJson" id="affiBankJson"/>
			<table id="childTB" ></table>
			<%
				WoodAffiBaseInfo affiBaseInfo = (WoodAffiBaseInfo)request.getAttribute("affiBaseInfo");
							List<WoodAffiBankInfo> affiBankList = affiBaseInfo.getAffiBankInfo();
							ObjectMapper objectMapper = new ObjectMapper();
							String abJson = objectMapper.writeValueAsString(affiBankList);
							request.setAttribute("abJson", abJson);
			%> 
		</div>	
		
		<div data-options="title: '客户评审', iconCls: false, refreshable: false">
			<input type="hidden" name="affiCustomerJson" id="affiCustomerJson"/>
			<table id="childTBCus" ></table>
			<%
				WoodAffiBaseInfo affiBaseInfoCus = (WoodAffiBaseInfo)request.getAttribute("affiBaseInfo");
						List<WoodAffiCustomerInfo> affiCustomerList = affiBaseInfoCus.getAffiCustomerInfo();
						ObjectMapper objectMapperCus = new ObjectMapper();
						String customerJson = objectMapperCus.writeValueAsString(affiCustomerList);
						request.setAttribute("customerJson", customerJson);
			%>
		</div>
		<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=WoodAffiBaseInfo.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${affiBaseInfo.id}" />
				<div id="dgAcc"  class="tableClass"></div>
		</div>		
	</div>
	</form>
</div>

<script type="text/javascript">

$(function () {
	$.ajax({
		url : '${ctx}/system/countryArea/getName/' + ${affiBaseInfo.country },
		type : 'get',
		cache : false,
		success : function(data) {
			$('#affiBaseCountry').html(data);
		}
	});


});

$(function () {
	$.ajax({
		url : '${ctx}/system/dict/getAllName/' + '${affiBaseInfo.customerType }'+"/"+"KHLX",
		type : 'get',
		cache : false,
		success : function(data) {
			$('#affiBaseCusType').text(data);
		}
	});
});




var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${abJson}'),
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
		{field:'bankName',title:'银行名称',width:20,editor:{type:'validatebox',options:{required:true}}},
		{field:'bankNO',title:'帐号',width:20,editor:{type:'validatebox',options:{required:true}}},
		{field:'partitionInfo',title:'省市信息',width:20,editor:{type:'validatebox',options:{required:true}}},
		{field:'contactPerson',title:'联系人',width:20,editor:{type:'validatebox',options:{required:true}}},
		{field:'phoneContact',title:'联系电话',width:20,editor:{type:'validatebox',options:{required:true}}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var childTBContact;
$(function(){
	childTB=$('#childTBContact').datagrid({
	data : JSON.parse('${abcJson}'),
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
	  		{field:'name',title:'姓名',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'addr',title:'地址',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'position',title:'职位',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'tel',title:'电话',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'fax',title:'传真',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'email',title:'email',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'breed',title:'主营品种',width:20,editor:{type:'validatebox',options:{required:true}}},
	  		{field:'mobil',title:'手机',width:20,editor:{type:'validatebox',options:{required:true}}}
	      ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false
   
	});
});

var childTBCus;
$(function(){
	childTBCus=$('#childTBCus').datagrid({
	data : JSON.parse('${customerJson}'),
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
		{field:'creditLine',title:'授信业务额度',width:20,editor:{type:'validatebox',options:{required:true}}},
	    {field:'checkStartTime',title:'评审有效开始日期',width:25, editor:{type:'datebox',options:{required:true}}},
        {field:'checkEndTime',title:'评审有效结束日期',width:25, editor:{type:'datebox',options:{required:true}}       },
		{field:'customerAndConditions',title:'客户简介及生产经营情况等',width:20,editor:{type:'validatebox',options:{required:true}}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar2'
	});
});


var urlValue = "${ctx}/accessory/json?filter_EQS_accParentId="
	+ $('#accParentId').attr('value');
var dgAcc;
$(function() {
dgAcc = $('#dgAcc')
		.datagrid(
				{
					method : "get",
					fit : false,
					fitColumns : true,
					border : false,
					striped : true,
					singleSelect : true,
					scrollbarSize : 0,
					url : urlValue,
					idField : 'accId',
					columns : [ [
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
											url : '${ctx}/system/user/getUserNameByLogin/'+value ,
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
									str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
											+ value + ");'>下载</a>"
									return str;
								}
							} ] ]
				})
});

//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}

</script>
</body>
</html>