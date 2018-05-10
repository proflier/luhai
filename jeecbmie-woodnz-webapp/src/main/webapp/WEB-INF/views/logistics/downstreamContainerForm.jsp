<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<form id="containerform" action="${ctx}/logistics/downstreamContainer/${actionContainer}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="20%">集装箱号</th>
			<td width="30%">
			<input type="hidden" id="downstreamContainerId" name="id" value="${downstreamContainer.id}" />
			<input name="parentId" type="hidden" value="${downstreamContainer.parentId}" />
			<input name="boxNo"  id="boxNo" value="${downstreamContainer.boxNo}" type="text" class="easyui-validatebox" 
					data-options="required:true" />
			</td>
			<th width="20%">商品描述</th>
			<td width="30%"><input name="goodsDes" id="goodsDes"  value="${downstreamContainer.goodsDes}" type="text" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<th>商品编码</th>
			<td><input name="goodsNo" id="goodsNo" type="text" value="${downstreamContainer.goodsNo}" class="easyui-validatebox" 
				data-options="required:true ,validType:'minLength[13]'" onchange="setGoodsName()" /></td>
			<th>商品名称</th>
			<td><input name="goodsName" id="goodsName" type="text" class="easyui-validatebox" data-options="required:true"  
				value="${downstreamContainer.goodsName}" /></td>
		</tr>
		<tr>
			<th>片数</th>
			<td><input name="pNum" id="pNum" type="text" value="${downstreamContainer.pNum}" onkeyup="countNum();" class="easyui-numberbox" data-options="required:true"/>
			</td>
			<th>单片体积</th>
			<td><input name="volume" id="volume" type="text" value="${downstreamContainer.volume}" onkeyup="countNum();" class="easyui-numberbox" data-options="required:true,precision:'3'"/>				
			</td>			
		</tr>
		<tr>
			<th>件/根数</th>
			<td> 
			<input name="pieceNum" id="pieceNum" type="text"  value="${downstreamContainer.pieceNum}"  onkeyup="countNum();" class="easyui-numberbox" data-options="required:true"/>	
			</td>
			<th>综合合同号</th>
			<td><input id="cpContractNo" name="cpContractNo" type="text" value="${downstreamContainer.cpContractNo}" class="easyui-validatebox" />				
			</td>						
		</tr>
		<tr>
			<th>数量</th>
			<td>
				<input name="num"  id="num" type="text"  value="${downstreamContainer.num}"   onkeyup="countAmount();" class="easyui-numberbox" data-options="required:true,precision:'3'"/>	
			</td>
			<th>数量单位</th>
			<td><input id="numberUnit"  name="numberUnits" type="text"  value="${downstreamContainer.numberUnits}"/>				
			</td>			
		</tr>
		<tr>
			<th>销售价</th>
			<td><input name="purchasePrice" id="purchasePrice" type="text"  onkeyup="countAmount();" value="${downstreamContainer.purchasePrice}"  class="easyui-numberbox" data-options="required:true,precision:'2'"/>
			</td>
			<th>币种</th>
			<td><input id="currencyBox" name="currency" type="text" value="${downstreamContainer.currency}" data-options="panelHeight:'auto'"/>				
			</td>			
		</tr>
		<tr>
			<th>总价</th>
			<td>
			<input name="totalPrice" id="totalPrice" type="text"  value="${downstreamContainer.totalPrice}" class="easyui-numberbox" data-options="required:true,precision:'2'"/>
			</td>
			<th>登记时间</th>
			<td>
			<input name="createDate" type="text" value="<fmt:formatDate value="${downstreamContainer.createDate}" />" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" data-options="required:false"/>
			</td>
		</tr>
		<tr>
			<th>是否濒危物种</th>
			<td ><input name="ifDanger" id="ifDanger" type="text" value="${downstreamContainer.ifDanger}"/>				
			</td>
			<th>含水率%</th>
			<td ><input name="waterRate" id="waterRate" type="text" value="${downstreamContainer.waterRate}" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>				
			</td>
		</tr>
		<tr>
			<th>登记人</th>
				<td>${empty downstreamContainer.createrName ? sessionScope.user.name : downstreamContainer.createrName }</td>
			<th>登记部门</th>
				<td>${empty downstreamContainer.createrDept ? sessionScope.user.organization.orgName : downstreamContainer.createrDept }</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">


