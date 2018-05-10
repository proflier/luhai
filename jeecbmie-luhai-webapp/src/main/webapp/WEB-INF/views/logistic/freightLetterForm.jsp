<%@page import="com.cbmie.lh.logistic.entity.FreightLetter"%>
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
		<form id="mainform"  action="${ctx}/logistic/freightLetter/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th>主合同编号</th>
						<td>
							<input type="hidden" name="id" id="id" value="${freightLetter.id}" />
							<input id="contractNo" name="contractNo" type="text" value="${freightLetter.contractNo }" class="easyui-validatebox" readonly="readonly"
							data-options="required:true"/>
							<a id="contractListId" href="javascript:void(0)" class="easyui-linkbutton" 
							iconCls="icon-standard-page-white-edit" plain="true" onclick="toContractList()">合同列表</a>
						</td>
						<th>承运函编号</th>
						<td>
							<input id="letterNo" name="letterNo" type="text" value="${freightLetter.letterNo }" class="easyui-validatebox"
							data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>发起码头</th>
						<td>
							<mytag:combobox name="lineFrom" type="customer" value="${freightLetter.lineFrom}" parameters="10230007"></mytag:combobox>
						</td>
						<th>收货单位</th>
						<td>
							<mytag:combobox name="lineTo" value="${freightLetter.lineTo}" type="customer" parameters="10230001or10230003" required="false" hightAuto="false"/>
						</td>
					</tr>
					<tr>
						<th>结算单价</th>
						<td>
							<input id="unitPrice" name="unitPrice" type="text" value="${freightLetter.unitPrice }" class="easyui-numberbox" data-options="min:0,precision:2"/>
						</td>
						<th>数量单位</th>
						<td>
							<mytag:combobox name="numberUnit" value="${freightLetter.numberUnit}" type="dict" required="false" parameters="sldw"/>
						</td>
					</tr>
					<tr>
						<th>承运时间</th>
						<td>
							<input name="transitTime" type="text"  value="<fmt:formatDate value="${freightLetter.transitTime}" />" 
						class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						</td>
						<th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${freightLetter.businessManager}"  required="true" disabled="true" type="userTree" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="3" cols="80" id="remark" class="easyui-validatebox" name="remark">${freightLetter.remark }</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >制单人</th>
						<td>
							<input type="hidden" name="createrNo" value="${freightLetter.createrNo }"/>
							<input type="hidden" name="createrName" value="${freightLetter.createrName }"/>
							<input type="hidden" name="createrDept" value="${freightLetter.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${freightLetter.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${freightLetter.createrName }</td>
						<th  >制单部门</th>
						<td>${freightLetter.createrDept }</td>
						<th  >制单时间</th>
						<td>
							<fmt:formatDate value="${freightLetter.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=FreightLetter.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${freightLetter.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
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
				successTipNew(data,dg);
				$.easyui.loaded();
			} 
		});
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
		    		tabIndex = index;//更换tab
		    	}else{
		    		if(index != 0){
		    			parent.$.messager.alert('提示','请先保存主表信息!!!');
		    		}
		    		$("#mainDiv").tabs("select", tabIndex);//当前tab
		    		return ;
		    	}
		    }
		});
	});
	$(function(){
		if('${action}' == 'view') {
			$("#contractListId").hide();
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
			$("#mainform").find('input').attr("readonly",true);
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
