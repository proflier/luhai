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
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '信息', iconCls: false, refreshable: false" >	
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%" >主合同编号</th>
						<td  width="30%" >
							<input type="hidden" name="id" id="id" value="${freightLetter.id}" />
							${freightLetter.contractNo }
						</td>
						<th  width="20%" >承运函编号</th>
						<td  width="30%" >
							${freightLetter.letterNo }
						</td>
					</tr>
					<tr>
						<th>发起码头</th>
						<td>
							${mytag:getAffiBaseInfoByCode(freightLetter.lineFrom)}
						</td>
						<th>收货单位</th>
						<td>
							${mytag:getAffiBaseInfoByCode(freightLetter.lineTo)}
						</td>
					</tr>
					<tr>
						<th>结算单价</th>
						<td>
							${freightLetter.unitPrice }
						</td>
						<th>数量单位</th>
						<td>
							${fns:getDictLabelByCode(freightLetter.numberUnit,'sldw','')}
						</td>
					</tr>
					<tr>
						<th>承运时间</th>
						<td>
							<fmt:formatDate value="${freightLetter.transitTime}" />
						</td>
						<th>业务经办人</th>
						<td>
							${mytag:getUserByLoginName(freightLetter.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${freightLetter.remark }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  width="20%"  >制单人</th>
						<td  width="30%" >
							<input type="hidden" name="createrNo" value="${freightLetter.createrNo }"/>
							<input type="hidden" name="createrName" value="${freightLetter.createrName }"/>
							<input type="hidden" name="createrDept" value="${freightLetter.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${freightLetter.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${freightLetter.createrName }</td>
						<th  width="20%"  >制单部门</th>
						<td  width="30%" >${freightLetter.createrDept }</td>
					</tr>
					<tr>
						<th  width="20%"  >制单时间</th>
						<td  width="30%" colspan="3" >
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
</script>
</body>
</html>