$(function(){  
	
	$('#containerform').form({
	    onSubmit: function(){    
	    	var isValid = $('#containerform').form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	var data = eval('(' + data + ')');
	    	if(data.returnFlag=='success'){
	    		dgContainer.datagrid('clearSelections');
	    		dgContainer.datagrid('reload');
	    		$('#downstreamContainerId').val(data.returnId);
	    		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
	    		return true;
	    	}else if(data.returnFlag=='fail'){
	    		parent.$.messager.alert(data.returnMsg);
	    		return false;
	    	}
	    }
	}); 
	
});
//数量 = 片数*根数*单片体积  ；
function countNum(){
	var pNum = $('#pNum').val();//片数
	var pieceNum = $('#pieceNum').val();//根数
	var volume = $('#volume').val();//单片体积
	var num = $('#num').val();//数量
	var r = parseFloat(pNum*pieceNum*volume);
	$('#num').numberbox('setValue', r.toFixed(3));
	countAmount();
}

// 总价 = 数量*采购价
function countAmount(){
	var num = $('#num').val();//数量
	var purchasePrice = $('#purchasePrice').val();//采购价
	var r = parseFloat(num*purchasePrice);
	$('#totalPrice').numberbox('setValue', r.toFixed(3));
}

function setGoodsName(){
        var spbm = $("#goodsNo").val();
        if(spbm.length != 13){
        	$.messager.alert('提示','编码长度应该为13位！请检查！','info');
        	$("#goodsName").val('');
        	$("#volume").numberbox('setValue', 0);
        	spbm='';
        	return;
        }else{
 			$.ajax({
 				url : '${ctx}/baseInfo/goodsInfo/getNameByCode/' + spbm,
 				type : 'get',
 				cache : false,
 				success : function(data) {
 					$("#goodsName").val(data);
 				}
 			});
 			var str = $("#goodsNo").val().substring(0,2);
 			if(str == '01'){//如果为板材商品，通过商品编码计算单片体积
	 			$.ajax({
	 				url : '${ctx}/baseInfo/goodsInfo/getVolumeByCode/' + $("#goodsNo").val()+'/'+'板材',
	 				type : 'get',
	 				cache : false,
	 				success : function(data) {
	 					data = parseFloat(data);
	 					$("#volume").numberbox('setValue', data);;
	 					countNum();
	 				}
	 			});
 			}
        }
}

function initSPBM(editIndex){
	var currenctObject = $('#childTB').datagrid('getEditor', { index: editIndex, field: 'goodsNo' });
	currenctObject.target.change(function () {
        var spbm = $(currenctObject.target).val();
        if(spbm.length>13){
        	$.messager.alert('提示','编码长度超过13位！请检查！','info');
        }else{
        	 var destObject = $('#childTB').datagrid('getEditor', {index:editIndex,field:'goodsName'});
 			$.ajax({
 				url : '${ctx}/baseInfo/goodsInfo/getNameByCode/' + spbm,
 				type : 'get',
 				cache : false,
 				success : function(data) {
 					$(destObject.target).val(data);
 				}
 			});
        }
	});
}

//币种列表
$('#currencyBox').combobox({
	panelHeight:'auto',
	url:'${ctx}/system/downMenu/getDictByCode/currency',
    valueField:'id',
    textField:'name', 
});


//数量单位列表
$('#numberUnit').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/downMenu/getDictByCode/sldw',
    valueField:'id',
    textField:'name', 
});

//是否濒危物种列表
$('#ifDanger').combobox({
	panelHeight:'auto',
	method: "get",
	url:'${ctx}/system/downMenu/getDictByCode/YESNO',
    valueField:'id',
    textField:'name', 
});

//综合合同号列表，从此到单货物明细中的综合合同号取值
// $('#cpContractNo').combobox({
// 	method: "get",
// 	url:'${ctx}/logistics/bills/getCpContractNo', 
//     valueField:'cpContractNo',
//     textField:'cpContractNo', 
//     required:true
// });

$.extend($.fn.validatebox.defaults.rules, {    
    minLength: {    
        validator: function(value, param){    
            return value.length == param[0];    
        },    
        message: '请输入 {0} 个字符.'   
    }    
});   
</script>
</body>
</html>

