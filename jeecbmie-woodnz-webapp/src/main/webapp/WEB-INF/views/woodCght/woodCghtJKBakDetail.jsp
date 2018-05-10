<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkBak"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMxBak"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMx"%>
<%@page import="com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
		<form id="mainform"  action="" method="post">
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th  >综合合同号</th>
						<td colspan="3">
							${woodCghtJkBak.interContractNo }
						</td>
					</tr>
					<tr>
						<th width="20%" >采购合同号</th>
						<td width="30%">
							${woodCghtJkBak.hth }
						</td>
						<th width="20%" >协议号</th>
						<td width="30%">
							${woodCghtJkBak.xyh }
						</td>
					</tr>
					<tr>
						<th  >供货单位</th>
						<td id="ghdw_bak">
						</td>
						<th  >国别</th>
						<td id="jkgb_bak">
						</td>
					</tr>
					<tr>
						<th  >价格条款</th>
						<td id="jgtk_bak">
						</td>
						<th  >应预付额</th>
						<td>
							${woodCghtJkBak.yyfe }
						</td>
					</tr>
					<tr>
						<th  >订货总量</th>
						<td>
							${woodCghtJkBak.dhzl }
						</td>
						<th  >数量单位</th>
						<td id="sldw_bak">
						</td>
					</tr>
					<tr>
						<th  >定金</th>
						<td>
							${woodCghtJkBak.dj }
						</td>
						<th  >货款</th>
						<td>
							${woodCghtJkBak.hk }
						</td>
					</tr>
					 <tr>
						<th  >币种</th>
						<td id="cgbz_bak">
						</td>
						<th  >汇率</th>
						<td>
							${woodCghtJkBak.hl }
						</td>
					</tr>
					<tr>
						<th  >收款方式 </th>
						<td id="skfs_bak">
						</td>
						<th  >授信类型</th>
						<td id="sxlx_bak">
						</td>
					</tr>
					<tr>
						<th  >溢短（%）</th>
						<td id="yd_bak">
						</td>
						<th  >订货日期</th>
						<td>
							<fmt:formatDate value="${woodCghtJkBak.dhrq }" />
						</td>
					</tr>
					<tr>
						<th  >交货起期</th>
						<td>
							<fmt:formatDate value="${woodCghtJkBak.jhqq }" />
						</td>
						<th  >交货止期</th>
						<td>
						<fmt:formatDate value="${woodCghtJkBak.jhzq }" />
						</td>
					</tr>
					<tr>
						<th  >订货单位  </th>
						<td id="dhdw_bak">
						</td>
						<th  >收货单位</th>
						<td id="sfdw_bak">
						</td>
					</tr>
					<tr>
						<th  >业务员 </th>
						<td>
						${woodCghtJkBak.ywy }
						</td>
						<th  >合同类别</th>
						<td id="htlb_bak">
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
					<table width="98%" class="tableClass">
						<tr>
							<th width="20%" >登记人</th>
							<td width="30%">${woodCghtJkBak.createrName }</td>
							<th width="20%" >登记部门</th>
							<td width="30%">${woodCghtJkBak.createrDept }</td>
						</tr>
					</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>子表变更信息</legend>
					<table id="childTB_bak" ></table>
					<%
						WoodCghtJkBak woodCghtJkBak = (WoodCghtJkBak)request.getAttribute("woodCghtJkBak");
						List<WoodCghtJkMxBak> woodCghtJkMxBakList = woodCghtJkBak.getWoodCghtJkMxBakList();
						ObjectMapper objectMapperBak = new ObjectMapper();
						String childJson_bak = objectMapperBak.writeValueAsString(woodCghtJkMxBakList);
						request.setAttribute("childJson_bak", childJson_bak);
					%>
			</fieldset>
		</form>
		</div>	
<script type="text/javascript">

	$(function() {
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    }
		});

	
		if('${woodCghtJkBak.ghdw }'!=''){
			$.ajax({
				url : '${ctx}/system/downMenu/getBaseInfoName/${woodCghtJkBak.ghdw }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#ghdw_bak').text(data);
				}
			});
		}
			
		
			$.ajax({
				url : '${ctx}/system/downMenu/getBaseInfoName/${woodCghtJkBak.dhdw }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#dhdw_bak').text(data);
				}
			});
		
			
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/jgtk/${woodCghtJkBak.jgtk }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#jgtk_bak').html(data);
				}
			});
			
			
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/sldw/${woodCghtJkBak.sldw }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#sldw_bak').html(data);
				}
			});
			
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/CURRENCY/${woodCghtJkBak.cgbz }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#cgbz_bak').html(data);
				}
			});
			
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/skfs/${woodCghtJkBak.skfs }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#skfs_bak').html(data);
				}
			});
			
			if('${woodCghtJkBak.sxlx }'!=''){
				$.ajax({
					url : '${ctx}/system/downMenu/getDictName/sxlx/${woodCghtJkBak.sxlx }' ,
					type : 'get',
					cache : false,
					success : function(data) {
						$('#sxlx_bak').html(data);
					}
				});
			}
			
			$.ajax({
				url : '${ctx}/system/downMenu/getBaseInfoName/${woodCghtJkBak.sfdw }',
				type : 'get',
				cache : false,
				success : function(data) {
					$('#sfdw_bak').text(data);
				}
			});
	
			$.ajax({
				url : '${ctx}/system/countryArea/getName/${woodCghtJkBak.jkgb }',
				type : 'get',
				cache : false,
				success : function(data) {
					$('#jkgb_bak').html(data);
				}
			});

			
			$.ajax({
				url : '${ctx}/system/downMenu/getDictName/htlb/${woodCghtJkBak.htlb }' ,
				type : 'get',
				cache : false,
				success : function(data) {
					$('#htlb_bak').html(data);
				}
			});
			
			$('#yd_bak').html("${woodCghtJkBak.yd }"+"%");
		
	});
	

	
	var childTB_bak;
	$(function(){
		childTB_bak=$('#childTB_bak').datagrid({
		data : JSON.parse('${childJson_bak}'),
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
     	{field:'waterRate',title:'含水率',sortable:true,width:10,editor:{type:'numberbox', options: {min:0,precision:2,suffix:'%'}}},
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
	    enableRowContextMenu: false
		});
	});
	

	
</script>
</body>
</html>