<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkBak"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMxBak"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMx"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/woodCght/woodCghtJK/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '合同状态修改', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th>合同状态</th>
						<td >
							<input id="closeOrOpen" name="closeOrOpen" type="text" value="${woodCghtJk.closeOrOpen }" class="easyui-validatebox"  />
							<input name="id" id="id"type="hidden"  value="${woodCghtJk.id }" />
						</td>
						<th  >综合合同号</th>
						<td >
							${woodCghtJk.interContractNo }
						</td>
					</tr>
					<tr>
						<th  >采购合同号</th>
						<td>
							${woodCghtJk.hth }
							<input name="id" type="hidden"  value="${woodCghtJk.id }" />
						</td>
						<th  >协议号</th>
						<td>
							${woodCghtJk.xyh }
						</td>
					</tr>
					<tr>
						<th  >供货单位</th>
						<td id="ghdw">
						</td>
						<th  >国别</th>
						<td id="jkgb">
						</td>
					</tr>
					<tr>
						<th  >价格条款</th>
						<td id="jgtk">
						</td>
						<th  >应预付额</th>
						<td>
							${woodCghtJk.yyfe }
						</td>
					</tr>
					<tr>
						<th  >订货总量</th>
						<td>
							${woodCghtJk.dhzl }
						</td>
						<th  >数量单位</th>
						<td id="sldw">
						</td>
					</tr>
					<tr>
						<th  >定金</th>
						<td>
							${woodCghtJk.dj }
						</td>
						<th  >货款</th>
						<td>
							${woodCghtJk.hk }
						</td>
					</tr>
					 <tr>
						<th  >币种</th>
						<td id="cgbz">
						</td>
						<th  >汇率</th>
						<td>
							${woodCghtJk.hl }
						</td>
					</tr>
					<tr>
						<th  >收款方式 </th>
						<td id="skfs">
						</td>
						<th  >授信类型</th>
						<td id="sxlx">
						</td>
					</tr>
					<tr>
						<th  >溢短(%)</th>
						<td id="yd">
						</td>
						<th  >订货日期</th>
						<td>
							<fmt:formatDate value="${woodCghtJk.dhrq }" />
						</td>
					</tr>
					<tr>
						<th  >交货起期</th>
						<td>
							<fmt:formatDate value="${woodCghtJk.jhqq }" />
						</td>
						<th  >交货止期</th>
						<td>
						<fmt:formatDate value="${woodCghtJk.jhzq }" />
						</td>
					</tr>
					<tr>
						<th  >订货单位  </th>
						<td id="dhdw">
						</td>
						<th  >收货单位</th>
						<td id="sfdw">
						</td>
					</tr>
					<tr>
						<th  >业务员 </th>
						<td>
						${sessionScope.user.name}
						</td>
						<th  >合同类别</th>
						<td id="htlb">
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${sessionScope.user.name}</td>
						<th  >登记部门</th>
						<td>${sessionScope.user.organization.orgName }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '子表信息', iconCls: false, refreshable: false">
					<table id="childTB" ></table>
					<%
						WoodCghtJk woodCghtJk = (WoodCghtJk)request.getAttribute("woodCghtJk");
						List<WoodCghtJkMx> woodCghtJkMxList = woodCghtJk.getWoodCghtJkMxList();
						ObjectMapper objectMapperCus = new ObjectMapper();
						String childJson = objectMapperCus.writeValueAsString(woodCghtJkMxList);
						request.setAttribute("childJson", childJson);
					%>
			</div>	
			<div data-options="title: '变更记录', iconCls: false, refreshable: false">
				<%
					List<WoodCghtJkBak> woodCghtJkBakList = (List<WoodCghtJkBak>)request.getAttribute("woodCghtJkBakList");
					ObjectMapper objectMapperBak = new ObjectMapper();
					String childJsonBak = objectMapperBak.writeValueAsString(woodCghtJkBakList);
					request.setAttribute("childJsonBak", childJsonBak);
				%>
				<table id="childTBBak" ></table>
				<div id="dlg_bak">
				</div>
		</div>		
		<div data-options="title: '附件', iconCls: false, refreshable: false">
			<input id="accParentId" type="hidden"  value="<%=WoodCghtJk.class.getName().replace(".","_") %>" />
			<div id="dgAcc"  class="tableClass"></div>
		</div>	
		</div>	
		</form>
	</div>
<script type="text/javascript">


