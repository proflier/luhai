<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/system/sequenceCode/${action}" method="post">
			<input type="hidden" id="id" name="id" value="${sequenceCode.id }" />
				<table width="98%" class="tableClass" >
					<tr>
						<th >名称</th>
						<td >
							<input id="moduleName" name="moduleName" type="text" value="${sequenceCode.moduleName }" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th >编码</th>
						<td >
							<input id="sequenceName" name="sequenceName" type="text" value="${sequenceCode.sequenceName }" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>	
						<th >公式样例</th>
						<td >
							自定义{PREFIX_true}自定义{DATE_日期格式}自定义{FLOW_流水位数}自定义，并且“{}”可以选择一个，注意大小写！{PREFIX_true}表示添加公司;{UNITCODE_unitAbbr}表示按照单位变化编码
						</td>
					</tr>
					<tr>	
						<th >公式</th>
						<td >
							<textarea id="formula" name="formula" class="easyui-validatebox" rows="5" style="width: 90%;margin-top: 5px;" data-options="required:true">${sequenceCode.formula }</textarea>
						</td>
					</tr>
					<tr>	
						<th >备注</th>
						<td >
							<textarea id="comment" name="comment" class="easyui-validatebox" style="width: 90%;margin-top: 5px;" >${sequenceCode.comment }</textarea>
						</td>
					</tr>
				</table>
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dataGrid);
				$.easyui.loaded();
			} 
		});
		
	});
	
	
	
	$(function(){
		//var action = '${action}';
		if('${action}' == 'view') {
			$("#tbGoods").hide();
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			//将数字框改成只读
			$("#mainform").find('.easyui-numberbox').numberbox({
			    disabled:true
			});
			//处理日期控件
			$("#mainform").find(".easyui-my97").each(function(index,element){
				$(element).attr("readonly",true);
				$(element).removeClass("easyui-my97");
				$(element).addClass("easyui-validatebox");
			});
		}
	});
	
	
</script>
</body>
</html>