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
	<form id="bmform" action="${ctx}/baseInfo/goodsInfo/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>类别</th>
			<td>
			<input type="hidden" name="id" value="${code.id}" />
			<input type="text" id="goodsType" name="type" value="${code.type}" class="easyui-combobox"/>
			</td>
			<th><font style="vertical-align:middle;color:red" ></font>材种</th>
			<td><input type="text" id="species" name="species" value="${code.species}" class="easyui-combobox"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>等级</th>
			<td><input name="classes" id="classes" type="text" value="${code.classes}" class="easyui-combobox"/></td>
		
			<th><font style="vertical-align:middle;color:red" ></font>宽厚</th>
			<td><input name="spec" type="text" id="spec" value="${code.spec}" class="easyui-combobox"/></td>
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>长度</th>
			<td><input name="length" id="length" type="text" value="${code.length}"  class="easyui-combobox"/>
			</td>
			<th><font style="vertical-align:middle;color:red" ></font>品牌</th>
			<td><input name="brand" id="brand" type="text" value="${code.brand}"  class="easyui-combobox"/>
			</td>	
		</tr>
		<tr>
			<th colspan="4">
			<input type="button" id="ck" value="查看编码" />
			</th>			
		</tr>
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>商品编码</th>
			<td><input name="goodsNo" id="goodsNo"  class="easyui-validatebox" value="${code.goodsNo}" />
			</td>
			<th><font style="vertical-align:middle;color:red" ></font>商品名称</th>
			<td><input name="goodsName" id="goodsName" type="text" class="easyui-validatebox" value="${code.goodsName}" />
			</td>
		</tr>	
		<tr>
			<th><font style="vertical-align:middle;color:red" ></font>立方数</th>
			<td colspan="3"><input name="lfs" id="lfs"  class="easyui-validatebox" value="${code.lfs}" />
			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
$(function(){
	//*****初始化
	var typeCode='00';
	var speciesCode='00';
	var classesCode='00';
	var specCode='00';
	var lengthCode='00';
	var brandCode='000';
	var typeName='';
	var speciesName='';
	var classesName='';
	var specName='';
	var lengthName='';
	var brandName='';
	//*******************
	$('#mainform').form({    
	    onSubmit: function(){ 
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },   
	    success:function(data){
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg);
	    }
	}); 
	$('#goodsType').combobox({
		panelHeight:'auto',
	    url:'${ctx}/baseInfo/goodsInfo/getType',
	    valueField:'name',
	    textField:'name',
	    onSelect: function (data) {
	    	//*************各个字段根据此类别联动并更新
	    	typeCode=data.code;
	    	typeName=data.name;
	    	speciesCode='00';//
	    	classesCode='00';
	    	specCode='00';
	    	lengthCode='00';
	    	brandCode='000';
	    	speciesName='';
	    	classesName='';
	        specName='';
	    	lengthName='';
	    	brandName='';
	    	
	    	var url1 = '${ctx}/baseInfo/goodsInfo/setSpecies?name='+data.name;
	    	$('#species').combobox('clear');
            $('#species').combobox('reload', url1);

            var url2 = '${ctx}/baseInfo/goodsInfo/setClasses?name='+data.name;  
            $('#classes').combobox('clear');
            $('#classes').combobox('reload', url2);
           
            var url3 = '${ctx}/baseInfo/goodsInfo/setSpec?name='+data.name; 
            $('#spec').combobox('clear');
            $('#spec').combobox('reload', url3);
            
            var url4 = '${ctx}/baseInfo/goodsInfo/setLength?name='+data.name; 
            $('#length').combobox('clear');
            $('#length').combobox('reload', url4);
           
            var url5 = '${ctx}/baseInfo/goodsInfo/setBrand?name='+data.name; 
            $('#brand').combobox('clear');
            $('#brand').combobox('reload', url5);
        }
	});
	$('#species').combobox({
// 		panelHeight:'300',
	    valueField:'name',
	    textField:'name', 
	    onSelect: function(data){
	    	speciesCode=data.code;
	    	speciesName=' '+data.name;
	    	
	    }
	});
	$('#classes').combobox({
// 		panelHeight:'300',
	    valueField:'name',
	    textField:'name',
	    onSelect: function(data){
	    	classesCode=data.code;
	    	classesName='#'+data.name;
	    	
	    }
	});
	$('#spec').combobox({
// 		panelHeight:'300',
	    valueField:'name',
	    textField:'name'  ,
	    onSelect: function(data){
	    	specCode=data.code;
	    	specName=' '+data.name;
	    	
	    }
	});
	$('#length').combobox({
// 		panelHeight:'300',
	    valueField:'name',
	    textField:'name'  ,
	    onSelect: function(data){
	    	lengthCode=data.code;
	    	lengthName=' *'+data.name;
	    	
	    }
	});
	$('#brand').combobox({
// 		panelHeight:'300',
	    valueField:'name',
	    textField:'name' ,
	    onSelect: function(data){
	    	brandCode=data.code;
	    	brandName=' '+data.name;
	    	
	    }
	});
	 $("#ck").click(function(){
		 countCode(typeCode+speciesCode+classesCode+specCode+lengthCode+brandCode,
	    			typeName+speciesName+classesName+specName+lengthName+brandName);
		 var str = $("#goodsNo").val().substring(0,2);
		 if(str == '01'){//如果为板材商品，通过商品编码计算单片体积
		 $.ajax({
 				url : '${ctx}/baseInfo/goodsInfo/getVolumeByCode/' + $("#goodsNo").val()+'/'+$('#goodsType').combobox('getValue'),
 				type : 'get',
 				cache : false,
 				success : function(data) {
 					data = parseFloat(data);
 					$("#lfs").val(data);
 				}
 			});
		}
	 });
});

function countCode(codeParam,nameParam){
	  $("#goodsNo").val(codeParam);
	  $("#goodsName").val(nameParam);
	  $("#lfs").val('');
}
</script>
</body>
</html>