<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<fieldset class="fieldsetClass">
<legend>登记信息</legend>
<form id="mainform" action="${ctx}/importShips/shipReg/${action}" method="post">
	
		<table width="98%" class="tableClass">
			<tr>
				<th width="20%">船编号</th>
				<td width="30%">
					<input  id="id" name="id" type="hidden" value="${shipReg.id }"/>
					<input type="text" id="shipNo" name="shipNo" value="${shipReg.shipNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th width="20%">船名</th>
				<td width="30%">
					<input type="text" id="shipName" name="shipName" value="${shipReg.shipName }" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>航次</th>
				<td>
					<input type="text" id="voyage" name="voyage" value="${shipReg.voyage }" class="easyui-validatebox" />
				</td>
				<th>装船时间</th>
				<td>
					<input type="text" name="shipLoadTime" value="<fmt:formatDate value="${shipReg.shipLoadTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<th>开船时间</th>
				<td>
					<input type="text" name="sailingTime" value="<fmt:formatDate value="${shipReg.sailingTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
				</td>
				<th>运输单位</th>
				<td>
					<input type="text" id="transportPart" name="transportPart" value="${shipReg.transportPart }"  />
				</td>
			</tr>
			<tr>
				<th>最后更新日期</th>
				<td>
					<input type="text" id="lastUpdateTime" name="lastUpdateTime" value="<fmt:formatDate value="${shipReg.lastUpdateTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" />
				</td>
				<th>登记人</th>
				<td>
					<input id="createrName" name="createrName" readonly="readonly" type="text" value="${empty shipReg.createrName ? sessionScope.user.name : shipReg.createrName }" class="easyui-validatebox"  />
				</td>
			</tr>
			<tr>
				<th>采购合同号</th>
				<td colspan="3">
					<input type="text" style="width:50%;" id="importContractNo" readonly="readonly" name="importContractNo" value="${shipReg.importContractNo }" class="easyui-validatebox" data-options="required:true,prompt: '请在合同列表中添加!'" />
				 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelect()"></a>
				</td>
			</tr>
		</table>
</form>
</fieldset>
<fieldset class="fieldsetClass">
<legend>合同信息</legend>
<div   style="height:355px">
	<div id="tb_importContract" style="padding:5px;height:auto">
		<form id="searchFrom_importContract" action="">
		    <input type="text" name="filter_LIKES_hth" class="easyui-validatebox" data-options="width:150,prompt: '合同号'"/>
		    <input type="text" name="filter_LIKES_xyh" class="easyui-validatebox" data-options="width:150,prompt: '协议号'"/>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_importContract()">查询</a>
		    <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_importContract()">重置</a>
		</form>
	</div>
	<table id="dg_importContract" class="tableClass">
	</table>
</div>	
</fieldset>
<script type="text/javascript">
var dg_importContract;
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTipNew(data,dg);
	    }
	});
	
	
	dg_importContract=$('#dg_importContract').datagrid({  
		method: "get",
	    url:'${ctx}/woodCght/woodCghtJK/json?filter_EQS_state=生效&filter_EQS_closeOrOpen=运行中',
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,	
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
			{field:'hth',title:'合同号',sortable:true,width:25}, 
			{field:'xyh',title:'协议号',sortable:true,width:25},
			{field:'ghdw',title:'供货单位',sortable:true,width:25,
				formatter: function(value,row,index){
					var val;
					if(row.ghdw!=''&&row.ghdw!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/downMenu/getBaseInfoName/"+row.ghdw,
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
			{field:'dhrq',title:'订货日期 ',sortable:true,width:25},
			{field:'createrDept',title:'申请部门',sortable:true,width:25},
			{field:'createDate',title:'创建时间',sortable:true,width:25},
			{field : 'id',title : '操作',sortable : true,width : 20,
				formatter : function(value, row, index) {
					var str = "";
					str += "<a  name='opera'  href='javascript:void(0)'  onclick='addHTH("
							+ value
							+ ");'></a> &nbsp"
					return str;
				}
			} 
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb_importContract',
	    onLoadSuccess:function(data){    
	        $("a[name='opera']").linkbutton({text:'添加',plain:true,iconCls:'icon-add'});    
	}
	});
	
	$('#transportPart').combobox({
		method: "get",
		url:'${ctx}/system/downMenu/jsonBaseInfo?filter_EQS_customerType=8',
		valueField : 'id',
		textField : 'customerName',
		onLoadSuccess: function () { 
			$("#lastUpdateTime").combo({disabled:true});
		}
	});
});

function cx_importContract(){
	var obj=$("#searchFrom_importContract").serializeObject();
	dg_importContract.datagrid('reload',obj);
}

function reset_importContract(){
	$("#searchFrom_importContract")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom_importContract").serializeObject();
	dg_importContract.datagrid('reload',obj); 
}
function addHTH(idvalue){
	$.ajax({
		 url:'${ctx}/woodCght/woodCghtJK/json?filter_EQL_id='+idvalue,
		type : 'get',
		dataType: "json",//必须定义此参数
		cache : false,
		success : function(data) {
			idvalue = (data.rows)[0].hth;
			var currenctValue = $("#importContractNo").val();
			var endValue ="";
			var flag = true;
			if(currenctValue!=""){
				var arrayObj = new Array();
				arrayObj =currenctValue.split(",");
				for(var i=0;i<arrayObj.length;i++){
					if(arrayObj[i]==idvalue){
						flag = false;
					}
				}
				if(flag){
					$("#importContractNo").val(currenctValue+","+idvalue);
				}
			}else{
				$("#importContractNo").val(idvalue);
			}
		}
	});
}

//清除选择
function clearSelect() {
	$('#importContractNo').val("");
}
</script>
</body>
</html>