$(function() {
	
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    }
	});
	
	$('#mainform').form({
		onSubmit : function() {
			var isValid = $(this).form('validate');
			//alert(isValid);
			return isValid; // 返回false终止表单提交
		} ,
		success : function(data) {
			successTipNew(data,dg);
		} 
	});
	
	if('${woodCghtJk.ghdw }'!=''){
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${woodCghtJk.ghdw }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#ghdw').text(data);
			}
		});
	}
		
	
	//合同状态
	$('#closeOrOpen').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/downMenu/getDictByCode/closeOrOpen',
		valueField : 'name',
		textField : 'name'
	});	
	
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${woodCghtJk.dhdw }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#dhdw').text(data);
			}
		});
	
		
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/jgtk/${woodCghtJk.jgtk }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#jgtk').html(data);
			}
		});
		
		
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/sldw/${woodCghtJk.sldw }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#sldw').html(data);
			}
		});
		
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/CURRENCY/${woodCghtJk.cgbz }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#cgbz').html(data);
			}
		});
		
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/skfs/${woodCghtJk.skfs }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#skfs').html(data);
			}
		});
		
		if('${woodCghtJk.sxlx }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/sxlx/${woodCghtJk.sxlx }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#sxlx').html(data);
				}
			});
		}
		
		$.ajax({
			url : '${ctx}/system/downMenu/getBaseInfoName/${woodCghtJk.sfdw }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#sfdw').text(data);
			}
		});

		$.ajax({
			url : '${ctx}/system/countryArea/getName/${woodCghtJk.jkgb }',
			type : 'get',
			cache : false,
			success : function(data) {
				$('#jkgb').html(data);
			}
		});

		
		$.ajax({
			url : '${ctx}/system/downMenu/getDictName/htlb/${woodCghtJk.htlb }' ,
			type : 'get',
			cache : false,
			success : function(data) {
				$('#htlb').html(data);
			}
		});
		
		$('#yd').html("${woodCghtJk.yd }"+"%");
	
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
								width : 10
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


var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${childJson}'),
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
		{field:'spbm',title:'商品编码',width:25,editor:{type:'validatebox',options:{required:true}}},
	    {field:'spmc',title:'商品名称',width:25, editor:{type:'validatebox',options:{required:true}}},
        {field:'spms',title:'商品描述',width:25, editor:{type:'validatebox'}},
   		{field:'sfbwwz',title:'是否濒危物种',width:15, 
        	editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/YESNO'
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.sfbwwz!=''&&row.sfbwwz!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/YESNO/"+row.sfbwwz,
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
     	{field:'dptj',title:'单片体积',width:15, editor:{type:'numberbox',options:{min:0,precision:3}}},
     	{field:'ps',title:'片数',width:15, editor:{type:'numberbox',options:{min:0}}},
     	{field:'js',title:'件数 ',width:15, editor:{type:'numberbox',options:{min:0}}},
     	{field:'lfs',title:'数量 ',width:15, editor:{type:'validatebox'}},
     	{field:'sldw',title:'数量单位',width:10, 
     		editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/sldw',
					required:true
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.sldw!=''&&row.sldw!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/sldw/"+row.sldw,
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
     	{field:'cgdj',title:'采购单价',width:15,  editor:{type:'numberbox',options:{min:0,precision:2}}},
     	{field:'cgbz',title:'币种 ',width:10,
     		editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'id',
					textField:'name',
					method:'get',
					url:'${ctx}/system/downMenu/getDictByCode/CURRENCY',
					required:true
				}
			},
			formatter: function(value,row,index){
				var val;
				if(row.cgbz!=''&&row.cgbz!=null){
					$.ajax({
						type:'GET',
						async: false,
						url:"${ctx}/system/downMenu/getDictName/CURRENCY/"+row.cgbz,
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
     	{field:'cgje',title:'采购金额 ',width:15,editor:{type:'numberbox',options:{min:0,precision:2}}},
     	{field:'yjhdgg',title:'预计货到港口',width:15,
     		formatter: function(value,row,index){
				var val;
				if(row.yjhdgg!=''&&row.yjhdgg!=null){
					$.ajax({
						type:'GET',
						async: false,
						url : '${ctx}/system/downMenu/getPortName/'+row.yjhdgg ,
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
			},
			editor:{
				type:'combobox',
				options:{
					method: "get",
					url : '${ctx}/system/downMenu/jsonGK',
					valueField : 'id',
					textField : 'gkm',
				}
			}			
     	}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var childTBBak;
$(function(){
	childTBMxBak=$('#childTBBak').datagrid({
	data : JSON.parse('${childJsonBak}'),
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
		{field:'changeReason',title:'变更原因',sortable:true,width:15},
		{field:'updateDate',title:'变更时间',sortable:true,width:15},
		{field:'hth',title:'合同号',sortable:true,width:25}, 
		{field:'xyh',title:'协议号',sortable:true,width:30},
		{field:'ghdw',title:'供货单位',sortable:true,width:15},
		{field:'cgbz',title:'币种',sortable:true,width:15},
     	{field:'id',title:'查看记录',sortable:true,width:15,
     		formatter : function(value, row, index) {
			var str = "";
			str += "<a   class='easyui-linkbutton' iconCls='icon-search' plain='true' href='javascript:void(0)'  onclick='showChange("
					+ value
					+ ");'>查看变更</a>"
			return str;
		}},
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

function showChange(idValue){
	d_change=$("#dlg_bak").dialog({   
	    title: '变更记录',    
	    width: 1000,    
	    height: 450,  
	    style:{borderWidth:0},
	    closable:false,
	    href:'${ctx}/woodCght/woodCghtJK/showChange/'+idValue,
	    modal:true,
	    buttons:[
	             {
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				d_change.panel('close');
			}
		}]
	});
	
}

	
</script>
</body>
</html>