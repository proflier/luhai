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
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">种类</th>
						<td width="30%" id="goodsTypeCode">
						</td>
						<th width="20%">编码</th>
						<td width="30%">
						<input id="goodsInformationId" value="${goodsInformation.id }" type="hidden"/>
							${goodsInformation.infoCode }
						</td>
					</tr>
					<tr>
						<th>品名（中）</th>
						<td>
							${goodsInformation.infoName }
						</td>
						<th>品名（英）</th>
						<td>
							${goodsInformation.infoNameE }
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<th>供应商（中）</th> -->
<!-- 						<td id="customerCode"> -->
<!-- 						</td> -->
<!-- 						<th>供应商（英）</th> -->
<!-- 						<td id="customerCodeE"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<th>国家（中）</th>
						<td id="countryArea">
						</td>
						<th>国家（英）</th>
						<td id="countryAreaE">
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td colspan="3">
							${goodsInformation.status == 0?'停用':'启用'}
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" id="relField">
					<legend>指标列表</legend>
					<div>
						<table id="relList"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >经办人</th>
						<td>
							${goodsInformation.createrName }</td>
						<th  >经办部门</th>
						<td>${goodsInformation.createrDept }</td>
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${goodsInformation.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		
<script type="text/javascript">
	$(function() {
		$('#relList').datagrid({
			method: "get",
			url: '${ctx}/baseInfo/goodsInfo/rel/son/'+$("#goodsInformationId").val(),
		  	fit : false,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			striped:true,
			idField : 'id',
			pagination:true,
			rownumbers:true,
			pageNumber:1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			rownumbers:true,
			extEditing:false,
		    columns:[[ 
				{field:'id',title: 'id',hidden:true},
				{field:'indexName',title:'名称',width:20,
					formatter: function(value,row,index){
						return row.goodsIndex.indexName;
					}},  
				{field:'indexCode',title:'编码',width:20,
						formatter: function(value,row,index){
							return row.goodsIndex.indexCode;
						}},
				{field:'indexNameE',title:'英文名称',width:15,
							formatter: function(value,row,index){
								return row.goodsIndex.indexNameE;
							}},
				{field:'unit',title:'单位',sortable:true,width:20,
					formatter: function(value,row,index){
						var unit_t = '';
						if(row.goodsIndex.unit!=null && row.goodsIndex.unit!=""){
							if(row.goodsIndex.unit=='%'){row.goodsIndex.unit='%25';}
							$.ajax({
								url : '${ctx}/system/dictUtil/getDictNameByCode/INDEXUNIT/'+row.goodsIndex.unit ,
								type : 'get',
								cache : false,
								async: false,
								success : function(data) {
									unit_t = data;
								}
							});
						}
						return unit_t;
					}	
				}
		    ]],
		    sortName:'id',
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false
		});
		$.ajax({
			url : '${ctx}/baseInfo/lhUtil/goodsTypeShow?code=${goodsInformation.goodsTypeCode }' ,
			type : 'get',
			cache : false,
			async: false,
			success : function(data) {
				$("#goodsTypeCode").html(data);
			}
		});
		var code = "${goodsInformation.customerCode }";
		showAffiInfo(code);
	});
	function showAffiInfo(code){
		if(code!=null && code!=""){
			$.ajax({
				type:'get',
				dataType:'json',
				url:'${ctx}/baseInfo/baseUtil/affiBaseInfoShow?code='+code,
				success:function(data){
					if(data!=null){
						$("#customerCode").html(data['customerName']);
						$("#customerCodeE").html(data['customerNameE']);
						$("#countryArea").html(data['countryName']);
						$("#countryAreaE").html(data['countryNameE']);
					}
				},
				error :function(){
				}
			});
		}
	}
	
	
</script>

</body>
</html>