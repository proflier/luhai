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
		<form id="mainform"  action="${ctx}/activiti/delegate/${action}" method="post">
			<input id="id" name="id" type="hidden"  value="${delegatePerson.id }" />
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '流程委托', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>流程委托</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">委托模块</th>
						<td colspan="3">
							 <input type="text" id="procDefKey" name="procDefKey"value="${delegatePerson.procDefKey }"/>
						</td>
					</tr>
					<tr>
						<th width="20%">委托人</th>
						<td width="30%">
							<input name='consigner' type="hidden" value="${delegatePerson.consigner }"/>
							${mytag:getUserByLoginName(delegatePerson.consigner).name}
						</td>
						<th width="20%">受委托人</th>
						<td width="30%">
							<input id="mandatary" name="mandatary" type="text" value="${delegatePerson.mandatary }" class="easyui-combotree"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>委托开始时间</th>
						<td>
							<input id="beginDate" name="beginDate" type="text" value='<fmt:formatDate value="${delegatePerson.beginDate }" pattern='yyyy-MM-dd HH:mm:ss'/>' class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true"/>
						</td>
						<th>委托结束时间</th>
						<td>
							<input id="endDate" name="endDate" type="text" value='<fmt:formatDate value="${delegatePerson.endDate }" pattern='yyyy-MM-dd HH:mm:ss'/>' class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${delegatePerson.createrNo }"/>
							<input type="hidden" name="createrName" value="${delegatePerson.createrName }"/>
							<input type="hidden" name="createrDept" value="${delegatePerson.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${delegatePerson.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${delegatePerson.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${delegatePerson.createrName }
						</td>
						<th  >登记部门</th>
						<td>${delegatePerson.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${delegatePerson.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${delegatePerson.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		</form>
		
<script type="text/javascript">
	$(function() {
		if('${action}' == 'view') {
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			$("#mainform").find('.easyui-combotree').combotree({
			    disabled:true
			});
			//处理日期控件
			$("#mainform").find(".easyui-my97").each(function(index,element){
				$(element).attr("readonly",true);
				$(element).removeClass("easyui-my97");
				$(element).addClass("easyui-validatebox");
			});
		}
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
		$('#mandatary').combotree({
			width:145,
			method:'GET',
			url:'${ctx}/workflow/getCompanyUser',
		    idField : 'loginName',
		    textFiled : 'name',
			parentField : 'pid',
			onBeforeSelect:function(node){
				if(node.type!='2'){
					return false;
				}
			},
		    onHidePanel:function(){}
		});
		$('#procDefKey').combobox({
			width:500,
			multiple:true,
			panelHeight :400,
			method: "get",
			url : '${ctx}/process/allProcIns',
			valueField : 'id',
			textField : 'value',
// 			loadFilter : function (data) {
//                 if (data && data instanceof Array) {
//                     data.splice(0, 0, {id: '', value: '请选择...'});
//                 }
//                 return data;
//             },
            onLoadSuccess: function () { //加载完成后,设置选中
    	        var val = $(this).combobox("getData"); 
    	        $(this).combobox("clear");
    	   		var curValue = this.value.split(',');
    	   		for(var j = 0; j < curValue.length; j++){
    	   		 	for(var i = 0; i < val.length; i++){
    					if(val[i].id == curValue[j]){
    		    			$(this).combobox("select",curValue[j]);
    		            } 
    		        } 
    	   		}
    	    },
    	    onHidePanel:function(){}
		});
	});
	
</script>

</body>
</html>