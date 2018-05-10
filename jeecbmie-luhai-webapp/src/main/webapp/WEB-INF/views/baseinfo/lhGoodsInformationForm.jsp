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
		<form id="mainform"  action="${ctx}/baseInfo/goodsInformation/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">种类</th>
						<td width="30%">
							<input id="goodsInformationId" name="id" type="hidden"  value="${goodsInformation.id }" />
							<input id="goodsTypeCode" name="goodsTypeCode" type="text"  value="${goodsInformation.goodsTypeCode }"/>
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							<input id="infoCode" name="infoCode" type="text"  value="${goodsInformation.infoCode }" 
							class="easyui-validatebox" readonly="readonly" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>品名（中）</th>
						<td>
							<input id="infoName" name="infoName" type="text" value="${goodsInformation.infoName }"
							class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th>品名（英）</th>
						<td>
							<input id="infoNameE" name="infoNameE" type="text" value="${goodsInformation.infoNameE }"/>
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<th>供应商（中）</th> -->
<!-- 						<td> -->
<%-- 							<input id="customerCode" name="customerCode" type="text" value="${goodsInformation.customerCode }"/> --%>
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
							<input name="status"  value="${goodsInformation.status }"  
								class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto',required:true, data: [{label: '1',value: '启用'}, {label: '0',value: '停用'}]" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" id="relField">
					<legend>指标列表</legend>
					<div style="width:98%;border:1px;" >
						<div id="tb_rel" style="padding:5px;height:auto;">
						     <div>	
								<a id="add_rel" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
								<span class="toolbar-item dialog-tool-separator"></span>
						   		<a id="delete_rel" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
						     </div>
						</div>
						<table id="relList" width="98%" class="tableClass"></table>
					</div>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >经办人</th>
						<td>
							<input type="hidden" name="createrNo" value="${goodsInformation.createrNo }" />
							<input type="hidden" name="createrName" value="${goodsInformation.createrName }"/>
							<input type="hidden" name="createrDept" value="${goodsInformation.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${goodsInformation.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
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
		</form>
		
<script type="text/javascript">
var infoIndexRel = {
		relList:{},
		initrelList:	function(){
			if(($("#goodsInformationId").val())==null ||($("#goodsInformationId").val())==""){
				return;
			} 
			infoIndexRel.relList=$('#relList').datagrid({
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
				singleSelect:true,
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
			})
		},
		relForm:'',
		relAdd:function(){
			var idValue = $('#goodsInformationId').val();
			if(idValue==null || idValue==""){
				$.messager.alert('提示','请先保存主表！','info');
				return;
			}
			infoIndexRel.relForm = $("#dlg_rel").dialog({
				method:'GET',
			    title: '添加指标',    
			    width: 700,    
			    height: 400,     
			    href:'${ctx}/baseInfo/goodsInfo/rel/create/'+idValue,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						saveIndex();
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						infoIndexRel.relForm.panel('close');
					}
				}]
			});
		},
		relDelete:function(){
			var row = infoIndexRel.relList.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:'${ctx}/baseInfo/goodsInfo/rel/delete/'+row.id,
						success: function(data){
							var data = eval('(' + data + ')');
							if(data.returnFlag=='success'){
								infoIndexRel.relList.datagrid('clearSelections');
								infoIndexRel.relList.datagrid('reload');
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
	$(function() {
		infoIndexRel.initrelList();
		$("#add_rel").on("click",infoIndexRel.relAdd);
		$("#delete_rel").on("click",infoIndexRel.relDelete);
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
					$.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				var data = eval('(' + data + ')');
		    	if(data.returnFlag=='success'){
		    		operObj.list.datagrid('reload');
		    		$('#goodsInformationId').val(data.returnId);
		    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
		    		infoIndexRel.initrelList();
		    		if(data.currnetSequence!=null){
			    		$('#infoCode').val(data.currnetSequence);
			    	}
					$.easyui.loaded();
		    		return true;
				}else if(data.returnFlag=='fail'){
					parent.$.messager.alert(data.returnMsg);
					$.easyui.loaded();
					return false;
				}  
		    } 
		});
		//类别
		$('#goodsTypeCode').combobox({
				panelHeight : 'auto',
				method:'get',
				required : true,
				url : '${ctx}/baseInfo/lhUtil/goodsTypeItems',
				valueField : 'typeCode',
				textField : 'typeName'
			});
		//供应商
		$('#customerCode').combobox({
				required:false,
				method:'get',
				onChange:showAffiInfoMiddle,
				url : '${ctx}/baseInfo/baseUtil/affiBaseInfoItems',
				valueField : 'customerCode',
				textField : 'customerName'
			});
			var code = "${goodsInformation.customerCode }";
			showAffiInfo(code);
			//----------
	});
	function showAffiInfoMiddle(record,record2){
		showAffiInfo(record);
	}
	function showAffiInfo(code){
		if(code!=null && code!=""){
			$.ajax({
				type:'get',
				dataType:'json',
				url:'${ctx}/baseInfo/baseUtil/affiBaseInfoShow?code='+code,
				success:function(data){
					if(data!=null){
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